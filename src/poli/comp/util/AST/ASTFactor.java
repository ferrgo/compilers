package poli.comp.util.AST;


import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

import java.util.ArrayList;

// FACTOR          ::= ID(FUNCTION_ARGS)? | LITERAL | LP EXPRESSION RP
public abstract class ASTFactor extends AST{

	//TODO
	@Override
	public Object visit(Visitor v, ArrayList<AST> scopeTracker) throws SemanticException {
		return null;
	}

	@Override
  	public String toString(int level) {
  			return null;
  	}
}
