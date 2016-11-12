package poli.comp.util.AST;


import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

import java.util.ArrayList;

// RETURN_STMT     ::= RETURN (ID)?
public abstract class ASTReturnStatement extends ASTStatement{

    private ASTExpression expression;

    public ASTReturnStatement(){

	}

    @Override
    public Object visit(Visitor v, ArrayList<AST> scopeTracker) throws SemanticException {
        return v.visitASTReturnStatement(this, scopeTracker);
    }

    public ASTExpression getExpression() {
        return expression;
    }
}
