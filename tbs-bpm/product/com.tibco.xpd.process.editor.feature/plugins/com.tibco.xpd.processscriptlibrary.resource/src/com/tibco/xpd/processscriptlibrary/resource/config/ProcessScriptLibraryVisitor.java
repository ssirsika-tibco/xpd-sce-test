/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.config;

/**
 * Interface represents visitor design pattern to visit {@link ProcessScriptLibraryElement} hierarchy
 * 
 * @author ssirsika
 * @since 26-Feb-2024
 */
public interface ProcessScriptLibraryVisitor
{
	/**
	 * Visit ProcessScriptLibraryProject
	 * 
	 * @param alibraryProject
	 *            {@link ProcessScriptLibraryProject}
	 * @param aContext
	 *            Context Object
	 * @return resulted object
	 */
	Object visit(ProcessScriptLibraryProject alibraryProject, Object aContext);

	/**
	 * Visit ProcessScriptLibrary
	 * 
	 * @param aLibrary
	 *            {@link ProcessScriptLibrary}
	 * @param aContext
	 *            Context Object
	 * @return resulted object
	 */
	Object visit(ProcessScriptLibrary aLibrary, Object aContext);

	/**
	 * Visit ProcessScriptFunction
	 * 
	 * @param aFunction
	 *            {@link ProcessScriptFunction}
	 * @param aContext
	 *            Context Object
	 * @return resulted object
	 */
	Object visit(ProcessScriptFunction aFunction, Object aContext);

	/**
	 * Visit ProcessScriptFunctionParam
	 * 
	 * @param aParameter
	 *            {@link ProcessScriptFunctionParam}
	 * @param aContext
	 *            Context Object
	 * @return resulted object
	 */
	Object visit(ProcessScriptFunctionParam aParameter, Object aContext);
}
