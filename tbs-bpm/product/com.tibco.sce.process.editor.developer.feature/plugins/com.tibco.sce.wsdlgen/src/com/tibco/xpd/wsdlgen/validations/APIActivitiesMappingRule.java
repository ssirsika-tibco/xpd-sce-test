/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.wsdlgen.validations;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * At times, we observe that mappings aren't generated when they are expected
 * to. This validation rule ensures that all API request and reply activities do
 * have necessary mappings, and since creation /modification/ deletion of
 * mappings is disabled for API request response activities is disabled, this
 * become essential
 * 
 * A Quick fix is provided to create mappings
 * 
 * @author rsomayaj
 * 
 */
public class APIActivitiesMappingRule extends PackageValidationRule {

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    private static final String INCORRECT_MAPPINGS =
            "bpmn.reqActIncorrectMappings";

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ActivityValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param container
     */
    @Override
    public void validate(Package pkg) {
        List<Process> processes = pkg.getProcesses();

        for (Process proc : processes) {
            Collection<Activity> activitiesInProc =
                    Xpdl2ModelUtil.getAllActivitiesInProc(proc);
            for (Activity activity : activitiesInProc) {
                boolean processAPIActivity =
                        Xpdl2ModelUtil.isProcessAPIActivity(activity);
                if (processAPIActivity) {
                    if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
                        // Validate mappings for generated request
                        // activities
                        validateGeneratedMappings(activity);
                    } else {
                        boolean replyActivity =
                                ReplyActivityUtil.isReplyActivity(activity);
                        // If activity is a Reply activity - verify whether
                        // the
                        // associated request activity is a generated
                        // request
                        // activity - if so validate the reply activity
                        // mappings.
                        if (replyActivity) {
                            Activity reqActivity =
                                    ReplyActivityUtil
                                            .getRequestActivityForReplyActivity(activity);
                            if (reqActivity != null) {
                                if (Xpdl2ModelUtil
                                        .isGeneratedRequestActivity(reqActivity)) {
                                    validateGeneratedMappings(activity);
                                }
                            }
                        }
                    }
                }
            }
        }
        // TODO - RS - Validate all process interface message events as well.
    }

    /**
     * @param activity
     */
    private void validateGeneratedMappings(Activity activity) {
        /*
         * XPD-7539 Message Events can be REST sdervce as well as WebService;
         * only want to deal with WebService
         */
        if (activity.getEvent() != null
                && EventObjectUtil.isWebServiceRelatedEvent(activity)) {
            // Test whether the given activity is an event.
            EventTriggerType eventTriggerType =
                    EventObjectUtil.getEventTriggerType(activity);
            if (EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL
                    .equals(eventTriggerType)) {
                // For Start and Intermediate Message Catch events.
                // Need to verify that mappings exist for implemented event as
                // well.
                TriggerResultMessage triggerResultMessage =
                        EventObjectUtil.getTriggerResultMessage(activity);
                validateRequestActivityMappings(activity, triggerResultMessage
                        .getMessage().getDataMappings());
            } else if (EventTriggerType.EVENT_MESSAGE_THROW_LITERAL
                    .equals(eventTriggerType)) {
                // For reply activities - end message events or intermediate
                // message throw.
                TriggerResultMessage triggerResultMessage =
                        EventObjectUtil.getTriggerResultMessage(activity);
                validateReplyActivityMappings(activity, triggerResultMessage
                        .getMessage().getDataMappings());
            }
        } else {
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof com.tibco.xpd.xpdl2.Task) {
                TaskReceive taskReceive =
                        ((com.tibco.xpd.xpdl2.Task) implementation)
                                .getTaskReceive();
                if (taskReceive != null) {
                    validateRequestActivityMappings(activity, taskReceive
                            .getMessage().getDataMappings());
                } else {
                    TaskSend taskSend =
                            ((com.tibco.xpd.xpdl2.Task) implementation)
                                    .getTaskSend();
                    // This will be a reply activity - need to verify whether
                    // this request activity is generated
                    if (taskSend != null) {
                        validateReplyActivityMappings(activity, taskSend
                                .getMessage().getDataMappings());
                    }
                }

            }
        }
    }

    /**
     * @param activity
     * @param dataMappings
     */
    private void validateReplyActivityMappings(Activity activity,
            List<DataMapping> dataMappings) {
        List<FormalParameter> formalParameters =
                ProcessInterfaceUtil.getAssociatedFormalParameters(activity);
        // There needs to be a mapping corresponding to each Formal Parameter
        for (FormalParameter fp : formalParameters) {
            if (!ModeType.IN_LITERAL.equals(fp.getMode())) {
                List mappingsForParam =
                        findMappingsForParameter(dataMappings, fp);
                if (mappingsForParam.size() != 1) {
                    addIssue(INCORRECT_MAPPINGS, activity);
                }
            }
        }
    }

    /**
     * @param dataMappings
     * @param fp
     * @return
     */
    private List findMappingsForParameter(List<DataMapping> dataMappings,
            FormalParameter fp) {
        return EMFSearchUtil.findManyInList(dataMappings,
                Xpdl2Package.eINSTANCE.getDataMapping_Formal(),
                fp.getName());
    }

    /**
     * @param activity
     * @param dataMappings
     */
    private void validateRequestActivityMappings(Activity activity,
            EList<DataMapping> dataMappings) {
        List<FormalParameter> formalParameters =
                ProcessInterfaceUtil.getAssociatedFormalParameters(activity);
        // There needs to be a mapping corresponding to each Formal Parameter
        for (FormalParameter fp : formalParameters) {
            if (!ModeType.OUT_LITERAL.equals(fp.getMode())) {
                List mappingsForParam =
                        findMappingsForParameter(dataMappings, fp);
                if (mappingsForParam.size() != 1) {
                    addIssue(INCORRECT_MAPPINGS, activity);
                }
            }
        }
    }
}
