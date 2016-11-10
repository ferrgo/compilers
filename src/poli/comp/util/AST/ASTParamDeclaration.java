package poli.comp.util.AST;

import poli.comp.checker.Visitor;

/**
 * Created by hgferr on 30/09/16.
 * @Deprecated - Replaced with Map in Subroutine
 */
public class ASTParamDeclaration extends AST{

	private final ASTType type;
	private final ASTIdentifier id;

	public ASTParamDeclaration(ASTType declType, ASTIdentifier declId) {
		 this.type = declType;
		 this.id = declId;
	}

    @Override
    public Object visit(Visitor v, Object o) {
        return null;
    }
}
