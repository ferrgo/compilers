package poli.comp.util.AST;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public abstract class ASTAssignment extends ASTStatement {

	private ASTIdentifier id;

	private ASTExpression exp;

	public ASTAssignment(String idSpelling, ASTExpression e){
		this.id = new ASTIdentifier(idSpelling);
		this.exp = e;
	}

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
