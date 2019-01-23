/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Position Feature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.PositionFeature#getFeatureType <em>Feature Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getPositionFeature()
 * @model
 * @generated
 */
public interface PositionFeature extends MultipleFeature {
    /**
     * Returns the value of the '<em><b>Feature Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Feature Type</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Feature Type</em>' reference.
     * @see #setFeatureType(PositionType)
     * @see com.tibco.xpd.om.core.om.OMPackage#getPositionFeature_FeatureType()
     * @model required="true"
     * @generated
     */
    PositionType getFeatureType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.PositionFeature#getFeatureType <em>Feature Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Feature Type</em>' reference.
     * @see #getFeatureType()
     * @generated
     */
    void setFeatureType(PositionType value);

} // PositionFeature
