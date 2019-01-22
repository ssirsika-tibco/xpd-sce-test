/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channels;

import com.tibco.xpd.presentation.channeltypes.ChannelDestination;
import com.tibco.xpd.presentation.channeltypes.NamedElement;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Channel</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.presentation.channels.Channel#getTypeAssociations <em>Type Associations</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channels.Channel#isDefault <em>Default</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.presentation.channels.ChannelsPackage#getChannel()
 * @model
 * @generated
 */
public interface Channel extends NamedElement {
    /**
     * Returns the value of the '<em><b>Type Associations</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.presentation.channels.TypeAssociation}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Type Associations</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Type Associations</em>' containment reference list.
     * @see com.tibco.xpd.presentation.channels.ChannelsPackage#getChannel_TypeAssociations()
     * @model containment="true"
     * @generated
     */
    EList<TypeAssociation> getTypeAssociations();

    /**
     * Returns the value of the '<em><b>Default</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Default</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Default</em>' attribute.
     * @see #setDefault(boolean)
     * @see com.tibco.xpd.presentation.channels.ChannelsPackage#getChannel_Default()
     * @model default="false"
     * @generated
     */
    boolean isDefault();

    /**
     * Sets the value of the '{@link com.tibco.xpd.presentation.channels.Channel#isDefault <em>Default</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Default</em>' attribute.
     * @see #isDefault()
     * @generated
     */
    void setDefault(boolean value);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model ordered="false" destinationsMany="true" destinationsOrdered="false"
     * @generated
     */
    EList<TypeAssociation> getTypeAssociations(EList<ChannelDestination> destinations);

} // Channel
