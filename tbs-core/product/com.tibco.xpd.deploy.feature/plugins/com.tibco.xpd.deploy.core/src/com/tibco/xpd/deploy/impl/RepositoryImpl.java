/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.Repository;
import com.tibco.xpd.deploy.RepositoryConfig;
import com.tibco.xpd.deploy.RepositoryType;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Repository</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.impl.RepositoryImpl#getRepositoryType <em>Repository Type</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.RepositoryImpl#getRepositoryConfig <em>Repository Config</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RepositoryImpl extends EObjectImpl implements Repository {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * The cached value of the '{@link #getRepositoryType() <em>Repository Type</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getRepositoryType()
     * @generated
     * @ordered
     */
    protected RepositoryType repositoryType;

    /**
     * The cached value of the '{@link #getRepositoryConfig() <em>Repository Config</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getRepositoryConfig()
     * @generated
     * @ordered
     */
    protected RepositoryConfig repositoryConfig;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected RepositoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DeployPackage.Literals.REPOSITORY;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public RepositoryType getRepositoryType() {
        if (repositoryType != null && repositoryType.eIsProxy()) {
            InternalEObject oldRepositoryType = (InternalEObject) repositoryType;
            repositoryType = (RepositoryType) eResolveProxy(oldRepositoryType);
            if (repositoryType != oldRepositoryType) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            DeployPackage.REPOSITORY__REPOSITORY_TYPE,
                            oldRepositoryType, repositoryType));
            }
        }
        return repositoryType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public RepositoryType basicGetRepositoryType() {
        return repositoryType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setRepositoryType(RepositoryType newRepositoryType) {
        RepositoryType oldRepositoryType = repositoryType;
        repositoryType = newRepositoryType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.REPOSITORY__REPOSITORY_TYPE,
                    oldRepositoryType, repositoryType));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public RepositoryConfig getRepositoryConfig() {
        return repositoryConfig;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetRepositoryConfig(
            RepositoryConfig newRepositoryConfig, NotificationChain msgs) {
        RepositoryConfig oldRepositoryConfig = repositoryConfig;
        repositoryConfig = newRepositoryConfig;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this,
                    Notification.SET,
                    DeployPackage.REPOSITORY__REPOSITORY_CONFIG,
                    oldRepositoryConfig, newRepositoryConfig);
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
    public void setRepositoryConfig(RepositoryConfig newRepositoryConfig) {
        if (newRepositoryConfig != repositoryConfig) {
            NotificationChain msgs = null;
            if (repositoryConfig != null)
                msgs = ((InternalEObject) repositoryConfig).eInverseRemove(
                        this, EOPPOSITE_FEATURE_BASE
                                - DeployPackage.REPOSITORY__REPOSITORY_CONFIG,
                        null, msgs);
            if (newRepositoryConfig != null)
                msgs = ((InternalEObject) newRepositoryConfig).eInverseAdd(
                        this, EOPPOSITE_FEATURE_BASE
                                - DeployPackage.REPOSITORY__REPOSITORY_CONFIG,
                        null, msgs);
            msgs = basicSetRepositoryConfig(newRepositoryConfig, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.REPOSITORY__REPOSITORY_CONFIG,
                    newRepositoryConfig, newRepositoryConfig));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case DeployPackage.REPOSITORY__REPOSITORY_CONFIG:
            return basicSetRepositoryConfig(null, msgs);
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
        case DeployPackage.REPOSITORY__REPOSITORY_TYPE:
            if (resolve)
                return getRepositoryType();
            return basicGetRepositoryType();
        case DeployPackage.REPOSITORY__REPOSITORY_CONFIG:
            return getRepositoryConfig();
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
        case DeployPackage.REPOSITORY__REPOSITORY_TYPE:
            setRepositoryType((RepositoryType) newValue);
            return;
        case DeployPackage.REPOSITORY__REPOSITORY_CONFIG:
            setRepositoryConfig((RepositoryConfig) newValue);
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
        case DeployPackage.REPOSITORY__REPOSITORY_TYPE:
            setRepositoryType((RepositoryType) null);
            return;
        case DeployPackage.REPOSITORY__REPOSITORY_CONFIG:
            setRepositoryConfig((RepositoryConfig) null);
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
        case DeployPackage.REPOSITORY__REPOSITORY_TYPE:
            return repositoryType != null;
        case DeployPackage.REPOSITORY__REPOSITORY_CONFIG:
            return repositoryConfig != null;
        }
        return super.eIsSet(featureID);
    }

} // RepositoryImpl
