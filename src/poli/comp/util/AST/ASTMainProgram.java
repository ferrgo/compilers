package poli.comp.util.AST;

import poli.comp.checker.Checker;
import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

import java.util.List;

/**
 * Created by hgferr on 30/09/16.
 */
public class ASTMainProgram extends AST {

	    private final ASTIdentifier id;
	    private final List<ASTStatement> statements;

	    public ASTMainProgram(ASTIdentifier id, List<ASTStatement> l_s) {
	        this.id = id;
	        this.statements = l_s;
	    }

		 public ASTIdentifier getIdentifier(){
	 		return this.id;
	 	}


	 	public List<ASTStatement> getStatements(){
	 		return this.statements;
	 	}

	public Object visit(Visitor v, Object o) throws SemanticException {
		return v.visitASTMainProgram(this, o);
	}
}
