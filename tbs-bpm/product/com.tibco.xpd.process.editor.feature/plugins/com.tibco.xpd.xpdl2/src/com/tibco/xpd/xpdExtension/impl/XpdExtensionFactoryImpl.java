/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * @generated
 */
public class XpdExtensionFactoryImpl extends EFactoryImpl implements XpdExtensionFactory {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    public static XpdExtensionFactory init() {
        try {
            XpdExtensionFactory theXpdExtensionFactory =
                    (XpdExtensionFactory) EPackage.Registry.INSTANCE.getEFactory(XpdExtensionPackage.eNS_URI);
            if (theXpdExtensionFactory != null) {
                return theXpdExtensionFactory;
            }
        } catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new XpdExtensionFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    public XpdExtensionFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
        case XpdExtensionPackage.ACTIVITY_REF:
            return createActivityRef();
        case XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS:
            return createActivityResourcePatterns();
        case XpdExtensionPackage.ALLOCATION_STRATEGY:
            return createAllocationStrategy();
        case XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELDS:
            return createAssociatedCorrelationFields();
        case XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELD:
            return createAssociatedCorrelationField();
        case XpdExtensionPackage.ASSOCIATED_PARAMETER:
            return createAssociatedParameter();
        case XpdExtensionPackage.ASSOCIATED_PARAMETERS:
            return createAssociatedParameters();
        case XpdExtensionPackage.AUDIT:
            return createAudit();
        case XpdExtensionPackage.AUDIT_EVENT:
            return createAuditEvent();
        case XpdExtensionPackage.BUSINESS_PROCESS:
            return createBusinessProcess();
        case XpdExtensionPackage.CALENDAR_REFERENCE:
            return createCalendarReference();
        case XpdExtensionPackage.CATCH_ERROR_MAPPINGS:
            return createCatchErrorMappings();
        case XpdExtensionPackage.CONSTANT_PERIOD:
            return createConstantPeriod();
        case XpdExtensionPackage.CONDITIONAL_PARTICIPANT:
            return createConditionalParticipant();
        case XpdExtensionPackage.REPLY_IMMEDIATE_DATA_MAPPINGS:
            return createReplyImmediateDataMappings();
        case XpdExtensionPackage.CORRELATION_DATA_MAPPINGS:
            return createCorrelationDataMappings();
        case XpdExtensionPackage.DISCRIMINATOR:
            return createDiscriminator();
        case XpdExtensionPackage.DOCUMENT_ROOT:
            return createDocumentRoot();
        case XpdExtensionPackage.DURATION_CALCULATION:
            return createDurationCalculation();
        case XpdExtensionPackage.DYNAMIC_ORGANIZATION_MAPPINGS:
            return createDynamicOrganizationMappings();
        case XpdExtensionPackage.DYNAMIC_ORGANIZATION_MAPPING:
            return createDynamicOrganizationMapping();
        case XpdExtensionPackage.DYNAMIC_ORG_IDENTIFIER_REF:
            return createDynamicOrgIdentifierRef();
        case XpdExtensionPackage.EMAIL_RESOURCE:
            return createEmailResource();
        case XpdExtensionPackage.ERROR_METHOD:
            return createErrorMethod();
        case XpdExtensionPackage.ERROR_THROWER_INFO:
            return createErrorThrowerInfo();
        case XpdExtensionPackage.EVENT_HANDLER_INITIALISERS:
            return createEventHandlerInitialisers();
        case XpdExtensionPackage.FAULT_MESSAGE:
            return createFaultMessage();
        case XpdExtensionPackage.FORM_IMPLEMENTATION:
            return createFormImplementation();
        case XpdExtensionPackage.IMPLEMENTED_INTERFACE:
            return createImplementedInterface();
        case XpdExtensionPackage.INITIAL_VALUES:
            return createInitialValues();
        case XpdExtensionPackage.INITIAL_PARAMETER_VALUE:
            return createInitialParameterValue();
        case XpdExtensionPackage.INTERMEDIATE_METHOD:
            return createIntermediateMethod();
        case XpdExtensionPackage.JDBC_RESOURCE:
            return createJdbcResource();
        case XpdExtensionPackage.MULTI_INSTANCE_SCRIPTS:
            return createMultiInstanceScripts();
        case XpdExtensionPackage.NAMESPACE_PREFIX_MAP:
            return createNamespacePrefixMap();
        case XpdExtensionPackage.NAMESPACE_MAP_ENTRY:
            return createNamespaceMapEntry();
        case XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE:
            return createParticipantSharedResource();
        case XpdExtensionPackage.PILING_INFO:
            return createPilingInfo();
        case XpdExtensionPackage.PORT_TYPE_OPERATION:
            return createPortTypeOperation();
        case XpdExtensionPackage.PROCESS_INTERFACE:
            return createProcessInterface();
        case XpdExtensionPackage.PROCESS_INTERFACES:
            return createProcessInterfaces();
        case XpdExtensionPackage.PROCESS_RESOURCE_PATTERNS:
            return createProcessResourcePatterns();
        case XpdExtensionPackage.RESCHEDULE_TIMER_SCRIPT:
            return createRescheduleTimerScript();
        case XpdExtensionPackage.RESCHEDULE_TIMERS:
            return createRescheduleTimers();
        case XpdExtensionPackage.REST_SERVICES:
            return createRESTServices();
        case XpdExtensionPackage.REST_SERVICE_RESOURCE:
            return createRestServiceResource();
        case XpdExtensionPackage.REST_SERVICE_RESOURCE_SECURITY:
            return createRestServiceResourceSecurity();
        case XpdExtensionPackage.RETAIN_FAMILIAR_ACTIVITIES:
            return createRetainFamiliarActivities();
        case XpdExtensionPackage.RETRY:
            return createRetry();
        case XpdExtensionPackage.SCRIPT_INFORMATION:
            return createScriptInformation();
        case XpdExtensionPackage.SEPARATION_OF_DUTIES_ACTIVITIES:
            return createSeparationOfDutiesActivities();
        case XpdExtensionPackage.SIGNAL_DATA:
            return createSignalData();
        case XpdExtensionPackage.START_METHOD:
            return createStartMethod();
        case XpdExtensionPackage.STRUCTURED_DISCRIMINATOR:
            return createStructuredDiscriminator();
        case XpdExtensionPackage.TASK_LIBRARY_REFERENCE:
            return createTaskLibraryReference();
        case XpdExtensionPackage.TRANSFORM_SCRIPT:
            return createTransformScript();
        case XpdExtensionPackage.USER_TASK_SCRIPTS:
            return createUserTaskScripts();
        case XpdExtensionPackage.VALIDATION_CONTROL:
            return createValidationControl();
        case XpdExtensionPackage.VALIDATION_ISSUE_OVERRIDE:
            return createValidationIssueOverride();
        case XpdExtensionPackage.WSDL_EVENT_ASSOCIATION:
            return createWsdlEventAssociation();
        case XpdExtensionPackage.WORK_ITEM_PRIORITY:
            return createWorkItemPriority();
        case XpdExtensionPackage.WSDL_GENERATION:
            return createWsdlGeneration();
        case XpdExtensionPackage.WS_BINDING:
            return createWsBinding();
        case XpdExtensionPackage.WS_INBOUND:
            return createWsInbound();
        case XpdExtensionPackage.WS_OUTBOUND:
            return createWsOutbound();
        case XpdExtensionPackage.WS_RESOURCE:
            return createWsResource();
        case XpdExtensionPackage.WS_SECURITY_POLICY:
            return createWsSecurityPolicy();
        case XpdExtensionPackage.WS_SOAP_BINDING:
            return createWsSoapBinding();
        case XpdExtensionPackage.WS_SOAP_HTTP_INBOUND_BINDING:
            return createWsSoapHttpInboundBinding();
        case XpdExtensionPackage.WS_SOAP_HTTP_OUTBOUND_BINDING:
            return createWsSoapHttpOutboundBinding();
        case XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING:
            return createWsSoapJmsInboundBinding();
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING:
            return createWsSoapJmsOutboundBinding();
        case XpdExtensionPackage.WS_SOAP_SECURITY:
            return createWsSoapSecurity();
        case XpdExtensionPackage.WS_VIRTUAL_BINDING:
            return createWsVirtualBinding();
        case XpdExtensionPackage.XPD_EXT_DATA_OBJECT_ATTRIBUTES:
            return createXpdExtDataObjectAttributes();
        case XpdExtensionPackage.XPD_EXT_PROPERTY:
            return createXpdExtProperty();
        case XpdExtensionPackage.XPD_EXT_ATTRIBUTE:
            return createXpdExtAttribute();
        case XpdExtensionPackage.XPD_EXT_ATTRIBUTES:
            return createXpdExtAttributes();
        case XpdExtensionPackage.UPDATE_CASE_OPERATION_TYPE:
            return createUpdateCaseOperationType();
        case XpdExtensionPackage.ADD_LINK_ASSOCIATIONS_TYPE:
            return createAddLinkAssociationsType();
        case XpdExtensionPackage.REMOVE_LINK_ASSOCIATIONS_TYPE:
            return createRemoveLinkAssociationsType();
        case XpdExtensionPackage.REMOVE_ALL_LINKS_BY_NAME_TYPE:
            return createRemoveAllLinksByNameType();
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE:
            return createCaseReferenceOperationsType();
        case XpdExtensionPackage.GLOBAL_DATA_OPERATION:
            return createGlobalDataOperation();
        case XpdExtensionPackage.DELETE_BY_CASE_IDENTIFIER_TYPE:
            return createDeleteByCaseIdentifierType();
        case XpdExtensionPackage.COMPOSITE_IDENTIFIER_TYPE:
            return createCompositeIdentifierType();
        case XpdExtensionPackage.DELETE_CASE_REFERENCE_OPERATION_TYPE:
            return createDeleteCaseReferenceOperationType();
        case XpdExtensionPackage.DELETE_BY_COMPOSITE_IDENTIFIERS_TYPE:
            return createDeleteByCompositeIdentifiersType();
        case XpdExtensionPackage.CREATE_CASE_OPERATION_TYPE:
            return createCreateCaseOperationType();
        case XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE:
            return createCaseAccessOperationsType();
        case XpdExtensionPackage.DATA_WORK_ITEM_ATTRIBUTE_MAPPING:
            return createDataWorkItemAttributeMapping();
        case XpdExtensionPackage.PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS:
            return createProcessDataWorkItemAttributeMappings();
        case XpdExtensionPackage.BPM_RUNTIME_CONFIGURATION:
            return createBpmRuntimeConfiguration();
        case XpdExtensionPackage.ENABLEMENT_TYPE:
            return createEnablementType();
        case XpdExtensionPackage.INITIALIZER_ACTIVITIES_TYPE:
            return createInitializerActivitiesType();
        case XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE:
            return createAdHocTaskConfigurationType();
        case XpdExtensionPackage.REQUIRED_ACCESS_PRIVILEGES:
            return createRequiredAccessPrivileges();
        case XpdExtensionPackage.VISIBLE_FOR_CASE_STATES:
            return createVisibleForCaseStates();
        case XpdExtensionPackage.CASE_SERVICE:
            return createCaseService();
        case XpdExtensionPackage.DOCUMENT_OPERATION:
            return createDocumentOperation();
        case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS:
            return createCaseDocRefOperations();
        case XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS:
            return createCaseDocFindOperations();
        case XpdExtensionPackage.MOVE_CASE_DOC_OPERATION:
            return createMoveCaseDocOperation();
        case XpdExtensionPackage.UNLINK_CASE_DOC_OPERATION:
            return createUnlinkCaseDocOperation();
        case XpdExtensionPackage.LINK_CASE_DOC_OPERATION:
            return createLinkCaseDocOperation();
        case XpdExtensionPackage.LINK_SYSTEM_DOCUMENT_OPERATION:
            return createLinkSystemDocumentOperation();
        case XpdExtensionPackage.DELETE_CASE_DOC_OPERATION:
            return createDeleteCaseDocOperation();
        case XpdExtensionPackage.FIND_BY_FILE_NAME_OPERATION:
            return createFindByFileNameOperation();
        case XpdExtensionPackage.FIND_BY_QUERY_OPERATION:
            return createFindByQueryOperation();
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION:
            return createCaseDocumentQueryExpression();
        case XpdExtensionPackage.SERVICE_PROCESS_CONFIGURATION:
            return createServiceProcessConfiguration();
        case XpdExtensionPackage.SCRIPT_DATA_MAPPER:
            return createScriptDataMapper();
        case XpdExtensionPackage.DATA_MAPPER_ARRAY_INFLATION:
            return createDataMapperArrayInflation();
        case XpdExtensionPackage.LIKE_MAPPING_EXCLUSION:
            return createLikeMappingExclusion();
        case XpdExtensionPackage.LIKE_MAPPING_EXCLUSIONS:
            return createLikeMappingExclusions();
        case XpdExtensionPackage.REST_SERVICE_OPERATION:
            return createRestServiceOperation();
        default:
            throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue) {
        switch (eDataType.getClassifierID()) {
        case XpdExtensionPackage.ALLOCATION_STRATEGY_TYPE:
            return createAllocationStrategyTypeFromString(eDataType, initialValue);
        case XpdExtensionPackage.ALLOCATION_TYPE:
            return createAllocationTypeFromString(eDataType, initialValue);
        case XpdExtensionPackage.AUDIT_EVENT_TYPE:
            return createAuditEventTypeFromString(eDataType, initialValue);
        case XpdExtensionPackage.CORRELATION_MODE:
            return createCorrelationModeFromString(eDataType, initialValue);
        case XpdExtensionPackage.ERROR_THROWER_TYPE:
            return createErrorThrowerTypeFromString(eDataType, initialValue);
        case XpdExtensionPackage.EVENT_HANDLER_FLOW_STRATEGY:
            return createEventHandlerFlowStrategyFromString(eDataType, initialValue);
        case XpdExtensionPackage.FLOW_ROUTING_STYLE:
            return createFlowRoutingStyleFromString(eDataType, initialValue);
        case XpdExtensionPackage.FORM_IMPLEMENTATION_TYPE:
            return createFormImplementationTypeFromString(eDataType, initialValue);
        case XpdExtensionPackage.MAX_RETRY_ACTION_TYPE:
            return createMaxRetryActionTypeFromString(eDataType, initialValue);
        case XpdExtensionPackage.RESCHEDULE_DURATION_TYPE:
            return createRescheduleDurationTypeFromString(eDataType, initialValue);
        case XpdExtensionPackage.RESCHEDULE_TIMER_SELECTION_TYPE:
            return createRescheduleTimerSelectionTypeFromString(eDataType, initialValue);
        case XpdExtensionPackage.SECURITY_POLICY:
            return createSecurityPolicyFromString(eDataType, initialValue);
        case XpdExtensionPackage.SOAP_BINDING_STYLE:
            return createSoapBindingStyleFromString(eDataType, initialValue);
        case XpdExtensionPackage.FIELD_FORMAT:
            return createFieldFormatFromString(eDataType, initialValue);
        case XpdExtensionPackage.SUB_PROCESS_START_STRATEGY:
            return createSubProcessStartStrategyFromString(eDataType, initialValue);
        case XpdExtensionPackage.SYSTEM_ERROR_ACTION_TYPE:
            return createSystemErrorActionTypeFromString(eDataType, initialValue);
        case XpdExtensionPackage.VALIDATION_ISSUE_OVERRIDE_TYPE:
            return createValidationIssueOverrideTypeFromString(eDataType, initialValue);
        case XpdExtensionPackage.VISIBILITY:
            return createVisibilityFromString(eDataType, initialValue);
        case XpdExtensionPackage.DELIVERY_MODE:
            return createDeliveryModeFromString(eDataType, initialValue);
        case XpdExtensionPackage.XPD_MODEL_TYPE:
            return createXpdModelTypeFromString(eDataType, initialValue);
        case XpdExtensionPackage.AD_HOC_EXECUTION_TYPE_TYPE:
            return createAdHocExecutionTypeTypeFromString(eDataType, initialValue);
        case XpdExtensionPackage.QUERY_EXPRESSION_JOIN_TYPE:
            return createQueryExpressionJoinTypeFromString(eDataType, initialValue);
        case XpdExtensionPackage.CMIS_QUERY_OPERATOR:
            return createCMISQueryOperatorFromString(eDataType, initialValue);
        case XpdExtensionPackage.ASYNC_EXECUTION_MODE:
            return createAsyncExecutionModeFromString(eDataType, initialValue);
        case XpdExtensionPackage.SIGNAL_TYPE:
            return createSignalTypeFromString(eDataType, initialValue);
        case XpdExtensionPackage.XPD_INTERFACE_TYPE:
            return createXpdInterfaceTypeFromString(eDataType, initialValue);
        case XpdExtensionPackage.DATA_MAPPER_ARRAY_INFLATION_TYPE:
            return createDataMapperArrayInflationTypeFromString(eDataType, initialValue);
        case XpdExtensionPackage.BUSINESS_SERVICE_PUBLISH_TYPE:
            return createBusinessServicePublishTypeFromString(eDataType, initialValue);
        case XpdExtensionPackage.AUDIT_EVENT_TYPE_OBJECT:
            return createAuditEventTypeObjectFromString(eDataType, initialValue);
        case XpdExtensionPackage.SECURITY_POLICY_OBJECT:
            return createSecurityPolicyObjectFromString(eDataType, initialValue);
        case XpdExtensionPackage.SOAP_BINDING_STYLE_OBJECT:
            return createSoapBindingStyleObjectFromString(eDataType, initialValue);
        default:
            throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue) {
        switch (eDataType.getClassifierID()) {
        case XpdExtensionPackage.ALLOCATION_STRATEGY_TYPE:
            return convertAllocationStrategyTypeToString(eDataType, instanceValue);
        case XpdExtensionPackage.ALLOCATION_TYPE:
            return convertAllocationTypeToString(eDataType, instanceValue);
        case XpdExtensionPackage.AUDIT_EVENT_TYPE:
            return convertAuditEventTypeToString(eDataType, instanceValue);
        case XpdExtensionPackage.CORRELATION_MODE:
            return convertCorrelationModeToString(eDataType, instanceValue);
        case XpdExtensionPackage.ERROR_THROWER_TYPE:
            return convertErrorThrowerTypeToString(eDataType, instanceValue);
        case XpdExtensionPackage.EVENT_HANDLER_FLOW_STRATEGY:
            return convertEventHandlerFlowStrategyToString(eDataType, instanceValue);
        case XpdExtensionPackage.FLOW_ROUTING_STYLE:
            return convertFlowRoutingStyleToString(eDataType, instanceValue);
        case XpdExtensionPackage.FORM_IMPLEMENTATION_TYPE:
            return convertFormImplementationTypeToString(eDataType, instanceValue);
        case XpdExtensionPackage.MAX_RETRY_ACTION_TYPE:
            return convertMaxRetryActionTypeToString(eDataType, instanceValue);
        case XpdExtensionPackage.RESCHEDULE_DURATION_TYPE:
            return convertRescheduleDurationTypeToString(eDataType, instanceValue);
        case XpdExtensionPackage.RESCHEDULE_TIMER_SELECTION_TYPE:
            return convertRescheduleTimerSelectionTypeToString(eDataType, instanceValue);
        case XpdExtensionPackage.SECURITY_POLICY:
            return convertSecurityPolicyToString(eDataType, instanceValue);
        case XpdExtensionPackage.SOAP_BINDING_STYLE:
            return convertSoapBindingStyleToString(eDataType, instanceValue);
        case XpdExtensionPackage.FIELD_FORMAT:
            return convertFieldFormatToString(eDataType, instanceValue);
        case XpdExtensionPackage.SUB_PROCESS_START_STRATEGY:
            return convertSubProcessStartStrategyToString(eDataType, instanceValue);
        case XpdExtensionPackage.SYSTEM_ERROR_ACTION_TYPE:
            return convertSystemErrorActionTypeToString(eDataType, instanceValue);
        case XpdExtensionPackage.VALIDATION_ISSUE_OVERRIDE_TYPE:
            return convertValidationIssueOverrideTypeToString(eDataType, instanceValue);
        case XpdExtensionPackage.VISIBILITY:
            return convertVisibilityToString(eDataType, instanceValue);
        case XpdExtensionPackage.DELIVERY_MODE:
            return convertDeliveryModeToString(eDataType, instanceValue);
        case XpdExtensionPackage.XPD_MODEL_TYPE:
            return convertXpdModelTypeToString(eDataType, instanceValue);
        case XpdExtensionPackage.AD_HOC_EXECUTION_TYPE_TYPE:
            return convertAdHocExecutionTypeTypeToString(eDataType, instanceValue);
        case XpdExtensionPackage.QUERY_EXPRESSION_JOIN_TYPE:
            return convertQueryExpressionJoinTypeToString(eDataType, instanceValue);
        case XpdExtensionPackage.CMIS_QUERY_OPERATOR:
            return convertCMISQueryOperatorToString(eDataType, instanceValue);
        case XpdExtensionPackage.ASYNC_EXECUTION_MODE:
            return convertAsyncExecutionModeToString(eDataType, instanceValue);
        case XpdExtensionPackage.SIGNAL_TYPE:
            return convertSignalTypeToString(eDataType, instanceValue);
        case XpdExtensionPackage.XPD_INTERFACE_TYPE:
            return convertXpdInterfaceTypeToString(eDataType, instanceValue);
        case XpdExtensionPackage.DATA_MAPPER_ARRAY_INFLATION_TYPE:
            return convertDataMapperArrayInflationTypeToString(eDataType, instanceValue);
        case XpdExtensionPackage.BUSINESS_SERVICE_PUBLISH_TYPE:
            return convertBusinessServicePublishTypeToString(eDataType, instanceValue);
        case XpdExtensionPackage.AUDIT_EVENT_TYPE_OBJECT:
            return convertAuditEventTypeObjectToString(eDataType, instanceValue);
        case XpdExtensionPackage.SECURITY_POLICY_OBJECT:
            return convertSecurityPolicyObjectToString(eDataType, instanceValue);
        case XpdExtensionPackage.SOAP_BINDING_STYLE_OBJECT:
            return convertSoapBindingStyleObjectToString(eDataType, instanceValue);
        default:
            throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ActivityRef createActivityRef() {
        ActivityRefImpl activityRef = new ActivityRefImpl();
        return activityRef;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ActivityResourcePatterns createActivityResourcePatterns() {
        ActivityResourcePatternsImpl activityResourcePatterns = new ActivityResourcePatternsImpl();
        return activityResourcePatterns;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public AllocationStrategy createAllocationStrategy() {
        AllocationStrategyImpl allocationStrategy = new AllocationStrategyImpl();
        return allocationStrategy;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public AssociatedCorrelationFields createAssociatedCorrelationFields() {
        AssociatedCorrelationFieldsImpl associatedCorrelationFields = new AssociatedCorrelationFieldsImpl();
        return associatedCorrelationFields;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public AssociatedCorrelationField createAssociatedCorrelationField() {
        AssociatedCorrelationFieldImpl associatedCorrelationField = new AssociatedCorrelationFieldImpl();
        return associatedCorrelationField;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public AssociatedParameter createAssociatedParameter() {
        AssociatedParameterImpl associatedParameter = new AssociatedParameterImpl();
        return associatedParameter;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public AssociatedParameters createAssociatedParameters() {
        AssociatedParametersImpl associatedParameters = new AssociatedParametersImpl();
        return associatedParameters;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Audit createAudit() {
        AuditImpl audit = new AuditImpl();
        return audit;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public AuditEvent createAuditEvent() {
        AuditEventImpl auditEvent = new AuditEventImpl();
        return auditEvent;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public BusinessProcess createBusinessProcess() {
        BusinessProcessImpl businessProcess = new BusinessProcessImpl();
        return businessProcess;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public CalendarReference createCalendarReference() {
        CalendarReferenceImpl calendarReference = new CalendarReferenceImpl();
        return calendarReference;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public CatchErrorMappings createCatchErrorMappings() {
        CatchErrorMappingsImpl catchErrorMappings = new CatchErrorMappingsImpl();
        return catchErrorMappings;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ConstantPeriod createConstantPeriod() {
        ConstantPeriodImpl constantPeriod = new ConstantPeriodImpl();
        return constantPeriod;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ConditionalParticipant createConditionalParticipant() {
        ConditionalParticipantImpl conditionalParticipant = new ConditionalParticipantImpl();
        return conditionalParticipant;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ReplyImmediateDataMappings createReplyImmediateDataMappings() {
        ReplyImmediateDataMappingsImpl replyImmediateDataMappings = new ReplyImmediateDataMappingsImpl();
        return replyImmediateDataMappings;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public CorrelationDataMappings createCorrelationDataMappings() {
        CorrelationDataMappingsImpl correlationDataMappings = new CorrelationDataMappingsImpl();
        return correlationDataMappings;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Discriminator createDiscriminator() {
        DiscriminatorImpl discriminator = new DiscriminatorImpl();
        return discriminator;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public DocumentRoot createDocumentRoot() {
        DocumentRootImpl documentRoot = new DocumentRootImpl();
        return documentRoot;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public DurationCalculation createDurationCalculation() {
        DurationCalculationImpl durationCalculation = new DurationCalculationImpl();
        return durationCalculation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public DynamicOrganizationMappings createDynamicOrganizationMappings() {
        DynamicOrganizationMappingsImpl dynamicOrganizationMappings = new DynamicOrganizationMappingsImpl();
        return dynamicOrganizationMappings;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public DynamicOrganizationMapping createDynamicOrganizationMapping() {
        DynamicOrganizationMappingImpl dynamicOrganizationMapping = new DynamicOrganizationMappingImpl();
        return dynamicOrganizationMapping;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public DynamicOrgIdentifierRef createDynamicOrgIdentifierRef() {
        DynamicOrgIdentifierRefImpl dynamicOrgIdentifierRef = new DynamicOrgIdentifierRefImpl();
        return dynamicOrgIdentifierRef;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ErrorMethod createErrorMethod() {
        ErrorMethodImpl errorMethod = new ErrorMethodImpl();
        return errorMethod;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ErrorThrowerInfo createErrorThrowerInfo() {
        ErrorThrowerInfoImpl errorThrowerInfo = new ErrorThrowerInfoImpl();
        return errorThrowerInfo;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EventHandlerInitialisers createEventHandlerInitialisers() {
        EventHandlerInitialisersImpl eventHandlerInitialisers = new EventHandlerInitialisersImpl();
        return eventHandlerInitialisers;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public FaultMessage createFaultMessage() {
        FaultMessageImpl faultMessage = new FaultMessageImpl();
        return faultMessage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public FormImplementation createFormImplementation() {
        FormImplementationImpl formImplementation = new FormImplementationImpl();
        return formImplementation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ImplementedInterface createImplementedInterface() {
        ImplementedInterfaceImpl implementedInterface = new ImplementedInterfaceImpl();
        return implementedInterface;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public InitialValues createInitialValues() {
        InitialValuesImpl initialValues = new InitialValuesImpl();
        return initialValues;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public InitialParameterValue createInitialParameterValue() {
        InitialParameterValueImpl initialParameterValue = new InitialParameterValueImpl();
        return initialParameterValue;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public IntermediateMethod createIntermediateMethod() {
        IntermediateMethodImpl intermediateMethod = new IntermediateMethodImpl();
        return intermediateMethod;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public MultiInstanceScripts createMultiInstanceScripts() {
        MultiInstanceScriptsImpl multiInstanceScripts = new MultiInstanceScriptsImpl();
        return multiInstanceScripts;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NamespacePrefixMap createNamespacePrefixMap() {
        NamespacePrefixMapImpl namespacePrefixMap = new NamespacePrefixMapImpl();
        return namespacePrefixMap;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NamespaceMapEntry createNamespaceMapEntry() {
        NamespaceMapEntryImpl namespaceMapEntry = new NamespaceMapEntryImpl();
        return namespaceMapEntry;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public PilingInfo createPilingInfo() {
        PilingInfoImpl pilingInfo = new PilingInfoImpl();
        return pilingInfo;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public PortTypeOperation createPortTypeOperation() {
        PortTypeOperationImpl portTypeOperation = new PortTypeOperationImpl();
        return portTypeOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ProcessInterface createProcessInterface() {
        ProcessInterfaceImpl processInterface = new ProcessInterfaceImpl();
        return processInterface;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ProcessInterfaces createProcessInterfaces() {
        ProcessInterfacesImpl processInterfaces = new ProcessInterfacesImpl();
        return processInterfaces;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ProcessResourcePatterns createProcessResourcePatterns() {
        ProcessResourcePatternsImpl processResourcePatterns = new ProcessResourcePatternsImpl();
        return processResourcePatterns;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public RescheduleTimerScript createRescheduleTimerScript() {
        RescheduleTimerScriptImpl rescheduleTimerScript = new RescheduleTimerScriptImpl();
        return rescheduleTimerScript;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public RescheduleTimers createRescheduleTimers() {
        RescheduleTimersImpl rescheduleTimers = new RescheduleTimersImpl();
        return rescheduleTimers;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public RetainFamiliarActivities createRetainFamiliarActivities() {
        RetainFamiliarActivitiesImpl retainFamiliarActivities = new RetainFamiliarActivitiesImpl();
        return retainFamiliarActivities;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Retry createRetry() {
        RetryImpl retry = new RetryImpl();
        return retry;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ScriptInformation createScriptInformation() {
        ScriptInformationImpl scriptInformation = new ScriptInformationImpl();
        return scriptInformation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public SeparationOfDutiesActivities createSeparationOfDutiesActivities() {
        SeparationOfDutiesActivitiesImpl separationOfDutiesActivities = new SeparationOfDutiesActivitiesImpl();
        return separationOfDutiesActivities;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public SignalData createSignalData() {
        SignalDataImpl signalData = new SignalDataImpl();
        return signalData;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public StartMethod createStartMethod() {
        StartMethodImpl startMethod = new StartMethodImpl();
        return startMethod;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public StructuredDiscriminator createStructuredDiscriminator() {
        StructuredDiscriminatorImpl structuredDiscriminator = new StructuredDiscriminatorImpl();
        return structuredDiscriminator;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public TaskLibraryReference createTaskLibraryReference() {
        TaskLibraryReferenceImpl taskLibraryReference = new TaskLibraryReferenceImpl();
        return taskLibraryReference;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public TransformScript createTransformScript() {
        TransformScriptImpl transformScript = new TransformScriptImpl();
        return transformScript;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public UserTaskScripts createUserTaskScripts() {
        UserTaskScriptsImpl userTaskScripts = new UserTaskScriptsImpl();
        return userTaskScripts;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ValidationControl createValidationControl() {
        ValidationControlImpl validationControl = new ValidationControlImpl();
        return validationControl;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ValidationIssueOverride createValidationIssueOverride() {
        ValidationIssueOverrideImpl validationIssueOverride = new ValidationIssueOverrideImpl();
        return validationIssueOverride;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public WsdlEventAssociation createWsdlEventAssociation() {
        WsdlEventAssociationImpl wsdlEventAssociation = new WsdlEventAssociationImpl();
        return wsdlEventAssociation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public WorkItemPriority createWorkItemPriority() {
        WorkItemPriorityImpl workItemPriority = new WorkItemPriorityImpl();
        return workItemPriority;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public XpdExtDataObjectAttributes createXpdExtDataObjectAttributes() {
        XpdExtDataObjectAttributesImpl xpdExtDataObjectAttributes = new XpdExtDataObjectAttributesImpl();
        return xpdExtDataObjectAttributes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public XpdExtProperty createXpdExtProperty() {
        XpdExtPropertyImpl xpdExtProperty = new XpdExtPropertyImpl();
        return xpdExtProperty;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public XpdExtAttribute createXpdExtAttribute() {
        XpdExtAttributeImpl xpdExtAttribute = new XpdExtAttributeImpl();
        return xpdExtAttribute;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public XpdExtAttributes createXpdExtAttributes() {
        XpdExtAttributesImpl xpdExtAttributes = new XpdExtAttributesImpl();
        return xpdExtAttributes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public UpdateCaseOperationType createUpdateCaseOperationType() {
        UpdateCaseOperationTypeImpl updateCaseOperationType = new UpdateCaseOperationTypeImpl();
        return updateCaseOperationType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public AddLinkAssociationsType createAddLinkAssociationsType() {
        AddLinkAssociationsTypeImpl addLinkAssociationsType = new AddLinkAssociationsTypeImpl();
        return addLinkAssociationsType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public RemoveLinkAssociationsType createRemoveLinkAssociationsType() {
        RemoveLinkAssociationsTypeImpl removeLinkAssociationsType = new RemoveLinkAssociationsTypeImpl();
        return removeLinkAssociationsType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RemoveAllLinksByNameType createRemoveAllLinksByNameType() {
        RemoveAllLinksByNameTypeImpl removeAllLinksByNameType = new RemoveAllLinksByNameTypeImpl();
        return removeAllLinksByNameType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public CaseReferenceOperationsType createCaseReferenceOperationsType() {
        CaseReferenceOperationsTypeImpl caseReferenceOperationsType = new CaseReferenceOperationsTypeImpl();
        return caseReferenceOperationsType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public GlobalDataOperation createGlobalDataOperation() {
        GlobalDataOperationImpl globalDataOperation = new GlobalDataOperationImpl();
        return globalDataOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public DeleteByCaseIdentifierType createDeleteByCaseIdentifierType() {
        DeleteByCaseIdentifierTypeImpl deleteByCaseIdentifierType = new DeleteByCaseIdentifierTypeImpl();
        return deleteByCaseIdentifierType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public CompositeIdentifierType createCompositeIdentifierType() {
        CompositeIdentifierTypeImpl compositeIdentifierType = new CompositeIdentifierTypeImpl();
        return compositeIdentifierType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public DeleteCaseReferenceOperationType createDeleteCaseReferenceOperationType() {
        DeleteCaseReferenceOperationTypeImpl deleteCaseReferenceOperationType =
                new DeleteCaseReferenceOperationTypeImpl();
        return deleteCaseReferenceOperationType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public DeleteByCompositeIdentifiersType createDeleteByCompositeIdentifiersType() {
        DeleteByCompositeIdentifiersTypeImpl deleteByCompositeIdentifiersType =
                new DeleteByCompositeIdentifiersTypeImpl();
        return deleteByCompositeIdentifiersType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public CreateCaseOperationType createCreateCaseOperationType() {
        CreateCaseOperationTypeImpl createCaseOperationType = new CreateCaseOperationTypeImpl();
        return createCaseOperationType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public CaseAccessOperationsType createCaseAccessOperationsType() {
        CaseAccessOperationsTypeImpl caseAccessOperationsType = new CaseAccessOperationsTypeImpl();
        return caseAccessOperationsType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public DataWorkItemAttributeMapping createDataWorkItemAttributeMapping() {
        DataWorkItemAttributeMappingImpl dataWorkItemAttributeMapping = new DataWorkItemAttributeMappingImpl();
        return dataWorkItemAttributeMapping;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ProcessDataWorkItemAttributeMappings createProcessDataWorkItemAttributeMappings() {
        ProcessDataWorkItemAttributeMappingsImpl processDataWorkItemAttributeMappings =
                new ProcessDataWorkItemAttributeMappingsImpl();
        return processDataWorkItemAttributeMappings;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public BpmRuntimeConfiguration createBpmRuntimeConfiguration() {
        BpmRuntimeConfigurationImpl bpmRuntimeConfiguration = new BpmRuntimeConfigurationImpl();
        return bpmRuntimeConfiguration;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EnablementType createEnablementType() {
        EnablementTypeImpl enablementType = new EnablementTypeImpl();
        return enablementType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public InitializerActivitiesType createInitializerActivitiesType() {
        InitializerActivitiesTypeImpl initializerActivitiesType = new InitializerActivitiesTypeImpl();
        return initializerActivitiesType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public AdHocTaskConfigurationType createAdHocTaskConfigurationType() {
        AdHocTaskConfigurationTypeImpl adHocTaskConfigurationType = new AdHocTaskConfigurationTypeImpl();
        return adHocTaskConfigurationType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public RequiredAccessPrivileges createRequiredAccessPrivileges() {
        RequiredAccessPrivilegesImpl requiredAccessPrivileges = new RequiredAccessPrivilegesImpl();
        return requiredAccessPrivileges;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public VisibleForCaseStates createVisibleForCaseStates() {
        VisibleForCaseStatesImpl visibleForCaseStates = new VisibleForCaseStatesImpl();
        return visibleForCaseStates;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public CaseService createCaseService() {
        CaseServiceImpl caseService = new CaseServiceImpl();
        return caseService;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public DocumentOperation createDocumentOperation() {
        DocumentOperationImpl documentOperation = new DocumentOperationImpl();
        return documentOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public CaseDocRefOperations createCaseDocRefOperations() {
        CaseDocRefOperationsImpl caseDocRefOperations = new CaseDocRefOperationsImpl();
        return caseDocRefOperations;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public CaseDocFindOperations createCaseDocFindOperations() {
        CaseDocFindOperationsImpl caseDocFindOperations = new CaseDocFindOperationsImpl();
        return caseDocFindOperations;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public MoveCaseDocOperation createMoveCaseDocOperation() {
        MoveCaseDocOperationImpl moveCaseDocOperation = new MoveCaseDocOperationImpl();
        return moveCaseDocOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public UnlinkCaseDocOperation createUnlinkCaseDocOperation() {
        UnlinkCaseDocOperationImpl unlinkCaseDocOperation = new UnlinkCaseDocOperationImpl();
        return unlinkCaseDocOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public LinkCaseDocOperation createLinkCaseDocOperation() {
        LinkCaseDocOperationImpl linkCaseDocOperation = new LinkCaseDocOperationImpl();
        return linkCaseDocOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public LinkSystemDocumentOperation createLinkSystemDocumentOperation() {
        LinkSystemDocumentOperationImpl linkSystemDocumentOperation = new LinkSystemDocumentOperationImpl();
        return linkSystemDocumentOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public DeleteCaseDocOperation createDeleteCaseDocOperation() {
        DeleteCaseDocOperationImpl deleteCaseDocOperation = new DeleteCaseDocOperationImpl();
        return deleteCaseDocOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public FindByFileNameOperation createFindByFileNameOperation() {
        FindByFileNameOperationImpl findByFileNameOperation = new FindByFileNameOperationImpl();
        return findByFileNameOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public FindByQueryOperation createFindByQueryOperation() {
        FindByQueryOperationImpl findByQueryOperation = new FindByQueryOperationImpl();
        return findByQueryOperation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public CaseDocumentQueryExpression createCaseDocumentQueryExpression() {
        CaseDocumentQueryExpressionImpl caseDocumentQueryExpression = new CaseDocumentQueryExpressionImpl();
        return caseDocumentQueryExpression;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ServiceProcessConfiguration createServiceProcessConfiguration() {
        ServiceProcessConfigurationImpl serviceProcessConfiguration = new ServiceProcessConfigurationImpl();
        return serviceProcessConfiguration;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ScriptDataMapper createScriptDataMapper() {
        ScriptDataMapperImpl scriptDataMapper = new ScriptDataMapperImpl();
        return scriptDataMapper;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DataMapperArrayInflation createDataMapperArrayInflation() {
        DataMapperArrayInflationImpl dataMapperArrayInflation = new DataMapperArrayInflationImpl();
        return dataMapperArrayInflation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public LikeMappingExclusions createLikeMappingExclusions() {
        LikeMappingExclusionsImpl likeMappingExclusions = new LikeMappingExclusionsImpl();
        return likeMappingExclusions;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RestServiceOperation createRestServiceOperation() {
        RestServiceOperationImpl restServiceOperation = new RestServiceOperationImpl();
        return restServiceOperation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public LikeMappingExclusion createLikeMappingExclusion() {
        LikeMappingExclusionImpl likeMappingExclusion = new LikeMappingExclusionImpl();
        return likeMappingExclusion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RESTServices createRESTServices() {
        RESTServicesImpl restServices = new RESTServicesImpl();
        return restServices;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RestServiceResource createRestServiceResource() {
        RestServiceResourceImpl restServiceResource = new RestServiceResourceImpl();
        return restServiceResource;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RestServiceResourceSecurity createRestServiceResourceSecurity() {
        RestServiceResourceSecurityImpl restServiceResourceSecurity = new RestServiceResourceSecurityImpl();
        return restServiceResourceSecurity;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public WsdlGeneration createWsdlGeneration() {
        WsdlGenerationImpl wsdlGeneration = new WsdlGenerationImpl();
        return wsdlGeneration;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EmailResource createEmailResource() {
        EmailResourceImpl emailResource = new EmailResourceImpl();
        return emailResource;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public JdbcResource createJdbcResource() {
        JdbcResourceImpl jdbcResource = new JdbcResourceImpl();
        return jdbcResource;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ParticipantSharedResource createParticipantSharedResource() {
        ParticipantSharedResourceImpl participantSharedResource = new ParticipantSharedResourceImpl();
        return participantSharedResource;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public WsBinding createWsBinding() {
        WsBindingImpl wsBinding = new WsBindingImpl();
        return wsBinding;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public WsInbound createWsInbound() {
        WsInboundImpl wsInbound = new WsInboundImpl();
        return wsInbound;
    }

    /**
     * @generated NOT
     */
    @Override
    public WsInbound createWsInboundDefault() {
        WsInboundImpl wsInbound = new WsInboundImpl();
        // Set default Virtual binding.
        wsInbound.setVirtualBinding(createWsVirtualBindingDefault());
        // Add one default Soap over Http binding.
        // wsInbound.getSoapHttpBinding()
        // .add(createWsSoapHttpInboundBindingDefault());
        return wsInbound;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public WsOutbound createWsOutbound() {
        WsOutboundImpl wsOutbound = new WsOutboundImpl();
        return wsOutbound;
    }

    /**
     * @generated NOT
     */
    @Override
    public WsOutbound createWsOutboundDefault() {
        WsOutbound wsOutbound = createWsOutbound();
        wsOutbound.setVirtualBinding(createWsVirtualBindingDefault());
        return wsOutbound;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public WsResource createWsResource() {
        WsResourceImpl wsResource = new WsResourceImpl();
        return wsResource;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public WsSecurityPolicy createWsSecurityPolicy() {
        WsSecurityPolicyImpl wsSecurityPolicy = new WsSecurityPolicyImpl();
        return wsSecurityPolicy;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public WsSoapBinding createWsSoapBinding() {
        WsSoapBindingImpl wsSoapBinding = new WsSoapBindingImpl();
        return wsSoapBinding;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public WsSoapHttpInboundBinding createWsSoapHttpInboundBinding() {
        WsSoapHttpInboundBindingImpl wsSoapHttpInboundBinding = new WsSoapHttpInboundBindingImpl();
        return wsSoapHttpInboundBinding;
    }

    /**
     * @generated NOT
     */
    @Override
    public WsSoapHttpInboundBinding createWsSoapHttpInboundBindingDefault() {
        WsSoapHttpInboundBinding wsSoapHttpInboundBinding = createWsSoapHttpInboundBinding();
        wsSoapHttpInboundBinding.setName("SoapOverHttp"); //$NON-NLS-1$
        wsSoapHttpInboundBinding.setBindingStyle(SoapBindingStyle.RPC_LITERAL);
        wsSoapHttpInboundBinding.setSoapVersion("1.1"); //$NON-NLS-1$
        wsSoapHttpInboundBinding.setEndpointUrlPath(""); //$NON-NLS-1$
        wsSoapHttpInboundBinding.setHttpConnectorInstanceName("httpConnector"); //$NON-NLS-1$
        return wsSoapHttpInboundBinding;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public WsSoapHttpOutboundBinding createWsSoapHttpOutboundBinding() {
        WsSoapHttpOutboundBindingImpl wsSoapHttpOutboundBinding = new WsSoapHttpOutboundBindingImpl();
        return wsSoapHttpOutboundBinding;
    }

    /**
     * @generated NOT
     */
    @Override
    public WsSoapHttpOutboundBinding createWsSoapHttpOutboundBindingDefault() {
        WsSoapHttpOutboundBinding wsSoapHttpOutboundBinding = createWsSoapHttpOutboundBinding();
        wsSoapHttpOutboundBinding.setName("SoapOverHttp"); //$NON-NLS-1$
        WsSoapSecurity wsSoapSecurity = createWsSoapSecurity();
        wsSoapHttpOutboundBinding.setOutboundSecurity(wsSoapSecurity);
        return wsSoapHttpOutboundBinding;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public WsSoapJmsInboundBinding createWsSoapJmsInboundBinding() {
        WsSoapJmsInboundBindingImpl wsSoapJmsInboundBinding = new WsSoapJmsInboundBindingImpl();
        return wsSoapJmsInboundBinding;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public WsSoapJmsOutboundBinding createWsSoapJmsOutboundBinding() {
        WsSoapJmsOutboundBindingImpl wsSoapJmsOutboundBinding = new WsSoapJmsOutboundBindingImpl();
        return wsSoapJmsOutboundBinding;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public WsSoapSecurity createWsSoapSecurity() {
        WsSoapSecurityImpl wsSoapSecurity = new WsSoapSecurityImpl();
        return wsSoapSecurity;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public WsVirtualBinding createWsVirtualBinding() {
        WsVirtualBindingImpl wsVirtualBinding = new WsVirtualBindingImpl();
        return wsVirtualBinding;
    }

    /**
     * @generated NOT
     */
    @Override
    public WsVirtualBinding createWsVirtualBindingDefault() {
        WsVirtualBinding wsVirtualBinding = createWsVirtualBinding();
        wsVirtualBinding.setName("Virtualization"); //$NON-NLS-1$
        return wsVirtualBinding;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public AllocationStrategyType createAllocationStrategyTypeFromString(EDataType eDataType, String initialValue) {
        AllocationStrategyType result = AllocationStrategyType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertAllocationStrategyTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public AllocationType createAllocationTypeFromString(EDataType eDataType, String initialValue) {
        AllocationType result = AllocationType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertAllocationTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public AuditEventType createAuditEventTypeFromString(EDataType eDataType, String initialValue) {
        AuditEventType result = AuditEventType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertAuditEventTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public CorrelationMode createCorrelationModeFromString(EDataType eDataType, String initialValue) {
        CorrelationMode result = CorrelationMode.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertCorrelationModeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ErrorThrowerType createErrorThrowerTypeFromString(EDataType eDataType, String initialValue) {
        ErrorThrowerType result = ErrorThrowerType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertErrorThrowerTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EventHandlerFlowStrategy createEventHandlerFlowStrategyFromString(EDataType eDataType, String initialValue) {
        EventHandlerFlowStrategy result = EventHandlerFlowStrategy.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertEventHandlerFlowStrategyToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public FlowRoutingStyle createFlowRoutingStyleFromString(EDataType eDataType, String initialValue) {
        FlowRoutingStyle result = FlowRoutingStyle.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertFlowRoutingStyleToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public FormImplementationType createFormImplementationTypeFromString(EDataType eDataType, String initialValue) {
        FormImplementationType result = FormImplementationType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertFormImplementationTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public MaxRetryActionType createMaxRetryActionTypeFromString(EDataType eDataType, String initialValue) {
        MaxRetryActionType result = MaxRetryActionType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertMaxRetryActionTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public RescheduleDurationType createRescheduleDurationTypeFromString(EDataType eDataType, String initialValue) {
        RescheduleDurationType result = RescheduleDurationType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertRescheduleDurationTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public RescheduleTimerSelectionType createRescheduleTimerSelectionTypeFromString(EDataType eDataType,
            String initialValue) {
        RescheduleTimerSelectionType result = RescheduleTimerSelectionType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertRescheduleTimerSelectionTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public Visibility createVisibilityFromString(EDataType eDataType, String initialValue) {
        Visibility result = Visibility.get(initialValue);
        // if (result == null)
        // throw new IllegalArgumentException(
        //                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public DeliveryMode createDeliveryModeFromString(EDataType eDataType, String initialValue) {
        DeliveryMode result = DeliveryMode.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertDeliveryModeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertVisibilityToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public XpdModelType createXpdModelTypeFromString(EDataType eDataType, String initialValue) {
        XpdModelType result = XpdModelType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertXpdModelTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public AdHocExecutionTypeType createAdHocExecutionTypeTypeFromString(EDataType eDataType, String initialValue) {
        AdHocExecutionTypeType result = AdHocExecutionTypeType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertAdHocExecutionTypeTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public QueryExpressionJoinType createQueryExpressionJoinTypeFromString(EDataType eDataType, String initialValue) {
        QueryExpressionJoinType result = QueryExpressionJoinType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertQueryExpressionJoinTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public CMISQueryOperator createCMISQueryOperatorFromString(EDataType eDataType, String initialValue) {
        CMISQueryOperator result = CMISQueryOperator.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertCMISQueryOperatorToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public AsyncExecutionMode createAsyncExecutionModeFromString(EDataType eDataType, String initialValue) {
        AsyncExecutionMode result = AsyncExecutionMode.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertAsyncExecutionModeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SignalType createSignalTypeFromString(EDataType eDataType, String initialValue) {
        SignalType result = SignalType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertSignalTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XpdInterfaceType createXpdInterfaceTypeFromString(EDataType eDataType, String initialValue) {
        XpdInterfaceType result = XpdInterfaceType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertXpdInterfaceTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DataMapperArrayInflationType createDataMapperArrayInflationTypeFromString(EDataType eDataType,
            String initialValue) {
        DataMapperArrayInflationType result = DataMapperArrayInflationType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertDataMapperArrayInflationTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BusinessServicePublishType createBusinessServicePublishTypeFromString(EDataType eDataType,
            String initialValue) {
        BusinessServicePublishType result = BusinessServicePublishType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertBusinessServicePublishTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public SecurityPolicy createSecurityPolicyFromString(EDataType eDataType, String initialValue) {
        SecurityPolicy result = SecurityPolicy.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertSecurityPolicyToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public SoapBindingStyle createSoapBindingStyleFromString(EDataType eDataType, String initialValue) {
        SoapBindingStyle result = SoapBindingStyle.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertSoapBindingStyleToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FieldFormat createFieldFormatFromString(EDataType eDataType, String initialValue) {
        FieldFormat result = FieldFormat.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertFieldFormatToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public SubProcessStartStrategy createSubProcessStartStrategyFromString(EDataType eDataType, String initialValue) {
        SubProcessStartStrategy result = SubProcessStartStrategy.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertSubProcessStartStrategyToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public SystemErrorActionType createSystemErrorActionTypeFromString(EDataType eDataType, String initialValue) {
        SystemErrorActionType result = SystemErrorActionType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertSystemErrorActionTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ValidationIssueOverrideType createValidationIssueOverrideTypeFromString(EDataType eDataType,
            String initialValue) {
        ValidationIssueOverrideType result = ValidationIssueOverrideType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertValidationIssueOverrideTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public AuditEventType createAuditEventTypeObjectFromString(EDataType eDataType, String initialValue) {
        return createAuditEventTypeFromString(XpdExtensionPackage.Literals.AUDIT_EVENT_TYPE, initialValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertAuditEventTypeObjectToString(EDataType eDataType, Object instanceValue) {
        return convertAuditEventTypeToString(XpdExtensionPackage.Literals.AUDIT_EVENT_TYPE, instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public SecurityPolicy createSecurityPolicyObjectFromString(EDataType eDataType, String initialValue) {
        return createSecurityPolicyFromString(XpdExtensionPackage.Literals.SECURITY_POLICY, initialValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertSecurityPolicyObjectToString(EDataType eDataType, Object instanceValue) {
        return convertSecurityPolicyToString(XpdExtensionPackage.Literals.SECURITY_POLICY, instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public SoapBindingStyle createSoapBindingStyleObjectFromString(EDataType eDataType, String initialValue) {
        return createSoapBindingStyleFromString(XpdExtensionPackage.Literals.SOAP_BINDING_STYLE, initialValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertSoapBindingStyleObjectToString(EDataType eDataType, Object instanceValue) {
        return convertSoapBindingStyleToString(XpdExtensionPackage.Literals.SOAP_BINDING_STYLE, instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public XpdExtensionPackage getXpdExtensionPackage() {
        return (XpdExtensionPackage) getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static XpdExtensionPackage getPackage() {
        return XpdExtensionPackage.eINSTANCE;
    }

    /**
     * @generated NOT
     */
    @Override
    public WsSoapJmsInboundBinding createWsSoapJmsInboundBindingDefault() {
        WsSoapJmsInboundBinding wsSoapJmsInboundBinding = createWsSoapJmsInboundBinding();
        wsSoapJmsInboundBinding.setName("SoapOverJms"); //$NON-NLS-1$   
        wsSoapJmsInboundBinding.setInboundConnectionFactoryConfiguration("amx.bpm.userapp.jmsConnFactoryConf"); //$NON-NLS-1$
        wsSoapJmsInboundBinding.setInboundDestination("amx.bpm.userapp.jms.request.conf"); //$NON-NLS-1$
        wsSoapJmsInboundBinding.setOutboundConnectionFactory("amx.bpm.userapp.jmsConnFactory"); //$NON-NLS-1$
        return wsSoapJmsInboundBinding;
    }

    /**
     * @see com.tibco.xpd.xpdExtension.XpdExtensionFactory#createWsSoapJmsOutboundBindingDefault()
     * 
     * @return
     */
    @Override
    public WsSoapJmsOutboundBinding createWsSoapJmsOutboundBindingDefault() {
        WsSoapJmsOutboundBinding wsSoapJmsOutboundBinding = createWsSoapJmsOutboundBinding();
        wsSoapJmsOutboundBinding.setInboundDestination("amx.bpm.userapp.jmsDest"); //$NON-NLS-1$
        wsSoapJmsOutboundBinding.setOutboundConnectionFactory("amx.bpm.userapp.jmsConnFactory"); //$NON-NLS-1$
        wsSoapJmsOutboundBinding.setOutboundDestination("amx.bpm.userapp.jmsDest"); //$NON-NLS-1$

        /* XPD-6974: Set Default Soap JMS OutBound Message Configuration */
        wsSoapJmsOutboundBinding.setPriority(4);
        wsSoapJmsOutboundBinding.setInvocationTimeout(6);
        wsSoapJmsOutboundBinding.setMessageExpiration(3);
        wsSoapJmsOutboundBinding.setDeliveryMode(DeliveryMode.PERSISTENT);

        return wsSoapJmsOutboundBinding;
    }

} // XpdExtensionFactoryImpl
