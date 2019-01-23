/**
 */
package com.tibco.xpd.rsd;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Payload Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.rsd.PayloadReference#getNamespace <em>Namespace</em>}</li>
 *   <li>{@link com.tibco.xpd.rsd.PayloadReference#getRef <em>Ref</em>}</li>
 *   <li>{@link com.tibco.xpd.rsd.PayloadReference#getLocation <em>Location</em>}</li>
 *   <li>{@link com.tibco.xpd.rsd.PayloadReference#getMediaType <em>Media Type</em>}</li>
 *   <li>{@link com.tibco.xpd.rsd.PayloadReference#isArray <em>Array</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.rsd.RsdPackage#getPayloadReference()
 * @model
 * @generated
 */
public interface PayloadReference extends EObject {
    /**
     * Returns the value of the '<em><b>Namespace</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Namespace</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Namespace</em>' attribute.
     * @see #setNamespace(String)
     * @see com.tibco.xpd.rsd.RsdPackage#getPayloadReference_Namespace()
     * @model
     * @generated
     */
    String getNamespace();

    /**
     * Sets the value of the '{@link com.tibco.xpd.rsd.PayloadReference#getNamespace <em>Namespace</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Namespace</em>' attribute.
     * @see #getNamespace()
     * @generated
     */
    void setNamespace(String value);

    /**
     * Returns the value of the '<em><b>Ref</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Ref</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Ref</em>' attribute.
     * @see #setRef(String)
     * @see com.tibco.xpd.rsd.RsdPackage#getPayloadReference_Ref()
     * @model
     * @generated
     */
    String getRef();

    /**
     * Sets the value of the '{@link com.tibco.xpd.rsd.PayloadReference#getRef <em>Ref</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Ref</em>' attribute.
     * @see #getRef()
     * @generated
     */
    void setRef(String value);

    /**
     * Returns the value of the '<em><b>Location</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Location</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Location</em>' attribute.
     * @see #setLocation(String)
     * @see com.tibco.xpd.rsd.RsdPackage#getPayloadReference_Location()
     * @model
     * @generated
     */
    String getLocation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.rsd.PayloadReference#getLocation <em>Location</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Location</em>' attribute.
     * @see #getLocation()
     * @generated
     */
    void setLocation(String value);

    /**
     * Returns the value of the '<em><b>Media Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Media Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Media Type</em>' attribute.
     * @see #setMediaType(String)
     * @see com.tibco.xpd.rsd.RsdPackage#getPayloadReference_MediaType()
     * @model
     * @generated
     */
    String getMediaType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.rsd.PayloadReference#getMediaType <em>Media Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Media Type</em>' attribute.
     * @see #getMediaType()
     * @generated
     */
    void setMediaType(String value);

    /**
     * Returns the value of the '<em><b>Array</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Array</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Array</em>' attribute.
     * @see #setArray(boolean)
     * @see com.tibco.xpd.rsd.RsdPackage#getPayloadReference_Array()
     * @model
     * @generated
     */
    boolean isArray();

    /**
     * Sets the value of the '{@link com.tibco.xpd.rsd.PayloadReference#isArray <em>Array</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Array</em>' attribute.
     * @see #isArray()
     * @generated
     */
    void setArray(boolean value);

} // PayloadReference
