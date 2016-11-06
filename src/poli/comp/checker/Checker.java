package poli.comp.checker;

import poli.comp.util.AST.*; //importing all AST node classes


class Checker implements Visitor{

	private IdentificationTable idt;
	//TODO dar um jeito de acessar o tipo de um ID por uma decoracao no ASTId.

	public Checker(){
			this.idt = new IdentificationTable();
	}


	public AST check(AST a){

		List<AST> scopeTracker = new ArrayList<AST>();

		//TODO: Even though we expect the parser to work reliably (i.e. throw an exception if at runtime it realizes the input code is wrong)
		//, would it be conceptually *wrong* to check for "a instanceof ASTProgram" here in this method?

		//So what we receive is an AST node which happens to be the root of the AST.
		//Assuming the Parser is working correctly, what we get in a is an ASTProgram node,
		// so the call to a.visit below will in turn call (this checker object, provided to a via argument).visitProgram
		a.visit(this,scopeTracker);

		//The tree is now decorated, let's return it.
		return a;

	}

	Object visitProgram(ASTProgram p, Object scopeTracker) throws SemanticException{

		//TODO add ASTProgram to scopeTracker to account for the global scope?

		//Checks globals
		for (ASTDeclarationGroup dg : p.getGlobalDeclarationGroups()){
			dg.visit(this, scopeTracker);//TODO change o
		}

		//Checks function declarations
		for (ASTFunctionDeclaration fd: p.getFunctionDeclarations()){
			fd.visit(this, scopeTracker);
		}

		//Checks subprogram declarations
		for(ASTSubprogramDeclaration sd : p.getSubprogramDeclarations()){
			sd.visit(this, scopeTracker);
		}

		//Checks main program
		p.getMainProgram().visit(this,scopeTracker);

	}

	Object visitMainProgram(){

	}



	Object visitDeclarationGroup(ASTDeclarationGroup dg, Object scopeTracker) throws SemanticException{
			//TODO: check for rules:

			String dgType = dg.getType().getSpelling();
			List<ASTSingleDeclaration> declarations = dg.getDeclarations();
			Map<ASTSingleDeclaration,ASTExpression> assignmentMap = dg.getAssignmentMap();
			ASTExpression currentExpression=null;

			for (ASTSingleDeclaration dec: declarations){
				dec.visit(this, scopeTracker)

				//Checking the assigned expression (if appicable)
				if(assignmentMap.containsKey(dec)){
					currentExpression = map.get(dec); //exp can be null
					currentExpression.visit(this,scopeTracker);

					if currentExpression.getTypeString()!=dgType{
						throw new SemanticException("Assigning an expresson with return type "+currentExpression.getTypeString()+" to the
						variable"+currentID.getSpelling()+", which is of type "+dgType);
					}

				}
			}
			return null;
	}

	Object visitFunctionDeclaration(ASTFunctionDeclaration fd, Object scopeTracker) throws SemanticException{
		//TODO: Check for rules:

		scopeTracker.add(fd); //Adding the function block to the scope tracker

		ASTType functionType = fd.getType();
		ASTIdentifier functionName = fd.getIdentifier();
		List<ASTSingleDeclaration> l_params = fd.getParams();
		List<ASTStatement> functionStatements = fd.getStatements();

		//Checking name and putting it on global scope
		idt.enter(functionName.getSpeling(),fd);

		// Increasing the scope, cause we will visit params
		idt.openScope();

		//Checking params
		for (ASTSingleDeclaration dec: functionParams) {
			dec.visit(this,scopeTracker);
		}

		//Opening a scope for local variables
		idt.openScope();

		//Checking statements, have an if / else ifs with types, check return etc
		for( Statement stt : functionStatements ){
			stt.visit(this,scopeTracker);
		}

		idt.closeScope();
		idt.closeScope();
		scopeTracker.remove(fd);

		return fd; //Really dont know what to return here ¯\_(ツ)_/¯
	}

	//This is basically a copy of the above, without the type because its a Subprogram
	//TODO maybe merge those two and have if statements for the functionType part and for the AST classes?
	Object visitSubprogramDeclaration(ASTFunctionDeclaration spd, Object scopeTracker) throws SemanticException{{

		scopeTracker.add(spd); //Adding the function block to the scope tracker

		ASTIdentifier functionName = spd.getIdentifier();
		List<ASTSingleDeclaration> l_params = spd.getParams();
		List<ASTStatement> functionStatements = spd.getStatements();

		//Checking name and putting it on global scope
		idt.enter(functionName.getSpeling(),spd);

		// Increasing the scope, cause we will visit params
		idt.openScope();

		//Checking params
		for (ASTSingleDeclaration dec: functionParams) {
			dec.visit(this,scopeTracker);
		}

		//Opening a scope for local variables
		idt.openScope();

		//Checking statements, have an if / else ifs with types, check return etc
		for( Statement stt : functionStatements ){
			stt.visit(this,scopeTracker);
		}

		idt.closeScope();
		idt.closeScope();
		scopeTracker.remove(spd);

		return spd; //Really dont know what to return here ¯\_(ツ)_/¯
	}

	Object visitExpression(ASTExpression e, Object scopeTracker) throws SemanticException{
		String returnTypeString;

		// do things (visit & maybe decorate more)
		//Decorating the expression with its checked return type
		e.setTypeString(returnTypeString);
		return returnTypeString;
	}

	//Checks an ID that was found in the code. Doesn't add it to IDT - this is done on visitSingleDeclaration.
	Object visitIdentifier(ASTIdentifier id, Object scopeTracker) throws SemanticException{
		//TODO check rules:
		//TODO unfinished.
		if(!idt.containsKey(id.getSpelling())){
			throw new SemanticException("Trying to refer to identifier "+id.getSpelling()
												 +",which is not declared within that scope");
		}

	}

	//Checks a declaration, adding it to the idt or raising an exception if there is a prvious variable with the same id
	Object visitSingleDeclaration(ASTSingleDeclaration sd, Object scopeTracker) throws SemanticException{
		//We are linking the idt key to the ASTSingleDeclaration and not the ASTIdentifier object, so that when
		//we wanna check type consistency we can have access to the ASTType object contained in the ASTSingleDeclaration object.

		//We don't need to check if the key already exists cause idt.enter does that and raises exceptions internally.

		String idSpelling = sd.getIdentifier().getSpelling();
		idt.enter(idSpelling,ASTSingleDeclaration);

	}

	Object visitIfStatement(ASTIfStatement ifStt, Object scopeTracker) throws SemanticException{

	}

	Object visitLoop(ASTLoop loopStt, Object scopeTracker) throws SemanticException{
		//check expression
		ASTExpression condition = loopStt.getCondition();
		String conditionType = condition.visit(this,scopeTracker);
		if(!conditionType.equals("LOGICAL")){
			throw new SemanticException("Loop conditon must be evaluated to a boolean value, but it's being evaluated to a "+conditionType+" value");
		}

		idt.openScope();
		scopeTracker.add(loopStt);
		List<ASTStatement> statements = loopStt.getStatements();
		for(ASTStatement stt : statements){
			stt.visit(this,scopeTracker);
		}
		idt.closeScope();
		scopeTracker.add(loopStt);

		return loopStt; // Agin, what the hell should I even return? :(
	}

	Object visitExpression(){
		//return a string with the exp type.
	}

	Object visitLoopControl(ASTLoopControl lc, Object scopeTracker) throws SemanticException{
		//TODO check for rules:

		//Checks if break/continue statement is inside loop
		List<AST> cScopeTracker = (List<AST>) scopeTracker; //c for Casted
		for(AST node : cScopeTracker){
			if (node instanceof ASTLoop) return null;
		}

		//If we get here, the loop control statemet is not in a loop
		throw new SemanticException("The "+()(lc instanceof ASTLoopExit)?("EXIT"):("CONTINUE"))+" statement is not insde of a loop");
	}

	Object visitReturnStatement(ASTReturnStatement rs, Object scopeTracker){
		List<AST> cScopeTracker = (List<AST>) scopeTracker; //c for Casted

		//if just Returns
			//find Subprogram
		//else
			//Find function
				//	Get function type, check for compatibility with returned expression's type


	}

	//visit FUNCTION_DECL
	// open scope for params
	// open scope for locals
	// close for params
	// close for locals

}
