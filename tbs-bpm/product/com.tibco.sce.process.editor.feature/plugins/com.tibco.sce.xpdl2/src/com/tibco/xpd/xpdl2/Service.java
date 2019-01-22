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
 * A representation of the model object '<em><b>Service</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Service#getEndPoint <em>End Point</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Service#getPortName <em>Port Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Service#getServiceName <em>Service Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getService()
 * @model extendedMetaData="name='Service_._type' kind='elementOnly'"
 * @generated
 */
public interface Service extends OtherAttributesContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>End Point</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>End Point</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>End Point</em>' containment reference.
     * @see #setEndPoint(EndPoint)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getService_EndPoint()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='EndPoint' namespace='##targetNamespace'"
     * @generated
     */
    EndPoint getEndPoint();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Service#getEndPoint <em>End Point</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>End Point</em>' containment reference.
     * @see #getEndPoint()
     * @generated
     */
    void setEndPoint(EndPoint value);

    /**
     * Returns the value of the '<em><b>Port Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Port Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Port Name</em>' attribute.
     * @see #setPortName(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getService_PortName()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='PortName'"
     * @generated
     */
    String getPortName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Service#getPortName <em>Port Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Port Name</em>' attribute.
     * @see #getPortName()
     * @generated
     */
    void setPortName(String value);

    /**
     * Returns the value of the '<em><b>Service Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Service Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Service Name</em>' attribute.
     * @see #setServiceName(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getService_ServiceName()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='ServiceName'"
     * @generated
     */
    String getServiceName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Service#getServiceName <em>Service Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Service Name</em>' attribute.
     * @see #getServiceName()
     * @generated
     */
    void setServiceName(String value);

} // Service
