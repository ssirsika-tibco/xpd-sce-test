package com.tibco.xpd.om.modeler.diagram.edit.parts;

import org.eclipse.draw2d.Connection;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITreeBranchEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.graphics.Color;

import com.tibco.xpd.om.modeler.diagram.edit.policies.DynamicOrgReferenceItemSemanticEditPolicy;

/**
 * @generated
 */
public class DynamicOrgReferenceEditPart extends ConnectionNodeEditPart
        implements ITreeBranchEditPart {

    /**
     * @generated
     */
    public static final int VISUAL_ID = 3002;

    /**
     * @generated
     */
    public DynamicOrgReferenceEditPart(View view) {
        super(view);
    }

    /**
     * @generated
     */
    @Override
    protected void createDefaultEditPolicies() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new DynamicOrgReferenceItemSemanticEditPolicy());

    }

    /**
     * Creates figure for this edit part.
     * 
     * Body of this method does not depend on settings in generation model so
     * you may safely remove <i>generated</i> tag and modify it.
     * 
     * @generated
     */

    @Override
    protected Connection createConnectionFigure() {
        return new DynamicOrgRelationshipLineFigure();
    }

    /**
     * @generated
     */
    public DynamicOrgRelationshipLineFigure getPrimaryShape() {
        return (DynamicOrgRelationshipLineFigure) getFigure();
    }

    /**
     * @generated NOT
     */
    public class DynamicOrgRelationshipLineFigure extends PolylineConnectionEx {

        /**
         * @generated
         */
        public DynamicOrgRelationshipLineFigure() {
            this.setForegroundColor(THIS_FORE);
        }

    }

    /**
     * @generated
     */
    static final Color THIS_FORE = new Color(null, 200, 200, 200);

}
