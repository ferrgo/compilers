package poli.comp.util.AST;

import java.util.List;

/**
 * Created by hgferr on 30/09/16.
 */
 // SUBROUTINE_DECL ::= FUNCTION_DECL | SUBPROGRAM_DECL
public abstract class ASTSubroutineDeclaration extends AST{
	private ASTIdentifier id;
	private ASTType tp;
	private boolean returnFound;
	private List<ASTStatement> statements;

	private List<ASTSingleDeclaration> l_params;


	public ASTSubroutineDeclaration (List<ASTSingleDeclaration> l_par, ASTIdentifier id, ASTType tp, List<ASTStatement> stts){
		this.id = id;
		this.tp=tp;
		this.returnFound=false;
		this.statements=stts;
		this.l_params = l_par;
	}

	//For checker:
	public ASTType getType(){
		return this.tp;
	}

	public String getTypeString(){
		return this.tp.getSpelling();
	}

	public boolean hasReturn(){
		return this.returnFound;
	}

	public void foundReturn(){
		this.returnFound=true;
	}
	
	public List<ASTStatement> getStatements() {
		return statements;
	}

	public ASTIdentifier getIdentifier() {
		return id;
	}

	public List<ASTSingleDeclaration> getParams() {
		return l_params;
	}



}
