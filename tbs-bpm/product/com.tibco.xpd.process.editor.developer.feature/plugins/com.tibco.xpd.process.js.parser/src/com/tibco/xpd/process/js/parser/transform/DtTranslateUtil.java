package com.tibco.xpd.process.js.parser.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import antlr.collections.AST;

import com.tibco.xpd.process.js.parser.util.Utility;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.util.ParseUtil;

public class DtTranslateUtil {
	
	public static void translateExprAST(AST exprAST,
			Map<String, String> oldNewDataNameMap,
			List<String> ignoreIdentifierList, List<String> destinationList,
			List<String> supportedContexts,String scriptType) {
		List<AST> firstLevelChildren = null;
		if (exprAST != null && exprAST.getType() == JScriptTokenTypes.DOT) {
			firstLevelChildren = new ArrayList<AST>();
			firstLevelChildren.add(exprAST);
		} else {
			firstLevelChildren = ParseUtil.getDotAstList(exprAST);
		}
		if (firstLevelChildren != null && !firstLevelChildren.isEmpty()) {
			for (AST dotAST : firstLevelChildren) {
				/*
				 * First, we need to translate the identifier with the new name.
				 * then, strip the specified identifiers. If reverse the order
				 * then we would not be able to distinguish between 
				 * process.b and 'b'.
				 */						
				translateDotAST(dotAST, oldNewDataNameMap, destinationList,
						supportedContexts, scriptType);			
				stripIdentifiersToIgnore(dotAST, ignoreIdentifierList);			
			}
		}		
	}

	private static void stripIdentifiersToIgnore(AST dotAST,
			List<String> ignoreIdentifierList) {
		if (ignoreIdentifierList == null || ignoreIdentifierList.isEmpty()) {
			return;
		}
		AST lastDotAST = getLastDotAST(dotAST);
		AST firstChild = lastDotAST.getFirstChild();
		String fcText = firstChild.getText();
		AST nextSibling = firstChild.getNextSibling();
		if (nextSibling != null
				&& nextSibling.getType() != JScriptTokenTypes.IDENT) {
			return;
		}
		String ncText = nextSibling.getText();
		for (String strIdentName : ignoreIdentifierList) {
			if (fcText.equals(strIdentName)) {
				lastDotAST.setFirstChild(null);
				lastDotAST.setType(JScriptTokenTypes.IDENT);
				lastDotAST.setText(ncText);
				break;
			}
		}
	}

	static void translateDotAST(AST dotAST,
			Map<String, String> oldNewDataNameMap,
			List<String> destinationList, List<String> supportedContexts,String scriptType) {
		if (oldNewDataNameMap == null || oldNewDataNameMap.isEmpty()) {
			return;
		}	
		AST lastDotAST = getLastDotAST(dotAST);
		AST firstChild = lastDotAST.getFirstChild();		
		translateIdentAST(firstChild, oldNewDataNameMap, destinationList, supportedContexts,scriptType);
	}
	
	static void translateIdentAST(AST firstChild,Map<String, String> oldNewDataNameMap,
			List<String> destinationList, List<String> supportedContexts,String scriptType){
		String fcText = firstChild.getText();
		boolean supportedClass = isSupportedClass(fcText, destinationList,scriptType);
		boolean supportedContext = isSupportedContext(fcText, supportedContexts);
		if (!supportedClass && supportedContext) {
			AST nextSibling = firstChild.getNextSibling();
			String nsText = nextSibling.getText();
			String strNewName = oldNewDataNameMap.get(nsText);
			if (strNewName != null) {
				nextSibling.setText(strNewName);
			}
		}
	}

	/**
	 * Returns true if the passed identifier is a supported class.
	 * 
	 * @param identName
	 * @param destinationList
	 * @return
	 */
	static boolean isSupportedClass(String identName,
			List<String> destinationList,String scriptType) {
		boolean toReturn = false;
		if (destinationList == null || destinationList.isEmpty()) {
			toReturn = true;
			return toReturn;
		}
		List<String> supportedClasses = Utility
				.getSupportedClasses(destinationList,scriptType);
		for (String strClassName : supportedClasses) {
			if (strClassName.equals(identName)) {
				toReturn = true;
				break;
			}
		}
		return toReturn;
	}

	static boolean isSupportedContext(String identName,
			List<String> supportedContextList) {
		boolean toReturn = false;
		if (supportedContextList == null || supportedContextList.isEmpty()) {
			// if there is no supported context, then we should change the
			// identifier.
			toReturn = true;
			return toReturn;
		}
		for (String strContextName : supportedContextList) {
			if (strContextName.equals(identName)) {
				toReturn = true;
				break;
			}
		}
		return toReturn;
	}
	
	static AST getLastDotAST(AST dotAST) {
		AST toReturn = dotAST;
		AST firstChild = dotAST.getFirstChild();
		while (firstChild != null
				&& firstChild.getType() == JScriptTokenTypes.DOT) {
			toReturn = firstChild;
			firstChild = firstChild.getFirstChild();
		}
		return toReturn;
	}

}
