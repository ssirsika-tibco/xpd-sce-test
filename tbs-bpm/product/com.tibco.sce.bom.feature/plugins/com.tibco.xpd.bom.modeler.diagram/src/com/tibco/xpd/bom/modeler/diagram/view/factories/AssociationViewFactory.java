/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.view.factories;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.ConnectionViewFactory;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationCardinalityAtSourceLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationCardinalityAtTargetLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationSourceLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationTargetLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry;

/**
 * @generated
 */
public class AssociationViewFactory extends ConnectionViewFactory {

    /**
     * @generated
     */
    protected List createStyles(View view) {
        List styles = new ArrayList();
        styles.add(NotationFactory.eINSTANCE.createConnectorStyle());
        styles.add(NotationFactory.eINSTANCE.createFontStyle());
        return styles;
    }

    /**
     * @generated
     */
    protected void decorateView(View containerView, View view,
            IAdaptable semanticAdapter, String semanticHint, int index,
            boolean persisted) {
        if (semanticHint == null) {
            semanticHint = UMLVisualIDRegistry
                    .getType(AssociationEditPart.VISUAL_ID);
            view.setType(semanticHint);
        }
        super.decorateView(containerView, view, semanticAdapter, semanticHint,
                index, persisted);
        IAdaptable eObjectAdapter = null;
        EObject eObject = (EObject) semanticAdapter.getAdapter(EObject.class);
        if (eObject != null) {
            eObjectAdapter = new EObjectAdapter(eObject);
        }
        getViewService().createNode(eObjectAdapter, view,
                UMLVisualIDRegistry.getType(AssociationNameEditPart.VISUAL_ID),
                ViewUtil.APPEND, true, getPreferencesHint());
        getViewService()
                .createNode(
                        eObjectAdapter,
                        view,
                        UMLVisualIDRegistry
                                .getType(AssociationCardinalityAtSourceLabelEditPart.VISUAL_ID),
                        ViewUtil.APPEND, true, getPreferencesHint());
        getViewService().createNode(
                eObjectAdapter,
                view,
                UMLVisualIDRegistry
                        .getType(AssociationSourceLabelEditPart.VISUAL_ID),
                ViewUtil.APPEND, true, getPreferencesHint());
        getViewService()
                .createNode(
                        eObjectAdapter,
                        view,
                        UMLVisualIDRegistry
                                .getType(AssociationCardinalityAtTargetLabelEditPart.VISUAL_ID),
                        ViewUtil.APPEND, true, getPreferencesHint());
        getViewService().createNode(
                eObjectAdapter,
                view,
                UMLVisualIDRegistry
                        .getType(AssociationTargetLabelEditPart.VISUAL_ID),
                ViewUtil.APPEND, true, getPreferencesHint());
    }
}
