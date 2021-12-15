/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.resources.projectconfig;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Unique Id Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.UniqueIdContainer#getId <em>Id</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.resources.projectconfig.ProjectConfigPackage#getUniqueIdContainer()
 * @model abstract="true"
 * @generated
 */
public interface UniqueIdContainer extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Unique ID
     * <!-- end-model-doc -->
     * @return the value of the '<em>Id</em>' attribute.
     * @see com.tibco.xpd.resources.projectconfig.ProjectConfigPackage#getUniqueIdContainer_Id()
     * @model id="true" dataType="org.eclipse.emf.ecore.xml.type.ID" required="true" changeable="false"
     *        extendedMetaData="kind='attribute' name='id'"
     * @generated
     */
    String getId();

} // UniqueIdContainer