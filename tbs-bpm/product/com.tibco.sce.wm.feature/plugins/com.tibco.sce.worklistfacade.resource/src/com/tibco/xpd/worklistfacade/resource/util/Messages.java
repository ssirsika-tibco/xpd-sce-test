/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.worklistfacade.resource.util;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * 
 * @author aprasad
 * @since 26-Sep-2013
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.worklistfacade.resource.util.messages"; //$NON-NLS-1$

    public static String AddMoreFacadeWizard_AddProjectReferencePageTitle;

    public static String AddMoreFacadeWizard_Title;

    public static String AddProcDataMappedWithWIAttribToUserTaskResolution_Error_Extracting_ProcessDataFrom_Marker;

    public static String AddProjectReferenceWizardPage_AddProjectReferenceDescription;

    public static String AddProjectReferenceWizardPage_CyclicDependencyError;

    public static String AddProjectReferenceWizardPage_ErrorAddingReference;

    public static String AddProjectReferenceWizardPage_NoProjectSelected;

    public static String ProcessDataToWorkItemAliasMappingSection_WorkListFacade_Mapper_Header_Title2;

    public static String ProcessDataToWorkItemAliasMappingSection_More_Facade_Picker_Button_Label;

    public static String ProcessDataToWorkItemAliasMappingSection_More_Facade_Picker_Label;

    public static String WLFAttributeAliasConceptPath_UnresolvedReference;

    public static String WlfDocXsltGeneration_WlfModelContainer_title;

    public static String WorkItemAliasMappingProcessDataResolver_DataContextReferenceMessage;

    public static String WorkItemAttributeAliasContentProvider_No_Facade_To_Map_Message;

    public static String WorkItemAttributesContentProvider_LOAD_RESOURCE_ERROR;

    public static String WorkListFacadeActionBarContributor_GlobalActionHandlerNotFoundError;

    public static String WorkListFacadeAssetWizardPage_Default_WLF_Folder_Name;

    public static String WorkListFacadeAssetWizardPage_WLFAssetWizardPageDescription;

    public static String WorkListFacadeAssetWizardPage_WLFAssetWizardTitle;

    public static String WorkListFacadeAssetWizardPage_WorkListFacade_CreateDefaultFileName;

    public static String WorkListFacadeAssetWizardPage_CreateInitialModelMsg;

    public static String WorkListFacadeAssetWizardPage_FacadeDetailsMsg;

    public static String WorkListFacadeAssetWizardPage_Filename_Label;

    public static String WorkListFacadeCreationWizard_Description;

    public static String WorkListFacadeCreationWizard_Error;

    public static String WorkListFacadeCreationWizard_Title;

    public static String WorkListFacadeCreationWizardPage_WrongFolder_Msg;

    public static String WorkListFacadeEditor_Error_Saving_File;

    public static String WorkListFacadeEditor_File_Deleted_Error_Msg;

    public static String WorkListFacadeEditor_File_open_ElseWhere;

    public static String WorkListFacadeEditor_Invalid_File;

    public static String WorkListFacadeEditor_Invalid_Input;

    public static String WorkListFacadeEditorSection_Facade_Label_Title;

    public static String WorkListFacadeEditorSection_SetFacadeLabel_Command_Text;

    public static String WorkListFacadeEditorSection_WorkItemAttributes_Section_Header;

    public static String WorkListFacadeEditorUtil_CreateFacadeMessage;

    public static String WorkListFacadeEditorUtil_DefautlFacade_FileName;

    public static String WorkListFacadeEditorUtil_Error_Creating_Facade_Model;

    public static String WorkListFacadeEditorUtil_Error_Saving_Facade_Model;

    public static String WorkListFacadeEditorUtil_INVALID_FILE_ISSUE;

    public static String WorkListFacadeTable_1;

    public static String WorkListFacadeTable_Add_Alias_ToolTip;

    public static String WorkListFacadeTable_ATTRIBUTE_NAME_MISSING;

    public static String WorkListFacadeTable_ATTRIBUTE_TYPE_NOT_SET;

    public static String WorkListFacadeTable_AttributeType_DATETIME;

    public static String WorkListFacadeTable_AttributeType_DECIMAL;

    public static String WorkListFacadeTable_AttributeType_INTEGER;

    public static String WorkListFacadeTable_AttributeType_TEXT;

    public static String WorkListFacadeTable_Create_Alias_Label;

    public static String WorkListFacadeTable_DELETE_Action;

    public static String WorkListFacadeTable_DELETE_AttributeAliases;

    public static String WorkListFacadeTable_DisplayLabel_Column;

    public static String WorkListFacadeTable_Name_Column;

    public static String WorkListFacadeTable_Set_Display_Label_Command_Title;

    public static String WorkListFacadeTable_Type_Column;

    public static String WorkListFacadeSection_AttributeLabel_value;

    public static String WorkListFacadeSection_AddLabel;

    public static String ProcessDataToWIAttributeMappingCommandFactory_ADD_MAPPING_CMD_LABEL;

    public static String ResourceCopier_CreatingStylesheetProgressMonitor_msg;

    public static String ResourceCopier_FailedCreatingFolderError_msg;

    public static String ResourceCopier_UnableToCreateFolderError_msg;

    public static String UserTaskDataAssociationValidation_ProcessDataWIAttributeMappingTypeDescription;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
