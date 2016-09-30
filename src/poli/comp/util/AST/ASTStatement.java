package poli.comp.util.AST;

// STATEMENT       ::= ( DECLARATION_GROUP | ID ( = EXPRESSION|FUNCTION_ARGS) | IF_STATEMENT | LOOP |
// 						    | LOOP_CONTROL | RETURN_STMT | PRINT_STMT )
public abstract class ASTStatement extends AST {


  private List<ASTDeclaration> l_dwa;
  private ASTIdentifier id;
  private ASTExpression ae;
  private ASTFunctionArgs afa;
  private ASTIfStatement ais;
  private ASTLoop alo;
  private ASTLoopControl alc;
  private ASTReturnStatement arst;
  private ASTPrintStatement apst;

  public ASTProgram(List<ASTDeclaration> l_dwa, ASTIdentifier id, ASTExpression ae, ASTFunctionArgs afa, ASTIfStatement ais, ASTLoop alo, ASTLoopControl alc, ASTReturnStatement arst, ASTPrintStatement apst) {
      this.l_dwa = l_d;
      this.id = id;
      this.ae = ae;
      this.afa = afa;
      this.ais = ais;
      this.alo = alo;
      this.alc =alc;
      this.arst = arst;
      this.apst = apst;
  }

	public String getSpaces(int level) {
		StringBuffer str = new StringBuffer();
		while( level>0 ) {
			str.append(" ");
			level--;
		}
		return str.toString();
	}
	@Override
	public String toString(int level) {
			return null;
	}
}
