/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.internal;

import org.eclipse.osgi.util.NLS;

/**
 * @author glewis
 */

public class Messages extends NLS{
	
	public final static String BUNDLE_NAME = "com.tibco.xpd.internal.messages"; //$NON-NLS-1$
	
    public static String PublicationStatusType_Under_Revision_UI_Text;
	
	public static String PublicationStatusType_Released_UI_Text;
	
	public static String PublicationStatusType_Under_Test_UI_Text;
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
	
	private Messages(){
	}
}
