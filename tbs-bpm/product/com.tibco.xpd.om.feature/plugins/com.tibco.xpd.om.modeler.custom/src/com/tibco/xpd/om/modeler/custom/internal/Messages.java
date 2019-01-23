package com.tibco.xpd.om.modeler.custom.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

    private static final String BUNDLE_NAME =
            "com.tibco.xpd.om.modeler.custom.internal.messages"; //$NON-NLS-1$

    public static String CreateOMElementDiagramAction_AddOrganizationLabel;

    public static String CreateOMElementDiagramAction_AddOrganizationUnitLabel;

    public static String CreateOMElementDiagramAction_AddPositionLabel;

    public static String OMAssetConfigurator_DefaultFolderName;

	public static String OMAssetWizardPage_ApplyDefaultType_label;

    public static String OMAssetWizardPage_CreateDefaultSchema_label;

    public static String OMAssetWizardPage_CreateInitialOM_label;

    public static String OMAssetWizardPage_DefaultOMFile_name;

	public static String OMAssetWizardPage_Description_label;

    public static String OMAssetWizardPage_Filename_label;

    public static String OMAssetWizardPage_ModelDetail_label;

    public static String OMAssetWizardPage_Title_label;

    public static String OMFileDateCreatedParser_UnknownDate_label;

    public static String OMFileDateModifiedParser_UnknownDate_label;

    public static String OMGlobalEditActionHandler_copy_action;

    public static String OrgModelAuthorParser_AuthorNotSet_label;

    public static String OrgModelVersionParser_VersionNotSet_label;

    public static String DiagramBackgroundSection_bgColor_accessible_shortdesc;

    public static String DiagramBackgroundSection_bgColor_button_tooltip;

    public static String DiagramBackgroundSection_bgColor_label;

    public static String DiagramBackgroundSection_setBgColor_command_label;

    public static String ShapeGradientSection_endColor_message;

    public static String ShapeGradientSection_Gradient_label;

    public static String ShapeGradientSection_startColor_message;

    public static String ShowHideBadgeDiagramAction_showHideBadge_action;

    public static String default_omfolder_name;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
