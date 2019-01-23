/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.parts;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Graphics;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITreeBranchEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.bom.modeler.diagram.edit.policies.AssociationClassConnectorItemSemanticEditPolicy;

/**
 * @generated
 */
public class AssociationClassConnectorEditPart extends ConnectionNodeEditPart
        implements ITreeBranchEditPart {

    /**
     * @generated
     */
    public static final int VISUAL_ID = 3004;

    /**
     * @generated
     */
    public AssociationClassConnectorEditPart(View view) {
        super(view);
    }

    /**
     * @generated
     */
    protected void createDefaultEditPolicies() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new AssociationClassConnectorItemSemanticEditPolicy());
    }

    /**
     * Creates figure for this edit part.
     * 
     * Body of this method does not depend on settings in generation model
     * so you may safely remove <i>generated</i> tag and modify it.
     * 
     * @generated
     */

    protected Connection createConnectionFigure() {
        return new AssociationClass2DanglingNodeConnectorFigure();
    }

    /**
     * @generated
     */
    public AssociationClass2DanglingNodeConnectorFigure getPrimaryShape() {
        return (AssociationClass2DanglingNodeConnectorFigure) getFigure();
    }

    /**
     * @generated
     */
    public class AssociationClass2DanglingNodeConnectorFigure extends
            PolylineConnectionEx {

        /**
         * @generated
         */
        public AssociationClass2DanglingNodeConnectorFigure() {
            this.setLineStyle(Graphics.LINE_DASH);

        }

    }

}
