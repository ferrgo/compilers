package poli.comp.util.AST;

import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

/**
 * Created by hgferr on 01/10/16.
 */
public class ASTFactorLiteral extends ASTFactor {
    private ASTLiteral l;

    public ASTFactorLiteral(ASTLiteral l) {
        this.l = l;
    }

	 public ASTLiteral getLiteral(){
		 return this.l;
	 }
    @Override
    public Object visit(Visitor v, Object o) throws SemanticException {
        return v.visitASTFactorLiteral(this, o);
    }
}
