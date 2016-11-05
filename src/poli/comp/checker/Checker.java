package poli.comp.checker;

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
		for (ASTDeclarationGroup dg : p.getGlobalDeclarations()){
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

	Object visitStatement(){

	}

	Object visitDeclarationGroup(ASTDeclarationGroup dg, Object scopeTracker) throws SemanticException{
			//TODO: check for rules:
			//TODO maybe decorate with returnType?
			String dgType = dg.getType().getSpelling();
			Map<ASTIdentifier,ASTExpression> declarations = dg.getDeclarations();
			for (Map.Entry<ASTIdentifier,ASTExpression> entry: declarations.entrySet()){
				ASTIdentifier currentId = entry.getKey();
				ASTExpression currentExpression = entry.getValue();

				//Checking the Id
				currentId.visit(this, scopeTracker);

				//Checking the assigned expression (if appicable)
				if(currentExpression!=null){ //second item in the tuple is the (optional) expression
					currentExpression.visit(this,scopeTracker);
					if currentExpression.getReturnType()!=dgType{
						throw new SemanticException("Assigning an expresson with type "+currentExpression.getReturnType()+" to a variable with type "+dgType);
					}
				}
			}
			return null;
	}

	Object visitFunctionDeclaration(ASTFunctionDeclaration fd, Object scopeTracker) throws SemanticException{
		//TODO: Check for rules:

		ASTType functionType = fd.getType();
		ASTIdentifier functionName = fd.getIdentifier();
		Map<ASTType,ASTIdentifier> functionParams = fd.getParams();
		List<ASTStatement> functionStatements = fd.getStatements();

		//Chceking name and putting it on global scope
		functionName.visit(this, scopeTracker);

		//

		idt.openScope(); // for params
		//Checking params

		for (Map.Entry<ASTType,ASTIdentifier> entry: functionParams.entrySet()) {
			ASTType currentType = entry.getKey();
			ASTIdentifier currentId = entry.getValue();

			//TODO finish
		}
		idt.openScope(); //for locals
		//Checking statements, have an if / else ifs with types, check return etc
		for(  : )
		String fdType = fd.


	}

	Object visitExpression(ASTExpression e, Object scopeTracker) throws SemanticException{
		String returnType;

		// do things (visit & maybe decorate more)
		//Decorating the expression with its checked return type
		e.setReturnType(returnType);
		return returnType;
	}

	Object visitIdentifier(){
		//TODO decorate with type? What if its a function?

	}


	Object visitIfStatement() throws SemanticException{

	}

	Object visitLoop() throws SemanticException{

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
