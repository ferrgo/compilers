package poli.comp.util.AST;



// FUNCTION_ARGS   ::= LP (EXPRESSION(,EXPRESSION)*)? RP
public abstract class ASTFunctionArgs extends AST{

    private List<ASTExpression> aex;

    public ASTFunctionArgs(List<ASTExpression> aex){
      this.aex = aex;
    }

  	@Override
  	public String toString(int level) {
  			return null;
  	}
}
