/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.validation.bpmn.rules.CorrelationDataRequiredRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * XPD-1346: BPM destination specific rule
 * <p>
 * XPD-6752: Process manager flavour of the BPMN validation "In-flow incoming
 * request activity must have at least one implicitly/explicitly
 * "Correlate mode" correlation data field associated."
 * 
 * @author rsomayaj
 * @since 21 Jan 2011
 */
public class N2CorrelationDataRequiredRule extends CorrelationDataRequiredRule {

    private static final String NO_CORRELATEMODE_DATA_FOR_REQACTIVITY =
            "bx.activityWithNoCorrelationDataSetToCorrelate"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.CorrelationDataRequiredRule#validateFlowContainer(com.tibco.xpd.xpdl2.Process,
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
        /*
         * Sid ACE-6366 disable correlation data rule as correlation currenty doesn't have the same rules as AMX BPM
         */

        // /*
        // * Don’t validate this issue for Pageflow / Business-service, because
        // * the types of activity it relates to (incoming message requests) are
        // * not supported in pageflows anyway. So adding issues will only get in
        // * the way of the “not supported activity type” issues that are more
        // * relevant.
        // */
        // if (Xpdl2ModelUtil.isPageflow(process)
        // || ProcessInterfaceUtil.isPageflowEngineServiceProcess(process)) {
        // return;
        // }
        //
        // /*
        // * Go through all activities.
        // */
        // for (Activity activity : activities) {
        // if (Xpdl2ModelUtil.isCorrelatingActivity(activity)) {
        //
        // /*
        // * Raise only when there's correlation data at process level.
        // */
        // if (!Xpdl2ModelUtil.getCorrelationDataFields(process).isEmpty()) {
        // /*
        // * Sid XPD-6865. Changed to use activity interface data util
        // * as old code would have complained if there was an empty
        // * xpdExt:AssociatedCorrelationFields (which still means
        // * 'implicitly all in correlate mode'and we used to say
        // * "if that element != null then there must be at least one correlation entry"
        // */
        // Collection<ActivityInterfaceCorrelationData> correlationData =
        // ActivityInterfaceDataUtil
        // .getActivityInterfaceCorrelationData(activity);
        //
        // boolean hasCorrelateModeAssoc = false;
        //
        // for (ActivityInterfaceCorrelationData data : correlationData) {
        // if (CorrelationMode.CORRELATE.equals(data
        // .getCorrelationMode())) {
        // hasCorrelateModeAssoc = true;
        // break;
        // }
        // }
        //
        // /*
        // * Raise issue if there's no associated field set to
        // * "Correlate".
        // */
        // if (!hasCorrelateModeAssoc) {
        // addIssue(NO_CORRELATEMODE_DATA_FOR_REQACTIVITY,
        // activity);
        // }
        // }
        // }
        // }
        //
        // super.validateFlowContainer(process, activities, transitions);
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.CorrelationDataRequiredRule#getIssueId()
     * 
     * @return
     */
    @Override
    protected String getIssueId() {
        return "bx.activityWithNoAssociatedCorrelationData"; //$NON-NLS-1$
    }

}
