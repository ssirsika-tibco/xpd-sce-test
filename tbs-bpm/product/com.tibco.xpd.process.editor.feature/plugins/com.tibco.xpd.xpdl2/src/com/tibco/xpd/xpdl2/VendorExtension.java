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
 * A representation of the model object '<em><b>Vendor Extension</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.VendorExtension#getExtensionDescription <em>Extension Description</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.VendorExtension#getSchemaLocation <em>Schema Location</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.VendorExtension#getToolId <em>Tool Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getVendorExtension()
 * @model extendedMetaData="name='VendorExtension_._type' kind='elementOnly'"
 * @generated
 */
public interface VendorExtension extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Extension Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Extension Description</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Extension Description</em>' attribute.
     * @see #setExtensionDescription(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getVendorExtension_ExtensionDescription()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
     *        extendedMetaData="kind='attribute' name='extensionDescription'"
     * @generated
     */
    String getExtensionDescription();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.VendorExtension#getExtensionDescription <em>Extension Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Extension Description</em>' attribute.
     * @see #getExtensionDescription()
     * @generated
     */
    void setExtensionDescription(String value);

    /**
     * Returns the value of the '<em><b>Schema Location</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Schema Location</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Schema Location</em>' attribute.
     * @see #setSchemaLocation(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getVendorExtension_SchemaLocation()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI" required="true"
     *        extendedMetaData="kind='attribute' name='schemaLocation'"
     * @generated
     */
    String getSchemaLocation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.VendorExtension#getSchemaLocation <em>Schema Location</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Schema Location</em>' attribute.
     * @see #getSchemaLocation()
     * @generated
     */
    void setSchemaLocation(String value);

    /**
     * Returns the value of the '<em><b>Tool Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Tool Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Tool Id</em>' attribute.
     * @see #setToolId(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getVendorExtension_ToolId()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='ToolId'"
     * @generated
     */
    String getToolId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.VendorExtension#getToolId <em>Tool Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Tool Id</em>' attribute.
     * @see #getToolId()
     * @generated
     */
    void setToolId(String value);

} // VendorExtension
