package poli.comp.util.AST;


// PRINT_STMT      ::= PRINT *, EXPRESSION
public abstract class ASTPrintStatement extends ASTStatement{

    private ASTExpression aex;

    public ASTPrintStatement(ASTExpression aex){
      this.aex = aex;
    }

}
