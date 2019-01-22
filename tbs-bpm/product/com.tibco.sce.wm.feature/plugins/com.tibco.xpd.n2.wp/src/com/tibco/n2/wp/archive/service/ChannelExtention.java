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
 * A representation of the model object '<em><b>Channel Extention</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Defines extended Channel Type deployment information such as Email Channel, or for a channel type not yet implemented
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.wp.archive.service.ChannelExtention#getFilename <em>Filename</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.ChannelExtention#getLocation <em>Location</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.wp.archive.service.WPPackage#getChannelExtention()
 * @model extendedMetaData="name='channelExtention' kind='empty'"
 * @generated
 */
public interface ChannelExtention extends EObject {
    /**
     * Returns the value of the '<em><b>Filename</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Filename</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Filename</em>' attribute.
     * @see #setFilename(String)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getChannelExtention_Filename()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='filename'"
     * @generated
     */
    String getFilename();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.ChannelExtention#getFilename <em>Filename</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Filename</em>' attribute.
     * @see #getFilename()
     * @generated
     */
    void setFilename(String value);

    /**
     * Returns the value of the '<em><b>Location</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Location</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Location</em>' attribute.
     * @see #setLocation(String)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getChannelExtention_Location()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='location'"
     * @generated
     */
    String getLocation();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.ChannelExtention#getLocation <em>Location</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Location</em>' attribute.
     * @see #getLocation()
     * @generated
     */
    void setLocation(String value);

} // ChannelExtention
