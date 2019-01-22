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
import com.tibco.xpd.deploy.RuntimeConfig;
import com.tibco.xpd.deploy.RuntimeType;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Runtime</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.impl.RuntimeImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.RuntimeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.RuntimeImpl#getRuntimeType <em>Runtime Type</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.RuntimeImpl#getRuntimeConfig <em>Runtime Config</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RuntimeImpl extends EObjectImpl implements
        com.tibco.xpd.deploy.Runtime {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected static final String DESCRIPTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected String description = DESCRIPTION_EDEFAULT;

    /**
     * The cached value of the '{@link #getRuntimeType() <em>Runtime Type</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getRuntimeType()
     * @generated
     * @ordered
     */
    protected RuntimeType runtimeType;

    /**
     * The cached value of the '{@link #getRuntimeConfig() <em>Runtime Config</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getRuntimeConfig()
     * @generated
     * @ordered
     */
    protected RuntimeConfig runtimeConfig;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected RuntimeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DeployPackage.Literals.RUNTIME;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.RUNTIME__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getDescription() {
        return description;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setDescription(String newDescription) {
        String oldDescription = description;
        description = newDescription;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.RUNTIME__DESCRIPTION, oldDescription,
                    description));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public RuntimeType getRuntimeType() {
        if (runtimeType != null && runtimeType.eIsProxy()) {
            InternalEObject oldRuntimeType = (InternalEObject) runtimeType;
            runtimeType = (RuntimeType) eResolveProxy(oldRuntimeType);
            if (runtimeType != oldRuntimeType) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            DeployPackage.RUNTIME__RUNTIME_TYPE,
                            oldRuntimeType, runtimeType));
            }
        }
        return runtimeType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public RuntimeType basicGetRuntimeType() {
        return runtimeType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setRuntimeType(RuntimeType newRuntimeType) {
        RuntimeType oldRuntimeType = runtimeType;
        runtimeType = newRuntimeType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.RUNTIME__RUNTIME_TYPE, oldRuntimeType,
                    runtimeType));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public RuntimeConfig getRuntimeConfig() {
        return runtimeConfig;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetRuntimeConfig(
            RuntimeConfig newRuntimeConfig, NotificationChain msgs) {
        RuntimeConfig oldRuntimeConfig = runtimeConfig;
        runtimeConfig = newRuntimeConfig;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this,
                    Notification.SET, DeployPackage.RUNTIME__RUNTIME_CONFIG,
                    oldRuntimeConfig, newRuntimeConfig);
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
    public void setRuntimeConfig(RuntimeConfig newRuntimeConfig) {
        if (newRuntimeConfig != runtimeConfig) {
            NotificationChain msgs = null;
            if (runtimeConfig != null)
                msgs = ((InternalEObject) runtimeConfig).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - DeployPackage.RUNTIME__RUNTIME_CONFIG, null,
                        msgs);
            if (newRuntimeConfig != null)
                msgs = ((InternalEObject) newRuntimeConfig).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - DeployPackage.RUNTIME__RUNTIME_CONFIG, null,
                        msgs);
            msgs = basicSetRuntimeConfig(newRuntimeConfig, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.RUNTIME__RUNTIME_CONFIG, newRuntimeConfig,
                    newRuntimeConfig));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case DeployPackage.RUNTIME__RUNTIME_CONFIG:
            return basicSetRuntimeConfig(null, msgs);
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
        case DeployPackage.RUNTIME__NAME:
            return getName();
        case DeployPackage.RUNTIME__DESCRIPTION:
            return getDescription();
        case DeployPackage.RUNTIME__RUNTIME_TYPE:
            if (resolve)
                return getRuntimeType();
            return basicGetRuntimeType();
        case DeployPackage.RUNTIME__RUNTIME_CONFIG:
            return getRuntimeConfig();
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
        case DeployPackage.RUNTIME__NAME:
            setName((String) newValue);
            return;
        case DeployPackage.RUNTIME__DESCRIPTION:
            setDescription((String) newValue);
            return;
        case DeployPackage.RUNTIME__RUNTIME_TYPE:
            setRuntimeType((RuntimeType) newValue);
            return;
        case DeployPackage.RUNTIME__RUNTIME_CONFIG:
            setRuntimeConfig((RuntimeConfig) newValue);
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
        case DeployPackage.RUNTIME__NAME:
            setName(NAME_EDEFAULT);
            return;
        case DeployPackage.RUNTIME__DESCRIPTION:
            setDescription(DESCRIPTION_EDEFAULT);
            return;
        case DeployPackage.RUNTIME__RUNTIME_TYPE:
            setRuntimeType((RuntimeType) null);
            return;
        case DeployPackage.RUNTIME__RUNTIME_CONFIG:
            setRuntimeConfig((RuntimeConfig) null);
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
        case DeployPackage.RUNTIME__NAME:
            return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
                    .equals(name);
        case DeployPackage.RUNTIME__DESCRIPTION:
            return DESCRIPTION_EDEFAULT == null ? description != null
                    : !DESCRIPTION_EDEFAULT.equals(description);
        case DeployPackage.RUNTIME__RUNTIME_TYPE:
            return runtimeType != null;
        case DeployPackage.RUNTIME__RUNTIME_CONFIG:
            return runtimeConfig != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (name: ");
        result.append(name);
        result.append(", description: ");
        result.append(description);
        result.append(')');
        return result.toString();
    }

} // RuntimeImpl
