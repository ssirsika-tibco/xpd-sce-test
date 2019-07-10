/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Discriminator</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Element specifying the structured discriminator for complex gateway.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.Discriminator#getDiscriminatorType <em>Discriminator Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.Discriminator#getStructuredDiscriminator <em>Structured Discriminator</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDiscriminator()
 * @model annotation="ExtendedMetaData kind='element' name='Discriminator' namespace='##targetNamespace'"
 * @generated
 */
public interface Discriminator extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Discriminator Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Discriminator Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Discriminator Type</em>' attribute.
     * @see #setDiscriminatorType(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDiscriminator_DiscriminatorType()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='Type'"
     * @generated
     */
    String getDiscriminatorType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.Discriminator#getDiscriminatorType <em>Discriminator Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Discriminator Type</em>' attribute.
     * @see #getDiscriminatorType()
     * @generated
     */
    void setDiscriminatorType(String value);

    /**
     * Returns the value of the '<em><b>Structured Discriminator</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Structured Discriminator</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Structured Discriminator</em>' containment reference.
     * @see #setStructuredDiscriminator(StructuredDiscriminator)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDiscriminator_StructuredDiscriminator()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='StructuredDiscriminator' namespace='##targetNamespace'"
     * @generated
     */
    StructuredDiscriminator getStructuredDiscriminator();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.Discriminator#getStructuredDiscriminator <em>Structured Discriminator</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Structured Discriminator</em>' containment reference.
     * @see #getStructuredDiscriminator()
     * @generated
     */
    void setStructuredDiscriminator(StructuredDiscriminator value);

} // Discriminator
