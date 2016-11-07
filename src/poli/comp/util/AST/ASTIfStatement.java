package poli.comp.util.AST;

import poli.comp.checker.Visitor;

import java.util.List;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class ASTIfStatement extends ASTStatement {

	private ASTExpression exp;

	private List<ASTStatement> l_ifs;

	private List<ASTStatement> l_elses;

	public ASTIfStatement(ASTExpression exp, List<ASTStatement> i, List<ASTStatement> e){
		super();
		this.exp=exp;
		this.l_ifs=i;
		this.l_elses=e;
	}


	//TODO
	@Override
	public Object visit(Visitor v, Object o) {
		return null;
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
