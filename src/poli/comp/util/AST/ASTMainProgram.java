package poli.comp.util.AST;

import java.util.List;

/**
 * Created by hgferr on 30/09/16.
 */
public class ASTMainProgram {

	    private final ASTIdentifier id;
	    private final List<ASTStatement> statements;

	    public ASTMainProgram(ASTIdentifier id, List<ASTStatement> l_s) {
	        this.id = id;
	        this.statements = l_s;
	    }

		 private ASTIdentifier getIdentifier(){
	 		return this.id;
	 	}


	 	private List<ASTStatement> getStatements(){
	 		return this.statements;
	 	}

}
