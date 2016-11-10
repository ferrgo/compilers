package poli.comp.util.AST;

import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

import java.util.List;
import java.util.Map;

/**
 * Created by hgferr on 30/09/16.
 */
public class ASTSubprogramDeclaration extends ASTSubroutineDeclaration{

	private ASTIdentifier id;
	private List<ASTSingleDeclaration> l_params;
	private List<ASTStatement> statements;

	public ASTSubprogramDeclaration( ASTIdentifier subroutineName, List<ASTSingleDeclaration> l_par, List<ASTStatement> l_s) {
		 this.type = t;
		 this.id = subroutineName;
		 this.map_params = l_par;
		 this.statements = l_s;
	}

    @Override
    public Object visit(Visitor v, Object o) throws SemanticException {
        return v.visitASTSubprogramDeclaration(this, o);
    }
}
