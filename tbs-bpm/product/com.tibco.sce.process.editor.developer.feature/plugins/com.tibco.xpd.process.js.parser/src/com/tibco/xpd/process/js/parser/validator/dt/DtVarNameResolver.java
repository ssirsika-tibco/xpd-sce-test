package com.tibco.xpd.process.js.parser.validator.dt;

import java.util.List;

import antlr.collections.AST;

import com.tibco.xpd.script.parser.Messages;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.validator.DefaultVarNameResolver;

public class DtVarNameResolver extends DefaultVarNameResolver {

	private static final String VAR_PREFIX = "process"; //$NON-NLS-1$

	@Override
	protected void getVariableChildAST(AST ast, List<String> varNameList,
			List<String> supportedClasses) {
		for (AST sibling = ast; sibling != null; sibling = sibling
				.getNextSibling()) {
			if (sibling.getType() == JScriptTokenTypes.METHOD_CALL) {
				String strVarName = resolveMethodCall(sibling, supportedClasses);
				if (strVarName != null && !varNameList.contains(strVarName)) {
					varNameList.add(strVarName);
				}
				continue;
			}
			if (sibling.getType() == JScriptTokenTypes.LITERAL_new) {
				continue;
			}
			if (sibling.getType() == JScriptTokenTypes.DOT) {
				String strVarName = resolveDotAST(sibling, supportedClasses);
				if (strVarName != null && !varNameList.contains(strVarName)) {
					varNameList.add(strVarName);
				}
				continue;
			}
			if (sibling.getType() == JScriptTokenTypes.IDENT) {
				/* will record identifier such as myDataField, not sure whether we need for decision table 
				 * String varText = sibling.getText();
				if (!varNameList.contains(varText)) {
					varNameList.add(varText);
				}*/
			}
			AST firstChild = sibling.getFirstChild();
			if (firstChild != null) {
				getVariableChildAST(firstChild, varNameList, supportedClasses);
			}
		}
	}

	private String resolveDotAST(AST dotAST, List<String> supportedClasses) {
		String toReturn = null;
		AST firstChild = dotAST.getFirstChild();
		if (firstChild.getType() == JScriptTokenTypes.DOT) {
			toReturn = resolveDotAST(firstChild, supportedClasses);
		} else if (firstChild.getType() == JScriptTokenTypes.IDENT) {
			String fcText = firstChild.getText();
			// check if it is a supported class name
			boolean b = isSupportedClass(fcText, supportedClasses);
			if (b) {
				return toReturn;
			}
			if (VAR_PREFIX.equals(fcText)) {
				AST nextSibling = firstChild.getNextSibling();
				if (nextSibling != null
						&& nextSibling.getType() == JScriptTokenTypes.IDENT) {
					String nsText = nextSibling.getText();
					toReturn = nsText;
					return toReturn;
				}
			}
		} else {
			throw new IllegalStateException(Messages.DtVarNameResolver_UnSupportedAST
					+ firstChild.toString());
		}
		return toReturn;
	}

	private String resolveMethodCall(AST methodAST,
			List<String> supportedClasses) {
		AST firstChild = methodAST.getFirstChild();
		if (firstChild.getType() == JScriptTokenTypes.DOT) {
			AST nextSibling = firstChild.getNextSibling();
			if (nextSibling != null
					&& nextSibling.getType() == JScriptTokenTypes.ELIST) {
				AST dotAStChild = firstChild.getFirstChild();
				if(dotAStChild.getType()==JScriptTokenTypes.IDENT){
					String dotAstText = dotAStChild.getText();
					if(!VAR_PREFIX.equals(dotAstText)){
						
					}else if(isSupportedClass(dotAstText, supportedClasses)){
						
					}
				}else if(dotAStChild.getType()==JScriptTokenTypes.DOT){
					String strVarName = resolveDotAST(firstChild, supportedClasses);
					return strVarName;
				}
			} else {
				String strVarName = resolveDotAST(firstChild, supportedClasses);
				return strVarName;
			}
		}
		return null;
	}
}
