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
 * A representation of the model object '<em><b>Work Model Entities</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Complete entity definition for a work model. 
 * 
 * This includes mutluple entity sets or expressions with a single expression that determines which set to use.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.WorkModelEntities#getWorkModelEntity <em>Work Model Entity</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkModelEntities#getExpression <em>Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModelEntities()
 * @model extendedMetaData="name='WorkModelEntities' kind='elementOnly'"
 * @generated
 */
public interface WorkModelEntities extends EObject {
    /**
     * Returns the value of the '<em><b>Work Model Entity</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.WorkModelEntity}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Work Model Entity</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Work Model Entity</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModelEntities_WorkModelEntity()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='WorkModelEntity'"
     * @generated
     */
    EList<WorkModelEntity> getWorkModelEntity();

    /**
     * Returns the value of the '<em><b>Expression</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The expression that BRM should use to chose one of the WorkModelEntity objects at runtime.   If no expression is supplied then BRM will always choose the first entry
     * <!-- end-model-doc -->
     * @return the value of the '<em>Expression</em>' attribute.
     * @see #setExpression(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModelEntities_Expression()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='expression'"
     * @generated
     */
    String getExpression();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkModelEntities#getExpression <em>Expression</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Expression</em>' attribute.
     * @see #getExpression()
     * @generated
     */
    void setExpression(String value);

} // WorkModelEntities
