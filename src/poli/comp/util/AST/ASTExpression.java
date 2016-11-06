package poli.comp.util.AST;


import java.util.List;

// EXPRESSION      ::= EXP_ARIT (OP_COMP EXP_ARIT)?
public class ASTExpression extends AST{

	//Set by the parser
   private ASTArithmeticExpression ae1;
	private ASTArithmeticExpression ae2;
	private ASTOperatorComp op_comp;

	//Set by the checker
	private String typeString;


    public ASTExpression(ASTArithmeticExpression ae1, ASTOperatorComp op, ASTArithmeticExpression ae2) {
      this.ae1 = ae1;
		this.ae2 = ae2;
		this.op_comp = op;
    }

	@Override
  	public String toString(int level){
  			return null;
  	}

	public ASTArithmeticExpression getExp1(){
		return this.ae1;
	}

	public ASTArithmeticExpression getExp2(){
		return this.ae2;
	}

	public ASTOperatorComp getOpComp(){
		return this.op_comp;
	}

	public String getTypeString(){
		return this.typeString;
	}

	public void setTypeString(String ts){
		this.typeString=ts;
	}

}
