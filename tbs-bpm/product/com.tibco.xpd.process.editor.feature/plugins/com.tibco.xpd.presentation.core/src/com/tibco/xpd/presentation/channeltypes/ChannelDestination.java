/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channeltypes;

import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Channel Destination</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.ChannelDestination#getChannelTypes <em>Channel Types</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getChannelDestination()
 * @model
 * @generated
 */
public interface ChannelDestination extends NamedElement {

    /**
     * Returns the value of the '<em><b>Channel Types</b></em>' reference list.
     * The list contents are of type {@link com.tibco.xpd.presentation.channeltypes.ChannelType}.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.presentation.channeltypes.ChannelType#getDestinations <em>Destinations</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Channel Types</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Channel Types</em>' reference list.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getChannelDestination_ChannelTypes()
     * @see com.tibco.xpd.presentation.channeltypes.ChannelType#getDestinations
     * @model opposite="destinations" transient="true" derived="true"
     * @generated
     */
    EList<ChannelType> getChannelTypes();
} // ChannelDestination
