package poli.comp.util.AST;

import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

import java.util.ArrayList;
import java.util.IdentityHashMap;

/**
 * Created by hgferr on 10/12/16.
 */
public class ASTFactorIdentifier extends ASTFactor {
    private ASTIdentifier id;

    public ASTFactorIdentifier(ASTIdentifier id){
        this.id = id;
    }

    public Object visit(Visitor v,ArrayList<AST> scopeTracker) throws SemanticException{
        return v.visitASTIdentifier(this.id, scopeTracker);
    }

    public ASTIdentifier getId(){
        return id;
    }

}
