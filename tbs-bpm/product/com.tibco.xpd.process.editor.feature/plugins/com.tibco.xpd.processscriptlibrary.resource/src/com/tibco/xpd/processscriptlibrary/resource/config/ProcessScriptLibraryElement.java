/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.config;

/**
 * Base Interface which represents Process Script library element.
 * 
 * @author ssirsika
 * @since 26-Feb-2024
 */
public interface ProcessScriptLibraryElement
{
	/**
	 * Accepts {@link ProcessScriptLibraryVisitor} with aContext object
	 * 
	 * @param aVisitor
	 *            Visitor
	 * @param aContext
	 *            Context Object
	 */
	public void accept(ProcessScriptLibraryVisitor aVisitor, Object aContext);

	/**
	 * @return {@link String} name of the element.
	 */
	public String getName();
}
