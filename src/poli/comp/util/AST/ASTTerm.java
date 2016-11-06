package poli.comp.util.AST;


import java.util.List;
import java.util.Map;

// TERM            ::= FACTOR ((*|/) FACTOR)*
public class ASTTerm extends AST{

    private ASTFactor factor1;
    private Map<ASTOperatorArit,ASTFactor> l_opfactors;


    public ASTTerm(ASTFactor af, Map<ASTOperatorArit,ASTFactor> l_ot){
        this.factor1 = af;
        this.l_opfactors = l_ot;
    }
}
