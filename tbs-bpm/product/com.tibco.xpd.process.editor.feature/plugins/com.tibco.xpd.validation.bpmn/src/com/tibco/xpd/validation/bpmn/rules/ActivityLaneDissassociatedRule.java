/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author rsomayaj
 * 
 */
public class ActivityLaneDissassociatedRule extends ProcessValidationRule {

    private static final String ISSUE_ID = "bpmn.activityLaneDisassociatedRule"; //$NON-NLS-1$

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
        Collection<Activity> allActivities =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);
        List<String> laneIds = getLaneIdsList(process);
        for (Activity activity : allActivities) {
            if (!(activity.getFlowContainer() instanceof ActivitySet)) {
                NodeGraphicsInfo nodeGraphicsInfo =
                        Xpdl2ModelUtil.getNodeGraphicsInfo(activity);
                if (nodeGraphicsInfo == null
                        || !laneIds.contains(nodeGraphicsInfo.getLaneId())) {
                    addIssue(ISSUE_ID, activity, Collections
                            .singletonList(Xpdl2ModelUtil
                                    .getDisplayName(activity)));
                }
            }
        }
    }

    /**
     * @param process
     * @return
     */
    private List<String> getLaneIdsList(Process process) {
        Collection<Pool> processPools = Xpdl2ModelUtil.getProcessPools(process);
        List<String> laneIds = new ArrayList<String>();
        for (Pool pool : processPools) {
            List<Lane> lanes = pool.getLanes();
            for (Lane lane : lanes) {
                laneIds.add(lane.getId());
            }
        }
        return laneIds;
    }
}
