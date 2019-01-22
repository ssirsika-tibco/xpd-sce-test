/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Location</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.Location#getLocationType <em>Location Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.Location#getLocale <em>Locale</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.Location#getTimeZone <em>Time Zone</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getLocation()
 * @model
 * @generated
 */
public interface Location extends OrgTypedElement, Allocable {
    /**
     * Returns the value of the '<em><b>Location Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Location Type</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Location Type</em>' reference.
     * @see #setLocationType(LocationType)
     * @see com.tibco.xpd.om.core.om.OMPackage#getLocation_LocationType()
     * @model
     * @generated
     */
    LocationType getLocationType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.Location#getLocationType <em>Location Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Location Type</em>' reference.
     * @see #getLocationType()
     * @generated
     */
    void setLocationType(LocationType value);

    /**
     * Returns the value of the '<em><b>Locale</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Locale</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Locale</em>' attribute.
     * @see #setLocale(String)
     * @see com.tibco.xpd.om.core.om.OMPackage#getLocation_Locale()
     * @model
     * @generated
     */
    String getLocale();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.Location#getLocale <em>Locale</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Locale</em>' attribute.
     * @see #getLocale()
     * @generated
     */
    void setLocale(String value);

    /**
     * Returns the value of the '<em><b>Time Zone</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Time Zone</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Time Zone</em>' attribute.
     * @see #setTimeZone(String)
     * @see com.tibco.xpd.om.core.om.OMPackage#getLocation_TimeZone()
     * @model
     * @generated
     */
    String getTimeZone();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.Location#getTimeZone <em>Time Zone</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Time Zone</em>' attribute.
     * @see #getTimeZone()
     * @generated
     */
    void setTimeZone(String value);

} // Location
