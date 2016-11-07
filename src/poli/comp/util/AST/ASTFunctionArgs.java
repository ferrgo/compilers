package poli.comp.util.AST;


import poli.comp.checker.Visitor;

import java.util.List;

// FUNCTION_ARGS   ::= LP (EXPRESSION(,EXPRESSION)*)? RP
public class ASTFunctionArgs extends AST{

    private List<ASTExpression> aex;

    public ASTFunctionArgs(List<ASTExpression> aex){
      this.aex = aex;
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
