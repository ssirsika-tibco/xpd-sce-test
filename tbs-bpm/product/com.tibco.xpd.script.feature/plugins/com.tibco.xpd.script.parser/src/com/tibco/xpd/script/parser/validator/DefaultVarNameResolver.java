/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.script.parser.validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import antlr.collections.AST;

import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
import com.tibco.xpd.script.parser.internal.validator.IScriptVarNameResolver;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolverExt;
import com.tibco.xpd.script.parser.internal.validator.VariableInfo;
/**
 * In the case of process.myIntDF, this class considers 
 * process as the variable name in use.
 * 
 * <p>
 * <i>Created: 8 May 2007</i>
 * </p>
 * @author Kamlesh Upadhyaya
 *
 */
public class DefaultVarNameResolver implements IVarNameResolver, IScriptVarNameResolver, IVarNameResolverExt {

	public List<String> getVariableNameList(AST topAST,
			List<String> supportedClasses) {
		List<String> varNameList = new ArrayList<String>();
		getVariableChildAST(topAST, varNameList, supportedClasses);
		return varNameList;
	}

	protected void getVariableChildAST(AST ast, List<String> varNameList,
			List<String> supportedClasses) {
		for (AST sibling = ast; sibling != null; sibling = sibling
				.getNextSibling()) {
			if (sibling.getType() == JScriptTokenTypes.METHOD_CALL) {
			    getDotVariableName(sibling, varNameList, supportedClasses);
				continue;
			}
			if (sibling.getType() == JScriptTokenTypes.LITERAL_new) {
				continue;
			}
			if (sibling.getType() == JScriptTokenTypes.DOT) {
			    getDotVariableName(sibling, varNameList, supportedClasses);
				continue;
			}
			if (sibling.getType() == JScriptTokenTypes.IDENT) {
				varNameList.add(sibling.getText());
			}			
			AST firstChild = sibling.getFirstChild();
			if (firstChild != null) {
				getVariableChildAST(firstChild, varNameList, supportedClasses);
			}
		}
	}
	
	protected void getDotVariableName(AST dotAst, List<String> varNameList,
            List<String> supportedClasses) {
        if (dotAst != null
                && dotAst.getFirstChild() != null
                && (dotAst.getFirstChild().getType() == JScriptTokenTypes.DOT || dotAst
                        .getFirstChild().getType() == JScriptTokenTypes.IDENT)) {
            AST child = dotAst.getFirstChild();
            while (child.getFirstChild() != null) {
                child = child.getFirstChild();
            }
            if (child != null && child.getType() == JScriptTokenTypes.IDENT) {
                AST nextSibling = child.getNextSibling();
                if (nextSibling != null
                        && nextSibling.getType() == JScriptTokenTypes.ELIST) {
                    return;
                }
                String variableName = child.getText();
                if (variableName != null
                        && !supportedClasses.contains(variableName)) {
                    varNameList.add(variableName);
                }
            }
        }
    }

	/**
	 * This method will return true/false depending upon whether the passes
	 * string is a class name.
	 * 
	 * @param strName
	 * @return
	 */
	protected boolean isSupportedClass(String strName,
			List<String> supportedClasses) {
		return supportedClasses.contains(strName);		
	}

    public List<String> getVariableNameList(IExpr expr,
            List<String> supportedClasses) {
       if(expr != null && expr.getExpr() instanceof AST){
           AST topAST = (AST) expr.getExpr();
           return getVariableNameList(topAST, supportedClasses);
       }
       return Collections.emptyList();
    }

    @Override
    public List<VariableInfo> getVariableInfoList(AST topAST,
            List<String> supportedClasses) {
        List<VariableInfo> variableInfoList = new ArrayList<VariableInfo>();
        getVariableInfoChildAST(topAST, variableInfoList, supportedClasses);
        return variableInfoList;
    }
    
    protected void getVariableInfoChildAST(AST ast, List<VariableInfo> variableInfoList,
            List<String> supportedClasses) {
        for (AST sibling = ast; sibling != null; sibling =
                sibling.getNextSibling()) {
            if (sibling.getType() == JScriptTokenTypes.IDENT) {
                variableInfoList.add(new VariableInfo(sibling.getText(),
                        sibling.getLine(), sibling.getColumn()));
            }
            AST firstChild = sibling.getFirstChild();
            if (firstChild != null) {
                getVariableInfoChildAST(firstChild,
                        variableInfoList,
                        supportedClasses);
            }
        }
    }

}
