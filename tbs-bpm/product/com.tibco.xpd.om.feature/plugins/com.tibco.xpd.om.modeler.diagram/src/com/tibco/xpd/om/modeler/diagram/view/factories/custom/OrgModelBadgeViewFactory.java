package com.tibco.xpd.om.modeler.diagram.view.factories.custom;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.AbstractShapeViewFactory;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.FillStyle;
import org.eclipse.gmf.runtime.notation.LineStyle;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;

import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.LabelAuthorBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.LabelDateCreatedBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.LabelDateModifiedBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.LabelVersionBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelAuthorBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelDateCreatedBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelDateModifiedBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelNameBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.custom.OrgModelVersionBadgeEditPart;
import com.tibco.xpd.om.modeler.diagram.preferences.custom.IOMPreferenceConstants;
import com.tibco.xpd.om.modeler.subdiagram.preferences.custom.IOMSubPreferenceConstants;
import com.tibco.xpd.om.resources.ui.omnotation.BorderGradientStyle;
import com.tibco.xpd.om.resources.ui.omnotation.OmNotationFactory;
import com.tibco.xpd.om.resources.ui.omnotation.OmNotationPackage;
import com.tibco.xpd.om.resources.ui.omnotation.ShapeGradientStyle;

public class OrgModelBadgeViewFactory extends AbstractShapeViewFactory {

    public OrgModelBadgeViewFactory() {
        super();
    }

    protected List createStyles(View view) {
        List styles = new ArrayList();
        // styles.add(NotationFactory.eINSTANCE.createShapeStyle());
        //
        // LineStyle ls = NotationFactory.eINSTANCE.createLineStyle();
        //
        // ls.setLineColor(SWT.COLOR_BLUE);
        //
        // FontStyle createFontStyle =
        // NotationFactory.eINSTANCE.createFontStyle();
        //
        // createFontStyle.setFontColor(SWT.COLOR_BLUE);
        //
        // styles.add(ls);
        // styles.add(createFontStyle);

        styles.add(NotationFactory.eINSTANCE.createShapeStyle());
        styles.add(OmNotationFactory.eINSTANCE.createShapeGradientStyle());
        styles.add(OmNotationFactory.eINSTANCE.createBorderGradientStyle());
        styles.add(NotationFactory.eINSTANCE.createFillStyle());
        styles.add(NotationFactory.eINSTANCE.createLineStyle());

        return styles;
    }

    protected void decorateView(View containerView, View view,
            IAdaptable semanticAdapter, String semanticHint, int index,
            boolean persisted) {

        super.decorateView(containerView, view, semanticAdapter, semanticHint,
                index, persisted);

        IAdaptable eObjectAdapter = null;

        EObject container = view.eContainer();

        PreferencesHint preferencesHint = getPreferencesHint();

        if (container instanceof Diagram) {
            Diagram diag = (Diagram) container;

            EObject element = diag.getElement();
            eObjectAdapter = new EObjectAdapter(element);

            getViewService().createNode(eObjectAdapter, view,
                    OrgModelNameBadgeEditPart.VISUAL_ID, ViewUtil.APPEND, true,
                    preferencesHint);

            // getViewService().createNode(container, eObject, type,
            // preferencesHint);

            // getViewService().createNode(view,
            // LabelAuthorBadgeEditPart.VISUAL_ID, preferencesHint);

            getViewService().createNode(eObjectAdapter, view,
                    LabelAuthorBadgeEditPart.VISUAL_ID, ViewUtil.APPEND, true,
                    preferencesHint);

            getViewService().createNode(eObjectAdapter, view,
                    OrgModelAuthorBadgeEditPart.VISUAL_ID, ViewUtil.APPEND,
                    true, preferencesHint);

            // getViewService().createNode(view,
            // LabelVersionBadgeEditPart.VISUAL_ID, preferencesHint);

            getViewService().createNode(eObjectAdapter, view,
                    LabelVersionBadgeEditPart.VISUAL_ID, ViewUtil.APPEND, true,
                    preferencesHint);

            getViewService().createNode(eObjectAdapter, view,
                    OrgModelVersionBadgeEditPart.VISUAL_ID, ViewUtil.APPEND,
                    true, preferencesHint);

            // getViewService().createNode(view,
            // LabelDateCreatedBadgeEditPart.VISUAL_ID, preferencesHint);

            getViewService().createNode(eObjectAdapter, view,
                    LabelDateCreatedBadgeEditPart.VISUAL_ID, ViewUtil.APPEND,
                    true, preferencesHint);

            getViewService().createNode(eObjectAdapter, view,
                    OrgModelDateCreatedBadgeEditPart.VISUAL_ID,
                    ViewUtil.APPEND, true, preferencesHint);

            // getViewService().createNode(view,
            // LabelDateModifiedBadgeEditPart.VISUAL_ID, preferencesHint);

            getViewService().createNode(eObjectAdapter, view,
                    LabelDateModifiedBadgeEditPart.VISUAL_ID, ViewUtil.APPEND,
                    true, preferencesHint);

            getViewService().createNode(eObjectAdapter, view,
                    OrgModelDateModifiedBadgeEditPart.VISUAL_ID,
                    ViewUtil.APPEND, true, preferencesHint);

        }

    }

    @Override
    protected void initializeFromPreferences(View view) {
        super.initializeFromPreferences(view);

        IPreferenceStore store = (IPreferenceStore) getPreferencesHint()
                .getPreferenceStore();

        ShapeGradientStyle gradStyle = (ShapeGradientStyle) view
                .getStyle(OmNotationPackage.Literals.SHAPE_GRADIENT_STYLE);
        if (gradStyle != null) {
            gradStyle
                    .setGradStartColor(FigureUtilities
                            .RGBToInteger(PreferenceConverter
                                    .getColor(
                                            store,
                                            IOMSubPreferenceConstants.PREF_BADGE_GRAD_START_COLOR)));

            gradStyle
                    .setGradEndColor(FigureUtilities
                            .RGBToInteger(PreferenceConverter
                                    .getColor(
                                            store,
                                            IOMSubPreferenceConstants.PREF_BADGE_GRAD_END_COLOR)));

        }

        LineStyle lineStyle = (LineStyle) view
                .getStyle(NotationPackage.Literals.LINE_STYLE);

        if (lineStyle != null) {
            lineStyle.setLineColor(FigureUtilities
                    .RGBToInteger(PreferenceConverter.getColor(store,
                            IOMSubPreferenceConstants.PREF_BADGE_LINE_COLOR)));
        }

        FillStyle fillStyle = (FillStyle) view
                .getStyle(NotationPackage.Literals.FILL_STYLE);

        if (fillStyle != null) {
            fillStyle.setFillColor(FigureUtilities
                    .RGBToInteger(PreferenceConverter.getColor(store,
                            IOMSubPreferenceConstants.PREF_BADGE_FILL_COLOR)));
        }

        BorderGradientStyle borderGradStyle = (BorderGradientStyle) view
                .getStyle(OmNotationPackage.Literals.BORDER_GRADIENT_STYLE);

        if (borderGradStyle != null) {
            borderGradStyle
                    .setGradStartColor(FigureUtilities
                            .RGBToInteger(PreferenceConverter
                                    .getColor(
                                            store,
                                            IOMPreferenceConstants.PREF_BADGE_BORDER_GRAD_START_COLOR)));

            borderGradStyle
                    .setGradEndColor(FigureUtilities
                            .RGBToInteger(PreferenceConverter
                                    .getColor(
                                            store,
                                            IOMPreferenceConstants.PREF_BADGE_BORDER_GRAD_END_COLOR)));

        }

    }

}
