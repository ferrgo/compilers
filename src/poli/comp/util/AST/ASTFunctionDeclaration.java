package poli.comp.util.AST;

/**
 * Created by hgferr on 30/09/16.
 */
 // FUNCTION_DECL   ::= FUNCTION TYPE ID LP (DECLARATION (, DECLARATION*))? RP (STATEMENT)* END FUNCTION
public abstract class ASTFunctionDeclaration extends ASTSubroutineDeclaration{

  private ASTType tp;
  private ASTIdentifier id;
  private List<ASTDeclaration> ldec;
  private List<ASTStatement> lsta;

    public ASTFunctionDeclaration(ASTType tp, ASTIdentifier id, List<ASTDeclaration> ldec, List<ASTStatement> lsta) {
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
