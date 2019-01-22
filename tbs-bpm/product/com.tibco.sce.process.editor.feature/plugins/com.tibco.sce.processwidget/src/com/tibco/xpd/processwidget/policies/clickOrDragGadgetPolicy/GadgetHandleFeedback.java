/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.EllipseAnchor;
import org.eclipse.draw2d.Polyline;
import org.eclipse.gef.GraphicalEditPart;

/**
 * Small data class for holding a gadget handle figure, the line that attaches
 * it to the host figure, the host figure anchor and the gadget anchor.
 * 
 * @author aallway
 * @since 3.2
 */
public class GadgetHandleFeedback {
    private ClickOrDragGadgetHandle gadgetHandle;

    private GadgetDragLine gadgetDragLine;

    private ConnectionAnchor hostAnchor;

    private ConnectionAnchor gadgetAnchor;

    private BaseClickOrDragGadgetAnchorFactory anchorFactory;

    GadgetHandleFeedback(GraphicalEditPart hostEditPart,
            AbstractClickOrDragGadgetInfo gadgetInfo,
            BaseClickOrDragGadgetAnchorFactory anchorFactory) {

        this.anchorFactory = anchorFactory;

        gadgetHandle =
                new ClickOrDragGadgetHandle(hostEditPart, gadgetInfo, this);

        hostAnchor = anchorFactory.getAnchor(hostEditPart.getFigure());
        gadgetHandle.setHostFigureAnchor(hostAnchor);

        gadgetAnchor = new EllipseAnchor(gadgetHandle);

        gadgetDragLine = new GadgetDragLine();

    }

    /**
     * @return the gadgetHandle
     */
    public ClickOrDragGadgetHandle getGadgetHandle() {
        return gadgetHandle;
    }

    /**
     * @return the gadgetDragLine
     */
    GadgetDragLine getGadgetDragLine() {
        return gadgetDragLine;
    }

    /**
     * @return the gadgetDragLine
     */
    public Polyline getDragLine() {
        return gadgetDragLine;
    }

    /**
     * @return the hostAnchor
     */
    public ConnectionAnchor getHostAnchor() {
        return hostAnchor;
    }

    /**
     * @return the gadgetAnchor
     */
    public ConnectionAnchor getGadgetAnchor() {
        return gadgetAnchor;
    }

    public void setAlpha(int alpha) {
        gadgetHandle.setAlpha(alpha);
        gadgetDragLine.setAlpha(alpha);
    }

    public int getAlpha(int alpha) {
        return gadgetHandle.getAlpha();
    }

    public void setMinimumAlpha(int minAlpha) {
        gadgetHandle.setMinimumAlpha(minAlpha);
        gadgetDragLine.setMinimumAlpha(minAlpha);
    }

    public void setVisible(boolean visible) {
        gadgetHandle.setVisible(visible);
        gadgetDragLine.setVisible(visible);
    }
}