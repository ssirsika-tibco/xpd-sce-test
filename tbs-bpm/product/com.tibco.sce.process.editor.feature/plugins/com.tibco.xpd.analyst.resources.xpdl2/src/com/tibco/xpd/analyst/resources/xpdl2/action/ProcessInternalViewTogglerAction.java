package com.tibco.xpd.analyst.resources.xpdl2.action;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.ActivityEvent;
import org.eclipse.ui.activities.IActivityListener;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.IWorkbenchActivitySupport;

import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.xpdl2.edit.util.ProcessInternalViewUtil;

@Deprecated
public class ProcessInternalViewTogglerAction extends Action implements
        IWorkbenchWindowActionDelegate, IActivityListener, IPerspectiveListener {

    private IWorkbenchWindow window;

    private boolean processInternalView = false;

    private IAction action = null;

    private IWorkbenchActivitySupport activitySupport = null;

    private IActivityManager activityManager = null;

    private boolean ignorePerspectiveEvents = false;

    public void dispose() {
        this.window = null;
    }

    public void init(IWorkbenchWindow window) {
        this.window = window;
        registerAsActivityListener();
        this.processInternalView = ProcessInternalViewUtil
                .isProcessInternalViewEnabled();
        // window.addPerspectiveListener(this);

    }

    private void registerAsActivityListener() {
        // registering activity listener
        activitySupport = PlatformUI.getWorkbench().getActivitySupport();
        activityManager = activitySupport.getActivityManager();
        Set definedActivityIds = activityManager.getDefinedActivityIds();
        for (Iterator iter = definedActivityIds.iterator(); iter.hasNext();) {
            String activityId = (String) iter.next();
            if (XpdConsts.PROCESS_INTERNAL_CAPABILITY.equals(activityId)
                    || XpdConsts.ANALYST_CAPABILITY.equals(activityId)) {
                activityManager.getActivity(activityId).addActivityListener(
                        this);
            }
        }
    }

    public void run(IAction action) {
        String question;
        if (processInternalView) {
            question = Messages.ProcessInternalViewTogglerAction_analystCapability_message;
        } else {
            question = Messages.ProcessInternalViewTogglerAction_processInternal_message;
        }
        boolean returnValue = MessageDialog.openConfirm(window.getShell(),
                Messages.ProcessInternalViewTogglerAction_title, question);
        if (!returnValue) {
            action.setChecked(ProcessInternalViewUtil
                    .isProcessInternalViewEnabled());
            return;
        }
        if (processInternalView) {
            processInternalView = false;
        } else {
            processInternalView = true;
        }
        toggleView();
    }

    private void toggleView() {
        // registering activity listener
        activitySupport = PlatformUI.getWorkbench().getActivitySupport();
        IActivityManager activityManager = activitySupport.getActivityManager();
        Set enabledActivityIds = new HashSet(activityManager
                .getEnabledActivityIds());
        String processInternalCapability = XpdConsts.PROCESS_INTERNAL_CAPABILITY;
        if (processInternalView) {
            if (enabledActivityIds.add(processInternalCapability)) {
                activitySupport.setEnabledActivityIds(enabledActivityIds);
            }
        } else {
            if (enabledActivityIds.remove(processInternalCapability)) {
                activitySupport.setEnabledActivityIds(enabledActivityIds);
            }
        }
    }

    public void selectionChanged(IAction action, ISelection selection) {
        this.action = action;
        action.setChecked(ProcessInternalViewUtil
                .isProcessInternalViewEnabled());
    }

    public void activityChanged(ActivityEvent activityEvent) {
        processInternalView = ProcessInternalViewUtil
                .isProcessInternalViewEnabled();
        if (action != null) {
            action.setChecked(processInternalView);
        }
    }

    public void perspectiveActivated(IWorkbenchPage page,
            IPerspectiveDescriptor perspective) {
        if (ignorePerspectiveEvents) {
            return;
        }
        if (XpdConsts.ANALYST_CAPABILITY.equals(perspective.getId())) {
            processInternalView = false;
            toggleView();
            action.setChecked(false);
        } else if (XpdConsts.PROCESS_INTERNAL_CAPABILITY.equals(perspective
                .getId())) {
            processInternalView = true;
            toggleView();
            action.setChecked(true);
        }
    }

    public void perspectiveChanged(IWorkbenchPage page,
            IPerspectiveDescriptor perspective, String changeId) {
        // TODO Auto-generated method stub
    }

}
