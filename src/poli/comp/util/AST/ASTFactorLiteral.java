package poli.comp.util.AST;

/**
 * Created by hgferr on 01/10/16.
 */
public class ASTFactorLiteral extends ASTFactor {
    private final ASTLiteral l;

    public ASTFactorLiteral(ASTLiteral l) {
        this.l = l;
    }
}
