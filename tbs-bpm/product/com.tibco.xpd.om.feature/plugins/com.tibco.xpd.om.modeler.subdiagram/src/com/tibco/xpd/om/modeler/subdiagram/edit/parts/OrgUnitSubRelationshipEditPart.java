package com.tibco.xpd.om.modeler.subdiagram.edit.parts;

import java.beans.PropertyChangeEvent;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrgUnitRelationshipCustomEditPart;
import com.tibco.xpd.om.modeler.subdiagram.edit.policies.OrgUnitSubRelationshipItemSemanticEditPolicy;

public class OrgUnitSubRelationshipEditPart extends
		OrgUnitRelationshipCustomEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 3001;

	/**
	 * @generated
	 */
	public OrgUnitSubRelationshipEditPart(View view) {
		super(view);
	}

	/**
	 * @generated NOT
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
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

	@Override
	protected Connection createConnectionFigure() {
		return super.createConnectionFigure();
	}

	/**
	 * @generated
	 */
	public OrgUnitRelationshipLineFigure getPrimaryShape() {
		return (OrgUnitRelationshipLineFigure) getFigure();
	}

	/**
	 * @generated
	 */
	public class OrgUnitRelationshipLineFigure extends PolylineConnectionEx {

		/**
		 * @generated
		 */
		public OrgUnitRelationshipLineFigure() {

			setTargetDecoration(createTargetDecoration());
		}

		/**
		 * @generated
		 */
		private RotatableDecoration createTargetDecoration() {
			PolygonDecoration df = new PolygonDecoration();
			df.setFill(true);
			df.setBackgroundColor(ColorConstants.black);
			PointList pl = new PointList();
			pl.addPoint(getMapMode().DPtoLP(0), getMapMode().DPtoLP(0));
			pl.addPoint(getMapMode().DPtoLP(-1), getMapMode().DPtoLP(1));
			pl.addPoint(getMapMode().DPtoLP(-1), getMapMode().DPtoLP(-1));
			pl.addPoint(getMapMode().DPtoLP(0), getMapMode().DPtoLP(0));
			df.setTemplate(pl);
			df.setScale(getMapMode().DPtoLP(7), getMapMode().DPtoLP(3));
			return df;
		}

	}

	@Override
	protected void handlePropertyChangeEvent(PropertyChangeEvent event) {
		super.handlePropertyChangeEvent(event);
	}

}
