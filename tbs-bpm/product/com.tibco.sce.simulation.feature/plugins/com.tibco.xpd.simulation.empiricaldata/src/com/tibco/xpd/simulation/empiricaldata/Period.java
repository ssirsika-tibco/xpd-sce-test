/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.empiricaldata;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Period</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.empiricaldata.Period#getParameter <em>Parameter</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.empiricaldata.Period#getPeriod <em>Period</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.empiricaldata.Period#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.empiricaldata.Period#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.empiricaldata.Period#getWeightingFactor <em>Weighting Factor</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.empiricaldata.EmpiricalDataPackage#getPeriod()
 * @model extendedMetaData="name='Period' kind='elementOnly'"
 * @generated
 */
public interface Period extends EObject {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Parameter</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.xpd.simulation.empiricaldata.Parameter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter</em>' containment reference list.
	 * @see com.tibco.xpd.simulation.empiricaldata.EmpiricalDataPackage#getPeriod_Parameter()
	 * @model type="com.tibco.xpd.simulation.empiricaldata.Parameter" containment="true"
	 *        extendedMetaData="kind='element' name='Parameter' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getParameter();

	/**
	 * Returns the value of the '<em><b>Period</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.xpd.simulation.empiricaldata.Period}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Period</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Period</em>' containment reference list.
	 * @see com.tibco.xpd.simulation.empiricaldata.EmpiricalDataPackage#getPeriod_Period()
	 * @model type="com.tibco.xpd.simulation.empiricaldata.Period" containment="true"
	 *        extendedMetaData="kind='element' name='Period' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getPeriod();

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
	 * @see com.tibco.xpd.simulation.empiricaldata.EmpiricalDataPackage#getPeriod_Name()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN" required="true"
	 *        extendedMetaData="kind='attribute' name='Name' namespace='##targetNamespace'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.simulation.empiricaldata.Period#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The default value is <code>"MonthOfYear"</code>.
	 * The literals are from the enumeration {@link com.tibco.xpd.simulation.empiricaldata.PeriodType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see com.tibco.xpd.simulation.empiricaldata.PeriodType
	 * @see #isSetType()
	 * @see #unsetType()
	 * @see #setType(PeriodType)
	 * @see com.tibco.xpd.simulation.empiricaldata.EmpiricalDataPackage#getPeriod_Type()
	 * @model default="MonthOfYear" unique="false" unsettable="true" required="true"
	 *        extendedMetaData="kind='attribute' name='Type' namespace='##targetNamespace'"
	 * @generated
	 */
	PeriodType getType();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.simulation.empiricaldata.Period#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see com.tibco.xpd.simulation.empiricaldata.PeriodType
	 * @see #isSetType()
	 * @see #unsetType()
	 * @see #getType()
	 * @generated
	 */
	void setType(PeriodType value);

	/**
	 * Unsets the value of the '{@link com.tibco.xpd.simulation.empiricaldata.Period#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetType()
	 * @see #getType()
	 * @see #setType(PeriodType)
	 * @generated
	 */
	void unsetType();

	/**
	 * Returns whether the value of the '{@link com.tibco.xpd.simulation.empiricaldata.Period#getType <em>Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Type</em>' attribute is set.
	 * @see #unsetType()
	 * @see #getType()
	 * @see #setType(PeriodType)
	 * @generated
	 */
	boolean isSetType();

	/**
	 * Returns the value of the '<em><b>Weighting Factor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Weighting Factor</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Weighting Factor</em>' attribute.
	 * @see #isSetWeightingFactor()
	 * @see #unsetWeightingFactor()
	 * @see #setWeightingFactor(double)
	 * @see com.tibco.xpd.simulation.empiricaldata.EmpiricalDataPackage#getPeriod_WeightingFactor()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
	 *        extendedMetaData="kind='attribute' name='WeightingFactor' namespace='##targetNamespace'"
	 * @generated
	 */
	double getWeightingFactor();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.simulation.empiricaldata.Period#getWeightingFactor <em>Weighting Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Weighting Factor</em>' attribute.
	 * @see #isSetWeightingFactor()
	 * @see #unsetWeightingFactor()
	 * @see #getWeightingFactor()
	 * @generated
	 */
	void setWeightingFactor(double value);

	/**
	 * Unsets the value of the '{@link com.tibco.xpd.simulation.empiricaldata.Period#getWeightingFactor <em>Weighting Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetWeightingFactor()
	 * @see #getWeightingFactor()
	 * @see #setWeightingFactor(double)
	 * @generated
	 */
	void unsetWeightingFactor();

	/**
	 * Returns whether the value of the '{@link com.tibco.xpd.simulation.empiricaldata.Period#getWeightingFactor <em>Weighting Factor</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Weighting Factor</em>' attribute is set.
	 * @see #unsetWeightingFactor()
	 * @see #getWeightingFactor()
	 * @see #setWeightingFactor(double)
	 * @generated
	 */
	boolean isSetWeightingFactor();

} // Period
