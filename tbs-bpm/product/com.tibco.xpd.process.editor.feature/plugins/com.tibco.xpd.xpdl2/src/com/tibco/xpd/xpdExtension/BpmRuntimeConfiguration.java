/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bpm Runtime Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * BPM Runtime Configuration specifying the incoming request time out in seconds (zero is default which means no timeout).
 * This value will be used in the AMX threading policy.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.BpmRuntimeConfiguration#getIncomingRequestTimeout <em>Incoming Request Timeout</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getBpmRuntimeConfiguration()
 * @model extendedMetaData="name='BpmRuntimeConfiguration' kind='elementOnly'"
 * @generated
 */
public interface BpmRuntimeConfiguration extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Incoming Request Timeout</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Incoming Request Timeout</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Incoming Request Timeout</em>' attribute.
     * @see #setIncomingRequestTimeout(Integer)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getBpmRuntimeConfiguration_IncomingRequestTimeout()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.IntObject"
     *        extendedMetaData="kind='attribute' name='IncomingRequestTimeout'"
     * @generated
     */
    Integer getIncomingRequestTimeout();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.BpmRuntimeConfiguration#getIncomingRequestTimeout <em>Incoming Request Timeout</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Incoming Request Timeout</em>' attribute.
     * @see #getIncomingRequestTimeout()
     * @generated
     */
    void setIncomingRequestTimeout(Integer value);

} // BpmRuntimeConfiguration
