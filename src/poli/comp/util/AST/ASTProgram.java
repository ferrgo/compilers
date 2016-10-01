package poli.comp.util.AST;

import java.util.List;

public class ASTProgram extends AST {

    private List<ASTDeclarationGroup> l_dwa;
    private List<ASTFunctionDeclaration> l_fd;
    private List<ASTSubprogramDeclaration> l_sd; //trippy
    private ASTMainProgram mp;

    public ASTProgram(List<ASTDeclarationGroup> l_d, List<ASTSubprogramDeclaration> l_sd, List<ASTFunctionDeclaration> l_fd, ASTMainProgram mp) {
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
