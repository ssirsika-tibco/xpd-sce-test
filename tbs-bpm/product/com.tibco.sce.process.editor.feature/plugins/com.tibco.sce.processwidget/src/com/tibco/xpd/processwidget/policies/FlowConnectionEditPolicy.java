/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.tibco.xpd.processwidget.policies;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import com.tibco.xpd.processwidget.figures.IHighlightableFigure;

/**
 * EditPolicy for Flow Connection
 * 
 * @author Daniel Lee
 */
public class FlowConnectionEditPolicy extends ConnectionEditPolicy {

    /**
     * @return host, casted to polylyne connection
     */
    private PolylineConnection getConnectionFigure() {
        return ((PolylineConnection) ((GraphicalEditPart) getHost())
                .getFigure());
    }

    /**
     * @see ConnectionEditPolicy#getDeleteCommand(org.eclipse.gef.requests.GroupRequest)
     */
    protected Command getDeleteCommand(GroupRequest request) {
        return null;
    }

    /**
     * @see org.eclipse.gef.editpolicies.AbstractEditPolicy#getTargetEditPart(org.eclipse.gef.Request)
     */
    public EditPart getTargetEditPart(Request request) {
        // We don't do anything with create in this policy so we shouldn't return as a valid edit part!
        /*if (REQ_CREATE.equals(request.getType()))
            return getHost();*/
        return null;
    }

    /**
     * @see org.eclipse.gef.editpolicies.AbstractEditPolicy#eraseTargetFeedback(org.eclipse.gef.Request)
     */
    public void eraseTargetFeedback(Request request) {
        if (!REQ_CREATE.equals(request.getType())) {
            PolylineConnection connectionFigure = getConnectionFigure();
//            connectionFigure.setLineWidth(1);

            IFigure src = connectionFigure.getSourceAnchor().getOwner();
            if (src instanceof IHighlightableFigure) {
                ((IHighlightableFigure)src).setHighlight(false);
            }
            
            IFigure tgt = connectionFigure.getTargetAnchor().getOwner();
            if (tgt instanceof IHighlightableFigure) {
                ((IHighlightableFigure)tgt).setHighlight(false);
            }
        }
    }

    /**
     * @see org.eclipse.gef.editpolicies.AbstractEditPolicy#showTargetFeedback(org.eclipse.gef.Request)
     */
    public void showTargetFeedback(Request request) {
        if (!REQ_CREATE.equals(request.getType())) {
            PolylineConnection connectionFigure = getConnectionFigure();
//            connectionFigure.setLineWidth(2);
            
            IFigure src = connectionFigure.getSourceAnchor().getOwner();
            if (src instanceof IHighlightableFigure) {
                ((IHighlightableFigure)src).setHighlight(true);
            }
            
            IFigure tgt = connectionFigure.getTargetAnchor().getOwner();
            if (tgt instanceof IHighlightableFigure) {
                ((IHighlightableFigure)tgt).setHighlight(true);
            }
        }
    }

}