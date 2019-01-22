/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.om.modeler.diagram.view.factories.custom;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.BasicNodeViewFactory;
import org.eclipse.gmf.runtime.notation.DrawerStyle;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.TitleStyle;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelGroupsCompartmentEditPart;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry;

/**
 * 
 * Custom compartment view to contain group items
 * 
 * @author rgreen
 * 
 */
public class OrgModelGroupsCompartmentViewFactory extends BasicNodeViewFactory {

    protected List createStyles(View view) {
        List styles = new ArrayList();
        styles.add(NotationFactory.eINSTANCE.createDrawerStyle());
        styles.add(NotationFactory.eINSTANCE.createTitleStyle());
        styles.add(NotationFactory.eINSTANCE.createSortingStyle());
        styles.add(NotationFactory.eINSTANCE.createFilteringStyle());
        return styles;
    }

    protected void decorateView(View containerView, View view,
            IAdaptable semanticAdapter, String semanticHint, int index,
            boolean persisted) {
        if (semanticHint == null) {
            semanticHint =
                    OrganizationModelVisualIDRegistry
                            .getType(OrgModelGroupsCompartmentEditPart.VISUAL_ID);

            view.setType(semanticHint);
        }
        super.decorateView(containerView,
                view,
                semanticAdapter,
                semanticHint,
                index,
                persisted);
        setupCompartmentTitle(view);
        setupCompartmentCollapsed(view);
    }

    protected void setupCompartmentTitle(View view) {
        TitleStyle titleStyle =
                (TitleStyle) view.getStyle(NotationPackage.eINSTANCE
                        .getTitleStyle());
        if (titleStyle != null) {
            titleStyle.setShowTitle(true);
        }
    }

    protected void setupCompartmentCollapsed(View view) {
        DrawerStyle drawerStyle =
                (DrawerStyle) view.getStyle(NotationPackage.eINSTANCE
                        .getDrawerStyle());
        if (drawerStyle != null) {
            drawerStyle.setCollapsed(false);
        }
    }
}
