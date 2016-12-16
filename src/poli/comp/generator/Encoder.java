package poli.comp.generator;

import poli.comp.util.AST.*; //importing all AST node classes
import poli.comp.util.symbolsTable.IdentificationTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Encoder implements Visitor{


	private List<Instruction> mcData; //mc for machine code
	private List<Instruction> mcText; //mc for machine code

	private Map<String,String> globalVarTable;
	private Map<String,Integer> currentVarTable; //Map local variable names to their offset (times 4)
	private Map<ASTLoop,Integer> loopMap; //Maps loop nodes to an index that we can use on visitASTContinue/Break
	private int loopCounter; //Counting loops to avoid repeated label names
	private int ifCounter;   //Counting if blocks to avoid repeated label names

	private int expEvalCounter;

	//Constants for the emit method
	public static final int DATA = 1;
	public static final int TEXT = 2;


	public CodeGenerator(){
		//TODO after treating globals, just use the table over there for addressing and you
		//wont need the DATA and TEXT bullshit on emit.
		mcData = new LinkedList<Instruction>();
		mcText = new LinkedList<Instruction>();
		expEvalCounter=0;
		loopCounter=0;
		ifCounter=0;

		emit("extern  _printf" , DATA );     //In order to allow printing, like in the example ASM file
		emit("SECTION .data"   , DATA );       //For globals

		emit("SECTION .text"       , TEXT);       //For code
		emit("\nglobal _WinMain@16", TEXT) //Here to help windows do its thing, like in the example ASM file
	}

	// Helpers for the local vartables. Silly, I know, but its more readable ¯\_(ツ)_/¯
	private void initializeVarTable(){
		currentVarTable= new HashMap<String,Integer>();
	}
	private void cleanVarTable(){
		currentVarTable= null;
	}
	private void addLocalVar(String name){
		currentVarTable.put(name, new Integer(4*currentVarTable.size())  );
	}

	//Emit method that appends instructions to the code in the correct places.
	//So far ive split these things manually into data and text. We might need to split
	//things in runtime though. TODO find out if we need and if thats the case refactor below.
	private void emit(String s, int section){

		Instruction i = new Instruction(s);

		if(section==DATA){
			mcData.add(i);
		}else if (section==TEXT){
			mcText.add(i);
		}else{
			throw new RuntimeException("Dude, do you even know what youre doing?
			Yeah, I mean you, the person who is writing this compiler
			(who has a 33.3% chance of being me, so who am I to judge?
			But I'm still judging you regardless).");
		}

	}

	//TODO maybe work with a stringbuffer because performance?
	public String encode(AST a){
		ArrayList<AST> scopeTracker = new ArrayList<AST>();

		try{
			a.visit(this,scopeTracker);
			String output="";
			for(Instruction i : this.mcData){
				output+=i.getContents();
			}
			for(Instruction i : this.mcText){
				output+=i.getContents();
			}

			return output;

		}catch(SemanticException se){
			throw new RuntimeException("We shouldn't even be here. Conceptually, the Code generator
												 shouldn't be throwing semantic errors, but
												 the visitor interface forces us to have all of these
												 'throws SemanticException' things. So if you're seeing this,
												 (and you're not reading the compiler's source code) something
												 is very, VERY WRONG. Good luck.");
		}

	}


	//	Object visitASTOperatorArit          (ASTOperatorArit              opa  ,Object o) throws SemanticException;

	Object visitASTProgram (ASTProgram p , ArrayList<AST> scopeTracker) throws SemanticException{

		//Encodes globals TODO apply a fix to global checking and encoding. Force globals to be only assigned to literals from start.
		for (ASTDeclarationGroup dg : p.getGlobalDeclarationGroups()){
			dg.visit(this, scopeTracker);
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


	Object visitASTDeclarationGroup (ASTDeclarationGroup dg, ArrayList<AST> scopeTracker) throws SemanticException{
		if (scopeTracker.size()==0){
			//In this case, these are global vars.
			//TODO (we can simply treat it as local vars before calling main, i guess)
		}else{
			//in this case, these are local vars.
			for (ASTSingleDeclaration dec: dg.getDeclarations();){

				String currentName = dec.getIdentifier().getSpelling();
				ASTExpression currentExpression = dg.getAssignmentMap().get(dec); //exp can be null, so we have to check for that later.
				addLocalVar(currentName);
				if(currentExpression!=null){
					currentExpression.visit(this,scopeTracker); //visitExpression will push the final result, so we just have to pop here.
					emit("pop [ebp-"+currentVarTable.get(currentName).toString()+"]",TEXT);
				}else{
					emit("push 0",TEXT);
					emit("pop [ebp-"+currentVarTable.get(currentName).toString()+"]",TEXT);
				}
			}
		}
	}


	Object visitASTAssignment (ASTAssignment a, ArrayList<AST> scopeTracker) throws SemanticException{

		//TODO Treat for globals
			ASTExpression exp = a.getExpression();
			exp.visit(this,scopeTracker); //puts value on stack top

			String varName=a.getTarget().getSpelling();
			emit("pop [ebp-"+currentVarTable.get(varName).toString()+"]",TEXT);
	}


	//-------------- Function declaration, calling, args etc--------------------//

	Object visitASTFunctionArgs (ASTFunctionArgs fa, ArrayList<AST> scopeTracker) throws SemanticException{
		//every visitASTExpression here ends with the result being pushed to the stack, so
		//visitASTFunctionArgs pushes all args to the stack already.
		for (ASTExpression exp : fa.getArgumentList()){
			exp.visit(this,scopeTracker);
		}
	}

	Object visitASTFunctionCall (ASTFunctionCall fc, ArrayList<AST> scopeTracker) throws SemanticException{
		ASTFunctionArgs args = fc.getFunctionArgs();
		args.visit(this,scopeTracker); //They will all be pushed in the stack here

		emit("call _"+fc.getFunctionId().getSpelling(),TEXT);
		amtParams = args.getArgumentList().size();
		emit("add esp, "+Integer.toString(amtParams*4));

		//If we're being used in an exp or assigned to something:
		if(scopeTracker.get(scopeTracker.size()-1) instanceof Factor){
			emit("push eax",TEXT); //so that we can pop it on the caller
		}
		return null;
		//find out from idt if thats a subprogram or function, if its a function then return the contents of eax here?
	}

	//The method for encoding function and subprogram declarations is the same, so lets use a helper shall we?
	Object visitASTFunctionDeclaration   (ASTFunctionDeclaration   fd  , ArrayList<AST> scopeTracker) throws SemanticException{
		subroutineDeclarationHelper(fd,scopeTracker);
		return null;
	}
	Object visitASTSubprogramDeclaration (ASTSubprogramDeclaration sd  , ArrayList<AST> scopeTracker) throws SemanticException{
		subroutineDeclarationHelper(sd,scopeTracker);
		return null;
	}
	Object subroutineDeclarationHelper(ASTSubroutineDeclaration srd,  ArrayList<AST> scopeTracker){

		initializeVarTable();
		//TODO add params to vartable for acess within

		emit("\n_"+srd.getIdentifier().getSpelling()+":\n",TEXT);
		emit("push ebp",TEXT);
		emit("move ebp, esp",TEXT);

		//We dont have to encode local variables here, they can be encoded on-demand as we find declarations,
		//because if we do this right, at the end of every command ST will point to the last LV to be declared.
		//What we need to do here is to declare some counter that counts the amount of LVs already declared,
		//so that we can use the right offset when we find a new one.
		for( ASTStatement stt : srd.getStatements() ){
			stt.visit(this,scopeTracker);
		}

		cleanVarTable();
		emit("move esp, ebp",TEXT);
		emit("pop ebp",TEXT);
		if (srd instanceof ASTSubprogramDeclaration) {
			emit("ret",TEXT);
		}
		return null;
	}

	Object visitASTMainProgram           (ASTMainProgram           mp  , ArrayList<AST> scopeTracker) throws SemanticException{
		initializeVarTable();

		emit("\n_"+srd.getIdentifier().getSpelling()+":\n",TEXT);
		emit("push ebp",TEXT);
		emit("move ebp, esp",TEXT);

		//We dont have to encode local variables here, they can be encoded on-demand as we find declarations,
		//because if we do this right, at the end of every command ST will point to the last LV to be declared.
		//What we need to do here is to declare some counter that counts the amount of LVs already declared,
		//so that we can use the right offset when we find a new one.
		for( ASTStatement stt : srd.getStatements() ){
			stt.visit(this,scopeTracker);
		}

		cleanVarTable();
		emit("move esp, ebp",TEXT);
		emit("pop ebp",TEXT);
		emit("ret",TEXT);
		return null;
	}

	Object visitASTReturnStatement       (ASTReturnStatement       rs  , ArrayList<AST> scopeTracker) throws SemanticException{

		if (rs instanceof ASTFunctionReturnStatement){
			frs = (ASTFunctionReturnStatement) rs;
			exp = frs.getExpression();
			exp.visit(this,scopeTracker);

			emit("pop eax",TEXT);
		}
		emit("mov esp, ebp",TEXT);
		emit("pop ebp",TEXT);
		emit("ret",TEXT);

	}

	//--------------------------//



	//------- Control flow ---------------//

	Object visitASTIfStatement(ASTIfStatement s, ArrayList<AST> scopeTracker) throws SemanticException{
		this.ifCounter++;
		currentIfStatementIndex=this.ifCounter;
		ifMap.put(s,new Integer(ifMap.size())); //Finding ourselves a number for this AST node.

		s.getCondition().visit(this,scopeTracker); // pushes condition (true or false)
		emit("push dword 1",TEXT);
		emit("pop ebx",TEXT);
		emit("pop eax",TEXT);
		emit("cmp eax,ebx",TEXT);

		//If theres only an if block
		if(s.getElseBlockStatements()==null){
		emit("jne _endif"+Integer.toString(currentIfStatementIndex),TEXT);
		for(ASTStatement current_stt : s.getIfBlockStatements()){
			current_stt.visit(this,scopeTracker);
		}
		emit("_endif"+Integer.toString(currentIfStatementIndex)+":",TEXT);
		//If theres an else block
		}else{
			emit("jne _elseBlock"+Integer.toString(currentIfStatementIndex),TEXT);
			for(ASTStatement current_stt : s.getIfBlockStatements()){
				current_stt.visit(this,scopeTracker);
			}
			emit("jne _endif"+Integer.toString(currentIfStatementIndex),TEXT);
			emit("_elseBlock"+Integer.toString(currentIfStatementIndex)+":",TEXT);
			for(ASTStatement current_stt : s.getIfBlockStatements()){
				current_stt.visit(this,scopeTracker);
			}
			emit("_endif"+Integer.toString(currentIfStatementIndex)+":",TEXT);
		}

	}

	Object visitASTLoop  (ASTLoop    l   , ArrayList<AST> scopeTracker) throws SemanticException{

		this.loopCounter++;
		currentLoopIndex=this.loopCounter;
		loopMap.put(l,new Integer(loopCounter));

		String condLabel = "\n_while_condition"+Integer.toString(currentLoopIndex)+":\n";
		String codeLabel = "\n_while_body"+Integer.toString(currentLoopIndex)+":\n";
		String endLabel  = "\n_while_end"+Integer.toString(currentLoopIndex)+":\n";

		// Here we visit the condition (which, assuming the checker is working,
		// is an expression that evaluates to a boolean).
		// The visitASTExpression method pushes the result to the stack.
		// We then compare it to true(1) and jmp to endwhile accordingly.
		emit(condLabel,TEXT);
		ASTExpression condExp = l.getCondition();
		condExp.visit(this,scopeTracker);
		emit("push dword 1",TEXT);
		emit("pop eax",TEXT);
		emit("pop ebx",TEXT);
		emit("cmp eax,ebx",TEXT);
		emit("jne "+endLabel,TEXT);

		//Now we visit every statement
		emit(codeLabel,TEXT);
		List<ASTStatement> statements = l.getStatements();
		scopeTracker.add(l);
		for(ASTSTtatement s : statements){
			s.visit(this,scopeTracker);
		}
		scopeTracker.remove(l);
		//Now we go back to the condition. If it is false, it will jmp
		//to the endLabel and we're out of the loop.
		emit("jmp "+condLabel,TEXT);
		emit(endLabel,TEXT);

		return null;
	}

	Object visitASTLoopContinue          (ASTLoopContinue astLoopContinue, ArrayList<AST> scopeTracker) throws SemanticException{
		//Finding innermost loop. TODO find a method that does this in one line, if there such a thing in the java library
		ASTLoop innermostLoop=null;
		for (AST node : scopeTracker){
			if(node instanceof ASTLoop){
				innermostLoop = node;
			}
		}

		emit("jmp _while_condition"+loopMap.get(innermostLoop).toString(),TEXT);
	}
	Object visitASTLoopExit              (ASTLoopExit astLoopExit, ArrayList<AST> scopeTracker) throws SemanticException{
		//Finding innermost loop. TODO find a method that does this in one line, if there such a thing in the java library
		ASTLoop innermostLoop=null;
		for (AST node : scopeTracker){
			if(node instanceof ASTLoop){
				innermostLoop = node;
			}
		}

		emit("jmp while_end"+loopMap.get(innermostLoop).toString(),TEXT);
	}

	//-------------------------------------//


	//------------Expressions and Terminals -------------------//

	//Whenever we get to a literal as we evaluate some expression, we simply push it to the
	Object visitASTLiteral               (ASTLiteral l, ArrayList<AST> scopeTracker) throws SemanticException{
		if(l.getTypeString().equals("LOGICAL")){
			//If its bool:
			String value = (l.getSpelling().equals(".true."))?"1":"0";
			emit("push dword "+value,TEXT);
		}else{
			//If its int:
			emit("push dword "+l.getSpelling(),TEXT);
		}
		return null;
	}

	Object visitASTIdentifier            (ASTIdentifier            id  , ArrayList<AST> scopeTracker) throws SemanticException{
		//TODO treat globals differently?
		Integer i = currentVarTable.get(id.getSpelling());
		emit("push dword [ebp+"+i.toString()+"]",TEXT);
		return null;
	}


	Object visitASTArithmeticExpression  (ASTArithmeticExpression ae  , ArrayList<AST> scopeTracker) throws SemanticException{
		ASTTerm term1 = ae.getTerm();
      Map<ASTOperatorArit,ASTTerm> opTerms = ae.getOpTerms();

		//If its only 1 operand
		if(opTerms.size()==0){
			factor1.visit(this,scopeTracker);
		//If there are multiple operands
		}else{
			term1.visit(this,scopeTracker); // pushes result to stack

			//In every iteration, we have the accumulated result of the previous terms on stack,
			//then we push the current term on stack, pull both, operate on them and push the result.
			for (Map.Entry<ASTOperatorArit, ASTTerm> entry : map.entrySet()) {

				entry.getValue().visit(this,scopeTracker) //pushes result to stack

				String opString = (entry.getKey().getSpelling()).equals("+")?"add":"sub";

				emit("pop eax",TEXT);
				emit("pop ebx",TEXT);
				emit(opString+" eax, ebx",TEXT);
				emit("push eax",TEXT);

			}
		}
		return null;
	}
	Object visitASTExpression            (ASTExpression e   , ArrayList<AST> o) throws SemanticException{
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
			}else if(op.equals("<=")){
				jumpString="jle";
			}

			//visit and push e2
			e.getExp2().visit(this,scopeTracker);

			//emits
			this.expEvalCounter++;
			emit("pop eax",TEXT);
			emit("pop ebx",TEXT);
			emit("cmp eax,ebx",TEXT);
			emit(jumpString+" _evalsToTrue"+Integer.toString(expEvalCounter),TEXT);
			emit("push dword 0",TEXT);
			emit("jmp _endExpEval"+Integer.toString(expEvalCounter),TEXT);
			emit("_evalsToTrue"+Integer.toString(expEvalCounter)+":",TEXT);
			emit("push dword 1",TEXT);
			emit("_endExpEval"+Integer.toString(expEvalCounter)+":",TEXT);
		}
		return null;
	}

	Object visitASTFactorExpression      (ASTFactorExpression      fe  , ArrayList<AST> scopeTracker) throws SemanticException{

		ASTExpression exp = fe.getExp();
		exp.visit(this,scopeTracker);
		return null;

	}
	Object visitASTFactorLiteral         (ASTFactorLiteral         fl  , ArrayList<AST> scopeTracker) throws SemanticException{

		ASTLiteral lit = fl.getLiteral();
		lit.visit(this,scopeTracker);
		return null;

	}
	Object visitASTFactorSubroutineCall  (ASTFactorSubroutineCall  fsc , ArrayList<AST> scopeTracker) throws SemanticException{
		ASTFunctionCall fc = fsc.getFC();
		fc.visit(this,scopeTracker);
		return null;
	}

	Object visitASTTerm                  (ASTTerm                  t   , ArrayList<AST> scopeTracker) throws SemanticException{
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
			for (Map.Entry<ASTOperatorArit, ASTFactor> entry : map.entrySet()) {

				entry.getValue().visit(this,scopeTracker) //pushes result to stack

				String opString = (entry.getKey().getSpelling()).equals("*")?"imul":"div";

				emit("pop eax",TEXT);
				emit("pop ebx",TEXT);
				emit(opString+" eax, ebx",TEXT);
				emit("push eax",TEXT);

			}
		}
		return null;
	}


	Object visitASTOperatorComp          (ASTOperatorComp          opc , ArrayList<AST> scopeTracker) throws SemanticException{
		return null;
	}
	Object visitASTOperator              (ASTOperator astOperator, ArrayList<AST> scopeTracker) throws SemanticException{
		return null;
	}

	//We wont use the methods below on the encoder, but we need them to implement visitor
	Object visitASTType                  (ASTType astType, ArrayList<AST> scopeTracker) throws SemanticException{
		return null;
	}
	Object visitASTSingleDeclaration     (ASTSingleDeclaration astSingleDeclaration, ArrayList<AST> scopeTracker) throws SemanticException{
		return null;
	}



}
