/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.preferences;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.gmf.runtime.diagram.ui.figures.DiagramColorConstants;
import org.eclipse.gmf.runtime.diagram.ui.preferences.AppearancePreferencePage;
import org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.bom.modeler.diagram.part.BOMDiagramEditorPlugin;
import com.tibco.xpd.bom.modeler.diagram.part.Messages;

/**
 * @generated
 */
public class DiagramAppearancePreferencePage extends AppearancePreferencePage {

    private ColorFieldEditor classGrad1ColorEditor = null;

    private ColorFieldEditor classGrad2ColorEditor = null;

    private ColorFieldEditor classGrad3ColorEditor = null;

    private ColorFieldEditor classGrad4ColorEditor = null;

    private ColorFieldEditor packageGrad1ColorEditor = null;

    private ColorFieldEditor packageGrad2ColorEditor = null;

    private ColorFieldEditor primTypeGrad1ColorEditor = null;

    private ColorFieldEditor primTypeGrad2ColorEditor = null;

    private ColorFieldEditor enumerationGrad1ColorEditor = null;

    private ColorFieldEditor enumerationGrad2ColorEditor = null;

    private ColorFieldEditor assocClassGrad1ColorEditor = null;

    private ColorFieldEditor assocClassGrad2ColorEditor = null;

    private ColorFieldEditor badgeBgColorEditor = null;

    private ColorFieldEditor diagramBgColorEditor = null;

    /**
     * @generated
     */
    public DiagramAppearancePreferencePage() {
        setPreferenceStore(BOMDiagramEditorPlugin.getInstance()
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
        PreferenceConverter.setDefault(store,
                IBOMPreferenceConstants.PREF_DIAGRAM_BG_COLOR,
                fillColor.getRGB());

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

        Color conceptGrad1Color = DiagramColorConstants.lightGreen;
        PreferenceConverter.setDefault(store,
                IBOMPreferenceConstants.PREF_CLASS_GRAD_COLOR1,
                conceptGrad1Color.getRGB());

        Color conceptGrad2Color = DiagramColorConstants.white;
        PreferenceConverter.setDefault(store,
                IBOMPreferenceConstants.PREF_CLASS_GRAD_COLOR2,
                conceptGrad2Color.getRGB());

        Color conceptGrad3Color =
                new Color(Display.getCurrent(), new RGB(207, 234, 254));
        PreferenceConverter.setDefault(store,
                IBOMPreferenceConstants.PREF_CLASS_GRAD_COLOR3,
                conceptGrad3Color.getRGB());

        Color conceptGrad4Color =
                new Color(Display.getCurrent(), new RGB(41, 158, 245));
        PreferenceConverter.setDefault(store,
                IBOMPreferenceConstants.PREF_CLASS_GRAD_COLOR4,
                conceptGrad4Color.getRGB());

        Color packageGrad1Color =
                new Color(Display.getCurrent(), new RGB(166, 202, 240));
        PreferenceConverter.setDefault(store,
                IBOMPreferenceConstants.PREF_PACKAGE_GRAD_COLOR1,
                packageGrad1Color.getRGB());

        Color packageGrad2Color = DiagramColorConstants.white;
        PreferenceConverter.setDefault(store,
                IBOMPreferenceConstants.PREF_PACKAGE_GRAD_COLOR2,
                packageGrad2Color.getRGB());

        Color primTypeGrad1Color = DiagramColorConstants.lightGray;
        PreferenceConverter.setDefault(store,
                IBOMPreferenceConstants.PREF_PRIMTYPE_GRAD_COLOR1,
                primTypeGrad1Color.getRGB());

        Color primTypeGrad2Color = DiagramColorConstants.white;
        PreferenceConverter.setDefault(store,
                IBOMPreferenceConstants.PREF_PRIMTYPE_GRAD_COLOR2,
                primTypeGrad2Color.getRGB());

        Color enumerationGrad1Color =
                new Color(Display.getCurrent(), new RGB(255, 255, 128));
        PreferenceConverter.setDefault(store,
                IBOMPreferenceConstants.PREF_ENUMERATION_GRAD_COLOR1,
                enumerationGrad1Color.getRGB());

        Color enumerationTypeGrad2Color = DiagramColorConstants.white;
        PreferenceConverter.setDefault(store,
                IBOMPreferenceConstants.PREF_ENUMERATION_GRAD_COLOR2,
                enumerationTypeGrad2Color.getRGB());

        Color assocClassGrad1Color = DiagramColorConstants.lightGreen;
        PreferenceConverter.setDefault(store,
                IBOMPreferenceConstants.PREF_ASSOCCLASS_GRAD_COLOR1,
                assocClassGrad1Color.getRGB());

        Color assocClassGrad2Color = DiagramColorConstants.white;
        PreferenceConverter.setDefault(store,
                IBOMPreferenceConstants.PREF_ASSOCCLASS_GRAD_COLOR2,
                assocClassGrad2Color.getRGB());

        PreferenceConverter.setDefault(store,
                IBOMPreferenceConstants.PREF_BADGE_BG_COLOR,
                new RGB(246, 246, 248));

        PreferenceConverter.setDefault(store,
                IBOMPreferenceConstants.PREF_BADGE_PACKAGE_BG_COLOR,
                new RGB(246, 246, 248));

        PreferenceConverter.setDefault(store,
                IBOMPreferenceConstants.PREF_BADGE_SUBDIAG_BG_COLOR,
                new RGB(246, 246, 248));

    }

    @Override
    protected void addFontAndColorFields(Composite composite) {
        super.addFontAndColorFields(composite);

        classGrad1ColorEditor =
                new ColorFieldEditor(
                        IBOMPreferenceConstants.PREF_CLASS_GRAD_COLOR1,
                        Messages.DiagramAppearancePreferencePage_ClassGradientColor1_Label,
                        composite);
        addField(classGrad1ColorEditor);

        classGrad2ColorEditor =
                new ColorFieldEditor(
                        IBOMPreferenceConstants.PREF_CLASS_GRAD_COLOR2,
                        Messages.DiagramAppearancePreferencePage_ClassGradientColor2_Label,
                        composite);
        addField(classGrad2ColorEditor);

        classGrad3ColorEditor =
                new ColorFieldEditor(
                        IBOMPreferenceConstants.PREF_CLASS_GRAD_COLOR3,
                        Messages.DiagramAppearancePreferencePage_ClassGradientColor3_Label,
                        composite);
        addField(classGrad3ColorEditor);

        classGrad4ColorEditor =
                new ColorFieldEditor(
                        IBOMPreferenceConstants.PREF_CLASS_GRAD_COLOR4,
                        Messages.DiagramAppearancePreferencePage_ClassGradientColor4_Label,
                        composite);
        addField(classGrad4ColorEditor);

        packageGrad1ColorEditor =
                new ColorFieldEditor(
                        IBOMPreferenceConstants.PREF_PACKAGE_GRAD_COLOR1,
                        Messages.DiagramAppearancePreferencePage_PackageGradientColor1_Label,
                        composite);
        addField(packageGrad1ColorEditor);

        packageGrad2ColorEditor =
                new ColorFieldEditor(
                        IBOMPreferenceConstants.PREF_PACKAGE_GRAD_COLOR2,
                        Messages.DiagramAppearancePreferencePage_PackageGradientColor2_Label,
                        composite);
        addField(packageGrad2ColorEditor);

        primTypeGrad1ColorEditor =
                new ColorFieldEditor(
                        IBOMPreferenceConstants.PREF_PRIMTYPE_GRAD_COLOR1,
                        Messages.DiagramAppearancePreferencePage_PrimTypeGradientColor1_Label,
                        composite);
        addField(primTypeGrad1ColorEditor);

        primTypeGrad2ColorEditor =
                new ColorFieldEditor(
                        IBOMPreferenceConstants.PREF_PRIMTYPE_GRAD_COLOR2,
                        Messages.DiagramAppearancePreferencePage_PrimTypeGradientColor2_Label,
                        composite);
        addField(primTypeGrad2ColorEditor);

        enumerationGrad1ColorEditor =
                new ColorFieldEditor(
                        IBOMPreferenceConstants.PREF_ENUMERATION_GRAD_COLOR1,
                        Messages.DiagramAppearancePreferencePage_EnumerationGradientColor1_Label,
                        composite);
        addField(enumerationGrad1ColorEditor);

        enumerationGrad2ColorEditor =
                new ColorFieldEditor(
                        IBOMPreferenceConstants.PREF_ENUMERATION_GRAD_COLOR2,
                        Messages.DiagramAppearancePreferencePage_EnumerationGradientColor2_Label,
                        composite);
        addField(enumerationGrad2ColorEditor);

        assocClassGrad1ColorEditor =
                new ColorFieldEditor(
                        IBOMPreferenceConstants.PREF_ASSOCCLASS_GRAD_COLOR1,
                        Messages.DiagramAppearancePreferencePage_AssocClassGradientColor1_Label,
                        composite);
        addField(assocClassGrad1ColorEditor);

        assocClassGrad2ColorEditor =
                new ColorFieldEditor(
                        IBOMPreferenceConstants.PREF_ASSOCCLASS_GRAD_COLOR2,
                        Messages.DiagramAppearancePreferencePage_AssocClassGradientColor2_Label,
                        composite);
        addField(assocClassGrad2ColorEditor);

        badgeBgColorEditor =
                new ColorFieldEditor(
                        IBOMPreferenceConstants.PREF_BADGE_BG_COLOR,
                        Messages.DiagramAppearancePreferencePage_BadgeBackgroundColor_Label,
                        composite);
        addField(badgeBgColorEditor);

        diagramBgColorEditor =
                new ColorFieldEditor(
                        IBOMPreferenceConstants.PREF_DIAGRAM_BG_COLOR,
                        Messages.DiagramAppearancePreferencePage_DiagramBackgroundColor_Label,
                        composite);
        addField(diagramBgColorEditor);

    }

}
