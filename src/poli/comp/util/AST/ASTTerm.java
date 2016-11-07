package poli.comp.util.AST;


import poli.comp.checker.Visitor;

import java.util.List;
import java.util.Map;

// TERM            ::= FACTOR ((*|/) FACTOR)*
public class ASTTerm extends AST{

    private ASTFactor factor1;
    private Map<ASTOperator,ASTFactor> l_opfactors;


    public ASTTerm(ASTFactor af, Map<ASTOperator,ASTFactor> l_ot){
        this.factor1 = af;
        this.l_opfactors = l_ot;
    }

    //TODO
    @Override
    public Object visit(Visitor v, Object o) {
        return null;
    }
}
