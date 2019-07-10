/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Update Case Operation Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.UpdateCaseOperationType#getFromFieldPath <em>From Field Path</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getUpdateCaseOperationType()
 * @model extendedMetaData="name='Update_._type' kind='empty'"
 * @generated
 */
public interface UpdateCaseOperationType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>From Field Path</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Field or javascript child content path e.g. myField.child etc 
     * If CaseRefField is an array, then FromFieldPath must also reference a multiple sequence.
     * <!-- end-model-doc -->
     * @return the value of the '<em>From Field Path</em>' attribute.
     * @see #setFromFieldPath(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getUpdateCaseOperationType_FromFieldPath()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='FromFieldPath'"
     * @generated
     */
    String getFromFieldPath();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.UpdateCaseOperationType#getFromFieldPath <em>From Field Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>From Field Path</em>' attribute.
     * @see #getFromFieldPath()
     * @generated
     */
    void setFromFieldPath(String value);

} // UpdateCaseOperationType
