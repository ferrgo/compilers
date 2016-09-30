package poli.comp.util.AST;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public abstract class AST {

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
