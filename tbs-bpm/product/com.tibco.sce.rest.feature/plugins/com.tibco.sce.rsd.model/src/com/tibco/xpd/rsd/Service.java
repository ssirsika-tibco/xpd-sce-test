/**
 */
package com.tibco.xpd.rsd;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Service</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.rsd.Service#getResources <em>Resources</em>}</li>
 *   <li>{@link com.tibco.xpd.rsd.Service#getContextPath <em>Context Path</em>}</li>
 *   <li>{@link com.tibco.xpd.rsd.Service#getMediaType <em>Media Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.rsd.RsdPackage#getService()
 * @model
 * @generated
 */
public interface Service extends NamedElement {
    /**
     * Returns the value of the '<em><b>Resources</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.rsd.Resource}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resources</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Resources</em>' containment reference list.
     * @see com.tibco.xpd.rsd.RsdPackage#getService_Resources()
     * @model containment="true"
     * @generated
     */
    EList<Resource> getResources();

    /**
     * Returns the value of the '<em><b>Context Path</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Context Path</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Context Path</em>' attribute.
     * @see #setContextPath(String)
     * @see com.tibco.xpd.rsd.RsdPackage#getService_ContextPath()
     * @model
     * @generated
     */
    String getContextPath();

    /**
     * Sets the value of the '{@link com.tibco.xpd.rsd.Service#getContextPath <em>Context Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Context Path</em>' attribute.
     * @see #getContextPath()
     * @generated
     */
    void setContextPath(String value);

    /**
     * Returns the value of the '<em><b>Media Type</b></em>' attribute.
     * The default value is <code>"application/json"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Media Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Media Type</em>' attribute.
     * @see #setMediaType(String)
     * @see com.tibco.xpd.rsd.RsdPackage#getService_MediaType()
     * @model default="application/json"
     * @generated
     */
    String getMediaType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.rsd.Service#getMediaType <em>Media Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Media Type</em>' attribute.
     * @see #getMediaType()
     * @generated
     */
    void setMediaType(String value);

} // Service
