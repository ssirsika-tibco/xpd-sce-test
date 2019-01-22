/**
 */
package com.tibco.xpd.rsd;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Request</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.rsd.Request#getContentType <em>Content Type</em>}</li>
 *   <li>{@link com.tibco.xpd.rsd.Request#getAccept <em>Accept</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.rsd.RsdPackage#getRequest()
 * @model
 * @generated
 */
public interface Request extends ParameterContainer, PayloadRefContainer {

    /**
     * Returns the value of the '<em><b>Content Type</b></em>' attribute.
     * The default value is <code>"application/json"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Content Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Content Type</em>' attribute.
     * @see #setContentType(String)
     * @see com.tibco.xpd.rsd.RsdPackage#getRequest_ContentType()
     * @model default="application/json"
     * @generated
     */
    String getContentType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.rsd.Request#getContentType <em>Content Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Content Type</em>' attribute.
     * @see #getContentType()
     * @generated
     */
    void setContentType(String value);

    /**
     * Returns the value of the '<em><b>Accept</b></em>' attribute.
     * The default value is <code>"application/json"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Accept</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Accept</em>' attribute.
     * @see #setAccept(String)
     * @see com.tibco.xpd.rsd.RsdPackage#getRequest_Accept()
     * @model default="application/json"
     * @generated
     */
    String getAccept();

    /**
     * Sets the value of the '{@link com.tibco.xpd.rsd.Request#getAccept <em>Accept</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Accept</em>' attribute.
     * @see #getAccept()
     * @generated
     */
    void setAccept(String value);
} // Request
