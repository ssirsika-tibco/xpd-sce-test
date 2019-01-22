package com.tibco.xpd.om.modeler.subdiagram.view.factories;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gmf.runtime.diagram.ui.view.factories.DiagramViewFactory;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.notation.FillStyle;
import org.eclipse.gmf.runtime.notation.MeasurementUnit;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;

import com.tibco.xpd.om.modeler.subdiagram.preferences.custom.IOMSubPreferenceConstants;

/**
 * @generated
 */
public class OrganizationSubViewFactory extends DiagramViewFactory {

	/**
	 * @generated NOT
	 */
	protected List createStyles(View view) {
		List styles = new ArrayList();
		styles.add(NotationFactory.eINSTANCE.createDiagramStyle());
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
		// TODO Auto-generated method stub
		super.initializeFromPreferences(view);

		// Set the diagram background colour
		FillStyle style = (FillStyle) view.getStyle(NotationPackage.eINSTANCE
				.getFillStyle());

		if (style != null) {
			IPreferenceStore store = (IPreferenceStore) getPreferencesHint()
					.getPreferenceStore();
			style.setFillColor(FigureUtilities.RGBToInteger(PreferenceConverter
					.getColor(store,
							IOMSubPreferenceConstants.PREF_DIAGRAM_BG_COLOR)));
		}

	}
}
