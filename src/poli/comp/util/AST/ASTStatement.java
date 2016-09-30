package poli.comp.util.AST;


public abstract class ASTStatement extends AST {


  private ASTDeclarationWithAssignment l_dwa;
	private ASTAssignment ass;
	private ASTIfStatement ist;

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
