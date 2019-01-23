/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Extended Attributes Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.ExtendedAttributesContainer#getExtendedAttributes <em>Extended Attributes</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getExtendedAttributesContainer()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface ExtendedAttributesContainer extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Extended Attributes</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.ExtendedAttribute}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Extended Attributes</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Extended Attributes</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getExtendedAttributesContainer_ExtendedAttributes()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ExtendedAttribute' namespace='##targetNamespace' wrap='ExtendedAttributes'"
     * @generated
     */
    EList<ExtendedAttribute> getExtendedAttributes();

} // ExtendedAttributesContainer
