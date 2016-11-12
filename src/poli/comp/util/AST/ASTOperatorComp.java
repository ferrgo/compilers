package poli.comp.util.AST;

import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

import java.util.ArrayList;

/**
 * Created by hgferr on 01/10/16.
 */
public class ASTOperatorComp extends ASTTerminal{

    public ASTOperatorComp(String s) {
        super(s);
    }

    @Override
    public Object visit(Visitor v, ArrayList<AST> scopeTracker) throws SemanticException {
        return v.visitASTOperatorComp(this, scopeTracker);
    }
}
