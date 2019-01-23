/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.tools;

import java.util.ArrayList;
import java.util.Iterator;

import com.tibco.xpd.validation.provider.IPreProcessor;

/**
 * Groups overlapping loops together so that they can be treated as single#
 * LoopGroup entities.
 *
 * @author nwilson
 */
public class LoopGrouper implements IPreProcessor {
    /** Transition helper tool. */
    private TransitionTool tool;
    /** The LoopAnalyser containing the individual ungrouped loops. */
    private LoopAnalyser loopAnalyser;
    /** A list of LoopGroup objects. */
    private ArrayList<LoopGroup> groups;

    /**
     * @param tool The transition tool.
     * @param loopAnalyser The LoopAnalyser from which to get the loops.
     */
    public LoopGrouper(TransitionTool tool, LoopAnalyser loopAnalyser) {
        this.tool = tool;
        this.loopAnalyser = loopAnalyser;
        groups = new ArrayList<LoopGroup>();
        mergeLoops();
    }

    /**
     * Merges overlapping loops from the analyser and creates the LoopGroup objects.
     */
    private void mergeLoops() {
        Loop[] loops = loopAnalyser.getLoops();
        for (int i = 0; i < loops.length; i++) {
            Loop loop = loops[i];
            ArrayList<LoopGroup> overlaps = new ArrayList<LoopGroup>();
            for (Iterator j = groups.iterator(); j.hasNext();) {
                LoopGroup group = (LoopGroup) j.next();
                if (isOverlapping(group, loop)) {
                    overlaps.add(group);
                }
            }
            LoopGroup newGroup = new LoopGroup(tool, loop);
            for (Iterator j = overlaps.iterator(); j.hasNext();) {
                LoopGroup toMerge = (LoopGroup) j.next();
                groups.remove(toMerge);
                newGroup.merge(toMerge);
            }
            groups.add(newGroup);
        }
    }

    /**
     * Checks to see if the group and loop overlap.
     * @param group The LoopGroup
     * @param loop The Loop
     * @return true if they overlap, otherwise false.
     */
    private boolean isOverlapping(LoopGroup group, Loop loop) {
        String[] activities = loop.getActivities();
        for (int i = 0; i < activities.length; i++) {
            if (group.contains(activities[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return An array of LoopGroup objects defining the merged loops.
     */
    public LoopGroup[] getLoopGroups() {
        LoopGroup[] groupArray = new LoopGroup[groups.size()];
        groups.toArray(groupArray);
        return groupArray;
    }

    /**
     * @param activity The activity to the loop group for.
     * @return The loop group containing the activity or null.
     */
    public LoopGroup getLoopGroupFor(String activity) {
        for (Iterator i = groups.iterator(); i.hasNext();) {
            LoopGroup group = (LoopGroup) i.next();
            if (group.contains(activity)) {
                return group;
            }
        }
        return null;
    }
}
