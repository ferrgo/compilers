package parser;

import scanner.Scanner;
import scanner.Token;
import util.AST.AST;

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
	 * Veririfes if the current token kind is the expected one
	 * @param kind
	 * @throws SyntacticException
	 */
	private void accept(GrammarSymbol expectedKind) throws SyntacticException {
		if(currentToken.getNextToken()==expectedKind){
			acceptIt();	// Gets next token and returns
		}else{
			throw new SyntacticException("Current token's kind is not the expected kind");
		}
	}

	/**
	 * Gets next token
	 */
	private void acceptIt() {
		currentToken = scanner.getNextToken();
	}

	/**
	 * Verifies if the source program is syntactically correct
	 * @throws SyntacticException
	 */ //TODO
	public AST parse() throws SyntacticException {
		//currentToken = Scanner.getNextToken(); Shouldnt this go in the 1st line of parseProgram?
		ASTProgram programTree = parsePROG();
		//TODO
		//accept(EOT); ??

		return programTree;
	}

	public ASTProgram parseProgram(){
		while(currentToken.getKind()!=EOT){
			accept()
		}
	}
}
