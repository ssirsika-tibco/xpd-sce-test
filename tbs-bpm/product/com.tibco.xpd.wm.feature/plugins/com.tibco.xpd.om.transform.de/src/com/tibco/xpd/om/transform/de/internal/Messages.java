/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.transform.de.internal;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * <p>
 * <i>Created: 7 Apr 2008</i>
 * </p>
 * @author Jan Arciuchiewicz
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME = "com.tibco.xpd.om.transform.de.internal.messages"; //$NON-NLS-1$
    public static String DEComponentImplConstraint_modelNotProvided_message;
    public static String DEComponentImplConstraint_validationProblem_message;
    public static String DEComponentImplConstraint_versionNeeded_message;
    public static String DECompositeContributor_AddingOrgModels_messages;
    public static String DEExportWizard_errorDialog_title;
    public static String DEExportWizard_longdesc;
    public static String DEExportWizard_outFileNamePage_label;
    public static String DEExportWizard_outFileNamePage_title;
    public static String DEExportWizard_title;
    public static String DEExportWizard_validationProblems_error_message;
    public static String ExportToDEAction_exporting_label;
    public static String ExportToDEAction_exportMessage_title;
    public static String ExportToDEAction_exportToDE_menu;
    public static String ExportToDEAction_fileExists_message;
    public static String ExportToDEAction_fileWillBeIgnored_message;
    public static String OMModelBuilder_buildOMModelModule_desc;
    public static String QueryParticipantScriptDetailsProvider__scriptInfoProvider_desc0;
    public static String RQLScriptEditorSection_DescribeOrgModelParticQ_shortdesc;
    public static String RQLScriptEditorSection_OrganizationalModelScript;
    public static String RQLScriptEditorSection_SetParticipantQ_shortdesc;
    public static String RQLScriptEditorSection_SetParticipantQuery;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
