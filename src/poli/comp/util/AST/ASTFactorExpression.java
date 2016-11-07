package poli.comp.util.AST;

import poli.comp.checker.Visitor;

/**
 * Created by hgferr on 01/10/16.
 */
public class ASTFactorExpression extends ASTFactor {
    private final ASTExpression exp;

    public ASTFactorExpression(ASTExpression exp) {
        this.exp=exp;
    }

    //TODO
    @Override
    public Object visit(Visitor v, Object o) {
        return null;
    }
}
