package poli.comp.generator;

import poli.comp.util.AST.*; //importing all AST node classes
import poli.comp.util.symbolsTable.IdentificationTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Encoder implements Visitor{


	private List<Instruction> mcData; //mc for machine code
	private List<Instruction> mcText; //mc for machine code



	private Map<String,Integer> currentVarTable;


	//Constants for the emit method
	public static final int DATA = 1;
	public static final int TEXT = 2;


	public CodeGenerator(){
		//TODO maybe encapsulate the instruction list into something and hide all the technicals below from this class
		mcData = new LinkedList<Instruction>();
		mcText = new LinkedList<Instruction>();

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

			return output; //TODO modify compiler class to spit this to a file.

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


	Object visitASTDeclarationGroup  (ASTDeclarationGroup dg  , ArrayList<AST> scopeTracker) throws SemanticException{
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



	Object visitASTArithmeticExpression  (ASTArithmeticExpression ae  , ArrayList<AST> scopeTracker) throws SemanticException{

	}
	Object visitASTAssignment            (ASTAssignment a   , ArrayList<AST> scopeTracker) throws SemanticException{

	}
	Object visitASTExpression            (ASTExpression e   , ArrayList<AST> o) throws SemanticException{

	}
	Object visitASTFactorExpression      (ASTFactorExpression      fe  , ArrayList<AST> scopeTracker) throws SemanticException{

	}
	Object visitASTFactorLiteral         (ASTFactorLiteral         fl  , ArrayList<AST> scopeTracker) throws SemanticException{

	}
	Object visitASTFactorSubroutineCall  (ASTFactorSubroutineCall  fsc , ArrayList<AST> scopeTracker) throws SemanticException{

	}
	Object visitASTFunctionArgs          (ASTFunctionArgs          fa  , ArrayList<AST> scopeTracker) throws SemanticException{

	}
	Object visitASTFunctionCall          (ASTFunctionCall          fc  , ArrayList<AST> scopeTracker) throws SemanticException{
		//get list of params
		//push them all accordingly, from vartable
		//emit call
		//remove params (i.e. call add on sp and 4*amt of params)
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

		int amtParams = fd.getParams().size();

		emit("_"+fd.getIdentifier().getSpelling()+":",TEXT);
		emit("push ebp",TEXT);
		emit("move ebp, esp",TEXT);

		//We dont have to encode local variables here, they can be encoded on-demand as we find declarations,
		//because if we do this right, at the end of every command ST will point to the last LV to be declared.
		//What we need to do here is to declare some counter that counts the amount of LVs already declared,
		//so that we can use the right offset when we find a new one.
		initializeVarTable();
		scopeTracker.add(srd);
		for( ASTStatement stt : fd.getStatements() ){
			stt.visit(this,scopeTracker);
		}
		scopeTracker.remove(srd);
		cleanVarTable();

		emit("move esp, ebp",TEXT);
		emit("pop ebp",TEXT);
		emit("ret",TEXT);
	}

	Object visitASTIdentifier            (ASTIdentifier            id  , ArrayList<AST> scopeTracker) throws SemanticException{

	}
	Object visitASTIfStatement           (ASTIfStatement           s   , ArrayList<AST> scopeTracker) throws SemanticException{

	}
	Object visitASTLoop                  (ASTLoop                  l   , ArrayList<AST> scopeTracker) throws SemanticException{

	}
	Object visitASTMainProgram           (ASTMainProgram           mp  , ArrayList<AST> scopeTracker) throws SemanticException{

	}
	Object visitASTOperatorComp          (ASTOperatorComp          opc , ArrayList<AST> scopeTracker) throws SemanticException{

	}
	Object visitASTReturnStatement       (ASTReturnStatement       rs  , ArrayList<AST> scopeTracker) throws SemanticException{

	}
	Object visitASTTerm                  (ASTTerm                  t   , ArrayList<AST> scopeTracker) throws SemanticException{

	}

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
	Object visitASTLoopContinue          (ASTLoopContinue astLoopContinue, ArrayList<AST> scopeTracker) throws SemanticException{

	}
	Object visitASTLoopExit              (ASTLoopExit astLoopExit, ArrayList<AST> scopeTracker) throws SemanticException{

	}
	Object visitASTOperator              (ASTOperator astOperator, ArrayList<AST> scopeTracker) throws SemanticException{

	}

	//We wont use the methods below on the encoder, but we need them to implement visitor
	Object visitASTType                  (ASTType astType, ArrayList<AST> scopeTracker) throws SemanticException{
		return null;
	}
	Object visitASTSingleDeclaration     (ASTSingleDeclaration astSingleDeclaration, ArrayList<AST> scopeTracker) throws SemanticException{
		return null;
	}



}
