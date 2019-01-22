/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.parts;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITreeBranchEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.bom.modeler.diagram.edit.policies.GeneralizationItemSemanticEditPolicy;

/**
 * @generated
 */
public class GeneralizationEditPart extends ConnectionNodeEditPart implements
        ITreeBranchEditPart {

    /**
     * @generated
     */
    public static final int VISUAL_ID = 3001;

    /**
     * @generated
     */
    public GeneralizationEditPart(View view) {
        super(view);
    }

    /**
     * @generated
     */
    protected void createDefaultEditPolicies() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new GeneralizationItemSemanticEditPolicy());
    }

    /**
     * @generated
     */
    protected boolean addFixedChild(EditPart childEditPart) {
        return false;
    }

    /**
     * @generated
     */
    protected void addChildVisual(EditPart childEditPart, int index) {
        if (addFixedChild(childEditPart)) {
            return;
        }
        super.addChildVisual(childEditPart, -1);
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
        return new GeneralizationLineFigure();
    }

    /**
     * @generated
     */
    public GeneralizationLineFigure getPrimaryShape() {
        return (GeneralizationLineFigure) getFigure();
    }

    /**
     * @generated
     */
    public class GeneralizationLineFigure extends PolylineConnectionEx {

        /**
         * @generated
         */
        public GeneralizationLineFigure() {

            setTargetDecoration(createTargetDecoration());
        }

        /**
         * @generated
         */
        private RotatableDecoration createTargetDecoration() {
            PolygonDecoration df = new PolygonDecoration();
            df.setFill(true);
            df.setBackgroundColor(ColorConstants.white);
            PointList pl = new PointList();
            pl.addPoint(getMapMode().DPtoLP(0), getMapMode().DPtoLP(0));
            pl.addPoint(getMapMode().DPtoLP(-2), getMapMode().DPtoLP(2));
            pl.addPoint(getMapMode().DPtoLP(-2), getMapMode().DPtoLP(-2));
            pl.addPoint(getMapMode().DPtoLP(0), getMapMode().DPtoLP(0));
            df.setTemplate(pl);
            df.setScale(getMapMode().DPtoLP(7), getMapMode().DPtoLP(3));
            return df;
        }

    }

}
