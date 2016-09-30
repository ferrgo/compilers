package poli.comp.util.AST;

/**
 * Created by hgferr on 30/09/16.
 */
public class ASTParamDeclaration {

    private final ASTType type;
    private final ASTIdentifier id;

    public ASTDeclaration(ASTType declType, ASTIdentifier declId) {
        this.type = declType;
        this.id = declId;
    }
}
