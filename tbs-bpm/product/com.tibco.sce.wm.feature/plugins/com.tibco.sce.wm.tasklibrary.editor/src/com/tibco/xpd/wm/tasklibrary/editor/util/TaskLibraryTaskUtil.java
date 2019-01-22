/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditDomain;

import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.wm.tasklibrary.editor.TaskLibraryEditorContstants;
import com.tibco.xpd.wm.tasklibrary.editor.internal.Messages;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Coordinates;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utilities for creating new tasks.
 * 
 * @author aallway
 * @since 3.2
 */
public class TaskLibraryTaskUtil {

    /**
     * @param taskType
     * @return Create a correctly formatted task of given type.
     */
    public static Activity createDefaultTask(TaskType taskType) {
        ProcessWidgetColors colors =
                ProcessWidgetColors.getInstance(ProcessWidgetType.TASK_LIBRARY);

        return ElementsFactory.createTask(new Point(0, 0), new Dimension(
                ProcessWidgetConstants.TASK_WIDTH_SIZE,
                ProcessWidgetConstants.TASK_HEIGHT_SIZE), "", //$NON-NLS-1$
                taskType,
                colors.getGraphicalNodeColor(null,
                        taskType.getGetDefaultFillColorId()).toString(),
                colors.getGraphicalNodeColor(null,
                        taskType.getGetDefaultLineColorId()).toString());
    }

    /**
     * Place the given activity in the given lane and position it automatically
     * 
     * @param task
     * @param width
     * @param height
     * @param lane
     * @param coords
     *            This will be set with the new coordinates for task
     * @return New miniminum height for the lane.
     * 
     */
    public static int placeTaskInLane(Activity task, int width, int height,
            Lane lane, Coordinates newCoords) {
        return getDefaultNewTaskPositionInLane(task, width, height, lane, newCoords, null);
    }

    public static int getDefaultNewTaskPositionInLane(Activity task, int width, int height,
            Lane lane, Coordinates newCoords, List<Rectangle> siblingBounds) {
        //
        // Position task in lane - first build up a picture of what's there
        // already.
        Rectangle newTaskBounds;

        if (siblingBounds == null) {
            siblingBounds = getLaneChildrenBounds(lane);
        }

        // 
        // Get object that has the bottom-rightmost bottom right corner.
        Rectangle botRightObjectRect = getBottomRightMostObject(siblingBounds);

        if (botRightObjectRect == null) {
            // Empty task set.
            newTaskBounds =
                    new Rectangle(
                            TaskLibraryEditorContstants.HORIZONTAL_LAYOUT_MARGIN,
                            TaskLibraryEditorContstants.VERTICAL_LAYOUT_MARGIN,
                            width, height);
        } else {
            //
            // The bottom right most object corner is not necessarily the actual
            // bottom right most object (because other objects may encringe on
            // space to the right of it). So we should get all objects that
            // intersect to the right of the bot-rightmost object and use the
            // right most one of these to base our judgement on.
            //

            // Create a rectangle representing a row going right across the lane
            // at same height as the bottom right most object
            Rectangle botRightRow = botRightObjectRect.getCopy();
            botRightRow.width = Integer.MAX_VALUE / 2;

            // Defaulting to lowest object in lane, look for objects
            // intersecting to it's right and set the right mpost x to the one
            // with furthest right edge.
            int rightMostX = botRightObjectRect.getBottomRight().x;
            int rightMostY = botRightObjectRect.getBottomRight().y;

            for (Rectangle objBounds : siblingBounds) {
                if (objBounds.intersects(botRightRow)) {
                    if (objBounds.x > rightMostX) {
                        rightMostX = objBounds.x + objBounds.width;
                    }
                }
            }

            //
            // We now know the right edge and bottom we wanted to know, so we
            // can place the new task to it's right with a 1 task width margin.
            // BUT if this would overrun the default task set width, add it
            // below the bottom most.
            newTaskBounds =
                    new Rectangle(
                            rightMostX
                                    + TaskLibraryEditorContstants.HORIZONTAL_LAYOUT_MARGIN,
                            rightMostY
                                    - ProcessWidgetConstants.TASK_HEIGHT_SIZE,
                            width, height);
            int optimumLaneWidth =
                    getOptimumLaneWidth(Xpdl2ModelUtil.getProcess(lane));

            if (newTaskBounds.getBottomRight().x > optimumLaneWidth) {
                // Off of right edge of default size - add new row.
                newTaskBounds =
                        new Rectangle(
                                TaskLibraryEditorContstants.HORIZONTAL_LAYOUT_MARGIN,
                                rightMostY
                                        + TaskLibraryEditorContstants.VERTICAL_LAYOUT_MARGIN,
                                width, height);
            }
        }

        // 
        // Update the location of the activity.
        // Coordinates are centre of the task.
        newCoords.setXCoordinate(newTaskBounds.x + (newTaskBounds.width / 2));
        newCoords.setYCoordinate(newTaskBounds.y + (newTaskBounds.height / 2));

        // 
        // Extend lane to accommodate if necessary.
        //
        int reqdLaneHeight =
                newTaskBounds.y + newTaskBounds.height
                        + TaskLibraryEditorContstants.VERTICAL_LAYOUT_MARGIN;

        return reqdLaneHeight;
    }

    /**
     * @param lane
     * @return The list of lane's child objects bounds.,
     */
    public static List<Rectangle> getLaneChildrenBounds(Lane lane) {
        Collection<EObject> laneObjs = Xpdl2ModelUtil.getAllNodesInLane(lane);

        ArrayList<Rectangle> siblingBounds = new ArrayList<Rectangle>();

        for (EObject eo : laneObjs) {
            if (eo instanceof GraphicalNode) {
                siblingBounds.add(getNodeBounds((GraphicalNode) eo));
            }
        }

        return siblingBounds;

    }

    /**
     * @param editingDomain
     * @param task
     * @param lane
     * @return Command to set the lane for the given task.
     */
    public static Command getSetTaskLaneCommand(EditingDomain editingDomain,
            Activity task, Lane lane) {
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.TasksPropertySection_SetTaskLane_menu);
        NodeGraphicsInfo ngi =
                Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(task,
                        editingDomain,
                        cmd);
        //
        // If the lane is changing then add task to lane and
        // possition it appropriately.
        if (!lane.getId().equals(ngi.getLaneId())) {
            cmd.append(SetCommand.create(editingDomain,
                    ngi,
                    Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_LaneId(),
                    lane.getId()));

            Coordinates newCoords = Xpdl2Factory.eINSTANCE.createCoordinates();

            int reqdLaneHeight =
                    TaskLibraryTaskUtil
                            .placeTaskInLane(task,
                                    (int) ngi.getWidth(),
                                    (int) ngi.getHeight(),
                                    lane,
                                    newCoords);

            cmd.append(SetCommand.create(editingDomain,
                    ngi,
                    Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_Coordinates(),
                    newCoords));

            NodeGraphicsInfo laneNgi =
                    Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(lane,
                            editingDomain,
                            cmd);
            if (laneNgi.getHeight() < reqdLaneHeight) {
                cmd.append(SetCommand.create(editingDomain,
                        laneNgi,
                        Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_Height(),
                        new Double(reqdLaneHeight)));
            }
        }

        return cmd;
    }

    /**
     * Given a baseName return either it or it+suffixIdx so that it is a unique
     * name within the given taskLibrary process.
     * 
     * @param taskLibrary
     * @param baseName
     * 
     * @return unique name within process.
     */
    public static String getUniqueTaskName(Process taskLibrary, String baseName) {
        if (baseName == null) {
            baseName = ""; //$NON-NLS-1$
        }

        String finalName = baseName;

        int idx = 1;
        while (isDuplicateTaskName(taskLibrary, finalName)) {
            idx++;
            finalName = baseName + " " + idx; //$NON-NLS-1$
        }

        return finalName;
    }

    private static boolean isDuplicateTaskName(Process taskLibrary,
            String labelName) {

        String tokenName = NameUtil.getInternalName(labelName, false);

        Collection<Activity> activities =
                Xpdl2ModelUtil.getAllActivitiesInProc(taskLibrary);
        for (Activity act : activities) {
            if (labelName.equals(Xpdl2ModelUtil.getDisplayName(act))
                    || tokenName.equals(act.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param occupiedSpace
     * @param siblingBounds
     * @param laneObjs
     * @return The object whose bottom right corner is more bopttonm and more
     *         right than any other's - and a list of task bounds rectangles.
     */
    private static Rectangle getBottomRightMostObject(
            List<Rectangle> siblingBounds) {
        Rectangle botRightObjectRect = null;

        for (Rectangle b : siblingBounds) {

            if (botRightObjectRect == null) {
                botRightObjectRect = b.getCopy();

            } else {
                Point curBR = botRightObjectRect.getBottomRight();
                Point thisBR = b.getBottomRight();

                if (thisBR.y > curBR.y) {
                    botRightObjectRect = b.getCopy();
                } else if (thisBR.y == curBR.y) {
                    if (thisBR.x > curBR.x) {
                        botRightObjectRect = b.getCopy();
                    }
                }
            }
        }
        return botRightObjectRect;
    }

    /**
     * @param node
     * @return The bounds of the given activity / artifact in it's parent.
     */
    public static Rectangle getNodeBounds(GraphicalNode node) {
        org.eclipse.swt.graphics.Rectangle b =
                Xpdl2ModelUtil.getObjectBounds(node);
        return new Rectangle(b.x, b.y, b.width, b.height);

    }

    /**
     * @param siblingBounds
     * @return Lane optimum width (including margins) (which will either be
     *         default (DEFAULT_TASKSET_EXTENT_WIDTH) or right most current
     *         child + margin in whole library).
     */
    private static int getOptimumLaneWidth(Process process) {
        int laneWidth =
                TaskLibraryEditorContstants.DEFAULT_TASKSET_EXTENT_WIDTH;

        List<EObject> nodes = new ArrayList<EObject>();
        nodes.addAll(Xpdl2ModelUtil.getAllActivitiesInProc(process));
        nodes.addAll(Xpdl2ModelUtil.getAllArtifactsInProcess(process));

        for (EObject node : nodes) {
            if (node instanceof GraphicalNode) {
                Rectangle b = getNodeBounds((GraphicalNode) node);

                int optRight =
                        b.x
                                + b.width
                                + TaskLibraryEditorContstants.HORIZONTAL_LAYOUT_MARGIN;
                if (optRight > laneWidth) {
                    laneWidth = optRight;
                }
            }
        }
        return laneWidth;
    }
}
