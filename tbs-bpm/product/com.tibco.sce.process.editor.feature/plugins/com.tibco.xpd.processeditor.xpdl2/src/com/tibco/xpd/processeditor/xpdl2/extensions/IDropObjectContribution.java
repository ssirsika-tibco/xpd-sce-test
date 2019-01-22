/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.extensions;

import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.DiagramDropObjectUtils;
import com.tibco.xpd.processwidget.adapters.DropObjectPopupItem;
import com.tibco.xpd.processwidget.adapters.DropTypeInfo;

/**
 * Interface to support the
 * com.tibco.xpd.processeditor.xpdl2.dropObjectContribution extension point.
 * <p>
 * This allows you to contribute a possible action to perform when any arbitrary
 * object(s) are dropped onto a process diagram object (via the
 * LocalSelectionTransfer drag-n-drop type.
 * <p>
 * Note: You may find useful utilities in {@link DiagramDropObjectUtils}.
 * 
 * @author aallway
 * 
 */
public interface IDropObjectContribution {

    /**
     * When drag of LocalSelectionTransfer type is performed and the mouse moves
     * over process diagram this method is called to check whether your
     * contribution handles drop of given dropObjects onto targetObject.
     * 
     * @param targetContainerObject
     *            The model object for the diagram part over which the drag is
     *            currently.
     * @param dropObjects
     *            The objects that are being dragged-dropped.
     * @param location
     *            The location of mouse (relative to the target object)
     * @param actualTargetObject
     *            The actual target under mouse (used for when target is a
     *            connection and hence may wish to delegate to the object
     *            container (such as lane) behind the sequenceflow at the given
     *            location.
     * @param userRequestedDropType
     *            DND.DROP_xxx - type of drop user has requested (by pressing
     *            ctrl and / or shift)
     * 
     * @return Drop type info specify the type of drop that can be performed (or
     *         DropTypeInfo.DROP_NONE / null if no operation can be performed).
     */
    DropTypeInfo getDropTypeInfo(Object targetContainerObject,
            List<Object> dropObjects, Point location,
            Object actualTargetObject, int userRequestedDropType);

    /**
     * When drop of LocalSelectionTransfer type is performed on a diagram object
     * this method is called to get a list of drop-popup items that you wish to
     * contribute to the drop-popup menu displayed.
     * <p>
     * DropObjectPopupItem's can be EMF commands, a list of objects that
     * represent new diagram objects to create and place in the diagram,
     * sub-menus and custom commands (see {@link DropObjectPopupItem} for more
     * details).
     * <p>
     * Note that if there is only one drop popup command type item from all
     * contributers then this will be executed immediately.
     * 
     * @param editingDomain
     *            The editing domain for any EMF commands.
     * @param targetObject
     *            The model object for the diagram part over which the drag is
     *            currently.
     * @param dropObjects
     *            The objects that are being dragged-dropped.
     * @param location
     *            The location of mouse (relative to the target object)
     * @param actualTargetObject
     *            The actual target under mouse (used for when target is a
     *            connection and hence may wish to delegate to the object
     *            container (such as lane) behind the sequenceflow at the given
     *            location.
     * @param userRequestedDropType
     *            DND.DROP_xxx - type of drop user has requested (by pressing
     *            ctrl and / or shift)
     * 
     * @return List of drop popup items or null/empty list if no contributions.
     */
    List<DropObjectPopupItem> getDropPopupItems(EditingDomain editingDomain,
            Object targetObject, List<Object> dropObjects, Point location,
            Object actualTargetObject, int userRequestedDropType);

}
