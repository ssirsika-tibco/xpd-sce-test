/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.util;

import com.tibco.xpd.xpdExtension.*;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.AllocationStrategy;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.AssociatedParametersContainer;
import com.tibco.xpd.xpdExtension.Audit;
import com.tibco.xpd.xpdExtension.AuditEvent;
import com.tibco.xpd.xpdExtension.ConstantPeriod;
import com.tibco.xpd.xpdExtension.Discriminator;
import com.tibco.xpd.xpdExtension.DocumentRoot;
import com.tibco.xpd.xpdExtension.DurationCalculation;
import com.tibco.xpd.xpdExtension.ImplementedInterface;
import com.tibco.xpd.xpdExtension.InitialParameterValue;
import com.tibco.xpd.xpdExtension.InitialValues;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.MultiInstanceScripts;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.ProcessResourcePatterns;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities;
import com.tibco.xpd.xpdExtension.StandardLoopScript;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.StructuredDiscriminator;
import com.tibco.xpd.xpdExtension.UserTaskScripts;
import com.tibco.xpd.xpdExtension.WsdlEventAssociation;
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
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage
 * @generated
 */
public class XpdExtensionSwitch<T> {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached model package
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static XpdExtensionPackage modelPackage;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XpdExtensionSwitch() {
        if (modelPackage == null) {
            modelPackage = XpdExtensionPackage.eINSTANCE;
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    public T doSwitch(EObject theEObject) {
        return doSwitch(theEObject.eClass(), theEObject);
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(EClass theEClass, EObject theEObject) {
        if (theEClass.eContainer() == modelPackage) {
            return doSwitch(theEClass.getClassifierID(), theEObject);
        } else {
            List<EClass> eSuperTypes = theEClass.getESuperTypes();
            return eSuperTypes.isEmpty() ? defaultCase(theEObject) : doSwitch(eSuperTypes.get(0), theEObject);
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
        case XpdExtensionPackage.ACTIVITY_REF: {
            ActivityRef activityRef = (ActivityRef) theEObject;
            T result = caseActivityRef(activityRef);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS: {
            ActivityResourcePatterns activityResourcePatterns = (ActivityResourcePatterns) theEObject;
            T result = caseActivityResourcePatterns(activityResourcePatterns);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.ALLOCATION_STRATEGY: {
            AllocationStrategy allocationStrategy = (AllocationStrategy) theEObject;
            T result = caseAllocationStrategy(allocationStrategy);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELDS: {
            AssociatedCorrelationFields associatedCorrelationFields = (AssociatedCorrelationFields) theEObject;
            T result = caseAssociatedCorrelationFields(associatedCorrelationFields);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELD: {
            AssociatedCorrelationField associatedCorrelationField = (AssociatedCorrelationField) theEObject;
            T result = caseAssociatedCorrelationField(associatedCorrelationField);
            if (result == null)
                result = caseDescribedElement(associatedCorrelationField);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.ASSOCIATED_PARAMETER: {
            AssociatedParameter associatedParameter = (AssociatedParameter) theEObject;
            T result = caseAssociatedParameter(associatedParameter);
            if (result == null)
                result = caseDescribedElement(associatedParameter);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.ASSOCIATED_PARAMETERS: {
            AssociatedParameters associatedParameters = (AssociatedParameters) theEObject;
            T result = caseAssociatedParameters(associatedParameters);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.ASSOCIATED_PARAMETERS_CONTAINER: {
            AssociatedParametersContainer associatedParametersContainer = (AssociatedParametersContainer) theEObject;
            T result = caseAssociatedParametersContainer(associatedParametersContainer);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.AUDIT: {
            Audit audit = (Audit) theEObject;
            T result = caseAudit(audit);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.AUDIT_EVENT: {
            AuditEvent auditEvent = (AuditEvent) theEObject;
            T result = caseAuditEvent(auditEvent);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.BUSINESS_PROCESS: {
            BusinessProcess businessProcess = (BusinessProcess) theEObject;
            T result = caseBusinessProcess(businessProcess);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.CALENDAR_REFERENCE: {
            CalendarReference calendarReference = (CalendarReference) theEObject;
            T result = caseCalendarReference(calendarReference);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.CATCH_ERROR_MAPPINGS: {
            CatchErrorMappings catchErrorMappings = (CatchErrorMappings) theEObject;
            T result = caseCatchErrorMappings(catchErrorMappings);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.CONSTANT_PERIOD: {
            ConstantPeriod constantPeriod = (ConstantPeriod) theEObject;
            T result = caseConstantPeriod(constantPeriod);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.CONDITIONAL_PARTICIPANT: {
            ConditionalParticipant conditionalParticipant = (ConditionalParticipant) theEObject;
            T result = caseConditionalParticipant(conditionalParticipant);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.REPLY_IMMEDIATE_DATA_MAPPINGS: {
            ReplyImmediateDataMappings replyImmediateDataMappings = (ReplyImmediateDataMappings) theEObject;
            T result = caseReplyImmediateDataMappings(replyImmediateDataMappings);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.CORRELATION_DATA_MAPPINGS: {
            CorrelationDataMappings correlationDataMappings = (CorrelationDataMappings) theEObject;
            T result = caseCorrelationDataMappings(correlationDataMappings);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.DISCRIMINATOR: {
            Discriminator discriminator = (Discriminator) theEObject;
            T result = caseDiscriminator(discriminator);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.DOCUMENT_ROOT: {
            DocumentRoot documentRoot = (DocumentRoot) theEObject;
            T result = caseDocumentRoot(documentRoot);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.DURATION_CALCULATION: {
            DurationCalculation durationCalculation = (DurationCalculation) theEObject;
            T result = caseDurationCalculation(durationCalculation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.DYNAMIC_ORGANIZATION_MAPPINGS: {
            DynamicOrganizationMappings dynamicOrganizationMappings = (DynamicOrganizationMappings) theEObject;
            T result = caseDynamicOrganizationMappings(dynamicOrganizationMappings);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.DYNAMIC_ORGANIZATION_MAPPING: {
            DynamicOrganizationMapping dynamicOrganizationMapping = (DynamicOrganizationMapping) theEObject;
            T result = caseDynamicOrganizationMapping(dynamicOrganizationMapping);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.DYNAMIC_ORG_IDENTIFIER_REF: {
            DynamicOrgIdentifierRef dynamicOrgIdentifierRef = (DynamicOrgIdentifierRef) theEObject;
            T result = caseDynamicOrgIdentifierRef(dynamicOrgIdentifierRef);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.EMAIL_RESOURCE: {
            EmailResource emailResource = (EmailResource) theEObject;
            T result = caseEmailResource(emailResource);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.ERROR_METHOD: {
            ErrorMethod errorMethod = (ErrorMethod) theEObject;
            T result = caseErrorMethod(errorMethod);
            if (result == null)
                result = caseUniqueIdElement(errorMethod);
            if (result == null)
                result = caseAssociatedParametersContainer(errorMethod);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.ERROR_THROWER_INFO: {
            ErrorThrowerInfo errorThrowerInfo = (ErrorThrowerInfo) theEObject;
            T result = caseErrorThrowerInfo(errorThrowerInfo);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.EVENT_HANDLER_INITIALISERS: {
            EventHandlerInitialisers eventHandlerInitialisers = (EventHandlerInitialisers) theEObject;
            T result = caseEventHandlerInitialisers(eventHandlerInitialisers);
            if (result == null)
                result = caseNamedElement(eventHandlerInitialisers);
            if (result == null)
                result = caseUniqueIdElement(eventHandlerInitialisers);
            if (result == null)
                result = caseOtherAttributesContainer(eventHandlerInitialisers);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.FAULT_MESSAGE: {
            FaultMessage faultMessage = (FaultMessage) theEObject;
            T result = caseFaultMessage(faultMessage);
            if (result == null)
                result = caseMessage(faultMessage);
            if (result == null)
                result = caseNamedElement(faultMessage);
            if (result == null)
                result = caseOtherElementsContainer(faultMessage);
            if (result == null)
                result = caseUniqueIdElement(faultMessage);
            if (result == null)
                result = caseOtherAttributesContainer(faultMessage);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.FORM_IMPLEMENTATION: {
            FormImplementation formImplementation = (FormImplementation) theEObject;
            T result = caseFormImplementation(formImplementation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.IMPLEMENTED_INTERFACE: {
            ImplementedInterface implementedInterface = (ImplementedInterface) theEObject;
            T result = caseImplementedInterface(implementedInterface);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.INITIAL_VALUES: {
            InitialValues initialValues = (InitialValues) theEObject;
            T result = caseInitialValues(initialValues);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.INITIAL_PARAMETER_VALUE: {
            InitialParameterValue initialParameterValue = (InitialParameterValue) theEObject;
            T result = caseInitialParameterValue(initialParameterValue);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.INTERFACE_METHOD: {
            InterfaceMethod interfaceMethod = (InterfaceMethod) theEObject;
            T result = caseInterfaceMethod(interfaceMethod);
            if (result == null)
                result = caseNamedElement(interfaceMethod);
            if (result == null)
                result = caseDescribedElement(interfaceMethod);
            if (result == null)
                result = caseAssociatedParametersContainer(interfaceMethod);
            if (result == null)
                result = caseUniqueIdElement(interfaceMethod);
            if (result == null)
                result = caseOtherAttributesContainer(interfaceMethod);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.INTERMEDIATE_METHOD: {
            IntermediateMethod intermediateMethod = (IntermediateMethod) theEObject;
            T result = caseIntermediateMethod(intermediateMethod);
            if (result == null)
                result = caseInterfaceMethod(intermediateMethod);
            if (result == null)
                result = caseNamedElement(intermediateMethod);
            if (result == null)
                result = caseDescribedElement(intermediateMethod);
            if (result == null)
                result = caseAssociatedParametersContainer(intermediateMethod);
            if (result == null)
                result = caseUniqueIdElement(intermediateMethod);
            if (result == null)
                result = caseOtherAttributesContainer(intermediateMethod);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.JDBC_RESOURCE: {
            JdbcResource jdbcResource = (JdbcResource) theEObject;
            T result = caseJdbcResource(jdbcResource);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.MULTI_INSTANCE_SCRIPTS: {
            MultiInstanceScripts multiInstanceScripts = (MultiInstanceScripts) theEObject;
            T result = caseMultiInstanceScripts(multiInstanceScripts);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.NAMESPACE_PREFIX_MAP: {
            NamespacePrefixMap namespacePrefixMap = (NamespacePrefixMap) theEObject;
            T result = caseNamespacePrefixMap(namespacePrefixMap);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.NAMESPACE_MAP_ENTRY: {
            NamespaceMapEntry namespaceMapEntry = (NamespaceMapEntry) theEObject;
            T result = caseNamespaceMapEntry(namespaceMapEntry);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE: {
            ParticipantSharedResource participantSharedResource = (ParticipantSharedResource) theEObject;
            T result = caseParticipantSharedResource(participantSharedResource);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.PILING_INFO: {
            PilingInfo pilingInfo = (PilingInfo) theEObject;
            T result = casePilingInfo(pilingInfo);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.PORT_TYPE_OPERATION: {
            PortTypeOperation portTypeOperation = (PortTypeOperation) theEObject;
            T result = casePortTypeOperation(portTypeOperation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.PROCESS_INTERFACE: {
            ProcessInterface processInterface = (ProcessInterface) theEObject;
            T result = caseProcessInterface(processInterface);
            if (result == null)
                result = caseNamedElement(processInterface);
            if (result == null)
                result = caseDescribedElement(processInterface);
            if (result == null)
                result = caseExtendedAttributesContainer(processInterface);
            if (result == null)
                result = caseFormalParametersContainer(processInterface);
            if (result == null)
                result = caseOtherElementsContainer(processInterface);
            if (result == null)
                result = caseUniqueIdElement(processInterface);
            if (result == null)
                result = caseOtherAttributesContainer(processInterface);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.PROCESS_INTERFACES: {
            ProcessInterfaces processInterfaces = (ProcessInterfaces) theEObject;
            T result = caseProcessInterfaces(processInterfaces);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.PROCESS_RESOURCE_PATTERNS: {
            ProcessResourcePatterns processResourcePatterns = (ProcessResourcePatterns) theEObject;
            T result = caseProcessResourcePatterns(processResourcePatterns);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.RESCHEDULE_TIMER_SCRIPT: {
            RescheduleTimerScript rescheduleTimerScript = (RescheduleTimerScript) theEObject;
            T result = caseRescheduleTimerScript(rescheduleTimerScript);
            if (result == null)
                result = caseExpression(rescheduleTimerScript);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.RESCHEDULE_TIMERS: {
            RescheduleTimers rescheduleTimers = (RescheduleTimers) theEObject;
            T result = caseRescheduleTimers(rescheduleTimers);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.REST_SERVICES: {
            RESTServices restServices = (RESTServices) theEObject;
            T result = caseRESTServices(restServices);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.REST_SERVICE_RESOURCE: {
            RestServiceResource restServiceResource = (RestServiceResource) theEObject;
            T result = caseRestServiceResource(restServiceResource);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.REST_SERVICE_RESOURCE_SECURITY: {
            RestServiceResourceSecurity restServiceResourceSecurity = (RestServiceResourceSecurity) theEObject;
            T result = caseRestServiceResourceSecurity(restServiceResourceSecurity);
            if (result == null)
                result = caseExtendedAttributesContainer(restServiceResourceSecurity);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.RETAIN_FAMILIAR_ACTIVITIES: {
            RetainFamiliarActivities retainFamiliarActivities = (RetainFamiliarActivities) theEObject;
            T result = caseRetainFamiliarActivities(retainFamiliarActivities);
            if (result == null)
                result = caseNamedElement(retainFamiliarActivities);
            if (result == null)
                result = caseUniqueIdElement(retainFamiliarActivities);
            if (result == null)
                result = caseOtherAttributesContainer(retainFamiliarActivities);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.RETRY: {
            Retry retry = (Retry) theEObject;
            T result = caseRetry(retry);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.SCRIPT_INFORMATION: {
            ScriptInformation scriptInformation = (ScriptInformation) theEObject;
            T result = caseScriptInformation(scriptInformation);
            if (result == null)
                result = caseNamedElement(scriptInformation);
            if (result == null)
                result = caseUniqueIdElement(scriptInformation);
            if (result == null)
                result = caseOtherAttributesContainer(scriptInformation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.SEPARATION_OF_DUTIES_ACTIVITIES: {
            SeparationOfDutiesActivities separationOfDutiesActivities = (SeparationOfDutiesActivities) theEObject;
            T result = caseSeparationOfDutiesActivities(separationOfDutiesActivities);
            if (result == null)
                result = caseNamedElement(separationOfDutiesActivities);
            if (result == null)
                result = caseUniqueIdElement(separationOfDutiesActivities);
            if (result == null)
                result = caseOtherAttributesContainer(separationOfDutiesActivities);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.SIGNAL_DATA: {
            SignalData signalData = (SignalData) theEObject;
            T result = caseSignalData(signalData);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.START_METHOD: {
            StartMethod startMethod = (StartMethod) theEObject;
            T result = caseStartMethod(startMethod);
            if (result == null)
                result = caseInterfaceMethod(startMethod);
            if (result == null)
                result = caseNamedElement(startMethod);
            if (result == null)
                result = caseDescribedElement(startMethod);
            if (result == null)
                result = caseAssociatedParametersContainer(startMethod);
            if (result == null)
                result = caseUniqueIdElement(startMethod);
            if (result == null)
                result = caseOtherAttributesContainer(startMethod);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.STRUCTURED_DISCRIMINATOR: {
            StructuredDiscriminator structuredDiscriminator = (StructuredDiscriminator) theEObject;
            T result = caseStructuredDiscriminator(structuredDiscriminator);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.TASK_LIBRARY_REFERENCE: {
            TaskLibraryReference taskLibraryReference = (TaskLibraryReference) theEObject;
            T result = caseTaskLibraryReference(taskLibraryReference);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.TRANSFORM_SCRIPT: {
            TransformScript transformScript = (TransformScript) theEObject;
            T result = caseTransformScript(transformScript);
            if (result == null)
                result = caseExtendedAttributesContainer(transformScript);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.USER_TASK_SCRIPTS: {
            UserTaskScripts userTaskScripts = (UserTaskScripts) theEObject;
            T result = caseUserTaskScripts(userTaskScripts);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.VALIDATION_CONTROL: {
            ValidationControl validationControl = (ValidationControl) theEObject;
            T result = caseValidationControl(validationControl);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.VALIDATION_ISSUE_OVERRIDE: {
            ValidationIssueOverride validationIssueOverride = (ValidationIssueOverride) theEObject;
            T result = caseValidationIssueOverride(validationIssueOverride);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.WSDL_EVENT_ASSOCIATION: {
            WsdlEventAssociation wsdlEventAssociation = (WsdlEventAssociation) theEObject;
            T result = caseWsdlEventAssociation(wsdlEventAssociation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.WORK_ITEM_PRIORITY: {
            WorkItemPriority workItemPriority = (WorkItemPriority) theEObject;
            T result = caseWorkItemPriority(workItemPriority);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.WSDL_GENERATION: {
            WsdlGeneration wsdlGeneration = (WsdlGeneration) theEObject;
            T result = caseWsdlGeneration(wsdlGeneration);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.WS_BINDING: {
            WsBinding wsBinding = (WsBinding) theEObject;
            T result = caseWsBinding(wsBinding);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.WS_INBOUND: {
            WsInbound wsInbound = (WsInbound) theEObject;
            T result = caseWsInbound(wsInbound);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.WS_OUTBOUND: {
            WsOutbound wsOutbound = (WsOutbound) theEObject;
            T result = caseWsOutbound(wsOutbound);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.WS_RESOURCE: {
            WsResource wsResource = (WsResource) theEObject;
            T result = caseWsResource(wsResource);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.WS_SECURITY_POLICY: {
            WsSecurityPolicy wsSecurityPolicy = (WsSecurityPolicy) theEObject;
            T result = caseWsSecurityPolicy(wsSecurityPolicy);
            if (result == null)
                result = caseExtendedAttributesContainer(wsSecurityPolicy);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.WS_SOAP_BINDING: {
            WsSoapBinding wsSoapBinding = (WsSoapBinding) theEObject;
            T result = caseWsSoapBinding(wsSoapBinding);
            if (result == null)
                result = caseWsBinding(wsSoapBinding);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.WS_SOAP_HTTP_INBOUND_BINDING: {
            WsSoapHttpInboundBinding wsSoapHttpInboundBinding = (WsSoapHttpInboundBinding) theEObject;
            T result = caseWsSoapHttpInboundBinding(wsSoapHttpInboundBinding);
            if (result == null)
                result = caseWsSoapBinding(wsSoapHttpInboundBinding);
            if (result == null)
                result = caseWsBinding(wsSoapHttpInboundBinding);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.WS_SOAP_HTTP_OUTBOUND_BINDING: {
            WsSoapHttpOutboundBinding wsSoapHttpOutboundBinding = (WsSoapHttpOutboundBinding) theEObject;
            T result = caseWsSoapHttpOutboundBinding(wsSoapHttpOutboundBinding);
            if (result == null)
                result = caseWsSoapBinding(wsSoapHttpOutboundBinding);
            if (result == null)
                result = caseWsBinding(wsSoapHttpOutboundBinding);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING: {
            WsSoapJmsInboundBinding wsSoapJmsInboundBinding = (WsSoapJmsInboundBinding) theEObject;
            T result = caseWsSoapJmsInboundBinding(wsSoapJmsInboundBinding);
            if (result == null)
                result = caseWsSoapBinding(wsSoapJmsInboundBinding);
            if (result == null)
                result = caseWsBinding(wsSoapJmsInboundBinding);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING: {
            WsSoapJmsOutboundBinding wsSoapJmsOutboundBinding = (WsSoapJmsOutboundBinding) theEObject;
            T result = caseWsSoapJmsOutboundBinding(wsSoapJmsOutboundBinding);
            if (result == null)
                result = caseWsSoapBinding(wsSoapJmsOutboundBinding);
            if (result == null)
                result = caseWsBinding(wsSoapJmsOutboundBinding);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.WS_SOAP_SECURITY: {
            WsSoapSecurity wsSoapSecurity = (WsSoapSecurity) theEObject;
            T result = caseWsSoapSecurity(wsSoapSecurity);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.WS_VIRTUAL_BINDING: {
            WsVirtualBinding wsVirtualBinding = (WsVirtualBinding) theEObject;
            T result = caseWsVirtualBinding(wsVirtualBinding);
            if (result == null)
                result = caseWsBinding(wsVirtualBinding);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.XPD_EXT_DATA_OBJECT_ATTRIBUTES: {
            XpdExtDataObjectAttributes xpdExtDataObjectAttributes = (XpdExtDataObjectAttributes) theEObject;
            T result = caseXpdExtDataObjectAttributes(xpdExtDataObjectAttributes);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.XPD_EXT_PROPERTY: {
            XpdExtProperty xpdExtProperty = (XpdExtProperty) theEObject;
            T result = caseXpdExtProperty(xpdExtProperty);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.XPD_EXT_ATTRIBUTE: {
            XpdExtAttribute xpdExtAttribute = (XpdExtAttribute) theEObject;
            T result = caseXpdExtAttribute(xpdExtAttribute);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.XPD_EXT_ATTRIBUTES: {
            XpdExtAttributes xpdExtAttributes = (XpdExtAttributes) theEObject;
            T result = caseXpdExtAttributes(xpdExtAttributes);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.UPDATE_CASE_OPERATION_TYPE: {
            UpdateCaseOperationType updateCaseOperationType = (UpdateCaseOperationType) theEObject;
            T result = caseUpdateCaseOperationType(updateCaseOperationType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.ADD_LINK_ASSOCIATIONS_TYPE: {
            AddLinkAssociationsType addLinkAssociationsType = (AddLinkAssociationsType) theEObject;
            T result = caseAddLinkAssociationsType(addLinkAssociationsType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.REMOVE_LINK_ASSOCIATIONS_TYPE: {
            RemoveLinkAssociationsType removeLinkAssociationsType = (RemoveLinkAssociationsType) theEObject;
            T result = caseRemoveLinkAssociationsType(removeLinkAssociationsType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE: {
            CaseReferenceOperationsType caseReferenceOperationsType = (CaseReferenceOperationsType) theEObject;
            T result = caseCaseReferenceOperationsType(caseReferenceOperationsType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.GLOBAL_DATA_OPERATION: {
            GlobalDataOperation globalDataOperation = (GlobalDataOperation) theEObject;
            T result = caseGlobalDataOperation(globalDataOperation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.DELETE_BY_CASE_IDENTIFIER_TYPE: {
            DeleteByCaseIdentifierType deleteByCaseIdentifierType = (DeleteByCaseIdentifierType) theEObject;
            T result = caseDeleteByCaseIdentifierType(deleteByCaseIdentifierType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.COMPOSITE_IDENTIFIER_TYPE: {
            CompositeIdentifierType compositeIdentifierType = (CompositeIdentifierType) theEObject;
            T result = caseCompositeIdentifierType(compositeIdentifierType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.DELETE_CASE_REFERENCE_OPERATION_TYPE: {
            DeleteCaseReferenceOperationType deleteCaseReferenceOperationType =
                    (DeleteCaseReferenceOperationType) theEObject;
            T result = caseDeleteCaseReferenceOperationType(deleteCaseReferenceOperationType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.DELETE_BY_COMPOSITE_IDENTIFIERS_TYPE: {
            DeleteByCompositeIdentifiersType deleteByCompositeIdentifiersType =
                    (DeleteByCompositeIdentifiersType) theEObject;
            T result = caseDeleteByCompositeIdentifiersType(deleteByCompositeIdentifiersType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.CREATE_CASE_OPERATION_TYPE: {
            CreateCaseOperationType createCaseOperationType = (CreateCaseOperationType) theEObject;
            T result = caseCreateCaseOperationType(createCaseOperationType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE: {
            CaseAccessOperationsType caseAccessOperationsType = (CaseAccessOperationsType) theEObject;
            T result = caseCaseAccessOperationsType(caseAccessOperationsType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.DATA_WORK_ITEM_ATTRIBUTE_MAPPING: {
            DataWorkItemAttributeMapping dataWorkItemAttributeMapping = (DataWorkItemAttributeMapping) theEObject;
            T result = caseDataWorkItemAttributeMapping(dataWorkItemAttributeMapping);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS: {
            ProcessDataWorkItemAttributeMappings processDataWorkItemAttributeMappings =
                    (ProcessDataWorkItemAttributeMappings) theEObject;
            T result = caseProcessDataWorkItemAttributeMappings(processDataWorkItemAttributeMappings);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.BPM_RUNTIME_CONFIGURATION: {
            BpmRuntimeConfiguration bpmRuntimeConfiguration = (BpmRuntimeConfiguration) theEObject;
            T result = caseBpmRuntimeConfiguration(bpmRuntimeConfiguration);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.ENABLEMENT_TYPE: {
            EnablementType enablementType = (EnablementType) theEObject;
            T result = caseEnablementType(enablementType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.INITIALIZER_ACTIVITIES_TYPE: {
            InitializerActivitiesType initializerActivitiesType = (InitializerActivitiesType) theEObject;
            T result = caseInitializerActivitiesType(initializerActivitiesType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE: {
            AdHocTaskConfigurationType adHocTaskConfigurationType = (AdHocTaskConfigurationType) theEObject;
            T result = caseAdHocTaskConfigurationType(adHocTaskConfigurationType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.REQUIRED_ACCESS_PRIVILEGES: {
            RequiredAccessPrivileges requiredAccessPrivileges = (RequiredAccessPrivileges) theEObject;
            T result = caseRequiredAccessPrivileges(requiredAccessPrivileges);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.VISIBLE_FOR_CASE_STATES: {
            VisibleForCaseStates visibleForCaseStates = (VisibleForCaseStates) theEObject;
            T result = caseVisibleForCaseStates(visibleForCaseStates);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.CASE_SERVICE: {
            CaseService caseService = (CaseService) theEObject;
            T result = caseCaseService(caseService);
            if (result == null)
                result = caseOtherElementsContainer(caseService);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.DOCUMENT_OPERATION: {
            DocumentOperation documentOperation = (DocumentOperation) theEObject;
            T result = caseDocumentOperation(documentOperation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS: {
            CaseDocRefOperations caseDocRefOperations = (CaseDocRefOperations) theEObject;
            T result = caseCaseDocRefOperations(caseDocRefOperations);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.CASE_DOC_FIND_OPERATIONS: {
            CaseDocFindOperations caseDocFindOperations = (CaseDocFindOperations) theEObject;
            T result = caseCaseDocFindOperations(caseDocFindOperations);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.MOVE_CASE_DOC_OPERATION: {
            MoveCaseDocOperation moveCaseDocOperation = (MoveCaseDocOperation) theEObject;
            T result = caseMoveCaseDocOperation(moveCaseDocOperation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.UNLINK_CASE_DOC_OPERATION: {
            UnlinkCaseDocOperation unlinkCaseDocOperation = (UnlinkCaseDocOperation) theEObject;
            T result = caseUnlinkCaseDocOperation(unlinkCaseDocOperation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.LINK_CASE_DOC_OPERATION: {
            LinkCaseDocOperation linkCaseDocOperation = (LinkCaseDocOperation) theEObject;
            T result = caseLinkCaseDocOperation(linkCaseDocOperation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.LINK_SYSTEM_DOCUMENT_OPERATION: {
            LinkSystemDocumentOperation linkSystemDocumentOperation = (LinkSystemDocumentOperation) theEObject;
            T result = caseLinkSystemDocumentOperation(linkSystemDocumentOperation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.DELETE_CASE_DOC_OPERATION: {
            DeleteCaseDocOperation deleteCaseDocOperation = (DeleteCaseDocOperation) theEObject;
            T result = caseDeleteCaseDocOperation(deleteCaseDocOperation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.FIND_BY_FILE_NAME_OPERATION: {
            FindByFileNameOperation findByFileNameOperation = (FindByFileNameOperation) theEObject;
            T result = caseFindByFileNameOperation(findByFileNameOperation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.FIND_BY_QUERY_OPERATION: {
            FindByQueryOperation findByQueryOperation = (FindByQueryOperation) theEObject;
            T result = caseFindByQueryOperation(findByQueryOperation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION: {
            CaseDocumentQueryExpression caseDocumentQueryExpression = (CaseDocumentQueryExpression) theEObject;
            T result = caseCaseDocumentQueryExpression(caseDocumentQueryExpression);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.SERVICE_PROCESS_CONFIGURATION: {
            ServiceProcessConfiguration serviceProcessConfiguration = (ServiceProcessConfiguration) theEObject;
            T result = caseServiceProcessConfiguration(serviceProcessConfiguration);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.SCRIPT_DATA_MAPPER: {
            ScriptDataMapper scriptDataMapper = (ScriptDataMapper) theEObject;
            T result = caseScriptDataMapper(scriptDataMapper);
            if (result == null)
                result = caseOtherElementsContainer(scriptDataMapper);
            if (result == null)
                result = caseOtherAttributesContainer(scriptDataMapper);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.DATA_MAPPER_ARRAY_INFLATION: {
            DataMapperArrayInflation dataMapperArrayInflation = (DataMapperArrayInflation) theEObject;
            T result = caseDataMapperArrayInflation(dataMapperArrayInflation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.LIKE_MAPPING_EXCLUSION: {
            LikeMappingExclusion likeMappingExclusion = (LikeMappingExclusion) theEObject;
            T result = caseLikeMappingExclusion(likeMappingExclusion);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.LIKE_MAPPING_EXCLUSIONS: {
            LikeMappingExclusions likeMappingExclusions = (LikeMappingExclusions) theEObject;
            T result = caseLikeMappingExclusions(likeMappingExclusions);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case XpdExtensionPackage.REST_SERVICE_OPERATION: {
            RestServiceOperation restServiceOperation = (RestServiceOperation) theEObject;
            T result = caseRestServiceOperation(restServiceOperation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        default:
            return defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Activity Ref</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Activity Ref</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseActivityRef(ActivityRef object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Activity Resource Patterns</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Activity Resource Patterns</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseActivityResourcePatterns(ActivityResourcePatterns object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Allocation Strategy</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Allocation Strategy</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAllocationStrategy(AllocationStrategy object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Associated Correlation Fields</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Associated Correlation Fields</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAssociatedCorrelationFields(AssociatedCorrelationFields object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Associated Correlation Field</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Associated Correlation Field</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAssociatedCorrelationField(AssociatedCorrelationField object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Associated Parameter</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Associated Parameter</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAssociatedParameter(AssociatedParameter object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Associated Parameters</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Associated Parameters</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAssociatedParameters(AssociatedParameters object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Associated Parameters Container</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Associated Parameters Container</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAssociatedParametersContainer(AssociatedParametersContainer object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Audit</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Audit</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAudit(Audit object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Audit Event</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Audit Event</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAuditEvent(AuditEvent object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Business Process</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Business Process</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseBusinessProcess(BusinessProcess object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Calendar Reference</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Calendar Reference</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCalendarReference(CalendarReference object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Catch Error Mappings</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Catch Error Mappings</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCatchErrorMappings(CatchErrorMappings object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Constant Period</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Constant Period</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConstantPeriod(ConstantPeriod object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Participant</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Participant</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalParticipant(ConditionalParticipant object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Reply Immediate Data Mappings</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Reply Immediate Data Mappings</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseReplyImmediateDataMappings(ReplyImmediateDataMappings object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Correlation Data Mappings</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Correlation Data Mappings</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCorrelationDataMappings(CorrelationDataMappings object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Discriminator</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Discriminator</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDiscriminator(Discriminator object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Document Root</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Document Root</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDocumentRoot(DocumentRoot object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Duration Calculation</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Duration Calculation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDurationCalculation(DurationCalculation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Dynamic Organization Mappings</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Dynamic Organization Mappings</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDynamicOrganizationMappings(DynamicOrganizationMappings object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Dynamic Organization Mapping</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Dynamic Organization Mapping</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDynamicOrganizationMapping(DynamicOrganizationMapping object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Dynamic Org Identifier Ref</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Dynamic Org Identifier Ref</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDynamicOrgIdentifierRef(DynamicOrgIdentifierRef object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Error Method</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Error Method</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseErrorMethod(ErrorMethod object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Error Thrower Info</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Error Thrower Info</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseErrorThrowerInfo(ErrorThrowerInfo object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Event Handler Initialisers</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Event Handler Initialisers</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEventHandlerInitialisers(EventHandlerInitialisers object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Fault Message</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Fault Message</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFaultMessage(FaultMessage object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Form Implementation</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Form Implementation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFormImplementation(FormImplementation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Implemented Interface</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Implemented Interface</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseImplementedInterface(ImplementedInterface object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Initial Values</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Initial Values</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseInitialValues(InitialValues object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Initial Parameter Value</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Initial Parameter Value</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseInitialParameterValue(InitialParameterValue object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Interface Method</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Interface Method</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseInterfaceMethod(InterfaceMethod object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Intermediate Method</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Intermediate Method</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseIntermediateMethod(IntermediateMethod object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Multi Instance Scripts</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Multi Instance Scripts</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMultiInstanceScripts(MultiInstanceScripts object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Namespace Prefix Map</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Namespace Prefix Map</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNamespacePrefixMap(NamespacePrefixMap object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Namespace Map Entry</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Namespace Map Entry</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNamespaceMapEntry(NamespaceMapEntry object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Piling Info</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Piling Info</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePilingInfo(PilingInfo object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Port Type Operation</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Port Type Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePortTypeOperation(PortTypeOperation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Process Interface</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Process Interface</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseProcessInterface(ProcessInterface object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Process Interfaces</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Process Interfaces</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseProcessInterfaces(ProcessInterfaces object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Process Resource Patterns</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Process Resource Patterns</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseProcessResourcePatterns(ProcessResourcePatterns object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Reschedule Timer Script</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Reschedule Timer Script</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRescheduleTimerScript(RescheduleTimerScript object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Reschedule Timers</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Reschedule Timers</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRescheduleTimers(RescheduleTimers object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Retain Familiar Activities</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Retain Familiar Activities</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRetainFamiliarActivities(RetainFamiliarActivities object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Retry</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Retry</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRetry(Retry object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Script Information</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Script Information</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseScriptInformation(ScriptInformation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Separation Of Duties Activities</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Separation Of Duties Activities</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSeparationOfDutiesActivities(SeparationOfDutiesActivities object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Signal Data</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Signal Data</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSignalData(SignalData object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Start Method</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Start Method</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseStartMethod(StartMethod object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Structured Discriminator</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Structured Discriminator</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseStructuredDiscriminator(StructuredDiscriminator object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Task Library Reference</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Task Library Reference</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTaskLibraryReference(TaskLibraryReference object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Transform Script</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Transform Script</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTransformScript(TransformScript object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>User Task Scripts</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>User Task Scripts</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseUserTaskScripts(UserTaskScripts object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Validation Control</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Validation Control</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseValidationControl(ValidationControl object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Validation Issue Override</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Validation Issue Override</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseValidationIssueOverride(ValidationIssueOverride object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Wsdl Event Association</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Wsdl Event Association</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWsdlEventAssociation(WsdlEventAssociation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Work Item Priority</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Work Item Priority</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkItemPriority(WorkItemPriority object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Xpd Ext Data Object Attributes</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Xpd Ext Data Object Attributes</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseXpdExtDataObjectAttributes(XpdExtDataObjectAttributes object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Xpd Ext Property</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Xpd Ext Property</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseXpdExtProperty(XpdExtProperty object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Xpd Ext Attribute</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Xpd Ext Attribute</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseXpdExtAttribute(XpdExtAttribute object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Xpd Ext Attributes</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Xpd Ext Attributes</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseXpdExtAttributes(XpdExtAttributes object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Update Case Operation Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Update Case Operation Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseUpdateCaseOperationType(UpdateCaseOperationType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Add Link Associations Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Add Link Associations Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAddLinkAssociationsType(AddLinkAssociationsType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Remove Link Associations Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Remove Link Associations Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRemoveLinkAssociationsType(RemoveLinkAssociationsType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Case Reference Operations Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Case Reference Operations Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCaseReferenceOperationsType(CaseReferenceOperationsType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Global Data Operation</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Global Data Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGlobalDataOperation(GlobalDataOperation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Delete By Case Identifier Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Delete By Case Identifier Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDeleteByCaseIdentifierType(DeleteByCaseIdentifierType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Composite Identifier Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Composite Identifier Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCompositeIdentifierType(CompositeIdentifierType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Delete Case Reference Operation Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Delete Case Reference Operation Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDeleteCaseReferenceOperationType(DeleteCaseReferenceOperationType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Delete By Composite Identifiers Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Delete By Composite Identifiers Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDeleteByCompositeIdentifiersType(DeleteByCompositeIdentifiersType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Create Case Operation Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Create Case Operation Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCreateCaseOperationType(CreateCaseOperationType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Case Access Operations Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Case Access Operations Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCaseAccessOperationsType(CaseAccessOperationsType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Data Work Item Attribute Mapping</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Data Work Item Attribute Mapping</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDataWorkItemAttributeMapping(DataWorkItemAttributeMapping object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Process Data Work Item Attribute Mappings</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Process Data Work Item Attribute Mappings</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseProcessDataWorkItemAttributeMappings(ProcessDataWorkItemAttributeMappings object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Bpm Runtime Configuration</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Bpm Runtime Configuration</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseBpmRuntimeConfiguration(BpmRuntimeConfiguration object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Enablement Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Enablement Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEnablementType(EnablementType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Initializer Activities Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Initializer Activities Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseInitializerActivitiesType(InitializerActivitiesType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Ad Hoc Task Configuration Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Ad Hoc Task Configuration Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAdHocTaskConfigurationType(AdHocTaskConfigurationType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Required Access Privileges</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Required Access Privileges</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRequiredAccessPrivileges(RequiredAccessPrivileges object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Visible For Case States</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Visible For Case States</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseVisibleForCaseStates(VisibleForCaseStates object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Case Service</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Case Service</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCaseService(CaseService object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Document Operation</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Document Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDocumentOperation(DocumentOperation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Case Doc Ref Operations</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Case Doc Ref Operations</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCaseDocRefOperations(CaseDocRefOperations object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Case Doc Find Operations</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Case Doc Find Operations</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCaseDocFindOperations(CaseDocFindOperations object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Move Case Doc Operation</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Move Case Doc Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMoveCaseDocOperation(MoveCaseDocOperation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Unlink Case Doc Operation</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Unlink Case Doc Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseUnlinkCaseDocOperation(UnlinkCaseDocOperation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Link Case Doc Operation</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Link Case Doc Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLinkCaseDocOperation(LinkCaseDocOperation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Link System Document Operation</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Link System Document Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLinkSystemDocumentOperation(LinkSystemDocumentOperation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Delete Case Doc Operation</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Delete Case Doc Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDeleteCaseDocOperation(DeleteCaseDocOperation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Find By File Name Operation</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Find By File Name Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFindByFileNameOperation(FindByFileNameOperation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Find By Query Operation</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Find By Query Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFindByQueryOperation(FindByQueryOperation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Case Document Query Expression</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Case Document Query Expression</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCaseDocumentQueryExpression(CaseDocumentQueryExpression object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Service Process Configuration</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Service Process Configuration</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseServiceProcessConfiguration(ServiceProcessConfiguration object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Script Data Mapper</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Script Data Mapper</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseScriptDataMapper(ScriptDataMapper object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Data Mapper Array Inflation</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Data Mapper Array Inflation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDataMapperArrayInflation(DataMapperArrayInflation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Like Mapping Exclusions</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Like Mapping Exclusions</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLikeMappingExclusions(LikeMappingExclusions object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Rest Service Operation</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Rest Service Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRestServiceOperation(RestServiceOperation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Like Mapping Exclusion</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Like Mapping Exclusion</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLikeMappingExclusion(LikeMappingExclusion object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>REST Services</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>REST Services</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRESTServices(RESTServices object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Rest Service Resource</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Rest Service Resource</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRestServiceResource(RestServiceResource object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Rest Service Resource Security</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Rest Service Resource Security</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRestServiceResourceSecurity(RestServiceResourceSecurity object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Wsdl Generation</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Wsdl Generation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWsdlGeneration(WsdlGeneration object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Email Resource</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Email Resource</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEmailResource(EmailResource object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Jdbc Resource</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Jdbc Resource</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseJdbcResource(JdbcResource object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Participant Shared Resource</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Participant Shared Resource</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseParticipantSharedResource(ParticipantSharedResource object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Ws Binding</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Ws Binding</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWsBinding(WsBinding object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Ws Inbound</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Ws Inbound</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWsInbound(WsInbound object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Ws Outbound</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Ws Outbound</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWsOutbound(WsOutbound object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Ws Resource</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Ws Resource</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWsResource(WsResource object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Ws Security Policy</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Ws Security Policy</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWsSecurityPolicy(WsSecurityPolicy object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Ws Soap Binding</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Ws Soap Binding</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWsSoapBinding(WsSoapBinding object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Ws Soap Http Inbound Binding</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Ws Soap Http Inbound Binding</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWsSoapHttpInboundBinding(WsSoapHttpInboundBinding object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Ws Soap Http Outbound Binding</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Ws Soap Http Outbound Binding</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWsSoapHttpOutboundBinding(WsSoapHttpOutboundBinding object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Ws Soap Jms Inbound Binding</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Ws Soap Jms Inbound Binding</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWsSoapJmsInboundBinding(WsSoapJmsInboundBinding object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Ws Soap Jms Outbound Binding</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Ws Soap Jms Outbound Binding</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWsSoapJmsOutboundBinding(WsSoapJmsOutboundBinding object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Ws Soap Security</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Ws Soap Security</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWsSoapSecurity(WsSoapSecurity object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Ws Virtual Binding</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Ws Virtual Binding</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWsVirtualBinding(WsVirtualBinding object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Described Element</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Described Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDescribedElement(DescribedElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Unique Id Element</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Unique Id Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseUniqueIdElement(UniqueIdElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Other Attributes Container</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Other Attributes Container</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOtherAttributesContainer(OtherAttributesContainer object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNamedElement(NamedElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Other Elements Container</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Other Elements Container</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOtherElementsContainer(OtherElementsContainer object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Message</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Message</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMessage(Message object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Extended Attributes Container</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Extended Attributes Container</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseExtendedAttributesContainer(ExtendedAttributesContainer object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Formal Parameters Container</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Formal Parameters Container</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFormalParametersContainer(FormalParametersContainer object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Expression</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Expression</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseExpression(Expression object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch, but this is the last case anyway.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    public T defaultCase(EObject object) {
        return null;
    }

} //XpdExtensionSwitch
