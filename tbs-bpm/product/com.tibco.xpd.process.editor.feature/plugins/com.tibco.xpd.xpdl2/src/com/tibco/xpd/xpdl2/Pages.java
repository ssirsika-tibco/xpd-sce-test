/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pages</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Pages#getPage <em>Page</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPages()
 * @model extendedMetaData="name='Pages' kind='elementOnly'"
 * @generated
 */
public interface Pages extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Page</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Page}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Page</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Page</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPages_Page()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Page' namespace='##targetNamespace' wrap='Pages'"
     * @generated
     */
    EList<Page> getPage();

} // Pages
