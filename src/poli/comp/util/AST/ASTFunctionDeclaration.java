package poli.comp.util.AST;

import java.util.List;

/**
 * Created by hgferr on 30/09/16.
 */
 // FUNCTION_DECL   ::= FUNCTION TYPE ID LP (DECLARATION (, DECLARATION*))? RP (STATEMENT)* END FUNCTION
public class ASTFunctionDeclaration extends ASTSubroutineDeclaration{

  private ASTType tp;
  private ASTIdentifier id;
  private Map<ASTType,ASTIdentifier> map_params;
  private List<ASTStatement> lsta;

    public ASTFunctionDeclaration(ASTType tp, ASTIdentifier id, Map<ASTType,ASTIdentifier> m_par, List<ASTStatement> lsta) {
        this.tp = tp;
        this.id = id;
        this.map_params = m_par;
        this.lsta = lsta;
    }

  	@Override
  	public String toString(int level) {
  			return null;
  	}

}
