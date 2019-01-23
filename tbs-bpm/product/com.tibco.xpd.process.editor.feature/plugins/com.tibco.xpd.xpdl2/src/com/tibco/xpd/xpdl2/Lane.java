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
 * A representation of the model object '<em><b>Lane</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Lane#getObject <em>Object</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Lane#getDeprecatedParentLane <em>Deprecated Parent Lane</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Lane#getDeprecatedParentPoolId <em>Deprecated Parent Pool Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Lane#getParentPool <em>Parent Pool</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Lane#getPerformers <em>Performers</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Lane#getNestedLane <em>Nested Lane</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLane()
 * @model extendedMetaData="name='Lane_._type' kind='elementOnly' features-order='object nodeGraphicsInfos'"
 * @generated
 */
public interface Lane extends NamedElement, GraphicalNode,
        OtherElementsContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Object</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Object</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Object</em>' containment reference.
     * @see #setObject(com.tibco.xpd.xpdl2.Object)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLane_Object()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Object' namespace='##targetNamespace'"
     * @generated
     */
    com.tibco.xpd.xpdl2.Object getObject();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Lane#getObject <em>Object</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Object</em>' containment reference.
     * @see #getObject()
     * @generated
     */
    void setObject(com.tibco.xpd.xpdl2.Object value);

    /**
     * Returns the value of the '<em><b>Deprecated Parent Lane</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Deprecated Parent Lane</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Deprecated Parent Lane</em>' attribute.
     * @see #setDeprecatedParentLane(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLane_DeprecatedParentLane()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='ParentLane'"
     * @generated
     */
    String getDeprecatedParentLane();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Lane#getDeprecatedParentLane <em>Deprecated Parent Lane</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Deprecated Parent Lane</em>' attribute.
     * @see #getDeprecatedParentLane()
     * @generated
     */
    void setDeprecatedParentLane(String value);

    /**
     * Returns the value of the '<em><b>Deprecated Parent Pool Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Deprecated Parent Pool Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Deprecated Parent Pool Id</em>' attribute.
     * @see #setDeprecatedParentPoolId(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLane_DeprecatedParentPoolId()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='ParentPool'"
     * @generated
     */
    String getDeprecatedParentPoolId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Lane#getDeprecatedParentPoolId <em>Deprecated Parent Pool Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Deprecated Parent Pool Id</em>' attribute.
     * @see #getDeprecatedParentPoolId()
     * @generated
     */
    void setDeprecatedParentPoolId(String value);

    /**
     * Returns the value of the '<em><b>Parent Pool</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.xpdl2.Pool#getLanes <em>Lanes</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parent Pool</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Parent Pool</em>' container reference.
     * @see #setParentPool(Pool)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLane_ParentPool()
     * @see com.tibco.xpd.xpdl2.Pool#getLanes
     * @model opposite="lanes" transient="false"
     * @generated
     */
    Pool getParentPool();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Lane#getParentPool <em>Parent Pool</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Parent Pool</em>' container reference.
     * @see #getParentPool()
     * @generated
     */
    void setParentPool(Pool value);

    /**
     * Returns the value of the '<em><b>Performers</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Performers</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Performers</em>' containment reference.
     * @see #setPerformers(Performers)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLane_Performers()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Performers' namespace='##targetNamespace'"
     * @generated
     */
    Performers getPerformers();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Lane#getPerformers <em>Performers</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Performers</em>' containment reference.
     * @see #getPerformers()
     * @generated
     */
    void setPerformers(Performers value);

    /**
     * Returns the value of the '<em><b>Nested Lane</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Lane}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Nested Lane</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Nested Lane</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLane_NestedLane()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='NestedLane' namespace='##targetNamespace'"
     * @generated
     */
    EList<Lane> getNestedLane();

} // Lane
