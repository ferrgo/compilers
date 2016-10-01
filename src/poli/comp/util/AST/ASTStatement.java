package poli.comp.util.AST;

// STATEMENT       ::= ( DECLARATION_GROUP | ID ( = EXPRESSION|FUNCTION_ARGS) | IF_STATEMENT | LOOP
// 						    | EXIT | CONTINUE | RETURN_STMT | PRINT_STMT )
public abstract class ASTStatement extends AST {

	public String getSpaces(int level) {
		StringBuffer str = new StringBuffer();
		while( level>0 ) {
			str.append(" ");
			level--;
		}
		return str.toString();
	}
	@Override
	public String toString(int level) {
			return null;
	}
}
