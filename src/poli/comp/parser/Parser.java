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

	/**
	 * Constructor getting args from command line...
	 * @param args
     */
	public Parser(String[] args) {
		this.scanner = new Scanner(args); // Initializes the scanner object
	}

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
		ASTProgram programTree = parsePROG();
		//TODO
		//accept(EOT); ??

		return programTree;
	}

	public ASTProgram parsePROG(){
		while(currentToken.getKind()!=GrammarSymbol.EOT){
			//TODO
			break;
		}
		return null;
	}
}
