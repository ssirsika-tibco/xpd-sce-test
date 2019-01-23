/**
 * 
 */
package com.tibco.xpd.processwidget.policies;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Locator;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.SharedCursors;
import org.eclipse.gef.handles.BendpointCreationHandle;
import org.eclipse.gef.tools.ConnectionBendpointTracker;

/**
 * @author wzurek
 * 
 */
public class XpdBendpointCreationHandle extends BendpointCreationHandle {
    private static final int HANDLE_SIZE = 10;
    private static final int GRAPHIC_SIZE = 4;
    
    public XpdBendpointCreationHandle() {
        super();
        init();
    }

    public XpdBendpointCreationHandle(ConnectionEditPart owner, int index,
            int locatorIndex) {
        super(owner, index, locatorIndex);
        init();
    }

    public XpdBendpointCreationHandle(ConnectionEditPart owner, int index,
            Locator locator) {
        super(owner, index, locator);
        init();
    }

    public XpdBendpointCreationHandle(ConnectionEditPart owner, int index) {
        super(owner, index);
        init();
    }

    /**
     * Initializes the handle.
     */
    protected void init() {
        setPreferredSize(new Dimension(HANDLE_SIZE, HANDLE_SIZE));
    }

    protected DragTracker createDragTracker() {
        ConnectionBendpointTracker tracker;
        tracker = new XpdConnectionBendpointTracker(
                (ConnectionEditPart) getOwner(), getIndex());
        tracker.setType(RequestConstants.REQ_CREATE_BENDPOINT);
        tracker.setDefaultCursor(getCursor());
        tracker.setDisabledCursor(SharedCursors.NO);
        
        return tracker;
    }
    
    public void paintFigure(Graphics g) {
        g.pushState();
        
        Rectangle r = getBounds().getCopy();
        r.shrink((HANDLE_SIZE - GRAPHIC_SIZE)/2, (HANDLE_SIZE - GRAPHIC_SIZE)/2);

        g.setBackgroundColor(getFillColor());
        g.fillRectangle(r.x, r.y, r.width, r.height);
        g.setForegroundColor(getBorderColor()); 
        g.drawRectangle(r.x, r.y, r.width, r.height);
        
        g.popState();
    }

    

}
