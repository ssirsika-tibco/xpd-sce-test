/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Transition;

/**
 * This class build up a map of transitions in and out of objects allowing for
 * easy navigation through the process.
 * 
 * @author NWilson
 */
public class TransitionTool implements IPreProcessor {
    /** The process to be analysed. */
    private FlowContainer container;

    /** The id of the start activity. */
    private String startId;

    /** Map of sources to ArrayList of destinations. */
    private Map<String, Collection<String>> outputs;

    /** Map of destinations to ArrayList of sources. */
    private Map<String, Collection<String>> inputs;

    /** A list of all of the activity ids in the process. */
    private List<String> activityList;

    /**
     * A list of conditional transitions, lazily instantiated by
     * isConditional().
     */
    private List<FromTo> conditionals;

    /**
     * Constructor initialises the tool with a map of transitions.
     * 
     * @param container The flow container containing the transitions to map.
     */
    public TransitionTool(FlowContainer container) {
        this.container = container;
        outputs = new HashMap<String, Collection<String>>();
        inputs = new HashMap<String, Collection<String>>();
        activityList = new ArrayList<String>();
        Activity start = getStart(container);
        if (start != null) {
            startId = start.getId();
            List transitionList = container.getTransitions();
            for (Iterator i = transitionList.iterator(); i.hasNext();) {
                Transition transition = (Transition) i.next();
                String from = transition.getFrom();
                String to = transition.getTo();
                Collection<String> destinations = outputs.get(from);
                if (destinations == null) {
                    destinations = new ArrayList<String>();
                    outputs.put(from, destinations);
                }
                destinations.add(to);
                Collection<String> sources = inputs.get(to);
                if (sources == null) {
                    sources = new ArrayList<String>();
                    inputs.put(to, sources);
                }
                sources.add(from);
            }
            calculateNavigableActivities();
            removeUnreachableTransitions();
        }
    }

    /**
     * Calculates a list of activities hat can be navigated to from the start
     * actvity.
     */
    private void calculateNavigableActivities() {
        addNavigableActivity(activityList, startId);
    }

    /**
     * @param activityList The navigable activity list.
     * @param id The current location.
     */
    private void addNavigableActivity(List<String> activityList, String id) {
        if (!activityList.contains(id)) {
            activityList.add(id);
            String[] destinations = getDestinations(id);
            if (destinations != null) {
                for (int i = 0; i < destinations.length; i++) {
                    addNavigableActivity(activityList, destinations[i]);
                }
            }
        }
    }

    /**
     * Cleans up navigation details for unreachable activites.
     */
    private void removeUnreachableTransitions() {
        for (Iterator i = outputs.keySet().iterator(); i.hasNext();) {
            String activity = (String) i.next();
            if (!activityList.contains(activity)) {
                i.remove();
            }
        }
        for (Iterator i = inputs.keySet().iterator(); i.hasNext();) {
            String activity = (String) i.next();
            if (!activityList.contains(activity)) {
                i.remove();
            }
        }
    }

    /**
     * @return A list of navigable activities in the process.
     */
    public String[] getActivities() {
        String[] activities = new String[activityList.size()];
        activityList.toArray(activities);
        return activities;
    }

    /**
     * @return The id of the start event.
     */
    public String getStart() {
        return startId;
    }

    /**
     * Get a list of destination ids from a source id.
     * 
     * @param source The id of the source activity.
     * @return An array of destination activity ids.
     */
    public String[] getDestinations(String source) {
        String[] destinations = new String[0];
        Collection<String> list = outputs.get(source);
        if (list != null) {
            destinations = new String[list.size()];
            list.toArray(destinations);
        }
        return destinations;
    }

    /**
     * Get a list of source ids from a destination id.
     * 
     * @param destination The id of the destination activity.
     * @return An array of source activity ids.
     */
    public String[] getSources(String destination) {
        String[] sources = new String[0];
        Collection<String> list = inputs.get(destination);
        if (list != null) {
            sources = new String[list.size()];
            list.toArray(sources);
        }
        return sources;
    }

    /**
     * Identifies a unique start activity. If no start activity exists, or if
     * there is more than one, null is returned.
     * 
     * @param container The workflow process containing the start.
     * @return The unique start activity or null.
     */
    private Activity getStart(FlowContainer container) {
        Activity start = null;
        List contents = container.eContents();
        for (Iterator j = contents.iterator(); j.hasNext();) {
            Object nextj = j.next();
            if (nextj instanceof Activity) {
                Activity activity = (Activity) nextj;
                Event event = activity.getEvent();
                if (event instanceof StartEvent) {
                    if (start == null) {
                        start = activity;
                    } else {
                        start = null;
                        break;
                    }
                }
            }
        }
        return start;
    }

    /**
     * Checks if a transition between two activities is conditional.
     * 
     * @param from The source activity.
     * @param to The destination activity.
     * @return true if the transition is conditional, otherwise false.
     */
    public boolean isConditional(String from, String to) {
        if (conditionals == null) {
            conditionals = new ArrayList<FromTo>();
            List transitionList = container.getTransitions();
            for (Iterator i = transitionList.iterator(); i.hasNext();) {
                Transition transition = (Transition) i.next();
                if (transition.getCondition() != null) {
                    conditionals.add(new FromTo(transition.getFrom(),
                            transition.getTo()));
                }
            }
        }
        return conditionals.contains(new FromTo(from, to));
    }

    /**
     * Container class for transition source and destination activites.
     *
     * @author nwilson
     */
    class FromTo {
        /** The <i>from</i> activity ID. */
        private String from;

        /** The <i>to</i> activity ID. */
        private String to;

        /**
         * @param from The <i>from</i> activity ID.
         * @param to The <i>to</i> activity ID.
         */
        public FromTo(String from, String to) {
            this.from = from;
            this.to = to;
        }

        /**
         * @param obj The object to test.
         * @return true if equal.
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof FromTo) {
                FromTo other = (FromTo) obj;
                if ((from == null && other.from == null)
                        || from.equals(other.from)) {
                    if ((to == null && other.to == null) || to.equals(other.to)) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * @return The combined hashcodes of the from and to IDs.
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            return from.hashCode() + to.hashCode();
        }
    }

    /**
     * @return The flow container being analysed.
     */
    public FlowContainer getFlowContainer() {
        return container;
    }

}
