package com.tibco.xpd.analyst.resources.xpdl2;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.analyst.resources.xpdl2.messages"; //$NON-NLS-1$	

    public static String ProcessSpecialFolder_projectFileReorg_message;

    public static String ProcessSpecialFolder_projectNotAccessible_message;

    public static String ProcessFilterPicker_Dialog_Display_Message;

    public static String ProcessFilterPicker_ProcessPicker_SelectProcess_Title;

    public static String ProcessFilterPicker_ProcessPicker_SelectProcessInterface_Title;

    public static String ProcessFilterPicker_ProcessPicker_SelectServiceProcessInterface_Title;

    public static String ProcessFilterPicker_ProcessPicker_SelectProcessOrPInterface_Title;

    public static String ProcessFilterPicker_SelectPAgeflow_title;

    public static String ProcessFilterPicker_SelectPageflowOrProcessIfc_title;

    public static String ProcessFilterPicker_SelectAnyProcess_title;

    public static String ProcessFilterPicker_SelectBusinessOrServiceProcess_title;

    public static String DataFilterPicker_DataPicker_SelectDataField_Title;

    public static String DataFilterPicker_DataPicker_SelectFormalParameter_Title;

    public static String DataFilterPicker_DataPicker_SelectDataFieldOrFormalParameter_Title;

    public static String DataFilterPicker_DataPicker_SelectParticipants_Title;

    public static String DataFilterPicker_Dialog_Display_Message;

    public static String ActivityFilterPicker_ActivityPicker_SelectActivity_Title;

    public static String ActivityFilterPicker_Dialog_Display_Message;



    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
