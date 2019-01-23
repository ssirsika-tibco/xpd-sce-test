/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.validation.xpdl2.tools.Branch;
import com.tibco.xpd.validation.xpdl2.tools.BranchAnalyser;
import com.tibco.xpd.validation.xpdl2.tools.LoopGroup;
import com.tibco.xpd.validation.xpdl2.tools.LoopGrouper;
import com.tibco.xpd.validation.xpdl2.tools.SplitJoinGroup;
import com.tibco.xpd.validation.xpdl2.tools.SplitJoinGroupAnalyser;
import com.tibco.xpd.validation.xpdl2.tools.TransitionTool;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This rule check for two problems with AND gateways:
 * <p>
 * 1. For joining parallel gateways (i.e. those with multiple inputs) all of the
 * input paths must be executed. This means that the paths must come from
 * parallel splits and there should be no conditional splits on the paths that
 * aren't re-joined prior to the gateway being checked.
 * <p>
 * 2. Parallel gateways within loops cannot have inputs from both inside and
 * outside the loop.
 * 
 * @author nwilson
 */
public class AndGatewayRule extends ProcessValidationRule {
    /** Conditional input issue ID. */
    public static final String ID = "sim.conditionalAndGateway"; //$NON-NLS-1$

    /** Parallel gateway loop input issue ID. */
    public static final String ID_INPUT = "sim.andGatewayLoopInput"; //$NON-NLS-1$   

    /**
     * @param process
     *            The process to check.
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     */
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activityList, EList<Transition> transitions) {
        TransitionTool tool = getTool(TransitionTool.class, process);
        LoopGrouper loopGrouper = getTool(LoopGrouper.class, process);
        BranchAnalyser branchAnalyser = getTool(BranchAnalyser.class, process);

        // 
        // MR 39970 (Sid) SplitJoinGroupAnalyser is written in a way that causes
        // severe performance issues when validating larger processes
        //
        // This should mean a complete rewrite but there is no time yet and the
        // affects of not checking the rule that says you "cannot have a parallel
        // join if an incoming path is conditional" is not vital anyway ).
        //
        // So for now, we just disable it...
        //
        // SplitJoinGroupAnalyser groupAnalyser =
        // getTool(SplitJoinGroupAnalyser.class, process);

        List activities = process.getActivities();
        for (Object next : activities) {
            Activity activity = (Activity) next;
            Route route = activity.getRoute();
            if (route != null) {
                if (JoinSplitType.PARALLEL_LITERAL.equals(Xpdl2ModelUtil
                        .safeGetGatewayType(activity))) {
                    Branch[] inputs =
                            branchAnalyser.getInputBranches(activity.getId());
                    if (inputs.length > 1) {
                        // 
                        // MR 39970 (Sid) SplitJoinGroupAnalyser is written in a way that causes
                        // severe performance issues when validating larger processes
                        //
                        // This should mean a complete rewrite but there is no time yet and the
                        // affects of not checking the rule that says you "cannot have a parallel
                        // join if an incoming path is conditional" is not vital anyway ).
                        //
                        // So for now, we just disable it...
                        //
                        // if (hasConditionalBranches(branchAnalyser,
                        // groupAnalyser,
                        // activity.getId())) {
                        // addIssue(ID, activity);
                        // }
                    }
                    if (hasMixedInputs(tool, loopGrouper, activity.getId())) {
                        addIssue(ID_INPUT, activity);
                    }
                }
            }
        }
    }

    /**
     * @param tool
     *            The transition tool.
     * @param loopGrouper
     *            The loop grouper tool.
     * @param activityId
     *            The activity ID to check.
     * @return true if the activity inputs from inside and outside a loop.
     */
    private boolean hasMixedInputs(TransitionTool tool,
            LoopGrouper loopGrouper, String activityId) {
        boolean mixed = false;
        LoopGroup loop = getLoopGroup(loopGrouper, activityId);
        if (loop != null) {
            boolean hasInternal = false;
            boolean hasExternal = false;
            String[] sources = tool.getSources(activityId);
            for (int i = 0; i < sources.length; i++) {
                if (loop.contains(sources[i])) {
                    hasInternal = true;
                } else {
                    hasExternal = true;
                }
            }
            mixed = hasInternal && hasExternal;
        }
        return mixed;
    }

    /**
     * @param loopGrouper
     *            The loop grouper tool.
     * @param activityId
     *            The activity ID.
     * @return The LoopGroup containing the activity or null.
     */
    private LoopGroup getLoopGroup(LoopGrouper loopGrouper, String activityId) {
        LoopGroup loop = null;
        LoopGroup[] loops = loopGrouper.getLoopGroups();
        for (int i = 0; i < loops.length; i++) {
            if (loops[i].contains(activityId)) {
                loop = loops[i];
                break;
            }
        }
        return loop;
    }

    /**
     * @param branchAnalyser
     *            The branch analyser tool.
     * @param splitJoinGroupAnalyser
     *            The split-join analyser tool.
     * @param join
     *            The join activity ID.
     * @return true if any input paths are conditional.
     */
    private boolean hasConditionalBranches(BranchAnalyser branchAnalyser,
            SplitJoinGroupAnalyser splitJoinGroupAnalyser, String join) {
        boolean hasConditional = false;
        List<List<String>> paths = new ArrayList<List<String>>();
        createPathsToJoin(branchAnalyser, paths, join);
        List<String> common = null;
        for (List<String> path : paths) {
            if (!(path.size() == 1 && path.get(0).equals(join))) {
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
        if (common != null && common.size() > 0) {
            String split = (String) common.get(0);
            hasConditional =
                    hasConditionalBranches(branchAnalyser,
                            splitJoinGroupAnalyser,
                            paths,
                            split,
                            join);
        }
        return hasConditional;
    }

    /**
     * @param branchAnalyser
     *            The branch analyser tool.
     * @param splitJoinGroupAnalyser
     *            The split-join analyser tool.
     * @param paths
     *            A list of paths to check.
     * @param split
     *            The split activity ID.
     * @param join
     *            The join activity ID.
     * @return true if any of the paths are conditional.
     */
    private boolean hasConditionalBranches(BranchAnalyser branchAnalyser,
            SplitJoinGroupAnalyser splitJoinGroupAnalyser,
            List<List<String>> paths, String split, String join) {
        for (List<String> path : paths) {
            Collections.reverse(path);
            path.add(join);
            List<String> subPath =
                    path.subList(path.indexOf(split), path.size());
            String[] items = new String[subPath.size()];
            subPath.toArray(items);
            if (items.length > 1) {
                for (int j = 0; j < (items.length - 1); j++) {
                    SplitJoinGroup[] groups =
                            splitJoinGroupAnalyser.getGroupsFrom(items[j]);
                    for (int k = 0; k < groups.length; k++) {
                        String end = groups[k].getJoin();
                        for (int l = j; l < (items.length - 2); l++) {
                            if (items[l + 1].equals(end)) {
                                j = l + 1;
                                break;
                            }
                        }
                    }
                    if (branchAnalyser.isConditional(items[j], items[j + 1])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Creates a list of paths to the join activity from the closest common
     * split activity.
     * 
     * @param branchAnalyser
     *            The branch analyser tool.
     * @param paths
     *            The list of paths being created.
     * @param join
     *            The join activity.
     */
    private void createPathsToJoin(BranchAnalyser branchAnalyser,
            List<List<String>> paths, String join) {
        List<String> stack = new ArrayList<String>();
        recursePaths(branchAnalyser, paths, stack, join);
    }

    /**
     * @param branchAnalyser
     *            The branch analyser tool.
     * @param paths
     *            The list of paths being created.
     * @param stack
     *            The activity ID stack tracing back from the join.
     * @param activity
     *            The current activity ID.
     */
    private void recursePaths(BranchAnalyser branchAnalyser,
            List<List<String>> paths, List<String> stack, String activity) {
        Branch[] inputs = branchAnalyser.getInputBranches(activity);
        if (inputs.length > 1) {
            for (int i = 1; i < inputs.length; i++) {
                List<String> newStack = new ArrayList<String>(stack);
                if (!newStack.contains(inputs[i].getStart())) {
                    newStack.add(inputs[i].getStart());
                    recursePaths(branchAnalyser, paths, newStack, inputs[i]
                            .getStart());
                }
            }
            if (!stack.contains(inputs[0].getStart())) {
                stack.add(inputs[0].getStart());
                recursePaths(branchAnalyser, paths, stack, inputs[0].getStart());
            }
        } else if (inputs.length == 1) {
            if (!stack.contains(inputs[0].getStart())) {
                stack.add(inputs[0].getStart());
                recursePaths(branchAnalyser, paths, stack, inputs[0].getStart());
            }
        } else {
            paths.add(stack);
        }
    }

}
