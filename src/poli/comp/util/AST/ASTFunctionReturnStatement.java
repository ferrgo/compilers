package poli.comp.util.AST;


// RETURN_STMT     ::= RETURN (ID)?
public class ASTFunctionReturnStatement extends ASTReturnStatement{

    private ASTExpression exp;

    public ASTFunctionReturnStatement(ASTExpression exp){
	 	super();
		this.exp = exp;
    }

	 public ASTExpression getExpression(){
		 return this.exp;
	 }
}
