package poli.comp.util.AST;


import java.util.List;

// EXPRESSION      ::= EXP_ARIT (OP_COMP EXP_ARIT)?
public class ASTExpression extends AST{

   private ASTArithmeticExpression ae1;
	private ASTArithmeticExpression ae2;
	private ASTOperatorComp op_comp;

    public ASTExpression(ASTArithmeticExpression ae1, ASTOperatorComp op, ASTArithmeticExpression ae2) {
      this.ae1 = ae1;
		this.ae2 = ae2;
		this.op_comp = op;
    }

	@Override
  	public String toString(int level) {
  			return null;
  	}
}
