package poli.comp.util.AST;


import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

// RETURN_STMT     ::= RETURN (ID)?
public abstract class ASTReturnStatement extends ASTStatement{

    private ASTExpression expression;

    public ASTReturnStatement(){

	}

    @Override
    public Object visit(Visitor v, Object o) throws SemanticException {
        return v.visitASTReturnStatement(this,o);
    }

    public ASTExpression getExpression() {
        return expression;
    }
}
