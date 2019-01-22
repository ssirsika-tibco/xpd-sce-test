/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.datamodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Work Type Spec</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Complex type for work type UID and version
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.common.datamodel.WorkTypeSpec#getVersion <em>Version</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.WorkTypeSpec#getWorkTypeDescription <em>Work Type Description</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.WorkTypeSpec#getWorkTypeID <em>Work Type ID</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.common.datamodel.DatamodelPackage#getWorkTypeSpec()
 * @model extendedMetaData="name='WorkTypeSpec' kind='empty'"
 * @generated
 */
public interface WorkTypeSpec extends EObject {
    /**
     * Returns the value of the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The OSGi version number of this work type.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Version</em>' attribute.
     * @see #setVersion(String)
     * @see com.tibco.n2.common.datamodel.DatamodelPackage#getWorkTypeSpec_Version()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='version'"
     * @generated
     */
    String getVersion();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.datamodel.WorkTypeSpec#getVersion <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Version</em>' attribute.
     * @see #getVersion()
     * @generated
     */
    void setVersion(String value);

    /**
     * Returns the value of the '<em><b>Work Type Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The description of the work type.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Type Description</em>' attribute.
     * @see #setWorkTypeDescription(String)
     * @see com.tibco.n2.common.datamodel.DatamodelPackage#getWorkTypeSpec_WorkTypeDescription()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='workTypeDescription'"
     * @generated
     */
    String getWorkTypeDescription();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.datamodel.WorkTypeSpec#getWorkTypeDescription <em>Work Type Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Type Description</em>' attribute.
     * @see #getWorkTypeDescription()
     * @generated
     */
    void setWorkTypeDescription(String value);

    /**
     * Returns the value of the '<em><b>Work Type ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The UID of the work type
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Type ID</em>' attribute.
     * @see #setWorkTypeID(String)
     * @see com.tibco.n2.common.datamodel.DatamodelPackage#getWorkTypeSpec_WorkTypeID()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='workTypeID'"
     * @generated
     */
    String getWorkTypeID();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.datamodel.WorkTypeSpec#getWorkTypeID <em>Work Type ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Type ID</em>' attribute.
     * @see #getWorkTypeID()
     * @generated
     */
    void setWorkTypeID(String value);

} // WorkTypeSpec
