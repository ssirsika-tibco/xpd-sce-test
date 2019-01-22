/**
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdExtension.EmailResource;
import com.tibco.xpd.xpdExtension.JdbcResource;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.RestServiceResource;
import com.tibco.xpd.xpdExtension.WsResource;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Participant Shared Resource</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ParticipantSharedResourceImpl#getEmail <em>Email</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ParticipantSharedResourceImpl#getJdbc <em>Jdbc</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ParticipantSharedResourceImpl#getWebService <em>Web Service</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ParticipantSharedResourceImpl#getRestService <em>Rest Service</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ParticipantSharedResourceImpl extends EObjectImpl
        implements ParticipantSharedResource {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getEmail() <em>Email</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getEmail()
     * @generated
     * @ordered
     */
    protected EmailResource email;

    /**
     * The cached value of the '{@link #getJdbc() <em>Jdbc</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getJdbc()
     * @generated
     * @ordered
     */
    protected JdbcResource jdbc;

    /**
     * The cached value of the '{@link #getWebService() <em>Web Service</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getWebService()
     * @generated
     * @ordered
     */
    protected WsResource webService;

    /**
     * The cached value of the '{@link #getRestService() <em>Rest Service</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getRestService()
     * @generated
     * @ordered
     */
    protected RestServiceResource restService;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected ParticipantSharedResourceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.PARTICIPANT_SHARED_RESOURCE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EmailResource getEmail() {
        return email;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetEmail(EmailResource newEmail,
            NotificationChain msgs) {
        EmailResource oldEmail = email;
        email = newEmail;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this,
                    Notification.SET,
                    XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__EMAIL,
                    oldEmail, newEmail);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setEmail(EmailResource newEmail) {
        if (newEmail != email) {
            NotificationChain msgs = null;
            if (email != null)
                msgs = ((InternalEObject) email).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__EMAIL,
                        null,
                        msgs);
            if (newEmail != null)
                msgs = ((InternalEObject) newEmail).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__EMAIL,
                        null,
                        msgs);
            msgs = basicSetEmail(newEmail, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__EMAIL,
                    newEmail, newEmail));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public JdbcResource getJdbc() {
        return jdbc;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetJdbc(JdbcResource newJdbc,
            NotificationChain msgs) {
        JdbcResource oldJdbc = jdbc;
        jdbc = newJdbc;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this,
                    Notification.SET,
                    XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__JDBC,
                    oldJdbc, newJdbc);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setJdbc(JdbcResource newJdbc) {
        if (newJdbc != jdbc) {
            NotificationChain msgs = null;
            if (jdbc != null)
                msgs = ((InternalEObject) jdbc).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__JDBC,
                        null,
                        msgs);
            if (newJdbc != null)
                msgs = ((InternalEObject) newJdbc).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__JDBC,
                        null,
                        msgs);
            msgs = basicSetJdbc(newJdbc, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__JDBC,
                    newJdbc, newJdbc));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public WsResource getWebService() {
        return webService;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWebService(WsResource newWebService,
            NotificationChain msgs) {
        WsResource oldWebService = webService;
        webService = newWebService;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this,
                    Notification.SET,
                    XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__WEB_SERVICE,
                    oldWebService, newWebService);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setWebService(WsResource newWebService) {
        if (newWebService != webService) {
            NotificationChain msgs = null;
            if (webService != null)
                msgs = ((InternalEObject) webService).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__WEB_SERVICE,
                        null,
                        msgs);
            if (newWebService != null)
                msgs = ((InternalEObject) newWebService).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__WEB_SERVICE,
                        null,
                        msgs);
            msgs = basicSetWebService(newWebService, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__WEB_SERVICE,
                    newWebService, newWebService));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public RestServiceResource getRestService() {
        return restService;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetRestService(
            RestServiceResource newRestService, NotificationChain msgs) {
        RestServiceResource oldRestService = restService;
        restService = newRestService;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this,
                    Notification.SET,
                    XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__REST_SERVICE,
                    oldRestService, newRestService);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setRestService(RestServiceResource newRestService) {
        if (newRestService != restService) {
            NotificationChain msgs = null;
            if (restService != null)
                msgs = ((InternalEObject) restService).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__REST_SERVICE,
                        null,
                        msgs);
            if (newRestService != null)
                msgs = ((InternalEObject) newRestService).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__REST_SERVICE,
                        null,
                        msgs);
            msgs = basicSetRestService(newRestService, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__REST_SERVICE,
                    newRestService, newRestService));
    }

    /**
     * {@inheritDoc}
     * 
     * @generated NOT
     */
    @Override
    public EObject getSharedResource() {
        if (email != null) {
            return email;
        } else if (jdbc != null) {
            return jdbc;
        } else if (webService != null) {
            return webService;
        } else if (restService != null) {
            return restService;
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @generated NOT
     */
    @Override
    public void setSharedResource(EObject sharedResource) {
        if (sharedResource instanceof EmailResource) {
            setEmail((EmailResource) sharedResource);
            setJdbc(null);
            setWebService(null);
            setRestService(null);
        } else if (sharedResource instanceof JdbcResource) {
            setEmail(null);
            setJdbc((JdbcResource) sharedResource);
            setWebService(null);
            setRestService(null);
        } else if (sharedResource instanceof WsResource) {
            setEmail(null);
            setJdbc(null);
            setWebService((WsResource) sharedResource);
            setRestService(null);
        } else if (sharedResource instanceof RestServiceResource) {
            setEmail(null);
            setJdbc(null);
            setWebService(null);
            setRestService((RestServiceResource) sharedResource);
        } else if (sharedResource == null) {
            setEmail(null);
            setJdbc(null);
            setWebService(null);
            setRestService(null);
        } else {
            throw new IllegalArgumentException(
                    "SharedResource parameter is incorrect. (" + sharedResource //$NON-NLS-1$
                            + ")"); //$NON-NLS-1$
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__EMAIL:
            return basicSetEmail(null, msgs);
        case XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__JDBC:
            return basicSetJdbc(null, msgs);
        case XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__WEB_SERVICE:
            return basicSetWebService(null, msgs);
        case XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__REST_SERVICE:
            return basicSetRestService(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__EMAIL:
            return getEmail();
        case XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__JDBC:
            return getJdbc();
        case XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__WEB_SERVICE:
            return getWebService();
        case XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__REST_SERVICE:
            return getRestService();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__EMAIL:
            setEmail((EmailResource) newValue);
            return;
        case XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__JDBC:
            setJdbc((JdbcResource) newValue);
            return;
        case XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__WEB_SERVICE:
            setWebService((WsResource) newValue);
            return;
        case XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__REST_SERVICE:
            setRestService((RestServiceResource) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__EMAIL:
            setEmail((EmailResource) null);
            return;
        case XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__JDBC:
            setJdbc((JdbcResource) null);
            return;
        case XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__WEB_SERVICE:
            setWebService((WsResource) null);
            return;
        case XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__REST_SERVICE:
            setRestService((RestServiceResource) null);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__EMAIL:
            return email != null;
        case XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__JDBC:
            return jdbc != null;
        case XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__WEB_SERVICE:
            return webService != null;
        case XpdExtensionPackage.PARTICIPANT_SHARED_RESOURCE__REST_SERVICE:
            return restService != null;
        }
        return super.eIsSet(featureID);
    }

} // ParticipantSharedResourceImpl
