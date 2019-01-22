/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.bx.validation.rules.scripts;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.js.validation.rules.AbstractCatchSignalMappingScriptRule;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil.GetSignalPayloadException;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Script validaiton rule for catch signal event mappings scripts.
 * 
 * @author aallway
 * @since 9 May 2012
 */
public class BxJsCatchSignalMappingScriptRule extends
        AbstractCatchSignalMappingScriptRule {

    private static final String EMPTY_SCRIPT_DATA_NOT_SUPPORTED =
            "bx.emptyScriptNotSupported"; //$NON-NLS-1$

    /** The issue id. */
    private static final String ERROR_ID = "bx.validateScriptTask"; //$NON-NLS-1$

    private static final String WARNING_ID = "bx.warning.validateScriptTask"; //$NON-NLS-1$

    @Override
    protected String getErrorId() {
        return ERROR_ID;
    }

    @Override
    protected String getWarningId() {
        return WARNING_ID;
    }

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractCatchSignalMappingScriptRule#getEmptyScriptIssueId()
     * 
     * @return
     */
    @Override
    protected String getEmptyScriptIssueId() {
        return EMPTY_SCRIPT_DATA_NOT_SUPPORTED;
    }

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractCatchSignalMappingScriptRule#shouldValidateMappingsScripts(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param catchSignalEvent
     * @return
     */
    @Override
    protected boolean shouldValidateMappingsScripts(Activity catchSignalEvent) {
        /*
         * Do not bother validating scripts if the event is not a
         * non-cancelling, attached to a task boundary. There is a different
         * rule to cover this.
         */
        Activity taskAttachedTo =
                EventObjectUtil.getTaskAttachedTo(catchSignalEvent);

        if (taskAttachedTo == null
                || !TaskType.USER_LITERAL.equals(TaskObjectUtil
                        .getTaskTypeStrict(taskAttachedTo))
                || !EventObjectUtil.isNonCancellingEvent(catchSignalEvent)) {
            return false;
        }

        /*
         * Do not bother validating scripts (and getting a whole load of errors
         * - if there is an inconsistent payload problem (which will be marked
         * separately).
         */
        String signalName = EventObjectUtil.getSignalName(catchSignalEvent);

        if (signalName != null) {
            Process process = catchSignalEvent.getProcess();

            if (process != null) {
                Set<Activity> throwerSet = new HashSet<Activity>();

                for (Activity activity : Xpdl2ModelUtil
                        .getAllActivitiesInProc(process)) {
                    if (EventObjectUtil.getEventTriggerType(activity) == EventTriggerType.EVENT_SIGNAL_THROW_LITERAL) {
                        if (signalName.equals(EventObjectUtil
                                .getSignalName(activity))) {
                            throwerSet.add(activity);
                        }
                    }
                }

                if (throwerSet.size() > 1) {
                    try {
                        Collection<ActivityInterfaceData> payload =
                                EventObjectUtil
                                        .validateSignalPayLoads(throwerSet);
                    } catch (GetSignalPayloadException e) {
                        return false;
                    }
                }
            }
        }

        return super.shouldValidateMappingsScripts(catchSignalEvent);
    }
}
