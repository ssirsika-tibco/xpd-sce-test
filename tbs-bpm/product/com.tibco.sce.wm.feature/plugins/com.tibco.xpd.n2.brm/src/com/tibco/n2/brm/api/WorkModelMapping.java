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
 * A representation of the model object '<em><b>Work Model Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Definition of a mapping of a work model parameter to a work type parameter.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.WorkModelMapping#getTypeParamName <em>Type Param Name</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkModelMapping#getModelParamName <em>Model Param Name</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkModelMapping#getDefaultValue <em>Default Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModelMapping()
 * @model extendedMetaData="name='WorkModelMapping' kind='elementOnly'"
 * @generated
 */
public interface WorkModelMapping extends EObject {
    /**
     * Returns the value of the '<em><b>Type Param Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The name of the WorkType parameter this mapping relates to.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Type Param Name</em>' attribute.
     * @see #setTypeParamName(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModelMapping_TypeParamName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='TypeParamName'"
     * @generated
     */
    String getTypeParamName();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkModelMapping#getTypeParamName <em>Type Param Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Type Param Name</em>' attribute.
     * @see #getTypeParamName()
     * @generated
     */
    void setTypeParamName(String value);

    /**
     * Returns the value of the '<em><b>Model Param Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The name of the Model parameter whose value should be mapped to map to this WorkType parameter.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Model Param Name</em>' attribute.
     * @see #setModelParamName(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModelMapping_ModelParamName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='ModelParamName'"
     * @generated
     */
    String getModelParamName();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkModelMapping#getModelParamName <em>Model Param Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Model Param Name</em>' attribute.
     * @see #getModelParamName()
     * @generated
     */
    void setModelParamName(String value);

    /**
     * Returns the value of the '<em><b>Default Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * A default value to specify for the WorkType parameter if no appropriate Model parameter exist to map from.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Default Value</em>' attribute.
     * @see #setDefaultValue(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModelMapping_DefaultValue()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='DefaultValue'"
     * @generated
     */
    String getDefaultValue();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkModelMapping#getDefaultValue <em>Default Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Default Value</em>' attribute.
     * @see #getDefaultValue()
     * @generated
     */
    void setDefaultValue(String value);

} // WorkModelMapping
