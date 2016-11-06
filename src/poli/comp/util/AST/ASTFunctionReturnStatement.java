package poli.comp.util.AST;


// RETURN_STMT     ::= RETURN (ID)?
public class ASTFunctionReturnStatement extends ASTStatement{

    private ASTExpression exp;

    public ASTFunctionReturnStatement(ASTExpression exp){
	 	super();
		this.exp = exp;
    }

	 public ASTExpression getExpression(){
		 return this.exp;
	 }
}
