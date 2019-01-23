/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.ConnectorGraphicsInfo;
import com.tibco.xpd.xpdl2.ExclusiveType;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Intermediate/End events activities are not permitted to have multiple
 * incoming flows by the BPMN notation (and hence by AMX BPM). Hence this
 * contribution will insert an XOR join gateway into the flow prior to any
 * intermediate/end event output by the conversion that has multiple incoming
 * flows and create a single flow into the event.
 * 
 * @author sajain
 * @since May 19, 2014
 */
public class EventCanHaveSingleFlowContribution extends
        AbstractIProcessToBPMContribution {

    private static final String DOT = "."; //$NON-NLS-1$

    /**
     * Border color to be used in connector graphics info.
     */
    private static final String BORDER_COLOR = "0,0,128"; //$NON-NLS-1$

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
            monitor.beginTask("", processes.size()); //$NON-NLS-1$

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

                        if (eachProcessActivity != null) {

                            EventFlowType eventFlowType =
                                    EventObjectUtil
                                            .getFlowType(eachProcessActivity);

                            /*
                             * Check if it's an intermediate or an end event.
                             */
                            if (EventFlowType.FLOW_INTERMEDIATE_LITERAL
                                    .equals(eventFlowType)
                                    || EventFlowType.FLOW_END_LITERAL
                                            .equals(eventFlowType)) {

                                /*
                                 * Check if there are multiple transitions for
                                 * this event.
                                 */
                                List<Transition> transitions =
                                        eachProcessActivity
                                                .getIncomingTransitions();

                                if ((transitions.size() > 1)) {

                                    /*
                                     * So there are multiple incoming flows,
                                     * hence insert XOR joins prior to this
                                     * event.
                                     */
                                    insertXORJoinPriorToEvents(eachProcessActivity,
                                            transitions);

                                }
                            }
                        }
                    }
                }
                monitor.worked(1);
            }

            return Status.OK_STATUS;

        } finally {
            monitor.done();
        }

    }

    /**
     * Edit the process transitions by inserting a new XOR gateway prior to the
     * specified event.
     * 
     * @param currentActivity
     *            The event prior to which XOR gateway is to be added.
     * @param transitions
     *            Transitions to be edited.
     */
    private void insertXORJoinPriorToEvents(Activity currentActivity,
            List<Transition> transitions) {

        FlowContainer flowContainer = currentActivity.getFlowContainer();

        /*
         * 1. Create a new XOR gateway.
         */
        Activity xorGate = getNewXORGateway();

        NodeGraphicsInfo nGInfo =
                EcoreUtil.copy(Xpdl2ModelUtil
                        .getNodeGraphicsInfo(currentActivity));
        if (null != nGInfo) {
            addNodeGraphicsInfo(xorGate, nGInfo);
        }

        flowContainer.getActivities().add(xorGate);

        /*
         * 2. Point all transitions to the newly created XOR gateway which are
         * currently pointing to the event being processed.
         */
        for (Transition eachTransition : transitions) {

            if (eachTransition != null) {

                eachTransition.setTo(xorGate.getId());
            }
        }

        /*
         * 3. Create a new transition from XOR gate to the event being
         * processed.
         */
        Transition newTransition = getXpdlFactory().createTransition();
        newTransition.setFrom(xorGate.getId());
        newTransition.setTo(currentActivity.getId());

        ConnectorGraphicsInfo newCGInfo =
                getXpdlFactory().createConnectorGraphicsInfo();

        newCGInfo.setBorderColor(BORDER_COLOR);
        newCGInfo.setToolId(Xpdl2ModelUtil.STUDIO_SPECIFIC_TOOL_ID + DOT
                + Xpdl2ModelUtil.CONNECTION_INFO_IDSUFFIX);

        newTransition.getConnectorGraphicsInfos().add(newCGInfo);

        /*
         * 4. Add the new transition from XOR gate to the event into the process
         * model.
         */
        flowContainer.getTransitions().add(newTransition);
    }

    /**
     * Creates and returns a brand new XOR gateway.
     * 
     * @return newly created XOR gateway.
     */
    private Activity getNewXORGateway() {
        /*
         * Create new activity.
         */
        Activity xorGateway = getXpdlFactory().createActivity();

        /*
         * Create and configure route.
         */
        Route route = getXpdlFactory().createRoute();
        route.setGatewayType(JoinSplitType.EXCLUSIVE_LITERAL);
        route.setMarkerVisible(true);
        route.setExclusiveType(ExclusiveType.DATA);

        /*
         * Add route to activity.
         */
        xorGateway.setRoute(route);

        return xorGateway;
    }

    /**
     * Adds node graphics information for the specified activity (passed as a
     * node) according to the specified co-ordinates and lane ID.
     * 
     * @param node
     * @param laneId
     * @param xCoOrd
     * @param yCoOrd
     */
    private void addNodeGraphicsInfo(GraphicalNode node, NodeGraphicsInfo nGInfo) {

        double xCoOrd =
                nGInfo.getCoordinates().getXCoordinate() - 2
                        * nGInfo.getWidth();

        nGInfo.getCoordinates().setXCoordinate(xCoOrd);

        nGInfo.setHeight(ProcessWidgetConstants.GATEWAY_HEIGHT_SIZE);
        nGInfo.setWidth(ProcessWidgetConstants.GATEWAY_WIDTH_SIZE);

        node.getNodeGraphicsInfos().add(nGInfo);
    }
}