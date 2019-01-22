/**
 */
package com.tibco.xpd.globalSignalDefinition;

import java.math.BigInteger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Global Signal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.globalSignalDefinition.GlobalSignal#getPayloadDataFields <em>Payload Data Fields</em>}</li>
 *   <li>{@link com.tibco.xpd.globalSignalDefinition.GlobalSignal#getCorrelationTimeout <em>Correlation Timeout</em>}</li>
 *   <li>{@link com.tibco.xpd.globalSignalDefinition.GlobalSignal#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.globalSignalDefinition.GlobalSignal#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage#getGlobalSignal()
 * @model extendedMetaData="name='GlobalSignal' kind='elementOnly'"
 * @generated
 */
public interface GlobalSignal extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.";

    /**
     * Returns the value of the '<em><b>Payload Data Fields</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.globalSignalDefinition.PayloadDataField}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Payload Data Fields</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Payload Data Fields</em>' containment reference list.
     * @see com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage#getGlobalSignal_PayloadDataFields()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='PayloadDataField' namespace='##targetNamespace' wrap='PayloadDataFields'"
     * @generated
     */
    EList<PayloadDataField> getPayloadDataFields();

    /**
     * Returns the value of the '<em><b>Correlation Timeout</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * This attribute would be added when user selects 'Timeout after' and the absence of this attribute would mean 'Correlate Immediately'
     * <!-- end-model-doc -->
     * @return the value of the '<em>Correlation Timeout</em>' attribute.
     * @see #isSetCorrelationTimeout()
     * @see #unsetCorrelationTimeout()
     * @see #setCorrelationTimeout(BigInteger)
     * @see com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage#getGlobalSignal_CorrelationTimeout()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Integer"
     *        extendedMetaData="kind='attribute' name='CorrelationTimeout'"
     * @generated
     */
    BigInteger getCorrelationTimeout();

    /**
     * Sets the value of the '{@link com.tibco.xpd.globalSignalDefinition.GlobalSignal#getCorrelationTimeout <em>Correlation Timeout</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Correlation Timeout</em>' attribute.
     * @see #isSetCorrelationTimeout()
     * @see #unsetCorrelationTimeout()
     * @see #getCorrelationTimeout()
     * @generated
     */
    void setCorrelationTimeout(BigInteger value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.globalSignalDefinition.GlobalSignal#getCorrelationTimeout <em>Correlation Timeout</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetCorrelationTimeout()
     * @see #getCorrelationTimeout()
     * @see #setCorrelationTimeout(BigInteger)
     * @generated
     */
    void unsetCorrelationTimeout();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.globalSignalDefinition.GlobalSignal#getCorrelationTimeout <em>Correlation Timeout</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Correlation Timeout</em>' attribute is set.
     * @see #unsetCorrelationTimeout()
     * @see #getCorrelationTimeout()
     * @see #setCorrelationTimeout(BigInteger)
     * @generated
     */
    boolean isSetCorrelationTimeout();

    /**
     * Returns the value of the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Description</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Description</em>' attribute.
     * @see #setDescription(String)
     * @see com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage#getGlobalSignal_Description()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='Description' namespace='##targetNamespace'"
     * @generated
     */
    String getDescription();

    /**
     * Sets the value of the '{@link com.tibco.xpd.globalSignalDefinition.GlobalSignal#getDescription <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Description</em>' attribute.
     * @see #getDescription()
     * @generated
     */
    void setDescription(String value);

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage#getGlobalSignal_Name()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='Name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.globalSignalDefinition.GlobalSignal#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

} // GlobalSignal
