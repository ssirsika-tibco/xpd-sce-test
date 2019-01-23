/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.ui.perspective;

import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * Adapter class for {@link IPerspectiveLifecycleListener} with empty
 * implementation of all event methods.
 * 
 * This class is intended to be overridden.
 * 
 * @author jarciuch
 * @since 18 Nov 2014
 */
public class PerspectiveLifecycleAdapter implements
        IPerspectiveLifecycleListener {

    /**
     * @see org.eclipse.ui.IPerspectiveListener4#perspectivePreDeactivate(org.eclipse.ui.IWorkbenchPage,
     *      org.eclipse.ui.IPerspectiveDescriptor)
     */
    @Override
    public void perspectivePreDeactivate(IWorkbenchPage page,
            IPerspectiveDescriptor perspective) {
    }

    /**
     * @see org.eclipse.ui.IPerspectiveListener3#perspectiveOpened(org.eclipse.ui.IWorkbenchPage,
     *      org.eclipse.ui.IPerspectiveDescriptor)
     */
    @Override
    public void perspectiveOpened(IWorkbenchPage page,
            IPerspectiveDescriptor perspective) {
    }

    /**
     * @see org.eclipse.ui.IPerspectiveListener3#perspectiveClosed(org.eclipse.ui.IWorkbenchPage,
     *      org.eclipse.ui.IPerspectiveDescriptor)
     */
    @Override
    public void perspectiveClosed(IWorkbenchPage page,
            IPerspectiveDescriptor perspective) {
    }

    /**
     * @see org.eclipse.ui.IPerspectiveListener3#perspectiveDeactivated(org.eclipse.ui.IWorkbenchPage,
     *      org.eclipse.ui.IPerspectiveDescriptor)
     */
    @Override
    public void perspectiveDeactivated(IWorkbenchPage page,
            IPerspectiveDescriptor perspective) {
    }

    /**
     * @see org.eclipse.ui.IPerspectiveListener3#perspectiveSavedAs(org.eclipse.ui.IWorkbenchPage,
     *      org.eclipse.ui.IPerspectiveDescriptor,
     *      org.eclipse.ui.IPerspectiveDescriptor)
     */
    @Override
    public void perspectiveSavedAs(IWorkbenchPage page,
            IPerspectiveDescriptor oldPerspective,
            IPerspectiveDescriptor newPerspective) {
    }

    /**
     * @see org.eclipse.ui.IPerspectiveListener2#perspectiveChanged(org.eclipse.ui.IWorkbenchPage,
     *      org.eclipse.ui.IPerspectiveDescriptor,
     *      org.eclipse.ui.IWorkbenchPartReference, java.lang.String)
     */
    @Override
    public void perspectiveChanged(IWorkbenchPage page,
            IPerspectiveDescriptor perspective,
            IWorkbenchPartReference partRef, String changeId) {
    }

    /**
     * @see org.eclipse.ui.IPerspectiveListener#perspectiveActivated(org.eclipse.ui.IWorkbenchPage,
     *      org.eclipse.ui.IPerspectiveDescriptor)
     */
    @Override
    public void perspectiveActivated(IWorkbenchPage page,
            IPerspectiveDescriptor perspective) {

    }

    /**
     * @see org.eclipse.ui.IPerspectiveListener#perspectiveChanged(org.eclipse.ui.IWorkbenchPage,
     *      org.eclipse.ui.IPerspectiveDescriptor, java.lang.String)
     */
    @Override
    public void perspectiveChanged(IWorkbenchPage page,
            IPerspectiveDescriptor perspective, String changeId) {

    }

    /**
     * @see com.tibco.xpd.ui.simplified.internal.IPerspectiveLifecycleListener#perspectiveInitialized(org.eclipse.ui.IWorkbenchWindow,
     *      org.eclipse.ui.IWorkbenchPage,
     *      org.eclipse.ui.IPerspectiveDescriptor)
     */
    @Override
    public void perspectiveInitialized(IWorkbenchWindow window,
            IWorkbenchPage page, IPerspectiveDescriptor perspective) {

    }

}
