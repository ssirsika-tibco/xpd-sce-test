package com.tibco.xpd.process.js.parser.transform;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import antlr.ASTFactory;
import antlr.RecognitionException;
import antlr.collections.AST;

import com.tibco.xpd.script.parser.antlr.JScriptEmitter;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;

public class JsIpeTranslateUtil {

	public static void handleNewExpression(AST expressionAST) {
		AST newExpressionAST = expressionAST.getFirstChild();
		if (newExpressionAST == null
				|| newExpressionAST.getType() != JScriptTokenTypes.LITERAL_new) {
			return;
		}
		AST className = newExpressionAST.getFirstChild();
		int type = className.getType();
		if (type == JScriptTokenTypes.IDENT) {
			String text = className.getText();
			if ("Date".equals(text)) { //$NON-NLS-1$				
				handleDateConstructor(expressionAST);
			} else if ("String".equals(text)) { //$NON-NLS-1$
				handleStringConstructor(expressionAST);
			} else if ("Boolean".equals(text)) { //$NON-NLS-1$
				handleBooleanConstructor(expressionAST);
			} else if ("Number".equals(text)) { //$NON-NLS-1$				
				handleNumberConstructor(expressionAST);
			}
		}
	}

	private static void handleStringConstructor(AST expressionAST) {
		AST newExpressionAST = expressionAST.getFirstChild();
		AST className = newExpressionAST.getFirstChild();
		AST eList = className.getNextSibling();
		AST exprAST = eList.getFirstChild();
		if (exprAST != null) {
			AST stringLiteral = exprAST.getFirstChild();
			if (stringLiteral.getType() == JScriptTokenTypes.STRING_LITERAL) {
				ASTFactory factory = new ASTFactory();
				AST tempAST = factory.create(JScriptTokenTypes.STRING_LITERAL);
				tempAST.setText(stringLiteral.getText());
				expressionAST.setFirstChild(tempAST);
			}
		}
	}

	private static void handleBooleanConstructor(AST expressionAST) {
		AST newExpressionAST = expressionAST.getFirstChild();
		AST className = newExpressionAST.getFirstChild();
		AST eList = className.getNextSibling();
		AST exprAST = eList.getFirstChild();
		if (exprAST != null) {
			AST stringLiteral = exprAST.getFirstChild();
			String toReturn = ""; //$NON-NLS-1$
			if (stringLiteral.getType() == JScriptTokenTypes.STRING_LITERAL) {
				toReturn = removeQuotes(stringLiteral.getText());
				boolean boolValue = Boolean.getBoolean(toReturn);
				ASTFactory factory = new ASTFactory();
				AST tempAST = null;
				if (boolValue) {
					tempAST = factory.create(JScriptTokenTypes.LITERAL_true);
				} else {
					tempAST = factory.create(JScriptTokenTypes.LITERAL_false);
				}
				tempAST.setText("" + boolValue); //$NON-NLS-1$
				expressionAST.setFirstChild(tempAST);
			} else {
				toReturn = stringLiteral.getText();
				ASTFactory factory = new ASTFactory();
				AST tempAST = factory.create(JScriptTokenTypes.STRING_LITERAL);
				tempAST.setText(toReturn);
				expressionAST.setFirstChild(tempAST);
			}
		}
	}

	private static void handleNumberConstructor(AST expressionAST) {
		AST newExpressionAST = expressionAST.getFirstChild();
		AST className = newExpressionAST.getFirstChild();
		AST eList = className.getNextSibling();
		AST exprAST = eList.getFirstChild();
		if (exprAST != null) {
			AST identAST = exprAST.getFirstChild();
			ASTFactory factory = new ASTFactory();
			AST dupAST = factory.dupTree(identAST);
			expressionAST.setFirstChild(dupAST);
//			int astType = identAST.getType();
//			if (astType == JScriptTokenTypes.NUM_INT
//					|| astType == JScriptTokenTypes.NUM_LONG
//					|| astType == JScriptTokenTypes.NUM_DOUBLE
//					|| astType == JScriptTokenTypes.NUM_FLOAT) {
//				ASTFactory factory = new ASTFactory();
//				AST tempAST = factory.create(identAST);
//				tempAST.setText(identAST.getText());
//				expressionAST.setFirstChild(tempAST);
//			}else{
//				ASTFactory factory = new ASTFactory();
//				AST dupAST = factory.dupTree(identAST);
//				expressionAST.setFirstChild(dupAST);
//			}
//			String expression = getExpression(exprAST);
//			ASTFactory factory = new ASTFactory();
//			AST tempAST = factory.create(JScriptTokenTypes.STRING_LITERAL);
//			tempAST.setText(expression);
//			expressionAST.setFirstChild(tempAST);
			
//			if (astType == JScriptTokenTypes.INC
//					|| astType == JScriptTokenTypes.DEC) {
//				toReturn = identAST.getText();
//				identAST = identAST.getFirstChild();
//			}
//			if (identAST.getType() == JScriptTokenTypes.NUM_INT
//					|| identAST.getType() == JScriptTokenTypes.NUM_LONG
//					|| identAST.getType() == JScriptTokenTypes.NUM_DOUBLE
//					|| identAST.getType() == JScriptTokenTypes.NUM_FLOAT) {
//				if (toReturn != null) {
//					toReturn = toReturn + identAST.getText();
//				} else {
//					toReturn = identAST.getText();
//				}
//				ASTFactory factory = new ASTFactory();
//				AST tempAST = factory.create(JScriptTokenTypes.NUM_INT);
//				tempAST.setText(toReturn);
//				expressionAST.setFirstChild(tempAST);
//			}
		}
	}

	private static void handleDateConstructor(AST expressionAST) {
		String newDateStr = ""; //$NON-NLS-1$
		AST newExpressionAST = expressionAST.getFirstChild();
		AST className = newExpressionAST.getFirstChild();
		AST eList = className.getNextSibling();
		AST exprAST = eList.getFirstChild();
		if (exprAST != null) {
			AST identAST = exprAST.getFirstChild();
			if (identAST.getType() == JScriptTokenTypes.STRING_LITERAL) {
				newDateStr = removeQuotes(identAST.getText());
			} else if (identAST.getType() == JScriptTokenTypes.NUM_INT) {
				String year = "00"; //$NON-NLS-1$
				String month = "00"; //$NON-NLS-1$
				String day = "00"; //$NON-NLS-1$
				year = identAST.getText();
				AST monthAST = exprAST.getNextSibling();
				if (monthAST != null) {
					// new Date(year,month,day);
					AST monthText = monthAST.getFirstChild();
					if (monthText.getType() == JScriptTokenTypes.NUM_INT) {
						month = monthText.getText();
					}else if(monthText.getType() == JScriptTokenTypes.IDENT){
						// new Date(MyYearDataField,MyMonthDataField,MyDayDataField);
						// do not know what can be done here.
						return;
					}
					AST dayAST = monthAST.getNextSibling();
					AST dayText = dayAST.getFirstChild();
					if (dayText.getType() == JScriptTokenTypes.NUM_INT) {
						day = dayText.getText();
					}else if(dayText.getType() == JScriptTokenTypes.IDENT){
						// new Date(MyYearDataField,MyMonthDataField,MyDayDataField);
						// do not know what can be done here.
						return;
					}
					newDateStr = year + "/" + month + "/" + day; //$NON-NLS-1$ //$NON-NLS-2$
				} else {
					// new Date(milliseconds);
					try {
						long i = Long.parseLong(year);
						Date date = new Date(i);
						SimpleDateFormat dateFormatter = new SimpleDateFormat(
								"yyyy/MM/dd"); //$NON-NLS-1$
						newDateStr = dateFormatter.format(date);
					} catch (NumberFormatException nfe) {
					}
				}
			}
		} else {
			// new Date()
			Calendar calInstance = Calendar.getInstance();
			newDateStr = calInstance.get(Calendar.YEAR) + "/" //$NON-NLS-1$
					+ (calInstance.get(Calendar.MONTH) + 1) + "/" //$NON-NLS-1$
					+ calInstance.get(Calendar.DAY_OF_MONTH);
		}
		newDateStr = "!" + newDateStr + "!"; //$NON-NLS-1$ //$NON-NLS-2$
		ASTFactory factory = new ASTFactory();
		AST tempAST = factory.create(JScriptTokenTypes.STRING_LITERAL);
		tempAST.setText(newDateStr);
		expressionAST.setFirstChild(tempAST);
	}

	private static String removeQuotes(String str) {
		boolean bool = str.startsWith("\""); //$NON-NLS-1$
		if (bool) {
			str = str.substring(1);
		}
		bool = str.endsWith("\""); //$NON-NLS-1$
		if (bool) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}
	
	private static String getExpression(AST expressionAST){
		StringBuffer buffer = new StringBuffer();
		JScriptEmitter emitter = new JScriptEmitter(buffer);
		String toReturn = ""; //$NON-NLS-1$
		try {
			emitter.expression(expressionAST);
			toReturn = buffer.toString();
		} catch (RecognitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return toReturn;
	}
}
