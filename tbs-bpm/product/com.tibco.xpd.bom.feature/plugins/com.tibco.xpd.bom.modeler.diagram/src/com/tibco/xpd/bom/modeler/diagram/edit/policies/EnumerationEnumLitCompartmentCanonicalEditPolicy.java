/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.policies;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationUtil;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.EnumerationLiteralEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramUpdater;
import com.tibco.xpd.bom.modeler.diagram.part.UMLNodeDescriptor;
import com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry;

/**
 * @generated
 */
public class EnumerationEnumLitCompartmentCanonicalEditPolicy extends
        CanonicalEditPolicy {

    /**
     * @generated
     */
    Set myFeaturesToSynchronize;

    /**
     * @generated
     */
    protected List getSemanticChildrenList() {
        View viewObject = (View) getHost().getModel();
        List result = new LinkedList();
        for (Iterator it = UMLDiagramUpdater
                .getEnumerationEnumerationLiteralsCompartment_5004SemanticChildren(
                        viewObject).iterator(); it.hasNext();) {
            result.add(((UMLNodeDescriptor) it.next()).getModelElement());
        }
        return result;
    }

    /**
     * @generated
     */
    protected boolean isOrphaned(Collection semanticChildren, final View view) {
        int visualID = UMLVisualIDRegistry.getVisualID(view);
        switch (visualID) {
        case EnumerationLiteralEditPart.VISUAL_ID:
            if (!semanticChildren.contains(view.getElement())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @generated
     */
    protected String getDefaultFactoryHint() {
        return null;
    }

    /**
     * @generated
     */
    protected Set getFeaturesToSynchronize() {
        if (myFeaturesToSynchronize == null) {
            myFeaturesToSynchronize = new HashSet();
            myFeaturesToSynchronize.add(UMLPackage.eINSTANCE
                    .getEnumeration_OwnedLiteral());
        }
        return myFeaturesToSynchronize;
    }

    @Override
    protected boolean shouldHandleNotificationEvent(Notification event) {
        boolean doHandle = super.shouldHandleNotificationEvent(event);
        
        if (!doHandle) {
            // Need to refresh if order of literals has changed
            Object element = event.getNotifier();
            if (element instanceof EObject && !(element instanceof View)) {
                Set<?> features = getFeaturesToSynchronize();
                if (features != null && !features.isEmpty()) {
                    if (features.contains(event.getFeature())
                            && (NotificationUtil.isMove(event))) {
                        doHandle = true;
                    }
                }
            }
        }
        
        return doHandle;
    }
    
    @Override
    protected void refreshSemantic() {
        super.refreshSemantic();
        refreshOrder();
    }
    
    /**
     * Synch order of the notation elements with the semantic elements.
     */
    private void refreshOrder() {
        List<?> children = getSemanticChildrenList();
        View parent = ((View) host().getModel());
        EList<?> viewChildren = parent.getChildren();

        out: for (int i = 0; i < children.size(); i++) {
            Object model = children.get(i);

            for (int j = i; j < viewChildren.size(); j++) {
                Object obj = viewChildren.get(j);
                if (obj instanceof View) {
                    View view = (View) obj;
                    if (view.getElement() == model) {
                        if (i != j) {
                            ViewUtil.repositionChildAt(parent, view, i);
                            continue out;
                        }
                    }
                }
            }
        }
    }
}
