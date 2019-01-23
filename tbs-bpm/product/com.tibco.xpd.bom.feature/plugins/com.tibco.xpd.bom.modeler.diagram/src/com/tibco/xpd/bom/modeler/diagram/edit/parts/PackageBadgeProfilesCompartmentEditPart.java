/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ListCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ResizableCompartmentEditPolicy;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;

import com.tibco.xpd.bom.modeler.diagram.edit.policies.PackageBadgeProfilesCompartmentCanonicalEditPolicy;
import com.tibco.xpd.bom.modeler.diagram.edit.policies.PackageBadgeProfilesCompartmentItemSemanticEditPolicy;
import com.tibco.xpd.bom.modeler.diagram.part.Messages;

/**
 * @generated
 */
public class PackageBadgeProfilesCompartmentEditPart extends
        ListCompartmentEditPart {

    /**
     * @generated
     */
    public static final int VISUAL_ID = 5007;

    /**
     * @generated
     */
    public PackageBadgeProfilesCompartmentEditPart(View view) {
        super(view);
    }

    /**
     * @generated
     */
    protected boolean hasModelChildrenChanged(Notification evt) {
        return false;
    }

    /**
     * @generated
     */
    public String getCompartmentName() {
        return Messages.PackageBadgeProfilesCompartmentEditPart_title;
    }

    /**
     * @generated
     */
    protected void createDefaultEditPolicies() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE,
                new ResizableCompartmentEditPolicy());
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new PackageBadgeProfilesCompartmentItemSemanticEditPolicy());
        installEditPolicy(EditPolicyRoles.CREATION_ROLE,
                new CreationEditPolicy());
        installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE,
                new DragDropEditPolicy());
        installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
                new PackageBadgeProfilesCompartmentCanonicalEditPolicy());
    }

    @SuppressWarnings("unchecked")//$NON-NLS-1$
    @Override
    protected List getFilteredChildren() {
        /*
         * Filter all profile applications that are of first-class profiles.
         */
        List<?> filteredChildren = super.getFilteredChildren();

        List<?> sortedChildren = getSortedChildren();

        if (sortedChildren != null && !sortedChildren.isEmpty()) {
            List<Object> ret = new ArrayList<Object>(filteredChildren);

            for (Object child : sortedChildren) {
                if (child instanceof Node) {
                    EObject eo = ((Node) child).getElement();
                    if (eo instanceof ProfileApplication) {
                        Profile profile =
                                ((ProfileApplication) eo).getAppliedProfile();
                        if (profile != null && profile.eResource() != null) {
                            URI uri = profile.eResource().getURI();
                            if (uri != null && !uri.isPlatformResource()) {
                                if (!ret.contains(child)) {
                                    ret.add(child);
                                }
                            }
                        }
                    }
                }
            }

            return ret;
        }

        return filteredChildren;
    }

    /**
     * @generated
     */
    protected void setRatio(Double ratio) {
        if (getFigure().getParent().getLayoutManager() instanceof ConstrainedToolbarLayout) {
            super.setRatio(ratio);
        }
    }

}
