package poli.comp.scanner;

import poli.comp.compiler.Properties;
//import poli.comp.compiler.Compiler;

import poli.comp.parser.GrammarSymbol;
import poli.comp.util.Arquivo;



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
	private GrammarSymbol currentKind;
	// Buffer to append characters read from file. I've changed that into StringBuilder
	// because the java documentation recommends that over StringBuffer in a single-threaded context.
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
	 * Default constructor
	 */
	public Scanner(String filepath) {
		this.file = new Arquivo(filepath);
		this.line = 0;
		this.column = 0;
		this.currentChar = this.file.readChar();
	}

	/**
	 * Returns the next token
	 * @return
	 */
	public Token getNextToken() throws LexicalException {
		// Initializes the string buffer
		// Ignores separators
		// Clears the string buffer
		// Scans the next token
		// Creates and returns a token for the lexema identified

			currentSpelling = new StringBuilder();
			//getNextChar();
			while(isSeparator(currentChar)){
				scanSeparator();
			}
			currentSpelling.setLength(0);

			int startLine = line;
			int startColumn = column;

			currentKind = scanToken();
//			getNextChar(); //TODO Pra nao ficar parado num char que ja leu ? Nao sei se é necessário
		//getNextChar here is getdting multiple parenteses together so LP comes as () if in the file is such as "asd()asd"

			Token rv = new Token(currentKind,currentSpelling.toString(),startLine,startColumn);
			currentKind = null;
			return rv;
	}

	/**
	 * Returns if a character is a separator
	 * @param c
	 * @return
	 */
	private boolean isSeparator(char c) {
		if ( c=='!' || c == ' ' || c == '\n' || c == '\t' ) {
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

		if(currentChar=='!') { // If the separator is a !, gnore the entire comment
			while (isGraphic(currentChar) || currentChar == '\t') { //Because apparently \t is somewhere else in the character table
				getNextChar();
				if (currentChar == '\n' || currentChar =='\000'){
					getNextChar(); // goes to next line
					return;
				}
			}
			//If you end up here, you-ve got a non-grapic symbol in the comment:
			throw new LexicalException("You've got some weird non-graphical character in your comment sir. Please learn how to write.", currentChar, line, column);

		}else{ //In case the separator is merely a space, a tab or a newline
			getNextChar();
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

//		currentSpelling.setLength(0); // Clearing the buffer so that we can use it below
		int automatonState=0;

		while(true){
			//NOTE Case 11 is rather unnecessary and redundant for case 10 but makes 8 and 9 more organized
			switch(automatonState){
				case(0): //Initial State
					if(currentChar=='\000'){
						automatonState=1;
					}else if(currentChar=='('){
						automatonState=2;
					}else if(currentChar==')'){
						automatonState=3;
					}else if(currentChar==','){
						automatonState=4;
					}else if(currentChar=='+'){
						automatonState=5;
					}else if(currentChar=='-'){
						automatonState=6;
					}else if(currentChar=='*'){
						automatonState=7;
					}else if(currentChar=='/'){
						automatonState=8;
					}else if(currentChar=='='){
						automatonState=9;
					}else if(currentChar=='<' || currentChar=='>'){
						automatonState=10;
					//State 11 is only accessible from states 8, 9 and 10. We didn't forget about it.
					}else if(currentChar=='.'){  // logical literal
						automatonState=12;
					}else if(currentChar==':'){
						automatonState=13;
					}else if(isDigit(currentChar)){
						automatonState=14;
					}else if(isLetter(currentChar)){
						automatonState=15;
					}else{ // Lexical Error
						automatonState=16;
					}
					getNextChar();
					break;
				case(1):// \000
					return GrammarSymbol.EOT;
				case(2):// (
					return GrammarSymbol.LP;
				case(3):// )
					return GrammarSymbol.RP;
				case(4): // ,
					return GrammarSymbol.COMMA;
				case(5): // +
					return GrammarSymbol.PLUS;
				case(6): // -
					return GrammarSymbol.MINUS;
				case(7): // *
					return GrammarSymbol.MULT;
				case(8): // /
					if(currentChar=='='){ //It might be a /=
						automatonState = 11;
					}else{
						return GrammarSymbol.DIV;
					}
					break;
				case(9): // =
					if(currentChar=='='){//It might be a ==
						automatonState = 11;
					}else{
						return GrammarSymbol.ASSIGNMENT;
					}
					break;
				case(10): // < >
					if(currentChar=='='){
						automatonState = 11;
					}else{
						return GrammarSymbol.OP_LOGICAL;
					}
					break;
				case(11): // < > <= >= == /=
					return GrammarSymbol.OP_LOGICAL;
				case(12): //.true. or .false.
					while(isLetter(currentChar)){
						getNextChar();
					}
					if(currentSpelling.toString().equals(".true") || currentSpelling.toString().equals(".false")){
						if (currentChar=='.'){
							getNextChar();
							return GrammarSymbol.LIT_LOGICAL;
						}
					}
					automatonState=16;//In case one of the above conditionals fails, we have ourselves an error here.
					break;
				case(13): // ::
					if(currentChar==':'){
						getNextChar();
						return GrammarSymbol.DOUBLECOLON;
					}else{
						automatonState=16; //Lexical Error
					}
					break;
				case(14): //Integer literal
					while(isDigit(currentChar)){
						getNextChar();
					}
					return GrammarSymbol.LIT_INTEGER;
				case(15): //ID or reserved words
					while(isLetter(currentChar) || isDigit(currentChar) || currentChar=='_'){
						getNextChar();
					}
					String alias = currentSpelling.toString();
					switch (alias){
						case ("INTEGER"):
							return GrammarSymbol.TYPE;
						case ("LOGICAL"):
							return GrammarSymbol.TYPE;
						case ("IF"):
							return GrammarSymbol.IF;
						case("THEN"):
							return GrammarSymbol.THEN;
						case("ELSE"):
							return GrammarSymbol.ELSE;
						case ("DO"):
							return GrammarSymbol.DO;
						case ("WHILE"):
							return GrammarSymbol.WHILE;
						case ("EXIT"):
							return GrammarSymbol.EXIT;
						case ("CONTINUE"):
							return GrammarSymbol.CONTINUE;
						case("PROGRAM"):
							return GrammarSymbol.PROGRAM;
						case("SUBPROGRAM"):
							return GrammarSymbol.SUBPROGRAM;
						case ("FUNCTION"):
							return GrammarSymbol.FUNCTION;
						case("RETURN"):
							return GrammarSymbol.RETURN;
						case("PRINT"):
							return GrammarSymbol.PRINT;
						case("END"):
							return GrammarSymbol.END;
						default:
							return GrammarSymbol.ID;
					}//SwitchInsideSwitch...
				case(16): //LEXICAL ERROR (╯°□°）╯︵ ┻━┻
					throw new LexicalException("Oh come on, are you seriously incapable of lexical correctness?", currentChar, line, column);
				default: // It should be impossible to get here, SO I DON'T EVEN KNOW WHAT HAPPENED (ノಠ益ಠ)ノ彡┻━┻
					throw new LexicalException("There is something weird that the scanner doesnt contemplate", currentChar, line, column);
			}//switch
		}//while loop
	}//method
}//class
