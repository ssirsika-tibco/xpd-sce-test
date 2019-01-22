/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author aprasad
 */
public class CatchLinkDuplicateLabelRule extends FlowContainerValidationRule {

    /** The issue id. */
    private static final String ISSUE_ID = "bpmn.catch.link.duplicate.label"; //$NON-NLS-1$

    /**
     * @param process
     *            The process to check.
     * @see com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate(FlowContainer)
     */
    @Override
    public void validate(FlowContainer process) {
        Collection<Activity> linkActivities =
                EventObjectUtil.getFlowContainerEvents(process);
        HashMap<String, List<Activity>> namesAndDuplicates =
                new HashMap<String, List<Activity>>();
        for (Activity activity : linkActivities) {
            if ((EventTriggerType.EVENT_LINK_CATCH_LITERAL
                    .equals(EventObjectUtil.getEventTriggerType(activity)) || EventTriggerType.EVENT_MULTIPLE_CATCH_LITERAL
                    .equals(EventObjectUtil.getEventTriggerType(activity)))) {
                String catchLinkLabel =
                        Xpdl2ModelUtil.getDisplayNameOrName(activity);
                if (catchLinkLabel != null && catchLinkLabel.length() != 0) {
                    if (!namesAndDuplicates.containsKey(catchLinkLabel)) {
                        List<Activity> activities = new ArrayList<Activity>();
                        activities.add(activity);
                        namesAndDuplicates.put(catchLinkLabel, activities);
                    } else {
                        namesAndDuplicates.get(catchLinkLabel).add(activity);
                    }
                }
            }
        }
        for (String name : namesAndDuplicates.keySet()) {
            List<Activity> activitiesWithDuplicateName =
                    namesAndDuplicates.get(name);
            if (!activitiesWithDuplicateName.isEmpty()
                    && activitiesWithDuplicateName.size() > 1) {
                for (Activity activity : activitiesWithDuplicateName) {
                    addIssue(ISSUE_ID, activity);
                }
            }
        }

    }
}
