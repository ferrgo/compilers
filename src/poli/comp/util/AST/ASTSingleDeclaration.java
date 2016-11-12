package poli.comp.util.AST;


import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

import java.util.ArrayList;

public class ASTSingleDeclaration extends AST{

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

	@Override
	public Object visit(Visitor v, ArrayList<AST> scopeTracker) throws SemanticException {
		return v.visitASTSingleDeclaration(this, scopeTracker);
	}
}
