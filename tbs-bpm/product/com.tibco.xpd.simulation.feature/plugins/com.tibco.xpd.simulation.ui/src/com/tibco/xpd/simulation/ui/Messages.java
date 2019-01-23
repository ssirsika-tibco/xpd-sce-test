package com.tibco.xpd.simulation.ui;

import java.text.DecimalFormat;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.simulation.ui.messages"; //$NON-NLS-1$

    private Messages() {
    }

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    public static String DeleteSimulationParameterDelegate_DeleteParameters;

    public static String MSG_SIMULATION_ALREADY_RUNNING;

    public static String PrepareSimulationAction_Title;

    public static String PrepareSimulationAction_WarningText;

    public static String PrepareSimulationAction_WarningTitle;

    public static String PrepareSimulationUIStatusHandler_Message;

    public static String PrepareSimulationUIStatusHandler_Title;

    public static String PrepareSimulationUIStatusHandler_Toggle;

    public static String ResultsContentViewer_Property;

    public static String ResultsContentViewer_Value;

    public static String SimulationAlreadyRunningStatusHandler_Message;

    public static String SimulationAlreadyRunningStatusHandler_Title;

    public static String SimulationControlView_Current;

    public static String SimulationControlView_Faster;

    public static String SimulationControlView_Minutes;

    public static String SimulationControlView_NoProcessSelected;

    public static String SimulationControlView_Process1;

    public static String SimulationControlView_Progress;

    public static String SimulationControlView_Slower;

    public static String SimulationControlView_Speed;

    public static String SimulationControlView_Start;

    public static String SimulationControlView_Time;

    public static String SimulationLaunchMainTab_Main;

    public static String SimulationLaunchMainTab_Package;

    public static String SimulationLaunchMainTab_Process;

    public static String SimulationLaunchMainTab_ProcessToRun;

    public static String SimulationLaunchMainTab_Project;

    public static String SimulationLaunchMainTab_ProjectSelection;

    public static String SimulationLaunchMainTab_SelectProject;

    public static String SimulationLaunchShortcut_Debug;

    public static String SimulationLaunchShortcut_DialogTitle;

    public static String SimulationLaunchShortcut_ErrorMessage;

    public static String SimulationLaunchShortcut_ErrorTitle;

    public static String SimulationLaunchShortcut_Message;

    public static String SimulationLaunchShortcut_Run;

    public static String SimulationLaunchShortcut_Title;

    public static String SimulationPreferencePage_DontAskToSwitch2;

    public static String SimulationSaveNeededStatusHandler_Message;

    public static String SimulationSaveNeededStatusHandler_Title;

    public static String SimulationValidationProcessErrorStatusHandler_Message;

    public static String SimulationValidationProcessErrorStatusHandler_Title;

    public static String TITLE_SWITCH_PERSPECTIVE;

    public static String MSG_SWITCH_PERSPECTIVE;

    public static String MSG_DONT_PROMPT_AGAIN;

    public static String MSG_SAVE_RESOURCES;

    public static String TITLE_SAVE_RESOURCES;

    public static String EX_SAVE_RESOURCES;

    public static String TITLE_SIMULATION;

    public static String MSG_SIMULATION_NOT_DESTINATION_ENVIRONMENT;

    public static String MSG_NO_LAST_SIMULATION_CONFIG;

    public static String SimulationLaunchMainTab_Browse;

    public static String SimulationLaunchMainTab_Project_Not_Exist_Error;

    public static String SimulationLaunchMainTab_Project_Closed_Error;

    public static String SimulationLaunchMainTab_Package_Unspecified_Error;

    public static String SimulationLaunchMainTab_Package_Not_Exist_Error;

    public static String SimulationLaunchMainTab_Process_Unspecified_Error;

    public static String SimulationLaunchMainTab_Illegal_Project_Name_Error;

    public static String SimulationLaunchMainTab_packagePicker_shortdesc;

    public static String SimulationLaunchMainTab_packagePicker_title;

    public static String SimulationLaunchMainTab_Project_Unspecified_Error;

    /**
     * Formats a number into localised double number rounded to specific decimal
     * places
     * 
     * @param doubleVal
     * @param decimalPlaces
     * @return
     */
    public static String formatNumber(String doubleVal, int decimalPlaces) {
        String localisedRoundedDoubleValue = doubleVal;
        try {
            double dblVal = Double.parseDouble(doubleVal.trim());
            dblVal =
                    Math.round(dblVal * Math.pow(10, decimalPlaces))
                            / Math.pow(10, decimalPlaces);
            localisedRoundedDoubleValue =
                    DecimalFormat.getInstance().format(dblVal);
        } catch (NumberFormatException e) {
        }
        return localisedRoundedDoubleValue;
    }
}
