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
 * A representation of the model object '<em><b>Base Model Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Base model definition, containing all model informaion that is static (i.e. not subject to change via a runtime expression).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.BaseModelInfo#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.BaseModelInfo#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.BaseModelInfo#getPriority <em>Priority</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getBaseModelInfo()
 * @model extendedMetaData="name='BaseModelInfo' kind='empty'"
 * @generated
 */
public interface BaseModelInfo extends EObject {
    /**
     * Returns the value of the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The description of the work model.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Description</em>' attribute.
     * @see #setDescription(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getBaseModelInfo_Description()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='description'"
     * @generated
     */
    String getDescription();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.BaseModelInfo#getDescription <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Description</em>' attribute.
     * @see #getDescription()
     * @generated
     */
    void setDescription(String value);

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The name of the work model
     * <!-- end-model-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getBaseModelInfo_Name()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.BaseModelInfo#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Priority</b></em>' attribute.
     * The default value is <code>"50"</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The priority of the work item this model will create (defaults to 50).
     * <!-- end-model-doc -->
     * @return the value of the '<em>Priority</em>' attribute.
     * @see #isSetPriority()
     * @see #unsetPriority()
     * @see #setPriority(int)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getBaseModelInfo_Priority()
     * @model default="50" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='priority'"
     * @generated
     */
    int getPriority();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.BaseModelInfo#getPriority <em>Priority</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Priority</em>' attribute.
     * @see #isSetPriority()
     * @see #unsetPriority()
     * @see #getPriority()
     * @generated
     */
    void setPriority(int value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.BaseModelInfo#getPriority <em>Priority</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetPriority()
     * @see #getPriority()
     * @see #setPriority(int)
     * @generated
     */
    void unsetPriority();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.BaseModelInfo#getPriority <em>Priority</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Priority</em>' attribute is set.
     * @see #unsetPriority()
     * @see #getPriority()
     * @see #setPriority(int)
     * @generated
     */
    boolean isSetPriority();

} // BaseModelInfo
