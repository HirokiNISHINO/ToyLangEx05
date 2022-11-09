package kut.compiler.parser.ast;


import kut.compiler.compiler.CodeGenerator;
import kut.compiler.exception.CompileErrorException;

/**
 * @author hnishino
 *
 */
public abstract class AstNode {
	
	
		/**
		 * @return the generated code string
		 */
		public abstract void cgen(CodeGenerator gen) throws CompileErrorException;
		



}
