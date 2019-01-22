/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.view.factories;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.AbstractShapeViewFactory;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.FillStyle;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.LineStyle;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeAuthorEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeDateCreatedEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeDateModifiedEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeDiagramNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeLabelAuthorEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeLabelDateCreatedEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeLabelDateModifiedEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeLabelModelIconEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeModelNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.CanvasPackageEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PackageBadgeProfilesCompartmentEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry;
import com.tibco.xpd.bom.modeler.diagram.preferences.IBOMPreferenceConstants;
import com.tibco.xpd.bom.resources.firstclassprofiles.FirstClassProfileManager;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;

/**
 * @generated
 */
public class BadgeViewFactory extends AbstractShapeViewFactory {

    /**
     * @generated
     */
    protected List createStyles(View view) {
        List styles = new ArrayList();
        styles.add(NotationFactory.eINSTANCE.createShapeStyle());
        styles.add(NotationFactory.eINSTANCE.createFillStyle());
        return styles;
    }

    /**
     * @generated NOT
     */
    protected void decorateView(View containerView, View view,
            IAdaptable semanticAdapter, String semanticHint, int index,
            boolean persisted) {
        if (semanticHint == null) {
            semanticHint = UMLVisualIDRegistry.getType(BadgeEditPart.VISUAL_ID);
            view.setType(semanticHint);
        }
        super.decorateView(containerView,
                view,
                semanticAdapter,
                semanticHint,
                index,
                persisted);

        // Set badge coordinates to Origin
        Node node = (Node) view;
        LayoutConstraint lc = node.getLayoutConstraint();

        if (lc instanceof Bounds) {
            Bounds bnds = (Bounds) lc;
            bnds.setX(0);
            bnds.setY(0);
        }

        EObject elem = view.getElement();

        if (elem != null) {
            if (BomUIUtil.isUserDiagram(node.getDiagram())) {
                decorateViewForSubDiagramBadge(containerView,
                        view,
                        semanticAdapter,
                        semanticHint,
                        index,
                        persisted);
            } else if (elem instanceof Model) {

                decorateViewForModelBadge(containerView,
                        view,
                        semanticAdapter,
                        semanticHint,
                        index,
                        persisted);
            } else {
                decorateViewForPackageBadge(containerView,
                        view,
                        semanticAdapter,
                        semanticHint,
                        index,
                        persisted);
            }
        }

    }

    private void decorateViewForModelBadge(View containerView, View view,
            IAdaptable semanticAdapter, String semanticHint, int index,
            boolean persisted) {

        if (!CanvasPackageEditPart.MODEL_ID.equals(UMLVisualIDRegistry
                .getModelID(containerView))) {
            EAnnotation shortcutAnnotation =
                    EcoreFactory.eINSTANCE.createEAnnotation();
            shortcutAnnotation.setSource("Shortcut"); //$NON-NLS-1$
            shortcutAnnotation.getDetails()
                    .put("modelID", CanvasPackageEditPart.MODEL_ID); //$NON-NLS-1$
            view.getEAnnotations().add(shortcutAnnotation);
        }
        IAdaptable eObjectAdapter = null;
        EObject eObject = (EObject) semanticAdapter.getAdapter(EObject.class);
        if (eObject != null) {
            eObjectAdapter = new EObjectAdapter(eObject);
        }
        getViewService().createNode(eObjectAdapter,
                view,
                UMLVisualIDRegistry.getType(BadgeModelNameEditPart.VISUAL_ID),
                ViewUtil.APPEND,
                true,
                getPreferencesHint());
        getViewService()
                .createNode(eObjectAdapter,
                        view,
                        UMLVisualIDRegistry
                                .getType(BadgeLabelAuthorEditPart.VISUAL_ID),
                        ViewUtil.APPEND,
                        true,
                        getPreferencesHint());
        getViewService().createNode(eObjectAdapter,
                view,
                UMLVisualIDRegistry.getType(BadgeAuthorEditPart.VISUAL_ID),
                ViewUtil.APPEND,
                true,
                getPreferencesHint());
        getViewService().createNode(eObjectAdapter,
                view,
                UMLVisualIDRegistry
                        .getType(BadgeLabelModelIconEditPart.VISUAL_ID),
                ViewUtil.APPEND,
                true,
                getPreferencesHint());
        getViewService().createNode(eObjectAdapter,
                view,
                UMLVisualIDRegistry
                        .getType(BadgeLabelDateCreatedEditPart.VISUAL_ID),
                ViewUtil.APPEND,
                true,
                getPreferencesHint());
        getViewService()
                .createNode(eObjectAdapter,
                        view,
                        UMLVisualIDRegistry
                                .getType(BadgeDateCreatedEditPart.VISUAL_ID),
                        ViewUtil.APPEND,
                        true,
                        getPreferencesHint());
        getViewService().createNode(eObjectAdapter,
                view,
                UMLVisualIDRegistry
                        .getType(BadgeLabelDateModifiedEditPart.VISUAL_ID),
                ViewUtil.APPEND,
                true,
                getPreferencesHint());
        getViewService().createNode(eObjectAdapter,
                view,
                UMLVisualIDRegistry
                        .getType(BadgeDateModifiedEditPart.VISUAL_ID),
                ViewUtil.APPEND,
                true,
                getPreferencesHint());

        EObject elem = view.getElement();

        if (elem instanceof Model) {
            Model model = (Model) elem;

            if (!FirstClassProfileManager.getInstance()
                    .isFirstClassProfileApplied(model)) {
                getViewService()
                        .createNode(eObjectAdapter,
                                view,
                                UMLVisualIDRegistry
                                        .getType(PackageBadgeProfilesCompartmentEditPart.VISUAL_ID),
                                ViewUtil.APPEND,
                                true,
                                getPreferencesHint());
            }
        }

    }

    private void decorateViewForPackageBadge(View containerView, View view,
            IAdaptable semanticAdapter, String semanticHint, int index,
            boolean persisted) {

        if (!CanvasPackageEditPart.MODEL_ID.equals(UMLVisualIDRegistry
                .getModelID(containerView))) {
            EAnnotation shortcutAnnotation =
                    EcoreFactory.eINSTANCE.createEAnnotation();
            shortcutAnnotation.setSource("Shortcut"); //$NON-NLS-1$
            shortcutAnnotation.getDetails()
                    .put("modelID", CanvasPackageEditPart.MODEL_ID); //$NON-NLS-1$
            view.getEAnnotations().add(shortcutAnnotation);
        }
        IAdaptable eObjectAdapter = null;
        EObject eObject = (EObject) semanticAdapter.getAdapter(EObject.class);
        if (eObject != null) {
            eObjectAdapter = new EObjectAdapter(eObject);
        }
        getViewService().createNode(eObjectAdapter,
                view,
                UMLVisualIDRegistry.getType(BadgeModelNameEditPart.VISUAL_ID),
                ViewUtil.APPEND,
                true,
                getPreferencesHint());

    }

    private void decorateViewForSubDiagramBadge(View containerView, View view,
            IAdaptable semanticAdapter, String semanticHint, int index,
            boolean persisted) {

        if (!CanvasPackageEditPart.MODEL_ID.equals(UMLVisualIDRegistry
                .getModelID(containerView))) {
            EAnnotation shortcutAnnotation =
                    EcoreFactory.eINSTANCE.createEAnnotation();
            shortcutAnnotation.setSource("Shortcut"); //$NON-NLS-1$
            shortcutAnnotation.getDetails()
                    .put("modelID", CanvasPackageEditPart.MODEL_ID); //$NON-NLS-1$
            view.getEAnnotations().add(shortcutAnnotation);
        }
        IAdaptable eObjectAdapter = null;
        EObject eObject = (EObject) semanticAdapter.getAdapter(EObject.class);

        if (eObject != null) {
            eObjectAdapter = new EObjectAdapter(eObject);
        }

        getViewService()
                .createNode(new EObjectAdapter(containerView),
                        view,
                        UMLVisualIDRegistry
                                .getType(BadgeDiagramNameEditPart.VISUAL_ID),
                        ViewUtil.APPEND,
                        true,
                        getPreferencesHint());

        getViewService().createNode(eObjectAdapter,
                view,
                String.valueOf(BadgeModelNameEditPart.VISUAL_ID),
                ViewUtil.APPEND,
                true,
                getPreferencesHint());
        getViewService().createNode(eObjectAdapter,
                view,
                UMLVisualIDRegistry
                        .getType(BadgeLabelModelIconEditPart.VISUAL_ID),
                ViewUtil.APPEND,
                true,
                getPreferencesHint());

    }

    @Override
    protected void initializeFromPreferences(View view) {
        super.initializeFromPreferences(view);

        IPreferenceStore store =
                (IPreferenceStore) getPreferencesHint().getPreferenceStore();

        FillStyle fillStyle =
                (FillStyle) view.getStyle(NotationPackage.Literals.FILL_STYLE);

        EObject element = view.getElement();

        if (element != null) {

            if (BomUIUtil.isUserDiagram(view.getDiagram())) {
                fillStyle
                        .setFillColor(FigureUtilities
                                .RGBToInteger(PreferenceConverter
                                        .getColor(store,
                                                IBOMPreferenceConstants.PREF_BADGE_SUBDIAG_BG_COLOR)));
            } else if (element instanceof Model) {
                fillStyle.setFillColor(FigureUtilities
                        .RGBToInteger(PreferenceConverter.getColor(store,
                                IBOMPreferenceConstants.PREF_BADGE_BG_COLOR)));
            } else {
                fillStyle
                        .setFillColor(FigureUtilities
                                .RGBToInteger(PreferenceConverter
                                        .getColor(store,
                                                IBOMPreferenceConstants.PREF_BADGE_PACKAGE_BG_COLOR)));
            }
        } else {
            fillStyle.setFillColor(FigureUtilities
                    .RGBToInteger(PreferenceConverter.getColor(store,
                            IBOMPreferenceConstants.PREF_BADGE_BG_COLOR)));
        }

        LineStyle lineStyle =
                (LineStyle) view.getStyle(NotationPackage.Literals.LINE_STYLE);

        lineStyle
                .setLineColor(FigureUtilities.RGBToInteger(new RGB(0, 145, 0)));

    }

    // Special Node
    @Override
    protected boolean requiresElement(EObject semanticElement, View view) {
        return true;
    }

}
