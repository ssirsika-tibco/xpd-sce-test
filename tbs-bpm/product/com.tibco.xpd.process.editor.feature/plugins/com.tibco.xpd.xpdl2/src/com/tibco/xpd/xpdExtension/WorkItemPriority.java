/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import java.math.BigInteger;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Work Item Priority</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This element is added under Activity Resource Patterns for assigning initial priority
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.WorkItemPriority#getInitialPriority <em>Initial Priority</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWorkItemPriority()
 * @model extendedMetaData="name='WorkItemPriority' kind='empty'"
 * @generated
 */
public interface WorkItemPriority extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Initial Priority</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Initial Priority</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Initial Priority</em>' attribute.
     * @see #setInitialPriority(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWorkItemPriority_InitialPriority()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='InitialPriority'"
     * @generated
     */
    String getInitialPriority();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WorkItemPriority#getInitialPriority <em>Initial Priority</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Initial Priority</em>' attribute.
     * @see #getInitialPriority()
     * @generated
     */
    void setInitialPriority(String value);

} // WorkItemPriority
