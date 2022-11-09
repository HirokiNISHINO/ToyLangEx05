package kut.compiler.parser.ast;


import kut.compiler.compiler.CodeGenerator;
import kut.compiler.lexer.Token;

public class AstIntLiteral extends AstNode 
{
	/**
	 * 
	 */
	protected Token t;
	
	/**
	 * @param t
	 */
	public AstIntLiteral(Token t)
	{
		this.t = t;
	}

	/**
	 *
	 */
	@Override
	public void cgen(CodeGenerator gen)
	{	
		gen.printCode("mov rax, " + this.t.getL());

		return;
	}
	

}
