/**
 */
package com.tibco.xpd.rsd;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameter Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.rsd.ParameterContainer#getParameters <em>Parameters</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.rsd.RsdPackage#getParameterContainer()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface ParameterContainer extends EObject {
    /**
     * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.rsd.Parameter}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Parameters</em>' containment reference list.
     * @see com.tibco.xpd.rsd.RsdPackage#getParameterContainer_Parameters()
     * @model containment="true"
     * @generated
     */
    EList<Parameter> getParameters();

} // ParameterContainer
