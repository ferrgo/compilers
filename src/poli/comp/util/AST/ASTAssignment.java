package poli.comp.util.AST;

import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

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

	@Override
	public Object visit(Visitor v, Object o) throws SemanticException {
		return v.visitASTAssignment(this, o);
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
