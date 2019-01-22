/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.worktype;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Work Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Array of work types.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.common.worktype.WorkType#getWorkType <em>Work Type</em>}</li>
 *   <li>{@link com.tibco.n2.common.worktype.WorkType#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.common.worktype.WorktypePackage#getWorkType()
 * @model extendedMetaData="name='WorkType' kind='elementOnly'"
 * @generated
 */
public interface WorkType extends EObject {
    /**
     * Returns the value of the '<em><b>Work Type</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.common.datamodel.WorkType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Work Type</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Work Type</em>' containment reference list.
     * @see com.tibco.n2.common.worktype.WorktypePackage#getWorkType_WorkType()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='WorkType' namespace='##targetNamespace'"
     * @generated
     */
    EList<com.tibco.n2.common.datamodel.WorkType> getWorkType();

    /**
     * Returns the value of the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Version</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Version</em>' attribute.
     * @see #setVersion(String)
     * @see com.tibco.n2.common.worktype.WorktypePackage#getWorkType_Version()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='version'"
     * @generated
     */
    String getVersion();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.worktype.WorkType#getVersion <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Version</em>' attribute.
     * @see #getVersion()
     * @generated
     */
    void setVersion(String value);

} // WorkType
