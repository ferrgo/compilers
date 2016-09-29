package poli.comp.parser;

import jdk.nashorn.internal.ir.LexicalContext;
import poli.comp.scanner.LexicalException;
import poli.comp.scanner.Scanner;
import poli.comp.scanner.Token;
import poli.comp.util.AST.*;

import java.util.List;
import java.util.ArrayList;

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
		currentToken = Scanner.getNextToken();
		ASTProgram programTree = parsePogram();
		accept(EOT);
		return programTree;
		//TODO do we have to do anything else here?
	}


	//Parses the rule PROG ::= (DECLARATION)* (FUNCTION_DECL | SUBPROGRAM_DECL)*  PROG_MAIN EOT
	public ASTProgram parseProgram(){

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
			else if (currentToken.getKind() == FUNCTION){
				l_fd.add(parseFunctionDeclaration());
			}

		}
		//parsing the core of the program (kind of a "main" method)
		mp = parseMainProgram();

		ASTProgram rv = new ASTProgram(l_d,l_sd,l_fd,mp);
		return rv; //return an ASTPROG
	}


	//Parses the rule PROG_MAIN ::= PROGRAM ID (STATEMENT)* END PROGRAM
	public ASTMainProgram parseMainProgram(){

		ASTIdentifier id;
		List<ASTStatement> l_s = new ArrayList<ASTStatement>();

		//Parses PROGRAM ID
		accept(PROGRAM);
		id=parseIdentifier();

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

	public ASTStatement parseStatement(){
		ASTStatement rv;

		//Parsing variable declarations
		if(currentToken.getKind()==TYPE){
			rv = parseDeclaration();
		}
		//Parsing assignments and function calls
		else if(currentToken.getKind()==ID){

			//Saving the id, then checking what kind of statement this is and treating it properly.
			//TODO should we refactor the grammar so that
			// we dont have to do this gambiarra?
			String idText = currentToken.getSpelling();
			acceptIt();
			if(currentToken.getKind()==EQUALS){
				parseAssignment(idText);
			}else if(currentToken.getKind()==LP){
				parseFunctionCall(idText);
			}

		}
		//Parsing loop control statements
		else if(currentToken.getKind()==EXIT ||currentToken.getKind()==CONTINUE){
			parseLoopControlStatement();
		}
		//Parsing loops
		else if(currentToken.getKind()==DO){
			parseLoop();
		}
		//Parsing if statements
		else if(currentToken.getKind()==IF){
			parseIfStatement();
		}
		//Parsing return statements
		else if(currentToken.getKind()==RETURN){
			parseReturnStatement();
		}

	}

	public parseAssignment(String varName){

	}

	public parseFunctionCall(String functionName){

	}

	public ASTDeclaration parseDeclaration(){

		// accept(TYPE);
		// accept(DOUBLECOLON);
		// parseDeclaredVariables();
	}
}
