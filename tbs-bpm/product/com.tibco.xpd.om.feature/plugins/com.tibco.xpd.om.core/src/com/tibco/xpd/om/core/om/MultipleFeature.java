/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Multiple Feature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.MultipleFeature#getLowerBound <em>Lower Bound</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.MultipleFeature#getUpperBound <em>Upper Bound</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getMultipleFeature()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface MultipleFeature extends Feature, OrgElement {
    /**
     * Returns the value of the '<em><b>Lower Bound</b></em>' attribute.
     * The default value is <code>"0"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Lower Bound</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Lower Bound</em>' attribute.
     * @see #setLowerBound(int)
     * @see com.tibco.xpd.om.core.om.OMPackage#getMultipleFeature_LowerBound()
     * @model default="0"
     * @generated
     */
    int getLowerBound();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.MultipleFeature#getLowerBound <em>Lower Bound</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Lower Bound</em>' attribute.
     * @see #getLowerBound()
     * @generated
     */
    void setLowerBound(int value);

    /**
     * Returns the value of the '<em><b>Upper Bound</b></em>' attribute.
     * The default value is <code>"1"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Upper Bound</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Upper Bound</em>' attribute.
     * @see #setUpperBound(int)
     * @see com.tibco.xpd.om.core.om.OMPackage#getMultipleFeature_UpperBound()
     * @model default="1"
     * @generated
     */
    int getUpperBound();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.MultipleFeature#getUpperBound <em>Upper Bound</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Upper Bound</em>' attribute.
     * @see #getUpperBound()
     * @generated
     */
    void setUpperBound(int value);

} // MultipleFeature
