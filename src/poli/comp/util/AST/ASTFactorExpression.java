package poli.comp.util.AST;

/**
 * Created by hgferr on 01/10/16.
 */
public class ASTFactorExpression extends ASTFactor {
    private final ASTExpression exp;

    public ASTFactorExpression(ASTExpression exp) {
        this.exp=exp;
    }
}
