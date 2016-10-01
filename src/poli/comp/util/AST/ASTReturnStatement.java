package poli.comp.util.AST;


// RETURN_STMT     ::= RETURN (ID)?
public abstract class ASTReturnStatement extends ASTStatement{

    private ASTIdentifier id;

    public ASTReturnStatement(ASTIdentifier id){
      this.id = id;
    }

}
