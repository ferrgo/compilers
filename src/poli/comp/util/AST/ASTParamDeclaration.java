package poli.comp.util.AST;

/**
 * Created by hgferr on 30/09/16.
 */
public class ASTParamDeclaration extends AST{

    private final ASTType type;
    private final ASTIdentifier id;

    public ASTParamDeclaration(ASTType declType, ASTIdentifier declId) {
        this.type = declType;
        this.id = declId;
    }
}
