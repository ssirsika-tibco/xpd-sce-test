/**
 */
package com.tibco.xpd.rsd;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Method</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.rsd.Method#getRequest <em>Request</em>}</li>
 *   <li>{@link com.tibco.xpd.rsd.Method#getHttpMethod <em>Http Method</em>}</li>
 *   <li>{@link com.tibco.xpd.rsd.Method#getFaults <em>Faults</em>}</li>
 *   <li>{@link com.tibco.xpd.rsd.Method#getResponse <em>Response</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.rsd.RsdPackage#getMethod()
 * @model
 * @generated
 */
public interface Method extends NamedElement {
    /**
     * Returns the value of the '<em><b>Request</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Request</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Request</em>' containment reference.
     * @see #setRequest(Request)
     * @see com.tibco.xpd.rsd.RsdPackage#getMethod_Request()
     * @model containment="true" required="true"
     * @generated
     */
    Request getRequest();

    /**
     * Sets the value of the '{@link com.tibco.xpd.rsd.Method#getRequest <em>Request</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Request</em>' containment reference.
     * @see #getRequest()
     * @generated
     */
    void setRequest(Request value);

    /**
     * Returns the value of the '<em><b>Http Method</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.rsd.HttpMethod}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Http Method</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Http Method</em>' attribute.
     * @see com.tibco.xpd.rsd.HttpMethod
     * @see #setHttpMethod(HttpMethod)
     * @see com.tibco.xpd.rsd.RsdPackage#getMethod_HttpMethod()
     * @model
     * @generated
     */
    HttpMethod getHttpMethod();

    /**
     * Sets the value of the '{@link com.tibco.xpd.rsd.Method#getHttpMethod <em>Http Method</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Http Method</em>' attribute.
     * @see com.tibco.xpd.rsd.HttpMethod
     * @see #getHttpMethod()
     * @generated
     */
    void setHttpMethod(HttpMethod value);

    /**
     * Returns the value of the '<em><b>Faults</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.rsd.Fault}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Faults</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Faults</em>' containment reference list.
     * @see com.tibco.xpd.rsd.RsdPackage#getMethod_Faults()
     * @model containment="true"
     * @generated
     */
    EList<Fault> getFaults();

    /**
     * Returns the value of the '<em><b>Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Response</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Response</em>' containment reference.
     * @see #setResponse(Response)
     * @see com.tibco.xpd.rsd.RsdPackage#getMethod_Response()
     * @model containment="true" required="true"
     * @generated
     */
    Response getResponse();

    /**
     * Sets the value of the '{@link com.tibco.xpd.rsd.Method#getResponse <em>Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Response</em>' containment reference.
     * @see #getResponse()
     * @generated
     */
    void setResponse(Response value);

} // Method
