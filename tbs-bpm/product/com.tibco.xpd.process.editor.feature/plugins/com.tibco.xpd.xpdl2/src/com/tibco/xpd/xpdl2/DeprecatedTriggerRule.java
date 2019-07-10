/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Deprecated Trigger Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.DeprecatedTriggerRule#getRuleName <em>Rule Name</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getDeprecatedTriggerRule()
 * @model extendedMetaData="name='TriggerRule_._type' kind='elementOnly'"
 * @generated
 */
public interface DeprecatedTriggerRule extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Rule Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * This is the nameof a Rule element.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Rule Name</em>' attribute.
     * @see #setRuleName(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getDeprecatedTriggerRule_RuleName()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='RuleName'"
     * @generated
     */
    String getRuleName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.DeprecatedTriggerRule#getRuleName <em>Rule Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Rule Name</em>' attribute.
     * @see #getRuleName()
     * @generated
     */
    void setRuleName(String value);

} // DeprecatedTriggerRule
