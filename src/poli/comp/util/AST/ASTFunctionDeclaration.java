package poli.comp.util.AST;

import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

import java.util.List;
import java.util.Map;

/**
 * Created by hgferr on 30/09/16.
 */
 // FUNCTION_DECL   ::= FUNCTION TYPE ID LP (DECLARATION (, DECLARATION*))? RP (STATEMENT)* END FUNCTION
public class ASTFunctionDeclaration extends ASTSubroutineDeclaration{

	private ASTType tp;
   private ASTIdentifier id;
   private List<ASTSingleDeclaration> l_params;
   private List<ASTStatement> l_stt;

     public ASTFunctionDeclaration(ASTType tp, ASTIdentifier id, List<ASTSingleDeclaration> l_par, List<ASTStatement> statements) {
         this.tp = tp;
         this.id = id;
         this.l_params = l_par;
         this.l_stt = statements;
     }
 	private ASTType getType(){
 		return this.tp;
 	}

 	private ASTIdentifier getIdentifier(){
 		return this.id;
 	}

 	private List<ASTSingleDeclaration> getParams(){
 		return this.l_params;
 	}

 	private List<ASTStatement> getStatements(){
 		return this.l_stt;
 	}


    @Override
    public Object visit(Visitor v, Object o) throws SemanticException {
        return v.visitASTFunctionDeclaration(this, o);
    }

    @Override
  	public String toString(int level) {
  			return null;
  	}

}
