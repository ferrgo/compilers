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
  private List<ASTStatement> l_stt;

    public ASTFunctionDeclaration(ASTType tp, ASTIdentifier id, Map<ASTType,ASTIdentifier> m_par, List<ASTStatement> statements) {
        this.tp = tp;
        this.id = id;
        this.map_params = m_par;
        this.l_stt = statements;
    }

  	@Override
  	public String toString(int level) {
  			return null;
  	}

	private ASTType getType(){
		return this.tp;
	}

	private ASTIdentifier getIdentifier(){
		return this.id;
	}

	private Map<ASTType,ASTIdentifier> getParams(){
		return this.map_params;
	}

	private List<ASTStatement> getStatements(){
		return this.l_stt;
	}


}
