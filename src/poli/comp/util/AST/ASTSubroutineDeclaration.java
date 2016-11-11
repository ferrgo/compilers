package poli.comp.util.AST;

/**
 * Created by hgferr on 30/09/16.
 */
 // SUBROUTINE_DECL ::= FUNCTION_DECL | SUBPROGRAM_DECL
public abstract class ASTSubroutineDeclaration extends AST{

	private boolean returnFound;
	public ASTSubroutineDeclaration(){
		this.returnFound=false;
	}

	public boolean hasReturn(){
		return this.returnFound;
	}

	public void foundReturn(){
		this.returnFound=true;
	}
}
