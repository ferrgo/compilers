package poli.comp.util.AST;


import poli.comp.checker.SemanticException;
import poli.comp.checker.Visitor;

import java.util.ArrayList;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public abstract class AST {

	public abstract Object visit(Visitor v, ArrayList<AST> scopeTracker) throws SemanticException;

	public String getSpaces(int level) {
		StringBuffer str = new StringBuffer();
		while( level>0 ) {
			str.append(" ");
			level--;
		}
		return str.toString();
	}


	public String toString(int level) {
			return null;
	}


}
