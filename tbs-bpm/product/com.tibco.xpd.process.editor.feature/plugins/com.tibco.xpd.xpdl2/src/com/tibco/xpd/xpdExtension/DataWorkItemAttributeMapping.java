/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Work Item Attribute Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.DataWorkItemAttributeMapping#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DataWorkItemAttributeMapping#getProcessData <em>Process Data</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDataWorkItemAttributeMapping()
 * @model extendedMetaData="name='DataWorkItemAttributeMapping_._type' kind='elementOnly'"
 * @generated
 */
public interface DataWorkItemAttributeMapping extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Attribute</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Attribute</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Attribute</em>' attribute.
     * @see #setAttribute(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDataWorkItemAttributeMapping_Attribute()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='WorkItemAttribute' namespace='##targetNamespace'"
     * @generated
     */
    String getAttribute();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DataWorkItemAttributeMapping#getAttribute <em>Attribute</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute</em>' attribute.
     * @see #getAttribute()
     * @generated
     */
    void setAttribute(String value);

    /**
     * Returns the value of the '<em><b>Process Data</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Process Data</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Process Data</em>' attribute.
     * @see #setProcessData(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDataWorkItemAttributeMapping_ProcessData()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='ProcessData' namespace='##targetNamespace'"
     * @generated
     */
    String getProcessData();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DataWorkItemAttributeMapping#getProcessData <em>Process Data</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Process Data</em>' attribute.
     * @see #getProcessData()
     * @generated
     */
    void setProcessData(String value);

} // DataWorkItemAttributeMapping
