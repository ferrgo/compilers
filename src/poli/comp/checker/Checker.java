
class Checker implements Visitor{

	private IdentificationTable idt;


	public Checker(){
			this.idt = new IdentificationTable();
	}


	public AST check(AST a){

		//TODO: Even though we expect the parser to work reliably (i.e. throw an exception if at runtime it realizes the input code is wrong)
		//, would it be conceptually *wrong* to check for "a instanceof ASTProgram" here in this method?

		//So what we receive is an AST node which happens to be the root of the AST.
		//Assuming the Parser is working correctly, what we get in a is an ASTProgram node,
		// so the call to a.visit below will in turn call (this checker object, provided to a via argument).visitProgram
		a.visit(this,null);

		//The tree is now decorated, let's return it.
		return a;

	}

	Object visitProgram(ASTProgram p, Object o){
		//get subroutine List
			//visit all
		//get global decl List
			// visit all
		//get statement List
			//visit all
	}



}
