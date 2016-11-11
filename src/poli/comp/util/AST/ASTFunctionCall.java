package poli.comp.util.AST;


import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

public class ASTFunctionCall extends ASTStatement{

	private final ASTFunctionArgs fa;
	private final ASTIdentifier id;

	public ASTFunctionCall(ASTIdentifier id, ASTFunctionArgs fa) {
		this.id = id;
		this.fa = fa;
	}

	public ASTIdentifier getFunctionId(){
		return this.id;
	}
	public ASTFunctionArgs getFunctionArgs(){
			return this.fa;
	}


	@Override
	public Object visit(Visitor v, Object o) throws SemanticException {
		return v.visitASTFunctionCall(this, o);
	}

	@Override
  	public String toString(int level) {
  			return null;
  	}
}
