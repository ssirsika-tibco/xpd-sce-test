/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.om.modeler.diagram.view.factories.custom;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.AbstractLabelViewFactory;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.GroupItemEditPart;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry;

/**
 * 
 * Custom view representing individual groups listed inside the groups
 * compartment.
 * 
 * @author rgreen
 * 
 */
public class GroupItemViewFactory extends AbstractLabelViewFactory {

    protected List createStyles(View view) {
        List styles = new ArrayList();
        return styles;
    }

    protected void decorateView(View containerView, View view,
            IAdaptable semanticAdapter, String semanticHint, int index,
            boolean persisted) {
        if (semanticHint == null) {
            semanticHint =
                    OrganizationModelVisualIDRegistry
                            .getType(GroupItemEditPart.VISUAL_ID);
            view.setType(semanticHint);
        }
        super.decorateView(containerView,
                view,
                semanticAdapter,
                semanticHint,
                index,
                persisted);
    }
}
