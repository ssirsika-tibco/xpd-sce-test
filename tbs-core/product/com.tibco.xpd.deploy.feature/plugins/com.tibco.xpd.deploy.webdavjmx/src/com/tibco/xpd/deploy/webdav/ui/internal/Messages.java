/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.deploy.webdav.ui.internal;

import org.eclipse.osgi.util.NLS;

/**
 * Localised display strings.
 * @author TIBCO Software, Inc.
 */
public final class Messages extends NLS {
    // CHECKSTYLE-CONSTANTS:OFF

    private static final String BUNDLE_NAME = "com.tibco.xpd.deploy.webdav.ui.internal.messages";
    public static String WorkspaceFileDeployWizard_window_title;
    public static String WorkspaceFileDeployWizard_deploy_label;
    public static String WorkspaceFileDeployWizard_preparing_label;
    public static String WorkspaceFileDeployWizard_exception_msg;
    public static String WorkspaceResourceSelectionWizardPage_CollapseAll_button;
    public static String WorkspaceResourceSelectionWizardPage_DeselectAll_button;
    public static String WorkspaceResourceSelectionWizardPage_ExpandAll_button;
    public static String WorkspaceResourceSelectionWizardPage_SelectAll_button;
    public static String WorkspaceResourceSelectionWizardPage_WebDAVDeployment_ResourceNotSelectedError_message;
    public static String WorkspaceResourceSelectionWizardPage_WebDAVDeployment_shortdesc;
    public static String WorkspaceResourceSelectionWizardPage_WebDAVDeployment_title;
    public static String WorkspaceResourceSelectionWizardPage_WebDAVSpecialFolderName;
    public static String SelectModulesPage_super_label;
    public static String SelectModulesPage_page_title;
    public static String SelectModulesPage_page_description;
    public static String SelectModulesPage_file_selection_label;
    public static String SelectModulesPage_file_label;
    public static String SelectModulesPage_browse_button_label;
    public static String SelectModulesPage_resource_selection_label;
    public static String SelectModulesPage_selection_empty_label;
    public static String SelectModulesPage_file_does_not_exist_label;
    public static String DeploymentConfig_webdav_root_folder;
    public static String WebDAVConnection_0;
    public static String WebDAVConnection_1;
    public static String WebDAVConnection_10;
    public static String WebDAVConnection_11;
    public static String WebDAVConnection_4;
    public static String WebDAVConnection_5;
    public static String WebDAVConnection_6;
    public static String WebDAVConnection_7;
    public static String WebDAVConnection_8;
    public static String WebDAVConnection_9;
    public static String WebDAVConnection_published_state_label;
    public static String WebDAVConnection_delete_action_label;
    public static String WebDAVConnection_connectionAttempFailed;
    public static String WebDAVConnection_create_root_folder_error;
    public static String WebDAVConnection_deploy_start_message;
    public static String WebDAVConnection_create_file_message;
    public static String WebDAVConnection_update_file_message;
    public static String WebDAVConnection_deploy_error_message;
    public static String WebDAVConnection_deploy_write_access_error_message;
    public static String WebDAVConnection_create_folder_generic_error;
    public static String WebDAVConnection_create_folder_write_access_error;
    public static String WebDAVConnection_delete_message;
    public static String WebDAVConnection_delete_error_message;
    public static String WebDAVConnection_delete_write_access_error_message;
    public static String WebDAVConnection_not_module_container_message;
    public static String WebDAVConnection_server_connection_error_message;
    public static String WebDAVConnection_server_unknownhost_error_message;
    public static String WebDAVConnection_server_auth_error_message;
    public static String WebDAVConnection_server_write_access_error_message;
    public static String WebDAVConnection_webdav_app_error_message;
    public static String SelectWebDAVLocationPage_label;
    public static String SelectWebDAVLocationPage_title;
    public static String SelectWebDAVLocationPage_desc;
    public static String SelectWebDAVLocationPage_targetLocation_label;
    public static String SelectWebDAVLocationPage_file_label;
    public static String SelectWebDAVLocationPage_browseServer_label;
    public static String SelectWebDAVLocationPage_selectTargetLocation_label;
    public static String BaseJmxConnection_ConnectionFailedOutOfDatePasswordMessage;
    public static String BaseJmxConnection_unsupportedJmxConnector_msg;
    public static String BaseJmxConnection_ConnectionFailedIncorrectParametersMessage;
    public static String BaseJmxConnection_ConnectionFailedIOExceptionMessage;
    public static String BaseJmxConnection_RepoNeedsSiteUrlMessage;
    public static String BaseJmxConnection_RepoNeedsUsernameMessage;
    public static String BaseJmxConnection_RepoNeedsPasswordMessage;
    public static String BaseJmxConnection_DeployingMessage;
    public static String BaseJmxConnection_DeletingMessage;
    public static String BaseJmxConnection_UndeployingMessage;
    public static String BaseJmxConnection_RenotifyingMessage;
    public static String BaseJmxConnection_UnDeployMessage;
    public static String BaseJmxConnection_RenotifyMessage;
    public static String BaseJmxConnection_DeleteMessage;
    public static String BaseJmxConnection_ElementNotCorrectType;
    public static String BaseJmxDeploymentRecord_FailedToCreateRecord_message;
    public static String BaseJmxDeploymentRecord_FailedToRemoveRecord_message;

    // CHECKSTYLE-CONSTANTS:ON

    public static String GenericWebDAVConnection_InvalidURISyntax_message;
    public static String GenericWebDAVConnection_InvalidURL_message;
    public static String GenericWebDAVConnection_MailformedURL_message2;
    public static String GenericWebDAVConnection_PathReady_message;
    public static String Utils_correspondingIResourceMissing_message;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    /** Private ctor prevents instantiation. */
    private Messages() {
    }
}
