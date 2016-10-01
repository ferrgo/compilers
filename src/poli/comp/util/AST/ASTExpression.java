package poli.comp.util.AST;



// EXPRESSION      ::= EXP_ARIT (OP_COMP EXP_ARIT)?
public abstract class ASTExpression extends AST{

    private List<ASTArithmeticExpression> ae;

    public ASTExpression(List<ASTArithmeticExpression> ae) {
        this.ae = ae;
    }

  	@Override
  	public String toString(int level) {
  			return null;
  	}
}
