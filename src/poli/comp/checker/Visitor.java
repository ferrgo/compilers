package poli.comp.checker;

import poli.comp.util.AST.*;

import java.util.ArrayList;

public interface Visitor{

	//TODO - Check for missing methods, return etc...
	Object visitASTProgram               (ASTProgram p   , ArrayList<AST> scopeTracker) throws SemanticException;


	Object visitASTArithmeticExpression  (ASTArithmeticExpression ae  , ArrayList<AST> scopeTracker) throws SemanticException;
	Object visitASTAssignment            (ASTAssignment a   , ArrayList<AST> scopeTracker) throws SemanticException;
	Object visitASTDeclarationGroup      (ASTDeclarationGroup dg  , ArrayList<AST> scopeTracker) throws SemanticException;
	Object visitASTExpression            (ASTExpression e   , ArrayList<AST> o) throws SemanticException;
	Object visitASTFactorExpression      (ASTFactorExpression      fe  , ArrayList<AST> scopeTracker) throws SemanticException;
	Object visitASTFactorLiteral         (ASTFactorLiteral         fl  , ArrayList<AST> scopeTracker) throws SemanticException;
	Object visitASTFactorSubroutineCall  (ASTFactorSubroutineCall  fsc , ArrayList<AST> scopeTracker) throws SemanticException;
	Object visitASTFunctionArgs          (ASTFunctionArgs          fa  , ArrayList<AST> scopeTracker) throws SemanticException;
	Object visitASTFunctionCall          (ASTFunctionCall          fc  , ArrayList<AST> scopeTracker) throws SemanticException;
	Object visitASTFunctionDeclaration   (ASTFunctionDeclaration   fd  , ArrayList<AST> scopeTracker) throws SemanticException;
	Object visitASTIdentifier            (ASTIdentifier            id  , ArrayList<AST> scopeTracker) throws SemanticException;
	Object visitASTIfStatement           (ASTIfStatement           s   , ArrayList<AST> scopeTracker) throws SemanticException;
	Object visitASTLoop                  (ASTLoop                  l   , ArrayList<AST> scopeTracker) throws SemanticException;

	Object visitASTMainProgram           (ASTMainProgram           mp  , ArrayList<AST> scopeTracker) throws SemanticException;
//	Object visitASTOperatorArit          (ASTOperatorArit              opa  ,Object o) throws SemanticException;
	Object visitASTOperatorComp          (ASTOperatorComp          opc , ArrayList<AST> scopeTracker) throws SemanticException;
	Object visitASTReturnStatement       (ASTReturnStatement       rs  , ArrayList<AST> scopeTracker) throws SemanticException;
	Object visitASTSingleDeclaration     (ASTSingleDeclaration astSingleDeclaration, ArrayList<AST> scopeTracker) throws SemanticException;
	Object visitASTSubprogramDeclaration (ASTSubprogramDeclaration sd  , ArrayList<AST> scopeTracker) throws SemanticException;
	Object visitASTTerm                  (ASTTerm                  t   , ArrayList<AST> scopeTracker) throws SemanticException;


	Object visitASTLiteral               (ASTLiteral l, ArrayList<AST> scopeTracker) throws SemanticException;

	Object visitASTLoopContinue          (ASTLoopContinue astLoopContinue, ArrayList<AST> scopeTracker) throws SemanticException;

	Object visitASTLoopExit              (ASTLoopExit astLoopExit, ArrayList<AST> scopeTracker);

	Object visitASTOperator              (ASTOperator astOperator, ArrayList<AST> scopeTracker) throws SemanticException;

	Object visitASTType                  (ASTType astType, ArrayList<AST> scopeTracker);
}
