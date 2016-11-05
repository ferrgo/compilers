package poli.comp.util.AST;

import java.util.List;

public class ASTProgram extends AST {

	//PROG  ::= (DECLARATION_GROUP)* (FUNCTION_DECL | SUBPROGRAM_DECL)*  PROG_MAIN EOT


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

	 public Object visit(Visitor v, Object o){
 		return v.visitProgram(this, o); //TODO: at a "base case", like a visitTerminal, does visitTerminal we return the ASTTerminal?
 	 }

    @Override
    public String toString(int level) {
        return null;
    }

	 public ASTDeclarationGroup getGlobalDeclarations(){
		 return this.l_globals;
	 }

	 public ASTFunctionDeclaration getFunctionDeclarations(){
		 return this.l_fd;
	 }

	 public ASTSubprogramDeclaration getSubprogramDeclarations(){
		 return this.l_sd;
	 }

}
