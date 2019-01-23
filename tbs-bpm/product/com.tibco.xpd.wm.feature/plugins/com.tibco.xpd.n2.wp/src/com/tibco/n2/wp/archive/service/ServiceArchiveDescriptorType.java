/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.wp.archive.service;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Service Archive Descriptor Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.wp.archive.service.ServiceArchiveDescriptorType#getChannels <em>Channels</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.ServiceArchiveDescriptorType#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.wp.archive.service.WPPackage#getServiceArchiveDescriptorType()
 * @model extendedMetaData="name='Service-Archive-Descriptor_._type' kind='elementOnly'"
 * @generated
 */
public interface ServiceArchiveDescriptorType extends EObject {
    /**
     * Returns the value of the '<em><b>Channels</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Channels are presenation channels which are RTC specific.One or more channels can be deployed at the same time during deployment
     * <!-- end-model-doc -->
     * @return the value of the '<em>Channels</em>' containment reference.
     * @see #setChannels(ChannelsType)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getServiceArchiveDescriptorType_Channels()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='channels' namespace='##targetNamespace'"
     * @generated
     */
    ChannelsType getChannels();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.ServiceArchiveDescriptorType#getChannels <em>Channels</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Channels</em>' containment reference.
     * @see #getChannels()
     * @generated
     */
    void setChannels(ChannelsType value);

    /**
     * Returns the value of the '<em><b>Version</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Version</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Version</em>' containment reference.
     * @see #setVersion(EObject)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getServiceArchiveDescriptorType_Version()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='version' namespace='##targetNamespace'"
     * @generated
     */
    EObject getVersion();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.ServiceArchiveDescriptorType#getVersion <em>Version</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Version</em>' containment reference.
     * @see #getVersion()
     * @generated
     */
    void setVersion(EObject value);

} // ServiceArchiveDescriptorType
