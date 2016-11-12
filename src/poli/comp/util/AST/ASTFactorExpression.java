package poli.comp.util.AST;

import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

import java.util.ArrayList;

/**
 * Created by hgferr on 01/10/16.
 */
public class ASTFactorExpression extends ASTFactor {
    private final ASTExpression exp;

    public ASTFactorExpression(ASTExpression exp) {
        this.exp=exp;
    }

    @Override
    public Object visit(Visitor v, ArrayList<AST> scopeTracker) throws SemanticException {
        return v.visitASTFactorExpression(this, scopeTracker);
    }

    public ASTExpression getExp() {
        return exp;
    }
}
