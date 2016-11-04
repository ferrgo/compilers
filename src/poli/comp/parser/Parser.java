package poli.comp.parser;

//import jdk.nashorn.internal.ir.LexicalContext;
import poli.comp.scanner.LexicalException;
import poli.comp.scanner.Scanner;
import poli.comp.scanner.Token;
import poli.comp.util.AST.*;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

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


	/**
	 * Parser constructor
	 */
	public Parser(String filepath) {
		this.scanner = new Scanner(filepath); // Initializes the scanner object
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
			if (currentToken.getKind() == expectedKind) {
				acceptIt();    // Gets next token and returns
			} else {
				throw new SyntacticException("Current token's kind is not the expected kind", currentToken, expectedKind);
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
	//Seem to be...

	//Parses the rule PROG ::= (DECLARATION_GROUP)* (FUNCTION_DECL | SUBPROGRAM_DECL)*  PROG_MAIN EOT
	private ASTProgram parseProgram() throws SyntacticException, LexicalException {

		List<ASTSubprogramDeclaration> l_sd = new ArrayList<ASTSubprogramDeclaration>(); // ( ͡◉ ͜ʖ ͡◉) ~trippy
		List<ASTFunctionDeclaration> l_fd = new ArrayList<ASTFunctionDeclaration>();
		List<ASTDeclarationGroup> l_d = new ArrayList<ASTDeclarationGroup>();
		ASTMainProgram mp = null;

		//parsing global declarations
		while( currentToken.getKind()==TYPE){
			l_d.add(parseDeclarationGroup());
		}

		//parsing subprogram(procedure) declarations
		while(currentToken.getKind()==SUBPROGRAM || currentToken.getKind()==FUNCTION){
			//parsing subprogram(procedure) declarations
			if(currentToken.getKind()==SUBPROGRAM){ //Inside here the current token is either SUBPROGRAM or FUNCTION
				l_sd.add((ASTSubprogramDeclaration) parseSubroutineDeclaration(false));
			} else { // If not SUBPROGRAM
				//parsing function declarations
				l_fd.add((ASTFunctionDeclaration) parseSubroutineDeclaration(true));
			}
		}
		//After Subprogram and Function should be a Main Program
		//parsing the core of the program (kind of a "main" method)
		mp = parseMainProgram();

		ASTProgram rv = new ASTProgram(l_d,l_sd,l_fd,mp);
		return rv; //return an ASTPROG
	}


	//Parses the rule PROG_MAIN ::= PROGRAM ID (STATEMENT)* END PROGRAM
	private ASTMainProgram parseMainProgram() throws SyntacticException, LexicalException {

		ASTIdentifier id;
		List<ASTStatement> l_s = new ArrayList<>();

		//Parses PROGRAM ID
		accept(PROGRAM);
		id= new ASTIdentifier(currentToken.getSpelling());
		accept(ID);

		//Parses each statement
		while(currentToken.getKind() != END ){
			// will this work?
			// >> Some question in life aren't meant to be answered...
			l_s.add(parseStatement());
		}
		//Parses END PROGRAM
		accept(END);
		//if if gets here while was broken with END...
//		acceptIt();
		accept(PROGRAM);

		ASTMainProgram rv = new ASTMainProgram(id,l_s);
		return rv;
	}

	//STATEMENT ::= ( DECLARATION_GROUP | ID ( = EXPRESSION|FUNCTION_ARGS) | IF_STATEMENT | LOOP | EXIT | CONTINUE | RETURN_STMT | PRINT_STMT )
	private ASTStatement parseStatement() throws SyntacticException, LexicalException {
		ASTStatement rv = null;

		//Parsing variable declarations
		if(currentToken.getKind()==TYPE){
			rv = parseDeclarationGroup();
		}
		//Parsing assignments and function calls
		else if(currentToken.getKind()==ID){
			ASTIdentifier id = new ASTIdentifier(currentToken.getSpelling());
			acceptIt();
			if(currentToken.getKind()==ASSIGNMENT){
				acceptIt();
				ASTExpression exp = parseExpression();
				return new ASTAssignment(id,exp);
			}else{
				ASTFunctionArgs fa = parseFunctionArgs();
				return new ASTFunctionCall(id,fa);
			}

		}

		//Parsing loop control statements
		else if(currentToken.getKind()==EXIT){
			rv = new ASTLoopExit();
			acceptIt();
		}else if (currentToken.getKind()==CONTINUE){
			rv = new ASTLoopContinue();
			acceptIt();
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

		else {
			rv = parsePrintStatement();
		}

		return rv;

	}


	private ASTIfStatement parseIfStatement() throws SyntacticException, LexicalException {
		ASTExpression exp;
		List<ASTStatement> l_if = new ArrayList<ASTStatement>();
		List<ASTStatement> l_else = new ArrayList<ASTStatement>();

		accept(IF);
		accept(LP);
		exp = parseExpression();
		accept(RP);
		accept(THEN);
		while(currentToken.getKind()!=END && currentToken.getKind()!= ELSE){
			l_if.add(parseStatement());
		}
		if(currentToken.getKind()==END){
			acceptIt();
			accept(IF);
			return new ASTIfStatement(exp,l_if, null);
		}else {
			accept(ELSE);
			while(currentToken.getKind()!=END){
				l_else.add(parseStatement());
			}
			accept(END);
			accept(IF);
			return new ASTIfStatement(exp,l_if,l_else);
		}
	}


	private ASTLoop parseLoop() throws SyntacticException, LexicalException {

		ASTExpression be;
		//TODO criar expression bool na gramatica pra usar aqui?
		//Don't think so... Expression is already booleable... Semantically we verify if is that so for the case...
		List<ASTStatement> l_s = new ArrayList<ASTStatement>();
		accept(DO);
		accept(WHILE);
		accept(LP);
		be = parseExpression();
		accept(RP);
		while(currentToken.getKind()!=END){ //the inner ends will be accepted by parseStatement, so this should be END DO
			l_s.add(parseStatement());
		}
		accept(END);
		accept(DO);

		ASTLoop rv = new ASTLoop(be,l_s);
		return rv;
	}


	private ASTDeclarationGroup parseDeclarationGroup() throws SyntacticException, LexicalException {
		//Objects that will go into the AST:
		ASTType t;
		Map<ASTIdentifier,ASTExpression> declarations = new HashMap<ASTIdentifier,ASTExpression>(); // if var is not initialized, expression will be null

		//Auxiliary objects for looping over the declarations:
		ASTIdentifier currentId = null;
		ASTExpression currentExpression = null;

		t = new ASTType(currentToken.getSpelling());
		acceptIt(); //We only get here in this method if the current token is TYPE, so theres no point in checking again

		accept(DOUBLECOLON);

		//For the first (i.e. the obligatory) declaration:
		currentId = null;
		currentExpression = null;
		//Parse the Identifier
		currentId = new ASTIdentifier(currentToken.getSpelling());
		acceptIt();
		//Parse the assignment, if thats the case
		if(currentToken.getKind()==ASSIGNMENT){
			acceptIt();
			currentExpression = parseExpression();
		}
		declarations.put(currentId,currentExpression);

		//For every following (optional) declaration
		while(currentToken.getKind()==COMMA){
			currentId = null;
			currentExpression = null;

			//Parse the comma
			accept(COMMA);
			//Parse the Identifier
			currentId = new ASTIdentifier(currentToken.getSpelling());
			acceptIt();
			//Parse the assignment, if thats the case
			if(currentToken.getKind()==ASSIGNMENT){
				acceptIt();
				currentExpression = parseExpression();
			}
			declarations.put(currentId,currentExpression);
		}

		ASTDeclarationGroup rv = new ASTDeclarationGroup(t,declarations);
		return rv;

	}

	private ASTExpression parseExpression() throws LexicalException, SyntacticException {
		// EXPRESSION ::= EXP_ARIT (OP_COMP EXP_ARIT)?
		ASTArithmeticExpression ae1 = null;
		ASTArithmeticExpression ae2 = null;
		ASTOperatorComp op = null;
		ae1 = parseArithmeticExpression();
		if(currentToken.getKind()==OP_LOGICAL) {
			op = new ASTOperatorComp(currentToken.getSpelling());
			acceptIt();
			ae2 = parseArithmeticExpression();
		}
		return new ASTExpression(ae1, op, ae2);
	}

	private ASTArithmeticExpression parseArithmeticExpression() throws LexicalException, SyntacticException {
		ASTTerm term1;
		ASTTerm aux = null;
		ASTOperator op = null;
		Map<ASTOperator,ASTTerm> l_ot = new HashMap<ASTOperator,ASTTerm>();

		term1 = parseTerm();
		while(currentToken.getKind()==PLUS || currentToken.getKind()==MINUS){
			op = new ASTOperator(currentToken.getSpelling());
			acceptIt();
			aux = parseTerm();
			l_ot.put(op, aux);
		}

		return new ASTArithmeticExpression(term1, l_ot);
	}

	private ASTTerm parseTerm() throws LexicalException, SyntacticException {
		ASTFactor factor1;
		ASTFactor aux = null;
		ASTOperator op = null;
		Map<ASTOperator,ASTFactor> l_of = new HashMap<ASTOperator,ASTFactor>();

		factor1 = parseFactor();
		while(currentToken.getKind()==MULT || currentToken.getKind()==DIV){
			op = new ASTOperator(currentToken.getSpelling());
			acceptIt();
			aux = parseFactor();
			l_of.put(op, aux);
		}

		return new ASTTerm(factor1, l_of);
	}


	private ASTFactor parseFactor() throws LexicalException, SyntacticException {
		if(currentToken.getKind()==ID){
			ASTIdentifier id = new ASTIdentifier(currentToken.getSpelling());
			acceptIt();
			ASTFunctionArgs l_args = null;
			if(currentToken.getKind()==LP){
				l_args = parseFunctionArgs();
			}
			return new ASTFactorSubroutineCall (id, l_args);
		} else if (currentToken.getKind()==LIT_INTEGER || currentToken.getKind()==LIT_LOGICAL){
			ASTLiteral l = new ASTLiteral(currentToken.getSpelling());
			acceptIt();
			return new ASTFactorLiteral (l);
		} else {
			accept(LP);
			ASTExpression exp = parseExpression();
			accept(RP);
			return new ASTFactorExpression(exp);
		}
	}

	//	FUNCTION_ARGS   ::= LP (EXPRESSION(,EXPRESSION)*)? RP
	private ASTFunctionArgs parseFunctionArgs() throws SyntacticException, LexicalException {
		List<ASTExpression> l_e = new ArrayList<ASTExpression>();
		accept(LP);
		if(currentToken.getKind()!= RP){//Changed to if
			//With while getting errors at new iteration currentToken.getKind()==COMMA in this case
			l_e.add(parseExpression());
			while(currentToken.getKind()==COMMA) {//Changed to while
				//iterations will happen here until there is no more commas, so no more need to check for another exp
				//GOES TO accept RP...
				acceptIt();
				l_e.add(parseExpression());
			}
		}
		accept(RP);
		return new ASTFunctionArgs(l_e);
	}

	private ASTReturnStatement parseReturnStatement() throws SyntacticException, LexicalException {
		accept(RETURN);
		ASTReturnStatement rv = new ASTReturnStatement(parseExpression());
		return rv;
	}

	private ASTParamDeclaration parseParamDeclaration() throws LexicalException, SyntacticException {
		ASTType declType;
		ASTIdentifier declId;

		declType = new ASTType(currentToken.getSpelling());
		accept(TYPE);
		accept(DOUBLECOLON);
		declId = new ASTIdentifier(currentToken.getSpelling());
		accept(ID);
		return new ASTParamDeclaration(declType, declId);

	}

	/**
	 * Following the idea of a Soubroutine superclass and single method
	 * @return
     */
	private ASTSubroutineDeclaration parseSubroutineDeclaration(boolean isFunction) throws SyntacticException, LexicalException {

		ASTType t;
		ASTIdentifier subroutineName;
		Map<ASTType,ASTIdentifier> map_params;

		ArrayList<ASTStatement> l_s   = new ArrayList<ASTStatement>();

		ASTSubroutineDeclaration rv;


		//Parsing name etc
		if(isFunction){
			accept(FUNCTION);
			t = new ASTType(currentToken.getSpelling());
			accept(TYPE);
		}else{
			accept(SUBPROGRAM);
		}


		subroutineName = new ASTIdentifier(currentToken.getSpelling());
		accept(ID); //TODO sempre dar accept no terminal dps q ler ele pra ast
		accept(LP);

		//If we have params...
		//TODO fix this to use a flag on the comma thingy and a single loop.
		if(currentToken.getKind()==TYPE){
			map_params = new HashMap<ASTType,ASTIdentifier>();

			ASTType currentParamType = new ASTType(currentToken.getSpelling());
			accept(TYPE);
			accept(DOUBLECOLON);
			ASTIdentifier currentParamIdentifier = new ASTIdentifier(currentToken.getSpelling());
			accept(ID);

			map_params.put(currentParamType,currentParamIdentifier);

			//If we have more than 1 param...
			while(currentToken.getKind()==COMMA){
				accept(COMMA);
				currentParamType = new ASTType(currentToken.getSpelling());
				accept(TYPE);
				accept(DOUBLECOLON);
				currentParamIdentifier = new ASTIdentifier(currentToken.getSpelling());
				accept(ID);
				map_params.put(currentParamType,currentParamIdentifier);
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
			rv = new ASTFunctionDeclaration(t, subroutineName, map_params, l_s);
		}else{
			accept(SUBPROGRAM);
			rv = new ASTSubprogramDeclaration( subroutineName, map_params, l_s);
		}
		return rv;
	}


	private ASTPrintStatement parsePrintStatement() throws SyntacticException, LexicalException {
		ASTExpression exp;

		accept(PRINT);
		accept(MULT);
		accept(COMMA);

		exp = parseExpression();
		return new ASTPrintStatement(exp);

	}


}
