/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.core.commands.SetPropertyCommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.PropertyHandlerEditPolicy;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.om.modeler.subdiagram.requests.custom.IOMCustomRequestConstants;
import com.tibco.xpd.om.modeler.subdiagram.requests.custom.ToggleViewVisibilityCustomRequest;

/**
 * 
 * Superclass EditPart for OrgModel (Top level) and Organization (sub) diagrams.
 * 
 * @author rgreen
 * 
 */
public class OMDiagramEditPart extends DiagramEditPart {

    public OMDiagramEditPart(View diagramView) {
        super(diagramView);
    }

    @Override
    protected List getModelChildren() {
        // The superclass method return only the "Visible" children. This is a
        // problem for our diagram because the Badge may be hidden when the
        // editor is opened and hence not included in the list of active
        // children editparts. This causes a problem for creating the popup
        // menu's show/hide action.
        Object model = getModel();
        if (model != null && model instanceof View) {
            return new ArrayList(((View) model).getChildren());
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    protected void createDefaultEditPolicies() {
        super.createDefaultEditPolicies();

        removeEditPolicy(EditPolicyRoles.PROPERTY_HANDLER_ROLE);

        installEditPolicy(EditPolicyRoles.PROPERTY_HANDLER_ROLE,
                new PropertyHandlerEditPolicy() {

                    // Overide here because we want to check for a customized
                    // ChangeProperty request originating from the PopUp menu
                    // option to ShowHideBadge. The request originates from a
                    // DiagramAction for the currently selected (right-clicked)
                    // editpart which could be the diagram - which is NOT the
                    // editpart for which we want to change the visiblity
                    // property of the view. (This is of course the badge).
                    @Override
                    public Command getCommand(Request request) {

                        if (!understandsRequest(request)) {
                            return null;
                        }

                        if (request
                                .getType()
                                .equals(
                                        IOMCustomRequestConstants.REQ_TOGGLE_VIEW_VISIBILITY)
                                && request instanceof ToggleViewVisibilityCustomRequest) {

                            ToggleViewVisibilityCustomRequest togRequest = (ToggleViewVisibilityCustomRequest) request;

                            View view2Toggle = togRequest.getView();

                            if (view2Toggle != null) {
                                EditPart ep = getHost();
                                if (ep instanceof IGraphicalEditPart) {
                                    View hostView = ((IGraphicalEditPart) ep)
                                            .getNotationView();

                                    // Check that the view is a child of this
                                    // editpart
                                    EList childViews = hostView.getChildren();

                                    for (Object object : childViews) {
                                        if (object == view2Toggle) {

                                            // Get current property setting so
                                            // that
                                            // we can flip it
                                            boolean visible = view2Toggle
                                                    .isVisible();
                                            boolean newVisibleState = true;

                                            if (visible) {
                                                newVisibleState = false;
                                            }

                                            if (ViewUtil.isPropertySupported(
                                                    view2Toggle, togRequest
                                                            .getPropertyID())) {
                                                return new ICommandProxy(
                                                        new SetPropertyCommand(
                                                                getEditingDomain(),
                                                                new EObjectAdapter(
                                                                        view2Toggle),
                                                                togRequest
                                                                        .getPropertyID(),
                                                                togRequest
                                                                        .getPropertyName(),
                                                                newVisibleState));
                                            }

                                        }
                                    }

                                }

                            }

                        }

                        return super.getCommand(request);
                    }

                    @Override
                    public boolean understandsRequest(Request request) {
                        if (request
                                .getType()
                                .equals(
                                        IOMCustomRequestConstants.REQ_TOGGLE_VIEW_VISIBILITY)) {
                            return true;
                        }
                        return super.understandsRequest(request);
                    }
                });

    }
}
