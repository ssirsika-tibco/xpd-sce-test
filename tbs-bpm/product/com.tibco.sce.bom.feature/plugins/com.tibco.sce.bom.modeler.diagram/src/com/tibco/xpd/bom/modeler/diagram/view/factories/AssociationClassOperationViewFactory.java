/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.view.factories;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.AbstractLabelViewFactory;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassOperationEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry;

/**
 * @generated
 */
public class AssociationClassOperationViewFactory extends
        AbstractLabelViewFactory {

    /**
     * @generated
     */
    protected List createStyles(View view) {
        List styles = new ArrayList();
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
                    .getType(AssociationClassOperationEditPart.VISUAL_ID);
            view.setType(semanticHint);
        }
        super.decorateView(containerView, view, semanticAdapter, semanticHint,
                index, persisted);
    }
}
