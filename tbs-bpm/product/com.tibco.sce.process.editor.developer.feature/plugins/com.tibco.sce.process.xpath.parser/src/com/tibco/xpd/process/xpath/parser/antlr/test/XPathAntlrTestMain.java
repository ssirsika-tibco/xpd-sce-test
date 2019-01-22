package com.tibco.xpd.process.xpath.parser.antlr.test;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.StringReader;

import antlr.ASTFactory;
import antlr.CommonAST;
import antlr.RecognitionException;
import antlr.Token;
import antlr.collections.AST;
import antlr.debug.misc.ASTFrame;

import com.tibco.xpd.process.xpath.parser.antlr.XPathLexer;
import com.tibco.xpd.process.xpath.parser.antlr.XPathParser;

public class XPathAntlrTestMain {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Need to pass the file path as an argument"); //$NON-NLS-1$
			System.exit(1);
		}
		String fileName = args[0];
		// compileIntoJava(args);
		File file = new File(fileName);
		if (!file.exists()) {
			System.err.println("File does not exist:" + fileName); //$NON-NLS-1$
			System.exit(1);
		}
		if (!file.isFile()) {
			System.err.println("File is not a regular file:" + fileName); //$NON-NLS-1$
			System.exit(1);
		}
		try {
		    
			FileInputStream fis = new FileInputStream(fileName);
			// Here BufferedInputStream is added for fast reading.
			BufferedInputStream bis = new BufferedInputStream(fis);
			DataInputStream dis = new DataInputStream(bis);
			//Remove the comments out of the file
			StringBuffer fileStringWithoutComments = new StringBuffer();
			while (dis.available() != 0) {
    	      String fileLine = dis.readLine();
    	      if(fileLine != null && !fileLine.startsWith("<!--")){ //$NON-NLS-1$
    	          fileStringWithoutComments.append(fileLine);
    	      }
			}
			StringReader reader = new StringReader(fileStringWithoutComments.toString());
            File fileWithoutComments = new File(file.getAbsolutePath()+"temp_file"); //$NON-NLS-1$
            
            FileWriter fw = new FileWriter(fileWithoutComments,false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(fileStringWithoutComments.toString());
            // dispose all the resources after using them.
            fis.close();
            bis.close();
            dis.close();
            bw.close();
            FileInputStream ncis = new FileInputStream(fileWithoutComments);
			// Create a scanner that reads from the input stream passed to us
			XPathLexer lexer = new XPathLexer(ncis);
			lexer.setFilename(fileWithoutComments.getAbsolutePath());
			
			// Create a parser that reads from the scanner
			XPathParser parser = new XPathParser(lexer);
			Token token = parser.LT(1);
			System.out.println("The token at index 1 is "+token.getText()); //$NON-NLS-1$
			parser.setFilename(fileWithoutComments.getAbsolutePath());
			parser.startValidation();
			
			showTreeAction(fileName, parser.getAST(), parser.getTokenNames(),
					"Parser"); //$NON-NLS-1$

			//printStaffwareScript(fileName, parser);
		} catch (Exception e) {
			if (e instanceof RecognitionException) {
				RecognitionException mte = (RecognitionException) e;
				int line = mte.getLine();
				int column = mte.getColumn();
				String msg = mte.getMessage();
				System.out.println("At line:" + line + " and column:" + column //$NON-NLS-1$ //$NON-NLS-2$
						+ ", the problem is " + msg); //$NON-NLS-1$
			} else {
				e.printStackTrace(); // so we can get stack trace
			}
		}
	}

	public static void showTreeAction(String f, AST t, String[] tokenNames,
			String parserName) {
		if (t == null)
			return;
		boolean showTree = true;
		if (showTree) {
			((CommonAST) t).setVerboseStringConversion(true, tokenNames);
			ASTFactory factory = new ASTFactory();
			AST r = factory.create(0, "AST ROOT " + parserName); //$NON-NLS-1$
			r.setFirstChild(t);
			final ASTFrame frame = new ASTFrame("XPath AST", r); //$NON-NLS-1$
			frame.setVisible(true);
			frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					frame.setVisible(false); // hide the Frame
					frame.dispose();
					System.exit(0);
				}
			});
		}


	}




}
