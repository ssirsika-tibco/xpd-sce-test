/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal;

import org.eclipse.ui.IStartup;

/**
 * <code>IStartup</code> implementation of the fragments plugin. This will start
 * the fragments initialization.
 * 
 * @author njpatel
 * 
 */
public class FragmentStartupClass implements IStartup {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IStartup#earlyStartup()
	 */
	public void earlyStartup() {
		FragmentsManager.getInstance().initialize();
	}

}
