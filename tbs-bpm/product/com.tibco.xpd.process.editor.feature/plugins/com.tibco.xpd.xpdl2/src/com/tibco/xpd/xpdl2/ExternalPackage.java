/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>External Package</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.ExternalPackage#getHref <em>Href</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getExternalPackage()
 * @model extendedMetaData="name='ExternalPackage_._type' kind='elementOnly' features-order='extendedAttributes'"
 * @generated
 */
public interface ExternalPackage extends NamedElement,
        ExtendedAttributesContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Href</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Href</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Href</em>' attribute.
     * @see #setHref(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getExternalPackage_Href()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='href'"
     * @generated
     */
    String getHref();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ExternalPackage#getHref <em>Href</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Href</em>' attribute.
     * @see #getHref()
     * @generated
     */
    void setHref(String value);

} // ExternalPackage
