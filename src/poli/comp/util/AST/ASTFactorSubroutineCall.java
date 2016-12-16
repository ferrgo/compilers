package poli.comp.util.AST;

import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

import java.util.ArrayList;

/**
 * Created by hgferr on 01/10/16.
 */
public class ASTFactorSubroutineCall extends ASTFactor {

	private ASTIdentifier id;
	private ASTFunctionArgs l_args;
	private ASTFunctionCall fc; //Gambiarra.

	public ASTFactorSubroutineCall(ASTIdentifier id, ASTFunctionArgs l_args) {
		this.id=id;
		this.l_args=l_args;
		this.fc=new ASTFunctionCall(id,l_args);
	}

	public ASTFunctionArgs getASTFunctionArgs (){
		return l_args;
	}

	public ASTIdentifier getId(){
		return id;
	}

	public ASTFunctionCall getFC(){
		return fc;
	}

    @Override
    public Object visit(Visitor v, ArrayList<AST> scopeTracker) throws SemanticException {
        return v.visitASTFactorSubroutineCall(this, scopeTracker);
    }
}
