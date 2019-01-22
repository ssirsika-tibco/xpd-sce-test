/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Work Model Scripts</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Definition of a set of work model scripts.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.WorkModelScripts#getWorkModelScript <em>Work Model Script</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModelScripts()
 * @model extendedMetaData="name='WorkModelScripts' kind='elementOnly'"
 * @generated
 */
public interface WorkModelScripts extends EObject {
    /**
     * Returns the value of the '<em><b>Work Model Script</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.WorkModelScript}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Work Model Script</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Work Model Script</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModelScripts_WorkModelScript()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='WorkModelScript'"
     * @generated
     */
    EList<WorkModelScript> getWorkModelScript();

} // WorkModelScripts
