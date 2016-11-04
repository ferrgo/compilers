package poli.comp.util.AST;

import java.util.List;

/**
 * Created by hgferr on 30/09/16.
 */
public class ASTSubprogramDeclaration extends ASTSubroutineDeclaration{

    private ASTIdentifier id;
    private Map<ASTType,ASTIdentifier> map_params;
    private List<ASTStatement> statements;

    public ASTSubprogramDeclaration( ASTIdentifier subroutineName, Map<ASTType,ASTIdentifier> m_par, List<ASTStatement> l_s) {
        this.type = t;
        this.id = subroutineName;
        this.map_params = m_par;
        this.statements = l_s;
    }
}
