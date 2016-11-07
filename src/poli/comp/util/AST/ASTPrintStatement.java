package poli.comp.util.AST;


import poli.comp.checker.Visitor;

// PRINT_STMT      ::= PRINT *, EXPRESSION
public class ASTPrintStatement extends ASTStatement{

    private ASTExpression aex;

    public ASTPrintStatement(ASTExpression aex){
      this.aex = aex;
    }

    //TODO
    @Override
    public Object visit(Visitor v, Object o) {
        return null;
    }
}
