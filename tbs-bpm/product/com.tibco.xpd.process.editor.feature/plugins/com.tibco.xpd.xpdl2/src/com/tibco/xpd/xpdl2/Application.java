/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Application</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Application#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Application#getExternalReference <em>External Reference</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getApplication()
 * @model extendedMetaData="name='Application_._type' kind='elementOnly' features-order='description type formalParameters externalReference extendedAttributes'"
 * @generated
 */
public interface Application
        extends NamedElement, ExtendedAttributesContainer, FormalParametersContainer, DescribedElement {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Type</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Type</em>' containment reference.
     * @see #setType(ApplicationType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getApplication_Type()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Type' namespace='##targetNamespace'"
     * @generated
     */
    ApplicationType getType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Application#getType <em>Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Type</em>' containment reference.
     * @see #getType()
     * @generated
     */
    void setType(ApplicationType value);

    /**
     * Returns the value of the '<em><b>External Reference</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>External Reference</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>External Reference</em>' containment reference.
     * @see #setExternalReference(ExternalReference)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getApplication_ExternalReference()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ExternalReference' namespace='##targetNamespace'"
     * @generated
     */
    ExternalReference getExternalReference();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Application#getExternalReference <em>External Reference</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>External Reference</em>' containment reference.
     * @see #getExternalReference()
     * @generated
     */
    void setExternalReference(ExternalReference value);

} // Application
