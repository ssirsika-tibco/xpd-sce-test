/**
 */
package com.tibco.xpd.rsd;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Fault</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.rsd.Fault#getStatusCode <em>Status Code</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.rsd.RsdPackage#getFault()
 * @model
 * @generated
 */
public interface Fault extends NamedElement, AbstractResponse {
    /**
     * Returns the value of the '<em><b>Status Code</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Status Code</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Status Code</em>' attribute.
     * @see #setStatusCode(String)
     * @see com.tibco.xpd.rsd.RsdPackage#getFault_StatusCode()
     * @model
     * @generated
     */
    String getStatusCode();

    /**
     * Sets the value of the '{@link com.tibco.xpd.rsd.Fault#getStatusCode <em>Status Code</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Status Code</em>' attribute.
     * @see #getStatusCode()
     * @generated
     */
    void setStatusCode(String value);

} // Fault
