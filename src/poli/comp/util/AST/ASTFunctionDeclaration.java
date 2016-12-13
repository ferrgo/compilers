package poli.comp.util.AST;

import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hgferr on 30/09/16.
 */
 // FUNCTION_DECL   ::= FUNCTION TYPE ID LP (DECLARATION (, DECLARATION*))? RP (STATEMENT)* END FUNCTION
public class ASTFunctionDeclaration extends ASTSubroutineDeclaration{

   private ASTIdentifier id;
   private List<ASTSingleDeclaration> l_params;
   private List<ASTStatement> l_stt;

     public ASTFunctionDeclaration(ASTType tp, ASTIdentifier id, List<ASTSingleDeclaration> l_par, List<ASTStatement> statements) {
         super(tp,statements);
         this.id = id;
         this.l_params = l_par;
     }

 	public ASTIdentifier getIdentifier(){
 		return this.id;
 	}

 	public List<ASTSingleDeclaration> getParams(){
 		return this.l_params;
 	}



    @Override
    public Object visit(Visitor v, ArrayList<AST> scopeTracker) throws SemanticException {
        return v.visitASTFunctionDeclaration(this, scopeTracker);
    }

    @Override
  	public String toString(int level) {
  			return null;
  	}

}
