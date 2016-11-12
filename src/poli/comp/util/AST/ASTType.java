package poli.comp.util.AST;

import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

import java.util.ArrayList;

public class ASTType extends ASTTerminal {

    private String spelling;

    public ASTType(String s) {
        super(s);
    }

    @Override
    public Object visit(Visitor v, ArrayList<AST> scopeTracker) throws SemanticException {
        return v.visitASTType(this, scopeTracker);
    }

    @Override
    public String toString(int level) {
        return null;
    }

    public String getSpelling() {
        return spelling;
    }
}
