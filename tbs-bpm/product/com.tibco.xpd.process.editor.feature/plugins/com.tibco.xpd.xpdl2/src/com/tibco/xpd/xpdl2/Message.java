/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Message</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Formal Parameters defined by WSDL. Must constraint the parameters to either all in or all out, because Message is in a single direction
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Message#getActualParameters <em>Actual Parameters</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Message#getDataMappings <em>Data Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Message#getFaultName <em>Fault Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Message#getFrom <em>From</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Message#getTo <em>To</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getMessage()
 * @model extendedMetaData="name='MessageType' kind='elementOnly' features-order='actualParameters dataMappings otherElements'"
 * @generated
 */
public interface Message extends NamedElement, OtherElementsContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Actual Parameters</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Expression}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Actual Parameters</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Actual Parameters</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getMessage_ActualParameters()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ActualParameter' namespace='##targetNamespace' wrap='ActualParameters'"
     * @generated
     */
    EList<Expression> getActualParameters();

    /**
     * Returns the value of the '<em><b>Data Mappings</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.DataMapping}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Data Mappings</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Data Mappings</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getMessage_DataMappings()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='DataMapping' namespace='##targetNamespace' wrap='DataMappings'"
     * @generated
     */
    EList<DataMapping> getDataMappings();

    /**
     * Returns the value of the '<em><b>Fault Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Fault Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Fault Name</em>' attribute.
     * @see #setFaultName(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getMessage_FaultName()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN"
     *        extendedMetaData="kind='attribute' name='FaultName'"
     * @generated
     */
    String getFaultName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Message#getFaultName <em>Fault Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Fault Name</em>' attribute.
     * @see #getFaultName()
     * @generated
     */
    void setFaultName(String value);

    /**
     * Returns the value of the '<em><b>From</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * This must be the name of a Participant
     * <!-- end-model-doc -->
     * @return the value of the '<em>From</em>' attribute.
     * @see #setFrom(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getMessage_From()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='From'"
     * @generated
     */
    String getFrom();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Message#getFrom <em>From</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>From</em>' attribute.
     * @see #getFrom()
     * @generated
     */
    void setFrom(String value);

    /**
     * Returns the value of the '<em><b>To</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * This must be the name of a participant
     * <!-- end-model-doc -->
     * @return the value of the '<em>To</em>' attribute.
     * @see #setTo(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getMessage_To()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='To'"
     * @generated
     */
    String getTo();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Message#getTo <em>To</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>To</em>' attribute.
     * @see #getTo()
     * @generated
     */
    void setTo(String value);

} // Message
