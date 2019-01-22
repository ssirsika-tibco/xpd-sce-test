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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.NamedElement;
import com.tibco.xpd.deploy.Repository;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerConfig;
import com.tibco.xpd.deploy.ServerElement;
import com.tibco.xpd.deploy.ServerGroup;
import com.tibco.xpd.deploy.ServerState;
import com.tibco.xpd.deploy.ServerType;
import com.tibco.xpd.deploy.UniqueIdElement;
import com.tibco.xpd.deploy.WorkspaceModule;
import com.tibco.xpd.deploy.model.extension.Connection;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Server</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerImpl#getServerType <em>Server Type</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerImpl#getServerConfig <em>Server Config</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerImpl#getServerState <em>Server State</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerImpl#getConnection <em>Connection</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerImpl#getRepository <em>Repository</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerImpl#getRuntime <em>Runtime</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerImpl#getServerElements <em>Server Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerImpl#getWorkspaceModules <em>Workspace Modules</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerImpl#getServerGroup <em>Server Group</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ServerImpl extends UniqueIdElementImpl implements Server {
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
     * The cached value of the '{@link #getServerType() <em>Server Type</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getServerType()
     * @generated
     * @ordered
     */
    protected ServerType serverType;

    /**
     * The cached value of the '{@link #getServerConfig() <em>Server Config</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getServerConfig()
     * @generated
     * @ordered
     */
    protected ServerConfig serverConfig;

    /**
     * The default value of the '{@link #getServerState() <em>Server State</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getServerState()
     * @generated
     * @ordered
     */
    protected static final ServerState SERVER_STATE_EDEFAULT = ServerState.DISCONNECTED_LITERAL;

    /**
     * The cached value of the '{@link #getServerState() <em>Server State</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getServerState()
     * @generated
     * @ordered
     */
    protected ServerState serverState = SERVER_STATE_EDEFAULT;

    /**
     * The default value of the '{@link #getConnection() <em>Connection</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getConnection()
     * @generated
     * @ordered
     */
    protected static final Connection CONNECTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getConnection() <em>Connection</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getConnection()
     * @generated
     * @ordered
     */
    protected Connection connection = CONNECTION_EDEFAULT;

    /**
     * The cached value of the '{@link #getRepository() <em>Repository</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getRepository()
     * @generated
     * @ordered
     */
    protected Repository repository;

    /**
     * The cached value of the '{@link #getRuntime() <em>Runtime</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getRuntime()
     * @generated
     * @ordered
     */
    protected com.tibco.xpd.deploy.Runtime runtime;

    /**
     * The cached value of the '{@link #getServerElements() <em>Server Elements</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getServerElements()
     * @generated
     * @ordered
     */
    protected EList<ServerElement> serverElements;

    /**
     * The cached value of the '{@link #getWorkspaceModules() <em>Workspace Modules</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getWorkspaceModules()
     * @generated
     * @ordered
     */
    protected EList<WorkspaceModule> workspaceModules;

    /**
     * The cached value of the '{@link #getServerGroup() <em>Server Group</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getServerGroup()
     * @generated
     * @ordered
     */
    protected ServerGroup serverGroup;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected ServerImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DeployPackage.Literals.SERVER;
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
                    DeployPackage.SERVER__NAME, oldName, name));
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
                    DeployPackage.SERVER__DESCRIPTION, oldDescription,
                    description));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ServerType getServerType() {
        if (serverType != null && serverType.eIsProxy()) {
            InternalEObject oldServerType = (InternalEObject) serverType;
            serverType = (ServerType) eResolveProxy(oldServerType);
            if (serverType != oldServerType) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            DeployPackage.SERVER__SERVER_TYPE, oldServerType,
                            serverType));
            }
        }
        return serverType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ServerType basicGetServerType() {
        return serverType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setServerType(ServerType newServerType) {
        ServerType oldServerType = serverType;
        serverType = newServerType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.SERVER__SERVER_TYPE, oldServerType,
                    serverType));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetServerConfig(ServerConfig newServerConfig,
            NotificationChain msgs) {
        ServerConfig oldServerConfig = serverConfig;
        serverConfig = newServerConfig;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this,
                    Notification.SET, DeployPackage.SERVER__SERVER_CONFIG,
                    oldServerConfig, newServerConfig);
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
    public void setServerConfig(ServerConfig newServerConfig) {
        if (newServerConfig != serverConfig) {
            NotificationChain msgs = null;
            if (serverConfig != null)
                msgs = ((InternalEObject) serverConfig).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - DeployPackage.SERVER__SERVER_CONFIG, null,
                        msgs);
            if (newServerConfig != null)
                msgs = ((InternalEObject) newServerConfig).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - DeployPackage.SERVER__SERVER_CONFIG, null,
                        msgs);
            msgs = basicSetServerConfig(newServerConfig, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.SERVER__SERVER_CONFIG, newServerConfig,
                    newServerConfig));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ServerState getServerState() {
        return serverState;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setServerState(ServerState newServerState) {
        ServerState oldServerState = serverState;
        serverState = newServerState == null ? SERVER_STATE_EDEFAULT
                : newServerState;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.SERVER__SERVER_STATE, oldServerState,
                    serverState));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated not
     */
    public Connection getConnection() {
        if (connection == null && serverType != null
                && serverType.getConnectionFactory() != null) {
            connection = serverType.getConnectionFactory().createConnection(
                    this);
        }
        return connection;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setConnection(Connection newConnection) {
        Connection oldConnection = connection;
        connection = newConnection;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.SERVER__CONNECTION, oldConnection, connection));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Repository getRepository() {
        return repository;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetRepository(Repository newRepository,
            NotificationChain msgs) {
        Repository oldRepository = repository;
        repository = newRepository;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this,
                    Notification.SET, DeployPackage.SERVER__REPOSITORY,
                    oldRepository, newRepository);
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
    public void setRepository(Repository newRepository) {
        if (newRepository != repository) {
            NotificationChain msgs = null;
            if (repository != null)
                msgs = ((InternalEObject) repository).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - DeployPackage.SERVER__REPOSITORY, null, msgs);
            if (newRepository != null)
                msgs = ((InternalEObject) newRepository).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - DeployPackage.SERVER__REPOSITORY, null, msgs);
            msgs = basicSetRepository(newRepository, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.SERVER__REPOSITORY, newRepository,
                    newRepository));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public com.tibco.xpd.deploy.Runtime getRuntime() {
        if (runtime != null && runtime.eIsProxy()) {
            InternalEObject oldRuntime = (InternalEObject) runtime;
            runtime = (com.tibco.xpd.deploy.Runtime) eResolveProxy(oldRuntime);
            if (runtime != oldRuntime) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            DeployPackage.SERVER__RUNTIME, oldRuntime, runtime));
            }
        }
        return runtime;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public com.tibco.xpd.deploy.Runtime basicGetRuntime() {
        return runtime;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setRuntime(com.tibco.xpd.deploy.Runtime newRuntime) {
        com.tibco.xpd.deploy.Runtime oldRuntime = runtime;
        runtime = newRuntime;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.SERVER__RUNTIME, oldRuntime, runtime));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<ServerElement> getServerElements() {
        if (serverElements == null) {
            serverElements = new EObjectContainmentEList<ServerElement>(
                    ServerElement.class, this,
                    DeployPackage.SERVER__SERVER_ELEMENTS);
        }
        return serverElements;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<WorkspaceModule> getWorkspaceModules() {
        if (workspaceModules == null) {
            workspaceModules = new EObjectContainmentEList<WorkspaceModule>(
                    WorkspaceModule.class, this,
                    DeployPackage.SERVER__WORKSPACE_MODULES);
        }
        return workspaceModules;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ServerGroup getServerGroup() {
        if (serverGroup != null && serverGroup.eIsProxy()) {
            InternalEObject oldServerGroup = (InternalEObject) serverGroup;
            serverGroup = (ServerGroup) eResolveProxy(oldServerGroup);
            if (serverGroup != oldServerGroup) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            DeployPackage.SERVER__SERVER_GROUP, oldServerGroup,
                            serverGroup));
            }
        }
        return serverGroup;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ServerGroup basicGetServerGroup() {
        return serverGroup;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetServerGroup(ServerGroup newServerGroup,
            NotificationChain msgs) {
        ServerGroup oldServerGroup = serverGroup;
        serverGroup = newServerGroup;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this,
                    Notification.SET, DeployPackage.SERVER__SERVER_GROUP,
                    oldServerGroup, newServerGroup);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setServerGroup(ServerGroup newServerGroup) {
        if (newServerGroup != serverGroup) {
            NotificationChain msgs = null;
            if (serverGroup != null)
                msgs = ((InternalEObject) serverGroup).eInverseRemove(this,
                        DeployPackage.SERVER_GROUP__MEMBERS, ServerGroup.class,
                        msgs);
            if (newServerGroup != null)
                msgs = ((InternalEObject) newServerGroup).eInverseAdd(this,
                        DeployPackage.SERVER_GROUP__MEMBERS, ServerGroup.class,
                        msgs);
            msgs = basicSetServerGroup(newServerGroup, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.SERVER__SERVER_GROUP, newServerGroup,
                    newServerGroup));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case DeployPackage.SERVER__SERVER_GROUP:
            if (serverGroup != null)
                msgs = ((InternalEObject) serverGroup).eInverseRemove(this,
                        DeployPackage.SERVER_GROUP__MEMBERS, ServerGroup.class,
                        msgs);
            return basicSetServerGroup((ServerGroup) otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case DeployPackage.SERVER__SERVER_CONFIG:
            return basicSetServerConfig(null, msgs);
        case DeployPackage.SERVER__REPOSITORY:
            return basicSetRepository(null, msgs);
        case DeployPackage.SERVER__SERVER_ELEMENTS:
            return ((InternalEList<?>) getServerElements()).basicRemove(
                    otherEnd, msgs);
        case DeployPackage.SERVER__WORKSPACE_MODULES:
            return ((InternalEList<?>) getWorkspaceModules()).basicRemove(
                    otherEnd, msgs);
        case DeployPackage.SERVER__SERVER_GROUP:
            return basicSetServerGroup(null, msgs);
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
        case DeployPackage.SERVER__NAME:
            return getName();
        case DeployPackage.SERVER__DESCRIPTION:
            return getDescription();
        case DeployPackage.SERVER__SERVER_TYPE:
            if (resolve)
                return getServerType();
            return basicGetServerType();
        case DeployPackage.SERVER__SERVER_CONFIG:
            return getServerConfig();
        case DeployPackage.SERVER__SERVER_STATE:
            return getServerState();
        case DeployPackage.SERVER__CONNECTION:
            return getConnection();
        case DeployPackage.SERVER__REPOSITORY:
            return getRepository();
        case DeployPackage.SERVER__RUNTIME:
            if (resolve)
                return getRuntime();
            return basicGetRuntime();
        case DeployPackage.SERVER__SERVER_ELEMENTS:
            return getServerElements();
        case DeployPackage.SERVER__WORKSPACE_MODULES:
            return getWorkspaceModules();
        case DeployPackage.SERVER__SERVER_GROUP:
            if (resolve)
                return getServerGroup();
            return basicGetServerGroup();
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
        case DeployPackage.SERVER__NAME:
            setName((String) newValue);
            return;
        case DeployPackage.SERVER__DESCRIPTION:
            setDescription((String) newValue);
            return;
        case DeployPackage.SERVER__SERVER_TYPE:
            setServerType((ServerType) newValue);
            return;
        case DeployPackage.SERVER__SERVER_CONFIG:
            setServerConfig((ServerConfig) newValue);
            return;
        case DeployPackage.SERVER__SERVER_STATE:
            setServerState((ServerState) newValue);
            return;
        case DeployPackage.SERVER__CONNECTION:
            setConnection((Connection) newValue);
            return;
        case DeployPackage.SERVER__REPOSITORY:
            setRepository((Repository) newValue);
            return;
        case DeployPackage.SERVER__RUNTIME:
            setRuntime((com.tibco.xpd.deploy.Runtime) newValue);
            return;
        case DeployPackage.SERVER__SERVER_ELEMENTS:
            getServerElements().clear();
            getServerElements().addAll(
                    (Collection<? extends ServerElement>) newValue);
            return;
        case DeployPackage.SERVER__WORKSPACE_MODULES:
            getWorkspaceModules().clear();
            getWorkspaceModules().addAll(
                    (Collection<? extends WorkspaceModule>) newValue);
            return;
        case DeployPackage.SERVER__SERVER_GROUP:
            setServerGroup((ServerGroup) newValue);
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
        case DeployPackage.SERVER__NAME:
            setName(NAME_EDEFAULT);
            return;
        case DeployPackage.SERVER__DESCRIPTION:
            setDescription(DESCRIPTION_EDEFAULT);
            return;
        case DeployPackage.SERVER__SERVER_TYPE:
            setServerType((ServerType) null);
            return;
        case DeployPackage.SERVER__SERVER_CONFIG:
            setServerConfig((ServerConfig) null);
            return;
        case DeployPackage.SERVER__SERVER_STATE:
            setServerState(SERVER_STATE_EDEFAULT);
            return;
        case DeployPackage.SERVER__CONNECTION:
            setConnection(CONNECTION_EDEFAULT);
            return;
        case DeployPackage.SERVER__REPOSITORY:
            setRepository((Repository) null);
            return;
        case DeployPackage.SERVER__RUNTIME:
            setRuntime((com.tibco.xpd.deploy.Runtime) null);
            return;
        case DeployPackage.SERVER__SERVER_ELEMENTS:
            getServerElements().clear();
            return;
        case DeployPackage.SERVER__WORKSPACE_MODULES:
            getWorkspaceModules().clear();
            return;
        case DeployPackage.SERVER__SERVER_GROUP:
            setServerGroup((ServerGroup) null);
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
        case DeployPackage.SERVER__NAME:
            return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
                    .equals(name);
        case DeployPackage.SERVER__DESCRIPTION:
            return DESCRIPTION_EDEFAULT == null ? description != null
                    : !DESCRIPTION_EDEFAULT.equals(description);
        case DeployPackage.SERVER__SERVER_TYPE:
            return serverType != null;
        case DeployPackage.SERVER__SERVER_CONFIG:
            return serverConfig != null;
        case DeployPackage.SERVER__SERVER_STATE:
            return serverState != SERVER_STATE_EDEFAULT;
        case DeployPackage.SERVER__CONNECTION:
            return CONNECTION_EDEFAULT == null ? connection != null
                    : !CONNECTION_EDEFAULT.equals(connection);
        case DeployPackage.SERVER__REPOSITORY:
            return repository != null;
        case DeployPackage.SERVER__RUNTIME:
            return runtime != null;
        case DeployPackage.SERVER__SERVER_ELEMENTS:
            return serverElements != null && !serverElements.isEmpty();
        case DeployPackage.SERVER__WORKSPACE_MODULES:
            return workspaceModules != null && !workspaceModules.isEmpty();
        case DeployPackage.SERVER__SERVER_GROUP:
            return serverGroup != null;
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
        if (baseClass == NamedElement.class) {
            switch (derivedFeatureID) {
            case DeployPackage.SERVER__NAME:
                return DeployPackage.NAMED_ELEMENT__NAME;
            case DeployPackage.SERVER__DESCRIPTION:
                return DeployPackage.NAMED_ELEMENT__DESCRIPTION;
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
        if (baseClass == NamedElement.class) {
            switch (baseFeatureID) {
            case DeployPackage.NAMED_ELEMENT__NAME:
                return DeployPackage.SERVER__NAME;
            case DeployPackage.NAMED_ELEMENT__DESCRIPTION:
                return DeployPackage.SERVER__DESCRIPTION;
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
        result.append(", serverState: ");
        result.append(serverState);
        result.append(", connection: ");
        result.append(connection);
        result.append(')');
        return result.toString();
    }

} // ServerImpl
