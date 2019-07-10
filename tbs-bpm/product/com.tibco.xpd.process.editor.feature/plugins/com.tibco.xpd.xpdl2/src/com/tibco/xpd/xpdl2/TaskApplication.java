/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Task Application</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.TaskApplication#getActualParameters <em>Actual Parameters</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TaskApplication#getDataMappings <em>Data Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TaskApplication#getPackageRef <em>Package Ref</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TaskApplication#getApplicationId <em>Application Id</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTaskApplication()
 * @model extendedMetaData="name='TaskApplication_._type' kind='elementOnly' features-order='actualParameters dataMappings description'"
 * @generated
 */
public interface TaskApplication extends DescribedElement {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Actual Parameters</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Expression}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Actual Parameters</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Actual Parameters</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTaskApplication_ActualParameters()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ActualParameter' namespace='##targetNamespace' wrap='ActualParameters'"
     * @generated
     */
    EList<Expression> getActualParameters();

    /**
     * Returns the value of the '<em><b>Data Mappings</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.DataMapping}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Data Mappings</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Data Mappings</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTaskApplication_DataMappings()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='DataMapping' namespace='##targetNamespace' wrap='DataMappings'"
     * @generated
     */
    EList<DataMapping> getDataMappings();

    /**
     * Returns the value of the '<em><b>Package Ref</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Package Ref</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Package Ref</em>' attribute.
     * @see #setPackageRef(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTaskApplication_PackageRef()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='PackageRef'"
     * @generated
     */
    String getPackageRef();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TaskApplication#getPackageRef <em>Package Ref</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Package Ref</em>' attribute.
     * @see #getPackageRef()
     * @generated
     */
    void setPackageRef(String value);

    /**
     * Returns the value of the '<em><b>Application Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Application Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Application Id</em>' attribute.
     * @see #setApplicationId(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTaskApplication_ApplicationId()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='Id'"
     * @generated
     */
    String getApplicationId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TaskApplication#getApplicationId <em>Application Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Application Id</em>' attribute.
     * @see #getApplicationId()
     * @generated
     */
    void setApplicationId(String value);

} // TaskApplication
