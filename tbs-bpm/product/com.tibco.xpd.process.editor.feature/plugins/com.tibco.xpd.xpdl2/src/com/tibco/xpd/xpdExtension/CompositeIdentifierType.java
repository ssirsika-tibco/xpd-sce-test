/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Composite Identifier Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.CompositeIdentifierType#getFieldPath <em>Field Path</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.CompositeIdentifierType#getIdentifiername <em>Identifiername</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCompositeIdentifierType()
 * @model extendedMetaData="name='CompositeIdentifier_._type' kind='empty'"
 * @generated
 */
public interface CompositeIdentifierType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Field Path</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Field or javascript child content path e.g. myField.child etc
     * <!-- end-model-doc -->
     * @return the value of the '<em>Field Path</em>' attribute.
     * @see #setFieldPath(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCompositeIdentifierType_FieldPath()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='FieldPath'"
     * @generated
     */
    String getFieldPath();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CompositeIdentifierType#getFieldPath <em>Field Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Field Path</em>' attribute.
     * @see #getFieldPath()
     * @generated
     */
    void setFieldPath(String value);

    /**
     * Returns the value of the '<em><b>Identifiername</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Identifiername</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Identifiername</em>' attribute.
     * @see #setIdentifiername(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCompositeIdentifierType_Identifiername()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='Identifiername'"
     * @generated
     */
    String getIdentifiername();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CompositeIdentifierType#getIdentifiername <em>Identifiername</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Identifiername</em>' attribute.
     * @see #getIdentifiername()
     * @generated
     */
    void setIdentifiername(String value);

} // CompositeIdentifierType
