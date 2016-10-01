package poli.comp.util.AST;

import java.util.List;

/**
 * Created by hgferr on 30/09/16.
 */
 // FUNCTION_DECL   ::= FUNCTION TYPE ID LP (DECLARATION (, DECLARATION*))? RP (STATEMENT)* END FUNCTION
public class ASTFunctionDeclaration extends ASTSubroutineDeclaration{

  private ASTType tp;
  private ASTIdentifier id;
  private List<ASTParamDeclaration> ldec;
  private List<ASTStatement> lsta;

    public ASTFunctionDeclaration(ASTType tp, ASTIdentifier id, List<ASTParamDeclaration> ldec, List<ASTStatement> lsta) {
        this.tp = tp;
        this.id = id;
        this.ldec = ldec;
        this.lsta = lsta;
    }

  	@Override
  	public String toString(int level) {
  			return null;
  	}

}
