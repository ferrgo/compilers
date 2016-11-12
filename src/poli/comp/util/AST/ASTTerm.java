package poli.comp.util.AST;


import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

import java.util.ArrayList;
import java.util.Map;

// TERM            ::= FACTOR ((*|/) FACTOR)*
public class ASTTerm extends AST{

    private ASTFactor factor;
    private Map<ASTOperatorArit,ASTFactor> l_opfactors;

	 public ASTFactor getFactor(){
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
    public Object visit(Visitor v, ArrayList<AST> scopeTracker) throws SemanticException {
        return v.visitASTTerm(this, scopeTracker);
    }
}
