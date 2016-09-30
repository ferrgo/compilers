package poli.comp.util.AST;

/**
 * Created by hgferr on 30/09/16.
 */
 // FUNCTION_DECL   ::= FUNCTION TYPE ID LP (DECLARATION (, DECLARATION*))? RP (STATEMENT)* END FUNCTION
public abstract class ASTFunctionDeclaration extends ASTSubroutineDeclaration{

  private ASTType tp;
  private ASTIdentifier id;
  private ASTLeftParenthesis lp;
  private List<ASTDeclaration> ldec;
  private ASTRightParenthesis rp;
  private List<ASTStatement> lsta;


	public FunctionDeclaration(Identifier id, ParameterList pl, Type type, ArrayList<Command> commands) {
		this.id = id;
		this.pl = pl;
		this.type = type;
		this.commands = commands;
	}

	public String toString() {
		String str = "FD[ID[" + id.toString() + "]";
		if (pl!=null) str = "PL[" + pl.toString() + "]";
		str += "Type[" + type.toString() + "]";
		str += "CMD[" + commands.toString() + "]]";
		return str;
	}

	public Object visit(Visitor v, ArrayList<AST> list) throws SemanticException {
		return v.visitFuncDec(this,list);
	}

	public Identifier getId() {
		return id;
	}

	public ParameterList getPl() {
		return pl;
	}

	public Type getType() {
		return type;
	}

	public ArrayList<Command> getCommands() {
		return commands;
}
}
