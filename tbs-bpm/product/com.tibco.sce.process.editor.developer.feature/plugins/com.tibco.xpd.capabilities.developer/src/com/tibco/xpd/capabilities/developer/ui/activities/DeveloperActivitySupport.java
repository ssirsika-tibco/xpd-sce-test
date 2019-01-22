/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.capabilities.developer.ui.activities;

import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.ActivityManagerEvent;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.IActivityManagerListener;
import org.eclipse.ui.activities.IWorkbenchActivitySupport;
import org.eclipse.ui.views.properties.PropertySheet;

import com.tibco.xpd.capabilities.developer.DeveloperCapabilitiesConstants;
import com.tibco.xpd.ui.projectexplorer.NavigatorUtil;

/**
 * Activity support.
 * <p>
 * <i>Created: 15 Mar 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class DeveloperActivitySupport implements IActivityManagerListener {

    private static final String PROPERTY_SHEET_VIEW_ID =
            "org.eclipse.ui.views.PropertySheet"; //$NON-NLS-1$

    /** Deployment common navigator content id. */
    private static final String DEPLOYMENT_CONTENT =
            "com.tibco.xpd.deploy.navigatorContent"; //$NON-NLS-1$

    /** Deployment view id. */
    private static final String DEPLOYMENT_VIEW_ID =
            "com.tibco.xpd.deploy.server.view"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.activities.IActivityManagerListener#activityManagerChanged
     * (org.eclipse.ui.activities.ActivityManagerEvent)
     */
    @Override
    public void activityManagerChanged(ActivityManagerEvent activityManagerEvent) {
        if (activityManagerEvent.haveEnabledActivityIdsChanged()) {
            updateDeploymentPresentation();
        }

    }

    /**
     * Updates deployment presentation.
     */
    @SuppressWarnings("unchecked")
    private void updateDeploymentPresentation() {
        IWorkbench workbench = PlatformUI.getWorkbench();
        IWorkbenchActivitySupport activitySupport =
                workbench.getActivitySupport();
        IActivityManager activityManager = activitySupport.getActivityManager();
        Set<String> enabledActivityIds =
                activityManager.getEnabledActivityIds();
        String processInternalCapability = //
                DeveloperCapabilitiesConstants.DEVELOPER_ACTIVITY_ID;

        if (enabledActivityIds.contains(processInternalCapability)) {
            NavigatorUtil
                    .switchProjectExplorerContent(true, DEPLOYMENT_CONTENT);
        } else {
            NavigatorUtil.switchProjectExplorerContent(false,
                    DEPLOYMENT_CONTENT);
        }

        // refreshing views
        IViewPart propertiesView = getActiveView(PROPERTY_SHEET_VIEW_ID);
        if (propertiesView instanceof PropertySheet) {
            PropertySheet propertySheet = (PropertySheet) propertiesView;
            IWorkbenchWindow activeWorkbenchWindow =
                    workbench.getActiveWorkbenchWindow();
            ISelectionService selectionService =
                    activeWorkbenchWindow.getSelectionService();
            ISelection selection = selectionService.getSelection();
            if (selection != null) {
                if (activeWorkbenchWindow.getActivePage() != null
                        && activeWorkbenchWindow.getActivePage()
                                .getActivePart() != null) {
                    IWorkbenchPart activePart =
                            activeWorkbenchWindow.getActivePage()
                                    .getActivePart();
                    propertySheet.selectionChanged(activePart,
                            StructuredSelection.EMPTY);
                    propertySheet.selectionChanged(activePart, selection);
                }
            }
        }

        // showing/hiding views
        IViewPart deploymentView = getActiveView(DEPLOYMENT_VIEW_ID);
        if (deploymentView != null) {
            // Hide if developer capability disabled
            if (!DeveloperActivityUtil.isDeveloperActivityEnabled()) {
                if (workbench.getActiveWorkbenchWindow() != null
                        && workbench.getActiveWorkbenchWindow().getActivePage() != null) {

                    workbench.getActiveWorkbenchWindow().getActivePage()
                            .hideView(deploymentView);
                }
            }
        } else {
            // Show if developer capability enabled
            if (DeveloperActivityUtil.isDeveloperActivityEnabled()) {
                try {
                    if (workbench.getActiveWorkbenchWindow() != null
                            && workbench.getActiveWorkbenchWindow()
                                    .getActivePage() != null) {
                        workbench
                                .getActiveWorkbenchWindow()
                                .getActivePage()
                                .showView(DEPLOYMENT_VIEW_ID,
                                        null,
                                        IWorkbenchPage.VIEW_VISIBLE);
                    }
                } catch (PartInitException e) {
                    // ignore
                }
            }
        }
    }

    /**
     * Returns view part if the part if it is active or null otherwise. If the
     * view part is not instantiated it will not be restored.
     * 
     * @param viewId
     *            view identifier.
     * @return view part if the part if it is active or null otherwise.
     */
    private IViewPart getActiveView(String viewId) {
        Assert.isLegal(viewId != null, "Passed incorrect viewId: " + viewId); //$NON-NLS-1$
        IWorkbenchPage activePage;
        if (PlatformUI.getWorkbench() != null
                && PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null
                && PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage() != null) {
            activePage =
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage();
            for (IViewReference viewRef : activePage.getViewReferences()) {
                if (viewId.equals(viewRef.getId())) {
                    return viewRef.getView(false);
                }
            }
        }
        return null;
    }

}
