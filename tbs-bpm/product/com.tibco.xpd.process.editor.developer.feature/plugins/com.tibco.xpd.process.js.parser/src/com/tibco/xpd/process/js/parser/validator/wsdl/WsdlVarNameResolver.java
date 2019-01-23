package com.tibco.xpd.process.js.parser.validator.wsdl;

import java.util.List;

import antlr.collections.AST;

import com.tibco.xpd.script.parser.Messages;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.validator.DefaultVarNameResolver;

/**
 * In the case of process.myIntDF, this class considers process.myIntDF as the
 * variable name in use.
 * 
 * <p>
 * <i>Created: 8 May 2007</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public class WsdlVarNameResolver extends DefaultVarNameResolver {

	@Override
	protected void getVariableChildAST(AST ast, List<String> varNameList,
			List<String> supportedClasses) {
		for (AST sibling = ast; sibling != null; sibling = sibling
				.getNextSibling()) {
			if (sibling.getType() == JScriptTokenTypes.METHOD_CALL) {
				String strVarName = resolveMethodCallAST(sibling,
						supportedClasses);
				if (strVarName != null && strVarName.length() > 0) {
					varNameList.add(strVarName);
				}
				continue;
			}
			if (sibling.getType() == JScriptTokenTypes.LITERAL_new) {
				continue;
			}
			if (sibling.getType() == JScriptTokenTypes.DOT) {
				String strVarName = resolveDotAST(sibling, supportedClasses);
				if (strVarName != null && strVarName.length() > 0) {
					varNameList.add(strVarName);
				}
				continue;
			}
			if (sibling.getType() == JScriptTokenTypes.IDENT) {
				String strVarName = sibling.getText();
				if (strVarName != null && strVarName.length() > 0) {
					varNameList.add(strVarName);
				}
			}
			if (sibling.getType() == JScriptTokenTypes.INDEX_OP) {
				// case is a[0][1]
				String strVarName = resolveIndexAST(sibling, supportedClasses);
				if (strVarName != null && strVarName.length() > 0) {
					varNameList.add(strVarName);
				}
				continue;
			}
			AST firstChild = sibling.getFirstChild();
			if (firstChild != null) {
				getVariableChildAST(firstChild, varNameList, supportedClasses);
			}
		}
	}

	private String resolveDotAST(AST dotAST, List<String> supportedClasses) {
		String toReturn = ""; //$NON-NLS-1$
		String fcText = ""; //$NON-NLS-1$
		AST firstChild = dotAST.getFirstChild();
		if (firstChild.getType() == JScriptTokenTypes.DOT) {
			toReturn = resolveDotAST(firstChild, supportedClasses);
		} else if (firstChild.getType() == JScriptTokenTypes.IDENT) {
			fcText = firstChild.getText();
			// check if it is a supported class name
			boolean b = isSupportedClass(fcText, supportedClasses);
			if (b) {
				return toReturn;
			}
		} else if (firstChild.getType() == JScriptTokenTypes.INDEX_OP) {
			toReturn = resolveIndexAST(firstChild, supportedClasses);
		} else {
			throw new IllegalStateException(
					Messages.WsdlVarNameResolver_UnsupportedASTType
							+ firstChild.toString());
		}
		AST nextSibling = firstChild.getNextSibling();
		String nsText = nextSibling.getText();
		toReturn += fcText + "." + nsText; //$NON-NLS-1$
		return toReturn;
	}

	private String resolveIndexAST(AST indexAST, List<String> supportedClasses) {
		String toReturn = ""; //$NON-NLS-1$
		String fcText = ""; //$NON-NLS-1$
		AST firstChild = indexAST.getFirstChild();
		if (firstChild.getType() == JScriptTokenTypes.INDEX_OP) {
			toReturn = resolveIndexAST(firstChild, supportedClasses);
		} else if (firstChild.getType() == JScriptTokenTypes.IDENT) {
			fcText = firstChild.getText();
			// check if it is a supported class name
			boolean b = isSupportedClass(fcText, supportedClasses);
			if (b) {
				return toReturn;
			}
		} else if (firstChild.getType() == JScriptTokenTypes.DOT) {
			toReturn = resolveDotAST(firstChild, supportedClasses);
		} else {
			throw new IllegalStateException(
					Messages.WsdlVarNameResolver_UnsupportedASTType
							+ firstChild.toString());
		}
		AST nextSibling = firstChild.getNextSibling();
		String fChildtext = ""; //$NON-NLS-1$
		if (nextSibling != null
				&& nextSibling.getType() == JScriptTokenTypes.EXPR) {
			AST fChildAST = nextSibling.getFirstChild();
			if (fChildAST != null
					&& fChildAST.getType() == JScriptTokenTypes.IDENT) {
				fChildtext = fChildAST.getText();
				// case: a[b], we need just to return the 'a' as 'b' will be
				// already recorded.
				toReturn += fcText;
			} else {
				fChildtext = fChildAST.getText();
				toReturn += fcText + "[" + fChildtext + "]"; //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		return toReturn;
	}

	private String resolveMethodCallAST(AST methodAST,
			List<String> supportedClasses) {
		AST dotAST = methodAST.getFirstChild();
		return resolveMethodCallDotAST(dotAST, supportedClasses);
	}

	private String resolveMethodCallDotAST(AST dotAST,
			List<String> supportedClasses) {
		String toReturn = ""; //$NON-NLS-1$
		AST firstChild = dotAST.getFirstChild();
		if (firstChild != null
				&& firstChild.getType() == JScriptTokenTypes.IDENT) {
			boolean b = isSupportedClass(firstChild.getText(), supportedClasses);
			if (b) {
				return toReturn;
			} else {
				// NOTE::avoiding cases like xxxx.toString(a.b,c.d);, we would
				// not like to return xxxx.toString as the identifier name
				// AST nextSibling = firstChild.getNextSibling();
				// toReturn += firstChild.getText() + "." +
				// nextSibling.getText();
				toReturn = firstChild.getText();
				return toReturn;
			}
		} else if (firstChild != null
				&& firstChild.getType() == JScriptTokenTypes.DOT) {
			toReturn += resolveDotAST(firstChild, supportedClasses);
		} else if (firstChild != null
				&& firstChild.getType() == JScriptTokenTypes.INDEX_OP) {
			toReturn += resolveIndexAST(firstChild, supportedClasses);
		}
		return toReturn;
	}

}
