/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.xpdl2.Expression;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Duration Calculation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Element specifying an expression-based duration.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.DurationCalculation#getYears <em>Years</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DurationCalculation#getMonths <em>Months</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DurationCalculation#getWeeks <em>Weeks</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DurationCalculation#getDays <em>Days</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DurationCalculation#getHours <em>Hours</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DurationCalculation#getMinutes <em>Minutes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DurationCalculation#getSeconds <em>Seconds</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DurationCalculation#getMicroseconds <em>Microseconds</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDurationCalculation()
 * @model extendedMetaData="name='DurationCalculation' kind='empty'"
 * @generated
 */
public interface DurationCalculation extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Years</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Years</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Years</em>' containment reference.
     * @see #setYears(Expression)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDurationCalculation_Years()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Years' namespace='##targetNamespace'"
     * @generated
     */
    Expression getYears();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DurationCalculation#getYears <em>Years</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Years</em>' containment reference.
     * @see #getYears()
     * @generated
     */
    void setYears(Expression value);

    /**
     * Returns the value of the '<em><b>Months</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Months</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Months</em>' containment reference.
     * @see #setMonths(Expression)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDurationCalculation_Months()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Months' namespace='##targetNamespace'"
     * @generated
     */
    Expression getMonths();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DurationCalculation#getMonths <em>Months</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Months</em>' containment reference.
     * @see #getMonths()
     * @generated
     */
    void setMonths(Expression value);

    /**
     * Returns the value of the '<em><b>Weeks</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Weeks</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Weeks</em>' containment reference.
     * @see #setWeeks(Expression)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDurationCalculation_Weeks()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Weeks' namespace='##targetNamespace'"
     * @generated
     */
    Expression getWeeks();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DurationCalculation#getWeeks <em>Weeks</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Weeks</em>' containment reference.
     * @see #getWeeks()
     * @generated
     */
    void setWeeks(Expression value);

    /**
     * Returns the value of the '<em><b>Days</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Days</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Days</em>' containment reference.
     * @see #setDays(Expression)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDurationCalculation_Days()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Days' namespace='##targetNamespace'"
     * @generated
     */
    Expression getDays();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DurationCalculation#getDays <em>Days</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Days</em>' containment reference.
     * @see #getDays()
     * @generated
     */
    void setDays(Expression value);

    /**
     * Returns the value of the '<em><b>Hours</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Hours</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Hours</em>' containment reference.
     * @see #setHours(Expression)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDurationCalculation_Hours()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Hours' namespace='##targetNamespace'"
     * @generated
     */
    Expression getHours();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DurationCalculation#getHours <em>Hours</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Hours</em>' containment reference.
     * @see #getHours()
     * @generated
     */
    void setHours(Expression value);

    /**
     * Returns the value of the '<em><b>Minutes</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Minutes</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Minutes</em>' containment reference.
     * @see #setMinutes(Expression)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDurationCalculation_Minutes()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Minutes' namespace='##targetNamespace'"
     * @generated
     */
    Expression getMinutes();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DurationCalculation#getMinutes <em>Minutes</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Minutes</em>' containment reference.
     * @see #getMinutes()
     * @generated
     */
    void setMinutes(Expression value);

    /**
     * Returns the value of the '<em><b>Seconds</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Seconds</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Seconds</em>' containment reference.
     * @see #setSeconds(Expression)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDurationCalculation_Seconds()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Seconds' namespace='##targetNamespace'"
     * @generated
     */
    Expression getSeconds();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DurationCalculation#getSeconds <em>Seconds</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Seconds</em>' containment reference.
     * @see #getSeconds()
     * @generated
     */
    void setSeconds(Expression value);

    /**
     * Returns the value of the '<em><b>Microseconds</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Microseconds</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Microseconds</em>' containment reference.
     * @see #setMicroseconds(Expression)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDurationCalculation_Microseconds()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Microseconds' namespace='##targetNamespace'"
     * @generated
     */
    Expression getMicroseconds();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DurationCalculation#getMicroseconds <em>Microseconds</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Microseconds</em>' containment reference.
     * @see #getMicroseconds()
     * @generated
     */
    void setMicroseconds(Expression value);

} // DurationCalculation
