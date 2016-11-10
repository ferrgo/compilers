package poli.comp.checker;

import poli.comp.util.AST.*; //importing all AST node classes

import java.util.List;
import java.util.Map;


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

	public Object visitASTProgram(ASTProgram p, Object scopeTracker) throws SemanticException{

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


	Object visitASTSingleDeclaration(ASTSingleDeclaration sd, Object scopeTracker) throws SemanticException{
		//We are linking the idt key to the ASTSingleDeclaration and not the ASTIdentifier object, so that when
		//we wanna check type consistency we can have access to the ASTType object contained in the ASTSingleDeclaration object.

		//We don't need to check if the key already exists cause idt.enter does that and raises exceptions internally.

		String idSpelling = sd.getIdentifier().getSpelling();
		idt.enter(idSpelling,ASTSingleDeclaration);

	}


	Object visitDeclarationGroup(ASTDeclarationGroup dg, Object scopeTracker) throws SemanticException{
			//TODO: check for rules:

			String dgType = dg.getType().getSpelling();
			//TODO
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
		boolean returnFlag = false;

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
			if(stt instanceof ASTFunctionReturnStatement){
				returnFlag=true;
			}
		}
		if(!returnFlag){
			throw new SemanticException("Function "+functionName.getSpelling()+" has no return statement!");
		}
		idt.closeScope();
		idt.closeScope();
		scopeTracker.remove(fd);

		return fd; //Really dont know what to return here ¯\_(ツ)_/¯
	}

	//This is basically a copy of the above, without the type because its a Subprogram
	//TODO maybe merge those two and have if statements for the functionType part and for the AST classes?
	Object visitSubprogramDeclaration(ASTFunctionDeclaration spd, Object scopeTracker) throws SemanticException{

		scopeTracker.add(spd); //Adding the function block to the scope tracker

		ASTIdentifier subprogramName = spd.getIdentifier();
		List<ASTSingleDeclaration> l_params = spd.getParams();
		List<ASTStatement> subprogramStatements = spd.getStatements();
		boolean returnFlag = false;

		//Checking name and putting it on global scope
		idt.enter(subprogramName.getSpeling(),spd);

		// Increasing the scope, cause we will visit params
		idt.openScope();

		//Checking params
		for (ASTSingleDeclaration dec: subprogramParams) {
			dec.visit(this,scopeTracker);
		}

		//Opening a scope for local variables
		idt.openScope();

		//Checking statements, have an if / else ifs with types, check return etc
		for( Statement stt : subprogramStatements ){
			stt.visit(this,scopeTracker);
			if(stt instanceof ASTSubprogramReturnStatement){
				returnFlag=true;
			}
		}
		if(!returnFlag){
			throw new SemanticException("Subprogram "+subprogramName.getSpelling()+" has no return statement!");
		}

		idt.closeScope();
		idt.closeScope();
		scopeTracker.remove(spd);

		return spd; //Really dont know what to return here ¯\_(ツ)_/¯
	}

	//Checks the main body of the program
	Object visitMainProgram(ASTMainProgram mp, Object scopeTracker) throws SemanticException{
		//TODO ter como regra extra que o main pode ter return?
		scopeTracker.add(mp);
		idt.openScope(mp);
		ASTIdentifier programName = mp.getIdentifier();
		List<ASTStatement> programStatements = mp.getStatements();


		idt.enter(programName.getSpelling(),mp);
		for(ASTStatement stt : programStatements){
			stt.visit(this,scopeTracker);
		}

		idt.closeScope(mp);
		scopeTracker.remove(mp);

		return mp;
	}



	//Checks a declaration, adding it to the idt or raising an exception if there is a prvious variable with the same id


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

	Object visitLoopControl(ASTLoopControl lc, Object scopeTracker) throws SemanticException{
		//TODO check for rules:

		//Checks if break/continue statement is inside loop
		List<AST> cScopeTracker = (List<AST>) scopeTracker; //c for Casted
		for(AST node : cScopeTracker){
			if (node instanceof ASTLoop) return lc; //I wish this was a void method
		}

		//If we get here, the loop control statemet is not in a loop
		throw new SemanticException("The "+()(lc instanceof ASTLoopExit)?("EXIT"):("CONTINUE"))+" statement is not insde of a loop!");
	}

	Object visitFunctionReturnStatement(ASTReturnStatement rs, Object scopeTracker) throws SemanticException{
		ASTExpression returnValue = rs.getExpression();
		returnValue.visit(this,scopeTracker);
		String returnValueType = returnValue.getTypeString();

		List<AST> cScopeTracker = (List<AST>) scopeTracker; //c for Casted
		for(AST node : cScopeTracker){
			if(node instanceof ASTFunctionDeclaration){
				String functionType = node.getType().getSpelling();
				if(!returnValueType.equals(functionType)){
					throw new SemanticException("Returning an expression of type "+returnValueType+" in a function of type "+functionType);
				}
				return rs; //Retorne risos (huehuebrbr)
			}
		}
		throw new SemanticException("Trying to return an expression outside of a function scope!");
	}

	Object visitSubprogramReturnStatement() throws SemanticException{

		List<AST> cScopeTracker = (List<AST>) scopeTracker; //c for Casted
		for(AST node : cScopeTracker){
			if(node instanceof ASTSubprogramDeclaration){
				return rs; //Retorne risos (huehuebrbr)
			}
		}
		throw new SemanticException("Trying to return from a subprogram outside of a subprogram scope!");
	}

	Object visitPrintStatement(ASTPrintStatement pstt, Object scopeTracker) throws SemanticException{

		ASTExpression printExpression = pstt.getExpression();
		printExpression.visit(this,scopeTracker);

	}



	Object visitFunctionCall(ASTFunctionCall fc, Object scopeTracker) throws SemanticException{
		//TODO (low priority): We named this SubroutineCall but it also works for subprogarms. Perhaps we
		// should refactor this ot SubroutineCall?
		//TODO (low priority): arent they called params when we call and args on the declaration?
		// Maybe we switched the right names. This is just an "readability/maintenance" problem tho, cause the
		// compiler will work either way as long as we're consistent.

		//Checking if function was declared
		ASTIdentifier functionId = fc.getFunctionId();
		functionId.visit(this,scopeTracker);
		ASTFunctionDeclaration declaration = idt.revrieteve(functionId.getSpelling());
		if(declaration == null){
			throw new SemanticException("Trying to call function "+ functionId.getSpelling() +", but it was not declared yet!");
		}

		//Checking if number of call arguments matches the number from the declaration
		List<ASTSingleDeclaration> declarationArguments = declaration.getParams();

		ASTFunctionArgs callArguments = fc.getFunctionArgs();
		List<ASTExpression> callArgumentList = arguments.getArgumentList();

		if(callArgumentList.size()!=declarationArgumentList.size()){
			throw new SemanticException("Function "+functionId.getSpelling()+" accepts "+declarationArgumentList.size()+" arguments
												 , but you're calling it with "+callArgumentList.size()+" arguments");
		}

		//Checking the argument expressions, and cheking if they have the right return types
		for(int i=0; i<arguments.size(); i++ ){

			ASTExpression currentExp = callArgumentList.get(i));
			String currentExpType = exp.visit(this,scopeTracker);

			ASTSingleDeclaration currentDeclaration = declarationArguments.get(i);
			String currentDeclarationType = currentDeclaration.getType().getSpelling();

			if(!currentExpType.equals(currentDeclarationType)){
					throw new SemanticException("The argument " + currentDeclaration.getIdentifier().getSpelling()
														 +" of function "+ functionId.getSpelling() +" accepts values of type "
														 + currentDeclarationType+", but was given a value of type "
														 + currentExpType+" instead");
			}

		}

	}

	Object visitIfStatement(ASTIfStatement ifStt, Object scopeTracker) throws SemanticException{

		ASTExpression condition = ifStt.getCondition();
		condition.visit(this,scopeTracker);

		if(!condition.getTypeString.equals("LOGICAL")){
			throw new SemanticException("If statement condition must evaluate to a boolean value!");
		}

		List<ASTStatement> ifBlockStatements = ifStt.getIfBlockStatements();
		List<ASTStatement> elseBlockStatements = ifStt.getElseBlockStatements();

		for(ASTStatement stt : ifBlockStatements){
			stt.visit(this,scopeTracker);
		}

		for(ASTStatement stt : elseBlockStatements){
			stt.visit(this,scopeTracker);
		}

	}

	Object visitAssignment(ASTAssignment asg, Object scopeTracker) throws SemanticException{

		ASTIdentifier target = asg.getTarget();
		target.visit(this,scopeTracker);

		ASTExpression expression = asg.getExpression();
		expression.visit(this,expression)
		String expType = expression.getTypeString();

		ASTSingleDeclaration targetDeclaration = idt.retrieve(target.getSpelling());
		String targetType = targetDeclaration.getTypeString();



	}


	Object visitExpression(ASTExpression e, Object scopeTracker) throws SemanticException{
		//We dont need to verify if e.getOpComp() indeer returns an OP_COMP, because
		//the parser will only save it if that is the case.
		if (e.getOpComp()==null){
			String ts = visit(e.getExp1(),scopeTracker);
			e.setTypeString(ts);
			return ts;
		}else{
			visit(e.getExp1(),scopeTracker);
			visit(e.getExp2(),scopeTracker)
			e.setTypeString("LOGICAL");
			return "LOGICAL";
		}

	}

	Object visitArithmeticExpression(ASTArithmeticExpression e, Object scopeTracker) throws SemanticException{
		//return type, cause it can just encapsulate a (bool)
	}

	Object visitTerm(ASTTerm t, Object scopeTracker) throws SemanticException{
		//return type, cause it can just encapsulate a (bool)
	}

	Object visitFactor(ASTFactor f, Object scopeTracker) throws SemanticException{
		//return type, cause it can just encapsulate a (bool)
	}

	//TODO UNFINISHED VISITS:

	//Checks an ID that was found in the code. Doesn't add it to IDT - this is done on visitSingleDeclaration.
	Object visitIdentifier(ASTIdentifier id, Object scopeTracker) throws SemanticException{
		//TODO check rules:
		//TODO unfinished.
		if(!idt.containsKey(id.getSpelling())){
			throw new SemanticException("Trying to refer to identifier "+id.getSpelling()
			+",which is not declared within that scope");
		}

		//TODO return declaration's type so that we can check for exp consistency?
	}

}
