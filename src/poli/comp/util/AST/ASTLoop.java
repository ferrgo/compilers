package poli.comp.util.AST;


import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

import java.util.List;

// LOOP            ::= DO WHILE LP EXPRESSION RP (STATEMENT)* END DO
public class ASTLoop extends ASTStatement{

    private ASTExpression aex;
    private List<ASTStatement> ast;

    public ASTLoop(ASTExpression aex, List<ASTStatement> ast){
      this.aex = aex;
      this.ast =ast;
    }

    @Override
    public Object visit(Visitor v, Object o) throws SemanticException {
        return v.visitASTLoop(this, o);
    }

    @Override
  	public String toString(int level) {
  			return null;
  	}
}
