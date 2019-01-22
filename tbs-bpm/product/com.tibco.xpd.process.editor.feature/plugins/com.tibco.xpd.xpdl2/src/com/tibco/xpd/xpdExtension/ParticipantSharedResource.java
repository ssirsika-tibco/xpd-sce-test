/**
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Participant Shared Resource</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Participant shared resource specifies the type of shared resource (email, web-serice, java ..) associated with a participant. This element is added under the xpdl element 'Participant'.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.ParticipantSharedResource#getEmail <em>Email</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ParticipantSharedResource#getJdbc <em>Jdbc</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ParticipantSharedResource#getWebService <em>Web Service</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ParticipantSharedResource#getRestService <em>Rest Service</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getParticipantSharedResource()
 * @model extendedMetaData="name='ParticipantSharedResource' kind='elementOnly'"
 * @generated
 */
public interface ParticipantSharedResource extends EObject {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Email</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Email</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Email</em>' containment reference.
     * @see #setEmail(EmailResource)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getParticipantSharedResource_Email()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Email' namespace='##targetNamespace'"
     * @generated
     */
    EmailResource getEmail();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ParticipantSharedResource#getEmail <em>Email</em>}' containment reference.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @param value the new value of the '<em>Email</em>' containment reference.
     * @see #getEmail()
     * @generated
     */
    void setEmail(EmailResource value);

    /**
     * Returns the value of the '<em><b>Jdbc</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Jdbc</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Jdbc</em>' containment reference.
     * @see #setJdbc(JdbcResource)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getParticipantSharedResource_Jdbc()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Jdbc' namespace='##targetNamespace'"
     * @generated
     */
    JdbcResource getJdbc();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ParticipantSharedResource#getJdbc <em>Jdbc</em>}' containment reference.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @param value the new value of the '<em>Jdbc</em>' containment reference.
     * @see #getJdbc()
     * @generated
     */
    void setJdbc(JdbcResource value);

    /**
     * Returns the value of the '<em><b>Web Service</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Web Service</em>' containment reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Web Service</em>' containment reference.
     * @see #setWebService(WsResource)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getParticipantSharedResource_WebService()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='WebService' namespace='##targetNamespace'"
     * @generated
     */
    WsResource getWebService();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ParticipantSharedResource#getWebService <em>Web Service</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Web Service</em>' containment reference.
     * @see #getWebService()
     * @generated
     */
    void setWebService(WsResource value);

    /**
     * Returns the value of the '<em><b>Rest Service</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Rest Service</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Rest Service</em>' containment reference.
     * @see #setRestService(RestServiceResource)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getParticipantSharedResource_RestService()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='RestService' namespace='##targetNamespace'"
     * @generated
     */
    RestServiceResource getRestService();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ParticipantSharedResource#getRestService <em>Rest Service</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Rest Service</em>' containment reference.
     * @see #getRestService()
     * @generated
     */
    void setRestService(RestServiceResource value);

    /**
     * <!-- begin-user-doc --> Gets one of the shared resource which is not set
     * to <code>null</code>. <!-- end-user-doc -->
     * @model kind="operation"
     * @generated
     */
    EObject getSharedResource();

    /**
     * <!-- begin-user-doc --> Sets the shared resource to an appropriate
     * feature depending on type and sets all other features to
     * <code>null</code>.
     * 
     * @param sharedResource
     *            the shared resource to set or <code>null</code> to set all
     *            features' values to null. <!-- end-user-doc -->
     * @model
     * @generated
     */
    void setSharedResource(EObject sharedResource);

} // ParticipantSharedResource
