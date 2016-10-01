package poli.comp.util.AST;

/**
 * Created by hgferr on 30/09/16.
 */

 // DECLARATION_GROUP ::= TYPE :: ID (= EXPRESSION)? (,ID (= EXPRESSION)? )*
public class ASTDeclaration {

  private ASTType tp;
  private Map<ASTIdentifier,ASTExpression> declarations;

  public ASTDeclaration(ASTType tp, Map<ASTIdentifier,ASTExpression> declarations) {
      this.tp = tp;
      this.declarations = declarations;
  }

  	@Override
  	public String toString(int level) {
  			return null;
  	}

}
