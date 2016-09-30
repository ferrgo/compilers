package poli.comp.util.AST;

import java.util.List;

public class ASTProgram extends AST {

   public List<ASTDeclarationWithAssignment> l_dwa;
   public List<ASTFunctionDeclaration> l_fd;
   public List<ASTSubprogramDeclaration> l_sd; //trippy
   public ASTMainProgram mp;

    public ASTProgram(List<ASTDeclaration> l_d, List<ASTSubprogramDeclaration> l_sd, List<ASTFunctionDeclaration> l_fd, ASTMainProgram mp) {
        this.l_dwa =
    }

    @Override
    public String toString(int level) {
        return null;
    }

}
