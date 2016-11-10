package poli.comp.util.AST;

public abstract class ASTTerminal extends AST {

   String spelling;

	 public ASTTerminal(String s){
        spelling = s;
    }

 	 public String getSpelling(){
 		 return this.contents;
 	 }

    @Override
    public String toString(int level) {
        return null;
    }
}
