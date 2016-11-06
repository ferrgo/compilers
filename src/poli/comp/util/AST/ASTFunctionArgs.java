package poli.comp.util.AST;


import java.util.List;

// FUNCTION_ARGS   ::= LP (EXPRESSION(,EXPRESSION)*)? RP
public class ASTFunctionArgs extends AST{

    private List<ASTExpression> aex;

    public ASTFunctionArgs(List<ASTExpression> aex){
      this.aex = aex;
    }

  	public List<ASTExpression> getArgumentList(){
		return this.aex;
	}
}
