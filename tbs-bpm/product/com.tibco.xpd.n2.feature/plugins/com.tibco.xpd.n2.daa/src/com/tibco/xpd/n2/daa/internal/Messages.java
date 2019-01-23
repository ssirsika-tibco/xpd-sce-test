/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.n2.daa.internal;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * <p>
 * <i>Created: 3 Apr 2008</i>
 * </p>
 * 
 * @author glewis
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.n2.daa.internal.messages"; //$NON-NLS-1$

    public static String AdminDropAssistantDelegate_DropException_message;

    public static String AdminDropAssistantDelegate_PerformDeployment;

    public static String AdminDropAssistantDelegate_PerformPredeploymentTasks;

    public static String AdminDropAssistantDelegate_PreparingDeploymentTasks;

    public static String BaseSelectModulesPage_SelectModulesMessage;

    public static String BPMProjectDeployWizard_BPMProjectDeployment_title;

    public static String BPMProjectDeployWizard2_BPM_ProjectTypeName_label;

    public static String DAAFromProjectExportWizard_CopyProblem_message;

    public static String DAAFromProjectExportWizard_DestinationExistsDialog_message;

    public static String DAAFromProjectExportWizard_DestinationExistsDialog_title;

    public static String DAAFromProjectExportWizard_Error_message;

    public static String DAAFromProjectExportWizard_Error_title;

    public static String DAAFromProjectExportWizard_Exporting_message;

    public static String DAAFromProjectExportWizard_problemCheckingProcessesInPackage_longdesc;

    public static String DAAFromProjectExportWizard_ProblemsWhileSavingResourcesError_msg;

    public static String DAAFromProjectExportWizard_problemWaitingForBuildsToFinish_error_longdesc;

    public static String DAAFromProjectExportWizard_title;

    public static String ExportsFolder_title;

    public static String DAAFolder_title;

    public static String InputOutputSelectionWizardPage_NoSelectionError_message;

    public static String InputOutputSelectionWizardPage_projects_contain_errors_label;

    public static String MultiProjectDAAGenerationWithProgress_BuildingDAA_message;

    public static String MultiProjectDAAGenerationWithProgress_BuildingProjects_message;

    public static String MultiProjectDAAGenerationWithProgress_genDAA_label;

    public static String MultiProjectDAAGenerationWithProgress_genDAAException_message;

    public static String MultiProjectDAAGenerationWithProgress_genDAAForProjectProblem_message;

    public static String MultiProjectDAAGenerationWithProgress_genDAAProblems_message;

    public static String MultiProjectDAAGenerationWithProgress_genDAAWorkspaceOpException_message;

    public static String MultiProjectDAAGenerationWithProgress_ProjectHasErrors_message;

    public static String MultiProjectDAAGenerationWithProgress_ValidatingProjects_message;

    public static String N2PEBuilder_buildN2PEModule_desc;

    public static String N2PEZipAndFileUtils_renameError_message;

    public static String ProjectDAAGenerator_3;

    public static String ProjectDAAGenerator_BuildingComposite_message_2;

    public static String ProjectDAAGenerator_BuildingDAAFor_message;

    public static String ProjectDAAGenerator_cleaningProject_message;

    public static String ProjectDAAGenerator_cleaningProjects_message;

    public static String ProjectDAAGenerator_componentProblem_message;

    public static String ProjectDAAGenerator_componentsProblems_message2;

    public static String ProjectDAAGenerator_CompositeGenForProjectError_message;

    public static String ProjectDAAGenerator_CompositeGenForProjectProblem_message;

    public static String ProjectDAAGenerator_CompositeGenProjectErrorsExists_message;

    public static String ProjectDAAGenerator_CompositeGenSkipped_message;

    public static String ProjectDAAGenerator_daaGenerationProblem_message;

    public static String ProjectDAAGenerator_emptyComposite_message;

    public static String ProjectDAAGenerator_GeneratingDAAFor_message;

    public static String ProjectDAAGenerator_svarDesctiption_message;

    public static String ProjectSelectionPage_cleaningStagingFolder_message;

    public static String ProjectSelectionPage_DAAGenErrors_message;

    public static String ProjectSelectionPage_DAAGenErrors_title;

    public static String ProjectSelectionPage_daaNotGenerated_message;

    public static String ProjectSelectionPage_DeploymentPrepareError_message;

    public static String ProjectSelectionPage_NoSelection_message;

    public static String ProjectSelectionPage_ProjectWithErrors_message;

    public static String ProjectSelectionPage_SelectProject_longdesc;

    public static String ProjectSelectionPage_SelectProject_title;

    public static String ProjectSelectionPage_SupportedProjects_label;

    public static String DAAExportUtils_TargetPlatform_Default_Not_Used_Msg_Que;

    public static String DAAExportUtils_Default_TargetPlatform_Not_Used_Title;

    public static String DAAExportUtils_DO_NOT_ASK_AGAIN;

    public static String DAAExportUtils_TargetPlatform_InvalidError_Title;

    public static String DAAExportUtils_TargetPlatform_Invalid;

    public static String BpmRuntimeConfig_ThreadingPolicy_InvocationTimeout_default_value;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
