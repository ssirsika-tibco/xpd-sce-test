/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processwidget.adapters;

import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * Abstract 'container' that can provide its content.
 * 
 * Such as Lane or Embedded Subflow task
 * 
 * @author wzurek
 */
public interface ProcessContainerAdapter extends BaseProcessAdapter {
    /**
     * List of all child nodes contained by this object (from a diagram point of
     * view).
     * 
     * i.e. For a Lane this would be all artifacts (data object / diagram note)
     * and all activities (tasks, events, gateways)
     * 
     * @return
     */
    List getChildGraphicalNodes();

    /**
     * Add existing Graphical Node to this container at specyfic location.
     * 
     * Graphical Node being any Activity (task, event, gateway) or artifact
     * (dataobject, note, group).
     * 
     * @param editingDomain
     * @param model
     * @param location
     * @return
     */
    Command getAddGraphicalNodeCommand(EditingDomain editingDomain,
            Object model, Point location);

    /**
     * Create new artifact.
     * 
     * @param editingDomain
     * @param type
     * @param location
     * @param size
     * @param fillColor
     * @param lineColor
     * @param returnNewObjId
     *            On success, populated with Id of new object (null if
     *            unrequired)
     * @return
     */
    CreateAccessibleObjectCommand getCreateArtifactCommand(
            EditingDomain editingDomain, Object type, Point location,
            Dimension size, String fillColor, String lineColor);

    /**
     * Create new event.
     * 
     * @param editingDomain
     * @param flowType
     * @param eventType
     * @param location
     * @param size
     * @param fillColor
     * @param lineColor
     * @param retFlowNodeId
     *            On success, populated with Id of new object (null if
     *            unrequired)
     * @return
     */
    CreateAccessibleObjectCommand getCreateEventCommand(
            EditingDomain editingDomain, EventFlowType flowType,
            EventTriggerType eventType, Point location, Dimension size,
            String fillColor, String lineColor);

    /**
     * Create new Gateway
     * 
     * @param editingDomain
     * @param gatewayType
     * @param location
     * @param size
     * @param fillColor
     * @param lineColor
     * @param retFlowNodeId
     *            On success, populated with Id of new object (null if
     *            unrequired)
     * @return
     */
    CreateAccessibleObjectCommand getCreateGatewayCommand(
            EditingDomain editingDomain, GatewayType gatewayType,
            Point location, Dimension size, String fillColor, String lineColor);

    /**
     * Create new task
     * 
     * @param editingDomain
     * @param taskType
     * @param location
     * @param dimension
     * @param fillColor
     * @param lineColor
     * @param retFlowNodeId
     *            On success, populated with Id of new object (null if
     *            unrequired)
     * @return
     */
    CreateAccessibleObjectCommand getCreateTaskCommand(
            EditingDomain editingDomain, TaskType taskType, Point location,
            Dimension dimension, String fillColor, String lineColor);

    /**
     * Create a drop popup menu item new object (as indicated within
     * cacPair.getObjectType() and connect to it).
     * 
     * @param editingDomain
     * @param cacPair
     *            Defines the new object type and connection type.
     * @param adjustableRelativeLocation
     *            Passed as initial drop location relative to target container,
     *            can be used to adjust the position to execute the drop (for
     *            centering object etc).
     * @param process
     *            Parent process.
     * @param actualDropTarget
     *            The model object that was actuall dropped upon (could be
     *            sequence flow or same as actaulDropTargetContainer).
     * @param connectionSourceObject
     *            The object that should be the source of the new connection (if
     *            null then no connection is created - this should normally be
     *            when actualDropTarget is a sequence flow to insert on.
     * 
     * @return drop popup item.
     */
    DropObjectPopupItem getCreateAndConnectPopupItem(
            EditingDomain editingDomain, CreateAndConnectObjectPair cacPair,
            Point adjustableRelativeLocation, Object actualDropTarget,
            Object connectionSourceObject);

}
