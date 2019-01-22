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

package com.tibco.xpd.processwidget.policies;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalEditPolicy;
import org.eclipse.gef.requests.LocationRequest;

import com.tibco.xpd.processwidget.figures.HoverDescriptionFigure;

/**
 * @author wzurek
 */
public class HoverInfoEditPolicy extends GraphicalEditPolicy {

    private HoverDescriptionFigure hover;

    @Override
    public void activate() {
        super.activate();
        hover = null;
    }

    @Override
    public void deactivate() {
        super.deactivate();
        if (hover != null) {
            removeFeedback(hover);
            hover = null;
        }
    }

    @Override
    public Command getCommand(Request request) {
        return null;
    }

    @Override
    public void showSourceFeedback(Request req) {
        super.showSourceFeedback(req);
    }

    /**
     * Interface for sofa provider
     */
    public interface HoverProvider {
        /**
         * @return hover text for the item
         */
        HoverInfo getHoverInfo();
    }

    @Override
    public void showTargetFeedback(Request req) {
        if (req.getType().equals(REQ_SELECTION_HOVER)) {
            LocationRequest lr = (LocationRequest) req;
            Point loc = lr.getLocation().getCopy();
            IFigure fl = getFeedbackLayer();
            fl.translateToRelative(loc);
            loc.translate(0, 22);
            if (hover != null) {
                removeFeedback(hover);
                hover = null;
            }
            if (getHost() instanceof HoverProvider) {
                HoverInfo hoverText =
                        ((HoverProvider) getHost()).getHoverInfo();
                if (hoverText != null) {
                    hover = new HoverDescriptionFigure();
                    hover.setInfo(hoverText);
                    addFeedback(hover);
                    Rectangle bnds =
                            new Rectangle(loc, hover.getPreferredSize());
                    hover.setBounds(bnds);
                }
            }
        } else {
            super.showTargetFeedback(req);
        }
    }

    @Override
    public void eraseSourceFeedback(Request req) {
        super.eraseSourceFeedback(req);
    }

    @Override
    public void eraseTargetFeedback(Request req) {
        if (req.getType().equals(REQ_SELECTION_HOVER)) {
            if (hover != null) {
                removeFeedback(hover);
                hover = null;
            }
        } else {
            super.eraseTargetFeedback(req);
        }
    }
}
