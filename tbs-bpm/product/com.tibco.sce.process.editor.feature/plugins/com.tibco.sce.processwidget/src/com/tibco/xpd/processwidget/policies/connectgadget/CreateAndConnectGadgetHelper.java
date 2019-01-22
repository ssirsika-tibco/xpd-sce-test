/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.policies.connectgadget;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.SnapToHelper;

import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorConfigurationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorObjectType;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.adapters.CreateAndConnectObjectPair;
import com.tibco.xpd.processwidget.adapters.CreateAndConnectObjectPair.CreateAndConnectConnectionType;
import com.tibco.xpd.processwidget.adapters.CreateAndConnectObjectPair.CreateAndConnectObjectType;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.figures.XPDLineUtilities;
import com.tibco.xpd.processwidget.parts.BaseConnectableNodeEditPart;
import com.tibco.xpd.processwidget.parts.BaseConnectionEditPart;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.EditPartUtil;
import com.tibco.xpd.processwidget.parts.EventEditPart;
import com.tibco.xpd.processwidget.parts.LaneEditPart;
import com.tibco.xpd.processwidget.parts.PoolEditPart;
import com.tibco.xpd.processwidget.parts.ProcessEditPart;
import com.tibco.xpd.processwidget.parts.SequenceFlowEditPart;
import com.tibco.xpd.processwidget.parts.TaskEditPart;
import com.tibco.xpd.processwidget.parts.WidgetRootEditPart;
import com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy.ClickOrDragGadgetRequest;

/**
 * CreateAndConnectObjectTypesFactory
 * <p>
 * Given the potential source of a link and the target of a link get a list of
 * the valid target object types (and valid connection type to that object) that
 * can be created IF the target is a valid container for the new object.
 * <p>
 * The list MAY contain {@link CreateAndConnectObjectType#SEPARATOR} to indicate
 * where a popup menu separator would be appropriate.
 * 
 * @author aallway
 * @since 3.3 (19 Aug 2009)
 */
public class CreateAndConnectGadgetHelper {

    public static final CreateAndConnectGadgetHelper INSTANCE =
            new CreateAndConnectGadgetHelper();

    /**
     * Given the potential source of a link and the target of a link get a list
     * of the valid target object types (and valid connection type to that
     * object) that can be created IF the target is a valid container for the
     * new object.
     * <p>
     * The list MAY contain {@link CreateAndConnectObjectType#SEPARATOR} to
     * indicate where a popup menu separator would be appropriate.
     * 
     * @param source
     * @param target
     * @param absLocation
     * 
     * @return List of valid object/connection type pairs and that could be
     *         created - empty if there are no valid possibilities.
     */
    public Collection<CreateAndConnectObjectPair> getCreateAndConnectObjectPairs(
            EditPart source, EditPart target, Point absLocation) {

        List<CreateAndConnectObjectPair> validPairs = Collections.emptyList();

        if (source instanceof BaseGraphicalEditPart) {
            BaseGraphicalEditPart sourceBGEP = (BaseGraphicalEditPart) source;

            if (EditPartUtil.isArtifactEditPart(source)) {
                /*
                 * Get valid create and connect pairs for when source is
                 * artifact
                 */
                validPairs =
                        getCreateAndConnectTypesFromArtifact(sourceBGEP,
                                target,
                                absLocation);

            } else if (EditPartUtil.isAttachedCatchCompensation(source)) {
                /*
                 * Get valid create and connect pairs for when source is task
                 * boundary catch compensation event.
                 */
                validPairs =
                        getCreateAndConnectTypesFromCatchCompensation(sourceBGEP,
                                target,
                                absLocation);

            } else if (sourceBGEP instanceof BaseConnectableNodeEditPart) {
                /*
                 * If it's not an artifact and not a catch compensation and it
                 * is connectable then its a normal flow activity.
                 */
                validPairs =
                        getCreateAndConnectTypesFromActivity(sourceBGEP,
                                target,
                                absLocation);

            }
        }

        /*
         * Now exclude any type of object that has been completely excluded via
         * preference/extension
         */
        if (source instanceof BaseGraphicalEditPart) {
            ProcessEditPart processEditPart =
                    ((BaseGraphicalEditPart) source).getParentProcess();

            if (processEditPart != null) {
                Set<ProcessEditorObjectType> excludedObjectTypes =
                        ProcessEditorConfigurationUtil
                                .getExcludedObjectTypes(processEditPart
                                        .getModel());

                for (Iterator iterator = validPairs.iterator(); iterator
                        .hasNext();) {
                    CreateAndConnectObjectPair createAndConnectObjectPair =
                            (CreateAndConnectObjectPair) iterator.next();

                    if (CreateAndConnectObjectType.TASK
                            .equals(createAndConnectObjectPair.getObjectType())
                            && excludedObjectTypes
                                    .contains(ProcessEditorObjectType.task_none)) {
                        iterator.remove();

                    } else if (CreateAndConnectObjectType.START_EVENT
                            .equals(createAndConnectObjectPair.getObjectType())
                            && excludedObjectTypes
                                    .contains(ProcessEditorObjectType.start_event_none)) {
                        iterator.remove();

                    } else if (CreateAndConnectObjectType.END_EVENT
                            .equals(createAndConnectObjectPair.getObjectType())
                            && excludedObjectTypes
                                    .contains(ProcessEditorObjectType.end_event_none)) {
                        iterator.remove();

                    } else if (CreateAndConnectObjectType.GATEWAY
                            .equals(createAndConnectObjectPair.getObjectType())
                            && excludedObjectTypes
                                    .contains(ProcessEditorObjectType.gateway_exclusive_data_based)) {
                        iterator.remove();

                    } else if (CreateAndConnectObjectType.INTERMEDIATE_EVENT
                            .equals(createAndConnectObjectPair.getObjectType())
                            && excludedObjectTypes
                                    .contains(ProcessEditorObjectType.intermediate_event_none)) {
                        iterator.remove();

                    } else if (CreateAndConnectObjectType.ANNOTATION
                            .equals(createAndConnectObjectPair.getObjectType())
                            && excludedObjectTypes
                                    .contains(ProcessEditorObjectType.artifact_text_annotation)) {
                        iterator.remove();

                    } else if (CreateAndConnectObjectType.DATAOBJECT
                            .equals(createAndConnectObjectPair.getObjectType())
                            && excludedObjectTypes
                                    .contains(ProcessEditorObjectType.artifact_data_object)) {
                        iterator.remove();

                    }
                }
            }
        }

        return validPairs;
    }

    /**
     * @param sourceBGEP
     * @param target
     * @param absLocation
     * 
     * @return The list of {@link CreateAndConnectObjectPair} that are valid for
     *         the given source(artifact) and target container.
     */
    private ArrayList<CreateAndConnectObjectPair> getCreateAndConnectTypesFromArtifact(
            BaseGraphicalEditPart sourceBGEP, EditPart target, Point absLocation) {
        /*
         * When source is an artifact then we can create any type of activity in
         * any other lane or embedded sub-proc.- because associations can cross
         * these boundarys.
         */
        ArrayList<CreateAndConnectObjectPair> validPairs =
                new ArrayList<CreateAndConnectObjectPair>();
        if (isLaneOrOpenEmbeddedSubProcess(target, absLocation)
                || target instanceof SequenceFlowEditPart) {

            validPairs.add(new CreateAndConnectObjectPair(
                    CreateAndConnectObjectType.TASK,
                    CreateAndConnectConnectionType.ASSOCIATION));

            if (!ProcessWidgetType.TASK_LIBRARY.equals(sourceBGEP
                    .getProcessWidgetType())) {
                if (!ProcessWidgetType.DECISION_FLOW.equals(sourceBGEP
                        .getProcessWidgetType())) {
                    validPairs.add(new CreateAndConnectObjectPair(
                            CreateAndConnectObjectType.GATEWAY,
                            CreateAndConnectConnectionType.ASSOCIATION));

                    validPairs.add(new CreateAndConnectObjectPair(
                            CreateAndConnectObjectType.INTERMEDIATE_EVENT,
                            CreateAndConnectConnectionType.ASSOCIATION));
                }

                validPairs.add(new CreateAndConnectObjectPair(
                        CreateAndConnectObjectType.END_EVENT,
                        CreateAndConnectConnectionType.ASSOCIATION));
            }
        }

        return validPairs;
    }

    /**
     * @param sourceBGEP
     * @param target
     * @param absLocation
     * 
     * @return The list of {@link CreateAndConnectObjectPair} that are valid for
     *         the given source(catch compensation event) and target container.
     */
    private ArrayList<CreateAndConnectObjectPair> getCreateAndConnectTypesFromCatchCompensation(
            BaseGraphicalEditPart sourceBGEP, EditPart target, Point absLocation) {
        /*
         * When source is an artifact then we can create any type of activity in
         * any other lane or embedded sub-proc.- because associations can cross
         * these boundarys.
         */
        ArrayList<CreateAndConnectObjectPair> validPairs =
                new ArrayList<CreateAndConnectObjectPair>();

        /*
         * When source is a catch compensation attached to task boundary then
         * could associate to new local pool activities or new artifact in any
         * container. (Cannot have a compensation linked into a new object in
         * sequence flow!)
         */
        if (isLaneOrOpenEmbeddedSubProcess(target, absLocation) /*
                                                                 * || (target
                                                                 * instanceof
                                                                 * SequenceFlowEditPart
                                                                 * )
                                                                 */) {
            if (sourceBGEP.getParentPoolOrTask() == ((BaseGraphicalEditPart) target)
                    .getParentPoolOrTask()) {
                /*
                 * When target is in lane in same Pool or same embedded - can
                 * create a new activity to connect to...
                 */
                validPairs.add(new CreateAndConnectObjectPair(
                        CreateAndConnectObjectType.TASK,
                        CreateAndConnectConnectionType.ASSOCIATION));

                validPairs.add(new CreateAndConnectObjectPair(
                        CreateAndConnectObjectType.GATEWAY,
                        CreateAndConnectConnectionType.ASSOCIATION));

                validPairs.add(new CreateAndConnectObjectPair(
                        CreateAndConnectObjectType.INTERMEDIATE_EVENT,
                        CreateAndConnectConnectionType.ASSOCIATION));

                validPairs.add(new CreateAndConnectObjectPair(
                        CreateAndConnectObjectType.END_EVENT,
                        CreateAndConnectConnectionType.ASSOCIATION));

                validPairs.add(new CreateAndConnectObjectPair(
                        CreateAndConnectObjectType.SEPARATOR, null));
            }

            /*
             * Can always create associations to artifacts.
             */
            validPairs.add(new CreateAndConnectObjectPair(
                    CreateAndConnectObjectType.ANNOTATION,
                    CreateAndConnectConnectionType.ASSOCIATION));

            validPairs.add(new CreateAndConnectObjectPair(
                    CreateAndConnectObjectType.DATAOBJECT,
                    CreateAndConnectConnectionType.ASSOCIATION));

        }

        return validPairs;
    }

    /**
     * @param sourceBGEP
     * @param target
     * @param absLocation
     * 
     * @return The list of {@link CreateAndConnectObjectPair} that are valid for
     *         the given source(flow activity) and target container.
     */
    private ArrayList<CreateAndConnectObjectPair> getCreateAndConnectTypesFromActivity(
            BaseGraphicalEditPart sourceBGEP, EditPart target, Point absLocation) {
        ArrayList<CreateAndConnectObjectPair> validPairs =
                new ArrayList<CreateAndConnectObjectPair>();

        BaseGraphicalEditPart targetPoolOrEmbedded = null;

        if (target instanceof LaneEditPart) {
            targetPoolOrEmbedded = ((LaneEditPart) target).getParentPool();

        } else if (target instanceof TaskEditPart) {
            if (isLaneOrOpenEmbeddedSubProcess(target, absLocation)) {
                targetPoolOrEmbedded = (BaseGraphicalEditPart) target;
            }

        } else if (target instanceof SequenceFlowEditPart) {
            targetPoolOrEmbedded =
                    getSequenceFlowParentPoolOrTask((SequenceFlowEditPart) target);
        }

        if (targetPoolOrEmbedded != null) {
            if (sourceBGEP.getParentPoolOrTask() == targetPoolOrEmbedded) {
                /*
                 * When the target containing pool / embedded is same as source
                 * then sequence flow is allowed - we are not in a task library
                 * provided source is not an End Event (except that inserting
                 * onto an end event's incoming flow is ok).
                 */
                if (!ProcessWidgetType.TASK_LIBRARY.equals(sourceBGEP
                        .getProcessWidgetType())) {
                    if (!isEndEvent(sourceBGEP)
                            || (target instanceof SequenceFlowEditPart && sourceBGEP
                                    .getTargetConnections().contains(target))) {

                        validPairs.add(new CreateAndConnectObjectPair(
                                CreateAndConnectObjectType.TASK,
                                CreateAndConnectConnectionType.SEQUENCE_FLOW));
                        if (!ProcessWidgetType.DECISION_FLOW.equals(sourceBGEP
                                .getProcessWidgetType())) {
                            validPairs
                                    .add(new CreateAndConnectObjectPair(
                                            CreateAndConnectObjectType.GATEWAY,
                                            CreateAndConnectConnectionType.SEQUENCE_FLOW));

                            validPairs
                                    .add(new CreateAndConnectObjectPair(
                                            CreateAndConnectObjectType.INTERMEDIATE_EVENT,
                                            CreateAndConnectConnectionType.SEQUENCE_FLOW));
                        }
                        if (!(target instanceof SequenceFlowEditPart)) {
                            validPairs
                                    .add(new CreateAndConnectObjectPair(
                                            CreateAndConnectObjectType.END_EVENT,
                                            CreateAndConnectConnectionType.SEQUENCE_FLOW));
                        }
                    }
                }
            } else {
                /*
                 * When the source and target are in different pools then
                 * message flows are allowed.
                 */
                PoolEditPart targetPool;

                if (targetPoolOrEmbedded instanceof PoolEditPart) {
                    targetPool = (PoolEditPart) targetPoolOrEmbedded;
                } else {
                    targetPool = targetPoolOrEmbedded.getParentPool();
                }

                if (sourceBGEP.getParentPool() != targetPool) {
                    validPairs.add(new CreateAndConnectObjectPair(
                            CreateAndConnectObjectType.TASK,
                            CreateAndConnectConnectionType.MESSAGE_FLOW));

                    if (!ProcessWidgetType.DECISION_FLOW.equals(sourceBGEP
                            .getProcessWidgetType())) {
                        validPairs.add(new CreateAndConnectObjectPair(
                                CreateAndConnectObjectType.GATEWAY,
                                CreateAndConnectConnectionType.MESSAGE_FLOW));
                    }

                    validPairs.add(new CreateAndConnectObjectPair(
                            CreateAndConnectObjectType.START_EVENT,
                            CreateAndConnectConnectionType.MESSAGE_FLOW));

                    if (!ProcessWidgetType.DECISION_FLOW.equals(sourceBGEP
                            .getProcessWidgetType())) {
                        validPairs.add(new CreateAndConnectObjectPair(
                                CreateAndConnectObjectType.INTERMEDIATE_EVENT,
                                CreateAndConnectConnectionType.MESSAGE_FLOW));
                    }

                    validPairs.add(new CreateAndConnectObjectPair(
                            CreateAndConnectObjectType.END_EVENT,
                            CreateAndConnectConnectionType.MESSAGE_FLOW));

                }
            }

            if (!(target instanceof SequenceFlowEditPart)) {
                /*
                 * Can always create association from activity to new artifact
                 * in container regardless of whether it is same as source
                 * container. Unless its a sequence flow of course.
                 */
                if (!validPairs.isEmpty()) {
                    validPairs.add(new CreateAndConnectObjectPair(
                            CreateAndConnectObjectType.SEPARATOR, null));
                }

                validPairs.add(new CreateAndConnectObjectPair(
                        CreateAndConnectObjectType.ANNOTATION,
                        CreateAndConnectConnectionType.ASSOCIATION));

                validPairs.add(new CreateAndConnectObjectPair(
                        CreateAndConnectObjectType.DATAOBJECT,
                        CreateAndConnectConnectionType.ASSOCIATION));

            }
        }

        return validPairs;
    }

    /**
     * @param ep
     * @param absLocation
     * @return true if edit part is a lane or an EXPANDED embedded sub-process.
     */
    public static boolean isLaneOrOpenEmbeddedSubProcess(EditPart ep,
            Point absLocation) {

        if (ep instanceof LaneEditPart) {
            return true;
        }

        if (ep instanceof TaskEditPart) {
            TaskEditPart task = (TaskEditPart) ep;

            if (task.isEmbeddedSubProc()) {
                /*
                 * We are only interested in open embedded sub-processes when we
                 * are over the content part NOT the border
                 */
                if (!task.isCollapsedEmbeddedSubproc()) {
                    /*
                     * taskContainsAbsPoint() returns true if the point is
                     * anywhere inside the task EXCLUDING the content if it is
                     * expanded. (i.e. in the case of embedded sub-process this
                     * is the border area only.
                     */
                    boolean locationInEmbeddedContent =
                            !task.taskContainsAbsPoint(absLocation);

                    if (locationInEmbeddedContent) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Equivalent of {@link BaseGraphicalEditPart#getParentPoolOrTask()} for
     * sequence flows.
     * <p>
     * When target is sequence flow then target container is the source of the
     * flow's parent embedded sub-proc.
     * 
     * @param ep
     * @return parent Pool edit part or embedded sub-process task or null.
     */
    private BaseGraphicalEditPart getSequenceFlowParentPoolOrTask(
            SequenceFlowEditPart ep) {
        return ((BaseGraphicalEditPart) ep.getSource()).getParentPoolOrTask();
    }

    /**
     * @param target
     * @return parent lane or embedded subprocess of sequence flow of target.
     */
    private BaseGraphicalEditPart getSequenceFlowParentLaneOrTask(
            SequenceFlowEditPart ep) {
        return ((BaseGraphicalEditPart) ep.getSource()).getParentLaneOrTask();
    }

    /**
     * @param ep
     * @return true if the edit part is an end event
     */
    private boolean isEndEvent(EditPart ep) {
        if (ep instanceof EventEditPart
                && EventFlowType.FLOW_END == ((EventEditPart) ep)
                        .getEventFlowType()) {
            return true;
        }
        return false;
    }

    /**
     * @param target
     * @param absLocation
     * 
     * @return The lane or embedded sub-process that would be the container for
     *         a new object for given target (i.e. if its a lane or embedded
     *         then returns the target itself else if its a sequence flow then
     *         returns parent of source object of sequence flow).
     */
    public BaseGraphicalEditPart getTargetContainer(EditPart target,
            Point absLocation) {
        if (isLaneOrOpenEmbeddedSubProcess(target, absLocation)) {
            return (BaseGraphicalEditPart) target;
        } else if (target instanceof SequenceFlowEditPart) {
            return getSequenceFlowParentLaneOrTask((SequenceFlowEditPart) target);
        }
        return null;
    }

    /**
     * @param absLocation
     * @return The edit part that should actually be targeted by the request
     *         (i.e. if mouse outside of lanes then appropriate lane etc).
     */
    public EditPart getCreateAndConnectActualTarget(EditPart target,
            Point absLocation) {
        EditPart actualTarget;
        if (target instanceof WidgetRootEditPart
                || target instanceof ProcessEditPart) {
            actualTarget = redirectCreateAndConnectRequest(target, absLocation);
        } else {
            actualTarget = target;
        }
        return actualTarget;
    }

    /**
     * @param target
     * @param absLocation
     * 
     * @return Given a WidgetRootEditPart / ProcessEditPart find an appropriate
     *         lane to redirect a request to (for create and connect object when
     *         user drags off end of lane.
     */
    private EditPart redirectCreateAndConnectRequest(EditPart target,
            Point absLocation) {
        EditPart redirectPart = null;

        ProcessEditPart processEP = null;

        if (target instanceof ProcessEditPart) {
            processEP = (ProcessEditPart) target;
        } else if (target instanceof WidgetRootEditPart) {
            processEP = ((WidgetRootEditPart) target).getProcessEditPart();
        }

        if (processEP != null) {
            /*
             * Find a pool that the request is next to...
             */
            Point processRelLocation = absLocation.getCopy();
            processEP.getFigure().translateToRelative(processRelLocation);

            List processChildren = processEP.getChildren();
            for (int poolIdx = 0; poolIdx < processChildren.size(); poolIdx++) {
                EditPart processChild = (EditPart) processChildren.get(poolIdx);

                /*
                 * Check whether location is somewhere to right of pool (or
                 * below last pool)
                 */
                if (processChild instanceof PoolEditPart) {
                    PoolEditPart pool = (PoolEditPart) processChild;

                    Rectangle poolRc = pool.getFigure().getBounds();

                    if (processRelLocation.y >= poolRc.y
                            && (processRelLocation.y <= (poolRc.y + poolRc.height))) {
                        /*
                         * Find the individual lane for location.
                         */
                        List poolChildren = pool.getChildren();
                        for (int laneIdx = 0; laneIdx < poolChildren.size(); laneIdx++) {
                            EditPart poolChild =
                                    (EditPart) poolChildren.get(laneIdx);

                            if (poolChild instanceof LaneEditPart) {
                                LaneEditPart lane = (LaneEditPart) poolChild;
                                Rectangle laneRc = lane.getFigure().getBounds();

                                Point poolRelLocation = absLocation.getCopy();
                                pool.getFigure()
                                        .translateToRelative(poolRelLocation);

                                if (poolRelLocation.y >= laneRc.y
                                        && (poolRelLocation.y <= (laneRc.y + laneRc.height) || (laneIdx == poolChildren
                                                .size() - 1))) {
                                    redirectPart = lane;
                                    break;
                                }
                            }
                        }

                    } else if (poolIdx == (processChildren.size() - 1)
                            && (processRelLocation.y >= (poolRc.y + poolRc.height))) {
                        /*
                         * Cursor is below all pools/lanes - redirect to last
                         * lane of last pool.
                         */
                        List poolChildren = pool.getChildren();

                        if (!poolChildren.isEmpty()) {
                            redirectPart =
                                    (EditPart) poolChildren.get(poolChildren
                                            .size() - 1);
                        }
                    }
                }
            }
        }

        return redirectPart;
    }

    /**
     * For the given request (and prospective new object size) return a location
     * adjusted for container snap grid / alignment settings.
     * 
     * @param creq
     * @param newObjectSize
     * 
     * @return Snapped location or original location if no snapping to perform.
     */
    public static Point getSnapLocation(ClickOrDragGadgetRequest creq,
            Dimension newObjectSize) {

        /*
         * Reset info prior to re-using the snap helper (ensures that guidelines
         * are wiped)
         */
        Map extendedData = creq.getExtendedData();
        if (extendedData != null) {
            extendedData.remove(SnapToGeometry.KEY_EAST_ANCHOR);
            extendedData.remove(SnapToGeometry.KEY_NORTH_ANCHOR);
            extendedData.remove(SnapToGeometry.KEY_SOUTH_ANCHOR);
            extendedData.remove(SnapToGeometry.KEY_WEST_ANCHOR);
        }
        creq.setIsSnappedToLocation(false);

        if (creq.getDragTarget() instanceof BaseConnectionEditPart) {
            /*
             * If the target is a connection then adjust the location to point
             * on line nearest the drop location.
             */
            BaseConnectionEditPart conn =
                    (BaseConnectionEditPart) creq.getDragTarget();

            PointList points = conn.getConnectionFigure().getPoints().getCopy();

            conn.getConnectionFigure().translateToAbsolute(points);

            Point newLoc =
                    XPDLineUtilities.getPolylinePointClosestToPoint(points,
                            creq.getLocation());

            return newLoc;

        } else {
            /*
             * Target is not a connection, so place in open space.
             */
            EditPart actualTarget =
                    CreateAndConnectGadgetHelper.INSTANCE
                            .getCreateAndConnectActualTarget(creq
                                    .getDragTarget(), creq.getLocation());

            SnapToHelper helper =
                    (SnapToHelper) actualTarget.getAdapter(SnapToHelper.class);

            if (helper != null
                    && (actualTarget instanceof GraphicalEditPart)
                    && ((GraphicalEditPart) actualTarget).getContentPane() != null) {

                /*
                 * Disable snap-to location if alt key is down (standard
                 * behaviour).
                 */
                if (!creq.getTrackerInput().isAltKeyDown()) {

                    Point location = creq.getLocation().getCopy();

                    Rectangle newObjRect = new Rectangle();
                    newObjRect.width = newObjectSize.width;
                    newObjRect.height = newObjectSize.height;
                    newObjRect.x = location.x - newObjRect.width / 2;
                    newObjRect.y = location.y - newObjRect.height / 2;

                    PrecisionRectangle oldRc =
                            new PrecisionRectangle(newObjRect);
                    PrecisionRectangle newRc = oldRc.getPreciseCopy();

                    helper.snapRectangle(creq, SnapToHelper.HORIZONTAL
                            | SnapToHelper.VERTICAL, oldRc, newRc);
                    newRc.updateInts();

                    creq.setIsSnappedToLocation(true);

                    /*
                     * Have to manually create a new point because getCentre()
                     * returns a precisionPoint which does not always have
                     * correct values in both the int's and double's /
                     */
                    Point c = newRc.getCenter();
                    Point newLoc = new Point(c.x, c.y);

                    return newLoc;
                }
            }
        }
        return creq.getLocation();
    }
}
