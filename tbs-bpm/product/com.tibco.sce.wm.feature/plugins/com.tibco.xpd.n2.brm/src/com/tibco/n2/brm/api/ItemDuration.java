/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Item Duration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Amount of time that can be spent on a work item, or for which a work item can be hidden.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.ItemDuration#getDays <em>Days</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ItemDuration#getHours <em>Hours</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ItemDuration#getMinutes <em>Minutes</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ItemDuration#getMonths <em>Months</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ItemDuration#getWeeks <em>Weeks</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ItemDuration#getYears <em>Years</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getItemDuration()
 * @model extendedMetaData="name='ItemDuration' kind='empty'"
 * @generated
 */
public interface ItemDuration extends EObject {
    /**
     * Returns the value of the '<em><b>Days</b></em>' attribute.
     * The default value is <code>"0"</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Number of days in this duration period.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Days</em>' attribute.
     * @see #isSetDays()
     * @see #unsetDays()
     * @see #setDays(int)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getItemDuration_Days()
     * @model default="0" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='days'"
     * @generated
     */
    int getDays();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ItemDuration#getDays <em>Days</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Days</em>' attribute.
     * @see #isSetDays()
     * @see #unsetDays()
     * @see #getDays()
     * @generated
     */
    void setDays(int value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.ItemDuration#getDays <em>Days</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDays()
     * @see #getDays()
     * @see #setDays(int)
     * @generated
     */
    void unsetDays();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.ItemDuration#getDays <em>Days</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Days</em>' attribute is set.
     * @see #unsetDays()
     * @see #getDays()
     * @see #setDays(int)
     * @generated
     */
    boolean isSetDays();

    /**
     * Returns the value of the '<em><b>Hours</b></em>' attribute.
     * The default value is <code>"0"</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Number of hours in this duration period.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Hours</em>' attribute.
     * @see #isSetHours()
     * @see #unsetHours()
     * @see #setHours(int)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getItemDuration_Hours()
     * @model default="0" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='hours'"
     * @generated
     */
    int getHours();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ItemDuration#getHours <em>Hours</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Hours</em>' attribute.
     * @see #isSetHours()
     * @see #unsetHours()
     * @see #getHours()
     * @generated
     */
    void setHours(int value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.ItemDuration#getHours <em>Hours</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetHours()
     * @see #getHours()
     * @see #setHours(int)
     * @generated
     */
    void unsetHours();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.ItemDuration#getHours <em>Hours</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Hours</em>' attribute is set.
     * @see #unsetHours()
     * @see #getHours()
     * @see #setHours(int)
     * @generated
     */
    boolean isSetHours();

    /**
     * Returns the value of the '<em><b>Minutes</b></em>' attribute.
     * The default value is <code>"0"</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Number of minutes in this duration period.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Minutes</em>' attribute.
     * @see #isSetMinutes()
     * @see #unsetMinutes()
     * @see #setMinutes(int)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getItemDuration_Minutes()
     * @model default="0" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='minutes'"
     * @generated
     */
    int getMinutes();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ItemDuration#getMinutes <em>Minutes</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Minutes</em>' attribute.
     * @see #isSetMinutes()
     * @see #unsetMinutes()
     * @see #getMinutes()
     * @generated
     */
    void setMinutes(int value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.ItemDuration#getMinutes <em>Minutes</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetMinutes()
     * @see #getMinutes()
     * @see #setMinutes(int)
     * @generated
     */
    void unsetMinutes();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.ItemDuration#getMinutes <em>Minutes</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Minutes</em>' attribute is set.
     * @see #unsetMinutes()
     * @see #getMinutes()
     * @see #setMinutes(int)
     * @generated
     */
    boolean isSetMinutes();

    /**
     * Returns the value of the '<em><b>Months</b></em>' attribute.
     * The default value is <code>"0"</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Number of months in this duration period.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Months</em>' attribute.
     * @see #isSetMonths()
     * @see #unsetMonths()
     * @see #setMonths(int)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getItemDuration_Months()
     * @model default="0" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='months'"
     * @generated
     */
    int getMonths();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ItemDuration#getMonths <em>Months</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Months</em>' attribute.
     * @see #isSetMonths()
     * @see #unsetMonths()
     * @see #getMonths()
     * @generated
     */
    void setMonths(int value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.ItemDuration#getMonths <em>Months</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetMonths()
     * @see #getMonths()
     * @see #setMonths(int)
     * @generated
     */
    void unsetMonths();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.ItemDuration#getMonths <em>Months</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Months</em>' attribute is set.
     * @see #unsetMonths()
     * @see #getMonths()
     * @see #setMonths(int)
     * @generated
     */
    boolean isSetMonths();

    /**
     * Returns the value of the '<em><b>Weeks</b></em>' attribute.
     * The default value is <code>"0"</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Number of weeks in this duration period.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Weeks</em>' attribute.
     * @see #isSetWeeks()
     * @see #unsetWeeks()
     * @see #setWeeks(int)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getItemDuration_Weeks()
     * @model default="0" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='weeks'"
     * @generated
     */
    int getWeeks();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ItemDuration#getWeeks <em>Weeks</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Weeks</em>' attribute.
     * @see #isSetWeeks()
     * @see #unsetWeeks()
     * @see #getWeeks()
     * @generated
     */
    void setWeeks(int value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.ItemDuration#getWeeks <em>Weeks</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetWeeks()
     * @see #getWeeks()
     * @see #setWeeks(int)
     * @generated
     */
    void unsetWeeks();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.ItemDuration#getWeeks <em>Weeks</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Weeks</em>' attribute is set.
     * @see #unsetWeeks()
     * @see #getWeeks()
     * @see #setWeeks(int)
     * @generated
     */
    boolean isSetWeeks();

    /**
     * Returns the value of the '<em><b>Years</b></em>' attribute.
     * The default value is <code>"0"</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Number of years in this duration period.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Years</em>' attribute.
     * @see #isSetYears()
     * @see #unsetYears()
     * @see #setYears(int)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getItemDuration_Years()
     * @model default="0" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='years'"
     * @generated
     */
    int getYears();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ItemDuration#getYears <em>Years</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Years</em>' attribute.
     * @see #isSetYears()
     * @see #unsetYears()
     * @see #getYears()
     * @generated
     */
    void setYears(int value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.ItemDuration#getYears <em>Years</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetYears()
     * @see #getYears()
     * @see #setYears(int)
     * @generated
     */
    void unsetYears();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.ItemDuration#getYears <em>Years</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Years</em>' attribute is set.
     * @see #unsetYears()
     * @see #getYears()
     * @see #setYears(int)
     * @generated
     */
    boolean isSetYears();

} // ItemDuration
