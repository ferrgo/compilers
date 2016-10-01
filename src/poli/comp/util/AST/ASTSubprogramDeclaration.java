package poli.comp.util.AST;

import java.util.List;

/**
 * Created by hgferr on 30/09/16.
 */
public class ASTSubprogramDeclaration extends ASTSubroutineDeclaration{
    private final ASTType type;
    private final ASTIdentifier id;
    private final List<ASTParamDeclaration> param;
    private final List<ASTStatement> statements;

    public ASTSubprogramDeclaration(ASTType t, ASTIdentifier subroutineName, List<ASTParamDeclaration> l_par, List<ASTStatement> l_s) {
        this.type = t;
        this.id = subroutineName;
        this.param = l_par;
        this.statements = l_s;
    }
}
