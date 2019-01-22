/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.highlighting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.extensions.AbstractStaticHighlighterContribution;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdExtension.Audit;
import com.tibco.xpd.xpdExtension.AuditEvent;
import com.tibco.xpd.xpdExtension.UserTaskScripts;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskUser;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Static process diagram highlighter contribution for activity.
 * 
 * @author aallway
 * @since 3 Feb 2011
 */
public class ActivitiesWithScriptsHighlightContribution extends
        AbstractStaticHighlighterContribution {

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractStaticHighlighterContribution#getHighlightedDiagramModelObjects(com.tibco.xpd.xpdl2.Process)
     * 
     * @param diagramProcess
     * @return
     */
    @Override
    public Collection<? extends EObject> getHighlightedDiagramModelObjects(
            Process diagramProcess) {
        List<Activity> withScripts = new ArrayList<Activity>();

        Collection<Activity> activities =
                Xpdl2ModelUtil.getAllActivitiesInProc(diagramProcess);
        for (Activity activity : activities) {
            if (isScriptTask(activity) || hasUserTaskScripts(activity)
                    || hasActivityScripts(activity) || hasLoopScripts(activity)) {
                withScripts.add(activity);
            }
        }

        return withScripts;
    }

    /**
     * @param activity
     * @return <code>true</code> if activity has loop expression scripts.
     */
    private boolean hasLoopScripts(Activity activity) {
        if (isScriptSpecified(ProcessScriptUtil.getStdLoopExpression(activity))) {
            return true;

        } else if (isScriptSpecified(ProcessScriptUtil
                .getStdLoopExpression(activity))) {
            return true;

        } else if (isScriptSpecified(ProcessScriptUtil
                .getMIAdditionalLoopExpression(activity))) {
            return true;

        } else if (isScriptSpecified(ProcessScriptUtil
                .getMIComplexExitExpression(activity))) {
            return true;

        } else if (isScriptSpecified(ProcessScriptUtil
                .getMILoopExpression(activity))) {
            return true;
        }

        return false;
    }

    /**
     * @param activity
     * @return <code>true</code> if the activity has user task scripts.
     */
    private boolean hasUserTaskScripts(Activity activity) {
        if (activity.getImplementation() instanceof Task) {
            TaskUser taskUser =
                    ((Task) activity.getImplementation()).getTaskUser();

            if (taskUser != null) {
                UserTaskScripts userTaskScripts =
                        (UserTaskScripts) taskUser
                                .getOtherElement(XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_UserTaskScripts()
                                        .getName());
                if (userTaskScripts != null) {
                    if (!isScriptSpecified(userTaskScripts.getOpenScript())
                            || !isScriptSpecified(userTaskScripts
                                    .getCloseScript())
                            || !isScriptSpecified(userTaskScripts
                                    .getSubmitScript())
                            || !isScriptSpecified(userTaskScripts
                                    .getScheduleScript())
                            || !isScriptSpecified(userTaskScripts
                                    .getRescheduleScript())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param activity
     * @return <code>true</code> if the activit has activity scripts defined.
     */
    private boolean hasActivityScripts(Activity activity) {
        Audit audit =
                (Audit) Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Audit());
        if (audit != null) {
            for (AuditEvent auditEvent : audit.getAuditEvent()) {
                if (isScriptSpecified(auditEvent.getInformation())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param activity
     * @return <code>true</code> if the activity is a script task we don't care
     *         whether it has a script task defined or not because the user will
     *         expect it to be included in highlighting because it is explicitly
     *         a script task.
     */
    private boolean isScriptTask(Activity activity) {
        if (TaskType.SCRIPT_LITERAL.equals(TaskObjectUtil
                .getTaskTypeStrict(activity))) {
            return true;
        }
        return false;
    }

    /**
     * @param openScript
     * @return <code>true</code> if the script exists and is not unspecified
     *         grammar.
     */
    private boolean isScriptSpecified(Expression expression) {
        if (expression != null) {
            if (!expression.getMixed().isEmpty()) {
                return true;
            }
        }

        return false;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractStaticHighlighterContribution#getMenuImageDescriptor()
     * 
     * @return
     */
    @Override
    public ImageDescriptor getMenuImageDescriptor() {
        return Xpdl2ProcessEditorPlugin
                .getImageDescriptor("icons/actsWithScriptsMenu.png"); //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractStaticHighlighterContribution#getMenuText()
     * 
     * @return
     */
    @Override
    public String getMenuText() {
        return Messages.TasksWithScriptsHighlightContribution_HighlightScriptActs_menu;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractStaticHighlighterContribution#getActivatedTooltipLabel()
     * 
     * @return
     */
    @Override
    public String getActivatedTooltipLabel() {
        return Messages.ActivitiesWithScriptsHighlightContribution_AcctivitiesWithScrtiptsDefined_tooltip;
    }

}
