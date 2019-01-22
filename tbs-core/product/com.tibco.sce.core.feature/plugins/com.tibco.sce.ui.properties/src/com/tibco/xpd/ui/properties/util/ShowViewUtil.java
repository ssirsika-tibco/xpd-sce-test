/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.ui.properties.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.ui.properties.internal.util.XpdPropertiesUtil;

/**
 * @author aallway
 * 
 * @deprecated This class was moved to <b>com.tibco.xpd.resources.ui</b>
 *             plug-in. Please use
 *             {@link com.tibco.xpd.resources.ui.util.ShowViewUtil} class
 *             instead.
 * @see com.tibco.xpd.resources.ui.util.ShowViewUtil
 */
@Deprecated
public class ShowViewUtil {

    private final static String PROPERTIES_VIEW_ID = "org.eclipse.ui.views.PropertySheet"; //$NON-NLS-1$

    private final static int INITSTATE_ATTACHED = 1;

    private final static int INITSTATE_DETACHED = 2;

    private final static int INITSTATE_FASTVIEW = 3;

    /**
     * Show the given view according to its and the current editors state.
     * 
     * If the editor is maximised and the props view is not detached or not
     * visible then it is set to a fast view and toggled up (else just show it).
     * The view is then returned to its original state when it is deactivated
     * (user clicks outside of properties view or closes it)
     * 
     * @param prefLocation
     *            Preferred location or null for current cursor location (the
     *            window will not be allowed to overrun screen size).
     * @param prefSize
     *            Preferred size or null for whatever the last selected size was
     *            (probably size of attached view)
     */
    public static void showOrDetachPropertiesView(Point prefLocation,
            Point prefSize) {

        showOrDetachView(PROPERTIES_VIEW_ID, prefLocation, prefSize);
    }

    /**
     * Show the properties view according to its and the current editors state.
     * 
     * If the editor is maximised and the view is not detached or not visible
     * then it is set to a fast view and toggled up (else just show it). The
     * view is then returned to its original state when it is deactivated (user
     * clicks outside of properties view or closes it)
     * 
     * @param viewId
     * @param prefLocation
     *            Preferred location or null for current cursor location (the
     *            window will not be allowed to overrun screen size).
     * @param prefSize
     *            Preferred size or null for whatever the last selected size was
     *            (probably size of attached view)
     */
    public static void showOrDetachView(String viewId, Point prefLocation,
            Point prefSize) {

        IWorkbenchWindow window = PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow();
        IWorkbenchPage page = window.getActivePage();
        try {
            IViewReference viewRef = page.findViewReference(viewId);

            boolean viewCreatedHere = false;

            //
            // Create (but don't show yet), the properties view if it
            // doesn't exist.
            if (viewRef == null) {
                page.showView(viewId, null, IWorkbenchPage.VIEW_CREATE);
                viewRef = page.findViewReference(viewId);

                viewCreatedHere = true;
            }

            if (viewRef != null) {
                // The actual view should exist now.
                IViewPart view = page.findView(viewId);
                if (view != null) {
                    int originalState = getInitState(page, viewRef, view);

                    // If the initial state was fast view anyway then bring it
                    // up.
                    if (originalState == INITSTATE_FASTVIEW) {
                        XpdPropertiesUtil.toggleFastView(page, viewRef);
                        return;
                    }

                    //
                    // If the editor is maximised then we detach the view
                    // (if it isn't already), show it and add a listener to
                    // return to original state when it's deactivated.
                    boolean detach = false;
                    boolean restoreOnDeactivate = false;

                    if (page.getPartState(page.getActivePartReference()) == IWorkbenchPage.STATE_MAXIMIZED) {
                        // If we are maximised then always detach unless
                        // view is already detached.
                        if (originalState != INITSTATE_DETACHED) {
                            detach = true;
                            restoreOnDeactivate = true;
                        } else {
                            // If maximised and was detached, then only
                            // restore to prev state on deactivate if we
                            // created it here.
                            if (viewCreatedHere) {
                                restoreOnDeactivate = true;
                            }
                        }

                    } else {
                        // For non-maximised editor only restore the state
                        // if we created here AND it was attached (i.e. if we
                        // created the view as attached then leave it visible
                        // afterwards).
                        if (viewCreatedHere
                                && originalState == INITSTATE_DETACHED) {
                            restoreOnDeactivate = true;
                        }
                    }

                    if (restoreOnDeactivate) {
                        // If we need to restore view on deactivate/close add a
                        // listener to the view part.
                        if (prefLocation == null) {
                            prefLocation = Display.getCurrent()
                                    .getCursorLocation();
                        }

                        page.addPartListener(new DetachedPropsViewPartListener(
                                page, viewRef, view, viewCreatedHere,
                                originalState, prefLocation, prefSize));
                    }

                    if (detach) {
                        // Instead of detaching, try setting it as a fast view
                        // and popping it up.
                        XpdPropertiesUtil.addFastView(page, viewRef);
                        XpdPropertiesUtil.toggleFastView(page, viewRef);

                    } else {
                        // Was already in detached mode, just show it.
                        page.showView(viewId);

                    }

                }
            }
        } catch (PartInitException e) {
            // ignore
        }

    }

    /**
     * @param viewRef
     * @param view
     * @return INITSTATE_xxx
     */
    private static int getInitState(IWorkbenchPage page,
            IViewReference viewRef, IViewPart view) {
        if (XpdPropertiesUtil.isInstanceofWorkbenchPage(page)) {
            if (XpdPropertiesUtil.isFastView(page, viewRef)) {
                return INITSTATE_FASTVIEW;
            }
        }

        if (isDetachedView(view)) {
            return INITSTATE_DETACHED;
        }
        return INITSTATE_ATTACHED;
    }

    /**
     * Although WorkbenchPage has an attachView and detachView it does not have
     * a 'view is currently detached'.
     * 
     * So we have to use other means... Attached property view has a minimize
     * button, detached view doesn't. So we will use this to distinguish.
     * 
     * @param view
     * @return
     */
    private static boolean isDetachedView(IViewPart view) {
        Shell shell = view.getViewSite().getShell();
        if (shell != null) {
            if ((shell.getStyle() & SWT.MIN) != SWT.MIN) {
                return true;
            }
        }
        return false;
    }

    private static class DetachedPropsViewPartListener implements
            IPartListener2 {
        private final IWorkbenchPage workbenchPage;

        private final IViewReference viewRef;

        private final boolean viewCreatedHere;

        private final int originalState;

        DetachedPropsViewPartListener(IWorkbenchPage workbenchPage,
                IViewReference viewRef, IViewPart viewPart,
                boolean viewCreatedHere, int originalState, Point location,
                Point prefSize) {
            this.workbenchPage = workbenchPage;
            this.viewRef = viewRef;
            this.viewCreatedHere = viewCreatedHere;
            this.originalState = originalState;
        }

        /*
         * (non-Javadoc)
         * 
         * @seeorg.eclipse.ui.IPartListener2#partActivated(org.eclipse.ui.
         * IWorkbenchPartReference)
         */
        public void partActivated(IWorkbenchPartReference partRef) {
        }

        /*
         * (non-Javadoc)
         * 
         * @seeorg.eclipse.ui.IPartListener2#partBroughtToTop(org.eclipse.ui.
         * IWorkbenchPartReference)
         */
        public void partBroughtToTop(IWorkbenchPartReference partRef) {
        }

        /*
         * (non-Javadoc)
         * 
         * @seeorg.eclipse.ui.IPartListener2#partClosed(org.eclipse.ui.
         * IWorkbenchPartReference)
         */
        public void partClosed(IWorkbenchPartReference partRef) {
        }

        /*
         * (non-Javadoc)
         * 
         * @seeorg.eclipse.ui.IPartListener2#partDeactivated(org.eclipse.ui.
         * IWorkbenchPartReference)
         */
        public void partDeactivated(IWorkbenchPartReference partRef) {
            if (partRef == viewRef) {
                // Return to previous state.
                if (XpdPropertiesUtil.isInstanceofWorkbenchPage(workbenchPage)) {
                    if (originalState == INITSTATE_ATTACHED) {

                        // ((WorkbenchPage) workbenchPage).attachView(viewRef);

                        // If originally attached, then unset fast view
                        if (XpdPropertiesUtil
                                .isFastView(workbenchPage, viewRef)) {
                            XpdPropertiesUtil.removeFastView(workbenchPage,
                                    viewRef);
                        }
                    }
                }

                if (viewCreatedHere) {
                    // The view was created by us, so hide it again.
                    // If we created the view on show props then allow it to
                    // close (i.e. don't lisen for close then recreate).

                    workbenchPage.hideView(viewRef);

                }
                // Once we are done, remove ourselves.
                workbenchPage.removePartListener(this);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @seeorg.eclipse.ui.IPartListener2#partHidden(org.eclipse.ui.
         * IWorkbenchPartReference)
         */
        public void partHidden(IWorkbenchPartReference partRef) {
        }

        /*
         * (non-Javadoc)
         * 
         * @seeorg.eclipse.ui.IPartListener2#partInputChanged(org.eclipse.ui.
         * IWorkbenchPartReference)
         */
        public void partInputChanged(IWorkbenchPartReference partRef) {
        }

        /*
         * (non-Javadoc)
         * 
         * @seeorg.eclipse.ui.IPartListener2#partOpened(org.eclipse.ui.
         * IWorkbenchPartReference)
         */
        public void partOpened(IWorkbenchPartReference partRef) {
        }

        /*
         * (non-Javadoc)
         * 
         * @seeorg.eclipse.ui.IPartListener2#partVisible(org.eclipse.ui.
         * IWorkbenchPartReference)
         */
        public void partVisible(IWorkbenchPartReference partRef) {
        }
    }

}
