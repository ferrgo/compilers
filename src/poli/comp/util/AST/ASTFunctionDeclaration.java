package poli.comp.util.AST;

import java.util.List;

/**
 * Created by hgferr on 30/09/16.
 */
 // FUNCTION_DECL   ::= FUNCTION TYPE ID LP (DECLARATION (, DECLARATION*))? RP (STATEMENT)* END FUNCTION
public class ASTFunctionDeclaration extends ASTSubroutineDeclaration{

  private ASTType tp;
  private ASTIdentifier id;
  private List<ASTSingleDeclaration> l_params;
  private List<ASTStatement> l_stt;

    public ASTFunctionDeclaration(ASTType tp, ASTIdentifier id, List<ASTSingleDeclaration> l_par, List<ASTStatement> statements) {
        this.tp = tp;
        this.id = id;
        this.l_params = l_par;
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

	private List<ASTSingleDeclaration> getParams(){
		return this.l_params;
	}

	private List<ASTStatement> getStatements(){
		return this.l_stt;
	}


}
