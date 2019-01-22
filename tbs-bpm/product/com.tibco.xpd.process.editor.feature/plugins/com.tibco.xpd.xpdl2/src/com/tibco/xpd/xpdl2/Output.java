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
 * A representation of the model object '<em><b>Output</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Output#getArtifactId <em>Artifact Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getOutput()
 * @model extendedMetaData="name='Output_._type' kind='elementOnly'"
 * @generated
 */
public interface Output extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Artifact Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Artifact Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Artifact Id</em>' attribute.
     * @see #setArtifactId(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getOutput_ArtifactId()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString" required="true"
     *        extendedMetaData="kind='attribute' name='ArtifactId'"
     * @generated
     */
    String getArtifactId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Output#getArtifactId <em>Artifact Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Artifact Id</em>' attribute.
     * @see #getArtifactId()
     * @generated
     */
    void setArtifactId(String value);

} // Output
