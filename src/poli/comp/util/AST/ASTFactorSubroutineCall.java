package poli.comp.util.AST;

/**
 * Created by hgferr on 01/10/16.
 */
public class ASTFactorSubroutineCall extends ASTFactor {

    private final ASTIdentifier id;
    private final ASTFunctionArgs l_args;

    public ASTFactorSubroutineCall(ASTIdentifier id, ASTFunctionArgs l_args) {
        this.id=id;
        this.l_args=l_args;
    }
}
