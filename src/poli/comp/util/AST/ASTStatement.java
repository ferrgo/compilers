package poli.comp.util.AST;


public abstract class ASTStatement extends AST {

	public String getSpaces(int level) {
		StringBuffer str = new StringBuffer();
		while( level>0 ) {
			str.append(" ");
			level--;
		}
		return str.toString();
	}

	public abstract String toString(int level);

}
