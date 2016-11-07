package poli.comp.util.AST;

import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

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

    //TODO
	 public Object visit(Visitor v, Object o){
         try {
             return v.visitASTProgram(this, o);
         } catch (SemanticException e) {
             e.printStackTrace();
         }
         return null;
     }

    @Override
    public String toString(int level) {
        return null;
    }

}
