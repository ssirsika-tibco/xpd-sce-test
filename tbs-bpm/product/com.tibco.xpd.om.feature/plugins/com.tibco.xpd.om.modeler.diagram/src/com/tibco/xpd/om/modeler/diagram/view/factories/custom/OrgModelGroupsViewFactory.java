/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.om.modeler.diagram.view.factories.custom;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.AbstractShapeViewFactory;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.LineStyle;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.graphics.RGB;

import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.LabelOrgModelGroupsEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelGroupsCompartmentEditPart;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry;

/**
 * 
 * Custom view that is a container for views representing all groups within the
 * Organization Model.
 * 
 * @author rgreen
 * 
 */
public class OrgModelGroupsViewFactory extends AbstractShapeViewFactory {

    public OrgModelGroupsViewFactory() {
        super();
    }

    protected List createStyles(View view) {
        List styles = new ArrayList();

        styles.add(NotationFactory.eINSTANCE.createShapeStyle());
        styles.add(NotationFactory.eINSTANCE.createLineStyle());
        styles.add(NotationFactory.eINSTANCE.createFillStyle());

        return styles;
    }

    @Override
    protected void decorateView(View containerView, View view,
            IAdaptable semanticAdapter, String semanticHint, int index,
            boolean persisted) {
        super.decorateView(containerView,
                view,
                semanticAdapter,
                semanticHint,
                index,
                persisted);

        IAdaptable eObjectAdapter = null;
        EObject eObject = (EObject) semanticAdapter.getAdapter(EObject.class);
        if (eObject != null) {
            eObjectAdapter = new EObjectAdapter(eObject);
        }

        Node createNode1 =
                getViewService()
                        .createNode(eObjectAdapter,
                                view,
                                OrganizationModelVisualIDRegistry
                                        .getType(LabelOrgModelGroupsEditPart.VISUAL_ID),
                                ViewUtil.APPEND,
                                true,
                                getPreferencesHint());

        Node createNode2 =
                getViewService()
                        .createNode(eObjectAdapter,
                                view,
                                OrganizationModelVisualIDRegistry
                                        .getType(OrgModelGroupsCompartmentEditPart.VISUAL_ID),
                                ViewUtil.APPEND,
                                true,
                                getPreferencesHint());

    }

    @Override
    protected void initializeFromPreferences(View view) {

        super.initializeFromPreferences(view);

        IPreferenceStore store =
                (IPreferenceStore) getPreferencesHint().getPreferenceStore();

        LineStyle lineStyle =
                (LineStyle) view.getStyle(NotationPackage.Literals.LINE_STYLE);

        if (lineStyle != null) {
            lineStyle.setLineColor(FigureUtilities
                    .RGBToInteger(new RGB(0, 0, 0)));
        }
    }
}