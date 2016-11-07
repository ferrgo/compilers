package poli.comp.util.AST;

import poli.comp.checker.Visitor;

/**
 * Created by hgferr on 01/10/16.
 */
public class ASTFactorLiteral extends ASTFactor {
    private final ASTLiteral l;

    public ASTFactorLiteral(ASTLiteral l) {
        this.l = l;
    }


    //TODO
    @Override
    public Object visit(Visitor v, Object o) {
        return null;
    }
}
