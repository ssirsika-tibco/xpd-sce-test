package com.tibco.xpd.simulation.compare;

import java.text.DecimalFormat;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.simulation.compare.messages"; //$NON-NLS-1$

    public static String CompareResultsAction_CompareSimulationResultsLabel;

    public static String RegisterReport_NameLabel;

    public static String RegisterReport_NewReportTitle;

    public static String RegisterReport_ReportDetails;

    public static String RegisterReport_ReportDetailsMessage;

    public static String RegisterReport_ReportDetailsTitle;

    public static String RegisterReport_ReportFileLabel;

    public static String ReportManagementPreferencePage_ConfirmDeleteMessage;

    public static String ReportManagementPreferencePage_DeleteLabel;

    public static String ReportManagementPreferencePage_DeleteReportLabel;

    public static String ReportManagementPreferencePage_EditLabel;

    public static String ReportManagementPreferencePage_ImportLabel;

    public static String ReportManagementPreferencePage_NameColumnLabel;

    public static String ReportManagementPreferencePage_RestoreDefaultsLabel;

    public static String ReportManagementPreferencePage_TypeColumnLabel;

    public static String SimulationCompareEditor_defaultReportFileName;

    public static String SimulationCompareEditor_deleteSimulationData_dialog_message;

    public static String SimulationCompareEditor_deleteSimulationData_dialog_title;

    public static String SimulationCompareEditor_deleteSimulationData_menu_label;

    public static String SimulationCompareEditor_DeleteFailed;

    public static String SimulationCompareEditor_DeleteFailedMessage;

    public static String SimulationCompareEditor_DisplayReportLabel;

    public static String SimulationCompareEditor_errorDeletingData_errorDialog_title;

    public static String SimulationCompareEditor_errorDeletingDataFile_longdesc;

    public static String SimulationCompareEditor_errorDeletingMultipleDataFiles_longdesc;

    public static String SimulationCompareEditor_FileExistsError;

    public static String SimulationCompareEditor_GenerateReportLabel;

    public static String SimulationCompareEditor_GenerateSavedReportLabel;

    public static String SimulationCompareEditor_NameColumnLabel;

    public static String SimulationCompareEditor_OverwriteLabel;

    public static String SimulationCompareEditor_PleaseWaitLabel;

    public static String SimulationCompareEditor_SaveHtmlReportLabel;

    public static String SimulationCompareEditor_SavePdfReportLabel;

    public static String SimulationCompareEditor_SaveReportTitle;

    public static String SimulationCompareEditor_seeDetails_shortdesc;

    public static String SimulationCompareEditor_SelectReportMessage;

    public static String SimulationCompareEditor_TypeColumnLabel;

    public static String SimulationReportMoneyFormat;

    public static String SimulationReportHtml_Simulation_title;

    public static String SimulationReportHtml_SimulationHeaderTag_title;

    public static String SimulationReportHtml_SimulationName_label;

    public static String SimulationReportHtml_SimulationPackageName_label;

    public static String SimulationReportHtml_SimulationProcessName_label;

    public static String SimulationReportHtml_SimulationExperimentData_label;

    public static String SimulationReportHtml_SimulationCases_label;

    public static String SimulationReportHtml_SimulationParticipants_label;

    public static String SimulationReportHtml_SimulationActivities_label;

    public static String SimulationReportHtml_SimulationExperimentState_label;

    public static String SimulationReportHtml_SimulationDuration_label;

    public static String SimulationReportHtml_SimulationTimeUnit_label;

    public static String SimulationReportHtml_SimulationStartTime_label;

    public static String SimulationReportHtml_SimulationEndTime_label;

    public static String SimulationReportHtml_SimulationCostUnit_label;

    public static String SimulationReportHtml_SimulationRoundedZeroPlaces_label;

    public static String SimulationReportHtml_SimulationRoundedTwoPlaces_label;

    public static String SimulationReportHtml_SimulationCases_title;

    public static String SimulationReportHtml_SimulationStarted_label;

    public static String SimulationReportHtml_SimulationFinished_label;

    public static String SimulationReportHtml_SimulationAverageTime_label;

    public static String SimulationReportHtml_SimulationMinimumTime_label;

    public static String SimulationReportHtml_SimulationMaximumTime_label;

    public static String SimulationReportHtml_SimulationCaseStartIntervalDistribution_label;

    public static String SimulationReportHtml_SimulationCaseCost_label;

    public static String SimulationReportHtml_SimulationLastResetTime_label;

    public static String SimulationReportHtml_SimulationObservedElements_label;

    public static String SimulationReportHtml_SimulationDistributionCategory_label;

    public static String SimulationReportHtml_SimulationParameterOne_label;

    public static String SimulationReportHtml_SimulationParameterTwo_label;

    public static String SimulationReportHtml_SimulationRandomiserSeed_label;

    public static String SimulationReportHtml_SimulationAverageCost_label;

    public static String SimulationReportHtml_SimulationMinimumCost_label;

    public static String SimulationReportHtml_SimulationMaximumCost_label;

    public static String SimulationReportHtml_SimulationCumulativeAverageCost_label;

    public static String SimulationReportHtml_SimulationParticipant_label;

    public static String SimulationReportHtml_SimulationCount_label;

    public static String SimulationReportHtml_SimulationCurrentIdleTime_label;

    public static String SimulationReportHtml_SimulationAverageIdleCount_label;

    public static String SimulationReportHtml_SimulationAverageIdleTime_label;

    public static String SimulationReportHtml_SimulationAverageBusyTime_label;

    public static String SimulationReportHtml_SimulationCumulativeCost_label;

    public static String SimulationReportHtml_SimulationActivity_label;

    public static String SimulationReportHtml_SimulationDurationDistribution_label;

    public static String SimulationReportHtml_SimulationActivityQueue_label;

    public static String SimulationReportHtml_SimulationActivityCost_label;

    public static String SimulationReportHtml_SimulationProcessedCases_label;

    public static String SimulationReportHtml_SimulationCurrentQueueSize_label;

    public static String SimulationReportHtml_SimulationMaximumQueueSize_label;

    public static String SimulationReportHtml_SimulationAverageQueueSize_label;

    public static String SimulationReportHtml_SimulationAverageWait_label;

    public static String SimulationReportHtml_SimulationSwitchToPrint_label;

    public static String SimulationReportHtml_SimulationSwitchToNormal_label;

    public static String SimulationReportHtml_ChangeView_title;

    public static String getReportFormat() {
        double tempVal = Double.parseDouble("1.11"); //$NON-NLS-1$
        String reportFormat = DecimalFormat.getInstance().format(tempVal);
        reportFormat = reportFormat.replace("1", "0"); //$NON-NLS-1$ //$NON-NLS-2$
        return reportFormat;
    }

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

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
