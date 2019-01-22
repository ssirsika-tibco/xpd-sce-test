/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Associates splits with their corresponding joins such that the activities
 * contained by the split-join group link only to other activities within that
 * group. The only inputs and outputs to external activites should be from the
 * split or join activity.
 * 
 * @author nwilson
 */
public class SplitJoinGroupAnalyser implements IPreProcessor {
    /** The transition tool. */
    private TransitionTool tool;

    /** The loop grouper tool. */
    private LoopGrouper loopGrouper;

    /** A map of paths to split activies. */
    private HashMap<String, ArrayList<ArrayList<String>>> pathsToSplits;

    /** A map of split activities to path stacks. */
    private HashMap<String, ArrayList<ArrayList<String>>> splitPathStacks;

    /** A collection of identified SplitJoinGroups. */
    private ArrayList<SplitJoinGroup> groups;

    Map<String, Activity> actIdToAct = new HashMap<String, Activity>();
    
    /**
     * Constructor to initialise the analyser.
     * 
     * @param tool The transition tool used to assist with analysis of the
     *            groups.
     * @param loopGrouper The loop grouper tool.
     */
    public SplitJoinGroupAnalyser(TransitionTool tool, LoopGrouper loopGrouper) {
        for (Activity a : tool.getFlowContainer().getActivities()) {
            actIdToAct.put(a.getId(), a);
        }
        
        this.tool = tool;
        this.loopGrouper = loopGrouper;
        pathsToSplits = new HashMap<String, ArrayList<ArrayList<String>>>();
        splitPathStacks = new HashMap<String, ArrayList<ArrayList<String>>>();
        groups = new ArrayList<SplitJoinGroup>();
        createPathsToSplits();
        createSplitPathStacks();
        createGroups();
        addLoopGroups();
        removeUnclosedGroups();
    }

    /**
     * Identifies all splits in the process and stores the paths from the start
     * activity to each split activity.
     */
    private void createPathsToSplits() {
        System.out.println("createPathsToSplits()"); //$NON-NLS-1$
        String start = tool.getStart();
        ArrayList<String> stack = new ArrayList<String>();
        checkSplit(stack, start);
        System.out.println("<== createPathsToSplits()"); //$NON-NLS-1$
    }

    /**
     * For each split, creates a complete list of all paths from that split to
     * the end of the process. In the case of loops, the path ends when it
     * reaches an activity that has already been visited.
     */
    private void createSplitPathStacks() {
        String[] activities = tool.getActivities();
        for (int i = 0; i < activities.length; i++) {
            String[] destinations = tool.getDestinations(activities[i]);
            if (destinations.length > 1) {
                processSplit(activities[i]);
            }
        }
    }

    /**
     * Analyses all paths from each split and finds the first activity common to
     * those paths. This activity is the join corresponding to the split. A
     * group is then created containing the split, the join and all activites
     * between them.
     */
    private void createGroups() {
        for (Iterator i = splitPathStacks.keySet().iterator(); i.hasNext();) {
            String split = (String) i.next();
            boolean ignoreSplitSelfLoops = false;
            if (hasMultipleOtherDestinationActivities(split)) {
                ignoreSplitSelfLoops = true;
            }
            ArrayList<ArrayList<String>> paths = splitPathStacks.get(split);
            ArrayList<String> common = null;
            for (ArrayList<String> path : paths) {
                if (!(ignoreSplitSelfLoops && path.size() == 1 && path.get(0)
                        .equals(split))) {
                    if (common == null) {
                        common = new ArrayList<String>(path);
                    } else {
                        for (Iterator k = common.iterator(); k.hasNext();) {
                            if (!path.contains(k.next())) {
                                k.remove();
                            }
                        }
                    }
                }
            }
            int joinIndex = ignoreSplitSelfLoops ? 1 : 0;
            if (common != null && common.size() > joinIndex) {
                String join = (String) common.get(joinIndex);
                SplitJoinGroup group = new SplitJoinGroup(split, join);
                for (ArrayList<String> path : paths) {
                    group.add(path.subList(0, path.indexOf(join)));
                }
                groups.add(group);
            }
        }
    }

    /**
     * If any activity in a group is contained in a LoopGroup, then all other
     * memebers of that LoopGroup should be added to the group. Also, if a
     * LoopGroup can be a SplitJoinGroup in its own right, add it as one.
     */
    private void addLoopGroups() {
        for (Iterator i = groups.iterator(); i.hasNext();) {
            SplitJoinGroup group = (SplitJoinGroup) i.next();
            String[] activities = group.getActivities();
            for (int j = 0; j < activities.length; j++) {
                LoopGroup loopGroup =
                        loopGrouper.getLoopGroupFor(activities[j]);
                if (loopGroup != null) {
                    group.add(loopGroup.getActivitySet());
                }
            }
        }
        LoopGroup[] loopGroups = loopGrouper.getLoopGroups();
        for (int i = 0; i < loopGroups.length; i++) {
            LoopGroup loopGroup = loopGroups[i];
            Collection starts = loopGroup.getStarts();
            Collection ends = loopGroup.getEnds();
            if (starts.size() == 1 && ends.size() == 1) {
                SplitJoinGroup group =
                        new SplitJoinGroup((String) ends.iterator().next(),
                                (String) starts.iterator().next());
                group.add(loopGroup.getActivitySet());
                groups.add(group);
            }
        }
    }

    /**
     * Removes any groups that are not properly closed. These are ones that have
     * inputs or outputs through any activity other than the split or join.
     */
    private void removeUnclosedGroups() {
        for (Iterator i = groups.iterator(); i.hasNext();) {
            SplitJoinGroup group = (SplitJoinGroup) i.next();
            if (!group.isClosed(tool)) {
                i.remove();
            }
        }
    }

    /**
     * @param split The split activity.
     * @return true if it has multiple destinations excluding itself.
     */
    private boolean hasMultipleOtherDestinationActivities(String split) {
        String[] destinations = tool.getDestinations(split);
        int othercount = 0;
        for (int i = 0; i < destinations.length; i++) {
            if (!split.equals(destinations[i])) {
                othercount++;
            }
        }
        return othercount > 1;
    }

    /**
     * @param stack The current path stack.
     * @param activity The current activity.
     */
    private void checkSplit(ArrayList<String> stack, String activity) {
        System.out.println("  checkSplit: "+actIdToAct.get(activity).getName()+" ("+activity+")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        System.out.println("              Stack: "+stack.hashCode()); //$NON-NLS-1$
        System.out.println("              pathsToSplits.size(): "+pathsToSplits.size()); //$NON-NLS-1$
        if (!stack.contains(activity)) {
            String[] destinations = tool.getDestinations(activity);
            if (destinations.length > 1) {
                ArrayList<ArrayList<String>> paths = pathsToSplits.get(activity);
                if (paths == null) {
                    paths = new ArrayList<ArrayList<String>>();
                    pathsToSplits.put(activity, paths);
                }
                paths.add(stack);
                for (int i = 0; i < destinations.length; i++) {
                    ArrayList<String> newStack = new ArrayList<String>(stack);
                    newStack.add(activity);
                    checkSplit(newStack, destinations[i]);
                }
            } else if (destinations.length == 1) {
                ArrayList<String> newStack = new ArrayList<String>(stack);
                newStack.add(activity);
                checkSplit(newStack, destinations[0]);
            }
        } else {
            System.out.println("             Loop!"); //$NON-NLS-1$
        }
        
        System.out.println("  <== checkSplit: "+actIdToAct.get(activity).getName()+" ("+activity+")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$      
    }

    /**
     * @param split The split activity.
     */
    private void processSplit(String split) {
        ArrayList<ArrayList<String>> stacks = new ArrayList<ArrayList<String>>();
        splitPathStacks.put(split, stacks);
        ArrayList<String> currentStack = new ArrayList<String>();
        currentStack.add(split);
        checkDestinations(split, split, currentStack);
        if (stacks.size() <= 1) {
            splitPathStacks.remove(split);
        }
    }

    /**
     * @param split The split activity
     * @param activity The current activity.
     * @param currentStack The current path stack.
     */
    private void checkDestinations(String split, String activity,
            ArrayList<String> currentStack) {
        String[] destinations = tool.getDestinations(activity);
        ArrayList<String> clone = new  ArrayList<String>(currentStack);
        if (destinations.length == 0) {
            ArrayList<ArrayList<String>> stacks = splitPathStacks.get(split);
            stacks.add(clone);
        } else {
            for (int i = 0; i < destinations.length; i++) {
                if (!isInPathToSplit(split, destinations[i])
                        && !clone.contains(destinations[i])) {
                    if (i == 0) {
                        if (!currentStack.contains(destinations[i])) {
                            currentStack.add(destinations[i]);
                            checkDestinations(split, destinations[i],
                                    currentStack);
                        }
                    } else {
                        ArrayList<String> newStack = new ArrayList<String>(clone);
                        newStack.add(destinations[i]);
                        if (!clone.contains(destinations[i])) {
                            checkDestinations(split, destinations[i], newStack);
                        }
                    }
                }
            }
        }
    }

    /**
     * @param split The split activity.
     * @param activity The current activity.
     * @return true if the activity is in the path to the split activity.
     */
    private boolean isInPathToSplit(String split, String activity) {
        boolean isIn = false;
        ArrayList paths = (ArrayList) pathsToSplits.get(split);
        if (paths != null) {
            for (Iterator i = paths.iterator(); i.hasNext();) {
                ArrayList stack = (ArrayList) i.next();
                if (stack.contains(activity)) {
                    isIn = true;
                    break;
                }
            }
        }
        return isIn;
    }

    /**
     * @param from The from activity.
     * @return An array of groups that split at the from activity.
     */
    public SplitJoinGroup[] getGroupsFrom(String from) {
        ArrayList<SplitJoinGroup> groupsFrom = new ArrayList<SplitJoinGroup>();
        for (Iterator i = groups.iterator(); i.hasNext();) {
            SplitJoinGroup group = (SplitJoinGroup) i.next();
            if (group.getSplit().equals(from)) {
                groupsFrom.add(group);
            }
        }
        SplitJoinGroup[] groupArray = new SplitJoinGroup[groupsFrom.size()];
        groupsFrom.toArray(groupArray);
        return groupArray;
    }
}
