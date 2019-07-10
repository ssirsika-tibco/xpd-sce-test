/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Process Interfaces</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Container for Process interfaces, used under a Process package for list of contained process interfaces.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.ProcessInterfaces#getProcessInterface <em>Process Interface</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getProcessInterfaces()
 * @model extendedMetaData="name='ProcessInterfaces' kind='elementOnly'"
 * @generated
 */
public interface ProcessInterfaces extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Process Interface</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.ProcessInterface}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Lists the process interface associated with the package.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Process Interface</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getProcessInterfaces_ProcessInterface()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ProcessInterface' namespace='##targetNamespace'"
     * @generated
     */
    EList<ProcessInterface> getProcessInterface();

} // ProcessInterfaces