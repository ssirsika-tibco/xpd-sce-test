package com.tibco.xpd.om.modeler.diagram.view.factories;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.AbstractShapeViewFactory;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.FillStyle;
import org.eclipse.gmf.runtime.notation.LineStyle;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;

import com.tibco.xpd.om.modeler.diagram.edit.parts.DynamicOrgUnitDisplayNameEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.DynamicOrgUnitEditPart;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry;
import com.tibco.xpd.om.modeler.subdiagram.preferences.custom.IOMSubPreferenceConstants;
import com.tibco.xpd.om.resources.ui.omnotation.OmNotationFactory;
import com.tibco.xpd.om.resources.ui.omnotation.OmNotationPackage;
import com.tibco.xpd.om.resources.ui.omnotation.ShapeGradientStyle;

/**
 * @generated
 */
public class DynamicOrgUnitViewFactory extends AbstractShapeViewFactory {

    /**
     * @generated
     */
    @Override
    protected List createStyles(View view) {
        List styles = new ArrayList();
        styles.add(NotationFactory.eINSTANCE.createShapeStyle());
        styles.add(OmNotationFactory.eINSTANCE.createShapeGradientStyle());
        styles.add(NotationFactory.eINSTANCE.createLineStyle());
        styles.add(NotationFactory.eINSTANCE.createFillStyle());
        return styles;
    }

    /**
     * @generated
     */
    @Override
    protected void decorateView(View containerView, View view,
            IAdaptable semanticAdapter, String semanticHint, int index,
            boolean persisted) {
        if (semanticHint == null) {
            semanticHint =
                    OrganizationModelVisualIDRegistry
                            .getType(DynamicOrgUnitEditPart.VISUAL_ID);
            view.setType(semanticHint);
        }
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
        getViewService().createNode(eObjectAdapter,
                view,
                OrganizationModelVisualIDRegistry
                        .getType(DynamicOrgUnitDisplayNameEditPart.VISUAL_ID),
                ViewUtil.APPEND,
                true,
                getPreferencesHint());
    }

    @Override
    protected void initializeFromPreferences(View view) {
        // TODO Auto-generated method stub
        super.initializeFromPreferences(view);

        IPreferenceStore store =
                (IPreferenceStore) getPreferencesHint().getPreferenceStore();

        ShapeGradientStyle gradStyle =
                (ShapeGradientStyle) view
                        .getStyle(OmNotationPackage.Literals.SHAPE_GRADIENT_STYLE);
        if (gradStyle != null) {
            gradStyle
                    .setGradStartColor(FigureUtilities.RGBToInteger(PreferenceConverter
                            .getColor(store,
                                    IOMSubPreferenceConstants.PREF_ORGUNIT_GRAD_START_COLOR)));

            gradStyle
                    .setGradEndColor(FigureUtilities.RGBToInteger(PreferenceConverter
                            .getColor(store,
                                    IOMSubPreferenceConstants.PREF_ORGUNIT_GRAD_END_COLOR)));

        }

        LineStyle lineStyle =
                (LineStyle) view.getStyle(NotationPackage.Literals.LINE_STYLE);

        if (lineStyle != null) {
            lineStyle
                    .setLineColor(FigureUtilities.RGBToInteger(PreferenceConverter
                            .getColor(store,
                                    IOMSubPreferenceConstants.PREF_ORGUNIT_LINE_COLOR)));
        }

        FillStyle fillStyle =
                (FillStyle) view.getStyle(NotationPackage.Literals.FILL_STYLE);

        if (fillStyle != null) {
            fillStyle
                    .setFillColor(FigureUtilities.RGBToInteger(PreferenceConverter
                            .getColor(store,
                                    IOMSubPreferenceConstants.PREF_ORGUNIT_FILL_COLOR)));
        }

    }
}
