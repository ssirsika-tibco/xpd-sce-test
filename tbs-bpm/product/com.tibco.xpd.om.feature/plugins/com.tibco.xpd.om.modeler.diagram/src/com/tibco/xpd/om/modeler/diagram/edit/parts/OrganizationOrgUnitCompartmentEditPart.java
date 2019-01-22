package com.tibco.xpd.om.modeler.diagram.edit.parts;

import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ResizableCompartmentEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.diagram.ui.figures.ShapeCompartmentFigure;
import org.eclipse.gmf.runtime.diagram.ui.requests.ArrangeRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.ChangePropertyValueRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.emf.core.util.PackageUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.DrawerStyle;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.SWT;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.modeler.diagram.edit.policies.OrgModelCanonicalEditPolicy;
import com.tibco.xpd.om.modeler.diagram.edit.policies.OrganizationOrgUnitCompartmentCanonicalEditPolicy;
import com.tibco.xpd.om.modeler.diagram.edit.policies.OrganizationOrgUnitCompartmentItemSemanticEditPolicy;
import com.tibco.xpd.om.modeler.diagram.part.Messages;

/**
 * @generated
 */
public class OrganizationOrgUnitCompartmentEditPart extends
        ShapeCompartmentEditPart {

    private int MAX_COORD = Integer.MAX_VALUE / 2;

    /**
     * @generated
     */
    public static final int VISUAL_ID = 5001;

    /**
     * @generated
     */
    public OrganizationOrgUnitCompartmentEditPart(View view) {
        super(view);
    }

    /**
     * @generated
     */
    public String getCompartmentName() {
        return Messages.OrganizationOrgUnitCompartmentEditPart_title;
    }

    @Override
    public Command getCommand(Request _request) {

        // If this is a request to expand/collapse the compartment we need to
        // intercept. If the expansion means the figure will overlap
        // neighbouring Organizations we need to move translate
        // their figures left/right/up/down. If its a collapse we again need
        // to translate all the figures.
        // To do this we will append a compound command to the intercepted
        // command which contains SetBoundsCommands for each figure
        // that needs moving.
        if (_request.getType().equals(RequestConstants.REQ_PROPERTY_CHANGE)) {
            ChangePropertyValueRequest cpvr = (ChangePropertyValueRequest) _request;

            String id = cpvr.getPropertyID();

            if (id instanceof String) {
                EStructuralFeature feature = (EStructuralFeature) PackageUtil
                        .getElement((String) id);
                if (feature != null) {
                    if (feature == NotationPackage.Literals.DRAWER_STYLE__COLLAPSED) {

                        // New compound command to wrap the original requests
                        // command and the new compound command containing
                        // our move commands
                        CompoundCommand cc = new CompoundCommand();
                        Command collapseCmd = super.getCommand(_request);
                        cc.add(collapseCmd);

                        // TODO IF NO SPECIFIC WIDTH SET ON OBJECT
                        // Get this compartments parent Organization
                        GraphicalEditPart orgEP = (GraphicalEditPart) getParent();

                        int orgEPwidth = ((Integer) orgEP
                                .getStructuralFeatureValue(NotationPackage.eINSTANCE
                                        .getSize_Width())).intValue();
                        int orgEPheight = ((Integer) orgEP
                                .getStructuralFeatureValue(NotationPackage.eINSTANCE
                                        .getSize_Height())).intValue();

                        if (orgEPwidth == -1 && orgEPheight == -1) {
                            Rectangle curOrgEPBnds = orgEP.getFigure()
                                    .getBounds().getCopy();

                            ResizableCompartmentFigure compartmentFig = (ResizableCompartmentFigure) this
                                    .getFigure();

                            if (Boolean.FALSE.equals(cpvr.getValue())) {
                                // FOR EXPAND!!!!
                                Dimension expandedSize = compartmentFig
                                        .getScrollPane().getLayoutManager()
                                        .getPreferredSize(
                                                compartmentFig.getScrollPane(),
                                                SWT.DEFAULT, SWT.DEFAULT);

                                Rectangle curScrollPaneBounds = compartmentFig
                                        .getScrollPane().getBounds().getCopy();
                                Rectangle curTextPaneBounds = compartmentFig
                                        .getTextPane().getBounds().getCopy();

                                // Now calc what the NEW expanded size of the
                                // parent
                                // will be.
                                Rectangle newOrgEPBnds = curOrgEPBnds.getCopy();

                                // The new width has to include the margins
                                // around
                                // the ScrollPane calculated by
                                // currentWidth-cuuScrollPane with.
                                // If default margins are 5 pixels then this
                                // should
                                // be 10!
                                newOrgEPBnds.width = (newOrgEPBnds.width - curScrollPaneBounds.width)
                                        + expandedSize.width;

                                // TODO: Tidy this result. We are
                                // overcompensating
                                // somehow

                                // Need to calculate the "excess" space
                                // underneath
                                // the compartment. The minimum height of an org
                                // is
                                // 100px remember and label+margins+collapsed
                                // compartment may not fill these 100px.
                                // Need to sum the height of each child figure
                                // and
                                // margins

                                int excessCptSpace = calcExcessFigureSpace(orgEP);

                                newOrgEPBnds.height = (newOrgEPBnds.height
                                        - curTextPaneBounds.height - curScrollPaneBounds.height)
                                        + expandedSize.height - excessCptSpace;

                                // ExpandContractUtil.moveOverlappedObjects(
                                // getEditingDomain(), cc, orgEP,
                                // curOrgEPBnds, newOrgEPBnds);

                                moveOverlappedObjects(cc, orgEP, curOrgEPBnds,
                                        newOrgEPBnds);

                            }

                            if (Boolean.TRUE.equals(cpvr.getValue())) {
                                // FOR COLLAPSE!!!!

                                // Now calc what the NEW expanded size of the
                                // parent
                                // will be.
                                Rectangle newOrgEPBnds = curOrgEPBnds.getCopy();

                                Dimension minimumSize = orgEP.getFigure()
                                        .getMinimumSize();

                                newOrgEPBnds.width = minimumSize.width;
                                newOrgEPBnds.height = minimumSize.height;

                                contractOverlappedObjects(cc, orgEP,
                                        curOrgEPBnds, newOrgEPBnds);

                            }

                            return cc;
                        }

                    }
                }
            }

        }

        return super.getCommand(_request);
    }

    /**
     * @param orgEP
     * @return
     */
    private int calcExcessFigureSpace(GraphicalEditPart orgEP) {

        int excess = 0;

        // Figure hierarchy will be:
        // Level 1: DefaultNode
        // Level 2: Base Figure (Organization)
        // Level 3: Children of base figure (WrappingLabels and
        // ResizableShapeCompartment for an Organization);

        // Get Level 2 Base Figure that sits on defaultNode
        // There should be only one child
        List defaultNodeFiguresChildren = orgEP.getFigure().getChildren();

        if (defaultNodeFiguresChildren.isEmpty()) {
            return 0;
        }

        Object defaultNodeChild = defaultNodeFiguresChildren.get(0);

        if (defaultNodeChild instanceof IFigure) {
            IFigure baseFigure = (IFigure) defaultNodeChild;

            // Get the children of this base figure
            List orgFiguresChildren = baseFigure.getChildren();

            int runningTotalHeight = 0;
            LayoutManager layoutManager = baseFigure.getLayoutManager();
            int verticalSpacing = 0;

            if (layoutManager != null && layoutManager instanceof GridLayout) {
                GridLayout gridLayout = (GridLayout) layoutManager;

                // Find the margin height of the layout for top and bottom i.e.
                // multiply by 2
                runningTotalHeight += gridLayout.marginHeight * 2;

                verticalSpacing = gridLayout.verticalSpacing;

            }

            // Then collect the heights of all the child figures and the spacing
            // in between each
            for (Object object : orgFiguresChildren) {
                if (object instanceof IFigure) {
                    IFigure childFig = (IFigure) object;
                    runningTotalHeight += childFig.getBounds().height;
                }
            }

            // Finally add the vertical spacing between children.
            if (orgFiguresChildren.size() > 1) {
                runningTotalHeight += (orgFiguresChildren.size() - 1)
                        * verticalSpacing;
            }

            // Now we can find the unused space in the baseFigure:
            excess = orgEP.getFigure().getBounds().height - runningTotalHeight;

        }

        return excess;
    }

    /**
     * @param cc
     * @param collapsingEP
     * @param originalBnds
     * @param newBnds
     */
    private void contractOverlappedObjects(CompoundCommand cc,
            GraphicalEditPart collapsingEP, Rectangle originalBnds,
            Rectangle newBnds) {

        EditPart parentEP = collapsingEP.getParent();

        Rectangle rightOfObject = new Rectangle(originalBnds.right(),
                originalBnds.y, MAX_COORD, originalBnds.height);

        Rectangle belowObject = new Rectangle(originalBnds.x, originalBnds
                .bottom(), originalBnds.width, MAX_COORD);

        Rectangle rightAndBelowObject = new Rectangle(originalBnds.right(),
                originalBnds.bottom(), MAX_COORD, MAX_COORD);

        // Calc the delta's for moving left / up.
        int deltaX = originalBnds.width - newBnds.width;
        int deltaY = originalBnds.height - newBnds.height;

        double ratioX = (double) newBnds.width / (double) originalBnds.width;
        double ratioY = (double) newBnds.height / (double) originalBnds.height;

        List childrenEPs = parentEP.getChildren();

        for (Object object : childrenEPs) {
            if (object instanceof GraphicalEditPart && object != collapsingEP) {

                GraphicalEditPart sibling = (GraphicalEditPart) object;
                IFigure siblingFigue = sibling.getFigure();

                Rectangle siblingBounds = siblingFigue.getBounds().getCopy();

                Point translatedLocation = siblingBounds.getTopLeft();
                Point origLocation = translatedLocation.getCopy();

                if (siblingBounds.intersects(rightOfObject)) {
                    // For objects intersecting the area to the right of
                    // current object rect. Shift left by the size
                    // difference between opened and closed.
                    translatedLocation.x -= deltaX;

                    // We may also be getting a LOT smaller in the vertical
                    // plane so we don't want to leave objects 'hanging
                    // down' way below the sub-proc (especially as this may
                    // cause them to run into objects that are being moved
                    // up and left because they're in the area right and
                    // below of closing sub-proc). So move the objects up by
                    // the ratio of the vertical size decrease.
                    double offset = translatedLocation.y - newBnds.y;
                    if (offset > 0) {
                        offset *= ratioY;
                    }
                    translatedLocation.y = newBnds.y + (int) offset;

                } else if (siblingBounds.intersects(belowObject)) {
                    // For objects intersecting the area below the current
                    // object rect. Shift up by the size difference between
                    // opened and closed.
                    translatedLocation.y -= deltaY;

                    // We may also be getting a LOT smaller in the
                    // horizontal plane so we don't want to leave objects
                    // 'hanging right' way further out than the object. So
                    // move the objects left by the ratio of the horizontal
                    // size decrease.
                    double offset = translatedLocation.x - newBnds.x;
                    if (offset > 0) {
                        offset *= ratioX;
                    }
                    translatedLocation.x = newBnds.x + (int) offset;

                } else if (rightAndBelowObject.contains(siblingBounds)) {
                    // For objects that are below and right of sub-proc,
                    // move left and up.
                    translatedLocation.x -= deltaX;
                    translatedLocation.y -= deltaY;
                }

                if (!origLocation.equals(translatedLocation)) {

                    // siblingBounds.x = modelLocation.x;
                    // siblingBounds.y = modelLocation.y;

                    ChangeBoundsRequest req = new ChangeBoundsRequest(REQ_MOVE);
                    req.setLocation(siblingBounds.getTopLeft());
                    req.setEditParts(sibling);
                    req.setMoveDelta(new Point(translatedLocation.x
                            - origLocation.x, translatedLocation.y
                            - origLocation.y));
                    req.setSizeDelta(new Dimension(0, 0));

                    Command command = sibling.getCommand(req);

                    if (command != null && command.canExecute()) {
                        cc.add(command);
                    }
                }

            }
        }

    }

    private void moveOverlappedObjects(CompoundCommand cc,
            GraphicalEditPart expandingEP, Rectangle originalBounds,
            Rectangle newBounds) {

        EditPart parentEP = expandingEP.getParent();

        Rectangle rightOfObject = new Rectangle(originalBounds.right(),
                originalBounds.y, MAX_COORD, originalBounds.height);

        Rectangle belowObject = new Rectangle(originalBounds.x, originalBounds
                .bottom(), originalBounds.width, MAX_COORD);

        Rectangle rightAndBelowObject = new Rectangle(originalBounds.right(),
                originalBounds.bottom(), MAX_COORD, MAX_COORD);

        // Calc the delta's for moving left / up.
        int deltaX = newBounds.width - originalBounds.width;
        int deltaY = newBounds.height - originalBounds.height;

        double ratioX = (double) newBounds.width
                / (double) originalBounds.width;
        double ratioY = (double) newBounds.height
                / (double) originalBounds.height;

        List childrenEPs = parentEP.getChildren();

        for (Object object : childrenEPs) {
            if (object instanceof GraphicalEditPart && object != expandingEP) {

                GraphicalEditPart sibling = (GraphicalEditPart) object;
                IFigure siblingFigue = sibling.getFigure();

                Rectangle siblingBounds = siblingFigue.getBounds().getCopy();

                Point translatedLocation = siblingBounds.getTopLeft();
                Point origLocation = translatedLocation.getCopy();

                // if (siblingBounds.intersects(newBounds)) {
                if (siblingBounds.intersects(rightOfObject)) {
                    // For objects intersecting the area to the right of
                    // current
                    // object rect. Shift right by the size difference
                    // between
                    // opened and closed.
                    translatedLocation.x += deltaX;

                    // CloseEmbeddedSubProcCommand will also move right hand
                    // side objects UP by the ratio which the vertical
                    // size is changing (So as not to leave objects 'hanging
                    // down').
                    //
                    // So we MUST do the converse in order to put things
                    // back
                    // where they were before we closed.
                    double offset = translatedLocation.y - newBounds.y;
                    if (offset > 0) {
                        offset *= ratioY;
                    }
                    translatedLocation.y = newBounds.y + (int) offset;

                } else if (siblingBounds.intersects(belowObject)) {
                    // For objects intersecting the area below the current
                    // object rect. Shift down by the size difference
                    // between
                    // opened and closed.
                    translatedLocation.y += deltaY;

                    // CloseEmbeddedSubProcCommand will also move below
                    // side objects LEFT by the ratio which the horizontal
                    // size is changing (So as not to leave objects 'hanging
                    // right').
                    //
                    // So we MUST do the converse in order to put things
                    // back
                    // where they were before we closed.
                    double offset = translatedLocation.x - newBounds.x;
                    if (offset > 0) {
                        offset *= ratioX;
                    }
                    translatedLocation.x = newBounds.x + (int) offset;

                } else if (rightAndBelowObject.contains(siblingBounds)) {
                    // For objects that are below and right of sub-proc,
                    // move
                    // right and down.
                    translatedLocation.x += deltaX;
                    translatedLocation.y += deltaY;
                }

                if (!origLocation.equals(translatedLocation)) {

                    // siblingBounds.x = modelLocation.x;
                    // siblingBounds.y = modelLocation.y;

                    ChangeBoundsRequest req = new ChangeBoundsRequest(REQ_MOVE);
                    req.setLocation(siblingBounds.getTopLeft());
                    req.setEditParts(sibling);
                    req.setMoveDelta(new Point(translatedLocation.x
                            - origLocation.x, translatedLocation.y
                            - origLocation.y));
                    req.setSizeDelta(new Dimension(0, 0));

                    Command command = sibling.getCommand(req);

                    if (command != null && command.canExecute()) {
                        cc.add(command);
                    }
                }
                // }

            }
        }

    }

    /**
     * @generated NOT
     */
    public IFigure createFigure() {
        ResizableCompartmentFigure result = (ResizableCompartmentFigure) super
                .createFigure();
        result
                .setTitle(Messages.OrganizationOrgUnitCompartmentEditPart_orgUnits_label);

        // if (result.isExpanded()) {
        // result.setTitleVisibility(false);
        // } else {
        // result.setTitleVisibility(true);
        // }

        // result.setBackgroundColor(DiagramColorConstants.diagramBlue);
        // result.setOpaque(true);
        return result;
    }

    /**
     * @generated
     */
    protected void createDefaultEditPolicies() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE,
                new ResizableCompartmentEditPolicy());
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new OrganizationOrgUnitCompartmentItemSemanticEditPolicy());
        installEditPolicy(EditPolicyRoles.CREATION_ROLE,
                new CreationEditPolicy());
        installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE,
                new DragDropEditPolicy());
        installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
                new OrganizationOrgUnitCompartmentCanonicalEditPolicy());
    }

    /**
     * @generated
     */
    protected void setRatio(Double ratio) {
        if (getFigure().getParent().getLayoutManager() instanceof ConstrainedToolbarLayout) {
            super.setRatio(ratio);
        }
    }

    @Override
    protected void handleNotificationEvent(Notification event) {

        super.handleNotificationEvent(event);

        int width = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE
                .getSize_Width())).intValue();
        int height = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE
                .getSize_Height())).intValue();

        Object feature = event.getFeature();

        if ((feature == NotationPackage.Literals.DRAWER_STYLE__COLLAPSED)
                && (event.getEventType() == Notification.SET)) {

            if (event.getNotifier() instanceof DrawerStyle) {
                DrawerStyle style = (DrawerStyle) event.getNotifier();
                EObject container = style.eContainer();

                if (container == getModel()) {

                    // 1. First check to see if we need to set a title for the
                    // a collapsed compartment
                    IFigure cptFigure = getFigure();

                    if (cptFigure instanceof ShapeCompartmentFigure) {
                        ShapeCompartmentFigure fig = (ShapeCompartmentFigure) cptFigure;

                        Rectangle bounds = fig.getBounds();

                        if (event.getNewBooleanValue() == true) {
                            // Is being collapsed, so show title ONLY if there
                            // are children inside compartment
                            // if (!getChildren().isEmpty()) {
                            fig.setTitleVisibility(true);
                            // }

                        } else if (event.getNewBooleanValue() == false) {
                            fig.setTitleVisibility(false);
                        }

                    }

                    // 2. Next check to see if a newly expanded compartment's
                    // bounds overlap a neighbouring organization
                    Map editPartRegistry = getViewer().getEditPartRegistry();
                    if (container instanceof Node) {
                        Node containerNode = (Node) container;

                        if (checkForOverlappingOrgs(containerNode) == true) {
                            // Re-layout the diagram
                            // doArrangeAll();
                        }

                    }
                }
            }

        }

        if (feature == OMPackage.Literals.ORGANIZATION__ORG_UNIT_RELATIONSHIPS
                && (event.getNotifier() == resolveSemanticElement())
                && (event.getEventType() == Notification.ADD || event
                        .getEventType() == Notification.REMOVE)) {

            // Intercepted an event relevant to an OrgUnitRelationship which has
            // probably been added/removed/reset from the subdiagram. Therefore
            // we need to force a refresh of the diagrams connections. To do
            // this we need to delegate to the
            // diagram editpart's canonical editpolicy.
            Object notifier = event.getNotifier();

            if (notifier instanceof Organization) {

            }

            if (event.getEventType() == Notification.ADD
                    && event.getNewValue() instanceof OrgUnitRelationship) {
                OrgUnitRelationship rel = (OrgUnitRelationship) event
                        .getNewValue();

                // If we are adding a new Association BETWEEN Organizations we
                // don't want to refresh connections as the diagram will do
                // this. Otherwise we'll get a duplicate connection being drawn.
                // So check that the Relationships source and target are owned
                // by the same Organization if we want to do a refresh.
                EObject srcOrg = rel.getFrom().eContainer();
                EObject trgOrg = rel.getTo().eContainer();

                if (srcOrg != trgOrg) {
                    return;
                }
            }

            if (notifier instanceof Organization) {
                EditPart child = this;
                EditPart parent = getParent();
                while (!(parent instanceof OrgModelEditPart)
                        && (parent != null)) {
                    child = parent;
                    parent = child.getParent();
                }

                if (parent instanceof OrgModelEditPart) {
                    OrgModelEditPart ep = (OrgModelEditPart) parent;
                    ep.refreshDiagramConnections();
                }

            }

        }

    }

    private void doArrangeAll() {

        ArrangeRequest arrangeRequest = new ArrangeRequest(
                ActionIds.ACTION_ARRANGE_ALL);

        Diagram diagramView = getDiagramView();
        List diagChildEditParts = null;
        Object object = getViewer().getEditPartRegistry().get(diagramView);

        if (object instanceof OrgModelEditPart) {
            OrgModelEditPart omEP = (OrgModelEditPart) object;

            OrgModelCanonicalEditPolicy editPolicy = (OrgModelCanonicalEditPolicy) omEP
                    .getEditPolicy(EditPolicyRoles.CANONICAL_ROLE);

            editPolicy.performAutoArrange();

            // diagChildEditParts = omEP.getChildren();

        }

        // if (diagChildEditParts != null) {
        //
        // arrangeRequest.setPartsToArrange(diagChildEditParts);
        //
        // Command arrangeCmd = getCommand(arrangeRequest);
        //
        // arrangeCmd.execute();
        // }

    }

    private boolean checkForOverlappingOrgs(Node expandedNode) {

        boolean needArrange = false;
        EList diagChildViews = getDiagramView().getChildren();
        Map editPartRegistry = getViewer().getEditPartRegistry();

        // Get the editpart for the expanded view
        OrganizationEditPart expandedOrgEP = null;
        Object objectExpanded = editPartRegistry.get(expandedNode);

        if (objectExpanded instanceof OrganizationOrgUnitCompartmentEditPart) {
            OrganizationOrgUnitCompartmentEditPart cptEP = (OrganizationOrgUnitCompartmentEditPart) objectExpanded;

            // Get the parent editpart which will be the orgunit editpart
            EditPart parentEP = cptEP.getParent();

            if (parentEP instanceof OrganizationEditPart) {
                expandedOrgEP = (OrganizationEditPart) parentEP;

            }

        }

        if (expandedOrgEP != null) {

            // Now go through all the diagrams organizations to see if the
            // expanded one overlaps with any.
            for (Object object : diagChildViews) {
                if (object instanceof Node) {
                    Node node = (Node) object;

                    Object object2 = editPartRegistry.get(node);

                    if (object2 instanceof OrganizationEditPart) {
                        OrganizationEditPart orgEP = (OrganizationEditPart) object2;

                        if (orgEP == expandedOrgEP) {
                            continue;
                        }

                        // Now do the actual intersection test
                        Rectangle boundsOfExpandedOrg = expandedOrgEP
                                .getFigure().getBounds();
                        Rectangle boundsOfChildOrg = orgEP.getFigure()
                                .getBounds();

                        if (boundsOfExpandedOrg.intersects(boundsOfChildOrg)) {
                            // We found an overlap!
                            needArrange = true;
                            break;
                        }
                    }
                }
            }
        }

        return needArrange;

    }

    @Override
    protected void refreshVisuals() {
        // TODO Auto-generated method stub
        super.refreshVisuals();

        IFigure cptFigure = getFigure();

        if (cptFigure instanceof ShapeCompartmentFigure) {
            ShapeCompartmentFigure fig = (ShapeCompartmentFigure) cptFigure;

            if (fig.isExpanded()) {
                fig.setTitleVisibility(false);
            } else {
                fig.setTitleVisibility(true);
            }
        }

    }

}
