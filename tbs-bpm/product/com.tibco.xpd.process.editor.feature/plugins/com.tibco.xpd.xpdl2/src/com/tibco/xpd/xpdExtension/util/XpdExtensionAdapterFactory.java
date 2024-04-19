/**
 * <copyright> </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.util;

import com.tibco.xpd.xpdExtension.*;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.AddLinkAssociationsType;
import com.tibco.xpd.xpdExtension.AllocationStrategy;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationField;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationFields;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.AssociatedParametersContainer;
import com.tibco.xpd.xpdExtension.Audit;
import com.tibco.xpd.xpdExtension.AuditEvent;
import com.tibco.xpd.xpdExtension.BusinessProcess;
import com.tibco.xpd.xpdExtension.CalendarReference;
import com.tibco.xpd.xpdExtension.CaseAccessOperationsType;
import com.tibco.xpd.xpdExtension.CaseReferenceOperationsType;
import com.tibco.xpd.xpdExtension.CatchErrorMappings;
import com.tibco.xpd.xpdExtension.CompositeIdentifierType;
import com.tibco.xpd.xpdExtension.ConditionalParticipant;
import com.tibco.xpd.xpdExtension.ConstantPeriod;
import com.tibco.xpd.xpdExtension.CorrelationDataMappings;
import com.tibco.xpd.xpdExtension.CreateType;
import com.tibco.xpd.xpdExtension.DataWorkItemAttributeMapping;
import com.tibco.xpd.xpdExtension.DeleteByCaseIdentifierType;
import com.tibco.xpd.xpdExtension.DeleteByCompositeIdentifiersType;
import com.tibco.xpd.xpdExtension.Discriminator;
import com.tibco.xpd.xpdExtension.DocumentRoot;
import com.tibco.xpd.xpdExtension.DurationCalculation;
import com.tibco.xpd.xpdExtension.DynamicOrgIdentifierRef;
import com.tibco.xpd.xpdExtension.DynamicOrganizationMapping;
import com.tibco.xpd.xpdExtension.DynamicOrganizationMappings;
import com.tibco.xpd.xpdExtension.EmailResource;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.ErrorThrowerInfo;
import com.tibco.xpd.xpdExtension.EventHandlerInitialisers;
import com.tibco.xpd.xpdExtension.FaultMessage;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.GlobalDataOperation;
import com.tibco.xpd.xpdExtension.ImplementedInterface;
import com.tibco.xpd.xpdExtension.InitialParameterValue;
import com.tibco.xpd.xpdExtension.InitialValues;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.JdbcResource;
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
import com.tibco.xpd.xpdExtension.RESTServices;
import com.tibco.xpd.xpdExtension.RemoveLinkAssociationsType;
import com.tibco.xpd.xpdExtension.ReplyImmediateDataMappings;
import com.tibco.xpd.xpdExtension.RescheduleTimerScript;
import com.tibco.xpd.xpdExtension.RescheduleTimers;
import com.tibco.xpd.xpdExtension.RetainFamiliarActivities;
import com.tibco.xpd.xpdExtension.Retry;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.StructuredDiscriminator;
import com.tibco.xpd.xpdExtension.TaskLibraryReference;
import com.tibco.xpd.xpdExtension.TransformScript;
import com.tibco.xpd.xpdExtension.UpdateType;
import com.tibco.xpd.xpdExtension.UserTaskScripts;
import com.tibco.xpd.xpdExtension.ValidationControl;
import com.tibco.xpd.xpdExtension.ValidationIssueOverride;
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
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DescribedElement;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import com.tibco.xpd.xpdl2.FormalParametersContainer;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.UniqueIdElement;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides
 * an adapter <code>createXXX</code> method for each class of the model. <!--
 * end-user-doc -->
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage
 * @generated
 */
public class XpdExtensionAdapterFactory extends AdapterFactoryImpl
{

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public static final String				copyright	= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The cached model package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected static XpdExtensionPackage	modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public XpdExtensionAdapterFactory()
	{
		if (modelPackage == null)
		{
			modelPackage = XpdExtensionPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc --> This implementation returns <code>true</code> if
	 * the object is either the model's package or is an instance object of the
	 * model. <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object)
	{
		if (object == modelPackage)
		{
			return true;
		}
		if (object instanceof EObject)
		{
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected XpdExtensionSwitch<Adapter> modelSwitch = new XpdExtensionSwitch<Adapter>()
	{
		@Override
		public Adapter caseActivityRef(ActivityRef object)
		{
			return createActivityRefAdapter();
		}

		@Override
		public Adapter caseActivityResourcePatterns(ActivityResourcePatterns object)
		{
			return createActivityResourcePatternsAdapter();
		}

		@Override
		public Adapter caseAllocationStrategy(AllocationStrategy object)
		{
			return createAllocationStrategyAdapter();
		}

		@Override
		public Adapter caseAssociatedCorrelationFields(AssociatedCorrelationFields object)
		{
			return createAssociatedCorrelationFieldsAdapter();
		}

		@Override
		public Adapter caseAssociatedCorrelationField(AssociatedCorrelationField object)
		{
			return createAssociatedCorrelationFieldAdapter();
		}

		@Override
		public Adapter caseAssociatedParameter(AssociatedParameter object)
		{
			return createAssociatedParameterAdapter();
		}

		@Override
		public Adapter caseAssociatedParameters(AssociatedParameters object)
		{
			return createAssociatedParametersAdapter();
		}

		@Override
		public Adapter caseAssociatedParametersContainer(AssociatedParametersContainer object)
		{
			return createAssociatedParametersContainerAdapter();
		}

		@Override
		public Adapter caseAudit(Audit object)
		{
			return createAuditAdapter();
		}

		@Override
		public Adapter caseAuditEvent(AuditEvent object)
		{
			return createAuditEventAdapter();
		}

		@Override
		public Adapter caseBusinessProcess(BusinessProcess object)
		{
			return createBusinessProcessAdapter();
		}

		@Override
		public Adapter caseCalendarReference(CalendarReference object)
		{
			return createCalendarReferenceAdapter();
		}

		@Override
		public Adapter caseCatchErrorMappings(CatchErrorMappings object)
		{
			return createCatchErrorMappingsAdapter();
		}

		@Override
		public Adapter caseConstantPeriod(ConstantPeriod object)
		{
			return createConstantPeriodAdapter();
		}

		@Override
		public Adapter caseConditionalParticipant(ConditionalParticipant object)
		{
			return createConditionalParticipantAdapter();
		}

		@Override
		public Adapter caseReplyImmediateDataMappings(ReplyImmediateDataMappings object)
		{
			return createReplyImmediateDataMappingsAdapter();
		}

		@Override
		public Adapter caseCorrelationDataMappings(CorrelationDataMappings object)
		{
			return createCorrelationDataMappingsAdapter();
		}

		@Override
		public Adapter caseDiscriminator(Discriminator object)
		{
			return createDiscriminatorAdapter();
		}

		@Override
		public Adapter caseDocumentRoot(DocumentRoot object)
		{
			return createDocumentRootAdapter();
		}

		@Override
		public Adapter caseDurationCalculation(DurationCalculation object)
		{
			return createDurationCalculationAdapter();
		}

		@Override
		public Adapter caseDynamicOrganizationMappings(DynamicOrganizationMappings object)
		{
			return createDynamicOrganizationMappingsAdapter();
		}

		@Override
		public Adapter caseDynamicOrganizationMapping(DynamicOrganizationMapping object)
		{
			return createDynamicOrganizationMappingAdapter();
		}

		@Override
		public Adapter caseDynamicOrgIdentifierRef(DynamicOrgIdentifierRef object)
		{
			return createDynamicOrgIdentifierRefAdapter();
		}

		@Override
		public Adapter caseEmailResource(EmailResource object)
		{
			return createEmailResourceAdapter();
		}

		@Override
		public Adapter caseErrorMethod(ErrorMethod object)
		{
			return createErrorMethodAdapter();
		}

		@Override
		public Adapter caseErrorThrowerInfo(ErrorThrowerInfo object)
		{
			return createErrorThrowerInfoAdapter();
		}

		@Override
		public Adapter caseEventHandlerInitialisers(EventHandlerInitialisers object)
		{
			return createEventHandlerInitialisersAdapter();
		}

		@Override
		public Adapter caseFaultMessage(FaultMessage object)
		{
			return createFaultMessageAdapter();
		}

		@Override
		public Adapter caseFormImplementation(FormImplementation object)
		{
			return createFormImplementationAdapter();
		}

		@Override
		public Adapter caseImplementedInterface(ImplementedInterface object)
		{
			return createImplementedInterfaceAdapter();
		}

		@Override
		public Adapter caseInitialValues(InitialValues object)
		{
			return createInitialValuesAdapter();
		}

		@Override
		public Adapter caseInitialParameterValue(InitialParameterValue object)
		{
			return createInitialParameterValueAdapter();
		}

		@Override
		public Adapter caseInterfaceMethod(InterfaceMethod object)
		{
			return createInterfaceMethodAdapter();
		}

		@Override
		public Adapter caseIntermediateMethod(IntermediateMethod object)
		{
			return createIntermediateMethodAdapter();
		}

		@Override
		public Adapter caseJdbcResource(JdbcResource object)
		{
			return createJdbcResourceAdapter();
		}

		@Override
		public Adapter caseMultiInstanceScripts(MultiInstanceScripts object)
		{
			return createMultiInstanceScriptsAdapter();
		}

		@Override
		public Adapter caseNamespacePrefixMap(NamespacePrefixMap object)
		{
			return createNamespacePrefixMapAdapter();
		}

		@Override
		public Adapter caseNamespaceMapEntry(NamespaceMapEntry object)
		{
			return createNamespaceMapEntryAdapter();
		}

		@Override
		public Adapter caseParticipantSharedResource(ParticipantSharedResource object)
		{
			return createParticipantSharedResourceAdapter();
		}

		@Override
		public Adapter casePilingInfo(PilingInfo object)
		{
			return createPilingInfoAdapter();
		}

		@Override
		public Adapter casePortTypeOperation(PortTypeOperation object)
		{
			return createPortTypeOperationAdapter();
		}

		@Override
		public Adapter caseProcessInterface(ProcessInterface object)
		{
			return createProcessInterfaceAdapter();
		}

		@Override
		public Adapter caseProcessInterfaces(ProcessInterfaces object)
		{
			return createProcessInterfacesAdapter();
		}

		@Override
		public Adapter caseProcessResourcePatterns(ProcessResourcePatterns object)
		{
			return createProcessResourcePatternsAdapter();
		}

		@Override
		public Adapter caseRescheduleTimerScript(RescheduleTimerScript object)
		{
			return createRescheduleTimerScriptAdapter();
		}

		@Override
		public Adapter caseRescheduleTimers(RescheduleTimers object)
		{
			return createRescheduleTimersAdapter();
		}

		@Override
		public Adapter caseRESTServices(RESTServices object)
		{
			return createRESTServicesAdapter();
		}

		@Override
		public Adapter caseRestServiceResource(RestServiceResource object)
		{
			return createRestServiceResourceAdapter();
		}

		@Override
		public Adapter caseRestServiceResourceSecurity(RestServiceResourceSecurity object)
		{
			return createRestServiceResourceSecurityAdapter();
		}

		@Override
		public Adapter caseRetainFamiliarActivities(RetainFamiliarActivities object)
		{
			return createRetainFamiliarActivitiesAdapter();
		}

		@Override
		public Adapter caseRetry(Retry object)
		{
			return createRetryAdapter();
		}

		@Override
		public Adapter caseScriptInformation(ScriptInformation object)
		{
			return createScriptInformationAdapter();
		}

		@Override
		public Adapter caseSeparationOfDutiesActivities(SeparationOfDutiesActivities object)
		{
			return createSeparationOfDutiesActivitiesAdapter();
		}

		@Override
		public Adapter caseSignalData(SignalData object)
		{
			return createSignalDataAdapter();
		}

		@Override
		public Adapter caseStartMethod(StartMethod object)
		{
			return createStartMethodAdapter();
		}

		@Override
		public Adapter caseStructuredDiscriminator(StructuredDiscriminator object)
		{
			return createStructuredDiscriminatorAdapter();
		}

		@Override
		public Adapter caseTaskLibraryReference(TaskLibraryReference object)
		{
			return createTaskLibraryReferenceAdapter();
		}

		@Override
		public Adapter caseTransformScript(TransformScript object)
		{
			return createTransformScriptAdapter();
		}

		@Override
		public Adapter caseUserTaskScripts(UserTaskScripts object)
		{
			return createUserTaskScriptsAdapter();
		}

		@Override
		public Adapter caseValidationControl(ValidationControl object)
		{
			return createValidationControlAdapter();
		}

		@Override
		public Adapter caseValidationIssueOverride(ValidationIssueOverride object)
		{
			return createValidationIssueOverrideAdapter();
		}

		@Override
		public Adapter caseWsdlEventAssociation(WsdlEventAssociation object)
		{
			return createWsdlEventAssociationAdapter();
		}

		@Override
		public Adapter caseWorkItemPriority(WorkItemPriority object)
		{
			return createWorkItemPriorityAdapter();
		}

		@Override
		public Adapter caseWsdlGeneration(WsdlGeneration object)
		{
			return createWsdlGenerationAdapter();
		}

		@Override
		public Adapter caseWsBinding(WsBinding object)
		{
			return createWsBindingAdapter();
		}

		@Override
		public Adapter caseWsInbound(WsInbound object)
		{
			return createWsInboundAdapter();
		}

		@Override
		public Adapter caseWsOutbound(WsOutbound object)
		{
			return createWsOutboundAdapter();
		}

		@Override
		public Adapter caseWsResource(WsResource object)
		{
			return createWsResourceAdapter();
		}

		@Override
		public Adapter caseWsSecurityPolicy(WsSecurityPolicy object)
		{
			return createWsSecurityPolicyAdapter();
		}

		@Override
		public Adapter caseWsSoapBinding(WsSoapBinding object)
		{
			return createWsSoapBindingAdapter();
		}

		@Override
		public Adapter caseWsSoapHttpInboundBinding(WsSoapHttpInboundBinding object)
		{
			return createWsSoapHttpInboundBindingAdapter();
		}

		@Override
		public Adapter caseWsSoapHttpOutboundBinding(WsSoapHttpOutboundBinding object)
		{
			return createWsSoapHttpOutboundBindingAdapter();
		}

		@Override
		public Adapter caseWsSoapJmsInboundBinding(WsSoapJmsInboundBinding object)
		{
			return createWsSoapJmsInboundBindingAdapter();
		}

		@Override
		public Adapter caseWsSoapJmsOutboundBinding(WsSoapJmsOutboundBinding object)
		{
			return createWsSoapJmsOutboundBindingAdapter();
		}

		@Override
		public Adapter caseWsSoapSecurity(WsSoapSecurity object)
		{
			return createWsSoapSecurityAdapter();
		}

		@Override
		public Adapter caseWsVirtualBinding(WsVirtualBinding object)
		{
			return createWsVirtualBindingAdapter();
		}

		@Override
		public Adapter caseXpdExtDataObjectAttributes(XpdExtDataObjectAttributes object)
		{
			return createXpdExtDataObjectAttributesAdapter();
		}

		@Override
		public Adapter caseXpdExtProperty(XpdExtProperty object)
		{
			return createXpdExtPropertyAdapter();
		}

		@Override
		public Adapter caseXpdExtAttribute(XpdExtAttribute object)
		{
			return createXpdExtAttributeAdapter();
		}

		@Override
		public Adapter caseXpdExtAttributes(XpdExtAttributes object)
		{
			return createXpdExtAttributesAdapter();
		}

		@Override
		public Adapter caseUpdateCaseOperationType(UpdateCaseOperationType object)
		{
			return createUpdateCaseOperationTypeAdapter();
		}

		@Override
		public Adapter caseAddLinkAssociationsType(AddLinkAssociationsType object)
		{
			return createAddLinkAssociationsTypeAdapter();
		}

		@Override
		public Adapter caseRemoveLinkAssociationsType(RemoveLinkAssociationsType object)
		{
			return createRemoveLinkAssociationsTypeAdapter();
		}

		@Override
		public Adapter caseRemoveAllLinksByNameType(RemoveAllLinksByNameType object)
		{
			return createRemoveAllLinksByNameTypeAdapter();
		}

		@Override
		public Adapter caseCaseReferenceOperationsType(CaseReferenceOperationsType object)
		{
			return createCaseReferenceOperationsTypeAdapter();
		}

		@Override
		public Adapter caseGlobalDataOperation(GlobalDataOperation object)
		{
			return createGlobalDataOperationAdapter();
		}

		@Override
		public Adapter caseDeleteByCaseIdentifierType(DeleteByCaseIdentifierType object)
		{
			return createDeleteByCaseIdentifierTypeAdapter();
		}

		@Override
		public Adapter caseCompositeIdentifierType(CompositeIdentifierType object)
		{
			return createCompositeIdentifierTypeAdapter();
		}

		@Override
		public Adapter caseDeleteCaseReferenceOperationType(DeleteCaseReferenceOperationType object)
		{
			return createDeleteCaseReferenceOperationTypeAdapter();
		}

		@Override
		public Adapter caseDeleteByCompositeIdentifiersType(DeleteByCompositeIdentifiersType object)
		{
			return createDeleteByCompositeIdentifiersTypeAdapter();
		}

		@Override
		public Adapter caseCreateCaseOperationType(CreateCaseOperationType object)
		{
			return createCreateCaseOperationTypeAdapter();
		}

		@Override
		public Adapter caseCaseAccessOperationsType(CaseAccessOperationsType object)
		{
			return createCaseAccessOperationsTypeAdapter();
		}

		@Override
		public Adapter caseDataWorkItemAttributeMapping(DataWorkItemAttributeMapping object)
		{
			return createDataWorkItemAttributeMappingAdapter();
		}

		@Override
		public Adapter caseProcessDataWorkItemAttributeMappings(ProcessDataWorkItemAttributeMappings object)
		{
			return createProcessDataWorkItemAttributeMappingsAdapter();
		}

		@Override
		public Adapter caseBpmRuntimeConfiguration(BpmRuntimeConfiguration object)
		{
			return createBpmRuntimeConfigurationAdapter();
		}

		@Override
		public Adapter caseEnablementType(EnablementType object)
		{
			return createEnablementTypeAdapter();
		}

		@Override
		public Adapter caseInitializerActivitiesType(InitializerActivitiesType object)
		{
			return createInitializerActivitiesTypeAdapter();
		}

		@Override
		public Adapter caseAdHocTaskConfigurationType(AdHocTaskConfigurationType object)
		{
			return createAdHocTaskConfigurationTypeAdapter();
		}

		@Override
		public Adapter caseRequiredAccessPrivileges(RequiredAccessPrivileges object)
		{
			return createRequiredAccessPrivilegesAdapter();
		}

		@Override
		public Adapter caseVisibleForCaseStates(VisibleForCaseStates object)
		{
			return createVisibleForCaseStatesAdapter();
		}

		@Override
		public Adapter caseCaseService(CaseService object)
		{
			return createCaseServiceAdapter();
		}

		@Override
		public Adapter caseDocumentOperation(DocumentOperation object)
		{
			return createDocumentOperationAdapter();
		}

		@Override
		public Adapter caseCaseDocRefOperations(CaseDocRefOperations object)
		{
			return createCaseDocRefOperationsAdapter();
		}

		@Override
		public Adapter caseCaseDocFindOperations(CaseDocFindOperations object)
		{
			return createCaseDocFindOperationsAdapter();
		}

		@Override
		public Adapter caseMoveCaseDocOperation(MoveCaseDocOperation object)
		{
			return createMoveCaseDocOperationAdapter();
		}

		@Override
		public Adapter caseUnlinkCaseDocOperation(UnlinkCaseDocOperation object)
		{
			return createUnlinkCaseDocOperationAdapter();
		}

		@Override
		public Adapter caseLinkCaseDocOperation(LinkCaseDocOperation object)
		{
			return createLinkCaseDocOperationAdapter();
		}

		@Override
		public Adapter caseLinkSystemDocumentOperation(LinkSystemDocumentOperation object)
		{
			return createLinkSystemDocumentOperationAdapter();
		}

		@Override
		public Adapter caseDeleteCaseDocOperation(DeleteCaseDocOperation object)
		{
			return createDeleteCaseDocOperationAdapter();
		}

		@Override
		public Adapter caseFindByFileNameOperation(FindByFileNameOperation object)
		{
			return createFindByFileNameOperationAdapter();
		}

		@Override
		public Adapter caseFindByQueryOperation(FindByQueryOperation object)
		{
			return createFindByQueryOperationAdapter();
		}

		@Override
		public Adapter caseCaseDocumentQueryExpression(CaseDocumentQueryExpression object)
		{
			return createCaseDocumentQueryExpressionAdapter();
		}

		@Override
		public Adapter caseServiceProcessConfiguration(ServiceProcessConfiguration object)
		{
			return createServiceProcessConfigurationAdapter();
		}

		@Override
		public Adapter caseScriptDataMapper(ScriptDataMapper object)
		{
			return createScriptDataMapperAdapter();
		}

		@Override
		public Adapter caseDataMapperArrayInflation(DataMapperArrayInflation object)
		{
			return createDataMapperArrayInflationAdapter();
		}

		@Override
		public Adapter caseLikeMappingExclusion(LikeMappingExclusion object)
		{
			return createLikeMappingExclusionAdapter();
		}

		@Override
		public Adapter caseLikeMappingExclusions(LikeMappingExclusions object)
		{
			return createLikeMappingExclusionsAdapter();
		}

		@Override
		public Adapter caseRestServiceOperation(RestServiceOperation object)
		{
			return createRestServiceOperationAdapter();
		}

		@Override
		public Adapter caseDescribedElement(DescribedElement object)
		{
			return createDescribedElementAdapter();
		}

		@Override
		public Adapter caseUniqueIdElement(UniqueIdElement object)
		{
			return createUniqueIdElementAdapter();
		}

		@Override
		public Adapter caseOtherAttributesContainer(OtherAttributesContainer object)
		{
			return createOtherAttributesContainerAdapter();
		}

		@Override
		public Adapter caseNamedElement(NamedElement object)
		{
			return createNamedElementAdapter();
		}

		@Override
		public Adapter caseOtherElementsContainer(OtherElementsContainer object)
		{
			return createOtherElementsContainerAdapter();
		}

		@Override
		public Adapter caseMessage(Message object)
		{
			return createMessageAdapter();
		}

		@Override
		public Adapter caseExtendedAttributesContainer(ExtendedAttributesContainer object)
		{
			return createExtendedAttributesContainerAdapter();
		}

		@Override
		public Adapter caseFormalParametersContainer(FormalParametersContainer object)
		{
			return createFormalParametersContainerAdapter();
		}

		@Override
		public Adapter caseExpression(Expression object)
		{
			return createExpressionAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object)
		{
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target)
	{
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.ActivityRef <em>Activity Ref</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.ActivityRef
	 * @generated
	 */
	public Adapter createActivityRefAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.ActivityResourcePatterns <em>Activity Resource Patterns</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.ActivityResourcePatterns
	 * @generated
	 */
	public Adapter createActivityResourcePatternsAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.AllocationStrategy <em>Allocation Strategy</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.AllocationStrategy
	 * @generated
	 */
	public Adapter createAllocationStrategyAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.AssociatedCorrelationFields <em>Associated Correlation Fields</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.AssociatedCorrelationFields
	 * @generated
	 */
	public Adapter createAssociatedCorrelationFieldsAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.AssociatedCorrelationField <em>Associated Correlation Field</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.AssociatedCorrelationField
	 * @generated
	 */
	public Adapter createAssociatedCorrelationFieldAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.AssociatedParameter <em>Associated Parameter</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.AssociatedParameter
	 * @generated
	 */
	public Adapter createAssociatedParameterAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.AssociatedParameters <em>Associated Parameters</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.AssociatedParameters
	 * @generated
	 */
	public Adapter createAssociatedParametersAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.AssociatedParametersContainer <em>Associated Parameters Container</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.AssociatedParametersContainer
	 * @generated
	 */
	public Adapter createAssociatedParametersContainerAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link com.tibco.xpd.xpdExtension.Audit <em>Audit</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.Audit
	 * @generated
	 */
	public Adapter createAuditAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link com.tibco.xpd.xpdExtension.AuditEvent <em>Audit Event</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.AuditEvent
	 * @generated
	 */
	public Adapter createAuditEventAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.BusinessProcess <em>Business Process</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.BusinessProcess
	 * @generated
	 */
	public Adapter createBusinessProcessAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.CalendarReference <em>Calendar Reference</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.CalendarReference
	 * @generated
	 */
	public Adapter createCalendarReferenceAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.CatchErrorMappings <em>Catch Error Mappings</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.CatchErrorMappings
	 * @generated
	 */
	public Adapter createCatchErrorMappingsAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.ConstantPeriod <em>Constant Period</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.ConstantPeriod
	 * @generated
	 */
	public Adapter createConstantPeriodAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.ConditionalParticipant <em>Conditional Participant</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.ConditionalParticipant
	 * @generated
	 */
	public Adapter createConditionalParticipantAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.ReplyImmediateDataMappings <em>Reply Immediate Data Mappings</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.ReplyImmediateDataMappings
	 * @generated
	 */
	public Adapter createReplyImmediateDataMappingsAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.CorrelationDataMappings <em>Correlation Data Mappings</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.CorrelationDataMappings
	 * @generated
	 */
	public Adapter createCorrelationDataMappingsAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.Discriminator <em>Discriminator</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.Discriminator
	 * @generated
	 */
	public Adapter createDiscriminatorAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.DocumentRoot
	 * @generated
	 */
	public Adapter createDocumentRootAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.DurationCalculation <em>Duration Calculation</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.DurationCalculation
	 * @generated
	 */
	public Adapter createDurationCalculationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.DynamicOrganizationMappings <em>Dynamic Organization Mappings</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.DynamicOrganizationMappings
	 * @generated
	 */
	public Adapter createDynamicOrganizationMappingsAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.DynamicOrganizationMapping <em>Dynamic Organization Mapping</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.DynamicOrganizationMapping
	 * @generated
	 */
	public Adapter createDynamicOrganizationMappingAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.DynamicOrgIdentifierRef <em>Dynamic Org Identifier Ref</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.DynamicOrgIdentifierRef
	 * @generated
	 */
	public Adapter createDynamicOrgIdentifierRefAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.ErrorMethod <em>Error Method</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.ErrorMethod
	 * @generated
	 */
	public Adapter createErrorMethodAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.ErrorThrowerInfo <em>Error Thrower Info</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.ErrorThrowerInfo
	 * @generated
	 */
	public Adapter createErrorThrowerInfoAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.EventHandlerInitialisers <em>Event Handler Initialisers</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.EventHandlerInitialisers
	 * @generated
	 */
	public Adapter createEventHandlerInitialisersAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.FaultMessage <em>Fault Message</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.FaultMessage
	 * @generated
	 */
	public Adapter createFaultMessageAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.FormImplementation <em>Form Implementation</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.FormImplementation
	 * @generated
	 */
	public Adapter createFormImplementationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.ImplementedInterface <em>Implemented Interface</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.ImplementedInterface
	 * @generated
	 */
	public Adapter createImplementedInterfaceAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.InitialValues <em>Initial Values</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.InitialValues
	 * @generated
	 */
	public Adapter createInitialValuesAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.InitialParameterValue <em>Initial Parameter Value</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.InitialParameterValue
	 * @generated
	 */
	public Adapter createInitialParameterValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.InterfaceMethod <em>Interface Method</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.InterfaceMethod
	 * @generated
	 */
	public Adapter createInterfaceMethodAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.IntermediateMethod <em>Intermediate Method</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.IntermediateMethod
	 * @generated
	 */
	public Adapter createIntermediateMethodAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.MultiInstanceScripts <em>Multi Instance Scripts</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.MultiInstanceScripts
	 * @generated
	 */
	public Adapter createMultiInstanceScriptsAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.NamespacePrefixMap <em>Namespace Prefix Map</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.NamespacePrefixMap
	 * @generated
	 */
	public Adapter createNamespacePrefixMapAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.NamespaceMapEntry <em>Namespace Map Entry</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.NamespaceMapEntry
	 * @generated
	 */
	public Adapter createNamespaceMapEntryAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link com.tibco.xpd.xpdExtension.PilingInfo <em>Piling Info</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.PilingInfo
	 * @generated
	 */
	public Adapter createPilingInfoAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.PortTypeOperation <em>Port Type Operation</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.PortTypeOperation
	 * @generated
	 */
	public Adapter createPortTypeOperationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.ProcessInterface <em>Process Interface</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.ProcessInterface
	 * @generated
	 */
	public Adapter createProcessInterfaceAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.ProcessInterfaces <em>Process Interfaces</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.ProcessInterfaces
	 * @generated
	 */
	public Adapter createProcessInterfacesAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.ProcessResourcePatterns <em>Process Resource Patterns</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.ProcessResourcePatterns
	 * @generated
	 */
	public Adapter createProcessResourcePatternsAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.RescheduleTimerScript <em>Reschedule Timer Script</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.RescheduleTimerScript
	 * @generated
	 */
	public Adapter createRescheduleTimerScriptAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.RescheduleTimers <em>Reschedule Timers</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.RescheduleTimers
	 * @generated
	 */
	public Adapter createRescheduleTimersAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.RetainFamiliarActivities <em>Retain Familiar Activities</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.RetainFamiliarActivities
	 * @generated
	 */
	public Adapter createRetainFamiliarActivitiesAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link com.tibco.xpd.xpdExtension.Retry <em>Retry</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.Retry
	 * @generated
	 */
	public Adapter createRetryAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.ScriptInformation <em>Script Information</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.ScriptInformation
	 * @generated
	 */
	public Adapter createScriptInformationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities <em>Separation Of Duties Activities</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities
	 * @generated
	 */
	public Adapter createSeparationOfDutiesActivitiesAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link com.tibco.xpd.xpdExtension.SignalData <em>Signal Data</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.SignalData
	 * @generated
	 */
	public Adapter createSignalDataAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.StartMethod <em>Start Method</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.StartMethod
	 * @generated
	 */
	public Adapter createStartMethodAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.StructuredDiscriminator <em>Structured Discriminator</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.StructuredDiscriminator
	 * @generated
	 */
	public Adapter createStructuredDiscriminatorAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.TaskLibraryReference <em>Task Library Reference</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.TaskLibraryReference
	 * @generated
	 */
	public Adapter createTaskLibraryReferenceAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.TransformScript <em>Transform Script</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.TransformScript
	 * @generated
	 */
	public Adapter createTransformScriptAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.UserTaskScripts <em>User Task Scripts</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.UserTaskScripts
	 * @generated
	 */
	public Adapter createUserTaskScriptsAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.ValidationControl <em>Validation Control</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.ValidationControl
	 * @generated
	 */
	public Adapter createValidationControlAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.ValidationIssueOverride <em>Validation Issue Override</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.ValidationIssueOverride
	 * @generated
	 */
	public Adapter createValidationIssueOverrideAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.WsdlEventAssociation <em>Wsdl Event Association</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.WsdlEventAssociation
	 * @generated
	 */
	public Adapter createWsdlEventAssociationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.WorkItemPriority <em>Work Item Priority</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.WorkItemPriority
	 * @generated
	 */
	public Adapter createWorkItemPriorityAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.XpdExtDataObjectAttributes <em>Xpd Ext Data Object Attributes</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.XpdExtDataObjectAttributes
	 * @generated
	 */
	public Adapter createXpdExtDataObjectAttributesAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.XpdExtProperty <em>Xpd Ext Property</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.XpdExtProperty
	 * @generated
	 */
	public Adapter createXpdExtPropertyAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.XpdExtAttribute <em>Xpd Ext Attribute</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.XpdExtAttribute
	 * @generated
	 */
	public Adapter createXpdExtAttributeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.XpdExtAttributes <em>Xpd Ext Attributes</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.XpdExtAttributes
	 * @generated
	 */
	public Adapter createXpdExtAttributesAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.UpdateCaseOperationType <em>Update Case Operation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.UpdateCaseOperationType
	 * @generated
	 */
	public Adapter createUpdateCaseOperationTypeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.AddLinkAssociationsType <em>Add Link Associations Type</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.AddLinkAssociationsType
	 * @generated
	 */
	public Adapter createAddLinkAssociationsTypeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.RemoveLinkAssociationsType <em>Remove Link Associations Type</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.RemoveLinkAssociationsType
	 * @generated
	 */
	public Adapter createRemoveLinkAssociationsTypeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.RemoveAllLinksByNameType <em>Remove All Links By Name Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.RemoveAllLinksByNameType
	 * @generated
	 */
	public Adapter createRemoveAllLinksByNameTypeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.CaseReferenceOperationsType <em>Case Reference Operations Type</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.CaseReferenceOperationsType
	 * @generated
	 */
	public Adapter createCaseReferenceOperationsTypeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.GlobalDataOperation <em>Global Data Operation</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.GlobalDataOperation
	 * @generated
	 */
	public Adapter createGlobalDataOperationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.DeleteByCaseIdentifierType <em>Delete By Case Identifier Type</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.DeleteByCaseIdentifierType
	 * @generated
	 */
	public Adapter createDeleteByCaseIdentifierTypeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.CompositeIdentifierType <em>Composite Identifier Type</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.CompositeIdentifierType
	 * @generated
	 */
	public Adapter createCompositeIdentifierTypeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.DeleteCaseReferenceOperationType <em>Delete Case Reference Operation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.DeleteCaseReferenceOperationType
	 * @generated
	 */
	public Adapter createDeleteCaseReferenceOperationTypeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.DeleteByCompositeIdentifiersType <em>Delete By Composite Identifiers Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the
	 * cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.DeleteByCompositeIdentifiersType
	 * @generated
	 */
	public Adapter createDeleteByCompositeIdentifiersTypeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.CreateCaseOperationType <em>Create Case Operation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.CreateCaseOperationType
	 * @generated
	 */
	public Adapter createCreateCaseOperationTypeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.CaseAccessOperationsType <em>Case Access Operations Type</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.CaseAccessOperationsType
	 * @generated
	 */
	public Adapter createCaseAccessOperationsTypeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.DataWorkItemAttributeMapping <em>Data Work Item Attribute Mapping</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.DataWorkItemAttributeMapping
	 * @generated
	 */
	public Adapter createDataWorkItemAttributeMappingAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.ProcessDataWorkItemAttributeMappings <em>Process Data Work Item Attribute Mappings</em>}'.
	 * <!-- begin-user-doc
	 * --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the
	 * cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.ProcessDataWorkItemAttributeMappings
	 * @generated
	 */
	public Adapter createProcessDataWorkItemAttributeMappingsAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.BpmRuntimeConfiguration <em>Bpm Runtime Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.BpmRuntimeConfiguration
	 * @generated
	 */
	public Adapter createBpmRuntimeConfigurationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.EnablementType <em>Enablement Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.EnablementType
	 * @generated
	 */
	public Adapter createEnablementTypeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.InitializerActivitiesType <em>Initializer Activities Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.InitializerActivitiesType
	 * @generated
	 */
	public Adapter createInitializerActivitiesTypeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType <em>Ad Hoc Task Configuration Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType
	 * @generated
	 */
	public Adapter createAdHocTaskConfigurationTypeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.RequiredAccessPrivileges <em>Required Access Privileges</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.RequiredAccessPrivileges
	 * @generated
	 */
	public Adapter createRequiredAccessPrivilegesAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.VisibleForCaseStates <em>Visible For Case States</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.VisibleForCaseStates
	 * @generated
	 */
	public Adapter createVisibleForCaseStatesAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.CaseService <em>Case Service</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.CaseService
	 * @generated
	 */
	public Adapter createCaseServiceAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.DocumentOperation <em>Document Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.DocumentOperation
	 * @generated
	 */
	public Adapter createDocumentOperationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.CaseDocRefOperations <em>Case Doc Ref Operations</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.CaseDocRefOperations
	 * @generated
	 */
	public Adapter createCaseDocRefOperationsAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.CaseDocFindOperations <em>Case Doc Find Operations</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.CaseDocFindOperations
	 * @generated
	 */
	public Adapter createCaseDocFindOperationsAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.MoveCaseDocOperation <em>Move Case Doc Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.MoveCaseDocOperation
	 * @generated
	 */
	public Adapter createMoveCaseDocOperationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.UnlinkCaseDocOperation <em>Unlink Case Doc Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.UnlinkCaseDocOperation
	 * @generated
	 */
	public Adapter createUnlinkCaseDocOperationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.LinkCaseDocOperation <em>Link Case Doc Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.LinkCaseDocOperation
	 * @generated
	 */
	public Adapter createLinkCaseDocOperationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.LinkSystemDocumentOperation <em>Link System Document Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.LinkSystemDocumentOperation
	 * @generated
	 */
	public Adapter createLinkSystemDocumentOperationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.DeleteCaseDocOperation <em>Delete Case Doc Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.DeleteCaseDocOperation
	 * @generated
	 */
	public Adapter createDeleteCaseDocOperationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.FindByFileNameOperation <em>Find By File Name Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.FindByFileNameOperation
	 * @generated
	 */
	public Adapter createFindByFileNameOperationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.FindByQueryOperation <em>Find By Query Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.FindByQueryOperation
	 * @generated
	 */
	public Adapter createFindByQueryOperationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression <em>Case Document Query Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression
	 * @generated
	 */
	public Adapter createCaseDocumentQueryExpressionAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.ServiceProcessConfiguration <em>Service Process Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.ServiceProcessConfiguration
	 * @generated
	 */
	public Adapter createServiceProcessConfigurationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.ScriptDataMapper <em>Script Data Mapper</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.ScriptDataMapper
	 * @generated
	 */
	public Adapter createScriptDataMapperAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.DataMapperArrayInflation <em>Data Mapper Array Inflation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.DataMapperArrayInflation
	 * @generated
	 */
	public Adapter createDataMapperArrayInflationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.LikeMappingExclusions <em>Like Mapping Exclusions</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.LikeMappingExclusions
	 * @generated
	 */
	public Adapter createLikeMappingExclusionsAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.RestServiceOperation <em>Rest Service Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.RestServiceOperation
	 * @generated
	 */
	public Adapter createRestServiceOperationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.LikeMappingExclusion <em>Like Mapping Exclusion</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.LikeMappingExclusion
	 * @generated
	 */
	public Adapter createLikeMappingExclusionAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.RESTServices <em>REST Services</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.RESTServices
	 * @generated
	 */
	public Adapter createRESTServicesAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.RestServiceResource <em>Rest Service Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.RestServiceResource
	 * @generated
	 */
	public Adapter createRestServiceResourceAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.RestServiceResourceSecurity <em>Rest Service Resource Security</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.RestServiceResourceSecurity
	 * @generated
	 */
	public Adapter createRestServiceResourceSecurityAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.WsdlGeneration <em>Wsdl Generation</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.WsdlGeneration
	 * @generated
	 */
	public Adapter createWsdlGenerationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.EmailResource <em>Email Resource</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.EmailResource
	 * @generated
	 */
	public Adapter createEmailResourceAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.JdbcResource <em>Jdbc Resource</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.JdbcResource
	 * @generated
	 */
	public Adapter createJdbcResourceAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.ParticipantSharedResource <em>Participant Shared Resource</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.ParticipantSharedResource
	 * @generated
	 */
	public Adapter createParticipantSharedResourceAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link com.tibco.xpd.xpdExtension.WsBinding <em>Ws Binding</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.WsBinding
	 * @generated
	 */
	public Adapter createWsBindingAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link com.tibco.xpd.xpdExtension.WsInbound <em>Ws Inbound</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.WsInbound
	 * @generated
	 */
	public Adapter createWsInboundAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link com.tibco.xpd.xpdExtension.WsOutbound <em>Ws Outbound</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.WsOutbound
	 * @generated
	 */
	public Adapter createWsOutboundAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link com.tibco.xpd.xpdExtension.WsResource <em>Ws Resource</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.WsResource
	 * @generated
	 */
	public Adapter createWsResourceAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.WsSecurityPolicy <em>Ws Security Policy</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.WsSecurityPolicy
	 * @generated
	 */
	public Adapter createWsSecurityPolicyAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.WsSoapBinding <em>Ws Soap Binding</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.WsSoapBinding
	 * @generated
	 */
	public Adapter createWsSoapBindingAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding <em>Ws Soap Http Inbound Binding</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding
	 * @generated
	 */
	public Adapter createWsSoapHttpInboundBindingAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.WsSoapHttpOutboundBinding <em>Ws Soap Http Outbound Binding</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.WsSoapHttpOutboundBinding
	 * @generated
	 */
	public Adapter createWsSoapHttpOutboundBindingAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding <em>Ws Soap Jms Inbound Binding</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding
	 * @generated
	 */
	public Adapter createWsSoapJmsInboundBindingAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding <em>Ws Soap Jms Outbound Binding</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding
	 * @generated
	 */
	public Adapter createWsSoapJmsOutboundBindingAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.WsSoapSecurity <em>Ws Soap Security</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.WsSoapSecurity
	 * @generated
	 */
	public Adapter createWsSoapSecurityAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdExtension.WsVirtualBinding <em>Ws Virtual Binding</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdExtension.WsVirtualBinding
	 * @generated
	 */
	public Adapter createWsVirtualBindingAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.DescribedElement <em>Described Element</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdl2.DescribedElement
	 * @generated
	 */
	public Adapter createDescribedElementAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.UniqueIdElement <em>Unique Id Element</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdl2.UniqueIdElement
	 * @generated
	 */
	public Adapter createUniqueIdElementAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.OtherAttributesContainer <em>Other Attributes Container</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdl2.OtherAttributesContainer
	 * @generated
	 */
	public Adapter createOtherAttributesContainerAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link com.tibco.xpd.xpdl2.NamedElement <em>Named Element</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdl2.NamedElement
	 * @generated
	 */
	public Adapter createNamedElementAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.OtherElementsContainer <em>Other Elements Container</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdl2.OtherElementsContainer
	 * @generated
	 */
	public Adapter createOtherElementsContainerAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link com.tibco.xpd.xpdl2.Message <em>Message</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdl2.Message
	 * @generated
	 */
	public Adapter createMessageAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.ExtendedAttributesContainer <em>Extended Attributes Container</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdl2.ExtendedAttributesContainer
	 * @generated
	 */
	public Adapter createExtendedAttributesContainerAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.xpd.xpdl2.FormalParametersContainer <em>Formal Parameters Container</em>}'.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdl2.FormalParametersContainer
	 * @generated
	 */
	public Adapter createFormalParametersContainerAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link com.tibco.xpd.xpdl2.Expression <em>Expression</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.tibco.xpd.xpdl2.Expression
	 * @generated
	 */
	public Adapter createExpressionAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter()
	{
		return null;
	}

} // XpdExtensionAdapterFactory
