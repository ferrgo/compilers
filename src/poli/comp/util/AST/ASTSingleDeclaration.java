package poli.comp.util.AST;


class ASTSingleDeclaration extends AST{

	//Set by parser
	private ASTIdentifier id;
	private ASTType type;

	public ASTSingleDeclaration(ASTType t,ASTIdentifier i){
		this.id=i;
		this.type=t;
	}

	public ASTType getType(){
		return this.type;
	}

	public String getTypeString(){
		return this.type.getSpelling();
	}
	public ASTIdentifier getIdentifier(){
		return this.id;
	}

}
