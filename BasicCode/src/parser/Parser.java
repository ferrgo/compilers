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
		// Initializes the scanner object
		this.scanner = new Scanner();
	}

	/**
	 * Veririfes if the current token kind is the expected one
	 * @param kind
	 * @throws SyntacticException
	 */ //TODO
	private void accept(int kind) throws SyntacticException {
		// If the current token kind is equal to the expected
		if(currentToken.getNextToken()==kind){
			// Gets next token
			currentToken = scanner.getNextToken();
		}
		// If not
		else{
			// Raises an exception
		}
	}

	/**
	 * Gets next token
	 */ //TODO
	private void acceptIt() {
		currentToken = scanner.getNextToken();
	}

	/**
	 * Verifies if the source program is syntactically correct
	 * @throws SyntacticException
	 */ //TODO
	public AST parse() throws SyntacticException {
		return null;
	}

}
