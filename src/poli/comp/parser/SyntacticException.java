package poli.comp.parser;

import poli.comp.scanner.Token;

/**
 * Synticatic Exception
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class SyntacticException extends Exception {

	private static final long serialVersionUID = 3457448332803077642L;

	// Not expected token
	private Token token;
	// Expected token
	private GrammarSymbol expected;
	
	/**
	 * Default constructor
	 * @param message
	 * @param token
	 * @param expected
	 */
	public SyntacticException(String message, Token token, GrammarSymbol expected) {
		super(message);
		this.token = token;
		this.expected = expected;
	}
	
	/**
	 * Creates the error report
	 */
	public String toString() {
		String errorMessage =
			"----------------------------- SYNTACTIC ERROR REPORT - BEGIN -----------------------------\n" +
			">> Message: " + super.getMessage() + "\n" +
			">> Token: " + this.token.getSpelling() + "\n" +
			"   at line: " + (this.token.getLine()+1) + "\n" +
			"   at column: " + (this.token.getColumn()+1) + "\n" +
			">> One would expect to see something like: " + this.expected + "\n" +
			"------------------------------ SYNTACTIC ERROR REPORT - END ------------------------------\n";
			
		return errorMessage;
	}
	
}
