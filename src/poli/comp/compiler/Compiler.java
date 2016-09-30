package poli.comp.compiler;

//import poli.comp.parser.Parser;
import poli.comp.parser.SyntacticException;
import poli.comp.scanner.LexicalException;
import poli.comp.util.AST.AST;
import poli.comp.util.symbolsTable.IdentificationTable;

import java.io.File;

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
		} catch (SyntacticException s) {
			// Shows the syntactic/lexical error stack trace
			s.printStackTrace();
		} catch (LexicalException l) {

			l.printStackTrace();
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
