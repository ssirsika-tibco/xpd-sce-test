package com.tibco.xpd.xpdl2.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerResultLink;

/**
 * ProcessFlowUtil
 * <p>
 * Little load on demand cache of activities and their incoming/outgoing
 * transitions. With some useful flow related utility methods.
 * 
 * @author aallway
 */
public class ProcessFlowAnalyser {

    private Map<String, Activity> activityIdToActivityCache =
            new HashMap<String, Activity>();

    private Map<String, List<Transition>> activityIdToIncomingCache =
            new HashMap<String, List<Transition>>();

    private Map<String, List<Activity>> activityIdToIncomingActivityCache =
            new HashMap<String, List<Activity>>();

    private Map<String, List<Transition>> activityIdToOutgoingCache =
            new HashMap<String, List<Transition>>();

    private Map<String, List<Activity>> activityIdToOutgoingActivityCache =
            new HashMap<String, List<Activity>>();

    private Map<String, Collection<Activity>> activityIdToAttachedEventsCache =
            new HashMap<String, Collection<Activity>>();

    private List<Activity> startActivitiesCache = null;

    private boolean includeExceptionFlow = false;

    private FlowContainer flowContainer;

    /**
     * @param includeExceptionFlow
     *            true = treat outgoing transitions from events attached to task
     *            boundaries as if they were outgoing from the task itself.
     *            (i.e. getOutgoingTransitions(task) would include outgoing
     *            transitions of any attached events).
     */
    public ProcessFlowAnalyser(boolean includeExceptionFlow,
            FlowContainer flowContainer) {
        this.includeExceptionFlow = includeExceptionFlow;
        this.flowContainer = flowContainer;

        for (Activity act : flowContainer.getActivities()) {
            activityIdToActivityCache.put(act.getId(), act);
        }
    }

    /**
     * @param activityId
     * @return List of transitions incoming to the given activity.
     */
    public List<Transition> getIncomingTransitions(String activityId) {
        List<Transition> trans = null;

        if (activityId != null) {

            trans = activityIdToIncomingCache.get(activityId);
            if (trans == null) {
                // Not in cache yet, cache and continue.
                cacheSequenceFlowInfoForActivity(activityId);
                trans = activityIdToIncomingCache.get(activityId);
            }
        }

        return trans != null ? trans : Collections.EMPTY_LIST;
    }

    /**
     * Get a list of the activities that process the given activity.
     * <p>
     * <b>Note: The important distinction between this method and
     * getIncomingTransitions() is that if the activity is a catch link event
     * then the throw link events that 'process' it are returned.</b>
     * 
     * @param activityId
     * 
     * @return List of activities that the given activity is processed as a
     *         result of.
     */
    public List<Activity> getIncomingActivities(String activityId) {
        //
        // See if we have already cached.
        List<Activity> incomingActs = Collections.emptyList();

        if (activityId != null) {
            incomingActs = activityIdToIncomingActivityCache.get(activityId);

            if (incomingActs == null) {
                //
                // Cache the incoming activities.
                List<Transition> trans = getIncomingTransitions(activityId);
                if (trans != null) {
                    for (Transition t : trans) {
                        Activity fromAct =
                                activityIdToActivityCache.get(t.getFrom());
                        if (fromAct != null) {
                            if (incomingActs == null) {
                                incomingActs = new ArrayList<Activity>();
                            }
                            incomingActs.add(fromAct);
                        }
                    }
                }

                //
                // If this activity is a catch link event then add the source
                // throw link event(s)
                Activity activity = activityIdToActivityCache.get(activityId);
                if (activity != null) {
                    if (activity.getEvent() instanceof IntermediateEvent) {
                        IntermediateEvent event =
                                (IntermediateEvent) activity.getEvent();
                        if (event.getTriggerResultLink() != null
                                && CatchThrow.CATCH
                                        .equals(event.getTriggerResultLink()
                                                .getCatchThrow())) {
                            String catchId = activity.getId();

                            // Look for throw link event linked to this catch.
                            for (Activity act : flowContainer.getActivities()) {
                                if (activity.getEvent() instanceof IntermediateEvent) {
                                    TriggerResultLink link =
                                            ((IntermediateEvent) activity
                                                    .getEvent())
                                                    .getTriggerResultLink();
                                    if (link != null
                                            && CatchThrow.THROW.equals(link
                                                    .getCatchThrow())) {
                                        if (catchId.equals(link.getName())) {
                                            if (incomingActs == null) {
                                                incomingActs =
                                                        new ArrayList<Activity>();
                                            }
                                            incomingActs.add(act);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (incomingActs == null) {
                    incomingActs = Collections.emptyList();
                }

                activityIdToIncomingActivityCache.put(activityId, incomingActs);
            }
        }

        return incomingActs;
    }

    /**
     * @param activityId
     * 
     * @return List of transitions outgoing from the given activity (and
     *         optionally
     */
    public List<Transition> getOutgoingTransitions(String activityId) {
        List<Transition> trans = null;

        if (activityId != null) {

            trans = activityIdToOutgoingCache.get(activityId);
            if (trans == null) {
                // Not in cache yet, cache and continue.
                cacheSequenceFlowInfoForActivity(activityId);
                trans = activityIdToOutgoingCache.get(activityId);
            }

        }

        return trans != null ? trans : Collections.EMPTY_LIST;
    }

    /**
     * Get a list of the activities that are processed as a result of the
     * outgoing transitions of the given activity.
     * <p>
     * <b>Note: The important distinction between this method and
     * getOutgoingTransitions() is that if the activity is a throw link event
     * then the catch link event it 'processes' is returned.</b>
     * 
     * @param activityId
     * 
     * @return List of activities that are processed as a result of the given
     *         activity.
     */
    public List<Activity> getOutgoingActivities(String activityId) {
        return getOutgoingActivities(activityId, false);
    }

    /**
     * Get a list of the activities that are processed as a result of the
     * outgoing transitions of the given activity.
     * <p>
     * <b>Note: The important distinction between this method and
     * getOutgoingTransitions() is that if the activity is a throw link event
     * then the catch link event it 'processes' is returned.</b>
     * <p>
     * Using excludeUpstreamInLoop=true will prevent downstream activities that
     * are also upstream of the given actvity from being included in the list.
     * 
     * @param activityId
     * @param excludeUpstreamInLoop
     * 
     * @return List of activities that are processed as a result of the given
     *         activity.
     */
    public List<Activity> getOutgoingActivities(String activityId,
            boolean excludeUpstreamInLoop) {
        /*
         * Sid XPD-4198. Removed enforcement of includeExceptionFlow=true
         * exception because
         * 
         * (a) it is only relevant if excludeUpstreamInLoop=true and It is
         * checked in getUpstreamActivityIds() if that is called anyway.
         * 
         * (b) the check here made the old getOutgoingActivities(activiyId)
         * method shell incompatible with previous version (because it didn't
         * used to check for includeExceptionFlow.
         */

        //
        // See if we have already cached.
        List<Activity> outgoingActs = Collections.emptyList();

        if (activityId != null) {
            outgoingActs = activityIdToOutgoingActivityCache.get(activityId);

            if (outgoingActs == null) {
                //
                // Cache the outgoing activities.
                List<Transition> trans = getOutgoingTransitions(activityId);
                if (trans != null) {
                    for (Transition t : trans) {
                        Activity toAct =
                                activityIdToActivityCache.get(t.getTo());
                        if (toAct != null) {
                            if (outgoingActs == null) {
                                outgoingActs = new ArrayList<Activity>();
                            }
                            outgoingActs.add(toAct);
                        }
                    }
                }

                //
                // If this activity is a throw link event then add the target.
                Activity activity = activityIdToActivityCache.get(activityId);
                if (activity != null) {
                    if (activity.getEvent() instanceof IntermediateEvent) {
                        IntermediateEvent event =
                                (IntermediateEvent) activity.getEvent();
                        TriggerResultLink link = event.getTriggerResultLink();
                        if (link != null
                                && CatchThrow.THROW
                                        .equals(link.getCatchThrow())) {
                            String catchLinkId = link.getName();
                            if (catchLinkId != null) {
                                Activity catchLinkAct =
                                        activityIdToActivityCache
                                                .get(catchLinkId);
                                if (catchLinkAct != null) {
                                    if (outgoingActs == null) {
                                        outgoingActs =
                                                new ArrayList<Activity>();
                                    }
                                    outgoingActs.add(catchLinkAct);
                                }
                            }
                        }
                    }
                }

                if (outgoingActs == null) {
                    outgoingActs = Collections.emptyList();
                }

                activityIdToOutgoingActivityCache.put(activityId, outgoingActs);

            }

            if (excludeUpstreamInLoop && outgoingActs.size() > 0) {
                //
                // When excluding actvities that are also upstream then get and
                // remove the upstream actvities.
                List<Activity> oldOutgoingActs = outgoingActs;
                outgoingActs = new ArrayList<Activity>();

                Set<String> upstreamActivities =
                        getUpstreamActivityIds(activityId);

                for (Activity activity : oldOutgoingActs) {
                    if (!upstreamActivities.contains(activity.getId())) {
                        outgoingActs.add(activity);
                    }
                }
            }

        }

        return outgoingActs;
    }

    /**
     * Get list of ids of activities that are upstream of the given activity.
     * <p>
     * <b>For this method to work correctly you MUST construct this class with
     * includeExceptionFlow=true</b>
     * 
     * @param activityId
     * 
     * @return list of ids of activities that are upstream of the given
     *         activity.
     */
    public Set<String> getUpstreamActivityIds(String activityId) {
        if (!includeExceptionFlow) {
            throw new IllegalStateException(
                    "The class should be contructed with includeExceptionFlow=true when getUpstreamActivityIds() is used."); //$NON-NLS-1$
        }

        Set<String> upstreamActivities = new HashSet<String>();
        Map<String, Boolean> alreadyProcessedAndIsDownstreamResult =
                new HashMap<String, Boolean>();

        //
        // Get a list of start activities.
        //
        List<Activity> startActs = getStartOfFlowActivities();

        //
        // Calculate the upstream activities - these are everything in flow
        // between the start activities and the given activity
        //

        // Starting from each of the possible start activities, see if the given
        // activity is anywhere downstream of it and if it is the add the list
        // of activities between them into the upstreamActivities list.
        for (Activity startAct : startActs) {
            getActivitiesBetween(startAct.getId(),
                    activityId,
                    upstreamActivities,
                    alreadyProcessedAndIsDownstreamResult);
        }

        // Activity sa = flowContainer.getActivity(activityId);
        //        System.out.println("Activities upstream of: "+activityId+"  ("+sa.getName()+")");   //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
        // for (String id : upstreamActivities)
        // {
        // Activity act = flowContainer.getActivity(id);
        //            System.out.println("    "+id+"  ("+act.getName()+")");   //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
        // }
        //        System.out.println("================================================\n"); //$NON-NLS-1$

        return upstreamActivities;
    }

    /**
     * Get list of activities that are upstream of the given activity.
     * <p>
     * <b>For this method to work correctly you MUST construct this class with
     * includeExceptionFlow=true</b>
     * 
     * @param activityId
     * 
     * @return list of activities that are upstream of the given activity.
     */
    public Set<Activity> getUpstreamActivities(String activityId) {
        Set<Activity> upstreamActivities = new HashSet<Activity>();

        Set<String> upstreamIds = getUpstreamActivityIds(activityId);
        for (String id : upstreamIds) {
            upstreamActivities.add(activityIdToActivityCache.get(id));
        }

        return upstreamActivities;
    }

    /**
     * Get list of ids of activities that are downstream of the given activity.
     * <p>
     * <b>For this method to work correctly you MUST construct this class with
     * includeExceptionFlow=true</b>
     * </p>
     * <p>
     * Nominally, when there is a loop-back in the flow, then some of the
     * activities that are processed upstream of the given activity can ALSO be
     * processed downstream of the given activity.
     * </p>
     * <p>
     * Using excludeUpstreamInLoop=true will prevent downstream activities that
     * are also upstream of the given actvity from being included in the list.
     * e.g. in a process that looks like this...
     * </p>
     * <p>
     * (Start)---->[A]----->[B]----->[C]----->[D]----->[Z]-->(End) ^ |
     * |<-------[F]-----[E]------|
     * </p>
     * If you do getDownstreamActivities([D], false) - then the list will
     * contain <li>[Z], (End), [E], [F], <b>[B], [C]</b></li>
     * <p>
     * If you do getDownstreamActivities([D], <b>true</b>) - then the list will
     * contain
     * <li>[Z], (End), [E], [F]</li>
     * 
     * @param activityId
     * @param excludeUpstreamInLoop
     * 
     * @return list of ids of activities that are upstream of the given
     *         activity.
     */
    public Set<String> getDownstreamActivityIds(String activityId,
            boolean excludeUpstreamInLoop) {
        if (!includeExceptionFlow) {
            throw new IllegalStateException(
                    "The class should be contructed with includeExceptionFlow=true when getDownstreamActivityIds() is used."); //$NON-NLS-1$
        }

        Set<String> downstreamActivities = new HashSet<String>();
        Set<String> alreadyProcessed = new HashSet<String>();

        /*
         * SID SNA-1. When excluding upstream activities we need NOT recurs into
         * ANY outgoing flow from ANY upstream activity.
         * 
         * We USED to simply remove the set of upstream activity ids from the
         * TOTAL set of downstream activities (including items upstream in a
         * loop back from a given activity.
         * 
         * However, if you have a loop back to a previous activity and after
         * that activity there is a BRANCH out of the loop then the objects on
         * that branch will NOT be upstream of the given activity and therefore
         * would still be included.
         * 
         * So NOW we will pass in a list of activities to STOP at when
         * recursively finding downstream activities (that in a loop may also be
         * upstream).
         */
        Set<String> upstreamActivities = Collections.emptySet();
        if (excludeUpstreamInLoop) {
            //
            // When excluding actvities that are also upstream then get and
            // remove the upstream actvities.
            upstreamActivities = getUpstreamActivityIds(activityId);
        }

        //
        // First of all, get all the activities that might be processed by
        // following outgoing flow.
        recursiveaddDownstreamActivityIds(activityId,
                downstreamActivities,
                alreadyProcessed,
                upstreamActivities);

        return downstreamActivities;
    }

    /**
     * Get list of activities that are downstream of the given activity.
     * <p>
     * <b>For this method to work correctly you MUST construct this class with
     * includeExceptionFlow=true</b>
     * </p>
     * <p>
     * Nominally, when there is a loop-back in the flow, then some of the
     * activities that are processed upstream of the given activity can ALSO be
     * processed downstream of the given activity.
     * </p>
     * <p>
     * Using excludeUpstreamInLoop=true will prevent downstream activities that
     * are also upstream of the given actvity from being included in the list.
     * e.g. in a process that looks like this...
     * </p>
     * 
     * <pre>
     * (Start)---->[A]----->[B]----->[C]----->[D]----->[Z]-->(End) ^ |
     *                       |<-------[F]-----[E]------|
     * </pre>
     * 
     * If you do getDownstreamActivities([D], false) - then the list will
     * contain <li>[Z], (End), [E], [F], <b>[B], [C]</b></li>
     * <p>
     * If you do getDownstreamActivities([D], <b>true</b>) - then the list will
     * contain
     * <li>[Z], (End), [E], [F]</li>
     * 
     * @param activityId
     * @param excludeUpstreamInLoop
     * 
     * @return list of activities that are upstream of the given activity.
     */
    public Set<Activity> getDownstreamActivities(String activityId,
            boolean excludeUpstreamInLoop) {
        Set<Activity> downstreamActivities = new HashSet<Activity>();

        Set<String> downstreamIds =
                getDownstreamActivityIds(activityId, excludeUpstreamInLoop);
        for (String id : downstreamIds) {
            downstreamActivities.add(activityIdToActivityCache.get(id));
        }

        return downstreamActivities;
    }

    /**
     * @param activity
     * @return List of events attached to the given activity.
     */
    public Collection<Activity> getAttachedEvents(Activity activity) {
        Collection<Activity> attachedEvents =
                activityIdToAttachedEventsCache.get(activity.getId());

        if (attachedEvents == null) {
            attachedEvents = Xpdl2ModelUtil.getAttachedEvents(activity);

            activityIdToAttachedEventsCache.put(activity.getId(),
                    attachedEvents);
        }

        return attachedEvents;
    }

    /**
     * Get all the activities between (in a downstream direction) the
     * fromActivityId and the toActivityId
     * <p>
     * <b>NOTE:</b> this method is recursive and will NOT count inner loops.
     * <p>
     * <b>NOTE: 2: This method will NOT find innerloops within a loopback flow.
     * i.e. will NOT return find [D] when asking for activities between [A] and
     * [X] (where C->D->B forms a loopback flow)
     * 
     * <pre>
     *     [A]---[B]--------[C]-------[X]
     *            |          |
     *            -----[D]---|
     * </pre>
     * 
     * 
     * @param fromActivityId
     * @param toActivityId
     * @param activitiesBetween
     * @param alreadyProcessedAndIsDownstreamResult
     * 
     * @return true if the endId activity is processed (directly or indirectly)
     *         downstream of the fromId activity.
     */
    private boolean getActivitiesBetween(String fromActivityId,
            String toActivityId, Set<String> activitiesBetween,
            Map<String, Boolean> alreadyProcessedAndIsDownstreamResult) {
        boolean isDownstream = false;

        //
        // This method recurses along the flow until it finds the endId
        // activity then returns true after adding the fromId to the list of
        // actsBetween.

        // Prevent infinite loops (if we have already processed the given from
        // object then quit now. And don't want to include endId activity in
        // list.
        if (!alreadyProcessedAndIsDownstreamResult.containsKey(fromActivityId)
                && !fromActivityId.equals(toActivityId)) {
            alreadyProcessedAndIsDownstreamResult.put(fromActivityId, null);

            // Use getOutgoingActivities() to take throw/catch link event pairs
            // into account
            List<Activity> outgoing = getOutgoingActivities(fromActivityId);
            for (Activity out : outgoing) {
                if (toActivityId.equals(out.getId())) {
                    // Found the final target toActivity
                    isDownstream = true;
                } else {

                    // Activity from =
                    // activityIdToActivityCache.get(fromActivityId);
                    //                    System.out.println("From@: "+from.getName()); //$NON-NLS-1$

                    // Recurs thru this outgoing activity
                    if (getActivitiesBetween(out.getId(),
                            toActivityId,
                            activitiesBetween,
                            alreadyProcessedAndIsDownstreamResult)) {
                        // If something downstream of us eventually processes
                        // final target then final is also downstream of us.
                        isDownstream = true;
                    }
                }
            }

            // If we found that endId is downstream of this fromId (or a
            // something that this fromId flows to) then add this fromId as an
            // upstream object.
            if (isDownstream) {
                activitiesBetween.add(fromActivityId);
            }

            // Now we have the result, overwrite the entry in the
            // alreadyProcessed list. So that we hit this activity after
            // processing a separate branch then we can use the same result.
            alreadyProcessedAndIsDownstreamResult.put(fromActivityId,
                    isDownstream);

        } else {
            //
            // If we come across an element that has already been processed then
            // copy it's stored isDownStream result.
            // This is specifically for when you have a split and rejoin before
            // an activity before the required toActivityId
            Boolean result =
                    alreadyProcessedAndIsDownstreamResult.get(fromActivityId);
            if (result != null) {
                isDownstream = result;
            }
        }

        return isDownstream;
    }

    /**
     * Get all the activities between (in a downstream direction) the
     * fromActivityId and the toActivityId
     * <p>
     * <b>NOTE:</b> this method is recursive and will NOT count inner loops.
     * <p>
     * <b>NOTE: 2: This method will NOT find innerloops within a loopback flow.
     * i.e. will NOT return find [D] when asking for activities between [A] and
     * [X] (where C->D->B forms a loopback flow)
     * 
     * <pre>
     *     [A]---[B]--------[C]-------[X]
     *            |          |
     *            -----[D]---|
     * </pre>
     * <p>
     * 
     * @param fromActivityId
     * @param toActivityId
     * @param withoutTraversingActivitiesList
     *            If necessary, you can pass a list of activity id's that any
     *            search for downstream activities will "stop at" - therefore
     *            allowing you to define places where you do not wish to
     *            traverse passed (such as a start of loop activity).
     * @param listInDownstreamOrder
     *            true = Return the list in downstream order (slightly less
     *            efficient. false = return in upstream (ish) order.
     * 
     * @return <code>null</code> if the toActivity is not reachable downstream
     *         from the from activity. Otherwise a list of activities between
     *         from and to activity - <b>NOT INCLUSIVE!</b>. Uses LinkedHasSet
     *         in order to preserve order.
     */
    public Set<String> getActivitiesBetween(String fromActivityId,
            String toActivityId, Set<String> withoutTraversingActivitiesList,
            boolean listInDownstreamOrder) {
        LinkedHashSet<String> activitiesBetween = new LinkedHashSet<String>();

        HashMap<String, Boolean> alreadyProcessedAndIsDownstreamResult =
                new HashMap<String, Boolean>();

        if (withoutTraversingActivitiesList != null
                && withoutTraversingActivitiesList.size() > 0) {
            /*
             * If caller wishes not to traverse some particular activities then
             * just preset them as already processed.
             */
            for (String id : withoutTraversingActivitiesList) {
                alreadyProcessedAndIsDownstreamResult.put(id, false);
            }
        }

        if (getActivitiesBetween(fromActivityId,
                toActivityId,
                activitiesBetween,
                alreadyProcessedAndIsDownstreamResult)) {

            if (listInDownstreamOrder && activitiesBetween.size() > 0) {
                /*
                 * The internal getActivitiesBetween() method will return the
                 * activities in upstream order. (ish) baring in mind that
                 * alternative branches may be returned consecutively or even at
                 * end.If required then reverse this into downstream order.
                 */
                String[] tmpList = new String[activitiesBetween.size()];

                int idx = activitiesBetween.size() - 1;
                for (String s : activitiesBetween) {
                    tmpList[idx] = s;
                    idx--;
                }

                activitiesBetween.clear();

                for (String s : tmpList) {
                    activitiesBetween.add(s);
                }

            }

            return activitiesBetween;
        }

        return null;
    }

    /**
     * Get the downstream activities of the given activity (until we reach an
     * activity already processed).
     * 
     * @param activityId
     * @param downstreamActivities
     * @param alreadyProcessed
     * @param excludeUpstreamActivities
     *            Set of upstream activities to exclude AND stop processing at
     *            (to prevent any other branches from those activities being
     *            added to list of downstream activities.
     */
    private void recursiveaddDownstreamActivityIds(String activityId,
            Set<String> downstreamActivities, Set<String> alreadyProcessed,
            Set<String> excludeUpstreamActivities) {

        alreadyProcessed.add(activityId);

        // Use getOutgoingActivities() so that link events are counted as if
        // joined with a sequence flow.
        List<Activity> outgoing = getOutgoingActivities(activityId);
        for (Activity outAct : outgoing) {
            if (!alreadyProcessed.contains(outAct.getId())
                    && !excludeUpstreamActivities.contains(outAct.getId())) {
                downstreamActivities.add(outAct.getId());

                recursiveaddDownstreamActivityIds(outAct.getId(),
                        downstreamActivities,
                        alreadyProcessed,
                        excludeUpstreamActivities);
            }
        }
        return;
    }

    /**
     * @return a list of activities that are at the start of flow threads
     */
    public List<Activity> getStartOfFlowActivities() {
        if (startActivitiesCache == null) {
            startActivitiesCache = new ArrayList<Activity>();

            for (Activity act : flowContainer.getActivities()) {
                if (isStartOfFlowActivity(act)) {
                    startActivitiesCache.add(act);
                }
            }
        }
        return startActivitiesCache;
    }

    /**
     * @param act
     * @return true if the given activity is at the start of a flow thread.
     */
    public boolean isStartOfFlowActivity(Activity act) {
        if (getIncomingTransitions(act.getId()).isEmpty()) {
            if (!Xpdl2ModelUtil.isEventAttachedToTask(act)
                    && !isCatchLinkEvent(act)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param act
     * @return true if the given activity is a catch link event.
     */
    private boolean isCatchLinkEvent(Activity act) {
        if (act.getEvent() instanceof IntermediateEvent) {
            TriggerResultLink link =
                    ((IntermediateEvent) act.getEvent()).getTriggerResultLink();
            if (link != null) {
                if (CatchThrow.CATCH.equals(link.getCatchThrow())) {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * Cache the incoming and outgoing flow for the given activity.
     * 
     * @param activityId
     */
    private void cacheSequenceFlowInfoForActivity(String activityId) {
        Collection<Activity> attachedEvents = Collections.EMPTY_LIST;
        if (includeExceptionFlow) {
            attachedEvents =
                    getAttachedEvents(activityIdToActivityCache.get(activityId));
        }

        List<Transition> incoming = Collections.EMPTY_LIST;
        List<Transition> outgoing = Collections.EMPTY_LIST;

        for (Transition t : flowContainer.getTransitions()) {
            if (activityId.equals(t.getTo())) {
                if (incoming == Collections.EMPTY_LIST) {
                    incoming = new ArrayList<Transition>();
                }
                incoming.add(t);
            }

            if (activityId.equals(t.getFrom())) {
                if (outgoing == Collections.EMPTY_LIST) {
                    outgoing = new ArrayList<Transition>();
                }
                outgoing.add(t);
            }

            // Add exception flow (outgoing from attached events) to owner tasks
            // outgoing if originally requested.
            if (includeExceptionFlow) {
                for (Activity ev : attachedEvents) {
                    if (ev.getId().equals(t.getFrom())) {
                        if (outgoing == Collections.EMPTY_LIST) {
                            outgoing = new ArrayList<Transition>();
                        }
                        outgoing.add(t);
                    }
                }
            }
        }

        activityIdToOutgoingCache.put(activityId, outgoing);
        activityIdToIncomingCache.put(activityId, incoming);

        return;
    }

    /**
     * Test whether the given activity is positioned in a process flow loop.
     * 
     * @param activityId
     * 
     * @return True if the given activity is within a process flow loop
     *         (including first/last activity in loop.
     * 
     * @since MR 41085 - Performance issues selecting tasks when simulation
     *        destination set on for a complex processes.
     */
    public boolean isInLoop(String activityId) {
        if (!includeExceptionFlow) {
            throw new IllegalStateException(
                    "The class should be contructed with includeExceptionFlow=true when isInLoop() is used."); //$NON-NLS-1$
        }

        // Activity is in loop if it is processed 'down stream' of itself...
        return recursiveIsReachable(activityId,
                activityId,
                new HashSet<String>());
    }

    /**
     * Is the isReachableActivityId reachable by following the outgoing flow
     * from activityId.
     * <p>
     * Note that isReachableActivityId may be downstream OR may actually be
     * upstream BUT reachable via a loop-back flow.
     * <p>
     * If you wish to guarantee that isReachableActivityId is downstream then
     * you should use {@link #getDownstreamActivityIds(String, boolean)} or
     * {@link #getDownstreamActivities(String, boolean)} which allow you to
     * eliminate upstream activities from the result set.
     * 
     * @param activityId
     *            Starting from this activity
     * @param isDownstreamActivityId
     *            Is this activity reachable following outgoing flow?
     * 
     * @return true if the isReachableActivityId is reachable by following the
     *         outgoing flow from activityId.
     * 
     * @since MR 41085 - Performance issues selecting tasks when simulation
     *        destination set on for a complex processes.
     */
    public boolean isReachable(String activityId, String isReachableActivityId) {
        return recursiveIsReachable(activityId,
                isReachableActivityId,
                new HashSet<String>());
    }

    /**
     * Is the isReachableActivityId reachable by following the outgoing flow
     * from activityId.
     * <p>
     * Note that isReachableActivityId may be downstream OR may actually be
     * upstream BUT reachable via a loop-back flow.
     * <p>
     * If you wish to guarantee that isReachableActivityId is downstream then
     * you should use {@link #getDownstreamActivityIds(String, boolean)} or
     * {@link #getDownstreamActivities(String, boolean)} which allow you to
     * eliminate upstream activities from the result set.
     * 
     * @param activityId
     *            Starting from this activity
     * @param isReachableActivityId
     *            Is this activity reachable following outgoing flow?
     * @param withoutTraversingActivitiesList
     *            If necessary, you can pass a list of activity id's that any
     *            search for downstream activities will "stop at" - therefore
     *            allowing you to define places where you do not wish to
     *            traverse passed (such as a start of loop activity). * @return
     * 
     * @return true if the isReachableActivityId is reachable by following the
     *         outgoing flow from activityId.
     * 
     * @since MR 41085 - Performance issues selecting tasks when simulation
     *        destination set on for a complex processes.
     */
    public boolean isReachableFiltered(String activityId,
            String isReachableActivityId,
            Set<String> withoutTraversingActivitiesList) {

        /*
         * If caller wishes to not pass thru8 a set of activities (like start of
         * a loop for instance) then pre-load the "alreadyProcess" list.
         */
        Set<String> alreadyProcessed = new HashSet<String>();
        if (withoutTraversingActivitiesList != null
                && withoutTraversingActivitiesList.size() > 0) {
            alreadyProcessed.addAll(withoutTraversingActivitiesList);
        }

        return recursiveIsReachable(activityId,
                isReachableActivityId,
                alreadyProcessed);
    }

    /**
     * Is the isReachableActivityId reachable by following the outgoing flow
     * from activityId.
     * <p>
     * Note that isReachableActivityId may be downstream OR may actually be
     * upstream BUT reachable via a loop-back flow.
     * <p>
     * If you wish to guarantee that isReachableActivityId is downstream then
     * you should use {@link #getDownstreamActivityIds(String, boolean)} or
     * {@link #getDownstreamActivities(String, boolean)} which allow you to
     * eliminate upstream activities from the result set.
     * 
     * @param activityId
     *            Starting from this activity
     * @param isDownstreamActivityId
     *            Is this activity reachable following outgoing flow?
     * 
     * @return true if the isReachableActivityId is reachable by following the
     *         outgoing flow from activityId.
     * 
     * @since MR 41085 - Performance issues selecting tasks when simulation
     *        destination set on for a complex processes.
     */
    private boolean recursiveIsReachable(String activityId,
            String isReachableActivityId, Set<String> alreadyProcessed) {

        alreadyProcessed.add(activityId);

        // Use getOutgoingActivities() so that link events are counted as if
        // joined with a sequence flow.
        List<Activity> outgoing = getOutgoingActivities(activityId);

        for (Activity outAct : outgoing) {
            String outgoingActId = outAct.getId();
            if (isReachableActivityId.equals(outgoingActId)) {
                return true;
            }

            if (!alreadyProcessed.contains(outgoingActId)) {
                if (recursiveIsReachable(outgoingActId,
                        isReachableActivityId,
                        alreadyProcessed)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @param id
     * @return Activity with the given id.
     */
    public Activity getActivity(String id) {
        return activityIdToActivityCache.get(id);
    }

    /**
     * @return the flowContainer analyser was constructed with
     */
    public FlowContainer getFlowContainer() {
        return flowContainer;
    }

}
