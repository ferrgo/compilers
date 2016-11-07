package poli.comp.util.AST;


import poli.comp.checker.Visitor;

public class ASTFunctionCall extends ASTStatement{

	private final ASTFunctionArgs fa;
	private final ASTIdentifier id;

	public ASTFunctionCall(ASTIdentifier id, ASTFunctionArgs fa) {
		this.id = id;
		this.fa = fa;
	}

	//TODO
	@Override
	public Object visit(Visitor v, Object o) {
		return null;
	}

	@Override
  	public String toString(int level) {
  			return null;
  	}
}
