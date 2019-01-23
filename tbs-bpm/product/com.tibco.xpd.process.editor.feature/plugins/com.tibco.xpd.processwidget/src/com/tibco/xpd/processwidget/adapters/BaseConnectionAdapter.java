/**
 * 
 */
package com.tibco.xpd.processwidget.adapters;

import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * Base Flow adapter for Message and Sequence flows and association connections
 * 
 * @author wzurek
 */
public interface BaseConnectionAdapter extends NamedElementAdapter, GraphicalColorAdapter {

    /**
     * List of locations of bendpoints (<code>BendpointType</code>)
     * 
     * @return list of <code>BendpointType</code>s
     */
    List getBendpoints();

    /**
     * Create command to move bendpoint to new location
     * 
     * @param editingDomain
     * @param index
     *            of the bendpoint to move
     * @param location
     * @return
     */
    Command getMoveBendpointCommand(EditingDomain editingDomain, int index,
            XPDBendpointType location);

    /**
     * Create command that add new bendpoint after given index
     * 
     * @param editingDomain
     * @param index
     * @param location
     * @return
     */
    Command getCreateBendpointCommand(EditingDomain editingDomain, int index,
            XPDBendpointType location);

    /**
     * Create command that delete given bendpoint
     * 
     * @param editingDomain
     * @param index
     * @return
     */
    Command getDeleteBendpointCommand(EditingDomain editingDomain, int index);

    /**
     * Reconnect target to given node
     * 
     * @param editingDomain
     * @param targetNode
     * @param endAnchorPos
     *            Percent position of anchor point on target object/line's
     *            boundary line
     * @return
     */
    Command getSetTargetCommand(EditingDomain editingDomain,
            EObject targetNode, Double endAnchorPos);

    /**
     * Reconnect source to given node
     * 
     * @param editingDomain
     * @param sourceNode
     * @param startAnchorPos
     *            Percent position of anchor point on source object/line's
     *            boundary line
     * @return
     */
    Command getSetSourceCommand(EditingDomain editingDomain,
            EObject sourceNode, Double srcAnchorPos);

    /**
     * Source Node (EObject that could be adapted to FlowNodeAdapter)
     * 
     * @return EObject that could be adapted to Activity
     */
    EObject getSourceNode();

    /**
     * Target Node (EObject that could be adapted to FlowNodeAdapter)
     * 
     * @return EObject that could be adapted to Activity
     */
    EObject getTargetNode();

    /**
     * List of associations
     * 
     * @return unmodificable list of source associations
     */
    List getSourceAssociations();

    /**
     * List of associations
     * 
     * @return unmodificable list of target associations
     */
    List getTargetAssociations();

    /**
     * Create association that starts in this object and finish on targetObject
     * with given list of initial bendpoints
     * 
     * @param editingDomain
     * @param targetNode
     * @param bendPoints
     * @param startAnchorPos
     *            Percent position of anchor point on source object/line's
     *            boundary line
     * @param endAnchorPos
     *            Percent position of anchor point on target object/line's
     *            boundary line
     * @return
     */
    Command getCreateAssociationCommand(EditingDomain editingDomain,
            EObject targetNode, List bendPoints, Double startAnchorPos,
            Double endAnchorPos, String lineColor);

    /**
     * Return the user specified non-default start anchor position for the
     * connection
     * 
     * @return Start anchor position (0.0 - 100.0) if specifically set by user
     *         OR null if none set.
     */
    Double getStartAnchorPosition();

    /**
     * Return the user specified non-default end anchor position for the
     * connection
     * 
     * @return End anchor position (0.0 - 100.0) if specifically set by user OR
     *         null if none set.
     */
    Double getEndAnchorPosition();

    /**
     * Set the position of the connection label.
     * 
     * @param editingDomain
     * @param connectionLabelPosition
     *            Connection label position (or null to return to default);
     * @return
     */
    Command getSetLabelPositionCommand(EditingDomain editingDomain,
            ConnectionLabelPosition connectionLabelPosition);

    /**
     * Get connection label position or null if default positioning required
     * 
     * @return
     */
    ConnectionLabelPosition getLabelPosition();

    /**
     * Get the command to set the size of the conneciton label
     * 
     * @param editingDomain
     * @param size
     * @return
     */
    Command getSetLabelSizeCommand(EditingDomain editingDomain, Dimension size);

    /**
     * Get the preferred size of the connection label.
     * @return  preferred label size or null if none set.
     */
    Dimension getLabelSize();

    
}
