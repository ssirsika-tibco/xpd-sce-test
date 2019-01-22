/**
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ws Binding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsBinding#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsBinding#getExtendedProperties <em>Extended Properties</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsBinding()
 * @model extendedMetaData="name='WsBinding' kind='empty'"
 * @generated
 */
public interface WsBinding extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsBinding_Name()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='Name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsBinding#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Extended Properties</b></em>' reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.XpdExtProperty}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Extended Properties</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Extended Properties</em>' reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsBinding_ExtendedProperties()
     * @model
     * @generated
     */
    EList<XpdExtProperty> getExtendedProperties();

} // WsBinding
