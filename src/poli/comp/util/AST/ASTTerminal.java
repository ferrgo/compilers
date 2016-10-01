package poli.comp.util.AST;

public abstract class ASTTerminal extends AST {

   String contents;
    public ASTTerminal(String s){
        contents = s;
    }

    @Override
    public String toString(int level) {
        return null;
    }
}
