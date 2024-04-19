/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.processscriptlibrary.resource.util;

/**
 * ACE-7546: Generic exception to thrown if retrieving 'Process Script Library' references failed.
 *
 * @author ssirsika
 * @since 28-Dec-2023
 */
public class ProcessScriptLibraryReferenceException extends Exception
{

	private static final long serialVersionUID = 7887072060161634010L;

	/**
	 * @param aMessage
	 */
	public ProcessScriptLibraryReferenceException(String aMessage)
	{
		super(aMessage);
	}


}
