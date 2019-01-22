/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.policies.custom;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.DropRequest;
import org.eclipse.gmf.runtime.diagram.core.commands.AddCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.FlowLayoutEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ResizableEditPolicyEx;
import org.eclipse.gmf.runtime.emf.commands.core.commands.RepositionEObjectCommand;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.Classifier;

import com.tibco.xpd.bom.modeler.diagram.edit.commands.custom.BOMCompartmentRepositionEObjectCommand;
import com.tibco.xpd.bom.modeler.diagram.part.Messages;

;

/**
 * 
 * Extends the FlowLayoutEditPolicy such that views in a list can be re-odered
 * by Drag and Drop.
 * 
 * @author rgreen
 * 
 */
public abstract class BOMCompartmentEditPolicy extends FlowLayoutEditPolicy {

    private EStructuralFeature feature = null;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gef.editpolicies.OrderedLayoutEditPolicy#createAddCommand
     * (org.eclipse.gef.EditPart, org.eclipse.gef.EditPart)
     */
    @Override
    protected Command createAddCommand(EditPart child, EditPart after) {
        EditPart hostEP = getHost();

        /*
         * XPD-5093: Ask the edit part that is implementing this edit policy
         * whether this add is allowed.
         */
        if (canAdd(hostEP, child)) {
            int index = getHost().getChildren().indexOf(after);
            TransactionalEditingDomain editingDomain =
                    ((IGraphicalEditPart) getHost()).getEditingDomain();
            AddCommand command =
                    new AddCommand(editingDomain, new EObjectAdapter(
                            (View) getHost().getModel()), new EObjectAdapter(
                            (View) child.getModel()), index);
            return new ICommandProxy(command);

        }

        return null;
    }

    /**
     * Check if the given child can be added to the host.
     * 
     * @param host
     * @param child
     * @return <code>true</code> to allow the child to be added to host.
     */
    protected abstract boolean canAdd(EditPart host, EditPart child);

    @Override
    protected EditPolicy createChildEditPolicy(EditPart child) {
        ResizableEditPolicyEx policy = new ResizableEditPolicyEx();
        policy.setResizeDirections(0);
        return policy;
    }

    @Override
    protected Command createMoveChildCommand(EditPart child, EditPart after) {
        /*
         * XPD-5196: Only create this move command if we have an "after" edit
         * part. Otherwise this command will always be added and executed even
         * when a draggable item cannot be dropped into the target (e.g.
         * attribute being dragged into a Package). The end result of this is
         * that, although the attribute is not moved into the Package, the
         * no-drop icon will not be shown and the resource will be marked as
         * dirty.
         */
        if (after != null) {
            int newIndex;
            int displacement;

            int childIndex = getHost().getChildren().indexOf(child);
            int afterIndex = getHost().getChildren().indexOf(after);

            if (afterIndex == -1) {
                newIndex = getHost().getChildren().size() - 1;
                displacement = newIndex - childIndex;
            } else {
                newIndex = afterIndex;
                displacement = afterIndex - childIndex;
                if (childIndex <= afterIndex) {
                    newIndex--;
                    displacement--;
                }
            }

            TransactionalEditingDomain editingDomain =
                    ((IGraphicalEditPart) getHost()).getEditingDomain();

            RepositionEObjectCommand command =
                    new BOMCompartmentRepositionEObjectCommand(
                            child,
                            editingDomain,
                            Messages.BOMCompartmentEditPolicy_RepositionCommand_label,
                            (EList) ((Classifier) ((View) child.getModel())
                                    .getElement().eContainer()).eGet(feature),
                            ((View) child.getModel()).getElement(),
                            displacement, newIndex);

            return new ICommandProxy(command);
        }
        return null;
    }

    @Override
    protected Command getCreateCommand(CreateRequest request) {
        return null;
    }

    @Override
    protected Command getDeleteDependantCommand(Request request) {
        return null;
    }

    @Override
    protected Command getOrphanChildrenCommand(Request request) {
        return null;
    }

    /**
     * @param feature
     *            has to be an EList
     */
    public BOMCompartmentEditPolicy(EStructuralFeature feature) {
        super();
        this.feature = feature;
    }

    /**
     * @see org.eclipse.gef.editpolicies.FlowLayoutEditPolicy#getFeedbackIndexFor(org.eclipse.gef.Request)
     * 
     * @param request
     * @return
     */
    @Override
    protected int getFeedbackIndexFor(Request request) {
        /*
         * XPD-5196: Don't allow the move of the child if the drop target is not
         * inside the host. Without this fix you can drag an attribute, for
         * example, and hover around the left or right of the attribute
         * compartment (hovering over the model/package) and it will show that
         * you can drop it. Once you drop it it changes the attribute's position
         * within the attribute compartment - this is very confusing!
         */
        Point p = ((DropRequest) request).getLocation();

        Rectangle rect = getAbsoluteBounds((GraphicalEditPart) (getHost()));

        if (rect.contains(p)) {
            return super.getFeedbackIndexFor(request);
        }

        return -1;
    }

    private Rectangle getAbsoluteBounds(GraphicalEditPart ep) {
        Rectangle bounds = ep.getFigure().getBounds().getCopy();
        ep.getFigure().translateToAbsolute(bounds);
        return bounds;
    }

}
