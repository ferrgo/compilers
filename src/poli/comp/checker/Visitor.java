package poli.comp.checker;
interface Visitor{

	Object visitASTProgram               (ASTProgram               p   ,Object scopeTracker) throws SemanticException;
	Object visitASTArithmeticExpression  (ASTArithmeticExpression  ae  ,Object scopeTracker) throws SemanticException;
	Object visitASTAssignment            (ASTAssignment            a   ,Object scopeTracker) throws SemanticException;
	Object visitASTDeclarationGroup      (ASTDeclarationGroup      dg  ,Object scopeTracker) throws SemanticException;
	Object visitASTExpression            (ASTExpression            e   ,Object scopeTracker) throws SemanticException;
	Object visitASTFactorExpression      (ASTFactorExpression      fe  ,Object scopeTracker) throws SemanticException;
	Object visitASTFactorLiteral         (ASTFactorLiteral         fl  ,Object scopeTracker) throws SemanticException;
	Object visitASTFactorSubroutineCall  (ASTFactorSubroutineCall  fsc ,Object scopeTracker) throws SemanticException;
	Object visitASTFunctionArgs          (ASTFunctionArgs          fa  ,Object scopeTracker) throws SemanticException;
	Object visitASTFunctionCall          (ASTFunctionCall          fc  ,Object scopeTracker) throws SemanticException;
	Object visitASTFunctionDeclaration   (ASTFunctionDeclaration   fd  ,Object scopeTracker) throws SemanticException;
	Object visitASTIdentifier            (ASTIdentifier            id  ,Object scopeTracker) throws SemanticException;
	Object visitASTIfStatement           (ASTIfStatement           s   ,Object scopeTracker) throws SemanticException;
	Object visitASTLiteral               (ASTLiteral               l   ,Object scopeTracker) throws SemanticException;
	Object visitASTLoop                  (ASTLoop                  l   ,Object scopeTracker) throws SemanticException;
	Object visitASTLoopContinue          (ASTLoopContinue          lc  ,Object scopeTracker) throws SemanticException;
	Object visitASTLoopExit              (ASTLoopExit              le  ,Object scopeTracker) throws SemanticException;
	Object visitASTMainProgram           (ASTMainProgram           mp  ,Object scopeTracker) throws SemanticException;
	Object visitASTOperator              (ASTOperator              op  ,Object scopeTracker) throws SemanticException;
	Object visitASTOperatorComp          (ASTOperatorComp          opc ,Object scopeTracker) throws SemanticException;
	Object visitASTReturnStatement       (ASTReturnStatement       rs  ,Object scopeTracker) throws SemanticException;
	Object visitASTSubprogramDeclaration (ASTSubprogramDeclaration sd  ,Object scopeTracker) throws SemanticException;
	Object visitASTTerm                  (ASTTerm                  t   ,Object scopeTracker) throws SemanticException;
	Object visitASTType                  (ASTType                  t   ,Object scopeTracker) throws SemanticException;

}
