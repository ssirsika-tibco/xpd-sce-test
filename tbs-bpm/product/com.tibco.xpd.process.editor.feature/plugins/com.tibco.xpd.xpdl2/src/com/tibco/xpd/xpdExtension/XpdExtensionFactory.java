/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage
 * @generated
 */
public interface XpdExtensionFactory extends EFactory {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    XpdExtensionFactory eINSTANCE = com.tibco.xpd.xpdExtension.impl.XpdExtensionFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Activity Ref</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Activity Ref</em>'.
     * @generated
     */
    ActivityRef createActivityRef();

    /**
     * Returns a new object of class '<em>Activity Resource Patterns</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Activity Resource Patterns</em>'.
     * @generated
     */
    ActivityResourcePatterns createActivityResourcePatterns();

    /**
     * Returns a new object of class '<em>Allocation Strategy</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Allocation Strategy</em>'.
     * @generated
     */
    AllocationStrategy createAllocationStrategy();

    /**
     * Returns a new object of class '<em>Associated Correlation Fields</em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return a new object of class '<em>Associated Correlation Fields</em>'.
     * @generated
     */
    AssociatedCorrelationFields createAssociatedCorrelationFields();

    /**
     * Returns a new object of class '<em>Associated Correlation Field</em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return a new object of class '<em>Associated Correlation Field</em>'.
     * @generated
     */
    AssociatedCorrelationField createAssociatedCorrelationField();

    /**
     * Returns a new object of class '<em>Associated Parameter</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Associated Parameter</em>'.
     * @generated
     */
    AssociatedParameter createAssociatedParameter();

    /**
     * Returns a new object of class '<em>Associated Parameters</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Associated Parameters</em>'.
     * @generated
     */
    AssociatedParameters createAssociatedParameters();

    /**
     * Returns a new object of class '<em>Audit</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Audit</em>'.
     * @generated
     */
    Audit createAudit();

    /**
     * Returns a new object of class '<em>Audit Event</em>'.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @return a new object of class '<em>Audit Event</em>'.
     * @generated
     */
    AuditEvent createAuditEvent();

    /**
     * Returns a new object of class '<em>Business Process</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Business Process</em>'.
     * @generated
     */
    BusinessProcess createBusinessProcess();

    /**
     * Returns a new object of class '<em>Calendar Reference</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Calendar Reference</em>'.
     * @generated
     */
    CalendarReference createCalendarReference();

    /**
     * Returns a new object of class '<em>Catch Error Mappings</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Catch Error Mappings</em>'.
     * @generated
     */
    CatchErrorMappings createCatchErrorMappings();

    /**
     * Returns a new object of class '<em>Constant Period</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Constant Period</em>'.
     * @generated
     */
    ConstantPeriod createConstantPeriod();

    /**
     * Returns a new object of class '<em>Conditional Participant</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Conditional Participant</em>'.
     * @generated
     */
    ConditionalParticipant createConditionalParticipant();

    /**
     * Returns a new object of class '<em>Reply Immediate Data Mappings</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Reply Immediate Data Mappings</em>'.
     * @generated
     */
    ReplyImmediateDataMappings createReplyImmediateDataMappings();

    /**
     * Returns a new object of class '<em>Correlation Data Mappings</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Correlation Data Mappings</em>'.
     * @generated
     */
    CorrelationDataMappings createCorrelationDataMappings();

    /**
     * Returns a new object of class '<em>Discriminator</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Discriminator</em>'.
     * @generated
     */
    Discriminator createDiscriminator();

    /**
     * Returns a new object of class '<em>Document Root</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Document Root</em>'.
     * @generated
     */
    DocumentRoot createDocumentRoot();

    /**
     * Returns a new object of class '<em>Duration Calculation</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Duration Calculation</em>'.
     * @generated
     */
    DurationCalculation createDurationCalculation();

    /**
     * Returns a new object of class '<em>Dynamic Organization Mappings</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Dynamic Organization Mappings</em>'.
     * @generated
     */
    DynamicOrganizationMappings createDynamicOrganizationMappings();

    /**
     * Returns a new object of class '<em>Dynamic Organization Mapping</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Dynamic Organization Mapping</em>'.
     * @generated
     */
    DynamicOrganizationMapping createDynamicOrganizationMapping();

    /**
     * Returns a new object of class '<em>Dynamic Org Identifier Ref</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Dynamic Org Identifier Ref</em>'.
     * @generated
     */
    DynamicOrgIdentifierRef createDynamicOrgIdentifierRef();

    /**
     * Returns a new object of class '<em>Error Method</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Error Method</em>'.
     * @generated
     */
    ErrorMethod createErrorMethod();

    /**
     * Returns a new object of class '<em>Error Thrower Info</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Error Thrower Info</em>'.
     * @generated
     */
    ErrorThrowerInfo createErrorThrowerInfo();

    /**
     * Returns a new object of class '<em>Event Handler Initialisers</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Event Handler Initialisers</em>'.
     * @generated
     */
    EventHandlerInitialisers createEventHandlerInitialisers();

    /**
     * Returns a new object of class '<em>Fault Message</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Fault Message</em>'.
     * @generated
     */
    FaultMessage createFaultMessage();

    /**
     * Returns a new object of class '<em>Form Implementation</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Form Implementation</em>'.
     * @generated
     */
    FormImplementation createFormImplementation();

    /**
     * Returns a new object of class '<em>Implemented Interface</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Implemented Interface</em>'.
     * @generated
     */
    ImplementedInterface createImplementedInterface();

    /**
     * Returns a new object of class '<em>Initial Values</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Initial Values</em>'.
     * @generated
     */
    InitialValues createInitialValues();

    /**
     * Returns a new object of class '<em>Initial Parameter Value</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Initial Parameter Value</em>'.
     * @generated
     */
    InitialParameterValue createInitialParameterValue();

    /**
     * Returns a new object of class '<em>Intermediate Method</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Intermediate Method</em>'.
     * @generated
     */
    IntermediateMethod createIntermediateMethod();

    /**
     * Returns a new object of class '<em>Multi Instance Scripts</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Multi Instance Scripts</em>'.
     * @generated
     */
    MultiInstanceScripts createMultiInstanceScripts();

    /**
     * Returns a new object of class '<em>Namespace Prefix Map</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Namespace Prefix Map</em>'.
     * @generated
     */
    NamespacePrefixMap createNamespacePrefixMap();

    /**
     * Returns a new object of class '<em>Namespace Map Entry</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Namespace Map Entry</em>'.
     * @generated
     */
    NamespaceMapEntry createNamespaceMapEntry();

    /**
     * Returns a new object of class '<em>Piling Info</em>'.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @return a new object of class '<em>Piling Info</em>'.
     * @generated
     */
    PilingInfo createPilingInfo();

    /**
     * Returns a new object of class '<em>Port Type Operation</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Port Type Operation</em>'.
     * @generated
     */
    PortTypeOperation createPortTypeOperation();

    /**
     * Returns a new object of class '<em>Process Interface</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Process Interface</em>'.
     * @generated
     */
    ProcessInterface createProcessInterface();

    /**
     * Returns a new object of class '<em>Process Interfaces</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Process Interfaces</em>'.
     * @generated
     */
    ProcessInterfaces createProcessInterfaces();

    /**
     * Returns a new object of class '<em>Process Resource Patterns</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Process Resource Patterns</em>'.
     * @generated
     */
    ProcessResourcePatterns createProcessResourcePatterns();

    /**
     * Returns a new object of class '<em>Reschedule Timer Script</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Reschedule Timer Script</em>'.
     * @generated
     */
    RescheduleTimerScript createRescheduleTimerScript();

    /**
     * Returns a new object of class '<em>Reschedule Timers</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Reschedule Timers</em>'.
     * @generated
     */
    RescheduleTimers createRescheduleTimers();

    /**
     * Returns a new object of class '<em>Retain Familiar Activities</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Retain Familiar Activities</em>'.
     * @generated
     */
    RetainFamiliarActivities createRetainFamiliarActivities();

    /**
     * Returns a new object of class '<em>Retry</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Retry</em>'.
     * @generated
     */
    Retry createRetry();

    /**
     * Returns a new object of class '<em>Script Information</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Script Information</em>'.
     * @generated
     */
    ScriptInformation createScriptInformation();

    /**
     * Returns a new object of class '<em>Separation Of Duties Activities</em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return a new object of class '<em>Separation Of Duties Activities</em>'.
     * @generated
     */
    SeparationOfDutiesActivities createSeparationOfDutiesActivities();

    /**
     * Returns a new object of class '<em>Signal Data</em>'.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @return a new object of class '<em>Signal Data</em>'.
     * @generated
     */
    SignalData createSignalData();

    /**
     * Returns a new object of class '<em>Start Method</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Start Method</em>'.
     * @generated
     */
    StartMethod createStartMethod();

    /**
     * Returns a new object of class '<em>Structured Discriminator</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Structured Discriminator</em>'.
     * @generated
     */
    StructuredDiscriminator createStructuredDiscriminator();

    /**
     * Returns a new object of class '<em>Task Library Reference</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Task Library Reference</em>'.
     * @generated
     */
    TaskLibraryReference createTaskLibraryReference();

    /**
     * Returns a new object of class '<em>Transform Script</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Transform Script</em>'.
     * @generated
     */
    TransformScript createTransformScript();

    /**
     * Returns a new object of class '<em>User Task Scripts</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>User Task Scripts</em>'.
     * @generated
     */
    UserTaskScripts createUserTaskScripts();

    /**
     * Returns a new object of class '<em>Validation Control</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Validation Control</em>'.
     * @generated
     */
    ValidationControl createValidationControl();

    /**
     * Returns a new object of class '<em>Validation Issue Override</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Validation Issue Override</em>'.
     * @generated
     */
    ValidationIssueOverride createValidationIssueOverride();

    /**
     * Returns a new object of class '<em>Wsdl Event Association</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Wsdl Event Association</em>'.
     * @generated
     */
    WsdlEventAssociation createWsdlEventAssociation();

    /**
     * Returns a new object of class '<em>Work Item Priority</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Work Item Priority</em>'.
     * @generated
     */
    WorkItemPriority createWorkItemPriority();

    /**
     * Returns a new object of class '<em>Xpd Ext Data Object Attributes</em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return a new object of class '<em>Xpd Ext Data Object Attributes</em>'.
     * @generated
     */
    XpdExtDataObjectAttributes createXpdExtDataObjectAttributes();

    /**
     * Returns a new object of class '<em>Xpd Ext Property</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Xpd Ext Property</em>'.
     * @generated
     */
    XpdExtProperty createXpdExtProperty();

    /**
     * Returns a new object of class '<em>Xpd Ext Attribute</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Xpd Ext Attribute</em>'.
     * @generated
     */
    XpdExtAttribute createXpdExtAttribute();

    /**
     * Returns a new object of class '<em>Xpd Ext Attributes</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Xpd Ext Attributes</em>'.
     * @generated
     */
    XpdExtAttributes createXpdExtAttributes();

    /**
     * Returns a new object of class '<em>Update Case Operation Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Update Case Operation Type</em>'.
     * @generated
     */
    UpdateCaseOperationType createUpdateCaseOperationType();

    /**
     * Returns a new object of class '<em>Add Link Associations Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Add Link Associations Type</em>'.
     * @generated
     */
    AddLinkAssociationsType createAddLinkAssociationsType();

    /**
     * Returns a new object of class '<em>Remove Link Associations Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Remove Link Associations Type</em>'.
     * @generated
     */
    RemoveLinkAssociationsType createRemoveLinkAssociationsType();

    /**
     * Returns a new object of class '<em>Remove All Links By Name Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Remove All Links By Name Type</em>'.
     * @generated
     */
    RemoveAllLinksByNameType createRemoveAllLinksByNameType();

    /**
     * Returns a new object of class '<em>Case Reference Operations Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Case Reference Operations Type</em>'.
     * @generated
     */
    CaseReferenceOperationsType createCaseReferenceOperationsType();

    /**
     * Returns a new object of class '<em>Global Data Operation</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Global Data Operation</em>'.
     * @generated
     */
    GlobalDataOperation createGlobalDataOperation();

    /**
     * Returns a new object of class '<em>Delete By Case Identifier Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Delete By Case Identifier Type</em>'.
     * @generated
     */
    DeleteByCaseIdentifierType createDeleteByCaseIdentifierType();

    /**
     * Returns a new object of class '<em>Composite Identifier Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Composite Identifier Type</em>'.
     * @generated
     */
    CompositeIdentifierType createCompositeIdentifierType();

    /**
     * Returns a new object of class '<em>Delete Case Reference Operation Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Delete Case Reference Operation Type</em>'.
     * @generated
     */
    DeleteCaseReferenceOperationType createDeleteCaseReferenceOperationType();

    /**
     * Returns a new object of class '<em>Delete By Composite Identifiers Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Delete By Composite Identifiers Type</em>'.
     * @generated
     */
    DeleteByCompositeIdentifiersType createDeleteByCompositeIdentifiersType();

    /**
     * Returns a new object of class '<em>Create Case Operation Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Create Case Operation Type</em>'.
     * @generated
     */
    CreateCaseOperationType createCreateCaseOperationType();

    /**
     * Returns a new object of class '<em>Case Access Operations Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Case Access Operations Type</em>'.
     * @generated
     */
    CaseAccessOperationsType createCaseAccessOperationsType();

    /**
     * Returns a new object of class '<em>Data Work Item Attribute Mapping</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Data Work Item Attribute Mapping</em>'.
     * @generated
     */
    DataWorkItemAttributeMapping createDataWorkItemAttributeMapping();

    /**
     * Returns a new object of class '<em>Process Data Work Item Attribute Mappings</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Process Data Work Item Attribute Mappings</em>'.
     * @generated
     */
    ProcessDataWorkItemAttributeMappings createProcessDataWorkItemAttributeMappings();

    /**
     * Returns a new object of class '<em>Bpm Runtime Configuration</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Bpm Runtime Configuration</em>'.
     * @generated
     */
    BpmRuntimeConfiguration createBpmRuntimeConfiguration();

    /**
     * Returns a new object of class '<em>Enablement Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Enablement Type</em>'.
     * @generated
     */
    EnablementType createEnablementType();

    /**
     * Returns a new object of class '<em>Initializer Activities Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Initializer Activities Type</em>'.
     * @generated
     */
    InitializerActivitiesType createInitializerActivitiesType();

    /**
     * Returns a new object of class '<em>Ad Hoc Task Configuration Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Ad Hoc Task Configuration Type</em>'.
     * @generated
     */
    AdHocTaskConfigurationType createAdHocTaskConfigurationType();

    /**
     * Returns a new object of class '<em>Required Access Privileges</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Required Access Privileges</em>'.
     * @generated
     */
    RequiredAccessPrivileges createRequiredAccessPrivileges();

    /**
     * Returns a new object of class '<em>Visible For Case States</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Visible For Case States</em>'.
     * @generated
     */
    VisibleForCaseStates createVisibleForCaseStates();

    /**
     * Returns a new object of class '<em>Case Service</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Case Service</em>'.
     * @generated
     */
    CaseService createCaseService();

    /**
     * Returns a new object of class '<em>Document Operation</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Document Operation</em>'.
     * @generated
     */
    DocumentOperation createDocumentOperation();

    /**
     * Returns a new object of class '<em>Case Doc Ref Operations</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Case Doc Ref Operations</em>'.
     * @generated
     */
    CaseDocRefOperations createCaseDocRefOperations();

    /**
     * Returns a new object of class '<em>Case Doc Find Operations</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Case Doc Find Operations</em>'.
     * @generated
     */
    CaseDocFindOperations createCaseDocFindOperations();

    /**
     * Returns a new object of class '<em>Move Case Doc Operation</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Move Case Doc Operation</em>'.
     * @generated
     */
    MoveCaseDocOperation createMoveCaseDocOperation();

    /**
     * Returns a new object of class '<em>Unlink Case Doc Operation</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Unlink Case Doc Operation</em>'.
     * @generated
     */
    UnlinkCaseDocOperation createUnlinkCaseDocOperation();

    /**
     * Returns a new object of class '<em>Link Case Doc Operation</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Link Case Doc Operation</em>'.
     * @generated
     */
    LinkCaseDocOperation createLinkCaseDocOperation();

    /**
     * Returns a new object of class '<em>Link System Document Operation</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Link System Document Operation</em>'.
     * @generated
     */
    LinkSystemDocumentOperation createLinkSystemDocumentOperation();

    /**
     * Returns a new object of class '<em>Delete Case Doc Operation</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Delete Case Doc Operation</em>'.
     * @generated
     */
    DeleteCaseDocOperation createDeleteCaseDocOperation();

    /**
     * Returns a new object of class '<em>Find By File Name Operation</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Find By File Name Operation</em>'.
     * @generated
     */
    FindByFileNameOperation createFindByFileNameOperation();

    /**
     * Returns a new object of class '<em>Find By Query Operation</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Find By Query Operation</em>'.
     * @generated
     */
    FindByQueryOperation createFindByQueryOperation();

    /**
     * Returns a new object of class '<em>Case Document Query Expression</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Case Document Query Expression</em>'.
     * @generated
     */
    CaseDocumentQueryExpression createCaseDocumentQueryExpression();

    /**
     * Returns a new object of class '<em>Service Process Configuration</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Service Process Configuration</em>'.
     * @generated
     */
    ServiceProcessConfiguration createServiceProcessConfiguration();

    /**
     * Returns a new object of class '<em>Script Data Mapper</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Script Data Mapper</em>'.
     * @generated
     */
    ScriptDataMapper createScriptDataMapper();

    /**
     * Returns a new object of class '<em>Data Mapper Array Inflation</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Data Mapper Array Inflation</em>'.
     * @generated
     */
    DataMapperArrayInflation createDataMapperArrayInflation();

    /**
     * Returns a new object of class '<em>Like Mapping Exclusions</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Like Mapping Exclusions</em>'.
     * @generated
     */
    LikeMappingExclusions createLikeMappingExclusions();

    /**
     * Returns a new object of class '<em>Rest Service Operation</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Rest Service Operation</em>'.
     * @generated
     */
    RestServiceOperation createRestServiceOperation();

    /**
     * Returns a new object of class '<em>Like Mapping Exclusion</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Like Mapping Exclusion</em>'.
     * @generated
     */
    LikeMappingExclusion createLikeMappingExclusion();

    /**
     * Returns a new object of class '<em>REST Services</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>REST Services</em>'.
     * @generated
     */
    RESTServices createRESTServices();

    /**
     * Returns a new object of class '<em>Rest Service Resource</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Rest Service Resource</em>'.
     * @generated
     */
    RestServiceResource createRestServiceResource();

    /**
     * Returns a new object of class '<em>Rest Service Resource Security</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Rest Service Resource Security</em>'.
     * @generated
     */
    RestServiceResourceSecurity createRestServiceResourceSecurity();

    /**
     * Returns a new object of class '<em>Wsdl Generation</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Wsdl Generation</em>'.
     * @generated
     */
    WsdlGeneration createWsdlGeneration();

    /**
     * Returns a new object of class '<em>Email Resource</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Email Resource</em>'.
     * @generated
     */
    EmailResource createEmailResource();

    /**
     * Returns a new object of class '<em>Jdbc Resource</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Jdbc Resource</em>'.
     * @generated
     */
    JdbcResource createJdbcResource();

    /**
     * Returns a new object of class '<em>Participant Shared Resource</em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return a new object of class '<em>Participant Shared Resource</em>'.
     * @generated
     */
    ParticipantSharedResource createParticipantSharedResource();

    /**
     * Returns a new object of class '<em>Ws Binding</em>'.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @return a new object of class '<em>Ws Binding</em>'.
     * @generated
     */
    WsBinding createWsBinding();

    /**
     * Returns a new object of class '<em>Ws Inbound</em>'.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @return a new object of class '<em>Ws Inbound</em>'.
     * @generated
     */
    WsInbound createWsInbound();

    /**
     * Returns a new object of class '<em>Ws Inbound</em>' initialized with
     * default values.
     * 
     * @return a new initialized with default values object of class '
     *         <em>Ws Inbound</em>'.
     * @generated NOT
     */
    WsInbound createWsInboundDefault();

    /**
     * Returns a new object of class '<em>Ws Outbound</em>'.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @return a new object of class '<em>Ws Outbound</em>'.
     * @generated
     */
    WsOutbound createWsOutbound();

    /**
     * Returns a new object of class '<em>Ws Outbound</em>'initialized with
     * default values.
     * 
     * @return a new initialized with default values object of class '
     *         <em>Ws Outbound</em>'.
     * @generated NOT
     */
    WsOutbound createWsOutboundDefault();

    /**
     * Returns a new object of class '<em>Ws Resource</em>'.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @return a new object of class '<em>Ws Resource</em>'.
     * @generated
     */
    WsResource createWsResource();

    /**
     * Returns a new object of class '<em>Ws Security Policy</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Ws Security Policy</em>'.
     * @generated
     */
    WsSecurityPolicy createWsSecurityPolicy();

    /**
     * Returns a new object of class '<em>Ws Soap Binding</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Ws Soap Binding</em>'.
     * @generated
     */
    WsSoapBinding createWsSoapBinding();

    /**
     * Returns a new object of class '<em>Ws Soap Http Inbound Binding</em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return a new object of class '<em>Ws Soap Http Inbound Binding</em>'.
     * @generated
     */
    WsSoapHttpInboundBinding createWsSoapHttpInboundBinding();

    /**
     * Returns a new object of class '<em>Ws Soap Http Inbound Binding</em>'
     * initialized with default values.
     * 
     * @return a new initialized with default values object of class '
     *         <em>Ws Soap Http Inbound Binding</em>'.
     * @generated NOT
     */
    WsSoapHttpInboundBinding createWsSoapHttpInboundBindingDefault();

    /**
     * Returns a new object of class '<em>Ws Soap Jms Inbound Binding</em>'
     * initialized with default values.
     * 
     * @return a new initialized with default values object of class '
     *         <em>Ws Soap Jms Inbound Binding</em>'.
     * @generated NOT
     */
    WsSoapJmsInboundBinding createWsSoapJmsInboundBindingDefault();

    /**
     * Returns a new object of class '<em>Ws Soap Jms Outbound Binding</em>'
     * initialized with default values.
     * 
     * @return a new initialized with default values object of class '
     *         <em>Ws Soap Jms Outbound Binding</em>'.
     * @generated NOT
     */
    WsSoapJmsOutboundBinding createWsSoapJmsOutboundBindingDefault();

    /**
     * Returns a new object of class '<em>Ws Soap Http Outbound Binding</em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return a new object of class '<em>Ws Soap Http Outbound Binding</em>'.
     * @generated
     */
    WsSoapHttpOutboundBinding createWsSoapHttpOutboundBinding();

    /**
     * Returns a new object of class '<em>Ws Soap Http Outbound Binding</em>'
     * initialized with default values.
     * 
     * @return a new initialized with default values object of class '
     *         <em>Ws Soap Http Outbound Binding</em>'.
     * @generated NOT
     */
    WsSoapHttpOutboundBinding createWsSoapHttpOutboundBindingDefault();

    /**
     * Returns a new object of class '<em>Ws Soap Jms Inbound Binding</em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return a new object of class '<em>Ws Soap Jms Inbound Binding</em>'.
     * @generated
     */
    WsSoapJmsInboundBinding createWsSoapJmsInboundBinding();

    /**
     * Returns a new object of class '<em>Ws Soap Jms Outbound Binding</em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return a new object of class '<em>Ws Soap Jms Outbound Binding</em>'.
     * @generated
     */
    WsSoapJmsOutboundBinding createWsSoapJmsOutboundBinding();

    /**
     * Returns a new object of class '<em>Ws Soap Security</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Ws Soap Security</em>'.
     * @generated
     */
    WsSoapSecurity createWsSoapSecurity();

    /**
     * Returns a new object of class '<em>Ws Virtual Binding</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Ws Virtual Binding</em>'.
     * @generated
     */
    WsVirtualBinding createWsVirtualBinding();

    /**
     * Returns a new object of class '<em>Ws Virtual Binding</em>' initialized
     * with default values.
     * 
     * @return a new initialized with default values object of class '
     *         <em>Ws Virtual Binding</em>'.
     * @generated NOT
     */
    WsVirtualBinding createWsVirtualBindingDefault();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    XpdExtensionPackage getXpdExtensionPackage();

} // XpdExtensionFactory
