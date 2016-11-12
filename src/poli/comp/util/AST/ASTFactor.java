package poli.comp.util.AST;


import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

// FACTOR          ::= ID(FUNCTION_ARGS)? | LITERAL | LP EXPRESSION RP
public abstract class ASTFactor extends AST{

	//TODO
	@Override
	public Object visit(Visitor v, Object o) throws SemanticException {
		return null;
	}

	@Override
  	public String toString(int level) {
  			return null;
  	}
}
