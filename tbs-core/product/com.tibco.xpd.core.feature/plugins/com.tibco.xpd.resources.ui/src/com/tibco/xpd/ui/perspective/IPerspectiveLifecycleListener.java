/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.ui.perspective;

import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveListener4;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * A perspective life cycle listener.
 * 
 * Note: {@link PerspectiveLifecycleAdapter} can be used for convenience.
 * 
 * @see PerspectiveLifecycleAdapter
 * 
 * @author jarciuch
 * @since 18 Nov 2014
 */
public interface IPerspectiveLifecycleListener extends IPerspectiveListener4 {

    /**
     * Called when the perspective is shown in a newly created workbench window.
     * 
     * @param window
     *            current workbench window.
     * @param page
     *            active page.
     * @param perspective
     *            current perspective descriptor.
     */
    public void perspectiveInitialized(IWorkbenchWindow window,
            IWorkbenchPage page, IPerspectiveDescriptor perspective);
}
