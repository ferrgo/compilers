package poli.comp.checker;

import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;
import poli.comp.util.AST.*;
import poli.comp.util.symbolsTable.IdentificationTable;

public class Checker implements Visitor {

	private IdentificationTable idt;


	public Checker(){
			this.idt = new IdentificationTable();
	}


	public AST check(AST a) throws SemanticException {

		//TODO: Even though we expect the parser to work reliably (i.e. throw an exception if at runtime it realizes the input code is wrong)
		//, would it be conceptually *wrong* to check for "a instanceof ASTProgram" here in this method?

		//So what we receive is an AST node which happens to be the root of the AST.
		//Assuming the Parser is working correctly, what we get in a is an ASTProgram node,
		// so the call to a.visit below will in turn call (this checker object, provided to a via argument).visitProgram
		a.visit(this,null);

		//The tree is now decorated, let's return it.
		return a;

	}


	//TODO
	@Override
	public Object visitASTProgram(ASTProgram p, Object o) throws SemanticException {
		//get subroutine List
		//visit all
		//get global decl List
		// visit all
		//get statement List
		//visit all
		return null;
	}
	//TODO
	@Override
	public Object visitASTArithmeticExpression(ASTArithmeticExpression ae, Object o) throws SemanticException {
		return null;
	}
	//TODO
	@Override
	public Object visitASTAssignment(ASTAssignment a, Object o) throws SemanticException {
		return null;
	}
	//TODO
	@Override
	public Object visitASTDeclarationGroup(ASTDeclarationGroup dg, Object o) throws SemanticException {
		return null;
	}
	//TODO
	@Override
	public Object visitASTExpression(ASTExpression e, Object o) throws SemanticException {
		return null;
	}
	//TODO
	@Override
	public Object visitASTFactorExpression(ASTFactorExpression fe, Object o) throws SemanticException {
		return null;
	}
	//TODO
	@Override
	public Object visitASTFactorLiteral(ASTFactorLiteral fl, Object o) throws SemanticException {
		return null;
	}
	//TODO
	@Override
	public Object visitASTFactorSubroutineCall(ASTFactorSubroutineCall fsc, Object o) throws SemanticException {
		return null;
	}
	//TODO
	@Override
	public Object visitASTFunctionArgs(ASTFunctionArgs fa, Object o) throws SemanticException {
		return null;
	}
	//TODO
	@Override
	public Object visitASTFunctionCall(ASTFunctionCall fc, Object o) throws SemanticException {
		return null;
	}
	//TODO
	@Override
	public Object visitASTFunctionDeclaration(ASTFunctionDeclaration fd, Object o) throws SemanticException {
		return null;
	}
	//TODO
	@Override
	public Object visitASTIdentifier(ASTIdentifier id, Object o) throws SemanticException {
		return null;
	}
	//TODO
	@Override
	public Object visitASTIfStatement(ASTIfStatement s, Object o) throws SemanticException {
		return null;
	}
	//TODO
	@Override
	public Object visitASTLiteral(ASTLiteral l, Object o) throws SemanticException {
		return null;
	}
	//TODO
	@Override
	public Object visitASTLoop(ASTLoop l, Object o) throws SemanticException {
		return null;
	}
	//TODO
	@Override
	public Object visitASTLoopContinue(ASTLoopContinue lc, Object o) throws SemanticException {
		return null;
	}
	//TODO
	@Override
	public Object visitASTLoopExit(ASTLoopExit le, Object o) throws SemanticException {
		return null;
	}
	//TODO
	@Override
	public Object visitASTMainProgram(ASTMainProgram mp, Object o) throws SemanticException {
		return null;
	}
	//TODO
	@Override
	public Object visitASTOperator(ASTOperator op, Object o) throws SemanticException {
		return null;
	}
	//TODO
	@Override
	public Object visitASTOperatorComp(ASTOperatorComp opc, Object o) throws SemanticException {
		return null;
	}
	//TODO
	@Override
	public Object visitASTReturnStatement(ASTReturnStatement rs, Object o) throws SemanticException {
		return null;
	}
	//TODO
	@Override
	public Object visitASTSubprogramDeclaration(ASTSubprogramDeclaration sd, Object o) throws SemanticException {
		return null;
	}
	//TODO
	@Override
	public Object visitASTTerm(ASTTerm t, Object o) throws SemanticException {
		return null;
	}
	//TODO
	@Override
	public Object visitASTType(ASTType t, Object o) throws SemanticException {
		return null;
	}
}
