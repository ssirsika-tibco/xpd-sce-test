/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.capabilities.developer.ui.actions;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IActionDelegate2;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.ActivityManagerEvent;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.IActivityManagerListener;
import org.eclipse.ui.activities.IWorkbenchActivitySupport;

import com.tibco.xpd.capabilities.developer.DeveloperCapabilitiesConstants;
import com.tibco.xpd.capabilities.developer.Messages;
import com.tibco.xpd.capabilities.developer.ui.activities.DeveloperActivityUtil;

/**
 * Toggles between Developer and Analyst capabilities.
 * <p>
 * <i>Created: 4 Apr 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class DeveloperActivityTogglerAction implements
        IWorkbenchWindowActionDelegate, IActivityManagerListener,
        IActionDelegate2 {

    private IWorkbenchWindow window;

    private IAction action = null;

    public DeveloperActivityTogglerAction() {
    }

    public void dispose() {
        PlatformUI.getWorkbench().getActivitySupport().getActivityManager()
                .removeActivityManagerListener(this);
    }

    public void init(IWorkbenchWindow window) {
        this.window = window;
        PlatformUI.getWorkbench().getActivitySupport().getActivityManager()
                .addActivityManagerListener(this);

    }

    public void run(IAction action) {
        String question;
        boolean developerActivityEnabled = DeveloperActivityUtil
                .isDeveloperActivityEnabled();
        if (developerActivityEnabled) {
            question = Messages.DeveloperActivityTogglerAction_ConfirmSwitchProcessAnalystCapability_message;
        } else {
            question = Messages.DeveloperActivityTogglerAction_ConfirmSwitchProcessDeveloperCapability_message;
        }
        boolean confirmed = MessageDialog.openConfirm(window.getShell(),
                Messages.DeveloperActivityTogglerAction_Confirm_title, question);
        if (confirmed) {
            setDeveloperActivity(!developerActivityEnabled);
        }
        refreshActionState();
    }

    private void refreshActionState() {
        boolean isDeveloperEnabled = DeveloperActivityUtil
                .isDeveloperActivityEnabled();
        if (action != null) {
            action.setChecked(isDeveloperEnabled);
        }
    }

    @SuppressWarnings("unchecked")
    private void setDeveloperActivity(boolean enabled) {
        IWorkbenchActivitySupport activitySupport = PlatformUI.getWorkbench()
                .getActivitySupport();
        IActivityManager activityManager = activitySupport.getActivityManager();
        Set enabledActivityIds = new HashSet(activityManager
                .getEnabledActivityIds());
        String developerActivityId = DeveloperCapabilitiesConstants.DEVELOPER_ACTIVITY_ID;
        if (enabled) {
            if (enabledActivityIds.add(developerActivityId)) {
                activitySupport.setEnabledActivityIds(enabledActivityIds);
            }
        } else {
            if (enabledActivityIds.remove(developerActivityId)) {
                activitySupport.setEnabledActivityIds(enabledActivityIds);
            }
        }
    }

    public void selectionChanged(IAction action, ISelection selection) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.activities.IActivityManagerListener#activityManagerChanged(org.eclipse.ui.activities.ActivityManagerEvent)
     */
    public void activityManagerChanged(ActivityManagerEvent activityManagerEvent) {
        if (activityManagerEvent.haveEnabledActivityIdsChanged()) {
            refreshActionState();
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionDelegate2#init(org.eclipse.jface.action.IAction)
     */
    public void init(IAction action) {
        this.action = action;
        refreshActionState();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionDelegate2#runWithEvent(org.eclipse.jface.action.IAction,
     *      org.eclipse.swt.widgets.Event)
     */
    public void runWithEvent(IAction action, Event event) {
        run(action);
    }
}