package poli.comp.util.AST;



// LOOP_CONTROL    ::= LOOP_BREAK | LOOP_CONTINUE
public abstract class ASTLoopControl extends ASTStatement{

      private ASTLoopExit ale;
      private ASTLoopContinue alc;

      public ASTLoopControl(ASTLoopExit ale, ASTLoopContinue alc){
        this.ale = ale;
        this.alc = alc;
      }

  	@Override
  	public String toString(int level) {
  			return null;
  	}
}
