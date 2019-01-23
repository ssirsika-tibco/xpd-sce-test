/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channels;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Channels</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.presentation.channels.Channels#getChannels <em>Channels</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.presentation.channels.ChannelsPackage#getChannels()
 * @model
 * @generated
 */
public interface Channels extends EObject {
    /**
     * Returns the value of the '<em><b>Channels</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.presentation.channels.Channel}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Channels</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Channels</em>' containment reference list.
     * @see com.tibco.xpd.presentation.channels.ChannelsPackage#getChannels_Channels()
     * @model containment="true"
     * @generated
     */
    EList<Channel> getChannels();

} // Channels
