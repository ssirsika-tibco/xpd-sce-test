/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.omdoc.internal;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * <p>
 * <i>Created: 30 Apr 2009</i>
 * </p>
 * 
 * @author glewis
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME = "com.tibco.xpd.om.omdoc.internal.messages"; //$NON-NLS-1$
    public static String DocExportWizard_errorDialog_title;
    public static String DocExportWizard_longdesc;
    public static String DocExportWizard_outFileNamePage_label;
    public static String DocExportWizard_outFileNamePage_title;
    public static String DocExportWizard_title;
    public static String DocExportWizard_ExportBOMWizard_errorExportingDoc;
    public static String DocExportWizard_exportFolderName;
    public static String ExportDocAction_exporting_label;
    public static String ExportDocAction_exportMessage_title;
    public static String ExportDocAction_exportDoc_menu;
    public static String ExportDocAction_fileExists_message;
    public static String ExportDocAction_fileWillBeIgnored_message;
    public static String AbstractDocExportWizard_exportFolder_label;
    public static String AbstractDocExportWizard_default_message;
    public static String AbstractDocExportWizard_exportErrDialog_title;
    public static String AbstractDocExportWizard_exportErrDialog_message;
    public static String AbstractDocExportWizard_exportingTask_title;
    public static String AbstractDocExportWizard_destFileExistsDlg_message;
    public static String AbstractDocExportWizard_destFileExistsDlg_title;
    public static String DocExportWizardPageIO_selectFolder_title;
    public static String DocExportWizardPageIO_selectFolder_message;
    public static String DocExportWizardPageIO_selectFileToExport_shortdesc;
    public static String DocExportWizardPageIO_selectOutputPath_shortdesc;
    public static String DocExportWizardPageIO_pathNotFoundErr_shortdesc;
    public static String DocExportWizardPageIO_expandAll_label;
    public static String DocExportWizardPageIO_collapseAll_label;
    public static String DocExportWizardPageIO_selectAll_label;
    public static String DocExportWizardPageIO_unselectAll_label;
    public static String DocExportWizardPageIO_destinationGroup_label;
    public static String DocExportWizardPageIO_project_label;
    public static String DocExportWizardPageIO_location_label;
    public static String DocExportWizardPageIO_path_label;
    public static String DocExportWizardPageIO_browse_label;
    public static String OMDocGen_GeneratorRootFolderName;
    public static String OMDocGen_OmModelContainer_title;
    public static String ResourceCopier_FailedToCreateFolderErr_message;
    public static String ResourceCopier_unableToCreateFolderErr_message;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
