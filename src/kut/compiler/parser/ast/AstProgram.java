package kut.compiler.parser.ast;

import kut.compiler.compiler.CodeGenerator;
import kut.compiler.exception.CompileErrorException;

public class AstProgram extends AstNode 
{

	
	/**
	 * child node
	 */
	protected AstNode child;
	
	/**
	 * @param node
	 * @param platform
	 */
	public AstProgram(AstNode child)
	{
		this.child = child;
	}


	/**
	 *
	 */
	@Override
	public void cgen(CodeGenerator gen) throws CompileErrorException
	{
		//--------------------------------------
		//extern 
		gen.printComment("; 64 bit code.");
		gen.printCode	("bits 64", 0);
		
		//--------------------------------------
		//extern 
		gen.printComment("; to use the printf() function.");

		gen.printCode	("extern " + gen.getExternalFunctionName("printf"), 0);
		gen.printCode	();
		
		//--------------------------------------
		//data section
		gen.printComment("; data section.");
		gen.printSection("section .data");
		
		gen.printCode	(	"fmt:    db \"exit code:%d\", 10, 0 ; the format string for the exit message.");

		gen.printCode();

		//--------------------------------------
		//text section
		gen.printComment("; text section");
		gen.printSection("section .text");
		gen.printCode	(	"global " + gen.getEntryPointLabelName() + " ; the entry point.");
		gen.printCode();
		
		
		//the exit_program subroutine.		
		gen.printComment("; the subroutine for sys-exit. rax will be the exit code.");
		gen.printLabel("exit_program");				// where we exit the program.
		
		gen.printCode(	"and rsp, 0xFFFFFFFFFFFFFFF0 ; stack must be 16 bytes aligned to call a C function.");
		gen.printCode(	"push rax ; we need to preserve rax here.");
		gen.printCode();
		gen.printCode(	"; call printf to print out the exti code.");
		gen.printCode(	"lea rdi, [rel fmt] ; the format string");
		gen.printCode(	"mov rsi, rax		; the exit code ");
		gen.printCode(  "mov rax, 0			; no xmm register is used.");
		gen.printCode(	"call " + gen.getExternalFunctionName("printf"));
		gen.printCode();
		gen.printCode(	"mov rax, "+ gen.getExitSysCallNum() + "; specify the exit sys call.");
		gen.printCode(	"pop rdi ; this is the rax value we pushed at the entry of this sub routine");
		gen.printCode(	"syscall ; exit!");
		gen.printCode();
		
		//main function
		gen.printLabel	(gen.getEntryPointLabelName());
		gen.printCode(	"mov rax, 0 ; initialize the accumulator register.");
		//body of the code
		this.child.cgen(gen);
		
		gen.printCode();
		gen.printCode(	"jmp exit_program ; exit the program, rax should hold the exit code.");
		
		return;
	}

}
