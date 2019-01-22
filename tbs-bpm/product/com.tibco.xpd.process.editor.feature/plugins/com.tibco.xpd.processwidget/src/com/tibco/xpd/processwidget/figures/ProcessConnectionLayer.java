/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processwidget.figures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

import com.tibco.xpd.processwidget.adapters.XpdFlowRoutingStyle;
import com.tibco.xpd.processwidget.figures.anchors.AbstractActivityAnchor;

/**
 * Override of standard connection layer that handles the ability on
 * flow-connections (sequence/message flow) to automatically spread all
 * connections that have {@link AbstractActivityAnchor} anchors out along the
 * most appropriate exit/entry side for that side.
 * <p>
 * This has to be done here (rather than in the anchor or in the
 * {@link BpmnFlowRouter} because they are per-connection and in order to spread
 * connection anchors out along the edge of an activity we need to be able to
 * sort them into the most appropriate order.
 * <p>
 * So we do this here - on a call to {@link #validate()} we look at all
 * connections for all activities that use {@link AbstractActivityAnchor}, group
 * the anchors by host activity and which edge (north/east/south/west) and then
 * we sort of these groups of anchors by the location of other end (or next
 * bendpoint) of the connection.
 * 
 * @author aallway
 * @since 1 Mar 2012
 */
public class ProcessConnectionLayer extends ConnectionLayer {

    private AnchorAndRefPointHorizontalSort anchorAndRefPointHorizontalSort =
            new AnchorAndRefPointHorizontalSort();

    private AnchorAndRefPointVerticalSort anchorAndRefPointVerticalSort =
            new AnchorAndRefPointVerticalSort();

    /**
     * Maximum spacing of multiple connections on one side if there is more than
     * enough room for all.
     */
    private static int MAX_MULTI_POINT_ANCHOR_SPACING = 8;

    /**
     * @see org.eclipse.draw2d.Figure#validate()
     * 
     */
    @Override
    public void validate() {
        /*
         * If we are doing multi-entry/exit point layout then we have to
         * pre-process all default location activity anchors to spread and sort
         * them into appropriate order when multi-anchors will be located on
         * single side of activity.
         */
        if (XpdFlowRoutingStyle.MultiEntryExit.equals(XPDFigureUtilities
                .getXpdRouterStyle(this))) {
            preProcesseMultiPointAnchors();
        }

        /*
         * Then layout all connections
         */
        super.validate();
    }

    /**
     * For multi-entry/exit point default location anchors we can't leave it up
     * to individual anchors to decide their position because they are not aware
     * of the other connections to / from the same objects.
     * 
     * We are, so we will calculate the anchor position (with it's help) and
     * preset it's location.
     */
    private void preProcesseMultiPointAnchors() {
        /*
         * Collate set of anchors positioned on each side of an activity.
         */
        Map<IFigure, ActivityAndAnchors> activityAndAnchorsSet =
                collateActivityAndAnchors();

        /*
         * Now we have collated a map of activity figures each with a list of
         * anchors attached to each side then we can go thru each set of these
         * and spread them out along that side.
         */
        for (Entry<IFigure, ActivityAndAnchors> entry : activityAndAnchorsSet
                .entrySet()) {
            ActivityAndAnchors activityAndAnchors = entry.getValue();

            for (Entry<Integer, List<AnchorAndTargetRefPoint>> anchorPerSideEntry : activityAndAnchors.anchorsPerSide
                    .entrySet()) {

                /*
                 * Distribute the anchors evenly and according the target
                 * location.
                 */
                distributeAnchorsOnSide(anchorPerSideEntry.getKey(),
                        anchorPerSideEntry.getValue());
            }
        }
    }

    /**
     * Collate set of anchors positioned on each side of an activity.
     * <p>
     * Each activity figure entry stores a set of groups-of-
     * {@link AbstractActivityAnchor}'s that will be located on the same side of
     * activity.
     * 
     * @return collated activity and anchors-per-side of activity.
     */
    private Map<IFigure, ActivityAndAnchors> collateActivityAndAnchors() {
        Map<IFigure, ActivityAndAnchors> activityAndAnchors =
                new HashMap<IFigure, ProcessConnectionLayer.ActivityAndAnchors>();

        List children = getChildren();

        for (Object child : children) {
            if (child instanceof RoundedPolylineConnection) {
                RoundedPolylineConnection sff =
                        (RoundedPolylineConnection) child;

                /* Only care about default activity anchoring anchor types. */
                ConnectionAnchor srcAnchor = sff.getSourceAnchor();
                if (srcAnchor instanceof AbstractActivityAnchor) {
                    IFigure activityFigure = srcAnchor.getOwner();

                    ActivityAndAnchors activityAndAnchor =
                            activityAndAnchors.get(activityFigure);
                    if (activityAndAnchor == null) {
                        activityAndAnchor =
                                new ActivityAndAnchors(activityFigure);
                        activityAndAnchors.put(activityFigure,
                                activityAndAnchor);
                    }

                    activityAndAnchor
                            .addAnchor((AbstractActivityAnchor) srcAnchor,
                                    sff,
                                    true);
                }

                /* Only care about default activity anchoring anchor types. */
                ConnectionAnchor tgtAnchor = sff.getTargetAnchor();
                if (tgtAnchor instanceof AbstractActivityAnchor) {
                    IFigure activityFigure = tgtAnchor.getOwner();

                    ActivityAndAnchors activityAndAnchor =
                            activityAndAnchors.get(activityFigure);
                    if (activityAndAnchor == null) {
                        activityAndAnchor =
                                new ActivityAndAnchors(activityFigure);
                        activityAndAnchors.put(activityFigure,
                                activityAndAnchor);
                    }

                    activityAndAnchor
                            .addAnchor((AbstractActivityAnchor) tgtAnchor,
                                    sff,
                                    false);
                }
            }
        }
        return activityAndAnchors;
    }

    /**
     * Distribute the activity anchors evenly along the given edge of activity,
     * sorted according to the other reference point on connection.
     * 
     * @param direction
     * @param anchorAndTargetRefPoints
     */
    private void distributeAnchorsOnSide(int direction,
            List<AnchorAndTargetRefPoint> anchorAndTargetRefPoints) {

        /*
         * Only need deal with a side that has more than one entry. Otherwise we
         * can safely leave it in its default mid point of side.
         */
        if (anchorAndTargetRefPoints.size() > 1) {
            if (direction == PositionConstants.EAST
                    || direction == PositionConstants.WEST) {

                if (distributeAnchorsVertically(anchorAndTargetRefPoints,
                        direction)) {
                    return;
                }

            } else if (direction == PositionConstants.NORTH
                    || direction == PositionConstants.SOUTH) {

                if (distributeAnchorsHorizontally(anchorAndTargetRefPoints,
                        direction)) {
                    return;
                }
            }
        }

        /*
         * If all else failed (like there was no room to spread connections out
         * then preset location to center of most appropriate side).
         */
        for (AnchorAndTargetRefPoint anchorAndTargetRefPoint : anchorAndTargetRefPoints) {
            Point location =
                    anchorAndTargetRefPoint.anchor
                            .getCenterOfBestSideLocation(anchorAndTargetRefPoint.refPoint);

            anchorAndTargetRefPoint.anchor
                    .presetAnchorLocationForMultiEntry(location);

            /*
             * If we have changed the location of an anchor from what it was
             * previously then we must force it to repaint. Compare it with
             * appropriate actual end of line location.
             */
            invalidateConnectionOnChange(anchorAndTargetRefPoint, location);
        }

        return;
    }

    /**
     * Distribute the activity anchors evenly along a vertical (EAST/WEST) edge
     * of activity, sorted according to the other reference point on connection.
     * 
     * @param anchorAndTargetRefPoints
     * @param direction
     * @return <code>true</code> if anchors distributed or <code>false</code> if
     *         not (this means that there was no room for ditribution so some
     *         default measure should be taken).
     */
    protected boolean distributeAnchorsVertically(
            List<AnchorAndTargetRefPoint> anchorAndTargetRefPoints,
            int direction) {

        /*
         * Sort anchors by their refPoints vertically.
         */
        Collections.sort(anchorAndTargetRefPoints,
                anchorAndRefPointVerticalSort);

        int numAnchors = anchorAndTargetRefPoints.size();

        /*
         * Can use the first anchor for some useful stuff.
         */
        AnchorAndTargetRefPoint firstAnchor = anchorAndTargetRefPoints.get(0);

        /*
         * Get the nominal margin that we are allowed to place anchors up to
         * (don't want them right in the corner.)
         */
        Dimension absMargin =
                new Dimension(TaskFigure.CORNER_ARC / 2,
                        TaskFigure.CORNER_ARC / 2);
        firstAnchor.anchor.getOwner().translateToAbsolute(absMargin);

        /*
         * COnvert the maximum spacing distance to absolute co-ords (in case of
         * zoom etc).
         */
        Dimension maxSpacing =
                new Dimension(MAX_MULTI_POINT_ANCHOR_SPACING,
                        MAX_MULTI_POINT_ANCHOR_SPACING);
        firstAnchor.anchor.getOwner().translateToAbsolute(maxSpacing);

        Rectangle bnds = firstAnchor.anchor.getActivityAbsoluteBounds();

        int availableSpace = bnds.height - (absMargin.height * 2);

        if (availableSpace >= ((numAnchors + 1) * 2)) {
            /*
             * Should be at least 2 pixel spacing per connection so that's ok
             * (else just leave them in the middle.
             */
            int middleY =
                    firstAnchor.anchor.getSideCentreLocationFromDirection(bnds,
                            direction).y;

            /*
             * Separate into lists of anchors that are above (or equal to) the
             * mid point of host activity.
             */
            List<AnchorAndTargetRefPoint> anchorsAboveMiddle =
                    new ArrayList<AnchorAndTargetRefPoint>();

            AnchorAndTargetRefPoint middleAnchor = null;

            List<AnchorAndTargetRefPoint> anchorsBelowMiddle =
                    new ArrayList<AnchorAndTargetRefPoint>();

            int i = 0;
            for (; i < anchorAndTargetRefPoints.size(); i++) {
                AnchorAndTargetRefPoint anchorAndTargetRefPoint =
                        anchorAndTargetRefPoints.get(i);

                /*
                 * We'll say "roughly" in the middle because it will account for
                 * slight rounding errors when zoomed in / out.
                 */
                if (anchorAndTargetRefPoint.refPoint.y >= (middleY - 1)
                        && anchorAndTargetRefPoint.refPoint.y <= (middleY + 1)) {
                    middleAnchor = anchorAndTargetRefPoint;
                    i++;
                    break;
                }

                if (anchorAndTargetRefPoint.refPoint.y < middleY) {
                    anchorsAboveMiddle.add(anchorAndTargetRefPoint);
                } else {
                    break;
                }
            }

            for (; i < anchorAndTargetRefPoints.size(); i++) {
                AnchorAndTargetRefPoint anchorAndTargetRefPoint =
                        anchorAndTargetRefPoints.get(i);
                anchorsBelowMiddle.add(anchorAndTargetRefPoint);
            }

            /*
             * If there is a refPoint exactly in the middle then always honour
             * it and spread the anchors above and below it separately.
             */
            if (middleAnchor != null) {
                setAnchorLocation(direction, middleY, middleAnchor);

                /**
                 * Spread the anchors above the mid point out.
                 */
                int availableSpaceInHalf = (bnds.height / 2) - absMargin.height;

                Integer spacingAboveMiddle =
                        new Integer(
                                Math.min((availableSpaceInHalf / (anchorsAboveMiddle
                                        .size() + 1)),
                                        maxSpacing.height));

                Integer spacingBelowMiddle =
                        new Integer(
                                Math.min((availableSpaceInHalf / (anchorsBelowMiddle
                                        .size() + 1)),
                                        maxSpacing.height));

                /* Prefer same as spacing above if possible. */
                Integer optimumSpacing =
                        Math.min(spacingAboveMiddle, spacingBelowMiddle);
                if (optimumSpacing < 2) {
                    optimumSpacing = 0;
                }

                Integer firstAnchorAboveY;
                if (optimumSpacing > 0) {
                    /* Enough space to spread all anchors out. */
                    firstAnchorAboveY =
                            middleY
                                    - (anchorsAboveMiddle.size() * optimumSpacing);
                } else {
                    /* Too many to fit in 1/2 side place in middle. */
                    firstAnchorAboveY = middleY - (availableSpaceInHalf / 2);
                }

                for (AnchorAndTargetRefPoint anchorAndTargetRefPoint : anchorsAboveMiddle) {
                    setAnchorLocation(direction,
                            firstAnchorAboveY,
                            anchorAndTargetRefPoint);

                    firstAnchorAboveY += optimumSpacing;
                }

                /**
                 * Spread the anchors below the mid point out.
                 */
                Integer firstAnchorBelowY;
                if (optimumSpacing > 0) {
                    /* Enough space to spread all anchors out. */
                    firstAnchorBelowY = middleY + optimumSpacing;
                } else {
                    /* Too many to fit in 1/2 side place in middle. */
                    firstAnchorBelowY = middleY + (availableSpaceInHalf / 2);
                }

                for (AnchorAndTargetRefPoint anchorAndTargetRefPoint : anchorsBelowMiddle) {
                    setAnchorLocation(direction,
                            firstAnchorBelowY,
                            anchorAndTargetRefPoint);

                    firstAnchorBelowY += optimumSpacing;
                }

                /*
                 * Calculate routing offsets. A routing offset is required when
                 * there is a set of connections that are before (or are after)
                 * the centre point and that also have the same reference point.
                 */
                calculateRoutingOffsets(anchorAndTargetRefPoints,
                        optimumSpacing,
                        direction);

            } else {
                /*
                 * Ok, it looks like the target reference points are above and
                 * below the middleY so we should spread the anchors along the
                 * whole side.
                 */
                Integer spacing =
                        new Integer(
                                Math.min((availableSpace / (numAnchors - 1)),
                                        maxSpacing.height));

                /*
                 * Calculate the position of the first anchor on this side. For
                 * odd number of anchors this is worked out so that middle
                 * anchor in list will be located at middleY, otherwise the
                 * middle 2 anchors will have middleY half way between them.
                 */
                boolean isOddNumAnchors = (numAnchors % 2) == 1;

                Integer firstAnchorY;
                if (isOddNumAnchors) {
                    firstAnchorY =
                            new Integer(middleY - ((numAnchors / 2) * spacing));
                } else {
                    firstAnchorY =
                            new Integer(middleY - ((numAnchors / 2) * spacing))
                                    + (int) (Math.floor(spacing / 2.0f));
                }

                for (AnchorAndTargetRefPoint anchorAndTargetRefPoint : anchorAndTargetRefPoints) {
                    setAnchorLocation(direction,
                            firstAnchorY,
                            anchorAndTargetRefPoint);

                    firstAnchorY += spacing;
                }

                /*
                 * Calculate routing offsets. A routing offset is required when
                 * there is a set of connections that are before (or are after)
                 * the centre point and that also have the same reference point.
                 */
                calculateRoutingOffsets(anchorAndTargetRefPoints,
                        spacing,
                        direction);
            }

            /*
             * Return now we have preset the locations.
             */
            return true;

        }
        return false;
    }

    /**
     * @param direction
     * @param locationXorY
     * @param anchorAndTargetRefPoint
     */
    protected void setAnchorLocation(int direction, Integer locationXorY,
            AnchorAndTargetRefPoint anchorAndTargetRefPoint) {
        /*
         * Get the best horizontal intersection point on figure for anchor's Y
         * position.
         */
        Point location =
                anchorAndTargetRefPoint.anchor
                        .getSideIntersectionForDirection(locationXorY,
                                direction);

        /*
         * And preset it in the anchor (this will be picked up when proper
         * layout calls this anchor for its location.
         */
        anchorAndTargetRefPoint.anchor
                .presetAnchorLocationForMultiEntry(location);

        /*
         * If we have changed the location of an anchor from what it was
         * previously then we must force it to repaint. Compare it with
         * appropriate actual end of line location.
         */
        invalidateConnectionOnChange(anchorAndTargetRefPoint, location);
    }

    /**
     * Calculate routing offsets. A routing offset is required when there is a
     * set of connections that are before (or are after) the centre point and
     * that also have the same reference point.
     * 
     * This would mean that the normal {@link BpmnFlowRouter} handling would
     * bend the connection line direction at exactly the same point for all of
     * the connections. For instance for connection from east (right) side of
     * activity, all connections to objects whose refPoint-Y is above the centre
     * line and have the same-refPoint-X will have line bent vertically up at
     * the same location.
     * 
     * So here, where we akready know ALL of the connection anchor and refPoint
     * locations, we can set an offset to allow tell {@link BpmnFlowRouter} to
     * bend the line in a slightly different location.
     * 
     * @param anchorAndTargetRefPoints
     * @param spacing
     * @param direction
     */
    protected void calculateRoutingOffsets(
            List<AnchorAndTargetRefPoint> anchorAndTargetRefPoints,
            Integer spacing, int direction) {
        /*
         * Initialise the routing offset info in each anchor
         */
        for (AnchorAndTargetRefPoint anchorAndTargetRefPoint : anchorAndTargetRefPoints) {
            anchorAndTargetRefPoint.routingOffsetProcessed = false;
            anchorAndTargetRefPoint.anchor.setRoutingOffset(null);

            if (direction == PositionConstants.EAST
                    || direction == PositionConstants.WEST) {
                if (anchorAndTargetRefPoint.refPoint.y < anchorAndTargetRefPoint.centerOfHost.y) {
                    anchorAndTargetRefPoint.preMidWay = new Boolean(true);
                } else if (anchorAndTargetRefPoint.refPoint.y > anchorAndTargetRefPoint.centerOfHost.y) {
                    anchorAndTargetRefPoint.preMidWay = new Boolean(false);
                } else {
                    anchorAndTargetRefPoint.preMidWay = null;
                }
            } else {
                if (anchorAndTargetRefPoint.refPoint.x < anchorAndTargetRefPoint.centerOfHost.x) {
                    anchorAndTargetRefPoint.preMidWay = new Boolean(true);
                } else if (anchorAndTargetRefPoint.refPoint.x > anchorAndTargetRefPoint.centerOfHost.x) {
                    anchorAndTargetRefPoint.preMidWay = new Boolean(false);
                } else {
                    anchorAndTargetRefPoint.preMidWay = null;
                }
            }
        }

        /*
         * Calculate the offsets for groups of anchors with same ref-point where
         * ref-point is before the midway point of host activity.
         */
        calculateRoutingOffsets(anchorAndTargetRefPoints,
                direction,
                Boolean.TRUE,
                spacing);

        /*
         * Calculate the offsets for groups of anchors with same ref-point where
         * ref-point is after the midway point of host activity.
         */
        calculateRoutingOffsets(anchorAndTargetRefPoints,
                direction,
                Boolean.FALSE,
                spacing);

        /*
         * If we have changed the routingOffset an anchor from what it was
         * previously then we must force it to repaint. Compare it with
         * appropriate actual end of line location.
         */
        for (AnchorAndTargetRefPoint anchorAndTargetRefPoint : anchorAndTargetRefPoints) {
            if (anchorAndTargetRefPoint.routingOffsetChanged()) {
                anchorAndTargetRefPoint.flowFigure.setValid(false);
            }
        }
    }

    /**
     * As per {@link #calculateRoutingOffsets(List, Integer)} but explicitly for
     * groups of anchors whose target reference point is either before the host
     * activity's mid-point or after it.
     * 
     * @param anchorAndTargetRefPoints
     * @param direction
     * @param doingPreMidWay
     * @param spacing
     */
    protected void calculateRoutingOffsets(
            List<AnchorAndTargetRefPoint> anchorAndTargetRefPoints,
            int direction, Boolean doingPreMidWay, Integer spacing) {
        /*
         * When handling connections whose ref-point is pre-mid-way && going in
         * negative (WEST|NORTH) direction, the anchor's host object then we
         * need to start offsets negative to the nominal BPmnFlowRouter-chosen
         * bend location (and then increase the offset past it for each
         * connection).
         * 
         * For post-mid-way or going in a positive direction (EAST|SOUTH) we
         * need to start after the nominal bend location and work backwards.
         */
        int spacingDelta;

        if (doingPreMidWay
                && (direction == PositionConstants.EAST || direction == PositionConstants.SOUTH)) {
            spacingDelta = -spacing;
        } else if (!doingPreMidWay
                && (direction == PositionConstants.WEST || direction == PositionConstants.NORTH)) {
            spacingDelta = -spacing;
        } else {
            spacingDelta = spacing;
        }

        boolean isVerticalSide =
                (direction == PositionConstants.EAST || direction == PositionConstants.WEST);

        /* Pre-mid-way connections first... */
        for (int i = 0; i < anchorAndTargetRefPoints.size(); i++) {
            AnchorAndTargetRefPoint firstWithSameRefPoint =
                    anchorAndTargetRefPoints.get(i);

            if (doingPreMidWay.equals(firstWithSameRefPoint.preMidWay)) {

                if (firstWithSameRefPoint.anchor
                        .isUsingOldSlideAlongSideMethod()) {
                    continue;
                }

                if (firstWithSameRefPoint.routingOffsetProcessed) {
                    /*
                     * This anchor is already part of a previously calculated
                     * set with same refPoint - no need to process again.
                     */
                    continue;
                }

                firstWithSameRefPoint.routingOffsetProcessed = true;

                /*
                 * Now go thru all the anchors after this anchor looking for
                 * ones with the same reference point.
                 */
                List<AnchorAndTargetRefPoint> anchorsWithSameRefAsFirst =
                        new ArrayList<ProcessConnectionLayer.AnchorAndTargetRefPoint>();
                anchorsWithSameRefAsFirst.add(firstWithSameRefPoint);

                for (int j = i + 1; j < anchorAndTargetRefPoints.size(); j++) {
                    AnchorAndTargetRefPoint otherAnchorAndTargetRefPoint =
                            anchorAndTargetRefPoints.get(j);

                    if (doingPreMidWay
                            .equals(otherAnchorAndTargetRefPoint.preMidWay)) {

                        if (isVerticalSide) {
                            /*
                             * Allow a little tolerance if things are
                             * near-enough lined up.
                             */
                            if (otherAnchorAndTargetRefPoint.refPoint.x >= (firstWithSameRefPoint.refPoint.x - 2)
                                    && otherAnchorAndTargetRefPoint.refPoint.x <= (firstWithSameRefPoint.refPoint.x + 2)) {
                                anchorsWithSameRefAsFirst
                                        .add(otherAnchorAndTargetRefPoint);
                                otherAnchorAndTargetRefPoint.routingOffsetProcessed =
                                        true;
                            }
                        } else {
                            /*
                             * Allow a little tolerance if things are
                             * near-enough lined up.
                             */
                            if (otherAnchorAndTargetRefPoint.refPoint.y >= (firstWithSameRefPoint.refPoint.y - 2)
                                    && otherAnchorAndTargetRefPoint.refPoint.y <= (firstWithSameRefPoint.refPoint.y + 2)) {
                                anchorsWithSameRefAsFirst
                                        .add(otherAnchorAndTargetRefPoint);
                                otherAnchorAndTargetRefPoint.routingOffsetProcessed =
                                        true;
                            }
                        }
                    }
                }

                if (anchorsWithSameRefAsFirst.size() > 1) {
                    /*
                     * Make sure there's enough room to spread the connection
                     * bend locations by the same amount as they are spread out
                     * along the side of the activity, if not then use maximum
                     * possible which is avalable space/number of connections ).
                     */
                    int maxAvailable;

                    if (isVerticalSide) {
                        maxAvailable =
                                Math.abs(firstWithSameRefPoint.refPoint.x
                                        - firstWithSameRefPoint.anchor
                                                .getPresetMultiEntryAnchorLocation().x);
                    } else {
                        maxAvailable =
                                Math.abs(firstWithSameRefPoint.refPoint.y
                                        - firstWithSameRefPoint.anchor
                                                .getPresetMultiEntryAnchorLocation().y);
                    }

                    if (Math.abs(spacingDelta
                            * anchorsWithSameRefAsFirst.size()) >= maxAvailable) {
                        if (spacingDelta < 1) {
                            spacingDelta =
                                    -(maxAvailable / anchorsWithSameRefAsFirst
                                            .size());
                        } else {
                            spacingDelta =
                                    (maxAvailable / anchorsWithSameRefAsFirst
                                            .size());
                        }
                    }

                    int offset =
                            ((anchorsWithSameRefAsFirst.size() / 2) * spacingDelta);

                    if ((anchorsWithSameRefAsFirst.size() % 2) == 0) {
                        /*
                         * For even number of connections then re-centre so that
                         * gap between lines is where the bend would nominally
                         * be.
                         */
                        offset += (-spacingDelta / 2);
                    }

                    for (AnchorAndTargetRefPoint sameRefXAnchor : anchorsWithSameRefAsFirst) {
                        if (isVerticalSide) {
                            sameRefXAnchor.anchor
                                    .setRoutingOffset(new Dimension(offset, 0));
                        } else {
                            sameRefXAnchor.anchor
                                    .setRoutingOffset(new Dimension(0, offset));
                        }
                        offset += -spacingDelta;
                    }
                }
            }
        }
    }

    /**
     * Distribute the activity anchors evenly along a horizontal (NPRTH/SOUTH)
     * edge of activity, sorted according to the other reference point on
     * connection.
     * 
     * @param anchorAndTargetRefPoints
     * @param direction
     * @return <code>true</code> if anchors distributed or <code>false</code> if
     *         not (this means that there was no room for distribution so some
     *         default measure should be taken).
     */
    protected boolean distributeAnchorsHorizontally(
            List<AnchorAndTargetRefPoint> anchorAndTargetRefPoints,
            int direction) {
        /*
         * Sort anchors by their refPoints horizontally.
         */
        Collections.sort(anchorAndTargetRefPoints,
                anchorAndRefPointHorizontalSort);

        int numAnchors = anchorAndTargetRefPoints.size();

        /*
         * Can use the first anchor for some useful stuff.
         */
        AnchorAndTargetRefPoint firstAnchor = anchorAndTargetRefPoints.get(0);

        /*
         * Get the nominal margin that we are allowed to place anchors up to
         * (don't want them right in the corner.)
         */
        Dimension absMargin =
                new Dimension(TaskFigure.CORNER_ARC / 2,
                        TaskFigure.CORNER_ARC / 2);
        firstAnchor.anchor.getOwner().translateToAbsolute(absMargin);

        /*
         * COnvert the maximum spacing distance to absolute co-ords (in case of
         * zoom etc).
         */
        Dimension maxSpacing =
                new Dimension(MAX_MULTI_POINT_ANCHOR_SPACING,
                        MAX_MULTI_POINT_ANCHOR_SPACING);
        firstAnchor.anchor.getOwner().translateToAbsolute(maxSpacing);

        Rectangle bnds = firstAnchor.anchor.getActivityAbsoluteBounds();

        int availableSpace = bnds.width - (absMargin.width * 2);

        if (availableSpace >= ((numAnchors + 1) * 2)) {
            /*
             * Should be at least 2 pixel spacing per connection so that's ok
             * (else just leave them in the middle).
             */
            int middleX =
                    firstAnchor.anchor.getSideCentreLocationFromDirection(bnds,
                            direction).x;

            /*
             * Separate into lists of anchors that are above (or equal to) the
             * mid point of host activity.
             */
            List<AnchorAndTargetRefPoint> anchorsLeftOfMiddle =
                    new ArrayList<AnchorAndTargetRefPoint>();

            AnchorAndTargetRefPoint middleAnchor = null;

            List<AnchorAndTargetRefPoint> anchorsRightOfMiddle =
                    new ArrayList<AnchorAndTargetRefPoint>();

            int i = 0;
            for (; i < anchorAndTargetRefPoints.size(); i++) {
                AnchorAndTargetRefPoint anchorAndTargetRefPoint =
                        anchorAndTargetRefPoints.get(i);

                /*
                 * We'll say "roughly" in the middle because it will account for
                 * slight rounding errors when zoomed in / out.
                 */
                if (anchorAndTargetRefPoint.refPoint.x >= (middleX - 1)
                        && anchorAndTargetRefPoint.refPoint.x <= (middleX + 1)) {
                    middleAnchor = anchorAndTargetRefPoint;
                    i++;
                    break;
                }

                if (anchorAndTargetRefPoint.refPoint.x < middleX) {
                    anchorsLeftOfMiddle.add(anchorAndTargetRefPoint);
                } else {
                    break;
                }
            }

            for (; i < anchorAndTargetRefPoints.size(); i++) {
                AnchorAndTargetRefPoint anchorAndTargetRefPoint =
                        anchorAndTargetRefPoints.get(i);
                anchorsRightOfMiddle.add(anchorAndTargetRefPoint);
            }

            /*
             * If there is a refPoint exactly in the middle then always honour
             * it and spread the anchors above and below it separately.
             */
            if (middleAnchor != null) {
                setAnchorLocation(direction, middleX, middleAnchor);

                /**
                 * Spread the anchors left of the mid point out.
                 */
                int availableSpaceInHalf = (bnds.width / 2) - absMargin.width;

                Integer spacingLeftOfMiddle =
                        new Integer(
                                Math.min((availableSpaceInHalf / (anchorsLeftOfMiddle
                                        .size() + 1)),
                                        maxSpacing.width));

                Integer spacingRightOfMiddle =
                        new Integer(
                                Math.min((availableSpaceInHalf / (anchorsRightOfMiddle
                                        .size() + 1)),
                                        maxSpacing.width));

                /* Prefer same as spacing above if possible. */
                Integer optimumSpacing =
                        Math.min(spacingLeftOfMiddle, spacingRightOfMiddle);
                if (optimumSpacing < 2) {
                    optimumSpacing = 0;
                }

                Integer firstAnchorLeftX;
                if (optimumSpacing > 0) {
                    /* Enough space to spread all anchors out. */
                    firstAnchorLeftX =
                            middleX
                                    - (anchorsLeftOfMiddle.size() * optimumSpacing);
                } else {
                    /* Too many to fit in 1/2 side place in middle. */
                    firstAnchorLeftX = middleX - (availableSpaceInHalf / 2);
                }

                for (AnchorAndTargetRefPoint anchorAndTargetRefPoint : anchorsLeftOfMiddle) {
                    setAnchorLocation(direction,
                            firstAnchorLeftX,
                            anchorAndTargetRefPoint);

                    firstAnchorLeftX += optimumSpacing;
                }

                /**
                 * Spread the anchors right of the mid point out.
                 */
                Integer firstAnchorRightX;
                if (optimumSpacing > 0) {
                    /* Enough space to spread all anchors out. */
                    firstAnchorRightX = middleX + optimumSpacing;
                } else {
                    /* Too many to fit in 1/2 side place in middle. */
                    firstAnchorRightX = middleX + (availableSpaceInHalf / 2);
                }

                for (AnchorAndTargetRefPoint anchorAndTargetRefPoint : anchorsRightOfMiddle) {
                    setAnchorLocation(direction,
                            firstAnchorRightX,
                            anchorAndTargetRefPoint);

                    firstAnchorRightX += optimumSpacing;
                }

                /*
                 * Calculate routing offsets. A routing offset is required when
                 * there is a set of connections that are before (or are after)
                 * the centre point and that also have the same reference point.
                 */
                calculateRoutingOffsets(anchorAndTargetRefPoints,
                        optimumSpacing,
                        direction);

            } else {
                /*
                 * Ok, it looks like the target reference points are above and
                 * below the middleX so we should spread the anchors along the
                 * whole side.
                 */
                Integer spacing =
                        new Integer(
                                Math.min((availableSpace / (numAnchors - 1)),
                                        maxSpacing.width));

                /*
                 * Calculate the position of the first anchor on this side. For
                 * odd number of anchors this is worked out so that middle
                 * anchor in list will be located at middleX, otherwise the
                 * middle 2 anchors will have middleX half way between them.
                 */
                boolean isOddNumAnchors = (numAnchors % 2) == 1;

                Integer firstAnchorX;
                if (isOddNumAnchors) {
                    firstAnchorX =
                            new Integer(middleX - ((numAnchors / 2) * spacing));
                } else {
                    firstAnchorX =
                            new Integer(middleX - ((numAnchors / 2) * spacing))
                                    + (int) (Math.floor(spacing / 2.0f));
                }

                for (AnchorAndTargetRefPoint anchorAndTargetRefPoint : anchorAndTargetRefPoints) {
                    setAnchorLocation(direction,
                            firstAnchorX,
                            anchorAndTargetRefPoint);

                    firstAnchorX += spacing;
                }

                /*
                 * Calculate routing offsets. A routing offset is required when
                 * there is a set of connections that are before (or are after)
                 * the centre point and that also have the same reference point.
                 */
                calculateRoutingOffsets(anchorAndTargetRefPoints,
                        spacing,
                        direction);
            }

            /*
             * Return now we have preset the locations.
             */
            return true;

        }
        return false;
    }

    /**
     * If we have changed the location of an anchor from what it was previously
     * then we must force it to repaint. Compare it with appropriate actual end
     * of line location.
     * 
     * @param anchorAndTargetRefPoint
     * @param location
     */
    public void invalidateConnectionOnChange(
            AnchorAndTargetRefPoint anchorAndTargetRefPoint, Point location) {
        PointList points = anchorAndTargetRefPoint.flowFigure.getPoints();
        if (points != null && points.size() > 0
                && anchorAndTargetRefPoint != null && location != null) {
            Point linePoint;

            if (anchorAndTargetRefPoint.isSource) {
                linePoint = points.getFirstPoint().getCopy();
            } else {
                linePoint = points.getLastPoint().getCopy();
            }

            Point testPoint = location.getCopy();
            anchorAndTargetRefPoint.flowFigure.translateToRelative(testPoint);

            if (!testPoint.equals(linePoint)) {
                anchorAndTargetRefPoint.flowFigure.setValid(false);
            }
        }
    }

    /**
     * An activity figure with its incoming / outgoing connection anchors
     * collated per side of activity.
     * 
     * 
     * @author aallway
     * @since 2 Mar 2012
     */
    private class ActivityAndAnchors {
        IFigure activityFigure;

        Map<Integer, List<AnchorAndTargetRefPoint>> anchorsPerSide =
                new HashMap<Integer, List<AnchorAndTargetRefPoint>>();

        private boolean isDirectSelfConnect = false;;

        ActivityAndAnchors(IFigure activityFigure) {
            this.activityFigure = activityFigure;
        }

        /**
         * Adds the given activity anchor (placing it in an appropriate per-side
         * list.
         */
        void addAnchor(AbstractActivityAnchor anchor,
                RoundedPolylineConnection flowFigure, boolean isSource) {

            boolean origSlide = anchor.isUsingOldSlideAlongSideMethod();

            /*
             * Calculate the direction from which the connection will leave the
             * anchor (i.e the side of the owner object.
             */
            Point targetRefPoint = new Point(0, 0);

            int direction =
                    getActivityAnchorDirection(anchor,
                            flowFigure,
                            isSource,
                            targetRefPoint);
            /*
             * Don't add anchor if it's been reverted to old
             * "allow slide along the side" method of location. (which may be
             * the case if the activity is over the sizing threshold because
             * it's big and linking into centre doesn't make sense.
             * 
             * Such as embedded sub-procs.
             */
            boolean nowSlide = anchor.isUsingOldSlideAlongSideMethod();
            if (!nowSlide) {

                List<AnchorAndTargetRefPoint> anchors =
                        anchorsPerSide.get(direction);

                if (anchors == null) {
                    anchors = new ArrayList<AnchorAndTargetRefPoint>();
                    anchorsPerSide.put(direction, anchors);
                }

                anchors.add(new AnchorAndTargetRefPoint(anchor, targetRefPoint,
                        flowFigure, isSource, isDirectSelfConnect));

            } else {
                /*
                 * If we are switching from not-sliding to now sliding then must
                 * make sure we re-validate the connection. And also make sure
                 * we wipe out any original preset location (which happens if
                 * we're swapping from something that was small enough to do
                 * multi-entry to something too big to do that) - otherwise even
                 * though we revalidate the getLocation() of the anchor will
                 * just pick up the previous preset.
                 */
                anchor.presetAnchorLocationForMultiEntry(null);

                Dimension prevRoutingOffset = anchor.getRoutingOffset();
                anchor.setRoutingOffset(null);

                if (origSlide != nowSlide || prevRoutingOffset != null) {
                    flowFigure.setValid(false);
                }
            }
        }

        /**
         * This method calculates the anchor direction and target (or rather
         * OTHER END OF CONNECTION) reference point for a given source or target
         * anchor.
         * <p>
         * We use the anchor's ability to do a simple 'middle of most
         * appropriate side' calculation for the direction that anchor causes
         * line to exit/enter an object.
         * 
         * @param anchor
         * @param flowFigure
         * @param isSource
         * @param targetRefPoint
         * @return
         */
        private int getActivityAnchorDirection(AbstractActivityAnchor anchor,
                RoundedPolylineConnection flowFigure, boolean isSource,
                Point targetRefPoint) {

            int direction;
            isDirectSelfConnect = false;

            /*
             * If there are any bend points, then use that as reference point to
             * calc direction.
             */
            Object constraint = flowFigure.getRoutingConstraint();
            if (constraint instanceof List && ((List) constraint).size() > 0) {
                List bendPoints = (List) constraint;

                Point refPoint = null;
                if (isSource) {
                    /*
                     * If anchor is source then reference point is first
                     * bendpoint.
                     */
                    refPoint = ((Bendpoint) bendPoints.get(0)).getLocation();
                } else {
                    /*
                     * If anchor is target then reference point is last
                     * bendpoint.
                     */
                    refPoint =
                            ((Bendpoint) bendPoints.get(bendPoints.size() - 1))
                                    .getLocation();
                }

                refPoint = refPoint.getCopy();
                flowFigure.translateToAbsolute(refPoint);

                /*
                 * Calculate the direction (this also sets the
                 * lastCalculatedDirection in the anchor.)
                 */
                Point singleEntryLocation =
                        anchor.getCenterOfBestSideLocation(refPoint);
                direction = anchor.getLastCalculatedDirection();

                targetRefPoint.x = refPoint.x;
                targetRefPoint.y = refPoint.y;

            } else {
                /*
                 * Get direction according to the anchor/object at other end of
                 * the connection.
                 */
                if (isSource) {
                    ConnectionAnchor targetAnchor =
                            flowFigure.getTargetAnchor();

                    if (targetAnchor instanceof AbstractActivityAnchor) {

                        isDirectSelfConnect =
                                anchor.getOwner() == targetAnchor.getOwner();

                        Rectangle refRect =
                                ((AbstractActivityAnchor) targetAnchor)
                                        .getActivityAbsoluteBounds();

                        /*
                         * Calculate the direction (this also sets the
                         * lastCalculatedDirection in the anchor.)
                         */

                        if (!isDirectSelfConnect) {
                            Point singleEntryLocation =
                                    anchor.getSinglePointLocation(refRect);
                            direction = anchor.getLastCalculatedDirection();

                            /*
                             * we can calculate the target's exact reference
                             * point now that we know ours.
                             */
                            Point targetAnchorLocation =
                                    ((AbstractActivityAnchor) targetAnchor)
                                            .getCenterOfBestSideLocation(singleEntryLocation);
                            targetRefPoint.x = targetAnchorLocation.x;
                            targetRefPoint.y = targetAnchorLocation.y;

                        } else {
                            /*
                             * Connecting activity back to itself is a special
                             * case.
                             */
                            direction = PositionConstants.EAST;
                            Point singleEntryLocation =
                                    anchor.getSideCentreLocationFromDirection(anchor
                                            .getActivityAbsoluteBounds(),
                                            direction);

                            targetRefPoint.x = singleEntryLocation.x + 1;
                            targetRefPoint.y = singleEntryLocation.y;

                        }

                    } else {
                        Point refPoint = targetAnchor.getReferencePoint();
                        /*
                         * Calculate the direction (this also sets the
                         * lastCalculatedDirection in the anchor.)
                         */
                        Point singleEntryLocation =
                                anchor.getCenterOfBestSideLocation(refPoint);

                        direction = anchor.getLastCalculatedDirection();

                        targetRefPoint.x = refPoint.x;
                        targetRefPoint.y = refPoint.y;
                    }

                } else {
                    /*
                     * When processing target connection, if source was an
                     * activity anchor then it's nominal (pre-spreading, centre
                     * of direction's side) location will have been set already
                     * (by the just-previous processing of the source anchor).
                     * so use that as the reference point.
                     */
                    ConnectionAnchor sourceAnchor =
                            flowFigure.getSourceAnchor();

                    if (sourceAnchor instanceof AbstractActivityAnchor) {

                        isDirectSelfConnect =
                                anchor.getOwner() == sourceAnchor.getOwner();

                        /*
                         * Calculate the direction (this also sets the
                         * lastCalculatedDirection in the anchor.)
                         */
                        if (!isDirectSelfConnect) {
                            Point refPoint =
                                    ((AbstractActivityAnchor) sourceAnchor)
                                            .getLastReturnedLocation();

                            Point singlePointLocation =
                                    anchor.getCenterOfBestSideLocation(refPoint);

                            direction = anchor.getLastCalculatedDirection();
                            targetRefPoint.x = refPoint.x;
                            targetRefPoint.y = refPoint.y;
                        } else {
                            /*
                             * Activity connecting back to itself is a special
                             * case.
                             */
                            direction = PositionConstants.NORTH;

                            Point refPoint =
                                    anchor.getSideCentreLocationFromDirection(anchor
                                            .getActivityAbsoluteBounds(),
                                            direction);

                            targetRefPoint.x = refPoint.x;
                            targetRefPoint.y = refPoint.y - 1;
                        }

                    } else {
                        Point refPoint = sourceAnchor.getReferencePoint();
                        /*
                         * Calculate the direction (this also sets the
                         * lastCalculatedDirection in the anchor.)
                         */
                        Point singlePointLocation =
                                anchor.getCenterOfBestSideLocation(refPoint);
                        direction = anchor.getLastCalculatedDirection();

                        targetRefPoint.x = refPoint.x;
                        targetRefPoint.y = refPoint.y;
                    }
                }
            }

            return direction;
        }
    }

    /**
     * Data class holding information regarding a single
     * {@link AbstractActivityAnchor} and it's reference point (othe rend of
     * connection / next bendpoint) etc
     * 
     * 
     * @author aallway
     * @since 26 Mar 2012
     */
    private class AnchorAndTargetRefPoint {
        Boolean preMidWay;

        boolean routingOffsetProcessed = false;

        Dimension originalRoutingOffset;

        AbstractActivityAnchor anchor;

        Point refPoint;

        boolean isSource;

        RoundedPolylineConnection flowFigure;

        boolean isDirectSelfConnect;

        Point centerOfHost;

        /**
         * @param anchor
         * @param isSource
         * @param flowFigure
         * @param flowFigure
         */
        public AnchorAndTargetRefPoint(AbstractActivityAnchor anchor,
                Point refPoint, RoundedPolylineConnection flowFigure,
                boolean isSource, boolean isDirectSelfConnect) {
            super();
            this.anchor = anchor;
            originalRoutingOffset = this.anchor.getRoutingOffset();
            this.anchor.setRoutingOffset(null);
            this.refPoint = refPoint;
            this.flowFigure = flowFigure;
            this.isSource = isSource;
            this.isDirectSelfConnect = isDirectSelfConnect;
            this.centerOfHost = anchor.getActivityAbsoluteBounds().getCenter();

        }

        /**
         * @return <code>true</code> if the anchor's routingOffset has changed.
         */
        public boolean routingOffsetChanged() {
            if (originalRoutingOffset == null) {
                if (anchor.getRoutingOffset() != null) {
                    return true;
                }
                return false;

            } else {
                if (!originalRoutingOffset.equals(anchor.getRoutingOffset())) {
                    return true;
                }
                return false;
            }
        }

        /**
         * @see java.lang.Object#toString()
         * 
         * @return
         */
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + ": " + anchor.toString() //$NON-NLS-1$
                    + " :: RefPoint: " + refPoint; //$NON-NLS-1$
        }
    }

    /**
     * Comparator to sort anchors by vertical position of the refPoints of the
     * things at other end of connection.
     * 
     * @author aallway
     * @since 2 Mar 2012
     */
    private static final class AnchorAndRefPointVerticalSort implements
            Comparator<AnchorAndTargetRefPoint> {
        @Override
        public int compare(AnchorAndTargetRefPoint a1,
                AnchorAndTargetRefPoint a2) {

            /* Prioritise direct self connect source anchor to start of list. */
            if (a1.isDirectSelfConnect) {
                if (a1.isSource) {
                    return -Integer.MAX_VALUE;
                }
            } else if (a2.isDirectSelfConnect) {
                if (a2.isDirectSelfConnect) {
                    return Integer.MAX_VALUE;
                }
            }

            /* Always sort the highest object first. */
            if (a1.refPoint.y < a2.refPoint.y) {
                return -1;
            } else if (a1.refPoint.y > a2.refPoint.y) {
                return 1;

            } else {
                /*
                 * If the target ref point's are same Y then sort by horizontal
                 * location.
                 */
                boolean bothAboveMidPoint =
                        (a1.refPoint.y < a1.centerOfHost.y)
                                && (a2.refPoint.y < a1.centerOfHost.y);
                boolean bothBelowMidPoint =
                        (a1.refPoint.y > a1.centerOfHost.y)
                                && (a2.refPoint.y > a1.centerOfHost.y);

                boolean leftSide = (a1.refPoint.x < a1.centerOfHost.x);

                Boolean sortLeftMostFirst = null;

                /* If both anchors are left of midPoint... */
                if (bothAboveMidPoint) {
                    if (leftSide) {
                        /*
                         * On left side, the right-most ref-point's anchor needs
                         * to be furthest up
                         */
                        sortLeftMostFirst = false;
                    } else {
                        /*
                         * On right-side then the left-most refPoint's anchor
                         * needs to be farthest up.
                         */
                        sortLeftMostFirst = true;
                    }
                } else if (bothBelowMidPoint) {
                    if (leftSide) {
                        /*
                         * On left-side, the left-most refPoint's anchor should
                         * be the Farthest up.
                         */
                        sortLeftMostFirst = true;
                    } else {
                        /*
                         * On right-side then the right-most refPoint's anchor
                         * should be farthest up.
                         */
                        sortLeftMostFirst = false;
                    }
                }

                if (Boolean.TRUE.equals(sortLeftMostFirst)) {
                    if (a1.refPoint.x < a2.refPoint.x) {
                        return -1;
                    } else if (a2.refPoint.x < a1.refPoint.x) {
                        return 1;
                    }

                } else if (Boolean.FALSE.equals(sortLeftMostFirst)) {
                    if (a1.refPoint.x > a2.refPoint.x) {
                        return -1;
                    } else if (a2.refPoint.x > a1.refPoint.x) {
                        return 1;
                    }
                }
            }

            /* Both refpoints are same x and y */
            return 0;
        }

    }

    /**
     * Comparator to sort anchors by vertical position of the refPoints of the
     * things at other end of connection.
     * 
     * @author aallway
     * @since 2 Mar 2012
     */
    private static final class AnchorAndRefPointHorizontalSort implements
            Comparator<AnchorAndTargetRefPoint> {
        @Override
        public int compare(AnchorAndTargetRefPoint a1,
                AnchorAndTargetRefPoint a2) {
            /* Prioritise direct self connect targetv anchor and to end of list. */
            if (a1.isDirectSelfConnect) {
                if (!a1.isSource) {
                    return Integer.MAX_VALUE;
                }
            } else if (a2.isDirectSelfConnect) {
                if (!a2.isSource) {
                    return -Integer.MAX_VALUE;
                }
            }

            /*
             * If the target ref point's are same X then sort by vertical
             * location.
             */
            boolean bothLeft =
                    (a1.refPoint.x < a1.centerOfHost.x)
                            && (a2.refPoint.x < a1.centerOfHost.x);

            boolean bothRight =
                    (a1.refPoint.x > a1.centerOfHost.x)
                            && (a2.refPoint.x > a1.centerOfHost.x);

            boolean topSide = (a1.refPoint.y < a1.centerOfHost.y);

            Boolean sortTopMostFirst = null;

            if (!bothLeft && !bothRight) {
                /*
                 * Where one ref-point is on left (or center) and other is on
                 * right (or center) then sort by X-pos.
                 */
                if (a1.refPoint.x < a2.refPoint.x) {
                    return -1;
                } else if (a1.refPoint.x > a2.refPoint.x) {
                    return 1;
                }

            } else {
                if (bothLeft) {
                    /* If both anchors are left of midPoint... */
                    if (topSide) {
                        /*
                         * On top side, the bottom-most ref-point's anchor needs
                         * to be furthest left
                         */
                        sortTopMostFirst = false;
                    } else {
                        /*
                         * On underside then the top-most refPoint's anchor
                         * needs to be farthest left.
                         */
                        sortTopMostFirst = true;
                    }
                } else if (bothRight) {
                    /* If both anchors are right of midPoint... */
                    if (topSide) {
                        /*
                         * On top side, the top-most refPoint's anchor should be
                         * the Farthest left.
                         */
                        sortTopMostFirst = true;
                    } else {
                        /*
                         * On underside then the bottom-most refPoint's anchor
                         * should be farthest left.
                         */
                        sortTopMostFirst = false;
                    }

                }
                if (Boolean.TRUE.equals(sortTopMostFirst)) {
                    if (a1.refPoint.y < a2.refPoint.y) {
                        return -1;
                    } else if (a2.refPoint.y < a1.refPoint.y) {
                        return 1;
                    }

                } else if (Boolean.FALSE.equals(sortTopMostFirst)) {
                    if (a1.refPoint.y > a2.refPoint.y) {
                        return -1;
                    } else if (a2.refPoint.y > a1.refPoint.y) {
                        return 1;
                    }

                }
            }

            /* Both ref's are same x and same Y. */
            return 0;
        }
    }

}
