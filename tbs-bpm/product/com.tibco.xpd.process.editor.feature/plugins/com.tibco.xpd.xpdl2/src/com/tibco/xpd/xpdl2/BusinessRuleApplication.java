/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Business Rule Application</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.BusinessRuleApplication#getRuleName <em>Rule Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.BusinessRuleApplication#getLocation <em>Location</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getBusinessRuleApplication()
 * @model extendedMetaData="name='BusinessRule_._type' kind='elementOnly'"
 * @generated
 */
public interface BusinessRuleApplication extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Rule Name</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Rule Name</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Rule Name</em>' containment reference.
     * @see #setRuleName(RuleName)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getBusinessRuleApplication_RuleName()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='RuleName' namespace='##targetNamespace'"
     * @generated
     */
    RuleName getRuleName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.BusinessRuleApplication#getRuleName <em>Rule Name</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Rule Name</em>' containment reference.
     * @see #getRuleName()
     * @generated
     */
    void setRuleName(RuleName value);

    /**
     * Returns the value of the '<em><b>Location</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Location</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Location</em>' containment reference.
     * @see #setLocation(Location)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getBusinessRuleApplication_Location()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='Location' namespace='##targetNamespace'"
     * @generated
     */
    Location getLocation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.BusinessRuleApplication#getLocation <em>Location</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Location</em>' containment reference.
     * @see #getLocation()
     * @generated
     */
    void setLocation(Location value);

} // BusinessRuleApplication
