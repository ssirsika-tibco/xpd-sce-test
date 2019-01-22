package com.tibco.xpd.om.modeler.subdiagram.edit.parts.customfigures;

import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;

import com.tibco.xpd.om.core.om.OrgUnitRelationship;

public class OrgUnitRelationshipCustomFigure extends PolylineConnectionEx {

    OrgUnitRelationship oUR;
    ConnectionEditPart parent;

    public OrgUnitRelationshipCustomFigure(OrgUnitRelationship orgUnitRel) {
        oUR = orgUnitRel;
        // this.setLineWidth(2);

        if (!oUR.isIsHierarchical()) {
            // this.setLineStyle(Graphics.LINE_DOT);
        }
    }

    public void setLineType(boolean isHierarchical) {
        if (isHierarchical) {
            // this.setLineStyle(Graphics.LINE_SOLID);
            // this.setLineWidth(1);
        } else {
            // this.setLineStyle(Graphics.LINE_DOT);
            // this.setLineWidth(2);
        }

    }

    public void setTargetDecoration(PolygonDecoration decor) {
        super.setTargetDecoration(decor);
    }

}
