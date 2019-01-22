/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.deploy.ui.navigator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.navigator.CommonDropAdapter;

/**
 * Delegate to handle a drop. Only local selection transfer is supported.
 * 
 * @author Jan Arciuchiewicz
 */
public abstract class DropAssistantDelegate {

    /**
     * Validates dropping on the given object. This method is called whenever
     * some aspect of the drop operation changes.
     * <p>
     * Subclasses must implement this method to define which drops make sense.
     * If clients return true, then they will be allowed to handle the drop in
     * {@link #handleDrop(CommonDropAdapter, DropTargetEvent, Object) }.
     * </p>
     * 
     * @param target
     *            the object that the mouse is currently hovering over, or
     *            <code>null</code> if the mouse is hovering over empty space
     * @param operation
     *            the current drag operation (copy, move, etc.)
     * @param transferType
     *            the current transfer type
     * @return A status indicating whether the drop is valid.
     */
    public abstract IStatus validateDrop(Object target, int operation,
            TransferData transferType);

    /**
     * Carry out the DND operation.
     * 
     * @param aDropAdapter
     *            The Drop Adapter contains information that has already been
     *            parsed from the drop event.
     * @param aDropTargetEvent
     *            The drop target event.
     * @param aTarget
     *            The object being dragged onto
     * @return A status indicating whether the drop completed OK.
     */
    public abstract IStatus handleDrop(CommonDropAdapter aDropAdapter,
            DropTargetEvent aDropTargetEvent, Object aTarget);

}
