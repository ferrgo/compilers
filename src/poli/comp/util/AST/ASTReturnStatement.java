package poli.comp.util.AST;


// RETURN_STMT     ::= RETURN (ID)?
public class ASTReturnStatement extends ASTStatement{

    private ASTExpression exp;

    public ASTReturnStatement(ASTExpression exp){
      this.exp = exp;
    }

}
