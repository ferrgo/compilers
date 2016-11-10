package poli.comp.util.AST;

import poli.comp.checker.SemanticException;
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

	private ASTExpression condition;

	private List<ASTStatement> ifBlockStatements;

	private List<ASTStatement> elseBlockStatements;

	public ASTIfStatement(ASTExpression c, List<ASTStatement> ibs, List<ASTStatement> ebs){
		super();
		this.condition=c;
		this.ifBlockStatements=ibs;
		this.elseBlockStatements=ebs;
	}


	public List<ASTStatement> getElseBlockStatements(){
		return this.elseBlockStatements;
	}

	public List<ASTStatement> getIfBlockStatements(){
		return this.ifBlockStatements;
	}

	public ASTExpression getCondition(){
		return this.condition;
	}

	@Override
	public Object visit(Visitor v, Object o) throws SemanticException {
		return v.visitASTIfStatement(this, o);
	}
}
