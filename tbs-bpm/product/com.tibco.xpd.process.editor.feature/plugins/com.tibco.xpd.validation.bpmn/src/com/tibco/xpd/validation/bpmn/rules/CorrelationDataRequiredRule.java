/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * Rule pulled out of {@link CorrelationDataRule} so that it can be inherited
 * from BPM destination for it to be set as an error.
 * 
 * @author rsomayaj
 * @since 21 Jan 2011
 */
public class CorrelationDataRequiredRule extends ProcessValidationRule {

    private static final String NO_CORRELATIONDATA_EXISTS_FOR_REQACTIVITY =
            "bpmn.activityWithNoAssociatedCorrelationData"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validateFlowContainer(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.emf.common.util.EList,
     *      org.eclipse.emf.common.util.EList)
     * 
     * @param process
     * @param activities
     * @param transitions
     */
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        Map<String, DataField> defined = new HashMap<String, DataField>();
        for (DataField field : process.getDataFields()) {
            if (field.isCorrelation()) {
                defined.put(field.getName(), field);
            }
        }
        for (Activity activity : activities) {
            if (Xpdl2ModelUtil.isCorrelatingActivity(activity)) {

                if (defined.keySet().size() == 0) {
                    // If there are any in-flow incoming request activities
                    // there must be at least one correlation data field so that
                    // it can be implicitly associated in Correlate mode.
                    //
                    /*
                     * MR 42774 But only if implementation type is set to web
                     * service!
                     */
                    if (isWebServiceImplementationSet(activity)) {
                        addIssue(getIssueId(), activity);
                    }
                }
            }
        }

    }

    /**
     * @param activity
     * @return true if the activity is a web-service capable activity with the
     *         web-service implementation type set.
     */
    private boolean isWebServiceImplementationSet(Activity activity) {
        if (TaskObjectUtil.getTaskTypeStrict(activity) != null) {
            ImplementationType taskImplementationType =
                    TaskObjectUtil.getTaskImplementationType(activity);
            if (taskImplementationType != null
                    && ImplementationType.WEB_SERVICE_LITERAL
                            .equals(taskImplementationType)) {
                return true;
            }
        } else if (activity.getEvent() != null) {
            ImplementationType eventImplementationType =
                    EventObjectUtil.getEventImplementationType(activity);
            if (eventImplementationType != null
                    && ImplementationType.WEB_SERVICE_LITERAL
                            .equals(eventImplementationType)) {
                return true;
            }
        }
        return false;
    }

    protected String getIssueId() {
        return NO_CORRELATIONDATA_EXISTS_FOR_REQACTIVITY;
    }

}
