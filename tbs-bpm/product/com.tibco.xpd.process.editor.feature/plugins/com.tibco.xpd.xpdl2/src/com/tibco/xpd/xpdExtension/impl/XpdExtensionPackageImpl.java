/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.AdHocExecutionTypeType;
import com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType;
import com.tibco.xpd.xpdExtension.AddLinkAssociationsType;
import com.tibco.xpd.xpdExtension.AllocationStrategy;
import com.tibco.xpd.xpdExtension.AllocationStrategyType;
import com.tibco.xpd.xpdExtension.AllocationType;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationField;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationFields;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.AssociatedParametersContainer;
import com.tibco.xpd.xpdExtension.AsyncExecutionMode;
import com.tibco.xpd.xpdExtension.Audit;
import com.tibco.xpd.xpdExtension.AuditEvent;
import com.tibco.xpd.xpdExtension.AuditEventType;
import com.tibco.xpd.xpdExtension.BpmRuntimeConfiguration;
import com.tibco.xpd.xpdExtension.BusinessProcess;
import com.tibco.xpd.xpdExtension.BusinessServicePublishType;
import com.tibco.xpd.xpdExtension.CMISQueryOperator;
import com.tibco.xpd.xpdExtension.CalendarReference;
import com.tibco.xpd.xpdExtension.CaseAccessOperationsType;
import com.tibco.xpd.xpdExtension.CaseDocFindOperations;
import com.tibco.xpd.xpdExtension.CaseDocRefOperations;
import com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression;
import com.tibco.xpd.xpdExtension.CaseReferenceOperationsType;
import com.tibco.xpd.xpdExtension.CaseService;
import com.tibco.xpd.xpdExtension.CatchErrorMappings;
import com.tibco.xpd.xpdExtension.CompositeIdentifierType;
import com.tibco.xpd.xpdExtension.ConditionalParticipant;
import com.tibco.xpd.xpdExtension.ConstantPeriod;
import com.tibco.xpd.xpdExtension.CorrelationDataMappings;
import com.tibco.xpd.xpdExtension.CorrelationMode;
import com.tibco.xpd.xpdExtension.CreateCaseOperationType;
import com.tibco.xpd.xpdExtension.DataMapperArrayInflation;
import com.tibco.xpd.xpdExtension.DataMapperArrayInflationType;
import com.tibco.xpd.xpdExtension.DataWorkItemAttributeMapping;
import com.tibco.xpd.xpdExtension.DeleteByCaseIdentifierType;
import com.tibco.xpd.xpdExtension.DeleteByCompositeIdentifiersType;
import com.tibco.xpd.xpdExtension.DeleteCaseDocOperation;
import com.tibco.xpd.xpdExtension.DeleteCaseReferenceOperationType;
import com.tibco.xpd.xpdExtension.DeliveryMode;
import com.tibco.xpd.xpdExtension.Discriminator;
import com.tibco.xpd.xpdExtension.DocumentOperation;
import com.tibco.xpd.xpdExtension.DocumentRoot;
import com.tibco.xpd.xpdExtension.DurationCalculation;
import com.tibco.xpd.xpdExtension.DynamicOrgIdentifierRef;
import com.tibco.xpd.xpdExtension.DynamicOrganizationMapping;
import com.tibco.xpd.xpdExtension.DynamicOrganizationMappings;
import com.tibco.xpd.xpdExtension.EmailResource;
import com.tibco.xpd.xpdExtension.EnablementType;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.ErrorThrowerInfo;
import com.tibco.xpd.xpdExtension.ErrorThrowerType;
import com.tibco.xpd.xpdExtension.EventHandlerFlowStrategy;
import com.tibco.xpd.xpdExtension.EventHandlerInitialisers;
import com.tibco.xpd.xpdExtension.FaultMessage;
import com.tibco.xpd.xpdExtension.FieldFormat;
import com.tibco.xpd.xpdExtension.FindByFileNameOperation;
import com.tibco.xpd.xpdExtension.FindByQueryOperation;
import com.tibco.xpd.xpdExtension.FlowRoutingStyle;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.FormImplementationType;
import com.tibco.xpd.xpdExtension.GlobalDataOperation;
import com.tibco.xpd.xpdExtension.ImplementedInterface;
import com.tibco.xpd.xpdExtension.InitialParameterValue;
import com.tibco.xpd.xpdExtension.InitialValues;
import com.tibco.xpd.xpdExtension.InitializerActivitiesType;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.JdbcResource;
import com.tibco.xpd.xpdExtension.LikeMappingExclusion;
import com.tibco.xpd.xpdExtension.LikeMappingExclusions;
import com.tibco.xpd.xpdExtension.LinkCaseDocOperation;
import com.tibco.xpd.xpdExtension.LinkSystemDocumentOperation;
import com.tibco.xpd.xpdExtension.MaxRetryActionType;
import com.tibco.xpd.xpdExtension.MoveCaseDocOperation;
import com.tibco.xpd.xpdExtension.MultiInstanceScripts;
import com.tibco.xpd.xpdExtension.NamespaceMapEntry;
import com.tibco.xpd.xpdExtension.NamespacePrefixMap;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.PilingInfo;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.ProcessDataWorkItemAttributeMappings;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.ProcessResourcePatterns;
import com.tibco.xpd.xpdExtension.QueryExpressionJoinType;
import com.tibco.xpd.xpdExtension.RESTServices;
import com.tibco.xpd.xpdExtension.RemoveLinkAssociationsType;
import com.tibco.xpd.xpdExtension.ReplyImmediateDataMappings;
import com.tibco.xpd.xpdExtension.RequiredAccessPrivileges;
import com.tibco.xpd.xpdExtension.RescheduleDurationType;
import com.tibco.xpd.xpdExtension.RescheduleTimerScript;
import com.tibco.xpd.xpdExtension.RescheduleTimerSelectionType;
import com.tibco.xpd.xpdExtension.RescheduleTimers;
import com.tibco.xpd.xpdExtension.RestServiceOperation;
import com.tibco.xpd.xpdExtension.RestServiceResource;
import com.tibco.xpd.xpdExtension.RestServiceResourceSecurity;
import com.tibco.xpd.xpdExtension.RetainFamiliarActivities;
import com.tibco.xpd.xpdExtension.Retry;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.SecurityPolicy;
import com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities;
import com.tibco.xpd.xpdExtension.ServiceProcessConfiguration;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdExtension.SignalType;
import com.tibco.xpd.xpdExtension.SoapBindingStyle;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.StructuredDiscriminator;
import com.tibco.xpd.xpdExtension.SubProcessStartStrategy;
import com.tibco.xpd.xpdExtension.SystemErrorActionType;
import com.tibco.xpd.xpdExtension.TaskLibraryReference;
import com.tibco.xpd.xpdExtension.TransformScript;
import com.tibco.xpd.xpdExtension.UnlinkCaseDocOperation;
import com.tibco.xpd.xpdExtension.UpdateCaseOperationType;
import com.tibco.xpd.xpdExtension.UserTaskScripts;
import com.tibco.xpd.xpdExtension.ValidationControl;
import com.tibco.xpd.xpdExtension.ValidationIssueOverride;
import com.tibco.xpd.xpdExtension.ValidationIssueOverrideType;
import com.tibco.xpd.xpdExtension.Visibility;
import com.tibco.xpd.xpdExtension.VisibleForCaseStates;
import com.tibco.xpd.xpdExtension.WorkItemPriority;
import com.tibco.xpd.xpdExtension.WsBinding;
import com.tibco.xpd.xpdExtension.WsInbound;
import com.tibco.xpd.xpdExtension.WsOutbound;
import com.tibco.xpd.xpdExtension.WsResource;
import com.tibco.xpd.xpdExtension.WsSecurityPolicy;
import com.tibco.xpd.xpdExtension.WsSoapBinding;
import com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapHttpOutboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapSecurity;
import com.tibco.xpd.xpdExtension.WsVirtualBinding;
import com.tibco.xpd.xpdExtension.WsdlEventAssociation;
import com.tibco.xpd.xpdExtension.WsdlGeneration;
import com.tibco.xpd.xpdExtension.XpdExtAttribute;
import com.tibco.xpd.xpdExtension.XpdExtAttributes;
import com.tibco.xpd.xpdExtension.XpdExtDataObjectAttributes;
import com.tibco.xpd.xpdExtension.XpdExtProperty;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdExtension.XpdInterfaceType;
import com.tibco.xpd.xpdExtension.XpdModelType;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import com.tibco.xpd.xpdExtension.*;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * @generated
 */
public class XpdExtensionPackageImpl extends EPackageImpl implements XpdExtensionPackage {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass activityRefEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass activityResourcePatternsEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass allocationStrategyEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass associatedCorrelationFieldsEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass associatedCorrelationFieldEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass documentRootEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass durationCalculationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass dynamicOrganizationMappingsEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass dynamicOrganizationMappingEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass dynamicOrgIdentifierRefEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass errorMethodEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass xpdExtDataObjectAttributesEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass xpdExtPropertyEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass xpdExtAttributeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass xpdExtAttributesEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass updateCaseOperationTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass addLinkAssociationsTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass removeLinkAssociationsTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass caseReferenceOperationsTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass globalDataOperationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass deleteByCaseIdentifierTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass compositeIdentifierTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass deleteCaseReferenceOperationTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass deleteByCompositeIdentifiersTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass createCaseOperationTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass caseAccessOperationsTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass dataWorkItemAttributeMappingEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass processDataWorkItemAttributeMappingsEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass bpmRuntimeConfigurationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass enablementTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass initializerActivitiesTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass adHocTaskConfigurationTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass requiredAccessPrivilegesEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass visibleForCaseStatesEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass caseServiceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass documentOperationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass caseDocRefOperationsEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass caseDocFindOperationsEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass moveCaseDocOperationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass unlinkCaseDocOperationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass linkCaseDocOperationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass linkSystemDocumentOperationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass deleteCaseDocOperationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass findByFileNameOperationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass findByQueryOperationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass caseDocumentQueryExpressionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass serviceProcessConfigurationEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass scriptDataMapperEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass dataMapperArrayInflationEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass likeMappingExclusionsEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass restServiceOperationEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass likeMappingExclusionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass restServicesEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass restServiceResourceEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass restServiceResourceSecurityEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass wsdlGenerationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass emailResourceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass jdbcResourceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass participantSharedResourceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass wsBindingEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass wsInboundEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass wsOutboundEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass wsResourceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass wsSecurityPolicyEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass wsSoapBindingEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass wsSoapHttpInboundBindingEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass wsSoapHttpOutboundBindingEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass wsSoapJmsInboundBindingEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass wsSoapJmsOutboundBindingEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass wsSoapSecurityEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass wsVirtualBindingEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass businessProcessEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass calendarReferenceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass structuredDiscriminatorEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass taskLibraryReferenceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass discriminatorEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass transformScriptEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass conditionalParticipantEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass replyImmediateDataMappingsEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum allocationStrategyTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass initialParameterValueEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass interfaceMethodEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass constantPeriodEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass correlationDataMappingsEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass userTaskScriptsEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass validationControlEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass validationIssueOverrideEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass auditEventEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass catchErrorMappingsEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass errorThrowerInfoEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass eventHandlerInitialisersEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass faultMessageEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass formImplementationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass auditEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass scriptInformationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass initialValuesEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass intermediateMethodEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass implementedInterfaceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass associatedParametersContainerEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass associatedParameterEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass associatedParametersEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass processInterfacesEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass processResourcePatternsEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass rescheduleTimerScriptEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass rescheduleTimersEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass separationOfDutiesActivitiesEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass signalDataEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass retainFamiliarActivitiesEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass retryEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass startMethodEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass processInterfaceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass multiInstanceScriptsEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass namespacePrefixMapEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass namespaceMapEntryEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass workItemPriorityEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass pilingInfoEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass portTypeOperationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass wsdlEventAssociationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum auditEventTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum correlationModeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum errorThrowerTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum eventHandlerFlowStrategyEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum flowRoutingStyleEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum formImplementationTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum maxRetryActionTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum rescheduleDurationTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum rescheduleTimerSelectionTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum visibilityEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum deliveryModeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum xpdModelTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum adHocExecutionTypeTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum queryExpressionJoinTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum cmisQueryOperatorEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum asyncExecutionModeEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum signalTypeEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum xpdInterfaceTypeEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum dataMapperArrayInflationTypeEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum businessServicePublishTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum securityPolicyEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum soapBindingStyleEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum fieldFormatEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum subProcessStartStrategyEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum systemErrorActionTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum validationIssueOverrideTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum allocationTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType auditEventTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType securityPolicyObjectEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType soapBindingStyleObjectEDataType = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
     * package package URI value.
     * <p>
     * Note: the correct way to create the package is via the static factory
     * method {@link #init init()}, which also performs initialization of the
     * package, or returns the registered package, if one already exists. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private XpdExtensionPackageImpl() {
        super(eNS_URI, XpdExtensionFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model,
     * and for any others upon which it depends.
     * 
     * <p>
     * This method is used to initialize {@link XpdExtensionPackage#eINSTANCE}
     * when that field is accessed. Clients should not invoke it directly.
     * Instead, they should simply access that field to obtain the package. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static XpdExtensionPackage init() {
        if (isInited)
            return (XpdExtensionPackage) EPackage.Registry.INSTANCE.getEPackage(XpdExtensionPackage.eNS_URI);

        // Obtain or create and register package
        Object registeredXpdExtensionPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
        XpdExtensionPackageImpl theXpdExtensionPackage =
                registeredXpdExtensionPackage instanceof XpdExtensionPackageImpl
                        ? (XpdExtensionPackageImpl) registeredXpdExtensionPackage
                        : new XpdExtensionPackageImpl();

        isInited = true;

        // Initialize simple dependencies
        Xpdl2Package.eINSTANCE.eClass();
        XMLTypePackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theXpdExtensionPackage.createPackageContents();

        // Initialize created meta-data
        theXpdExtensionPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theXpdExtensionPackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(XpdExtensionPackage.eNS_URI, theXpdExtensionPackage);
        return theXpdExtensionPackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getActivityRef() {
        return activityRefEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getActivityRef_IdRef() {
        return (EAttribute) activityRefEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getActivityResourcePatterns() {
        return activityResourcePatternsEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getActivityResourcePatterns_AllocationStrategy() {
        return (EReference) activityResourcePatternsEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getActivityResourcePatterns_Piling() {
        return (EReference) activityResourcePatternsEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getActivityResourcePatterns_WorkItemPriority() {
        return (EReference) activityResourcePatternsEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getAllocationStrategy() {
        return allocationStrategyEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getAllocationStrategy_Offer() {
        return (EAttribute) allocationStrategyEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getAllocationStrategy_Strategy() {
        return (EAttribute) allocationStrategyEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getAllocationStrategy_ReOfferOnClose() {
        return (EAttribute) allocationStrategyEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getAllocationStrategy_ReOfferOnCancel() {
        return (EAttribute) allocationStrategyEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAllocationStrategy_AllocateToOfferSetMemberIdentifier() {
        return (EAttribute) allocationStrategyEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getAssociatedCorrelationFields() {
        return associatedCorrelationFieldsEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getAssociatedCorrelationFields_AssociatedCorrelationField() {
        return (EReference) associatedCorrelationFieldsEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getAssociatedCorrelationFields_DisableImplicitAssociation() {
        return (EAttribute) associatedCorrelationFieldsEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getAssociatedCorrelationField() {
        return associatedCorrelationFieldEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getAssociatedCorrelationField_Name() {
        return (EAttribute) associatedCorrelationFieldEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getAssociatedCorrelationField_CorrelationMode() {
        return (EAttribute) associatedCorrelationFieldEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getDocumentRoot() {
        return documentRootEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_XMLNSPrefixMap() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_XSISchemaLocation() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_ImplementationType() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_DataObjectAttributes() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_ExtendedAttributes() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_ContinueOnTimeout() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_Alias() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_ConstantPeriod() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_UserTaskScripts() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_Audit() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_Script() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_ReplyImmediately() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(11);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_InitialValues() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(12);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_AssociatedCorrelationFields() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(13);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_AssociatedParameters() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(14);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_ImplementedInterface() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(15);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_ProcessInterfaces() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(16);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_WsdlEventAssociation() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(17);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_InlineSubProcess() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(18);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_DocumentationURL() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(19);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_Implements() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(20);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_MultiInstanceScripts() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(21);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_ProcessIdentifierField() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(22);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_Expression() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(23);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_Visibility() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(24);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_SecurityProfile() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(25);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_Language() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(26);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_InitialParameterValue() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(27);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_InitialValueMapping() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(28);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_PortTypeOperation() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(29);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_Transport() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(30);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_IsChained() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(31);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_ExternalReference() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(32);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_ProcessResourcePatterns() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(33);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_EventHandlerInitialisers() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(34);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_ActivityResourcePatterns() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(35);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_RequireNewTransaction() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(36);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_DocumentOperation() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(37);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_DurationCalculation() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(38);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_Discriminator() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(39);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_DisplayName() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(40);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_CatchThrow() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(41);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_IsRemote() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(42);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_CorrelationDataMappings() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(43);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_TransformScript() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(44);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_PublishAsBusinessService() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(45);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_BusinessServiceCategory() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(46);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_ErrorThrowerInfo() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(47);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_CatchErrorMappings() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(48);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_ConditionalParticipant() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(49);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_Generated() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(50);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_ReplyToActivityId() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(51);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_TaskLibraryReference() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(52);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_SetPerformerInProcess() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(53);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_EmbSubprocOtherStateHeight() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(54);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_EmbSubprocOtherStateWidth() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(55);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_FormImplementation() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(56);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_ParticipantQuery() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(57);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_ApiEndPointParticipant() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(58);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_FaultMessage() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(59);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_RequestActivityId() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(60);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_BusinessProcess() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(61);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_WsdlGeneration() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(62);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_TargetPrimitiveProperty() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(63);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_SourcePrimitiveProperty() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(64);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_DecisionService() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(65);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_ParticipantSharedResource() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(66);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_XpdModelType() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(67);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_FlowRoutingStyle() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(68);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_CalendarReference() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(69);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_NonCancelling() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(70);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_SignalData() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(71);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_Retry() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(72);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_CaseRefType() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(84);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_RESTServices() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(85);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_PublishAsRestService() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(86);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_RestActivityId() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(87);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_RescheduleTimerScript() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(88);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_DynamicOrganizationMappings() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(89);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_SignalHandlerAsynchronous() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(90);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_GlobalDataOperation() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(91);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_ProcessDataWorkItemAttributeMappings() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(92);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_AllowUnqualifiedSubProcessIdentification() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(93);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_BpmRuntimeConfiguration() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(94);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_IsCaseService() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(95);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_RequiredAccessPrivileges() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(96);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_CaseService() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(97);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_AdHocTaskConfiguration() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(98);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_IsEventSubProcess() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(99);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_NonInterruptingEvent() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(100);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDocumentRoot_CorrelateImmediately() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(101);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDocumentRoot_AsyncExecutionMode() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(102);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDocumentRoot_SignalType() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(103);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_ServiceProcessConfiguration() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(104);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDocumentRoot_LikeMapping() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(105);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_ScriptDataMapper() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(106);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_LikeMappingExclusions() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(107);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDocumentRoot_SourceContributorId() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(108);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDocumentRoot_TargetContributorId() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(109);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_RestServiceOperation() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(110);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_InputMappings() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(111);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_OutputMappings() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(112);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDocumentRoot_BusinessServicePublishType() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(113);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDocumentRoot_SuppressMaxMappingsError() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(114);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDocumentRoot_FieldFormat() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(115);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_ActivityDeadlineEventId() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(73);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_StartStrategy() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(74);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_OverwriteAlreadyModifiedTaskData() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(75);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_EventHandlerFlowStrategy() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(76);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_NamespacePrefixMap() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(77);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_SuspendResumeWithParent() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(78);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_SystemErrorAction() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(79);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_CorrelationTimeout() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(80);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_ValidationControl() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(81);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentRoot_ReplyImmediateDataMappings() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(82);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_BxUseUnqualifiedPropertyNames() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(83);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getDurationCalculation() {
        return durationCalculationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDurationCalculation_Years() {
        return (EReference) durationCalculationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDurationCalculation_Months() {
        return (EReference) durationCalculationEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDurationCalculation_Weeks() {
        return (EReference) durationCalculationEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDurationCalculation_Days() {
        return (EReference) durationCalculationEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDurationCalculation_Hours() {
        return (EReference) durationCalculationEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDurationCalculation_Minutes() {
        return (EReference) durationCalculationEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDurationCalculation_Seconds() {
        return (EReference) durationCalculationEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDurationCalculation_Microseconds() {
        return (EReference) durationCalculationEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getDynamicOrganizationMappings() {
        return dynamicOrganizationMappingsEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDynamicOrganizationMappings_DynamicOrganizationMapping() {
        return (EReference) dynamicOrganizationMappingsEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getDynamicOrganizationMapping() {
        return dynamicOrganizationMappingEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDynamicOrganizationMapping_SourcePath() {
        return (EAttribute) dynamicOrganizationMappingEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDynamicOrganizationMapping_DynamicOrgIdentifierRef() {
        return (EReference) dynamicOrganizationMappingEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getDynamicOrgIdentifierRef() {
        return dynamicOrgIdentifierRefEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDynamicOrgIdentifierRef_IdentifierName() {
        return (EAttribute) dynamicOrgIdentifierRefEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDynamicOrgIdentifierRef_DynamicOrgId() {
        return (EAttribute) dynamicOrgIdentifierRefEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDynamicOrgIdentifierRef_OrgModelPath() {
        return (EAttribute) dynamicOrgIdentifierRefEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getErrorMethod() {
        return errorMethodEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getErrorMethod_ErrorCode() {
        return (EAttribute) errorMethodEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getXpdExtDataObjectAttributes() {
        return xpdExtDataObjectAttributesEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getXpdExtDataObjectAttributes_Description() {
        return (EAttribute) xpdExtDataObjectAttributesEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getXpdExtDataObjectAttributes_ExternalReference() {
        return (EAttribute) xpdExtDataObjectAttributesEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getXpdExtDataObjectAttributes_Properties() {
        return (EReference) xpdExtDataObjectAttributesEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getXpdExtProperty() {
        return xpdExtPropertyEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getXpdExtProperty_Name() {
        return (EAttribute) xpdExtPropertyEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getXpdExtProperty_Value() {
        return (EAttribute) xpdExtPropertyEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getXpdExtAttribute() {
        return xpdExtAttributeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getXpdExtAttribute_Mixed() {
        return (EAttribute) xpdExtAttributeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getXpdExtAttribute_Group() {
        return (EAttribute) xpdExtAttributeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getXpdExtAttribute_Any() {
        return (EAttribute) xpdExtAttributeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getXpdExtAttribute_Name() {
        return (EAttribute) xpdExtAttributeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getXpdExtAttribute_Value() {
        return (EAttribute) xpdExtAttributeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getXpdExtAttributes() {
        return xpdExtAttributesEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getXpdExtAttributes_Attributes() {
        return (EReference) xpdExtAttributesEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getUpdateCaseOperationType() {
        return updateCaseOperationTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getUpdateCaseOperationType_FromFieldPath() {
        return (EAttribute) updateCaseOperationTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getAddLinkAssociationsType() {
        return addLinkAssociationsTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getAddLinkAssociationsType_AddCaseRefField() {
        return (EAttribute) addLinkAssociationsTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getAddLinkAssociationsType_AssociationName() {
        return (EAttribute) addLinkAssociationsTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getRemoveLinkAssociationsType() {
        return removeLinkAssociationsTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getRemoveLinkAssociationsType_AssociationName() {
        return (EAttribute) removeLinkAssociationsTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getRemoveLinkAssociationsType_RemoveCaseRefField() {
        return (EAttribute) removeLinkAssociationsTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getCaseReferenceOperationsType() {
        return caseReferenceOperationsTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getCaseReferenceOperationsType_CaseRefField() {
        return (EAttribute) caseReferenceOperationsTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getCaseReferenceOperationsType_Update() {
        return (EReference) caseReferenceOperationsTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getCaseReferenceOperationsType_Delete() {
        return (EReference) caseReferenceOperationsTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getCaseReferenceOperationsType_AddLinkAssociations() {
        return (EReference) caseReferenceOperationsTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getCaseReferenceOperationsType_RemoveLinkAssociations() {
        return (EReference) caseReferenceOperationsTypeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getGlobalDataOperation() {
        return globalDataOperationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getGlobalDataOperation_CaseAccessOperations() {
        return (EReference) globalDataOperationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getGlobalDataOperation_CaseReferenceOperations() {
        return (EReference) globalDataOperationEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getDeleteByCaseIdentifierType() {
        return deleteByCaseIdentifierTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDeleteByCaseIdentifierType_FieldPath() {
        return (EAttribute) deleteByCaseIdentifierTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDeleteByCaseIdentifierType_IdentifierName() {
        return (EAttribute) deleteByCaseIdentifierTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getCompositeIdentifierType() {
        return compositeIdentifierTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getCompositeIdentifierType_FieldPath() {
        return (EAttribute) compositeIdentifierTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getCompositeIdentifierType_Identifiername() {
        return (EAttribute) compositeIdentifierTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getDeleteCaseReferenceOperationType() {
        return deleteCaseReferenceOperationTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getDeleteByCompositeIdentifiersType() {
        return deleteByCompositeIdentifiersTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDeleteByCompositeIdentifiersType_Group() {
        return (EAttribute) deleteByCompositeIdentifiersTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDeleteByCompositeIdentifiersType_CompositeIdentifier() {
        return (EReference) deleteByCompositeIdentifiersTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getCreateCaseOperationType() {
        return createCaseOperationTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getCreateCaseOperationType_FromFieldPath() {
        return (EAttribute) createCaseOperationTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getCreateCaseOperationType_ToCaseRefField() {
        return (EAttribute) createCaseOperationTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getCaseAccessOperationsType() {
        return caseAccessOperationsTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getCaseAccessOperationsType_CaseClassExternalReference() {
        return (EReference) caseAccessOperationsTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getCaseAccessOperationsType_Create() {
        return (EReference) caseAccessOperationsTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getCaseAccessOperationsType_DeleteByCaseIdentifier() {
        return (EReference) caseAccessOperationsTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getCaseAccessOperationsType_DeleteByCompositeIdentifiers() {
        return (EReference) caseAccessOperationsTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getDataWorkItemAttributeMapping() {
        return dataWorkItemAttributeMappingEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDataWorkItemAttributeMapping_Attribute() {
        return (EAttribute) dataWorkItemAttributeMappingEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDataWorkItemAttributeMapping_ProcessData() {
        return (EAttribute) dataWorkItemAttributeMappingEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getProcessDataWorkItemAttributeMappings() {
        return processDataWorkItemAttributeMappingsEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getProcessDataWorkItemAttributeMappings_DataWorkItemAttributeMapping() {
        return (EReference) processDataWorkItemAttributeMappingsEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getBpmRuntimeConfiguration() {
        return bpmRuntimeConfigurationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getBpmRuntimeConfiguration_IncomingRequestTimeout() {
        return (EAttribute) bpmRuntimeConfigurationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getEnablementType() {
        return enablementTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getEnablementType_InitializerActivities() {
        return (EReference) enablementTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getEnablementType_PreconditionExpression() {
        return (EReference) enablementTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getInitializerActivitiesType() {
        return initializerActivitiesTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getInitializerActivitiesType_ActivityRef() {
        return (EReference) initializerActivitiesTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getAdHocTaskConfigurationType() {
        return adHocTaskConfigurationTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getAdHocTaskConfigurationType_Enablement() {
        return (EReference) adHocTaskConfigurationTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getAdHocTaskConfigurationType_AdHocExecutionType() {
        return (EAttribute) adHocTaskConfigurationTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getAdHocTaskConfigurationType_SuspendMainFlow() {
        return (EAttribute) adHocTaskConfigurationTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getAdHocTaskConfigurationType_AllowMultipleInvocations() {
        return (EAttribute) adHocTaskConfigurationTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getAdHocTaskConfigurationType_RequiredAccessPrivileges() {
        return (EReference) adHocTaskConfigurationTypeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getRequiredAccessPrivileges() {
        return requiredAccessPrivilegesEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getRequiredAccessPrivileges_PrivilegeReference() {
        return (EReference) requiredAccessPrivilegesEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getVisibleForCaseStates() {
        return visibleForCaseStatesEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getVisibleForCaseStates_VisibleForUnsetCaseState() {
        return (EAttribute) visibleForCaseStatesEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getVisibleForCaseStates_CaseState() {
        return (EReference) visibleForCaseStatesEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getCaseService() {
        return caseServiceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getCaseService_CaseClassType() {
        return (EReference) caseServiceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getCaseService_VisibleForCaseStates() {
        return (EReference) caseServiceEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getDocumentOperation() {
        return documentOperationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentOperation_CaseDocRefOperation() {
        return (EReference) documentOperationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentOperation_CaseDocFindOperations() {
        return (EReference) documentOperationEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDocumentOperation_LinkSystemDocumentOperation() {
        return (EReference) documentOperationEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getCaseDocRefOperations() {
        return caseDocRefOperationsEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getCaseDocRefOperations_MoveCaseDocOperation() {
        return (EReference) caseDocRefOperationsEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getCaseDocRefOperations_UnlinkCaseDocOperation() {
        return (EReference) caseDocRefOperationsEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getCaseDocRefOperations_LinkCaseDocOperation() {
        return (EReference) caseDocRefOperationsEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getCaseDocRefOperations_DeleteCaseDocOperation() {
        return (EReference) caseDocRefOperationsEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getCaseDocRefOperations_CaseDocumentRefField() {
        return (EAttribute) caseDocRefOperationsEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getCaseDocFindOperations() {
        return caseDocFindOperationsEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getCaseDocFindOperations_FindByFileNameOperation() {
        return (EReference) caseDocFindOperationsEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getCaseDocFindOperations_FindByQueryOperation() {
        return (EReference) caseDocFindOperationsEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getCaseDocFindOperations_ReturnCaseDocRefsField() {
        return (EAttribute) caseDocFindOperationsEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getCaseDocFindOperations_CaseRefField() {
        return (EAttribute) caseDocFindOperationsEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getMoveCaseDocOperation() {
        return moveCaseDocOperationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getMoveCaseDocOperation_SourceCaseRefField() {
        return (EAttribute) moveCaseDocOperationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getMoveCaseDocOperation_TargetCaseRefField() {
        return (EAttribute) moveCaseDocOperationEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getUnlinkCaseDocOperation() {
        return unlinkCaseDocOperationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getUnlinkCaseDocOperation_SourceCaseRefField() {
        return (EAttribute) unlinkCaseDocOperationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getLinkCaseDocOperation() {
        return linkCaseDocOperationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getLinkCaseDocOperation_TargetCaseRefField() {
        return (EAttribute) linkCaseDocOperationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getLinkSystemDocumentOperation() {
        return linkSystemDocumentOperationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getLinkSystemDocumentOperation_DocumentId() {
        return (EAttribute) linkSystemDocumentOperationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getLinkSystemDocumentOperation_ReturnCaseDocRefField() {
        return (EAttribute) linkSystemDocumentOperationEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getLinkSystemDocumentOperation_CaseRefField() {
        return (EAttribute) linkSystemDocumentOperationEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getDeleteCaseDocOperation() {
        return deleteCaseDocOperationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getFindByFileNameOperation() {
        return findByFileNameOperationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getFindByFileNameOperation_FileNameField() {
        return (EAttribute) findByFileNameOperationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getFindByQueryOperation() {
        return findByQueryOperationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getFindByQueryOperation_CaseDocumentQueryExpression() {
        return (EReference) findByQueryOperationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getCaseDocumentQueryExpression() {
        return caseDocumentQueryExpressionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getCaseDocumentQueryExpression_QueryExpressionJoinType() {
        return (EAttribute) caseDocumentQueryExpressionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCaseDocumentQueryExpression_OpenBracketCount() {
        return (EAttribute) caseDocumentQueryExpressionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getCaseDocumentQueryExpression_Operator() {
        return (EAttribute) caseDocumentQueryExpressionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getCaseDocumentQueryExpression_CmisProperty() {
        return (EAttribute) caseDocumentQueryExpressionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getCaseDocumentQueryExpression_ProcessDataField() {
        return (EAttribute) caseDocumentQueryExpressionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCaseDocumentQueryExpression_CloseBracketCount() {
        return (EAttribute) caseDocumentQueryExpressionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCaseDocumentQueryExpression_CmisDocumentPropertySelected() {
        return (EAttribute) caseDocumentQueryExpressionEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getServiceProcessConfiguration() {
        return serviceProcessConfigurationEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getServiceProcessConfiguration_DeployToProcessRuntime() {
        return (EAttribute) serviceProcessConfigurationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getServiceProcessConfiguration_DeployToPageflowRuntime() {
        return (EAttribute) serviceProcessConfigurationEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getScriptDataMapper() {
        return scriptDataMapperEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getScriptDataMapper_MapperContext() {
        return (EAttribute) scriptDataMapperEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getScriptDataMapper_MappingDirection() {
        return (EAttribute) scriptDataMapperEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getScriptDataMapper_DataMappings() {
        return (EReference) scriptDataMapperEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getScriptDataMapper_UnmappedScripts() {
        return (EReference) scriptDataMapperEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getScriptDataMapper_ArrayInflationType() {
        return (EReference) scriptDataMapperEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDataMapperArrayInflation() {
        return dataMapperArrayInflationEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDataMapperArrayInflation_Path() {
        return (EAttribute) dataMapperArrayInflationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDataMapperArrayInflation_MappingType() {
        return (EAttribute) dataMapperArrayInflationEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDataMapperArrayInflation_ContributorId() {
        return (EAttribute) dataMapperArrayInflationEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getLikeMappingExclusions() {
        return likeMappingExclusionsEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getLikeMappingExclusions_Exclusions() {
        return (EReference) likeMappingExclusionsEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getRestServiceOperation() {
        return restServiceOperationEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRestServiceOperation_Location() {
        return (EAttribute) restServiceOperationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRestServiceOperation_MethodId() {
        return (EAttribute) restServiceOperationEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getLikeMappingExclusion() {
        return likeMappingExclusionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLikeMappingExclusion_Path() {
        return (EAttribute) likeMappingExclusionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getRESTServices() {
        return restServicesEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getRESTServices_RESTServices() {
        return (EReference) restServicesEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getRestServiceResource() {
        return restServiceResourceEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getRestServiceResource_SecurityPolicy() {
        return (EReference) restServiceResourceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRestServiceResource_ResourceName() {
        return (EAttribute) restServiceResourceEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRestServiceResource_ResourceType() {
        return (EAttribute) restServiceResourceEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRestServiceResource_Description() {
        return (EAttribute) restServiceResourceEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getRestServiceResourceSecurity() {
        return restServiceResourceSecurityEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRestServiceResourceSecurity_PolicyType() {
        return (EAttribute) restServiceResourceSecurityEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getWsdlGeneration() {
        return wsdlGenerationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getWsdlGeneration_SoapBindingStyle() {
        return (EAttribute) wsdlGenerationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getEmailResource() {
        return emailResourceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getEmailResource_InstanceName() {
        return (EAttribute) emailResourceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getJdbcResource() {
        return jdbcResourceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getJdbcResource_InstanceName() {
        return (EAttribute) jdbcResourceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getJdbcResource_JdbcProfileName() {
        return (EAttribute) jdbcResourceEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getParticipantSharedResource() {
        return participantSharedResourceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getParticipantSharedResource_Email() {
        return (EReference) participantSharedResourceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getParticipantSharedResource_Jdbc() {
        return (EReference) participantSharedResourceEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getParticipantSharedResource_WebService() {
        return (EReference) participantSharedResourceEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getParticipantSharedResource_RestService() {
        return (EReference) participantSharedResourceEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getWsBinding() {
        return wsBindingEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getWsBinding_Name() {
        return (EAttribute) wsBindingEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getWsBinding_ExtendedProperties() {
        return (EReference) wsBindingEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getWsInbound() {
        return wsInboundEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getWsInbound_VirtualBinding() {
        return (EReference) wsInboundEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getWsInbound_SoapHttpBinding() {
        return (EReference) wsInboundEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getWsInbound_SoapJmsBinding() {
        return (EReference) wsInboundEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getWsOutbound() {
        return wsOutboundEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getWsOutbound_VirtualBinding() {
        return (EReference) wsOutboundEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getWsOutbound_SoapHttpBinding() {
        return (EReference) wsOutboundEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getWsOutbound_SoapJmsBinding() {
        return (EReference) wsOutboundEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getWsResource() {
        return wsResourceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getWsResource_Inbound() {
        return (EReference) wsResourceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getWsResource_Outbound() {
        return (EReference) wsResourceEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getWsSecurityPolicy() {
        return wsSecurityPolicyEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getWsSecurityPolicy_GovernanceApplicationName() {
        return (EAttribute) wsSecurityPolicyEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getWsSecurityPolicy_Type() {
        return (EAttribute) wsSecurityPolicyEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getWsSoapBinding() {
        return wsSoapBindingEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getWsSoapBinding_BindingStyle() {
        return (EAttribute) wsSoapBindingEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getWsSoapBinding_SoapVersion() {
        return (EAttribute) wsSoapBindingEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getWsSoapBinding_SoapSecurity() {
        return (EReference) wsSoapBindingEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getWsSoapHttpInboundBinding() {
        return wsSoapHttpInboundBindingEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getWsSoapHttpInboundBinding_InboundSecurity() {
        return (EReference) wsSoapHttpInboundBindingEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getWsSoapHttpInboundBinding_EndpointUrlPath() {
        return (EAttribute) wsSoapHttpInboundBindingEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getWsSoapHttpInboundBinding_HttpConnectorInstanceName() {
        return (EAttribute) wsSoapHttpInboundBindingEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getWsSoapHttpOutboundBinding() {
        return wsSoapHttpOutboundBindingEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getWsSoapHttpOutboundBinding_OutboundSecurity() {
        return (EReference) wsSoapHttpOutboundBindingEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getWsSoapHttpOutboundBinding_HttpClientInstanceName() {
        return (EAttribute) wsSoapHttpOutboundBindingEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getWsSoapJmsInboundBinding() {
        return wsSoapJmsInboundBindingEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getWsSoapJmsInboundBinding_OutboundConnectionFactory() {
        return (EAttribute) wsSoapJmsInboundBindingEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getWsSoapJmsInboundBinding_InboundConnectionFactoryConfiguration() {
        return (EAttribute) wsSoapJmsInboundBindingEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getWsSoapJmsInboundBinding_InboundDestination() {
        return (EAttribute) wsSoapJmsInboundBindingEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getWsSoapJmsInboundBinding_InboundSecurity() {
        return (EReference) wsSoapJmsInboundBindingEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getWsSoapJmsOutboundBinding() {
        return wsSoapJmsOutboundBindingEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getWsSoapJmsOutboundBinding_OutboundConnectionFactory() {
        return (EAttribute) wsSoapJmsOutboundBindingEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getWsSoapJmsOutboundBinding_InboundDestination() {
        return (EAttribute) wsSoapJmsOutboundBindingEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWsSoapJmsOutboundBinding_DeliveryMode() {
        return (EAttribute) wsSoapJmsOutboundBindingEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWsSoapJmsOutboundBinding_Priority() {
        return (EAttribute) wsSoapJmsOutboundBindingEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWsSoapJmsOutboundBinding_MessageExpiration() {
        return (EAttribute) wsSoapJmsOutboundBindingEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWsSoapJmsOutboundBinding_InvocationTimeout() {
        return (EAttribute) wsSoapJmsOutboundBindingEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getWsSoapJmsOutboundBinding_OutboundDestination() {
        return (EAttribute) wsSoapJmsOutboundBindingEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getWsSoapJmsOutboundBinding_OutboundSecurity() {
        return (EReference) wsSoapJmsOutboundBindingEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getWsSoapSecurity() {
        return wsSoapSecurityEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getWsSoapSecurity_SecurityPolicy() {
        return (EReference) wsSoapSecurityEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getWsVirtualBinding() {
        return wsVirtualBindingEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getBusinessProcess() {
        return businessProcessEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getBusinessProcess_ProcessId() {
        return (EAttribute) businessProcessEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getBusinessProcess_ActivityId() {
        return (EAttribute) businessProcessEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getBusinessProcess_PackageRefId() {
        return (EAttribute) businessProcessEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getCalendarReference() {
        return calendarReferenceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getCalendarReference_Alias() {
        return (EAttribute) calendarReferenceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getCalendarReference_DataFieldId() {
        return (EAttribute) calendarReferenceEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getStructuredDiscriminator() {
        return structuredDiscriminatorEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getStructuredDiscriminator_WaitForIncomingPath() {
        return (EAttribute) structuredDiscriminatorEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getStructuredDiscriminator_UpStreamParallelSplit() {
        return (EAttribute) structuredDiscriminatorEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getTaskLibraryReference() {
        return taskLibraryReferenceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getTaskLibraryReference_TaskLibraryId() {
        return (EAttribute) taskLibraryReferenceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getTaskLibraryReference_PackageRef() {
        return (EAttribute) taskLibraryReferenceEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getDiscriminator() {
        return discriminatorEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getDiscriminator_DiscriminatorType() {
        return (EAttribute) discriminatorEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getDiscriminator_StructuredDiscriminator() {
        return (EReference) discriminatorEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getTransformScript() {
        return transformScriptEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getTransformScript_DataMappings() {
        return (EReference) transformScriptEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getTransformScript_InputDom() {
        return (EAttribute) transformScriptEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getTransformScript_OutputDom() {
        return (EAttribute) transformScriptEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getConditionalParticipant() {
        return conditionalParticipantEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getConditionalParticipant_PerformerScript() {
        return (EReference) conditionalParticipantEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getReplyImmediateDataMappings() {
        return replyImmediateDataMappingsEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getReplyImmediateDataMappings_DataMappings() {
        return (EReference) replyImmediateDataMappingsEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EEnum getAllocationStrategyType() {
        return allocationStrategyTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getInitialParameterValue() {
        return initialParameterValueEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getInitialParameterValue_Name() {
        return (EAttribute) initialParameterValueEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getInitialParameterValue_Value() {
        return (EAttribute) initialParameterValueEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getInterfaceMethod() {
        return interfaceMethodEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getInterfaceMethod_Trigger() {
        return (EAttribute) interfaceMethodEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getInterfaceMethod_TriggerResultMessage() {
        return (EReference) interfaceMethodEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getInterfaceMethod_Visibility() {
        return (EAttribute) interfaceMethodEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getInterfaceMethod_ErrorMethods() {
        return (EReference) interfaceMethodEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getConstantPeriod() {
        return constantPeriodEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getConstantPeriod_Days() {
        return (EAttribute) constantPeriodEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getConstantPeriod_Hours() {
        return (EAttribute) constantPeriodEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getConstantPeriod_MicroSeconds() {
        return (EAttribute) constantPeriodEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getConstantPeriod_Minutes() {
        return (EAttribute) constantPeriodEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getConstantPeriod_Months() {
        return (EAttribute) constantPeriodEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getConstantPeriod_Seconds() {
        return (EAttribute) constantPeriodEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getConstantPeriod_Weeks() {
        return (EAttribute) constantPeriodEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getConstantPeriod_Years() {
        return (EAttribute) constantPeriodEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getCorrelationDataMappings() {
        return correlationDataMappingsEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getCorrelationDataMappings_DataMappings() {
        return (EReference) correlationDataMappingsEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getUserTaskScripts() {
        return userTaskScriptsEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getUserTaskScripts_OpenScript() {
        return (EReference) userTaskScriptsEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getUserTaskScripts_CloseScript() {
        return (EReference) userTaskScriptsEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getUserTaskScripts_SubmitScript() {
        return (EReference) userTaskScriptsEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getUserTaskScripts_ScheduleScript() {
        return (EReference) userTaskScriptsEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getUserTaskScripts_RescheduleScript() {
        return (EReference) userTaskScriptsEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getValidationControl() {
        return validationControlEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getValidationControl_ValidationIssueOverrides() {
        return (EReference) validationControlEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getValidationIssueOverride() {
        return validationIssueOverrideEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getValidationIssueOverride_ValidationIssueId() {
        return (EAttribute) validationIssueOverrideEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getValidationIssueOverride_OverrideType() {
        return (EAttribute) validationIssueOverrideEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getAuditEvent() {
        return auditEventEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getAuditEvent_Type() {
        return (EAttribute) auditEventEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getAuditEvent_Information() {
        return (EReference) auditEventEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getCatchErrorMappings() {
        return catchErrorMappingsEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getCatchErrorMappings_Message() {
        return (EReference) catchErrorMappingsEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getErrorThrowerInfo() {
        return errorThrowerInfoEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getErrorThrowerInfo_ThrowerId() {
        return (EAttribute) errorThrowerInfoEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getErrorThrowerInfo_ThrowerContainerId() {
        return (EAttribute) errorThrowerInfoEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getErrorThrowerInfo_ThrowerType() {
        return (EAttribute) errorThrowerInfoEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getEventHandlerInitialisers() {
        return eventHandlerInitialisersEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getEventHandlerInitialisers_ActivityRef() {
        return (EReference) eventHandlerInitialisersEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getFaultMessage() {
        return faultMessageEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getFormImplementation() {
        return formImplementationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getFormImplementation_FormType() {
        return (EAttribute) formImplementationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getFormImplementation_FormURI() {
        return (EAttribute) formImplementationEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getAudit() {
        return auditEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getAudit_AuditEvent() {
        return (EReference) auditEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getAudit_Any() {
        return (EAttribute) auditEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getScriptInformation() {
        return scriptInformationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getScriptInformation_Expression() {
        return (EReference) scriptInformationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getScriptInformation_Direction() {
        return (EAttribute) scriptInformationEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getScriptInformation_Activity() {
        return (EReference) scriptInformationEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getScriptInformation_Reference() {
        return (EAttribute) scriptInformationEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getInitialValues() {
        return initialValuesEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getInitialValues_Value() {
        return (EAttribute) initialValuesEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getIntermediateMethod() {
        return intermediateMethodEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getImplementedInterface() {
        return implementedInterfaceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getImplementedInterface_PackageRef() {
        return (EAttribute) implementedInterfaceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getImplementedInterface_ProcessInterfaceId() {
        return (EAttribute) implementedInterfaceEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getAssociatedParametersContainer() {
        return associatedParametersContainerEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getAssociatedParametersContainer_AssociatedParameters() {
        return (EReference) associatedParametersContainerEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getAssociatedParametersContainer_DisableImplicitAssociation() {
        return (EAttribute) associatedParametersContainerEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getAssociatedParameter() {
        return associatedParameterEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getAssociatedParameter_FormalParam() {
        return (EAttribute) associatedParameterEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getAssociatedParameter_Mode() {
        return (EAttribute) associatedParameterEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getAssociatedParameter_Mandatory() {
        return (EAttribute) associatedParameterEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getAssociatedParameters() {
        return associatedParametersEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getAssociatedParameters_AssociatedParameter() {
        return (EReference) associatedParametersEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getAssociatedParameters_DisableImplicitAssociation() {
        return (EAttribute) associatedParametersEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getProcessInterfaces() {
        return processInterfacesEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getProcessInterfaces_ProcessInterface() {
        return (EReference) processInterfacesEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getProcessResourcePatterns() {
        return processResourcePatternsEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getProcessResourcePatterns_SeparationOfDutiesActivities() {
        return (EReference) processResourcePatternsEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getProcessResourcePatterns_RetainFamiliarActivities() {
        return (EReference) processResourcePatternsEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getRescheduleTimerScript() {
        return rescheduleTimerScriptEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getRescheduleTimerScript_DurationRelativeTo() {
        return (EAttribute) rescheduleTimerScriptEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getRescheduleTimers() {
        return rescheduleTimersEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getRescheduleTimers_TimerSelectionType() {
        return (EAttribute) rescheduleTimersEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getRescheduleTimers_TimerEvents() {
        return (EReference) rescheduleTimersEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getSeparationOfDutiesActivities() {
        return separationOfDutiesActivitiesEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getSeparationOfDutiesActivities_ActivityRef() {
        return (EReference) separationOfDutiesActivitiesEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getSignalData() {
        return signalDataEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSignalData_CorrelationMappings() {
        return (EReference) signalDataEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getSignalData_DataMappings() {
        return (EReference) signalDataEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getSignalData_RescheduleTimers() {
        return (EReference) signalDataEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSignalData_InputScriptDataMapper() {
        return (EReference) signalDataEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSignalData_OutputScriptDataMapper() {
        return (EReference) signalDataEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getRetainFamiliarActivities() {
        return retainFamiliarActivitiesEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getRetainFamiliarActivities_ActivityRef() {
        return (EReference) retainFamiliarActivitiesEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getRetry() {
        return retryEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getRetry_Max() {
        return (EAttribute) retryEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getRetry_InitialPeriod() {
        return (EAttribute) retryEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getRetry_PeriodIncrement() {
        return (EAttribute) retryEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getRetry_MaxRetryAction() {
        return (EAttribute) retryEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getStartMethod() {
        return startMethodEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getProcessInterface() {
        return processInterfaceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getProcessInterface_StartMethods() {
        return (EReference) processInterfaceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getProcessInterface_IntermediateMethods() {
        return (EReference) processInterfaceEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getProcessInterface_XpdInterfaceType() {
        return (EAttribute) processInterfaceEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getProcessInterface_ServiceProcessConfiguration() {
        return (EReference) processInterfaceEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getMultiInstanceScripts() {
        return multiInstanceScriptsEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getMultiInstanceScripts_AdditionalInstances() {
        return (EReference) multiInstanceScriptsEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getNamespacePrefixMap() {
        return namespacePrefixMapEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getNamespacePrefixMap_NamespaceEntries() {
        return (EReference) namespacePrefixMapEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getNamespacePrefixMap_PrefixMappingDisabled() {
        return (EAttribute) namespacePrefixMapEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getNamespaceMapEntry() {
        return namespaceMapEntryEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getNamespaceMapEntry_Prefix() {
        return (EAttribute) namespaceMapEntryEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getNamespaceMapEntry_Namespace() {
        return (EAttribute) namespaceMapEntryEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getWorkItemPriority() {
        return workItemPriorityEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getWorkItemPriority_InitialPriority() {
        return (EAttribute) workItemPriorityEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getPilingInfo() {
        return pilingInfoEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getPilingInfo_PilingAllowed() {
        return (EAttribute) pilingInfoEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getPilingInfo_MaxPiliableItems() {
        return (EAttribute) pilingInfoEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getPortTypeOperation() {
        return portTypeOperationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getPortTypeOperation_PortTypeName() {
        return (EAttribute) portTypeOperationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getPortTypeOperation_OperationName() {
        return (EAttribute) portTypeOperationEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getPortTypeOperation_ExternalReference() {
        return (EReference) portTypeOperationEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getPortTypeOperation_Transport() {
        return (EAttribute) portTypeOperationEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getWsdlEventAssociation() {
        return wsdlEventAssociationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getWsdlEventAssociation_EventId() {
        return (EAttribute) wsdlEventAssociationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EEnum getAuditEventType() {
        return auditEventTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EEnum getCorrelationMode() {
        return correlationModeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EEnum getErrorThrowerType() {
        return errorThrowerTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EEnum getEventHandlerFlowStrategy() {
        return eventHandlerFlowStrategyEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EEnum getFlowRoutingStyle() {
        return flowRoutingStyleEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EEnum getFormImplementationType() {
        return formImplementationTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EEnum getMaxRetryActionType() {
        return maxRetryActionTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getDeliveryMode() {
        return deliveryModeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EEnum getRescheduleDurationType() {
        return rescheduleDurationTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EEnum getRescheduleTimerSelectionType() {
        return rescheduleTimerSelectionTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EEnum getVisibility() {
        return visibilityEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EEnum getXpdModelType() {
        return xpdModelTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EEnum getAdHocExecutionTypeType() {
        return adHocExecutionTypeTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EEnum getQueryExpressionJoinType() {
        return queryExpressionJoinTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EEnum getCMISQueryOperator() {
        return cmisQueryOperatorEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getAsyncExecutionMode() {
        return asyncExecutionModeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getSignalType() {
        return signalTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getXpdInterfaceType() {
        return xpdInterfaceTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getDataMapperArrayInflationType() {
        return dataMapperArrayInflationTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getBusinessServicePublishType() {
        return businessServicePublishTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EEnum getSecurityPolicy() {
        return securityPolicyEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EEnum getSoapBindingStyle() {
        return soapBindingStyleEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getFieldFormat() {
        return fieldFormatEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EEnum getSubProcessStartStrategy() {
        return subProcessStartStrategyEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EEnum getSystemErrorActionType() {
        return systemErrorActionTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EEnum getValidationIssueOverrideType() {
        return validationIssueOverrideTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EEnum getAllocationType() {
        return allocationTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EDataType getAuditEventTypeObject() {
        return auditEventTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EDataType getSecurityPolicyObject() {
        return securityPolicyObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EDataType getSoapBindingStyleObject() {
        return soapBindingStyleObjectEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public XpdExtensionFactory getXpdExtensionFactory() {
        return (XpdExtensionFactory) getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package.  This method is
     * guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void createPackageContents() {
        if (isCreated)
            return;
        isCreated = true;

        // Create classes and their features
        activityRefEClass = createEClass(ACTIVITY_REF);
        createEAttribute(activityRefEClass, ACTIVITY_REF__ID_REF);

        activityResourcePatternsEClass = createEClass(ACTIVITY_RESOURCE_PATTERNS);
        createEReference(activityResourcePatternsEClass, ACTIVITY_RESOURCE_PATTERNS__ALLOCATION_STRATEGY);
        createEReference(activityResourcePatternsEClass, ACTIVITY_RESOURCE_PATTERNS__PILING);
        createEReference(activityResourcePatternsEClass, ACTIVITY_RESOURCE_PATTERNS__WORK_ITEM_PRIORITY);

        allocationStrategyEClass = createEClass(ALLOCATION_STRATEGY);
        createEAttribute(allocationStrategyEClass, ALLOCATION_STRATEGY__OFFER);
        createEAttribute(allocationStrategyEClass, ALLOCATION_STRATEGY__STRATEGY);
        createEAttribute(allocationStrategyEClass, ALLOCATION_STRATEGY__RE_OFFER_ON_CLOSE);
        createEAttribute(allocationStrategyEClass, ALLOCATION_STRATEGY__RE_OFFER_ON_CANCEL);
        createEAttribute(allocationStrategyEClass, ALLOCATION_STRATEGY__ALLOCATE_TO_OFFER_SET_MEMBER_IDENTIFIER);

        associatedCorrelationFieldsEClass = createEClass(ASSOCIATED_CORRELATION_FIELDS);
        createEReference(associatedCorrelationFieldsEClass,
                ASSOCIATED_CORRELATION_FIELDS__ASSOCIATED_CORRELATION_FIELD);
        createEAttribute(associatedCorrelationFieldsEClass,
                ASSOCIATED_CORRELATION_FIELDS__DISABLE_IMPLICIT_ASSOCIATION);

        associatedCorrelationFieldEClass = createEClass(ASSOCIATED_CORRELATION_FIELD);
        createEAttribute(associatedCorrelationFieldEClass, ASSOCIATED_CORRELATION_FIELD__NAME);
        createEAttribute(associatedCorrelationFieldEClass, ASSOCIATED_CORRELATION_FIELD__CORRELATION_MODE);

        associatedParameterEClass = createEClass(ASSOCIATED_PARAMETER);
        createEAttribute(associatedParameterEClass, ASSOCIATED_PARAMETER__FORMAL_PARAM);
        createEAttribute(associatedParameterEClass, ASSOCIATED_PARAMETER__MODE);
        createEAttribute(associatedParameterEClass, ASSOCIATED_PARAMETER__MANDATORY);

        associatedParametersEClass = createEClass(ASSOCIATED_PARAMETERS);
        createEReference(associatedParametersEClass, ASSOCIATED_PARAMETERS__ASSOCIATED_PARAMETER);
        createEAttribute(associatedParametersEClass, ASSOCIATED_PARAMETERS__DISABLE_IMPLICIT_ASSOCIATION);

        associatedParametersContainerEClass = createEClass(ASSOCIATED_PARAMETERS_CONTAINER);
        createEReference(associatedParametersContainerEClass, ASSOCIATED_PARAMETERS_CONTAINER__ASSOCIATED_PARAMETERS);
        createEAttribute(associatedParametersContainerEClass,
                ASSOCIATED_PARAMETERS_CONTAINER__DISABLE_IMPLICIT_ASSOCIATION);

        auditEClass = createEClass(AUDIT);
        createEReference(auditEClass, AUDIT__AUDIT_EVENT);
        createEAttribute(auditEClass, AUDIT__ANY);

        auditEventEClass = createEClass(AUDIT_EVENT);
        createEAttribute(auditEventEClass, AUDIT_EVENT__TYPE);
        createEReference(auditEventEClass, AUDIT_EVENT__INFORMATION);

        businessProcessEClass = createEClass(BUSINESS_PROCESS);
        createEAttribute(businessProcessEClass, BUSINESS_PROCESS__PROCESS_ID);
        createEAttribute(businessProcessEClass, BUSINESS_PROCESS__PACKAGE_REF_ID);
        createEAttribute(businessProcessEClass, BUSINESS_PROCESS__ACTIVITY_ID);

        calendarReferenceEClass = createEClass(CALENDAR_REFERENCE);
        createEAttribute(calendarReferenceEClass, CALENDAR_REFERENCE__ALIAS);
        createEAttribute(calendarReferenceEClass, CALENDAR_REFERENCE__DATA_FIELD_ID);

        catchErrorMappingsEClass = createEClass(CATCH_ERROR_MAPPINGS);
        createEReference(catchErrorMappingsEClass, CATCH_ERROR_MAPPINGS__MESSAGE);

        constantPeriodEClass = createEClass(CONSTANT_PERIOD);
        createEAttribute(constantPeriodEClass, CONSTANT_PERIOD__DAYS);
        createEAttribute(constantPeriodEClass, CONSTANT_PERIOD__HOURS);
        createEAttribute(constantPeriodEClass, CONSTANT_PERIOD__MICRO_SECONDS);
        createEAttribute(constantPeriodEClass, CONSTANT_PERIOD__MINUTES);
        createEAttribute(constantPeriodEClass, CONSTANT_PERIOD__MONTHS);
        createEAttribute(constantPeriodEClass, CONSTANT_PERIOD__SECONDS);
        createEAttribute(constantPeriodEClass, CONSTANT_PERIOD__WEEKS);
        createEAttribute(constantPeriodEClass, CONSTANT_PERIOD__YEARS);

        conditionalParticipantEClass = createEClass(CONDITIONAL_PARTICIPANT);
        createEReference(conditionalParticipantEClass, CONDITIONAL_PARTICIPANT__PERFORMER_SCRIPT);

        replyImmediateDataMappingsEClass = createEClass(REPLY_IMMEDIATE_DATA_MAPPINGS);
        createEReference(replyImmediateDataMappingsEClass, REPLY_IMMEDIATE_DATA_MAPPINGS__DATA_MAPPINGS);

        correlationDataMappingsEClass = createEClass(CORRELATION_DATA_MAPPINGS);
        createEReference(correlationDataMappingsEClass, CORRELATION_DATA_MAPPINGS__DATA_MAPPINGS);

        discriminatorEClass = createEClass(DISCRIMINATOR);
        createEAttribute(discriminatorEClass, DISCRIMINATOR__DISCRIMINATOR_TYPE);
        createEReference(discriminatorEClass, DISCRIMINATOR__STRUCTURED_DISCRIMINATOR);

        documentRootEClass = createEClass(DOCUMENT_ROOT);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__IMPLEMENTATION_TYPE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__DATA_OBJECT_ATTRIBUTES);
        createEReference(documentRootEClass, DOCUMENT_ROOT__EXTENDED_ATTRIBUTES);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__CONTINUE_ON_TIMEOUT);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__ALIAS);
        createEReference(documentRootEClass, DOCUMENT_ROOT__CONSTANT_PERIOD);
        createEReference(documentRootEClass, DOCUMENT_ROOT__USER_TASK_SCRIPTS);
        createEReference(documentRootEClass, DOCUMENT_ROOT__AUDIT);
        createEReference(documentRootEClass, DOCUMENT_ROOT__SCRIPT);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__REPLY_IMMEDIATELY);
        createEReference(documentRootEClass, DOCUMENT_ROOT__INITIAL_VALUES);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ASSOCIATED_CORRELATION_FIELDS);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ASSOCIATED_PARAMETERS);
        createEReference(documentRootEClass, DOCUMENT_ROOT__IMPLEMENTED_INTERFACE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__PROCESS_INTERFACES);
        createEReference(documentRootEClass, DOCUMENT_ROOT__WSDL_EVENT_ASSOCIATION);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__INLINE_SUB_PROCESS);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__DOCUMENTATION_URL);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__IMPLEMENTS);
        createEReference(documentRootEClass, DOCUMENT_ROOT__MULTI_INSTANCE_SCRIPTS);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__PROCESS_IDENTIFIER_FIELD);
        createEReference(documentRootEClass, DOCUMENT_ROOT__EXPRESSION);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__VISIBILITY);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__SECURITY_PROFILE);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__LANGUAGE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__INITIAL_PARAMETER_VALUE);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__INITIAL_VALUE_MAPPING);
        createEReference(documentRootEClass, DOCUMENT_ROOT__PORT_TYPE_OPERATION);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__TRANSPORT);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__IS_CHAINED);
        createEReference(documentRootEClass, DOCUMENT_ROOT__EXTERNAL_REFERENCE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__PROCESS_RESOURCE_PATTERNS);
        createEReference(documentRootEClass, DOCUMENT_ROOT__EVENT_HANDLER_INITIALISERS);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ACTIVITY_RESOURCE_PATTERNS);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__REQUIRE_NEW_TRANSACTION);
        createEReference(documentRootEClass, DOCUMENT_ROOT__DOCUMENT_OPERATION);
        createEReference(documentRootEClass, DOCUMENT_ROOT__DURATION_CALCULATION);
        createEReference(documentRootEClass, DOCUMENT_ROOT__DISCRIMINATOR);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__DISPLAY_NAME);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__CATCH_THROW);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__IS_REMOTE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__CORRELATION_DATA_MAPPINGS);
        createEReference(documentRootEClass, DOCUMENT_ROOT__TRANSFORM_SCRIPT);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__PUBLISH_AS_BUSINESS_SERVICE);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__BUSINESS_SERVICE_CATEGORY);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ERROR_THROWER_INFO);
        createEReference(documentRootEClass, DOCUMENT_ROOT__CATCH_ERROR_MAPPINGS);
        createEReference(documentRootEClass, DOCUMENT_ROOT__CONDITIONAL_PARTICIPANT);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__GENERATED);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__REPLY_TO_ACTIVITY_ID);
        createEReference(documentRootEClass, DOCUMENT_ROOT__TASK_LIBRARY_REFERENCE);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__SET_PERFORMER_IN_PROCESS);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__EMB_SUBPROC_OTHER_STATE_HEIGHT);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__EMB_SUBPROC_OTHER_STATE_WIDTH);
        createEReference(documentRootEClass, DOCUMENT_ROOT__FORM_IMPLEMENTATION);
        createEReference(documentRootEClass, DOCUMENT_ROOT__PARTICIPANT_QUERY);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__API_END_POINT_PARTICIPANT);
        createEReference(documentRootEClass, DOCUMENT_ROOT__FAULT_MESSAGE);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__REQUEST_ACTIVITY_ID);
        createEReference(documentRootEClass, DOCUMENT_ROOT__BUSINESS_PROCESS);
        createEReference(documentRootEClass, DOCUMENT_ROOT__WSDL_GENERATION);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__TARGET_PRIMITIVE_PROPERTY);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__SOURCE_PRIMITIVE_PROPERTY);
        createEReference(documentRootEClass, DOCUMENT_ROOT__DECISION_SERVICE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__PARTICIPANT_SHARED_RESOURCE);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__XPD_MODEL_TYPE);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__FLOW_ROUTING_STYLE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__CALENDAR_REFERENCE);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__NON_CANCELLING);
        createEReference(documentRootEClass, DOCUMENT_ROOT__SIGNAL_DATA);
        createEReference(documentRootEClass, DOCUMENT_ROOT__RETRY);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__ACTIVITY_DEADLINE_EVENT_ID);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__START_STRATEGY);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__OVERWRITE_ALREADY_MODIFIED_TASK_DATA);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__EVENT_HANDLER_FLOW_STRATEGY);
        createEReference(documentRootEClass, DOCUMENT_ROOT__NAMESPACE_PREFIX_MAP);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__SUSPEND_RESUME_WITH_PARENT);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__SYSTEM_ERROR_ACTION);
        createEReference(documentRootEClass, DOCUMENT_ROOT__CORRELATION_TIMEOUT);
        createEReference(documentRootEClass, DOCUMENT_ROOT__VALIDATION_CONTROL);
        createEReference(documentRootEClass, DOCUMENT_ROOT__REPLY_IMMEDIATE_DATA_MAPPINGS);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__BX_USE_UNQUALIFIED_PROPERTY_NAMES);
        createEReference(documentRootEClass, DOCUMENT_ROOT__CASE_REF_TYPE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__REST_SERVICES);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__PUBLISH_AS_REST_SERVICE);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__REST_ACTIVITY_ID);
        createEReference(documentRootEClass, DOCUMENT_ROOT__RESCHEDULE_TIMER_SCRIPT);
        createEReference(documentRootEClass, DOCUMENT_ROOT__DYNAMIC_ORGANIZATION_MAPPINGS);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__SIGNAL_HANDLER_ASYNCHRONOUS);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GLOBAL_DATA_OPERATION);
        createEReference(documentRootEClass, DOCUMENT_ROOT__PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__ALLOW_UNQUALIFIED_SUB_PROCESS_IDENTIFICATION);
        createEReference(documentRootEClass, DOCUMENT_ROOT__BPM_RUNTIME_CONFIGURATION);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__IS_CASE_SERVICE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__REQUIRED_ACCESS_PRIVILEGES);
        createEReference(documentRootEClass, DOCUMENT_ROOT__CASE_SERVICE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__AD_HOC_TASK_CONFIGURATION);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__IS_EVENT_SUB_PROCESS);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__NON_INTERRUPTING_EVENT);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__CORRELATE_IMMEDIATELY);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__ASYNC_EXECUTION_MODE);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__SIGNAL_TYPE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__SERVICE_PROCESS_CONFIGURATION);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__LIKE_MAPPING);
        createEReference(documentRootEClass, DOCUMENT_ROOT__SCRIPT_DATA_MAPPER);
        createEReference(documentRootEClass, DOCUMENT_ROOT__LIKE_MAPPING_EXCLUSIONS);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__SOURCE_CONTRIBUTOR_ID);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__TARGET_CONTRIBUTOR_ID);
        createEReference(documentRootEClass, DOCUMENT_ROOT__REST_SERVICE_OPERATION);
        createEReference(documentRootEClass, DOCUMENT_ROOT__INPUT_MAPPINGS);
        createEReference(documentRootEClass, DOCUMENT_ROOT__OUTPUT_MAPPINGS);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__BUSINESS_SERVICE_PUBLISH_TYPE);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__SUPPRESS_MAX_MAPPINGS_ERROR);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__FIELD_FORMAT);

        durationCalculationEClass = createEClass(DURATION_CALCULATION);
        createEReference(durationCalculationEClass, DURATION_CALCULATION__YEARS);
        createEReference(durationCalculationEClass, DURATION_CALCULATION__MONTHS);
        createEReference(durationCalculationEClass, DURATION_CALCULATION__WEEKS);
        createEReference(durationCalculationEClass, DURATION_CALCULATION__DAYS);
        createEReference(durationCalculationEClass, DURATION_CALCULATION__HOURS);
        createEReference(durationCalculationEClass, DURATION_CALCULATION__MINUTES);
        createEReference(durationCalculationEClass, DURATION_CALCULATION__SECONDS);
        createEReference(durationCalculationEClass, DURATION_CALCULATION__MICROSECONDS);

        dynamicOrganizationMappingsEClass = createEClass(DYNAMIC_ORGANIZATION_MAPPINGS);
        createEReference(dynamicOrganizationMappingsEClass,
                DYNAMIC_ORGANIZATION_MAPPINGS__DYNAMIC_ORGANIZATION_MAPPING);

        dynamicOrganizationMappingEClass = createEClass(DYNAMIC_ORGANIZATION_MAPPING);
        createEAttribute(dynamicOrganizationMappingEClass, DYNAMIC_ORGANIZATION_MAPPING__SOURCE_PATH);
        createEReference(dynamicOrganizationMappingEClass, DYNAMIC_ORGANIZATION_MAPPING__DYNAMIC_ORG_IDENTIFIER_REF);

        dynamicOrgIdentifierRefEClass = createEClass(DYNAMIC_ORG_IDENTIFIER_REF);
        createEAttribute(dynamicOrgIdentifierRefEClass, DYNAMIC_ORG_IDENTIFIER_REF__IDENTIFIER_NAME);
        createEAttribute(dynamicOrgIdentifierRefEClass, DYNAMIC_ORG_IDENTIFIER_REF__DYNAMIC_ORG_ID);
        createEAttribute(dynamicOrgIdentifierRefEClass, DYNAMIC_ORG_IDENTIFIER_REF__ORG_MODEL_PATH);

        emailResourceEClass = createEClass(EMAIL_RESOURCE);
        createEAttribute(emailResourceEClass, EMAIL_RESOURCE__INSTANCE_NAME);

        errorMethodEClass = createEClass(ERROR_METHOD);
        createEAttribute(errorMethodEClass, ERROR_METHOD__ERROR_CODE);

        errorThrowerInfoEClass = createEClass(ERROR_THROWER_INFO);
        createEAttribute(errorThrowerInfoEClass, ERROR_THROWER_INFO__THROWER_ID);
        createEAttribute(errorThrowerInfoEClass, ERROR_THROWER_INFO__THROWER_CONTAINER_ID);
        createEAttribute(errorThrowerInfoEClass, ERROR_THROWER_INFO__THROWER_TYPE);

        eventHandlerInitialisersEClass = createEClass(EVENT_HANDLER_INITIALISERS);
        createEReference(eventHandlerInitialisersEClass, EVENT_HANDLER_INITIALISERS__ACTIVITY_REF);

        faultMessageEClass = createEClass(FAULT_MESSAGE);

        formImplementationEClass = createEClass(FORM_IMPLEMENTATION);
        createEAttribute(formImplementationEClass, FORM_IMPLEMENTATION__FORM_TYPE);
        createEAttribute(formImplementationEClass, FORM_IMPLEMENTATION__FORM_URI);

        implementedInterfaceEClass = createEClass(IMPLEMENTED_INTERFACE);
        createEAttribute(implementedInterfaceEClass, IMPLEMENTED_INTERFACE__PACKAGE_REF);
        createEAttribute(implementedInterfaceEClass, IMPLEMENTED_INTERFACE__PROCESS_INTERFACE_ID);

        initialValuesEClass = createEClass(INITIAL_VALUES);
        createEAttribute(initialValuesEClass, INITIAL_VALUES__VALUE);

        initialParameterValueEClass = createEClass(INITIAL_PARAMETER_VALUE);
        createEAttribute(initialParameterValueEClass, INITIAL_PARAMETER_VALUE__NAME);
        createEAttribute(initialParameterValueEClass, INITIAL_PARAMETER_VALUE__VALUE);

        interfaceMethodEClass = createEClass(INTERFACE_METHOD);
        createEAttribute(interfaceMethodEClass, INTERFACE_METHOD__TRIGGER);
        createEReference(interfaceMethodEClass, INTERFACE_METHOD__TRIGGER_RESULT_MESSAGE);
        createEAttribute(interfaceMethodEClass, INTERFACE_METHOD__VISIBILITY);
        createEReference(interfaceMethodEClass, INTERFACE_METHOD__ERROR_METHODS);

        intermediateMethodEClass = createEClass(INTERMEDIATE_METHOD);

        jdbcResourceEClass = createEClass(JDBC_RESOURCE);
        createEAttribute(jdbcResourceEClass, JDBC_RESOURCE__INSTANCE_NAME);
        createEAttribute(jdbcResourceEClass, JDBC_RESOURCE__JDBC_PROFILE_NAME);

        multiInstanceScriptsEClass = createEClass(MULTI_INSTANCE_SCRIPTS);
        createEReference(multiInstanceScriptsEClass, MULTI_INSTANCE_SCRIPTS__ADDITIONAL_INSTANCES);

        namespacePrefixMapEClass = createEClass(NAMESPACE_PREFIX_MAP);
        createEReference(namespacePrefixMapEClass, NAMESPACE_PREFIX_MAP__NAMESPACE_ENTRIES);
        createEAttribute(namespacePrefixMapEClass, NAMESPACE_PREFIX_MAP__PREFIX_MAPPING_DISABLED);

        namespaceMapEntryEClass = createEClass(NAMESPACE_MAP_ENTRY);
        createEAttribute(namespaceMapEntryEClass, NAMESPACE_MAP_ENTRY__PREFIX);
        createEAttribute(namespaceMapEntryEClass, NAMESPACE_MAP_ENTRY__NAMESPACE);

        participantSharedResourceEClass = createEClass(PARTICIPANT_SHARED_RESOURCE);
        createEReference(participantSharedResourceEClass, PARTICIPANT_SHARED_RESOURCE__EMAIL);
        createEReference(participantSharedResourceEClass, PARTICIPANT_SHARED_RESOURCE__JDBC);
        createEReference(participantSharedResourceEClass, PARTICIPANT_SHARED_RESOURCE__WEB_SERVICE);
        createEReference(participantSharedResourceEClass, PARTICIPANT_SHARED_RESOURCE__REST_SERVICE);

        pilingInfoEClass = createEClass(PILING_INFO);
        createEAttribute(pilingInfoEClass, PILING_INFO__PILING_ALLOWED);
        createEAttribute(pilingInfoEClass, PILING_INFO__MAX_PILIABLE_ITEMS);

        portTypeOperationEClass = createEClass(PORT_TYPE_OPERATION);
        createEAttribute(portTypeOperationEClass, PORT_TYPE_OPERATION__PORT_TYPE_NAME);
        createEAttribute(portTypeOperationEClass, PORT_TYPE_OPERATION__OPERATION_NAME);
        createEReference(portTypeOperationEClass, PORT_TYPE_OPERATION__EXTERNAL_REFERENCE);
        createEAttribute(portTypeOperationEClass, PORT_TYPE_OPERATION__TRANSPORT);

        processInterfaceEClass = createEClass(PROCESS_INTERFACE);
        createEReference(processInterfaceEClass, PROCESS_INTERFACE__START_METHODS);
        createEReference(processInterfaceEClass, PROCESS_INTERFACE__INTERMEDIATE_METHODS);
        createEAttribute(processInterfaceEClass, PROCESS_INTERFACE__XPD_INTERFACE_TYPE);
        createEReference(processInterfaceEClass, PROCESS_INTERFACE__SERVICE_PROCESS_CONFIGURATION);

        processInterfacesEClass = createEClass(PROCESS_INTERFACES);
        createEReference(processInterfacesEClass, PROCESS_INTERFACES__PROCESS_INTERFACE);

        processResourcePatternsEClass = createEClass(PROCESS_RESOURCE_PATTERNS);
        createEReference(processResourcePatternsEClass, PROCESS_RESOURCE_PATTERNS__SEPARATION_OF_DUTIES_ACTIVITIES);
        createEReference(processResourcePatternsEClass, PROCESS_RESOURCE_PATTERNS__RETAIN_FAMILIAR_ACTIVITIES);

        rescheduleTimerScriptEClass = createEClass(RESCHEDULE_TIMER_SCRIPT);
        createEAttribute(rescheduleTimerScriptEClass, RESCHEDULE_TIMER_SCRIPT__DURATION_RELATIVE_TO);

        rescheduleTimersEClass = createEClass(RESCHEDULE_TIMERS);
        createEAttribute(rescheduleTimersEClass, RESCHEDULE_TIMERS__TIMER_SELECTION_TYPE);
        createEReference(rescheduleTimersEClass, RESCHEDULE_TIMERS__TIMER_EVENTS);

        restServicesEClass = createEClass(REST_SERVICES);
        createEReference(restServicesEClass, REST_SERVICES__REST_SERVICES);

        restServiceResourceEClass = createEClass(REST_SERVICE_RESOURCE);
        createEReference(restServiceResourceEClass, REST_SERVICE_RESOURCE__SECURITY_POLICY);
        createEAttribute(restServiceResourceEClass, REST_SERVICE_RESOURCE__RESOURCE_NAME);
        createEAttribute(restServiceResourceEClass, REST_SERVICE_RESOURCE__RESOURCE_TYPE);
        createEAttribute(restServiceResourceEClass, REST_SERVICE_RESOURCE__DESCRIPTION);

        restServiceResourceSecurityEClass = createEClass(REST_SERVICE_RESOURCE_SECURITY);
        createEAttribute(restServiceResourceSecurityEClass, REST_SERVICE_RESOURCE_SECURITY__POLICY_TYPE);

        retainFamiliarActivitiesEClass = createEClass(RETAIN_FAMILIAR_ACTIVITIES);
        createEReference(retainFamiliarActivitiesEClass, RETAIN_FAMILIAR_ACTIVITIES__ACTIVITY_REF);

        retryEClass = createEClass(RETRY);
        createEAttribute(retryEClass, RETRY__MAX);
        createEAttribute(retryEClass, RETRY__INITIAL_PERIOD);
        createEAttribute(retryEClass, RETRY__PERIOD_INCREMENT);
        createEAttribute(retryEClass, RETRY__MAX_RETRY_ACTION);

        scriptInformationEClass = createEClass(SCRIPT_INFORMATION);
        createEReference(scriptInformationEClass, SCRIPT_INFORMATION__EXPRESSION);
        createEAttribute(scriptInformationEClass, SCRIPT_INFORMATION__DIRECTION);
        createEReference(scriptInformationEClass, SCRIPT_INFORMATION__ACTIVITY);
        createEAttribute(scriptInformationEClass, SCRIPT_INFORMATION__REFERENCE);

        separationOfDutiesActivitiesEClass = createEClass(SEPARATION_OF_DUTIES_ACTIVITIES);
        createEReference(separationOfDutiesActivitiesEClass, SEPARATION_OF_DUTIES_ACTIVITIES__ACTIVITY_REF);

        signalDataEClass = createEClass(SIGNAL_DATA);
        createEReference(signalDataEClass, SIGNAL_DATA__CORRELATION_MAPPINGS);
        createEReference(signalDataEClass, SIGNAL_DATA__DATA_MAPPINGS);
        createEReference(signalDataEClass, SIGNAL_DATA__RESCHEDULE_TIMERS);
        createEReference(signalDataEClass, SIGNAL_DATA__INPUT_SCRIPT_DATA_MAPPER);
        createEReference(signalDataEClass, SIGNAL_DATA__OUTPUT_SCRIPT_DATA_MAPPER);

        startMethodEClass = createEClass(START_METHOD);

        structuredDiscriminatorEClass = createEClass(STRUCTURED_DISCRIMINATOR);
        createEAttribute(structuredDiscriminatorEClass, STRUCTURED_DISCRIMINATOR__WAIT_FOR_INCOMING_PATH);
        createEAttribute(structuredDiscriminatorEClass, STRUCTURED_DISCRIMINATOR__UP_STREAM_PARALLEL_SPLIT);

        taskLibraryReferenceEClass = createEClass(TASK_LIBRARY_REFERENCE);
        createEAttribute(taskLibraryReferenceEClass, TASK_LIBRARY_REFERENCE__TASK_LIBRARY_ID);
        createEAttribute(taskLibraryReferenceEClass, TASK_LIBRARY_REFERENCE__PACKAGE_REF);

        transformScriptEClass = createEClass(TRANSFORM_SCRIPT);
        createEReference(transformScriptEClass, TRANSFORM_SCRIPT__DATA_MAPPINGS);
        createEAttribute(transformScriptEClass, TRANSFORM_SCRIPT__INPUT_DOM);
        createEAttribute(transformScriptEClass, TRANSFORM_SCRIPT__OUTPUT_DOM);

        userTaskScriptsEClass = createEClass(USER_TASK_SCRIPTS);
        createEReference(userTaskScriptsEClass, USER_TASK_SCRIPTS__OPEN_SCRIPT);
        createEReference(userTaskScriptsEClass, USER_TASK_SCRIPTS__CLOSE_SCRIPT);
        createEReference(userTaskScriptsEClass, USER_TASK_SCRIPTS__SUBMIT_SCRIPT);
        createEReference(userTaskScriptsEClass, USER_TASK_SCRIPTS__SCHEDULE_SCRIPT);
        createEReference(userTaskScriptsEClass, USER_TASK_SCRIPTS__RESCHEDULE_SCRIPT);

        validationControlEClass = createEClass(VALIDATION_CONTROL);
        createEReference(validationControlEClass, VALIDATION_CONTROL__VALIDATION_ISSUE_OVERRIDES);

        validationIssueOverrideEClass = createEClass(VALIDATION_ISSUE_OVERRIDE);
        createEAttribute(validationIssueOverrideEClass, VALIDATION_ISSUE_OVERRIDE__VALIDATION_ISSUE_ID);
        createEAttribute(validationIssueOverrideEClass, VALIDATION_ISSUE_OVERRIDE__OVERRIDE_TYPE);

        wsdlEventAssociationEClass = createEClass(WSDL_EVENT_ASSOCIATION);
        createEAttribute(wsdlEventAssociationEClass, WSDL_EVENT_ASSOCIATION__EVENT_ID);

        workItemPriorityEClass = createEClass(WORK_ITEM_PRIORITY);
        createEAttribute(workItemPriorityEClass, WORK_ITEM_PRIORITY__INITIAL_PRIORITY);

        wsdlGenerationEClass = createEClass(WSDL_GENERATION);
        createEAttribute(wsdlGenerationEClass, WSDL_GENERATION__SOAP_BINDING_STYLE);

        wsBindingEClass = createEClass(WS_BINDING);
        createEAttribute(wsBindingEClass, WS_BINDING__NAME);
        createEReference(wsBindingEClass, WS_BINDING__EXTENDED_PROPERTIES);

        wsInboundEClass = createEClass(WS_INBOUND);
        createEReference(wsInboundEClass, WS_INBOUND__VIRTUAL_BINDING);
        createEReference(wsInboundEClass, WS_INBOUND__SOAP_HTTP_BINDING);
        createEReference(wsInboundEClass, WS_INBOUND__SOAP_JMS_BINDING);

        wsOutboundEClass = createEClass(WS_OUTBOUND);
        createEReference(wsOutboundEClass, WS_OUTBOUND__VIRTUAL_BINDING);
        createEReference(wsOutboundEClass, WS_OUTBOUND__SOAP_HTTP_BINDING);
        createEReference(wsOutboundEClass, WS_OUTBOUND__SOAP_JMS_BINDING);

        wsResourceEClass = createEClass(WS_RESOURCE);
        createEReference(wsResourceEClass, WS_RESOURCE__INBOUND);
        createEReference(wsResourceEClass, WS_RESOURCE__OUTBOUND);

        wsSecurityPolicyEClass = createEClass(WS_SECURITY_POLICY);
        createEAttribute(wsSecurityPolicyEClass, WS_SECURITY_POLICY__GOVERNANCE_APPLICATION_NAME);
        createEAttribute(wsSecurityPolicyEClass, WS_SECURITY_POLICY__TYPE);

        wsSoapBindingEClass = createEClass(WS_SOAP_BINDING);
        createEAttribute(wsSoapBindingEClass, WS_SOAP_BINDING__BINDING_STYLE);
        createEAttribute(wsSoapBindingEClass, WS_SOAP_BINDING__SOAP_VERSION);
        createEReference(wsSoapBindingEClass, WS_SOAP_BINDING__SOAP_SECURITY);

        wsSoapHttpInboundBindingEClass = createEClass(WS_SOAP_HTTP_INBOUND_BINDING);
        createEReference(wsSoapHttpInboundBindingEClass, WS_SOAP_HTTP_INBOUND_BINDING__INBOUND_SECURITY);
        createEAttribute(wsSoapHttpInboundBindingEClass, WS_SOAP_HTTP_INBOUND_BINDING__ENDPOINT_URL_PATH);
        createEAttribute(wsSoapHttpInboundBindingEClass, WS_SOAP_HTTP_INBOUND_BINDING__HTTP_CONNECTOR_INSTANCE_NAME);

        wsSoapHttpOutboundBindingEClass = createEClass(WS_SOAP_HTTP_OUTBOUND_BINDING);
        createEReference(wsSoapHttpOutboundBindingEClass, WS_SOAP_HTTP_OUTBOUND_BINDING__OUTBOUND_SECURITY);
        createEAttribute(wsSoapHttpOutboundBindingEClass, WS_SOAP_HTTP_OUTBOUND_BINDING__HTTP_CLIENT_INSTANCE_NAME);

        wsSoapJmsInboundBindingEClass = createEClass(WS_SOAP_JMS_INBOUND_BINDING);
        createEAttribute(wsSoapJmsInboundBindingEClass, WS_SOAP_JMS_INBOUND_BINDING__OUTBOUND_CONNECTION_FACTORY);
        createEAttribute(wsSoapJmsInboundBindingEClass,
                WS_SOAP_JMS_INBOUND_BINDING__INBOUND_CONNECTION_FACTORY_CONFIGURATION);
        createEAttribute(wsSoapJmsInboundBindingEClass, WS_SOAP_JMS_INBOUND_BINDING__INBOUND_DESTINATION);
        createEReference(wsSoapJmsInboundBindingEClass, WS_SOAP_JMS_INBOUND_BINDING__INBOUND_SECURITY);

        wsSoapJmsOutboundBindingEClass = createEClass(WS_SOAP_JMS_OUTBOUND_BINDING);
        createEAttribute(wsSoapJmsOutboundBindingEClass, WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_CONNECTION_FACTORY);
        createEAttribute(wsSoapJmsOutboundBindingEClass, WS_SOAP_JMS_OUTBOUND_BINDING__INBOUND_DESTINATION);
        createEAttribute(wsSoapJmsOutboundBindingEClass, WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_DESTINATION);
        createEReference(wsSoapJmsOutboundBindingEClass, WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_SECURITY);
        createEAttribute(wsSoapJmsOutboundBindingEClass, WS_SOAP_JMS_OUTBOUND_BINDING__DELIVERY_MODE);
        createEAttribute(wsSoapJmsOutboundBindingEClass, WS_SOAP_JMS_OUTBOUND_BINDING__PRIORITY);
        createEAttribute(wsSoapJmsOutboundBindingEClass, WS_SOAP_JMS_OUTBOUND_BINDING__MESSAGE_EXPIRATION);
        createEAttribute(wsSoapJmsOutboundBindingEClass, WS_SOAP_JMS_OUTBOUND_BINDING__INVOCATION_TIMEOUT);

        wsSoapSecurityEClass = createEClass(WS_SOAP_SECURITY);
        createEReference(wsSoapSecurityEClass, WS_SOAP_SECURITY__SECURITY_POLICY);

        wsVirtualBindingEClass = createEClass(WS_VIRTUAL_BINDING);

        xpdExtDataObjectAttributesEClass = createEClass(XPD_EXT_DATA_OBJECT_ATTRIBUTES);
        createEAttribute(xpdExtDataObjectAttributesEClass, XPD_EXT_DATA_OBJECT_ATTRIBUTES__DESCRIPTION);
        createEAttribute(xpdExtDataObjectAttributesEClass, XPD_EXT_DATA_OBJECT_ATTRIBUTES__EXTERNAL_REFERENCE);
        createEReference(xpdExtDataObjectAttributesEClass, XPD_EXT_DATA_OBJECT_ATTRIBUTES__PROPERTIES);

        xpdExtPropertyEClass = createEClass(XPD_EXT_PROPERTY);
        createEAttribute(xpdExtPropertyEClass, XPD_EXT_PROPERTY__NAME);
        createEAttribute(xpdExtPropertyEClass, XPD_EXT_PROPERTY__VALUE);

        xpdExtAttributeEClass = createEClass(XPD_EXT_ATTRIBUTE);
        createEAttribute(xpdExtAttributeEClass, XPD_EXT_ATTRIBUTE__MIXED);
        createEAttribute(xpdExtAttributeEClass, XPD_EXT_ATTRIBUTE__GROUP);
        createEAttribute(xpdExtAttributeEClass, XPD_EXT_ATTRIBUTE__ANY);
        createEAttribute(xpdExtAttributeEClass, XPD_EXT_ATTRIBUTE__NAME);
        createEAttribute(xpdExtAttributeEClass, XPD_EXT_ATTRIBUTE__VALUE);

        xpdExtAttributesEClass = createEClass(XPD_EXT_ATTRIBUTES);
        createEReference(xpdExtAttributesEClass, XPD_EXT_ATTRIBUTES__ATTRIBUTES);

        updateCaseOperationTypeEClass = createEClass(UPDATE_CASE_OPERATION_TYPE);
        createEAttribute(updateCaseOperationTypeEClass, UPDATE_CASE_OPERATION_TYPE__FROM_FIELD_PATH);

        addLinkAssociationsTypeEClass = createEClass(ADD_LINK_ASSOCIATIONS_TYPE);
        createEAttribute(addLinkAssociationsTypeEClass, ADD_LINK_ASSOCIATIONS_TYPE__ADD_CASE_REF_FIELD);
        createEAttribute(addLinkAssociationsTypeEClass, ADD_LINK_ASSOCIATIONS_TYPE__ASSOCIATION_NAME);

        removeLinkAssociationsTypeEClass = createEClass(REMOVE_LINK_ASSOCIATIONS_TYPE);
        createEAttribute(removeLinkAssociationsTypeEClass, REMOVE_LINK_ASSOCIATIONS_TYPE__ASSOCIATION_NAME);
        createEAttribute(removeLinkAssociationsTypeEClass, REMOVE_LINK_ASSOCIATIONS_TYPE__REMOVE_CASE_REF_FIELD);

        caseReferenceOperationsTypeEClass = createEClass(CASE_REFERENCE_OPERATIONS_TYPE);
        createEAttribute(caseReferenceOperationsTypeEClass, CASE_REFERENCE_OPERATIONS_TYPE__CASE_REF_FIELD);
        createEReference(caseReferenceOperationsTypeEClass, CASE_REFERENCE_OPERATIONS_TYPE__UPDATE);
        createEReference(caseReferenceOperationsTypeEClass, CASE_REFERENCE_OPERATIONS_TYPE__DELETE);
        createEReference(caseReferenceOperationsTypeEClass, CASE_REFERENCE_OPERATIONS_TYPE__ADD_LINK_ASSOCIATIONS);
        createEReference(caseReferenceOperationsTypeEClass, CASE_REFERENCE_OPERATIONS_TYPE__REMOVE_LINK_ASSOCIATIONS);

        globalDataOperationEClass = createEClass(GLOBAL_DATA_OPERATION);
        createEReference(globalDataOperationEClass, GLOBAL_DATA_OPERATION__CASE_ACCESS_OPERATIONS);
        createEReference(globalDataOperationEClass, GLOBAL_DATA_OPERATION__CASE_REFERENCE_OPERATIONS);

        deleteByCaseIdentifierTypeEClass = createEClass(DELETE_BY_CASE_IDENTIFIER_TYPE);
        createEAttribute(deleteByCaseIdentifierTypeEClass, DELETE_BY_CASE_IDENTIFIER_TYPE__FIELD_PATH);
        createEAttribute(deleteByCaseIdentifierTypeEClass, DELETE_BY_CASE_IDENTIFIER_TYPE__IDENTIFIER_NAME);

        compositeIdentifierTypeEClass = createEClass(COMPOSITE_IDENTIFIER_TYPE);
        createEAttribute(compositeIdentifierTypeEClass, COMPOSITE_IDENTIFIER_TYPE__FIELD_PATH);
        createEAttribute(compositeIdentifierTypeEClass, COMPOSITE_IDENTIFIER_TYPE__IDENTIFIERNAME);

        deleteCaseReferenceOperationTypeEClass = createEClass(DELETE_CASE_REFERENCE_OPERATION_TYPE);

        deleteByCompositeIdentifiersTypeEClass = createEClass(DELETE_BY_COMPOSITE_IDENTIFIERS_TYPE);
        createEAttribute(deleteByCompositeIdentifiersTypeEClass, DELETE_BY_COMPOSITE_IDENTIFIERS_TYPE__GROUP);
        createEReference(deleteByCompositeIdentifiersTypeEClass,
                DELETE_BY_COMPOSITE_IDENTIFIERS_TYPE__COMPOSITE_IDENTIFIER);

        createCaseOperationTypeEClass = createEClass(CREATE_CASE_OPERATION_TYPE);
        createEAttribute(createCaseOperationTypeEClass, CREATE_CASE_OPERATION_TYPE__FROM_FIELD_PATH);
        createEAttribute(createCaseOperationTypeEClass, CREATE_CASE_OPERATION_TYPE__TO_CASE_REF_FIELD);

        caseAccessOperationsTypeEClass = createEClass(CASE_ACCESS_OPERATIONS_TYPE);
        createEReference(caseAccessOperationsTypeEClass, CASE_ACCESS_OPERATIONS_TYPE__CASE_CLASS_EXTERNAL_REFERENCE);
        createEReference(caseAccessOperationsTypeEClass, CASE_ACCESS_OPERATIONS_TYPE__CREATE);
        createEReference(caseAccessOperationsTypeEClass, CASE_ACCESS_OPERATIONS_TYPE__DELETE_BY_CASE_IDENTIFIER);
        createEReference(caseAccessOperationsTypeEClass, CASE_ACCESS_OPERATIONS_TYPE__DELETE_BY_COMPOSITE_IDENTIFIERS);

        dataWorkItemAttributeMappingEClass = createEClass(DATA_WORK_ITEM_ATTRIBUTE_MAPPING);
        createEAttribute(dataWorkItemAttributeMappingEClass, DATA_WORK_ITEM_ATTRIBUTE_MAPPING__ATTRIBUTE);
        createEAttribute(dataWorkItemAttributeMappingEClass, DATA_WORK_ITEM_ATTRIBUTE_MAPPING__PROCESS_DATA);

        processDataWorkItemAttributeMappingsEClass = createEClass(PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS);
        createEReference(processDataWorkItemAttributeMappingsEClass,
                PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS__DATA_WORK_ITEM_ATTRIBUTE_MAPPING);

        bpmRuntimeConfigurationEClass = createEClass(BPM_RUNTIME_CONFIGURATION);
        createEAttribute(bpmRuntimeConfigurationEClass, BPM_RUNTIME_CONFIGURATION__INCOMING_REQUEST_TIMEOUT);

        enablementTypeEClass = createEClass(ENABLEMENT_TYPE);
        createEReference(enablementTypeEClass, ENABLEMENT_TYPE__INITIALIZER_ACTIVITIES);
        createEReference(enablementTypeEClass, ENABLEMENT_TYPE__PRECONDITION_EXPRESSION);

        initializerActivitiesTypeEClass = createEClass(INITIALIZER_ACTIVITIES_TYPE);
        createEReference(initializerActivitiesTypeEClass, INITIALIZER_ACTIVITIES_TYPE__ACTIVITY_REF);

        adHocTaskConfigurationTypeEClass = createEClass(AD_HOC_TASK_CONFIGURATION_TYPE);
        createEReference(adHocTaskConfigurationTypeEClass, AD_HOC_TASK_CONFIGURATION_TYPE__ENABLEMENT);
        createEAttribute(adHocTaskConfigurationTypeEClass, AD_HOC_TASK_CONFIGURATION_TYPE__AD_HOC_EXECUTION_TYPE);
        createEAttribute(adHocTaskConfigurationTypeEClass, AD_HOC_TASK_CONFIGURATION_TYPE__SUSPEND_MAIN_FLOW);
        createEAttribute(adHocTaskConfigurationTypeEClass, AD_HOC_TASK_CONFIGURATION_TYPE__ALLOW_MULTIPLE_INVOCATIONS);
        createEReference(adHocTaskConfigurationTypeEClass, AD_HOC_TASK_CONFIGURATION_TYPE__REQUIRED_ACCESS_PRIVILEGES);

        requiredAccessPrivilegesEClass = createEClass(REQUIRED_ACCESS_PRIVILEGES);
        createEReference(requiredAccessPrivilegesEClass, REQUIRED_ACCESS_PRIVILEGES__PRIVILEGE_REFERENCE);

        visibleForCaseStatesEClass = createEClass(VISIBLE_FOR_CASE_STATES);
        createEAttribute(visibleForCaseStatesEClass, VISIBLE_FOR_CASE_STATES__VISIBLE_FOR_UNSET_CASE_STATE);
        createEReference(visibleForCaseStatesEClass, VISIBLE_FOR_CASE_STATES__CASE_STATE);

        caseServiceEClass = createEClass(CASE_SERVICE);
        createEReference(caseServiceEClass, CASE_SERVICE__CASE_CLASS_TYPE);
        createEReference(caseServiceEClass, CASE_SERVICE__VISIBLE_FOR_CASE_STATES);

        documentOperationEClass = createEClass(DOCUMENT_OPERATION);
        createEReference(documentOperationEClass, DOCUMENT_OPERATION__CASE_DOC_REF_OPERATION);
        createEReference(documentOperationEClass, DOCUMENT_OPERATION__CASE_DOC_FIND_OPERATIONS);
        createEReference(documentOperationEClass, DOCUMENT_OPERATION__LINK_SYSTEM_DOCUMENT_OPERATION);

        caseDocRefOperationsEClass = createEClass(CASE_DOC_REF_OPERATIONS);
        createEReference(caseDocRefOperationsEClass, CASE_DOC_REF_OPERATIONS__MOVE_CASE_DOC_OPERATION);
        createEReference(caseDocRefOperationsEClass, CASE_DOC_REF_OPERATIONS__UNLINK_CASE_DOC_OPERATION);
        createEReference(caseDocRefOperationsEClass, CASE_DOC_REF_OPERATIONS__LINK_CASE_DOC_OPERATION);
        createEReference(caseDocRefOperationsEClass, CASE_DOC_REF_OPERATIONS__DELETE_CASE_DOC_OPERATION);
        createEAttribute(caseDocRefOperationsEClass, CASE_DOC_REF_OPERATIONS__CASE_DOCUMENT_REF_FIELD);

        caseDocFindOperationsEClass = createEClass(CASE_DOC_FIND_OPERATIONS);
        createEReference(caseDocFindOperationsEClass, CASE_DOC_FIND_OPERATIONS__FIND_BY_FILE_NAME_OPERATION);
        createEReference(caseDocFindOperationsEClass, CASE_DOC_FIND_OPERATIONS__FIND_BY_QUERY_OPERATION);
        createEAttribute(caseDocFindOperationsEClass, CASE_DOC_FIND_OPERATIONS__RETURN_CASE_DOC_REFS_FIELD);
        createEAttribute(caseDocFindOperationsEClass, CASE_DOC_FIND_OPERATIONS__CASE_REF_FIELD);

        moveCaseDocOperationEClass = createEClass(MOVE_CASE_DOC_OPERATION);
        createEAttribute(moveCaseDocOperationEClass, MOVE_CASE_DOC_OPERATION__SOURCE_CASE_REF_FIELD);
        createEAttribute(moveCaseDocOperationEClass, MOVE_CASE_DOC_OPERATION__TARGET_CASE_REF_FIELD);

        unlinkCaseDocOperationEClass = createEClass(UNLINK_CASE_DOC_OPERATION);
        createEAttribute(unlinkCaseDocOperationEClass, UNLINK_CASE_DOC_OPERATION__SOURCE_CASE_REF_FIELD);

        linkCaseDocOperationEClass = createEClass(LINK_CASE_DOC_OPERATION);
        createEAttribute(linkCaseDocOperationEClass, LINK_CASE_DOC_OPERATION__TARGET_CASE_REF_FIELD);

        linkSystemDocumentOperationEClass = createEClass(LINK_SYSTEM_DOCUMENT_OPERATION);
        createEAttribute(linkSystemDocumentOperationEClass, LINK_SYSTEM_DOCUMENT_OPERATION__DOCUMENT_ID);
        createEAttribute(linkSystemDocumentOperationEClass, LINK_SYSTEM_DOCUMENT_OPERATION__RETURN_CASE_DOC_REF_FIELD);
        createEAttribute(linkSystemDocumentOperationEClass, LINK_SYSTEM_DOCUMENT_OPERATION__CASE_REF_FIELD);

        deleteCaseDocOperationEClass = createEClass(DELETE_CASE_DOC_OPERATION);

        findByFileNameOperationEClass = createEClass(FIND_BY_FILE_NAME_OPERATION);
        createEAttribute(findByFileNameOperationEClass, FIND_BY_FILE_NAME_OPERATION__FILE_NAME_FIELD);

        findByQueryOperationEClass = createEClass(FIND_BY_QUERY_OPERATION);
        createEReference(findByQueryOperationEClass, FIND_BY_QUERY_OPERATION__CASE_DOCUMENT_QUERY_EXPRESSION);

        caseDocumentQueryExpressionEClass = createEClass(CASE_DOCUMENT_QUERY_EXPRESSION);
        createEAttribute(caseDocumentQueryExpressionEClass, CASE_DOCUMENT_QUERY_EXPRESSION__QUERY_EXPRESSION_JOIN_TYPE);
        createEAttribute(caseDocumentQueryExpressionEClass, CASE_DOCUMENT_QUERY_EXPRESSION__OPEN_BRACKET_COUNT);
        createEAttribute(caseDocumentQueryExpressionEClass, CASE_DOCUMENT_QUERY_EXPRESSION__CMIS_PROPERTY);
        createEAttribute(caseDocumentQueryExpressionEClass, CASE_DOCUMENT_QUERY_EXPRESSION__OPERATOR);
        createEAttribute(caseDocumentQueryExpressionEClass, CASE_DOCUMENT_QUERY_EXPRESSION__PROCESS_DATA_FIELD);
        createEAttribute(caseDocumentQueryExpressionEClass, CASE_DOCUMENT_QUERY_EXPRESSION__CLOSE_BRACKET_COUNT);
        createEAttribute(caseDocumentQueryExpressionEClass,
                CASE_DOCUMENT_QUERY_EXPRESSION__CMIS_DOCUMENT_PROPERTY_SELECTED);

        serviceProcessConfigurationEClass = createEClass(SERVICE_PROCESS_CONFIGURATION);
        createEAttribute(serviceProcessConfigurationEClass, SERVICE_PROCESS_CONFIGURATION__DEPLOY_TO_PROCESS_RUNTIME);
        createEAttribute(serviceProcessConfigurationEClass, SERVICE_PROCESS_CONFIGURATION__DEPLOY_TO_PAGEFLOW_RUNTIME);

        scriptDataMapperEClass = createEClass(SCRIPT_DATA_MAPPER);
        createEAttribute(scriptDataMapperEClass, SCRIPT_DATA_MAPPER__MAPPER_CONTEXT);
        createEAttribute(scriptDataMapperEClass, SCRIPT_DATA_MAPPER__MAPPING_DIRECTION);
        createEReference(scriptDataMapperEClass, SCRIPT_DATA_MAPPER__DATA_MAPPINGS);
        createEReference(scriptDataMapperEClass, SCRIPT_DATA_MAPPER__UNMAPPED_SCRIPTS);
        createEReference(scriptDataMapperEClass, SCRIPT_DATA_MAPPER__ARRAY_INFLATION_TYPE);

        dataMapperArrayInflationEClass = createEClass(DATA_MAPPER_ARRAY_INFLATION);
        createEAttribute(dataMapperArrayInflationEClass, DATA_MAPPER_ARRAY_INFLATION__PATH);
        createEAttribute(dataMapperArrayInflationEClass, DATA_MAPPER_ARRAY_INFLATION__MAPPING_TYPE);
        createEAttribute(dataMapperArrayInflationEClass, DATA_MAPPER_ARRAY_INFLATION__CONTRIBUTOR_ID);

        likeMappingExclusionEClass = createEClass(LIKE_MAPPING_EXCLUSION);
        createEAttribute(likeMappingExclusionEClass, LIKE_MAPPING_EXCLUSION__PATH);

        likeMappingExclusionsEClass = createEClass(LIKE_MAPPING_EXCLUSIONS);
        createEReference(likeMappingExclusionsEClass, LIKE_MAPPING_EXCLUSIONS__EXCLUSIONS);

        restServiceOperationEClass = createEClass(REST_SERVICE_OPERATION);
        createEAttribute(restServiceOperationEClass, REST_SERVICE_OPERATION__LOCATION);
        createEAttribute(restServiceOperationEClass, REST_SERVICE_OPERATION__METHOD_ID);

        // Create enums
        allocationStrategyTypeEEnum = createEEnum(ALLOCATION_STRATEGY_TYPE);
        allocationTypeEEnum = createEEnum(ALLOCATION_TYPE);
        auditEventTypeEEnum = createEEnum(AUDIT_EVENT_TYPE);
        correlationModeEEnum = createEEnum(CORRELATION_MODE);
        errorThrowerTypeEEnum = createEEnum(ERROR_THROWER_TYPE);
        eventHandlerFlowStrategyEEnum = createEEnum(EVENT_HANDLER_FLOW_STRATEGY);
        flowRoutingStyleEEnum = createEEnum(FLOW_ROUTING_STYLE);
        formImplementationTypeEEnum = createEEnum(FORM_IMPLEMENTATION_TYPE);
        maxRetryActionTypeEEnum = createEEnum(MAX_RETRY_ACTION_TYPE);
        rescheduleDurationTypeEEnum = createEEnum(RESCHEDULE_DURATION_TYPE);
        rescheduleTimerSelectionTypeEEnum = createEEnum(RESCHEDULE_TIMER_SELECTION_TYPE);
        securityPolicyEEnum = createEEnum(SECURITY_POLICY);
        soapBindingStyleEEnum = createEEnum(SOAP_BINDING_STYLE);
        fieldFormatEEnum = createEEnum(FIELD_FORMAT);
        subProcessStartStrategyEEnum = createEEnum(SUB_PROCESS_START_STRATEGY);
        systemErrorActionTypeEEnum = createEEnum(SYSTEM_ERROR_ACTION_TYPE);
        validationIssueOverrideTypeEEnum = createEEnum(VALIDATION_ISSUE_OVERRIDE_TYPE);
        visibilityEEnum = createEEnum(VISIBILITY);
        deliveryModeEEnum = createEEnum(DELIVERY_MODE);
        xpdModelTypeEEnum = createEEnum(XPD_MODEL_TYPE);
        adHocExecutionTypeTypeEEnum = createEEnum(AD_HOC_EXECUTION_TYPE_TYPE);
        queryExpressionJoinTypeEEnum = createEEnum(QUERY_EXPRESSION_JOIN_TYPE);
        cmisQueryOperatorEEnum = createEEnum(CMIS_QUERY_OPERATOR);
        asyncExecutionModeEEnum = createEEnum(ASYNC_EXECUTION_MODE);
        signalTypeEEnum = createEEnum(SIGNAL_TYPE);
        xpdInterfaceTypeEEnum = createEEnum(XPD_INTERFACE_TYPE);
        dataMapperArrayInflationTypeEEnum = createEEnum(DATA_MAPPER_ARRAY_INFLATION_TYPE);
        businessServicePublishTypeEEnum = createEEnum(BUSINESS_SERVICE_PUBLISH_TYPE);

        // Create data types
        auditEventTypeObjectEDataType = createEDataType(AUDIT_EVENT_TYPE_OBJECT);
        securityPolicyObjectEDataType = createEDataType(SECURITY_POLICY_OBJECT);
        soapBindingStyleObjectEDataType = createEDataType(SOAP_BINDING_STYLE_OBJECT);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model. This
     * method is guarded to have no affect on any invocation but its first. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized)
            return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Obtain other dependent packages
        Xpdl2Package theXpdl2Package = (Xpdl2Package) EPackage.Registry.INSTANCE.getEPackage(Xpdl2Package.eNS_URI);
        XMLTypePackage theXMLTypePackage =
                (XMLTypePackage) EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        associatedCorrelationFieldEClass.getESuperTypes().add(theXpdl2Package.getDescribedElement());
        associatedParameterEClass.getESuperTypes().add(theXpdl2Package.getDescribedElement());
        errorMethodEClass.getESuperTypes().add(theXpdl2Package.getUniqueIdElement());
        errorMethodEClass.getESuperTypes().add(this.getAssociatedParametersContainer());
        eventHandlerInitialisersEClass.getESuperTypes().add(theXpdl2Package.getNamedElement());
        faultMessageEClass.getESuperTypes().add(theXpdl2Package.getMessage());
        interfaceMethodEClass.getESuperTypes().add(theXpdl2Package.getNamedElement());
        interfaceMethodEClass.getESuperTypes().add(theXpdl2Package.getDescribedElement());
        interfaceMethodEClass.getESuperTypes().add(this.getAssociatedParametersContainer());
        intermediateMethodEClass.getESuperTypes().add(this.getInterfaceMethod());
        processInterfaceEClass.getESuperTypes().add(theXpdl2Package.getNamedElement());
        processInterfaceEClass.getESuperTypes().add(theXpdl2Package.getDescribedElement());
        processInterfaceEClass.getESuperTypes().add(theXpdl2Package.getExtendedAttributesContainer());
        processInterfaceEClass.getESuperTypes().add(theXpdl2Package.getFormalParametersContainer());
        processInterfaceEClass.getESuperTypes().add(theXpdl2Package.getOtherAttributesContainer());
        processInterfaceEClass.getESuperTypes().add(theXpdl2Package.getOtherElementsContainer());
        rescheduleTimerScriptEClass.getESuperTypes().add(theXpdl2Package.getExpression());
        restServiceResourceSecurityEClass.getESuperTypes().add(theXpdl2Package.getExtendedAttributesContainer());
        retainFamiliarActivitiesEClass.getESuperTypes().add(theXpdl2Package.getNamedElement());
        scriptInformationEClass.getESuperTypes().add(theXpdl2Package.getNamedElement());
        separationOfDutiesActivitiesEClass.getESuperTypes().add(theXpdl2Package.getNamedElement());
        startMethodEClass.getESuperTypes().add(this.getInterfaceMethod());
        transformScriptEClass.getESuperTypes().add(theXpdl2Package.getExtendedAttributesContainer());
        wsSecurityPolicyEClass.getESuperTypes().add(theXpdl2Package.getExtendedAttributesContainer());
        wsSoapBindingEClass.getESuperTypes().add(this.getWsBinding());
        wsSoapHttpInboundBindingEClass.getESuperTypes().add(this.getWsSoapBinding());
        wsSoapHttpOutboundBindingEClass.getESuperTypes().add(this.getWsSoapBinding());
        wsSoapJmsInboundBindingEClass.getESuperTypes().add(this.getWsSoapBinding());
        wsSoapJmsOutboundBindingEClass.getESuperTypes().add(this.getWsSoapBinding());
        wsVirtualBindingEClass.getESuperTypes().add(this.getWsBinding());
        caseServiceEClass.getESuperTypes().add(theXpdl2Package.getOtherElementsContainer());
        scriptDataMapperEClass.getESuperTypes().add(theXpdl2Package.getOtherElementsContainer());
        scriptDataMapperEClass.getESuperTypes().add(theXpdl2Package.getOtherAttributesContainer());

        // Initialize classes and features; add operations and parameters
        initEClass(activityRefEClass,
                ActivityRef.class,
                "ActivityRef", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getActivityRef_IdRef(),
                theXpdl2Package.getIdReferenceString(),
                "idRef", //$NON-NLS-1$
                null,
                1,
                1,
                ActivityRef.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        addEOperation(activityRefEClass, theXpdl2Package.getActivity(), "getActivity", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

        initEClass(activityResourcePatternsEClass,
                ActivityResourcePatterns.class,
                "ActivityResourcePatterns", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getActivityResourcePatterns_AllocationStrategy(),
                this.getAllocationStrategy(),
                null,
                "allocationStrategy", //$NON-NLS-1$
                null,
                0,
                1,
                ActivityResourcePatterns.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getActivityResourcePatterns_Piling(),
                this.getPilingInfo(),
                null,
                "piling", //$NON-NLS-1$
                null,
                0,
                1,
                ActivityResourcePatterns.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getActivityResourcePatterns_WorkItemPriority(),
                this.getWorkItemPriority(),
                null,
                "workItemPriority", //$NON-NLS-1$
                null,
                0,
                1,
                ActivityResourcePatterns.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(allocationStrategyEClass,
                AllocationStrategy.class,
                "AllocationStrategy", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAllocationStrategy_Offer(),
                this.getAllocationType(),
                "offer", //$NON-NLS-1$
                null,
                0,
                1,
                AllocationStrategy.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getAllocationStrategy_Strategy(),
                this.getAllocationStrategyType(),
                "strategy", //$NON-NLS-1$
                null,
                0,
                1,
                AllocationStrategy.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getAllocationStrategy_ReOfferOnClose(),
                theXMLTypePackage.getBoolean(),
                "reOfferOnClose", //$NON-NLS-1$
                null,
                0,
                1,
                AllocationStrategy.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getAllocationStrategy_ReOfferOnCancel(),
                theXMLTypePackage.getBoolean(),
                "reOfferOnCancel", //$NON-NLS-1$
                null,
                0,
                1,
                AllocationStrategy.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getAllocationStrategy_AllocateToOfferSetMemberIdentifier(),
                theXMLTypePackage.getString(),
                "allocateToOfferSetMemberIdentifier", //$NON-NLS-1$
                null,
                0,
                1,
                AllocationStrategy.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(associatedCorrelationFieldsEClass,
                AssociatedCorrelationFields.class,
                "AssociatedCorrelationFields", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getAssociatedCorrelationFields_AssociatedCorrelationField(),
                this.getAssociatedCorrelationField(),
                null,
                "associatedCorrelationField", //$NON-NLS-1$
                null,
                0,
                -1,
                AssociatedCorrelationFields.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getAssociatedCorrelationFields_DisableImplicitAssociation(),
                theXMLTypePackage.getBoolean(),
                "disableImplicitAssociation", //$NON-NLS-1$
                null,
                0,
                1,
                AssociatedCorrelationFields.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(associatedCorrelationFieldEClass,
                AssociatedCorrelationField.class,
                "AssociatedCorrelationField", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAssociatedCorrelationField_Name(),
                theXMLTypePackage.getString(),
                "name", //$NON-NLS-1$
                null,
                0,
                1,
                AssociatedCorrelationField.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getAssociatedCorrelationField_CorrelationMode(),
                this.getCorrelationMode(),
                "correlationMode", //$NON-NLS-1$
                null,
                0,
                1,
                AssociatedCorrelationField.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(associatedParameterEClass,
                AssociatedParameter.class,
                "AssociatedParameter", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAssociatedParameter_FormalParam(),
                theXMLTypePackage.getString(),
                "formalParam", //$NON-NLS-1$
                null,
                0,
                1,
                AssociatedParameter.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getAssociatedParameter_Mode(),
                theXpdl2Package.getModeType(),
                "mode", //$NON-NLS-1$
                null,
                0,
                1,
                AssociatedParameter.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getAssociatedParameter_Mandatory(),
                theXMLTypePackage.getBoolean(),
                "mandatory", //$NON-NLS-1$
                null,
                0,
                1,
                AssociatedParameter.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(associatedParametersEClass,
                AssociatedParameters.class,
                "AssociatedParameters", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getAssociatedParameters_AssociatedParameter(),
                this.getAssociatedParameter(),
                null,
                "associatedParameter", //$NON-NLS-1$
                null,
                0,
                -1,
                AssociatedParameters.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getAssociatedParameters_DisableImplicitAssociation(),
                theXMLTypePackage.getBoolean(),
                "disableImplicitAssociation", //$NON-NLS-1$
                null,
                0,
                1,
                AssociatedParameters.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(associatedParametersContainerEClass,
                AssociatedParametersContainer.class,
                "AssociatedParametersContainer", //$NON-NLS-1$
                IS_ABSTRACT,
                IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getAssociatedParametersContainer_AssociatedParameters(),
                this.getAssociatedParameter(),
                null,
                "associatedParameters", //$NON-NLS-1$
                null,
                0,
                -1,
                AssociatedParametersContainer.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getAssociatedParametersContainer_DisableImplicitAssociation(),
                theXMLTypePackage.getBoolean(),
                "disableImplicitAssociation", //$NON-NLS-1$
                null,
                0,
                1,
                AssociatedParametersContainer.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(auditEClass, Audit.class, "Audit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getAudit_AuditEvent(),
                this.getAuditEvent(),
                null,
                "auditEvent", //$NON-NLS-1$
                null,
                0,
                -1,
                Audit.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getAudit_Any(),
                ecorePackage.getEFeatureMapEntry(),
                "any", //$NON-NLS-1$
                null,
                0,
                -1,
                Audit.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(auditEventEClass,
                AuditEvent.class,
                "AuditEvent", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAuditEvent_Type(),
                this.getAuditEventType(),
                "type", //$NON-NLS-1$
                "Initiated", //$NON-NLS-1$
                1,
                1,
                AuditEvent.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getAuditEvent_Information(),
                theXpdl2Package.getExpression(),
                null,
                "information", //$NON-NLS-1$
                null,
                0,
                1,
                AuditEvent.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(businessProcessEClass,
                BusinessProcess.class,
                "BusinessProcess", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getBusinessProcess_ProcessId(),
                theXpdl2Package.getIdReferenceString(),
                "processId", //$NON-NLS-1$
                null,
                0,
                1,
                BusinessProcess.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getBusinessProcess_PackageRefId(),
                theXpdl2Package.getIdReferenceString(),
                "packageRefId", //$NON-NLS-1$
                null,
                0,
                1,
                BusinessProcess.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getBusinessProcess_ActivityId(),
                theXpdl2Package.getIdReferenceString(),
                "activityId", //$NON-NLS-1$
                null,
                0,
                1,
                BusinessProcess.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(calendarReferenceEClass,
                CalendarReference.class,
                "CalendarReference", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getCalendarReference_Alias(),
                theXMLTypePackage.getString(),
                "alias", //$NON-NLS-1$
                null,
                0,
                1,
                CalendarReference.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getCalendarReference_DataFieldId(),
                theXpdl2Package.getIdReferenceString(),
                "dataFieldId", //$NON-NLS-1$
                null,
                0,
                1,
                CalendarReference.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(catchErrorMappingsEClass,
                CatchErrorMappings.class,
                "CatchErrorMappings", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getCatchErrorMappings_Message(),
                theXpdl2Package.getMessage(),
                null,
                "message", //$NON-NLS-1$
                null,
                0,
                1,
                CatchErrorMappings.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(constantPeriodEClass,
                ConstantPeriod.class,
                "ConstantPeriod", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getConstantPeriod_Days(),
                theXMLTypePackage.getInteger(),
                "days", //$NON-NLS-1$
                null,
                0,
                1,
                ConstantPeriod.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getConstantPeriod_Hours(),
                theXMLTypePackage.getInteger(),
                "hours", //$NON-NLS-1$
                null,
                0,
                1,
                ConstantPeriod.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getConstantPeriod_MicroSeconds(),
                theXMLTypePackage.getInteger(),
                "microSeconds", //$NON-NLS-1$
                null,
                0,
                1,
                ConstantPeriod.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getConstantPeriod_Minutes(),
                theXMLTypePackage.getInteger(),
                "minutes", //$NON-NLS-1$
                null,
                0,
                1,
                ConstantPeriod.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getConstantPeriod_Months(),
                theXMLTypePackage.getInteger(),
                "months", //$NON-NLS-1$
                null,
                0,
                1,
                ConstantPeriod.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getConstantPeriod_Seconds(),
                theXMLTypePackage.getInteger(),
                "seconds", //$NON-NLS-1$
                null,
                0,
                1,
                ConstantPeriod.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getConstantPeriod_Weeks(),
                theXMLTypePackage.getInteger(),
                "weeks", //$NON-NLS-1$
                null,
                0,
                1,
                ConstantPeriod.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getConstantPeriod_Years(),
                theXMLTypePackage.getInteger(),
                "years", //$NON-NLS-1$
                null,
                0,
                1,
                ConstantPeriod.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(conditionalParticipantEClass,
                ConditionalParticipant.class,
                "ConditionalParticipant", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getConditionalParticipant_PerformerScript(),
                theXpdl2Package.getExpression(),
                null,
                "performerScript", //$NON-NLS-1$
                null,
                0,
                1,
                ConditionalParticipant.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(replyImmediateDataMappingsEClass,
                ReplyImmediateDataMappings.class,
                "ReplyImmediateDataMappings", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getReplyImmediateDataMappings_DataMappings(),
                theXpdl2Package.getDataMapping(),
                null,
                "dataMappings", //$NON-NLS-1$
                null,
                0,
                -1,
                ReplyImmediateDataMappings.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(correlationDataMappingsEClass,
                CorrelationDataMappings.class,
                "CorrelationDataMappings", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getCorrelationDataMappings_DataMappings(),
                theXpdl2Package.getDataMapping(),
                null,
                "dataMappings", //$NON-NLS-1$
                null,
                0,
                -1,
                CorrelationDataMappings.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(discriminatorEClass,
                Discriminator.class,
                "Discriminator", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDiscriminator_DiscriminatorType(),
                theXMLTypePackage.getString(),
                "discriminatorType", //$NON-NLS-1$
                null,
                0,
                1,
                Discriminator.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDiscriminator_StructuredDiscriminator(),
                this.getStructuredDiscriminator(),
                null,
                "structuredDiscriminator", //$NON-NLS-1$
                null,
                0,
                1,
                Discriminator.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(documentRootEClass,
                DocumentRoot.class,
                "DocumentRoot", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getDocumentRoot_XMLNSPrefixMap(),
                ecorePackage.getEStringToStringMapEntry(),
                null,
                "xMLNSPrefixMap", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_XSISchemaLocation(),
                ecorePackage.getEStringToStringMapEntry(),
                null,
                "xSISchemaLocation", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_ImplementationType(),
                theXMLTypePackage.getString(),
                "implementationType", //$NON-NLS-1$
                null,
                1,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_DataObjectAttributes(),
                this.getXpdExtDataObjectAttributes(),
                null,
                "dataObjectAttributes", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_ExtendedAttributes(),
                this.getXpdExtAttributes(),
                null,
                "extendedAttributes", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_ContinueOnTimeout(),
                theXMLTypePackage.getBoolean(),
                "continueOnTimeout", //$NON-NLS-1$
                null,
                1,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_Alias(),
                theXpdl2Package.getIdReferenceString(),
                "alias", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_ConstantPeriod(),
                this.getConstantPeriod(),
                null,
                "constantPeriod", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_UserTaskScripts(),
                this.getUserTaskScripts(),
                null,
                "userTaskScripts", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_Audit(),
                this.getAudit(),
                null,
                "audit", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                IS_TRANSIENT,
                IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_Script(),
                this.getScriptInformation(),
                null,
                "script", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_ReplyImmediately(),
                theXMLTypePackage.getBoolean(),
                "replyImmediately", //$NON-NLS-1$
                null,
                1,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_InitialValues(),
                this.getInitialValues(),
                null,
                "initialValues", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_AssociatedCorrelationFields(),
                this.getAssociatedCorrelationFields(),
                null,
                "associatedCorrelationFields", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_AssociatedParameters(),
                this.getAssociatedParameters(),
                null,
                "associatedParameters", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_ImplementedInterface(),
                this.getImplementedInterface(),
                null,
                "implementedInterface", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_ProcessInterfaces(),
                this.getProcessInterfaces(),
                null,
                "processInterfaces", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_WsdlEventAssociation(),
                this.getWsdlEventAssociation(),
                null,
                "wsdlEventAssociation", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_InlineSubProcess(),
                theXMLTypePackage.getBoolean(),
                "inlineSubProcess", //$NON-NLS-1$
                null,
                1,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_DocumentationURL(),
                theXMLTypePackage.getString(),
                "documentationURL", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_Implements(),
                theXpdl2Package.getIdReferenceString(),
                "implements", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_MultiInstanceScripts(),
                this.getMultiInstanceScripts(),
                null,
                "multiInstanceScripts", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_ProcessIdentifierField(),
                theXMLTypePackage.getString(),
                "processIdentifierField", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_Expression(),
                theXpdl2Package.getExpression(),
                null,
                "expression", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_Visibility(),
                this.getVisibility(),
                "visibility", //$NON-NLS-1$
                null,
                1,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_SecurityProfile(),
                theXMLTypePackage.getString(),
                "securityProfile", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_Language(),
                theXMLTypePackage.getString(),
                "language", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_InitialParameterValue(),
                this.getInitialParameterValue(),
                null,
                "initialParameterValue", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_InitialValueMapping(),
                theXMLTypePackage.getBoolean(),
                "initialValueMapping", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_PortTypeOperation(),
                this.getPortTypeOperation(),
                null,
                "portTypeOperation", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_Transport(),
                theXMLTypePackage.getString(),
                "transport", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_IsChained(),
                theXMLTypePackage.getBoolean(),
                "isChained", //$NON-NLS-1$
                null,
                1,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_ExternalReference(),
                theXpdl2Package.getExternalReference(),
                null,
                "externalReference", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_ProcessResourcePatterns(),
                this.getProcessResourcePatterns(),
                null,
                "processResourcePatterns", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_EventHandlerInitialisers(),
                this.getEventHandlerInitialisers(),
                null,
                "eventHandlerInitialisers", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_ActivityResourcePatterns(),
                this.getActivityResourcePatterns(),
                null,
                "activityResourcePatterns", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_RequireNewTransaction(),
                theXMLTypePackage.getBoolean(),
                "requireNewTransaction", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_DocumentOperation(),
                this.getDocumentOperation(),
                null,
                "documentOperation", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_DurationCalculation(),
                this.getDurationCalculation(),
                null,
                "durationCalculation", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_Discriminator(),
                this.getDiscriminator(),
                null,
                "discriminator", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_DisplayName(),
                theXMLTypePackage.getString(),
                "displayName", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_CatchThrow(),
                theXpdl2Package.getCatchThrow(),
                "catchThrow", //$NON-NLS-1$
                null,
                1,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_IsRemote(),
                theXMLTypePackage.getBoolean(),
                "isRemote", //$NON-NLS-1$
                null,
                1,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_CorrelationDataMappings(),
                this.getCorrelationDataMappings(),
                null,
                "correlationDataMappings", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_TransformScript(),
                this.getTransformScript(),
                null,
                "transformScript", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_PublishAsBusinessService(),
                theXMLTypePackage.getBoolean(),
                "publishAsBusinessService", //$NON-NLS-1$
                null,
                1,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_BusinessServiceCategory(),
                theXMLTypePackage.getString(),
                "businessServiceCategory", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_ErrorThrowerInfo(),
                this.getErrorThrowerInfo(),
                null,
                "errorThrowerInfo", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_CatchErrorMappings(),
                this.getCatchErrorMappings(),
                null,
                "catchErrorMappings", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_ConditionalParticipant(),
                this.getConditionalParticipant(),
                null,
                "ConditionalParticipant", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_Generated(),
                theXMLTypePackage.getBoolean(),
                "generated", //$NON-NLS-1$
                null,
                1,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_ReplyToActivityId(),
                theXpdl2Package.getIdReferenceString(),
                "replyToActivityId", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_TaskLibraryReference(),
                this.getTaskLibraryReference(),
                null,
                "taskLibraryReference", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_SetPerformerInProcess(),
                theXMLTypePackage.getBoolean(),
                "setPerformerInProcess", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_EmbSubprocOtherStateHeight(),
                theXMLTypePackage.getDouble(),
                "embSubprocOtherStateHeight", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_EmbSubprocOtherStateWidth(),
                theXMLTypePackage.getDouble(),
                "embSubprocOtherStateWidth", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_FormImplementation(),
                this.getFormImplementation(),
                null,
                "formImplementation", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_ParticipantQuery(),
                theXpdl2Package.getExpression(),
                null,
                "participantQuery", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_ApiEndPointParticipant(),
                theXpdl2Package.getIdReferenceString(),
                "apiEndPointParticipant", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_FaultMessage(),
                theXpdl2Package.getMessage(),
                null,
                "faultMessage", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_RequestActivityId(),
                theXpdl2Package.getIdReferenceString(),
                "requestActivityId", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_BusinessProcess(),
                this.getBusinessProcess(),
                null,
                "businessProcess", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_WsdlGeneration(),
                this.getWsdlGeneration(),
                null,
                "wsdlGeneration", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_TargetPrimitiveProperty(),
                theXMLTypePackage.getBoolean(),
                "targetPrimitiveProperty", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_SourcePrimitiveProperty(),
                theXMLTypePackage.getBoolean(),
                "sourcePrimitiveProperty", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_DecisionService(),
                theXpdl2Package.getSubFlow(),
                null,
                "decisionService", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_ParticipantSharedResource(),
                this.getParticipantSharedResource(),
                null,
                "participantSharedResource", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_XpdModelType(),
                this.getXpdModelType(),
                "xpdModelType", //$NON-NLS-1$
                "PageFlow", //$NON-NLS-1$
                1,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_FlowRoutingStyle(),
                this.getFlowRoutingStyle(),
                "flowRoutingStyle", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_CalendarReference(),
                this.getCalendarReference(),
                null,
                "calendarReference", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_NonCancelling(),
                theXMLTypePackage.getBoolean(),
                "nonCancelling", //$NON-NLS-1$
                null,
                1,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_SignalData(),
                this.getSignalData(),
                null,
                "signalData", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                IS_TRANSIENT,
                IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_Retry(),
                this.getRetry(),
                null,
                "retry", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_ActivityDeadlineEventId(),
                theXpdl2Package.getIdReferenceString(),
                "activityDeadlineEventId", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_StartStrategy(),
                this.getSubProcessStartStrategy(),
                "startStrategy", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_OverwriteAlreadyModifiedTaskData(),
                theXMLTypePackage.getBoolean(),
                "overwriteAlreadyModifiedTaskData", //$NON-NLS-1$
                null,
                1,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_EventHandlerFlowStrategy(),
                this.getEventHandlerFlowStrategy(),
                "eventHandlerFlowStrategy", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_NamespacePrefixMap(),
                this.getNamespacePrefixMap(),
                null,
                "namespacePrefixMap", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_SuspendResumeWithParent(),
                theXMLTypePackage.getBoolean(),
                "suspendResumeWithParent", //$NON-NLS-1$
                null,
                1,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_SystemErrorAction(),
                this.getSystemErrorActionType(),
                "systemErrorAction", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_CorrelationTimeout(),
                this.getConstantPeriod(),
                null,
                "correlationTimeout", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_ValidationControl(),
                this.getValidationControl(),
                null,
                "validationControl", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_ReplyImmediateDataMappings(),
                this.getReplyImmediateDataMappings(),
                null,
                "replyImmediateDataMappings", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_BxUseUnqualifiedPropertyNames(),
                theXMLTypePackage.getBoolean(),
                "bxUseUnqualifiedPropertyNames", //$NON-NLS-1$
                null,
                1,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_CaseRefType(),
                theXpdl2Package.getRecordType(),
                null,
                "caseRefType", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_RESTServices(),
                this.getRESTServices(),
                null,
                "RESTServices", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_PublishAsRestService(),
                theXMLTypePackage.getBoolean(),
                "publishAsRestService", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_RestActivityId(),
                theXMLTypePackage.getString(),
                "restActivityId", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_RescheduleTimerScript(),
                this.getRescheduleTimerScript(),
                null,
                "rescheduleTimerScript", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                IS_TRANSIENT,
                IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_DynamicOrganizationMappings(),
                this.getDynamicOrganizationMappings(),
                null,
                "dynamicOrganizationMappings", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_SignalHandlerAsynchronous(),
                theXMLTypePackage.getBoolean(),
                "signalHandlerAsynchronous", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_GlobalDataOperation(),
                this.getGlobalDataOperation(),
                null,
                "globalDataOperation", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_ProcessDataWorkItemAttributeMappings(),
                this.getProcessDataWorkItemAttributeMappings(),
                null,
                "processDataWorkItemAttributeMappings", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_AllowUnqualifiedSubProcessIdentification(),
                theXMLTypePackage.getBoolean(),
                "allowUnqualifiedSubProcessIdentification", //$NON-NLS-1$
                null,
                1,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_BpmRuntimeConfiguration(),
                this.getBpmRuntimeConfiguration(),
                null,
                "bpmRuntimeConfiguration", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_IsCaseService(),
                theXMLTypePackage.getBoolean(),
                "isCaseService", //$NON-NLS-1$
                null,
                1,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_RequiredAccessPrivileges(),
                this.getRequiredAccessPrivileges(),
                null,
                "requiredAccessPrivileges", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_CaseService(),
                this.getCaseService(),
                null,
                "caseService", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_AdHocTaskConfiguration(),
                this.getAdHocTaskConfigurationType(),
                null,
                "adHocTaskConfiguration", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_IsEventSubProcess(),
                theXMLTypePackage.getBoolean(),
                "isEventSubProcess", //$NON-NLS-1$
                null,
                1,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_NonInterruptingEvent(),
                theXMLTypePackage.getBoolean(),
                "nonInterruptingEvent", //$NON-NLS-1$
                null,
                1,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_CorrelateImmediately(),
                theXMLTypePackage.getBoolean(),
                "correlateImmediately", //$NON-NLS-1$
                "false", //$NON-NLS-1$
                1,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_AsyncExecutionMode(),
                this.getAsyncExecutionMode(),
                "asyncExecutionMode", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_SignalType(),
                this.getSignalType(),
                "signalType", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_ServiceProcessConfiguration(),
                this.getServiceProcessConfiguration(),
                null,
                "serviceProcessConfiguration", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_LikeMapping(),
                theXMLTypePackage.getBoolean(),
                "likeMapping", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_ScriptDataMapper(),
                this.getScriptDataMapper(),
                null,
                "scriptDataMapper", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_LikeMappingExclusions(),
                this.getLikeMappingExclusions(),
                null,
                "likeMappingExclusions", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_SourceContributorId(),
                theXMLTypePackage.getString(),
                "sourceContributorId", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_TargetContributorId(),
                theXMLTypePackage.getString(),
                "targetContributorId", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_RestServiceOperation(),
                this.getRestServiceOperation(),
                null,
                "restServiceOperation", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_InputMappings(),
                this.getScriptDataMapper(),
                null,
                "inputMappings", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_OutputMappings(),
                this.getScriptDataMapper(),
                null,
                "outputMappings", //$NON-NLS-1$
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_BusinessServicePublishType(),
                this.getBusinessServicePublishType(),
                "businessServicePublishType", //$NON-NLS-1$
                null,
                0,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_SuppressMaxMappingsError(),
                theXMLTypePackage.getBoolean(),
                "suppressMaxMappingsError", //$NON-NLS-1$
                null,
                1,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDocumentRoot_FieldFormat(),
                this.getFieldFormat(),
                "fieldFormat", //$NON-NLS-1$
                null,
                1,
                1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(durationCalculationEClass,
                DurationCalculation.class,
                "DurationCalculation", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getDurationCalculation_Years(),
                theXpdl2Package.getExpression(),
                null,
                "years", //$NON-NLS-1$
                null,
                0,
                1,
                DurationCalculation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDurationCalculation_Months(),
                theXpdl2Package.getExpression(),
                null,
                "months", //$NON-NLS-1$
                null,
                0,
                1,
                DurationCalculation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDurationCalculation_Weeks(),
                theXpdl2Package.getExpression(),
                null,
                "weeks", //$NON-NLS-1$
                null,
                0,
                1,
                DurationCalculation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDurationCalculation_Days(),
                theXpdl2Package.getExpression(),
                null,
                "days", //$NON-NLS-1$
                null,
                0,
                1,
                DurationCalculation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDurationCalculation_Hours(),
                theXpdl2Package.getExpression(),
                null,
                "hours", //$NON-NLS-1$
                null,
                0,
                1,
                DurationCalculation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDurationCalculation_Minutes(),
                theXpdl2Package.getExpression(),
                null,
                "minutes", //$NON-NLS-1$
                null,
                0,
                1,
                DurationCalculation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDurationCalculation_Seconds(),
                theXpdl2Package.getExpression(),
                null,
                "seconds", //$NON-NLS-1$
                null,
                0,
                1,
                DurationCalculation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDurationCalculation_Microseconds(),
                theXpdl2Package.getExpression(),
                null,
                "microseconds", //$NON-NLS-1$
                null,
                0,
                1,
                DurationCalculation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(dynamicOrganizationMappingsEClass,
                DynamicOrganizationMappings.class,
                "DynamicOrganizationMappings", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getDynamicOrganizationMappings_DynamicOrganizationMapping(),
                this.getDynamicOrganizationMapping(),
                null,
                "dynamicOrganizationMapping", //$NON-NLS-1$
                null,
                0,
                -1,
                DynamicOrganizationMappings.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(dynamicOrganizationMappingEClass,
                DynamicOrganizationMapping.class,
                "DynamicOrganizationMapping", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDynamicOrganizationMapping_SourcePath(),
                ecorePackage.getEString(),
                "sourcePath", //$NON-NLS-1$
                null,
                0,
                1,
                DynamicOrganizationMapping.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDynamicOrganizationMapping_DynamicOrgIdentifierRef(),
                this.getDynamicOrgIdentifierRef(),
                null,
                "dynamicOrgIdentifierRef", //$NON-NLS-1$
                null,
                0,
                1,
                DynamicOrganizationMapping.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(dynamicOrgIdentifierRefEClass,
                DynamicOrgIdentifierRef.class,
                "DynamicOrgIdentifierRef", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDynamicOrgIdentifierRef_IdentifierName(),
                ecorePackage.getEString(),
                "identifierName", //$NON-NLS-1$
                null,
                0,
                1,
                DynamicOrgIdentifierRef.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDynamicOrgIdentifierRef_DynamicOrgId(),
                ecorePackage.getEString(),
                "dynamicOrgId", //$NON-NLS-1$
                null,
                0,
                1,
                DynamicOrgIdentifierRef.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDynamicOrgIdentifierRef_OrgModelPath(),
                ecorePackage.getEString(),
                "orgModelPath", //$NON-NLS-1$
                null,
                0,
                1,
                DynamicOrgIdentifierRef.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(emailResourceEClass,
                EmailResource.class,
                "EmailResource", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getEmailResource_InstanceName(),
                theXMLTypePackage.getString(),
                "instanceName", //$NON-NLS-1$
                null,
                0,
                1,
                EmailResource.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(errorMethodEClass,
                ErrorMethod.class,
                "ErrorMethod", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getErrorMethod_ErrorCode(),
                theXMLTypePackage.getString(),
                "errorCode", //$NON-NLS-1$
                null,
                0,
                1,
                ErrorMethod.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(errorThrowerInfoEClass,
                ErrorThrowerInfo.class,
                "ErrorThrowerInfo", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getErrorThrowerInfo_ThrowerId(),
                theXpdl2Package.getIdReferenceString(),
                "throwerId", //$NON-NLS-1$
                null,
                0,
                1,
                ErrorThrowerInfo.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getErrorThrowerInfo_ThrowerContainerId(),
                theXpdl2Package.getIdReferenceString(),
                "throwerContainerId", //$NON-NLS-1$
                null,
                0,
                1,
                ErrorThrowerInfo.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getErrorThrowerInfo_ThrowerType(),
                this.getErrorThrowerType(),
                "throwerType", //$NON-NLS-1$
                null,
                0,
                1,
                ErrorThrowerInfo.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(eventHandlerInitialisersEClass,
                EventHandlerInitialisers.class,
                "EventHandlerInitialisers", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getEventHandlerInitialisers_ActivityRef(),
                this.getActivityRef(),
                null,
                "activityRef", //$NON-NLS-1$
                null,
                0,
                -1,
                EventHandlerInitialisers.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(faultMessageEClass,
                FaultMessage.class,
                "FaultMessage", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        initEClass(formImplementationEClass,
                FormImplementation.class,
                "FormImplementation", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getFormImplementation_FormType(),
                this.getFormImplementationType(),
                "formType", //$NON-NLS-1$
                null,
                0,
                1,
                FormImplementation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getFormImplementation_FormURI(),
                theXMLTypePackage.getString(),
                "formURI", //$NON-NLS-1$
                null,
                0,
                1,
                FormImplementation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(implementedInterfaceEClass,
                ImplementedInterface.class,
                "ImplementedInterface", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getImplementedInterface_PackageRef(),
                theXpdl2Package.getIdReferenceString(),
                "packageRef", //$NON-NLS-1$
                null,
                0,
                1,
                ImplementedInterface.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getImplementedInterface_ProcessInterfaceId(),
                theXpdl2Package.getIdReferenceString(),
                "processInterfaceId", //$NON-NLS-1$
                null,
                1,
                1,
                ImplementedInterface.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(initialValuesEClass,
                InitialValues.class,
                "InitialValues", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getInitialValues_Value(),
                ecorePackage.getEString(),
                "value", //$NON-NLS-1$
                null,
                0,
                -1,
                InitialValues.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(initialParameterValueEClass,
                InitialParameterValue.class,
                "InitialParameterValue", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getInitialParameterValue_Name(),
                theXMLTypePackage.getString(),
                "name", //$NON-NLS-1$
                null,
                1,
                1,
                InitialParameterValue.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getInitialParameterValue_Value(),
                theXMLTypePackage.getString(),
                "value", //$NON-NLS-1$
                null,
                1,
                1,
                InitialParameterValue.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(interfaceMethodEClass,
                InterfaceMethod.class,
                "InterfaceMethod", //$NON-NLS-1$
                IS_ABSTRACT,
                IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getInterfaceMethod_Trigger(),
                theXpdl2Package.getTriggerType(),
                "trigger", //$NON-NLS-1$
                null,
                1,
                1,
                InterfaceMethod.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getInterfaceMethod_TriggerResultMessage(),
                theXpdl2Package.getTriggerResultMessage(),
                null,
                "triggerResultMessage", //$NON-NLS-1$
                null,
                0,
                1,
                InterfaceMethod.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getInterfaceMethod_Visibility(),
                this.getVisibility(),
                "visibility", //$NON-NLS-1$
                null,
                1,
                1,
                InterfaceMethod.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getInterfaceMethod_ErrorMethods(),
                this.getErrorMethod(),
                null,
                "errorMethods", //$NON-NLS-1$
                null,
                0,
                -1,
                InterfaceMethod.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(intermediateMethodEClass,
                IntermediateMethod.class,
                "IntermediateMethod", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        initEClass(jdbcResourceEClass,
                JdbcResource.class,
                "JdbcResource", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getJdbcResource_InstanceName(),
                theXMLTypePackage.getString(),
                "instanceName", //$NON-NLS-1$
                null,
                0,
                1,
                JdbcResource.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getJdbcResource_JdbcProfileName(),
                theXMLTypePackage.getString(),
                "jdbcProfileName", //$NON-NLS-1$
                null,
                0,
                1,
                JdbcResource.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(multiInstanceScriptsEClass,
                MultiInstanceScripts.class,
                "MultiInstanceScripts", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getMultiInstanceScripts_AdditionalInstances(),
                theXpdl2Package.getExpression(),
                null,
                "additionalInstances", //$NON-NLS-1$
                null,
                0,
                1,
                MultiInstanceScripts.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(namespacePrefixMapEClass,
                NamespacePrefixMap.class,
                "NamespacePrefixMap", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getNamespacePrefixMap_NamespaceEntries(),
                this.getNamespaceMapEntry(),
                null,
                "namespaceEntries", //$NON-NLS-1$
                null,
                0,
                -1,
                NamespacePrefixMap.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getNamespacePrefixMap_PrefixMappingDisabled(),
                theXMLTypePackage.getBoolean(),
                "PrefixMappingDisabled", //$NON-NLS-1$
                "false", //$NON-NLS-1$
                1,
                1,
                NamespacePrefixMap.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(namespaceMapEntryEClass,
                NamespaceMapEntry.class,
                "NamespaceMapEntry", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getNamespaceMapEntry_Prefix(),
                theXMLTypePackage.getNMTOKEN(),
                "Prefix", //$NON-NLS-1$
                null,
                1,
                1,
                NamespaceMapEntry.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getNamespaceMapEntry_Namespace(),
                theXMLTypePackage.getAnyURI(),
                "Namespace", //$NON-NLS-1$
                null,
                1,
                1,
                NamespaceMapEntry.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(participantSharedResourceEClass,
                ParticipantSharedResource.class,
                "ParticipantSharedResource", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getParticipantSharedResource_Email(),
                this.getEmailResource(),
                null,
                "email", //$NON-NLS-1$
                null,
                0,
                1,
                ParticipantSharedResource.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getParticipantSharedResource_Jdbc(),
                this.getJdbcResource(),
                null,
                "jdbc", //$NON-NLS-1$
                null,
                0,
                1,
                ParticipantSharedResource.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getParticipantSharedResource_WebService(),
                this.getWsResource(),
                null,
                "webService", //$NON-NLS-1$
                null,
                0,
                1,
                ParticipantSharedResource.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getParticipantSharedResource_RestService(),
                this.getRestServiceResource(),
                null,
                "restService", //$NON-NLS-1$
                null,
                0,
                1,
                ParticipantSharedResource.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        addEOperation(participantSharedResourceEClass,
                ecorePackage.getEObject(),
                "getSharedResource", //$NON-NLS-1$
                0,
                1,
                IS_UNIQUE,
                IS_ORDERED);

        EOperation op =
                addEOperation(participantSharedResourceEClass, null, "setSharedResource", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
        addEParameter(op, ecorePackage.getEObject(), "sharedResource", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

        initEClass(pilingInfoEClass,
                PilingInfo.class,
                "PilingInfo", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getPilingInfo_PilingAllowed(),
                theXMLTypePackage.getBoolean(),
                "pilingAllowed", //$NON-NLS-1$
                null,
                0,
                1,
                PilingInfo.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getPilingInfo_MaxPiliableItems(),
                theXMLTypePackage.getString(),
                "maxPiliableItems", //$NON-NLS-1$
                null,
                0,
                1,
                PilingInfo.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(portTypeOperationEClass,
                PortTypeOperation.class,
                "PortTypeOperation", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getPortTypeOperation_PortTypeName(),
                theXMLTypePackage.getString(),
                "portTypeName", //$NON-NLS-1$
                null,
                0,
                1,
                PortTypeOperation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getPortTypeOperation_OperationName(),
                theXMLTypePackage.getString(),
                "operationName", //$NON-NLS-1$
                null,
                0,
                1,
                PortTypeOperation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getPortTypeOperation_ExternalReference(),
                theXpdl2Package.getExternalReference(),
                null,
                "externalReference", //$NON-NLS-1$
                null,
                0,
                1,
                PortTypeOperation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getPortTypeOperation_Transport(),
                theXMLTypePackage.getString(),
                "transport", //$NON-NLS-1$
                null,
                0,
                1,
                PortTypeOperation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(processInterfaceEClass,
                ProcessInterface.class,
                "ProcessInterface", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getProcessInterface_StartMethods(),
                this.getStartMethod(),
                null,
                "startMethods", //$NON-NLS-1$
                null,
                0,
                -1,
                ProcessInterface.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getProcessInterface_IntermediateMethods(),
                this.getIntermediateMethod(),
                null,
                "intermediateMethods", //$NON-NLS-1$
                null,
                0,
                -1,
                ProcessInterface.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getProcessInterface_XpdInterfaceType(),
                this.getXpdInterfaceType(),
                "xpdInterfaceType", //$NON-NLS-1$
                null,
                1,
                1,
                ProcessInterface.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getProcessInterface_ServiceProcessConfiguration(),
                this.getServiceProcessConfiguration(),
                null,
                "serviceProcessConfiguration", //$NON-NLS-1$
                null,
                0,
                1,
                ProcessInterface.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(processInterfacesEClass,
                ProcessInterfaces.class,
                "ProcessInterfaces", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getProcessInterfaces_ProcessInterface(),
                this.getProcessInterface(),
                null,
                "processInterface", //$NON-NLS-1$
                null,
                0,
                -1,
                ProcessInterfaces.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(processResourcePatternsEClass,
                ProcessResourcePatterns.class,
                "ProcessResourcePatterns", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getProcessResourcePatterns_SeparationOfDutiesActivities(),
                this.getSeparationOfDutiesActivities(),
                null,
                "separationOfDutiesActivities", //$NON-NLS-1$
                null,
                0,
                -1,
                ProcessResourcePatterns.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getProcessResourcePatterns_RetainFamiliarActivities(),
                this.getRetainFamiliarActivities(),
                null,
                "retainFamiliarActivities", //$NON-NLS-1$
                null,
                0,
                -1,
                ProcessResourcePatterns.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(rescheduleTimerScriptEClass,
                RescheduleTimerScript.class,
                "RescheduleTimerScript", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getRescheduleTimerScript_DurationRelativeTo(),
                this.getRescheduleDurationType(),
                "durationRelativeTo", //$NON-NLS-1$
                null,
                1,
                1,
                RescheduleTimerScript.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(rescheduleTimersEClass,
                RescheduleTimers.class,
                "RescheduleTimers", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getRescheduleTimers_TimerSelectionType(),
                this.getRescheduleTimerSelectionType(),
                "timerSelectionType", //$NON-NLS-1$
                null,
                1,
                1,
                RescheduleTimers.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getRescheduleTimers_TimerEvents(),
                this.getActivityRef(),
                null,
                "timerEvents", //$NON-NLS-1$
                null,
                0,
                -1,
                RescheduleTimers.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(restServicesEClass,
                RESTServices.class,
                "RESTServices", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getRESTServices_RESTServices(),
                theXpdl2Package.getProcess(),
                null,
                "RESTServices", //$NON-NLS-1$
                null,
                0,
                -1,
                RESTServices.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(restServiceResourceEClass,
                RestServiceResource.class,
                "RestServiceResource", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getRestServiceResource_SecurityPolicy(),
                this.getRestServiceResourceSecurity(),
                null,
                "securityPolicy", //$NON-NLS-1$
                null,
                0,
                -1,
                RestServiceResource.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getRestServiceResource_ResourceName(),
                theXMLTypePackage.getString(),
                "resourceName", //$NON-NLS-1$
                null,
                0,
                1,
                RestServiceResource.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getRestServiceResource_ResourceType(),
                theXMLTypePackage.getString(),
                "resourceType", //$NON-NLS-1$
                null,
                0,
                1,
                RestServiceResource.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getRestServiceResource_Description(),
                theXMLTypePackage.getString(),
                "description", //$NON-NLS-1$
                null,
                0,
                1,
                RestServiceResource.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(restServiceResourceSecurityEClass,
                RestServiceResourceSecurity.class,
                "RestServiceResourceSecurity", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getRestServiceResourceSecurity_PolicyType(),
                this.getSecurityPolicy(),
                "policyType", //$NON-NLS-1$
                null,
                0,
                1,
                RestServiceResourceSecurity.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(retainFamiliarActivitiesEClass,
                RetainFamiliarActivities.class,
                "RetainFamiliarActivities", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getRetainFamiliarActivities_ActivityRef(),
                this.getActivityRef(),
                null,
                "activityRef", //$NON-NLS-1$
                null,
                0,
                -1,
                RetainFamiliarActivities.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(retryEClass, Retry.class, "Retry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getRetry_Max(),
                theXMLTypePackage.getInt(),
                "max", //$NON-NLS-1$
                null,
                0,
                1,
                Retry.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getRetry_InitialPeriod(),
                theXMLTypePackage.getInt(),
                "initialPeriod", //$NON-NLS-1$
                null,
                0,
                1,
                Retry.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getRetry_PeriodIncrement(),
                theXMLTypePackage.getInt(),
                "periodIncrement", //$NON-NLS-1$
                null,
                0,
                1,
                Retry.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getRetry_MaxRetryAction(),
                this.getMaxRetryActionType(),
                "maxRetryAction", //$NON-NLS-1$
                null,
                0,
                1,
                Retry.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(scriptInformationEClass,
                ScriptInformation.class,
                "ScriptInformation", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getScriptInformation_Expression(),
                theXpdl2Package.getExpression(),
                null,
                "expression", //$NON-NLS-1$
                null,
                0,
                1,
                ScriptInformation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getScriptInformation_Direction(),
                theXpdl2Package.getDirectionType(),
                "direction", //$NON-NLS-1$
                null,
                0,
                1,
                ScriptInformation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getScriptInformation_Activity(),
                theXpdl2Package.getActivity(),
                null,
                "activity", //$NON-NLS-1$
                null,
                0,
                1,
                ScriptInformation.class,
                IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_COMPOSITE,
                IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getScriptInformation_Reference(),
                theXMLTypePackage.getBoolean(),
                "reference", //$NON-NLS-1$
                null,
                0,
                1,
                ScriptInformation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(separationOfDutiesActivitiesEClass,
                SeparationOfDutiesActivities.class,
                "SeparationOfDutiesActivities", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getSeparationOfDutiesActivities_ActivityRef(),
                this.getActivityRef(),
                null,
                "activityRef", //$NON-NLS-1$
                null,
                0,
                -1,
                SeparationOfDutiesActivities.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(signalDataEClass,
                SignalData.class,
                "SignalData", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getSignalData_CorrelationMappings(),
                this.getCorrelationDataMappings(),
                null,
                "correlationMappings", //$NON-NLS-1$
                null,
                0,
                1,
                SignalData.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getSignalData_DataMappings(),
                theXpdl2Package.getDataMapping(),
                null,
                "dataMappings", //$NON-NLS-1$
                null,
                0,
                -1,
                SignalData.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getSignalData_RescheduleTimers(),
                this.getRescheduleTimers(),
                null,
                "rescheduleTimers", //$NON-NLS-1$
                null,
                0,
                1,
                SignalData.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getSignalData_InputScriptDataMapper(),
                this.getScriptDataMapper(),
                null,
                "inputScriptDataMapper", //$NON-NLS-1$
                null,
                0,
                1,
                SignalData.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getSignalData_OutputScriptDataMapper(),
                this.getScriptDataMapper(),
                null,
                "outputScriptDataMapper", //$NON-NLS-1$
                null,
                0,
                1,
                SignalData.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(startMethodEClass,
                StartMethod.class,
                "StartMethod", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        initEClass(structuredDiscriminatorEClass,
                StructuredDiscriminator.class,
                "StructuredDiscriminator", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getStructuredDiscriminator_WaitForIncomingPath(),
                theXMLTypePackage.getInteger(),
                "waitForIncomingPath", //$NON-NLS-1$
                null,
                1,
                1,
                StructuredDiscriminator.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getStructuredDiscriminator_UpStreamParallelSplit(),
                theXpdl2Package.getIdReferenceString(),
                "UpStreamParallelSplit", //$NON-NLS-1$
                null,
                1,
                1,
                StructuredDiscriminator.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(taskLibraryReferenceEClass,
                TaskLibraryReference.class,
                "TaskLibraryReference", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getTaskLibraryReference_TaskLibraryId(),
                theXpdl2Package.getIdReferenceString(),
                "taskLibraryId", //$NON-NLS-1$
                null,
                1,
                1,
                TaskLibraryReference.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getTaskLibraryReference_PackageRef(),
                theXMLTypePackage.getString(),
                "packageRef", //$NON-NLS-1$
                null,
                0,
                1,
                TaskLibraryReference.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(transformScriptEClass,
                TransformScript.class,
                "TransformScript", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getTransformScript_DataMappings(),
                theXpdl2Package.getDataMapping(),
                null,
                "dataMappings", //$NON-NLS-1$
                null,
                0,
                -1,
                TransformScript.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getTransformScript_InputDom(),
                theXMLTypePackage.getString(),
                "inputDom", //$NON-NLS-1$
                null,
                1,
                -1,
                TransformScript.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getTransformScript_OutputDom(),
                theXMLTypePackage.getString(),
                "outputDom", //$NON-NLS-1$
                null,
                1,
                -1,
                TransformScript.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(userTaskScriptsEClass,
                UserTaskScripts.class,
                "UserTaskScripts", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getUserTaskScripts_OpenScript(),
                theXpdl2Package.getExpression(),
                null,
                "openScript", //$NON-NLS-1$
                null,
                0,
                1,
                UserTaskScripts.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getUserTaskScripts_CloseScript(),
                theXpdl2Package.getExpression(),
                null,
                "closeScript", //$NON-NLS-1$
                null,
                0,
                1,
                UserTaskScripts.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getUserTaskScripts_SubmitScript(),
                theXpdl2Package.getExpression(),
                null,
                "submitScript", //$NON-NLS-1$
                null,
                0,
                1,
                UserTaskScripts.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getUserTaskScripts_ScheduleScript(),
                theXpdl2Package.getExpression(),
                null,
                "scheduleScript", //$NON-NLS-1$
                null,
                0,
                1,
                UserTaskScripts.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getUserTaskScripts_RescheduleScript(),
                theXpdl2Package.getExpression(),
                null,
                "rescheduleScript", //$NON-NLS-1$
                null,
                0,
                1,
                UserTaskScripts.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(validationControlEClass,
                ValidationControl.class,
                "ValidationControl", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getValidationControl_ValidationIssueOverrides(),
                this.getValidationIssueOverride(),
                null,
                "validationIssueOverrides", //$NON-NLS-1$
                null,
                0,
                -1,
                ValidationControl.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(validationIssueOverrideEClass,
                ValidationIssueOverride.class,
                "ValidationIssueOverride", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getValidationIssueOverride_ValidationIssueId(),
                theXMLTypePackage.getString(),
                "validationIssueId", //$NON-NLS-1$
                null,
                1,
                1,
                ValidationIssueOverride.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getValidationIssueOverride_OverrideType(),
                this.getValidationIssueOverrideType(),
                "overrideType", //$NON-NLS-1$
                null,
                1,
                1,
                ValidationIssueOverride.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(wsdlEventAssociationEClass,
                WsdlEventAssociation.class,
                "WsdlEventAssociation", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getWsdlEventAssociation_EventId(),
                theXMLTypePackage.getString(),
                "eventId", //$NON-NLS-1$
                null,
                1,
                1,
                WsdlEventAssociation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(workItemPriorityEClass,
                WorkItemPriority.class,
                "WorkItemPriority", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getWorkItemPriority_InitialPriority(),
                theXMLTypePackage.getString(),
                "initialPriority", //$NON-NLS-1$
                null,
                0,
                1,
                WorkItemPriority.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(wsdlGenerationEClass,
                WsdlGeneration.class,
                "WsdlGeneration", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getWsdlGeneration_SoapBindingStyle(),
                this.getSoapBindingStyle(),
                "soapBindingStyle", //$NON-NLS-1$
                null,
                1,
                1,
                WsdlGeneration.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(wsBindingEClass,
                WsBinding.class,
                "WsBinding", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getWsBinding_Name(),
                theXMLTypePackage.getString(),
                "name", //$NON-NLS-1$
                null,
                0,
                1,
                WsBinding.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getWsBinding_ExtendedProperties(),
                this.getXpdExtProperty(),
                null,
                "extendedProperties", //$NON-NLS-1$
                null,
                0,
                -1,
                WsBinding.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_COMPOSITE,
                IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(wsInboundEClass,
                WsInbound.class,
                "WsInbound", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getWsInbound_VirtualBinding(),
                this.getWsVirtualBinding(),
                null,
                "virtualBinding", //$NON-NLS-1$
                null,
                1,
                1,
                WsInbound.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getWsInbound_SoapHttpBinding(),
                this.getWsSoapHttpInboundBinding(),
                null,
                "soapHttpBinding", //$NON-NLS-1$
                null,
                0,
                -1,
                WsInbound.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getWsInbound_SoapJmsBinding(),
                this.getWsSoapJmsInboundBinding(),
                null,
                "soapJmsBinding", //$NON-NLS-1$
                null,
                0,
                -1,
                WsInbound.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        addEOperation(wsInboundEClass, this.getWsBinding(), "getAllBindings", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

        initEClass(wsOutboundEClass,
                WsOutbound.class,
                "WsOutbound", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getWsOutbound_VirtualBinding(),
                this.getWsVirtualBinding(),
                null,
                "virtualBinding", //$NON-NLS-1$
                null,
                0,
                1,
                WsOutbound.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getWsOutbound_SoapHttpBinding(),
                this.getWsSoapHttpOutboundBinding(),
                null,
                "soapHttpBinding", //$NON-NLS-1$
                null,
                0,
                1,
                WsOutbound.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getWsOutbound_SoapJmsBinding(),
                this.getWsSoapJmsOutboundBinding(),
                null,
                "soapJmsBinding", //$NON-NLS-1$
                null,
                0,
                1,
                WsOutbound.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        addEOperation(wsOutboundEClass, this.getWsBinding(), "getBinding", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

        op = addEOperation(wsOutboundEClass, null, "setBinding", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
        addEParameter(op, this.getWsBinding(), "inboundBinding", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

        initEClass(wsResourceEClass,
                WsResource.class,
                "WsResource", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getWsResource_Inbound(),
                this.getWsInbound(),
                null,
                "inbound", //$NON-NLS-1$
                null,
                0,
                1,
                WsResource.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getWsResource_Outbound(),
                this.getWsOutbound(),
                null,
                "outbound", //$NON-NLS-1$
                null,
                0,
                1,
                WsResource.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(wsSecurityPolicyEClass,
                WsSecurityPolicy.class,
                "WsSecurityPolicy", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getWsSecurityPolicy_GovernanceApplicationName(),
                theXMLTypePackage.getString(),
                "governanceApplicationName", //$NON-NLS-1$
                null,
                0,
                1,
                WsSecurityPolicy.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getWsSecurityPolicy_Type(),
                this.getSecurityPolicy(),
                "type", //$NON-NLS-1$
                null,
                0,
                1,
                WsSecurityPolicy.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(wsSoapBindingEClass,
                WsSoapBinding.class,
                "WsSoapBinding", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getWsSoapBinding_BindingStyle(),
                this.getSoapBindingStyle(),
                "bindingStyle", //$NON-NLS-1$
                "RpcLiteral", //$NON-NLS-1$
                0,
                1,
                WsSoapBinding.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getWsSoapBinding_SoapVersion(),
                theXMLTypePackage.getString(),
                "soapVersion", //$NON-NLS-1$
                "1.1", //$NON-NLS-1$
                0,
                1,
                WsSoapBinding.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getWsSoapBinding_SoapSecurity(),
                this.getWsSoapSecurity(),
                null,
                "soapSecurity", //$NON-NLS-1$
                null,
                0,
                1,
                WsSoapBinding.class,
                IS_TRANSIENT,
                IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                IS_DERIVED,
                IS_ORDERED);

        initEClass(wsSoapHttpInboundBindingEClass,
                WsSoapHttpInboundBinding.class,
                "WsSoapHttpInboundBinding", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getWsSoapHttpInboundBinding_InboundSecurity(),
                this.getWsSoapSecurity(),
                null,
                "inboundSecurity", //$NON-NLS-1$
                null,
                1,
                1,
                WsSoapHttpInboundBinding.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getWsSoapHttpInboundBinding_EndpointUrlPath(),
                theXMLTypePackage.getAnyURI(),
                "endpointUrlPath", //$NON-NLS-1$
                null,
                0,
                1,
                WsSoapHttpInboundBinding.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getWsSoapHttpInboundBinding_HttpConnectorInstanceName(),
                theXMLTypePackage.getString(),
                "httpConnectorInstanceName", //$NON-NLS-1$
                null,
                0,
                1,
                WsSoapHttpInboundBinding.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(wsSoapHttpOutboundBindingEClass,
                WsSoapHttpOutboundBinding.class,
                "WsSoapHttpOutboundBinding", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getWsSoapHttpOutboundBinding_OutboundSecurity(),
                this.getWsSoapSecurity(),
                null,
                "outboundSecurity", //$NON-NLS-1$
                null,
                1,
                1,
                WsSoapHttpOutboundBinding.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getWsSoapHttpOutboundBinding_HttpClientInstanceName(),
                theXMLTypePackage.getString(),
                "httpClientInstanceName", //$NON-NLS-1$
                null,
                0,
                1,
                WsSoapHttpOutboundBinding.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(wsSoapJmsInboundBindingEClass,
                WsSoapJmsInboundBinding.class,
                "WsSoapJmsInboundBinding", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getWsSoapJmsInboundBinding_OutboundConnectionFactory(),
                theXMLTypePackage.getString(),
                "outboundConnectionFactory", //$NON-NLS-1$
                null,
                0,
                1,
                WsSoapJmsInboundBinding.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getWsSoapJmsInboundBinding_InboundConnectionFactoryConfiguration(),
                theXMLTypePackage.getString(),
                "inboundConnectionFactoryConfiguration", //$NON-NLS-1$
                null,
                0,
                1,
                WsSoapJmsInboundBinding.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getWsSoapJmsInboundBinding_InboundDestination(),
                theXMLTypePackage.getString(),
                "inboundDestination", //$NON-NLS-1$
                null,
                0,
                1,
                WsSoapJmsInboundBinding.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getWsSoapJmsInboundBinding_InboundSecurity(),
                this.getWsSoapSecurity(),
                null,
                "inboundSecurity", //$NON-NLS-1$
                null,
                1,
                1,
                WsSoapJmsInboundBinding.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(wsSoapJmsOutboundBindingEClass,
                WsSoapJmsOutboundBinding.class,
                "WsSoapJmsOutboundBinding", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getWsSoapJmsOutboundBinding_OutboundConnectionFactory(),
                theXMLTypePackage.getString(),
                "outboundConnectionFactory", //$NON-NLS-1$
                null,
                0,
                1,
                WsSoapJmsOutboundBinding.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getWsSoapJmsOutboundBinding_InboundDestination(),
                theXMLTypePackage.getString(),
                "inboundDestination", //$NON-NLS-1$
                null,
                0,
                1,
                WsSoapJmsOutboundBinding.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getWsSoapJmsOutboundBinding_OutboundDestination(),
                theXMLTypePackage.getString(),
                "outboundDestination", //$NON-NLS-1$
                null,
                0,
                1,
                WsSoapJmsOutboundBinding.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getWsSoapJmsOutboundBinding_OutboundSecurity(),
                this.getWsSoapSecurity(),
                null,
                "outboundSecurity", //$NON-NLS-1$
                null,
                1,
                1,
                WsSoapJmsOutboundBinding.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getWsSoapJmsOutboundBinding_DeliveryMode(),
                this.getDeliveryMode(),
                "deliveryMode", //$NON-NLS-1$
                null,
                0,
                1,
                WsSoapJmsOutboundBinding.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getWsSoapJmsOutboundBinding_Priority(),
                theXMLTypePackage.getInt(),
                "priority", //$NON-NLS-1$
                null,
                0,
                1,
                WsSoapJmsOutboundBinding.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getWsSoapJmsOutboundBinding_MessageExpiration(),
                theXMLTypePackage.getInt(),
                "messageExpiration", //$NON-NLS-1$
                null,
                0,
                1,
                WsSoapJmsOutboundBinding.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getWsSoapJmsOutboundBinding_InvocationTimeout(),
                theXMLTypePackage.getInt(),
                "invocationTimeout", //$NON-NLS-1$
                null,
                0,
                1,
                WsSoapJmsOutboundBinding.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(wsSoapSecurityEClass,
                WsSoapSecurity.class,
                "WsSoapSecurity", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getWsSoapSecurity_SecurityPolicy(),
                this.getWsSecurityPolicy(),
                null,
                "securityPolicy", //$NON-NLS-1$
                null,
                0,
                -1,
                WsSoapSecurity.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(wsVirtualBindingEClass,
                WsVirtualBinding.class,
                "WsVirtualBinding", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        initEClass(xpdExtDataObjectAttributesEClass,
                XpdExtDataObjectAttributes.class,
                "XpdExtDataObjectAttributes", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getXpdExtDataObjectAttributes_Description(),
                ecorePackage.getEString(),
                "description", //$NON-NLS-1$
                null,
                0,
                1,
                XpdExtDataObjectAttributes.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getXpdExtDataObjectAttributes_ExternalReference(),
                ecorePackage.getEString(),
                "externalReference", //$NON-NLS-1$
                null,
                0,
                1,
                XpdExtDataObjectAttributes.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getXpdExtDataObjectAttributes_Properties(),
                this.getXpdExtProperty(),
                null,
                "properties", //$NON-NLS-1$
                null,
                0,
                -1,
                XpdExtDataObjectAttributes.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(xpdExtPropertyEClass,
                XpdExtProperty.class,
                "XpdExtProperty", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getXpdExtProperty_Name(),
                ecorePackage.getEString(),
                "Name", //$NON-NLS-1$
                null,
                1,
                1,
                XpdExtProperty.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getXpdExtProperty_Value(),
                ecorePackage.getEString(),
                "value", //$NON-NLS-1$
                null,
                0,
                1,
                XpdExtProperty.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(xpdExtAttributeEClass,
                XpdExtAttribute.class,
                "XpdExtAttribute", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getXpdExtAttribute_Mixed(),
                ecorePackage.getEFeatureMapEntry(),
                "mixed", //$NON-NLS-1$
                null,
                0,
                -1,
                XpdExtAttribute.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getXpdExtAttribute_Group(),
                ecorePackage.getEFeatureMapEntry(),
                "group", //$NON-NLS-1$
                null,
                0,
                -1,
                XpdExtAttribute.class,
                IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getXpdExtAttribute_Any(),
                ecorePackage.getEFeatureMapEntry(),
                "any", //$NON-NLS-1$
                null,
                0,
                -1,
                XpdExtAttribute.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getXpdExtAttribute_Name(),
                theXMLTypePackage.getNMTOKEN(),
                "name", //$NON-NLS-1$
                null,
                1,
                1,
                XpdExtAttribute.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getXpdExtAttribute_Value(),
                theXMLTypePackage.getString(),
                "value", //$NON-NLS-1$
                null,
                0,
                1,
                XpdExtAttribute.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(xpdExtAttributesEClass,
                XpdExtAttributes.class,
                "XpdExtAttributes", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getXpdExtAttributes_Attributes(),
                this.getXpdExtAttribute(),
                null,
                "attributes", //$NON-NLS-1$
                null,
                0,
                -1,
                XpdExtAttributes.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(updateCaseOperationTypeEClass,
                UpdateCaseOperationType.class,
                "UpdateCaseOperationType", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getUpdateCaseOperationType_FromFieldPath(),
                theXMLTypePackage.getString(),
                "fromFieldPath", //$NON-NLS-1$
                null,
                1,
                1,
                UpdateCaseOperationType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(addLinkAssociationsTypeEClass,
                AddLinkAssociationsType.class,
                "AddLinkAssociationsType", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAddLinkAssociationsType_AddCaseRefField(),
                theXMLTypePackage.getString(),
                "addCaseRefField", //$NON-NLS-1$
                null,
                1,
                1,
                AddLinkAssociationsType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getAddLinkAssociationsType_AssociationName(),
                theXMLTypePackage.getString(),
                "associationName", //$NON-NLS-1$
                null,
                1,
                1,
                AddLinkAssociationsType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(removeLinkAssociationsTypeEClass,
                RemoveLinkAssociationsType.class,
                "RemoveLinkAssociationsType", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getRemoveLinkAssociationsType_AssociationName(),
                theXMLTypePackage.getString(),
                "associationName", //$NON-NLS-1$
                null,
                1,
                1,
                RemoveLinkAssociationsType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getRemoveLinkAssociationsType_RemoveCaseRefField(),
                theXMLTypePackage.getString(),
                "removeCaseRefField", //$NON-NLS-1$
                null,
                1,
                1,
                RemoveLinkAssociationsType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(caseReferenceOperationsTypeEClass,
                CaseReferenceOperationsType.class,
                "CaseReferenceOperationsType", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getCaseReferenceOperationsType_CaseRefField(),
                theXMLTypePackage.getString(),
                "caseRefField", //$NON-NLS-1$
                null,
                1,
                1,
                CaseReferenceOperationsType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getCaseReferenceOperationsType_Update(),
                this.getUpdateCaseOperationType(),
                null,
                "update", //$NON-NLS-1$
                null,
                0,
                1,
                CaseReferenceOperationsType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getCaseReferenceOperationsType_Delete(),
                this.getDeleteCaseReferenceOperationType(),
                null,
                "delete", //$NON-NLS-1$
                null,
                0,
                1,
                CaseReferenceOperationsType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getCaseReferenceOperationsType_AddLinkAssociations(),
                this.getAddLinkAssociationsType(),
                null,
                "addLinkAssociations", //$NON-NLS-1$
                null,
                0,
                1,
                CaseReferenceOperationsType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getCaseReferenceOperationsType_RemoveLinkAssociations(),
                this.getRemoveLinkAssociationsType(),
                null,
                "removeLinkAssociations", //$NON-NLS-1$
                null,
                0,
                1,
                CaseReferenceOperationsType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(globalDataOperationEClass,
                GlobalDataOperation.class,
                "GlobalDataOperation", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getGlobalDataOperation_CaseAccessOperations(),
                this.getCaseAccessOperationsType(),
                null,
                "caseAccessOperations", //$NON-NLS-1$
                null,
                0,
                1,
                GlobalDataOperation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getGlobalDataOperation_CaseReferenceOperations(),
                this.getCaseReferenceOperationsType(),
                null,
                "caseReferenceOperations", //$NON-NLS-1$
                null,
                0,
                1,
                GlobalDataOperation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(deleteByCaseIdentifierTypeEClass,
                DeleteByCaseIdentifierType.class,
                "DeleteByCaseIdentifierType", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDeleteByCaseIdentifierType_FieldPath(),
                theXMLTypePackage.getString(),
                "fieldPath", //$NON-NLS-1$
                null,
                1,
                1,
                DeleteByCaseIdentifierType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDeleteByCaseIdentifierType_IdentifierName(),
                theXMLTypePackage.getString(),
                "identifierName", //$NON-NLS-1$
                null,
                1,
                1,
                DeleteByCaseIdentifierType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(compositeIdentifierTypeEClass,
                CompositeIdentifierType.class,
                "CompositeIdentifierType", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getCompositeIdentifierType_FieldPath(),
                theXMLTypePackage.getString(),
                "fieldPath", //$NON-NLS-1$
                null,
                1,
                1,
                CompositeIdentifierType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getCompositeIdentifierType_Identifiername(),
                theXMLTypePackage.getString(),
                "identifiername", //$NON-NLS-1$
                null,
                1,
                1,
                CompositeIdentifierType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(deleteCaseReferenceOperationTypeEClass,
                DeleteCaseReferenceOperationType.class,
                "DeleteCaseReferenceOperationType", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        initEClass(deleteByCompositeIdentifiersTypeEClass,
                DeleteByCompositeIdentifiersType.class,
                "DeleteByCompositeIdentifiersType", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDeleteByCompositeIdentifiersType_Group(),
                ecorePackage.getEFeatureMapEntry(),
                "group", //$NON-NLS-1$
                null,
                0,
                -1,
                DeleteByCompositeIdentifiersType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDeleteByCompositeIdentifiersType_CompositeIdentifier(),
                this.getCompositeIdentifierType(),
                null,
                "compositeIdentifier", //$NON-NLS-1$
                null,
                1,
                -1,
                DeleteByCompositeIdentifiersType.class,
                IS_TRANSIENT,
                IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                IS_DERIVED,
                IS_ORDERED);

        initEClass(createCaseOperationTypeEClass,
                CreateCaseOperationType.class,
                "CreateCaseOperationType", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getCreateCaseOperationType_FromFieldPath(),
                theXMLTypePackage.getString(),
                "fromFieldPath", //$NON-NLS-1$
                null,
                1,
                1,
                CreateCaseOperationType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getCreateCaseOperationType_ToCaseRefField(),
                theXMLTypePackage.getString(),
                "toCaseRefField", //$NON-NLS-1$
                null,
                1,
                1,
                CreateCaseOperationType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(caseAccessOperationsTypeEClass,
                CaseAccessOperationsType.class,
                "CaseAccessOperationsType", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getCaseAccessOperationsType_CaseClassExternalReference(),
                theXpdl2Package.getExternalReference(),
                null,
                "caseClassExternalReference", //$NON-NLS-1$
                null,
                1,
                1,
                CaseAccessOperationsType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getCaseAccessOperationsType_Create(),
                this.getCreateCaseOperationType(),
                null,
                "create", //$NON-NLS-1$
                null,
                0,
                1,
                CaseAccessOperationsType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getCaseAccessOperationsType_DeleteByCaseIdentifier(),
                this.getDeleteByCaseIdentifierType(),
                null,
                "deleteByCaseIdentifier", //$NON-NLS-1$
                null,
                0,
                1,
                CaseAccessOperationsType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getCaseAccessOperationsType_DeleteByCompositeIdentifiers(),
                this.getDeleteByCompositeIdentifiersType(),
                null,
                "deleteByCompositeIdentifiers", //$NON-NLS-1$
                null,
                0,
                1,
                CaseAccessOperationsType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(dataWorkItemAttributeMappingEClass,
                DataWorkItemAttributeMapping.class,
                "DataWorkItemAttributeMapping", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDataWorkItemAttributeMapping_Attribute(),
                theXMLTypePackage.getString(),
                "attribute", //$NON-NLS-1$
                null,
                1,
                1,
                DataWorkItemAttributeMapping.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDataWorkItemAttributeMapping_ProcessData(),
                theXMLTypePackage.getString(),
                "processData", //$NON-NLS-1$
                null,
                1,
                1,
                DataWorkItemAttributeMapping.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(processDataWorkItemAttributeMappingsEClass,
                ProcessDataWorkItemAttributeMappings.class,
                "ProcessDataWorkItemAttributeMappings", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getProcessDataWorkItemAttributeMappings_DataWorkItemAttributeMapping(),
                this.getDataWorkItemAttributeMapping(),
                null,
                "dataWorkItemAttributeMapping", //$NON-NLS-1$
                null,
                0,
                -1,
                ProcessDataWorkItemAttributeMappings.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(bpmRuntimeConfigurationEClass,
                BpmRuntimeConfiguration.class,
                "BpmRuntimeConfiguration", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getBpmRuntimeConfiguration_IncomingRequestTimeout(),
                theXMLTypePackage.getIntObject(),
                "incomingRequestTimeout", //$NON-NLS-1$
                null,
                0,
                1,
                BpmRuntimeConfiguration.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(enablementTypeEClass,
                EnablementType.class,
                "EnablementType", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getEnablementType_InitializerActivities(),
                this.getInitializerActivitiesType(),
                null,
                "initializerActivities", //$NON-NLS-1$
                null,
                0,
                1,
                EnablementType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getEnablementType_PreconditionExpression(),
                theXpdl2Package.getExpression(),
                null,
                "preconditionExpression", //$NON-NLS-1$
                null,
                0,
                1,
                EnablementType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(initializerActivitiesTypeEClass,
                InitializerActivitiesType.class,
                "InitializerActivitiesType", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getInitializerActivitiesType_ActivityRef(),
                this.getActivityRef(),
                null,
                "activityRef", //$NON-NLS-1$
                null,
                0,
                -1,
                InitializerActivitiesType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(adHocTaskConfigurationTypeEClass,
                AdHocTaskConfigurationType.class,
                "AdHocTaskConfigurationType", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getAdHocTaskConfigurationType_Enablement(),
                this.getEnablementType(),
                null,
                "enablement", //$NON-NLS-1$
                null,
                1,
                1,
                AdHocTaskConfigurationType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getAdHocTaskConfigurationType_AdHocExecutionType(),
                this.getAdHocExecutionTypeType(),
                "adHocExecutionType", //$NON-NLS-1$
                null,
                1,
                1,
                AdHocTaskConfigurationType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getAdHocTaskConfigurationType_SuspendMainFlow(),
                theXMLTypePackage.getBoolean(),
                "suspendMainFlow", //$NON-NLS-1$
                "false", //$NON-NLS-1$
                1,
                1,
                AdHocTaskConfigurationType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getAdHocTaskConfigurationType_AllowMultipleInvocations(),
                theXMLTypePackage.getBoolean(),
                "allowMultipleInvocations", //$NON-NLS-1$
                "false", //$NON-NLS-1$
                0,
                1,
                AdHocTaskConfigurationType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getAdHocTaskConfigurationType_RequiredAccessPrivileges(),
                this.getRequiredAccessPrivileges(),
                null,
                "requiredAccessPrivileges", //$NON-NLS-1$
                null,
                1,
                1,
                AdHocTaskConfigurationType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(requiredAccessPrivilegesEClass,
                RequiredAccessPrivileges.class,
                "RequiredAccessPrivileges", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getRequiredAccessPrivileges_PrivilegeReference(),
                theXpdl2Package.getExternalReference(),
                null,
                "privilegeReference", //$NON-NLS-1$
                null,
                0,
                -1,
                RequiredAccessPrivileges.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(visibleForCaseStatesEClass,
                VisibleForCaseStates.class,
                "VisibleForCaseStates", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getVisibleForCaseStates_VisibleForUnsetCaseState(),
                theXMLTypePackage.getBoolean(),
                "visibleForUnsetCaseState", //$NON-NLS-1$
                null,
                0,
                1,
                VisibleForCaseStates.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getVisibleForCaseStates_CaseState(),
                theXpdl2Package.getExternalReference(),
                null,
                "caseState", //$NON-NLS-1$
                null,
                0,
                -1,
                VisibleForCaseStates.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(caseServiceEClass,
                CaseService.class,
                "CaseService", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getCaseService_CaseClassType(),
                theXpdl2Package.getExternalReference(),
                null,
                "caseClassType", //$NON-NLS-1$
                null,
                0,
                1,
                CaseService.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                !IS_ORDERED);
        initEReference(getCaseService_VisibleForCaseStates(),
                this.getVisibleForCaseStates(),
                null,
                "visibleForCaseStates", //$NON-NLS-1$
                null,
                0,
                1,
                CaseService.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(documentOperationEClass,
                DocumentOperation.class,
                "DocumentOperation", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getDocumentOperation_CaseDocRefOperation(),
                this.getCaseDocRefOperations(),
                null,
                "caseDocRefOperation", //$NON-NLS-1$
                null,
                0,
                1,
                DocumentOperation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentOperation_CaseDocFindOperations(),
                this.getCaseDocFindOperations(),
                null,
                "caseDocFindOperations", //$NON-NLS-1$
                null,
                0,
                1,
                DocumentOperation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentOperation_LinkSystemDocumentOperation(),
                this.getLinkSystemDocumentOperation(),
                null,
                "linkSystemDocumentOperation", //$NON-NLS-1$
                null,
                0,
                1,
                DocumentOperation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(caseDocRefOperationsEClass,
                CaseDocRefOperations.class,
                "CaseDocRefOperations", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getCaseDocRefOperations_MoveCaseDocOperation(),
                this.getMoveCaseDocOperation(),
                null,
                "moveCaseDocOperation", //$NON-NLS-1$
                null,
                0,
                1,
                CaseDocRefOperations.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getCaseDocRefOperations_UnlinkCaseDocOperation(),
                this.getUnlinkCaseDocOperation(),
                null,
                "unlinkCaseDocOperation", //$NON-NLS-1$
                null,
                0,
                1,
                CaseDocRefOperations.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getCaseDocRefOperations_LinkCaseDocOperation(),
                this.getLinkCaseDocOperation(),
                null,
                "linkCaseDocOperation", //$NON-NLS-1$
                null,
                0,
                1,
                CaseDocRefOperations.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getCaseDocRefOperations_DeleteCaseDocOperation(),
                this.getDeleteCaseDocOperation(),
                null,
                "deleteCaseDocOperation", //$NON-NLS-1$
                null,
                0,
                1,
                CaseDocRefOperations.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getCaseDocRefOperations_CaseDocumentRefField(),
                theXMLTypePackage.getString(),
                "caseDocumentRefField", //$NON-NLS-1$
                null,
                1,
                1,
                CaseDocRefOperations.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(caseDocFindOperationsEClass,
                CaseDocFindOperations.class,
                "CaseDocFindOperations", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getCaseDocFindOperations_FindByFileNameOperation(),
                this.getFindByFileNameOperation(),
                null,
                "findByFileNameOperation", //$NON-NLS-1$
                null,
                0,
                1,
                CaseDocFindOperations.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getCaseDocFindOperations_FindByQueryOperation(),
                this.getFindByQueryOperation(),
                null,
                "findByQueryOperation", //$NON-NLS-1$
                null,
                0,
                1,
                CaseDocFindOperations.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getCaseDocFindOperations_ReturnCaseDocRefsField(),
                theXMLTypePackage.getString(),
                "returnCaseDocRefsField", //$NON-NLS-1$
                null,
                1,
                1,
                CaseDocFindOperations.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getCaseDocFindOperations_CaseRefField(),
                theXMLTypePackage.getString(),
                "caseRefField", //$NON-NLS-1$
                null,
                1,
                1,
                CaseDocFindOperations.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(moveCaseDocOperationEClass,
                MoveCaseDocOperation.class,
                "MoveCaseDocOperation", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getMoveCaseDocOperation_SourceCaseRefField(),
                theXMLTypePackage.getString(),
                "sourceCaseRefField", //$NON-NLS-1$
                null,
                1,
                1,
                MoveCaseDocOperation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getMoveCaseDocOperation_TargetCaseRefField(),
                theXMLTypePackage.getString(),
                "targetCaseRefField", //$NON-NLS-1$
                null,
                1,
                1,
                MoveCaseDocOperation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(unlinkCaseDocOperationEClass,
                UnlinkCaseDocOperation.class,
                "UnlinkCaseDocOperation", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getUnlinkCaseDocOperation_SourceCaseRefField(),
                theXMLTypePackage.getString(),
                "sourceCaseRefField", //$NON-NLS-1$
                null,
                1,
                1,
                UnlinkCaseDocOperation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(linkCaseDocOperationEClass,
                LinkCaseDocOperation.class,
                "LinkCaseDocOperation", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getLinkCaseDocOperation_TargetCaseRefField(),
                theXMLTypePackage.getString(),
                "targetCaseRefField", //$NON-NLS-1$
                null,
                1,
                1,
                LinkCaseDocOperation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(linkSystemDocumentOperationEClass,
                LinkSystemDocumentOperation.class,
                "LinkSystemDocumentOperation", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getLinkSystemDocumentOperation_DocumentId(),
                theXMLTypePackage.getString(),
                "documentId", //$NON-NLS-1$
                null,
                1,
                1,
                LinkSystemDocumentOperation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getLinkSystemDocumentOperation_ReturnCaseDocRefField(),
                theXMLTypePackage.getString(),
                "returnCaseDocRefField", //$NON-NLS-1$
                null,
                1,
                1,
                LinkSystemDocumentOperation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getLinkSystemDocumentOperation_CaseRefField(),
                theXMLTypePackage.getString(),
                "caseRefField", //$NON-NLS-1$
                null,
                1,
                1,
                LinkSystemDocumentOperation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(deleteCaseDocOperationEClass,
                DeleteCaseDocOperation.class,
                "DeleteCaseDocOperation", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        initEClass(findByFileNameOperationEClass,
                FindByFileNameOperation.class,
                "FindByFileNameOperation", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getFindByFileNameOperation_FileNameField(),
                theXMLTypePackage.getString(),
                "fileNameField", //$NON-NLS-1$
                null,
                1,
                1,
                FindByFileNameOperation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(findByQueryOperationEClass,
                FindByQueryOperation.class,
                "FindByQueryOperation", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getFindByQueryOperation_CaseDocumentQueryExpression(),
                this.getCaseDocumentQueryExpression(),
                null,
                "caseDocumentQueryExpression", //$NON-NLS-1$
                null,
                1,
                -1,
                FindByQueryOperation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(caseDocumentQueryExpressionEClass,
                CaseDocumentQueryExpression.class,
                "CaseDocumentQueryExpression", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getCaseDocumentQueryExpression_QueryExpressionJoinType(),
                this.getQueryExpressionJoinType(),
                "queryExpressionJoinType", //$NON-NLS-1$
                null,
                0,
                1,
                CaseDocumentQueryExpression.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getCaseDocumentQueryExpression_OpenBracketCount(),
                theXMLTypePackage.getInt(),
                "openBracketCount", //$NON-NLS-1$
                null,
                0,
                1,
                CaseDocumentQueryExpression.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getCaseDocumentQueryExpression_CmisProperty(),
                theXMLTypePackage.getString(),
                "cmisProperty", //$NON-NLS-1$
                null,
                0,
                1,
                CaseDocumentQueryExpression.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getCaseDocumentQueryExpression_Operator(),
                this.getCMISQueryOperator(),
                "operator", //$NON-NLS-1$
                null,
                1,
                1,
                CaseDocumentQueryExpression.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getCaseDocumentQueryExpression_ProcessDataField(),
                theXMLTypePackage.getString(),
                "processDataField", //$NON-NLS-1$
                null,
                0,
                1,
                CaseDocumentQueryExpression.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getCaseDocumentQueryExpression_CloseBracketCount(),
                theXMLTypePackage.getInt(),
                "closeBracketCount", //$NON-NLS-1$
                null,
                0,
                1,
                CaseDocumentQueryExpression.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getCaseDocumentQueryExpression_CmisDocumentPropertySelected(),
                theXMLTypePackage.getBoolean(),
                "cmisDocumentPropertySelected", //$NON-NLS-1$
                null,
                0,
                1,
                CaseDocumentQueryExpression.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(serviceProcessConfigurationEClass,
                ServiceProcessConfiguration.class,
                "ServiceProcessConfiguration", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getServiceProcessConfiguration_DeployToProcessRuntime(),
                theXMLTypePackage.getBoolean(),
                "deployToProcessRuntime", //$NON-NLS-1$
                null,
                1,
                1,
                ServiceProcessConfiguration.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getServiceProcessConfiguration_DeployToPageflowRuntime(),
                theXMLTypePackage.getBoolean(),
                "deployToPageflowRuntime", //$NON-NLS-1$
                null,
                1,
                1,
                ServiceProcessConfiguration.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(scriptDataMapperEClass,
                ScriptDataMapper.class,
                "ScriptDataMapper", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getScriptDataMapper_MapperContext(),
                theXMLTypePackage.getString(),
                "mapperContext", //$NON-NLS-1$
                null,
                1,
                1,
                ScriptDataMapper.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getScriptDataMapper_MappingDirection(),
                theXpdl2Package.getDirectionType(),
                "mappingDirection", //$NON-NLS-1$
                null,
                1,
                1,
                ScriptDataMapper.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getScriptDataMapper_DataMappings(),
                theXpdl2Package.getDataMapping(),
                null,
                "dataMappings", //$NON-NLS-1$
                null,
                0,
                -1,
                ScriptDataMapper.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getScriptDataMapper_UnmappedScripts(),
                this.getScriptInformation(),
                null,
                "unmappedScripts", //$NON-NLS-1$
                null,
                0,
                -1,
                ScriptDataMapper.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getScriptDataMapper_ArrayInflationType(),
                this.getDataMapperArrayInflation(),
                null,
                "arrayInflationType", //$NON-NLS-1$
                null,
                0,
                -1,
                ScriptDataMapper.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(dataMapperArrayInflationEClass,
                DataMapperArrayInflation.class,
                "DataMapperArrayInflation", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDataMapperArrayInflation_Path(),
                theXMLTypePackage.getString(),
                "path", //$NON-NLS-1$
                null,
                1,
                1,
                DataMapperArrayInflation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDataMapperArrayInflation_MappingType(),
                this.getDataMapperArrayInflationType(),
                "mappingType", //$NON-NLS-1$
                null,
                1,
                1,
                DataMapperArrayInflation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getDataMapperArrayInflation_ContributorId(),
                theXMLTypePackage.getString(),
                "contributorId", //$NON-NLS-1$
                null,
                1,
                1,
                DataMapperArrayInflation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(likeMappingExclusionEClass,
                LikeMappingExclusion.class,
                "LikeMappingExclusion", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getLikeMappingExclusion_Path(),
                theXMLTypePackage.getString(),
                "path", //$NON-NLS-1$
                null,
                1,
                1,
                LikeMappingExclusion.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(likeMappingExclusionsEClass,
                LikeMappingExclusions.class,
                "LikeMappingExclusions", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getLikeMappingExclusions_Exclusions(),
                this.getLikeMappingExclusion(),
                null,
                "exclusions", //$NON-NLS-1$
                null,
                0,
                -1,
                LikeMappingExclusions.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(restServiceOperationEClass,
                RestServiceOperation.class,
                "RestServiceOperation", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getRestServiceOperation_Location(),
                ecorePackage.getEString(),
                "location", //$NON-NLS-1$
                null,
                0,
                1,
                RestServiceOperation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getRestServiceOperation_MethodId(),
                ecorePackage.getEString(),
                "methodId", //$NON-NLS-1$
                null,
                0,
                1,
                RestServiceOperation.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        // Initialize enums and add enum literals
        initEEnum(allocationStrategyTypeEEnum, AllocationStrategyType.class, "AllocationStrategyType"); //$NON-NLS-1$
        addEEnumLiteral(allocationStrategyTypeEEnum, AllocationStrategyType.SYSTEM_DETERMINED);
        addEEnumLiteral(allocationStrategyTypeEEnum, AllocationStrategyType.RANDOM);
        addEEnumLiteral(allocationStrategyTypeEEnum, AllocationStrategyType.ROUND_ROBIN);

        initEEnum(allocationTypeEEnum, AllocationType.class, "AllocationType"); //$NON-NLS-1$
        addEEnumLiteral(allocationTypeEEnum, AllocationType.OFFER_ALL);
        addEEnumLiteral(allocationTypeEEnum, AllocationType.OFFER_ONE);
        addEEnumLiteral(allocationTypeEEnum, AllocationType.ALLOCATE_ONE);
        addEEnumLiteral(allocationTypeEEnum, AllocationType.ALLOCATE_OFFER_SET_MEMBER);

        initEEnum(auditEventTypeEEnum, AuditEventType.class, "AuditEventType"); //$NON-NLS-1$
        addEEnumLiteral(auditEventTypeEEnum, AuditEventType.INITIATED_LITERAL);
        addEEnumLiteral(auditEventTypeEEnum, AuditEventType.COMPLETED_LITERAL);
        addEEnumLiteral(auditEventTypeEEnum, AuditEventType.DEADLINE_EXPIRED_LITERAL);
        addEEnumLiteral(auditEventTypeEEnum, AuditEventType.CANCELLED_LITERAL);

        initEEnum(correlationModeEEnum, CorrelationMode.class, "CorrelationMode"); //$NON-NLS-1$
        addEEnumLiteral(correlationModeEEnum, CorrelationMode.INITIALIZE);
        addEEnumLiteral(correlationModeEEnum, CorrelationMode.CORRELATE);
        addEEnumLiteral(correlationModeEEnum, CorrelationMode.JOIN);

        initEEnum(errorThrowerTypeEEnum, ErrorThrowerType.class, "ErrorThrowerType"); //$NON-NLS-1$
        addEEnumLiteral(errorThrowerTypeEEnum, ErrorThrowerType.PROCESS_ACTIVITY);
        addEEnumLiteral(errorThrowerTypeEEnum, ErrorThrowerType.INTERFACE_EVENT);
        addEEnumLiteral(errorThrowerTypeEEnum, ErrorThrowerType.OTHER);

        initEEnum(eventHandlerFlowStrategyEEnum, EventHandlerFlowStrategy.class, "EventHandlerFlowStrategy"); //$NON-NLS-1$
        addEEnumLiteral(eventHandlerFlowStrategyEEnum, EventHandlerFlowStrategy.SERIALIZE_CONCURRENT);
        addEEnumLiteral(eventHandlerFlowStrategyEEnum, EventHandlerFlowStrategy.ALLOW_CONCURRENT);

        initEEnum(flowRoutingStyleEEnum, FlowRoutingStyle.class, "FlowRoutingStyle"); //$NON-NLS-1$
        addEEnumLiteral(flowRoutingStyleEEnum, FlowRoutingStyle.UNCENTERED_ON_TASKS);
        addEEnumLiteral(flowRoutingStyleEEnum, FlowRoutingStyle.SINGLE_ENTRY_EXIT);
        addEEnumLiteral(flowRoutingStyleEEnum, FlowRoutingStyle.MULTI_ENTRY_EXIT);

        initEEnum(formImplementationTypeEEnum, FormImplementationType.class, "FormImplementationType"); //$NON-NLS-1$
        addEEnumLiteral(formImplementationTypeEEnum, FormImplementationType.USER_DEFINED);
        addEEnumLiteral(formImplementationTypeEEnum, FormImplementationType.FORM);
        addEEnumLiteral(formImplementationTypeEEnum, FormImplementationType.PAGEFLOW);

        initEEnum(maxRetryActionTypeEEnum, MaxRetryActionType.class, "MaxRetryActionType"); //$NON-NLS-1$
        addEEnumLiteral(maxRetryActionTypeEEnum, MaxRetryActionType.HALT);
        addEEnumLiteral(maxRetryActionTypeEEnum, MaxRetryActionType.ERROR);

        initEEnum(rescheduleDurationTypeEEnum, RescheduleDurationType.class, "RescheduleDurationType"); //$NON-NLS-1$
        addEEnumLiteral(rescheduleDurationTypeEEnum, RescheduleDurationType.RESCHEDULE_TIME);
        addEEnumLiteral(rescheduleDurationTypeEEnum, RescheduleDurationType.EXISTING_TIMEOUT);

        initEEnum(rescheduleTimerSelectionTypeEEnum,
                RescheduleTimerSelectionType.class,
                "RescheduleTimerSelectionType"); //$NON-NLS-1$
        addEEnumLiteral(rescheduleTimerSelectionTypeEEnum, RescheduleTimerSelectionType.ALL);
        addEEnumLiteral(rescheduleTimerSelectionTypeEEnum, RescheduleTimerSelectionType.DEADLINE);
        addEEnumLiteral(rescheduleTimerSelectionTypeEEnum, RescheduleTimerSelectionType.SELECTED);

        initEEnum(securityPolicyEEnum, SecurityPolicy.class, "SecurityPolicy"); //$NON-NLS-1$
        addEEnumLiteral(securityPolicyEEnum, SecurityPolicy.USERNAME_TOKEN);
        addEEnumLiteral(securityPolicyEEnum, SecurityPolicy.X509_TOKEN);
        addEEnumLiteral(securityPolicyEEnum, SecurityPolicy.SAML_TOKEN);
        addEEnumLiteral(securityPolicyEEnum, SecurityPolicy.CUSTOM);
        addEEnumLiteral(securityPolicyEEnum, SecurityPolicy.BASIC);

        initEEnum(soapBindingStyleEEnum, SoapBindingStyle.class, "SoapBindingStyle"); //$NON-NLS-1$
        addEEnumLiteral(soapBindingStyleEEnum, SoapBindingStyle.RPC_LITERAL);
        addEEnumLiteral(soapBindingStyleEEnum, SoapBindingStyle.DOCUMENT_LITERAL);

        initEEnum(fieldFormatEEnum, FieldFormat.class, "FieldFormat"); //$NON-NLS-1$
        addEEnumLiteral(fieldFormatEEnum, FieldFormat.URI);
        addEEnumLiteral(fieldFormatEEnum, FieldFormat.EMAIL);

        initEEnum(subProcessStartStrategyEEnum, SubProcessStartStrategy.class, "SubProcessStartStrategy"); //$NON-NLS-1$
        addEEnumLiteral(subProcessStartStrategyEEnum, SubProcessStartStrategy.START_IMMEDIATELY);
        addEEnumLiteral(subProcessStartStrategyEEnum, SubProcessStartStrategy.SCHEDULE_START);

        initEEnum(systemErrorActionTypeEEnum, SystemErrorActionType.class, "SystemErrorActionType"); //$NON-NLS-1$
        addEEnumLiteral(systemErrorActionTypeEEnum, SystemErrorActionType.HALT);
        addEEnumLiteral(systemErrorActionTypeEEnum, SystemErrorActionType.ERROR);

        initEEnum(validationIssueOverrideTypeEEnum, ValidationIssueOverrideType.class, "ValidationIssueOverrideType"); //$NON-NLS-1$
        addEEnumLiteral(validationIssueOverrideTypeEEnum, ValidationIssueOverrideType.SUPPRESS_UNTIL_NEXT_FLOW_CHANGE);
        addEEnumLiteral(validationIssueOverrideTypeEEnum,
                ValidationIssueOverrideType.SUPPRESS_UNTIL_MANUAL_REACTIVATION);

        initEEnum(visibilityEEnum, Visibility.class, "Visibility"); //$NON-NLS-1$
        addEEnumLiteral(visibilityEEnum, Visibility.PRIVATE);
        addEEnumLiteral(visibilityEEnum, Visibility.PUBLIC);

        initEEnum(deliveryModeEEnum, DeliveryMode.class, "DeliveryMode"); //$NON-NLS-1$
        addEEnumLiteral(deliveryModeEEnum, DeliveryMode.PERSISTENT);
        addEEnumLiteral(deliveryModeEEnum, DeliveryMode.NON_PERSISTENT);

        initEEnum(xpdModelTypeEEnum, XpdModelType.class, "XpdModelType"); //$NON-NLS-1$
        addEEnumLiteral(xpdModelTypeEEnum, XpdModelType.PAGE_FLOW);
        addEEnumLiteral(xpdModelTypeEEnum, XpdModelType.TASK_LIBRARY);
        addEEnumLiteral(xpdModelTypeEEnum, XpdModelType.DECISION_FLOW);
        addEEnumLiteral(xpdModelTypeEEnum, XpdModelType.SERVICE_PROCESS);

        initEEnum(adHocExecutionTypeTypeEEnum, AdHocExecutionTypeType.class, "AdHocExecutionTypeType"); //$NON-NLS-1$
        addEEnumLiteral(adHocExecutionTypeTypeEEnum, AdHocExecutionTypeType.AUTOMATIC);
        addEEnumLiteral(adHocExecutionTypeTypeEEnum, AdHocExecutionTypeType.MANUAL);

        initEEnum(queryExpressionJoinTypeEEnum, QueryExpressionJoinType.class, "QueryExpressionJoinType"); //$NON-NLS-1$
        addEEnumLiteral(queryExpressionJoinTypeEEnum, QueryExpressionJoinType.AND);
        addEEnumLiteral(queryExpressionJoinTypeEEnum, QueryExpressionJoinType.OR);

        initEEnum(cmisQueryOperatorEEnum, CMISQueryOperator.class, "CMISQueryOperator"); //$NON-NLS-1$
        addEEnumLiteral(cmisQueryOperatorEEnum, CMISQueryOperator.EQUAL);
        addEEnumLiteral(cmisQueryOperatorEEnum, CMISQueryOperator.NOT_EQUAL);
        addEEnumLiteral(cmisQueryOperatorEEnum, CMISQueryOperator.GREATER_THAN);
        addEEnumLiteral(cmisQueryOperatorEEnum, CMISQueryOperator.GREATER_THAN_EQUAL);
        addEEnumLiteral(cmisQueryOperatorEEnum, CMISQueryOperator.LESS_THAN);
        addEEnumLiteral(cmisQueryOperatorEEnum, CMISQueryOperator.LESS_THAN_EQUAL);
        addEEnumLiteral(cmisQueryOperatorEEnum, CMISQueryOperator.IN);
        addEEnumLiteral(cmisQueryOperatorEEnum, CMISQueryOperator.NOT_IN);
        addEEnumLiteral(cmisQueryOperatorEEnum, CMISQueryOperator.CONTAINS);
        addEEnumLiteral(cmisQueryOperatorEEnum, CMISQueryOperator.NOT_CONTAINS);
        addEEnumLiteral(cmisQueryOperatorEEnum, CMISQueryOperator.LIKE);
        addEEnumLiteral(cmisQueryOperatorEEnum, CMISQueryOperator.NOT_LIKE);
        addEEnumLiteral(cmisQueryOperatorEEnum, CMISQueryOperator.NULL);
        addEEnumLiteral(cmisQueryOperatorEEnum, CMISQueryOperator.NOT_NULL);

        initEEnum(asyncExecutionModeEEnum, AsyncExecutionMode.class, "AsyncExecutionMode"); //$NON-NLS-1$
        addEEnumLiteral(asyncExecutionModeEEnum, AsyncExecutionMode.ATTACHED);
        addEEnumLiteral(asyncExecutionModeEEnum, AsyncExecutionMode.DETACHED);

        initEEnum(signalTypeEEnum, SignalType.class, "SignalType"); //$NON-NLS-1$
        addEEnumLiteral(signalTypeEEnum, SignalType.LOCAL);
        addEEnumLiteral(signalTypeEEnum, SignalType.GLOBAL);
        addEEnumLiteral(signalTypeEEnum, SignalType.CASE_DATA);

        initEEnum(xpdInterfaceTypeEEnum, XpdInterfaceType.class, "XpdInterfaceType"); //$NON-NLS-1$
        addEEnumLiteral(xpdInterfaceTypeEEnum, XpdInterfaceType.PROCESS_INTERFACE);
        addEEnumLiteral(xpdInterfaceTypeEEnum, XpdInterfaceType.SERVICE_PROCESS_INTERFACE);

        initEEnum(dataMapperArrayInflationTypeEEnum,
                DataMapperArrayInflationType.class,
                "DataMapperArrayInflationType"); //$NON-NLS-1$
        addEEnumLiteral(dataMapperArrayInflationTypeEEnum, DataMapperArrayInflationType.APPEND_LIST);
        addEEnumLiteral(dataMapperArrayInflationTypeEEnum, DataMapperArrayInflationType.MERGE_LIST);

        initEEnum(businessServicePublishTypeEEnum, BusinessServicePublishType.class, "BusinessServicePublishType"); //$NON-NLS-1$
        addEEnumLiteral(businessServicePublishTypeEEnum, BusinessServicePublishType.DESKTOP);
        addEEnumLiteral(businessServicePublishTypeEEnum, BusinessServicePublishType.MOBILE);

        // Initialize data types
        initEDataType(auditEventTypeObjectEDataType,
                AuditEventType.class,
                "AuditEventTypeObject", //$NON-NLS-1$
                IS_SERIALIZABLE,
                IS_GENERATED_INSTANCE_CLASS);
        initEDataType(securityPolicyObjectEDataType,
                SecurityPolicy.class,
                "SecurityPolicyObject", //$NON-NLS-1$
                IS_SERIALIZABLE,
                IS_GENERATED_INSTANCE_CLASS);
        initEDataType(soapBindingStyleObjectEDataType,
                SoapBindingStyle.class,
                "SoapBindingStyleObject", //$NON-NLS-1$
                IS_SERIALIZABLE,
                IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        createResource(eNS_URI);

        // Create annotations
        // http:///org/eclipse/emf/ecore/util/ExtendedMetaData
        createExtendedMetaDataAnnotations();
        // ExtendedMetaData
        createExtendedMetaData_1Annotations();
    }

    /**
     * Initializes the annotations for
     * <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void createExtendedMetaDataAnnotations() {
        String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData"; //$NON-NLS-1$
        addAnnotation(this, source, new String[] { "qualified", "true" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(activityRefEClass,
                source,
                new String[] { "name", "ActivityRef", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getActivityRef_IdRef(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "IdRef", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(activityResourcePatternsEClass,
                source,
                new String[] { "name", "ActivityResourcePatterns", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getActivityResourcePatterns_AllocationStrategy(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "AllocationStrategy", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getActivityResourcePatterns_Piling(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Piling", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getActivityResourcePatterns_WorkItemPriority(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "WorkItemPriority", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(allocationStrategyEClass,
                source,
                new String[] { "name", "AllocationStrategy", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getAllocationStrategy_Offer(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Offer", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getAllocationStrategy_Strategy(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Strategy", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getAllocationStrategy_ReOfferOnClose(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ReOfferOnClose", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getAllocationStrategy_ReOfferOnCancel(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ReOfferOnCancel", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getAllocationStrategy_AllocateToOfferSetMemberIdentifier(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "AllocateToOfferSetMemberIdentifier", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(allocationStrategyTypeEEnum, source, new String[] { "name", "AllocationStrategyType_._type" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(associatedCorrelationFieldsEClass,
                source,
                new String[] { "name", "AssociatedCorrelationFields", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getAssociatedCorrelationFields_AssociatedCorrelationField(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "AssociatedCorrelationField", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getAssociatedCorrelationFields_DisableImplicitAssociation(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DisableImplicitAssociation" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(associatedCorrelationFieldEClass,
                source,
                new String[] { "name", "AssociatedCorrelationField", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getAssociatedCorrelationField_Name(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Name" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getAssociatedCorrelationField_CorrelationMode(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "CorrelationMode" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(associatedParameterEClass,
                source,
                new String[] { "name", "AssociatedParameter", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getAssociatedParameter_FormalParam(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "FormalParam" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getAssociatedParameter_Mode(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Mode" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getAssociatedParameter_Mandatory(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Mandatory" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(associatedParametersEClass,
                source,
                new String[] { "name", "AssociatedParameters", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getAssociatedParameters_AssociatedParameter(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "AssociatedParameter", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getAssociatedParameters_DisableImplicitAssociation(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DisableImplicitAssociation" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getAssociatedParametersContainer_AssociatedParameters(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "AssociatedParameter", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace", //$NON-NLS-1$ //$NON-NLS-2$
                        "wrap", "AssociatedParameters" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getAssociatedParametersContainer_DisableImplicitAssociation(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DisableImplicitAssociation" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(auditEClass,
                source,
                new String[] { "name", "Audit_._type", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getAudit_AuditEvent(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "AuditEvent", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getAudit_Any(),
                source,
                new String[] { "kind", "elementWildcard", //$NON-NLS-1$ //$NON-NLS-2$
                        "wildcards", "##other", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", ":1", //$NON-NLS-1$ //$NON-NLS-2$
                        "processing", "lax" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(auditEventEClass,
                source,
                new String[] { "name", "AuditEvent_._type", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getAuditEvent_Type(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Type" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getAuditEvent_Information(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Information", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(auditEventTypeEEnum, source, new String[] { "name", "Type_._type" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(auditEventTypeObjectEDataType,
                source,
                new String[] { "name", "Type_._type:Object", //$NON-NLS-1$ //$NON-NLS-2$
                        "baseType", "Type_._type" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(businessProcessEClass,
                source,
                new String[] { "name", "BusinessProcess", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getBusinessProcess_ProcessId(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "BusinessProcessId" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getBusinessProcess_PackageRefId(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "PackageRef" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getBusinessProcess_ActivityId(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ActivityId" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(calendarReferenceEClass,
                source,
                new String[] { "name", "CalendarReference_._type", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCalendarReference_Alias(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Alias" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCalendarReference_DataFieldId(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DataFieldId" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(catchErrorMappingsEClass,
                source,
                new String[] { "name", "CatchErrorMappings", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCatchErrorMappings_Message(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Message", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(constantPeriodEClass,
                source,
                new String[] { "name", "ConstantPeriod", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getConstantPeriod_Days(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Days" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getConstantPeriod_Hours(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Hours" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getConstantPeriod_MicroSeconds(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "MicroSeconds" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getConstantPeriod_Minutes(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Minutes" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getConstantPeriod_Months(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Months" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getConstantPeriod_Seconds(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Seconds" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getConstantPeriod_Weeks(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Weeks" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getConstantPeriod_Years(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Years" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(conditionalParticipantEClass,
                source,
                new String[] { "name", "ConditionalParticipant", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getConditionalParticipant_PerformerScript(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "PerformerScript", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(replyImmediateDataMappingsEClass,
                source,
                new String[] { "name", "ReplyImmediateDataMappings", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getReplyImmediateDataMappings_DataMappings(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DataMapping", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(correlationDataMappingsEClass,
                source,
                new String[] { "name", "CorrelationDataMappings", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCorrelationDataMappings_DataMappings(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DataMapping", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(correlationModeEEnum, source, new String[] { "name", "CorrelationMode_._type" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getDiscriminator_DiscriminatorType(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Type" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDiscriminator_StructuredDiscriminator(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "StructuredDiscriminator", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(documentRootEClass,
                source,
                new String[] { "name", "", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "mixed" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_XMLNSPrefixMap(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "xmlns:prefix" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_XSISchemaLocation(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "xsi:schemaLocation" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_ImplementationType(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ImplementationType", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_DataObjectAttributes(),
                source,
                new String[] { "name", "DataObjectAttributes", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_ExtendedAttributes(),
                source,
                new String[] { "name", "XpdExtAttributes", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_ContinueOnTimeout(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ContinueOnTimeout", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_Alias(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Alias", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_ConstantPeriod(),
                source,
                new String[] { "name", "ConstantPeriod", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_UserTaskScripts(),
                source,
                new String[] { "name", "UserTaskScripts", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_Audit(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Audit", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_Script(),
                source,
                new String[] { "name", "ScriptInformation", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_ReplyImmediately(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ReplyImmediate", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_InitialValues(),
                source,
                new String[] { "name", "InitialValues", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_AssociatedCorrelationFields(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "AssociatedCorrelationFields", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_AssociatedParameters(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "AssociatedParameters", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_ImplementedInterface(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ImplementedInterface", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_ProcessInterfaces(),
                source,
                new String[] { "name", "ProcessInterfaces", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_InlineSubProcess(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "InlineSubProcess", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_DocumentationURL(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DocumentationURL", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_Implements(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Implements", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_MultiInstanceScripts(),
                source,
                new String[] { "name", "MultiInstanceScripts", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_ProcessIdentifierField(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ProcessIdentifierField", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_Expression(),
                source,
                new String[] { "name", "Expression", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_Visibility(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Visibility", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_SecurityProfile(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SecurityProfile", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_Language(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Language", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_InitialParameterValue(),
                source,
                new String[] { "name", "InitialParameterValue", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_InitialValueMapping(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "InitialValueMapping", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_PortTypeOperation(),
                source,
                new String[] { "name", "PortTypeOperation", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_Transport(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Transport", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_IsChained(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "IsChained", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_ExternalReference(),
                source,
                new String[] { "name", "ExternalReference", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_ProcessResourcePatterns(),
                source,
                new String[] { "name", "ProcessResourcePatterns", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_EventHandlerInitialisers(),
                source,
                new String[] { "name", "EventHandlerInitialisers", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_ActivityResourcePatterns(),
                source,
                new String[] { "name", "ActivityResourcePatterns", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_RequireNewTransaction(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "RequireNewTransaction", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_DocumentOperation(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DocumentOperation", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_DurationCalculation(),
                source,
                new String[] { "name", "DurationCalculation", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_Discriminator(),
                source,
                new String[] { "name", "Discriminator", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_DisplayName(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DisplayName", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_CatchThrow(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "CatchThrow", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_IsRemote(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "IsRemote", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_CorrelationDataMappings(),
                source,
                new String[] { "name", "CorrelationDataMappings", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_TransformScript(),
                source,
                new String[] { "name", "TransformScript", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_PublishAsBusinessService(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "PublishAsBusinessService", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_BusinessServiceCategory(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "BusinessServiceCategory", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_ErrorThrowerInfo(),
                source,
                new String[] { "name", "ErrorThrowerInfo", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_CatchErrorMappings(),
                source,
                new String[] { "name", "CatchErrorMappings", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_ConditionalParticipant(),
                source,
                new String[] { "name", "ConditionalParticipant", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_Generated(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Generated", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_ReplyToActivityId(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ReplyToActivityId", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_TaskLibraryReference(),
                source,
                new String[] { "name", "TaskLibraryReference", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_SetPerformerInProcess(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SetPerformerInProcess", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_EmbSubprocOtherStateHeight(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "EmbSubprocOtherStateHeight", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_EmbSubprocOtherStateWidth(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "EmbSubprocOtherStateWidth", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_FormImplementation(),
                source,
                new String[] { "name", "FormImplementation", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_ParticipantQuery(),
                source,
                new String[] { "name", "ParticipantQuery", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_ApiEndPointParticipant(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ApiEndPointParticipant", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_FaultMessage(),
                source,
                new String[] { "name", "FaultMessage", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_RequestActivityId(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "RequestActivityId", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_BusinessProcess(),
                source,
                new String[] { "name", "BusinessProcess", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_WsdlGeneration(),
                source,
                new String[] { "name", "WsdlGeneration", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_TargetPrimitiveProperty(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "TargetPrimitiveProperty", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_SourcePrimitiveProperty(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SourcePrimitiveProperty", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_DecisionService(),
                source,
                new String[] { "name", "DecisionService", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_ParticipantSharedResource(),
                source,
                new String[] { "name", "ParticipantSharedResource", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_XpdModelType(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "XpdModelType", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_FlowRoutingStyle(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "FlowRoutingStyle", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_CalendarReference(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "CalendarReference", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_NonCancelling(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "NonCancelling", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_SignalData(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SignalData", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_Retry(),
                source,
                new String[] { "name", "Retry", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_ActivityDeadlineEventId(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ActivityDeadlineEventId", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_StartStrategy(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "StartStrategy", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_OverwriteAlreadyModifiedTaskData(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "OverwriteAlreadyModifiedTaskData", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_EventHandlerFlowStrategy(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "EventHandlerFlowStrategy", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_NamespacePrefixMap(),
                source,
                new String[] { "name", "NamespacePrefixMap", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_SuspendResumeWithParent(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SuspendResumeWithParent", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_SystemErrorAction(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SystemErrorAction", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_CorrelationTimeout(),
                source,
                new String[] { "name", "CorrelationTimeout", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_ValidationControl(),
                source,
                new String[] { "name", "ValidationControl", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_ReplyImmediateDataMappings(),
                source,
                new String[] { "name", "ReplyImmediateDataMappings", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_BxUseUnqualifiedPropertyNames(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "BxUseUnqualifiedPropertyNames", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_CaseRefType(),
                source,
                new String[] { "name", "CaseRefType", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_RESTServices(),
                source,
                new String[] { "name", "RESTServices", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_PublishAsRestService(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "publishAsRestService", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_RestActivityId(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "restActivityId", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_RescheduleTimerScript(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "RescheduleTimerScript", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_DynamicOrganizationMappings(),
                source,
                new String[] { "name", "DynamicOrganizationMappings", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_SignalHandlerAsynchronous(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "signalHandlerAsynchronous", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_GlobalDataOperation(),
                source,
                new String[] { "name", "GlobalDataOperation", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_ProcessDataWorkItemAttributeMappings(),
                source,
                new String[] { "name", "ProcessDataWorkItemAttributeMappings", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_AllowUnqualifiedSubProcessIdentification(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "AllowUnqualifiedSubProcessIdentification", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_BpmRuntimeConfiguration(),
                source,
                new String[] { "name", "BpmRuntimeConfiguration", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_IsCaseService(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "IsCaseService", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_RequiredAccessPrivileges(),
                source,
                new String[] { "name", "RequiredAccessPrivileges", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_CaseService(),
                source,
                new String[] { "name", "CaseService", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_AdHocTaskConfiguration(),
                source,
                new String[] { "name", "AdHocTaskConfiguration", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_IsEventSubProcess(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "IsEventSubProcess", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_NonInterruptingEvent(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "NonInterruptingEvent", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_CorrelateImmediately(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "CorrelateImmediately", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_AsyncExecutionMode(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "AsyncExecutionMode", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_SignalType(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SignalType", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_ServiceProcessConfiguration(),
                source,
                new String[] { "name", "ServiceProcessConfiguration", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_LikeMapping(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "likeMapping", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_ScriptDataMapper(),
                source,
                new String[] { "name", "ScriptDataMapper", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_LikeMappingExclusions(),
                source,
                new String[] { "name", "LikeMappingExclusions", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_SourceContributorId(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SourceContributorId", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_TargetContributorId(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "TargetContributorId", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_RestServiceOperation(),
                source,
                new String[] { "name", "RestServiceOperation", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_InputMappings(),
                source,
                new String[] { "name", "InputMappings", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_OutputMappings(),
                source,
                new String[] { "name", "OutputMappings", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_BusinessServicePublishType(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "BusinessServicePublishType", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_SuppressMaxMappingsError(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SuppressMaxMappingsError", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_FieldFormat(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "FieldFormat", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(durationCalculationEClass,
                source,
                new String[] { "name", "DurationCalculation", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDurationCalculation_Years(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Years", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDurationCalculation_Months(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Months", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDurationCalculation_Weeks(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Weeks", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDurationCalculation_Days(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Days", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDurationCalculation_Hours(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Hours", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDurationCalculation_Minutes(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Minutes", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDurationCalculation_Seconds(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Seconds", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDurationCalculation_Microseconds(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Microseconds", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(dynamicOrganizationMappingsEClass,
                source,
                new String[] { "name", "DynamicOrganizationMappings", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDynamicOrganizationMappings_DynamicOrganizationMapping(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DynamicOrganizationMapping", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(dynamicOrganizationMappingEClass,
                source,
                new String[] { "name", "DynamicOrganizationMapping", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDynamicOrganizationMapping_SourcePath(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SourcePath", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDynamicOrganizationMapping_DynamicOrgIdentifierRef(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DynamicOrgIdentifierRef", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(dynamicOrgIdentifierRefEClass,
                source,
                new String[] { "name", "DynamicOrgIdentifierRef", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDynamicOrgIdentifierRef_IdentifierName(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "IdentifierName" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDynamicOrgIdentifierRef_DynamicOrgId(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DynamicOrgId" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDynamicOrgIdentifierRef_OrgModelPath(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "OrgModelPath" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(emailResourceEClass,
                source,
                new String[] { "name", "EmailResource", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getEmailResource_InstanceName(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "InstanceName" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(errorMethodEClass,
                source,
                new String[] { "name", "ErrorMethod", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getErrorMethod_ErrorCode(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ErrorCode" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(errorThrowerInfoEClass,
                source,
                new String[] { "name", "ErrorThrowerInfo", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getErrorThrowerInfo_ThrowerId(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ThrowerId" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getErrorThrowerInfo_ThrowerContainerId(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ThrowerContainerId" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getErrorThrowerInfo_ThrowerType(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ThrowerType" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(eventHandlerFlowStrategyEEnum, source, new String[] { "name", "EventHandlerFlowStrategy_._type" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(eventHandlerInitialisersEClass,
                source,
                new String[] { "name", "EventHandlerInitialisers", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getEventHandlerInitialisers_ActivityRef(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ActivityRef", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(faultMessageEClass,
                source,
                new String[] { "name", "FaultMessage", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(flowRoutingStyleEEnum, source, new String[] { "name", "FlowRoutingStyle_._type" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getFormImplementation_FormType(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "FormType" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getFormImplementation_FormURI(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "FormURI" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(implementedInterfaceEClass,
                source,
                new String[] { "name", "ImplementedInterface", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getImplementedInterface_PackageRef(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "PackageRef", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getImplementedInterface_ProcessInterfaceId(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ProcessInterfaceId", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(initialValuesEClass,
                source,
                new String[] { "name", "InitialValues", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getInitialValues_Value(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Value", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(initialParameterValueEClass,
                source,
                new String[] { "name", "InitialParameterValue", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getInitialParameterValue_Name(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Name" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getInitialParameterValue_Value(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Value" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getInterfaceMethod_Trigger(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Trigger" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getInterfaceMethod_TriggerResultMessage(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "TriggerResultMessage", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getInterfaceMethod_Visibility(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Visibility" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getInterfaceMethod_ErrorMethods(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ErrorMethod", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace", //$NON-NLS-1$ //$NON-NLS-2$
                        "wrap", "ErrorMethods" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(intermediateMethodEClass,
                source,
                new String[] { "name", "IntermediateMethod", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(jdbcResourceEClass,
                source,
                new String[] { "name", "JdbcResource", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getJdbcResource_InstanceName(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "InstanceName" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getJdbcResource_JdbcProfileName(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "JdbcProfileName" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(multiInstanceScriptsEClass,
                source,
                new String[] { "name", "MultiInstanceScripts", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getMultiInstanceScripts_AdditionalInstances(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "AdditionalInstances", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(namespacePrefixMapEClass,
                source,
                new String[] { "name", "NamespacePrefixMap", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getNamespacePrefixMap_NamespaceEntries(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "NamespaceEntry", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace", //$NON-NLS-1$ //$NON-NLS-2$
                        "wrap", "NamespaceEntries" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getNamespacePrefixMap_PrefixMappingDisabled(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "PrefixMappingDisabled" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(namespaceMapEntryEClass,
                source,
                new String[] { "name", "NamespaceMapEntry", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getNamespaceMapEntry_Prefix(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Prefix" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getNamespaceMapEntry_Namespace(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Namespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(participantSharedResourceEClass,
                source,
                new String[] { "name", "ParticipantSharedResource", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getParticipantSharedResource_Email(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Email", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getParticipantSharedResource_Jdbc(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Jdbc", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getParticipantSharedResource_WebService(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "WebService", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getParticipantSharedResource_RestService(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "RestService", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(pilingInfoEClass,
                source,
                new String[] { "name", "PilingInformation", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getPilingInfo_PilingAllowed(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "PilingAllowed" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getPilingInfo_MaxPiliableItems(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "MaxPilableItems" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(portTypeOperationEClass,
                source,
                new String[] { "kind", "elementOnly", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "PortTypeOperation" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getPortTypeOperation_PortTypeName(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "PortTypeName" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getPortTypeOperation_OperationName(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "OperationName" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getPortTypeOperation_ExternalReference(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ExternalReference", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getPortTypeOperation_Transport(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Transport" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(processInterfaceEClass,
                source,
                new String[] { "name", "ProcessInterface", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly", //$NON-NLS-1$ //$NON-NLS-2$
                        "features-order", "startMethods intermediateMethods extendedAttributes" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getProcessInterface_StartMethods(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "StartMethod", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace", //$NON-NLS-1$ //$NON-NLS-2$
                        "wrap", "StartMethods" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getProcessInterface_IntermediateMethods(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "IntermediateMethod", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace", //$NON-NLS-1$ //$NON-NLS-2$
                        "wrap", "IntermediateMethods" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getProcessInterface_XpdInterfaceType(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "XpdInterfaceType" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getProcessInterface_ServiceProcessConfiguration(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ServiceProcessConfiguration", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(processInterfacesEClass,
                source,
                new String[] { "name", "ProcessInterfaces", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getProcessInterfaces_ProcessInterface(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ProcessInterface", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(processResourcePatternsEClass,
                source,
                new String[] { "name", "ProcessResourcePatterns", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getProcessResourcePatterns_SeparationOfDutiesActivities(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SeparationOfDutiesActivities", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getProcessResourcePatterns_RetainFamiliarActivities(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "RetainFamiliarActivities", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(rescheduleTimerScriptEClass,
                source,
                new String[] { "name", "RescheduleTimerScript", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "mixed" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getRescheduleTimerScript_DurationRelativeTo(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DurationRelativeTo" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(rescheduleDurationTypeEEnum, source, new String[] { "name", "DurationRelativeToType_._type" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(rescheduleTimersEClass,
                source,
                new String[] { "name", "RescheduleTimers", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "Element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getRescheduleTimers_TimerSelectionType(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "TimerSelection" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getRescheduleTimers_TimerEvents(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ActivityRef", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace", //$NON-NLS-1$ //$NON-NLS-2$
                        "wrap", "TimerEvents" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(rescheduleTimerSelectionTypeEEnum,
                source,
                new String[] { "name", "RescheduleTimerSelectionType_._type" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(restServicesEClass, source, new String[] { "name", "RESTServices" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getRESTServices_RESTServices(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "RESTService", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(restServiceResourceEClass,
                source,
                new String[] { "name", "RestServiceResource", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getRestServiceResource_SecurityPolicy(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SecurityPolicy", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getRestServiceResource_ResourceName(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "resourceName" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getRestServiceResource_ResourceType(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "resourceType" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getRestServiceResource_Description(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "description" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getRestServiceResourceSecurity_PolicyType(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "PolicyType" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(retainFamiliarActivitiesEClass,
                source,
                new String[] { "name", "RetainFamiliarActivities", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getRetainFamiliarActivities_ActivityRef(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ActivityRef", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(retryEClass,
                source,
                new String[] { "name", "Retry", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getRetry_Max(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Max" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getRetry_InitialPeriod(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "InitialPeriod" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getRetry_PeriodIncrement(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "PeriodIncrement" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getRetry_MaxRetryAction(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "MaxRetryAction" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(scriptInformationEClass,
                source,
                new String[] { "name", "ScriptInformation", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getScriptInformation_Expression(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Expression", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getScriptInformation_Direction(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Direction" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getScriptInformation_Activity(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Activity", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getScriptInformation_Reference(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Reference" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(securityPolicyEEnum, source, new String[] { "name", "SecurityPolicy" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(securityPolicyObjectEDataType,
                source,
                new String[] { "name", "SecurityPolicy:Object", //$NON-NLS-1$ //$NON-NLS-2$
                        "baseType", "SecurityPolicy" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(separationOfDutiesActivitiesEClass,
                source,
                new String[] { "name", "SeparationOfDutiesActivities", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSeparationOfDutiesActivities_ActivityRef(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ActivityRef", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(signalDataEClass,
                source,
                new String[] { "name", "SignalData", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSignalData_CorrelationMappings(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "CorrelationMappings", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSignalData_DataMappings(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DataMapping", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace", //$NON-NLS-1$ //$NON-NLS-2$
                        "wrap", "DataMappings" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSignalData_RescheduleTimers(),
                source,
                new String[] { "name", "RescheduleTimers", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSignalData_InputScriptDataMapper(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "InputScriptDataMapper", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSignalData_OutputScriptDataMapper(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "OutputScriptDataMapper", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(startMethodEClass,
                source,
                new String[] { "name", "StartMethod", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(soapBindingStyleEEnum, source, new String[] { "name", "SoapBindingStyle" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(soapBindingStyleEEnum,
                1,
                "http:///org/eclipse/emf/ecore/util/ExtendedMetaData", //$NON-NLS-1$
                new String[] { "name", "SoapBindingStyle" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(soapBindingStyleObjectEDataType,
                source,
                new String[] { "name", "SoapBindingStyle:Object", //$NON-NLS-1$ //$NON-NLS-2$
                        "baseType", "SoapBindingStyle" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(fieldFormatEEnum, source, new String[] { "name", "FieldFormat" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(fieldFormatEEnum,
                1,
                "http:///org/eclipse/emf/ecore/util/ExtendedMetaData", //$NON-NLS-1$
                new String[] { "name", "FieldFormat" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(structuredDiscriminatorEClass,
                source,
                new String[] { "name", "StructuredDiscriminator", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "Element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getStructuredDiscriminator_WaitForIncomingPath(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "WaitForIncomingPath", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getStructuredDiscriminator_UpStreamParallelSplit(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "UpStreamParallelSplit", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(subProcessStartStrategyEEnum, source, new String[] { "name", "SubProcessStartStrategy_._type" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(systemErrorActionTypeEEnum, source, new String[] { "name", "SystemErrorActionType_._type" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(taskLibraryReferenceEClass,
                source,
                new String[] { "name", "TaskLibraryReference", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getTaskLibraryReference_TaskLibraryId(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "TaskLibraryId" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getTaskLibraryReference_PackageRef(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "PackageRef" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(transformScriptEClass,
                source,
                new String[] { "name", "TransformScript", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getTransformScript_DataMappings(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DataMapping", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace", //$NON-NLS-1$ //$NON-NLS-2$
                        "wrap", "DataMappings" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getTransformScript_InputDom(),
                source,
                new String[] { "kind", "elementOnly", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "InputDom", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getTransformScript_OutputDom(),
                source,
                new String[] { "kind", "elementOnly", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "OutputDom", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(userTaskScriptsEClass,
                source,
                new String[] { "name", "UserTaskScripts", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getUserTaskScripts_OpenScript(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "OpenScript", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getUserTaskScripts_CloseScript(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "CloseScript", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getUserTaskScripts_SubmitScript(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SubmitScript", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getUserTaskScripts_ScheduleScript(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ScheduleScript", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getUserTaskScripts_RescheduleScript(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "RescheduleScript", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(validationControlEClass,
                source,
                new String[] { "name", "ValidationControl", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getValidationControl_ValidationIssueOverrides(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ValidationIssueOverride", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace", //$NON-NLS-1$ //$NON-NLS-2$
                        "wrap", "ValidationIssueOverrides" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(validationIssueOverrideEClass,
                source,
                new String[] { "name", "ValidationIssueOverride", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getValidationIssueOverride_ValidationIssueId(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ValidationIssueId" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getValidationIssueOverride_OverrideType(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "OverrideType" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(validationIssueOverrideTypeEEnum,
                source,
                new String[] { "name", "ValidationIssueOverrideType_._type" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(visibilityEEnum, source, new String[] { "name", "Visibility_._type" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(wsdlEventAssociationEClass,
                source,
                new String[] { "name", "WsdlEventAssociation", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsdlEventAssociation_EventId(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "EventId", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(workItemPriorityEClass,
                source,
                new String[] { "name", "WorkItemPriority", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWorkItemPriority_InitialPriority(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "InitialPriority" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(wsdlGenerationEClass,
                source,
                new String[] { "name", "WsdlGeneration", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsdlGeneration_SoapBindingStyle(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SoapBindingStyle" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(wsBindingEClass,
                source,
                new String[] { "name", "WsBinding", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsBinding_Name(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Name" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(wsInboundEClass,
                source,
                new String[] { "name", "WsInbound", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsInbound_VirtualBinding(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "VirtualBinding", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsInbound_SoapHttpBinding(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SoapHttpBinding", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsInbound_SoapJmsBinding(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SoapJmsBinding", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(wsOutboundEClass,
                source,
                new String[] { "name", "WsOutbound", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsOutbound_VirtualBinding(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "VirtualBinding", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsOutbound_SoapHttpBinding(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SoapHttpBinding", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsOutbound_SoapJmsBinding(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SoapJmsBinding", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(wsResourceEClass,
                source,
                new String[] { "name", "WsResource", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsResource_Inbound(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Inbound", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsResource_Outbound(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Outbound", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(wsSecurityPolicyEClass,
                source,
                new String[] { "name", "WsSecurityPolicy", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsSecurityPolicy_GovernanceApplicationName(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "GovernanceApplicationName" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsSecurityPolicy_Type(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Type" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(wsSoapBindingEClass,
                source,
                new String[] { "name", "WsSoapBinding", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsSoapBinding_BindingStyle(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "BindingStyle" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsSoapBinding_SoapVersion(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SoapVersion" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(wsSoapHttpInboundBindingEClass,
                source,
                new String[] { "name", "WsSoapHttpInboundBinding", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsSoapHttpInboundBinding_InboundSecurity(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "InboundSecurity", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsSoapHttpInboundBinding_EndpointUrlPath(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "EndpointUrlPath" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsSoapHttpInboundBinding_HttpConnectorInstanceName(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "HttpConnectorInstanceName" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(wsSoapHttpOutboundBindingEClass,
                source,
                new String[] { "name", "WsSoapHttpOutboundBinding", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsSoapHttpOutboundBinding_OutboundSecurity(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "OutboundSecurity", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsSoapHttpOutboundBinding_HttpClientInstanceName(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "HttpClientInstanceName" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(wsSoapJmsInboundBindingEClass,
                source,
                new String[] { "name", "WsSoapJmsInboundBinding", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsSoapJmsInboundBinding_OutboundConnectionFactory(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ConnectionFactory" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsSoapJmsInboundBinding_InboundConnectionFactoryConfiguration(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ConnectionFactoryConfigurator" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsSoapJmsInboundBinding_InboundDestination(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "InboundDestination" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(wsSoapJmsOutboundBindingEClass,
                source,
                new String[] { "name", "WsSoapJmsOutboundBinding", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsSoapJmsOutboundBinding_OutboundConnectionFactory(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "OutboundConnectionFactory" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsSoapJmsOutboundBinding_InboundDestination(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "InboundDestination" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsSoapJmsOutboundBinding_OutboundDestination(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "OutboundDestination" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsSoapJmsOutboundBinding_DeliveryMode(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DeliveryMode" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsSoapJmsOutboundBinding_Priority(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Priority" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsSoapJmsOutboundBinding_MessageExpiration(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "MessageExpiration" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsSoapJmsOutboundBinding_InvocationTimeout(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "InvocationTimeout" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(deliveryModeEEnum, source, new String[] { "name", "Delivery_._Mode" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(wsSoapSecurityEClass,
                source,
                new String[] { "name", "WsSoapSecurity", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWsSoapSecurity_SecurityPolicy(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SecurityPolicy", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(wsVirtualBindingEClass,
                source,
                new String[] { "name", "WsVirtualBinding", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(xpdExtDataObjectAttributesEClass, source, new String[] { "name", "DataObjectAttributes" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getXpdExtDataObjectAttributes_Description(),
                source,
                new String[] { "name", "Description", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getXpdExtDataObjectAttributes_ExternalReference(),
                source,
                new String[] { "name", "ExternalReference", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getXpdExtDataObjectAttributes_Properties(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Property", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace", //$NON-NLS-1$ //$NON-NLS-2$
                        "wrap", "Properties" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(xpdExtPropertyEClass,
                source,
                new String[] { "name", "Property", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "simple" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getXpdExtProperty_Value(),
                source,
                new String[] { "name", ":0", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "simple" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(xpdExtAttributeEClass,
                source,
                new String[] { "name", "XpdExtAttribute_._type", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "mixed" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getXpdExtAttribute_Mixed(),
                source,
                new String[] { "kind", "elementWildcard", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", ":mixed" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getXpdExtAttribute_Group(),
                source,
                new String[] { "kind", "group", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "group:1" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getXpdExtAttribute_Any(),
                source,
                new String[] { "kind", "elementWildcard", //$NON-NLS-1$ //$NON-NLS-2$
                        "wildcards", "##any", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", ":2", //$NON-NLS-1$ //$NON-NLS-2$
                        "processing", "lax", //$NON-NLS-1$ //$NON-NLS-2$
                        "group", "#group:1" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getXpdExtAttribute_Name(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Name" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getXpdExtAttribute_Value(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Value" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(xpdExtAttributesEClass, source, new String[] { "name", "ExtendedAttributes" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getXpdExtAttributes_Attributes(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace", //$NON-NLS-1$ //$NON-NLS-2$
                        "wrap", "Attributes" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(updateCaseOperationTypeEClass,
                source,
                new String[] { "name", "Update_._type", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getUpdateCaseOperationType_FromFieldPath(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "FromFieldPath" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(xpdModelTypeEEnum, source, new String[] { "name", "XpdModelType_._type" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(addLinkAssociationsTypeEClass,
                source,
                new String[] { "name", "AddLinkAssociations_._type", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getAddLinkAssociationsType_AddCaseRefField(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "AddCaseRefField" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getAddLinkAssociationsType_AssociationName(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "AssociationName" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(removeLinkAssociationsTypeEClass,
                source,
                new String[] { "name", "RemoveLinkAssociations_._type", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getRemoveLinkAssociationsType_AssociationName(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "AssociationName" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getRemoveLinkAssociationsType_RemoveCaseRefField(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "RemoveCaseRefField" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(caseReferenceOperationsTypeEClass,
                source,
                new String[] { "name", "CaseReferenceOperations_._type", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCaseReferenceOperationsType_CaseRefField(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "CaseRefField", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCaseReferenceOperationsType_Update(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Update", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCaseReferenceOperationsType_Delete(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Delete", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCaseReferenceOperationsType_AddLinkAssociations(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "AddLinkAssociations", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCaseReferenceOperationsType_RemoveLinkAssociations(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "RemoveLinkAssociations", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(globalDataOperationEClass,
                source,
                new String[] { "name", "GlobalDataOperation", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getGlobalDataOperation_CaseAccessOperations(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "CaseAccessOperations", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getGlobalDataOperation_CaseReferenceOperations(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "CaseReferenceOperations", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(deleteByCaseIdentifierTypeEClass,
                source,
                new String[] { "name", "DeleteByCaseIdentifier_._type", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDeleteByCaseIdentifierType_FieldPath(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "FieldPath" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDeleteByCaseIdentifierType_IdentifierName(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "IdentifierName" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(compositeIdentifierTypeEClass,
                source,
                new String[] { "name", "CompositeIdentifier_._type", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCompositeIdentifierType_FieldPath(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "FieldPath" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCompositeIdentifierType_Identifiername(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Identifiername" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(deleteCaseReferenceOperationTypeEClass,
                source,
                new String[] { "name", "DeleteCaseReferenceOperationType", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(deleteByCompositeIdentifiersTypeEClass,
                source,
                new String[] { "name", "DeleteByCompositeIdentifiers_._type", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDeleteByCompositeIdentifiersType_Group(),
                source,
                new String[] { "kind", "group", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "group:0" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDeleteByCompositeIdentifiersType_CompositeIdentifier(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "CompositeIdentifier", //$NON-NLS-1$ //$NON-NLS-2$
                        "group", "#group:0", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(createCaseOperationTypeEClass,
                source,
                new String[] { "name", "Create_._type", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCreateCaseOperationType_FromFieldPath(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "FromFieldPath" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCreateCaseOperationType_ToCaseRefField(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ToCaseRefField" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(caseAccessOperationsTypeEClass,
                source,
                new String[] { "name", "CaseAccessOperations_._type", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCaseAccessOperationsType_CaseClassExternalReference(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "CaseClassExternalReference", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCaseAccessOperationsType_Create(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Create", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCaseAccessOperationsType_DeleteByCaseIdentifier(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DeleteByCaseIdentifier", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCaseAccessOperationsType_DeleteByCompositeIdentifiers(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DeleteByCompositeIdentifiers", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(dataWorkItemAttributeMappingEClass,
                source,
                new String[] { "name", "DataWorkItemAttributeMapping_._type", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDataWorkItemAttributeMapping_Attribute(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "WorkItemAttribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDataWorkItemAttributeMapping_ProcessData(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ProcessData", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(processDataWorkItemAttributeMappingsEClass,
                source,
                new String[] { "name", "ProcessDataWorkItemAttributeMappings_._type", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getProcessDataWorkItemAttributeMappings_DataWorkItemAttributeMapping(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DataWorkItemAttributeMapping", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(bpmRuntimeConfigurationEClass,
                source,
                new String[] { "name", "BpmRuntimeConfiguration", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getBpmRuntimeConfiguration_IncomingRequestTimeout(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "IncomingRequestTimeout" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(enablementTypeEClass,
                source,
                new String[] { "name", "Enablement_._type", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getEnablementType_InitializerActivities(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "InitializerActivities", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getEnablementType_PreconditionExpression(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "PreconditionExpression", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(initializerActivitiesTypeEClass,
                source,
                new String[] { "name", "InitializerActivities_._type", //$NON-NLS-1$ //$NON-NLS-2$
                        "key", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getInitializerActivitiesType_ActivityRef(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ActivityRef", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(adHocTaskConfigurationTypeEClass,
                source,
                new String[] { "name", "AdHocTaskConfiguration_._type", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getAdHocTaskConfigurationType_Enablement(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Enablement", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getAdHocTaskConfigurationType_AdHocExecutionType(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "AdHocExecutionType" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getAdHocTaskConfigurationType_SuspendMainFlow(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SuspendMainFlow" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getAdHocTaskConfigurationType_AllowMultipleInvocations(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "AllowMultipleInvocations" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getAdHocTaskConfigurationType_RequiredAccessPrivileges(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "RequiredAccessPrivileges", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(adHocExecutionTypeTypeEEnum, source, new String[] { "name", "AdHocExecutionType_._type" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(requiredAccessPrivilegesEClass,
                source,
                new String[] { "name", "RequiredAccessPrivileges", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getRequiredAccessPrivileges_PrivilegeReference(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "PrivilegeReference", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(visibleForCaseStatesEClass,
                source,
                new String[] { "name", "VisibleForCaseStates", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getVisibleForCaseStates_VisibleForUnsetCaseState(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "VisibleForUnsetCaseState", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getVisibleForCaseStates_CaseState(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "CaseState", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(caseServiceEClass,
                source,
                new String[] { "name", "CaseService", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCaseService_CaseClassType(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "CaseClassType", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCaseService_VisibleForCaseStates(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "VisibleForCaseStates" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(documentOperationEClass,
                source,
                new String[] { "name", "DocumentOperation", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentOperation_CaseDocRefOperation(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "CaseDocRefOperations", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentOperation_CaseDocFindOperations(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "CaseDocFindOperations", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentOperation_LinkSystemDocumentOperation(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "LinkSystemDocumentOperation", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(caseDocRefOperationsEClass,
                source,
                new String[] { "name", "CaseDocRefOperations", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCaseDocRefOperations_MoveCaseDocOperation(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "MoveCaseDocOperation", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCaseDocRefOperations_UnlinkCaseDocOperation(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "UnlinkCaseDocOperation", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCaseDocRefOperations_LinkCaseDocOperation(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "LinkCaseDocOperation", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCaseDocRefOperations_DeleteCaseDocOperation(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DeleteCaseDocOperation", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCaseDocRefOperations_CaseDocumentRefField(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "CaseDocumentRefField" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(caseDocFindOperationsEClass,
                source,
                new String[] { "name", "CaseDocFindOperations", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCaseDocFindOperations_FindByFileNameOperation(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "FindByFileNameOperation", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCaseDocFindOperations_FindByQueryOperation(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "FindByQueryOperation", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCaseDocFindOperations_ReturnCaseDocRefsField(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ReturnCaseDocRefsField" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCaseDocFindOperations_CaseRefField(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "CaseRefField" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(moveCaseDocOperationEClass,
                source,
                new String[] { "name", "MoveCaseDocOperation", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getMoveCaseDocOperation_SourceCaseRefField(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SourceCaseRefField" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getMoveCaseDocOperation_TargetCaseRefField(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "TargetCaseRefField" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(unlinkCaseDocOperationEClass,
                source,
                new String[] { "name", "UnlinkCaseDocOperation", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getUnlinkCaseDocOperation_SourceCaseRefField(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SourceCaseRefField" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(linkCaseDocOperationEClass,
                source,
                new String[] { "name", "LinkCaseDocOperation", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getLinkCaseDocOperation_TargetCaseRefField(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "TargetCaseRefField" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getLinkSystemDocumentOperation_DocumentId(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DocumentId" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getLinkSystemDocumentOperation_ReturnCaseDocRefField(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ReturnCaseDocRefField" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getLinkSystemDocumentOperation_CaseRefField(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "CaseRefField" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(deleteCaseDocOperationEClass,
                source,
                new String[] { "name", "DeleteCaseDocOperation", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(findByFileNameOperationEClass,
                source,
                new String[] { "name", "FindByFileNameOperation", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getFindByFileNameOperation_FileNameField(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "FileNameField" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(findByQueryOperationEClass,
                source,
                new String[] { "name", "FindByQueryOperation", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getFindByQueryOperation_CaseDocumentQueryExpression(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "CaseDocumentQueryExpression", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCaseDocumentQueryExpression_QueryExpressionJoinType(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "QueryExpressionJoinType" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCaseDocumentQueryExpression_OpenBracketCount(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "OpenBracketCount" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCaseDocumentQueryExpression_CmisProperty(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "CMISProperty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCaseDocumentQueryExpression_Operator(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "CMISQueryOperator" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCaseDocumentQueryExpression_ProcessDataField(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ProcessDataField" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCaseDocumentQueryExpression_CloseBracketCount(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "CloseBracketCount" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getCaseDocumentQueryExpression_CmisDocumentPropertySelected(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "CmisDocumentPropertySelected" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(asyncExecutionModeEEnum, source, new String[] { "name", "AsyncExecutionMode_._type" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(signalTypeEEnum, source, new String[] { "name", "SignalType_._type" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(xpdInterfaceTypeEEnum, source, new String[] { "name", "XpdInterfaceType_._type" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(serviceProcessConfigurationEClass,
                source,
                new String[] { "name", "ServiceProcessConfiguration", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getServiceProcessConfiguration_DeployToProcessRuntime(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DeployToProcessRuntime", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getServiceProcessConfiguration_DeployToPageflowRuntime(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DeployToPageflowRuntime", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(scriptDataMapperEClass,
                source,
                new String[] { "name", "ScriptDataMapper", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getScriptDataMapper_MapperContext(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "MapperContext" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getScriptDataMapper_MappingDirection(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "MappingDirection" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getScriptDataMapper_DataMappings(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DataMapping", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace", //$NON-NLS-1$ //$NON-NLS-2$
                        "wrap", "DataMappings" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getScriptDataMapper_UnmappedScripts(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ScriptInformation", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace", //$NON-NLS-1$ //$NON-NLS-2$
                        "wrap", "UnmappedScripts" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getScriptDataMapper_ArrayInflationType(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DataMapperArrayInflation", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace", //$NON-NLS-1$ //$NON-NLS-2$
                        "wrap", "ArrayInflation" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(dataMapperArrayInflationEClass,
                source,
                new String[] { "name", "DataMapperArrayInflation", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDataMapperArrayInflation_Path(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Path" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDataMapperArrayInflation_MappingType(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "MappingType" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDataMapperArrayInflation_ContributorId(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ContributorId" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(dataMapperArrayInflationTypeEEnum,
                source,
                new String[] { "name", "DataMapperArrayInflationType_._type" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(likeMappingExclusionEClass,
                source,
                new String[] { "name", "LikeMappingExclusion", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getLikeMappingExclusion_Path(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Path" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(likeMappingExclusionsEClass,
                source,
                new String[] { "name", "LikeMappingExclusions", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getLikeMappingExclusions_Exclusions(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "LikeMappingExclusion", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(restServiceOperationEClass,
                source,
                new String[] { "name", "RestServiceOperation", //$NON-NLS-1$ //$NON-NLS-2$
                        "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getRestServiceOperation_Location(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Location", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getRestServiceOperation_MethodId(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "MethodId", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(businessServicePublishTypeEEnum,
                source,
                new String[] { "name", "BusinessServicePublishType_._type" //$NON-NLS-1$ //$NON-NLS-2$
                });
    }

    /**
     * Initializes the annotations for <b>ExtendedMetaData</b>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void createExtendedMetaData_1Annotations() {
        String source = "ExtendedMetaData"; //$NON-NLS-1$
        addAnnotation(discriminatorEClass,
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Discriminator", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(formImplementationEClass,
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "FormImplementation", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(interfaceMethodEClass,
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "InterfaceMethod", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
    }

} // XpdExtensionPackageImpl
