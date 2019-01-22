package com.tibco.xpd.om.modeler.diagram.preferences;

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

import com.tibco.xpd.om.modeler.diagram.edit.parts.customfigures.IOMDiagramColorConstants;
import com.tibco.xpd.om.modeler.diagram.part.Messages;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorPlugin;
import com.tibco.xpd.om.modeler.diagram.preferences.custom.IOMPreferenceConstants;

/**
 * @generated
 */
public class DiagramAppearancePreferencePage extends AppearancePreferencePage {

    private ColorFieldEditor orgGradStartColorEditor;

    private ColorFieldEditor orgGradEndColorEditor;

    private ColorFieldEditor orgLineColorEditor;

    private ColorFieldEditor orgFillColorEditor;

    private ColorFieldEditor orgUnitFillColorEditor;

    private ColorFieldEditor orgUnitGradStartColorEditor;

    private ColorFieldEditor orgUnitGradEndColorEditor;

    private ColorFieldEditor orgUnitLineColorEditor;

    private ColorFieldEditor hierarchyLineColorEditor;

    private ColorFieldEditor associationLineColorEditor;

    private ColorFieldEditor dynamicOrgReferenceLineColorEditor;

    private ColorFieldEditor badgeFillColorEditor;

    private ColorFieldEditor badgeLineColorEditor;

    private ColorFieldEditor diagramBgColorEditor;

    /**
     * @generated
     */
    public DiagramAppearancePreferencePage() {
        setPreferenceStore(OrganizationModelDiagramEditorPlugin.getInstance()
                .getPreferenceStore());
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

        FontData[] defaultFontDataArray =
                PreferenceConverter.getDefaultFontDataArray(store,
                        IPreferenceConstants.PREF_DEFAULT_FONT);

        if (defaultFontDataArray.length > 0) {
            defaultFontDataArray[0].setHeight(10);
            PreferenceConverter.setDefault(store,
                    IPreferenceConstants.PREF_DEFAULT_FONT,
                    defaultFontDataArray);
        }

        Color fontColor = ColorConstants.black;
        PreferenceConverter.setDefault(store,
                IPreferenceConstants.PREF_FONT_COLOR,
                fontColor.getRGB());

        Color fillColor = DiagramColorConstants.white;
        PreferenceConverter.setDefault(store,
                IPreferenceConstants.PREF_FILL_COLOR,
                fillColor.getRGB());

        // Set default canvas background colour
        Color bgColor = DiagramColorConstants.white;
        PreferenceConverter.setDefault(store,
                IOMPreferenceConstants.PREF_DIAGRAM_BG_COLOR,
                bgColor.getRGB());

        Color lineColor = DiagramColorConstants.diagramGray;
        PreferenceConverter.setDefault(store,
                IPreferenceConstants.PREF_LINE_COLOR,
                lineColor.getRGB());

        Color noteFillColor = DiagramColorConstants.diagramLightYellow;
        PreferenceConverter.setDefault(store,
                IPreferenceConstants.PREF_NOTE_FILL_COLOR,
                noteFillColor.getRGB());

        Color noteLineColor = DiagramColorConstants.diagramDarkYellow;
        PreferenceConverter.setDefault(store,
                IPreferenceConstants.PREF_NOTE_LINE_COLOR,
                noteLineColor.getRGB());

        // Organizations
        PreferenceConverter.setDefault(store,
                IOMPreferenceConstants.PREF_ORG_FILL_COLOR,
                IOMDiagramColorConstants.OMOrgFillColor.getRGB());

        PreferenceConverter.setDefault(store,
                IOMPreferenceConstants.PREF_ORG_GRAD_START_COLOR,
                IOMDiagramColorConstants.OMOrgGradStartColor.getRGB());

        PreferenceConverter.setDefault(store,
                IOMPreferenceConstants.PREF_ORG_GRAD_END_COLOR,
                IOMDiagramColorConstants.OMOrgUnitGradEndColor.getRGB());

        PreferenceConverter.setDefault(store,
                IOMPreferenceConstants.PREF_ORG_LINE_COLOR,
                IOMDiagramColorConstants.OMOrgLineColor.getRGB());

        // OrgUnits
        PreferenceConverter.setDefault(store,
                IOMPreferenceConstants.PREF_ORGUNIT_FILL_COLOR,
                IOMDiagramColorConstants.OMOrgUnitFillColor.getRGB());

        PreferenceConverter.setDefault(store,
                IOMPreferenceConstants.PREF_ORGUNIT_GRAD_START_COLOR,
                IOMDiagramColorConstants.OMOrgUnitGradStartColor.getRGB());

        PreferenceConverter.setDefault(store,
                IOMPreferenceConstants.PREF_ORGUNIT_GRAD_END_COLOR,
                IOMDiagramColorConstants.OMOrgUnitGradEndColor.getRGB());

        Color orgUnitLineColor = DiagramColorConstants.black;
        PreferenceConverter.setDefault(store,
                IOMPreferenceConstants.PREF_ORGUNIT_LINE_COLOR,
                IOMDiagramColorConstants.OMOrgUnitLineColor.getRGB());

        // Badge
        PreferenceConverter.setDefault(store,
                IOMPreferenceConstants.PREF_BADGE_FILL_COLOR,
                IOMDiagramColorConstants.OMBadgeFillColor.getRGB());

        PreferenceConverter.setDefault(store,
                IOMPreferenceConstants.PREF_BADGE_LINE_COLOR,
                IOMDiagramColorConstants.OMBadgeLineColor.getRGB());

        // OrgUnitRelationships
        PreferenceConverter.setDefault(store,
                IOMPreferenceConstants.PREF_HIERARCHY_LINE_COLOR,
                IOMDiagramColorConstants.OMHierarchyLineColor.getRGB());

        // Dynamic organization reference connection
        PreferenceConverter.setDefault(store,
                IOMPreferenceConstants.PREF_DYN_ORG_REF_LINE_COLOR,
                IOMDiagramColorConstants.OMDynamicOrgReferenceLineColor
                        .getRGB());

        PreferenceConverter.setDefault(store,
                IOMPreferenceConstants.PREF_ASSOCIATION_LINE_COLOR,
                IOMDiagramColorConstants.OMAssociationLineColor.getRGB());

    }

    @Override
    protected void addFontAndColorFields(Composite composite) {
        super.addFontAndColorFields(composite);

        orgFillColorEditor =
                new ColorFieldEditor(
                        IOMPreferenceConstants.PREF_ORG_FILL_COLOR,
                        Messages.DiagramAppearancePreferencePage_OrganizationFillColor_Label,
                        composite);
        addField(orgFillColorEditor);

        orgGradStartColorEditor =
                new ColorFieldEditor(
                        IOMPreferenceConstants.PREF_ORG_GRAD_END_COLOR,
                        Messages.DiagramAppearancePreferencePage_OrganizationGradientStartColor_Label,
                        composite);
        addField(orgGradStartColorEditor);

        orgGradEndColorEditor =
                new ColorFieldEditor(
                        IOMPreferenceConstants.PREF_ORG_GRAD_END_COLOR,
                        Messages.DiagramAppearancePreferencePage_OrganizationGradientEndColor_Label,
                        composite);
        addField(orgGradEndColorEditor);

        orgLineColorEditor =
                new ColorFieldEditor(
                        IOMPreferenceConstants.PREF_ORG_LINE_COLOR,
                        Messages.DiagramAppearancePreferencePage_OrganizationLineColor_Label,
                        composite);
        addField(orgLineColorEditor);

        orgUnitFillColorEditor =
                new ColorFieldEditor(
                        IOMPreferenceConstants.PREF_ORGUNIT_FILL_COLOR,
                        Messages.DiagramAppearancePreferencePage_OrgUnitFillColor_Label,
                        composite);
        addField(orgUnitFillColorEditor);

        orgUnitGradStartColorEditor =
                new ColorFieldEditor(
                        IOMPreferenceConstants.PREF_ORGUNIT_GRAD_START_COLOR,
                        Messages.DiagramAppearancePreferencePage_OrgUnitGradientStartColor_Label,
                        composite);
        addField(orgUnitGradStartColorEditor);

        orgUnitGradEndColorEditor =
                new ColorFieldEditor(
                        IOMPreferenceConstants.PREF_ORGUNIT_GRAD_END_COLOR,
                        Messages.DiagramAppearancePreferencePage_OrgUnitGradientEndColor_Label,
                        composite);
        addField(orgUnitGradEndColorEditor);

        orgUnitLineColorEditor =
                new ColorFieldEditor(
                        IOMPreferenceConstants.PREF_ORGUNIT_LINE_COLOR,
                        Messages.DiagramAppearancePreferencePage_OrgUnitLineColor_Label,
                        composite);
        addField(orgUnitLineColorEditor);

        hierarchyLineColorEditor =
                new ColorFieldEditor(
                        IOMPreferenceConstants.PREF_HIERARCHY_LINE_COLOR,
                        Messages.DiagramAppearancePreferencePage_HierarchyLineColor_Label,
                        composite);
        addField(hierarchyLineColorEditor);

        associationLineColorEditor =
                new ColorFieldEditor(
                        IOMPreferenceConstants.PREF_ASSOCIATION_LINE_COLOR,
                        Messages.DiagramAppearancePreferencePage_AssociationLineColor_Label,
                        composite);
        addField(associationLineColorEditor);

        dynamicOrgReferenceLineColorEditor =
                new ColorFieldEditor(
                        IOMPreferenceConstants.PREF_DYN_ORG_REF_LINE_COLOR,
                        "Dynamic Organization Reference Line Color:", composite);
        addField(dynamicOrgReferenceLineColorEditor);

        badgeFillColorEditor =
                new ColorFieldEditor(
                        IOMPreferenceConstants.PREF_BADGE_FILL_COLOR,
                        Messages.DiagramAppearancePreferencePage_BadgeFillColor_Label,
                        composite);
        addField(badgeFillColorEditor);

        badgeLineColorEditor =
                new ColorFieldEditor(
                        IOMPreferenceConstants.PREF_BADGE_LINE_COLOR,
                        Messages.DiagramAppearancePreferencePage_BadgeLineColor_Label,
                        composite);
        addField(badgeLineColorEditor);

        diagramBgColorEditor =
                new ColorFieldEditor(
                        IOMPreferenceConstants.PREF_DIAGRAM_BG_COLOR,
                        Messages.DiagramAppearancePreferencePage_DiagramBackgroundColor_Label,
                        composite);
        addField(diagramBgColorEditor);

    }
}
