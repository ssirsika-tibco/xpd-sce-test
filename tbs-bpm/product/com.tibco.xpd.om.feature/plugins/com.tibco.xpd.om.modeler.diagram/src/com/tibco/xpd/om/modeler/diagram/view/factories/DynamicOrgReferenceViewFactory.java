package com.tibco.xpd.om.modeler.diagram.view.factories;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.ConnectionViewFactory;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.notation.LineStyle;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;

import com.tibco.xpd.om.modeler.diagram.edit.parts.DynamicOrgReferenceEditPart;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelVisualIDRegistry;
import com.tibco.xpd.om.modeler.diagram.preferences.custom.IOMPreferenceConstants;

/**
 * @generated
 */
public class DynamicOrgReferenceViewFactory extends ConnectionViewFactory {

    /**
     * @generated
     */
    @Override
    protected List createStyles(View view) {
        List styles = new ArrayList();
        styles.add(NotationFactory.eINSTANCE.createConnectorStyle());
        styles.add(NotationFactory.eINSTANCE.createFontStyle());
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
                            .getType(DynamicOrgReferenceEditPart.VISUAL_ID);
            view.setType(semanticHint);
        }
        super.decorateView(containerView,
                view,
                semanticAdapter,
                semanticHint,
                index,
                persisted);
    }

    /**
     * @see org.eclipse.gmf.runtime.diagram.ui.view.factories.ConnectionViewFactory#initializeFromPreferences(org.eclipse.gmf.runtime.notation.View)
     * 
     * @param view
     */
    @Override
    protected void initializeFromPreferences(View view) {
        super.initializeFromPreferences(view);

        IPreferenceStore store =
                (IPreferenceStore) getPreferencesHint().getPreferenceStore();

        LineStyle lineStyle =
                (LineStyle) view.getStyle(NotationPackage.Literals.LINE_STYLE);

        if (lineStyle != null) {

            lineStyle
                    .setLineColor(FigureUtilities.RGBToInteger(PreferenceConverter
                            .getColor(store,
                                    IOMPreferenceConstants.PREF_DYN_ORG_REF_LINE_COLOR)));
            lineStyle.setLineWidth(2);

        }
    }
}
