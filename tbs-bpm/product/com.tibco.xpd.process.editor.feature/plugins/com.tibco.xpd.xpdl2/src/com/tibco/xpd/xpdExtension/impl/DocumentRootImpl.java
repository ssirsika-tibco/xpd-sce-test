/**
 * <copyright> </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationFields;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.AsyncExecutionMode;
import com.tibco.xpd.xpdExtension.Audit;
import com.tibco.xpd.xpdExtension.BpmRuntimeConfiguration;
import com.tibco.xpd.xpdExtension.BusinessProcess;
import com.tibco.xpd.xpdExtension.BusinessServicePublishType;
import com.tibco.xpd.xpdExtension.CalendarReference;
import com.tibco.xpd.xpdExtension.CaseService;
import com.tibco.xpd.xpdExtension.CatchErrorMappings;
import com.tibco.xpd.xpdExtension.ConditionalParticipant;
import com.tibco.xpd.xpdExtension.ConstantPeriod;
import com.tibco.xpd.xpdExtension.CorrelationDataMappings;
import com.tibco.xpd.xpdExtension.Discriminator;
import com.tibco.xpd.xpdExtension.DocumentOperation;
import com.tibco.xpd.xpdExtension.DocumentRoot;
import com.tibco.xpd.xpdExtension.DurationCalculation;
import com.tibco.xpd.xpdExtension.DynamicOrganizationMappings;
import com.tibco.xpd.xpdExtension.ErrorThrowerInfo;
import com.tibco.xpd.xpdExtension.EventHandlerFlowStrategy;
import com.tibco.xpd.xpdExtension.EventHandlerInitialisers;
import com.tibco.xpd.xpdExtension.FieldFormat;
import com.tibco.xpd.xpdExtension.FlowRoutingStyle;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.GlobalDataOperation;
import com.tibco.xpd.xpdExtension.ImplementedInterface;
import com.tibco.xpd.xpdExtension.InitialParameterValue;
import com.tibco.xpd.xpdExtension.InitialValues;
import com.tibco.xpd.xpdExtension.LibraryFunctionUseIn;
import com.tibco.xpd.xpdExtension.LikeMappingExclusions;
import com.tibco.xpd.xpdExtension.MultiInstanceScripts;
import com.tibco.xpd.xpdExtension.NamespacePrefixMap;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.ProcessDataWorkItemAttributeMappings;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.ProcessResourcePatterns;
import com.tibco.xpd.xpdExtension.RESTServices;
import com.tibco.xpd.xpdExtension.ReplyImmediateDataMappings;
import com.tibco.xpd.xpdExtension.RequiredAccessPrivileges;
import com.tibco.xpd.xpdExtension.RescheduleTimerScript;
import com.tibco.xpd.xpdExtension.RestServiceOperation;
import com.tibco.xpd.xpdExtension.Retry;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.ServiceProcessConfiguration;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdExtension.SignalType;
import com.tibco.xpd.xpdExtension.SubProcessStartStrategy;
import com.tibco.xpd.xpdExtension.SystemErrorActionType;
import com.tibco.xpd.xpdExtension.TaskLibraryReference;
import com.tibco.xpd.xpdExtension.TransformScript;
import com.tibco.xpd.xpdExtension.UserTaskScripts;
import com.tibco.xpd.xpdExtension.ValidationControl;
import com.tibco.xpd.xpdExtension.Visibility;
import com.tibco.xpd.xpdExtension.WsdlEventAssociation;
import com.tibco.xpd.xpdExtension.WsdlGeneration;
import com.tibco.xpd.xpdExtension.XpdExtAttributes;
import com.tibco.xpd.xpdExtension.XpdExtDataObjectAttributes;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdExtension.XpdInterfaceType;
import com.tibco.xpd.xpdExtension.XpdModelType;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.SubFlow;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Document Root</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getImplementationType <em>Implementation Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getDataObjectAttributes <em>Data Object Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getExtendedAttributes <em>Extended Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#isContinueOnTimeout <em>Continue On Timeout</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getAlias <em>Alias</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getConstantPeriod <em>Constant Period</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getUserTaskScripts <em>User Task Scripts</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getAudit <em>Audit</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getScript <em>Script</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#isReplyImmediately <em>Reply Immediately</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getInitialValues <em>Initial Values</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getAssociatedCorrelationFields <em>Associated Correlation Fields</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getAssociatedParameters <em>Associated Parameters</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getImplementedInterface <em>Implemented Interface</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getProcessInterfaces <em>Process Interfaces</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getWsdlEventAssociation <em>Wsdl Event Association</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#isInlineSubProcess <em>Inline Sub Process</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getDocumentationURL <em>Documentation URL</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getImplements <em>Implements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getMultiInstanceScripts <em>Multi Instance Scripts</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getProcessIdentifierField <em>Process Identifier Field</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getExpression <em>Expression</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getVisibility <em>Visibility</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getSecurityProfile <em>Security Profile</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getLanguage <em>Language</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getInitialParameterValue <em>Initial Parameter Value</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#isInitialValueMapping <em>Initial Value Mapping</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getPortTypeOperation <em>Port Type Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getTransport <em>Transport</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#isIsChained <em>Is Chained</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getExternalReference <em>External Reference</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getProcessResourcePatterns <em>Process Resource Patterns</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getEventHandlerInitialisers <em>Event Handler Initialisers</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getActivityResourcePatterns <em>Activity Resource Patterns</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#isRequireNewTransaction <em>Require New Transaction</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getDocumentOperation <em>Document Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getDurationCalculation <em>Duration Calculation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getDiscriminator <em>Discriminator</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getCatchThrow <em>Catch Throw</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#isIsRemote <em>Is Remote</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getCorrelationDataMappings <em>Correlation Data Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getTransformScript <em>Transform Script</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#isPublishAsBusinessService <em>Publish As Business Service</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getBusinessServiceCategory <em>Business Service Category</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getErrorThrowerInfo <em>Error Thrower Info</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getCatchErrorMappings <em>Catch Error Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getConditionalParticipant <em>Conditional Participant</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#isGenerated <em>Generated</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getReplyToActivityId <em>Reply To Activity Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getTaskLibraryReference <em>Task Library Reference</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#isSetPerformerInProcess <em>Set Performer In Process</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getEmbSubprocOtherStateHeight <em>Emb Subproc Other State Height</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getEmbSubprocOtherStateWidth <em>Emb Subproc Other State Width</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getFormImplementation <em>Form Implementation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getParticipantQuery <em>Participant Query</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getApiEndPointParticipant <em>Api End Point Participant</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getFaultMessage <em>Fault Message</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getRequestActivityId <em>Request Activity Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getBusinessProcess <em>Business Process</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getWsdlGeneration <em>Wsdl Generation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#isTargetPrimitiveProperty <em>Target Primitive Property</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#isSourcePrimitiveProperty <em>Source Primitive Property</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getDecisionService <em>Decision Service</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getParticipantSharedResource <em>Participant Shared Resource</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getXpdModelType <em>Xpd Model Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getFlowRoutingStyle <em>Flow Routing Style</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getCalendarReference <em>Calendar Reference</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#isNonCancelling <em>Non Cancelling</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getSignalData <em>Signal Data</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getRetry <em>Retry</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getActivityDeadlineEventId <em>Activity Deadline Event Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getStartStrategy <em>Start Strategy</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#isOverwriteAlreadyModifiedTaskData <em>Overwrite Already Modified Task Data</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getEventHandlerFlowStrategy <em>Event Handler Flow Strategy</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getNamespacePrefixMap <em>Namespace Prefix Map</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#isSuspendResumeWithParent <em>Suspend Resume With Parent</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getSystemErrorAction <em>System Error Action</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getCorrelationTimeout <em>Correlation Timeout</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getValidationControl <em>Validation Control</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getReplyImmediateDataMappings <em>Reply Immediate Data Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#isBxUseUnqualifiedPropertyNames <em>Bx Use Unqualified Property Names</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getCaseRefType <em>Case Ref Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getRESTServices <em>REST Services</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#isPublishAsRestService <em>Publish As Rest Service</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getRestActivityId <em>Rest Activity Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getRescheduleTimerScript <em>Reschedule Timer Script</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getDynamicOrganizationMappings <em>Dynamic Organization Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#isSignalHandlerAsynchronous <em>Signal Handler Asynchronous</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getGlobalDataOperation <em>Global Data Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getProcessDataWorkItemAttributeMappings <em>Process Data Work Item Attribute Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#isAllowUnqualifiedSubProcessIdentification <em>Allow Unqualified Sub Process Identification</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getBpmRuntimeConfiguration <em>Bpm Runtime Configuration</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#isIsCaseService <em>Is Case Service</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getRequiredAccessPrivileges <em>Required Access Privileges</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getCaseService <em>Case Service</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getAdHocTaskConfiguration <em>Ad Hoc Task Configuration</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#isIsEventSubProcess <em>Is Event Sub Process</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#isNonInterruptingEvent <em>Non Interrupting Event</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#isCorrelateImmediately <em>Correlate Immediately</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getAsyncExecutionMode <em>Async Execution Mode</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getSignalType <em>Signal Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getServiceProcessConfiguration <em>Service Process Configuration</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#isLikeMapping <em>Like Mapping</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getScriptDataMapper <em>Script Data Mapper</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getLikeMappingExclusions <em>Like Mapping Exclusions</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getSourceContributorId <em>Source Contributor Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getTargetContributorId <em>Target Contributor Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getRestServiceOperation <em>Rest Service Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getInputMappings <em>Input Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getOutputMappings <em>Output Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getBusinessServicePublishType <em>Business Service Publish Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#isSuppressMaxMappingsError <em>Suppress Max Mappings Error</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getFieldFormat <em>Field Format</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl#getUseIn <em>Use In</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DocumentRootImpl extends EObjectImpl implements DocumentRoot
{

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public static final String								copyright												= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getXMLNSPrefixMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String>							xMLNSPrefixMap;

	/**
	 * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getXSISchemaLocation()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String>							xSISchemaLocation;

	/**
	 * The default value of the '{@link #getImplementationType() <em>Implementation Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getImplementationType()
	 * @generated
	 * @ordered
	 */
	protected static final String							IMPLEMENTATION_TYPE_EDEFAULT							= null;

	/**
	 * The cached value of the '{@link #getImplementationType() <em>Implementation Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getImplementationType()
	 * @generated
	 * @ordered
	 */
	protected String										implementationType										= IMPLEMENTATION_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDataObjectAttributes()
	 * <em>Data Object Attributes</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDataObjectAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<XpdExtDataObjectAttributes>				dataObjectAttributes;

	/**
	 * The cached value of the '{@link #getExtendedAttributes()
	 * <em>Extended Attributes</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getExtendedAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<XpdExtAttributes>						extendedAttributes;

	/**
	 * The default value of the '{@link #isContinueOnTimeout() <em>Continue On Timeout</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #isContinueOnTimeout()
	 * @generated
	 * @ordered
	 */
	protected static final boolean							CONTINUE_ON_TIMEOUT_EDEFAULT							= false;

	/**
	 * The cached value of the '{@link #isContinueOnTimeout() <em>Continue On Timeout</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #isContinueOnTimeout()
	 * @generated
	 * @ordered
	 */
	protected boolean										continueOnTimeout										= CONTINUE_ON_TIMEOUT_EDEFAULT;

	/**
	 * The default value of the '{@link #getAlias() <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getAlias()
	 * @generated
	 * @ordered
	 */
	protected static final String							ALIAS_EDEFAULT											= null;

	/**
	 * The cached value of the '{@link #getAlias() <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getAlias()
	 * @generated
	 * @ordered
	 */
	protected String										alias													= ALIAS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getConstantPeriod()
	 * <em>Constant Period</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getConstantPeriod()
	 * @generated
	 * @ordered
	 */
	protected EList<ConstantPeriod>							constantPeriod;

	/**
	 * The cached value of the '{@link #getUserTaskScripts()
	 * <em>User Task Scripts</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getUserTaskScripts()
	 * @generated
	 * @ordered
	 */
	protected EList<UserTaskScripts>						userTaskScripts;

	/**
	 * The cached value of the '{@link #getScript() <em>Script</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getScript()
	 * @generated
	 * @ordered
	 */
	protected EList<ScriptInformation>						script;

	/**
	 * The default value of the '{@link #isReplyImmediately() <em>Reply Immediately</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #isReplyImmediately()
	 * @generated
	 * @ordered
	 */
	protected static final boolean							REPLY_IMMEDIATELY_EDEFAULT								= false;

	/**
	 * The cached value of the '{@link #isReplyImmediately() <em>Reply Immediately</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #isReplyImmediately()
	 * @generated
	 * @ordered
	 */
	protected boolean										replyImmediately										= REPLY_IMMEDIATELY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getInitialValues() <em>Initial Values</em>}' containment reference list.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see #getInitialValues()
	 * @generated
	 * @ordered
	 */
	protected EList<InitialValues>							initialValues;

	/**
	 * The cached value of the '{@link #getAssociatedCorrelationFields()
	 * <em>Associated Correlation Fields</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAssociatedCorrelationFields()
	 * @generated
	 * @ordered
	 */
	protected EList<AssociatedCorrelationFields>			associatedCorrelationFields;

	/**
	 * The cached value of the '{@link #getAssociatedParameters()
	 * <em>Associated Parameters</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAssociatedParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<AssociatedParameters>					associatedParameters;

	/**
	 * The cached value of the '{@link #getImplementedInterface()
	 * <em>Implemented Interface</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getImplementedInterface()
	 * @generated
	 * @ordered
	 */
	protected EList<ImplementedInterface>					implementedInterface;

	/**
	 * The cached value of the '{@link #getProcessInterfaces()
	 * <em>Process Interfaces</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getProcessInterfaces()
	 * @generated
	 * @ordered
	 */
	protected EList<ProcessInterfaces>						processInterfaces;

	/**
	 * The cached value of the '{@link #getWsdlEventAssociation()
	 * <em>Wsdl Event Association</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getWsdlEventAssociation()
	 * @generated
	 * @ordered
	 */
	protected EList<WsdlEventAssociation>					wsdlEventAssociation;

	/**
	 * The default value of the '{@link #isInlineSubProcess() <em>Inline Sub Process</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #isInlineSubProcess()
	 * @generated
	 * @ordered
	 */
	protected static final boolean							INLINE_SUB_PROCESS_EDEFAULT								= false;

	/**
	 * The cached value of the '{@link #isInlineSubProcess() <em>Inline Sub Process</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #isInlineSubProcess()
	 * @generated
	 * @ordered
	 */
	protected boolean										inlineSubProcess										= INLINE_SUB_PROCESS_EDEFAULT;

	/**
	 * The default value of the '{@link #getDocumentationURL() <em>Documentation URL</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getDocumentationURL()
	 * @generated
	 * @ordered
	 */
	protected static final String							DOCUMENTATION_URL_EDEFAULT								= null;

	/**
	 * The cached value of the '{@link #getDocumentationURL() <em>Documentation URL</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getDocumentationURL()
	 * @generated
	 * @ordered
	 */
	protected String										documentationURL										= DOCUMENTATION_URL_EDEFAULT;

	/**
	 * The default value of the '{@link #getImplements() <em>Implements</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getImplements()
	 * @generated
	 * @ordered
	 */
	protected static final String							IMPLEMENTS_EDEFAULT										= null;

	/**
	 * The cached value of the '{@link #getImplements() <em>Implements</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getImplements()
	 * @generated
	 * @ordered
	 */
	protected String										implements_												= IMPLEMENTS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMultiInstanceScripts()
	 * <em>Multi Instance Scripts</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMultiInstanceScripts()
	 * @generated
	 * @ordered
	 */
	protected EList<MultiInstanceScripts>					multiInstanceScripts;

	/**
	 * The default value of the '{@link #getProcessIdentifierField() <em>Process Identifier Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProcessIdentifierField()
	 * @generated
	 * @ordered
	 */
	protected static final String							PROCESS_IDENTIFIER_FIELD_EDEFAULT						= null;

	/**
	 * The cached value of the '{@link #getProcessIdentifierField() <em>Process Identifier Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProcessIdentifierField()
	 * @generated
	 * @ordered
	 */
	protected String										processIdentifierField									= PROCESS_IDENTIFIER_FIELD_EDEFAULT;

	/**
	 * The cached value of the '{@link #getExpression() <em>Expression</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
	protected EList<Expression>								expression;

	/**
	 * The default value of the '{@link #getVisibility() <em>Visibility</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getVisibility()
	 * @generated
	 * @ordered
	 */
	protected static final Visibility						VISIBILITY_EDEFAULT										= Visibility.PRIVATE;

	/**
	 * The cached value of the '{@link #getVisibility() <em>Visibility</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getVisibility()
	 * @generated
	 * @ordered
	 */
	protected Visibility									visibility												= VISIBILITY_EDEFAULT;

	/**
	 * This is true if the Visibility attribute has been set. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	protected boolean										visibilityESet;

	/**
	 * The default value of the '{@link #getSecurityProfile() <em>Security Profile</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getSecurityProfile()
	 * @generated
	 * @ordered
	 */
	protected static final String							SECURITY_PROFILE_EDEFAULT								= null;

	/**
	 * The cached value of the '{@link #getSecurityProfile() <em>Security Profile</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getSecurityProfile()
	 * @generated
	 * @ordered
	 */
	protected String										securityProfile											= SECURITY_PROFILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getLanguage() <em>Language</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLanguage()
	 * @generated
	 * @ordered
	 */
	protected static final String							LANGUAGE_EDEFAULT										= null;

	/**
	 * The cached value of the '{@link #getLanguage() <em>Language</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLanguage()
	 * @generated
	 * @ordered
	 */
	protected String										language												= LANGUAGE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getInitialParameterValue()
	 * <em>Initial Parameter Value</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getInitialParameterValue()
	 * @generated
	 * @ordered
	 */
	protected EList<InitialParameterValue>					initialParameterValue;

	/**
	 * The default value of the '{@link #isInitialValueMapping() <em>Initial Value Mapping</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #isInitialValueMapping()
	 * @generated
	 * @ordered
	 */
	protected static final boolean							INITIAL_VALUE_MAPPING_EDEFAULT							= false;

	/**
	 * The cached value of the '{@link #isInitialValueMapping() <em>Initial Value Mapping</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #isInitialValueMapping()
	 * @generated
	 * @ordered
	 */
	protected boolean										initialValueMapping										= INITIAL_VALUE_MAPPING_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPortTypeOperation()
	 * <em>Port Type Operation</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPortTypeOperation()
	 * @generated
	 * @ordered
	 */
	protected EList<PortTypeOperation>						portTypeOperation;

	/**
	 * The default value of the '{@link #getTransport() <em>Transport</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTransport()
	 * @generated
	 * @ordered
	 */
	protected static final String							TRANSPORT_EDEFAULT										= null;

	/**
	 * The cached value of the '{@link #getTransport() <em>Transport</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTransport()
	 * @generated
	 * @ordered
	 */
	protected String										transport												= TRANSPORT_EDEFAULT;

	/**
	 * The default value of the '{@link #isIsChained() <em>Is Chained</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isIsChained()
	 * @generated
	 * @ordered
	 */
	protected static final boolean							IS_CHAINED_EDEFAULT										= false;

	/**
	 * The cached value of the '{@link #isIsChained() <em>Is Chained</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isIsChained()
	 * @generated
	 * @ordered
	 */
	protected boolean										isChained												= IS_CHAINED_EDEFAULT;

	/**
	 * This is true if the Is Chained attribute has been set. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	protected boolean										isChainedESet;

	/**
	 * The cached value of the '{@link #getExternalReference()
	 * <em>External Reference</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getExternalReference()
	 * @generated
	 * @ordered
	 */
	protected EList<ExternalReference>						externalReference;

	/**
	 * The cached value of the '{@link #getProcessResourcePatterns()
	 * <em>Process Resource Patterns</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getProcessResourcePatterns()
	 * @generated
	 * @ordered
	 */
	protected EList<ProcessResourcePatterns>				processResourcePatterns;

	/**
	 * The cached value of the '{@link #getEventHandlerInitialisers()
	 * <em>Event Handler Initialisers</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEventHandlerInitialisers()
	 * @generated
	 * @ordered
	 */
	protected EList<EventHandlerInitialisers>				eventHandlerInitialisers;

	/**
	 * The cached value of the '{@link #getActivityResourcePatterns()
	 * <em>Activity Resource Patterns</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getActivityResourcePatterns()
	 * @generated
	 * @ordered
	 */
	protected EList<ActivityResourcePatterns>				activityResourcePatterns;

	/**
	 * The default value of the '{@link #isRequireNewTransaction() <em>Require New Transaction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRequireNewTransaction()
	 * @generated
	 * @ordered
	 */
	protected static final boolean							REQUIRE_NEW_TRANSACTION_EDEFAULT						= false;

	/**
	 * The cached value of the '{@link #isRequireNewTransaction() <em>Require New Transaction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRequireNewTransaction()
	 * @generated
	 * @ordered
	 */
	protected boolean										requireNewTransaction									= REQUIRE_NEW_TRANSACTION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDocumentOperation() <em>Document Operation</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocumentOperation()
	 * @generated
	 * @ordered
	 */
	protected EList<DocumentOperation>						documentOperation;

	/**
	 * The cached value of the '{@link #getDurationCalculation()
	 * <em>Duration Calculation</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDurationCalculation()
	 * @generated
	 * @ordered
	 */
	protected EList<DurationCalculation>					durationCalculation;

	/**
	 * The cached value of the '{@link #getDiscriminator() <em>Discriminator</em>}' containment reference list.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see #getDiscriminator()
	 * @generated
	 * @ordered
	 */
	protected EList<Discriminator>							discriminator;

	/**
	 * The default value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected static final String							DISPLAY_NAME_EDEFAULT									= null;

	/**
	 * The cached value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected String										displayName												= DISPLAY_NAME_EDEFAULT;

	/**
	 * This is true if the Display Name attribute has been set. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	protected boolean										displayNameESet;

	/**
	 * The default value of the '{@link #getCatchThrow() <em>Catch Throw</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getCatchThrow()
	 * @generated
	 * @ordered
	 */
	protected static final CatchThrow						CATCH_THROW_EDEFAULT									= CatchThrow.CATCH;

	/**
	 * The cached value of the '{@link #getCatchThrow() <em>Catch Throw</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getCatchThrow()
	 * @generated
	 * @ordered
	 */
	protected CatchThrow									catchThrow												= CATCH_THROW_EDEFAULT;

	/**
	 * The default value of the '{@link #isIsRemote() <em>Is Remote</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isIsRemote()
	 * @generated
	 * @ordered
	 */
	protected static final boolean							IS_REMOTE_EDEFAULT										= false;

	/**
	 * The cached value of the '{@link #isIsRemote() <em>Is Remote</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isIsRemote()
	 * @generated
	 * @ordered
	 */
	protected boolean										isRemote												= IS_REMOTE_EDEFAULT;

	/**
	 * This is true if the Is Remote attribute has been set.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean										isRemoteESet;

	/**
	 * The cached value of the '{@link #getCorrelationDataMappings()
	 * <em>Correlation Data Mappings</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCorrelationDataMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<CorrelationDataMappings>				correlationDataMappings;

	/**
	 * The cached value of the '{@link #getTransformScript()
	 * <em>Transform Script</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTransformScript()
	 * @generated
	 * @ordered
	 */
	protected EList<TransformScript>						transformScript;

	/**
	 * The default value of the '{@link #isPublishAsBusinessService() <em>Publish As Business Service</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPublishAsBusinessService()
	 * @generated
	 * @ordered
	 */
	protected static final boolean							PUBLISH_AS_BUSINESS_SERVICE_EDEFAULT					= false;

	/**
	 * The cached value of the '{@link #isPublishAsBusinessService() <em>Publish As Business Service</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPublishAsBusinessService()
	 * @generated
	 * @ordered
	 */
	protected boolean										publishAsBusinessService								= PUBLISH_AS_BUSINESS_SERVICE_EDEFAULT;

	/**
	 * This is true if the Publish As Business Service attribute has been set.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean										publishAsBusinessServiceESet;

	/**
	 * The default value of the '{@link #getBusinessServiceCategory() <em>Business Service Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBusinessServiceCategory()
	 * @generated
	 * @ordered
	 */
	protected static final String							BUSINESS_SERVICE_CATEGORY_EDEFAULT						= null;

	/**
	 * The cached value of the '{@link #getBusinessServiceCategory() <em>Business Service Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBusinessServiceCategory()
	 * @generated
	 * @ordered
	 */
	protected String										businessServiceCategory									= BUSINESS_SERVICE_CATEGORY_EDEFAULT;

	/**
	 * This is true if the Business Service Category attribute has been set.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean										businessServiceCategoryESet;

	/**
	 * The cached value of the '{@link #getErrorThrowerInfo()
	 * <em>Error Thrower Info</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getErrorThrowerInfo()
	 * @generated
	 * @ordered
	 */
	protected EList<ErrorThrowerInfo>						errorThrowerInfo;

	/**
	 * The cached value of the '{@link #getCatchErrorMappings()
	 * <em>Catch Error Mappings</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCatchErrorMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<CatchErrorMappings>						catchErrorMappings;

	/**
	 * The cached value of the '{@link #getConditionalParticipant()
	 * <em>Conditional Participant</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getConditionalParticipant()
	 * @generated
	 * @ordered
	 */
	protected EList<ConditionalParticipant>					conditionalParticipant;

	/**
	 * The default value of the '{@link #isGenerated() <em>Generated</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isGenerated()
	 * @generated
	 * @ordered
	 */
	protected static final boolean							GENERATED_EDEFAULT										= false;

	/**
	 * The cached value of the '{@link #isGenerated() <em>Generated</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isGenerated()
	 * @generated
	 * @ordered
	 */
	protected boolean										generated												= GENERATED_EDEFAULT;

	/**
	 * The default value of the '{@link #getReplyToActivityId() <em>Reply To Activity Id</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getReplyToActivityId()
	 * @generated
	 * @ordered
	 */
	protected static final String							REPLY_TO_ACTIVITY_ID_EDEFAULT							= null;

	/**
	 * The cached value of the '{@link #getReplyToActivityId() <em>Reply To Activity Id</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getReplyToActivityId()
	 * @generated
	 * @ordered
	 */
	protected String										replyToActivityId										= REPLY_TO_ACTIVITY_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTaskLibraryReference()
	 * <em>Task Library Reference</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTaskLibraryReference()
	 * @generated
	 * @ordered
	 */
	protected EList<TaskLibraryReference>					taskLibraryReference;

	/**
	 * The default value of the '{@link #isSetPerformerInProcess() <em>Set Performer In Process</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetPerformerInProcess()
	 * @generated
	 * @ordered
	 */
	protected static final boolean							SET_PERFORMER_IN_PROCESS_EDEFAULT						= false;

	/**
	 * The cached value of the '{@link #isSetPerformerInProcess() <em>Set Performer In Process</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetPerformerInProcess()
	 * @generated
	 * @ordered
	 */
	protected boolean										setPerformerInProcess									= SET_PERFORMER_IN_PROCESS_EDEFAULT;

	/**
	 * This is true if the Set Performer In Process attribute has been set. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	protected boolean										setPerformerInProcessESet;

	/**
	 * The default value of the '{@link #getEmbSubprocOtherStateHeight() <em>Emb Subproc Other State Height</em>}' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see #getEmbSubprocOtherStateHeight()
	 * @generated
	 * @ordered
	 */
	protected static final double							EMB_SUBPROC_OTHER_STATE_HEIGHT_EDEFAULT					= 0.0;

	/**
	 * The cached value of the '{@link #getEmbSubprocOtherStateHeight() <em>Emb Subproc Other State Height</em>}' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see #getEmbSubprocOtherStateHeight()
	 * @generated
	 * @ordered
	 */
	protected double										embSubprocOtherStateHeight								= EMB_SUBPROC_OTHER_STATE_HEIGHT_EDEFAULT;

	/**
	 * This is true if the Emb Subproc Other State Height attribute has been set.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean										embSubprocOtherStateHeightESet;

	/**
	 * The default value of the '{@link #getEmbSubprocOtherStateWidth() <em>Emb Subproc Other State Width</em>}' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see #getEmbSubprocOtherStateWidth()
	 * @generated
	 * @ordered
	 */
	protected static final double							EMB_SUBPROC_OTHER_STATE_WIDTH_EDEFAULT					= 0.0;

	/**
	 * The cached value of the '{@link #getEmbSubprocOtherStateWidth() <em>Emb Subproc Other State Width</em>}' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see #getEmbSubprocOtherStateWidth()
	 * @generated
	 * @ordered
	 */
	protected double										embSubprocOtherStateWidth								= EMB_SUBPROC_OTHER_STATE_WIDTH_EDEFAULT;

	/**
	 * This is true if the Emb Subproc Other State Width attribute has been set.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean										embSubprocOtherStateWidthESet;

	/**
	 * The cached value of the '{@link #getFormImplementation()
	 * <em>Form Implementation</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFormImplementation()
	 * @generated
	 * @ordered
	 */
	protected EList<FormImplementation>						formImplementation;

	/**
	 * The cached value of the '{@link #getParticipantQuery()
	 * <em>Participant Query</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getParticipantQuery()
	 * @generated
	 * @ordered
	 */
	protected EList<Expression>								participantQuery;

	/**
	 * The default value of the '{@link #getApiEndPointParticipant() <em>Api End Point Participant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getApiEndPointParticipant()
	 * @generated
	 * @ordered
	 */
	protected static final String							API_END_POINT_PARTICIPANT_EDEFAULT						= null;

	/**
	 * The cached value of the '{@link #getApiEndPointParticipant() <em>Api End Point Participant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getApiEndPointParticipant()
	 * @generated
	 * @ordered
	 */
	protected String										apiEndPointParticipant									= API_END_POINT_PARTICIPANT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFaultMessage() <em>Fault Message</em>}' containment reference list.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see #getFaultMessage()
	 * @generated
	 * @ordered
	 */
	protected EList<Message>								faultMessage;

	/**
	 * The default value of the '{@link #getRequestActivityId() <em>Request Activity Id</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getRequestActivityId()
	 * @generated
	 * @ordered
	 */
	protected static final String							REQUEST_ACTIVITY_ID_EDEFAULT							= null;

	/**
	 * The cached value of the '{@link #getRequestActivityId() <em>Request Activity Id</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getRequestActivityId()
	 * @generated
	 * @ordered
	 */
	protected String										requestActivityId										= REQUEST_ACTIVITY_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getBusinessProcess()
	 * <em>Business Process</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getBusinessProcess()
	 * @generated
	 * @ordered
	 */
	protected EList<BusinessProcess>						businessProcess;

	/**
	 * The cached value of the '{@link #getWsdlGeneration()
	 * <em>Wsdl Generation</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getWsdlGeneration()
	 * @generated
	 * @ordered
	 */
	protected EList<WsdlGeneration>							wsdlGeneration;

	/**
	 * The default value of the '{@link #isTargetPrimitiveProperty() <em>Target Primitive Property</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTargetPrimitiveProperty()
	 * @generated
	 * @ordered
	 */
	protected static final boolean							TARGET_PRIMITIVE_PROPERTY_EDEFAULT						= false;

	/**
	 * The cached value of the '{@link #isTargetPrimitiveProperty() <em>Target Primitive Property</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTargetPrimitiveProperty()
	 * @generated
	 * @ordered
	 */
	protected boolean										targetPrimitiveProperty									= TARGET_PRIMITIVE_PROPERTY_EDEFAULT;

	/**
	 * This is true if the Target Primitive Property attribute has been set.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean										targetPrimitivePropertyESet;

	/**
	 * The default value of the '{@link #isSourcePrimitiveProperty() <em>Source Primitive Property</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSourcePrimitiveProperty()
	 * @generated
	 * @ordered
	 */
	protected static final boolean							SOURCE_PRIMITIVE_PROPERTY_EDEFAULT						= false;

	/**
	 * The cached value of the '{@link #isSourcePrimitiveProperty() <em>Source Primitive Property</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSourcePrimitiveProperty()
	 * @generated
	 * @ordered
	 */
	protected boolean										sourcePrimitiveProperty									= SOURCE_PRIMITIVE_PROPERTY_EDEFAULT;

	/**
	 * This is true if the Source Primitive Property attribute has been set.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean										sourcePrimitivePropertyESet;

	/**
	 * The cached value of the '{@link #getDecisionService()
	 * <em>Decision Service</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDecisionService()
	 * @generated
	 * @ordered
	 */
	protected EList<SubFlow>								decisionService;

	/**
	 * The cached value of the '{@link #getParticipantSharedResource()
	 * <em>Participant Shared Resource</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getParticipantSharedResource()
	 * @generated
	 * @ordered
	 */
	protected EList<ParticipantSharedResource>				participantSharedResource;

	/**
	 * The default value of the '{@link #getXpdModelType() <em>Xpd Model Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getXpdModelType()
	 * @generated
	 * @ordered
	 */
	protected static final XpdModelType						XPD_MODEL_TYPE_EDEFAULT									= XpdModelType.PAGE_FLOW;

	/**
	 * The cached value of the '{@link #getXpdModelType() <em>Xpd Model Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getXpdModelType()
	 * @generated
	 * @ordered
	 */
	protected XpdModelType									xpdModelType											= XPD_MODEL_TYPE_EDEFAULT;

	/**
	 * This is true if the Xpd Model Type attribute has been set. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	protected boolean										xpdModelTypeESet;

	/**
	 * The default value of the '{@link #getFlowRoutingStyle() <em>Flow Routing Style</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getFlowRoutingStyle()
	 * @generated
	 * @ordered
	 */
	protected static final FlowRoutingStyle					FLOW_ROUTING_STYLE_EDEFAULT								= FlowRoutingStyle.UNCENTERED_ON_TASKS;

	/**
	 * The cached value of the '{@link #getFlowRoutingStyle() <em>Flow Routing Style</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getFlowRoutingStyle()
	 * @generated
	 * @ordered
	 */
	protected FlowRoutingStyle								flowRoutingStyle										= FLOW_ROUTING_STYLE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCalendarReference()
	 * <em>Calendar Reference</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCalendarReference()
	 * @generated
	 * @ordered
	 */
	protected EList<CalendarReference>						calendarReference;

	/**
	 * The default value of the '{@link #isNonCancelling() <em>Non Cancelling</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #isNonCancelling()
	 * @generated
	 * @ordered
	 */
	protected static final boolean							NON_CANCELLING_EDEFAULT									= false;

	/**
	 * The cached value of the '{@link #isNonCancelling() <em>Non Cancelling</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #isNonCancelling()
	 * @generated
	 * @ordered
	 */
	protected boolean										nonCancelling											= NON_CANCELLING_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRetry() <em>Retry</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getRetry()
	 * @generated
	 * @ordered
	 */
	protected EList<Retry>									retry;

	/**
	 * The default value of the '{@link #getActivityDeadlineEventId() <em>Activity Deadline Event Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActivityDeadlineEventId()
	 * @generated
	 * @ordered
	 */
	protected static final String							ACTIVITY_DEADLINE_EVENT_ID_EDEFAULT						= null;

	/**
	 * The cached value of the '{@link #getActivityDeadlineEventId() <em>Activity Deadline Event Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActivityDeadlineEventId()
	 * @generated
	 * @ordered
	 */
	protected String										activityDeadlineEventId									= ACTIVITY_DEADLINE_EVENT_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getStartStrategy() <em>Start Strategy</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getStartStrategy()
	 * @generated
	 * @ordered
	 */
	protected static final SubProcessStartStrategy			START_STRATEGY_EDEFAULT									= SubProcessStartStrategy.START_IMMEDIATELY;

	/**
	 * The cached value of the '{@link #getStartStrategy() <em>Start Strategy</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getStartStrategy()
	 * @generated
	 * @ordered
	 */
	protected SubProcessStartStrategy						startStrategy											= START_STRATEGY_EDEFAULT;

	/**
	 * The default value of the '{@link #isOverwriteAlreadyModifiedTaskData()
	 * <em>Overwrite Already Modified Task Data</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isOverwriteAlreadyModifiedTaskData()
	 * @generated
	 * @ordered
	 */
	protected static final boolean							OVERWRITE_ALREADY_MODIFIED_TASK_DATA_EDEFAULT			= false;

	/**
	 * The cached value of the '{@link #isOverwriteAlreadyModifiedTaskData()
	 * <em>Overwrite Already Modified Task Data</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isOverwriteAlreadyModifiedTaskData()
	 * @generated
	 * @ordered
	 */
	protected boolean										overwriteAlreadyModifiedTaskData						= OVERWRITE_ALREADY_MODIFIED_TASK_DATA_EDEFAULT;

	/**
	 * The default value of the '{@link #getEventHandlerFlowStrategy() <em>Event Handler Flow Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventHandlerFlowStrategy()
	 * @generated
	 * @ordered
	 */
	protected static final EventHandlerFlowStrategy			EVENT_HANDLER_FLOW_STRATEGY_EDEFAULT					= EventHandlerFlowStrategy.SERIALIZE_CONCURRENT;

	/**
	 * The cached value of the '{@link #getEventHandlerFlowStrategy() <em>Event Handler Flow Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventHandlerFlowStrategy()
	 * @generated
	 * @ordered
	 */
	protected EventHandlerFlowStrategy						eventHandlerFlowStrategy								= EVENT_HANDLER_FLOW_STRATEGY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getNamespacePrefixMap()
	 * <em>Namespace Prefix Map</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getNamespacePrefixMap()
	 * @generated
	 * @ordered
	 */
	protected EList<NamespacePrefixMap>						namespacePrefixMap;

	/**
	 * The default value of the '{@link #isSuspendResumeWithParent() <em>Suspend Resume With Parent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSuspendResumeWithParent()
	 * @generated
	 * @ordered
	 */
	protected static final boolean							SUSPEND_RESUME_WITH_PARENT_EDEFAULT						= false;

	/**
	 * The cached value of the '{@link #isSuspendResumeWithParent() <em>Suspend Resume With Parent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSuspendResumeWithParent()
	 * @generated
	 * @ordered
	 */
	protected boolean										suspendResumeWithParent									= SUSPEND_RESUME_WITH_PARENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getSystemErrorAction() <em>System Error Action</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getSystemErrorAction()
	 * @generated
	 * @ordered
	 */
	protected static final SystemErrorActionType			SYSTEM_ERROR_ACTION_EDEFAULT							= SystemErrorActionType.HALT;

	/**
	 * The cached value of the '{@link #getSystemErrorAction() <em>System Error Action</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getSystemErrorAction()
	 * @generated
	 * @ordered
	 */
	protected SystemErrorActionType							systemErrorAction										= SYSTEM_ERROR_ACTION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCorrelationTimeout()
	 * <em>Correlation Timeout</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCorrelationTimeout()
	 * @generated
	 * @ordered
	 */
	protected EList<ConstantPeriod>							correlationTimeout;

	/**
	 * The cached value of the '{@link #getValidationControl()
	 * <em>Validation Control</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getValidationControl()
	 * @generated
	 * @ordered
	 */
	protected EList<ValidationControl>						validationControl;

	/**
	 * The cached value of the '{@link #getReplyImmediateDataMappings()
	 * <em>Reply Immediate Data Mappings</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getReplyImmediateDataMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<ReplyImmediateDataMappings>				replyImmediateDataMappings;

	/**
	 * The default value of the '{@link #isBxUseUnqualifiedPropertyNames()
	 * <em>Bx Use Unqualified Property Names</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isBxUseUnqualifiedPropertyNames()
	 * @generated
	 * @ordered
	 */
	protected static final boolean							BX_USE_UNQUALIFIED_PROPERTY_NAMES_EDEFAULT				= false;

	/**
	 * The cached value of the '{@link #isBxUseUnqualifiedPropertyNames()
	 * <em>Bx Use Unqualified Property Names</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isBxUseUnqualifiedPropertyNames()
	 * @generated
	 * @ordered
	 */
	protected boolean										bxUseUnqualifiedPropertyNames							= BX_USE_UNQUALIFIED_PROPERTY_NAMES_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCaseRefType() <em>Case Ref Type</em>}
	 * ' containment reference list. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see #getCaseRefType()
	 * @generated
	 * @ordered
	 */
	protected EList<RecordType>								caseRefType;

	/**
	 * The cached value of the '{@link #getRESTServices() <em>REST Services</em>}' containment reference list.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see #getRESTServices()
	 * @generated
	 * @ordered
	 */
	protected EList<RESTServices>							restServices;

	/**
	 * The default value of the '{@link #isPublishAsRestService() <em>Publish As Rest Service</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPublishAsRestService()
	 * @generated
	 * @ordered
	 */
	protected static final boolean							PUBLISH_AS_REST_SERVICE_EDEFAULT						= false;

	/**
	 * The cached value of the '{@link #isPublishAsRestService() <em>Publish As Rest Service</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPublishAsRestService()
	 * @generated
	 * @ordered
	 */
	protected boolean										publishAsRestService									= PUBLISH_AS_REST_SERVICE_EDEFAULT;

	/**
	 * The default value of the '{@link #getRestActivityId() <em>Rest Activity Id</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getRestActivityId()
	 * @generated
	 * @ordered
	 */
	protected static final String							REST_ACTIVITY_ID_EDEFAULT								= null;

	/**
	 * The cached value of the '{@link #getRestActivityId() <em>Rest Activity Id</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getRestActivityId()
	 * @generated
	 * @ordered
	 */
	protected String										restActivityId											= REST_ACTIVITY_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDynamicOrganizationMappings()
	 * <em>Dynamic Organization Mappings</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDynamicOrganizationMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<DynamicOrganizationMappings>			dynamicOrganizationMappings;

	/**
	 * The default value of the '{@link #isSignalHandlerAsynchronous() <em>Signal Handler Asynchronous</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSignalHandlerAsynchronous()
	 * @generated
	 * @ordered
	 */
	protected static final boolean							SIGNAL_HANDLER_ASYNCHRONOUS_EDEFAULT					= false;

	/**
	 * The cached value of the '{@link #isSignalHandlerAsynchronous() <em>Signal Handler Asynchronous</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSignalHandlerAsynchronous()
	 * @generated
	 * @ordered
	 */
	protected boolean										signalHandlerAsynchronous								= SIGNAL_HANDLER_ASYNCHRONOUS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getGlobalDataOperation() <em>Global Data Operation</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGlobalDataOperation()
	 * @generated
	 * @ordered
	 */
	protected EList<GlobalDataOperation>					globalDataOperation;

	/**
	 * The cached value of the '{@link #getProcessDataWorkItemAttributeMappings() <em>Process Data Work Item Attribute Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getProcessDataWorkItemAttributeMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<ProcessDataWorkItemAttributeMappings>	processDataWorkItemAttributeMappings;

	/**
	 * The default value of the '{@link #isAllowUnqualifiedSubProcessIdentification() <em>Allow Unqualified Sub Process Identification</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAllowUnqualifiedSubProcessIdentification()
	 * @generated
	 * @ordered
	 */
	protected static final boolean							ALLOW_UNQUALIFIED_SUB_PROCESS_IDENTIFICATION_EDEFAULT	= false;

	/**
	 * The cached value of the '{@link #isAllowUnqualifiedSubProcessIdentification() <em>Allow Unqualified Sub Process Identification</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAllowUnqualifiedSubProcessIdentification()
	 * @generated
	 * @ordered
	 */
	protected boolean										allowUnqualifiedSubProcessIdentification				= ALLOW_UNQUALIFIED_SUB_PROCESS_IDENTIFICATION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getBpmRuntimeConfiguration() <em>Bpm Runtime Configuration</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBpmRuntimeConfiguration()
	 * @generated
	 * @ordered
	 */
	protected EList<BpmRuntimeConfiguration>				bpmRuntimeConfiguration;

	/**
	 * The default value of the '{@link #isIsCaseService() <em>Is Case Service</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsCaseService()
	 * @generated
	 * @ordered
	 */
	protected static final boolean							IS_CASE_SERVICE_EDEFAULT								= false;

	/**
	 * The cached value of the '{@link #isIsCaseService() <em>Is Case Service</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsCaseService()
	 * @generated
	 * @ordered
	 */
	protected boolean										isCaseService											= IS_CASE_SERVICE_EDEFAULT;

	/**
	 * This is true if the Is Case Service attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean										isCaseServiceESet;

	/**
	 * The cached value of the '{@link #getRequiredAccessPrivileges() <em>Required Access Privileges</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequiredAccessPrivileges()
	 * @generated
	 * @ordered
	 */
	protected EList<RequiredAccessPrivileges>				requiredAccessPrivileges;

	/**
	 * The cached value of the '{@link #getCaseService() <em>Case Service</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCaseService()
	 * @generated
	 * @ordered
	 */
	protected EList<CaseService>							caseService;

	/**
	 * The cached value of the '{@link #getAdHocTaskConfiguration() <em>Ad Hoc Task Configuration</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdHocTaskConfiguration()
	 * @generated
	 * @ordered
	 */
	protected EList<AdHocTaskConfigurationType>				adHocTaskConfiguration;

	/**
	 * The default value of the '{@link #isIsEventSubProcess() <em>Is Event Sub Process</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsEventSubProcess()
	 * @generated
	 * @ordered
	 */
	protected static final boolean							IS_EVENT_SUB_PROCESS_EDEFAULT							= false;

	/**
	 * The cached value of the '{@link #isIsEventSubProcess() <em>Is Event Sub Process</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsEventSubProcess()
	 * @generated
	 * @ordered
	 */
	protected boolean										isEventSubProcess										= IS_EVENT_SUB_PROCESS_EDEFAULT;

	/**
	 * This is true if the Is Event Sub Process attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean										isEventSubProcessESet;

	/**
	 * The default value of the '{@link #isNonInterruptingEvent() <em>Non Interrupting Event</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNonInterruptingEvent()
	 * @generated
	 * @ordered
	 */
	protected static final boolean							NON_INTERRUPTING_EVENT_EDEFAULT							= false;

	/**
	 * The cached value of the '{@link #isNonInterruptingEvent() <em>Non Interrupting Event</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNonInterruptingEvent()
	 * @generated
	 * @ordered
	 */
	protected boolean										nonInterruptingEvent									= NON_INTERRUPTING_EVENT_EDEFAULT;

	/**
	 * This is true if the Non Interrupting Event attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean										nonInterruptingEventESet;

	/**
	 * The default value of the '{@link #isCorrelateImmediately() <em>Correlate Immediately</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCorrelateImmediately()
	 * @generated
	 * @ordered
	 */
	protected static final boolean							CORRELATE_IMMEDIATELY_EDEFAULT							= false;

	/**
	 * The cached value of the '{@link #isCorrelateImmediately() <em>Correlate Immediately</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCorrelateImmediately()
	 * @generated
	 * @ordered
	 */
	protected boolean										correlateImmediately									= CORRELATE_IMMEDIATELY_EDEFAULT;

	/**
	 * The default value of the '{@link #getAsyncExecutionMode() <em>Async Execution Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAsyncExecutionMode()
	 * @generated
	 * @ordered
	 */
	protected static final AsyncExecutionMode				ASYNC_EXECUTION_MODE_EDEFAULT							= AsyncExecutionMode.ATTACHED;

	/**
	 * The cached value of the '{@link #getAsyncExecutionMode() <em>Async Execution Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAsyncExecutionMode()
	 * @generated
	 * @ordered
	 */
	protected AsyncExecutionMode							asyncExecutionMode										= ASYNC_EXECUTION_MODE_EDEFAULT;

	/**
	 * This is true if the Async Execution Mode attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean										asyncExecutionModeESet;

	/**
	 * The default value of the '{@link #getSignalType() <em>Signal Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSignalType()
	 * @generated
	 * @ordered
	 */
	protected static final SignalType						SIGNAL_TYPE_EDEFAULT									= SignalType.LOCAL;

	/**
	 * The cached value of the '{@link #getSignalType() <em>Signal Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSignalType()
	 * @generated
	 * @ordered
	 */
	protected SignalType									signalType												= SIGNAL_TYPE_EDEFAULT;

	/**
	 * This is true if the Signal Type attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean										signalTypeESet;

	/**
	 * The cached value of the '{@link #getServiceProcessConfiguration() <em>Service Process Configuration</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServiceProcessConfiguration()
	 * @generated
	 * @ordered
	 */
	protected EList<ServiceProcessConfiguration>			serviceProcessConfiguration;

	/**
	 * The default value of the '{@link #isLikeMapping() <em>Like Mapping</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLikeMapping()
	 * @generated
	 * @ordered
	 */
	protected static final boolean							LIKE_MAPPING_EDEFAULT									= false;

	/**
	 * The cached value of the '{@link #isLikeMapping() <em>Like Mapping</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLikeMapping()
	 * @generated
	 * @ordered
	 */
	protected boolean										likeMapping												= LIKE_MAPPING_EDEFAULT;

	/**
	 * The cached value of the '{@link #getScriptDataMapper() <em>Script Data Mapper</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScriptDataMapper()
	 * @generated
	 * @ordered
	 */
	protected EList<ScriptDataMapper>						scriptDataMapper;

	/**
	 * The cached value of the '{@link #getLikeMappingExclusions() <em>Like Mapping Exclusions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLikeMappingExclusions()
	 * @generated
	 * @ordered
	 */
	protected EList<LikeMappingExclusions>					likeMappingExclusions;

	/**
	 * The default value of the '{@link #getSourceContributorId() <em>Source Contributor Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceContributorId()
	 * @generated
	 * @ordered
	 */
	protected static final String							SOURCE_CONTRIBUTOR_ID_EDEFAULT							= null;

	/**
	 * The cached value of the '{@link #getSourceContributorId() <em>Source Contributor Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceContributorId()
	 * @generated
	 * @ordered
	 */
	protected String										sourceContributorId										= SOURCE_CONTRIBUTOR_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getTargetContributorId() <em>Target Contributor Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetContributorId()
	 * @generated
	 * @ordered
	 */
	protected static final String							TARGET_CONTRIBUTOR_ID_EDEFAULT							= null;

	/**
	 * The cached value of the '{@link #getTargetContributorId() <em>Target Contributor Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetContributorId()
	 * @generated
	 * @ordered
	 */
	protected String										targetContributorId										= TARGET_CONTRIBUTOR_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRestServiceOperation() <em>Rest Service Operation</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getRestServiceOperation()
	 * @generated
	 * @ordered
	 */
	protected EList<RestServiceOperation>					restServiceOperation;

	/**
	 * The cached value of the '{@link #getInputMappings() <em>Input Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInputMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<ScriptDataMapper>						inputMappings;

	/**
	 * The cached value of the '{@link #getOutputMappings() <em>Output Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<ScriptDataMapper>						outputMappings;

	/**
	 * The default value of the '{@link #getBusinessServicePublishType() <em>Business Service Publish Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBusinessServicePublishType()
	 * @generated
	 * @ordered
	 */
	protected static final BusinessServicePublishType		BUSINESS_SERVICE_PUBLISH_TYPE_EDEFAULT					= BusinessServicePublishType.DESKTOP;

	/**
	 * The cached value of the '{@link #getBusinessServicePublishType() <em>Business Service Publish Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBusinessServicePublishType()
	 * @generated
	 * @ordered
	 */
	protected BusinessServicePublishType					businessServicePublishType								= BUSINESS_SERVICE_PUBLISH_TYPE_EDEFAULT;

	/**
	 * This is true if the Business Service Publish Type attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean										businessServicePublishTypeESet;

	/**
	 * The default value of the '{@link #isSuppressMaxMappingsError() <em>Suppress Max Mappings Error</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSuppressMaxMappingsError()
	 * @generated
	 * @ordered
	 */
	protected static final boolean							SUPPRESS_MAX_MAPPINGS_ERROR_EDEFAULT					= false;

	/**
	 * The cached value of the '{@link #isSuppressMaxMappingsError() <em>Suppress Max Mappings Error</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSuppressMaxMappingsError()
	 * @generated
	 * @ordered
	 */
	protected boolean										suppressMaxMappingsError								= SUPPRESS_MAX_MAPPINGS_ERROR_EDEFAULT;

	/**
	 * The default value of the '{@link #getFieldFormat() <em>Field Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFieldFormat()
	 * @generated
	 * @ordered
	 */
	protected static final FieldFormat						FIELD_FORMAT_EDEFAULT									= FieldFormat.URI;

	/**
	 * The cached value of the '{@link #getFieldFormat() <em>Field Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFieldFormat()
	 * @generated
	 * @ordered
	 */
	protected FieldFormat									fieldFormat												= FIELD_FORMAT_EDEFAULT;

	/**
	 * The default value of the '{@link #getUseIn() <em>Use In</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUseIn()
	 * @generated
	 * @ordered
	 */
	protected static final LibraryFunctionUseIn				USE_IN_EDEFAULT											= LibraryFunctionUseIn.ALL;

	/**
	 * The cached value of the '{@link #getUseIn() <em>Use In</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUseIn()
	 * @generated
	 * @ordered
	 */
	protected LibraryFunctionUseIn							useIn													= USE_IN_EDEFAULT;

	/**
	 * This is true if the Use In attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean										useInESet;

	/**
	
	 * @generated
	 */
	protected DocumentRootImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return XpdExtensionPackage.Literals.DOCUMENT_ROOT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<String, String> getXMLNSPrefixMap()
	{
		if (xMLNSPrefixMap == null)
		{
			xMLNSPrefixMap = new EcoreEMap<String, String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY,
					EStringToStringMapEntryImpl.class, this, XpdExtensionPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		}
		return xMLNSPrefixMap;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<String, String> getXSISchemaLocation()
	{
		if (xSISchemaLocation == null)
		{
			xSISchemaLocation = new EcoreEMap<String, String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY,
					EStringToStringMapEntryImpl.class, this, XpdExtensionPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		}
		return xSISchemaLocation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getImplementationType()
	{
		return implementationType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setImplementationType(String newImplementationType)
	{
		String oldImplementationType = implementationType;
		implementationType = newImplementationType;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__IMPLEMENTATION_TYPE, oldImplementationType, implementationType));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<XpdExtDataObjectAttributes> getDataObjectAttributes()
	{
		if (dataObjectAttributes == null)
		{
			dataObjectAttributes = new EObjectContainmentEList<XpdExtDataObjectAttributes>(
					XpdExtDataObjectAttributes.class, this, XpdExtensionPackage.DOCUMENT_ROOT__DATA_OBJECT_ATTRIBUTES);
		}
		return dataObjectAttributes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<XpdExtAttributes> getExtendedAttributes()
	{
		if (extendedAttributes == null)
		{
			extendedAttributes = new EObjectContainmentEList<XpdExtAttributes>(XpdExtAttributes.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__EXTENDED_ATTRIBUTES);
		}
		return extendedAttributes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isContinueOnTimeout()
	{
		return continueOnTimeout;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setContinueOnTimeout(boolean newContinueOnTimeout)
	{
		boolean oldContinueOnTimeout = continueOnTimeout;
		continueOnTimeout = newContinueOnTimeout;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__CONTINUE_ON_TIMEOUT, oldContinueOnTimeout, continueOnTimeout));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getAlias()
	{
		return alias;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAlias(String newAlias)
	{
		String oldAlias = alias;
		alias = newAlias;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__ALIAS, oldAlias, alias));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ConstantPeriod> getConstantPeriod()
	{
		if (constantPeriod == null)
		{
			constantPeriod = new EObjectContainmentEList<ConstantPeriod>(ConstantPeriod.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__CONSTANT_PERIOD);
		}
		return constantPeriod;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<UserTaskScripts> getUserTaskScripts()
	{
		if (userTaskScripts == null)
		{
			userTaskScripts = new EObjectContainmentEList<UserTaskScripts>(UserTaskScripts.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__USER_TASK_SCRIPTS);
		}
		return userTaskScripts;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Audit> getAudit()
	{
		// TODO: implement this method to return the 'Audit' containment reference list
		// Ensure that you remove @generated or mark it @generated NOT
		// The list is expected to implement org.eclipse.emf.ecore.util.InternalEList and org.eclipse.emf.ecore.EStructuralFeature.Setting
		// so it's likely that an appropriate subclass of org.eclipse.emf.ecore.util.EcoreEList should be used.
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ScriptInformation> getScript()
	{
		if (script == null)
		{
			script = new EObjectContainmentEList<ScriptInformation>(ScriptInformation.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__SCRIPT);
		}
		return script;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isReplyImmediately()
	{
		return replyImmediately;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReplyImmediately(boolean newReplyImmediately)
	{
		boolean oldReplyImmediately = replyImmediately;
		replyImmediately = newReplyImmediately;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__REPLY_IMMEDIATELY, oldReplyImmediately, replyImmediately));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<InitialValues> getInitialValues()
	{
		if (initialValues == null)
		{
			initialValues = new EObjectContainmentEList<InitialValues>(InitialValues.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__INITIAL_VALUES);
		}
		return initialValues;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AssociatedCorrelationFields> getAssociatedCorrelationFields()
	{
		if (associatedCorrelationFields == null)
		{
			associatedCorrelationFields = new EObjectContainmentEList<AssociatedCorrelationFields>(
					AssociatedCorrelationFields.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__ASSOCIATED_CORRELATION_FIELDS);
		}
		return associatedCorrelationFields;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AssociatedParameters> getAssociatedParameters()
	{
		if (associatedParameters == null)
		{
			associatedParameters = new EObjectContainmentEList<AssociatedParameters>(AssociatedParameters.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__ASSOCIATED_PARAMETERS);
		}
		return associatedParameters;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ImplementedInterface> getImplementedInterface()
	{
		if (implementedInterface == null)
		{
			implementedInterface = new EObjectContainmentEList<ImplementedInterface>(ImplementedInterface.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__IMPLEMENTED_INTERFACE);
		}
		return implementedInterface;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ProcessInterfaces> getProcessInterfaces()
	{
		if (processInterfaces == null)
		{
			processInterfaces = new EObjectContainmentEList<ProcessInterfaces>(ProcessInterfaces.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__PROCESS_INTERFACES);
		}
		return processInterfaces;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<WsdlEventAssociation> getWsdlEventAssociation()
	{
		if (wsdlEventAssociation == null)
		{
			wsdlEventAssociation = new EObjectContainmentEList<WsdlEventAssociation>(WsdlEventAssociation.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__WSDL_EVENT_ASSOCIATION);
		}
		return wsdlEventAssociation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isInlineSubProcess()
	{
		return inlineSubProcess;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setInlineSubProcess(boolean newInlineSubProcess)
	{
		boolean oldInlineSubProcess = inlineSubProcess;
		inlineSubProcess = newInlineSubProcess;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__INLINE_SUB_PROCESS, oldInlineSubProcess, inlineSubProcess));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDocumentationURL()
	{
		return documentationURL;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDocumentationURL(String newDocumentationURL)
	{
		String oldDocumentationURL = documentationURL;
		documentationURL = newDocumentationURL;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__DOCUMENTATION_URL, oldDocumentationURL, documentationURL));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getImplements()
	{
		return implements_;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setImplements(String newImplements)
	{
		String oldImplements = implements_;
		implements_ = newImplements;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__IMPLEMENTS, oldImplements, implements_));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<MultiInstanceScripts> getMultiInstanceScripts()
	{
		if (multiInstanceScripts == null)
		{
			multiInstanceScripts = new EObjectContainmentEList<MultiInstanceScripts>(MultiInstanceScripts.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__MULTI_INSTANCE_SCRIPTS);
		}
		return multiInstanceScripts;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getProcessIdentifierField()
	{
		return processIdentifierField;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setProcessIdentifierField(String newProcessIdentifierField)
	{
		String oldProcessIdentifierField = processIdentifierField;
		processIdentifierField = newProcessIdentifierField;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__PROCESS_IDENTIFIER_FIELD, oldProcessIdentifierField,
				processIdentifierField));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Expression> getExpression()
	{
		if (expression == null)
		{
			expression = new EObjectContainmentEList<Expression>(Expression.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__EXPRESSION);
		}
		return expression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Visibility getVisibility()
	{
		return visibility;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setVisibility(Visibility newVisibility)
	{
		Visibility oldVisibility = visibility;
		visibility = newVisibility == null ? VISIBILITY_EDEFAULT : newVisibility;
		boolean oldVisibilityESet = visibilityESet;
		visibilityESet = true;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__VISIBILITY, oldVisibility, visibility, !oldVisibilityESet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetVisibility()
	{
		Visibility oldVisibility = visibility;
		boolean oldVisibilityESet = visibilityESet;
		visibility = VISIBILITY_EDEFAULT;
		visibilityESet = false;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.UNSET,
				XpdExtensionPackage.DOCUMENT_ROOT__VISIBILITY, oldVisibility, VISIBILITY_EDEFAULT, oldVisibilityESet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetVisibility()
	{
		return visibilityESet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSecurityProfile()
	{
		return securityProfile;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSecurityProfile(String newSecurityProfile)
	{
		String oldSecurityProfile = securityProfile;
		securityProfile = newSecurityProfile;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__SECURITY_PROFILE, oldSecurityProfile, securityProfile));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLanguage()
	{
		return language;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLanguage(String newLanguage)
	{
		String oldLanguage = language;
		language = newLanguage;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__LANGUAGE, oldLanguage, language));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<InitialParameterValue> getInitialParameterValue()
	{
		if (initialParameterValue == null)
		{
			initialParameterValue = new EObjectContainmentEList<InitialParameterValue>(InitialParameterValue.class,
					this, XpdExtensionPackage.DOCUMENT_ROOT__INITIAL_PARAMETER_VALUE);
		}
		return initialParameterValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isInitialValueMapping()
	{
		return initialValueMapping;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setInitialValueMapping(boolean newInitialValueMapping)
	{
		boolean oldInitialValueMapping = initialValueMapping;
		initialValueMapping = newInitialValueMapping;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__INITIAL_VALUE_MAPPING, oldInitialValueMapping, initialValueMapping));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<PortTypeOperation> getPortTypeOperation()
	{
		if (portTypeOperation == null)
		{
			portTypeOperation = new EObjectContainmentEList<PortTypeOperation>(PortTypeOperation.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__PORT_TYPE_OPERATION);
		}
		return portTypeOperation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTransport()
	{
		return transport;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTransport(String newTransport)
	{
		String oldTransport = transport;
		transport = newTransport;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__TRANSPORT, oldTransport, transport));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isIsChained()
	{
		return isChained;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIsChained(boolean newIsChained)
	{
		boolean oldIsChained = isChained;
		isChained = newIsChained;
		boolean oldIsChainedESet = isChainedESet;
		isChainedESet = true;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__IS_CHAINED, oldIsChained, isChained, !oldIsChainedESet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetIsChained()
	{
		boolean oldIsChained = isChained;
		boolean oldIsChainedESet = isChainedESet;
		isChained = IS_CHAINED_EDEFAULT;
		isChainedESet = false;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.UNSET,
				XpdExtensionPackage.DOCUMENT_ROOT__IS_CHAINED, oldIsChained, IS_CHAINED_EDEFAULT, oldIsChainedESet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetIsChained()
	{
		return isChainedESet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ExternalReference> getExternalReference()
	{
		if (externalReference == null)
		{
			externalReference = new EObjectContainmentEList<ExternalReference>(ExternalReference.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__EXTERNAL_REFERENCE);
		}
		return externalReference;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ProcessResourcePatterns> getProcessResourcePatterns()
	{
		if (processResourcePatterns == null)
		{
			processResourcePatterns = new EObjectContainmentEList<ProcessResourcePatterns>(
					ProcessResourcePatterns.class, this, XpdExtensionPackage.DOCUMENT_ROOT__PROCESS_RESOURCE_PATTERNS);
		}
		return processResourcePatterns;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<EventHandlerInitialisers> getEventHandlerInitialisers()
	{
		if (eventHandlerInitialisers == null)
		{
			eventHandlerInitialisers = new EObjectContainmentEList<EventHandlerInitialisers>(
					EventHandlerInitialisers.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__EVENT_HANDLER_INITIALISERS);
		}
		return eventHandlerInitialisers;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ActivityResourcePatterns> getActivityResourcePatterns()
	{
		if (activityResourcePatterns == null)
		{
			activityResourcePatterns = new EObjectContainmentEList<ActivityResourcePatterns>(
					ActivityResourcePatterns.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__ACTIVITY_RESOURCE_PATTERNS);
		}
		return activityResourcePatterns;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isRequireNewTransaction()
	{
		return requireNewTransaction;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRequireNewTransaction(boolean newRequireNewTransaction)
	{
		boolean oldRequireNewTransaction = requireNewTransaction;
		requireNewTransaction = newRequireNewTransaction;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__REQUIRE_NEW_TRANSACTION, oldRequireNewTransaction,
				requireNewTransaction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DocumentOperation> getDocumentOperation()
	{
		if (documentOperation == null)
		{
			documentOperation = new EObjectContainmentEList<DocumentOperation>(DocumentOperation.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__DOCUMENT_OPERATION);
		}
		return documentOperation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<DurationCalculation> getDurationCalculation()
	{
		if (durationCalculation == null)
		{
			durationCalculation = new EObjectContainmentEList<DurationCalculation>(DurationCalculation.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__DURATION_CALCULATION);
		}
		return durationCalculation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Discriminator> getDiscriminator()
	{
		if (discriminator == null)
		{
			discriminator = new EObjectContainmentEList<Discriminator>(Discriminator.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__DISCRIMINATOR);
		}
		return discriminator;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDisplayName()
	{
		return displayName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDisplayName(String newDisplayName)
	{
		String oldDisplayName = displayName;
		displayName = newDisplayName;
		boolean oldDisplayNameESet = displayNameESet;
		displayNameESet = true;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__DISPLAY_NAME, oldDisplayName, displayName, !oldDisplayNameESet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetDisplayName()
	{
		String oldDisplayName = displayName;
		boolean oldDisplayNameESet = displayNameESet;
		displayName = DISPLAY_NAME_EDEFAULT;
		displayNameESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, XpdExtensionPackage.DOCUMENT_ROOT__DISPLAY_NAME,
					oldDisplayName, DISPLAY_NAME_EDEFAULT, oldDisplayNameESet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetDisplayName()
	{
		return displayNameESet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CatchThrow getCatchThrow()
	{
		return catchThrow;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCatchThrow(CatchThrow newCatchThrow)
	{
		CatchThrow oldCatchThrow = catchThrow;
		catchThrow = newCatchThrow == null ? CATCH_THROW_EDEFAULT : newCatchThrow;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__CATCH_THROW, oldCatchThrow, catchThrow));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isIsRemote()
	{
		return isRemote;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIsRemote(boolean newIsRemote)
	{
		boolean oldIsRemote = isRemote;
		isRemote = newIsRemote;
		boolean oldIsRemoteESet = isRemoteESet;
		isRemoteESet = true;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__IS_REMOTE, oldIsRemote, isRemote, !oldIsRemoteESet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetIsRemote()
	{
		boolean oldIsRemote = isRemote;
		boolean oldIsRemoteESet = isRemoteESet;
		isRemote = IS_REMOTE_EDEFAULT;
		isRemoteESet = false;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.UNSET,
				XpdExtensionPackage.DOCUMENT_ROOT__IS_REMOTE, oldIsRemote, IS_REMOTE_EDEFAULT, oldIsRemoteESet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetIsRemote()
	{
		return isRemoteESet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<CorrelationDataMappings> getCorrelationDataMappings()
	{
		if (correlationDataMappings == null)
		{
			correlationDataMappings = new EObjectContainmentEList<CorrelationDataMappings>(
					CorrelationDataMappings.class, this, XpdExtensionPackage.DOCUMENT_ROOT__CORRELATION_DATA_MAPPINGS);
		}
		return correlationDataMappings;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TransformScript> getTransformScript()
	{
		if (transformScript == null)
		{
			transformScript = new EObjectContainmentEList<TransformScript>(TransformScript.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__TRANSFORM_SCRIPT);
		}
		return transformScript;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isPublishAsBusinessService()
	{
		return publishAsBusinessService;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPublishAsBusinessService(boolean newPublishAsBusinessService)
	{
		boolean oldPublishAsBusinessService = publishAsBusinessService;
		publishAsBusinessService = newPublishAsBusinessService;
		boolean oldPublishAsBusinessServiceESet = publishAsBusinessServiceESet;
		publishAsBusinessServiceESet = true;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__PUBLISH_AS_BUSINESS_SERVICE, oldPublishAsBusinessService,
				publishAsBusinessService, !oldPublishAsBusinessServiceESet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetPublishAsBusinessService()
	{
		boolean oldPublishAsBusinessService = publishAsBusinessService;
		boolean oldPublishAsBusinessServiceESet = publishAsBusinessServiceESet;
		publishAsBusinessService = PUBLISH_AS_BUSINESS_SERVICE_EDEFAULT;
		publishAsBusinessServiceESet = false;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.UNSET,
				XpdExtensionPackage.DOCUMENT_ROOT__PUBLISH_AS_BUSINESS_SERVICE, oldPublishAsBusinessService,
				PUBLISH_AS_BUSINESS_SERVICE_EDEFAULT, oldPublishAsBusinessServiceESet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetPublishAsBusinessService()
	{
		return publishAsBusinessServiceESet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getBusinessServiceCategory()
	{
		return businessServiceCategory;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBusinessServiceCategory(String newBusinessServiceCategory)
	{
		String oldBusinessServiceCategory = businessServiceCategory;
		businessServiceCategory = newBusinessServiceCategory;
		boolean oldBusinessServiceCategoryESet = businessServiceCategoryESet;
		businessServiceCategoryESet = true;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__BUSINESS_SERVICE_CATEGORY, oldBusinessServiceCategory,
				businessServiceCategory, !oldBusinessServiceCategoryESet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetBusinessServiceCategory()
	{
		String oldBusinessServiceCategory = businessServiceCategory;
		boolean oldBusinessServiceCategoryESet = businessServiceCategoryESet;
		businessServiceCategory = BUSINESS_SERVICE_CATEGORY_EDEFAULT;
		businessServiceCategoryESet = false;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.UNSET,
				XpdExtensionPackage.DOCUMENT_ROOT__BUSINESS_SERVICE_CATEGORY, oldBusinessServiceCategory,
				BUSINESS_SERVICE_CATEGORY_EDEFAULT, oldBusinessServiceCategoryESet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetBusinessServiceCategory()
	{
		return businessServiceCategoryESet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ErrorThrowerInfo> getErrorThrowerInfo()
	{
		if (errorThrowerInfo == null)
		{
			errorThrowerInfo = new EObjectContainmentEList<ErrorThrowerInfo>(ErrorThrowerInfo.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__ERROR_THROWER_INFO);
		}
		return errorThrowerInfo;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<CatchErrorMappings> getCatchErrorMappings()
	{
		if (catchErrorMappings == null)
		{
			catchErrorMappings = new EObjectContainmentEList<CatchErrorMappings>(CatchErrorMappings.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__CATCH_ERROR_MAPPINGS);
		}
		return catchErrorMappings;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ConditionalParticipant> getConditionalParticipant()
	{
		if (conditionalParticipant == null)
		{
			conditionalParticipant = new EObjectContainmentEList<ConditionalParticipant>(ConditionalParticipant.class,
					this, XpdExtensionPackage.DOCUMENT_ROOT__CONDITIONAL_PARTICIPANT);
		}
		return conditionalParticipant;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isGenerated()
	{
		return generated;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGenerated(boolean newGenerated)
	{
		boolean oldGenerated = generated;
		generated = newGenerated;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__GENERATED, oldGenerated, generated));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getReplyToActivityId()
	{
		return replyToActivityId;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReplyToActivityId(String newReplyToActivityId)
	{
		String oldReplyToActivityId = replyToActivityId;
		replyToActivityId = newReplyToActivityId;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__REPLY_TO_ACTIVITY_ID, oldReplyToActivityId, replyToActivityId));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TaskLibraryReference> getTaskLibraryReference()
	{
		if (taskLibraryReference == null)
		{
			taskLibraryReference = new EObjectContainmentEList<TaskLibraryReference>(TaskLibraryReference.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__TASK_LIBRARY_REFERENCE);
		}
		return taskLibraryReference;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetPerformerInProcess()
	{
		return setPerformerInProcess;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSetPerformerInProcess(boolean newSetPerformerInProcess)
	{
		boolean oldSetPerformerInProcess = setPerformerInProcess;
		setPerformerInProcess = newSetPerformerInProcess;
		boolean oldSetPerformerInProcessESet = setPerformerInProcessESet;
		setPerformerInProcessESet = true;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__SET_PERFORMER_IN_PROCESS, oldSetPerformerInProcess,
				setPerformerInProcess, !oldSetPerformerInProcessESet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetSetPerformerInProcess()
	{
		boolean oldSetPerformerInProcess = setPerformerInProcess;
		boolean oldSetPerformerInProcessESet = setPerformerInProcessESet;
		setPerformerInProcess = SET_PERFORMER_IN_PROCESS_EDEFAULT;
		setPerformerInProcessESet = false;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.UNSET,
				XpdExtensionPackage.DOCUMENT_ROOT__SET_PERFORMER_IN_PROCESS, oldSetPerformerInProcess,
				SET_PERFORMER_IN_PROCESS_EDEFAULT, oldSetPerformerInProcessESet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetSetPerformerInProcess()
	{
		return setPerformerInProcessESet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getEmbSubprocOtherStateHeight()
	{
		return embSubprocOtherStateHeight;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEmbSubprocOtherStateHeight(double newEmbSubprocOtherStateHeight)
	{
		double oldEmbSubprocOtherStateHeight = embSubprocOtherStateHeight;
		embSubprocOtherStateHeight = newEmbSubprocOtherStateHeight;
		boolean oldEmbSubprocOtherStateHeightESet = embSubprocOtherStateHeightESet;
		embSubprocOtherStateHeightESet = true;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__EMB_SUBPROC_OTHER_STATE_HEIGHT, oldEmbSubprocOtherStateHeight,
				embSubprocOtherStateHeight, !oldEmbSubprocOtherStateHeightESet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetEmbSubprocOtherStateHeight()
	{
		double oldEmbSubprocOtherStateHeight = embSubprocOtherStateHeight;
		boolean oldEmbSubprocOtherStateHeightESet = embSubprocOtherStateHeightESet;
		embSubprocOtherStateHeight = EMB_SUBPROC_OTHER_STATE_HEIGHT_EDEFAULT;
		embSubprocOtherStateHeightESet = false;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.UNSET,
				XpdExtensionPackage.DOCUMENT_ROOT__EMB_SUBPROC_OTHER_STATE_HEIGHT, oldEmbSubprocOtherStateHeight,
				EMB_SUBPROC_OTHER_STATE_HEIGHT_EDEFAULT, oldEmbSubprocOtherStateHeightESet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetEmbSubprocOtherStateHeight()
	{
		return embSubprocOtherStateHeightESet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getEmbSubprocOtherStateWidth()
	{
		return embSubprocOtherStateWidth;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEmbSubprocOtherStateWidth(double newEmbSubprocOtherStateWidth)
	{
		double oldEmbSubprocOtherStateWidth = embSubprocOtherStateWidth;
		embSubprocOtherStateWidth = newEmbSubprocOtherStateWidth;
		boolean oldEmbSubprocOtherStateWidthESet = embSubprocOtherStateWidthESet;
		embSubprocOtherStateWidthESet = true;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__EMB_SUBPROC_OTHER_STATE_WIDTH, oldEmbSubprocOtherStateWidth,
				embSubprocOtherStateWidth, !oldEmbSubprocOtherStateWidthESet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetEmbSubprocOtherStateWidth()
	{
		double oldEmbSubprocOtherStateWidth = embSubprocOtherStateWidth;
		boolean oldEmbSubprocOtherStateWidthESet = embSubprocOtherStateWidthESet;
		embSubprocOtherStateWidth = EMB_SUBPROC_OTHER_STATE_WIDTH_EDEFAULT;
		embSubprocOtherStateWidthESet = false;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.UNSET,
				XpdExtensionPackage.DOCUMENT_ROOT__EMB_SUBPROC_OTHER_STATE_WIDTH, oldEmbSubprocOtherStateWidth,
				EMB_SUBPROC_OTHER_STATE_WIDTH_EDEFAULT, oldEmbSubprocOtherStateWidthESet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetEmbSubprocOtherStateWidth()
	{
		return embSubprocOtherStateWidthESet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<FormImplementation> getFormImplementation()
	{
		if (formImplementation == null)
		{
			formImplementation = new EObjectContainmentEList<FormImplementation>(FormImplementation.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__FORM_IMPLEMENTATION);
		}
		return formImplementation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Expression> getParticipantQuery()
	{
		if (participantQuery == null)
		{
			participantQuery = new EObjectContainmentEList<Expression>(Expression.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__PARTICIPANT_QUERY);
		}
		return participantQuery;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getApiEndPointParticipant()
	{
		return apiEndPointParticipant;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setApiEndPointParticipant(String newApiEndPointParticipant)
	{
		String oldApiEndPointParticipant = apiEndPointParticipant;
		apiEndPointParticipant = newApiEndPointParticipant;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__API_END_POINT_PARTICIPANT, oldApiEndPointParticipant,
				apiEndPointParticipant));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Message> getFaultMessage()
	{
		if (faultMessage == null)
		{
			faultMessage = new EObjectContainmentEList<Message>(Message.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__FAULT_MESSAGE);
		}
		return faultMessage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getRequestActivityId()
	{
		return requestActivityId;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRequestActivityId(String newRequestActivityId)
	{
		String oldRequestActivityId = requestActivityId;
		requestActivityId = newRequestActivityId;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__REQUEST_ACTIVITY_ID, oldRequestActivityId, requestActivityId));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<BusinessProcess> getBusinessProcess()
	{
		if (businessProcess == null)
		{
			businessProcess = new EObjectContainmentEList<BusinessProcess>(BusinessProcess.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__BUSINESS_PROCESS);
		}
		return businessProcess;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<WsdlGeneration> getWsdlGeneration()
	{
		if (wsdlGeneration == null)
		{
			wsdlGeneration = new EObjectContainmentEList<WsdlGeneration>(WsdlGeneration.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__WSDL_GENERATION);
		}
		return wsdlGeneration;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isTargetPrimitiveProperty()
	{
		return targetPrimitiveProperty;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTargetPrimitiveProperty(boolean newTargetPrimitiveProperty)
	{
		boolean oldTargetPrimitiveProperty = targetPrimitiveProperty;
		targetPrimitiveProperty = newTargetPrimitiveProperty;
		boolean oldTargetPrimitivePropertyESet = targetPrimitivePropertyESet;
		targetPrimitivePropertyESet = true;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__TARGET_PRIMITIVE_PROPERTY, oldTargetPrimitiveProperty,
				targetPrimitiveProperty, !oldTargetPrimitivePropertyESet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetTargetPrimitiveProperty()
	{
		boolean oldTargetPrimitiveProperty = targetPrimitiveProperty;
		boolean oldTargetPrimitivePropertyESet = targetPrimitivePropertyESet;
		targetPrimitiveProperty = TARGET_PRIMITIVE_PROPERTY_EDEFAULT;
		targetPrimitivePropertyESet = false;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.UNSET,
				XpdExtensionPackage.DOCUMENT_ROOT__TARGET_PRIMITIVE_PROPERTY, oldTargetPrimitiveProperty,
				TARGET_PRIMITIVE_PROPERTY_EDEFAULT, oldTargetPrimitivePropertyESet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetTargetPrimitiveProperty()
	{
		return targetPrimitivePropertyESet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSourcePrimitiveProperty()
	{
		return sourcePrimitiveProperty;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSourcePrimitiveProperty(boolean newSourcePrimitiveProperty)
	{
		boolean oldSourcePrimitiveProperty = sourcePrimitiveProperty;
		sourcePrimitiveProperty = newSourcePrimitiveProperty;
		boolean oldSourcePrimitivePropertyESet = sourcePrimitivePropertyESet;
		sourcePrimitivePropertyESet = true;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__SOURCE_PRIMITIVE_PROPERTY, oldSourcePrimitiveProperty,
				sourcePrimitiveProperty, !oldSourcePrimitivePropertyESet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetSourcePrimitiveProperty()
	{
		boolean oldSourcePrimitiveProperty = sourcePrimitiveProperty;
		boolean oldSourcePrimitivePropertyESet = sourcePrimitivePropertyESet;
		sourcePrimitiveProperty = SOURCE_PRIMITIVE_PROPERTY_EDEFAULT;
		sourcePrimitivePropertyESet = false;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.UNSET,
				XpdExtensionPackage.DOCUMENT_ROOT__SOURCE_PRIMITIVE_PROPERTY, oldSourcePrimitiveProperty,
				SOURCE_PRIMITIVE_PROPERTY_EDEFAULT, oldSourcePrimitivePropertyESet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetSourcePrimitiveProperty()
	{
		return sourcePrimitivePropertyESet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<SubFlow> getDecisionService()
	{
		if (decisionService == null)
		{
			decisionService = new EObjectContainmentEList<SubFlow>(SubFlow.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__DECISION_SERVICE);
		}
		return decisionService;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ParticipantSharedResource> getParticipantSharedResource()
	{
		if (participantSharedResource == null)
		{
			participantSharedResource = new EObjectContainmentEList<ParticipantSharedResource>(
					ParticipantSharedResource.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__PARTICIPANT_SHARED_RESOURCE);
		}
		return participantSharedResource;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public XpdModelType getXpdModelType()
	{
		return xpdModelType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setXpdModelType(XpdModelType newXpdModelType)
	{
		XpdModelType oldXpdModelType = xpdModelType;
		xpdModelType = newXpdModelType == null ? XPD_MODEL_TYPE_EDEFAULT : newXpdModelType;
		boolean oldXpdModelTypeESet = xpdModelTypeESet;
		xpdModelTypeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.DOCUMENT_ROOT__XPD_MODEL_TYPE,
					oldXpdModelType, xpdModelType, !oldXpdModelTypeESet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetXpdModelType()
	{
		XpdModelType oldXpdModelType = xpdModelType;
		boolean oldXpdModelTypeESet = xpdModelTypeESet;
		xpdModelType = XPD_MODEL_TYPE_EDEFAULT;
		xpdModelTypeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, XpdExtensionPackage.DOCUMENT_ROOT__XPD_MODEL_TYPE,
					oldXpdModelType, XPD_MODEL_TYPE_EDEFAULT, oldXpdModelTypeESet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<RecordType> getCaseRefType()
	{
		if (caseRefType == null)
		{
			caseRefType = new EObjectContainmentEList<RecordType>(RecordType.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__CASE_REF_TYPE);
		}
		return caseRefType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<RESTServices> getRESTServices()
	{
		if (restServices == null)
		{
			restServices = new EObjectContainmentEList<RESTServices>(RESTServices.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__REST_SERVICES);
		}
		return restServices;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isPublishAsRestService()
	{
		return publishAsRestService;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPublishAsRestService(boolean newPublishAsRestService)
	{
		boolean oldPublishAsRestService = publishAsRestService;
		publishAsRestService = newPublishAsRestService;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__PUBLISH_AS_REST_SERVICE, oldPublishAsRestService,
				publishAsRestService));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getRestActivityId()
	{
		return restActivityId;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRestActivityId(String newRestActivityId)
	{
		String oldRestActivityId = restActivityId;
		restActivityId = newRestActivityId;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__REST_ACTIVITY_ID, oldRestActivityId, restActivityId));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<RescheduleTimerScript> getRescheduleTimerScript()
	{
		// TODO: implement this method to return the 'Reschedule Timer Script' containment reference list
		// Ensure that you remove @generated or mark it @generated NOT
		// The list is expected to implement org.eclipse.emf.ecore.util.InternalEList and org.eclipse.emf.ecore.EStructuralFeature.Setting
		// so it's likely that an appropriate subclass of org.eclipse.emf.ecore.util.EcoreEList should be used.
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<DynamicOrganizationMappings> getDynamicOrganizationMappings()
	{
		if (dynamicOrganizationMappings == null)
		{
			dynamicOrganizationMappings = new EObjectContainmentEList<DynamicOrganizationMappings>(
					DynamicOrganizationMappings.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__DYNAMIC_ORGANIZATION_MAPPINGS);
		}
		return dynamicOrganizationMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSignalHandlerAsynchronous()
	{
		return signalHandlerAsynchronous;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSignalHandlerAsynchronous(boolean newSignalHandlerAsynchronous)
	{
		boolean oldSignalHandlerAsynchronous = signalHandlerAsynchronous;
		signalHandlerAsynchronous = newSignalHandlerAsynchronous;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__SIGNAL_HANDLER_ASYNCHRONOUS, oldSignalHandlerAsynchronous,
				signalHandlerAsynchronous));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GlobalDataOperation> getGlobalDataOperation()
	{
		if (globalDataOperation == null)
		{
			globalDataOperation = new EObjectContainmentEList<GlobalDataOperation>(GlobalDataOperation.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__GLOBAL_DATA_OPERATION);
		}
		return globalDataOperation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ProcessDataWorkItemAttributeMappings> getProcessDataWorkItemAttributeMappings()
	{
		if (processDataWorkItemAttributeMappings == null)
		{
			processDataWorkItemAttributeMappings = new EObjectContainmentEList<ProcessDataWorkItemAttributeMappings>(
					ProcessDataWorkItemAttributeMappings.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS);
		}
		return processDataWorkItemAttributeMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAllowUnqualifiedSubProcessIdentification()
	{
		return allowUnqualifiedSubProcessIdentification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAllowUnqualifiedSubProcessIdentification(boolean newAllowUnqualifiedSubProcessIdentification)
	{
		boolean oldAllowUnqualifiedSubProcessIdentification = allowUnqualifiedSubProcessIdentification;
		allowUnqualifiedSubProcessIdentification = newAllowUnqualifiedSubProcessIdentification;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__ALLOW_UNQUALIFIED_SUB_PROCESS_IDENTIFICATION,
				oldAllowUnqualifiedSubProcessIdentification, allowUnqualifiedSubProcessIdentification));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<BpmRuntimeConfiguration> getBpmRuntimeConfiguration()
	{
		if (bpmRuntimeConfiguration == null)
		{
			bpmRuntimeConfiguration = new EObjectContainmentEList<BpmRuntimeConfiguration>(
					BpmRuntimeConfiguration.class, this, XpdExtensionPackage.DOCUMENT_ROOT__BPM_RUNTIME_CONFIGURATION);
		}
		return bpmRuntimeConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsCaseService()
	{
		return isCaseService;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsCaseService(boolean newIsCaseService)
	{
		boolean oldIsCaseService = isCaseService;
		isCaseService = newIsCaseService;
		boolean oldIsCaseServiceESet = isCaseServiceESet;
		isCaseServiceESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.DOCUMENT_ROOT__IS_CASE_SERVICE,
					oldIsCaseService, isCaseService, !oldIsCaseServiceESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetIsCaseService()
	{
		boolean oldIsCaseService = isCaseService;
		boolean oldIsCaseServiceESet = isCaseServiceESet;
		isCaseService = IS_CASE_SERVICE_EDEFAULT;
		isCaseServiceESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, XpdExtensionPackage.DOCUMENT_ROOT__IS_CASE_SERVICE,
					oldIsCaseService, IS_CASE_SERVICE_EDEFAULT, oldIsCaseServiceESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetIsCaseService()
	{
		return isCaseServiceESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RequiredAccessPrivileges> getRequiredAccessPrivileges()
	{
		if (requiredAccessPrivileges == null)
		{
			requiredAccessPrivileges = new EObjectContainmentEList<RequiredAccessPrivileges>(
					RequiredAccessPrivileges.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__REQUIRED_ACCESS_PRIVILEGES);
		}
		return requiredAccessPrivileges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CaseService> getCaseService()
	{
		if (caseService == null)
		{
			caseService = new EObjectContainmentEList<CaseService>(CaseService.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__CASE_SERVICE);
		}
		return caseService;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AdHocTaskConfigurationType> getAdHocTaskConfiguration()
	{
		if (adHocTaskConfiguration == null)
		{
			adHocTaskConfiguration = new EObjectContainmentEList<AdHocTaskConfigurationType>(
					AdHocTaskConfigurationType.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__AD_HOC_TASK_CONFIGURATION);
		}
		return adHocTaskConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsEventSubProcess()
	{
		return isEventSubProcess;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsEventSubProcess(boolean newIsEventSubProcess)
	{
		boolean oldIsEventSubProcess = isEventSubProcess;
		isEventSubProcess = newIsEventSubProcess;
		boolean oldIsEventSubProcessESet = isEventSubProcessESet;
		isEventSubProcessESet = true;
		if (eNotificationRequired()) eNotify(
				new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.DOCUMENT_ROOT__IS_EVENT_SUB_PROCESS,
						oldIsEventSubProcess, isEventSubProcess, !oldIsEventSubProcessESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetIsEventSubProcess()
	{
		boolean oldIsEventSubProcess = isEventSubProcess;
		boolean oldIsEventSubProcessESet = isEventSubProcessESet;
		isEventSubProcess = IS_EVENT_SUB_PROCESS_EDEFAULT;
		isEventSubProcessESet = false;
		if (eNotificationRequired()) eNotify(
				new ENotificationImpl(this, Notification.UNSET, XpdExtensionPackage.DOCUMENT_ROOT__IS_EVENT_SUB_PROCESS,
						oldIsEventSubProcess, IS_EVENT_SUB_PROCESS_EDEFAULT, oldIsEventSubProcessESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetIsEventSubProcess()
	{
		return isEventSubProcessESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isNonInterruptingEvent()
	{
		return nonInterruptingEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNonInterruptingEvent(boolean newNonInterruptingEvent)
	{
		boolean oldNonInterruptingEvent = nonInterruptingEvent;
		nonInterruptingEvent = newNonInterruptingEvent;
		boolean oldNonInterruptingEventESet = nonInterruptingEventESet;
		nonInterruptingEventESet = true;
		if (eNotificationRequired()) eNotify(
				new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.DOCUMENT_ROOT__NON_INTERRUPTING_EVENT,
						oldNonInterruptingEvent, nonInterruptingEvent, !oldNonInterruptingEventESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetNonInterruptingEvent()
	{
		boolean oldNonInterruptingEvent = nonInterruptingEvent;
		boolean oldNonInterruptingEventESet = nonInterruptingEventESet;
		nonInterruptingEvent = NON_INTERRUPTING_EVENT_EDEFAULT;
		nonInterruptingEventESet = false;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.UNSET,
				XpdExtensionPackage.DOCUMENT_ROOT__NON_INTERRUPTING_EVENT, oldNonInterruptingEvent,
				NON_INTERRUPTING_EVENT_EDEFAULT, oldNonInterruptingEventESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetNonInterruptingEvent()
	{
		return nonInterruptingEventESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isCorrelateImmediately()
	{
		return correlateImmediately;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCorrelateImmediately(boolean newCorrelateImmediately)
	{
		boolean oldCorrelateImmediately = correlateImmediately;
		correlateImmediately = newCorrelateImmediately;
		if (eNotificationRequired()) eNotify(
				new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.DOCUMENT_ROOT__CORRELATE_IMMEDIATELY,
						oldCorrelateImmediately, correlateImmediately));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AsyncExecutionMode getAsyncExecutionMode()
	{
		return asyncExecutionMode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAsyncExecutionMode(AsyncExecutionMode newAsyncExecutionMode)
	{
		AsyncExecutionMode oldAsyncExecutionMode = asyncExecutionMode;
		asyncExecutionMode = newAsyncExecutionMode == null ? ASYNC_EXECUTION_MODE_EDEFAULT : newAsyncExecutionMode;
		boolean oldAsyncExecutionModeESet = asyncExecutionModeESet;
		asyncExecutionModeESet = true;
		if (eNotificationRequired()) eNotify(
				new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.DOCUMENT_ROOT__ASYNC_EXECUTION_MODE,
						oldAsyncExecutionMode, asyncExecutionMode, !oldAsyncExecutionModeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetAsyncExecutionMode()
	{
		AsyncExecutionMode oldAsyncExecutionMode = asyncExecutionMode;
		boolean oldAsyncExecutionModeESet = asyncExecutionModeESet;
		asyncExecutionMode = ASYNC_EXECUTION_MODE_EDEFAULT;
		asyncExecutionModeESet = false;
		if (eNotificationRequired()) eNotify(
				new ENotificationImpl(this, Notification.UNSET, XpdExtensionPackage.DOCUMENT_ROOT__ASYNC_EXECUTION_MODE,
						oldAsyncExecutionMode, ASYNC_EXECUTION_MODE_EDEFAULT, oldAsyncExecutionModeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetAsyncExecutionMode()
	{
		return asyncExecutionModeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SignalType getSignalType()
	{
		return signalType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSignalType(SignalType newSignalType)
	{
		SignalType oldSignalType = signalType;
		signalType = newSignalType == null ? SIGNAL_TYPE_EDEFAULT : newSignalType;
		boolean oldSignalTypeESet = signalTypeESet;
		signalTypeESet = true;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__SIGNAL_TYPE, oldSignalType, signalType, !oldSignalTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetSignalType()
	{
		SignalType oldSignalType = signalType;
		boolean oldSignalTypeESet = signalTypeESet;
		signalType = SIGNAL_TYPE_EDEFAULT;
		signalTypeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, XpdExtensionPackage.DOCUMENT_ROOT__SIGNAL_TYPE,
					oldSignalType, SIGNAL_TYPE_EDEFAULT, oldSignalTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetSignalType()
	{
		return signalTypeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ServiceProcessConfiguration> getServiceProcessConfiguration()
	{
		if (serviceProcessConfiguration == null)
		{
			serviceProcessConfiguration = new EObjectContainmentEList<ServiceProcessConfiguration>(
					ServiceProcessConfiguration.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__SERVICE_PROCESS_CONFIGURATION);
		}
		return serviceProcessConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isLikeMapping()
	{
		return likeMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLikeMapping(boolean newLikeMapping)
	{
		boolean oldLikeMapping = likeMapping;
		likeMapping = newLikeMapping;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__LIKE_MAPPING, oldLikeMapping, likeMapping));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ScriptDataMapper> getScriptDataMapper()
	{
		if (scriptDataMapper == null)
		{
			scriptDataMapper = new EObjectContainmentEList<ScriptDataMapper>(ScriptDataMapper.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__SCRIPT_DATA_MAPPER);
		}
		return scriptDataMapper;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<LikeMappingExclusions> getLikeMappingExclusions()
	{
		if (likeMappingExclusions == null)
		{
			likeMappingExclusions = new EObjectContainmentEList<LikeMappingExclusions>(LikeMappingExclusions.class,
					this, XpdExtensionPackage.DOCUMENT_ROOT__LIKE_MAPPING_EXCLUSIONS);
		}
		return likeMappingExclusions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSourceContributorId()
	{
		return sourceContributorId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceContributorId(String newSourceContributorId)
	{
		String oldSourceContributorId = sourceContributorId;
		sourceContributorId = newSourceContributorId;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__SOURCE_CONTRIBUTOR_ID, oldSourceContributorId, sourceContributorId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTargetContributorId()
	{
		return targetContributorId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTargetContributorId(String newTargetContributorId)
	{
		String oldTargetContributorId = targetContributorId;
		targetContributorId = newTargetContributorId;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__TARGET_CONTRIBUTOR_ID, oldTargetContributorId, targetContributorId));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RestServiceOperation> getRestServiceOperation()
	{
		if (restServiceOperation == null)
		{
			restServiceOperation = new EObjectContainmentEList<RestServiceOperation>(RestServiceOperation.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__REST_SERVICE_OPERATION);
		}
		return restServiceOperation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ScriptDataMapper> getInputMappings()
	{
		if (inputMappings == null)
		{
			inputMappings = new EObjectContainmentEList<ScriptDataMapper>(ScriptDataMapper.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__INPUT_MAPPINGS);
		}
		return inputMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ScriptDataMapper> getOutputMappings()
	{
		if (outputMappings == null)
		{
			outputMappings = new EObjectContainmentEList<ScriptDataMapper>(ScriptDataMapper.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__OUTPUT_MAPPINGS);
		}
		return outputMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BusinessServicePublishType getBusinessServicePublishType()
	{
		return businessServicePublishType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBusinessServicePublishType(BusinessServicePublishType newBusinessServicePublishType)
	{
		BusinessServicePublishType oldBusinessServicePublishType = businessServicePublishType;
		businessServicePublishType = newBusinessServicePublishType == null ? BUSINESS_SERVICE_PUBLISH_TYPE_EDEFAULT
				: newBusinessServicePublishType;
		boolean oldBusinessServicePublishTypeESet = businessServicePublishTypeESet;
		businessServicePublishTypeESet = true;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__BUSINESS_SERVICE_PUBLISH_TYPE, oldBusinessServicePublishType,
				businessServicePublishType, !oldBusinessServicePublishTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetBusinessServicePublishType()
	{
		BusinessServicePublishType oldBusinessServicePublishType = businessServicePublishType;
		boolean oldBusinessServicePublishTypeESet = businessServicePublishTypeESet;
		businessServicePublishType = BUSINESS_SERVICE_PUBLISH_TYPE_EDEFAULT;
		businessServicePublishTypeESet = false;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.UNSET,
				XpdExtensionPackage.DOCUMENT_ROOT__BUSINESS_SERVICE_PUBLISH_TYPE, oldBusinessServicePublishType,
				BUSINESS_SERVICE_PUBLISH_TYPE_EDEFAULT, oldBusinessServicePublishTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetBusinessServicePublishType()
	{
		return businessServicePublishTypeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSuppressMaxMappingsError()
	{
		return suppressMaxMappingsError;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSuppressMaxMappingsError(boolean newSuppressMaxMappingsError)
	{
		boolean oldSuppressMaxMappingsError = suppressMaxMappingsError;
		suppressMaxMappingsError = newSuppressMaxMappingsError;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__SUPPRESS_MAX_MAPPINGS_ERROR, oldSuppressMaxMappingsError,
				suppressMaxMappingsError));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FieldFormat getFieldFormat()
	{
		return fieldFormat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFieldFormat(FieldFormat newFieldFormat)
	{
		FieldFormat oldFieldFormat = fieldFormat;
		fieldFormat = newFieldFormat == null ? FIELD_FORMAT_EDEFAULT : newFieldFormat;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__FIELD_FORMAT, oldFieldFormat, fieldFormat));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LibraryFunctionUseIn getUseIn()
	{
		return useIn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUseIn(LibraryFunctionUseIn newUseIn)
	{
		LibraryFunctionUseIn oldUseIn = useIn;
		useIn = newUseIn == null ? USE_IN_EDEFAULT : newUseIn;
		boolean oldUseInESet = useInESet;
		useInESet = true;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__USE_IN, oldUseIn, useIn, !oldUseInESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetUseIn()
	{
		LibraryFunctionUseIn oldUseIn = useIn;
		boolean oldUseInESet = useInESet;
		useIn = USE_IN_EDEFAULT;
		useInESet = false;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.UNSET,
				XpdExtensionPackage.DOCUMENT_ROOT__USE_IN, oldUseIn, USE_IN_EDEFAULT, oldUseInESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetUseIn()
	{
		return useInESet;
	}

	/**
	
	 * @generated
	 */
	@Override
	public boolean isSetXpdModelType()
	{
		return xpdModelTypeESet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FlowRoutingStyle getFlowRoutingStyle()
	{
		return flowRoutingStyle;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFlowRoutingStyle(FlowRoutingStyle newFlowRoutingStyle)
	{
		FlowRoutingStyle oldFlowRoutingStyle = flowRoutingStyle;
		flowRoutingStyle = newFlowRoutingStyle == null ? FLOW_ROUTING_STYLE_EDEFAULT : newFlowRoutingStyle;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__FLOW_ROUTING_STYLE, oldFlowRoutingStyle, flowRoutingStyle));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<CalendarReference> getCalendarReference()
	{
		if (calendarReference == null)
		{
			calendarReference = new EObjectContainmentEList<CalendarReference>(CalendarReference.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__CALENDAR_REFERENCE);
		}
		return calendarReference;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isNonCancelling()
	{
		return nonCancelling;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNonCancelling(boolean newNonCancelling)
	{
		boolean oldNonCancelling = nonCancelling;
		nonCancelling = newNonCancelling;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__NON_CANCELLING, oldNonCancelling, nonCancelling));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<SignalData> getSignalData()
	{
		// TODO: implement this method to return the 'Signal Data' containment reference list
		// Ensure that you remove @generated or mark it @generated NOT
		// The list is expected to implement org.eclipse.emf.ecore.util.InternalEList and org.eclipse.emf.ecore.EStructuralFeature.Setting
		// so it's likely that an appropriate subclass of org.eclipse.emf.ecore.util.EcoreEList should be used.
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Retry> getRetry()
	{
		if (retry == null)
		{
			retry = new EObjectContainmentEList<Retry>(Retry.class, this, XpdExtensionPackage.DOCUMENT_ROOT__RETRY);
		}
		return retry;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getActivityDeadlineEventId()
	{
		return activityDeadlineEventId;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setActivityDeadlineEventId(String newActivityDeadlineEventId)
	{
		String oldActivityDeadlineEventId = activityDeadlineEventId;
		activityDeadlineEventId = newActivityDeadlineEventId;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__ACTIVITY_DEADLINE_EVENT_ID, oldActivityDeadlineEventId,
				activityDeadlineEventId));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SubProcessStartStrategy getStartStrategy()
	{
		return startStrategy;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStartStrategy(SubProcessStartStrategy newStartStrategy)
	{
		SubProcessStartStrategy oldStartStrategy = startStrategy;
		startStrategy = newStartStrategy == null ? START_STRATEGY_EDEFAULT : newStartStrategy;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__START_STRATEGY, oldStartStrategy, startStrategy));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isOverwriteAlreadyModifiedTaskData()
	{
		return overwriteAlreadyModifiedTaskData;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOverwriteAlreadyModifiedTaskData(boolean newOverwriteAlreadyModifiedTaskData)
	{
		boolean oldOverwriteAlreadyModifiedTaskData = overwriteAlreadyModifiedTaskData;
		overwriteAlreadyModifiedTaskData = newOverwriteAlreadyModifiedTaskData;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__OVERWRITE_ALREADY_MODIFIED_TASK_DATA,
				oldOverwriteAlreadyModifiedTaskData, overwriteAlreadyModifiedTaskData));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EventHandlerFlowStrategy getEventHandlerFlowStrategy()
	{
		return eventHandlerFlowStrategy;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEventHandlerFlowStrategy(EventHandlerFlowStrategy newEventHandlerFlowStrategy)
	{
		EventHandlerFlowStrategy oldEventHandlerFlowStrategy = eventHandlerFlowStrategy;
		eventHandlerFlowStrategy = newEventHandlerFlowStrategy == null ? EVENT_HANDLER_FLOW_STRATEGY_EDEFAULT
				: newEventHandlerFlowStrategy;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__EVENT_HANDLER_FLOW_STRATEGY, oldEventHandlerFlowStrategy,
				eventHandlerFlowStrategy));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<NamespacePrefixMap> getNamespacePrefixMap()
	{
		if (namespacePrefixMap == null)
		{
			namespacePrefixMap = new EObjectContainmentEList<NamespacePrefixMap>(NamespacePrefixMap.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__NAMESPACE_PREFIX_MAP);
		}
		return namespacePrefixMap;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSuspendResumeWithParent()
	{
		return suspendResumeWithParent;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSuspendResumeWithParent(boolean newSuspendResumeWithParent)
	{
		boolean oldSuspendResumeWithParent = suspendResumeWithParent;
		suspendResumeWithParent = newSuspendResumeWithParent;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__SUSPEND_RESUME_WITH_PARENT, oldSuspendResumeWithParent,
				suspendResumeWithParent));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SystemErrorActionType getSystemErrorAction()
	{
		return systemErrorAction;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSystemErrorAction(SystemErrorActionType newSystemErrorAction)
	{
		SystemErrorActionType oldSystemErrorAction = systemErrorAction;
		systemErrorAction = newSystemErrorAction == null ? SYSTEM_ERROR_ACTION_EDEFAULT : newSystemErrorAction;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__SYSTEM_ERROR_ACTION, oldSystemErrorAction, systemErrorAction));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ConstantPeriod> getCorrelationTimeout()
	{
		if (correlationTimeout == null)
		{
			correlationTimeout = new EObjectContainmentEList<ConstantPeriod>(ConstantPeriod.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__CORRELATION_TIMEOUT);
		}
		return correlationTimeout;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ValidationControl> getValidationControl()
	{
		if (validationControl == null)
		{
			validationControl = new EObjectContainmentEList<ValidationControl>(ValidationControl.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__VALIDATION_CONTROL);
		}
		return validationControl;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ReplyImmediateDataMappings> getReplyImmediateDataMappings()
	{
		if (replyImmediateDataMappings == null)
		{
			replyImmediateDataMappings = new EObjectContainmentEList<ReplyImmediateDataMappings>(
					ReplyImmediateDataMappings.class, this,
					XpdExtensionPackage.DOCUMENT_ROOT__REPLY_IMMEDIATE_DATA_MAPPINGS);
		}
		return replyImmediateDataMappings;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isBxUseUnqualifiedPropertyNames()
	{
		return bxUseUnqualifiedPropertyNames;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBxUseUnqualifiedPropertyNames(boolean newBxUseUnqualifiedPropertyNames)
	{
		boolean oldBxUseUnqualifiedPropertyNames = bxUseUnqualifiedPropertyNames;
		bxUseUnqualifiedPropertyNames = newBxUseUnqualifiedPropertyNames;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DOCUMENT_ROOT__BX_USE_UNQUALIFIED_PROPERTY_NAMES, oldBxUseUnqualifiedPropertyNames,
				bxUseUnqualifiedPropertyNames));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return ((InternalEList< ? >) getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return ((InternalEList< ? >) getXSISchemaLocation()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__DATA_OBJECT_ATTRIBUTES:
				return ((InternalEList< ? >) getDataObjectAttributes()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__EXTENDED_ATTRIBUTES:
				return ((InternalEList< ? >) getExtendedAttributes()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__CONSTANT_PERIOD:
				return ((InternalEList< ? >) getConstantPeriod()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__USER_TASK_SCRIPTS:
				return ((InternalEList< ? >) getUserTaskScripts()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__AUDIT:
				return ((InternalEList< ? >) getAudit()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__SCRIPT:
				return ((InternalEList< ? >) getScript()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__INITIAL_VALUES:
				return ((InternalEList< ? >) getInitialValues()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__ASSOCIATED_CORRELATION_FIELDS:
				return ((InternalEList< ? >) getAssociatedCorrelationFields()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__ASSOCIATED_PARAMETERS:
				return ((InternalEList< ? >) getAssociatedParameters()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__IMPLEMENTED_INTERFACE:
				return ((InternalEList< ? >) getImplementedInterface()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__PROCESS_INTERFACES:
				return ((InternalEList< ? >) getProcessInterfaces()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__WSDL_EVENT_ASSOCIATION:
				return ((InternalEList< ? >) getWsdlEventAssociation()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__MULTI_INSTANCE_SCRIPTS:
				return ((InternalEList< ? >) getMultiInstanceScripts()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__EXPRESSION:
				return ((InternalEList< ? >) getExpression()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__INITIAL_PARAMETER_VALUE:
				return ((InternalEList< ? >) getInitialParameterValue()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__PORT_TYPE_OPERATION:
				return ((InternalEList< ? >) getPortTypeOperation()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__EXTERNAL_REFERENCE:
				return ((InternalEList< ? >) getExternalReference()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__PROCESS_RESOURCE_PATTERNS:
				return ((InternalEList< ? >) getProcessResourcePatterns()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__EVENT_HANDLER_INITIALISERS:
				return ((InternalEList< ? >) getEventHandlerInitialisers()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__ACTIVITY_RESOURCE_PATTERNS:
				return ((InternalEList< ? >) getActivityResourcePatterns()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__DOCUMENT_OPERATION:
				return ((InternalEList< ? >) getDocumentOperation()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__DURATION_CALCULATION:
				return ((InternalEList< ? >) getDurationCalculation()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__DISCRIMINATOR:
				return ((InternalEList< ? >) getDiscriminator()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__CORRELATION_DATA_MAPPINGS:
				return ((InternalEList< ? >) getCorrelationDataMappings()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__TRANSFORM_SCRIPT:
				return ((InternalEList< ? >) getTransformScript()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__ERROR_THROWER_INFO:
				return ((InternalEList< ? >) getErrorThrowerInfo()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__CATCH_ERROR_MAPPINGS:
				return ((InternalEList< ? >) getCatchErrorMappings()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__CONDITIONAL_PARTICIPANT:
				return ((InternalEList< ? >) getConditionalParticipant()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__TASK_LIBRARY_REFERENCE:
				return ((InternalEList< ? >) getTaskLibraryReference()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__FORM_IMPLEMENTATION:
				return ((InternalEList< ? >) getFormImplementation()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__PARTICIPANT_QUERY:
				return ((InternalEList< ? >) getParticipantQuery()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__FAULT_MESSAGE:
				return ((InternalEList< ? >) getFaultMessage()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__BUSINESS_PROCESS:
				return ((InternalEList< ? >) getBusinessProcess()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__WSDL_GENERATION:
				return ((InternalEList< ? >) getWsdlGeneration()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__DECISION_SERVICE:
				return ((InternalEList< ? >) getDecisionService()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__PARTICIPANT_SHARED_RESOURCE:
				return ((InternalEList< ? >) getParticipantSharedResource()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__CALENDAR_REFERENCE:
				return ((InternalEList< ? >) getCalendarReference()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__SIGNAL_DATA:
				return ((InternalEList< ? >) getSignalData()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__RETRY:
				return ((InternalEList< ? >) getRetry()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__NAMESPACE_PREFIX_MAP:
				return ((InternalEList< ? >) getNamespacePrefixMap()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__CORRELATION_TIMEOUT:
				return ((InternalEList< ? >) getCorrelationTimeout()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__VALIDATION_CONTROL:
				return ((InternalEList< ? >) getValidationControl()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__REPLY_IMMEDIATE_DATA_MAPPINGS:
				return ((InternalEList< ? >) getReplyImmediateDataMappings()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__CASE_REF_TYPE:
				return ((InternalEList< ? >) getCaseRefType()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__REST_SERVICES:
				return ((InternalEList< ? >) getRESTServices()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__RESCHEDULE_TIMER_SCRIPT:
				return ((InternalEList< ? >) getRescheduleTimerScript()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__DYNAMIC_ORGANIZATION_MAPPINGS:
				return ((InternalEList< ? >) getDynamicOrganizationMappings()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__GLOBAL_DATA_OPERATION:
				return ((InternalEList< ? >) getGlobalDataOperation()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS:
				return ((InternalEList< ? >) getProcessDataWorkItemAttributeMappings()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__BPM_RUNTIME_CONFIGURATION:
				return ((InternalEList< ? >) getBpmRuntimeConfiguration()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__REQUIRED_ACCESS_PRIVILEGES:
				return ((InternalEList< ? >) getRequiredAccessPrivileges()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__CASE_SERVICE:
				return ((InternalEList< ? >) getCaseService()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__AD_HOC_TASK_CONFIGURATION:
				return ((InternalEList< ? >) getAdHocTaskConfiguration()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__SERVICE_PROCESS_CONFIGURATION:
				return ((InternalEList< ? >) getServiceProcessConfiguration()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__SCRIPT_DATA_MAPPER:
				return ((InternalEList< ? >) getScriptDataMapper()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__LIKE_MAPPING_EXCLUSIONS:
				return ((InternalEList< ? >) getLikeMappingExclusions()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__REST_SERVICE_OPERATION:
				return ((InternalEList< ? >) getRestServiceOperation()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__INPUT_MAPPINGS:
				return ((InternalEList< ? >) getInputMappings()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.DOCUMENT_ROOT__OUTPUT_MAPPINGS:
				return ((InternalEList< ? >) getOutputMappings()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				if (coreType) return getXMLNSPrefixMap();
				else
					return getXMLNSPrefixMap().map();
			case XpdExtensionPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				if (coreType) return getXSISchemaLocation();
				else
					return getXSISchemaLocation().map();
			case XpdExtensionPackage.DOCUMENT_ROOT__IMPLEMENTATION_TYPE:
				return getImplementationType();
			case XpdExtensionPackage.DOCUMENT_ROOT__DATA_OBJECT_ATTRIBUTES:
				return getDataObjectAttributes();
			case XpdExtensionPackage.DOCUMENT_ROOT__EXTENDED_ATTRIBUTES:
				return getExtendedAttributes();
			case XpdExtensionPackage.DOCUMENT_ROOT__CONTINUE_ON_TIMEOUT:
				return isContinueOnTimeout();
			case XpdExtensionPackage.DOCUMENT_ROOT__ALIAS:
				return getAlias();
			case XpdExtensionPackage.DOCUMENT_ROOT__CONSTANT_PERIOD:
				return getConstantPeriod();
			case XpdExtensionPackage.DOCUMENT_ROOT__USER_TASK_SCRIPTS:
				return getUserTaskScripts();
			case XpdExtensionPackage.DOCUMENT_ROOT__AUDIT:
				return getAudit();
			case XpdExtensionPackage.DOCUMENT_ROOT__SCRIPT:
				return getScript();
			case XpdExtensionPackage.DOCUMENT_ROOT__REPLY_IMMEDIATELY:
				return isReplyImmediately();
			case XpdExtensionPackage.DOCUMENT_ROOT__INITIAL_VALUES:
				return getInitialValues();
			case XpdExtensionPackage.DOCUMENT_ROOT__ASSOCIATED_CORRELATION_FIELDS:
				return getAssociatedCorrelationFields();
			case XpdExtensionPackage.DOCUMENT_ROOT__ASSOCIATED_PARAMETERS:
				return getAssociatedParameters();
			case XpdExtensionPackage.DOCUMENT_ROOT__IMPLEMENTED_INTERFACE:
				return getImplementedInterface();
			case XpdExtensionPackage.DOCUMENT_ROOT__PROCESS_INTERFACES:
				return getProcessInterfaces();
			case XpdExtensionPackage.DOCUMENT_ROOT__WSDL_EVENT_ASSOCIATION:
				return getWsdlEventAssociation();
			case XpdExtensionPackage.DOCUMENT_ROOT__INLINE_SUB_PROCESS:
				return isInlineSubProcess();
			case XpdExtensionPackage.DOCUMENT_ROOT__DOCUMENTATION_URL:
				return getDocumentationURL();
			case XpdExtensionPackage.DOCUMENT_ROOT__IMPLEMENTS:
				return getImplements();
			case XpdExtensionPackage.DOCUMENT_ROOT__MULTI_INSTANCE_SCRIPTS:
				return getMultiInstanceScripts();
			case XpdExtensionPackage.DOCUMENT_ROOT__PROCESS_IDENTIFIER_FIELD:
				return getProcessIdentifierField();
			case XpdExtensionPackage.DOCUMENT_ROOT__EXPRESSION:
				return getExpression();
			case XpdExtensionPackage.DOCUMENT_ROOT__VISIBILITY:
				return getVisibility();
			case XpdExtensionPackage.DOCUMENT_ROOT__SECURITY_PROFILE:
				return getSecurityProfile();
			case XpdExtensionPackage.DOCUMENT_ROOT__LANGUAGE:
				return getLanguage();
			case XpdExtensionPackage.DOCUMENT_ROOT__INITIAL_PARAMETER_VALUE:
				return getInitialParameterValue();
			case XpdExtensionPackage.DOCUMENT_ROOT__INITIAL_VALUE_MAPPING:
				return isInitialValueMapping();
			case XpdExtensionPackage.DOCUMENT_ROOT__PORT_TYPE_OPERATION:
				return getPortTypeOperation();
			case XpdExtensionPackage.DOCUMENT_ROOT__TRANSPORT:
				return getTransport();
			case XpdExtensionPackage.DOCUMENT_ROOT__IS_CHAINED:
				return isIsChained();
			case XpdExtensionPackage.DOCUMENT_ROOT__EXTERNAL_REFERENCE:
				return getExternalReference();
			case XpdExtensionPackage.DOCUMENT_ROOT__PROCESS_RESOURCE_PATTERNS:
				return getProcessResourcePatterns();
			case XpdExtensionPackage.DOCUMENT_ROOT__EVENT_HANDLER_INITIALISERS:
				return getEventHandlerInitialisers();
			case XpdExtensionPackage.DOCUMENT_ROOT__ACTIVITY_RESOURCE_PATTERNS:
				return getActivityResourcePatterns();
			case XpdExtensionPackage.DOCUMENT_ROOT__REQUIRE_NEW_TRANSACTION:
				return isRequireNewTransaction();
			case XpdExtensionPackage.DOCUMENT_ROOT__DOCUMENT_OPERATION:
				return getDocumentOperation();
			case XpdExtensionPackage.DOCUMENT_ROOT__DURATION_CALCULATION:
				return getDurationCalculation();
			case XpdExtensionPackage.DOCUMENT_ROOT__DISCRIMINATOR:
				return getDiscriminator();
			case XpdExtensionPackage.DOCUMENT_ROOT__DISPLAY_NAME:
				return getDisplayName();
			case XpdExtensionPackage.DOCUMENT_ROOT__CATCH_THROW:
				return getCatchThrow();
			case XpdExtensionPackage.DOCUMENT_ROOT__IS_REMOTE:
				return isIsRemote();
			case XpdExtensionPackage.DOCUMENT_ROOT__CORRELATION_DATA_MAPPINGS:
				return getCorrelationDataMappings();
			case XpdExtensionPackage.DOCUMENT_ROOT__TRANSFORM_SCRIPT:
				return getTransformScript();
			case XpdExtensionPackage.DOCUMENT_ROOT__PUBLISH_AS_BUSINESS_SERVICE:
				return isPublishAsBusinessService();
			case XpdExtensionPackage.DOCUMENT_ROOT__BUSINESS_SERVICE_CATEGORY:
				return getBusinessServiceCategory();
			case XpdExtensionPackage.DOCUMENT_ROOT__ERROR_THROWER_INFO:
				return getErrorThrowerInfo();
			case XpdExtensionPackage.DOCUMENT_ROOT__CATCH_ERROR_MAPPINGS:
				return getCatchErrorMappings();
			case XpdExtensionPackage.DOCUMENT_ROOT__CONDITIONAL_PARTICIPANT:
				return getConditionalParticipant();
			case XpdExtensionPackage.DOCUMENT_ROOT__GENERATED:
				return isGenerated();
			case XpdExtensionPackage.DOCUMENT_ROOT__REPLY_TO_ACTIVITY_ID:
				return getReplyToActivityId();
			case XpdExtensionPackage.DOCUMENT_ROOT__TASK_LIBRARY_REFERENCE:
				return getTaskLibraryReference();
			case XpdExtensionPackage.DOCUMENT_ROOT__SET_PERFORMER_IN_PROCESS:
				return isSetPerformerInProcess();
			case XpdExtensionPackage.DOCUMENT_ROOT__EMB_SUBPROC_OTHER_STATE_HEIGHT:
				return getEmbSubprocOtherStateHeight();
			case XpdExtensionPackage.DOCUMENT_ROOT__EMB_SUBPROC_OTHER_STATE_WIDTH:
				return getEmbSubprocOtherStateWidth();
			case XpdExtensionPackage.DOCUMENT_ROOT__FORM_IMPLEMENTATION:
				return getFormImplementation();
			case XpdExtensionPackage.DOCUMENT_ROOT__PARTICIPANT_QUERY:
				return getParticipantQuery();
			case XpdExtensionPackage.DOCUMENT_ROOT__API_END_POINT_PARTICIPANT:
				return getApiEndPointParticipant();
			case XpdExtensionPackage.DOCUMENT_ROOT__FAULT_MESSAGE:
				return getFaultMessage();
			case XpdExtensionPackage.DOCUMENT_ROOT__REQUEST_ACTIVITY_ID:
				return getRequestActivityId();
			case XpdExtensionPackage.DOCUMENT_ROOT__BUSINESS_PROCESS:
				return getBusinessProcess();
			case XpdExtensionPackage.DOCUMENT_ROOT__WSDL_GENERATION:
				return getWsdlGeneration();
			case XpdExtensionPackage.DOCUMENT_ROOT__TARGET_PRIMITIVE_PROPERTY:
				return isTargetPrimitiveProperty();
			case XpdExtensionPackage.DOCUMENT_ROOT__SOURCE_PRIMITIVE_PROPERTY:
				return isSourcePrimitiveProperty();
			case XpdExtensionPackage.DOCUMENT_ROOT__DECISION_SERVICE:
				return getDecisionService();
			case XpdExtensionPackage.DOCUMENT_ROOT__PARTICIPANT_SHARED_RESOURCE:
				return getParticipantSharedResource();
			case XpdExtensionPackage.DOCUMENT_ROOT__XPD_MODEL_TYPE:
				return getXpdModelType();
			case XpdExtensionPackage.DOCUMENT_ROOT__FLOW_ROUTING_STYLE:
				return getFlowRoutingStyle();
			case XpdExtensionPackage.DOCUMENT_ROOT__CALENDAR_REFERENCE:
				return getCalendarReference();
			case XpdExtensionPackage.DOCUMENT_ROOT__NON_CANCELLING:
				return isNonCancelling();
			case XpdExtensionPackage.DOCUMENT_ROOT__SIGNAL_DATA:
				return getSignalData();
			case XpdExtensionPackage.DOCUMENT_ROOT__RETRY:
				return getRetry();
			case XpdExtensionPackage.DOCUMENT_ROOT__ACTIVITY_DEADLINE_EVENT_ID:
				return getActivityDeadlineEventId();
			case XpdExtensionPackage.DOCUMENT_ROOT__START_STRATEGY:
				return getStartStrategy();
			case XpdExtensionPackage.DOCUMENT_ROOT__OVERWRITE_ALREADY_MODIFIED_TASK_DATA:
				return isOverwriteAlreadyModifiedTaskData();
			case XpdExtensionPackage.DOCUMENT_ROOT__EVENT_HANDLER_FLOW_STRATEGY:
				return getEventHandlerFlowStrategy();
			case XpdExtensionPackage.DOCUMENT_ROOT__NAMESPACE_PREFIX_MAP:
				return getNamespacePrefixMap();
			case XpdExtensionPackage.DOCUMENT_ROOT__SUSPEND_RESUME_WITH_PARENT:
				return isSuspendResumeWithParent();
			case XpdExtensionPackage.DOCUMENT_ROOT__SYSTEM_ERROR_ACTION:
				return getSystemErrorAction();
			case XpdExtensionPackage.DOCUMENT_ROOT__CORRELATION_TIMEOUT:
				return getCorrelationTimeout();
			case XpdExtensionPackage.DOCUMENT_ROOT__VALIDATION_CONTROL:
				return getValidationControl();
			case XpdExtensionPackage.DOCUMENT_ROOT__REPLY_IMMEDIATE_DATA_MAPPINGS:
				return getReplyImmediateDataMappings();
			case XpdExtensionPackage.DOCUMENT_ROOT__BX_USE_UNQUALIFIED_PROPERTY_NAMES:
				return isBxUseUnqualifiedPropertyNames();
			case XpdExtensionPackage.DOCUMENT_ROOT__CASE_REF_TYPE:
				return getCaseRefType();
			case XpdExtensionPackage.DOCUMENT_ROOT__REST_SERVICES:
				return getRESTServices();
			case XpdExtensionPackage.DOCUMENT_ROOT__PUBLISH_AS_REST_SERVICE:
				return isPublishAsRestService();
			case XpdExtensionPackage.DOCUMENT_ROOT__REST_ACTIVITY_ID:
				return getRestActivityId();
			case XpdExtensionPackage.DOCUMENT_ROOT__RESCHEDULE_TIMER_SCRIPT:
				return getRescheduleTimerScript();
			case XpdExtensionPackage.DOCUMENT_ROOT__DYNAMIC_ORGANIZATION_MAPPINGS:
				return getDynamicOrganizationMappings();
			case XpdExtensionPackage.DOCUMENT_ROOT__SIGNAL_HANDLER_ASYNCHRONOUS:
				return isSignalHandlerAsynchronous();
			case XpdExtensionPackage.DOCUMENT_ROOT__GLOBAL_DATA_OPERATION:
				return getGlobalDataOperation();
			case XpdExtensionPackage.DOCUMENT_ROOT__PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS:
				return getProcessDataWorkItemAttributeMappings();
			case XpdExtensionPackage.DOCUMENT_ROOT__ALLOW_UNQUALIFIED_SUB_PROCESS_IDENTIFICATION:
				return isAllowUnqualifiedSubProcessIdentification();
			case XpdExtensionPackage.DOCUMENT_ROOT__BPM_RUNTIME_CONFIGURATION:
				return getBpmRuntimeConfiguration();
			case XpdExtensionPackage.DOCUMENT_ROOT__IS_CASE_SERVICE:
				return isIsCaseService();
			case XpdExtensionPackage.DOCUMENT_ROOT__REQUIRED_ACCESS_PRIVILEGES:
				return getRequiredAccessPrivileges();
			case XpdExtensionPackage.DOCUMENT_ROOT__CASE_SERVICE:
				return getCaseService();
			case XpdExtensionPackage.DOCUMENT_ROOT__AD_HOC_TASK_CONFIGURATION:
				return getAdHocTaskConfiguration();
			case XpdExtensionPackage.DOCUMENT_ROOT__IS_EVENT_SUB_PROCESS:
				return isIsEventSubProcess();
			case XpdExtensionPackage.DOCUMENT_ROOT__NON_INTERRUPTING_EVENT:
				return isNonInterruptingEvent();
			case XpdExtensionPackage.DOCUMENT_ROOT__CORRELATE_IMMEDIATELY:
				return isCorrelateImmediately();
			case XpdExtensionPackage.DOCUMENT_ROOT__ASYNC_EXECUTION_MODE:
				return getAsyncExecutionMode();
			case XpdExtensionPackage.DOCUMENT_ROOT__SIGNAL_TYPE:
				return getSignalType();
			case XpdExtensionPackage.DOCUMENT_ROOT__SERVICE_PROCESS_CONFIGURATION:
				return getServiceProcessConfiguration();
			case XpdExtensionPackage.DOCUMENT_ROOT__LIKE_MAPPING:
				return isLikeMapping();
			case XpdExtensionPackage.DOCUMENT_ROOT__SCRIPT_DATA_MAPPER:
				return getScriptDataMapper();
			case XpdExtensionPackage.DOCUMENT_ROOT__LIKE_MAPPING_EXCLUSIONS:
				return getLikeMappingExclusions();
			case XpdExtensionPackage.DOCUMENT_ROOT__SOURCE_CONTRIBUTOR_ID:
				return getSourceContributorId();
			case XpdExtensionPackage.DOCUMENT_ROOT__TARGET_CONTRIBUTOR_ID:
				return getTargetContributorId();
			case XpdExtensionPackage.DOCUMENT_ROOT__REST_SERVICE_OPERATION:
				return getRestServiceOperation();
			case XpdExtensionPackage.DOCUMENT_ROOT__INPUT_MAPPINGS:
				return getInputMappings();
			case XpdExtensionPackage.DOCUMENT_ROOT__OUTPUT_MAPPINGS:
				return getOutputMappings();
			case XpdExtensionPackage.DOCUMENT_ROOT__BUSINESS_SERVICE_PUBLISH_TYPE:
				return getBusinessServicePublishType();
			case XpdExtensionPackage.DOCUMENT_ROOT__SUPPRESS_MAX_MAPPINGS_ERROR:
				return isSuppressMaxMappingsError();
			case XpdExtensionPackage.DOCUMENT_ROOT__FIELD_FORMAT:
				return getFieldFormat();
			case XpdExtensionPackage.DOCUMENT_ROOT__USE_IN:
				return getUseIn();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				((EStructuralFeature.Setting) getXMLNSPrefixMap()).set(newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				((EStructuralFeature.Setting) getXSISchemaLocation()).set(newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__IMPLEMENTATION_TYPE:
				setImplementationType((String) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__DATA_OBJECT_ATTRIBUTES:
				getDataObjectAttributes().clear();
				getDataObjectAttributes().addAll((Collection< ? extends XpdExtDataObjectAttributes>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__EXTENDED_ATTRIBUTES:
				getExtendedAttributes().clear();
				getExtendedAttributes().addAll((Collection< ? extends XpdExtAttributes>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__CONTINUE_ON_TIMEOUT:
				setContinueOnTimeout((Boolean) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__ALIAS:
				setAlias((String) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__CONSTANT_PERIOD:
				getConstantPeriod().clear();
				getConstantPeriod().addAll((Collection< ? extends ConstantPeriod>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__USER_TASK_SCRIPTS:
				getUserTaskScripts().clear();
				getUserTaskScripts().addAll((Collection< ? extends UserTaskScripts>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__AUDIT:
				getAudit().clear();
				getAudit().addAll((Collection< ? extends Audit>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__SCRIPT:
				getScript().clear();
				getScript().addAll((Collection< ? extends ScriptInformation>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__REPLY_IMMEDIATELY:
				setReplyImmediately((Boolean) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__INITIAL_VALUES:
				getInitialValues().clear();
				getInitialValues().addAll((Collection< ? extends InitialValues>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__ASSOCIATED_CORRELATION_FIELDS:
				getAssociatedCorrelationFields().clear();
				getAssociatedCorrelationFields().addAll((Collection< ? extends AssociatedCorrelationFields>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__ASSOCIATED_PARAMETERS:
				getAssociatedParameters().clear();
				getAssociatedParameters().addAll((Collection< ? extends AssociatedParameters>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__IMPLEMENTED_INTERFACE:
				getImplementedInterface().clear();
				getImplementedInterface().addAll((Collection< ? extends ImplementedInterface>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__PROCESS_INTERFACES:
				getProcessInterfaces().clear();
				getProcessInterfaces().addAll((Collection< ? extends ProcessInterfaces>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__WSDL_EVENT_ASSOCIATION:
				getWsdlEventAssociation().clear();
				getWsdlEventAssociation().addAll((Collection< ? extends WsdlEventAssociation>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__INLINE_SUB_PROCESS:
				setInlineSubProcess((Boolean) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__DOCUMENTATION_URL:
				setDocumentationURL((String) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__IMPLEMENTS:
				setImplements((String) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__MULTI_INSTANCE_SCRIPTS:
				getMultiInstanceScripts().clear();
				getMultiInstanceScripts().addAll((Collection< ? extends MultiInstanceScripts>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__PROCESS_IDENTIFIER_FIELD:
				setProcessIdentifierField((String) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__EXPRESSION:
				getExpression().clear();
				getExpression().addAll((Collection< ? extends Expression>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__VISIBILITY:
				setVisibility((Visibility) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__SECURITY_PROFILE:
				setSecurityProfile((String) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__LANGUAGE:
				setLanguage((String) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__INITIAL_PARAMETER_VALUE:
				getInitialParameterValue().clear();
				getInitialParameterValue().addAll((Collection< ? extends InitialParameterValue>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__INITIAL_VALUE_MAPPING:
				setInitialValueMapping((Boolean) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__PORT_TYPE_OPERATION:
				getPortTypeOperation().clear();
				getPortTypeOperation().addAll((Collection< ? extends PortTypeOperation>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__TRANSPORT:
				setTransport((String) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__IS_CHAINED:
				setIsChained((Boolean) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__EXTERNAL_REFERENCE:
				getExternalReference().clear();
				getExternalReference().addAll((Collection< ? extends ExternalReference>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__PROCESS_RESOURCE_PATTERNS:
				getProcessResourcePatterns().clear();
				getProcessResourcePatterns().addAll((Collection< ? extends ProcessResourcePatterns>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__EVENT_HANDLER_INITIALISERS:
				getEventHandlerInitialisers().clear();
				getEventHandlerInitialisers().addAll((Collection< ? extends EventHandlerInitialisers>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__ACTIVITY_RESOURCE_PATTERNS:
				getActivityResourcePatterns().clear();
				getActivityResourcePatterns().addAll((Collection< ? extends ActivityResourcePatterns>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__REQUIRE_NEW_TRANSACTION:
				setRequireNewTransaction((Boolean) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__DOCUMENT_OPERATION:
				getDocumentOperation().clear();
				getDocumentOperation().addAll((Collection< ? extends DocumentOperation>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__DURATION_CALCULATION:
				getDurationCalculation().clear();
				getDurationCalculation().addAll((Collection< ? extends DurationCalculation>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__DISCRIMINATOR:
				getDiscriminator().clear();
				getDiscriminator().addAll((Collection< ? extends Discriminator>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__DISPLAY_NAME:
				setDisplayName((String) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__CATCH_THROW:
				setCatchThrow((CatchThrow) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__IS_REMOTE:
				setIsRemote((Boolean) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__CORRELATION_DATA_MAPPINGS:
				getCorrelationDataMappings().clear();
				getCorrelationDataMappings().addAll((Collection< ? extends CorrelationDataMappings>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__TRANSFORM_SCRIPT:
				getTransformScript().clear();
				getTransformScript().addAll((Collection< ? extends TransformScript>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__PUBLISH_AS_BUSINESS_SERVICE:
				setPublishAsBusinessService((Boolean) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__BUSINESS_SERVICE_CATEGORY:
				setBusinessServiceCategory((String) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__ERROR_THROWER_INFO:
				getErrorThrowerInfo().clear();
				getErrorThrowerInfo().addAll((Collection< ? extends ErrorThrowerInfo>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__CATCH_ERROR_MAPPINGS:
				getCatchErrorMappings().clear();
				getCatchErrorMappings().addAll((Collection< ? extends CatchErrorMappings>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__CONDITIONAL_PARTICIPANT:
				getConditionalParticipant().clear();
				getConditionalParticipant().addAll((Collection< ? extends ConditionalParticipant>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__GENERATED:
				setGenerated((Boolean) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__REPLY_TO_ACTIVITY_ID:
				setReplyToActivityId((String) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__TASK_LIBRARY_REFERENCE:
				getTaskLibraryReference().clear();
				getTaskLibraryReference().addAll((Collection< ? extends TaskLibraryReference>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__SET_PERFORMER_IN_PROCESS:
				setSetPerformerInProcess((Boolean) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__EMB_SUBPROC_OTHER_STATE_HEIGHT:
				setEmbSubprocOtherStateHeight((Double) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__EMB_SUBPROC_OTHER_STATE_WIDTH:
				setEmbSubprocOtherStateWidth((Double) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__FORM_IMPLEMENTATION:
				getFormImplementation().clear();
				getFormImplementation().addAll((Collection< ? extends FormImplementation>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__PARTICIPANT_QUERY:
				getParticipantQuery().clear();
				getParticipantQuery().addAll((Collection< ? extends Expression>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__API_END_POINT_PARTICIPANT:
				setApiEndPointParticipant((String) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__FAULT_MESSAGE:
				getFaultMessage().clear();
				getFaultMessage().addAll((Collection< ? extends Message>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__REQUEST_ACTIVITY_ID:
				setRequestActivityId((String) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__BUSINESS_PROCESS:
				getBusinessProcess().clear();
				getBusinessProcess().addAll((Collection< ? extends BusinessProcess>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__WSDL_GENERATION:
				getWsdlGeneration().clear();
				getWsdlGeneration().addAll((Collection< ? extends WsdlGeneration>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__TARGET_PRIMITIVE_PROPERTY:
				setTargetPrimitiveProperty((Boolean) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__SOURCE_PRIMITIVE_PROPERTY:
				setSourcePrimitiveProperty((Boolean) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__DECISION_SERVICE:
				getDecisionService().clear();
				getDecisionService().addAll((Collection< ? extends SubFlow>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__PARTICIPANT_SHARED_RESOURCE:
				getParticipantSharedResource().clear();
				getParticipantSharedResource().addAll((Collection< ? extends ParticipantSharedResource>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__XPD_MODEL_TYPE:
				setXpdModelType((XpdModelType) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__FLOW_ROUTING_STYLE:
				setFlowRoutingStyle((FlowRoutingStyle) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__CALENDAR_REFERENCE:
				getCalendarReference().clear();
				getCalendarReference().addAll((Collection< ? extends CalendarReference>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__NON_CANCELLING:
				setNonCancelling((Boolean) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__SIGNAL_DATA:
				getSignalData().clear();
				getSignalData().addAll((Collection< ? extends SignalData>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__RETRY:
				getRetry().clear();
				getRetry().addAll((Collection< ? extends Retry>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__ACTIVITY_DEADLINE_EVENT_ID:
				setActivityDeadlineEventId((String) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__START_STRATEGY:
				setStartStrategy((SubProcessStartStrategy) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__OVERWRITE_ALREADY_MODIFIED_TASK_DATA:
				setOverwriteAlreadyModifiedTaskData((Boolean) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__EVENT_HANDLER_FLOW_STRATEGY:
				setEventHandlerFlowStrategy((EventHandlerFlowStrategy) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__NAMESPACE_PREFIX_MAP:
				getNamespacePrefixMap().clear();
				getNamespacePrefixMap().addAll((Collection< ? extends NamespacePrefixMap>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__SUSPEND_RESUME_WITH_PARENT:
				setSuspendResumeWithParent((Boolean) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__SYSTEM_ERROR_ACTION:
				setSystemErrorAction((SystemErrorActionType) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__CORRELATION_TIMEOUT:
				getCorrelationTimeout().clear();
				getCorrelationTimeout().addAll((Collection< ? extends ConstantPeriod>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__VALIDATION_CONTROL:
				getValidationControl().clear();
				getValidationControl().addAll((Collection< ? extends ValidationControl>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__REPLY_IMMEDIATE_DATA_MAPPINGS:
				getReplyImmediateDataMappings().clear();
				getReplyImmediateDataMappings().addAll((Collection< ? extends ReplyImmediateDataMappings>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__BX_USE_UNQUALIFIED_PROPERTY_NAMES:
				setBxUseUnqualifiedPropertyNames((Boolean) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__CASE_REF_TYPE:
				getCaseRefType().clear();
				getCaseRefType().addAll((Collection< ? extends RecordType>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__REST_SERVICES:
				getRESTServices().clear();
				getRESTServices().addAll((Collection< ? extends RESTServices>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__PUBLISH_AS_REST_SERVICE:
				setPublishAsRestService((Boolean) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__REST_ACTIVITY_ID:
				setRestActivityId((String) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__RESCHEDULE_TIMER_SCRIPT:
				getRescheduleTimerScript().clear();
				getRescheduleTimerScript().addAll((Collection< ? extends RescheduleTimerScript>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__DYNAMIC_ORGANIZATION_MAPPINGS:
				getDynamicOrganizationMappings().clear();
				getDynamicOrganizationMappings().addAll((Collection< ? extends DynamicOrganizationMappings>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__SIGNAL_HANDLER_ASYNCHRONOUS:
				setSignalHandlerAsynchronous((Boolean) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__GLOBAL_DATA_OPERATION:
				getGlobalDataOperation().clear();
				getGlobalDataOperation().addAll((Collection< ? extends GlobalDataOperation>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS:
				getProcessDataWorkItemAttributeMappings().clear();
				getProcessDataWorkItemAttributeMappings()
						.addAll((Collection< ? extends ProcessDataWorkItemAttributeMappings>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__ALLOW_UNQUALIFIED_SUB_PROCESS_IDENTIFICATION:
				setAllowUnqualifiedSubProcessIdentification((Boolean) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__BPM_RUNTIME_CONFIGURATION:
				getBpmRuntimeConfiguration().clear();
				getBpmRuntimeConfiguration().addAll((Collection< ? extends BpmRuntimeConfiguration>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__IS_CASE_SERVICE:
				setIsCaseService((Boolean) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__REQUIRED_ACCESS_PRIVILEGES:
				getRequiredAccessPrivileges().clear();
				getRequiredAccessPrivileges().addAll((Collection< ? extends RequiredAccessPrivileges>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__CASE_SERVICE:
				getCaseService().clear();
				getCaseService().addAll((Collection< ? extends CaseService>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__AD_HOC_TASK_CONFIGURATION:
				getAdHocTaskConfiguration().clear();
				getAdHocTaskConfiguration().addAll((Collection< ? extends AdHocTaskConfigurationType>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__IS_EVENT_SUB_PROCESS:
				setIsEventSubProcess((Boolean) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__NON_INTERRUPTING_EVENT:
				setNonInterruptingEvent((Boolean) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__CORRELATE_IMMEDIATELY:
				setCorrelateImmediately((Boolean) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__ASYNC_EXECUTION_MODE:
				setAsyncExecutionMode((AsyncExecutionMode) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__SIGNAL_TYPE:
				setSignalType((SignalType) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__SERVICE_PROCESS_CONFIGURATION:
				getServiceProcessConfiguration().clear();
				getServiceProcessConfiguration().addAll((Collection< ? extends ServiceProcessConfiguration>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__LIKE_MAPPING:
				setLikeMapping((Boolean) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__SCRIPT_DATA_MAPPER:
				getScriptDataMapper().clear();
				getScriptDataMapper().addAll((Collection< ? extends ScriptDataMapper>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__LIKE_MAPPING_EXCLUSIONS:
				getLikeMappingExclusions().clear();
				getLikeMappingExclusions().addAll((Collection< ? extends LikeMappingExclusions>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__SOURCE_CONTRIBUTOR_ID:
				setSourceContributorId((String) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__TARGET_CONTRIBUTOR_ID:
				setTargetContributorId((String) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__REST_SERVICE_OPERATION:
				getRestServiceOperation().clear();
				getRestServiceOperation().addAll((Collection< ? extends RestServiceOperation>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__INPUT_MAPPINGS:
				getInputMappings().clear();
				getInputMappings().addAll((Collection< ? extends ScriptDataMapper>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__OUTPUT_MAPPINGS:
				getOutputMappings().clear();
				getOutputMappings().addAll((Collection< ? extends ScriptDataMapper>) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__BUSINESS_SERVICE_PUBLISH_TYPE:
				setBusinessServicePublishType((BusinessServicePublishType) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__SUPPRESS_MAX_MAPPINGS_ERROR:
				setSuppressMaxMappingsError((Boolean) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__FIELD_FORMAT:
				setFieldFormat((FieldFormat) newValue);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__USE_IN:
				setUseIn((LibraryFunctionUseIn) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				getXMLNSPrefixMap().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				getXSISchemaLocation().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__IMPLEMENTATION_TYPE:
				setImplementationType(IMPLEMENTATION_TYPE_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__DATA_OBJECT_ATTRIBUTES:
				getDataObjectAttributes().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__EXTENDED_ATTRIBUTES:
				getExtendedAttributes().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__CONTINUE_ON_TIMEOUT:
				setContinueOnTimeout(CONTINUE_ON_TIMEOUT_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__ALIAS:
				setAlias(ALIAS_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__CONSTANT_PERIOD:
				getConstantPeriod().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__USER_TASK_SCRIPTS:
				getUserTaskScripts().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__AUDIT:
				getAudit().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__SCRIPT:
				getScript().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__REPLY_IMMEDIATELY:
				setReplyImmediately(REPLY_IMMEDIATELY_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__INITIAL_VALUES:
				getInitialValues().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__ASSOCIATED_CORRELATION_FIELDS:
				getAssociatedCorrelationFields().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__ASSOCIATED_PARAMETERS:
				getAssociatedParameters().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__IMPLEMENTED_INTERFACE:
				getImplementedInterface().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__PROCESS_INTERFACES:
				getProcessInterfaces().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__WSDL_EVENT_ASSOCIATION:
				getWsdlEventAssociation().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__INLINE_SUB_PROCESS:
				setInlineSubProcess(INLINE_SUB_PROCESS_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__DOCUMENTATION_URL:
				setDocumentationURL(DOCUMENTATION_URL_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__IMPLEMENTS:
				setImplements(IMPLEMENTS_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__MULTI_INSTANCE_SCRIPTS:
				getMultiInstanceScripts().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__PROCESS_IDENTIFIER_FIELD:
				setProcessIdentifierField(PROCESS_IDENTIFIER_FIELD_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__EXPRESSION:
				getExpression().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__VISIBILITY:
				unsetVisibility();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__SECURITY_PROFILE:
				setSecurityProfile(SECURITY_PROFILE_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__LANGUAGE:
				setLanguage(LANGUAGE_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__INITIAL_PARAMETER_VALUE:
				getInitialParameterValue().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__INITIAL_VALUE_MAPPING:
				setInitialValueMapping(INITIAL_VALUE_MAPPING_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__PORT_TYPE_OPERATION:
				getPortTypeOperation().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__TRANSPORT:
				setTransport(TRANSPORT_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__IS_CHAINED:
				unsetIsChained();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__EXTERNAL_REFERENCE:
				getExternalReference().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__PROCESS_RESOURCE_PATTERNS:
				getProcessResourcePatterns().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__EVENT_HANDLER_INITIALISERS:
				getEventHandlerInitialisers().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__ACTIVITY_RESOURCE_PATTERNS:
				getActivityResourcePatterns().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__REQUIRE_NEW_TRANSACTION:
				setRequireNewTransaction(REQUIRE_NEW_TRANSACTION_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__DOCUMENT_OPERATION:
				getDocumentOperation().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__DURATION_CALCULATION:
				getDurationCalculation().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__DISCRIMINATOR:
				getDiscriminator().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__DISPLAY_NAME:
				unsetDisplayName();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__CATCH_THROW:
				setCatchThrow(CATCH_THROW_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__IS_REMOTE:
				unsetIsRemote();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__CORRELATION_DATA_MAPPINGS:
				getCorrelationDataMappings().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__TRANSFORM_SCRIPT:
				getTransformScript().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__PUBLISH_AS_BUSINESS_SERVICE:
				unsetPublishAsBusinessService();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__BUSINESS_SERVICE_CATEGORY:
				unsetBusinessServiceCategory();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__ERROR_THROWER_INFO:
				getErrorThrowerInfo().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__CATCH_ERROR_MAPPINGS:
				getCatchErrorMappings().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__CONDITIONAL_PARTICIPANT:
				getConditionalParticipant().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__GENERATED:
				setGenerated(GENERATED_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__REPLY_TO_ACTIVITY_ID:
				setReplyToActivityId(REPLY_TO_ACTIVITY_ID_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__TASK_LIBRARY_REFERENCE:
				getTaskLibraryReference().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__SET_PERFORMER_IN_PROCESS:
				unsetSetPerformerInProcess();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__EMB_SUBPROC_OTHER_STATE_HEIGHT:
				unsetEmbSubprocOtherStateHeight();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__EMB_SUBPROC_OTHER_STATE_WIDTH:
				unsetEmbSubprocOtherStateWidth();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__FORM_IMPLEMENTATION:
				getFormImplementation().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__PARTICIPANT_QUERY:
				getParticipantQuery().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__API_END_POINT_PARTICIPANT:
				setApiEndPointParticipant(API_END_POINT_PARTICIPANT_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__FAULT_MESSAGE:
				getFaultMessage().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__REQUEST_ACTIVITY_ID:
				setRequestActivityId(REQUEST_ACTIVITY_ID_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__BUSINESS_PROCESS:
				getBusinessProcess().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__WSDL_GENERATION:
				getWsdlGeneration().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__TARGET_PRIMITIVE_PROPERTY:
				unsetTargetPrimitiveProperty();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__SOURCE_PRIMITIVE_PROPERTY:
				unsetSourcePrimitiveProperty();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__DECISION_SERVICE:
				getDecisionService().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__PARTICIPANT_SHARED_RESOURCE:
				getParticipantSharedResource().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__XPD_MODEL_TYPE:
				unsetXpdModelType();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__FLOW_ROUTING_STYLE:
				setFlowRoutingStyle(FLOW_ROUTING_STYLE_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__CALENDAR_REFERENCE:
				getCalendarReference().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__NON_CANCELLING:
				setNonCancelling(NON_CANCELLING_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__SIGNAL_DATA:
				getSignalData().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__RETRY:
				getRetry().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__ACTIVITY_DEADLINE_EVENT_ID:
				setActivityDeadlineEventId(ACTIVITY_DEADLINE_EVENT_ID_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__START_STRATEGY:
				setStartStrategy(START_STRATEGY_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__OVERWRITE_ALREADY_MODIFIED_TASK_DATA:
				setOverwriteAlreadyModifiedTaskData(OVERWRITE_ALREADY_MODIFIED_TASK_DATA_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__EVENT_HANDLER_FLOW_STRATEGY:
				setEventHandlerFlowStrategy(EVENT_HANDLER_FLOW_STRATEGY_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__NAMESPACE_PREFIX_MAP:
				getNamespacePrefixMap().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__SUSPEND_RESUME_WITH_PARENT:
				setSuspendResumeWithParent(SUSPEND_RESUME_WITH_PARENT_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__SYSTEM_ERROR_ACTION:
				setSystemErrorAction(SYSTEM_ERROR_ACTION_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__CORRELATION_TIMEOUT:
				getCorrelationTimeout().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__VALIDATION_CONTROL:
				getValidationControl().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__REPLY_IMMEDIATE_DATA_MAPPINGS:
				getReplyImmediateDataMappings().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__BX_USE_UNQUALIFIED_PROPERTY_NAMES:
				setBxUseUnqualifiedPropertyNames(BX_USE_UNQUALIFIED_PROPERTY_NAMES_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__CASE_REF_TYPE:
				getCaseRefType().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__REST_SERVICES:
				getRESTServices().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__PUBLISH_AS_REST_SERVICE:
				setPublishAsRestService(PUBLISH_AS_REST_SERVICE_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__REST_ACTIVITY_ID:
				setRestActivityId(REST_ACTIVITY_ID_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__RESCHEDULE_TIMER_SCRIPT:
				getRescheduleTimerScript().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__DYNAMIC_ORGANIZATION_MAPPINGS:
				getDynamicOrganizationMappings().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__SIGNAL_HANDLER_ASYNCHRONOUS:
				setSignalHandlerAsynchronous(SIGNAL_HANDLER_ASYNCHRONOUS_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__GLOBAL_DATA_OPERATION:
				getGlobalDataOperation().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS:
				getProcessDataWorkItemAttributeMappings().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__ALLOW_UNQUALIFIED_SUB_PROCESS_IDENTIFICATION:
				setAllowUnqualifiedSubProcessIdentification(ALLOW_UNQUALIFIED_SUB_PROCESS_IDENTIFICATION_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__BPM_RUNTIME_CONFIGURATION:
				getBpmRuntimeConfiguration().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__IS_CASE_SERVICE:
				unsetIsCaseService();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__REQUIRED_ACCESS_PRIVILEGES:
				getRequiredAccessPrivileges().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__CASE_SERVICE:
				getCaseService().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__AD_HOC_TASK_CONFIGURATION:
				getAdHocTaskConfiguration().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__IS_EVENT_SUB_PROCESS:
				unsetIsEventSubProcess();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__NON_INTERRUPTING_EVENT:
				unsetNonInterruptingEvent();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__CORRELATE_IMMEDIATELY:
				setCorrelateImmediately(CORRELATE_IMMEDIATELY_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__ASYNC_EXECUTION_MODE:
				unsetAsyncExecutionMode();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__SIGNAL_TYPE:
				unsetSignalType();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__SERVICE_PROCESS_CONFIGURATION:
				getServiceProcessConfiguration().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__LIKE_MAPPING:
				setLikeMapping(LIKE_MAPPING_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__SCRIPT_DATA_MAPPER:
				getScriptDataMapper().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__LIKE_MAPPING_EXCLUSIONS:
				getLikeMappingExclusions().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__SOURCE_CONTRIBUTOR_ID:
				setSourceContributorId(SOURCE_CONTRIBUTOR_ID_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__TARGET_CONTRIBUTOR_ID:
				setTargetContributorId(TARGET_CONTRIBUTOR_ID_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__REST_SERVICE_OPERATION:
				getRestServiceOperation().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__INPUT_MAPPINGS:
				getInputMappings().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__OUTPUT_MAPPINGS:
				getOutputMappings().clear();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__BUSINESS_SERVICE_PUBLISH_TYPE:
				unsetBusinessServicePublishType();
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__SUPPRESS_MAX_MAPPINGS_ERROR:
				setSuppressMaxMappingsError(SUPPRESS_MAX_MAPPINGS_ERROR_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__FIELD_FORMAT:
				setFieldFormat(FIELD_FORMAT_EDEFAULT);
				return;
			case XpdExtensionPackage.DOCUMENT_ROOT__USE_IN:
				unsetUseIn();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__IMPLEMENTATION_TYPE:
				return IMPLEMENTATION_TYPE_EDEFAULT == null ? implementationType != null
						: !IMPLEMENTATION_TYPE_EDEFAULT.equals(implementationType);
			case XpdExtensionPackage.DOCUMENT_ROOT__DATA_OBJECT_ATTRIBUTES:
				return dataObjectAttributes != null && !dataObjectAttributes.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__EXTENDED_ATTRIBUTES:
				return extendedAttributes != null && !extendedAttributes.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__CONTINUE_ON_TIMEOUT:
				return continueOnTimeout != CONTINUE_ON_TIMEOUT_EDEFAULT;
			case XpdExtensionPackage.DOCUMENT_ROOT__ALIAS:
				return ALIAS_EDEFAULT == null ? alias != null : !ALIAS_EDEFAULT.equals(alias);
			case XpdExtensionPackage.DOCUMENT_ROOT__CONSTANT_PERIOD:
				return constantPeriod != null && !constantPeriod.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__USER_TASK_SCRIPTS:
				return userTaskScripts != null && !userTaskScripts.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__AUDIT:
				return !getAudit().isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__SCRIPT:
				return script != null && !script.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__REPLY_IMMEDIATELY:
				return replyImmediately != REPLY_IMMEDIATELY_EDEFAULT;
			case XpdExtensionPackage.DOCUMENT_ROOT__INITIAL_VALUES:
				return initialValues != null && !initialValues.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__ASSOCIATED_CORRELATION_FIELDS:
				return associatedCorrelationFields != null && !associatedCorrelationFields.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__ASSOCIATED_PARAMETERS:
				return associatedParameters != null && !associatedParameters.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__IMPLEMENTED_INTERFACE:
				return implementedInterface != null && !implementedInterface.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__PROCESS_INTERFACES:
				return processInterfaces != null && !processInterfaces.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__WSDL_EVENT_ASSOCIATION:
				return wsdlEventAssociation != null && !wsdlEventAssociation.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__INLINE_SUB_PROCESS:
				return inlineSubProcess != INLINE_SUB_PROCESS_EDEFAULT;
			case XpdExtensionPackage.DOCUMENT_ROOT__DOCUMENTATION_URL:
				return DOCUMENTATION_URL_EDEFAULT == null ? documentationURL != null
						: !DOCUMENTATION_URL_EDEFAULT.equals(documentationURL);
			case XpdExtensionPackage.DOCUMENT_ROOT__IMPLEMENTS:
				return IMPLEMENTS_EDEFAULT == null ? implements_ != null : !IMPLEMENTS_EDEFAULT.equals(implements_);
			case XpdExtensionPackage.DOCUMENT_ROOT__MULTI_INSTANCE_SCRIPTS:
				return multiInstanceScripts != null && !multiInstanceScripts.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__PROCESS_IDENTIFIER_FIELD:
				return PROCESS_IDENTIFIER_FIELD_EDEFAULT == null ? processIdentifierField != null
						: !PROCESS_IDENTIFIER_FIELD_EDEFAULT.equals(processIdentifierField);
			case XpdExtensionPackage.DOCUMENT_ROOT__EXPRESSION:
				return expression != null && !expression.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__VISIBILITY:
				return isSetVisibility();
			case XpdExtensionPackage.DOCUMENT_ROOT__SECURITY_PROFILE:
				return SECURITY_PROFILE_EDEFAULT == null ? securityProfile != null
						: !SECURITY_PROFILE_EDEFAULT.equals(securityProfile);
			case XpdExtensionPackage.DOCUMENT_ROOT__LANGUAGE:
				return LANGUAGE_EDEFAULT == null ? language != null : !LANGUAGE_EDEFAULT.equals(language);
			case XpdExtensionPackage.DOCUMENT_ROOT__INITIAL_PARAMETER_VALUE:
				return initialParameterValue != null && !initialParameterValue.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__INITIAL_VALUE_MAPPING:
				return initialValueMapping != INITIAL_VALUE_MAPPING_EDEFAULT;
			case XpdExtensionPackage.DOCUMENT_ROOT__PORT_TYPE_OPERATION:
				return portTypeOperation != null && !portTypeOperation.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__TRANSPORT:
				return TRANSPORT_EDEFAULT == null ? transport != null : !TRANSPORT_EDEFAULT.equals(transport);
			case XpdExtensionPackage.DOCUMENT_ROOT__IS_CHAINED:
				return isSetIsChained();
			case XpdExtensionPackage.DOCUMENT_ROOT__EXTERNAL_REFERENCE:
				return externalReference != null && !externalReference.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__PROCESS_RESOURCE_PATTERNS:
				return processResourcePatterns != null && !processResourcePatterns.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__EVENT_HANDLER_INITIALISERS:
				return eventHandlerInitialisers != null && !eventHandlerInitialisers.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__ACTIVITY_RESOURCE_PATTERNS:
				return activityResourcePatterns != null && !activityResourcePatterns.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__REQUIRE_NEW_TRANSACTION:
				return requireNewTransaction != REQUIRE_NEW_TRANSACTION_EDEFAULT;
			case XpdExtensionPackage.DOCUMENT_ROOT__DOCUMENT_OPERATION:
				return documentOperation != null && !documentOperation.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__DURATION_CALCULATION:
				return durationCalculation != null && !durationCalculation.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__DISCRIMINATOR:
				return discriminator != null && !discriminator.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__DISPLAY_NAME:
				return isSetDisplayName();
			case XpdExtensionPackage.DOCUMENT_ROOT__CATCH_THROW:
				return catchThrow != CATCH_THROW_EDEFAULT;
			case XpdExtensionPackage.DOCUMENT_ROOT__IS_REMOTE:
				return isSetIsRemote();
			case XpdExtensionPackage.DOCUMENT_ROOT__CORRELATION_DATA_MAPPINGS:
				return correlationDataMappings != null && !correlationDataMappings.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__TRANSFORM_SCRIPT:
				return transformScript != null && !transformScript.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__PUBLISH_AS_BUSINESS_SERVICE:
				return isSetPublishAsBusinessService();
			case XpdExtensionPackage.DOCUMENT_ROOT__BUSINESS_SERVICE_CATEGORY:
				return isSetBusinessServiceCategory();
			case XpdExtensionPackage.DOCUMENT_ROOT__ERROR_THROWER_INFO:
				return errorThrowerInfo != null && !errorThrowerInfo.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__CATCH_ERROR_MAPPINGS:
				return catchErrorMappings != null && !catchErrorMappings.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__CONDITIONAL_PARTICIPANT:
				return conditionalParticipant != null && !conditionalParticipant.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__GENERATED:
				return generated != GENERATED_EDEFAULT;
			case XpdExtensionPackage.DOCUMENT_ROOT__REPLY_TO_ACTIVITY_ID:
				return REPLY_TO_ACTIVITY_ID_EDEFAULT == null ? replyToActivityId != null
						: !REPLY_TO_ACTIVITY_ID_EDEFAULT.equals(replyToActivityId);
			case XpdExtensionPackage.DOCUMENT_ROOT__TASK_LIBRARY_REFERENCE:
				return taskLibraryReference != null && !taskLibraryReference.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__SET_PERFORMER_IN_PROCESS:
				return isSetSetPerformerInProcess();
			case XpdExtensionPackage.DOCUMENT_ROOT__EMB_SUBPROC_OTHER_STATE_HEIGHT:
				return isSetEmbSubprocOtherStateHeight();
			case XpdExtensionPackage.DOCUMENT_ROOT__EMB_SUBPROC_OTHER_STATE_WIDTH:
				return isSetEmbSubprocOtherStateWidth();
			case XpdExtensionPackage.DOCUMENT_ROOT__FORM_IMPLEMENTATION:
				return formImplementation != null && !formImplementation.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__PARTICIPANT_QUERY:
				return participantQuery != null && !participantQuery.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__API_END_POINT_PARTICIPANT:
				return API_END_POINT_PARTICIPANT_EDEFAULT == null ? apiEndPointParticipant != null
						: !API_END_POINT_PARTICIPANT_EDEFAULT.equals(apiEndPointParticipant);
			case XpdExtensionPackage.DOCUMENT_ROOT__FAULT_MESSAGE:
				return faultMessage != null && !faultMessage.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__REQUEST_ACTIVITY_ID:
				return REQUEST_ACTIVITY_ID_EDEFAULT == null ? requestActivityId != null
						: !REQUEST_ACTIVITY_ID_EDEFAULT.equals(requestActivityId);
			case XpdExtensionPackage.DOCUMENT_ROOT__BUSINESS_PROCESS:
				return businessProcess != null && !businessProcess.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__WSDL_GENERATION:
				return wsdlGeneration != null && !wsdlGeneration.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__TARGET_PRIMITIVE_PROPERTY:
				return isSetTargetPrimitiveProperty();
			case XpdExtensionPackage.DOCUMENT_ROOT__SOURCE_PRIMITIVE_PROPERTY:
				return isSetSourcePrimitiveProperty();
			case XpdExtensionPackage.DOCUMENT_ROOT__DECISION_SERVICE:
				return decisionService != null && !decisionService.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__PARTICIPANT_SHARED_RESOURCE:
				return participantSharedResource != null && !participantSharedResource.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__XPD_MODEL_TYPE:
				return isSetXpdModelType();
			case XpdExtensionPackage.DOCUMENT_ROOT__FLOW_ROUTING_STYLE:
				return flowRoutingStyle != FLOW_ROUTING_STYLE_EDEFAULT;
			case XpdExtensionPackage.DOCUMENT_ROOT__CALENDAR_REFERENCE:
				return calendarReference != null && !calendarReference.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__NON_CANCELLING:
				return nonCancelling != NON_CANCELLING_EDEFAULT;
			case XpdExtensionPackage.DOCUMENT_ROOT__SIGNAL_DATA:
				return !getSignalData().isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__RETRY:
				return retry != null && !retry.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__ACTIVITY_DEADLINE_EVENT_ID:
				return ACTIVITY_DEADLINE_EVENT_ID_EDEFAULT == null ? activityDeadlineEventId != null
						: !ACTIVITY_DEADLINE_EVENT_ID_EDEFAULT.equals(activityDeadlineEventId);
			case XpdExtensionPackage.DOCUMENT_ROOT__START_STRATEGY:
				return startStrategy != START_STRATEGY_EDEFAULT;
			case XpdExtensionPackage.DOCUMENT_ROOT__OVERWRITE_ALREADY_MODIFIED_TASK_DATA:
				return overwriteAlreadyModifiedTaskData != OVERWRITE_ALREADY_MODIFIED_TASK_DATA_EDEFAULT;
			case XpdExtensionPackage.DOCUMENT_ROOT__EVENT_HANDLER_FLOW_STRATEGY:
				return eventHandlerFlowStrategy != EVENT_HANDLER_FLOW_STRATEGY_EDEFAULT;
			case XpdExtensionPackage.DOCUMENT_ROOT__NAMESPACE_PREFIX_MAP:
				return namespacePrefixMap != null && !namespacePrefixMap.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__SUSPEND_RESUME_WITH_PARENT:
				return suspendResumeWithParent != SUSPEND_RESUME_WITH_PARENT_EDEFAULT;
			case XpdExtensionPackage.DOCUMENT_ROOT__SYSTEM_ERROR_ACTION:
				return systemErrorAction != SYSTEM_ERROR_ACTION_EDEFAULT;
			case XpdExtensionPackage.DOCUMENT_ROOT__CORRELATION_TIMEOUT:
				return correlationTimeout != null && !correlationTimeout.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__VALIDATION_CONTROL:
				return validationControl != null && !validationControl.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__REPLY_IMMEDIATE_DATA_MAPPINGS:
				return replyImmediateDataMappings != null && !replyImmediateDataMappings.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__BX_USE_UNQUALIFIED_PROPERTY_NAMES:
				return bxUseUnqualifiedPropertyNames != BX_USE_UNQUALIFIED_PROPERTY_NAMES_EDEFAULT;
			case XpdExtensionPackage.DOCUMENT_ROOT__CASE_REF_TYPE:
				return caseRefType != null && !caseRefType.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__REST_SERVICES:
				return restServices != null && !restServices.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__PUBLISH_AS_REST_SERVICE:
				return publishAsRestService != PUBLISH_AS_REST_SERVICE_EDEFAULT;
			case XpdExtensionPackage.DOCUMENT_ROOT__REST_ACTIVITY_ID:
				return REST_ACTIVITY_ID_EDEFAULT == null ? restActivityId != null
						: !REST_ACTIVITY_ID_EDEFAULT.equals(restActivityId);
			case XpdExtensionPackage.DOCUMENT_ROOT__RESCHEDULE_TIMER_SCRIPT:
				return !getRescheduleTimerScript().isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__DYNAMIC_ORGANIZATION_MAPPINGS:
				return dynamicOrganizationMappings != null && !dynamicOrganizationMappings.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__SIGNAL_HANDLER_ASYNCHRONOUS:
				return signalHandlerAsynchronous != SIGNAL_HANDLER_ASYNCHRONOUS_EDEFAULT;
			case XpdExtensionPackage.DOCUMENT_ROOT__GLOBAL_DATA_OPERATION:
				return globalDataOperation != null && !globalDataOperation.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS:
				return processDataWorkItemAttributeMappings != null && !processDataWorkItemAttributeMappings.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__ALLOW_UNQUALIFIED_SUB_PROCESS_IDENTIFICATION:
				return allowUnqualifiedSubProcessIdentification != ALLOW_UNQUALIFIED_SUB_PROCESS_IDENTIFICATION_EDEFAULT;
			case XpdExtensionPackage.DOCUMENT_ROOT__BPM_RUNTIME_CONFIGURATION:
				return bpmRuntimeConfiguration != null && !bpmRuntimeConfiguration.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__IS_CASE_SERVICE:
				return isSetIsCaseService();
			case XpdExtensionPackage.DOCUMENT_ROOT__REQUIRED_ACCESS_PRIVILEGES:
				return requiredAccessPrivileges != null && !requiredAccessPrivileges.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__CASE_SERVICE:
				return caseService != null && !caseService.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__AD_HOC_TASK_CONFIGURATION:
				return adHocTaskConfiguration != null && !adHocTaskConfiguration.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__IS_EVENT_SUB_PROCESS:
				return isSetIsEventSubProcess();
			case XpdExtensionPackage.DOCUMENT_ROOT__NON_INTERRUPTING_EVENT:
				return isSetNonInterruptingEvent();
			case XpdExtensionPackage.DOCUMENT_ROOT__CORRELATE_IMMEDIATELY:
				return correlateImmediately != CORRELATE_IMMEDIATELY_EDEFAULT;
			case XpdExtensionPackage.DOCUMENT_ROOT__ASYNC_EXECUTION_MODE:
				return isSetAsyncExecutionMode();
			case XpdExtensionPackage.DOCUMENT_ROOT__SIGNAL_TYPE:
				return isSetSignalType();
			case XpdExtensionPackage.DOCUMENT_ROOT__SERVICE_PROCESS_CONFIGURATION:
				return serviceProcessConfiguration != null && !serviceProcessConfiguration.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__LIKE_MAPPING:
				return likeMapping != LIKE_MAPPING_EDEFAULT;
			case XpdExtensionPackage.DOCUMENT_ROOT__SCRIPT_DATA_MAPPER:
				return scriptDataMapper != null && !scriptDataMapper.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__LIKE_MAPPING_EXCLUSIONS:
				return likeMappingExclusions != null && !likeMappingExclusions.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__SOURCE_CONTRIBUTOR_ID:
				return SOURCE_CONTRIBUTOR_ID_EDEFAULT == null ? sourceContributorId != null
						: !SOURCE_CONTRIBUTOR_ID_EDEFAULT.equals(sourceContributorId);
			case XpdExtensionPackage.DOCUMENT_ROOT__TARGET_CONTRIBUTOR_ID:
				return TARGET_CONTRIBUTOR_ID_EDEFAULT == null ? targetContributorId != null
						: !TARGET_CONTRIBUTOR_ID_EDEFAULT.equals(targetContributorId);
			case XpdExtensionPackage.DOCUMENT_ROOT__REST_SERVICE_OPERATION:
				return restServiceOperation != null && !restServiceOperation.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__INPUT_MAPPINGS:
				return inputMappings != null && !inputMappings.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__OUTPUT_MAPPINGS:
				return outputMappings != null && !outputMappings.isEmpty();
			case XpdExtensionPackage.DOCUMENT_ROOT__BUSINESS_SERVICE_PUBLISH_TYPE:
				return isSetBusinessServicePublishType();
			case XpdExtensionPackage.DOCUMENT_ROOT__SUPPRESS_MAX_MAPPINGS_ERROR:
				return suppressMaxMappingsError != SUPPRESS_MAX_MAPPINGS_ERROR_EDEFAULT;
			case XpdExtensionPackage.DOCUMENT_ROOT__FIELD_FORMAT:
				return fieldFormat != FIELD_FORMAT_EDEFAULT;
			case XpdExtensionPackage.DOCUMENT_ROOT__USE_IN:
				return isSetUseIn();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString()
	{
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (implementationType: "); //$NON-NLS-1$
		result.append(implementationType);
		result.append(", continueOnTimeout: "); //$NON-NLS-1$
		result.append(continueOnTimeout);
		result.append(", alias: "); //$NON-NLS-1$
		result.append(alias);
		result.append(", replyImmediately: "); //$NON-NLS-1$
		result.append(replyImmediately);
		result.append(", inlineSubProcess: "); //$NON-NLS-1$
		result.append(inlineSubProcess);
		result.append(", documentationURL: "); //$NON-NLS-1$
		result.append(documentationURL);
		result.append(", implements: "); //$NON-NLS-1$
		result.append(implements_);
		result.append(", processIdentifierField: "); //$NON-NLS-1$
		result.append(processIdentifierField);
		result.append(", visibility: "); //$NON-NLS-1$
		if (visibilityESet) result.append(visibility);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", securityProfile: "); //$NON-NLS-1$
		result.append(securityProfile);
		result.append(", language: "); //$NON-NLS-1$
		result.append(language);
		result.append(", initialValueMapping: "); //$NON-NLS-1$
		result.append(initialValueMapping);
		result.append(", transport: "); //$NON-NLS-1$
		result.append(transport);
		result.append(", isChained: "); //$NON-NLS-1$
		if (isChainedESet) result.append(isChained);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", requireNewTransaction: "); //$NON-NLS-1$
		result.append(requireNewTransaction);
		result.append(", displayName: "); //$NON-NLS-1$
		if (displayNameESet) result.append(displayName);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", catchThrow: "); //$NON-NLS-1$
		result.append(catchThrow);
		result.append(", isRemote: "); //$NON-NLS-1$
		if (isRemoteESet) result.append(isRemote);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", publishAsBusinessService: "); //$NON-NLS-1$
		if (publishAsBusinessServiceESet) result.append(publishAsBusinessService);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", businessServiceCategory: "); //$NON-NLS-1$
		if (businessServiceCategoryESet) result.append(businessServiceCategory);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", generated: "); //$NON-NLS-1$
		result.append(generated);
		result.append(", replyToActivityId: "); //$NON-NLS-1$
		result.append(replyToActivityId);
		result.append(", setPerformerInProcess: "); //$NON-NLS-1$
		if (setPerformerInProcessESet) result.append(setPerformerInProcess);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", embSubprocOtherStateHeight: "); //$NON-NLS-1$
		if (embSubprocOtherStateHeightESet) result.append(embSubprocOtherStateHeight);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", embSubprocOtherStateWidth: "); //$NON-NLS-1$
		if (embSubprocOtherStateWidthESet) result.append(embSubprocOtherStateWidth);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", apiEndPointParticipant: "); //$NON-NLS-1$
		result.append(apiEndPointParticipant);
		result.append(", requestActivityId: "); //$NON-NLS-1$
		result.append(requestActivityId);
		result.append(", targetPrimitiveProperty: "); //$NON-NLS-1$
		if (targetPrimitivePropertyESet) result.append(targetPrimitiveProperty);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", sourcePrimitiveProperty: "); //$NON-NLS-1$
		if (sourcePrimitivePropertyESet) result.append(sourcePrimitiveProperty);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", xpdModelType: "); //$NON-NLS-1$
		if (xpdModelTypeESet) result.append(xpdModelType);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", flowRoutingStyle: "); //$NON-NLS-1$
		result.append(flowRoutingStyle);
		result.append(", nonCancelling: "); //$NON-NLS-1$
		result.append(nonCancelling);
		result.append(", activityDeadlineEventId: "); //$NON-NLS-1$
		result.append(activityDeadlineEventId);
		result.append(", startStrategy: "); //$NON-NLS-1$
		result.append(startStrategy);
		result.append(", overwriteAlreadyModifiedTaskData: "); //$NON-NLS-1$
		result.append(overwriteAlreadyModifiedTaskData);
		result.append(", eventHandlerFlowStrategy: "); //$NON-NLS-1$
		result.append(eventHandlerFlowStrategy);
		result.append(", suspendResumeWithParent: "); //$NON-NLS-1$
		result.append(suspendResumeWithParent);
		result.append(", systemErrorAction: "); //$NON-NLS-1$
		result.append(systemErrorAction);
		result.append(", bxUseUnqualifiedPropertyNames: "); //$NON-NLS-1$
		result.append(bxUseUnqualifiedPropertyNames);
		result.append(", publishAsRestService: "); //$NON-NLS-1$
		result.append(publishAsRestService);
		result.append(", restActivityId: "); //$NON-NLS-1$
		result.append(restActivityId);
		result.append(", signalHandlerAsynchronous: "); //$NON-NLS-1$
		result.append(signalHandlerAsynchronous);
		result.append(", allowUnqualifiedSubProcessIdentification: "); //$NON-NLS-1$
		result.append(allowUnqualifiedSubProcessIdentification);
		result.append(", isCaseService: "); //$NON-NLS-1$
		if (isCaseServiceESet) result.append(isCaseService);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", isEventSubProcess: "); //$NON-NLS-1$
		if (isEventSubProcessESet) result.append(isEventSubProcess);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", nonInterruptingEvent: "); //$NON-NLS-1$
		if (nonInterruptingEventESet) result.append(nonInterruptingEvent);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", correlateImmediately: "); //$NON-NLS-1$
		result.append(correlateImmediately);
		result.append(", asyncExecutionMode: "); //$NON-NLS-1$
		if (asyncExecutionModeESet) result.append(asyncExecutionMode);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", signalType: "); //$NON-NLS-1$
		if (signalTypeESet) result.append(signalType);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", likeMapping: "); //$NON-NLS-1$
		result.append(likeMapping);
		result.append(", sourceContributorId: "); //$NON-NLS-1$
		result.append(sourceContributorId);
		result.append(", targetContributorId: "); //$NON-NLS-1$
		result.append(targetContributorId);
		result.append(", businessServicePublishType: "); //$NON-NLS-1$
		if (businessServicePublishTypeESet) result.append(businessServicePublishType);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", suppressMaxMappingsError: "); //$NON-NLS-1$
		result.append(suppressMaxMappingsError);
		result.append(", fieldFormat: "); //$NON-NLS-1$
		result.append(fieldFormat);
		result.append(", useIn: "); //$NON-NLS-1$
		if (useInESet) result.append(useIn);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(')');
		return result.toString();
	}

} // DocumentRootImpl