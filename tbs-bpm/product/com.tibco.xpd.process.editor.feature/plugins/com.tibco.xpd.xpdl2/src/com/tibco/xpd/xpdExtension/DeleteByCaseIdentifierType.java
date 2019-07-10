/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Delete By Case Identifier Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.DeleteByCaseIdentifierType#getFieldPath <em>Field Path</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DeleteByCaseIdentifierType#getIdentifierName <em>Identifier Name</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDeleteByCaseIdentifierType()
 * @model extendedMetaData="name='DeleteByCaseIdentifier_._type' kind='empty'"
 * @generated
 */
public interface DeleteByCaseIdentifierType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Field Path</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Field or javascript child content path e.g. myField.child etc
     * <!-- end-model-doc -->
     * @return the value of the '<em>Field Path</em>' attribute.
     * @see #setFieldPath(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDeleteByCaseIdentifierType_FieldPath()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='FieldPath'"
     * @generated
     */
    String getFieldPath();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DeleteByCaseIdentifierType#getFieldPath <em>Field Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Field Path</em>' attribute.
     * @see #getFieldPath()
     * @generated
     */
    void setFieldPath(String value);

    /**
     * Returns the value of the '<em><b>Identifier Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Identifier Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Identifier Name</em>' attribute.
     * @see #setIdentifierName(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDeleteByCaseIdentifierType_IdentifierName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='IdentifierName'"
     * @generated
     */
    String getIdentifierName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DeleteByCaseIdentifierType#getIdentifierName <em>Identifier Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Identifier Name</em>' attribute.
     * @see #getIdentifierName()
     * @generated
     */
    void setIdentifierName(String value);

} // DeleteByCaseIdentifierType
