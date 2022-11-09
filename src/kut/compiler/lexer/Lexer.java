package kut.compiler.lexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

import kut.compiler.exception.CompileErrorException;


/**
 * @author hnishino
 *
 */
public class Lexer 
{
	/**
	 * the filename of a program to load.
	 */
	protected File				file	;	
	
	protected FileReader 		reader	;
	protected Stack<Integer>	unreadCharacters;
	
	/**
	 * @param program
	 */
	public Lexer(String filename) throws CompileErrorException
	{
		this.unreadCharacters = new Stack<Integer>();
		
		this.file = new File(filename);
		
		reader = null;
		try {
			reader = new FileReader(file);
		}
		catch (FileNotFoundException e) {
			throw new CompileErrorException("file not found: " + this.file.getAbsolutePath());
		}
		
		return;
	}
	


	/**
	 * @return
	 * @throws IOException
	 */
	protected int read() throws IOException
	{
		if (!unreadCharacters.isEmpty()) {
			return unreadCharacters.pop();
		}
		int i = reader.read();
		
		return i;
	}
	
	/**
	 * @param i
	 * @throws IOException
	 */
	protected void unread(int i) throws IOException
	{
		unreadCharacters.push(i);
	}
	/**
	 * @return
	 */
	public Token getNextToken() throws IOException
	{
		if (this.reader == null) {
			return null;
		}
		
		do {
			int i = this.read();
			
			if (i < 0) {
				return new Token(-1, "EOF");
			}
			
			char c = (char)i;

			
			//skip the white space character.
			if (Character.isWhitespace(c)) {
				continue;
			}
			
			//if it is a digit, then get a integer number token.
			if (Character.isDigit(c)) {
				this.unread(i); 
				return this.getNextTokenInteger();
			}
			
			//otherwise, tokenize one character as a token.
			return new Token(i, "" + c);
		} while(true);
	}
	
	/**
	 * @return
	 * @throws IOException
	 */
	public Token getNextTokenInteger() throws IOException
	{
		StringBuffer sb = new StringBuffer();
		
		int i;
		do {
			i = this.read();
			if (i < 0) {
				break;
			}
			char c = (char)i;
			if (!Character.isDigit(c)) {
				break;
			}
			
			sb.append(c);
		} while(true);

		this.unread(i);

		Token t = new Token(TokenClass.IntLiteral, sb.toString());
		
		return t;
	}
}
