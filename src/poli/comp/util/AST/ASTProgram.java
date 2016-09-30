package poli.comp.util.AST;

import java.util.List;

public class ASTProgram extends AST {

//   public List<ASTDeclarationWithAssignment> l_dwa;//WaT? TODO Check..
    public  List<ASTDeclaration> l_dwa;
    public List<ASTFunctionDeclaration> l_fd;
    public List<ASTSubprogramDeclaration> l_sd; //trippy
    public ASTMainProgram mp;

    public ASTProgram(List<ASTDeclaration> l_d, List<ASTSubprogramDeclaration> l_sd, List<ASTFunctionDeclaration> l_fd, ASTMainProgram mp) {
        this.l_dwa = l_d;
        this.l_fd = l_fd;
        this.l_sd = l_sd;
        this.mp = mp;
    }

    @Override
    public String toString(int level) {
        return null;
    }

}
