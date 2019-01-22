/**
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.requests.custom;

import org.eclipse.gef.Request;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationEditPart;

/**
 * @author rgreen
 * 
 */
public class RefactorToAssocClassCustomRequest extends Request {

    // The editpart to be "refactored"
    private AssociationEditPart assocEP;

    /**
     * @param type
     */
    public RefactorToAssocClassCustomRequest(AssociationEditPart ep) {
        this.assocEP = ep;
        setType(IBOMCustomRequestConstants.REQ_REFACTOR_TO_ASSOCCLASS);
    }

    public AssociationEditPart getAssociationEditPart() {
        return assocEP;
    }

    public void setAssociationEditPart(AssociationEditPart ep) {
        this.assocEP = ep;
    }

}
