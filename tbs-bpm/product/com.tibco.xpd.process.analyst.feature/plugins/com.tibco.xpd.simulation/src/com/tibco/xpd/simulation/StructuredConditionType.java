/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Structured Condition Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.StructuredConditionType#getParameterId <em>Parameter Id</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.StructuredConditionType#getOperator <em>Operator</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.StructuredConditionType#getParameterValue <em>Parameter Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.SimulationPackage#getStructuredConditionType()
 * @model extendedMetaData="name='StructuredConditionType' kind='elementOnly'"
 * @generated
 */
public interface StructuredConditionType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Parameter Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parameter Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Parameter Id</em>' attribute.
     * @see #setParameterId(String)
     * @see com.tibco.xpd.simulation.SimulationPackage#getStructuredConditionType_ParameterId()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN" required="true"
     *        extendedMetaData="kind='element' name='ParameterId' namespace='##targetNamespace'"
     * @generated
     */
    String getParameterId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.StructuredConditionType#getParameterId <em>Parameter Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Parameter Id</em>' attribute.
     * @see #getParameterId()
     * @generated
     */
    void setParameterId(String value);

    /**
     * Returns the value of the '<em><b>Operator</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Operator</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Operator</em>' attribute.
     * @see #setOperator(String)
     * @see com.tibco.xpd.simulation.SimulationPackage#getStructuredConditionType_Operator()
     * @model unique="false" dataType="com.tibco.xpd.simulation.OperatorType" required="true"
     *        extendedMetaData="kind='element' name='Operator' namespace='##targetNamespace'"
     * @generated
     */
    String getOperator();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.StructuredConditionType#getOperator <em>Operator</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Operator</em>' attribute.
     * @see #getOperator()
     * @generated
     */
    void setOperator(String value);

    /**
     * Returns the value of the '<em><b>Parameter Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parameter Value</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Parameter Value</em>' attribute.
     * @see #setParameterValue(String)
     * @see com.tibco.xpd.simulation.SimulationPackage#getStructuredConditionType_ParameterValue()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='ParameterValue' namespace='##targetNamespace'"
     * @generated
     */
    String getParameterValue();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.StructuredConditionType#getParameterValue <em>Parameter Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Parameter Value</em>' attribute.
     * @see #getParameterValue()
     * @generated
     */
    void setParameterValue(String value);

} // StructuredConditionType
