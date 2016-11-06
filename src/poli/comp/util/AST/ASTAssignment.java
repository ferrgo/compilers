package poli.comp.util.AST;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class ASTAssignment extends ASTStatement {

	private ASTIdentifier id;

	private ASTExpression exp;

	public ASTAssignment(ASTIdentifier id, ASTExpression e){
		this.id = id;
		this.exp = e;
	}

	public ASTIdentifier getTarget(){
		return this.id;
	}

	public ASTExpression getExpression(){
		return this.exp;
	}

}
