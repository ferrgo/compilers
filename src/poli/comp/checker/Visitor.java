package poli.comp.checker;
interface Visitor{

	Object visitProgram               (ASTProgram               p   ,Object scopeTracker) throws SemanticException;
	Object visitArithmeticExpression  (ASTArithmeticExpression  ae  ,Object scopeTracker) throws SemanticException;
	Object visitAssignment            (ASTAssignment            a   ,Object scopeTracker) throws SemanticException;
	Object visitDeclarationGroup      (ASTDeclarationGroup      dg  ,Object scopeTracker) throws SemanticException;
	Object visitExpression            (ASTExpression            e   ,Object scopeTracker) throws SemanticException;
	Object visitFactorExpression      (ASTFactorExpression      fe  ,Object scopeTracker) throws SemanticException;
	Object visitFactorLiteral         (ASTFactorLiteral         fl  ,Object scopeTracker) throws SemanticException;
	Object visitFactorSubroutineCall  (ASTFactorSubroutineCall  fsc ,Object scopeTracker) throws SemanticException;
	Object visitFunctionArgs          (ASTFunctionArgs          fa  ,Object scopeTracker) throws SemanticException;
	Object visitFunctionCall          (ASTFunctionCall          fc  ,Object scopeTracker) throws SemanticException;
	Object visitFunctionDeclaration   (ASTFunctionDeclaration   fd  ,Object scopeTracker) throws SemanticException;
	Object visitIdentifier            (ASTIdentifier            id  ,Object scopeTracker) throws SemanticException;
	Object visitIfStatement           (ASTIfStatement           s   ,Object scopeTracker) throws SemanticException;
	Object visitLiteral               (ASTLiteral               l   ,Object scopeTracker) throws SemanticException;
	Object visitLoop                  (ASTLoop                  l   ,Object scopeTracker) throws SemanticException;
	Object visitLoopContinue          (ASTLoopContinue          lc  ,Object scopeTracker) throws SemanticException;
	Object visitLoopExit              (ASTLoopExit              le  ,Object scopeTracker) throws SemanticException;
	Object visitMainProgram           (ASTMainProgram           mp  ,Object scopeTracker) throws SemanticException;
	Object visitOperator              (ASTOperator              op  ,Object scopeTracker) throws SemanticException;
	Object visitOperatorComp          (ASTOperatorComp          opc ,Object scopeTracker) throws SemanticException;
	Object visitReturnStatement       (ASTReturnStatement       rs  ,Object scopeTracker) throws SemanticException;
	Object visitSubprogramDeclaration (ASTSubprogramDeclaration sd  ,Object scopeTracker) throws SemanticException;
	Object visitTerm                  (ASTTerm                  t   ,Object scopeTracker) throws SemanticException;
	Object visitType                  (ASTType                  t   ,Object scopeTracker) throws SemanticException;

}
