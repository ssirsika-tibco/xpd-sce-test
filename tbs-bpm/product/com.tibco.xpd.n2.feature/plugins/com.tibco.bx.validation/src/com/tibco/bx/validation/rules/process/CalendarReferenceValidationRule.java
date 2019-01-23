/**
 * Copyright (c) TIBCO Software Inc 2004-2012. All rights reserved.
 */
package com.tibco.bx.validation.rules.process;

import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.CalendarReference;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TriggerTimer;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Rule to validate {@link CalendarReference} set on a {@link Process} or Timer
 * Event.
 * 
 * @author njpatel
 */
public class CalendarReferenceValidationRule extends ProcessValidationRule {

    private static final String ID_MISSING_ISSUE_ID =
            "n2pe.calendarReferenceRuntimeFieldIdentifierMissing"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     */
    @Override
    public void validate(Process process) {
        List<ProcessRelevantData> processData =
                ProcessInterfaceUtil.getAllProcessRelevantData(process);

        // Validate the calendar reference (if data field set) set on the
        // process
        CalendarReference reference = getCalendarReference(process);
        if (reference != null && reference.getDataFieldId() != null
                && !reference.getDataFieldId().isEmpty()) {
            validate(process, reference, processData);
        }

        // Validate the Trigger timer events
        for (Activity activity : Xpdl2ModelUtil.getAllActivitiesInProc(process)) {
            TriggerTimer triggerTimer =
                    EventObjectUtil.getTriggerTimer(activity);
            if (triggerTimer != null) {
                CalendarReference activityReference =
                        getCalendarReference(triggerTimer);
                if (activityReference != null
                        && activityReference.getDataFieldId() != null
                        && !activityReference.getDataFieldId().isEmpty()) {
                    processData =
                            ProcessInterfaceUtil
                                    .getAllAvailableRelevantDataForActivity(activity);
                    validate(activity, activityReference, processData);
                }
            }
        }
    }

    /**
     * Validate the given CalendarReference.
     * 
     * @param container
     *            CalendarReference container
     * @param reference
     *            CalendarReference
     * @param processData
     *            all process data
     */
    private void validate(OtherElementsContainer container,
            CalendarReference reference, List<ProcessRelevantData> processData) {
        if (reference.getDataFieldId() != null
                && !reference.getDataFieldId().isEmpty()) {
            boolean isDataFieldPresent = false;
            for (ProcessRelevantData data : processData) {
                if (reference.getDataFieldId().equals(data.getId())) {
                    isDataFieldPresent = true;
                    break;
                }
            }

            if (!isDataFieldPresent) {
                addIssue(ID_MISSING_ISSUE_ID, container);
            }
        }
    }

    /**
     * Get the {@link CalendarReference} from the container.
     * 
     * @param container
     * @return CalendarReference or <code>null</code> if none set.
     */
    private CalendarReference getCalendarReference(
            OtherElementsContainer container) {
        return (CalendarReference) Xpdl2ModelUtil.getOtherElement(container,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_CalendarReference());
    }

}
