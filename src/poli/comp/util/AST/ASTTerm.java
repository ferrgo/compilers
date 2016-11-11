package poli.comp.util.AST;


import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

import java.util.List;
import java.util.Map;

// TERM            ::= FACTOR ((*|/) FACTOR)*
public class ASTTerm extends AST{

    private ASTFactor factor;
    private Map<ASTOperatorArit,ASTFactor> l_opfactors;

	 public ASTTerm getFactor(){
		 return this.factor;
	 }
	 public Map<ASTOperatorArit,ASTFactor> getOpFactors(){
		 return this.l_opfactors;
	 }

    public ASTTerm(ASTFactor af, Map<ASTOperatorArit,ASTFactor> l_ot){
        this.factor = af;
        this.l_opfactors = l_ot;
    }

    @Override
    public Object visit(Visitor v, Object o) throws SemanticException {
        return v.visitASTTerm(this, o);
    }
}
