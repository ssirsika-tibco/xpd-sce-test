/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.brm.internal;

import org.eclipse.osgi.util.NLS;

/**
 * @author Jan Arciuchiewicz
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.n2.brm.internal.messages"; //$NON-NLS-1$

    public static String BRMComponentImplConstraint__wpDescriptorNotProvided_message;

    public static String BRMComponentImplConstraint_loadingProblem_message;

    public static String BRMComponentImplConstraint_versionNeeded_message;

    public static String BRMGenerator_GenerationException_message;

    public static String BRMGenerator_invalidWlf_message;

    public static String BRMGenerator_invalidWlfContent_message;

    public static String BRMGenerator_onlyOneWlfPerProject_message;

    public static String BRMGenerator_resourceSaveProblem_message;

    public static String BRMRequiresWPConstraint_WorkPresentationNotPresent_message;

    public static String WDMExportOperation_BuildinProjects_message;

    public static String WDMExportOperation_ExportException_message;

    public static String WDMExportOperation_Exporting_message;

    public static String WDMExportOperation_ExportingWDM_message;

    public static String WDMExportOperation_FileCopyProblem_message;

    public static String WDMExportOperation_FileExists_title;

    public static String WDMExportOperation_OverwriteDestination_message;

    public static String WDMExportOperation_ProjectHasErrors_message;

    public static String WDMExportOperation_Validation_message;

    public static String WorkDataModelExportWizard_WDMExportProblem_message;

    public static String WorkDataModelExportWizard_WDMExportProblem_title;

    public static String WorkDataModelExportWizard_WDMExportWizard_title;

    public static String WorkDataModelProjectSelectionPage_EmptySelection_message;

    public static String WorkDataModelProjectSelectionPage_SelectionWithErrors_message;

    public static String WorkSpecificationConstraint_descriptorLocationMissing_message;

    public static String WorkSpecificationConstraint_validationProblem_message;

    public static String WorkSpecificationConstraint_versionNeeded_message;

    public static String BRMCompositeContributor_CreateingComponents_message;

    public static String WorkDataModelFolder_title;

    public static String ExportsFolder_title;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
