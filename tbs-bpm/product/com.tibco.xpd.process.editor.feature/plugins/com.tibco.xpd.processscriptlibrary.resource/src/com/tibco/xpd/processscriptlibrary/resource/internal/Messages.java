/*
 * Copyright (c) 2004-2023. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.processscriptlibrary.resource.internal;

import org.eclipse.osgi.util.NLS;

/**
 *
 *
 * @author ssirsika
 * @since 06-Dec-2023
 */
public class Messages extends NLS
{
	private static final String	BUNDLE_NAME	= "com.tibco.xpd.processscriptlibrary.resource.internal.messages";	//$NON-NLS-1$

	public static String		ProcessScriptLibraryAssetConfigurator_DefaultFolderName;

	public static String		ProcessScriptLibraryAssetWizardPage_title;

	public static String		ProcessScriptLibraryAssetWizardPage_desc;

	public static String		ProcessScriptLibrary_DescriptorDetails_label;

	public static String		ProcessScriptLibraryWizardPage_CreateInitialPsl_label;

	public static String		ProcessScriptLibraryWizardPage_FilaName_label;

	public static String		ProcessScriptLibrary_DefaultPslFile_name;

	public static String		ProcessScriptLibraryCreationWizard_Title;

	public static String		ProcessScriptLibraryCreationWizard_Description;

	public static String		ProcessScriptLibraryCreationWizard_Error;

	public static String		PSLPropertyPanel_ScriptFunctionName_label;

	public static String		PSLPropertyPanel_PAGEDESCRIPTION;

	public static String		PSLPropertyPanel_UseIn_Label;

	public static String		PSLPropertyPanel_UseIn_ToolTip;

	public static String		PSLPropertyPanel_Any_Label;

	public static String		PSLPropertyPanel_ProcessManager_Label;

	public static String		PSLPropertyPanel_WorkManager_Label;

	public static String ProcessScriptLibraryEditor_dontAskInFuture;

	public static String ProcessScriptLibraryEditor_EditorInpurError;

	public static String ProcessScriptLibraryEditor_ReadOnly;

	public static String ProcessScriptLibraryEditor_SaveDialogMessage;

	public static String ProcessScriptLibraryEditor_SaveDialogTitle;

	public static String		ProcessScriptLibraryEditorUtil_CreatePSLMessage;

	public static String		ProcessScriptLibraryEditorUtil_Error_Saving_PSL_Model;

	public static String		ProcessScriptLibraryEditorUtil_Error_Creating_PSL_Model;

	public static String		ProcessScriptLibraryEditorUtil_INVALID_FILE_ISSUE;

	public static String		ProcessScriptLibraryEditorUtil_DefautlFacade_FileName;

	public static String		ProcessScriptLibraryCreationWizardPage_WrongFolder_Msg;

	public static String ProcessScriptLibraryPackageEditorFormPage_CreatePSLFnDesc;

	public static String ProcessScriptLibraryPackageEditorFormPage_PSLEditorOpenError;

	public static String PSLAddChildActionProvider_AddChildMenu;

	public static String PSLAddChildActionProvider_ScriptFnMenu;

	public static String PslEditorUtil_CaseRef_label;

	public static String PslEditorUtil_NewScriptFnCommandTitle;

	public static String PslEditorUtil_UnresolvedRefMsg;

	public static String		PSLPropertyPanel_Parameters_Label;
	
	public static String		PSLPropertyPanel_ReturnType_Label;

	public static String		PSLSection_AddParameterButton_tooltip;

	public static String		PSLPropertiesSection_AddLabel;

	public static String		PSLPropertiesSection_DeleteLabel;

	public static String		PSLSection_ParameterName_value;

	public static String		PSLSection_createParameter_menu;

	public static String		PSLSection_DeleteParameterButton_tooltip;

	public static String		ScriptFunctionGroup_title;

	public static String		PSLPropertySection_NameEmpty;

	public static String		PSLElements_invalidNameErrorMessage;

	public static String		PSLPropertySection_NameMustBeUnique;

	static
	{
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages()
	{
	}
}
