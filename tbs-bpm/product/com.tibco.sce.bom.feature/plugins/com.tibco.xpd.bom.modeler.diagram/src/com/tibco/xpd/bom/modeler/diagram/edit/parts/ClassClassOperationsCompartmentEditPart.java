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
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ListCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ResizableCompartmentEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.diagram.edit.policies.ClassClassOperationsCompartmentCanonicalEditPolicy;
import com.tibco.xpd.bom.modeler.diagram.edit.policies.ClassClassOperationsCompartmentItemSemanticEditPolicy;
import com.tibco.xpd.bom.modeler.diagram.edit.policies.custom.BOMCompartmentEditPolicy;
import com.tibco.xpd.bom.modeler.diagram.part.Messages;

/**
 * @generated
 */
public class ClassClassOperationsCompartmentEditPart extends
        ListCompartmentEditPart {

    /**
     * @generated
     */
    public static final int VISUAL_ID = 5003;

    /**
     * @generated
     */
    public ClassClassOperationsCompartmentEditPart(View view) {
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
        return Messages.ClassClassOperationsCompartmentEditPart_title;
    }

    /**
     * @generated NOT
     */
    @Override
    public IFigure createFigure() {
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
                new ClassClassOperationsCompartmentItemSemanticEditPolicy());
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
                        if (element instanceof Property) {
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
                new ClassClassOperationsCompartmentCanonicalEditPolicy());

        installEditPolicy(EditPolicy.LAYOUT_ROLE, new BOMCompartmentEditPolicy(
                UMLPackage.eINSTANCE.getClass_OwnedOperation()) {

            @Override
            protected boolean canAdd(EditPart host, EditPart child) {
                return host instanceof ClassClassOperationsCompartmentEditPart
                        && child instanceof OperationEditPart;
            }

        });
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
        // TODO Auto-generated method stub

        // TODO:
        // If we get a set visibility notification we want to ignore it if
        // we have the BE profile loaded.

        super.handleNotificationEvent(event);

        // this.setVisibility(false);
        // refresh();
    }

    @Override
    public void refresh() {
        // TODO Auto-generated method stub

        super.refresh();
        // this.setVisibility(false);
        // this.refreshVisibility();
    }

}
