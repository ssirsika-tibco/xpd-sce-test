/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.view.factories;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.BasicNodeViewFactory;
import org.eclipse.gmf.runtime.notation.DrawerStyle;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.TitleStyle;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.ClassClassOperationsCompartmentEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry;
import com.tibco.xpd.bom.resources.firstclassprofiles.FirstClassProfileManager;
import com.tibco.xpd.bom.resources.firstclassprofiles.IFirstClassProfileExtension;

/**
 * @generated
 */
public class ClassClassOperationsCompartmentViewFactory extends
        BasicNodeViewFactory {

    /**
     * @generated
     */
    protected List createStyles(View view) {
        List styles = new ArrayList();
        styles.add(NotationFactory.eINSTANCE.createDrawerStyle());
        styles.add(NotationFactory.eINSTANCE.createSortingStyle());
        styles.add(NotationFactory.eINSTANCE.createFilteringStyle());
        return styles;
    }

    /**
     * @generated NOT
     */
    protected void decorateView(View containerView, View view,
            IAdaptable semanticAdapter, String semanticHint, int index,
            boolean persisted) {
        decorateViewGen(containerView,
                view,
                semanticAdapter,
                semanticHint,
                index,
                persisted);

        // See if this compartment should be hidden
        setupVisibility(view);

    }

    /**
     * @generated
     */
    protected void decorateViewGen(View containerView, View view,
            IAdaptable semanticAdapter, String semanticHint, int index,
            boolean persisted) {
        if (semanticHint == null) {
            semanticHint =
                    UMLVisualIDRegistry
                            .getType(ClassClassOperationsCompartmentEditPart.VISUAL_ID);
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

    /**
     * 
     * @generated NOT
     * 
     * @param view
     */
    private void setupVisibility(View view) {
        // If a custom profile has specified that this compartment should
        // not be visible then change visibility here
        EObject element = view.getDiagram().getElement();

        if (element instanceof Package) {
            Model model = ((Model) element).getModel();
            if (model != null) {
                /*
                 * Check if there is a first-class profile applied, and if so
                 * then check if operations should be displayed
                 */
                IFirstClassProfileExtension ext =
                        FirstClassProfileManager.getInstance()
                                .getAppliedFirstClassProfile(model);
                if (ext != null && !ext.showOperations()) {
                    view.setVisible(false);
                }
            }
        }
    }

    /**
     * @generated
     */
    protected void setupCompartmentTitle(View view) {
        TitleStyle titleStyle =
                (TitleStyle) view.getStyle(NotationPackage.eINSTANCE
                        .getTitleStyle());
        if (titleStyle != null) {
            titleStyle.setShowTitle(true);
        }
    }

    /**
     * @generated
     */
    protected void setupCompartmentCollapsed(View view) {
        DrawerStyle drawerStyle =
                (DrawerStyle) view.getStyle(NotationPackage.eINSTANCE
                        .getDrawerStyle());
        if (drawerStyle != null) {
            drawerStyle.setCollapsed(false);
        }
    }
}
