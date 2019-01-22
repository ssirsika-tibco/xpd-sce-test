/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.Loadable;
import com.tibco.xpd.deploy.RepositoryType;
import com.tibco.xpd.deploy.RuntimeType;
import com.tibco.xpd.deploy.ServerConfigInfo;
import com.tibco.xpd.deploy.ServerType;
import com.tibco.xpd.deploy.model.extension.ConnectionFactory;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Server Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerTypeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerTypeImpl#isValid <em>Valid</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerTypeImpl#getLastExtensionId <em>Last Extension Id</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerTypeImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerTypeImpl#getServerConfigInfo <em>Server Config Info</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerTypeImpl#getConnectionFactory <em>Connection Factory</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerTypeImpl#getRuntimeType <em>Runtime Type</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerTypeImpl#getSupportedRepositoryTypes <em>Supported Repository Types</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerTypeImpl#isSuppressConectivity <em>Suppress Conectivity</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ServerTypeImpl extends EObjectImpl implements ServerType {
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
     * The default value of the '{@link #isValid() <em>Valid</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isValid()
     * @generated
     * @ordered
     */
    protected static final boolean VALID_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isValid() <em>Valid</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isValid()
     * @generated
     * @ordered
     */
    protected boolean valid = VALID_EDEFAULT;

    /**
     * The default value of the '{@link #getLastExtensionId() <em>Last Extension Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLastExtensionId()
     * @generated
     * @ordered
     */
    protected static final String LAST_EXTENSION_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getLastExtensionId() <em>Last Extension Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLastExtensionId()
     * @generated
     * @ordered
     */
    protected String lastExtensionId = LAST_EXTENSION_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected static final String ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected String id = ID_EDEFAULT;

    /**
     * The cached value of the '{@link #getServerConfigInfo() <em>Server Config Info</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getServerConfigInfo()
     * @generated
     * @ordered
     */
    protected ServerConfigInfo serverConfigInfo;

    /**
     * The default value of the '{@link #getConnectionFactory() <em>Connection Factory</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getConnectionFactory()
     * @generated
     * @ordered
     */
    protected static final ConnectionFactory CONNECTION_FACTORY_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getConnectionFactory() <em>Connection Factory</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getConnectionFactory()
     * @generated
     * @ordered
     */
    protected ConnectionFactory connectionFactory = CONNECTION_FACTORY_EDEFAULT;

    /**
     * The cached value of the '{@link #getRuntimeType() <em>Runtime Type</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getRuntimeType()
     * @generated
     * @ordered
     */
    protected RuntimeType runtimeType;

    /**
     * The cached value of the '{@link #getSupportedRepositoryTypes() <em>Supported Repository Types</em>}' reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getSupportedRepositoryTypes()
     * @generated
     * @ordered
     */
    protected EList<RepositoryType> supportedRepositoryTypes;

    /**
     * The default value of the '{@link #isSuppressConectivity() <em>Suppress Conectivity</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSuppressConectivity()
     * @generated
     * @ordered
     */
    protected static final boolean SUPPRESS_CONECTIVITY_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isSuppressConectivity() <em>Suppress Conectivity</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSuppressConectivity()
     * @generated
     * @ordered
     */
    protected boolean suppressConectivity = SUPPRESS_CONECTIVITY_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected ServerTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DeployPackage.Literals.SERVER_TYPE;
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
                    DeployPackage.SERVER_TYPE__NAME, oldName, name));
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
                    DeployPackage.SERVER_TYPE__DESCRIPTION, oldDescription,
                    description));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getId() {
        return id;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setId(String newId) {
        String oldId = id;
        id = newId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.SERVER_TYPE__ID, oldId, id));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ServerConfigInfo getServerConfigInfo() {
        return serverConfigInfo;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetServerConfigInfo(
            ServerConfigInfo newServerConfigInfo, NotificationChain msgs) {
        ServerConfigInfo oldServerConfigInfo = serverConfigInfo;
        serverConfigInfo = newServerConfigInfo;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this,
                    Notification.SET,
                    DeployPackage.SERVER_TYPE__SERVER_CONFIG_INFO,
                    oldServerConfigInfo, newServerConfigInfo);
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
    public void setServerConfigInfo(ServerConfigInfo newServerConfigInfo) {
        if (newServerConfigInfo != serverConfigInfo) {
            NotificationChain msgs = null;
            if (serverConfigInfo != null)
                msgs = ((InternalEObject) serverConfigInfo)
                        .eInverseRemove(
                                this,
                                EOPPOSITE_FEATURE_BASE
                                        - DeployPackage.SERVER_TYPE__SERVER_CONFIG_INFO,
                                null, msgs);
            if (newServerConfigInfo != null)
                msgs = ((InternalEObject) newServerConfigInfo)
                        .eInverseAdd(
                                this,
                                EOPPOSITE_FEATURE_BASE
                                        - DeployPackage.SERVER_TYPE__SERVER_CONFIG_INFO,
                                null, msgs);
            msgs = basicSetServerConfigInfo(newServerConfigInfo, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.SERVER_TYPE__SERVER_CONFIG_INFO,
                    newServerConfigInfo, newServerConfigInfo));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setConnectionFactory(ConnectionFactory newConnectionFactory) {
        ConnectionFactory oldConnectionFactory = connectionFactory;
        connectionFactory = newConnectionFactory;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.SERVER_TYPE__CONNECTION_FACTORY,
                    oldConnectionFactory, connectionFactory));
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
                            DeployPackage.SERVER_TYPE__RUNTIME_TYPE,
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
                    DeployPackage.SERVER_TYPE__RUNTIME_TYPE, oldRuntimeType,
                    runtimeType));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<RepositoryType> getSupportedRepositoryTypes() {
        if (supportedRepositoryTypes == null) {
            supportedRepositoryTypes = new EObjectResolvingEList<RepositoryType>(
                    RepositoryType.class, this,
                    DeployPackage.SERVER_TYPE__SUPPORTED_REPOSITORY_TYPES);
        }
        return supportedRepositoryTypes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSuppressConectivity() {
        return suppressConectivity;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSuppressConectivity(boolean newSuppressConectivity) {
        boolean oldSuppressConectivity = suppressConectivity;
        suppressConectivity = newSuppressConectivity;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.SERVER_TYPE__SUPPRESS_CONECTIVITY,
                    oldSuppressConectivity, suppressConectivity));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setValid(boolean newValid) {
        boolean oldValid = valid;
        valid = newValid;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.SERVER_TYPE__VALID, oldValid, valid));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getLastExtensionId() {
        return lastExtensionId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLastExtensionId(String newLastExtensionId) {
        String oldLastExtensionId = lastExtensionId;
        lastExtensionId = newLastExtensionId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.SERVER_TYPE__LAST_EXTENSION_ID,
                    oldLastExtensionId, lastExtensionId));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case DeployPackage.SERVER_TYPE__SERVER_CONFIG_INFO:
            return basicSetServerConfigInfo(null, msgs);
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
        case DeployPackage.SERVER_TYPE__NAME:
            return getName();
        case DeployPackage.SERVER_TYPE__DESCRIPTION:
            return getDescription();
        case DeployPackage.SERVER_TYPE__VALID:
            return isValid() ? Boolean.TRUE : Boolean.FALSE;
        case DeployPackage.SERVER_TYPE__LAST_EXTENSION_ID:
            return getLastExtensionId();
        case DeployPackage.SERVER_TYPE__ID:
            return getId();
        case DeployPackage.SERVER_TYPE__SERVER_CONFIG_INFO:
            return getServerConfigInfo();
        case DeployPackage.SERVER_TYPE__CONNECTION_FACTORY:
            return getConnectionFactory();
        case DeployPackage.SERVER_TYPE__RUNTIME_TYPE:
            if (resolve)
                return getRuntimeType();
            return basicGetRuntimeType();
        case DeployPackage.SERVER_TYPE__SUPPORTED_REPOSITORY_TYPES:
            return getSupportedRepositoryTypes();
        case DeployPackage.SERVER_TYPE__SUPPRESS_CONECTIVITY:
            return isSuppressConectivity() ? Boolean.TRUE : Boolean.FALSE;
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case DeployPackage.SERVER_TYPE__NAME:
            setName((String) newValue);
            return;
        case DeployPackage.SERVER_TYPE__DESCRIPTION:
            setDescription((String) newValue);
            return;
        case DeployPackage.SERVER_TYPE__VALID:
            setValid(((Boolean) newValue).booleanValue());
            return;
        case DeployPackage.SERVER_TYPE__LAST_EXTENSION_ID:
            setLastExtensionId((String) newValue);
            return;
        case DeployPackage.SERVER_TYPE__ID:
            setId((String) newValue);
            return;
        case DeployPackage.SERVER_TYPE__SERVER_CONFIG_INFO:
            setServerConfigInfo((ServerConfigInfo) newValue);
            return;
        case DeployPackage.SERVER_TYPE__CONNECTION_FACTORY:
            setConnectionFactory((ConnectionFactory) newValue);
            return;
        case DeployPackage.SERVER_TYPE__RUNTIME_TYPE:
            setRuntimeType((RuntimeType) newValue);
            return;
        case DeployPackage.SERVER_TYPE__SUPPORTED_REPOSITORY_TYPES:
            getSupportedRepositoryTypes().clear();
            getSupportedRepositoryTypes().addAll(
                    (Collection<? extends RepositoryType>) newValue);
            return;
        case DeployPackage.SERVER_TYPE__SUPPRESS_CONECTIVITY:
            setSuppressConectivity(((Boolean) newValue).booleanValue());
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
        case DeployPackage.SERVER_TYPE__NAME:
            setName(NAME_EDEFAULT);
            return;
        case DeployPackage.SERVER_TYPE__DESCRIPTION:
            setDescription(DESCRIPTION_EDEFAULT);
            return;
        case DeployPackage.SERVER_TYPE__VALID:
            setValid(VALID_EDEFAULT);
            return;
        case DeployPackage.SERVER_TYPE__LAST_EXTENSION_ID:
            setLastExtensionId(LAST_EXTENSION_ID_EDEFAULT);
            return;
        case DeployPackage.SERVER_TYPE__ID:
            setId(ID_EDEFAULT);
            return;
        case DeployPackage.SERVER_TYPE__SERVER_CONFIG_INFO:
            setServerConfigInfo((ServerConfigInfo) null);
            return;
        case DeployPackage.SERVER_TYPE__CONNECTION_FACTORY:
            setConnectionFactory(CONNECTION_FACTORY_EDEFAULT);
            return;
        case DeployPackage.SERVER_TYPE__RUNTIME_TYPE:
            setRuntimeType((RuntimeType) null);
            return;
        case DeployPackage.SERVER_TYPE__SUPPORTED_REPOSITORY_TYPES:
            getSupportedRepositoryTypes().clear();
            return;
        case DeployPackage.SERVER_TYPE__SUPPRESS_CONECTIVITY:
            setSuppressConectivity(SUPPRESS_CONECTIVITY_EDEFAULT);
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
        case DeployPackage.SERVER_TYPE__NAME:
            return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
                    .equals(name);
        case DeployPackage.SERVER_TYPE__DESCRIPTION:
            return DESCRIPTION_EDEFAULT == null ? description != null
                    : !DESCRIPTION_EDEFAULT.equals(description);
        case DeployPackage.SERVER_TYPE__VALID:
            return valid != VALID_EDEFAULT;
        case DeployPackage.SERVER_TYPE__LAST_EXTENSION_ID:
            return LAST_EXTENSION_ID_EDEFAULT == null ? lastExtensionId != null
                    : !LAST_EXTENSION_ID_EDEFAULT.equals(lastExtensionId);
        case DeployPackage.SERVER_TYPE__ID:
            return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
        case DeployPackage.SERVER_TYPE__SERVER_CONFIG_INFO:
            return serverConfigInfo != null;
        case DeployPackage.SERVER_TYPE__CONNECTION_FACTORY:
            return CONNECTION_FACTORY_EDEFAULT == null ? connectionFactory != null
                    : !CONNECTION_FACTORY_EDEFAULT.equals(connectionFactory);
        case DeployPackage.SERVER_TYPE__RUNTIME_TYPE:
            return runtimeType != null;
        case DeployPackage.SERVER_TYPE__SUPPORTED_REPOSITORY_TYPES:
            return supportedRepositoryTypes != null
                    && !supportedRepositoryTypes.isEmpty();
        case DeployPackage.SERVER_TYPE__SUPPRESS_CONECTIVITY:
            return suppressConectivity != SUPPRESS_CONECTIVITY_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == Loadable.class) {
            switch (derivedFeatureID) {
            case DeployPackage.SERVER_TYPE__VALID:
                return DeployPackage.LOADABLE__VALID;
            case DeployPackage.SERVER_TYPE__LAST_EXTENSION_ID:
                return DeployPackage.LOADABLE__LAST_EXTENSION_ID;
            default:
                return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
        if (baseClass == Loadable.class) {
            switch (baseFeatureID) {
            case DeployPackage.LOADABLE__VALID:
                return DeployPackage.SERVER_TYPE__VALID;
            case DeployPackage.LOADABLE__LAST_EXTENSION_ID:
                return DeployPackage.SERVER_TYPE__LAST_EXTENSION_ID;
            default:
                return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
        result.append(", valid: ");
        result.append(valid);
        result.append(", lastExtensionId: ");
        result.append(lastExtensionId);
        result.append(", id: ");
        result.append(id);
        result.append(", connectionFactory: ");
        result.append(connectionFactory);
        result.append(", suppressConectivity: ");
        result.append(suppressConectivity);
        result.append(')');
        return result.toString();
    }

} // ServerTypeImpl
