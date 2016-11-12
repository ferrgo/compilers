package poli.comp.util.AST;


import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

import java.util.ArrayList;
import java.util.List;

// FUNCTION_ARGS   ::= LP (EXPRESSION(,EXPRESSION)*)? RP
public class ASTFunctionArgs extends AST{

	private List<ASTExpression> aex;

	public ASTFunctionArgs(List<ASTExpression> aex){
	  this.aex = aex;
	}

  public List<ASTExpression> getArgumentList(){
	  return this.aex;
  }

    @Override
    public Object visit(Visitor v, ArrayList<AST> scopeTracker) throws SemanticException {
        return v.visitASTFunctionArgs(this, scopeTracker);
    }

    @Override
  	public String toString(int level) {
  			return null;
  	}
}
