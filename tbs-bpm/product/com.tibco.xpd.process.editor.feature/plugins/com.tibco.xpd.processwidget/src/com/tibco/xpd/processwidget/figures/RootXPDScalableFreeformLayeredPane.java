/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processwidget.figures;

import org.eclipse.draw2d.Graphics;

import com.tibco.xpd.processwidget.adapters.XpdFlowRoutingStyle;

/**
 * 
 * 
 * @author aallway
 * @since 15 Mar 2012
 */
public class RootXPDScalableFreeformLayeredPane extends
        XPDScalableFreeformLayeredPane {

    private XpdFlowRoutingStyle flowRoutingStyle =
            XpdFlowRoutingStyle.MultiEntryExit;

    /**
     * Create base layer (setting font makes in the default for all figures
     * underneath
     */
    public RootXPDScalableFreeformLayeredPane() {
        setFont(XPDFigureUtilities.getProcessEditorFont());
    }

    /**
     * @param flowRoutingStyle
     *            the flowRoutingStyle to set
     */
    public void setFlowRoutingStyle(XpdFlowRoutingStyle flowRoutingStyle) {
        if (!flowRoutingStyle.equals(this.flowRoutingStyle)) {
            this.flowRoutingStyle = flowRoutingStyle;

            this.invalidateTree();
        }
    }

    /**
     * @return the flowRoutingStyle
     */
    public XpdFlowRoutingStyle getFlowRoutingStyle() {
        return flowRoutingStyle;
    }

    /**
     * @see org.eclipse.draw2d.Figure#paint(org.eclipse.draw2d.Graphics)
     * 
     * @param graphics
     */
    @Override
    public void paint(Graphics graphics) {
        // long nanoTime = System.nanoTime();

        super.paint(graphics);

        // long took = System.nanoTime() - nanoTime;
        // System.out.println(String
        //                .format("%s.paint(zoom=%f) took %d nanoSecs (%d.%09d secs)", //$NON-NLS-1$
        // this.getClass().getSimpleName(),
        // this.getScale(),
        // took,
        // took / 1000000000,
        // took % 1000000000));
    }

}
