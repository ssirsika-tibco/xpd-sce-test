/**
 * Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rest Service Resource Security</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.RestServiceResourceSecurity#getPolicyType <em>Policy Type</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRestServiceResourceSecurity()
 * @model
 * @generated
 */
public interface RestServiceResourceSecurity
        extends ExtendedAttributesContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Policy Type</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.SecurityPolicy}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Policy Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Policy Type</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.SecurityPolicy
     * @see #isSetPolicyType()
     * @see #unsetPolicyType()
     * @see #setPolicyType(SecurityPolicy)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRestServiceResourceSecurity_PolicyType()
     * @model unsettable="true"
     *        extendedMetaData="kind='attribute' name='PolicyType'"
     * @generated
     */
    SecurityPolicy getPolicyType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.RestServiceResourceSecurity#getPolicyType <em>Policy Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Policy Type</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.SecurityPolicy
     * @see #isSetPolicyType()
     * @see #unsetPolicyType()
     * @see #getPolicyType()
     * @generated
     */
    void setPolicyType(SecurityPolicy value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.RestServiceResourceSecurity#getPolicyType <em>Policy Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetPolicyType()
     * @see #getPolicyType()
     * @see #setPolicyType(SecurityPolicy)
     * @generated
     */
    void unsetPolicyType();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.RestServiceResourceSecurity#getPolicyType <em>Policy Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Policy Type</em>' attribute is set.
     * @see #unsetPolicyType()
     * @see #getPolicyType()
     * @see #setPolicyType(SecurityPolicy)
     * @generated
     */
    boolean isSetPolicyType();

} // RestServiceResourceSecurity
