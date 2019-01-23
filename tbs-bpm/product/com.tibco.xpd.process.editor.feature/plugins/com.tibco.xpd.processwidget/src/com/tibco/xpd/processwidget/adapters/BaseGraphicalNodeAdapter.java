/*
 * BaseGraphicalNodeAdapter.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */

package com.tibco.xpd.processwidget.adapters;

import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * Base adapter for graphical objects
 * 
 * It provide visualization properties of the object
 * 
 * @author wzurek
 */
public interface BaseGraphicalNodeAdapter extends NamedElementAdapter,
        GraphicalColorAdapter {

    /**
     * @return parent activity set / lane / process.
     */
    Object getContainer();
    
    
    /**
     * @return icon ID for the node
     */
    String getIcon();

    /**
     * @return icon change command, UnExecutableCommand if not possible
     */
    Command getSetIconCommand(EditingDomain editingDomain, String newIcon);

    /**
     * @return shape change command, UnExecutableCommand if not possible
     */
    Command getSetShapeCommand(EditingDomain editingDomain, String newShape);

    String getFont();

    /**
     * @return font change command, UnExecutableCommand if not possible
     */
    Command getSetFontCommand(EditingDomain editingDomain, String newFont);

    Point getLocation();

    /**
     * @return location change command, UnExecutableCommand if not possible
     */
    Command getSetLocationCommand(EditingDomain ed, Point newLocation,
            Dimension newDimension);

    /** null for default */
    Dimension getSize();

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
     *            Percent portion of source object line for start anchor or null
     *            for default anchoring
     * @param endAnchorPos
     *            Percent portion of target object line for end anchor or null
     *            for default anchoring
     * 
     * @return
     */
    Command getCreateAssociationCommand(EditingDomain editingDomain,
            EObject targetNode, List bendPoints, Double startAnchorPos,
            Double endAnchorPos, String lineColor);
    
    /**
     * Can the name be set on the given object? This enables/disables the direct
     * label edit facility in diagram editor.
     * 
     * @return true if name can be set.
     */
    boolean canSetName();

}
