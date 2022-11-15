package kut.compiler.compiler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import kut.compiler.exception.CompileErrorException;
import kut.compiler.parser.ast.AstNode;

/**
 * @author hnishino
 *
 */
/**
 * @param program
 */
public class CodeGenerator 
{
	protected Platform		platform;
	protected String 		filename;
	protected AstNode			program	;
	protected PrintWriter 	writer	;
	
	/**
	 * @param program
	 * @param filename
	 */
	public CodeGenerator(AstNode program, String filename, Platform platform) 
	{
		this.program 	= program;
		this.filename 	= filename;
		this.platform	= platform;
	}
	
	/**
	 * @return
	 */
	public String getExitSysCallNum()
	{
		return (this.platform == Platform.MAC ? "0x2000001" : "60");
	}
	
	/**
	 * @param funcname
	 * @return
	 */
	public String getExternalFunctionName(String funcname)
	{
		return (this.platform == Platform.MAC ? "_" + funcname : funcname);
	}
	
	/**
	 * @return
	 */
	public String getEntryPointLabelName()
	{
		return (this.platform == Platform.MAC ? "_main" : "_start");
	}
	
	/**
	 * @throws IOException
	 */
	public void generateCode() throws IOException, CompileErrorException
	{
		File f = new File(filename);
		writer = new PrintWriter(f);
		
		this.program.cgen(this);
		
		writer.flush();
		writer.close();
		
		return;
		
	}
	
	/**
	 * 
	 */
	public void printCode()
	{
		writer.println("");
		return;		
	}
	
	/**
	 * @param buf
	 * @param code
	 * @param indent
	 */
	public void printCode(String code)
	{
		this.printCode(code, 1);
		return;
	}
	
	/**
	 * @param label
	 */
	public void printLabel(String label)
	{
		this.printCode(label + ":", 0);
	}
	
	/**
	 * @param comment
	 */
	public void printComment(String comment)
	{
		this.printCode(comment, 0);
	}
	
	/**
	 * @param section
	 */
	public void printSection(String section)
	{
		this.printCode(section, 0);
	}
	
	/**
	 * @param buf
	 * @param code
	 * @param indent
	 */
	public void printCode(String code, int indent)
	{
		for (int i = 0; i < indent; i++) {
			writer.print("\t");
		}
		
		writer.println(code);
		
		return;
	}

}
