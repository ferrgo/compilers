package poli.comp.generator;

import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;
import poli.comp.util.AST.*; //importing all AST node classes

import java.util.*;


public class Encoder implements Visitor {


	private Code code;
	private List<Instruction> mcData; //mc for machine code
	private List<Instruction> mcText; //mc for machine code

	private List<String> globalVarList; //List with the names of the declared global variables.
	private Map<String,Integer> localVarTable; //Map local variable names to their offset (times 4)
	private Map<String,Integer> localParamTable; //Map local param names to their offset (times 4) (plus 4 because of PC)
	private Map<ASTLoop,Integer> loopMap; //Maps loop nodes to an index that we can use on visitASTContinue/Break
	private int loopCounter; //Counting loops to avoid repeated label names
	private int ifCounter;   //Counting if blocks to avoid repeated label names
	private boolean globalVarDec; //Its me GlobalVar
											//(a wild globalvar appears)

	private int expEvalCounter;

	//Constants for the emit method
	public static final int EXTERN = 1;
	public static final int DATA = 2;
	public static final int TEXT = 3;


	public Encoder(){
//		mcData = new LinkedList<>();
//		mcText = new LinkedList<>();
		globalVarList = new LinkedList<>();
		expEvalCounter=0;
		loopCounter=0;
		ifCounter=0;

		List<String> globalVarList   = new ArrayList<String>();
		Map<ASTLoop,Integer> loopMap = new HashMap<ASTLoop,Integer>();


		code = new Code();

		emit(EXTERN, "_printf");     //In order to allow printing, like in the example ASM file
//		emit(DATA, "SECTION .data");       //For globals
//		emit("SECTION .text");       //For code
		emit("global _WinMain@16"); //Here to help windows do its thing, like in the example ASM file
	}

	// Helpers for the local vartables. Silly, I know, but its more readable ¯\_(ツ)_/¯
	private void initializeVarTable(){
		localVarTable= new HashMap<String,Integer>();
	}
	private void cleanVarTable(){
		localVarTable= null;
	}
	private void addLocalVar(String name){
		localVarTable.put(name, new Integer(4*localVarTable.size())  );
	}
	private void initializeParamTable(){
		localParamTable= new HashMap<String,Integer>();
	}
	private void cleanParamTable(){
		localParamTable= null;
	}
	private void addLocalParam(String name){
		//Plus 4 because of the program counter on the stack.
		localParamTable.put(name, new Integer( 4+(4*localParamTable.size())  ) );
	}

	//Emit method that appends instructions to the code in the correct places.
	private void emit(int section, String s){

		Instruction i = new Instruction(s, section);
		code.add(i);
//		if(i.getSection()==DATA){
//			mcData.add(i);
//		}else if (i.getSection()==TEXT){
//			mcText.add(i);
//		}else{
//			throw new RuntimeException("Dude, do you even know what youre doing?"
//					+"\nYeah, I mean you, the person who is writing this compiler"
//					+"\n(who has a 33.3% chance of being me, so who am I to judge?" +
//					"\nBut I'm still judging you regardless).");
//		}

	}

	public void emit (String s){
		emit(TEXT, s);
	}

	public String encode(AST a){
		ArrayList<AST> scopeTracker = new ArrayList<AST>();
		String asm = "";//Should be a file mayb
		try{
			a.visit(this,scopeTracker);
//			String output="";
//			for(Instruction i : this.mcData){
//				output+=i.getContents();
//			}
//			for(Instruction i : this.mcText){
//				output+=i.getContents();
//			}
//
//			return output;
			asm = code.generate();//TODO modify code class to spit this to a file.
			return asm;
		}catch(SemanticException se){
			throw new RuntimeException("We shouldn't even be here. Conceptually, the Code generator" +
					"\nshouldn't be throwing semantic errors, but" +
					"\nthe visitor interface forces us to have all of these" +
					"\n'throws SemanticException' things. So if you're seeing this," +
					"\n(and you're not reading the compiler's source code) something" +
					"\nis very, VERY WRONG. Good luck.");
		}

	}


	//	Object visitASTOperatorArit          (ASTOperatorArit              opa  ,Object o) throws SemanticException;

	public Object visitASTProgram(ASTProgram p, ArrayList<AST> scopeTracker) throws SemanticException {

		//Encodes globals
		for (ASTDeclarationGroup dg : p.getGlobalDeclarationGroups()){
			globalVarDec = true;
			dg.visit(this, scopeTracker);
			globalVarDec = false;
		}

		//Encodes function declarations
		for (ASTFunctionDeclaration fd: p.getFunctionDeclarations()){
			fd.visit(this, scopeTracker);
		}

		//Encodes subprogram declarations
		for(ASTSubprogramDeclaration sd : p.getSubprogramDeclarations()){
			sd.visit(this, scopeTracker);
		}

		//Encodes main program
		return p.getMainProgram().visit(this,scopeTracker);

	}


	public Object visitASTDeclarationGroup(ASTDeclarationGroup dg, ArrayList<AST> scopeTracker) throws SemanticException{
		if (globalVarDec){ //If we can only have asg to literals for globals...
			for (ASTSingleDeclaration dec : dg.getDeclarations()){
				if( dg.getAssignmentMap().get(dec) == null){
					emit(DATA, dec.getIdentifier().getSpelling() + ": dd 0");
					globalVarList.add(dec.getIdentifier().getSpelling());
				}else {
					ASTFactorLiteral fl = (ASTFactorLiteral) dg.getAssignmentMap().get(dec).getExp1().getTerm().getFactor();
					ASTLiteral lit = (ASTLiteral) fl.getLiteral();
					if (lit.getTypeString().equals("LOGICAL")) {
						//If its bool:
						String value = (lit.getSpelling().equals(".true.")) ? "1" : "0";
						emit(DATA, dec.getIdentifier().getSpelling() + ": dd " + value);
						System.out.print(dec.getIdentifier().getSpelling());
						globalVarList.add(dec.getIdentifier().getSpelling());
					} else {
						//If its int:
						emit(DATA, dec.getIdentifier().getSpelling() + ": dd " + lit.getSpelling());
						globalVarList.add(dec.getIdentifier().getSpelling());
					}
				}
			}
		}else{
			//in this case, these are local vars.
			for (ASTSingleDeclaration dec : dg.getDeclarations()){
				String currentName = dec.getIdentifier().getSpelling();
				ASTExpression currentExpression = dg.getAssignmentMap().get(dec); //exp can be null, so we have to check for that later.
				addLocalVar(currentName);
				if(currentExpression!=null){
					currentExpression.visit(this,scopeTracker); //visitExpression will push the final result, so we just have to pop here.
					emit("pop [ebp-"+localVarTable.get(currentName).toString()+"]");
				}else{
					emit("push 0");
					emit("pop [ebp-"+localVarTable.get(currentName).toString()+"]");
				}
			}
		}
		return null;
	}


	public Object visitASTAssignment(ASTAssignment a, ArrayList<AST> scopeTracker) throws SemanticException{
		ASTExpression exp = a.getExpression();
		String varName=a.getTarget().getSpelling();

		exp.visit(this,scopeTracker); //puts value on stack top

		if (localVarTable.containsKey(varName)){
			emit("pop [ebp-"+localVarTable.get(varName).toString()+"]");
		}else if (localParamTable.containsKey(varName)){
			emit("pop [ebp+"+localParamTable.get(varName).toString()+"]");
		}else{
			//We have ourselves a global. How exquisite.
			emit("pop ["+varName+"]");
		}
			return null;
	}


	//-------------- Function declaration, calling, args etc--------------------//

	public Object visitASTFunctionArgs(ASTFunctionArgs fa, ArrayList<AST> scopeTracker) throws SemanticException{
		//every visitASTExpression here ends with the result being pushed to the stack, so
		//visitASTFunctionArgs pushes all args to the stack already.
		for (ASTExpression exp : fa.getArgumentList()){
			exp.visit(this,scopeTracker);
		}
		return null;
	}

	public Object visitASTFunctionCall(ASTFunctionCall fc, ArrayList<AST> scopeTracker) throws SemanticException{
		ASTFunctionArgs args = fc.getFunctionArgs();
		args.visit(this,scopeTracker); //They will all be pushed in the stack here
		int amtParams;

		emit("call _"+fc.getFunctionId().getSpelling());
		amtParams = args.getArgumentList().size();
		emit("add esp, " + amtParams*4 );

		//If we're being used in an exp or assigned to something:
		if(scopeTracker.get(scopeTracker.size()-1) instanceof ASTFactor){
			emit("push eax"); //so that we can pop it on the caller
		}
		return null;
		//find out from idt if thats a subprogram or function, if its a function then return the contents of eax here?
	}

	//The method for encoding function and subprogram declarations is the same, so lets use a helper shall we?
	public Object visitASTFunctionDeclaration(ASTFunctionDeclaration fd, ArrayList<AST> scopeTracker) throws SemanticException{
		subroutineDeclarationHelper(fd,scopeTracker);
		return null;
	}

	public Object visitASTSubprogramDeclaration(ASTSubprogramDeclaration sd, ArrayList<AST> scopeTracker) throws SemanticException{
		subroutineDeclarationHelper(sd,scopeTracker);
		return null;
	}

	private Object subroutineDeclarationHelper(ASTSubroutineDeclaration srd, ArrayList<AST> scopeTracker) throws SemanticException {

		initializeVarTable();
		initializeParamTable();

		List<ASTSingleDeclaration> args = srd.getParams();

		for(ASTSingleDeclaration sd: args){
			addLocalParam(sd.getIdentifier().getSpelling());
		}

		emit("_"+srd.getIdentifier().getSpelling()+":");
		emit("push ebp");
		emit("move ebp, esp");

		//We dont have to encode local variables here, they can be encoded on-demand as we find declarations,
		//because if we do this right, at the end of every command ST will point to the last LV to be declared.
		//What we need to do here is to declare some counter that counts the amount of LVs already declared,
		//so that we can use the right offset when we find a new one.
		for( ASTStatement stt : srd.getStatements() ){
			stt.visit(this,scopeTracker);
		}

		cleanVarTable();
		cleanParamTable();
		emit("move esp, ebp");
		emit("pop ebp");
		if (srd instanceof ASTSubprogramDeclaration) {
			emit("ret");
		}
		return null;
	}

	public Object visitASTMainProgram(ASTMainProgram mp, ArrayList<AST> scopeTracker) throws SemanticException{
		initializeVarTable();

//		emit("\n_"+mp.getIdentifier().getSpelling()+":\n");
		emit("_" + "WinMain@16" + ":");//If we set global _WinMain@16 then I guess so has to be this one
		emit("push ebp");
		emit("move ebp, esp");

		//We dont have to encode local variables here, they can be encoded on-demand as we find declarations,
		//because if we do this right, at the end of every command ST will point to the last LV to be declared.
		//What we need to do here is to declare some counter that counts the amount of LVs already declared,
		//so that we can use the right offset when we find a new one.
		for( ASTStatement stt : mp.getStatements() ){
			stt.visit(this,scopeTracker);
		}

		cleanVarTable();
		emit("move esp, ebp");
		emit("pop ebp");
		emit("ret");
		return null;
	}

	public Object visitASTReturnStatement(ASTReturnStatement rs, ArrayList<AST> scopeTracker) throws SemanticException{
		ASTFunctionReturnStatement frs;
		ASTExpression exp;
		if (rs instanceof ASTFunctionReturnStatement){
			frs = (ASTFunctionReturnStatement) rs;
			exp = frs.getExpression();
			exp.visit(this,scopeTracker);

			emit("pop eax");
		}
		emit("mov esp, ebp");
		emit("pop ebp");
		emit("ret");
		return null;
	}

	//--------------------------//



	//------- Control flow ---------------//

	public Object visitASTIfStatement(ASTIfStatement s, ArrayList<AST> scopeTracker) throws SemanticException{
		this.ifCounter++;
		int currentIfStatementIndex = this.ifCounter;

		s.getCondition().visit(this,scopeTracker); // pushes condition (true or false)

		emit("push dword 1");
		emit("pop ebx");
		emit("pop eax");
		emit("cmp eax,ebx");

		//If theres only an if block
		if(s.getElseBlockStatements()==null){
		emit("jne _endif"+Integer.toString(currentIfStatementIndex));
		for(ASTStatement current_stt : s.getIfBlockStatements()){
			current_stt.visit(this,scopeTracker);
		}
		emit("_endif"+Integer.toString(currentIfStatementIndex)+":");
		//If theres an else block
		}else{
			emit("jne _elseBlock"+Integer.toString(currentIfStatementIndex));
			for(ASTStatement current_stt : s.getIfBlockStatements()){
				current_stt.visit(this,scopeTracker);
			}
			emit("jmp _endif"+Integer.toString(currentIfStatementIndex));//jmp out of if else
			emit("_elseBlock"+Integer.toString(currentIfStatementIndex)+":");
			for(ASTStatement current_stt : s.getIfBlockStatements()){
				current_stt.visit(this,scopeTracker);
			}
			emit("_endif"+Integer.toString(currentIfStatementIndex)+":");
		}
		return null;
	}

	public Object visitASTLoop(ASTLoop l, ArrayList<AST> scopeTracker) throws SemanticException{

		this.loopCounter++;
		int currentLoopIndex = this.loopCounter;
		loopMap.put(l, loopCounter);

		String condLabel = "\n_while_condition"+Integer.toString(currentLoopIndex)+":\n";
		String codeLabel = "\n_while_body"+Integer.toString(currentLoopIndex)+":\n";
		String endLabel  = "\n_while_end"+Integer.toString(currentLoopIndex)+":\n";

		// Here we visit the condition (which, assuming the checker is working,
		// is an expression that evaluates to a boolean).
		// The visitASTExpression method pushes the result to the stack.
		// We then compare it to true(1) and jmp to endwhile accordingly.
		emit(condLabel);
		ASTExpression condExp = l.getCondition();
		condExp.visit(this,scopeTracker);
		emit("push dword 1");
		emit("pop eax");
		emit("pop ebx");
		emit("cmp eax,ebx");
		emit("jne "+endLabel);

		//Now we visit every statement
		emit(codeLabel);
		List<ASTStatement> statements = l.getStatements();
		scopeTracker.add(l);
		for(ASTStatement s : statements){
			s.visit(this,scopeTracker);
		}
		scopeTracker.remove(l);
		//Now we go back to the condition. If it is false, it will jmp
		//to the endLabel and we're out of the loop.
		emit("jmp "+condLabel);
		emit(endLabel);

		return null;
	}

	public Object visitASTLoopContinue(ASTLoopContinue astLoopContinue, ArrayList<AST> scopeTracker) throws SemanticException{
		//Finding innermost loop.
		ASTLoop innermostLoop=null;
		for (AST node : scopeTracker){
			if(node instanceof ASTLoop){
				innermostLoop = (ASTLoop) node;
			}
		}

		emit("jmp _while_condition"+loopMap.get(innermostLoop).toString());
		return null;
	}

	public Object visitASTLoopExit(ASTLoopExit astLoopExit, ArrayList<AST> scopeTracker) throws SemanticException{
		//Finding innermost loop.
		ASTLoop innermostLoop=null;
		for (AST node : scopeTracker){
			if(node instanceof ASTLoop){
				innermostLoop = (ASTLoop) node;
			}
		}

		emit("jmp while_end"+loopMap.get(innermostLoop).toString());
		return null;
	}

	//-------------------------------------//


	//------------Expressions and Terminals -------------------//

	//Whenever we get to a literal as we evaluate some expression, we simply push it to the
	public Object visitASTLiteral(ASTLiteral l, ArrayList<AST> scopeTracker) throws SemanticException{
		if(l.getTypeString().equals("LOGICAL")){
			//If its bool:
			String value = (l.getSpelling().equals(".true."))?"1":"0";
			emit("push dword "+value);
		}else{
			//If its int:
			emit("push dword "+l.getSpelling());
		}
		return null;
	}

	public Object visitASTIdentifier(ASTIdentifier id, ArrayList<AST> scopeTracker) throws SemanticException{

		if(localVarTable.containsKey(id.getSpelling())){
			String offset = (localVarTable.get(id.getSpelling())).toString();
			emit("push dword [ebp-"+offset+"]");
		}else if(localParamTable.containsKey(id.getSpelling())){
			String offset = (localParamTable.get(id.getSpelling())).toString();
			emit("push dword [ebp+"+offset+"]");
		}else{//Assuming its not in the current table of local vars, it should be global
			//TODO In theory, the checker should guarantee that if there isnt a local with
			// this name, there is certainly a global, so I dont know if we have to check for errors here.
			emit("push dword ["+id.getSpelling()+"]");
		}
		return null;
	}


	public Object visitASTArithmeticExpression(ASTArithmeticExpression ae, ArrayList<AST> scopeTracker) throws SemanticException{
		ASTTerm term1 = ae.getTerm();
      	Map<ASTOperatorArit,ASTTerm> opTerms = ae.getOpTerms();

		//If its only 1 operand
		if(opTerms.size()==0){
			term1.visit(this,scopeTracker);
		//If there are multiple operands
		}else{
			term1.visit(this,scopeTracker); // pushes result to stack

			//In every iteration, we have the accumulated result of the previous terms on stack,
			//then we push the current term on stack, pull both, operate on them and push the result.
			for (Map.Entry<ASTOperatorArit, ASTTerm> entry : opTerms.entrySet()) {

				entry.getValue().visit(this,scopeTracker); //pushes result to stack

				String opString = (entry.getKey().getSpelling()).equals("+")?"add":"sub";

				emit("pop eax");
				emit("pop ebx");
				emit(opString+" eax, ebx");
				emit("push eax");

			}
		}
		return null;
	}
	public Object visitASTExpression(ASTExpression e, ArrayList<AST> scopeTracker) throws SemanticException{
		//If only 1 operand
		if(e.getExp2()==null){
			e.getExp1().visit(this,scopeTracker); // pushes to stack

		//If 2 operands
		}else{

			//Visit and push e1
			e.getExp1().visit(this,scopeTracker);

			//For every possible operator, we generate the jump string that
			//
			String op = e.getOpComp().getSpelling();
			String jumpString;
			if(op.equals("==")){
				jumpString="je";
			}else if(op.equals("!=")){
				jumpString="jne";
			}else if(op.equals(">")){
				jumpString="jg";
			}else if(op.equals("<")){
				jumpString="jl";
			}else if(op.equals(">=")){
				jumpString="jge";
			}else{// <=
				jumpString="jle";
			}

			//visit and push e2
			e.getExp2().visit(this,scopeTracker);

			//emits
			this.expEvalCounter++;
			emit("pop eax");
			emit("pop ebx");
			emit("cmp eax,ebx");
			emit(jumpString +" _evalsToTrue"+Integer.toString(expEvalCounter));
			emit("push dword 0");
			emit("jmp _endExpEval"+Integer.toString(expEvalCounter));
			emit("_evalsToTrue"+Integer.toString(expEvalCounter)+":");
			emit("push dword 1");
			emit("_endExpEval"+Integer.toString(expEvalCounter)+":");
		}
		return null;
	}

	public Object visitASTFactorExpression(ASTFactorExpression fe, ArrayList<AST> scopeTracker) throws SemanticException{

		ASTExpression exp = fe.getExpression();
		exp.visit(this,scopeTracker);
		return null;

	}
	public Object visitASTFactorLiteral(ASTFactorLiteral fl, ArrayList<AST> scopeTracker) throws SemanticException{

		ASTLiteral lit = fl.getLiteral();
		lit.visit(this,scopeTracker);
		return null;

	}
	public Object visitASTFactorSubroutineCall(ASTFactorSubroutineCall fsc, ArrayList<AST> scopeTracker) throws SemanticException{
		ASTFunctionCall fc = fsc.getFC();
		fc.visit(this,scopeTracker);
		return null;
	}

	public Object visitASTTerm(ASTTerm t, ArrayList<AST> scopeTracker) throws SemanticException{
		ASTFactor factor1 = t.getFactor();
      	Map<ASTOperatorArit,ASTFactor> opFactors = t.getOpFactors();

		//If its only 1 operand
		if(opFactors.size()==0){
			factor1.visit(this,scopeTracker);
		//If there are multiple operands
		}else{
			factor1.visit(this,scopeTracker); // pushes result to stack

			//In every iteration, we have the accumulated result of the previous factors on stack,
			//then we push the current factor on stack, pull both, operate on them and push the result.
			for (Map.Entry<ASTOperatorArit, ASTFactor> entry : opFactors.entrySet()) {

				entry.getValue().visit(this,scopeTracker); //pushes result to stack

				String opString = (entry.getKey().getSpelling()).equals("*")?"imul":"div";

				emit("pop eax");
				emit("pop ebx");
				emit(opString+" eax, ebx");
				emit("push eax");

			}
		}
		return null;
	}


	public Object visitASTOperatorComp(ASTOperatorComp opc, ArrayList<AST> scopeTracker) throws SemanticException{
		return null;
	}
	public Object visitASTOperator(ASTOperator astOperator, ArrayList<AST> scopeTracker) throws SemanticException{
		return null;
	}

	//We wont use the methods below on the encoder, but we need them to implement visitor
	public Object visitASTType(ASTType astType, ArrayList<AST> scopeTracker) throws SemanticException{
		return null;
	}
	public Object visitASTSingleDeclaration(ASTSingleDeclaration astSingleDeclaration, ArrayList<AST> scopeTracker) throws SemanticException{
		return null;
	}



}
