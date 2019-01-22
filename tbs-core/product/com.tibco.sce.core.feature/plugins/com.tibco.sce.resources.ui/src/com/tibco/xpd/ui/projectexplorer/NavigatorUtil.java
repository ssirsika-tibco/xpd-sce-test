/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.INavigatorActivationService;
import org.eclipse.ui.navigator.INavigatorContentService;
import org.eclipse.ui.navigator.NavigatorContentServiceFactory;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;

/**
 * Common navigator utility methods. This utility contains utility methods for
 * programmatically performing some operations or queries on the navigator
 * instances and/or ProjectExplorer in particular.
 * 
 * <p/> <i>Created: 16 Mar 2007</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class NavigatorUtil {

    /** Project Explorer view ID. */
    public static final String PROJECT_EXPLORER_VIEW_ID = //
    "org.eclipse.ui.navigator.ProjectExplorer"; //$NON-NLS-1$

    /**
     * Switches on or off specified list of content extensions for the common
     * Project Explorer. The result will be persisted and if the view is not
     * visible the effect will be applied next time the view will be activated.
     * 
     * @param switchToOn
     *            if specified extension contents should be switched on (true)
     *            or off (false).
     * @param contentIds
     *            navigator content extensions' identifiers.
     */
    public static void switchProjectExplorerContent(boolean switchToOn,
            String... contentIds) {
        switchNavigatorContent(PROJECT_EXPLORER_VIEW_ID, switchToOn, true,
                contentIds);
    }

    /**
     * Set the project explorer selection.
     * 
     * @param selection
     * @return
     */
    public static boolean setProjectExplorerSelection(ISelection selection) {
        boolean ret = false;

        IWorkbenchPart part = null;

        IWorkbench workbench = PlatformUI.getWorkbench();
        if (workbench != null) {

            IWorkbenchWindow activeWorkbenchWindow = workbench
                    .getActiveWorkbenchWindow();
            if (activeWorkbenchWindow != null) {
                IWorkbenchPage page = activeWorkbenchWindow.getActivePage();

                if (page != null) {
                    IViewReference[] views = page.getViewReferences();
                    for (int i = 0; i < views.length; i++) {
                        if (PROJECT_EXPLORER_VIEW_ID.equals(views[i].getId())) {
                            part = views[i].getPart(true);
                        }
                    }

                    if (part == null) {
                        try {
                            IViewPart p = page
                                    .showView(PROJECT_EXPLORER_VIEW_ID);
                            part = p;

                        } catch (PartInitException e) {
                            Logger logger = XpdResourcesPlugin.getDefault()
                                    .getLogger();
                            logger.error(e,
                                    "Can't load project explorer view: " //$NON-NLS-1$
                                            + PROJECT_EXPLORER_VIEW_ID);
                        }
                    }

                    if (part != null) {
                        if (part instanceof CommonNavigator) {
                            CommonNavigator nav = (CommonNavigator) part;
                            page.activate(part);
                            nav.selectReveal(selection);

                        }
                    }
                }
            }
        }
        return ret;
    }

    /*
     * This method is specific to ProjecExplorer view. It could be later
     * refactored to be more general if possible.
     */
    private static void switchNavigatorContent(String viewerId,
            boolean switchToOn, boolean persist, String... contentIds) {
        INavigatorActivationService activationService = null;
        IViewPart part = null;
        if (PlatformUI.getWorkbench() != null
                && PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null
                && PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage() != null) {
            part = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                    .getActivePage().findView(viewerId);
        }
        if (part instanceof CommonNavigator) {
            CommonNavigator navigator = (CommonNavigator) part;
            INavigatorContentService service = navigator
                    .getNavigatorContentService();
            activationService = service.getActivationService();
        } else if (persist) {
            NavigatorContentServiceFactory fact = NavigatorContentServiceFactory.INSTANCE;
            INavigatorContentService navigatorService = fact
                    .createContentService(viewerId);
            if (navigatorService != null) {
                activationService = navigatorService.getActivationService();
            }
        }
        if (activationService != null) {
            if (switchToOn) {
                activationService.activateExtensions(contentIds, false);
            } else {
                activationService.deactivateExtensions(contentIds, false);
            }

            if (persist) {
                activationService.persistExtensionActivations();
            }
        }
    }

    /**
     * The private constructor to prevent instantiation.
     */
    private NavigatorUtil() {
    }

}
