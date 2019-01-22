package com.tibco.xpd.om.modeler.diagram.edit.parts;

import java.beans.PropertyChangeEvent;

import org.eclipse.draw2d.Connection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.notation.LineStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.modeler.diagram.edit.policies.OrgUnitRelationshipItemSemanticEditPolicy;
import com.tibco.xpd.om.modeler.diagram.edit.policies.custom.OrgUnitRelationshipCustomConnectionEditPolicy;
import com.tibco.xpd.om.modeler.diagram.view.factories.custom.IOMViewConstants;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrgUnitRelationshipCustomEditPart;

/**
 * 
 * @generated NOT
 * 
 * @author rgreen
 * 
 */

public class OrgUnitRelationshipEditPart extends
        OrgUnitRelationshipCustomEditPart {

    /**
     * @generated
     */
    public static final int VISUAL_ID = 3001;

    /**
     * @generated
     */
    public OrgUnitRelationshipEditPart(View view) {
        super(view);
    }

    /**
     * @generated NOT
     */
    protected void createDefaultEditPolicies() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new OrgUnitRelationshipItemSemanticEditPolicy());

        // Install a custom CONNECTION_ROLE editpolicy so that we can modify the
        // delete behaviour of this connection.
        removeEditPolicy(EditPolicy.CONNECTION_ROLE);
        installEditPolicy(EditPolicy.CONNECTION_ROLE,
                new OrgUnitRelationshipCustomConnectionEditPolicy());

    }

    @Override
    public Command getCommand(Request _request) {
        // TODO Auto-generated method stub
        return super.getCommand(_request);
    }

    /**
     * Creates figure for this edit part.
     * 
     * Body of this method does not depend on settings in generation model so
     * you may safely remove <i>generated</i> tag and modify it.
     * 
     * @generated NOT
     */
    protected Connection createConnectionFigure() {
        return super.createConnectionFigure();
    }

    /**
     * @generated NOT
     */
    public PolylineConnectionEx getPrimaryShape() {
        return (PolylineConnectionEx) super.getFigure();

    }

    @Override
    protected void setHierarchicalLineWidthStyle(View view) {
        // Update the style
        LineStyle lineStyle =
                (LineStyle) view.getStyle(NotationPackage.Literals.LINE_STYLE);

        lineStyle
                .setLineWidth(IOMViewConstants.OM_VIEW_CONSTANTS_DEFAULT_ORGUNITREL_WIDTH);

        // Then update figure
        PolylineConnectionEx conn = (PolylineConnectionEx) getFigure();
        conn.setLineWidth(1);
    }

    @Override
    protected void handlePropertyChangeEvent(PropertyChangeEvent event) {
        // TODO Auto-generated method stub
        super.handlePropertyChangeEvent(event);
    }

    // @Override
    // protected void handleNotificationEvent(Notification notification) {
    //
    // super.handleNotificationEvent(notification);
    //
    // Object newValue = notification.getNewValue();
    // Object notifier = notification.getNotifier();
    // Edge edge = null;
    //
    // if (notifier instanceof Edge) {
    // edge = (Edge) notifier;
    //
    // // invokeSemanticRefresh(notification);
    //
    // }
    //
    // if (edge != null && newValue != null && newValue instanceof Node) {
    // Node node = (Node) newValue;
    //
    // // Invoke a canoncical refresh so that we can kick off an
    // // autoarrange via the canonical editpolicy's refreshSemantic().
    // //
    // // Only attempt a canonical refresh if this event originates from
    // // the addition of an OrgUnit as the target of the edge. It seems
    // // that the target assignment always follows source assignment. If
    // // we attempted a canonical refresh for the source event then the
    // // refresh would fail as the edge would only have one of its ends
    // // defined i.e. has source but noy yet a target end node.
    // if (edge.getTarget() == newValue) {
    //
    // Object object = getViewer().getEditPartRegistry().get(node);
    //
    // if (object instanceof OrgUnitEditPart) {
    // OrgUnitEditPart ouEP = (OrgUnitEditPart) object;
    //
    // EditPart parentEP = ouEP.getParent();
    //
    // if (parentEP instanceof OrganizationOrgUnitCompartmentEditPart) {
    // OrganizationOrgUnitCompartmentEditPart cptEP =
    // (OrganizationOrgUnitCompartmentEditPart) parentEP;
    //
    // OrganizationOrgUnitCompartmentCanonicalEditPolicy editPolicy =
    // (OrganizationOrgUnitCompartmentCanonicalEditPolicy) cptEP
    // .getEditPolicy(EditPolicyRoles.CANONICAL_ROLE);
    //
    // // editPolicy.refreshSemanticWithAutoArrange();
    //
    // }
    //
    // }
    // }
    //
    // }
    //
    // }

}
