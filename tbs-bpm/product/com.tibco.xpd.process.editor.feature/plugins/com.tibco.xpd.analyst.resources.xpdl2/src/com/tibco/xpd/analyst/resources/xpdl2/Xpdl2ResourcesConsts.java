/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2;

import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.xpdl2.PublicationStatusType;

/**
 * Constants used in the plugin
 * 
 * @author njpatel
 * 
 */
public class Xpdl2ResourcesConsts {

    /**
     * Project Explorer Viewer ID
     */
    public static final String PROJECT_EXPLORER_VIEWER_ID =
            "org.eclipse.ui.navigator.ProjectExplorer"; //$NON-NLS-1$

    public static final String WIZARD_NEW_PACKAGE =
            "icons/wizards/NewPackageWizard.png"; //$NON-NLS-1$

    public static final String WIZARD_NEW_PROCESS =
            "icons/wizards/ProcessWizardBusiness.png"; //$NON-NLS-1$

    public static final String WIZARD_NEW_PAGEFLOW_PROCESS =
            "icons/wizards/ProcessWizardPageflow.png"; //$NON-NLS-1$

    public static final String WIZARD_NEW_BUSINESS_SERVICE =
            "icons/wizards/BusinessServiceWizard.png"; //$NON-NLS-1$

    public static final String WIZARD_NEW_CASE_SERVICE =
            "icons/wizards/CaseServiceWizard.png"; //$NON-NLS-1$

    public static final String WIZARD_NEW_SERVICE_PROCESS =
            "icons/wizards/ServiceProcessWizard.png"; //$NON-NLS-1$

    public static final String WIZARD_NEW_PARTICIPANT =
            "icons/wizards/ParticipantWizard.png"; //$NON-NLS-1$

    public static final String WIZARD_NEW_DATAFIELD =
            "icons/wizards/DataFieldWizard.png"; //$NON-NLS-1$

    public static final String WIZARD_NEW_CORRELATIONDATAFIELD =
            "icons/wizards/CorrelationDataWizard.png"; //$NON-NLS-1$

    public static final String WIZARD_NEW_TYPEDECLARATION =
            "icons/wizards/Type Declaration nxn n p.png"; //$NON-NLS-1$

    public static final String WIZARD_NEW_FORMAL_PARAMETER =
            "icons/wizards/FormalParameter.png"; //$NON-NLS-1$

    public static final String WIZARD_NEW_APPLICATION =
            "icons/wizards/ApplicationWizard.png"; //$NON-NLS-1$

    /**
     * extension of the xpdl file.
     */
    public static final String XPDL_EXTENSION = "xpdl";//$NON-NLS-1$

    /**
     * extension of dflow file
     * */
    public static final String DFLOW_EXTENSION = "dflow"; //$NON-NLS-1$

    /**
     * 
     */
    public static final String PROCESSES_SPECIAL_FOLDER_KIND = "processes"; //$NON-NLS-1$

    public static final String DECISIONS_SPECIAL_FOLDER_KIND = "decisions"; //$NON-NLS-1$

    public static final String FORMS_SPECIAL_FOLDER_KIND = "forms"; //$NON-NLS-1$

    public static final String FORMS_FILE_EXTENSION = "form"; //$NON-NLS-1$

    // SID NOTE: This should n't really be here as I wanted to have task
    // libraries completely separate HOWEVER, certain things like teh New Type
    // Declaration wizard demand that the type declaration is added to a file
    // that is in Profecss Package special folders.
    //
    // So rather than duplicating / abstracting everything, I will allow a
    // little 'knowledge' of task libraries seep into process editor/analyst -
    // but just simple stuff like knowing the special folder stuff.
    public static final String TASK_LIBRARY_SPECIAL_FOLDER_KIND = "wm"; //$NON-NLS-1$

    public static final String TASKS_EXTENSION = "tasks"; //$NON-NLS-1$

    public static final String DECISION_FLOW_EXTENSION = "dflow"; //$NON-NLS-1$

    /**
     * Image locations
     */
    public static final String ICON_PACKAGE = "/icons/packageFile.png"; //$NON-NLS-1$

    public static final String ICON_PARTICIPANT = "/icons/Participant.gif"; //$NON-NLS-1$

    public static final String ICON_PARTICIPANT_GROUP =
            "/icons/ParticipantGroup.png"; //$NON-NLS-1$

    public static final String ICON_TYPEDECLARATION =
            "/icons/TypeDeclaration.gif"; //$NON-NLS-1$

    public static final String ICON_PROCESS = "/icons/ProcessBusiness.png"; //$NON-NLS-1$

    public static final String ICON_PAGEFLOW_PROCESS =
            "/icons/ProcessPageflow.png"; //$NON-NLS-1$

    public static final String ICON_BUSINESS_SERVICE_PAGEFLOW_PROCESS =
            "/icons/ProcessBusinessService.png"; //$NON-NLS-1$

    public static final String ICON_CASE_SERVICE_PROCESS =
            "/icons/CaseService.png"; //$NON-NLS-1$

    public static final String ICON_SERVICE_PROCESS =
            "/icons/ServiceProcess.png"; //$NON-NLS-1$

    public static final String ICON_PROCESSINTERFACE =
            "/icons/processInterface.gif"; //$NON-NLS-1$

    public static final String ICON_SERVICEPROCESSINTERFACE =
            "/icons/ServiceProcessInterface.png"; //$NON-NLS-1$

    public static final String ICON_INTERFACE_STARTEVENT =
            "/icons/interfaceStartEvent.png"; //$NON-NLS-1$

    public static final String ICON_INTERFACE_INTERMEDIATEEVENT =
            "/icons/interfaceIntermediateEvent.png"; //$NON-NLS-1$

    public static final String ICON_INTERFACE_ERROREVENT =
            "icons/ErrorMethodCustom.png"; //$NON-NLS-1$

    public static final String ICON_FORMALPARAMETER =
            "/icons/FormalParameter.gif"; //$NON-NLS-1$

    public static final String ICON_PROCESS_SPECIAL_FOLDER =
            "icons/packagefolder.PNG"; //$NON-NLS-1$

    public static final String ICON_FIELDTOPARAM = "/icons/FieldToParam.gif"; //$NON-NLS-1$

    public static final String ICON_PARAMTOFIELD = "/icons/ParamToField.gif"; //$NON-NLS-1$

    public static final String ICON_DATATOCORRELATION =
            "/icons/DataToCorrelation.png"; //$NON-NLS-1$

    public static final String ICON_PROCESS_FORMAL_PARAM_HEADER =
            "icons/FormalParamInOut.png"; //$NON-NLS-1$

    public static final String ICON_INTERFACE_FORMAL_PARAM_HEADER =
            "icons/InterfaceParamInOut.png"; //$NON-NLS-1$

    public static final String ICON_INTERFACE_FORMAL_PARAM_INOUT =
            "icons/InterfaceParamInOut.png"; //$NON-NLS-1$

    public static final String ICON_INTERFACE_FORMAL_PARAM_IN =
            "icons/InterfaceParamIn.png"; //$NON-NLS-1$

    public static final String ICON_INTERFACE_FORMAL_PARAM_OUT =
            "icons/InterfaceParamOut.png"; //$NON-NLS-1$

    public static final String IMG_TOOLBAR_HORIZONTAL_LAYOUT =
            "icons/th_horizontal.gif"; //$NON-NLS-1$

    public static final String IMG_TOOLBAR_VERTICAL_LAYOUT =
            "icons/th_vertical.gif"; //$NON-NLS-1$

    public static final String ICON_DATAFIELD = "/icons/DataField.gif"; //$NON-NLS-1$

    public static final String IMG_DATAFIELD_STRING =
            "icons/DataFieldString.png"; //$NON-NLS-1$

    public static final String IMG_DATAFIELD_FLOAT = "icons/DataFieldFloat.png"; //$NON-NLS-1$

    public static final String IMG_DATAFIELD_INT = "icons/DataFieldInt.png"; //$NON-NLS-1$

    public static final String IMG_DATAFIELD_REFERENCE =
            "icons/DataFieldReference.png"; //$NON-NLS-1$

    public static final String IMG_DATAFIELD_DATETIME =
            "icons/DataFieldDateTime.png"; //$NON-NLS-1$

    public static final String IMG_DATAFIELD_BOOLEAN =
            "icons/DataFieldBoolean.png"; //$NON-NLS-1$

    public static final String IMG_DATAFIELD_PERFORMER =
            "icons/DataFieldPerformer.png"; //$NON-NLS-1$

    public static final String IMG_DATAFIELD_DECLAREDTYPE =
            "icons/DataFieldDeclaredType.png"; //$NON-NLS-1$

    public static final String IMG_DATAFIELD_EXTERNALREFERENCE =
            "icons/DataFieldExtRef.png"; //$NON-NLS-1$

    public static final String IMG_DATAFIELD_CASEREFERENCETYPE =
            "icons/DataFieldCaseRefType.png"; //$NON-NLS-1$

    public static final String ICON_CORRELATIONDATA =
            "/icons/CorrelationDataField.png"; //$NON-NLS-1$

    public static final String IMG_CORRELATIONDATAFIELD_STRING =
            "icons/CorrelationDataFieldString.png"; //$NON-NLS-1$

    public static final String IMG_CORRELATIONDATAFIELD_FLOAT =
            "icons/CorrelationDataFieldFloat.png"; //$NON-NLS-1$

    public static final String IMG_CORRELATIONDATAFIELD_INT =
            "icons/CorrelationDataFieldInt.png"; //$NON-NLS-1$

    public static final String IMG_CORRELATIONDATAFIELD_REFERENCE =
            "icons/CorrelationDataFieldReference.png"; //$NON-NLS-1$

    public static final String IMG_CORRELATIONDATAFIELD_DATETIME =
            "icons/CorrelationDataFieldDateTime.png"; //$NON-NLS-1$

    public static final String IMG_CORRELATIONDATAFIELD_BOOLEAN =
            "icons/CorrelationDataFieldBoolean.png"; //$NON-NLS-1$

    public static final String IMG_CORRELATIONDATAFIELD_PERFORMER =
            "icons/CorrelationDataFieldPerformer.png"; //$NON-NLS-1$

    public static final String IMG_CORRELATIONDATAFIELD_DECLAREDTYPE =
            "icons/CorrelationDataFieldDeclaredType.png"; //$NON-NLS-1$

    public static final String IMG_CORRELATIONDATAFIELD_EXTERNALREFERENCE =
            "icons/CorrelationDataFieldExtRef.png"; //$NON-NLS-1$

    public static final String IMG_FORMALPARAM_IN = "icons/FormalParamIn.png"; //$NON-NLS-1$

    public static final String IMG_FORMALPARAM_OUT = "icons/FormalParamOut.png"; //$NON-NLS-1$

    public static final String IMG_FORMALPARAM_INOUT =
            "icons/FormalParamInOut.png"; //$NON-NLS-1$

    public static final String IMG_START_EVENT_ICON =
            "icons/picker/event_start_16.png"; //$NON-NLS-1$

    public static final String IMG_INTERMEDIATE_EVENT_ICON =
            "icons/picker/event_intermediate_16.png"; //$NON-NLS-1$

    public static final String IMG_END_EVENT_ICON =
            "icons/picker/event_end_16.png"; //$NON-NLS-1$

    public static final String IMG_CANCEL_INTERMEDIATE_EVENT_ICON =
            "icons/picker/event_intermediate_cancel_16.png"; //$NON-NLS-1$

    public static final String IMG_CANCEL_END_EVENT_ICON =
            "icons/picker/event_end_cancel_16.png"; //$NON-NLS-1$

    public static final String IMG_CONPENSATION_INTERMEDIATE_EVENT_ICON =
            "icons/picker/event_intermediate_compensation_16.png"; //$NON-NLS-1$

    public static final String IMG_CONPENSATION_END_EVENT_ICON =
            "icons/picker/event_end_compensation_16.png"; //$NON-NLS-1$

    public static final String IMG_ERROR_INTERMEDIATE_EVENT_ICON =
            "icons/picker/event_intermediate_error_16.png"; //$NON-NLS-1$

    public static final String IMG_ERROR_END_EVENT_ICON =
            "icons/picker/event_end_error_16.png"; //$NON-NLS-1$

    public static final String IMG_ERROR_EVENT_ICON12 =
            "icons/picker/eventErrorThrow_12.png"; //$NON-NLS-1$

    public static final String IMG_LINK_START_EVENT_ICON =
            "icons/picker/event_start_link_16.png"; //$NON-NLS-1$

    public static final String IMG_LINK_INTERMEDIATE_EVENT_ICON =
            "icons/picker/event_intermediate_link_16.png"; //$NON-NLS-1$

    public static final String IMG_LINK_END_EVENT_ICON =
            "icons/picker/event_end_link_16.png"; //$NON-NLS-1$

    public static final String IMG_MESSAGE_START_EVENT_ICON =
            "icons/picker/event_start_message_16.png"; //$NON-NLS-1$

    public static final String IMG_MESSAGE_INTERMEDIATE_EVENT_ICON =
            "icons/picker/event_intermediate_message_16.png"; //$NON-NLS-1$

    public static final String IMG_MESSAGE_END_EVENT_ICON =
            "icons/picker/event_end_message_16.png"; //$NON-NLS-1$

    public static final String IMG_MULTIPLE_START_EVENT_ICON =
            "icons/picker/event_start_multiple_16.png"; //$NON-NLS-1$

    public static final String IMG_MULTIPLE_INTERMEDIATE_EVENT_ICON =
            "icons/picker/event_intermediate_multiple_16.png"; //$NON-NLS-1$

    public static final String IMG_MULTIPLE_END_EVENT_ICON =
            "icons/picker/event_end_multi_16.png"; //$NON-NLS-1$

    public static final String IMG_RULE_START_EVENT_ICON =
            "icons/picker/event_start_rule_16.png"; //$NON-NLS-1$

    public static final String IMG_RULE_INTERMEDIATE_EVENT_ICON =
            "icons/picker/event_intermediate_rule_16.png"; //$NON-NLS-1$

    public static final String IMG_TIMER_START_EVENT_ICON =
            "icons/picker/event_start_timer_16.png"; //$NON-NLS-1$

    public static final String IMG_TIMER_INTERMEDIATE_EVENT_ICON =
            "icons/picker/event_intermediate_timer_16.png"; //$NON-NLS-1$

    public static final String IMG_TERMINATE_END_EVENT_ICON =
            "icons/picker/event_end_terminate_16.png"; //$NON-NLS-1$

    public static final String IMG_TASK_ICON = "icons/picker/task_16.png"; //$NON-NLS-1$

    public static final String IMG_TASK_USER_ICON =
            "icons/picker/task_user_16.png"; //$NON-NLS-1$

    public static final String IMG_TASK_MANUAL_ICON =
            "icons/picker/task_manual_16.png"; //$NON-NLS-1$

    public static final String IMG_TASK_SERVICE_ICON =
            "icons/picker/task_service_16.png"; //$NON-NLS-1$

    public static final String IMG_TASK_SCRIPT_ICON =
            "icons/picker/task_script_16.png"; //$NON-NLS-1$

    public static final String IMG_TASK_SEND_ICON =
            "icons/picker/task_send_16.png"; //$NON-NLS-1$

    public static final String IMG_TASK_RECEIVE_ICON =
            "icons/picker/task_receive_16.png"; //$NON-NLS-1$

    public static final String IMG_TASK_REFERENCE_ICON =
            "icons/picker/task_reference_16.png"; //$NON-NLS-1$

    public static final String IMG_SUBPROC_ICON = "icons/picker/subproc_16.png"; //$NON-NLS-1$

    public static final String IMG_SUBPROC_ENBEDDED_ICON =
            "icons/picker/subproc_embedded_16.png"; //$NON-NLS-1$

    public static final String IMG_EVENT_SUBPROC_ENBEDDED_ICON =
            "icons/picker/eventSubProcess_16.png"; //$NON-NLS-1$

    public static final String IMG_GATEWAY_XOR_DATA_ICON =
            "icons/picker/gateway_XORData_16.png"; //$NON-NLS-1$

    public static final String IMG_GATEWAY_XOR_EVENT_ICON =
            "icons/picker/gateway_XOREvent_16.png"; //$NON-NLS-1$

    public static final String IMG_GATEWAY_OR_ICON =
            "icons/picker/gateway_OR_16.png"; //$NON-NLS-1$

    public static final String IMG_GATEWAY_COMPLEX_ICON =
            "icons/picker/gateway_Complex_16.png"; //$NON-NLS-1$

    public static final String IMG_GATEWAY_PARALLEL_ICON =
            "icons/picker/gateway_parallel_16.png"; //$NON-NLS-1$

    public static final String IMG_ADD = "icons/add.gif"; //$NON-NLS-1$

    public static final String IMG_REMOVE = "icons/remove.gif"; //$NON-NLS-1$

    public static final String IMG_ERRORCODE_PICKER =
            "icons/ErrorCodePickerDialog.png"; //$NON-NLS-1$

    public static final String PAGEFLOW_FRAGMENT_IMG =
            "icons/wizards/fragment.png"; //$NON-NLS-1$

    public static final String ICON_TASK_FOLDER = "icons/taskFolder.png"; //$NON-NLS-1$

    public static final String ICON_SEQUENCEFLOW_FOLDER =
            "icons/sequenceFlowFolder.png"; //$NON-NLS-1$

    public static final String ICON_MESSAGEFLOW_FOLDER =
            "icons/messageFlowFolder.png"; //$NON-NLS-1$

    public static final String ICON_ASSOCIATIONS_FOLDER =
            "icons/associationFolder.png"; //$NON-NLS-1$

    public static final String ICON_PACKAGE_FILE = "icons/pkgFile.png"; //$NON-NLS-1$

    public static final String IMG_POOL = "icons/picker/pool_16.png"; //$NON-NLS-1$

    public static final String IMG_LANE = "icons/picker/lane_16.png"; //$NON-NLS-1$

    public static final String IMG_DATAOBJECT =
            "icons/picker/data_object_16.png"; //$NON-NLS-1$

    public static final String ICON_WEB_BROWSER = "icons/web_browser.gif"; //$NON-NLS-1$

    public static final String IMG_START_EVENT_SIGNAL_ICON =
            "icons/picker/event_start_signal_16.png"; //$NON-NLS-1$

    public static final String IMG_END_EVENT_SIGNAL_ICON =
            "icons/picker/event_end_signal_16.png"; //$NON-NLS-1$

    public static final String[] IMAGES = new String[] {

    WIZARD_NEW_PACKAGE, WIZARD_NEW_PROCESS, WIZARD_NEW_PAGEFLOW_PROCESS,
            WIZARD_NEW_BUSINESS_SERVICE, WIZARD_NEW_CASE_SERVICE,
            WIZARD_NEW_SERVICE_PROCESS, WIZARD_NEW_PARTICIPANT,
            WIZARD_NEW_DATAFIELD, WIZARD_NEW_CORRELATIONDATAFIELD,
            WIZARD_NEW_TYPEDECLARATION, WIZARD_NEW_FORMAL_PARAMETER,
            WIZARD_NEW_APPLICATION,

            IMG_POOL, IMG_LANE, IMG_DATAOBJECT, ICON_PACKAGE_FILE,
            ICON_PACKAGE, ICON_DATAFIELD, ICON_CORRELATIONDATA,
            ICON_PARTICIPANT, ICON_PARTICIPANT_GROUP, ICON_TYPEDECLARATION,
            ICON_PROCESS, ICON_PAGEFLOW_PROCESS,
            ICON_BUSINESS_SERVICE_PAGEFLOW_PROCESS, ICON_CASE_SERVICE_PROCESS,
            ICON_SERVICE_PROCESS, ICON_FORMALPARAMETER, ICON_PROCESSINTERFACE,
            ICON_SERVICEPROCESSINTERFACE, ICON_INTERFACE_INTERMEDIATEEVENT,
            ICON_INTERFACE_STARTEVENT, ICON_INTERFACE_ERROREVENT,
            ICON_FIELDTOPARAM, ICON_PARAMTOFIELD, ICON_PROCESS_SPECIAL_FOLDER,
            ICON_PROCESS_FORMAL_PARAM_HEADER,
            ICON_INTERFACE_FORMAL_PARAM_HEADER,
            ICON_INTERFACE_FORMAL_PARAM_INOUT, ICON_INTERFACE_FORMAL_PARAM_IN,
            ICON_INTERFACE_FORMAL_PARAM_OUT, IMG_TOOLBAR_HORIZONTAL_LAYOUT,
            IMG_TOOLBAR_VERTICAL_LAYOUT, IMG_DATAFIELD_STRING,
            IMG_DATAFIELD_FLOAT, IMG_DATAFIELD_INT, IMG_DATAFIELD_REFERENCE,
            IMG_DATAFIELD_DATETIME, IMG_DATAFIELD_BOOLEAN,
            IMG_DATAFIELD_PERFORMER, IMG_DATAFIELD_DECLAREDTYPE,
            IMG_DATAFIELD_EXTERNALREFERENCE, IMG_DATAFIELD_CASEREFERENCETYPE,
            IMG_CORRELATIONDATAFIELD_STRING, IMG_CORRELATIONDATAFIELD_FLOAT,
            IMG_CORRELATIONDATAFIELD_INT, IMG_CORRELATIONDATAFIELD_REFERENCE,
            IMG_CORRELATIONDATAFIELD_DATETIME,
            IMG_CORRELATIONDATAFIELD_BOOLEAN,
            IMG_CORRELATIONDATAFIELD_PERFORMER,
            IMG_CORRELATIONDATAFIELD_DECLAREDTYPE,
            IMG_CORRELATIONDATAFIELD_EXTERNALREFERENCE,

            IMG_FORMALPARAM_IN, IMG_FORMALPARAM_OUT, IMG_FORMALPARAM_INOUT,
            IMG_START_EVENT_ICON, IMG_INTERMEDIATE_EVENT_ICON,
            IMG_END_EVENT_ICON, IMG_CANCEL_INTERMEDIATE_EVENT_ICON,
            IMG_CANCEL_END_EVENT_ICON, IMG_ERROR_INTERMEDIATE_EVENT_ICON,
            IMG_ERROR_END_EVENT_ICON, IMG_LINK_START_EVENT_ICON,
            IMG_MESSAGE_INTERMEDIATE_EVENT_ICON, IMG_MESSAGE_END_EVENT_ICON,
            IMG_MULTIPLE_START_EVENT_ICON,
            IMG_MULTIPLE_INTERMEDIATE_EVENT_ICON, IMG_MULTIPLE_END_EVENT_ICON,
            IMG_RULE_START_EVENT_ICON, IMG_RULE_INTERMEDIATE_EVENT_ICON,
            IMG_TIMER_START_EVENT_ICON, IMG_TIMER_INTERMEDIATE_EVENT_ICON,
            IMG_TASK_ICON, IMG_TASK_USER_ICON, IMG_TASK_MANUAL_ICON,
            IMG_TASK_SERVICE_ICON, IMG_TASK_SCRIPT_ICON, IMG_TASK_SEND_ICON,
            IMG_TASK_RECEIVE_ICON, IMG_TASK_REFERENCE_ICON, IMG_SUBPROC_ICON,
            IMG_SUBPROC_ENBEDDED_ICON, IMG_GATEWAY_XOR_DATA_ICON, IMG_ADD,
            IMG_REMOVE, IMG_ERRORCODE_PICKER, IMG_ERROR_EVENT_ICON12,
            PAGEFLOW_FRAGMENT_IMG,

            ICON_TASK_FOLDER, ICON_SEQUENCEFLOW_FOLDER,
            ICON_ASSOCIATIONS_FOLDER, ICON_MESSAGEFLOW_FOLDER,
            ICON_WEB_BROWSER,

            IMG_START_EVENT_SIGNAL_ICON, IMG_END_EVENT_SIGNAL_ICON, };

    /**
     * Default values to add to the new wizards and templates
     */
    public static final String DEFAULT_PACKAGE_FILENAME_EXT = ".xpdl"; //$NON-NLS-1$

    public static final String DEFAULT_PACKAGE_NAME =
            Messages.Xpdl2ResourcesConsts_DefaultPackageName_shortdesc;

    public static final String DEFAULT_PROCESS_NAME =
            Messages.Xpdl2ResourcesConsts_DefaultProcessName_shortdesc;

    public static final String DEFAULT_STATUS =
            PublicationStatusType.UNDER_REVISION_LITERAL.getName();

    public static final String DEFAULT_BUSINESS_VERSION =
            Messages.Xpdl2ResourcesConsts_DefaultBusinessVersion_shortdesc;

    public static final String DEFAULT_PACKAGE_FOLDER_NAME =
            Messages.BusinessProcessAssetConfig_DEFAULT_PROCESS_FOLDER_NAME;

    /**
     * Don't ask to set project references preference key.
     */
    // SCF-436: Moved this constant to XpdConsts
    public static final String PREF_DONT_ASK_AGAIN_FOR_PROJECT_REFERENCE =
            XpdConsts.PREF_DONT_ASK_AGAIN_FOR_PROJECT_REFERENCE;

    /**
     * Preference setting to indicate whether a checksum should be appended to
     * the generated WSDL's namespace. The checksum will change when the content
     * of the WSDL changes.
     * 
     * @since 3.5
     */
    public static final String PREF_GENERATE_CHECKSUM_FOR_GENERATED_WSDL_NAMESPACE =
            "add_checksum_generated_wsdl_namespace"; //$NON-NLS-1$

    /**
     * Preference to indicate that array fields in the mapper should be
     * expanded. This was added for XDP-3499 to support CIM requirements, but
     * will usually be turned off for other Studio releases.
     */
    public static final String PREF_EXPAND_MAPPER_FIELDS =
            "expand_mapper_fields"; //$NON-NLS-1$

    /**
     * Default xpdl2:Activity/xpdl2:Priority value or sub-process tasks
     */
    public static final String DEFAULT_SUBPROCESS_TASK_PRIORITY_VALUE = "300"; //$NON-NLS-1$

	public static final String		PSL_FILE_EXTENSION									= "psl";							//$NON-NLS-1$

}
