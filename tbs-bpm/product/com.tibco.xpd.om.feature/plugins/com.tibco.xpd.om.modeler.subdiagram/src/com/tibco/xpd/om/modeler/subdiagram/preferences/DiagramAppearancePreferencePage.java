package com.tibco.xpd.om.modeler.subdiagram.preferences;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.gmf.runtime.diagram.ui.figures.DiagramColorConstants;
import org.eclipse.gmf.runtime.diagram.ui.preferences.AppearancePreferencePage;
import org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.om.modeler.subdiagram.edit.parts.customfigures.IOMSubDiagramColorConstants;
import com.tibco.xpd.om.modeler.subdiagram.part.Messages;
import com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelSubDiagramEditorPlugin;
import com.tibco.xpd.om.modeler.subdiagram.preferences.custom.IOMSubPreferenceConstants;

/**
 * @generated
 */
public class DiagramAppearancePreferencePage extends AppearancePreferencePage {

	private ColorFieldEditor orgUnitFillColorEditor = null;

	private ColorFieldEditor orgUnitGradStartColorEditor = null;

	private ColorFieldEditor orgUnitGradEndColorEditor = null;

	private ColorFieldEditor orgUnitLineColorEditor = null;

	private ColorFieldEditor hierarchyLineColorEditor = null;

	private ColorFieldEditor associationLineColorEditor = null;

	private ColorFieldEditor badgeFillColorEditor = null;

	private ColorFieldEditor badgeLineColorEditor = null;

	private ColorFieldEditor diagramBgColorEditor = null;

	/**
	 * @generated
	 */
	public DiagramAppearancePreferencePage() {
		setPreferenceStore(OrganizationModelSubDiagramEditorPlugin
				.getInstance().getPreferenceStore());
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		initDefaults(getPreferenceStore());
	}

	/**
	 * Initializes the default preference values for this preference store.
	 * 
	 * @param store
	 */
	public static void initDefaults(IPreferenceStore store) {

		FontData[] defaultFontDataArray = PreferenceConverter
				.getDefaultFontDataArray(store,
						IPreferenceConstants.PREF_DEFAULT_FONT);

		if (defaultFontDataArray.length > 0) {
			defaultFontDataArray[0].setHeight(10);
			PreferenceConverter.setDefault(store,
					IPreferenceConstants.PREF_DEFAULT_FONT,
					defaultFontDataArray);
		}

		Color fontColor = ColorConstants.black;
		PreferenceConverter.setDefault(store,
				IPreferenceConstants.PREF_FONT_COLOR, fontColor.getRGB());

		Color fillColor = DiagramColorConstants.white;
		PreferenceConverter.setDefault(store,
				IPreferenceConstants.PREF_FILL_COLOR, fillColor.getRGB());

		Color lineColor = DiagramColorConstants.diagramGray;
		PreferenceConverter.setDefault(store,
				IPreferenceConstants.PREF_LINE_COLOR, lineColor.getRGB());

		Color noteFillColor = DiagramColorConstants.diagramLightYellow;
		PreferenceConverter.setDefault(store,
				IPreferenceConstants.PREF_NOTE_FILL_COLOR, noteFillColor
						.getRGB());

		Color noteLineColor = DiagramColorConstants.diagramDarkYellow;
		PreferenceConverter.setDefault(store,
				IPreferenceConstants.PREF_NOTE_LINE_COLOR, noteLineColor
						.getRGB());

		// Set default canvas background colour
		PreferenceConverter.setDefault(store,
				IOMSubPreferenceConstants.PREF_DIAGRAM_BG_COLOR,
				IOMSubDiagramColorConstants.OMSubDiagramBackgroundColor
						.getRGB());

		// OrgUnit
		PreferenceConverter.setDefault(store,
				IOMSubPreferenceConstants.PREF_ORGUNIT_FILL_COLOR,
				IOMSubDiagramColorConstants.OMSubOrgUnitFillColor.getRGB());

		PreferenceConverter.setDefault(store,
				IOMSubPreferenceConstants.PREF_ORGUNIT_LINE_COLOR,
				IOMSubDiagramColorConstants.OMSubOrgUnitLineColor.getRGB());

		PreferenceConverter
				.setDefault(
						store,
						IOMSubPreferenceConstants.PREF_ORGUNIT_GRAD_START_COLOR,
						IOMSubDiagramColorConstants.OMSubOrgUnitGradStartColor
								.getRGB());

		PreferenceConverter.setDefault(store,
				IOMSubPreferenceConstants.PREF_ORGUNIT_GRAD_END_COLOR,
				IOMSubDiagramColorConstants.OMSubOrgUnitGradEndColor.getRGB());

		// Badge
		PreferenceConverter.setDefault(store,
				IOMSubPreferenceConstants.PREF_BADGE_FILL_COLOR,
				IOMSubDiagramColorConstants.OMSubBadgeFillColor.getRGB());

		PreferenceConverter.setDefault(store,
				IOMSubPreferenceConstants.PREF_BADGE_LINE_COLOR,
				IOMSubDiagramColorConstants.OMSubBadgeLineColor.getRGB());

		// OrgUnitRelationship
		Color hierarchyLineColor = IOMSubDiagramColorConstants.OMSubHierarchyLineColor;
		PreferenceConverter.setDefault(store,
				IOMSubPreferenceConstants.PREF_HIERARCHY_LINE_COLOR,
				hierarchyLineColor.getRGB());

		Color associationLineColor = IOMSubDiagramColorConstants.OMSubAssociationLineColor;
		PreferenceConverter.setDefault(store,
				IOMSubPreferenceConstants.PREF_ASSOCIATION_LINE_COLOR,
				associationLineColor.getRGB());

	}

	@Override
	protected void addFontAndColorFields(Composite composite) {
		super.addFontAndColorFields(composite);

		orgUnitFillColorEditor = new ColorFieldEditor(
				IOMSubPreferenceConstants.PREF_ORGUNIT_FILL_COLOR,
				Messages.DiagramAppearancePreferencePage_OrgUnitFillColor_Label,
				composite);
		addField(orgUnitFillColorEditor);

		orgUnitGradStartColorEditor = new ColorFieldEditor(
				IOMSubPreferenceConstants.PREF_ORGUNIT_GRAD_START_COLOR,
				Messages.DiagramAppearancePreferencePage_OrgUnitGradientStartColor_Label,
				composite);
		addField(orgUnitGradStartColorEditor);

		orgUnitGradEndColorEditor = new ColorFieldEditor(
				IOMSubPreferenceConstants.PREF_ORGUNIT_GRAD_END_COLOR,
				Messages.DiagramAppearancePreferencePage_OrgUnitGradientEndColor_Label,
				composite);
		addField(orgUnitGradEndColorEditor);

		orgUnitLineColorEditor = new ColorFieldEditor(
				IOMSubPreferenceConstants.PREF_ORGUNIT_LINE_COLOR,
				Messages.DiagramAppearancePreferencePage_OrgUnitLineColor_Label,
				composite);
		addField(orgUnitLineColorEditor);

		hierarchyLineColorEditor = new ColorFieldEditor(
				IOMSubPreferenceConstants.PREF_HIERARCHY_LINE_COLOR,
				Messages.DiagramAppearancePreferencePage_HierarchyLineColor_Label,
				composite);
		addField(hierarchyLineColorEditor);

		associationLineColorEditor = new ColorFieldEditor(
				IOMSubPreferenceConstants.PREF_ASSOCIATION_LINE_COLOR,
				Messages.DiagramAppearancePreferencePage_AssociationLineColor_Label,
				composite);
		addField(associationLineColorEditor);

		badgeFillColorEditor = new ColorFieldEditor(
				IOMSubPreferenceConstants.PREF_BADGE_FILL_COLOR,
				Messages.DiagramAppearancePreferencePage_BadgeFillColor_Label,
				composite);
		addField(badgeFillColorEditor);

		badgeLineColorEditor = new ColorFieldEditor(
				IOMSubPreferenceConstants.PREF_BADGE_LINE_COLOR,
				Messages.DiagramAppearancePreferencePage_BadgeLineColor_Label,
				composite);
		addField(badgeLineColorEditor);

		diagramBgColorEditor = new ColorFieldEditor(
				IOMSubPreferenceConstants.PREF_DIAGRAM_BG_COLOR,
				Messages.DiagramAppearancePreferencePage_DiagramBackgroundColor_Label,
				composite);
		addField(diagramBgColorEditor);

	}

}
