package poli.comp.util.AST;


// TERM            ::= FACTOR ((*|/) FACTOR)*
public abstract class ASTTerm extends ASTExpression{

    private List<ASTFactor> afa;

    public ASTTerm(List<ASTFactor> afa){
      this.afa = afa;
    }
}
