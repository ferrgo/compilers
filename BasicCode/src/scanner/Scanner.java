package scanner;

import compiler.Properties;
import compiler.Compiler;

import parser.GrammarSymbols;
import util.Arquivo;

/**
 * Scanner class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class Scanner {

	// The file object that will be used to read the source code
	private Arquivo file;
	// The last char read from the source code
	private char currentChar;
	// The kind of the current token
	private int currentKind;
	// Buffer to append characters read from file. I've changed that into StringBuilder
	// because the java documentation recommends that over StringBuffer for a single-threaded context.
	private StringBuilder currentSpelling;
	// Current line and column in the source file
	private int line, column;

	/**
	 * Default constructor
	 */
	public Scanner() {
		this.file = new Arquivo(Properties.sourceCodeLocation);
		this.line = 0;
		this.column = 0;
		this.currentChar = this.file.readChar();
	}

	/**
	 * Returns the next token
	 * @return
	 */ //TODO
	public Token getNextToken() {

			// Initializes the string buffer
			// Ignores separators
			// Clears the string buffer
			// Scans the next token
		 // Creates and returns a token for the lexema identified
		return null;
	}

	/**
	 * Returns if a character is a separator
	 * @param c
	 * @return
	 */
	private boolean isSeparator(char c) {
		if ( c == '!' || c == ' ' || c == '\n' || c == '\t' ) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Reads (and ignores) a separator
	 * @throws LexicalException
	 */
	private void scanSeparator() throws LexicalException {

		if(isSeparator(currentChar)){
			// Do nothing, I guess ¯\_(ツ)_/¯
		}	else if(currentChar=='!'){
			getNextChar();
			while(isGraphic(currentChar) || currentChar=='\t'){ //Because apparently \t is somewhere else in the character table //TODO put eot here as well?
				getNextChar();
				if(currentChar=='\n') return; // Also not sure if \n is in the spectrum, so better safe than sorry  ¯\_(ツ)_/¯
			}
			//If you get here, it's because you got a non-graphical character in the comment.
			throw LexicalException("You've got some weird character in your comment sir. Please learn how to write.");
		}else{
			throw LexicalException("This is not even a separator sir, why the fornication did your compiler end up here? Please learn how to code.");
		}

	}

	/**
	 * Gets the next char
	 */
	private void getNextChar() {
		// Appends the current char to the string buffer
		this.currentSpelling.append(this.currentChar);
		// Reads the next one
		this.currentChar = this.file.readChar();
		// Increments the line and column
		this.incrementLineColumn();
	}

	/**
	 * Increments line and column
	 */
	private void incrementLineColumn() {
		// If the char read is a '\n', increments the line variable and assigns 0 to the column
		if ( this.currentChar == '\n' ) {
			this.line++;
			this.column = 0;
		// If the char read is not a '\n'
		} else {
			// If it is a '\t', increments the column by 4
			if ( this.currentChar == '\t' ) {
				this.column = this.column + 4;
			// If it is not a '\t', increments the column by 1
			} else {
				this.column++;
			}
		}
	}

	/**
	 * Returns if a char is a digit (between 0 and 9)
	 * @param c
	 * @return
	 */
	private boolean isDigit(char c) {
		if ( c >= '0' && c <= '9' ) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns if a char is a letter (between a and z or between A and Z)
	 * @param c
	 * @return
	 */
	private boolean isLetter(char c) {
		if ( (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') ) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns if a char is a graphic (any ASCII visible character)
	 * @param c
	 * @return
	 */
	private boolean isGraphic(char c) {
		if ( c >= ' ' && c <= '~' ) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Scans the next token
	 * Simulates the DFA that recognizes the language described by the lexical grammar
	 * @return
	 * @throws LexicalException
	 */
	private GrammarSymbol scanToken() throws LexicalException {
		//TODO do i have to make a gnc here in case i dont do it before returns?
		int automatonState=0;
		currentSpelling.setLength(0); // Clearing the buffer so that we can use it below
		//TODO clean the buffer
		while(true){
			//TODO we could remove cases 1-6 and insert return statements instead of the corresponding state changes.
			//NOTE Case 11 is rather unnecessary and redundant for case 10 but makes 8 and 9 more organized
			switch(automatonState){
				case(0): //Initial State
					if(currentChar=='\000'){
						automatonState=1;
						getNextCharacter();
					}else if(currentChar=='('){
						automatonState=2;
						getNextCharacter();
					}else if(currentChar==')'){
						automatonState=3;
						getNextCharacter();
					}else if(currentChar==','){
						automatonState=4;
						getNextCharacter();
					}else if(currentChar=='+'){
						automatonState=5;
						getNextCharacter();
					}else if(currentChar=='-'){
						automatonState=6;
						getNextCharacter();
					}else if(currentChar=='*'){
						automatonState=7;
						getNextCharacter();
					}else if(currentChar=='/'){
						automatonState=8;
						getNextCharacter();
					}else if(currentChar=='='){
						automatonState=9;
						getNextCharacter();
					}else if(currentChar=='<' || currentChar=='>'){
						automatonState=10;
						getNextCharacter();
					//State 11 is only accessible from states 8, 9 and 10. We didn't forget about it.
					}else if(currentChar=='.'){
						automatonState=12;
						getNextCharacter();
					}else if(currentChar==':'){
						automatonState=13;
						getNextCharacter();
					}else if(isDigit(currentChar)){
						automatonState=14;
						getNextCharacter();
					}else if(isLetter(currentChar)){
						automatonState=15;
						getNextCharacter();
					}else{ // Lexical Error
						automatonState=16;
						getNextCharacter();
					}
					//TODO put gnc here rather than in the else ifs
					break;
				case(1):// \000
					return EOT;
				case(2):// (
					return LP;
				case(3):// )
					return RP;
				case(4): // ,
					return COMMA;
				case(5): // +
					return PLUS;
				case(6): // -
					return MINUS;
				case(7): // *
					return MULT;
				case(8): // /
					if(currentChar=='='){ //It might be a /=
						automatonState = 11;
					}else{
						return DIV;
					}
					break;
				case(9): // =
					if(currentChar=='='){//It might be a ==
						automatonState = 11;
					}else{
						return ASSIGNMENT;
					}
					break;
				case(10): // < >
					if(currentChar=='='){
						automatonState = 11;
					}else{
						return OP_LOGICAL;
					}
					break;
				case(11): // < > <= >= == /=
					return OP_LOGICAL;
				case(12): //.true. or .false.
					while(isLetter(currentChar)){
						getNextCharacter();
					}
					if(currentSpelling.toString().equals(".true") || currentSpelling.toString().equals(".false")){
						if (currentChar=='.'){
							return LIT_LOGICAL;
						}
					}
					//In case one of the above conditionals fails, we have ourselves an error here D:
					automatonState=16;
					break;
				case(13): // ::
					if(currentChar==':'){
						return DOUBLECOLON;
					}else{
						automatonState=16; //Lexical Error
						break;
					}
					
				case(14):
					while(isDigit(currentChar)){
						getNextCharacter();
					}
					return LIT_INTEGER;
				case(15):
					while(isLetter(currentChar) || isDigit(currentChar) || currentChar=='_'){
						getNextCharacter();
					}
					String alias = currentSpelling.toString();
					if(alias.equals("INTEGER") || alias.equals("LOGICAL")){
						return TYPE;
					}else if(alias.equals("IF")){
						return IF;
					}else if(alias.equals("THEN")){
						return THEN;
					}else if(alias.equals("ELSE")){
						return ELSE;
					}else if(alias.equals("DO")){
						return DO;
					}else if(alias.equals("WHILE")){
						return WHILE;
					}else if(alias.equals("EXIT")){
						return EXIT;
					}else if(alias.equals("CONTINUE")){
						return CONTINUE;
					}else if(alias.equals("PROGRAM")){
						return PROGRAM;
					}else if(alias.equals("FUNCTION")){
						return FUNCTION;
					}else if(alias.equals("RETURN")){
						return RETURN;
					}else if(alias.equals("PRINT")){
						return PRINT;
					}else{
						return ID;
					}
				case(16):
					throw new LexicalException("Oh come on, are you seriously incapable of lexical correctness?");
				default:
					throw new LexicalException("There is something weird that the scanner doesnt contemplate");
			}//switch
		}//while loop
	}//method

}//class
