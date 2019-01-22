/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Process</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Process#getProcessHeader <em>Process Header</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Process#getRedefinableHeader <em>Redefinable Header</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Process#getPartnerLinks <em>Partner Links</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Process#getObject <em>Object</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Process#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Process#getAccessLevel <em>Access Level</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Process#getDefaultStartActivitySetId <em>Default Start Activity Set Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Process#isEnableInstanceCompensation <em>Enable Instance Compensation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Process#getProcessType <em>Process Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Process#getStatus <em>Status</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Process#isSuppressJoinFailure <em>Suppress Join Failure</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Process#getPackage <em>Package</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Process#getActivitySets <em>Activity Sets</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getProcess()
 * @model extendedMetaData="name='ProcessType' kind='elementOnly' features-order='processHeader redefinableHeader formalParameters participants applications dataFields activitySets activities transitions extendedAttributes assignments partnerLinks object extensions otherElements'"
 * @generated
 */
public interface Process extends NamedElement, FlowContainer,
        ExtendedAttributesContainer, FormalParametersContainer,
        AssigmentsContainer, DataFieldsContainer, ParticipantsContainer,
        ApplicationsContainer, OtherElementsContainer {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Process Header</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Process Header</em>' containment reference
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Process Header</em>' containment reference.
     * @see #setProcessHeader(ProcessHeader)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getProcess_ProcessHeader()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='ProcessHeader' namespace='##targetNamespace'"
     * @generated
     */
    ProcessHeader getProcessHeader();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Process#getProcessHeader <em>Process Header</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Process Header</em>' containment reference.
     * @see #getProcessHeader()
     * @generated
     */
    void setProcessHeader(ProcessHeader value);

    /**
     * Returns the value of the '<em><b>Redefinable Header</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Redefinable Header</em>' containment
     * reference isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Redefinable Header</em>' containment reference.
     * @see #setRedefinableHeader(RedefinableHeader)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getProcess_RedefinableHeader()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='RedefinableHeader' namespace='##targetNamespace'"
     * @generated
     */
    RedefinableHeader getRedefinableHeader();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Process#getRedefinableHeader <em>Redefinable Header</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Redefinable Header</em>' containment reference.
     * @see #getRedefinableHeader()
     * @generated
     */
    void setRedefinableHeader(RedefinableHeader value);

    /**
     * Returns the value of the '<em><b>Partner Links</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.PartnerLink}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Partner Links</em>' containment reference
     * list isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Partner Links</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getProcess_PartnerLinks()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='PartnerLink' namespace='##targetNamespace' wrap='PartnerLinks'"
     * @generated
     */
    EList<PartnerLink> getPartnerLinks();

    /**
     * Returns the value of the '<em><b>Object</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Object</em>' containment reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Object</em>' containment reference.
     * @see #setObject(com.tibco.xpd.xpdl2.Object)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getProcess_Object()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Object' namespace='##targetNamespace'"
     * @generated
     */
    com.tibco.xpd.xpdl2.Object getObject();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Process#getObject <em>Object</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Object</em>' containment reference.
     * @see #getObject()
     * @generated
     */
    void setObject(com.tibco.xpd.xpdl2.Object value);

    /**
     * Returns the value of the '<em><b>Extensions</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Extensions</em>' containment reference
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Extensions</em>' containment reference.
     * @see #setExtensions(EObject)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getProcess_Extensions()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Extensions' namespace='##targetNamespace'"
     * @generated
     */
    EObject getExtensions();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Process#getExtensions <em>Extensions</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Extensions</em>' containment reference.
     * @see #getExtensions()
     * @generated
     */
    void setExtensions(EObject value);

    /**
     * Returns the value of the '<em><b>Access Level</b></em>' attribute.
     * The default value is <code>"PUBLIC"</code>. The literals are from the
     * enumeration {@link com.tibco.xpd.xpdl2.AccessLevelType}. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Access Level</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Access Level</em>' attribute.
     * @see com.tibco.xpd.xpdl2.AccessLevelType
     * @see #isSetAccessLevel()
     * @see #unsetAccessLevel()
     * @see #setAccessLevel(AccessLevelType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getProcess_AccessLevel()
     * @model default="PUBLIC" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='AccessLevel'"
     * @generated
     */
    AccessLevelType getAccessLevel();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Process#getAccessLevel <em>Access Level</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Access Level</em>' attribute.
     * @see com.tibco.xpd.xpdl2.AccessLevelType
     * @see #isSetAccessLevel()
     * @see #unsetAccessLevel()
     * @see #getAccessLevel()
     * @generated
     */
    void setAccessLevel(AccessLevelType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Process#getAccessLevel <em>Access Level</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #isSetAccessLevel()
     * @see #getAccessLevel()
     * @see #setAccessLevel(AccessLevelType)
     * @generated
     */
    void unsetAccessLevel();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Process#getAccessLevel <em>Access Level</em>}' attribute is set.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return whether the value of the '<em>Access Level</em>' attribute is set.
     * @see #unsetAccessLevel()
     * @see #getAccessLevel()
     * @see #setAccessLevel(AccessLevelType)
     * @generated
     */
    boolean isSetAccessLevel();

    /**
     * Returns the value of the '<em><b>Default Start Activity Set Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Default Start Activity Set Id</em>'
     * attribute isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Default Start Activity Set Id</em>' attribute.
     * @see #setDefaultStartActivitySetId(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getProcess_DefaultStartActivitySetId()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='DefaultStartActivitySetId'"
     * @generated
     */
    String getDefaultStartActivitySetId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Process#getDefaultStartActivitySetId <em>Default Start Activity Set Id</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Default Start Activity Set Id</em>' attribute.
     * @see #getDefaultStartActivitySetId()
     * @generated
     */
    void setDefaultStartActivitySetId(String value);

    /**
     * Returns the value of the '<em><b>Enable Instance Compensation</b></em>'
     * attribute. The default value is <code>"false"</code>. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Enable Instance Compensation</em>'
     * attribute isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Enable Instance Compensation</em>'
     *         attribute.
     * @see #isSetEnableInstanceCompensation()
     * @see #unsetEnableInstanceCompensation()
     * @see #setEnableInstanceCompensation(boolean)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getProcess_EnableInstanceCompensation()
     * @model default="false" unique="false" unsettable="true"
     *        dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute'
     *        name='EnableInstanceCompensation'"
     * @generated
     */
    boolean isEnableInstanceCompensation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Process#isEnableInstanceCompensation <em>Enable Instance Compensation</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Enable Instance Compensation</em>' attribute.
     * @see #isSetEnableInstanceCompensation()
     * @see #unsetEnableInstanceCompensation()
     * @see #isEnableInstanceCompensation()
     * @generated
     */
    void setEnableInstanceCompensation(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Process#isEnableInstanceCompensation <em>Enable Instance Compensation</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #isSetEnableInstanceCompensation()
     * @see #isEnableInstanceCompensation()
     * @see #setEnableInstanceCompensation(boolean)
     * @generated
     */
    void unsetEnableInstanceCompensation();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Process#isEnableInstanceCompensation <em>Enable Instance Compensation</em>}' attribute is set.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return whether the value of the '<em>Enable Instance Compensation</em>' attribute is set.
     * @see #unsetEnableInstanceCompensation()
     * @see #isEnableInstanceCompensation()
     * @see #setEnableInstanceCompensation(boolean)
     * @generated
     */
    boolean isSetEnableInstanceCompensation();

    /**
     * Returns the value of the '<em><b>Process Type</b></em>' attribute.
     * The default value is <code>"None"</code>. The literals are from the
     * enumeration {@link com.tibco.xpd.xpdl2.ProcessType}. <!-- begin-user-doc
     * --> <!-- end-user-doc --> <!-- begin-model-doc --> BPMN: <!--
     * end-model-doc -->
     * 
     * @return the value of the '<em>Process Type</em>' attribute.
     * @see com.tibco.xpd.xpdl2.ProcessType
     * @see #isSetProcessType()
     * @see #unsetProcessType()
     * @see #setProcessType(ProcessType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getProcess_ProcessType()
     * @model default="None" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='ProcessType'"
     * @generated
     */
    ProcessType getProcessType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Process#getProcessType <em>Process Type</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Process Type</em>' attribute.
     * @see com.tibco.xpd.xpdl2.ProcessType
     * @see #isSetProcessType()
     * @see #unsetProcessType()
     * @see #getProcessType()
     * @generated
     */
    void setProcessType(ProcessType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Process#getProcessType <em>Process Type</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #isSetProcessType()
     * @see #getProcessType()
     * @see #setProcessType(ProcessType)
     * @generated
     */
    void unsetProcessType();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Process#getProcessType <em>Process Type</em>}' attribute is set.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return whether the value of the '<em>Process Type</em>' attribute is set.
     * @see #unsetProcessType()
     * @see #getProcessType()
     * @see #setProcessType(ProcessType)
     * @generated
     */
    boolean isSetProcessType();

    /**
     * Returns the value of the '<em><b>Status</b></em>' attribute. The
     * default value is <code>"None"</code>. The literals are from the
     * enumeration {@link com.tibco.xpd.xpdl2.StatusType}. <!-- begin-user-doc
     * --> <!-- end-user-doc --> <!-- begin-model-doc --> BPMN: Status values
     * are assigned during execution. Status can be treated as a property and
     * used in expressions local to a Process. It is unclear that status belongs
     * in the XPDL document. <!-- end-model-doc -->
     * 
     * @return the value of the '<em>Status</em>' attribute.
     * @see com.tibco.xpd.xpdl2.StatusType
     * @see #isSetStatus()
     * @see #unsetStatus()
     * @see #setStatus(StatusType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getProcess_Status()
     * @model default="None" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='Status'"
     * @generated
     */
    StatusType getStatus();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Process#getStatus <em>Status</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Status</em>' attribute.
     * @see com.tibco.xpd.xpdl2.StatusType
     * @see #isSetStatus()
     * @see #unsetStatus()
     * @see #getStatus()
     * @generated
     */
    void setStatus(StatusType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Process#getStatus <em>Status</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #isSetStatus()
     * @see #getStatus()
     * @see #setStatus(StatusType)
     * @generated
     */
    void unsetStatus();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Process#getStatus <em>Status</em>}' attribute is set.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return whether the value of the '<em>Status</em>' attribute is set.
     * @see #unsetStatus()
     * @see #getStatus()
     * @see #setStatus(StatusType)
     * @generated
     */
    boolean isSetStatus();

    /**
     * Returns the value of the '<em><b>Suppress Join Failure</b></em>'
     * attribute. The default value is <code>"false"</code>. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Suppress Join Failure</em>' attribute
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Suppress Join Failure</em>' attribute.
     * @see #isSetSuppressJoinFailure()
     * @see #unsetSuppressJoinFailure()
     * @see #setSuppressJoinFailure(boolean)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getProcess_SuppressJoinFailure()
     * @model default="false" unique="false" unsettable="true"
     *        dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='SuppressJoinFailure'"
     * @generated
     */
    boolean isSuppressJoinFailure();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Process#isSuppressJoinFailure <em>Suppress Join Failure</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Suppress Join Failure</em>' attribute.
     * @see #isSetSuppressJoinFailure()
     * @see #unsetSuppressJoinFailure()
     * @see #isSuppressJoinFailure()
     * @generated
     */
    void setSuppressJoinFailure(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Process#isSuppressJoinFailure <em>Suppress Join Failure</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #isSetSuppressJoinFailure()
     * @see #isSuppressJoinFailure()
     * @see #setSuppressJoinFailure(boolean)
     * @generated
     */
    void unsetSuppressJoinFailure();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Process#isSuppressJoinFailure <em>Suppress Join Failure</em>}' attribute is set.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return whether the value of the '<em>Suppress Join Failure</em>' attribute is set.
     * @see #unsetSuppressJoinFailure()
     * @see #isSuppressJoinFailure()
     * @see #setSuppressJoinFailure(boolean)
     * @generated
     */
    boolean isSetSuppressJoinFailure();

    /**
     * Returns the value of the '<em><b>Package</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.xpdl2.Package#getProcesses <em>Processes</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the value of the '<em>Package</em>' container reference.
     * @see #setPackage(com.tibco.xpd.xpdl2.Package)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getProcess_Package()
     * @see com.tibco.xpd.xpdl2.Package#getProcesses
     * @model opposite="processes" transient="false"
     * @generated
     */
    com.tibco.xpd.xpdl2.Package getPackage();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Process#getPackage <em>Package</em>}' container reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Package</em>' container reference.
     * @see #getPackage()
     * @generated
     */
    void setPackage(com.tibco.xpd.xpdl2.Package value);

    /**
     * Returns the value of the '<em><b>Activity Sets</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.ActivitySet}.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.xpdl2.ActivitySet#getProcess <em>Process</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Activity Sets</em>' containment reference
     * list isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Activity Sets</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getProcess_ActivitySets()
     * @see com.tibco.xpd.xpdl2.ActivitySet#getProcess
     * @model opposite="process" containment="true"
     *        extendedMetaData="kind='element' name='ActivitySet' namespace='##targetNamespace' wrap='ActivitySets'"
     * @generated
     */
    EList<ActivitySet> getActivitySets();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @model idDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    ActivitySet getActivitySet(String id);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @model idDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    ProcessRelevantData getProcessData(String id);

    /**
     * Provided to ease understanding that the parameters are of the Process
     * alone
     * 
     * @return
     */

    EList getLocalFormalParameters();

} // Process
