/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.validation.xpdl2.tools.ProcessFlowAnalyserPreProcessor;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ResultType;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.ProcessFlowAnalyser;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * ParallelJoinFromDownstreamRule
 * <p>
 * Check that a parallel join gateway is NOT directly connected to by a sequence
 * flow that is a result of a downstream activity. (This is illegal because the
 * sequence flow that came from down stream CANNOT have been processed when the
 * parallel join is reached for the first time - therefore the first time the
 * join is hit it can NEVER be satisfied (because an incoming flow can't be
 * processed until the after the parallel join 'completes'.
 * <p>
 * <b>It is intended that ALL BPMN flow rules are placed in this single class.
 * It creates a ProcessFlowAnalyser which can work efficiently (as a load of
 * demand cache) when you need to do lots of 'get outgoing / incoming / upstream
 * / downstream type methods).</b> Therefore the caching is shared by all the
 * different individual issues.
 * 
 * @author aallway
 */
public abstract class AbstractFlowContainerFlowRules extends
        FlowContainerValidationRule {

    public enum ISSUE_TYPE {
        PARALLEL_JOIN_FROM_DOWNSTREAM, REQUEST_NOT_UPSTREAM_OF_REPLY, REQUESTIMPLEMENTOR_NOT_UPSTREAM_OF_ERROR, REQUEST_NOT_UPSTREAM_OF_ERROR, INCLUSIVE_JOIN_FROM_DOWNSTREAM
    }

    /**
     * @param issueType
     * @return The issue id for the given issue type (or null if issue is not to
     *         be raised).
     */
    protected abstract String getIssueId(ISSUE_TYPE issueType);

    @Override
    public void validate(FlowContainer container) {
        long start = System.currentTimeMillis();

        //        System.out.println("==>" + this.getClass().getSimpleName() //$NON-NLS-1$
        //                + ".validate()"); //$NON-NLS-1$

        //
        // Prepare a load-on-demand cache of activity id and their incoming
        // transitions. And same again for outgoing transitions.
        // In a big complex process we are likely to be referencing same
        // thing time and time again.
        // NOTE: THIS CANNOT BE CLASS FIELDS AS THERE IS ONYL ONE INSTANCE
        // OF EACH RULE (HENCE THERE WOULD BE THREADING ISSUES.
        //
        // NOTE2: This cache will include outgoing transitions from events
        // attached to task boundary as if they were coming from the task
        // itself (makes things easier as we can say that the transition
        // comes from a task that is upstream etc).
        //
        ProcessFlowAnalyser flowAnalyser =
                getTool(ProcessFlowAnalyserPreProcessor.class, container)
                        .getProcessFlowAnalyser();

        if (getIssueId(ISSUE_TYPE.PARALLEL_JOIN_FROM_DOWNSTREAM) != null) {
            checkParallelJoinFromDownstream(container, flowAnalyser);
        }

        /*
         * XPD-5163: Inclusive join should have same rule as parallel - should
         * not have an incoming transition from downstream activity.
         */
        if (getIssueId(ISSUE_TYPE.INCLUSIVE_JOIN_FROM_DOWNSTREAM) != null) {
            checkInclusiveJoinFromDownstream(container, flowAnalyser);
        }

        if (getIssueId(ISSUE_TYPE.REQUEST_NOT_UPSTREAM_OF_REPLY) != null) {
            checkRequestUpstreamOfReply(container, flowAnalyser);
        }

        if (getIssueId(ISSUE_TYPE.REQUESTIMPLEMENTOR_NOT_UPSTREAM_OF_ERROR) != null) {
            checkRequestUpstreamOfImplementingError(container, flowAnalyser);
        }

        if (getIssueId(ISSUE_TYPE.REQUEST_NOT_UPSTREAM_OF_ERROR) != null) {
            checkRequestUpstreamOfNonImplementingError(container, flowAnalyser);
        }

        // System.out
        //                .println("<==" + this.getClass().getSimpleName() //$NON-NLS-1$
        //                        + ".validate() Took: " + ((float) ((System.currentTimeMillis() - start) / 1000))+"\n"); //$NON-NLS-1$ //$NON-NLS-2$

        return;
    }

    /**
     * Check that all throw fault error events in the given container are
     * downstream of the request activity that they throw error for.
     * 
     * parent operation
     * 
     * @param container
     * @param flowAnalyser
     */
    private void checkRequestUpstreamOfNonImplementingError(
            FlowContainer container, ProcessFlowAnalyser flowAnalyser) {
        for (Activity activity : container.getActivities()) {

            if (ThrowErrorEventUtil.isThrowFaultMessageErrorEvent(activity)
                    && !Xpdl2ModelUtil.isEventImplemented(activity)) {

                Activity reqAct =
                        ThrowErrorEventUtil.getRequestActivity(activity);
                if (reqAct != null) {

                    Set<String> upstream =
                            flowAnalyser.getUpstreamActivityIds(activity
                                    .getId());
                    /*
                     * Sid XPD-2109. The flow analyser does not treat attached
                     * events as downstream / upstream (which is usually the
                     * desired behaviour. However, in the situation that the
                     * request activity event is attached to task it means that
                     * the catch event itself will not be included in the list
                     * of upstream activities. So if the request activity is
                     * event attached to task boundary we should check whether
                     * that task is in the upstream activities.
                     */
                    String requestOrBoundaryTask = reqAct.getId();
                    if (EventObjectUtil.isAttachedToTask(reqAct)) {
                        requestOrBoundaryTask =
                                EventObjectUtil.getTaskIdAttachedTo(reqAct);
                    }

                    if (!upstream.contains(requestOrBoundaryTask)) {
                        doAddIssue(ISSUE_TYPE.REQUEST_NOT_UPSTREAM_OF_ERROR,
                                activity,
                                flowAnalyser);
                    }
                }
            }
        }
        return;
    }

    /**
     * Check that all implementing error events in the given container are
     * downstream of the request activities that implement their errorMethod's
     * parent operation
     * 
     * @param container
     * @param flowAnalyser
     */
    private void checkRequestUpstreamOfReply(FlowContainer container,
            ProcessFlowAnalyser flowAnalyser) {

        for (Activity act : container.getActivities()) {
            Activity reqAct =
                    ReplyActivityUtil.getRequestActivityForReplyActivity(act);
            if (reqAct != null) {
                /*
                 * Found a reply activity with a valid request activity - see if
                 * it is included in the list of upstream activity id's.
                 */
                Set<String> upstream =
                        flowAnalyser.getUpstreamActivityIds(act.getId());

                /*
                 * Sid XPD-2109. The flow analyser does not treat attached
                 * events as downstream / upstream (which is usually the desired
                 * behaviour. However, in the situation that the request
                 * activity event is attached to task it means that the catch
                 * event itself will not be included in the list of upstream
                 * activities.
                 * 
                 * So if the request activity is event attached to task boundary
                 * we should check whether that task is in the upstream
                 * activities.
                 */
                String requestOrBoundaryTask = reqAct.getId();
                if (EventObjectUtil.isAttachedToTask(reqAct)) {
                    requestOrBoundaryTask =
                            EventObjectUtil.getTaskIdAttachedTo(reqAct);
                }

                if (!upstream.contains(requestOrBoundaryTask)) {
                    doAddIssue(ISSUE_TYPE.REQUEST_NOT_UPSTREAM_OF_REPLY,
                            act,
                            flowAnalyser);
                }
            }
        }

        return;
    }

    /**
     * Check that all error events that implement process interface errors in
     * the given container are downstream of implemented start / intermediate
     * event that is the parent of that error.
     * 
     * @param container
     * @param flowAnalyser
     */
    private void checkRequestUpstreamOfImplementingError(
            FlowContainer container, ProcessFlowAnalyser flowAnalyser) {
        Process process = Xpdl2ModelUtil.getProcess(container);
        if (process != null) {

            for (Activity act : container.getActivities()) {
                //
                // Check if this is an activity that implements a process
                // interface error method.
                if (act.getEvent() instanceof EndEvent
                        && ResultType.ERROR_LITERAL.equals(((EndEvent) act
                                .getEvent()).getResult())) {

                    ErrorMethod errorMethod =
                            ProcessInterfaceUtil.getImplementedErrorMethod(act);
                    if (errorMethod != null) {
                        //
                        // Yup, it is. Now find the implementing event for the
                        // error's parent start/intermediate method.
                        if (errorMethod.eContainer() instanceof InterfaceMethod) {
                            InterfaceMethod parentMethod =
                                    (InterfaceMethod) errorMethod.eContainer();

                            Activity parentImplementingEvent =
                                    ProcessInterfaceUtil
                                            .getImplementingActivity(process,
                                                    parentMethod.getId());
                            if (parentImplementingEvent != null
                                    && (parentImplementingEvent.getEvent() instanceof StartEvent || parentImplementingEvent
                                            .getEvent() instanceof IntermediateEvent)) {
                                // Check that the parent method implementing
                                // start/event is upstream of the error method.
                                Set<String> upstream =
                                        flowAnalyser.getUpstreamActivityIds(act
                                                .getId());
                                if (!upstream.contains(parentImplementingEvent
                                        .getId())) {
                                    doAddIssue(ISSUE_TYPE.REQUESTIMPLEMENTOR_NOT_UPSTREAM_OF_ERROR,
                                            act,
                                            Collections
                                                    .singletonList(Xpdl2ModelUtil
                                                            .getDisplayNameOrName(parentMethod)),
                                            flowAnalyser);
                                }

                            } else {
                                // It is not up to us to complain if the parent
                                // method implementor is missing.
                            }
                        }
                    }
                }
            }
        }
        return;
    }

    /**
     * Check for parallel joins with sequence flows coming in from activities
     * downstream.
     * 
     * @param container
     * @param transitionCache
     */
    private void checkParallelJoinFromDownstream(FlowContainer container,
            ProcessFlowAnalyser transitionCache) {
        //
        // Linking back from a downstream activity may seem easy to detect when
        // visually looking at a diagram. However, this is purely (and sometimes
        // misleadingly) because we can often 'see' the diagram flow running
        // from left to right and the 'loop back flow' running right to left.
        //
        // However, just looking at the activities and transition/to&from in the
        // model is a different matter. You can't just say
        // "if I path follow from activity A thru to activity Z then Z must be downstream of A"
        // .
        // This is because, when there is a loop back flow then Z can be
        // downstream of A and A can also be downstream of Z.
        //
        // Therefore we must be careful how we check this rule so that we do not
        // complain unnecessarily. The basic principle will be to find all the
        // parallel joins then for each one...
        // - Fetch a list of all activities upstream of the parallel join (by
        // path-following from all activities with no incoming flow to see
        // whether we reach the parallel gate).
        // - Ensure that all parallel join gate incoming flow is from one of the
        // upstream activities.
        //

        //
        // Get a list of parallel gateways
        //
        List<Activity> paraGates = getParallelGates(container);

        if (!paraGates.isEmpty()) {
            //
            // Pick out just the gates with multiple incoming.
            //
            for (Iterator<Activity> iter = paraGates.iterator(); iter.hasNext();) {
                Activity paraGate = iter.next();

                if (transitionCache.getIncomingTransitions(paraGate.getId())
                        .size() < 2) {
                    // < 2 incoming so it's not a join.
                    iter.remove();
                }
            }

            if (!paraGates.isEmpty()) {
                //
                // Evaluate each parallel join in turn
                //
                for (Activity paraGate : paraGates) {
                    evaluateParallelJoin(paraGate, transitionCache, container);
                }
            }
        }
    }

    /**
     * Check for parallel joins with sequence flows coming in from activities
     * downstream.
     * 
     * @param container
     * @param transitionCache
     */
    private void checkInclusiveJoinFromDownstream(FlowContainer container,
            ProcessFlowAnalyser transitionCache) {

        /*
         * See checkParallelJoinFromDownstream(...) for more details.
         */
        //
        // Get a list of parallel gateways
        //
        List<Activity> inclGates = getInclusiveGates(container);

        if (!inclGates.isEmpty()) {
            //
            // Pick out just the gates with multiple incoming.
            //
            for (Iterator<Activity> iter = inclGates.iterator(); iter.hasNext();) {
                Activity paraGate = iter.next();

                if (transitionCache.getIncomingTransitions(paraGate.getId())
                        .size() < 2) {
                    // < 2 incoming so it's not a join.
                    iter.remove();
                }
            }

            if (!inclGates.isEmpty()) {
                //
                // Evaluate each inclusive join in turn
                //
                for (Activity inclusiveGate : inclGates) {
                    evaluateInclusiveJoin(inclusiveGate,
                            transitionCache,
                            container);
                }
            }
        }
    }

    /**
     * @param paraGate
     * @param transitionCache
     * @param container
     */
    private void evaluateParallelJoin(Activity paraGate,
            ProcessFlowAnalyser transitionCache, FlowContainer container) {

        //
        // Calculate the upstream activities - these are everything in flow
        // between the start activities and the parallel gateway.
        //
        Set<String> upstreamActivityIds =
                transitionCache.getUpstreamActivityIds(paraGate.getId());

        //        System.out.println("  Acts Upstream Of: " + paraGate.getId()+ " (" + paraGate.getName() + ")..."); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
        // for (String actId : upstreamActivityIds) {
        // Activity act = container.getActivity(actId);
        //            System.out.println("    " + actId + " (" + act.getName() + ")"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
        // }

        //
        // Ok, all we have to do now is check that all the incoming
        // transitions are from upstream activities.
        //
        Set<Transition> incomingTransitions =
                new HashSet<Transition>(
                        transitionCache.getIncomingTransitions(paraGate.getId()));

        for (Transition t : incomingTransitions) {
            if (!upstreamActivityIds.contains(t.getFrom())) {
                // System.out.println(" *** Downstream join flow: "+t.getId() +
                // "("+t.getName());
                doAddIssue(ISSUE_TYPE.PARALLEL_JOIN_FROM_DOWNSTREAM,
                        t,
                        transitionCache);
            }
        }

        return;
    }

    /**
     * @param inclGate
     * @param transitionCache
     * @param container
     */
    private void evaluateInclusiveJoin(Activity inclGate,
            ProcessFlowAnalyser transitionCache, FlowContainer container) {

        //
        // Calculate the upstream activities - these are everything in flow
        // between the start activities and the inclusive gateway.
        //
        Set<String> upstreamActivityIds =
                transitionCache.getUpstreamActivityIds(inclGate.getId());

        //
        // Ok, all we have to do now is check that all the incoming
        // transitions are from upstream activities.
        //
        Set<Transition> incomingTransitions =
                new HashSet<Transition>(
                        transitionCache.getIncomingTransitions(inclGate.getId()));

        for (Transition t : incomingTransitions) {
            if (!upstreamActivityIds.contains(t.getFrom())) {
                doAddIssue(ISSUE_TYPE.INCLUSIVE_JOIN_FROM_DOWNSTREAM,
                        t,
                        transitionCache);
            }
        }

        return;
    }

    /**
     * Add the given problem marker issue to the given target object.
     * <p>
     * Provides sub-class the final ability to ignore errors allowing to allow
     * for special cases for that destination env.
     * 
     * @param issueType
     * @param targetObject
     * @param flowAnalyser
     */
    protected void doAddIssue(ISSUE_TYPE issueType, EObject targetObject,
            ProcessFlowAnalyser flowAnalyser) {
        addIssue(getIssueId(issueType), targetObject);
    }

    /**
     * Add the given problem makre issue to the given target object.
     * <p>
     * Provides sub-class the final ability to ignore errors allowing to allow
     * for special cases for that desitnation env.
     * 
     * @param issueType
     * @param targetObject
     * @param flowAnalyser
     */
    protected void doAddIssue(ISSUE_TYPE issueType, EObject targetObject,
            List<String> messages, ProcessFlowAnalyser flowAnalyser) {
        String issueId = getIssueId(issueType);
        if (issueId != null && issueId.length() > 0) {
            addIssue(issueId, targetObject, messages);
        }
    }

    /**
     * Get all the parallel gateways from the given container.
     * 
     * @param container
     * @return all the parallel gateways.
     */
    private List<Activity> getParallelGates(FlowContainer container) {
        return getGates(container,
                JoinSplitType.PARALLEL_LITERAL,
                JoinSplitType.DEPRECATED_AND_LITERAL);
    }

    /**
     * Get all the inclusive gateways from the given container.
     * 
     * @param container
     * @return all the parallel gateways.
     */
    private List<Activity> getInclusiveGates(FlowContainer container) {
        return getGates(container,
                JoinSplitType.INCLUSIVE_LITERAL,
                JoinSplitType.DEPRECATED_OR_LITERAL);
    }

    /**
     * Get all the inclusive gateways from the given container.
     * 
     * @param container
     * @param typesToLookFor
     *            types of gates to look for
     * @return all the parallel gateways.
     */
    private List<Activity> getGates(FlowContainer container,
            JoinSplitType... typesToLookFor) {

        List<Activity> gates = new ArrayList<Activity>();

        for (Activity act : container.getActivities()) {
            Route route = act.getRoute();
            if (route != null) {
                JoinSplitType type = route.getGatewayType();

                for (JoinSplitType typeToLookFor : typesToLookFor) {
                    if (typeToLookFor.equals(type)) {
                        gates.add(act);
                        break;
                    }
                }
            }
        }

        return gates;
    }

}
