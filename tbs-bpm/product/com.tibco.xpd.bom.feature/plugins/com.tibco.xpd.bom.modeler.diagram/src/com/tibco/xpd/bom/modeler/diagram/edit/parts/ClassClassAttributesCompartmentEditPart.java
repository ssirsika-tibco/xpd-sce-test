/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.parts;

import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ListCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ResizableCompartmentEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.modeler.diagram.edit.policies.ClassClassAttributesCompartmentCanonicalEditPolicy;
import com.tibco.xpd.bom.modeler.diagram.edit.policies.ClassClassAttributesCompartmentItemSemanticEditPolicy;
import com.tibco.xpd.bom.modeler.diagram.edit.policies.custom.BOMCompartmentEditPolicy;
import com.tibco.xpd.bom.modeler.diagram.part.Messages;

/**
 * @generated
 */
public class ClassClassAttributesCompartmentEditPart extends
        ListCompartmentEditPart {

    /**
     * @generated
     */
    public static final int VISUAL_ID = 5002;

    /**
     * @generated
     */
    public ClassClassAttributesCompartmentEditPart(View view) {
        super(view);
    }

    /**
     * @generated
     */
    @Override
    protected boolean hasModelChildrenChanged(Notification evt) {
        return false;
    }

    /**
     * @generated
     */
    @Override
    public String getCompartmentName() {
        return Messages.ClassClassAttributesCompartmentEditPart_title;
    }

    /**
     * @generated NOT
     * 
     *            Set Opacity
     */
    @Override
    public IFigure createFigure() {

        // Set Opacity
        // result.setOpaque(true);

        ResizableCompartmentFigure rcf =
                (ResizableCompartmentFigure) super.createFigure();
        FlowLayout layout = new FlowLayout();
        layout.setMajorSpacing(getMapMode().DPtoLP(5));
        layout.setMinorSpacing(getMapMode().DPtoLP(5));
        layout.setHorizontal(false);

        rcf.getContentPane().setLayoutManager(layout);

        rcf.setTitleVisibility(false);
        return rcf;
    }

    /**
     * @generated NOT
     */
    @Override
    protected void createDefaultEditPolicies() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE,
                new ResizableCompartmentEditPolicy());
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new ClassClassAttributesCompartmentItemSemanticEditPolicy());
        installEditPolicy(EditPolicyRoles.CREATION_ROLE,
                new CreationEditPolicy() {

                    @Override
                    protected ICommand getReparentViewCommand(
                            IGraphicalEditPart gep) {
                        /*
                         * XPD-5093: This is taken care by the layout edit
                         * policy (BOMCompartmentEditPolicy)
                         */
                        return null;
                    }

                    @Override
                    protected ICommand getReparentCommand(IGraphicalEditPart gep) {
                        View view = (View) gep.getModel();
                        EObject element = ViewUtil.resolveSemanticElement(view);

                        if (element instanceof Class) {
                            return null;
                        }
                        if (element instanceof Package) {
                            return null;
                        }
                        if (element instanceof PrimitiveType) {
                            return null;
                        }
                        if (element instanceof Operation) {
                            return null;
                        }
                        if (element instanceof Enumeration) {
                            return null;
                        }
                        return super.getReparentCommand(gep);
                    }

                });
        installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE,
                new DragDropEditPolicy());
        installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
                new ClassClassAttributesCompartmentCanonicalEditPolicy());

        installEditPolicy(EditPolicy.LAYOUT_ROLE, new BOMCompartmentEditPolicy(
                UMLPackage.eINSTANCE.getStructuredClassifier_OwnedAttribute()) {

            @Override
            protected boolean canAdd(EditPart host, EditPart child) {

                boolean isCaseIdentifier = false;

                // Check for the case where what is being moved is a Case
                // Identifier
                if (child instanceof PropertyEditPart) {
                    Object propModel = ((PropertyEditPart) child).getModel();
                    if ((propModel != null) && (propModel instanceof View)) {
                        EObject element = ((View) propModel).getElement();
                        if ((element != null) && (element instanceof Property)) {
                            Property prop = (Property) element;
                            // Check to see if this is a case identifier
                            if (GlobalDataProfileManager.getInstance()
                                    .isCID(prop)
                                    || GlobalDataProfileManager.getInstance()
                                            .isAutoCaseIdentifier(prop)
                                    || GlobalDataProfileManager.getInstance()
                                            .isCompositeCaseIdentifier(prop)) {
                                isCaseIdentifier = true;
                            }
                        }
                    }
                }

                // If it is a case identifier being moved check to ensure that
                // it can only be moved to a case class, as they are invalid for
                // other classes
                if ((isCaseIdentifier != false)
                        && (host instanceof ClassClassAttributesCompartmentEditPart)) {
                    Object attrCompModel =
                            ((ClassClassAttributesCompartmentEditPart) host)
                                    .getModel();
                    if ((attrCompModel != null)
                            && (attrCompModel instanceof View)) {
                        EObject element = ((View) attrCompModel).getElement();
                        if ((element != null) && (element instanceof Class)) {
                            // Check to see if this class is global or case
                            // class
                            if (!GlobalDataProfileManager.getInstance()
                                    .isCase((Class) element)) {
                                // stop the case identifier being moved to
                                // anything other than a case class
                                return false;
                            }
                        }
                    }
                }

                return host instanceof ClassClassAttributesCompartmentEditPart
                        && child instanceof PropertyEditPart;
            }

        });
    }

    /**
     * @see org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart#getCommand(org.eclipse.gef.Request)
     * 
     * @param _request
     * @return
     */
    @Override
    public Command getCommand(Request _request) {
        return super.getCommand(_request);
    }

    /**
     * @generated
     */
    @Override
    protected void setRatio(Double ratio) {
        if (getFigure().getParent().getLayoutManager() instanceof ConstrainedToolbarLayout) {
            super.setRatio(ratio);
        }
    }

    @Override
    protected void handleNotificationEvent(Notification event) {

        /*
         * If an (association) edge is added then refresh the canonical edit
         * policy to update the (association) properties shown (if in custom
         * diagram).
         */
        View view = getNotationView();
        if (view != null && view.eContainer() instanceof View) {
            if (event.getNotifier() == view.eContainer()
                    && (event.getFeature() == NotationPackage.eINSTANCE
                            .getView_SourceEdges() || event.getFeature() == NotationPackage.eINSTANCE
                            .getView_TargetEdges())) {

                EditPolicy policy =
                        getEditPolicy(EditPolicyRoles.CANONICAL_ROLE);
                if (policy instanceof CanonicalEditPolicy) {
                    ((CanonicalEditPolicy) policy).refresh();
                }
            }
        }

        super.handleNotificationEvent(event);
    }

}
