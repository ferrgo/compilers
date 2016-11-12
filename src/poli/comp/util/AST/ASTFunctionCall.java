package poli.comp.util.AST;


import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

import java.util.ArrayList;

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
	public Object visit(Visitor v, ArrayList<AST> scopeTracker) throws SemanticException {
		return v.visitASTFunctionCall(this, scopeTracker);
	}

	@Override
  	public String toString(int level) {
  			return null;
  	}
}
