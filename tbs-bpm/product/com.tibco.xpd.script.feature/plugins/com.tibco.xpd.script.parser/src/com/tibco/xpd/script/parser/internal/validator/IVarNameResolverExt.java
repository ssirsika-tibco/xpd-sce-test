/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator;

import java.util.List;
import antlr.collections.AST;

/**
 * Extension of IVarNameResolver, This extension will return the information of the variables
 * 
 * used
 * 
 * 
 * @author mtorres
 *
 */
public interface IVarNameResolverExt {
	/**
	 * Returns a list of variable names which are used in the passed AST.
	 * 
	 * @param topAST
	 * @param supportedClasses
	 * @return
	 */
	List<VariableInfo> getVariableInfoList(AST topAST, List<String> supportedClasses);
}
