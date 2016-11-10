package poli.comp.util.AST;

import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

import java.util.List;
import java.util.Map;

/**
 * Created by hgferr on 30/09/16.
 */

 // DECLARATION_GROUP ::= TYPE :: ID (= EXPRESSION)? (,ID (= EXPRESSION)? )*
public class ASTDeclarationGroup extends ASTStatement{

	private ASTType tp;
   private List<ASTSingleDeclaration> declarations;
   private Map<ASTSingleDeclaration,ASTExpression> m_assign; //Maps assignments to declared vars

   public ASTDeclarationGroup(ASTType tp, List<ASTSingleDeclaration> declarations, Map<ASTSingleDeclaration,ASTExpression> m_a ) {
       this.tp = tp;
       this.declarations = declarations;
 		this.m_assign = m_a;
   }
 	public ASTType getType(){
 		return this.tp;
 	}

 	public List<ASTSingleDeclaration> getDeclarations(){
 		return this.declarations;
 	}

 	public Map<ASTSingleDeclaration,ASTExpression> getAssignmentMap(){
 		return this.m_assign;
 	}

    @Override
    public Object visit(Visitor v, Object o) throws SemanticException {
        return v.visitASTDeclarationGroup(this, o);
    }

    @Override
  	public String toString(int level) {
  			return null;
  	}

}
