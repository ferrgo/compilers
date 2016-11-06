package poli.comp.util.AST;


import java.util.List;
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
  	public String toString(int level) {
  			return null;
  	}
}
