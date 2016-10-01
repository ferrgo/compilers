package poli.comp.util.AST;



// EXP_ARIT        ::= TERM ((+|-) TERM)*
public abstract class ASTArithmeticExpression extends ASTExpression{

    private List<ASTTerm> at;

    public ASTArithmeticExpression(List<ASTTerm> at){
      this.at = at;
    }

  	@Override
  	public String toString(int level) {
  			return null;
  	}
}
