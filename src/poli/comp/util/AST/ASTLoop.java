package poli.comp.util.AST;


import java.util.List;

// LOOP            ::= DO WHILE LP EXPRESSION RP (STATEMENT)* END DO
public abstract class ASTLoop extends ASTStatement{

    private ASTExpression aex;
    private List<ASTStatement> ast;

    public ASTLoop(ASTExpression aex, List<ASTStatement> ast){
      this.aex = aex;
      this.ast =ast;
    }

  	@Override
  	public String toString(int level) {
  			return null;
  	}
}
