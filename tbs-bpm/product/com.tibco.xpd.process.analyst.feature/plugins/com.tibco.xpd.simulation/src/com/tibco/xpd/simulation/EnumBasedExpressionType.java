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
 * A representation of the model object '<em><b>Enum Based Expression Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.EnumBasedExpressionType#getEnumValue <em>Enum Value</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.EnumBasedExpressionType#getParamName <em>Param Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.SimulationPackage#getEnumBasedExpressionType()
 * @model extendedMetaData="name='EnumBasedExpression_._type' kind='empty'"
 * @generated
 */
public interface EnumBasedExpressionType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Enum Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Enum Value</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Enum Value</em>' attribute.
     * @see #setEnumValue(Object)
     * @see com.tibco.xpd.simulation.SimulationPackage#getEnumBasedExpressionType_EnumValue()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
     *        extendedMetaData="kind='attribute' name='enumValue'"
     * @generated
     */
    Object getEnumValue();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.EnumBasedExpressionType#getEnumValue <em>Enum Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Enum Value</em>' attribute.
     * @see #getEnumValue()
     * @generated
     */
    void setEnumValue(Object value);

    /**
     * Returns the value of the '<em><b>Param Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Param Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Param Name</em>' attribute.
     * @see #setParamName(Object)
     * @see com.tibco.xpd.simulation.SimulationPackage#getEnumBasedExpressionType_ParamName()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType"
     *        extendedMetaData="kind='attribute' name='paramName'"
     * @generated
     */
    Object getParamName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.EnumBasedExpressionType#getParamName <em>Param Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Param Name</em>' attribute.
     * @see #getParamName()
     * @generated
     */
    void setParamName(Object value);

} // EnumBasedExpressionType