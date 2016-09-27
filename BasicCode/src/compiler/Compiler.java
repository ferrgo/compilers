package compiler;

import parser.Parser;
import parser.SyntacticException;
import util.AST.AST;
import util.symbolsTable.IdentificationTable;

/**
 * Compiler driver
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class Compiler {
	
	// Compiler identification table
	public static IdentificationTable identificationTable = null;

	/**
	 * Compiler start point
	 * @param args - none
	 */
	public static void main(String[] args) {
		// Initializes the identification table with the reserved words 
		Compiler.initIdentificationTable();
		
		// Creates the parser object
		Parser p = new Parser();
		
		// Creates the AST object
		AST astRoot = null;
		
		try {
			// Parses the source code
			astRoot = p.parse();
			System.out.println("\n-- AST STRUCTURE --");
			if ( astRoot != null ) {
				System.out.println(astRoot.toString(0));
			}
		} catch (SyntacticException e) {
			// Shows the syntactic/lexical error stack trace 
			e.printStackTrace();
		}
	}
	
	/**
	 * Initializes the identification table with the reserved words
	 */
	private static void initIdentificationTable() {
		// Calls the initializer methods
		Compiler.identificationTable = new IdentificationTable();
	}
	
}
