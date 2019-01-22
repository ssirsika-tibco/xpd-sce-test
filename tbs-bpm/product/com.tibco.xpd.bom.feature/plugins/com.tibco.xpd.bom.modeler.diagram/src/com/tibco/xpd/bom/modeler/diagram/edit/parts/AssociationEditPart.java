/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.parts;

import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListener;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITreeBranchEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.diagram.edit.policies.AssociationItemSemanticEditPolicy;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * @generated
 */
public class AssociationEditPart extends ConnectionNodeEditPart implements
        ITreeBranchEditPart {

    /**
     * @generated NOT
     */
    private PrimTypeResourceListener rsl;

    /**
     * @generated NOT
     */
    private Association assoc;

    private EObject sourceType;

    private EObject targetType;

    private AssociationLineFigure figure;

    public EObject getSourceType() {
        return sourceType;
    }

    @Override
    public boolean isSemanticConnection() {

        /*
         * If in user diagram then return false. One of the effects from this
         * will be disabling of the "Delete From Diagram" option of Associations
         * in a user diagram
         */
        if (BomUIUtil.isUserDiagram(getDiagramView())) {
            return false;
        }

        return super.isSemanticConnection();
    }

    public EObject getTargetType() {
        return targetType;
    }

    /**
     * @generated
     */
    public static final int VISUAL_ID = 3002;

    public Association getAssoc() {
        return assoc;
    }

    /**
     * @generated
     */
    public AssociationEditPart(View view) {
        super(view);
    }

    /**
     * @generated
     */
    protected void createDefaultEditPolicies() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new AssociationItemSemanticEditPolicy());
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
     * Body of this method does not depend on settings in generation model so
     * you may safely remove <i>generated</i> tag and modify it.
     * 
     * @generated NOT
     */

    protected Connection createConnectionFigure() {
        assoc = (Association) resolveSemanticElement();

        sourceType = getSourceType();
        targetType = getTargetType();

        figure = new AssociationLineFigure();
        return figure;
    }

    /**
     * @generated
     */
    public AssociationLineFigure getPrimaryShape() {
        return (AssociationLineFigure) getFigure();
    }

    /**
     * @generated NOT
     */
    @Override
    protected void handleNotificationEvent(Notification notification) {
        if (notification.getFeatureID(UMLPackage.class) == UMLPackage.ASSOCIATION__OWNED_END) {
            int eventType = notification.getEventType();
            switch (eventType) {
            case Notification.ADD:
                handleAddOwnedEnd((Property) notification.getNewValue());
                break;
            case Notification.REMOVE:
                handleRemoveOwnedEnd((Property) notification.getOldValue());
                break;
            }
        }
        super.handleNotificationEvent(notification);
    }

    /**
     * @generated NOT
     */
    private void handleAddOwnedEnd(Property property) {
        if (assoc.isBinary()) {
            if (property.equals(assoc.getMemberEnds().get(0))) {
                figure.setTargetArrowDecoration();
            } else if (property.equals(assoc.getMemberEnds().get(1))) {
                figure.setSourceArrowDecoration();
            }
        }
    }

    /**
     * @generated NOT
     */
    private void handleRemoveOwnedEnd(Property property) {
        if (assoc.isBinary()) {
            if (property.equals(assoc.getMemberEnds().get(0))) {
                figure.setTargetDecoration(null);
            } else if (property.equals(assoc.getMemberEnds().get(1))) {
                figure.setSourceDecoration(null);
            }
        }
    }

    /**
     * @generated
     */
    public class AssociationLineFigure extends PolylineConnectionEx {

        /**
         * @generated NOT
         */
        public AssociationLineFigure() {

        }

        /**
         * @generated
         */
        private RotatableDecoration createTargetDecoration() {
            PolylineDecoration df = new PolylineDecoration();
            PointList pl = new PointList();
            pl.addPoint(getMapMode().DPtoLP(-1), getMapMode().DPtoLP(1));
            pl.addPoint(getMapMode().DPtoLP(0), getMapMode().DPtoLP(0));
            pl.addPoint(getMapMode().DPtoLP(-1), getMapMode().DPtoLP(-1));
            df.setTemplate(pl);
            df.setScale(getMapMode().DPtoLP(7), getMapMode().DPtoLP(3));
            return df;
        }

        /**
         * @generated NOT
         */
        private RotatableDecoration createAggregationDiamondDecoration() {
            PolygonDecoration df = new PolygonDecoration();
            df.setBackgroundColor(ColorConstants.white);
            PointList pl = new PointList();
            pl.addPoint(getMapMode().DPtoLP(0), getMapMode().DPtoLP(0));
            pl.addPoint(getMapMode().DPtoLP(-1), getMapMode().DPtoLP(-1));
            pl.addPoint(getMapMode().DPtoLP(-2), getMapMode().DPtoLP(0));
            pl.addPoint(getMapMode().DPtoLP(-1), getMapMode().DPtoLP(1));
            pl.addPoint(getMapMode().DPtoLP(0), getMapMode().DPtoLP(0));
            df.setTemplate(pl);
            df.setScale(getMapMode().DPtoLP(7), getMapMode().DPtoLP(3));
            return df;
        }

        /**
         * @generated NOT
         */
        public void setSourceAggregationDecoration() {
            setSourceDecoration(createAggregationDiamondDecoration());
        }

        /**
         * @generated NOT
         */
        public void setTargetAggregationDecoration() {
            setTargetDecoration(createAggregationDiamondDecoration());
        }

        /**
         * @generated NOT
         */
        private RotatableDecoration createCompositionDecoration() {
            PolygonDecoration df = new PolygonDecoration();
            df.setBackgroundColor(ColorConstants.black);
            PointList pl = new PointList();
            pl.addPoint(getMapMode().DPtoLP(0), getMapMode().DPtoLP(0));
            pl.addPoint(getMapMode().DPtoLP(-1), getMapMode().DPtoLP(-1));
            pl.addPoint(getMapMode().DPtoLP(-2), getMapMode().DPtoLP(0));
            pl.addPoint(getMapMode().DPtoLP(-1), getMapMode().DPtoLP(1));
            pl.addPoint(getMapMode().DPtoLP(0), getMapMode().DPtoLP(0));
            df.setTemplate(pl);
            df.setScale(getMapMode().DPtoLP(7), getMapMode().DPtoLP(3));
            return df;
        }

        /**
         * @generated NOT
         */
        public void setSourceCompositionDecoration() {
            setSourceDecoration(createCompositionDecoration());
        }

        /**
         * @generated NOT
         */
        public void setTargetCompositionDecoration() {
            setTargetDecoration(createCompositionDecoration());
        }

        /**
         * @generated NOT
         */
        public void setSourceArrowDecoration() {
            setSourceDecoration(createTargetDecoration());
        }

        /**
         * @generated NOT
         */
        public void removeSourceArrowDecoration() {
            // setTargetDecoration(null);
            setSourceDecoration(null);
        }

        /**
         * @generated NOT
         */
        public void setTargetArrowDecoration() {
            setTargetDecoration(createTargetDecoration());
        }

        /**
         * @generated NOT
         */
        public void removeTargetArrowDecoration() {
            setTargetDecoration(null);
        }

    }

    @Override
    public void deactivate() {
        // TODO Auto-generated method stub

        if (rsl != null) {
            XpdResourcesPlugin.getDefault().getEditingDomain()
                    .removeResourceSetListener(rsl);
            rsl = null;
        }
        super.deactivate();
    }

    @Override
    public void activate() {
        super.activate();

        // Create a Listener to listen to all EMF events. We can sift through
        // them to find the addition of the stereotype

        rsl = new PrimTypeResourceListener();
        XpdResourcesPlugin.getDefault().getEditingDomain()
                .addResourceSetListener(rsl);
    }

    class PrimTypeResourceListener implements ResourceSetListener {

        public PrimTypeResourceListener() {
        }

        public NotificationFilter getFilter() {
            return null;
        }

        public boolean isAggregatePrecommitListener() {
            return false;
        }

        public boolean isPostcommitOnly() {
            return true;
        }

        public boolean isPrecommitOnly() {
            return false;
        }

        public void resourceSetChanged(ResourceSetChangeEvent event) {
            // This is where we listen for resource changes

            // Get this element and resource
            Association thisAssoc = (Association) resolveSemanticElement();
            if (thisAssoc == null) {
                // if cannot resolve, this part has been removed, need to remove
                // the listener.
                XpdResourcesPlugin.getDefault().getEditingDomain()
                        .removeResourceSetListener(this);
                return;
            }
            Resource res = thisAssoc.eResource();

            // Loop through all the notification this event contains and
            // locate the one we are interested in.
            List notifications = event.getNotifications();
            for (Object object : notifications) {

                Notification n = (Notification) object;

                Property prop1 = null;
                Property prop2 = null;

                // Is the notifier one of the end-members of this Association?
                if (!thisAssoc.getMemberEnds().isEmpty()) {
                    prop1 = (Property) thisAssoc.getMemberEnds().get(0);
                    if (thisAssoc.getMemberEnds().size() > 1)
                        prop2 = (Property) thisAssoc.getMemberEnds().get(1);
                }

                if ((n.getNotifier() == prop1) || (n.getNotifier() == prop2)) {
                    // This notification is relevant to this editpart's resource
                    if (n.getEventType() == Notification.SET) {

                        // Also check for the case where the type of association
                        // has changed, for example from an Aggregation to a
                        // composition, in which case we need to redraw the line
                        // start
                        if ((n.getFeatureID(UMLPackage.class) == UMLPackage.CONNECTOR_END)
                                || (n.getNewValue() != n.getOldValue())) {

                            if (n.getNewValue() instanceof AggregationKind) {
                                AggregationKind agg =
                                        (AggregationKind) n.getNewValue();
                                refresh();
                            }
                        }
                    }

                }

            }

        }

        public org.eclipse.emf.common.command.Command transactionAboutToCommit(
                ResourceSetChangeEvent event) throws RollbackException {
            return null;
        }
    }

    @Override
    public void refresh() {

        super.refresh();

        boolean isSourceArrowSet = false;
        boolean isTargetArrowSet = false;
        Association assoc = (Association) resolveSemanticElement();

        // Refresh any navigability arrows
        if (!UML2ModelUtil.isAssociationBiDirectional(assoc)) {
            List<Property> lstOwnedEnds = assoc.getOwnedEnds();

            if (lstOwnedEnds.size() == 1) {
                Property prop = lstOwnedEnds.get(0);
                Classifier cl = (Classifier) prop.getType();

                GraphicalEditPart clEpSrc = (GraphicalEditPart) getSource();
                if (clEpSrc != null) {
                    Classifier srcClass =
                            (Classifier) clEpSrc.resolveSemanticElement();
                    if (cl == srcClass) {
                        this.figure.setTargetArrowDecoration();
                        isTargetArrowSet = true;
                    }
                }

                GraphicalEditPart clEpTrg = (GraphicalEditPart) getTarget();
                if (clEpTrg != null) {
                    Classifier trgClass =
                            (Classifier) clEpTrg.resolveSemanticElement();
                    if (cl == trgClass) {
                        this.figure.setSourceArrowDecoration();
                        isSourceArrowSet = true;
                    }
                }
            }

        }

        Classifier sourceType = null, targetType = null;
        Property sourceEnd = null, targetEnd = null;

        List<Property> memberEnds = assoc.getMemberEnds();
        sourceEnd = memberEnds.get(0);
        sourceType = (Classifier) sourceEnd.getType();
        targetEnd = memberEnds.get(1);
        targetType = (Classifier) targetEnd.getType();

        // Check if this Association is a Composite or Aggregation.
        // If so then set the relevant end decorator
        Property prop = null;
        AggregationKind agg = null;

        // Is the notifier one of the end-members of this Association?
        if (!assoc.getMemberEnds().isEmpty()) {

            for (int x = 0; x < 2; x++) {
                prop = assoc.getMemberEnds().get(x);

                agg = prop.getAggregation();

                Type cl = prop.getType();

                if (agg == AggregationKind.COMPOSITE_LITERAL) {
                    if (x == 0) {
                        this.figure.setTargetCompositionDecoration();
                    } else if (x == 1) {
                        this.figure.setSourceCompositionDecoration();
                    }
                } else if (agg == AggregationKind.SHARED_LITERAL) {
                    if (x == 0) {
                        this.figure.setTargetAggregationDecoration();
                    } else if (x == 1) {
                        // this.figure.setSourceAggregationDecoration();
                        this.figure.setSourceAggregationDecoration();
                    }
                } else {
                    if (x == 0) {
                        if (!isTargetArrowSet) {
                            this.figure.removeTargetArrowDecoration();
                        }
                    } else if (x == 1) {
                        if (!isSourceArrowSet) {
                            this.figure.removeSourceArrowDecoration();
                        }
                    }
                }
            }

        }

    }

}
