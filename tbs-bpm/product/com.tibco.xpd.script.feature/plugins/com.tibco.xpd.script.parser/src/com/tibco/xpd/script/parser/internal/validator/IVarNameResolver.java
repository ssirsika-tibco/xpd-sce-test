/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.validator;

import java.util.List;
import antlr.collections.AST;
/**
 * 
 * @author mtorres
 *
 */
public interface IVarNameResolver {
	/**
	 * Returns a list of variable names which are used in the passed AST.
	 * 
	 * @param topAST
	 * @param supportedClasses
	 * @return
	 */
	List<String> getVariableNameList(AST topAST, List<String> supportedClasses);
}
