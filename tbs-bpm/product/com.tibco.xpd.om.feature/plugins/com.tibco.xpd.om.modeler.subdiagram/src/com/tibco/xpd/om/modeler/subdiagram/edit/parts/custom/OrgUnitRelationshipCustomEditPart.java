package com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.util.StringStatics;
import org.eclipse.gmf.runtime.diagram.core.commands.SetPropertyCommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITreeBranchEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.internal.properties.Properties;
import org.eclipse.gmf.runtime.diagram.ui.requests.ChangePropertyValueRequest;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.notation.LineStyle;
import org.eclipse.gmf.runtime.notation.LineType;
import org.eclipse.gmf.runtime.notation.LineTypeStyle;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Routing;
import org.eclipse.gmf.runtime.notation.RoutingStyle;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.modeler.subdiagram.edit.policies.OrgUnitSubRelationshipItemSemanticEditPolicy;
import com.tibco.xpd.om.modeler.subdiagram.part.custom.DiagramUtils;
import com.tibco.xpd.om.modeler.subdiagram.view.factories.custom.IOMSubViewConstants;

public abstract class OrgUnitRelationshipCustomEditPart extends
        ConnectionNodeEditPart implements ITreeBranchEditPart {

    // public int routingStyle;

    public OrgUnitRelationshipCustomEditPart(View view) {
        super(view);
    }

    @Override
    protected void createDefaultEditPolicies() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new OrgUnitSubRelationshipItemSemanticEditPolicy());

        removeEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE);

        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
                new OrgUnitRelGraphicalNodeEditPolicy());

    }

    @Override
    protected void handleNotificationEvent(Notification notification) {

        // Possible that an OrgUnitRelationship is being set from hierarchical
        // to non-hierarchical.
        if (notification.getEventType() == Notification.SET
                && notification.getNotifier() instanceof OrgUnitRelationship) {

            Object feature = notification.getFeature();

            if ((feature == OMPackage.Literals.ORG_UNIT_RELATIONSHIP__IS_HIERARCHICAL)
                    && (notification.getNotifier() == resolveSemanticElement())) {

                // Get a list of all the outgoing relationships for the source
                // OrgUnit so that we can decide whether there should be an
                // Expand/Collapse icon the the its figure.
                EditPart source = getSource();
                if (source instanceof OrgUnitCustomEditPart) {
                    OrgUnitCustomEditPart ep = (OrgUnitCustomEditPart) source;
                    OrgUnit srcOrgUnit = (OrgUnit) ep.resolveSemanticElement();

                    EList<OrgUnitRelationship> outRels =
                            srcOrgUnit.getOutgoingRelationships();

                    // Set line as Hierarchical
                    if (notification.getNewBooleanValue() == true) {
                        setHierarchicalStyles();

                        if (outRels.size() > 1) {
                            // If none of the relationships are hierarchical
                            // force the figure to redraw without a expand icon
                            boolean hasHierarchical = false;
                            for (OrgUnitRelationship rel : outRels) {
                                if (rel.isIsHierarchical()) {
                                    hasHierarchical = true;
                                    // And check if its visible
                                    break;
                                }
                            }

                            if (hasHierarchical) {
                                if (DiagramUtils
                                        .isOrgUnitHierarchyCollapsed((Node) ep
                                                .getModel())) {
                                    ep.expandHierarchyViews();
                                }
                            }
                        }

                        if (outRels.size() == 1) {
                            ep.setFigureCollapseIcon();
                            ep.refresh();
                        }
                    }

                    // Set line as an association
                    if (notification.getNewBooleanValue() == false) {
                        setAssociationStyles();

                        if (outRels.size() > 1) {
                            // If none of the relationships are hierarchical
                            // force the figure to redraw without a expand icon.
                            // Otherwise no need to redraw
                            boolean hasHierarchical = false;
                            for (OrgUnitRelationship rel : outRels) {
                                if (rel.isIsHierarchical()) {
                                    hasHierarchical = true;
                                    break;
                                }
                            }

                            if (!hasHierarchical) {
                                ep.getPrimaryShape()
                                        .rebuildFigureWithExpandCollapseIcon(false,
                                                0);
                            }

                        }

                        if (outRels.size() == 1) {
                            ep.getPrimaryShape()
                                    .rebuildFigureWithExpandCollapseIcon(false,
                                            0);
                            ep.refresh();
                        }
                    }
                }

                refreshVisuals();

            }

        }

        super.handleNotificationEvent(notification);
    }

    /**
     * Creates figure for this edit part.
     * 
     * Body of this method does not depend on settings in generation model so
     * you may safely remove <i>generated</i> tag and modify it.
     * 
     * @generated NOT
     */
    @Override
    protected Connection createConnectionFigure() {
        PolylineConnectionEx conn = new PolylineConnectionEx();
        EObject element = resolveSemanticElement();

        if (element instanceof OrgUnitRelationship) {
            OrgUnitRelationship rel = (OrgUnitRelationship) element;

            // Set a decoration arrow if this is an hierarchical relationship
            if (rel != null && rel.isIsHierarchical()) {
                conn.setTargetDecoration(createHierachicalDecoration());
            }

            Object obj = getModel();

            if (obj instanceof View) {
                View view = (View) obj;

                LineStyle style =
                        (LineStyle) view
                                .getStyle(NotationPackage.Literals.LINE_STYLE);

                if (style != null) {
                    conn.setLineWidth(style.getLineWidth());
                }

                LineTypeStyle lineTypeStyle =
                        (LineTypeStyle) view
                                .getStyle(NotationPackage.Literals.LINE_TYPE_STYLE);

                if (lineTypeStyle != null) {
                    LineType type = lineTypeStyle.getLineType();

                    if (type == LineType.SOLID_LITERAL) {
                        conn.setLineStyle(Graphics.LINE_SOLID);
                    } else if (type == LineType.DOT_LITERAL) {
                        conn.setLineStyle(Graphics.LINE_DOT);
                    } else if (type == LineType.DASH_LITERAL) {
                        conn.setLineStyle(Graphics.LINE_DASH);
                    }
                }

            }
        }

        return conn;
    }

    /**
     * 
     * Make the line decoration (arrow)
     * 
     * @return Polygon Decoration
     */
    private PolygonDecoration createHierachicalDecoration() {
        PolygonDecoration df = new PolygonDecoration();
        df.setFill(true);
        // df.setBackgroundColor(ColorConstants.black);
        PointList pl = new PointList();
        pl.addPoint(getMapMode().DPtoLP(0), getMapMode().DPtoLP(0));
        pl.addPoint(getMapMode().DPtoLP(-1), getMapMode().DPtoLP(1));
        pl.addPoint(getMapMode().DPtoLP(-1), getMapMode().DPtoLP(-1));
        pl.addPoint(getMapMode().DPtoLP(0), getMapMode().DPtoLP(0));
        df.setTemplate(pl);
        df.setScale(getMapMode().DPtoLP(7), getMapMode().DPtoLP(3));
        return df;
    }

    private void setHierarchicalStyles() {
        setLineTypeToHierarchical();
        setRoutingToHierarchical();
    }

    private void setAssociationStyles() {
        setLineTypeToAssociation();
        setRoutingToAssociation();
    }

    private void setRoutingToHierarchical() {
        // Update style
        View primaryView = getPrimaryView();

        if (primaryView != null) {
            RoutingStyle style =
                    (RoutingStyle) primaryView
                            .getStyle(NotationPackage.Literals.CONNECTOR_STYLE);

            if (style != null) {
                style.setRouting(Routing.TREE_LITERAL);
            }
        }
    }

    protected void setHierarchicalLineWidthStyle(View view) {
        // Default setting
        LineStyle lineStyle =
                (LineStyle) view.getStyle(NotationPackage.Literals.LINE_STYLE);

        lineStyle
                .setLineWidth(IOMSubViewConstants.OM_VIEW_CONSTANTS_DEFAULT_ORGUNITREL_WIDTH);

        // Then update figure
        PolylineConnectionEx conn = (PolylineConnectionEx) getFigure();
        conn.setLineWidth(2);

    }

    private void setRoutingToAssociation() {
        // Update style
        View primaryView = getPrimaryView();

        if (primaryView != null) {
            RoutingStyle style =
                    (RoutingStyle) primaryView
                            .getStyle(NotationPackage.Literals.CONNECTOR_STYLE);

            if (style != null) {
                style.setRouting(Routing.MANUAL_LITERAL);
            }
        }

    }

    private void setLineTypeToHierarchical() {
        // Update style
        View primaryView = getPrimaryView();
        LineTypeStyle style =
                (LineTypeStyle) primaryView
                        .getStyle(NotationPackage.Literals.LINE_TYPE_STYLE);

        if (style != null) {
            style.setLineType(LineType.SOLID_LITERAL);
        }

        // Then update figure
        PolylineConnectionEx conn = (PolylineConnectionEx) getFigure();
        conn.setLineStyle(Graphics.LINE_SOLID);

        setHierarchicalLineWidthStyle(primaryView);

        // Add the arrow
        conn.setTargetDecoration(createHierachicalDecoration());

    }

    private void setLineTypeToAssociation() {
        // Update style
        View primaryView = getPrimaryView();
        LineTypeStyle style =
                (LineTypeStyle) primaryView
                        .getStyle(NotationPackage.Literals.LINE_TYPE_STYLE);

        if (style != null) {
            style.setLineType(LineType.DOT_LITERAL);
        }

        // Then update figure
        PolylineConnectionEx conn = (PolylineConnectionEx) getFigure();
        conn.setLineStyle(Graphics.LINE_DOT);

        // Make sure there is no arrow
        conn.setTargetDecoration(null);

    }

    /**
     * 
     * Extends GraphicalNodeEDitPolicy so that we can override the
     * getRoutingAdjustment() method which resets a Tree style to the connector
     * style defined in the preference store. We don't want this to happen.
     * 
     * @author rgreen
     * 
     */
    class OrgUnitRelGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {

        @Override
        protected Command getRoutingAdjustment(IAdaptable connection,
                String connectionHint, Routing currentRouterType,
                EditPart target) {
            Command cmd = null;
            if (connectionHint == null || target == null
                    || target.getModel() == null
                    || ((View) target.getModel()).getElement() == null)
                return null;
            // check if router needs to change type due to reorient.
            String targetHint =
                    ViewUtil.getSemanticElementClassId((View) target.getModel());
            Routing newRouterType = null;
            if (target instanceof ITreeBranchEditPart
                    && connectionHint.equals(targetHint)) {
                newRouterType = Routing.TREE_LITERAL;
                ChangePropertyValueRequest cpvr =
                        new ChangePropertyValueRequest(StringStatics.BLANK,
                                Properties.ID_ROUTING, newRouterType);
                Command cmdRouter = target.getCommand(cpvr);
                if (cmdRouter != null)
                    cmd = cmd == null ? cmdRouter : cmd.chain(cmdRouter);
            } else {
                // if (currentRouterType.equals(Routing.TREE_LITERAL)) {
                // IPreferenceStore store = (IPreferenceStore)
                // ((IGraphicalEditPart) getHost())
                // .getDiagramPreferencesHint().getPreferenceStore();
                // newRouterType = Routing.get(store
                // .getInt(IPreferenceConstants.PREF_LINE_STYLE));
                // }
            }
            if (newRouterType != null) {
                // add commands for line routing. Convert the new connection and
                // also the targeted connection.
                ICommand spc =
                        new SetPropertyCommand(getEditingDomain(), connection,
                                Properties.ID_ROUTING, StringStatics.BLANK,
                                newRouterType);
                Command cmdRouter = new ICommandProxy(spc);
                if (cmdRouter != null) {
                    cmd = cmd == null ? cmdRouter : cmd.chain(cmdRouter);
                }
            }
            return cmd;
        }

    }

}
