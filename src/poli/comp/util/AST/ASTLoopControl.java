package poli.comp.util.AST;



// LOOP_CONTROL    ::= LOOP_BREAK | LOOP_CONTINUE
public abstract class ASTLoopControl extends ASTStatement{

	private final String contents;

	public ASTLoopControl(String s) {
		this.contents = s;
	}

	@Override
  	public String toString(int level) {
  			return null;
  	}
}
