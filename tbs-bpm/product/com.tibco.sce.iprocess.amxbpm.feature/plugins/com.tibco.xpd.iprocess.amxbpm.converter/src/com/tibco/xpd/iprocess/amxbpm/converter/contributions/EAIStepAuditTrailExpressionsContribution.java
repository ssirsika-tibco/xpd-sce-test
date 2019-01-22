/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution;
import com.tibco.xpd.iprocess.amxbpm.converter.IProcessAMXBPMConverterPlugin;
import com.tibco.xpd.iprocess.amxbpm.converter.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdExtension.Audit;
import com.tibco.xpd.xpdExtension.AuditEvent;
import com.tibco.xpd.xpdExtension.AuditEventType;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Contribution to handle the transformation of EAI Step Initiate/Complete Audit
 * Trail Expressions.
 * 
 * @author sajain
 * @since Apr 28, 2014
 */
public class EAIStepAuditTrailExpressionsContribution extends
        AbstractIProcessToBPMContribution {

    /**
     * @see com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution#convert(java.util.List,
     *      java.util.List, org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param processes
     * @param processInterfaces
     * @param monitor
     * @return
     */
    @Override
    public IStatus convert(List<Process> processes,
            List<ProcessInterface> processInterfaces, IProgressMonitor monitor) {
        try {
            monitor.beginTask("", 1); //$NON-NLS-1$

            /*
             * Sid XPD-6230 No need to set task name if it's judst a repeat of
             * the contribution plugin.xml desc' as that is output by framework
             * anyway.
             */

            MultiStatus returnStatus =
                    new MultiStatus(IProcessAMXBPMConverterPlugin.PLUGIN_ID, 0,
                            "", null); //$NON-NLS-1$

            for (Process eachProcess : processes) {
                List<Activity> allProcessActivities = new ArrayList<Activity>();

                /*
                 * Fetch all activities from processes.
                 */
                allProcessActivities.addAll(eachProcess.getActivities());

                /*
                 * Fetch all activities from activity sets.
                 */
                EList<ActivitySet> allActivitySets =
                        eachProcess.getActivitySets();
                for (ActivitySet eachActivitySet : allActivitySets) {
                    allProcessActivities
                            .addAll(eachActivitySet.getActivities());
                }

                if (allProcessActivities != null
                        && !allProcessActivities.isEmpty()) {
                    for (Activity eachProcessActivity : allProcessActivities) {
                        IStatus iS =
                                transformEAIStepInitiateAndCompleteAuditTrailExpressions(eachProcessActivity);
                        /*
                         * Collect status.
                         */
                        returnStatus.add(iS);

                        /*
                         * If there's an error, return
                         */
                        if (iS.getSeverity() == IStatus.ERROR) {

                            return returnStatus;
                        }
                    }
                }
            }

            /*
             * XPD-6370: Main framework now reports status with each conversion
             * description. so no need to do it here also just for OK
             */

            monitor.worked(1);

            return returnStatus;

        } finally {
            monitor.done();
        }
    }

    /**
     * Transform EAI Step Initiate/Complete Audit Trail Expressions.
     * 
     * @param allProcessActivities
     */
    private IStatus transformEAIStepInitiateAndCompleteAuditTrailExpressions(
            Activity eachProcessActivity) {

        TaskType taskType =
                TaskObjectUtil.getTaskTypeStrict(eachProcessActivity);

        MultiStatus status =
                new MultiStatus(IProcessAMXBPMConverterPlugin.PLUGIN_ID, 0,
                        "", null); //$NON-NLS-1$

        if (TaskType.SERVICE_LITERAL.equals(taskType)
                || TaskType.SCRIPT_LITERAL.equals(taskType)) {
            Object auditElement =
                    getExtensionElement(eachProcessActivity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Audit());

            if (auditElement instanceof Audit) {
                Audit aI = (Audit) auditElement;
                EList<AuditEvent> allAuditEvents = aI.getAuditEvent();
                for (int i = 0; i < allAuditEvents.size(); i++) {
                    AuditEvent eachAuditEvent = allAuditEvents.get(i);
                    AuditEventType eachAuditEventType =
                            eachAuditEvent.getType();
                    if (AuditEventType.INITIATED_LITERAL
                            .equals(eachAuditEventType)
                            || AuditEventType.COMPLETED_LITERAL
                                    .equals(eachAuditEventType)) {

                        String auditEventInfoText =
                                eachAuditEvent.getInformation().getText();
                        if (auditEventInfoText != null
                                && !auditEventInfoText.isEmpty()) {
                            /*
                             * Modify the expression text to a
                             * Process.auditLog(...) method call.
                             */
                            String modifiedExpressionText =
                                    getModifiedExpressionText(eachProcessActivity,
                                            auditEventInfoText,
                                            status);

                            if (modifiedExpressionText != null
                                    && !modifiedExpressionText.isEmpty()) {
                                /*
                                 * Find existing text feature in feature map.
                                 */
                                int textIndex = -1;
                                for (int j = 0; j < eachAuditEvent
                                        .getInformation().getMixed().size(); j++) {
                                    if (XMLTypePackage.eINSTANCE
                                            .getXMLTypeDocumentRoot_Text()
                                            .equals(eachAuditEvent
                                                    .getInformation()
                                                    .getMixed()
                                                    .getEStructuralFeature(j))) {
                                        textIndex = j;
                                        break;
                                    }
                                }

                                /*
                                 * If it already exists, then replace it.
                                 */
                                if (textIndex != -1) {
                                    eachAuditEvent
                                            .getInformation()
                                            .getMixed()
                                            .setValue(textIndex,
                                                    modifiedExpressionText);
                                }
                            }
                        }
                    }
                }
            }
        }

        return status;
    }

    /**
     * Modifies the expression text to process.AuditLog(...) method call.
     * 
     * @param eachProcessActivity
     *            passed for context for error messages.
     * @param eachAuditEvent
     * 
     * @return modified script
     */
    private String getModifiedExpressionText(Activity eachProcessActivity,
            String infoText, MultiStatus status) {

        JavaScriptLineList javaScript = new JavaScriptLineList(infoText);

        String convertedJavaScript = null;

        if (javaScript.size() > 1) {
            status.add(new Status(
                    IStatus.WARNING,
                    IProcessAMXBPMConverterPlugin.PLUGIN_ID,
                    String.format(Messages.EAIStepAuditTrailExpressionsContribution_IStatus_NumOfScriptLinesExceeded,
                            Xpdl2ModelUtil
                                    .getDisplayNameOrName(eachProcessActivity
                                            .getProcess()),
                            Xpdl2ModelUtil
                                    .getDisplayNameOrName(eachProcessActivity))));

        } else {
            int semicolonIndex = getSemicolonIndex(infoText);
            if (semicolonIndex != -1) {
                convertedJavaScript =
                        insertScriptsInProcessAuditLog(javaScript.get(0),
                                semicolonIndex);
            } else {
                status.add(new Status(
                        IStatus.WARNING,
                        IProcessAMXBPMConverterPlugin.PLUGIN_ID,
                        String.format(Messages.EAIStepAuditTrailExpressionsContribution_IStatus_ScriptLineShouldEndWithSemiColon,
                                Xpdl2ModelUtil
                                        .getDisplayNameOrName(eachProcessActivity
                                                .getProcess()),
                                Xpdl2ModelUtil
                                        .getDisplayNameOrName(eachProcessActivity))));

            }
        }

        return convertedJavaScript;
    }

    /**
     * Inserts the converted javascript into process.auditLog(...) method call
     * and returns the modified string.
     * 
     * @param string
     * @param semicolonIndex
     * @return
     */
    private String insertScriptsInProcessAuditLog(String javaScript,
            int semicolonIndex) {
        StringBuffer sB = new StringBuffer();
        sB.append("Process.auditLog("); //$NON-NLS-1$
        sB.append(javaScript.substring(0, semicolonIndex));
        sB.append(")"); //$NON-NLS-1$
        sB.append(javaScript.substring(semicolonIndex, javaScript.length()));
        return sB.toString();
    }

    /**
     * Returns the index of first semicolon which is not under quotes (or nested
     * quotes for that matter).
     * 
     * @param infoText
     * @return index of first semicolon which is not under quotes
     */
    private int getSemicolonIndex(String infoText) {

        boolean isDoubleQuotes = false;
        boolean isSingleQuotes = false;

        for (int idx = 0; idx < infoText.length(); idx++) {

            char charAt = infoText.charAt(idx);

            if (charAt == '\\' && isDoubleQuotes) {
                /*
                 * Ignore escaped next character in string.
                 */
                idx++;
            } else if (charAt == '\\' && isSingleQuotes) {
                /*
                 * Ignore escaped next character in string.
                 */
                idx++;
            } else if (charAt == '"') {

                if (!isDoubleQuotes) {
                    isDoubleQuotes = true;
                } else {
                    isDoubleQuotes = false;
                }
            } else if (charAt == '\'') {
                if (!isSingleQuotes) {
                    isSingleQuotes = true;
                } else {
                    isSingleQuotes = false;
                }
            }

            if (isDoubleQuotes || isSingleQuotes) {
                continue;
            }

            if (charAt == ';') {
                return idx;
            }
        }
        return -1;
    }
}
