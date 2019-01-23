/**
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.requests.custom;

import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;

/**
 * @author mtorres
 * 
 */
public class RefactorBOMElementNameCustomRequest extends Request {

    // The editpart to be "refactored"
    private GraphicalEditPart shapeNodeEP;

    /**
     * @param type
     */
    public RefactorBOMElementNameCustomRequest(GraphicalEditPart ep) {
        this.shapeNodeEP = ep;
        setType(IBOMCustomRequestConstants.REQ_REFACTOR_BOMELEMENTNAME);
    }

    public GraphicalEditPart getAssociationEditPart() {
        return shapeNodeEP;
    }

    public void setAssociationEditPart(ShapeNodeEditPart ep) {
        this.shapeNodeEP = ep;
    }

}
