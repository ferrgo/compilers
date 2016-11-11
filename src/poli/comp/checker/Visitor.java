package poli.comp.checker;

import poli.comp.util.AST.*;

public interface Visitor{

	//TODO - Check for missing methods, return etc...
	Object visitASTProgram               (ASTProgram p   , Object o) throws SemanticException;


	Object visitASTArithmeticExpression  (ASTArithmeticExpression ae  , Object o) throws SemanticException;
	Object visitASTAssignment            (ASTAssignment a   , Object o) throws SemanticException;
	Object visitASTDeclarationGroup      (ASTDeclarationGroup dg  , Object o) throws SemanticException;
	Object visitASTExpression            (ASTExpression e   , Object o) throws SemanticException;
	Object visitASTFactorExpression      (ASTFactorExpression      fe  ,Object o) throws SemanticException;
	Object visitASTFactorLiteral         (ASTFactorLiteral         fl  ,Object o) throws SemanticException;
	Object visitASTFactorSubroutineCall  (ASTFactorSubroutineCall  fsc ,Object o) throws SemanticException;
	Object visitASTFunctionArgs          (ASTFunctionArgs          fa  ,Object o) throws SemanticException;
	Object visitASTFunctionCall          (ASTFunctionCall          fc  ,Object o) throws SemanticException;
	Object visitASTFunctionDeclaration   (ASTFunctionDeclaration   fd  ,Object o) throws SemanticException;
	Object visitASTIdentifier            (ASTIdentifier            id  ,Object o) throws SemanticException;
	Object visitASTIfStatement           (ASTIfStatement           s   ,Object o) throws SemanticException;
	Object visitASTLiteral               (ASTLiteral               l   ,Object o) throws SemanticException;
	Object visitASTLoop                  (ASTLoop                  l   ,Object o) throws SemanticException;
	Object visitASTLoopContinue          (ASTLoopContinue          lc  ,Object o) throws SemanticException;
	Object visitASTLoopExit              (ASTLoopExit              le  ,Object o) throws SemanticException;
	Object visitASTMainProgram           (ASTMainProgram           mp  ,Object o) throws SemanticException;
	Object visitASTOperator              (ASTOperator              op  ,Object o) throws SemanticException;
	Object visitASTOperatorComp          (ASTOperatorComp          opc ,Object o) throws SemanticException;
	Object visitASTReturnStatement       (ASTReturnStatement       rs  ,Object o) throws SemanticException;
	Object visitASTSingleDeclaration     (ASTSingleDeclaration astSingleDeclaration, Object o) throws SemanticException;
	Object visitASTSubprogramDeclaration (ASTSubprogramDeclaration sd  ,Object o) throws SemanticException;
	Object visitASTTerm                  (ASTTerm                  t   ,Object o) throws SemanticException;
	Object visitASTType                  (ASTType                  t   ,Object o) throws SemanticException;


}
