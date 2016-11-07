package poli.comp.util.AST;

import poli.comp.checker.Visitor;

/**
 * Created by hgferr on 01/10/16.
 */
public class ASTLiteral extends ASTTerminal{
    public ASTLiteral(String spelling) {
        super(spelling);
    }

    //TODO
    @Override
    public Object visit(Visitor v, Object o) {
        return null;
    }
}
