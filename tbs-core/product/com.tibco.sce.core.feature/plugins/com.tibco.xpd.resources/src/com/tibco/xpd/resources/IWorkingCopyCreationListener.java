/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources;

import java.util.EventListener;

/**
 * This listener will be notified when a working copy is created. Use
 * {@link XpdResourcesPlugin#addWorkingCopyCreationListener(IWorkingCopyCreationListener, boolean)
 * XpdResourcesPlugin.addWorkingCopyCreationListener} to add this listener.
 * 
 * @author njpatel
 * 
 */
public interface IWorkingCopyCreationListener extends EventListener {

    /**
     * The given <code>WorkingCopy</code> has been created.
     * 
     * @param wc
     */
    public void workingCopyCreated(WorkingCopy wc);
}
