/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.ProcessFlowAnalyser;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This class analyses a process and identifies any loops in that process.
 * <p>
 * For details on how loops are identified see
 * {@link #findLoops(ProcessFlowAnalyser)}
 * <p>
 * <b>NOTE: </b> Currently the list of activities in a loop will NOT include
 * activities that are solely within an inner nested loop.
 * <p>
 * <b>NOTE 2: before making ANY changes to this analyser (specifically
 * findLoops()) you must consider all the user cases in the LoopAlgorithm.xpdl
 * package in this class's java package. After you have made changes load it
 * into studio, turn logging on (see LoopAnalyser contructor) then switch the
 * simulation desitnation on for each process in turn chacking the debug console
 * that the listed loops for each process match those annotated in the process
 * diagrams.
 * 
 * 
 * @author nwilson, allway
 * @since 2.0 - Technique greatly improved @3.3
 */
public class LoopAnalyser implements IPreProcessor {

    private ProcessFlowAnalyser flowAnalyser;

    /** A collection of identified loops. */
    private Collection<Loop> loops;

    //
    // Some stuff for logging to console / file just in case we need it in the
    // future.
    private boolean wantFileLogging;

    private boolean wantConsoleLogging;

    private IFile logfile;

    private String LOG_FILE = "LoopAnalyser"; //$NON-NLS-1$

    /**
     * Constructor. The loop analysis is performed here.
     * 
     * @param tool
     *            The TransitionTool for the process to analyse.
     */
    public LoopAnalyser(ProcessFlowAnalyser flowAnalyser) {
        this.flowAnalyser = flowAnalyser;

        wantFileLogging = false;
        wantConsoleLogging = false;

        // 
        // Init the log if necessary.
        initLog();

        long start = System.currentTimeMillis();

        log("LoopAnalyzer: " //$NON-NLS-1$
                + Xpdl2ModelUtil.getProcess(flowAnalyser.getFlowContainer())
                        .getName()
                + "\n========================================"); //$NON-NLS-1$

        //
        // Perform the analysis.
        //
        analyseLoops();

        log("<============= LoopAnalyzer() took(ms): " + (System.currentTimeMillis() - start)); //$NON-NLS-1$

        //
        // CLose the log if necessary.
        closeLog();

        return;
    }

    /**
     * @param tool
     * @deprecated 3.3 Do not use the method of construction use alterntive
     *             {@link #LoopAnalyser(ProcessFlowAnalyser)}
     */
    public LoopAnalyser(TransitionTool tool) {
        throw new RuntimeException(
                "Contruction With transition tool is no longer valid - used alternative construction with ProcessFlowAnalyser"); //$NON-NLS-1$
    }

    /**
     * @return An array of loops identified by the analyser.
     */
    public Loop[] getLoops() {
        Loop[] loopArray = new Loop[loops.size()];
        loops.toArray(loopArray);
        return loopArray;
    }

    /**
     * Analyses the process for loops.
     */
    private void analyseLoops() {

        loops = findLoops();

        if (wantConsoleLogging || wantFileLogging) {
            for (Loop loop : loops) {
                log("  LOOP: " + activityListToString(loop.getActivities())); //$NON-NLS-1$
            }
        }

        return;
    }

    /**
     * <p>
     * Find loops in the flow container (process / enbedded sub-process used to
     * initialise the given flow analyser.
     * <p>
     * For more details follow comments inlined.
     * 
     * <p>
     * <b>NOTE 2: before making ANY changes to this analyser (specifically
     * findLoops()) you must consider all the user cases in the
     * LoopAlgorithm.xpdl package in this class's java package. After you have
     * made changes load it into studio, turn logging on (see LoopAnalyser
     * contructor) then switch the simulation desitnation on for each process in
     * turn chacking the debug console that the listed loops for each process
     * match those annotated in the process diagrams. </b>
     * 
     * @param flowAnalyser
     * @return list of loops.
     * 
     * @author aallway
     * @since 3.3
     */
    private Collection<Loop> findLoops() {

        EList<Activity> activitiesToCheck =
                flowAnalyser.getFlowContainer().getActivities();

        List<Loop> foundLoops = new ArrayList<Loop>();

        /*
         * Go thru each activity identifying loops...
         */
        for (Activity potentialStartOfLoopActivity : activitiesToCheck) {
            //            log("potentialStartOfLoopActivity: " + potentialStartOfLoopActivity.getName()); //$NON-NLS-1$

            /*
             * Get a list of activities that are a source of the incoming flow
             * into the activity we're dealing with.
             */

            List<Activity> incomingFlowActivities =
                    flowAnalyser
                            .getIncomingActivities(potentialStartOfLoopActivity
                                    .getId());

            if (incomingFlowActivities.size() > 1) {

                /*
                 * (a) It has more than one incoming flow - Could be a join OR
                 * start of a loop.
                 */

                /*
                 * For performing quick filter of incoming flows that are are
                 * definitely downstream only...
                 */
                Set<String> allUpstreamActivities =
                        flowAnalyser
                                .getUpstreamActivityIds(potentialStartOfLoopActivity
                                        .getId());

                /*
                 * Iterate thru the source-of-incoming-flow activities looking
                 * for ones that are upstream.
                 * 
                 * (Note that this is NOT enough on its own to identify flows
                 * coming into the head of a loop (because a flow can be both
                 * upstream AND part of a loop because the free-form-ness of
                 * BPMN allows loops with multiple entry points).
                 */
                for (Activity potentialLoopEntryPointActivity : incomingFlowActivities) {

                    //                    log("  potentialLoopEntryPointActivity: " + potentialLoopEntryPointActivity.getName()); //$NON-NLS-1$

                    if (!allUpstreamActivities
                            .contains(potentialLoopEntryPointActivity.getId())) {
                        /*
                         * Discount incoming flow activities that are definitely
                         * only downstream as potential loop entry points.
                         */
                        continue;
                    }

                    /*
                     * Incoming flow from an upstream activity...
                     * 
                     * In BPMN, I think, a "loop" can really be described as...
                     * 
                     * "any outgoing flow that comes back to any incoming flow
                     * from upstream WITHOUT passing thru ANY activities along
                     * the route between a start activity and that incoming
                     * flow".
                     */

                    /**
                     * <code>
                     * So the first thing to do is to identify the
                     * activities that are upstream of this particular
                     * source-of-incoming flow activity. 
                     * i.e. If you have a complex situation such as the 
                     * following (the loop [A,L1,L2,B] has 2 entry points 
                     * [A] and [B] depending on which direction is taken 
                     * at the <X> gateway...
                     * 
                     *   [Start]-><X>->[A]->[L1]->[L2]->[D]->[End]
                     *             |    ^          |
                     *             |    |          |
                     *             --->[B]<--------
                     *  
                     * When dealing with [A] as a loop entry point then [B] 
                     * is simply a 'join' within the Loop [A,L1,L2,B].
                     * When dealing with [B] as a loop entry point then [A] 
                     * is simply a 'join' within the Loop [B,A,L1,L2].
                     * 
                     * Therefore, when dealing with <X>->[A] as a potential start of 
                     * loop you you need to identify downstream activities 
                     * between The outgoing to [L1] and the incoming from [B].
                     * 
                     * However, [B] will look like it is upstream of [A] 
                     * (route: Start->X->B->A) so we must be very particular 
                     * about ONLY stopping the traversal for "get activities
                     * between [L1] and [B]" if we hit an activity that is 
                     * upstream on the particular potential loop start point
                     * (i.e. <X>->[A]).
                     * 
                     * When we then come to consider the potential loop start 
                     * point [B]->[A], [B] IS upstream so when we say "get 
                     * activities between [L1] and [B], we will not be allowed to
                     * traverse [B] cos its upstream and so will be identified 
                     * as a join when dealing with [A] as potential loop start.
                     * =============================================================
                     * 
                     * This was a complex case, but the same logic allows us to
                     * identify a simple 'join' within a loop WITHOUT thinking it's
                     * another loop starting at the join point then going backwards
                     * to an upstream point before arriving back at the join....
                     * 
                     *                   -->[L1]---
                     *                   |        |
                     *                   |        v
                     *   [Start]->[A]-><G1>     <G2>- ->[D]->[End]
                     *             ^     |        ^      |
                     *             |     |        |      |
                     *             |     -->[L2]---      |
                     *             |                     |
                     *             -----------------------
                     * 
                     * When considering <G2> as a potential start of loop,
                     * we will first be saying "get all activities between the 
                     * outgoing [D] and the incoming [L1]". If we do not stop
                     * traversing downstream when we find something on the 
                     * upstream flow eading to [L1] then we will think we
                     * have a loop [ G2->D->A->G1->L1 ] (and same again for 
                     * considering the [L2]-><G2> case.
                     * 
                     * This is NOT really a start of a loop it just appears
                     * to be one IF you don't take upstream activities into account.
                     * And you have to take into account upstream activities 
                     * specifically leading to each incoming flow to cover the
                     * more complex case above (a double entry point loop). 
                     * </code>
                     */

                    /*
                     * So get the activities that are specifically upstream of
                     * the incomingFlowActivity we're currently dealing with.
                     * This is used to tell us when to STOP traversing whilst
                     * searching downstream for activities in loop because we
                     * have looped back to an activity that is upstream of
                     * potential start.
                     */
                    Set<String> stopTraversingWhenHitUpstreamOfLoopEntryActivityList =
                            flowAnalyser
                                    .getUpstreamActivityIds(potentialLoopEntryPointActivity
                                            .getId());
                    // Add source-of-incomingflow activity itself.
                    stopTraversingWhenHitUpstreamOfLoopEntryActivityList
                            .add(potentialLoopEntryPointActivity.getId());
                    // Add the potential start of loop activity itself.
                    stopTraversingWhenHitUpstreamOfLoopEntryActivityList
                            .add(potentialStartOfLoopActivity.getId());

                    /*
                     * Each target outgoing flow is first step on a potential
                     * loop. So iterate them...
                     */
                    List<Activity> outgoingFlowActivities =
                            flowAnalyser
                                    .getOutgoingActivities(potentialStartOfLoopActivity
                                            .getId());

                    for (Activity potentialFirstStepOnLoopActivity : outgoingFlowActivities) {
                        //                        log("    potentialFirstStepOnLoopActivity: " + potentialFirstStepOnLoopActivity.getName()); //$NON-NLS-1$

                        if (potentialFirstStepOnLoopActivity == potentialStartOfLoopActivity) {
                            /*
                             * Deal with special case [1] a flow that links
                             * directly back to source (a 1-activity loop).
                             */
                            List<String> activitiesInLoop =
                                    Collections
                                            .singletonList(potentialStartOfLoopActivity
                                                    .getId());

                            Loop loop =
                                    new Loop(flowAnalyser.getFlowContainer(),
                                            potentialStartOfLoopActivity
                                                    .getId(), activitiesInLoop);
                            foundLoops.add(loop);

                            if (wantConsoleLogging || wantFileLogging) {
                                // log("                Special Case [1] a 1-activity loop: " //$NON-NLS-1$
                                // + activityListToString(loop
                                // .getActivities()));
                            }

                            continue;
                        }

                        if (stopTraversingWhenHitUpstreamOfLoopEntryActivityList
                                .contains(potentialFirstStepOnLoopActivity
                                        .getId())) {
                            /*
                             * The outgoing activity is directly upstream of the
                             * potential start-of-loop. This happens when a join
                             * activity within a loop ALSO has a loop back
                             * transition.
                             * 
                             * Ignore it!
                             */
                            continue;
                        }

                        /*
                         * Even though we are already iterating thru incoming
                         * flow, then outgoing flow within that by the time we
                         * get here, we must now re-iterate thru the incoming
                         * flow.
                         * 
                         * The outer-iteration of incoming flow is for iterating
                         * thru potential loop entry points.
                         * 
                         * This inner iteration of incoming flow is to identify
                         * the end of each loop that can start @ the
                         * first-step-of loop outgoing flow activity we are
                         * currently dealing with.
                         * 
                         * Therefore any unjoined split path that joins back
                         * into start-of-loop will (and SHOULD) be treated as a
                         * separate loop.
                         */
                        for (Activity potentialEndOfLoopActivity : incomingFlowActivities) {

                            //                            log("      potentialEndOfLoopActivity: " + potentialEndOfLoopActivity.getName()); //$NON-NLS-1$

                            if (stopTraversingWhenHitUpstreamOfLoopEntryActivityList
                                    .contains(potentialEndOfLoopActivity
                                            .getId())) {
                                /*
                                 * The potential end of loop incoming flow
                                 * activity is definitely upstream so so need to
                                 * consider any further.
                                 */
                                continue;
                            }

                            if (potentialFirstStepOnLoopActivity == potentialEndOfLoopActivity) {
                                /*
                                 * Deal with a special case [2], a 2-activity
                                 * loop...
                                 * 
                                 * We can't do a 'getActivitiesBetween(
                                 * potentialFirstStepOnLoopActivity and
                                 * potentialEndOfLoopActivity ) because they are
                                 * the same activity and therefore
                                 * getActivitiesBetween() will return null
                                 * (activity is not reachable).
                                 * 
                                 * So if potential first step == potential end
                                 * then we have a 2-activity loop.
                                 */
                                List<String> activitiesInLoop =
                                        new ArrayList<String>();
                                activitiesInLoop
                                        .add(potentialStartOfLoopActivity
                                                .getId());
                                activitiesInLoop
                                        .add(potentialFirstStepOnLoopActivity
                                                .getId());

                                Loop loop =
                                        new Loop(flowAnalyser
                                                .getFlowContainer(),
                                                potentialStartOfLoopActivity
                                                        .getId(),
                                                activitiesInLoop);
                                foundLoops.add(loop);

                                if (wantConsoleLogging || wantFileLogging) {
                                    //                                    log("                Special Case [2] a 2-activity loop: " //$NON-NLS-1$
                                    // + activityListToString(loop
                                    // .getActivities()));
                                }

                                continue;

                            }

                            /**
                             * Now we're dealing with either a non-loop OR a
                             * loop with > 2 activities (potentialFirst !=
                             * potentialEnd).
                             * 
                             * So get activities in between the potentialFirst
                             * and potentialEnd - making sure we stop traversing
                             * downstream (looking for potentialEnd) when we hit
                             * an activity that is upstream of the loop entry
                             * point.
                             * 
                             * BTW We do 'get activities between
                             * "first activity AFTER start-of-loop" and
                             * "last activity before start-of-loop"' because we
                             * want to treat each outgoing from start-of-loop
                             * reaching each incoming to start-of-loop as a
                             * separate loop.
                             * 
                             * <code>
                             * Although this means that an unjoined split whose 
                             * paths both reach back to start of loop will create 
                             * a separate loop for each path that reaches back, 
                             * it is the ONLY way to deal with nested loops that 
                             * have the same start activity...
                             * 
                             *   [Start]->[A]->[B]->[C]->[D]->[End]
                             *            ^ ^        |    |
                             *            | |        |    |
                             *            | ----------    |            
                             *            -----------------            
                             * 
                             * If we performed 'get activities between [B] and [A] 
                             * this would include both [C] and [D] which effectively 
                             * mergers BOTH the loops as you can see above.
                             * 
                             * Therefore it is better to treat unjoined splits as 
                             * separate loops - at least the user can rejoin them 
                             * properly within loop if necessary. 
                             * 
                             * </code>
                             */
                            Set<String> activitiesBetween =
                                    flowAnalyser
                                            .getActivitiesBetween(potentialFirstStepOnLoopActivity
                                                    .getId(),
                                                    potentialEndOfLoopActivity
                                                            .getId(),
                                                    stopTraversingWhenHitUpstreamOfLoopEntryActivityList,
                                                    true);

                            if (activitiesBetween != null) {
                                /*
                                 * potentialEnd is downstream of potentialFirst
                                 * so we have a loop!
                                 * 
                                 * (Note: If they directly follow each other the
                                 * list will be empty).
                                 */

                                List<String> activitiesInLoop =
                                        new ArrayList<String>(activitiesBetween);

                                /*
                                 * Complete the loop by inserting the start of
                                 * loop and first step on loop.
                                 */
                                activitiesInLoop.add(0,
                                        potentialStartOfLoopActivity.getId());
                                activitiesInLoop.add(1,
                                        potentialFirstStepOnLoopActivity
                                                .getId());

                                /* And append the end of loop activity. */
                                activitiesInLoop.add(potentialEndOfLoopActivity
                                        .getId());

                                Loop loop =
                                        new Loop(flowAnalyser
                                                .getFlowContainer(),
                                                potentialStartOfLoopActivity
                                                        .getId(),
                                                activitiesInLoop);
                                foundLoops.add(loop);

                                if (wantConsoleLogging || wantFileLogging) {
                                    //                                    log("        3+ Activity Loop: " //$NON-NLS-1$
                                    // + activityListToString(loop
                                    // .getActivities()));
                                }
                            }

                        } /*
                           * For each incoming flow activity that's potentiall
                           * an end of loop from the potential first step on
                           * loop.
                           */

                    } /*
                       * For each outgoing flow activity that's potential the
                       * first step in a loop.
                       */

                } /*
                   * For each incoming flow activity that's potentially a loop
                   * entry point.
                   */

            } /* > 1 incoming flow to activity. */

        } /* Next activity in flow container. */

        return foundLoops;
    }

    /**
     * For logging purposes.
     * 
     * @param activityList
     * 
     * @return formatted string list of activity names.
     */
    private String activityListToString(Collection<String> activityList) {
        return activityListToString(activityList.toArray(new String[0]));
    }

    private String activityListToString(String[] activityList) {
        if (!wantConsoleLogging && !wantFileLogging) {
            // Don't hit performance when nbot logging.
            return ""; //$NON-NLS-1$
        }

        StringBuffer buff = new StringBuffer();
        buff.append("[ "); //$NON-NLS-1$

        boolean first = true;
        for (String id : activityList) {
            String name = null;

            Activity act = flowAnalyser.getActivity(id);
            if (act != null) {
                name = Xpdl2ModelUtil.getDisplayNameOrName(act);
                if (name == null || name.length() == 0) {
                    name = act.getId();
                }
            } else {
                name = "?????????"; //$NON-NLS-1$
            }

            if (first) {
                buff.append(name);
                first = false;
            } else {
                buff.append(", "); //$NON-NLS-1$
                buff.append(name);
            }
        }

        buff.append(" ]"); //$NON-NLS-1$
        return buff.toString();
    }

    /**
     * Log the message to file (if enabled by {@link #wantFileLogging}) and/or
     * to console (if enabled by {@link #wantConsoleLogging})
     * 
     * @param s
     * @param fileOnly
     *            disable console ,logging of this message.
     */
    private void log(String s, boolean fileOnly) {
        if (wantConsoleLogging && !fileOnly) {
            System.out.println(s);
        }

        if (logfile != null) {
            s = s + "\r\n"; //$NON-NLS-1$

            try {
                File file = logfile.getLocation().toFile();
                FileOutputStream os = new FileOutputStream(file, true);

                os.write(s.getBytes());
                os.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return;
    }

    private void log(String s) {
        log(s, false);
    }

    /**
     * If logging turned on will create a file LoopAnalyser'n'.txt @ project
     * level and other methods will start logging to this.
     */
    private void initLog() {
        if (wantFileLogging) {
            IProject project =
                    WorkingCopyUtil.getProjectFor(flowAnalyser
                            .getFlowContainer());
            int idx = 1;
            do {
                logfile = project.getFile(LOG_FILE + idx + ".txt"); //$NON-NLS-1$
                idx++;
            } while (logfile.exists());
        }
        return;
    }

    /**
     * Perform any log file tidy up necessary.
     */
    private void closeLog() {
        if (logfile != null) {
            try {
                logfile.refreshLocal(1, null);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
    }

}
