package poli.comp.util.AST;


import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

import java.util.ArrayList;

public class ASTLoopContinue extends ASTLoopControl{

	public ASTLoopContinue(){
	
	}

	@Override
	public Object visit(Visitor v, ArrayList<AST> scopeTracker) throws SemanticException {
		return v.visitASTLoopContinue(this, scopeTracker);
	}

	@Override
  	public String toString(int level) {
  			return null;
  	}
}
