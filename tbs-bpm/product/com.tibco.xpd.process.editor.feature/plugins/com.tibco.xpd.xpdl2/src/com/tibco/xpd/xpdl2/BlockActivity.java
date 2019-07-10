/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Block Activity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.BlockActivity#getActivitySetId <em>Activity Set Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.BlockActivity#getStartActivityId <em>Start Activity Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.BlockActivity#getView <em>View</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getBlockActivity()
 * @model extendedMetaData="name='BlockActivity_._type' kind='elementOnly'"
 * @generated
 */
public interface BlockActivity extends OtherAttributesContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Activity Set Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Activity Set Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Activity Set Id</em>' attribute.
     * @see #setActivitySetId(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getBlockActivity_ActivitySetId()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString" required="true"
     *        extendedMetaData="kind='attribute' name='ActivitySetId'"
     * @generated
     */
    String getActivitySetId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.BlockActivity#getActivitySetId <em>Activity Set Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Activity Set Id</em>' attribute.
     * @see #getActivitySetId()
     * @generated
     */
    void setActivitySetId(String value);

    /**
     * Returns the value of the '<em><b>Start Activity Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Start Activity Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Start Activity Id</em>' attribute.
     * @see #setStartActivityId(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getBlockActivity_StartActivityId()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='StartActivityId'"
     * @generated
     */
    String getStartActivityId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.BlockActivity#getStartActivityId <em>Start Activity Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Start Activity Id</em>' attribute.
     * @see #getStartActivityId()
     * @generated
     */
    void setStartActivityId(String value);

    /**
     * Returns the value of the '<em><b>View</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.ViewType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>View</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>View</em>' attribute.
     * @see com.tibco.xpd.xpdl2.ViewType
     * @see #isSetView()
     * @see #unsetView()
     * @see #setView(ViewType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getBlockActivity_View()
     * @model unsettable="true"
     *        extendedMetaData="kind='attribute' name='View'"
     * @generated
     */
    ViewType getView();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.BlockActivity#getView <em>View</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>View</em>' attribute.
     * @see com.tibco.xpd.xpdl2.ViewType
     * @see #isSetView()
     * @see #unsetView()
     * @see #getView()
     * @generated
     */
    void setView(ViewType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.BlockActivity#getView <em>View</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetView()
     * @see #getView()
     * @see #setView(ViewType)
     * @generated
     */
    void unsetView();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.BlockActivity#getView <em>View</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>View</em>' attribute is set.
     * @see #unsetView()
     * @see #getView()
     * @see #setView(ViewType)
     * @generated
     */
    boolean isSetView();

} // BlockActivity
