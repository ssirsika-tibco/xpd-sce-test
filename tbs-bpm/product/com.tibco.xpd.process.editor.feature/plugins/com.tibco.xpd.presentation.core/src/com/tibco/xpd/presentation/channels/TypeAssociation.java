/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channels;

import com.tibco.xpd.presentation.channeltypes.ChannelType;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Association</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.presentation.channels.TypeAssociation#getChannelType <em>Channel Type</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channels.TypeAssociation#getAttributeValues <em>Attribute Values</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channels.TypeAssociation#getExtendedAttributes <em>Extended Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channels.TypeAssociation#getChannel <em>Channel</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channels.TypeAssociation#getDerivedId <em>Derived Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.presentation.channels.ChannelsPackage#getTypeAssociation()
 * @model
 * @generated
 */
public interface TypeAssociation extends EObject {
    /**
     * Returns the value of the '<em><b>Channel Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Channel Type</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Channel Type</em>' reference.
     * @see #setChannelType(ChannelType)
     * @see com.tibco.xpd.presentation.channels.ChannelsPackage#getTypeAssociation_ChannelType()
     * @model
     * @generated
     */
    ChannelType getChannelType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.presentation.channels.TypeAssociation#getChannelType <em>Channel Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Channel Type</em>' reference.
     * @see #getChannelType()
     * @generated
     */
    void setChannelType(ChannelType value);

    /**
     * Returns the value of the '<em><b>Attribute Values</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.presentation.channels.AttributeValue}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Attribute Values</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Attribute Values</em>' containment reference list.
     * @see com.tibco.xpd.presentation.channels.ChannelsPackage#getTypeAssociation_AttributeValues()
     * @model containment="true"
     * @generated
     */
    EList<AttributeValue> getAttributeValues();

    /**
     * Returns the value of the '<em><b>Extended Attributes</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.presentation.channels.ExtendedAttribute}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Extended Attributes</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Extended Attributes</em>' containment reference list.
     * @see com.tibco.xpd.presentation.channels.ChannelsPackage#getTypeAssociation_ExtendedAttributes()
     * @model containment="true"
     * @generated
     */
    EList<ExtendedAttribute> getExtendedAttributes();

    /**
     * Returns the value of the '<em><b>Channel</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Channel</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Channel</em>' reference.
     * @see com.tibco.xpd.presentation.channels.ChannelsPackage#getTypeAssociation_Channel()
     * @model transient="true" changeable="false" volatile="true" derived="true"
     * @generated
     */
    Channel getChannel();

    /**
     * Returns the value of the '<em><b>Derived Id</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Derived Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Derived Id</em>' attribute.
     * @see com.tibco.xpd.presentation.channels.ChannelsPackage#getTypeAssociation_DerivedId()
     * @model default="" transient="true" changeable="false" volatile="true" derived="true"
     * @generated
     */
    String getDerivedId();

} // TypeAssociation
