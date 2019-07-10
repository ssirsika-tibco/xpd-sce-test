/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.CatchThrow;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.SubFlow;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getImplementationType <em>Implementation Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getDataObjectAttributes <em>Data Object Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getExtendedAttributes <em>Extended Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#isContinueOnTimeout <em>Continue On Timeout</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getAlias <em>Alias</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getConstantPeriod <em>Constant Period</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getUserTaskScripts <em>User Task Scripts</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getAudit <em>Audit</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getScript <em>Script</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#isReplyImmediately <em>Reply Immediately</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getInitialValues <em>Initial Values</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getAssociatedCorrelationFields <em>Associated Correlation Fields</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getAssociatedParameters <em>Associated Parameters</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getImplementedInterface <em>Implemented Interface</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getProcessInterfaces <em>Process Interfaces</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getWsdlEventAssociation <em>Wsdl Event Association</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#isInlineSubProcess <em>Inline Sub Process</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getDocumentationURL <em>Documentation URL</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getImplements <em>Implements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getMultiInstanceScripts <em>Multi Instance Scripts</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getProcessIdentifierField <em>Process Identifier Field</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getExpression <em>Expression</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getVisibility <em>Visibility</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getSecurityProfile <em>Security Profile</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getLanguage <em>Language</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getInitialParameterValue <em>Initial Parameter Value</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#isInitialValueMapping <em>Initial Value Mapping</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getPortTypeOperation <em>Port Type Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getTransport <em>Transport</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#isIsChained <em>Is Chained</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getExternalReference <em>External Reference</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getProcessResourcePatterns <em>Process Resource Patterns</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getEventHandlerInitialisers <em>Event Handler Initialisers</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getActivityResourcePatterns <em>Activity Resource Patterns</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#isRequireNewTransaction <em>Require New Transaction</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getDocumentOperation <em>Document Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getDurationCalculation <em>Duration Calculation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getDiscriminator <em>Discriminator</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getCatchThrow <em>Catch Throw</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#isIsRemote <em>Is Remote</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getCorrelationDataMappings <em>Correlation Data Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getTransformScript <em>Transform Script</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#isPublishAsBusinessService <em>Publish As Business Service</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getBusinessServiceCategory <em>Business Service Category</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getErrorThrowerInfo <em>Error Thrower Info</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getCatchErrorMappings <em>Catch Error Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getConditionalParticipant <em>Conditional Participant</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#isGenerated <em>Generated</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getReplyToActivityId <em>Reply To Activity Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getTaskLibraryReference <em>Task Library Reference</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#isSetPerformerInProcess <em>Set Performer In Process</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getEmbSubprocOtherStateHeight <em>Emb Subproc Other State Height</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getEmbSubprocOtherStateWidth <em>Emb Subproc Other State Width</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getFormImplementation <em>Form Implementation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getParticipantQuery <em>Participant Query</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getApiEndPointParticipant <em>Api End Point Participant</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getFaultMessage <em>Fault Message</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getRequestActivityId <em>Request Activity Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getBusinessProcess <em>Business Process</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getWsdlGeneration <em>Wsdl Generation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#isTargetPrimitiveProperty <em>Target Primitive Property</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#isSourcePrimitiveProperty <em>Source Primitive Property</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getDecisionService <em>Decision Service</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getParticipantSharedResource <em>Participant Shared Resource</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getXpdModelType <em>Xpd Model Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getFlowRoutingStyle <em>Flow Routing Style</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getCalendarReference <em>Calendar Reference</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#isNonCancelling <em>Non Cancelling</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getSignalData <em>Signal Data</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getRetry <em>Retry</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getActivityDeadlineEventId <em>Activity Deadline Event Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getStartStrategy <em>Start Strategy</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#isOverwriteAlreadyModifiedTaskData <em>Overwrite Already Modified Task Data</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getEventHandlerFlowStrategy <em>Event Handler Flow Strategy</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getNamespacePrefixMap <em>Namespace Prefix Map</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#isSuspendResumeWithParent <em>Suspend Resume With Parent</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getSystemErrorAction <em>System Error Action</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getCorrelationTimeout <em>Correlation Timeout</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getValidationControl <em>Validation Control</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getReplyImmediateDataMappings <em>Reply Immediate Data Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#isBxUseUnqualifiedPropertyNames <em>Bx Use Unqualified Property Names</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getCaseRefType <em>Case Ref Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getRESTServices <em>REST Services</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#isPublishAsRestService <em>Publish As Rest Service</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getRestActivityId <em>Rest Activity Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getRescheduleTimerScript <em>Reschedule Timer Script</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getDynamicOrganizationMappings <em>Dynamic Organization Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#isSignalHandlerAsynchronous <em>Signal Handler Asynchronous</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getGlobalDataOperation <em>Global Data Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getProcessDataWorkItemAttributeMappings <em>Process Data Work Item Attribute Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#isAllowUnqualifiedSubProcessIdentification <em>Allow Unqualified Sub Process Identification</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getBpmRuntimeConfiguration <em>Bpm Runtime Configuration</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#isIsCaseService <em>Is Case Service</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getRequiredAccessPrivileges <em>Required Access Privileges</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getCaseService <em>Case Service</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getAdHocTaskConfiguration <em>Ad Hoc Task Configuration</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#isIsEventSubProcess <em>Is Event Sub Process</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#isNonInterruptingEvent <em>Non Interrupting Event</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#isCorrelateImmediately <em>Correlate Immediately</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getAsyncExecutionMode <em>Async Execution Mode</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getSignalType <em>Signal Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getServiceProcessConfiguration <em>Service Process Configuration</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#isLikeMapping <em>Like Mapping</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getScriptDataMapper <em>Script Data Mapper</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getLikeMappingExclusions <em>Like Mapping Exclusions</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getSourceContributorId <em>Source Contributor Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getTargetContributorId <em>Target Contributor Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getRestServiceOperation <em>Rest Service Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getInputMappings <em>Input Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getOutputMappings <em>Output Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getBusinessServicePublishType <em>Business Service Publish Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#isSuppressMaxMappingsError <em>Suppress Max Mappings Error</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentRoot#getFieldFormat <em>Field Format</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface DocumentRoot extends EObject {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>XMLNS Prefix Map</b></em>' map.
     * The key is of type {@link java.lang.String},
     * and the value is of type {@link java.lang.String},
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>XMLNS Prefix Map</em>' map isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * XML namespace prefix map for elements which belong to XpdExtension.
     * <!-- end-model-doc -->
     * @return the value of the '<em>XMLNS Prefix Map</em>' map.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_XMLNSPrefixMap()
     * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry&lt;org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString&gt;" transient="true"
     *        extendedMetaData="kind='attribute' name='xmlns:prefix'"
     * @generated
     */
    EMap<String, String> getXMLNSPrefixMap();

    /**
     * Returns the value of the '<em><b>XSI Schema Location</b></em>' map.
     * The key is of type {@link java.lang.String},
     * and the value is of type {@link java.lang.String},
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>XSI Schema Location</em>' map isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * XSI schema location for XpdExtension elements.
     * <!-- end-model-doc -->
     * @return the value of the '<em>XSI Schema Location</em>' map.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_XSISchemaLocation()
     * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry&lt;org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString&gt;" transient="true"
     *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
     * @generated
     */
    EMap<String, String> getXSISchemaLocation();

    /**
     * Returns the value of the '<em><b>Implementation Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     *  Name of the task implementation currently set.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Implementation Type</em>' attribute.
     * @see #setImplementationType(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_ImplementationType()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='ImplementationType' namespace='##targetNamespace'"
     * @generated
     */
    String getImplementationType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getImplementationType <em>Implementation Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Implementation Type</em>' attribute.
     * @see #getImplementationType()
     * @generated
     */
    void setImplementationType(String value);

    /**
     * Returns the value of the '<em><b>Data Object Attributes</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.XpdExtDataObjectAttributes}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Data Object Attributes</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Contains attributes of a DataObject, added as other element to a DataObject.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Data Object Attributes</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_DataObjectAttributes()
     * @model containment="true"
     *        extendedMetaData="name='DataObjectAttributes' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<XpdExtDataObjectAttributes> getDataObjectAttributes();

    /**
     * Returns the value of the '<em><b>Extended Attributes</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.XpdExtAttributes}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Extended Attributes</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Extended attributes. Added to xpdl elements when extended attributes are defined for those elements.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Extended Attributes</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_ExtendedAttributes()
     * @model containment="true"
     *        extendedMetaData="name='XpdExtAttributes' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<XpdExtAttributes> getExtendedAttributes();

    /**
     * Returns the value of the '<em><b>Continue On Timeout</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * For Intermediate Timer Event on Task Boundary... 
     * If true, override BPMN default behaviour of interrupt task 
     * on timeoput and instead, allow task to continue as well as processing
     * timer event outgoiung flow.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Continue On Timeout</em>' attribute.
     * @see #setContinueOnTimeout(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_ContinueOnTimeout()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
     *        extendedMetaData="kind='attribute' name='ContinueOnTimeout' namespace='##targetNamespace'"
     * @generated
     */
    boolean isContinueOnTimeout();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isContinueOnTimeout <em>Continue On Timeout</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Continue On Timeout</em>' attribute.
     * @see #isContinueOnTimeout()
     * @generated
     */
    void setContinueOnTimeout(boolean value);

    /**
     * Returns the value of the '<em><b>Alias</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     *  Name of the web service alias.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Alias</em>' attribute.
     * @see #setAlias(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_Alias()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='Alias' namespace='##targetNamespace'"
     * @generated
     */
    String getAlias();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getAlias <em>Alias</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Alias</em>' attribute.
     * @see #getAlias()
     * @generated
     */
    void setAlias(String value);

    /**
     * Returns the value of the '<em><b>Constant Period</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.ConstantPeriod}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Constant Period</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Defines constant period time duration and is used for Timer Events by specifying the Script type as ‘Constant Period’ and values of the duration.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Constant Period</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_ConstantPeriod()
     * @model containment="true"
     *        extendedMetaData="name='ConstantPeriod' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<ConstantPeriod> getConstantPeriod();

    /**
     * Returns the value of the '<em><b>User Task Scripts</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.UserTaskScripts}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>User Task Scripts</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Container for the Schedule, Reschedule, Open, Close, Submit scripts for a Work Item of a User Task.
     * <!-- end-model-doc -->
     * @return the value of the '<em>User Task Scripts</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_UserTaskScripts()
     * @model containment="true"
     *        extendedMetaData="name='UserTaskScripts' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<UserTaskScripts> getUserTaskScripts();

    /**
     * Returns the value of the '<em><b>Audit</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.Audit}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Auditing to perform on activity.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Audit</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_Audit()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='Audit' namespace='##targetNamespace'"
     * @generated
     */
    EList<Audit> getAudit();

    /**
     * Returns the value of the '<em><b>Script</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.ScriptInformation}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Script</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Contains information of the script used in a DataMapping. It is used when a data mapping involves a script.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Script</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_Script()
     * @model containment="true"
     *        extendedMetaData="name='ScriptInformation' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<ScriptInformation> getScript();

    /**
     * Returns the value of the '<em><b>Reply Immediately</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Attribute used in Start Message Events to set to reply immediately with Process Id.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Reply Immediately</em>' attribute.
     * @see #setReplyImmediately(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_ReplyImmediately()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
     *        extendedMetaData="kind='attribute' name='ReplyImmediate' namespace='##targetNamespace'"
     * @generated
     */
    boolean isReplyImmediately();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isReplyImmediately <em>Reply Immediately</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Reply Immediately</em>' attribute.
     * @see #isReplyImmediately()
     * @generated
     */
    void setReplyImmediately(boolean value);

    /**
     * Returns the value of the '<em><b>Initial Values</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.InitialValues}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Initial Values</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Type to represent initial values, used for Data Fields and correlation Data as other element.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Initial Values</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_InitialValues()
     * @model containment="true"
     *        extendedMetaData="name='InitialValues' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<InitialValues> getInitialValues();

    /**
     * Returns the value of the '<em><b>Associated Correlation Fields</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.AssociatedCorrelationFields}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Associated Correlation Fields</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Container for Correlation fields associated to correlating activities. Used for xpdl Correlating Activity.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Associated Correlation Fields</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_AssociatedCorrelationFields()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='AssociatedCorrelationFields' namespace='##targetNamespace'"
     * @generated
     */
    EList<AssociatedCorrelationFields> getAssociatedCorrelationFields();

    /**
     * Returns the value of the '<em><b>Associated Parameters</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.AssociatedParameters}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The associated parameters for an activity.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Associated Parameters</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_AssociatedParameters()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='AssociatedParameters' namespace='##targetNamespace'"
     * @generated
     */
    EList<AssociatedParameters> getAssociatedParameters();

    /**
     * Returns the value of the '<em><b>Implemented Interface</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.ImplementedInterface}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * This attribute is to store the process designers intent to 
     * have this processes content in-lined into calling processes in
     * the execution environment.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Implemented Interface</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_ImplementedInterface()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ImplementedInterface' namespace='##targetNamespace'"
     * @generated
     */
    EList<ImplementedInterface> getImplementedInterface();

    /**
     * Returns the value of the '<em><b>Process Interfaces</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.ProcessInterfaces}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Container for Process interfaces, used under a Process package for list of contained process interfaces.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Process Interfaces</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_ProcessInterfaces()
     * @model containment="true"
     *        extendedMetaData="name='ProcessInterfaces' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<ProcessInterfaces> getProcessInterfaces();

    /**
     * Returns the value of the '<em><b>Wsdl Event Association</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.WsdlEventAssociation}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Wsdl Event Association</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * deprecated.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Wsdl Event Association</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_WsdlEventAssociation()
     * @model containment="true"
     * @generated
     */
    EList<WsdlEventAssociation> getWsdlEventAssociation();

    /**
     * Returns the value of the '<em><b>Inline Sub Process</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * This attribute is to store the process designers intent to 
     * have this processes content in-lined into calling processes in
     * the execution environment.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Inline Sub Process</em>' attribute.
     * @see #setInlineSubProcess(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_InlineSubProcess()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
     *        extendedMetaData="kind='attribute' name='InlineSubProcess' namespace='##targetNamespace'"
     * @generated
     */
    boolean isInlineSubProcess();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isInlineSubProcess <em>Inline Sub Process</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Inline Sub Process</em>' attribute.
     * @see #isInlineSubProcess()
     * @generated
     */
    void setInlineSubProcess(boolean value);

    /**
     * Returns the value of the '<em><b>Documentation URL</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * URL for the Process Documentation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Documentation URL</em>' attribute.
     * @see #setDocumentationURL(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_DocumentationURL()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='DocumentationURL' namespace='##targetNamespace'"
     * @generated
     */
    String getDocumentationURL();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getDocumentationURL <em>Documentation URL</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Documentation URL</em>' attribute.
     * @see #getDocumentationURL()
     * @generated
     */
    void setDocumentationURL(String value);

    /**
     * Returns the value of the '<em><b>Implements</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Attribute to indicate a Process implements a Process Interface.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Implements</em>' attribute.
     * @see #setImplements(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_Implements()
     * @model dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='Implements' namespace='##targetNamespace'"
     * @generated
     */
    String getImplements();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getImplements <em>Implements</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Implements</em>' attribute.
     * @see #getImplements()
     * @generated
     */
    void setImplements(String value);

    /**
     * Returns the value of the '<em><b>Multi Instance Scripts</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.MultiInstanceScripts}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Multi Instance Scripts</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * MultiInstanceScripts are scripts which can be defined when a  Task has 'Activity Marker' as 'Multi Instance Loop' and are added to the xpdl element 'LoopMultiInstance'
     * <!-- end-model-doc -->
     * @return the value of the '<em>Multi Instance Scripts</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_MultiInstanceScripts()
     * @model containment="true"
     *        extendedMetaData="name='MultiInstanceScripts' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<MultiInstanceScripts> getMultiInstanceScripts();

    /**
     * Returns the value of the '<em><b>Process Identifier Field</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Reference to the process data which contains the process names to 
     * be invoked.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Process Identifier Field</em>' attribute.
     * @see #setProcessIdentifierField(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_ProcessIdentifierField()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='ProcessIdentifierField' namespace='##targetNamespace'"
     * @generated
     */
    String getProcessIdentifierField();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getProcessIdentifierField <em>Process Identifier Field</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Process Identifier Field</em>' attribute.
     * @see #getProcessIdentifierField()
     * @generated
     */
    void setProcessIdentifierField(String value);

    /**
     * Returns the value of the '<em><b>Expression</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Expression}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Expression</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Expression help define the Script type (JavaScript , RQL, Free Text...) and the corresponding Script.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Expression</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_Expression()
     * @model containment="true"
     *        extendedMetaData="name='Expression' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<Expression> getExpression();

    /**
     * Returns the value of the '<em><b>Visibility</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.Visibility}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Visibility</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Attribute used for Activities to specify the visibility/access outside of process application.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Visibility</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.Visibility
     * @see #isSetVisibility()
     * @see #unsetVisibility()
     * @see #setVisibility(Visibility)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_Visibility()
     * @model unsettable="true" required="true"
     *        extendedMetaData="kind='attribute' name='Visibility' namespace='##targetNamespace'"
     * @generated
     */
    Visibility getVisibility();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getVisibility <em>Visibility</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Visibility</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.Visibility
     * @see #isSetVisibility()
     * @see #unsetVisibility()
     * @see #getVisibility()
     * @generated
     */
    void setVisibility(Visibility value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getVisibility <em>Visibility</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetVisibility()
     * @see #getVisibility()
     * @see #setVisibility(Visibility)
     * @generated
     */
    void unsetVisibility();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getVisibility <em>Visibility</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Visibility</em>' attribute is set.
     * @see #unsetVisibility()
     * @see #getVisibility()
     * @see #setVisibility(Visibility)
     * @generated
     */
    boolean isSetVisibility();

    /**
     * Returns the value of the '<em><b>Security Profile</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     *  Name of the web service alias.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Security Profile</em>' attribute.
     * @see #setSecurityProfile(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_SecurityProfile()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='SecurityProfile' namespace='##targetNamespace'"
     * @generated
     */
    String getSecurityProfile();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getSecurityProfile <em>Security Profile</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Security Profile</em>' attribute.
     * @see #getSecurityProfile()
     * @generated
     */
    void setSecurityProfile(String value);

    /**
     * Returns the value of the '<em><b>Language</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Language</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Language specifies the language of a xpdl 'Package' and is added to the xpdl element 'PackageHeader'
     * <!-- end-model-doc -->
     * @return the value of the '<em>Language</em>' attribute.
     * @see #setLanguage(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_Language()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='Language' namespace='##targetNamespace'"
     * @generated
     */
    String getLanguage();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getLanguage <em>Language</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Language</em>' attribute.
     * @see #getLanguage()
     * @generated
     */
    void setLanguage(String value);

    /**
     * Returns the value of the '<em><b>Initial Parameter Value</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.InitialParameterValue}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Initial Parameter Value</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * deprecated.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Initial Parameter Value</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_InitialParameterValue()
     * @model containment="true"
     *        extendedMetaData="name='InitialParameterValue' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<InitialParameterValue> getInitialParameterValue();

    /**
     * Returns the value of the '<em><b>Initial Value Mapping</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Initial Value Mapping</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Boolean flag that states whether a data mapping is a mapping to a sub-process formal parameter that has pre-defined ‘allowed values’ .
     * <!-- end-model-doc -->
     * @return the value of the '<em>Initial Value Mapping</em>' attribute.
     * @see #setInitialValueMapping(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_InitialValueMapping()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='InitialValueMapping' namespace='##targetNamespace'"
     * @generated
     */
    boolean isInitialValueMapping();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isInitialValueMapping <em>Initial Value Mapping</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Initial Value Mapping</em>' attribute.
     * @see #isInitialValueMapping()
     * @generated
     */
    void setInitialValueMapping(boolean value);

    /**
     * Returns the value of the '<em><b>Port Type Operation</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.PortTypeOperation}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Port Type Operation</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * PortTypeOperation defines the web service port type.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Port Type Operation</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_PortTypeOperation()
     * @model containment="true"
     *        extendedMetaData="name='PortTypeOperation' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<PortTypeOperation> getPortTypeOperation();

    /**
     * Returns the value of the '<em><b>Transport</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Transport</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Transport defines the web service transport type.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Transport</em>' attribute.
     * @see #setTransport(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_Transport()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='Transport' namespace='##targetNamespace'"
     * @generated
     */
    String getTransport();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getTransport <em>Transport</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Transport</em>' attribute.
     * @see #getTransport()
     * @generated
     */
    void setTransport(String value);

    /**
     * Returns the value of the '<em><b>Is Chained</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Is Chained</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * isChained property helps determine if the Tasks conained in the embedded sub process are to be executed sequentially by the same user.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Is Chained</em>' attribute.
     * @see #isSetIsChained()
     * @see #unsetIsChained()
     * @see #setIsChained(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_IsChained()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
     *        extendedMetaData="kind='attribute' name='IsChained' namespace='##targetNamespace'"
     * @generated
     */
    boolean isIsChained();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isIsChained <em>Is Chained</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Is Chained</em>' attribute.
     * @see #isSetIsChained()
     * @see #unsetIsChained()
     * @see #isIsChained()
     * @generated
     */
    void setIsChained(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isIsChained <em>Is Chained</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetIsChained()
     * @see #isIsChained()
     * @see #setIsChained(boolean)
     * @generated
     */
    void unsetIsChained();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isIsChained <em>Is Chained</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Is Chained</em>' attribute is set.
     * @see #unsetIsChained()
     * @see #isIsChained()
     * @see #setIsChained(boolean)
     * @generated
     */
    boolean isSetIsChained();

    /**
     * Returns the value of the '<em><b>External Reference</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.ExternalReference}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>External Reference</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ExternalReference allow predefined elements present in different scope to be referenced externally. (eq datafields externally referencing BOM classes, participants externally referencing OM elements)
     * <!-- end-model-doc -->
     * @return the value of the '<em>External Reference</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_ExternalReference()
     * @model containment="true"
     *        extendedMetaData="name='ExternalReference' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<ExternalReference> getExternalReference();

    /**
     * Returns the value of the '<em><b>Process Resource Patterns</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.ProcessResourcePatterns}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Process Resource Patterns</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Used to define process work allocation strategy.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Process Resource Patterns</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_ProcessResourcePatterns()
     * @model containment="true"
     *        extendedMetaData="name='ProcessResourcePatterns' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<ProcessResourcePatterns> getProcessResourcePatterns();

    /**
     * Returns the value of the '<em><b>Event Handler Initialisers</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.EventHandlerInitialisers}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Event Handler Initialisers</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * EventHandlerInitialisers specify the 'Activity/Activities' that should be completed for event handler to be invoked/executed.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Event Handler Initialisers</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_EventHandlerInitialisers()
     * @model containment="true"
     *        extendedMetaData="name='EventHandlerInitialisers' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<EventHandlerInitialisers> getEventHandlerInitialisers();

    /**
     * Returns the value of the '<em><b>Activity Resource Patterns</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.ActivityResourcePatterns}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Activity Resource Patterns</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Used to define Activity work allocation strategy
     * <!-- end-model-doc -->
     * @return the value of the '<em>Activity Resource Patterns</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_ActivityResourcePatterns()
     * @model containment="true"
     *        extendedMetaData="name='ActivityResourcePatterns' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<ActivityResourcePatterns> getActivityResourcePatterns();

    /**
     * Returns the value of the '<em><b>Require New Transaction</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Require New Transaction</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * RequireNewTransaction specify if an embedded/event sub process should be opened as a new transaction.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Require New Transaction</em>' attribute.
     * @see #setRequireNewTransaction(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_RequireNewTransaction()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='RequireNewTransaction' namespace='##targetNamespace'"
     * @generated
     */
    boolean isRequireNewTransaction();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isRequireNewTransaction <em>Require New Transaction</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Require New Transaction</em>' attribute.
     * @see #isRequireNewTransaction()
     * @generated
     */
    void setRequireNewTransaction(boolean value);

    /**
     * Returns the value of the '<em><b>Document Operation</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.DocumentOperation}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Document Operation</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Type to represent the document related operations. It is used as the details of Service Task of type ‘Document Operations’.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Document Operation</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_DocumentOperation()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='DocumentOperation' namespace='##targetNamespace'"
     * @generated
     */
    EList<DocumentOperation> getDocumentOperation();

    /**
     * Returns the value of the '<em><b>Duration Calculation</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.DurationCalculation}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Duration Calculation</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Element specifying an expression-based duration.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Duration Calculation</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_DurationCalculation()
     * @model containment="true"
     *        extendedMetaData="name='DurationCalculation' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<DurationCalculation> getDurationCalculation();

    /**
     * Returns the value of the '<em><b>Discriminator</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.Discriminator}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Discriminator</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Element specifying the structured discriminator for complex gateway.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Discriminator</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_Discriminator()
     * @model containment="true"
     *        extendedMetaData="name='Discriminator' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<Discriminator> getDiscriminator();

    /**
     * Returns the value of the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Display Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Attribute specifying the display name/label of xpdl elements.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Display Name</em>' attribute.
     * @see #isSetDisplayName()
     * @see #unsetDisplayName()
     * @see #setDisplayName(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_DisplayName()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='DisplayName' namespace='##targetNamespace'"
     * @generated
     */
    String getDisplayName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getDisplayName <em>Display Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Display Name</em>' attribute.
     * @see #isSetDisplayName()
     * @see #unsetDisplayName()
     * @see #getDisplayName()
     * @generated
     */
    void setDisplayName(String value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getDisplayName <em>Display Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDisplayName()
     * @see #getDisplayName()
     * @see #setDisplayName(String)
     * @generated
     */
    void unsetDisplayName();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getDisplayName <em>Display Name</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Display Name</em>' attribute is set.
     * @see #unsetDisplayName()
     * @see #getDisplayName()
     * @see #setDisplayName(String)
     * @generated
     */
    boolean isSetDisplayName();

    /**
     * Returns the value of the '<em><b>Catch Throw</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.CatchThrow}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Catch Throw</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Attribute specifying the type of event(catch or throw).
     * <!-- end-model-doc -->
     * @return the value of the '<em>Catch Throw</em>' attribute.
     * @see com.tibco.xpd.xpdl2.CatchThrow
     * @see #setCatchThrow(CatchThrow)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_CatchThrow()
     * @model unique="false" required="true"
     *        extendedMetaData="kind='attribute' name='CatchThrow' namespace='##targetNamespace'"
     * @generated
     */
    CatchThrow getCatchThrow();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getCatchThrow <em>Catch Throw</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Catch Throw</em>' attribute.
     * @see com.tibco.xpd.xpdl2.CatchThrow
     * @see #getCatchThrow()
     * @generated
     */
    void setCatchThrow(CatchThrow value);

    /**
     * Returns the value of the '<em><b>Is Remote</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Is Remote</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * isRemote defines whether a local or remote WSDL will be used for web service operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Is Remote</em>' attribute.
     * @see #isSetIsRemote()
     * @see #unsetIsRemote()
     * @see #setIsRemote(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_IsRemote()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
     *        extendedMetaData="kind='attribute' name='IsRemote' namespace='##targetNamespace'"
     * @generated
     */
    boolean isIsRemote();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isIsRemote <em>Is Remote</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Is Remote</em>' attribute.
     * @see #isSetIsRemote()
     * @see #unsetIsRemote()
     * @see #isIsRemote()
     * @generated
     */
    void setIsRemote(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isIsRemote <em>Is Remote</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetIsRemote()
     * @see #isIsRemote()
     * @see #setIsRemote(boolean)
     * @generated
     */
    void unsetIsRemote();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isIsRemote <em>Is Remote</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Is Remote</em>' attribute is set.
     * @see #unsetIsRemote()
     * @see #isIsRemote()
     * @see #setIsRemote(boolean)
     * @generated
     */
    boolean isSetIsRemote();

    /**
     * Returns the value of the '<em><b>Correlation Data Mappings</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.CorrelationDataMappings}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Correlation Data Mappings</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * CorrelationDataMapping define mappings of correlation data for correlating activities.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Correlation Data Mappings</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_CorrelationDataMappings()
     * @model containment="true"
     *        extendedMetaData="name='CorrelationDataMappings' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<CorrelationDataMappings> getCorrelationDataMappings();

    /**
     * Returns the value of the '<em><b>Transform Script</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.TransformScript}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Transform Script</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     *  Model for Transform Script Editor bound for iProcess.(currently not bound to the BPM destination and probably never will be).
     * <!-- end-model-doc -->
     * @return the value of the '<em>Transform Script</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_TransformScript()
     * @model containment="true"
     *        extendedMetaData="name='TransformScript' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<TransformScript> getTransformScript();

    /**
     * Returns the value of the '<em><b>Publish As Business Service</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Publish As Business Service</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Attribute that specifies if a pageflow is published as a business service.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Publish As Business Service</em>' attribute.
     * @see #isSetPublishAsBusinessService()
     * @see #unsetPublishAsBusinessService()
     * @see #setPublishAsBusinessService(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_PublishAsBusinessService()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
     *        extendedMetaData="kind='attribute' name='PublishAsBusinessService' namespace='##targetNamespace'"
     * @generated
     */
    boolean isPublishAsBusinessService();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isPublishAsBusinessService <em>Publish As Business Service</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Publish As Business Service</em>' attribute.
     * @see #isSetPublishAsBusinessService()
     * @see #unsetPublishAsBusinessService()
     * @see #isPublishAsBusinessService()
     * @generated
     */
    void setPublishAsBusinessService(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isPublishAsBusinessService <em>Publish As Business Service</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetPublishAsBusinessService()
     * @see #isPublishAsBusinessService()
     * @see #setPublishAsBusinessService(boolean)
     * @generated
     */
    void unsetPublishAsBusinessService();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isPublishAsBusinessService <em>Publish As Business Service</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Publish As Business Service</em>' attribute is set.
     * @see #unsetPublishAsBusinessService()
     * @see #isPublishAsBusinessService()
     * @see #setPublishAsBusinessService(boolean)
     * @generated
     */
    boolean isSetPublishAsBusinessService();

    /**
     * Returns the value of the '<em><b>Business Service Category</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Business Service Category</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Attribute to specify the category of the business service.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Business Service Category</em>' attribute.
     * @see #isSetBusinessServiceCategory()
     * @see #unsetBusinessServiceCategory()
     * @see #setBusinessServiceCategory(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_BusinessServiceCategory()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='BusinessServiceCategory' namespace='##targetNamespace'"
     * @generated
     */
    String getBusinessServiceCategory();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getBusinessServiceCategory <em>Business Service Category</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Business Service Category</em>' attribute.
     * @see #isSetBusinessServiceCategory()
     * @see #unsetBusinessServiceCategory()
     * @see #getBusinessServiceCategory()
     * @generated
     */
    void setBusinessServiceCategory(String value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getBusinessServiceCategory <em>Business Service Category</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetBusinessServiceCategory()
     * @see #getBusinessServiceCategory()
     * @see #setBusinessServiceCategory(String)
     * @generated
     */
    void unsetBusinessServiceCategory();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getBusinessServiceCategory <em>Business Service Category</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Business Service Category</em>' attribute is set.
     * @see #unsetBusinessServiceCategory()
     * @see #getBusinessServiceCategory()
     * @see #setBusinessServiceCategory(String)
     * @generated
     */
    boolean isSetBusinessServiceCategory();

    /**
     * Returns the value of the '<em><b>Error Thrower Info</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.ErrorThrowerInfo}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * This element is added to the xpdl:ResultError element of an intermediate
     * catch error event when the error has been set (in Business Studio) to
     * catch an error thrown by a specific activity.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Error Thrower Info</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_ErrorThrowerInfo()
     * @model containment="true"
     *        extendedMetaData="name='ErrorThrowerInfo' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<ErrorThrowerInfo> getErrorThrowerInfo();

    /**
     * Returns the value of the '<em><b>Catch Error Mappings</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.CatchErrorMappings}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * This element is added to the xpdl:ResultError element of an intermediate
     * catch error event to specify the data mappings to the data output with the 
     * caught error.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Catch Error Mappings</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_CatchErrorMappings()
     * @model containment="true"
     *        extendedMetaData="name='CatchErrorMappings' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<CatchErrorMappings> getCatchErrorMappings();

    /**
     * Returns the value of the '<em><b>Conditional Participant</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.ConditionalParticipant}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Conditional Participant</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * deprecated.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Conditional Participant</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_ConditionalParticipant()
     * @model containment="true"
     *        extendedMetaData="name='ConditionalParticipant' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<ConditionalParticipant> getConditionalParticipant();

    /**
     * Returns the value of the '<em><b>Generated</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Generated</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Attribute that specifies if an Activity generates wsdl.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Generated</em>' attribute.
     * @see #setGenerated(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_Generated()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
     *        extendedMetaData="kind='attribute' name='Generated' namespace='##targetNamespace'"
     * @generated
     */
    boolean isGenerated();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isGenerated <em>Generated</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Generated</em>' attribute.
     * @see #isGenerated()
     * @generated
     */
    void setGenerated(boolean value);

    /**
     * Returns the value of the '<em><b>Reply To Activity Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Attribute added to xpdl2:TaskSend / xpdl2:TriggerResultMessage for throw
     * message intermediate/end event when it has been configured to 
     * be a reply to  message received by start/intermedaite/receiveTask.
     * 
     * <!-- end-model-doc -->
     * @return the value of the '<em>Reply To Activity Id</em>' attribute.
     * @see #setReplyToActivityId(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_ReplyToActivityId()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='ReplyToActivityId' namespace='##targetNamespace'"
     * @generated
     */
    String getReplyToActivityId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getReplyToActivityId <em>Reply To Activity Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Reply To Activity Id</em>' attribute.
     * @see #getReplyToActivityId()
     * @generated
     */
    void setReplyToActivityId(String value);

    /**
     * Returns the value of the '<em><b>Task Library Reference</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.TaskLibraryReference}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Task Library Reference</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * This element is added under Activity xpdl:Reference element for 
     * reference tasks that reference an activity in another process / package.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Task Library Reference</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_TaskLibraryReference()
     * @model containment="true"
     *        extendedMetaData="name='TaskLibraryReference' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<TaskLibraryReference> getTaskLibraryReference();

    /**
     * Returns the value of the '<em><b>Set Performer In Process</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Set Performer In Process</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * This attribute marks an activity in a Task Library to allow setting of performer from the process on usage, and not a performer of the parent Task Library.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Set Performer In Process</em>' attribute.
     * @see #isSetSetPerformerInProcess()
     * @see #unsetSetPerformerInProcess()
     * @see #setSetPerformerInProcess(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_SetPerformerInProcess()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='SetPerformerInProcess' namespace='##targetNamespace'"
     * @generated
     */
    boolean isSetPerformerInProcess();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isSetPerformerInProcess <em>Set Performer In Process</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Set Performer In Process</em>' attribute.
     * @see #isSetSetPerformerInProcess()
     * @see #unsetSetPerformerInProcess()
     * @see #isSetPerformerInProcess()
     * @generated
     */
    void setSetPerformerInProcess(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isSetPerformerInProcess <em>Set Performer In Process</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetSetPerformerInProcess()
     * @see #isSetPerformerInProcess()
     * @see #setSetPerformerInProcess(boolean)
     * @generated
     */
    void unsetSetPerformerInProcess();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isSetPerformerInProcess <em>Set Performer In Process</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Set Performer In Process</em>' attribute is set.
     * @see #unsetSetPerformerInProcess()
     * @see #isSetPerformerInProcess()
     * @see #setSetPerformerInProcess(boolean)
     * @generated
     */
    boolean isSetSetPerformerInProcess();

    /**
     * Returns the value of the '<em><b>Emb Subproc Other State Height</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Added to the BlockActivity for an embedded sub-proces activity.
     * This stores the last set size / width of the OPPOSITE to current state size/width.
     * i.e. if the current state is ViewType=COLLAPSED then this stores the last 
     * set size whilst the sub-process was in expanded state.
     * 
     * <!-- end-model-doc -->
     * @return the value of the '<em>Emb Subproc Other State Height</em>' attribute.
     * @see #isSetEmbSubprocOtherStateHeight()
     * @see #unsetEmbSubprocOtherStateHeight()
     * @see #setEmbSubprocOtherStateHeight(double)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_EmbSubprocOtherStateHeight()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
     *        extendedMetaData="kind='attribute' name='EmbSubprocOtherStateHeight' namespace='##targetNamespace'"
     * @generated
     */
    double getEmbSubprocOtherStateHeight();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getEmbSubprocOtherStateHeight <em>Emb Subproc Other State Height</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Emb Subproc Other State Height</em>' attribute.
     * @see #isSetEmbSubprocOtherStateHeight()
     * @see #unsetEmbSubprocOtherStateHeight()
     * @see #getEmbSubprocOtherStateHeight()
     * @generated
     */
    void setEmbSubprocOtherStateHeight(double value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getEmbSubprocOtherStateHeight <em>Emb Subproc Other State Height</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetEmbSubprocOtherStateHeight()
     * @see #getEmbSubprocOtherStateHeight()
     * @see #setEmbSubprocOtherStateHeight(double)
     * @generated
     */
    void unsetEmbSubprocOtherStateHeight();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getEmbSubprocOtherStateHeight <em>Emb Subproc Other State Height</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Emb Subproc Other State Height</em>' attribute is set.
     * @see #unsetEmbSubprocOtherStateHeight()
     * @see #getEmbSubprocOtherStateHeight()
     * @see #setEmbSubprocOtherStateHeight(double)
     * @generated
     */
    boolean isSetEmbSubprocOtherStateHeight();

    /**
     * Returns the value of the '<em><b>Emb Subproc Other State Width</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Added to the BlockActivity for an embedded sub-proces activity.
     * This stores the last set size / width of the OPPOSITE to current state size/width.
     * i.e. if the current state is ViewType=COLLAPSED then this stores the last 
     * set size whilst the sub-process was in expanded state.
     * 
     * <!-- end-model-doc -->
     * @return the value of the '<em>Emb Subproc Other State Width</em>' attribute.
     * @see #isSetEmbSubprocOtherStateWidth()
     * @see #unsetEmbSubprocOtherStateWidth()
     * @see #setEmbSubprocOtherStateWidth(double)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_EmbSubprocOtherStateWidth()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
     *        extendedMetaData="kind='attribute' name='EmbSubprocOtherStateWidth' namespace='##targetNamespace'"
     * @generated
     */
    double getEmbSubprocOtherStateWidth();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getEmbSubprocOtherStateWidth <em>Emb Subproc Other State Width</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Emb Subproc Other State Width</em>' attribute.
     * @see #isSetEmbSubprocOtherStateWidth()
     * @see #unsetEmbSubprocOtherStateWidth()
     * @see #getEmbSubprocOtherStateWidth()
     * @generated
     */
    void setEmbSubprocOtherStateWidth(double value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getEmbSubprocOtherStateWidth <em>Emb Subproc Other State Width</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetEmbSubprocOtherStateWidth()
     * @see #getEmbSubprocOtherStateWidth()
     * @see #setEmbSubprocOtherStateWidth(double)
     * @generated
     */
    void unsetEmbSubprocOtherStateWidth();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getEmbSubprocOtherStateWidth <em>Emb Subproc Other State Width</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Emb Subproc Other State Width</em>' attribute is set.
     * @see #unsetEmbSubprocOtherStateWidth()
     * @see #getEmbSubprocOtherStateWidth()
     * @see #setEmbSubprocOtherStateWidth(double)
     * @generated
     */
    boolean isSetEmbSubprocOtherStateWidth();

    /**
     * Returns the value of the '<em><b>Form Implementation</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.FormImplementation}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Form Implementation information - 
     * This is added to TaskUser element of user task activities.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Form Implementation</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_FormImplementation()
     * @model containment="true"
     *        extendedMetaData="name='FormImplementation' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<FormImplementation> getFormImplementation();

    /**
     * Returns the value of the '<em><b>Participant Query</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Expression}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ParticipantQuery -
     * Organisation Model Query Scripts are persisted as these participant query.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Participant Query</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_ParticipantQuery()
     * @model containment="true"
     *        extendedMetaData="name='ParticipantQuery' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<Expression> getParticipantQuery();

    /**
     * Returns the value of the '<em><b>Api End Point Participant</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Reference (by Id) of the package participant that is used as the default endpoint participant for all process API activities (stored on xpdl2:WorkflowProcess).
     * <!-- end-model-doc -->
     * @return the value of the '<em>Api End Point Participant</em>' attribute.
     * @see #setApiEndPointParticipant(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_ApiEndPointParticipant()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='ApiEndPointParticipant' namespace='##targetNamespace'"
     * @generated
     */
    String getApiEndPointParticipant();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getApiEndPointParticipant <em>Api End Point Participant</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Api End Point Participant</em>' attribute.
     * @see #getApiEndPointParticipant()
     * @generated
     */
    void setApiEndPointParticipant(String value);

    /**
     * Returns the value of the '<em><b>Fault Message</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Message}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Element added to xpdl2:ResultError for throw error end event when that event 
     * is configured to throw a fault message defined for the operation in the referenced
     * incoming message request activity (ref'd via xpdl2:ResultError/xpdExt:RequestActivityId).
     * <!-- end-model-doc -->
     * @return the value of the '<em>Fault Message</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_FaultMessage()
     * @model containment="true"
     *        extendedMetaData="name='FaultMessage' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<Message> getFaultMessage();

    /**
     * Returns the value of the '<em><b>Request Activity Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Attribute added to xpdl2:RssultError for a throw error end event configured to
     * throw a fault message for the operation of the incoming message request 
     * activity referenced defined by this attribute.
     * 
     * <!-- end-model-doc -->
     * @return the value of the '<em>Request Activity Id</em>' attribute.
     * @see #setRequestActivityId(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_RequestActivityId()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='RequestActivityId' namespace='##targetNamespace'"
     * @generated
     */
    String getRequestActivityId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getRequestActivityId <em>Request Activity Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Request Activity Id</em>' attribute.
     * @see #getRequestActivityId()
     * @generated
     */
    void setRequestActivityId(String value);

    /**
     * Returns the value of the '<em><b>Business Process</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.BusinessProcess}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Business Process</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Used in TaskSend to specify the Receive Task Activity of a given Process which is being invoked.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Business Process</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_BusinessProcess()
     * @model containment="true"
     *        extendedMetaData="name='BusinessProcess' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<BusinessProcess> getBusinessProcess();

    /**
     * Returns the value of the '<em><b>Wsdl Generation</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.WsdlGeneration}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Wsdl Generation</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Extension element associated with process interfaces to say how the WSDL was being generated (what soap binding style to be used). Deprecated.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Wsdl Generation</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_WsdlGeneration()
     * @model containment="true"
     *        extendedMetaData="name='WsdlGeneration' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<WsdlGeneration> getWsdlGeneration();

    /**
     * Returns the value of the '<em><b>Target Primitive Property</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Target Primitive Property</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Attribute used in DataMapping to specify that the target is of Primitive type.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Target Primitive Property</em>' attribute.
     * @see #isSetTargetPrimitiveProperty()
     * @see #unsetTargetPrimitiveProperty()
     * @see #setTargetPrimitiveProperty(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_TargetPrimitiveProperty()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='TargetPrimitiveProperty' namespace='##targetNamespace'"
     * @generated
     */
    boolean isTargetPrimitiveProperty();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isTargetPrimitiveProperty <em>Target Primitive Property</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Target Primitive Property</em>' attribute.
     * @see #isSetTargetPrimitiveProperty()
     * @see #unsetTargetPrimitiveProperty()
     * @see #isTargetPrimitiveProperty()
     * @generated
     */
    void setTargetPrimitiveProperty(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isTargetPrimitiveProperty <em>Target Primitive Property</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetTargetPrimitiveProperty()
     * @see #isTargetPrimitiveProperty()
     * @see #setTargetPrimitiveProperty(boolean)
     * @generated
     */
    void unsetTargetPrimitiveProperty();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isTargetPrimitiveProperty <em>Target Primitive Property</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Target Primitive Property</em>' attribute is set.
     * @see #unsetTargetPrimitiveProperty()
     * @see #isTargetPrimitiveProperty()
     * @see #setTargetPrimitiveProperty(boolean)
     * @generated
     */
    boolean isSetTargetPrimitiveProperty();

    /**
     * Returns the value of the '<em><b>Source Primitive Property</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Source Primitive Property</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Attribute used in DataMapping to specify that the source is of Primitive type.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Source Primitive Property</em>' attribute.
     * @see #isSetSourcePrimitiveProperty()
     * @see #unsetSourcePrimitiveProperty()
     * @see #setSourcePrimitiveProperty(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_SourcePrimitiveProperty()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='SourcePrimitiveProperty' namespace='##targetNamespace'"
     * @generated
     */
    boolean isSourcePrimitiveProperty();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isSourcePrimitiveProperty <em>Source Primitive Property</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Source Primitive Property</em>' attribute.
     * @see #isSetSourcePrimitiveProperty()
     * @see #unsetSourcePrimitiveProperty()
     * @see #isSourcePrimitiveProperty()
     * @generated
     */
    void setSourcePrimitiveProperty(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isSourcePrimitiveProperty <em>Source Primitive Property</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetSourcePrimitiveProperty()
     * @see #isSourcePrimitiveProperty()
     * @see #setSourcePrimitiveProperty(boolean)
     * @generated
     */
    void unsetSourcePrimitiveProperty();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isSourcePrimitiveProperty <em>Source Primitive Property</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Source Primitive Property</em>' attribute is set.
     * @see #unsetSourcePrimitiveProperty()
     * @see #isSourcePrimitiveProperty()
     * @see #setSourcePrimitiveProperty(boolean)
     * @generated
     */
    boolean isSetSourcePrimitiveProperty();

    /**
     * Returns the value of the '<em><b>Decision Service</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.SubFlow}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Decision Service</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * DecisionService element is added to 'Decision Service tasks' which help them invoke a decision flow.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Decision Service</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_DecisionService()
     * @model containment="true"
     *        extendedMetaData="name='DecisionService' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<SubFlow> getDecisionService();

    /**
     * Returns the value of the '<em><b>Participant Shared Resource</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.ParticipantSharedResource}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Participant Shared Resource</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Participant shared resource specifies the type of shared resource (email, web-serice, java ..) associated with a participant. This element is added under the xpdl element 'Participant'.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Participant Shared Resource</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_ParticipantSharedResource()
     * @model containment="true"
     *        extendedMetaData="name='ParticipantSharedResource' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<ParticipantSharedResource> getParticipantSharedResource();

    /**
     * Returns the value of the '<em><b>Xpd Model Type</b></em>' attribute.
     * The default value is <code>"PageFlow"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.XpdModelType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Xpd Model Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * XpdModelType attribute is added to xpdl element 'WorkflowProcess' and specifies the Model type(pageflow , decision flow, task library..)
     * <!-- end-model-doc -->
     * @return the value of the '<em>Xpd Model Type</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.XpdModelType
     * @see #isSetXpdModelType()
     * @see #unsetXpdModelType()
     * @see #setXpdModelType(XpdModelType)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_XpdModelType()
     * @model default="PageFlow" unique="false" unsettable="true" required="true"
     *        extendedMetaData="kind='attribute' name='XpdModelType' namespace='##targetNamespace'"
     * @generated
     */
    XpdModelType getXpdModelType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getXpdModelType <em>Xpd Model Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Xpd Model Type</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.XpdModelType
     * @see #isSetXpdModelType()
     * @see #unsetXpdModelType()
     * @see #getXpdModelType()
     * @generated
     */
    void setXpdModelType(XpdModelType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getXpdModelType <em>Xpd Model Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetXpdModelType()
     * @see #getXpdModelType()
     * @see #setXpdModelType(XpdModelType)
     * @generated
     */
    void unsetXpdModelType();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getXpdModelType <em>Xpd Model Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Xpd Model Type</em>' attribute is set.
     * @see #unsetXpdModelType()
     * @see #getXpdModelType()
     * @see #setXpdModelType(XpdModelType)
     * @generated
     */
    boolean isSetXpdModelType();

    /**
     * Returns the value of the '<em><b>Flow Routing Style</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.FlowRoutingStyle}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Flow Routing Style</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * FlowRoutingStyle attribute is added to the xpdl element 'WorkflowProcess' and  specifies the routing style of the process.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Flow Routing Style</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.FlowRoutingStyle
     * @see #setFlowRoutingStyle(FlowRoutingStyle)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_FlowRoutingStyle()
     * @model extendedMetaData="kind='attribute' name='FlowRoutingStyle' namespace='##targetNamespace'"
     * @generated
     */
    FlowRoutingStyle getFlowRoutingStyle();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getFlowRoutingStyle <em>Flow Routing Style</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Flow Routing Style</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.FlowRoutingStyle
     * @see #getFlowRoutingStyle()
     * @generated
     */
    void setFlowRoutingStyle(FlowRoutingStyle value);

    /**
     * Returns the value of the '<em><b>Calendar Reference</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.CalendarReference}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Calendar reference on Process and Timer Event.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Calendar Reference</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_CalendarReference()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='CalendarReference' namespace='##targetNamespace'"
     * @generated
     */
    EList<CalendarReference> getCalendarReference();

    /**
     * Returns the value of the '<em><b>Non Cancelling</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * For Intermediate Catch Signal Event on Task Boundary... 
     * If true, override BPMN default behaviour of cancel task 
     * on catch signal and instead, allow task to continue as well as processing
     * signal event outgoing flow.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Non Cancelling</em>' attribute.
     * @see #setNonCancelling(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_NonCancelling()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
     *        extendedMetaData="kind='attribute' name='NonCancelling' namespace='##targetNamespace'"
     * @generated
     */
    boolean isNonCancelling();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isNonCancelling <em>Non Cancelling</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Non Cancelling</em>' attribute.
     * @see #isNonCancelling()
     * @generated
     */
    void setNonCancelling(boolean value);

    /**
     * Returns the value of the '<em><b>Signal Data</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.SignalData}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Container for Signal specific data like Data mapping and Reschedule configuration, used by  TriggerResultSignal for Intermediate Catch Events
     * <!-- end-model-doc -->
     * @return the value of the '<em>Signal Data</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_SignalData()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='SignalData' namespace='##targetNamespace'"
     * @generated
     */
    EList<SignalData> getSignalData();

    /**
     * Returns the value of the '<em><b>Retry</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.Retry}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Retry Configuration element used by xpdl:Activity to specify retry options.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Retry</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_Retry()
     * @model containment="true"
     *        extendedMetaData="name='Retry' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<Retry> getRetry();

    /**
     * Returns the value of the '<em><b>Activity Deadline Event Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Activity Deadline Event Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Attribute used in Activity to specify the attached timer event configured to be used as the Activity’s Deadline.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Activity Deadline Event Id</em>' attribute.
     * @see #setActivityDeadlineEventId(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_ActivityDeadlineEventId()
     * @model dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='ActivityDeadlineEventId' namespace='##targetNamespace'"
     * @generated
     */
    String getActivityDeadlineEventId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getActivityDeadlineEventId <em>Activity Deadline Event Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Activity Deadline Event Id</em>' attribute.
     * @see #getActivityDeadlineEventId()
     * @generated
     */
    void setActivityDeadlineEventId(String value);

    /**
     * Returns the value of the '<em><b>Start Strategy</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.SubProcessStartStrategy}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Added to xpdl:Subflow elemnt to define the start strategy 
     * for sub-process invocations
     * <!-- end-model-doc -->
     * @return the value of the '<em>Start Strategy</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.SubProcessStartStrategy
     * @see #setStartStrategy(SubProcessStartStrategy)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_StartStrategy()
     * @model extendedMetaData="kind='attribute' name='StartStrategy' namespace='##targetNamespace'"
     * @generated
     */
    SubProcessStartStrategy getStartStrategy();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getStartStrategy <em>Start Strategy</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Start Strategy</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.SubProcessStartStrategy
     * @see #getStartStrategy()
     * @generated
     */
    void setStartStrategy(SubProcessStartStrategy value);

    /**
     * Returns the value of the '<em><b>Overwrite Already Modified Task Data</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Attribtue added to xpdl:Activity element to specify whether already 
     * changed work item data should be overwritten on receipt of an 
     * update-task-item-data via non-cancelling event.
     * - OverwriteAlreadyModifiedData = (IgnoreIncomingData=false) in Work type.
     * 
     * <!-- end-model-doc -->
     * @return the value of the '<em>Overwrite Already Modified Task Data</em>' attribute.
     * @see #setOverwriteAlreadyModifiedTaskData(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_OverwriteAlreadyModifiedTaskData()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
     *        extendedMetaData="kind='attribute' name='OverwriteAlreadyModifiedTaskData' namespace='##targetNamespace'"
     * @generated
     */
    boolean isOverwriteAlreadyModifiedTaskData();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isOverwriteAlreadyModifiedTaskData <em>Overwrite Already Modified Task Data</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Overwrite Already Modified Task Data</em>' attribute.
     * @see #isOverwriteAlreadyModifiedTaskData()
     * @generated
     */
    void setOverwriteAlreadyModifiedTaskData(boolean value);

    /**
     * Returns the value of the '<em><b>Event Handler Flow Strategy</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.EventHandlerFlowStrategy}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Attribtue added to xpdl:TriggerResultMessage element for event handler
     * activity, to control the behaviour of multiple concurrent flows
     * <!-- end-model-doc -->
     * @return the value of the '<em>Event Handler Flow Strategy</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.EventHandlerFlowStrategy
     * @see #setEventHandlerFlowStrategy(EventHandlerFlowStrategy)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_EventHandlerFlowStrategy()
     * @model extendedMetaData="kind='attribute' name='EventHandlerFlowStrategy' namespace='##targetNamespace'"
     * @generated
     */
    EventHandlerFlowStrategy getEventHandlerFlowStrategy();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getEventHandlerFlowStrategy <em>Event Handler Flow Strategy</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Event Handler Flow Strategy</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.EventHandlerFlowStrategy
     * @see #getEventHandlerFlowStrategy()
     * @generated
     */
    void setEventHandlerFlowStrategy(EventHandlerFlowStrategy value);

    /**
     * Returns the value of the '<em><b>Namespace Prefix Map</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.NamespacePrefixMap}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * This element is added under xpdl:Activity for web service
     * activities that use XPath mappings so that we can relate namespace to prefix
     * in existing mappings when these change in the WSDL.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Namespace Prefix Map</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_NamespacePrefixMap()
     * @model containment="true"
     *        extendedMetaData="name='NamespacePrefixMap' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<NamespacePrefixMap> getNamespacePrefixMap();

    /**
     * Returns the value of the '<em><b>Suspend Resume With Parent</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Attribute added to xpdl:SubFlow for sub-process tasks to configure the 
     * whether the sub-process instance should suspend/resume with the parent 
     * process instance or not.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Suspend Resume With Parent</em>' attribute.
     * @see #setSuspendResumeWithParent(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_SuspendResumeWithParent()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
     *        extendedMetaData="kind='attribute' name='SuspendResumeWithParent' namespace='##targetNamespace'"
     * @generated
     */
    boolean isSuspendResumeWithParent();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isSuspendResumeWithParent <em>Suspend Resume With Parent</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Suspend Resume With Parent</em>' attribute.
     * @see #isSuspendResumeWithParent()
     * @generated
     */
    void setSuspendResumeWithParent(boolean value);

    /**
     * Returns the value of the '<em><b>System Error Action</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.SystemErrorActionType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>System Error Action</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Element to configure the Action to be taken when a system error happens. Used in XPDL:process to specify the System Action HALT/ERROR when a system error happens.
     * <!-- end-model-doc -->
     * @return the value of the '<em>System Error Action</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.SystemErrorActionType
     * @see #setSystemErrorAction(SystemErrorActionType)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_SystemErrorAction()
     * @model extendedMetaData="kind='attribute' name='SystemErrorAction' namespace='##targetNamespace'"
     * @generated
     */
    SystemErrorActionType getSystemErrorAction();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getSystemErrorAction <em>System Error Action</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>System Error Action</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.SystemErrorActionType
     * @see #getSystemErrorAction()
     * @generated
     */
    void setSystemErrorAction(SystemErrorActionType value);

    /**
     * Returns the value of the '<em><b>Correlation Timeout</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.ConstantPeriod}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * For receive task with incoming flow and intermediate catch message event specifying the uncorrelated message timeout information. Added to xpdl:Activity
     * <!-- end-model-doc -->
     * @return the value of the '<em>Correlation Timeout</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_CorrelationTimeout()
     * @model containment="true"
     *        extendedMetaData="name='CorrelationTimeout' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<ConstantPeriod> getCorrelationTimeout();

    /**
     * Returns the value of the '<em><b>Validation Control</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.ValidationControl}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Element used in XPDL:Process to override the default validate behaviour of the specific validation to supress it until next flow container change OR until it is reset to validate.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Validation Control</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_ValidationControl()
     * @model containment="true"
     *        extendedMetaData="name='ValidationControl' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<ValidationControl> getValidationControl();

    /**
     * Returns the value of the '<em><b>Reply Immediate Data Mappings</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.ReplyImmediateDataMappings}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Reply Immediate Data Mappings</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ReplyImmediatelyDataMapping element helps configure the mapping of output process id when a start event is configured to 'reply immediately with process id'. This element is added to the xpdl element "Message".
     * <!-- end-model-doc -->
     * @return the value of the '<em>Reply Immediate Data Mappings</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_ReplyImmediateDataMappings()
     * @model containment="true"
     *        extendedMetaData="name='ReplyImmediateDataMappings' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<ReplyImmediateDataMappings> getReplyImmediateDataMappings();

    /**
     * Returns the value of the '<em><b>Bx Use Unqualified Property Names</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Optionally added to xpdl2:Package element.
     * Indicates to Xpdl2Bpel converter that when generating PropertyName's for the
     * PropertyAlias's (added to BPEL copy of WSDL) for correlation mappings it 
     * should not qualify the PropertyName (dervied from correlation data name)
     * with the process name. DEFAULT is FALSE when not present (i.e. new 
     * packages will qualify ProeprtyName with processname). 
     * Only Migrated XPDL from pre FormatVersion=13 should have this set.
     * 
     * <!-- end-model-doc -->
     * @return the value of the '<em>Bx Use Unqualified Property Names</em>' attribute.
     * @see #setBxUseUnqualifiedPropertyNames(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_BxUseUnqualifiedPropertyNames()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
     *        extendedMetaData="kind='attribute' name='BxUseUnqualifiedPropertyNames' namespace='##targetNamespace'"
     * @generated
     */
    boolean isBxUseUnqualifiedPropertyNames();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isBxUseUnqualifiedPropertyNames <em>Bx Use Unqualified Property Names</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Bx Use Unqualified Property Names</em>' attribute.
     * @see #isBxUseUnqualifiedPropertyNames()
     * @generated
     */
    void setBxUseUnqualifiedPropertyNames(boolean value);

    /**
     * Returns the value of the '<em><b>Case Ref Type</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.RecordType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Case Ref Type</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * CaseRefType is used to set the type for a process relevant data to case classes (via external reference in a RecordType type) 
     * <!-- end-model-doc -->
     * @return the value of the '<em>Case Ref Type</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_CaseRefType()
     * @model containment="true"
     *        extendedMetaData="name='CaseRefType' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<RecordType> getCaseRefType();

    /**
     * Returns the value of the '<em><b>REST Services</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.RESTServices}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The REST services for a Business Process (stored as a pageflow process)
     * <!-- end-model-doc -->
     * @return the value of the '<em>REST Services</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_RESTServices()
     * @model containment="true"
     *        extendedMetaData="name='RESTServices' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<RESTServices> getRESTServices();

    /**
     * Returns the value of the '<em><b>Publish As Rest Service</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Optionally added to xpdl2:Activity if it is provided as a REST Service
     * <!-- end-model-doc -->
     * @return the value of the '<em>Publish As Rest Service</em>' attribute.
     * @see #setPublishAsRestService(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_PublishAsRestService()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='publishAsRestService' namespace='##targetNamespace'"
     * @generated
     */
    boolean isPublishAsRestService();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isPublishAsRestService <em>Publish As Rest Service</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Publish As Rest Service</em>' attribute.
     * @see #isPublishAsRestService()
     * @generated
     */
    void setPublishAsRestService(boolean value);

    /**
     * Returns the value of the '<em><b>Rest Activity Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Added to xpdExt:RESTService to store ID of the activity generating this REST service.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Rest Activity Id</em>' attribute.
     * @see #setRestActivityId(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_RestActivityId()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='restActivityId' namespace='##targetNamespace'"
     * @generated
     */
    String getRestActivityId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getRestActivityId <em>Rest Activity Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Rest Activity Id</em>' attribute.
     * @see #getRestActivityId()
     * @generated
     */
    void setRestActivityId(String value);

    /**
     * Returns the value of the '<em><b>Reschedule Timer Script</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.RescheduleTimerScript}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * This element is optionally added under xpdl2:TriggerTimer and specifies 
     * how to recalculate expiry time of timer.
     * DurationRelativeTo specifies how to treat durations if they are returned by the
     * script (i.e. as relative to time of reschedule, or to eixsiting timeout for timer event)
     * <!-- end-model-doc -->
     * @return the value of the '<em>Reschedule Timer Script</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_RescheduleTimerScript()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='RescheduleTimerScript' namespace='##targetNamespace'"
     * @generated
     */
    EList<RescheduleTimerScript> getRescheduleTimerScript();

    /**
     * Returns the value of the '<em><b>Dynamic Organization Mappings</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.DynamicOrganizationMappings}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Dynamic Organization Mappings</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * DynamicOrganizationMappings specify the mappings from the 'Dynamic Organization Identifier' to the dynamic participants. They are defined in the  'Work Resource' property tab of business process. 
     * <!-- end-model-doc -->
     * @return the value of the '<em>Dynamic Organization Mappings</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_DynamicOrganizationMappings()
     * @model containment="true"
     *        extendedMetaData="name='DynamicOrganizationMappings' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<DynamicOrganizationMappings> getDynamicOrganizationMappings();

    /**
     * Returns the value of the '<em><b>Signal Handler Asynchronous</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Optionally added to xpdl2:TriggerResultSignal element (when the signal is an 
     * event handler) of a catch signal event handler activity to determine the 
     * behaviour of the invoking throw signals (i.e., Synchronous or Asynchronous). 
     * <!-- end-model-doc -->
     * @return the value of the '<em>Signal Handler Asynchronous</em>' attribute.
     * @see #setSignalHandlerAsynchronous(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_SignalHandlerAsynchronous()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='signalHandlerAsynchronous' namespace='##targetNamespace'"
     * @generated
     */
    boolean isSignalHandlerAsynchronous();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isSignalHandlerAsynchronous <em>Signal Handler Asynchronous</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Signal Handler Asynchronous</em>' attribute.
     * @see #isSignalHandlerAsynchronous()
     * @generated
     */
    void setSignalHandlerAsynchronous(boolean value);

    /**
     * Returns the value of the '<em><b>Global Data Operation</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.GlobalDataOperation}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Global Data Operation</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Element for the Global Data operations configuration to be used in the Service Task of type Global Data Operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Global Data Operation</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_GlobalDataOperation()
     * @model containment="true"
     *        extendedMetaData="name='GlobalDataOperation' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<GlobalDataOperation> getGlobalDataOperation();

    /**
     * Returns the value of the '<em><b>Process Data Work Item Attribute Mappings</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.ProcessDataWorkItemAttributeMappings}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Process Data Work Item Attribute Mappings</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Container for Process Data and Work Item Attribute mapping, which is used at Process level to define which Work Item Attribute a Process Data is mapped to.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Process Data Work Item Attribute Mappings</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_ProcessDataWorkItemAttributeMappings()
     * @model containment="true"
     *        extendedMetaData="name='ProcessDataWorkItemAttributeMappings' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<ProcessDataWorkItemAttributeMappings> getProcessDataWorkItemAttributeMappings();

    /**
     * Returns the value of the '<em><b>Allow Unqualified Sub Process Identification</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * For dynamic sub-process tasks, this attribute can be added to the xpdl2:Subflow
     * element.
     * It configures the runtime behaviour of how Unqualified sub-process names are
     * treated when they are used in the runtime identifier field. 
     * If true, the process engine will look for unqualified sub-process names in 
     * the same package as invoking process, then in all packages in same project,
     * then all packages deployed ot the BPM node to find the given process.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Allow Unqualified Sub Process Identification</em>' attribute.
     * @see #setAllowUnqualifiedSubProcessIdentification(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_AllowUnqualifiedSubProcessIdentification()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
     *        extendedMetaData="kind='attribute' name='AllowUnqualifiedSubProcessIdentification' namespace='##targetNamespace'"
     * @generated
     */
    boolean isAllowUnqualifiedSubProcessIdentification();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isAllowUnqualifiedSubProcessIdentification <em>Allow Unqualified Sub Process Identification</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Allow Unqualified Sub Process Identification</em>' attribute.
     * @see #isAllowUnqualifiedSubProcessIdentification()
     * @generated
     */
    void setAllowUnqualifiedSubProcessIdentification(boolean value);

    /**
     * Returns the value of the '<em><b>Bpm Runtime Configuration</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.BpmRuntimeConfiguration}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * BPM Runtime Configuration specifying the incoming request time out in seconds (zero is default which means no timeout).
     * This value will be used in the AMX threading policy.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Bpm Runtime Configuration</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_BpmRuntimeConfiguration()
     * @model containment="true"
     *        extendedMetaData="name='BpmRuntimeConfiguration' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<BpmRuntimeConfiguration> getBpmRuntimeConfiguration();

    /**
     * Returns the value of the '<em><b>Is Case Service</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Added to xpdl2:Process to determine if it is a Case Service
     * <!-- end-model-doc -->
     * @return the value of the '<em>Is Case Service</em>' attribute.
     * @see #isSetIsCaseService()
     * @see #unsetIsCaseService()
     * @see #setIsCaseService(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_IsCaseService()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
     *        extendedMetaData="kind='attribute' name='IsCaseService' namespace='##targetNamespace'"
     * @generated
     */
    boolean isIsCaseService();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isIsCaseService <em>Is Case Service</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Is Case Service</em>' attribute.
     * @see #isSetIsCaseService()
     * @see #unsetIsCaseService()
     * @see #isIsCaseService()
     * @generated
     */
    void setIsCaseService(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isIsCaseService <em>Is Case Service</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetIsCaseService()
     * @see #isIsCaseService()
     * @see #setIsCaseService(boolean)
     * @generated
     */
    void unsetIsCaseService();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isIsCaseService <em>Is Case Service</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Is Case Service</em>' attribute is set.
     * @see #unsetIsCaseService()
     * @see #isIsCaseService()
     * @see #setIsCaseService(boolean)
     * @generated
     */
    boolean isSetIsCaseService();

    /**
     * Returns the value of the '<em><b>Required Access Privileges</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.RequiredAccessPrivileges}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Configuration of one or more organisation model privileges used on a resource at run-time
     * <!-- end-model-doc -->
     * @return the value of the '<em>Required Access Privileges</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_RequiredAccessPrivileges()
     * @model containment="true"
     *        extendedMetaData="name='RequiredAccessPrivileges' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<RequiredAccessPrivileges> getRequiredAccessPrivileges();

    /**
     * Returns the value of the '<em><b>Case Service</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.CaseService}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The element used to contain the details like Case Class Type and states for a Case Action.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Case Service</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_CaseService()
     * @model containment="true"
     *        extendedMetaData="name='CaseService' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<CaseService> getCaseService();

    /**
     * Returns the value of the '<em><b>Ad Hoc Task Configuration</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Element for Ad-Hoc task configuration settings, used for an Activity set to Adhoc.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Ad Hoc Task Configuration</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_AdHocTaskConfiguration()
     * @model containment="true"
     *        extendedMetaData="name='AdHocTaskConfiguration' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<AdHocTaskConfigurationType> getAdHocTaskConfiguration();

    /**
     * Returns the value of the '<em><b>Is Event Sub Process</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Is Event Sub Process</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Attribute used to mark an Embedded Subprocess as an Event Sub process when set to true.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Is Event Sub Process</em>' attribute.
     * @see #isSetIsEventSubProcess()
     * @see #unsetIsEventSubProcess()
     * @see #setIsEventSubProcess(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_IsEventSubProcess()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
     *        extendedMetaData="kind='attribute' name='IsEventSubProcess' namespace='##targetNamespace'"
     * @generated
     */
    boolean isIsEventSubProcess();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isIsEventSubProcess <em>Is Event Sub Process</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Is Event Sub Process</em>' attribute.
     * @see #isSetIsEventSubProcess()
     * @see #unsetIsEventSubProcess()
     * @see #isIsEventSubProcess()
     * @generated
     */
    void setIsEventSubProcess(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isIsEventSubProcess <em>Is Event Sub Process</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetIsEventSubProcess()
     * @see #isIsEventSubProcess()
     * @see #setIsEventSubProcess(boolean)
     * @generated
     */
    void unsetIsEventSubProcess();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isIsEventSubProcess <em>Is Event Sub Process</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Is Event Sub Process</em>' attribute is set.
     * @see #unsetIsEventSubProcess()
     * @see #isIsEventSubProcess()
     * @see #setIsEventSubProcess(boolean)
     * @generated
     */
    boolean isSetIsEventSubProcess();

    /**
     * Returns the value of the '<em><b>Non Interrupting Event</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Non Interrupting Event</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Attribute used to mark an event as Non interrupting.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Non Interrupting Event</em>' attribute.
     * @see #isSetNonInterruptingEvent()
     * @see #unsetNonInterruptingEvent()
     * @see #setNonInterruptingEvent(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_NonInterruptingEvent()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
     *        extendedMetaData="kind='attribute' name='NonInterruptingEvent' namespace='##targetNamespace'"
     * @generated
     */
    boolean isNonInterruptingEvent();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isNonInterruptingEvent <em>Non Interrupting Event</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Non Interrupting Event</em>' attribute.
     * @see #isSetNonInterruptingEvent()
     * @see #unsetNonInterruptingEvent()
     * @see #isNonInterruptingEvent()
     * @generated
     */
    void setNonInterruptingEvent(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isNonInterruptingEvent <em>Non Interrupting Event</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetNonInterruptingEvent()
     * @see #isNonInterruptingEvent()
     * @see #setNonInterruptingEvent(boolean)
     * @generated
     */
    void unsetNonInterruptingEvent();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isNonInterruptingEvent <em>Non Interrupting Event</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Non Interrupting Event</em>' attribute is set.
     * @see #unsetNonInterruptingEvent()
     * @see #isNonInterruptingEvent()
     * @see #setNonInterruptingEvent(boolean)
     * @generated
     */
    boolean isSetNonInterruptingEvent();

    /**
     * Returns the value of the '<em><b>Correlate Immediately</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     *  When enabled, message failing to correlate immediately with any process instance will be treated as an error. Added as an other attribute  to the xpdl elements 'TriggerResultMessage' and 'TaskReceive'
     * <!-- end-model-doc -->
     * @return the value of the '<em>Correlate Immediately</em>' attribute.
     * @see #setCorrelateImmediately(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_CorrelateImmediately()
     * @model default="false" unique="false" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
     *        extendedMetaData="kind='attribute' name='CorrelateImmediately' namespace='##targetNamespace'"
     * @generated
     */
    boolean isCorrelateImmediately();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isCorrelateImmediately <em>Correlate Immediately</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Correlate Immediately</em>' attribute.
     * @see #isCorrelateImmediately()
     * @generated
     */
    void setCorrelateImmediately(boolean value);

    /**
     * Returns the value of the '<em><b>Async Execution Mode</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.AsyncExecutionMode}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Attribute to indicate Asynchronous Execution Mode.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Async Execution Mode</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.AsyncExecutionMode
     * @see #isSetAsyncExecutionMode()
     * @see #unsetAsyncExecutionMode()
     * @see #setAsyncExecutionMode(AsyncExecutionMode)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_AsyncExecutionMode()
     * @model unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='AsyncExecutionMode' namespace='##targetNamespace'"
     * @generated
     */
    AsyncExecutionMode getAsyncExecutionMode();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getAsyncExecutionMode <em>Async Execution Mode</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Async Execution Mode</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.AsyncExecutionMode
     * @see #isSetAsyncExecutionMode()
     * @see #unsetAsyncExecutionMode()
     * @see #getAsyncExecutionMode()
     * @generated
     */
    void setAsyncExecutionMode(AsyncExecutionMode value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getAsyncExecutionMode <em>Async Execution Mode</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetAsyncExecutionMode()
     * @see #getAsyncExecutionMode()
     * @see #setAsyncExecutionMode(AsyncExecutionMode)
     * @generated
     */
    void unsetAsyncExecutionMode();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getAsyncExecutionMode <em>Async Execution Mode</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Async Execution Mode</em>' attribute is set.
     * @see #unsetAsyncExecutionMode()
     * @see #getAsyncExecutionMode()
     * @see #setAsyncExecutionMode(AsyncExecutionMode)
     * @generated
     */
    boolean isSetAsyncExecutionMode();

    /**
     * Returns the value of the '<em><b>Signal Type</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.SignalType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * For local signal xpdExt:SignalType will be unset or SignalType.LOCAL.  For Global Signal xpdExt:SignalType will be SignalType.Global
     * <!-- end-model-doc -->
     * @return the value of the '<em>Signal Type</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.SignalType
     * @see #isSetSignalType()
     * @see #unsetSignalType()
     * @see #setSignalType(SignalType)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_SignalType()
     * @model unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='SignalType' namespace='##targetNamespace'"
     * @generated
     */
    SignalType getSignalType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getSignalType <em>Signal Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Signal Type</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.SignalType
     * @see #isSetSignalType()
     * @see #unsetSignalType()
     * @see #getSignalType()
     * @generated
     */
    void setSignalType(SignalType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getSignalType <em>Signal Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetSignalType()
     * @see #getSignalType()
     * @see #setSignalType(SignalType)
     * @generated
     */
    void unsetSignalType();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getSignalType <em>Signal Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Signal Type</em>' attribute is set.
     * @see #unsetSignalType()
     * @see #getSignalType()
     * @see #setSignalType(SignalType)
     * @generated
     */
    boolean isSetSignalType();

    /**
     * Returns the value of the '<em><b>Service Process Configuration</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.ServiceProcessConfiguration}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * This element is used to specify the deployment targets on a ServiceProcess
     * <!-- end-model-doc -->
     * @return the value of the '<em>Service Process Configuration</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_ServiceProcessConfiguration()
     * @model containment="true"
     *        extendedMetaData="name='ServiceProcessConfiguration' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<ServiceProcessConfiguration> getServiceProcessConfiguration();

    /**
     * Returns the value of the '<em><b>Like Mapping</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Optionally added to xpdl2:DataMapping element to specify whether it's a 'like maping' or not.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Like Mapping</em>' attribute.
     * @see #setLikeMapping(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_LikeMapping()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='likeMapping' namespace='##targetNamespace'"
     * @generated
     */
    boolean isLikeMapping();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isLikeMapping <em>Like Mapping</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Like Mapping</em>' attribute.
     * @see #isLikeMapping()
     * @generated
     */
    void setLikeMapping(boolean value);

    /**
     * Returns the value of the '<em><b>Script Data Mapper</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.ScriptDataMapper}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ScriptDataMapper contains Process Data Mappings created using Data Mapper
     * <!-- end-model-doc -->
     * @return the value of the '<em>Script Data Mapper</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_ScriptDataMapper()
     * @model containment="true"
     *        extendedMetaData="name='ScriptDataMapper' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<ScriptDataMapper> getScriptDataMapper();

    /**
     * Returns the value of the '<em><b>Like Mapping Exclusions</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.LikeMappingExclusions}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * LikeMappingExclusions element stores likeMappingExclusion elements to maintain a list of items that should be ignored during like mapping. 
     * <!-- end-model-doc -->
     * @return the value of the '<em>Like Mapping Exclusions</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_LikeMappingExclusions()
     * @model containment="true"
     *        extendedMetaData="name='LikeMappingExclusions' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<LikeMappingExclusions> getLikeMappingExclusions();

    /**
     * Returns the value of the '<em><b>Source Contributor Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ID of the Process Data Mapper content contributor resposible for providing source item for the data mapping.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Source Contributor Id</em>' attribute.
     * @see #setSourceContributorId(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_SourceContributorId()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='SourceContributorId' namespace='##targetNamespace'"
     * @generated
     */
    String getSourceContributorId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getSourceContributorId <em>Source Contributor Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Source Contributor Id</em>' attribute.
     * @see #getSourceContributorId()
     * @generated
     */
    void setSourceContributorId(String value);

    /**
     * Returns the value of the '<em><b>Target Contributor Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ID of the Process Data Mapper content contributor resposible for providing target item for the data mapping. 
     * <!-- end-model-doc -->
     * @return the value of the '<em>Target Contributor Id</em>' attribute.
     * @see #setTargetContributorId(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_TargetContributorId()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='TargetContributorId' namespace='##targetNamespace'"
     * @generated
     */
    String getTargetContributorId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getTargetContributorId <em>Target Contributor Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Target Contributor Id</em>' attribute.
     * @see #getTargetContributorId()
     * @generated
     */
    void setTargetContributorId(String value);

    /**
     * Returns the value of the '<em><b>Rest Service Operation</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.RestServiceOperation}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * REST Service Operation parameters.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Rest Service Operation</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_RestServiceOperation()
     * @model containment="true"
     *        extendedMetaData="name='RestServiceOperation' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<RestServiceOperation> getRestServiceOperation();

    /**
     * Returns the value of the '<em><b>Input Mappings</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.ScriptDataMapper}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Specifically used to enclose input mappings created via Data Mapper for Call Sub-Process acitivities.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Input Mappings</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_InputMappings()
     * @model containment="true"
     *        extendedMetaData="name='InputMappings' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<ScriptDataMapper> getInputMappings();

    /**
     * Returns the value of the '<em><b>Output Mappings</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.ScriptDataMapper}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Specifically used to enclose output mappings created via Data Mapper for Call Sub-Process acitivities.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Output Mappings</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_OutputMappings()
     * @model containment="true"
     *        extendedMetaData="name='OutputMappings' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<ScriptDataMapper> getOutputMappings();

    /**
     * Returns the value of the '<em><b>Business Service Publish Type</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.BusinessServicePublishType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Attribute used on xpdl2:WorkflowProcess to designate the Business service 
     * publish type (whether targetted for Desktop or Mobile). Default=Desktop
     * <!-- end-model-doc -->
     * @return the value of the '<em>Business Service Publish Type</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.BusinessServicePublishType
     * @see #isSetBusinessServicePublishType()
     * @see #unsetBusinessServicePublishType()
     * @see #setBusinessServicePublishType(BusinessServicePublishType)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_BusinessServicePublishType()
     * @model unsettable="true"
     *        extendedMetaData="kind='attribute' name='BusinessServicePublishType' namespace='##targetNamespace'"
     * @generated
     */
    BusinessServicePublishType getBusinessServicePublishType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getBusinessServicePublishType <em>Business Service Publish Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Business Service Publish Type</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.BusinessServicePublishType
     * @see #isSetBusinessServicePublishType()
     * @see #unsetBusinessServicePublishType()
     * @see #getBusinessServicePublishType()
     * @generated
     */
    void setBusinessServicePublishType(BusinessServicePublishType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getBusinessServicePublishType <em>Business Service Publish Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetBusinessServicePublishType()
     * @see #getBusinessServicePublishType()
     * @see #setBusinessServicePublishType(BusinessServicePublishType)
     * @generated
     */
    void unsetBusinessServicePublishType();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getBusinessServicePublishType <em>Business Service Publish Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Business Service Publish Type</em>' attribute is set.
     * @see #unsetBusinessServicePublishType()
     * @see #getBusinessServicePublishType()
     * @see #setBusinessServicePublishType(BusinessServicePublishType)
     * @generated
     */
    boolean isSetBusinessServicePublishType();

    /**
     * Returns the value of the '<em><b>Suppress Max Mappings Error</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * For suppressing the maximum mappings from error to warning or vice versa.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Suppress Max Mappings Error</em>' attribute.
     * @see #setSuppressMaxMappingsError(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_SuppressMaxMappingsError()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
     *        extendedMetaData="kind='attribute' name='SuppressMaxMappingsError' namespace='##targetNamespace'"
     * @generated
     */
    boolean isSuppressMaxMappingsError();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isSuppressMaxMappingsError <em>Suppress Max Mappings Error</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Suppress Max Mappings Error</em>' attribute.
     * @see #isSuppressMaxMappingsError()
     * @generated
     */
    void setSuppressMaxMappingsError(boolean value);

    /**
     * Returns the value of the '<em><b>Field Format</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.FieldFormat}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Used on BasicType for expressing a specific format for a given base type (such as URI)
     * <!-- end-model-doc -->
     * @return the value of the '<em>Field Format</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.FieldFormat
     * @see #setFieldFormat(FieldFormat)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentRoot_FieldFormat()
     * @model unique="false" required="true"
     *        extendedMetaData="kind='attribute' name='FieldFormat' namespace='##targetNamespace'"
     * @generated
     */
    FieldFormat getFieldFormat();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getFieldFormat <em>Field Format</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Field Format</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.FieldFormat
     * @see #getFieldFormat()
     * @generated
     */
    void setFieldFormat(FieldFormat value);

} // DocumentRoot