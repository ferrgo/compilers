package poli.comp.util.AST;


import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

public class ASTLoopExit extends ASTLoopControl{

	public ASTLoopExit(){
	
	}

	@Override
	public Object visit(Visitor v, Object o) throws SemanticException {
		return v.visitASTLoopExit(this, o);
	}

	@Override
  	public String toString(int level) {
  			return null;
  	}
}
