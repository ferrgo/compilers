package poli.comp.util.AST;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public abstract class ASTIfStatement extends ASTStatement {

	private ASTExpression exp;

	private List<ASTStatement> l_s;

	public ASTIfStatement(ASTExpression e, List<ASTStatement> l){
		this.exp=e;
		this.l_s=l;
	}


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
