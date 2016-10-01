package poli.comp.util.AST;



// FACTOR          ::= ID(FUNCTION_ARGS)? | LITERAL | LP EXPRESSION RP
public abstract class ASTFactor extends ASTExpression{

    private ASTIdentifier id;
    private ASTFunctionArgs afa;
    private ASTTerminal ate;
    private ASTExpression aex;

    public ASTFactor(ASTIdentifier id, ASTFunctionArgs afa, ASTTerminal ate, ASTExpression aex){
      this.id = id;
      this.afa = afa;
      this.ate = ate;
      this.aex = aex;
    }

  	@Override
  	public String toString(int level) {
  			return null;
  	}
}
