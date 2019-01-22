/**
 */
package com.tibco.xpd.rsd;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.rsd.Resource#getMethods <em>Methods</em>}</li>
 *   <li>{@link com.tibco.xpd.rsd.Resource#getPathTemplate <em>Path Template</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.rsd.RsdPackage#getResource()
 * @model
 * @generated
 */
public interface Resource extends NamedElement, ParameterContainer {
    /**
     * Returns the value of the '<em><b>Methods</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.rsd.Method}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Methods</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Methods</em>' containment reference list.
     * @see com.tibco.xpd.rsd.RsdPackage#getResource_Methods()
     * @model containment="true"
     * @generated
     */
    EList<Method> getMethods();

    /**
     * Returns the value of the '<em><b>Path Template</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Path Template</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Path Template</em>' attribute.
     * @see #setPathTemplate(String)
     * @see com.tibco.xpd.rsd.RsdPackage#getResource_PathTemplate()
     * @model
     * @generated
     */
    String getPathTemplate();

    /**
     * Sets the value of the '{@link com.tibco.xpd.rsd.Resource#getPathTemplate <em>Path Template</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Path Template</em>' attribute.
     * @see #getPathTemplate()
     * @generated
     */
    void setPathTemplate(String value);

} // Resource
