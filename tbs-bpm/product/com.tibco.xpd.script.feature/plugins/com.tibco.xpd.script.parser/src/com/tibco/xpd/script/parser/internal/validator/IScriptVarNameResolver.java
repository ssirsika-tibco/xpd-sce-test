/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator;

import java.util.List;

import com.tibco.xpd.script.parser.internal.expr.IExpr;

public interface IScriptVarNameResolver {
	/**
	 * Returns a list of variable names which are used in the passed expression.
	 * 
	 * @param expr
	 * @param supportedClasses
	 * @return
	 */
	List<String> getVariableNameList(IExpr expr, List<String> supportedClasses);
}
