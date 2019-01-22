/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.view.factories;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gmf.runtime.diagram.ui.view.factories.DiagramViewFactory;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.notation.FillStyle;
import org.eclipse.gmf.runtime.notation.MeasurementUnit;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;

import com.tibco.xpd.bom.modeler.diagram.preferences.IBOMPreferenceConstants;

/**
 * @generated
 */
public class CanvasPackageViewFactory extends DiagramViewFactory {

    /**
     * @generated NOT
     */
    @SuppressWarnings("unchecked")
    protected List<?> createStyles(View view) {
        List<Style> styles = super.createStyles(view);
        // Add the fill style so that the diagram background colour can be
        // stored
        styles.add(NotationFactory.eINSTANCE.createFillStyle());

        return styles;
    }

    /**
     * @generated
     */
    protected MeasurementUnit getMeasurementUnit() {
        return MeasurementUnit.PIXEL_LITERAL;
    }

    @Override
    protected void initializeFromPreferences(View view) {
        super.initializeFromPreferences(view);

        // Set the diagram background colour
        FillStyle style = (FillStyle) view.getStyle(NotationPackage.eINSTANCE
                .getFillStyle());

        if (style != null) {
            IPreferenceStore store = (IPreferenceStore) getPreferencesHint()
                    .getPreferenceStore();
            style.setFillColor(FigureUtilities.RGBToInteger(PreferenceConverter
                    .getColor(store,
                            IBOMPreferenceConstants.PREF_DIAGRAM_BG_COLOR)));
        }

    }
}
