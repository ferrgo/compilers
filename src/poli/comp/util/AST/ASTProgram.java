package poli.comp.util.AST;

import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

import java.util.List;

public class ASTProgram extends AST {

	private List<ASTDeclarationGroup> l_globals;
	private List<ASTFunctionDeclaration> l_fd;
	private List<ASTSubprogramDeclaration> l_sd; //trippy
	private ASTMainProgram mp;

	public ASTProgram(List<ASTDeclarationGroup> l_d, List<ASTSubprogramDeclaration> l_sd, List<ASTFunctionDeclaration> l_fd, ASTMainProgram mp) {
		 this.l_globals = l_d;
		 this.l_fd = l_fd;
		 this.l_sd = l_sd;
		 this.mp = mp;
	}
	public List<ASTDeclarationGroup> getGlobalDeclarationGroups(){
		return this.l_globals;
	}

	public List<ASTFunctionDeclaration> getFunctionDeclarations(){
		return this.l_fd;
	}

	public List<ASTSubprogramDeclaration> getSubprogramDeclarations(){
		return this.l_sd;
	}

	 public Object visit(Visitor v, Object o) throws SemanticException {
         return v.visitASTProgram(this, o);
     }

    @Override
    public String toString(int level) {
        return null;
    }

	public ASTMainProgram getMainProgram() {
		return mp;
	}
}
