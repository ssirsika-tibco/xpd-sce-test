/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Wsdl Event Association</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * deprecated.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsdlEventAssociation#getEventId <em>Event Id</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsdlEventAssociation()
 * @model extendedMetaData="name='WsdlEventAssociation' kind='elementOnly'"
 * @generated
 */
public interface WsdlEventAssociation extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Event Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * This attribute stores the event id of the event associated with the WSDL
     *  file.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Event Id</em>' attribute.
     * @see #setEventId(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsdlEventAssociation_EventId()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='EventId' namespace='##targetNamespace'"
     * @generated
     */
    String getEventId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsdlEventAssociation#getEventId <em>Event Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Event Id</em>' attribute.
     * @see #getEventId()
     * @generated
     */
    void setEventId(String value);

} // WsdlEventAssociation