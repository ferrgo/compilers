package poli.comp.util.AST;

public class ASTType extends ASTTerminal {

   public ASTType(String c){
      super.contents = c;
      //TODO do we have to add checking here? i think its already done in the lex analyzer.
   }

    @Override
    public String toString(int level) {
        return null;
    }

}
