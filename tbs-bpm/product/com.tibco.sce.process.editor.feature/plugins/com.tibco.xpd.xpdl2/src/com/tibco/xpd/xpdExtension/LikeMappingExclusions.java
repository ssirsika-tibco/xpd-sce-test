/**
 * Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Like Mapping Exclusions</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.LikeMappingExclusions#getExclusions <em>Exclusions</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getLikeMappingExclusions()
 * @model extendedMetaData="name='LikeMappingExclusions' kind='empty'"
 * @generated
 */
public interface LikeMappingExclusions extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Exclusions</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.LikeMappingExclusion}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Contains path of target array element to be exclude during like mapping.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Exclusions</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getLikeMappingExclusions_Exclusions()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='LikeMappingExclusion' namespace='##targetNamespace'"
     * @generated
     */
    EList<LikeMappingExclusion> getExclusions();

} // LikeMappingExclusions
