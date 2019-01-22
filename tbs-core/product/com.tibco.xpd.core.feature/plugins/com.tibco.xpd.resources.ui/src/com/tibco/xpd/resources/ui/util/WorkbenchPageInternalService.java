/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.ui.util;

import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.internal.WorkbenchPage;

/**
 * This class is package internal and is NOT part of API.
 * <p>
 * <i>Created: 24 Mar 2009</i>
 * </p>
 * 
 * @deprecated FastViews were removed in eclipse 4.
 * @author Jan Arciuchiewicz
 */
@Deprecated
/* package */class WorkbenchPageInternalService {

    /**
     * Check whether the view is fast.
     * 
     * 
     * @param page
     *            workbench page
     * @param ref
     *            view part reference
     * @return <code>true</code> if the view is fast, <code>false</code>
     *         otherwise.
     * 
     * @deprecated FastViews were removed in eclipse 4.
     */
    @Deprecated
    @SuppressWarnings("restriction")
    /* package */static boolean isFastView(IWorkbenchPage page,
            IViewReference ref) {

        boolean isFastView = false;

        // if (isInstanceofWorkbenchPage(page) && ref != null) {
        // isFastView = ((WorkbenchPage) page).isFastView(ref);
        // }

        return isFastView;
    }

    /**
     * Add a fast view.
     * 
     * @param page
     *            workbench page
     * @param ref
     *            view part reference
     * @deprecated FastViews were removed in eclipse 4.
     */
    @Deprecated
    @SuppressWarnings("restriction")
    /* package */static void addFastView(IWorkbenchPage page, IViewReference ref) {
        // if (isInstanceofWorkbenchPage(page) && ref != null) {
        // ((WorkbenchPage) page).addFastView(ref);
        // }
    }

    /**
     * Remove a fast view.
     * 
     * @param page
     *            workbench page
     * @param ref
     *            view part reference
     * 
     * @deprecated FastViews were removed in eclipse 4.
     */
    @Deprecated
    @SuppressWarnings("restriction")
    /* package */static void removeFastView(IWorkbenchPage page,
            IViewReference ref) {
        // if (isInstanceofWorkbenchPage(page) && ref != null) {
        // ((WorkbenchPage)
        // page).getViewStack(ref.getView(false));removeFastView(ref);
        // }
    }

    /**
     * Toggles the visibility of a fast view. If the view is active it is
     * deactivated. Otherwise, it is activated.
     * 
     * @param page
     *            workbench page
     * @param ref
     *            view part reference
     * 
     * @deprecated FastViews were removed in eclipse 4.
     */
    @Deprecated
    @SuppressWarnings("restriction")
    /* package */static void toggleFastView(IWorkbenchPage page,
            IViewReference ref) {
        // if (isInstanceofWorkbenchPage(page) && ref != null) {
        // ((WorkbenchPage) page).toggleFastView(ref);
        // }
    }

    /**
     * Check if the given object is an instanceof of
     * org.eclipse.ui.internal.WorkbenchPage.
     * 
     * @param page
     *            workbench page
     * @return <code>true</code> if the given page is an instanceof
     *         WorkbenchPage, <code>false</code> otherwise.
     */
    @SuppressWarnings("restriction")
    /* package */static boolean isInstanceofWorkbenchPage(IWorkbenchPage page) {
        return page instanceof WorkbenchPage;
    }
}