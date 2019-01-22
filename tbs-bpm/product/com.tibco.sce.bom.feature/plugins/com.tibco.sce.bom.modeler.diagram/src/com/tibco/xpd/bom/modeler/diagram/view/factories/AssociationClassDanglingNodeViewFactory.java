/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.view.factories;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.AbstractShapeViewFactory;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassDanglingNodeEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.CanvasPackageEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry;

/**
 * @generated
 */
public class AssociationClassDanglingNodeViewFactory extends
        AbstractShapeViewFactory {

    /**
     * @generated
     */
    protected List createStyles(View view) {
        List styles = new ArrayList();
        styles.add(NotationFactory.eINSTANCE.createShapeStyle());
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
                    .getType(AssociationClassDanglingNodeEditPart.VISUAL_ID);
            view.setType(semanticHint);
        }
        super.decorateView(containerView, view, semanticAdapter, semanticHint,
                index, persisted);
        if (!CanvasPackageEditPart.MODEL_ID.equals(UMLVisualIDRegistry
                .getModelID(containerView))) {
            EAnnotation shortcutAnnotation = EcoreFactory.eINSTANCE
                    .createEAnnotation();
            shortcutAnnotation.setSource("Shortcut"); //$NON-NLS-1$
            shortcutAnnotation.getDetails().put(
                    "modelID", CanvasPackageEditPart.MODEL_ID); //$NON-NLS-1$
            view.getEAnnotations().add(shortcutAnnotation);
        }
    }
}
