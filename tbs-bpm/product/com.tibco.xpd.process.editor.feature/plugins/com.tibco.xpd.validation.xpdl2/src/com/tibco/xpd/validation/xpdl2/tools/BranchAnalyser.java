/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.tibco.xpd.validation.provider.IPreProcessor;

/**
 * This class analyses a process and identifies all the branches in that
 * process. A branch is defined as a series of activities along which there are
 * no splits or joins.
 *
 * @author nwilson
 */
public class BranchAnalyser implements IPreProcessor {
    /** The transition tool. */
    private TransitionTool tool;
    /** The loop grouper tool. */
    private LoopGrouper loopGrouper;
    /** The list of identified branches. */
    private ArrayList<Branch> branches;
    /** Map of branches by start activity. */
    private HashMap<String, ArrayList<Branch>> branchesByStart;
    /** Map of branches by end activity. */
    private HashMap<String, ArrayList<Branch>> branchesByEnd;

    /**
     * @param tool The transition tool.
     * @param loopGrouper The loop grouper tool.
     */
    public BranchAnalyser(TransitionTool tool, LoopGrouper loopGrouper) {
        this.tool = tool;
        this.loopGrouper = loopGrouper;
        analyseBranches();
    }

    /**
     * Analyses the process branches.
     */
    private void analyseBranches() {
        branches = new ArrayList<Branch>();
        branchesByStart = new HashMap<String, ArrayList<Branch>>();
        branchesByEnd = new HashMap<String, ArrayList<Branch>>();
        String start = tool.getStart();
        ArrayList<String> stack = new ArrayList<String>();
        checkBranch(stack, new Branch(start), start);
    }

    /**
     * @param stack The current location stack.
     * @param branch The current branch.
     * @param activity The current activity.
     */
    private void checkBranch(ArrayList<String> stack, Branch branch, String activity) {
        boolean endloop = false;
        if (stack.contains(activity)) {
            endloop = true;
        }
        
        stack.add(activity);
        branch.add(activity);
        String[] inputs = tool.getSources(activity);
        String[] outputs = tool.getDestinations(activity);
        if (inputs.length > 1 || outputs.length != 1 || endloop) {
            branches.add(branch);
            addBranchByStart(branch);
            addBranchByEnd(branch);
            branch = new Branch(activity);
        }
        if (!endloop) {
            if (outputs.length == 1) {
                String next = outputs[0];
                if (tool.isConditional(activity, next)) {
                    branch.setConditional(true);
                }
                checkBranch(stack, branch, next);
            } else if (outputs.length > 1) {
                for (int i = 0; i < outputs.length; i++) {
                    String next = outputs[i];
                    branch = new Branch(activity);
                    if (tool.isConditional(activity, next)) {
                        if (!isOnlyLoopExitOrLoopInternal(activity, next)) {
                            branch.setConditional(true);
                        }
                    }
                    checkBranch(stack, branch, next);
                }
            }
        }
    }

    /**
     * @param activity The activity to check.
     * @param next The next actiity.
     * @return true if the activity is the only loop group exit.
     */
    private boolean isOnlyLoopExitOrLoopInternal(String activity, String next) {
        boolean isOnlyLoopExit = false;
        LoopGroup group = loopGrouper.getLoopGroupFor(activity);
        if (group != null) {
            boolean onlyExit = true;
            String[] outputs = tool.getDestinations(activity);
            for (int i = 0; i < outputs.length; i++) {
                if (!outputs[i].equals(next) && !group.contains(outputs[i])) {
                    onlyExit = false;
                }
            }
            if (onlyExit) {
                isOnlyLoopExit = true;
            }
        }
        return isOnlyLoopExit;
    }

    /**
     * @param branch The branch to add.
     */
    private void addBranchByStart(Branch branch) {
        String start = branch.getStart();
        ArrayList<Branch> startBranches = branchesByStart.get(start);
        if (startBranches == null) {
            startBranches = new ArrayList<Branch>();
            branchesByStart.put(start, startBranches);
        }
        startBranches.add(branch);
    }

    /**
     * @param branch The branch to add.
     */
    private void addBranchByEnd(Branch branch) {
        String end = branch.getEnd();
        ArrayList<Branch> endBranches = branchesByEnd.get(end);
        if (endBranches == null) {
            endBranches = new ArrayList<Branch>();
            branchesByEnd.put(end, endBranches);
        }
        endBranches.add(branch);
    }

    /**
     * @return A text description of the branches.
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (Iterator i = branches.iterator(); i.hasNext();) {
            Branch branch = (Branch) i.next();
            buffer.append(branch.toString());
            buffer.append("\r\n"); //$NON-NLS-1$
        }
        return buffer.toString();
    }

    /**
     * @param activity The activity to get input branches for.
     * @return An array of input branches for the activity.
     */
    public Branch[] getInputBranches(String activity) {
        Branch[] branches = new Branch[0];
        ArrayList<Branch> branchList = branchesByEnd.get(activity);
        if (branchList != null) {
            branches = new Branch[branchList.size()];
            branchList.toArray(branches);
        }
        return branches;
    }

    /**
     * @param from The from activity.
     * @param to The to activity.
     * @return true if the branch is conditional.
     */
    public boolean isConditional(String from, String to) {
        ArrayList startBranches = (ArrayList) branchesByStart.get(from);
        for (Iterator i = startBranches.iterator(); i.hasNext();) {
            Branch branch = (Branch) i.next();
            if (branch.getEnd().equals(to)) {
                if (branch.isConditional()) {
                    return true;
                }
            }
        }
        return false;
    }
}
