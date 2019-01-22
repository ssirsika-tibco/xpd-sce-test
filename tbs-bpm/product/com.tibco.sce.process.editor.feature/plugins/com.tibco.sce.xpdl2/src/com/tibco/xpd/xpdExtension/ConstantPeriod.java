/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import java.math.BigInteger;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Constant Period</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Defines constant period time duration and is used for Timer Events by specifying the Script type as ‘Constant Period’ and values of the duration.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.ConstantPeriod#getDays <em>Days</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ConstantPeriod#getHours <em>Hours</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ConstantPeriod#getMicroSeconds <em>Micro Seconds</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ConstantPeriod#getMinutes <em>Minutes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ConstantPeriod#getMonths <em>Months</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ConstantPeriod#getSeconds <em>Seconds</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ConstantPeriod#getWeeks <em>Weeks</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ConstantPeriod#getYears <em>Years</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getConstantPeriod()
 * @model extendedMetaData="name='ConstantPeriod' kind='empty'"
 * @generated
 */
public interface ConstantPeriod extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Days</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Days</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Days</em>' attribute.
     * @see #setDays(BigInteger)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getConstantPeriod_Days()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Integer"
     *        extendedMetaData="kind='attribute' name='Days'"
     * @generated
     */
    BigInteger getDays();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ConstantPeriod#getDays <em>Days</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Days</em>' attribute.
     * @see #getDays()
     * @generated
     */
    void setDays(BigInteger value);

    /**
     * Returns the value of the '<em><b>Hours</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Hours</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Hours</em>' attribute.
     * @see #setHours(BigInteger)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getConstantPeriod_Hours()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Integer"
     *        extendedMetaData="kind='attribute' name='Hours'"
     * @generated
     */
    BigInteger getHours();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ConstantPeriod#getHours <em>Hours</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Hours</em>' attribute.
     * @see #getHours()
     * @generated
     */
    void setHours(BigInteger value);

    /**
     * Returns the value of the '<em><b>Micro Seconds</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Micro Seconds</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Micro Seconds</em>' attribute.
     * @see #setMicroSeconds(BigInteger)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getConstantPeriod_MicroSeconds()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Integer"
     *        extendedMetaData="kind='attribute' name='MicroSeconds'"
     * @generated
     */
    BigInteger getMicroSeconds();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ConstantPeriod#getMicroSeconds <em>Micro Seconds</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Micro Seconds</em>' attribute.
     * @see #getMicroSeconds()
     * @generated
     */
    void setMicroSeconds(BigInteger value);

    /**
     * Returns the value of the '<em><b>Minutes</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Minutes</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Minutes</em>' attribute.
     * @see #setMinutes(BigInteger)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getConstantPeriod_Minutes()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Integer"
     *        extendedMetaData="kind='attribute' name='Minutes'"
     * @generated
     */
    BigInteger getMinutes();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ConstantPeriod#getMinutes <em>Minutes</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Minutes</em>' attribute.
     * @see #getMinutes()
     * @generated
     */
    void setMinutes(BigInteger value);

    /**
     * Returns the value of the '<em><b>Months</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Months</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Months</em>' attribute.
     * @see #setMonths(BigInteger)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getConstantPeriod_Months()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Integer"
     *        extendedMetaData="kind='attribute' name='Months'"
     * @generated
     */
    BigInteger getMonths();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ConstantPeriod#getMonths <em>Months</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Months</em>' attribute.
     * @see #getMonths()
     * @generated
     */
    void setMonths(BigInteger value);

    /**
     * Returns the value of the '<em><b>Seconds</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Seconds</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Seconds</em>' attribute.
     * @see #setSeconds(BigInteger)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getConstantPeriod_Seconds()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Integer"
     *        extendedMetaData="kind='attribute' name='Seconds'"
     * @generated
     */
    BigInteger getSeconds();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ConstantPeriod#getSeconds <em>Seconds</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Seconds</em>' attribute.
     * @see #getSeconds()
     * @generated
     */
    void setSeconds(BigInteger value);

    /**
     * Returns the value of the '<em><b>Weeks</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Weeks</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Weeks</em>' attribute.
     * @see #setWeeks(BigInteger)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getConstantPeriod_Weeks()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Integer"
     *        extendedMetaData="kind='attribute' name='Weeks'"
     * @generated
     */
    BigInteger getWeeks();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ConstantPeriod#getWeeks <em>Weeks</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Weeks</em>' attribute.
     * @see #getWeeks()
     * @generated
     */
    void setWeeks(BigInteger value);

    /**
     * Returns the value of the '<em><b>Years</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Years</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Years</em>' attribute.
     * @see #setYears(BigInteger)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getConstantPeriod_Years()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Integer"
     *        extendedMetaData="kind='attribute' name='Years'"
     * @generated
     */
    BigInteger getYears();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ConstantPeriod#getYears <em>Years</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Years</em>' attribute.
     * @see #getYears()
     * @generated
     */
    void setYears(BigInteger value);

} // ConstantPeriod