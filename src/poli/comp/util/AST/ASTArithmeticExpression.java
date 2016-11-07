package poli.comp.util.AST;


import poli.comp.checker.Visitor;
import poli.comp.checker.SemanticException;

import java.util.List;
import java.util.Map;

// EXP_ARIT        ::= TERM ((+|-) TERM)*
public class ASTArithmeticExpression extends AST{

    private ASTTerm term;
    private Map<ASTOperator,ASTTerm> l_opterms;


    public ASTArithmeticExpression(ASTTerm at, Map<ASTOperator,ASTTerm> l_ot){
        this.term = at;
        this.l_opterms = l_ot;
    }

    @Override
    public Object visit(Visitor v, Object o) throws SemanticException {
        return v.visitASTArithmeticExpression(this, o);
    }

    @Override
  	public String toString(int level) {
  			return null;
  	}
}
