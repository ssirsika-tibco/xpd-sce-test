// $ANTLR 2.7.6 (2005-12-22): "xpath.g" -> "XPathLexer.java"$

package com.tibco.xpd.process.xpath.parser.antlr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.wst.wsdl.Part;

import antlr.collections.AST;

import com.tibco.xpd.process.xpath.parser.util.XPathScriptParserUtil;
import com.tibco.xpd.process.xpath.parser.util.ProcessXPathUtil;
import com.tibco.xpd.process.xpath.parser.validator.xpath.XPathSymbolTable;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.validator.ISymbolTable;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;

import org.jaxen.saxpath.Axis;
import org.jaxen.saxpath.Operator;
import org.jaxen.expr.*;
import org.jaxen.JaxenException;

public interface XPathLexerTokenTypes {
	int EOF = 1;
	int NULL_TREE_LOOKAHEAD = 3;
	int SLASH = 4;
	int DOUBLE_SLASH = 5;
	int AT = 6;
	int STAR = 7;
	int IDENTIFIER = 8;
	int DOUBLE_COLON = 9;
	int COLON = 10;
	int LEFT_PAREN = 11;
	int RIGHT_PAREN = 12;
	int LEFT_BRACKET = 13;
	int RIGHT_BRACKET = 14;
	int DOT = 15;
	int DOT_DOT = 16;
	int LITERAL = 17;
	int NUMBER = 18;
	int DOLLAR_SIGN = 19;
	int COMMA = 20;
	int PIPE = 21;
	int KW_OR = 22;
	int KW_AND = 23;
	int EQUALS = 24;
	int NOT_EQUALS = 25;
	int LT = 26;
	int GT = 27;
	int LTE = 28;
	int GTE = 29;
	int PLUS = 30;
	int MINUS = 31;
	int DIV = 32;
	int MOD = 33;
	int WS = 34;
	int DIGIT = 35;
	int SINGLE_QUOTE_STRING = 36;
	int DOUBLE_QUOTE_STRING = 37;
	int COMMENT_DATA = 38;
	int COMMENT = 39;
}
