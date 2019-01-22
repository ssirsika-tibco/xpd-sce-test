/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.wp.archive.service;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Channels Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.wp.archive.service.ChannelsType#getChannel <em>Channel</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.wp.archive.service.WPPackage#getChannelsType()
 * @model extendedMetaData="name='channels_._type' kind='elementOnly'"
 * @generated
 */
public interface ChannelsType extends EObject {
    /**
     * Returns the value of the '<em><b>Channel</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.wp.archive.service.ChannelType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Channel is a presentation channel.At least one channel needs to be specified during deployment.It is possible to just deploy the channel without deploying the assosciate resources or can be a full fledged channel deployment with associated work model type and its resources
     * <!-- end-model-doc -->
     * @return the value of the '<em>Channel</em>' containment reference list.
     * @see com.tibco.n2.wp.archive.service.WPPackage#getChannelsType_Channel()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='channel' namespace='##targetNamespace'"
     * @generated
     */
    EList<ChannelType> getChannel();

} // ChannelsType
