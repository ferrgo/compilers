package poli.comp.util.AST;




public class ASTFunctionCall extends ASTStatement{

	private final ASTFunctionArgs fa;
	private final ASTIdentifier id;

	public ASTFunctionCall(ASTIdentifier id, ASTFunctionArgs fa) {
		this.id = id;
		this.fa = fa;
	}

	@Override
  	public String toString(int level) {
  			return null;
  	}
}
