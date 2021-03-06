package poli.comp.compiler;

import poli.comp.generator.Encoder;
import poli.comp.checker.Checker;
import poli.comp.checker.SemanticException;
import poli.comp.parser.Parser;
import poli.comp.parser.SyntacticException;
import poli.comp.scanner.LexicalException;
import poli.comp.util.AST.AST;
import poli.comp.util.symbolsTable.IdentificationTable;

/**
 * Compiler driver
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class Compiler {

	// Compiler identification table TODO is this useless? We make a new on in the Checker so i think it is.
										//ans: I guess I can be useful, it could be the same for both Checker and Encoder...
	public static IdentificationTable identificationTable = null;

	/**
	 * Compiler start point
	 * @param args - none
	 */
	public static void main(String[] args) throws SemanticException {
		// Initializes the identification table with the reserved words
		Compiler.initIdentificationTable();

		// Creates the parser object
		Parser p = new Parser("./testFiles/Checker/Pass/program15.txt");

		// Creates the AST object
		AST astRoot = null;

		try {
			// Parses the source code
			astRoot = p.parse();
			System.out.println("\n-- AST STRUCTURE --");
			if ( astRoot != null ) {
				System.out.println(astRoot.toString(0));
			}
		} catch (SyntacticException se) {
			// Shows the syntactic/lexical error stack trace
			se.printStackTrace();
		} catch (LexicalException le) {

			le.printStackTrace();
		}

		// Creates the checker object
		Checker c = new Checker();

		// Runs a semantic analysis, returning a decorated AST
		AST decoratedAST = c.check(astRoot);

		Encoder e = new Encoder();
		String code = e.encode(decoratedAST);
		System.out.println(code);
	}



	/**
	 * Initializes the identification table with the reserved words
	 */
	private static void initIdentificationTable() {
		// Calls the initializer methods
		Compiler.identificationTable = new IdentificationTable();
	}

}
