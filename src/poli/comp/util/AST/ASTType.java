package poli.comp.util.AST;

import poli.comp.checker.Visitor;

public class ASTType extends ASTTerminal {

    private String spelling;

    public ASTType(String s) {
        super(s);
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return null;
    }

    @Override
    public String toString(int level) {
        return null;
    }

    public String getSpelling() {
        return spelling;
    }
}
