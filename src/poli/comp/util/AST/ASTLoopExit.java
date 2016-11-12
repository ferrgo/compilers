package poli.comp.util.AST;


import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

import java.util.ArrayList;

public class ASTLoopExit extends ASTLoopControl{

	public ASTLoopExit(){
	
	}

	@Override
	public Object visit(Visitor v, ArrayList<AST> scopeTracker) throws SemanticException {
		return v.visitASTLoopExit(this, scopeTracker);
	}

	@Override
  	public String toString(int level) {
  			return null;
  	}
}
