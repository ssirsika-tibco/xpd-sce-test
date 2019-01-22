/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Work Model Types</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Complete work type definition for a work model. This includes multiple work type ID references and an expression that determines which work type to use.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.WorkModelTypes#getWorkModelType <em>Work Model Type</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkModelTypes#getExpression <em>Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModelTypes()
 * @model extendedMetaData="name='WorkModelTypes' kind='elementOnly'"
 * @generated
 */
public interface WorkModelTypes extends EObject {
    /**
     * Returns the value of the '<em><b>Work Model Type</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.WorkModelType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Work Model Type</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Work Model Type</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModelTypes_WorkModelType()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='WorkModelType'"
     * @generated
     */
    EList<WorkModelType> getWorkModelType();

    /**
     * Returns the value of the '<em><b>Expression</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The expression that BRM should use to chose one of the WorkModelType objects at runtime.   If no expression is supplied then BRM will always choose the first entry
     * <!-- end-model-doc -->
     * @return the value of the '<em>Expression</em>' attribute.
     * @see #setExpression(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModelTypes_Expression()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='expression'"
     * @generated
     */
    String getExpression();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkModelTypes#getExpression <em>Expression</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Expression</em>' attribute.
     * @see #getExpression()
     * @generated
     */
    void setExpression(String value);

} // WorkModelTypes
