package poli.comp.parser;

import jdk.nashorn.internal.ir.LexicalContext;
import poli.comp.scanner.LexicalException;
import poli.comp.scanner.Scanner;
import poli.comp.scanner.Token;
import poli.comp.util.AST.*;

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
	 */ //TODO
	public AST parse() throws SyntacticException, LexicalException{
		//currentToken = Scanner.getNextToken(); Shouldnt this go in the 1st line of parseProgram?
		//ASTProgram programTree = parsePROG();
		//TODO
		parsePogram();
		accept(EOT);
		//return programTree;
	}

	public ASTProgram parseProgram(){

		List<ASTDeclaration> l_d;
		List<ASTSubprogramDeclaration> l_sd; // ( ͡◉ ͜ʖ ͡◉)
		List<ASTFunctionDeclaration> l_fd;
		ASTMainProgram mp;

		//parse declaration assignments
		while( currentToken.getKind()!=FUNCTION && currentToken.getKind()!=SUBPROGRAM){
			parseDeclaration();
		}
		//parse function and subprogram declarations
		while(currentToken.getKind()!=PROGRAM){

			if(currentToken.getKind()==SUBPROGRAM){
				parseSubprogramDeclaration();
			}else if (currentToken.getKind == FUNCTION){
				parseFunctionDeclaration();
			}

		}
		parseMainProgram();
		ASTProgram rv = new ASTProgram();
		return rv; //return an ASTPROG
	}

	public void parseMainProgram(){

	}

	// accept(TYPE);
	// accept(DOUBLECOLON);
	// parseDeclaredVariables();
}
