package poli.comp.util.AST;


import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

import java.util.ArrayList;
import java.util.Map;

// EXP_ARIT        ::= TERM ((+|-) TERM)*
public class ASTArithmeticExpression extends AST{

    private ASTTerm term;
    private Map<ASTOperatorArit,ASTTerm> l_opterms;


    public ASTArithmeticExpression(ASTTerm at, Map<ASTOperatorArit,ASTTerm> l_ot){
        this.term = at;
        this.l_opterms = l_ot;
    }

    @Override
    public Object visit(Visitor v, ArrayList<AST> scopeTracker) throws SemanticException {
        return v.visitASTArithmeticExpression(this, scopeTracker);
    }

	 public ASTTerm getTerm(){
		 return this.term;
	 }
	 public Map<ASTOperatorArit,ASTTerm> getOpTerms(){
		 return this.l_opterms;
	 }
    @Override
  	public String toString(int level) {
  			return null;
  	}
}
