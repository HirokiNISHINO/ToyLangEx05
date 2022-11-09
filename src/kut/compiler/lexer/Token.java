package kut.compiler.lexer;

public class Token 
{
	/**
	 * token class
	 */
	protected int		c;
	
	
	/**
	 * lexeme
	 */
	protected String	l;
	
	/**
	 * @param c
	 * @param l
	 */
	public Token(int c, String l) {
		this.c = c;
		this.l = l;
	}

	/**
	 * @return
	 */
	public int getC() {
		return c;
	}

	/**
	 * @return
	 */
	public String getL() {
		return l;
	}
	
	
}
