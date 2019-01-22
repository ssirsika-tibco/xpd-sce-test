/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.api.exception;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.common.api.exception.DocumentRoot#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.n2.common.api.exception.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.tibco.n2.common.api.exception.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.tibco.n2.common.api.exception.DocumentRoot#getDeploymentParameterFault <em>Deployment Parameter Fault</em>}</li>
 *   <li>{@link com.tibco.n2.common.api.exception.DocumentRoot#getInternalServiceFault <em>Internal Service Fault</em>}</li>
 *   <li>{@link com.tibco.n2.common.api.exception.DocumentRoot#getInvalidWorkTypeFault <em>Invalid Work Type Fault</em>}</li>
 *   <li>{@link com.tibco.n2.common.api.exception.DocumentRoot#getWorkTypeFault <em>Work Type Fault</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.common.api.exception.ExceptionPackage#getDocumentRoot()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface DocumentRoot extends EObject {
    /**
     * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Mixed</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Mixed</em>' attribute list.
     * @see com.tibco.n2.common.api.exception.ExceptionPackage#getDocumentRoot_Mixed()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='elementWildcard' name=':mixed'"
     * @generated
     */
    FeatureMap getMixed();

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
     * @return the value of the '<em>XMLNS Prefix Map</em>' map.
     * @see com.tibco.n2.common.api.exception.ExceptionPackage#getDocumentRoot_XMLNSPrefixMap()
     * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
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
     * @return the value of the '<em>XSI Schema Location</em>' map.
     * @see com.tibco.n2.common.api.exception.ExceptionPackage#getDocumentRoot_XSISchemaLocation()
     * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
     *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
     * @generated
     */
    EMap<String, String> getXSISchemaLocation();

    /**
     * Returns the value of the '<em><b>Deployment Parameter Fault</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Deployment Parameter Fault</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Deployment Parameter Fault</em>' containment reference.
     * @see #setDeploymentParameterFault(DeploymentParameterFaultType)
     * @see com.tibco.n2.common.api.exception.ExceptionPackage#getDocumentRoot_DeploymentParameterFault()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='DeploymentParameterFault' namespace='##targetNamespace'"
     * @generated
     */
    DeploymentParameterFaultType getDeploymentParameterFault();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.api.exception.DocumentRoot#getDeploymentParameterFault <em>Deployment Parameter Fault</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Deployment Parameter Fault</em>' containment reference.
     * @see #getDeploymentParameterFault()
     * @generated
     */
    void setDeploymentParameterFault(DeploymentParameterFaultType value);

    /**
     * Returns the value of the '<em><b>Internal Service Fault</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Internal Service Fault</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Internal Service Fault</em>' containment reference.
     * @see #setInternalServiceFault(InternalServiceFaultType)
     * @see com.tibco.n2.common.api.exception.ExceptionPackage#getDocumentRoot_InternalServiceFault()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='InternalServiceFault' namespace='##targetNamespace'"
     * @generated
     */
    InternalServiceFaultType getInternalServiceFault();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.api.exception.DocumentRoot#getInternalServiceFault <em>Internal Service Fault</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Internal Service Fault</em>' containment reference.
     * @see #getInternalServiceFault()
     * @generated
     */
    void setInternalServiceFault(InternalServiceFaultType value);

    /**
     * Returns the value of the '<em><b>Invalid Work Type Fault</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Invalid Work Type Fault</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Invalid Work Type Fault</em>' containment reference.
     * @see #setInvalidWorkTypeFault(InvalidWorkTypeFaultType)
     * @see com.tibco.n2.common.api.exception.ExceptionPackage#getDocumentRoot_InvalidWorkTypeFault()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='InvalidWorkTypeFault' namespace='##targetNamespace'"
     * @generated
     */
    InvalidWorkTypeFaultType getInvalidWorkTypeFault();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.api.exception.DocumentRoot#getInvalidWorkTypeFault <em>Invalid Work Type Fault</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Invalid Work Type Fault</em>' containment reference.
     * @see #getInvalidWorkTypeFault()
     * @generated
     */
    void setInvalidWorkTypeFault(InvalidWorkTypeFaultType value);

    /**
     * Returns the value of the '<em><b>Work Type Fault</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Work Type Fault</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Work Type Fault</em>' containment reference.
     * @see #setWorkTypeFault(WorkTypeFaultType)
     * @see com.tibco.n2.common.api.exception.ExceptionPackage#getDocumentRoot_WorkTypeFault()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='WorkTypeFault' namespace='##targetNamespace'"
     * @generated
     */
    WorkTypeFaultType getWorkTypeFault();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.api.exception.DocumentRoot#getWorkTypeFault <em>Work Type Fault</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Type Fault</em>' containment reference.
     * @see #getWorkTypeFault()
     * @generated
     */
    void setWorkTypeFault(WorkTypeFaultType value);

} // DocumentRoot
