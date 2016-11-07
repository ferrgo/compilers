package poli.comp.util.AST;


import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

public class ASTLoopContinue extends ASTLoopControl{

	public ASTLoopContinue(){
	
	}

	@Override
	public Object visit(Visitor v, Object o) throws SemanticException {
		return v.visitASTLoopContinue(this, o);
	}

	@Override
  	public String toString(int level) {
  			return null;
  	}
}
