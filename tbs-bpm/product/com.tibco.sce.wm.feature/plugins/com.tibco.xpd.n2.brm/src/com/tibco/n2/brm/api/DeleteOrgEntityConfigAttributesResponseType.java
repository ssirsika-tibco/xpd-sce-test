/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Delete Org Entity Config Attributes Response Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.DeleteOrgEntityConfigAttributesResponseType#isSuccess <em>Success</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getDeleteOrgEntityConfigAttributesResponseType()
 * @model extendedMetaData="name='deleteOrgEntityConfigAttributesResponse_._type' kind='elementOnly'"
 * @generated
 */
public interface DeleteOrgEntityConfigAttributesResponseType extends EObject {
    /**
     * Returns the value of the '<em><b>Success</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Boolean value indicating whether the request succeeded or failed.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Success</em>' attribute.
     * @see #isSetSuccess()
     * @see #unsetSuccess()
     * @see #setSuccess(boolean)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDeleteOrgEntityConfigAttributesResponseType_Success()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
     *        extendedMetaData="kind='element' name='success'"
     * @generated
     */
    boolean isSuccess();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DeleteOrgEntityConfigAttributesResponseType#isSuccess <em>Success</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Success</em>' attribute.
     * @see #isSetSuccess()
     * @see #unsetSuccess()
     * @see #isSuccess()
     * @generated
     */
    void setSuccess(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.DeleteOrgEntityConfigAttributesResponseType#isSuccess <em>Success</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetSuccess()
     * @see #isSuccess()
     * @see #setSuccess(boolean)
     * @generated
     */
    void unsetSuccess();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.DeleteOrgEntityConfigAttributesResponseType#isSuccess <em>Success</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Success</em>' attribute is set.
     * @see #unsetSuccess()
     * @see #isSuccess()
     * @see #setSuccess(boolean)
     * @generated
     */
    boolean isSetSuccess();

} // DeleteOrgEntityConfigAttributesResponseType
