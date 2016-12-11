package poli.comp.util.AST;

import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

import java.util.ArrayList;

/**
 * Created by hgferr on 01/10/16.
 */
public class ASTLiteral extends ASTTerminal{
    public ASTLiteral(String spelling) {
        super(spelling);
    }

	 public String getTypeString(){
		 if (this.spelling.equals(".true.") || this.spelling.equals(".false.")){
			 return "LOGICAL";
		 }else{
			 return "INTEGER";
		 }
	 }


	@Override
	public Object visit(Visitor v, ArrayList<AST> scopeTracker) throws SemanticException {
		return v.visitASTLiteral(this, scopeTracker);
	}
}
