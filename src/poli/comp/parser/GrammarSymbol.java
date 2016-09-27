package poli.comp.parser;

/**
 * This class contains codes for each grammar terminal
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */

public enum GrammarSymbol {
	/*
	We changed the implementation to an enum rather than a class full of constants because,
	well, that's what enums are here for ^_^

	Note:
	The print statement in fortran has a non-arithmethic use for the symbol '*'.
	We cannot, therefore, include it in some "OP_ARIT" terminal. For OCD reasons
	I'm separating each arithmetic operation as a different terminal too.
	*/

	//Identifiers, variables, values etc
	ID,
	LIT_INTEGER,
	LIT_LOGICAL,
	TYPE,

	//Operators
	PLUS,
	MINUS,
	MULT,
	DIV,
	OP_LOGICAL,

	//"Punctuation"
	LP,
	RP,
	COMMA,
	DOUBLECOLON,
	ASSIGNMENT,

	//Conditionals
	IF,
	THEN,
	ELSE,

	//Loops
	DO,
	WHILE,
	EXIT,
	CONTINUE,

	//Subroutines etc
	PROGRAM,
	SUBPROGRAM,
	FUNCTION,
	RETURN,

	//Printing
	PRINT,

	//End of text
	EOT

}
