package poli.comp.util.AST;

import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

import java.util.ArrayList;

public class ASTIdentifier extends ASTTerminal {
    private String type = null;
    private AST dec = null;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AST getDec() {
        return dec;
    }

    public void setDec(AST dec) {
        this.dec = dec;
    }



}
