package poli.comp.util.AST;

import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

public class ASTIdentifier extends ASTTerminal {

   public ASTIdentifier(String s){
       super(s);
   }

    @Override
    public Object visit(Visitor v, Object o) throws SemanticException {
        return v.visitASTIdentifier(this, o);
    }

    @Override
    public String toString(int level) {
        return null;
    }

}
