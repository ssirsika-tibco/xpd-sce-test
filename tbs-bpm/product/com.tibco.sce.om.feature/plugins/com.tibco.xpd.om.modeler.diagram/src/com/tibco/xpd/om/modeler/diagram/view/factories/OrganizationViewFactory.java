package com.tibco.xpd.om.modeler.diagram.view.factories;

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
import org.eclipse.gmf.runtime.notation.FillStyle;
import org.eclipse.gmf.runtime.notation.HintedDiagramLinkStyle;
import org.eclipse.gmf.runtime.notation.LineStyle;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;

import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgModelEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrganizationDisplayNameEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrganizationEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrganizationOrgUnitCompartmentEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrganizationTypeNameEditPart;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry;
import com.tibco.xpd.om.modeler.subdiagram.preferences.custom.IOMSubPreferenceConstants;
import com.tibco.xpd.om.resources.ui.omnotation.OmNotationFactory;
import com.tibco.xpd.om.resources.ui.omnotation.OmNotationPackage;
import com.tibco.xpd.om.resources.ui.omnotation.ShapeGradientStyle;

/**
 * @generated
 */
public class OrganizationViewFactory extends AbstractShapeViewFactory {

    /**
     * @generated NOT
     */
    protected List createStyles(View view) {
        List styles = new ArrayList();
        styles.add(NotationFactory.eINSTANCE.createShapeStyle());
        {
            HintedDiagramLinkStyle diagramFacet =
                    NotationFactory.eINSTANCE.createHintedDiagramLinkStyle();
            diagramFacet.setHint("Organization Model Sub"); //$NON-NLS-1$
            styles.add(diagramFacet);
        }

        styles.add(OmNotationFactory.eINSTANCE.createShapeGradientStyle());
        styles.add(NotationFactory.eINSTANCE.createLineStyle());
        styles.add(NotationFactory.eINSTANCE.createFillStyle());

        return styles;
    }

    /**
     * @generated
     */
    protected void decorateView(View containerView, View view,
            IAdaptable semanticAdapter, String semanticHint, int index,
            boolean persisted) {
        if (semanticHint == null) {
            semanticHint =
                    OrganizationModelVisualIDRegistry
                            .getType(OrganizationEditPart.VISUAL_ID);
            view.setType(semanticHint);
        }
        super.decorateView(containerView,
                view,
                semanticAdapter,
                semanticHint,
                index,
                persisted);
        if (!OrgModelEditPart.MODEL_ID.equals(OrganizationModelVisualIDRegistry
                .getModelID(containerView))) {
            EAnnotation shortcutAnnotation =
                    EcoreFactory.eINSTANCE.createEAnnotation();
            shortcutAnnotation.setSource("Shortcut"); //$NON-NLS-1$
            shortcutAnnotation.getDetails()
                    .put("modelID", OrgModelEditPart.MODEL_ID); //$NON-NLS-1$
            view.getEAnnotations().add(shortcutAnnotation);
        }
        IAdaptable eObjectAdapter = null;
        EObject eObject = (EObject) semanticAdapter.getAdapter(EObject.class);
        if (eObject != null) {
            eObjectAdapter = new EObjectAdapter(eObject);
        }
        getViewService().createNode(eObjectAdapter,
                view,
                OrganizationModelVisualIDRegistry
                        .getType(OrganizationDisplayNameEditPart.VISUAL_ID),
                ViewUtil.APPEND,
                true,
                getPreferencesHint());
        getViewService().createNode(eObjectAdapter,
                view,
                OrganizationModelVisualIDRegistry
                        .getType(OrganizationTypeNameEditPart.VISUAL_ID),
                ViewUtil.APPEND,
                true,
                getPreferencesHint());
        getViewService()
                .createNode(eObjectAdapter,
                        view,
                        OrganizationModelVisualIDRegistry
                                .getType(OrganizationOrgUnitCompartmentEditPart.VISUAL_ID),
                        ViewUtil.APPEND,
                        true,
                        getPreferencesHint());
    }

    @Override
    protected void initializeFromPreferences(View view) {

        super.initializeFromPreferences(view);

        IPreferenceStore store =
                (IPreferenceStore) getPreferencesHint().getPreferenceStore();

        ShapeGradientStyle gradStyle =
                (ShapeGradientStyle) view
                        .getStyle(OmNotationPackage.Literals.SHAPE_GRADIENT_STYLE);
        if (gradStyle != null) {
            gradStyle
                    .setGradStartColor(FigureUtilities
                            .RGBToInteger(PreferenceConverter
                                    .getColor(store,
                                            IOMSubPreferenceConstants.PREF_ORG_GRAD_START_COLOR)));

            gradStyle
                    .setGradEndColor(FigureUtilities
                            .RGBToInteger(PreferenceConverter
                                    .getColor(store,
                                            IOMSubPreferenceConstants.PREF_ORG_GRAD_END_COLOR)));

        }

        LineStyle lineStyle =
                (LineStyle) view.getStyle(NotationPackage.Literals.LINE_STYLE);

        if (lineStyle != null) {
            lineStyle.setLineColor(FigureUtilities
                    .RGBToInteger(PreferenceConverter.getColor(store,
                            IOMSubPreferenceConstants.PREF_ORG_LINE_COLOR)));
        }

        FillStyle fillStyle =
                (FillStyle) view.getStyle(NotationPackage.Literals.FILL_STYLE);

        if (fillStyle != null) {
            fillStyle.setFillColor(FigureUtilities
                    .RGBToInteger(PreferenceConverter.getColor(store,
                            IOMSubPreferenceConstants.PREF_ORG_FILL_COLOR)));
        }
    }
}
