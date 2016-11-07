package poli.comp.util.AST;

import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

/**
 * Created by hgferr on 01/10/16.
 */
public class ASTOperatorComp extends ASTTerminal{

    public ASTOperatorComp(String s) {
        super(s);
    }

    @Override
    public Object visit(Visitor v, Object o) throws SemanticException {
        return v.visitASTOperatorComp(this, o);
    }
}
