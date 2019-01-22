/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Location</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *         A geographic location to which organizations, org units and org positions can be
 *         associated. A sub-class of typedEntity, it can take its type from the meta-model
 *         element locationType.
 *         It also carries locale and timezone identifiers (using the same coding as Java).
 *         These will provide future support for currency and date conversion.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.Location#getLocale <em>Locale</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Location#getTimezone <em>Timezone</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.directory.model.de.DePackage#getLocation()
 * @model extendedMetaData="name='Location' kind='elementOnly'"
 * @generated
 */
public interface Location extends TypedEntity {
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
     * @see com.tibco.n2.directory.model.de.DePackage#getLocation_Locale()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='locale'"
     * @generated
     */
    String getLocale();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Location#getLocale <em>Locale</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Locale</em>' attribute.
     * @see #getLocale()
     * @generated
     */
    void setLocale(String value);

    /**
     * Returns the value of the '<em><b>Timezone</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Timezone</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Timezone</em>' attribute.
     * @see #setTimezone(String)
     * @see com.tibco.n2.directory.model.de.DePackage#getLocation_Timezone()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='timezone'"
     * @generated
     */
    String getTimezone();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Location#getTimezone <em>Timezone</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Timezone</em>' attribute.
     * @see #getTimezone()
     * @generated
     */
    void setTimezone(String value);

} // Location
