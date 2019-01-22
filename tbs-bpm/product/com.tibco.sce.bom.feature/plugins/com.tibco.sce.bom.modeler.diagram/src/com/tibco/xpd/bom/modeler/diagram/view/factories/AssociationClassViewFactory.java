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
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassClassifierAttributesCompartmentEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassClassifierOperationsCompartmentEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassStereoTypeLabelEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.AssociationClassSuperClassNameEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.CanvasPackageEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry;
import com.tibco.xpd.bom.modeler.diagram.preferences.IBOMPreferenceConstants;
import com.tibco.xpd.bom.resources.ui.bomnotation.BomNotationFactory;
import com.tibco.xpd.bom.resources.ui.bomnotation.BomNotationPackage;
import com.tibco.xpd.bom.resources.ui.bomnotation.ShapeGradientStyle;

/**
 * @generated
 */
public class AssociationClassViewFactory extends AbstractShapeViewFactory {

    /**
     * @generated NOT
     * 
     *            Add custom gradient style
     * 
     */
    protected List createStyles(View view) {
        List styles = new ArrayList();
        styles.add(NotationFactory.eINSTANCE.createShapeStyle());
        styles.add(BomNotationFactory.eINSTANCE.createShapeGradientStyle());
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
                    .getType(AssociationClassEditPart.VISUAL_ID);
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
        IAdaptable eObjectAdapter = null;
        EObject eObject = (EObject) semanticAdapter.getAdapter(EObject.class);
        if (eObject != null) {
            eObjectAdapter = new EObjectAdapter(eObject);
        }
        getViewService().createNode(
                eObjectAdapter,
                view,
                UMLVisualIDRegistry
                        .getType(AssociationClassNameEditPart.VISUAL_ID),
                ViewUtil.APPEND, true, getPreferencesHint());
        getViewService()
                .createNode(
                        eObjectAdapter,
                        view,
                        UMLVisualIDRegistry
                                .getType(AssociationClassSuperClassNameEditPart.VISUAL_ID),
                        ViewUtil.APPEND, true, getPreferencesHint());
        getViewService()
                .createNode(
                        eObjectAdapter,
                        view,
                        UMLVisualIDRegistry
                                .getType(AssociationClassStereoTypeLabelEditPart.VISUAL_ID),
                        ViewUtil.APPEND, true, getPreferencesHint());
        getViewService()
                .createNode(
                        eObjectAdapter,
                        view,
                        UMLVisualIDRegistry
                                .getType(AssociationClassClassifierAttributesCompartmentEditPart.VISUAL_ID),
                        ViewUtil.APPEND, true, getPreferencesHint());
        getViewService()
                .createNode(
                        eObjectAdapter,
                        view,
                        UMLVisualIDRegistry
                                .getType(AssociationClassClassifierOperationsCompartmentEditPart.VISUAL_ID),
                        ViewUtil.APPEND, true, getPreferencesHint());
    }

    @Override
    protected void initializeFromPreferences(View view) {
        super.initializeFromPreferences(view);

        IPreferenceStore store = (IPreferenceStore) getPreferencesHint()
                .getPreferenceStore();

        ShapeGradientStyle gradStyle = (ShapeGradientStyle) view
                .getStyle(BomNotationPackage.Literals.SHAPE_GRADIENT_STYLE);
        if (gradStyle != null) {
            gradStyle
                    .setGradStartColor(FigureUtilities
                            .RGBToInteger(PreferenceConverter
                                    .getColor(
                                            store,
                                            IBOMPreferenceConstants.PREF_ASSOCCLASS_GRAD_COLOR1)));

            gradStyle
                    .setGradEndColor(FigureUtilities
                            .RGBToInteger(PreferenceConverter
                                    .getColor(
                                            store,
                                            IBOMPreferenceConstants.PREF_ASSOCCLASS_GRAD_COLOR2)));

        }

    }
}
