/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xpd Ext Data Object Attributes</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Contains attributes of a DataObject, added as other element to a DataObject.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.XpdExtDataObjectAttributes#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.XpdExtDataObjectAttributes#getExternalReference <em>External Reference</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.XpdExtDataObjectAttributes#getProperties <em>Properties</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getXpdExtDataObjectAttributes()
 * @model extendedMetaData="name='DataObjectAttributes'"
 * @generated
 */
public interface XpdExtDataObjectAttributes extends EObject {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Description</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Description</em>' attribute.
     * @see #setDescription(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getXpdExtDataObjectAttributes_Description()
     * @model extendedMetaData="name='Description' kind='element' namespace='##targetNamespace'"
     * @generated
     */
    String getDescription();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.XpdExtDataObjectAttributes#getDescription <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Description</em>' attribute.
     * @see #getDescription()
     * @generated
     */
    void setDescription(String value);

    /**
     * Returns the value of the '<em><b>External Reference</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>External Reference</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>External Reference</em>' attribute.
     * @see #setExternalReference(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getXpdExtDataObjectAttributes_ExternalReference()
     * @model extendedMetaData="name='ExternalReference' kind='attribute' namespace='##targetNamespace'"
     * @generated
     */
    String getExternalReference();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.XpdExtDataObjectAttributes#getExternalReference <em>External Reference</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>External Reference</em>' attribute.
     * @see #getExternalReference()
     * @generated
     */
    void setExternalReference(String value);

    /**
     * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.XpdExtProperty}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Properties</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Properties</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getXpdExtDataObjectAttributes_Properties()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Property' namespace='##targetNamespace' wrap='Properties'"
     * @generated
     */
    EList<XpdExtProperty> getProperties();

} // XpdExtDataObjectAttributes