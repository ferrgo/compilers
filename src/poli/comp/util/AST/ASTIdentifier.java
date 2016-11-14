package poli.comp.util.AST;

import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

import java.util.ArrayList;

public class ASTIdentifier extends ASTTerminal {


    public ASTIdentifier(String s){
       super(s);
   }

    @Override
    public Object visit(Visitor v, ArrayList<AST> scopeTracker) throws SemanticException {
        return v.visitASTIdentifier(this, scopeTracker);
    }

    @Override
    public String toString(int level) {
        return null;
    }


}
