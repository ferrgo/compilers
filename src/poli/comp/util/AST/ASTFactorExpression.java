package poli.comp.util.AST;

import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

/**
 * Created by hgferr on 01/10/16.
 */
public class ASTFactorExpression extends ASTFactor {
    private final ASTExpression exp;

    public ASTFactorExpression(ASTExpression exp) {
        this.exp=exp;
    }

    @Override
    public Object visit(Visitor v, Object o) throws SemanticException {
        return v.visitASTFactorExpression(this, o);
    }

    public ASTExpression getExp() {
        return exp;
    }
}
