package poli.comp.parser;

//import jdk.nashorn.internal.ir.LexicalContext;
import poli.comp.scanner.LexicalException;
import poli.comp.scanner.Scanner;
import poli.comp.scanner.Token;
import poli.comp.util.AST.*;

import java.util.List;
import java.util.ArrayList;

import static poli.comp.parser.GrammarSymbol.*;

/**
 * Parser class
 * @version 2010-august-29
 * @discipline Projeto de Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class Parser {

	// The current token
	private Token currentToken = null;
	// The scanner
	private Scanner scanner = null;

	/**
	 * Parser constructor
	 */
	public Parser() {
		this.scanner = new Scanner(); // Initializes the scanner object
	}

	//O acceptIt deve ser usado quando ja sabemos q o token eh o esperado
	//o accept é quando esperamos que os proximos tokens sejam de certo tipo,
	// e caso nao sejam é pq deu merda. por exemplo, se o ultimo token foi a
	// palavra PROGRAM, vamos precisar de um accept(ID)

	/**
	 * Veririfes if the current token kind is the expected one
	 * @param expectedKind
	 * @throws SyntacticException
	 * @throws LexicalException
	 */
	private void accept(GrammarSymbol expectedKind) throws SyntacticException, LexicalException {
		try {
			currentToken = scanner.getNextToken();
			if (currentToken.getKind() == expectedKind) {
				acceptIt();    // Gets next token and returns
			} else {
				throw new SyntacticException("Current token's kind is not the expected kind", currentToken);
			}
		} catch (LexicalException l){
			throw l;
		}
	}

	/**
	 * Gets next token
	 */
	private void acceptIt() throws LexicalException {
		currentToken = scanner.getNextToken();
	}

	/**
	 * Verifies if the source program is syntactically correct
	 * @throws SyntacticException
	 */
	public AST parse() throws SyntacticException, LexicalException{
		currentToken = scanner.getNextToken();
		ASTProgram programTree = parseProgram();
		accept(EOT);
		return programTree;
	}

	//TODO check if all the uses of the GrammarSymbol enum below are correct.

	//Parses the rule PROG ::= (DECLARATION)* (FUNCTION_DECL | SUBPROGRAM_DECL)*  PROG_MAIN EOT
	public ASTProgram parseProgram() throws SyntacticException, LexicalException {

		List<ASTSubprogramDeclaration> l_sd = new ArrayList<ASTSubprogramDeclaration>(); // ( ͡◉ ͜ʖ ͡◉) ~trippy
		List<ASTFunctionDeclaration> l_fd = new ArrayList<ASTFunctionDeclaration>();
		List<ASTDeclaration> l_d = new ArrayList<ASTDeclaration>();
		ASTMainProgram mp = null;

		//parsing global declarations
		while( currentToken.getKind()!=FUNCTION && currentToken.getKind()!=SUBPROGRAM){
			l_d.add(parseDeclaration());
		}
		//parsing function and subprogram declarations
		while(currentToken.getKind()!=PROGRAM){

			//parsing subprogram(procedure) declarations
			if(currentToken.getKind()==SUBPROGRAM){
				l_sd.add(parseSubprogramDeclaration());
			}
			//parsing function declarations
			//"if" we don't get into the first if and also not get into the else
			// then we got ourselves a mindblowing overflow
//			else if (currentToken.getKind() == FUNCTION){
				l_fd.add(parseFunctionDeclaration());
//			}

		}
		//parsing the core of the program (kind of a "main" method)
		mp = parseMainProgram();

		ASTProgram rv = new ASTProgram(l_d,l_sd,l_fd,mp);
		return rv; //return an ASTPROG
	}

	private ASTDeclaration parseDeclaration() {
		return null;
	}

	/**
	 * Following the idea of a Soubroutine superclass and single method
	 * @return
     */
	private ASTSubroutineDeclaration parseSubroutineDeclaration() throws SyntacticException, LexicalException {
		Boolean isFunction = false;
		ASTType t;
		ASTIdentifier subroutineName;

		List<ASTParamDeclaration>  l_par = new ArrayList<ASTParamDeclaration>(); //TODO seriam declaracoes mesmo?
		List<ASTStatement>    l_s    = new ArrayList<ASTStatement>();

		ASTSubroutineDeclaration rv;

		if(currentToken.getKind()==FUNCTION){
			isFunction=true;
		}

		//Parsing name etc
		if(isFunction){
			accept(FUNCTION);

			if(currentToken.getKind()==INTEGER){
				acceptIt();
				t = new ASTType(currentToken.getSpelling());
			}else{
				accept(LOGICAL);
				t = new ASTType(currentToken.getSpelling());
			}
		}else{
			accept(SUBPROGRAM);
		}


		subroutineName = new ASTIdentifier(currentToken.getSpelling());
		accept(ID); //TODO sempre dar accept no terminal dps q ler ele pra ast
		accept(LP);

		//Parsing args
		ASTParamDeclaration decl;
		ASTType declType;
		ASTIdentifier declId;
		//TODO add ast declarations and fix the flag thing
		while(currentToken.getKind()!=RP){ //I think we cant simply call parseDeclaration() cause it would allow for ='s
			//If inside the LP RP we must have the structur TYPE :: ID,....
			accept(TYPE);
			declType = new ASTType(currentToken.getSpelling());
			accept(DOUBLECOLON);
			accept(ID);
			declId = new ASTIdentifier(currentToken.getSpelling());
			decl = new ASTParamDeclaration(declType, declId);

			//We got ourselves another decl...
			l_par.add(decl);

			if(currentToken.getKind()!=COMMA) break; //Case there is no more commas
			else {
				acceptIt();
				if(currentToken.getKind()!=TYPE) break; //Gambi if we don't have a type after Comma we have a problem
				else accept(TYPE); //So we force the lexical error accepting TYPE...
			}
		}
		accept(RP);

		//Parsing Statements
		while(currentToken.getKind()!=END){
			l_s.add(parseStatement());
		}

		accept(END);

		if (isFunction){
			accept(FUNCTION);
			rv = new ASTFunctionDeclaration(t, subroutineName, l_par, l_s);
		}else{
			accept(SUBPROGRAM);
			rv = new ASTSubprogramDeclaration(t, subroutineName, l_par, l_s);
		}
		return rv;
	}


	//Parses the rule PROG_MAIN ::= PROGRAM ID (STATEMENT)* END PROGRAM
	public ASTMainProgram parseMainProgram() throws SyntacticException, LexicalException {

		ASTIdentifier id;
		List<ASTStatement> l_s = new ArrayList<ASTStatement>();

		//Parses PROGRAM ID
		accept(PROGRAM);
		id= new ASTIdentifier(currentToken.getSpelling());
		accept(ID);


		//Parses each statement
		while(currentToken.getKind() != END){ // will this work?
			l_s.add(parseStatement());
		}

		//Parses END PROGRAM
		accept(END);
		accept(PROGRAM);

		ASTMainProgram rv = new ASTMainProgram(id,l_s);
		return rv;
	}


	//TODO Here I am...
	public ASTStatement parseStatement() throws SyntacticException, LexicalException {
		ASTStatement rv;

		//Parsing variable declarations
		if(currentToken.getKind()==TYPE){
			rv = parseStatement();
		}
		//Parsing assignments and function calls
		else if(currentToken.getKind()==ID){


			ASTIdentifier id = new ASTIdentifier(currentToken.getSpelling());
			accept(ID);


			if(currentToken.getKind()==EQUALS){
				acceptIt();
				ASTExpression exp = parseExpression();
				return new ASTAssignment(id,exp);
			}else{
				ASTFunctionArgs fa = parseFunctionArgs();
				return new ASTFunctionCall(id,fa);
			}

		}
		//Parsing loop control statements
		else if(currentToken.getKind()==EXIT ||currentToken.getKind()==CONTINUE){
			rv = parseLoopControlStatement();
		}
		//Parsing loops
		else if(currentToken.getKind()==DO){
			rv = parseLoop();
		}
		//Parsing if statements
		else if(currentToken.getKind()==IF){
			rv = parseIfStatement();
		}
		//Parsing return statements
		else if(currentToken.getKind()==RETURN){
			rv = parseReturnStatement();
		}
		else if(currentToken.getKind()==PRINT){
			rv = parsePrintStatement();
		}

		return rv;

	}
	public ASTFunctionArgs parseFunctionArgs(){
		//TODO

	}

	public ASTAssignment parseAssignment(String varName){

		ASTExpression exp;

		accept(EQUALS);

		exp = parseExpression();

		ASTAssignment rv = new ASTAssignment(varName, exp);
		return rv;

	}

	public ASTDeclarationGroup parseDeclarationGroup(){

		ASTType t;
		Map<ASTIdentifier,ASTExpression> declarations = new HashMap<ASTIdentifier,ASTExpression>(); // if var is not initialized, expression will be null

		t = parseType();
		accept(DOUBLECOLON);
		//For every declaration
		while(currentToken.getNextToken()==ID){
			ASTIdentifier currentId = null;
			ASTExpression currentExpression = null;

			//Parse the Identifier
			currentId = new ASTIdentifier(currentToken.getSpelling());
			accept(ID);
			//Parse the assignment, if thats the case
			if(currentToken.getNextToken()==EQUALS){
				acceptIt();
				currentExpression = parseExpression();
			}
			declarations.put(currentId,currentExpression);

		};

		ASTDeclarationGroup rv = new ASTDeclaration(t,declarations);
		return rv;

	}

	public parseExpression(){ // EXPRESSION ::= EXP' (OP_COMP EXP')?

		//TODO


	}

	public ASTLoop parseLoop(){

		ASTBooleanExpression be; //TODO criar expression bool na gramatica pra usar aqui?
		List<ASTStatement> l_s = new ArrayList<ASTStatement>();
		accept(DO);
		accept(WHILE);
		accept(LP);
		be = parseBooleanExpression();
		accept(RP);
		while(currentToken!=END){ //the inner ends will be accepted by parseStatement, so this should be END DO
			l_s.add(parseStatement());
		}
		accept(END);
		accept(DO);

		ASTLoop rv = new ASTLoop(be,l_s);
		return rv;
	}

	public ASTLoopControl parseLoopControl(){ //TODO the ASTLoopControl class should be abstract
															//TODO also do the other 2 that inherit it
		if(currentToken.getKind()==EXIT){
			acceptIt();
			return new ASTLoopExit();
		}else if (currentToken.getKind()==CONTINUE){
			acceptIt();
			return new ASTLoopContinue();
		}

	}

	public ASTFunctionCall parseFunctionCall(String functionName){

	}

	public ASTReturnStatement parseReturnStatement(){ //TODO remember to create the 2 return classes ^_^

		accept(RETURN);
		if(currentToken.getKind()==EXPRESSION){
			ASTExpression exp = parseExpression();
			return new ASTReturnFromFunction(exp);
		}else{
			return new ASTReturnFromSubprogram();
		}

	}

	public ASTPrintStatement parsePrintStatement(){

		ASTExpression exp;

		accept(PRINT);
		accept(MULT);
		accept(COMMA);
		exp = parseExpression();
		return new ASTPrintStatement(exp);

	}

	//TODO micro-optimization: Maybe make a common method for parsing functions and sbps
	// since the 2 methods are so similar. We can make an abstract ASTSubroutineDeclaration
	// class and input a boolean to pick the return type in the common method.
	public ASTFunctionDeclaration parseFunctionDeclaration(){
		ASTType t;
		ASTIdentifier functionName;
		List<ASTDeclarationr> l_args = new ArrayList<ASTDeclaration>(); //TODO seriam declaracoes mesmo?
		List<ASTStatement>    l_s;   = new ArrayList<ASTStatement>();

		//Parsing name etc
		accept(FUNCTION);
		t = parseType();
		functionName = new ASTIdentifier(currentToken.getSpelling());
		accept(ID);
		accept(LP);

		//Parsing args
		boolean comma_flag;
		while(currentToken.getKind() != RP){ //I think we cant simply call parseDeclaration() cause it would allow for ='s

			if(comma_flag) accept(COMMA); //TODO do that for declarations too

			ASTType temp_type = parseType();

			accept(DOUBLECOLON);

			ASTIdentifier temp_id = ASTIdentifier(currentToken.getSpelling());

			l_args.add(new ASTDeclaration(temp_type,temp_id));

			comma_flag=true;
		}
		accept(RP);

		//Parsing Statements
		while(currentToken.getKind()!=END){
			l_s.add(parseStatement());
		}
		accept(END);
		accept(FUNCTION);
		rv = new ASTFunctionDeclaration(t, functionName, l_args, l_s);
		return rv;
	}

	/**
	 * Changed name to SubprogramDeclaration but didn't change the function itself
	 * @return
     */
	public ASTSubprogramDeclaration parseSubprogramDeclaration(){

		ASTIdentifier sbpName;
		List<ASTDeclarationr> l_args = new ArrayList<ASTDeclaration>(); //TODO seriam declaracoes mesmo?
		List<ASTStatement>    l_s;   = new ArrayList<ASTStatement>();

		//Parsing name etc
		accept(SUBPROGRAM);
		functionName = new ASTIdentifier(currentToken.getSpelling());
		accept(ID);
		accept(LP);

		//Parsing args
		boolean comma_flag;
		while(currentToken.getKind() != RP){ //I think we cant simply call parseDeclaration() cause it would allow for ='s

			if(comma_flag) accept(COMMA); //TODO do that for declarations too

			ASTType temp_type = parseType();

			accept(DOUBLECOLON);

			ASTIdentifier temp_id = new ASTIdentifier(currentToken.getSpelling());

			l_args.add(new ASTDeclaration(temp_type,temp_id));

			comma_flag=true;
		}
		accept(RP);

		//Parsing Statements
		while(currentToken.getKind()!=END){
			l_s.add(parseStatement());
		}

		//Parsing end
		accept(END);
		accept(SUBPROGRAM);

		rv = new ASTSubprogramDeclaration(sbpName, l_args, l_s);
		return rv;
	}



	public parseIfStatement(){
		ASTExpression exp;
		List<ASTStatement> l_if = new ArrayList<ASTStatement>();
		List<ASTStatement> l_else = new ArrayList<ASTStatement>();

		accept(IF);
		exp = parseExpression();
		accept(THEN);
		while(currentToken.getKind()!=END && currentToken.getKind()!= ELSE){
			l_if.add(parseStatement());
		}
		if(currentToken.getKind()==END){
			acceptIt();
			accept(IF);
			return new ASTSimpleIfStatement(exp,l_if);
		}else if(currentToken.getKind()==ELSE){
			acceptIt();
			while(currentToken.getKind()!=END){
				l_else.add(parseStatement());
			}
			accept(END);
			accept(IF);
			return new ASTIfStatementWithElse(exp,l_if,l_else);
		}
	}

	//TODO:
	//Parse terminals
	//Parse expression/term/etc

}
