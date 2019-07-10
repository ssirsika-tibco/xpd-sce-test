/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Group#getCategory <em>Category</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Group#getObject <em>Object</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getGroup()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Group extends NamedElement, OtherElementsContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Category</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Category</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Category</em>' containment reference.
     * @see #setCategory(Category)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getGroup_Category()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Category' namespace='##targetNamespace'"
     * @generated
     */
    Category getCategory();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Group#getCategory <em>Category</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Category</em>' containment reference.
     * @see #getCategory()
     * @generated
     */
    void setCategory(Category value);

    /**
     * Returns the value of the '<em><b>Object</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Object}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Object</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Object</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getGroup_Object()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Object' namespace='##targetNamespace'"
     * @generated
     */
    EList<com.tibco.xpd.xpdl2.Object> getObject();

} // Group
