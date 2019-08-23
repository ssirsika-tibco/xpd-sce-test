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

import java.util.EventObject;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.dnd.DND;

import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;

/**
 * Base interface of all process widget adapters
 * 
 * @author wzurek
 */
public interface BaseProcessAdapter extends Adapter {

    /**
     * Callbacks from the model
     */
    public static interface ProcessWidgetListener {
        void notifyChanged(ProcessWidgetEvent processEvent);
    }

    /**
     * Model change event
     */
    public static class ProcessWidgetEvent extends EventObject {

        private static final long serialVersionUID = 5658166916027719579L;

        public ProcessWidgetEvent(Object source) {
            super(source);
        }
    }

    /**
     * @return commands that delete adapted object
     */
    Command getDeleteCommand(EditingDomain editingDomain);

    /**
     * Add listener to the adapter, if this listener alredy exists, it should be
     * ignored
     * 
     * @param listener
     */
    void addListener(ProcessWidgetListener listener);

    /**
     * Remove listener from the adapter
     * 
     * @param listener
     */
    void removeListener(ProcessWidgetListener listener);

    /**
     * @return adapter factory for widget adapters
     */
    public AdapterFactory getAdapterFactory();

    /**
     * Return the {@link DND}.DROP_xxx type for the given objects that the
     * process widget is attempting to drop onto a diagram object.
     * 
     * @param dropObjects
     * @param location
     *            location of drop relative to this object.
     * @param actualTarget
     *            The actual target under mouse (used for when target is a
     *            connection and hence may wish to delegate to the object
     *            container (such as lane) behind the sequenceflow at the given
     *            location.
     * @param userRequestedDropType
     *            DND.DROP_xxx - type of drop user has requested (by pressing
     *            ctrl and / or shift)
     * 
     * @return Drop type (DropTypeInfo.DROP_COPY/MOVE etc) or
     *         DropTypeInfo.DROP_NONE if no drop operation available for given
     *         object types.
     */
    public DropTypeInfo getDropTypeInfo(List<Object> dropObjects,
            Point location, Object actualTarget, int userRequestedDropType);

    /**
     * Get a list of drop items for the drag-drop popup menu.
     * <p>
     * Build your tree of DropObjectPopupItems representing...
     * <li>EMF Commands to execute</li>
     * <li>List of objects that can be used (via your
     * {@link ProcessDiagramAdapter#getPasteObjectsCommand(EditingDomain, Object, Point, java.util.Collection)}
     * implementation) to create new diagram objects at the drop location.</li>
     * <li>Sub-menus</li>
     * 
     * @param dropObjects
     * @param location
     *            location of drop relative to this object.
     * @param actualTarget
     *            The actual target under mouse (used for when target is a
     *            connection and hence may wish to delegate to the object
     *            container (such as lane) behind the sequenceflow at the given
     *            location.
     * @param userRequestedDropType
     *            DND.DROP_xxx - type of drop user has requested (by pressing
     *            ctrl and / or shift)
     * 
     * @return List of popup menu items or null if there are no commands to run
     *         (<b>Note</b> that this method will only be called if
     *         getDropTypeInfo does not return DropTypeInfo.DROP_NONE)
     */
    public List<DropObjectPopupItem> getDropPopupItems(
            EditingDomain editingDomain, List<Object> dropObjects,
            Point location, Object actualTarget, int userRequestedDropType);

    /**
     * Get the IMarker.xxx problem marker severity for this adapter's model
     * object.
     * 
     * @return IMarker.SEVERITY_xxx
     */
    public int getProblemMarkerSeverity();

    /**
     * Get list of markers for this adapter's model object.
     * 
     * @param mostSevereOnly
     *            If true then only return markers of the most severe type
     *            currently associated with the model object.
     * 
     * @return list of markers.
     */
    public List<IMarker> getProblemMarkerList(boolean mostSevereOnly);

    public ProcessWidgetType getProcessType();

    /**
     * Get the Model Object that represents the parent proess of this adapter's
     * model object.
     * 
     * @return
     */
    public Object getProcess();

    public boolean isWMFeatureAvailable();
    
    public boolean isRCPApplication();
    
    /**
     * @return The documentation URL (used for hyperlink from tooltip).
     */
    String getDocumentationURL();

    /**
     * Returns if the process is read-only.
     */
    public boolean isReadOnly();
}
