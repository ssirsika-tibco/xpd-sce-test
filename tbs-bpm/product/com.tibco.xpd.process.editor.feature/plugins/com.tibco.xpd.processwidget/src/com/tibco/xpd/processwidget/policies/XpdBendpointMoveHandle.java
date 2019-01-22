/**
 * 
 */
package com.tibco.xpd.processwidget.policies;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.SharedCursors;
import org.eclipse.gef.handles.BendpointMoveHandle;
import org.eclipse.gef.tools.ConnectionBendpointTracker;
import org.eclipse.swt.graphics.Color;

/**
 * @author wzurek
 * 
 */
public class XpdBendpointMoveHandle extends BendpointMoveHandle {

    public XpdBendpointMoveHandle(ConnectionEditPart connEP,
            int bendPointIndex, int i) {
        super(connEP, bendPointIndex, i);
    }

    protected DragTracker createDragTracker() {
        ConnectionBendpointTracker tracker;
        tracker = new XpdConnectionBendpointTracker(
                (ConnectionEditPart) getOwner(), getIndex());
        tracker.setType(RequestConstants.REQ_MOVE_BENDPOINT);
        tracker.setDefaultCursor(getCursor());
        tracker.setDisabledCursor(SharedCursors.NO);
        return tracker;
    }

    /**
     * Initializes the handle.
     */
    protected void init() {
        setPreferredSize(new Dimension(DEFAULT_HANDLE_SIZE, DEFAULT_HANDLE_SIZE));
        
    }

    /**
     * Returns the color for the inside of the handle.
     * @return the color of the handle
     */
    protected Color getFillColor() {
        return (isPrimary())
            ? ColorConstants.green
            : ColorConstants.white;
    }

    /**
     * Returns the color for the outside of the handle.
     * @return the color for the border
     */
    protected Color getBorderColor() {
        return (isPrimary())
            ? ColorConstants.black
            : ColorConstants.white;
    }

}
