/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.nimbus.layoutprocess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.Coordinates;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.ProcessFlowAnalyser;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Class to handle auto-layout of a BPMN process created from importing a Nimbus
 * process diagram.
 * <p>
 * This auto-layout is not (currently) a full BPMN auto-layout implementation as
 * this is not required for the fairly restrictive case of the types of process
 * that Nimbus diagrams are converted to.
 * <p>
 * <b>NOTE-1: Layout strategy...</b>
 * <p>
 * This layout uses a very simple "hanging tree" strategy whereby the longest
 * continuous activity stream is laid out across the top of the process (from
 * start to end). Then the branches hanging off of that will be laid out in
 * order of length. So in the following illustration Sn indicates the streams
 * and the order in which the stream are laid out.
 * 
 * <pre>
 *  S1-->S1-->S1-->S1-->S1-->S1-->S1-->S1-->S1
 *       |              |
 *       -->S4-->S4     -->S2-->S2-->S2
 *                         |
 *                         -->S3
 * </pre>
 * 
 * <b>NOTE-2: Avoiding overlaps...</b>
 * <p>
 * When laying out a stream of activities, we will attempt to ensure that we
 * don't position any activity over an activity that has already been laid out
 * (for instance when an earlier, longer stream has already been laid out and it
 * has branches hanging off it too then we may run into those when processing an
 * earlier, shorter branch afterwards.
 * <p>
 * i.e. In the following illustration Fn represents the first (longest) stream
 * laid out and a later branch and Sn represents the second (shorter) branch
 * that is laid out. In this case, if we did nothing, the S1 branch would
 * "run into" the F2 branch.
 * 
 * <pre>
 *  F1-->F1-->F1-->F1-->F1-->F1-->F1-->F1-->F1
 *       |              |
 *       -->S1-->S1     -->F2-->F2-->F2
 * </pre>
 * 
 * We can see that if the S1 stream had 2 more activities then it would
 * "run into" the F2 stream. We prevent this by deciding on a nominal position
 * for each activity in stream (say 3rd in act S1 stream) and then checking that
 * it does not overlap anything already laid out (i.e. 1st F2) and move it
 * downwards until it does not overlap...
 * 
 * <pre>
 *  F1-->F1-->F1-->F1-->F1-->F1-->F1-->F1-->F1
 *       |              |
 *       -->S1-->S1--   -->F2-->F2-->F2
 *                  |
 *                  -->S1-->S1
 * </pre>
 * 
 * <b>NOTE-3: Tidying up after avoiding overlaps...</b>
 * <p>
 * Following on from NOTE-2. If we need to avoid an overlap as described then we
 * will also attempt to move previous activities in the same single stream by
 * the same amount so that the stream becomes 'straight' again (i.e. 1st and 2nd
 * in S1 stream)...
 * 
 * <pre>
 *  F1-->F1-->F1-->F1-->F1-->F1-->F1-->F1-->F1
 *       |              |
 *       |              -->F2-->F2-->F2
 *       |
 *       -->S1-->S1-->S1-->S1
 * </pre>
 * 
 * @author aallway
 * @since 31 Oct 2012
 */
public class ImportNimbusProcessAutoLayout {

    private ProcessFlowAnalyser flowAnalyser;

    private Process process;

    private int HORIZONTAL_SPACING = 80;

    private int VERTICAL_SPACING = 60;

    private int ACTIVITY_ARTIFACT_VERTICAL_OFFSET = VERTICAL_SPACING / 4;

    private int LANE_LEFT_MARGIN = 60;

    private int LANE_BOTTOM_MARGIN = 80;

    private int LANE_TOP_MARGIN = 80;

    public ImportNimbusProcessAutoLayout(Process process) {
        this.process = process;
        flowAnalyser = new ProcessFlowAnalyser(true, this.process);
    }

    /**
     * Layout the process.
     * 
     * @throws LayoutException
     *             Unexpected exception that occurs during layout
     */
    public void layoutProcess() throws LayoutException {
        /*
         * Set of activities that we have already layed out. This can be used to
         * prevent us from laying out the same activfity twice when it appears
         * downstream of several activities (i.e. is a join).
         */
        Set<Activity> alreadyLaidOut = new HashSet<Activity>();

        List<Activity> startActivities =
                flowAnalyser.getStartOfFlowActivities();
        if (startActivities.size() == 0) {
            /*
             * If there are no start activities it may be that there is a closed
             * loop of activities or none at all!
             * 
             * If it's a closed loop then we can just pick one at random (that
             * has all other activities downstream because process may be "P"
             * shaped and start with a closed loop but end normally.
             */
            Activity startActivity = findStartActivity();
            if (startActivity == null) {
                throw new LayoutException(
                        "Cannot ascertain any start activity."); //$NON-NLS-1$
            }

            startActivities = new ArrayList<Activity>();
            startActivities.add(startActivity);

        }

        /*
         * Then go thru the next set of activities favouring the one with the
         * longest downstream list of activities as the flow-stream to layout
         * across top.
         */
        layoutOutgoingActivityStreams(startActivities,
                alreadyLaidOut,
                LANE_LEFT_MARGIN - HORIZONTAL_SPACING,
                LANE_TOP_MARGIN);

        /*
         * Compress / expand lanes to fit activities within them.
         */
        sizeLanes();

    }

    /**
     * If there is no explicit start activity (activity with no incoming flow)
     * then we need to find any activity that looks like it could be.
     * <p>
     * This will only happen if the process starts with (or consists entirely
     * of) a closed-loop of activities. In this case we'll just pick one of the
     * activities in the starting closed loop (which we can work out by looking
     * for any activity that has all other activities downstream of it.
     * 
     * @return Find any implied start activity in the absence of any explicit
     *         start activities.
     */
    private Activity findStartActivity() {
        List<Activity> potentialStarters = new ArrayList<Activity>();

        int totalActivities = process.getActivities().size();

        for (Activity activity : process.getActivities()) {

            Set<Activity> downstreamActivities =
                    flowAnalyser.getDownstreamActivities(activity.getId(),
                            false);

            if (downstreamActivities.size() == (totalActivities - 1)) {
                potentialStarters.add(activity);
            }
        }

        /*
         * Then just for good measure, we'll tak ethe left most one (current
         * nimbus export doesn't include X/Y position info so x/y is arbitrary
         * on import at the moment BUT in phase 2 we can use the real original
         * X/Y during xslt import if it is added to the export so will be more
         * meaningful here).
         */
        if (potentialStarters.size() > 0) {
            Activity leftMost = null;
            int leftMostX = Integer.MAX_VALUE;

            for (Activity activity : potentialStarters) {
                int leftX = Xpdl2ModelUtil.getObjectBounds(activity).x;

                if (leftMost == null || leftX < leftMostX) {
                    leftMost = activity;
                    leftMostX = leftX;
                }
            }

            return leftMost;
        }

        return null;
    }

    /**
     * Go thru the next set of sibling outgoing activities favouring the one
     * with the longest downstream list of activities as the flow-stream to
     * layout across top (of this set).
     * <p>
     * <b>See NOTE-1 in javadoc.</b>
     * 
     * @param activity
     * @param alreadyLaidOut
     * @param x
     *            right edge of upstream activity (post layout)
     * @param y
     *            centre Y offset of current stream.
     */
    private void layoutOutgoingActivityStreams(
            List<Activity> outgoingActivities, Set<Activity> alreadyLaidOut,
            int x, int y) {

        /* Get a list of the activities processed by this one activity. */
        outgoingActivities = new ArrayList<Activity>(outgoingActivities);

        /*
         * Sort the list by largest number of downstream activities from each
         * activity processed directly from
         */

        if (outgoingActivities.size() > 1) {
            Collections.sort(outgoingActivities,
                    new LargestDownstreamActivitiesComparator());
        }

        /*
         * Lay out each and recurs for it's downstream activities. (Do that
         * before processing next in order that we process the longest stream
         * from start to finish before moving onto next.
         */
        boolean first = true;

        for (Activity outgoingActivity : outgoingActivities) {
            /*
             * Don't re-process things already laid out (which would happen in
             * the case of a join.
             */
            if (!alreadyLaidOut.contains(outgoingActivity)) {

                /* Set location of this activity. */
                Rectangle bounds =
                        Xpdl2ModelUtil.getObjectBounds(outgoingActivity);

                Point centrePoint = new Point(0, 0);
                centrePoint.x = x + HORIZONTAL_SPACING + (bounds.width / 2);

                if (first) {
                    centrePoint.y = y;
                } else {
                    centrePoint.y = y + VERTICAL_SPACING + (bounds.height / 2);
                }

                int nominalCentreY = centrePoint.y;

                /* Ensure against overlaps (see NOTE-2 in class javadoc). */
                int yDeltaChange =
                        setUniqueLocation(centrePoint,
                                bounds.width,
                                bounds.height,
                                alreadyLaidOut);

                if (yDeltaChange > 0) {
                    /*
                     * If we needed to move activity down to avoid overlaps then
                     * go back thru upstream activities (whilst still on a
                     * single branch) and offset those by the same amount - thus
                     * keeping each branch 'straight' (see NOTE-3 in class
                     * javadoc).
                     */
                    offsetUpstreamActivities(outgoingActivity,
                            yDeltaChange,
                            nominalCentreY);
                }

                /* Set it. */
                setLocation(outgoingActivity, centrePoint.x, centrePoint.y);

                alreadyLaidOut.add(outgoingActivity);

                /* Then process it's down stream activities. */
                layoutOutgoingActivityStreams(flowAnalyser
                        .getOutgoingActivities(outgoingActivity.getId(), true),
                        alreadyLaidOut,
                        centrePoint.x + (bounds.width / 2),
                        centrePoint.y);

                /*
                 * Set up ready for the next sibling outgoing activity.
                 * 
                 * After first time round set y to bottom co-ordinate of this
                 * activity (so next below can be offset from there.
                 */
                first = false;

                /*
                 * Note that the location we just set above on outgoingActivity
                 * MAY have changed if one of it's downstream activities was
                 * offset to avoid overlap - so we will re-get it's location
                 * before setting the next Y
                 */
                bounds = Xpdl2ModelUtil.getObjectBounds(outgoingActivity);

                /* We also need to locate the annotation now if there is one. */
                setActivityArtifactLocation(outgoingActivity, bounds);

                /*
                 * Set y to bottom-edge ready to offset for next sibling
                 * outgoing activity.
                 */
                y = bounds.y + bounds.height;
            }
        }

        return;
    }

    /**
     * Offset the upstream activities in the same single stream by the given
     * offset.
     * <p>
     * We go back thru upstream activities (whilst still on a single branch as
     * indicated by having the same nominalCentreY) and offest those by the same
     * amount - thus keeping each branch 'straight' (see NOTE-3 in class
     * javadoc).
     * 
     * @param activity
     * @param yDeltaChange
     * @param nominalCentreY
     */
    private void offsetUpstreamActivities(Activity activity, int yDeltaChange,
            int nominalCentreY) {

        Set<Activity> upstreamActivities =
                flowAnalyser.getUpstreamActivities(activity.getId());

        for (Activity upstreamActivity : upstreamActivities) {

            Point location = getLocation(upstreamActivity);

            if (location.y == nominalCentreY) {
                setLocation(upstreamActivity, location.x, location.y
                        + yDeltaChange);
            }
        }
    }

    /**
     * Given the nominal centre point location of an given activity ensure that
     * it will not overlap any activity that has already been laid out (see
     * NOTE-2 in class javadoc).
     * 
     * @param centrePoint
     * @param width
     * @param height
     * @param alreadyLaidOut
     * 
     * @return The amount that centrePoint.y was changed to avoind overlap.
     */
    private int setUniqueLocation(Point centrePoint, int width, int height,
            Set<Activity> alreadyLaidOut) {

        Rectangle originalBnds =
                new Rectangle(centrePoint.x - (width / 2), centrePoint.y
                        - (height / 2), width, height);

        Rectangle adjustedBnds =
                new Rectangle(originalBnds.x, originalBnds.y,
                        originalBnds.width, originalBnds.height);

        boolean intersects;
        do {
            intersects = false;

            for (Activity activity : alreadyLaidOut) {
                Rectangle b = Xpdl2ModelUtil.getObjectBounds(activity);

                if (adjustedBnds.intersects(b)) {
                    adjustedBnds.y = (b.y + b.height) + VERTICAL_SPACING;
                    intersects = true;
                    break;
                }
            }
        } while (intersects);

        centrePoint.y = adjustedBnds.y + (adjustedBnds.height / 2);

        return adjustedBnds.y - originalBnds.y;
    }

    /**
     * size lanes according to the activities in them.
     */
    private void sizeLanes() {
        for (Lane lane : Xpdl2ModelUtil.getProcessLanes(process)) {

            List<Activity> activitiesInLane =
                    Xpdl2ModelUtil.getActivitiesInLane(lane);

            if (activitiesInLane.size() > 0) {
                Activity topMostActivity = null;
                int topMostActivityY = Integer.MAX_VALUE;
                int bottomMostActivityY = Integer.MIN_VALUE;

                for (Activity activity : activitiesInLane) {
                    Rectangle b = Xpdl2ModelUtil.getObjectBounds(activity);

                    if (b.y < topMostActivityY) {
                        topMostActivity = activity;
                        topMostActivityY = b.y;
                    }

                    if ((b.y + b.height) > bottomMostActivityY) {
                        bottomMostActivityY = b.y + b.height;
                    }
                }

                int topMargin = LANE_TOP_MARGIN;
                /*
                 * Allow extra at bottom for event text etc.
                 */
                int bottomMargin = LANE_BOTTOM_MARGIN;

                int totalHeight =
                        (bottomMostActivityY - topMostActivityY) + topMargin
                                + bottomMargin;

                int deltaY =
                        Xpdl2ModelUtil.getObjectBounds(topMostActivity).y
                                - topMargin;

                for (Activity activity : activitiesInLane) {
                    Point location = getLocation(activity);

                    setLocation(activity, location.x, location.y - deltaY);
                }

                NodeGraphicsInfo laneInfo =
                        Xpdl2ModelUtil.getNodeGraphicsInfo(lane);
                if (laneInfo == null) {
                    laneInfo = Xpdl2Factory.eINSTANCE.createNodeGraphicsInfo();
                    lane.getNodeGraphicsInfos().add(laneInfo);
                }

                laneInfo.setHeight(totalHeight);
            }
        }
    }

    /**
     * @param activityOrArtifact
     * @return Location of given activity/artifact
     */
    private Point getLocation(GraphicalNode activityOrArtifact) {
        NodeGraphicsInfo nodeGraphicsInfo =
                Xpdl2ModelUtil.getNodeGraphicsInfo(activityOrArtifact);

        if (nodeGraphicsInfo != null) {
            Coordinates coords = nodeGraphicsInfo.getCoordinates();

            if (coords != null) {
                return new Point((int) coords.getXCoordinate(),
                        (int) coords.getYCoordinate());
            }
        }

        return new Point(0, 0);
    }

    /**
     * Set location of given activity.
     * 
     * @param activityOrArtifact
     * @param x
     * @param y
     */
    private void setLocation(GraphicalNode activityOrArtifact, int x, int y) {
        NodeGraphicsInfo nodeGraphicsInfo =
                Xpdl2ModelUtil.getNodeGraphicsInfo(activityOrArtifact);

        if (nodeGraphicsInfo == null) {
            nodeGraphicsInfo = Xpdl2Factory.eINSTANCE.createNodeGraphicsInfo();
            activityOrArtifact.getNodeGraphicsInfos().add(nodeGraphicsInfo);
        }

        Coordinates coordinates = Xpdl2Factory.eINSTANCE.createCoordinates();
        coordinates.setXCoordinate(x);
        coordinates.setYCoordinate(y);

        nodeGraphicsInfo.setCoordinates(coordinates);

        /*
         * Whenever we set (or reset) the location of an activity, reset the
         * location of any associated artifacts.
         */
        if (activityOrArtifact instanceof Activity) {
            setActivityArtifactLocation((Activity) activityOrArtifact,
                    Xpdl2ModelUtil.getObjectBounds(activityOrArtifact));
        }

    }

    /**
     * If the activity has a text annotation then set it's location.
     * 
     * @param activity
     * @param activityBounds
     */
    private void setActivityArtifactLocation(Activity activity,
            Rectangle activityBounds) {
        /* Just in case we have multiple artifacts. */
        int offsetX = 0;
        int offsetY = 0;

        /* Look for associations between activity and artifacts. */
        String activityId = activity.getId();

        Package pkg = activity.getProcess().getPackage();

        for (Association association : pkg.getAssociations()) {
            String artifactId = null;

            if (activityId.equals(association.getSource())) {
                artifactId = association.getTarget();
            } else if (activityId.equals(association.getTarget())) {
                artifactId = association.getSource();
            }

            if (artifactId != null) {
                Artifact artifact = pkg.getArtifact(artifactId);

                if (artifact != null) {
                    setLocation(artifact,
                            (activityBounds.x - (HORIZONTAL_SPACING / 4))
                                    + offsetX,
                            (activityBounds.y - (ACTIVITY_ARTIFACT_VERTICAL_OFFSET * 2))
                                    + offsetY);

                    offsetX += 10;
                    offsetY += 10;
                }
            }
        }
    }

    /**
     * Sort comparator that sorts by number of downstream activities from a
     * given activity (largest-first)
     * 
     * @author aallway
     * @since 31 Oct 2012
     */
    private final class LargestDownstreamActivitiesComparator implements
            Comparator<Activity> {
        @Override
        public int compare(Activity activity1, Activity activity2) {
            Set<String> act1UpstreamActivityIds =
                    flowAnalyser.getUpstreamActivityIds(activity1.getId());

            int act1LongestDownstream =
                    getLongestDownStreamStream(activity1,
                            act1UpstreamActivityIds,
                            0,
                            0,
                            new HashSet<Activity>());

            Set<String> act2UpstreamActivityIds =
                    flowAnalyser.getUpstreamActivityIds(activity2.getId());
            int act2LongestDownstream =
                    getLongestDownStreamStream(activity2,
                            act2UpstreamActivityIds,
                            0,
                            0,
                            new HashSet<Activity>());

            int method2 = act2LongestDownstream - act1LongestDownstream;

            return method2;
        }

        private int getLongestDownStreamStream(Activity activity,
                Set<String> upstreamActivityIds, int currentCount,
                int longestSoFar, HashSet<Activity> alreadyProcessed) {
            /* Prevent infinite recursion when have a loop of activities. */
            if (alreadyProcessed.contains(activity)) {
                return longestSoFar;
            }

            alreadyProcessed.add(activity);

            currentCount++;
            if (currentCount > longestSoFar) {
                longestSoFar = currentCount;
            }

            List<Activity> outgoingActivities =
                    flowAnalyser.getOutgoingActivities(activity.getId());

            for (Activity outgoingActivity : outgoingActivities) {
                if (!upstreamActivityIds.contains(outgoingActivity.getId())) {

                    longestSoFar =
                            getLongestDownStreamStream(outgoingActivity,
                                    upstreamActivityIds,
                                    currentCount,
                                    longestSoFar,
                                    alreadyProcessed);
                }
            }

            return longestSoFar;

        }

    }

    /**
     * Layout exception.
     * 
     * @author aallway
     * @since 31 Oct 2012
     */
    public static class LayoutException extends Exception {

        private static final long serialVersionUID = -6716843762825314391L;

        private LayoutException(String message) {
            super(message);
        }
    }

}
