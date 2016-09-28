package poli.comp.util.AST;

public class ASTProgram extends AST {

   public List<ASTDeclarationWithAssignment> l_dwa;
   public List<ASTFunctionDeclaration> l_fd;
   public List<ASTSubprogramDeclaration> l_sd; //trippy
   public ASTMainProgram mp;

    @Override
    public String toString(int level) {
        return null;
    }

}
