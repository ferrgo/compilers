package poli.comp.util.AST;


import poli.comp.checker.Visitor;

import java.util.ArrayList;

// PRINT_STMT      ::= PRINT *, EXPRESSION
public class ASTPrintStatement extends ASTStatement{

	private ASTExpression aex;

	public ASTPrintStatement(ASTExpression aex){
	  this.aex = aex;
	}

	public ASTExpression getExpression(){
		return this.aex;
	}

    //TODO
    @Override
    public Object visit(Visitor v, ArrayList<AST> scopeTracker) {
        return null;
    }
}
