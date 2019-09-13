/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.bx.validation.ace.rules;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdExtension.CatchErrorMappings;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Rule to ensure that JavaScript mappings are validated against in ACE.
 *
 * @author sajain
 * @since Sep 12, 2019
 */
public class AceJavaScriptMappingsRule extends FlowContainerValidationRule {

    /** The issue id. */
    private static final String ID = "ace.jsMappingsNotSupported"; //$NON-NLS-1$

    /**
     * @param process
     *            The process to validate.
     * @see com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate(
     *      FlowContainer)
     */
    @Override
    public void validate(FlowContainer process) {
        EList<Activity> contents = process.getActivities();
        for (Activity eachActivity : contents) {
            boolean addIssue = false;
            if (eachActivity.getImplementation() instanceof SubFlow) {
                /*
                 * SubFlow Data mappings.
                 */
                SubFlow subFlow = (SubFlow) (eachActivity.getImplementation());
                if (null != subFlow.getDataMappings() && !subFlow.getDataMappings().isEmpty()) {
                    addIssue = true;
                }
            } else if (eachActivity.getEvent() != null) {
                EventTriggerType eventType = EventObjectUtil.getEventTriggerType(eachActivity);
                if (EventTriggerType.EVENT_ERROR_LITERAL.equals(eventType)
                        || EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL.equals(eventType)
                        || EventTriggerType.EVENT_SIGNAL_THROW_LITERAL.equals(eventType)) {
                    SignalData sigData = EventObjectUtil.getSignalDataExtensionElement(eachActivity);
                    if (null != sigData && null != sigData.getDataMappings() && !sigData.getDataMappings().isEmpty()) {
                        /*
                         * Catch/Throw Global/Local signal data mappings.
                         */
                        addIssue = true;
                    } else {
                        /*
                         * Catch error data mappings.
                         */
                        ResultError resultError = EventObjectUtil.getResultError(eachActivity);
                        if (null != resultError) {
                            CatchErrorMappings catchErrorMappings =
                                    (CatchErrorMappings) Xpdl2ModelUtil.getOtherElement(resultError,
                                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_CatchErrorMappings());
                            if (null != catchErrorMappings && null != catchErrorMappings.getMessage()
                                    && null != catchErrorMappings.getMessage().getDataMappings()
                                    && !catchErrorMappings.getMessage().getDataMappings().isEmpty()) {
                                addIssue = true;
                            }
                        }
                    }
                }
            }
            if (addIssue) {
                addIssue(ID, eachActivity);
            }
        }
    }

}
