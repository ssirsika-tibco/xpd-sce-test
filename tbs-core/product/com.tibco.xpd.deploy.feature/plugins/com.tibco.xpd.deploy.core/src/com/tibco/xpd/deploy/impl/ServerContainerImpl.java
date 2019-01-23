/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.RepositoryType;
import com.tibco.xpd.deploy.Runtime;
import com.tibco.xpd.deploy.RuntimeType;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerContainer;
import com.tibco.xpd.deploy.ServerGroup;
import com.tibco.xpd.deploy.ServerGroupType;
import com.tibco.xpd.deploy.ServerType;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Server Container</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerContainerImpl#getServers <em>Servers</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerContainerImpl#getRuntimes <em>Runtimes</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerContainerImpl#getServerTypes <em>Server Types</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerContainerImpl#getRepositoryTypes <em>Repository Types</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerContainerImpl#getRuntimeTypes <em>Runtime Types</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerContainerImpl#getServerGroupTypes <em>Server Group Types</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerContainerImpl#getServerGroups <em>Server Groups</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ServerContainerImpl extends EObjectImpl implements ServerContainer {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * The cached value of the '{@link #getServers() <em>Servers</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getServers()
     * @generated
     * @ordered
     */
    protected EList<Server> servers;

    /**
     * The cached value of the '{@link #getRuntimes() <em>Runtimes</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getRuntimes()
     * @generated
     * @ordered
     */
    protected EList<com.tibco.xpd.deploy.Runtime> runtimes;

    /**
     * The cached value of the '{@link #getServerTypes() <em>Server Types</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getServerTypes()
     * @generated
     * @ordered
     */
    protected EList<ServerType> serverTypes;

    /**
     * The cached value of the '{@link #getRepositoryTypes()
     * <em>Repository Types</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getRepositoryTypes()
     * @generated
     * @ordered
     */
    protected EList<RepositoryType> repositoryTypes;

    /**
     * The cached value of the '{@link #getRuntimeTypes() <em>Runtime Types</em>}' containment reference list.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #getRuntimeTypes()
     * @generated
     * @ordered
     */
    protected EList<RuntimeType> runtimeTypes;

    /**
     * The cached value of the '{@link #getServerGroupTypes()
     * <em>Server Group Types</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getServerGroupTypes()
     * @generated
     * @ordered
     */
    protected EList<ServerGroupType> serverGroupTypes;

    /**
     * The cached value of the '{@link #getServerGroups() <em>Server Groups</em>}' containment reference list.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #getServerGroups()
     * @generated
     * @ordered
     */
    protected EList<ServerGroup> serverGroups;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected ServerContainerImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DeployPackage.Literals.SERVER_CONTAINER;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<Server> getServers() {
        if (servers == null) {
            servers = new EObjectContainmentEList<Server>(Server.class, this,
                    DeployPackage.SERVER_CONTAINER__SERVERS);
        }
        return servers;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<com.tibco.xpd.deploy.Runtime> getRuntimes() {
        if (runtimes == null) {
            runtimes = new EObjectContainmentEList<com.tibco.xpd.deploy.Runtime>(
                    com.tibco.xpd.deploy.Runtime.class, this,
                    DeployPackage.SERVER_CONTAINER__RUNTIMES);
        }
        return runtimes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<ServerType> getServerTypes() {
        if (serverTypes == null) {
            serverTypes = new EObjectContainmentEList<ServerType>(
                    ServerType.class, this,
                    DeployPackage.SERVER_CONTAINER__SERVER_TYPES);
        }
        return serverTypes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<RepositoryType> getRepositoryTypes() {
        if (repositoryTypes == null) {
            repositoryTypes = new EObjectContainmentEList<RepositoryType>(
                    RepositoryType.class, this,
                    DeployPackage.SERVER_CONTAINER__REPOSITORY_TYPES);
        }
        return repositoryTypes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<RuntimeType> getRuntimeTypes() {
        if (runtimeTypes == null) {
            runtimeTypes = new EObjectContainmentEList<RuntimeType>(
                    RuntimeType.class, this,
                    DeployPackage.SERVER_CONTAINER__RUNTIME_TYPES);
        }
        return runtimeTypes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<ServerGroupType> getServerGroupTypes() {
        if (serverGroupTypes == null) {
            serverGroupTypes = new EObjectContainmentEList<ServerGroupType>(
                    ServerGroupType.class, this,
                    DeployPackage.SERVER_CONTAINER__SERVER_GROUP_TYPES);
        }
        return serverGroupTypes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<ServerGroup> getServerGroups() {
        if (serverGroups == null) {
            serverGroups = new EObjectContainmentEList<ServerGroup>(
                    ServerGroup.class, this,
                    DeployPackage.SERVER_CONTAINER__SERVER_GROUPS);
        }
        return serverGroups;
    }

    /**
     * <!-- begin-user-doc --> Returns repository type identified by the
     * provided id. <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public RepositoryType getRepositoryTypeById(String repositoryTypeId) {
        List repositoryTypes = getRepositoryTypes();
        for (Iterator iter = repositoryTypes.iterator(); iter.hasNext();) {
            RepositoryType repositoryType = (RepositoryType) iter.next();
            if (repositoryType.getId().equals(repositoryTypeId)) {
                return repositoryType;
            }
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> Returns repository type identified by the
     * provided id. <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public ServerType getServerTypeById(String serverTypeId) {
        List serverTypes = getServerTypes();
        for (Iterator iter = serverTypes.iterator(); iter.hasNext();) {
            ServerType serverType = (ServerType) iter.next();
            if (serverType.getId().equals(serverTypeId)) {
                return serverType;
            }
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> Returns repository type identified by the
     * provided id. <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public RuntimeType getRuntimeTypeById(String runtimeTypeId) {
        List runtimeTypes = getRuntimeTypes();
        for (Iterator iter = runtimeTypes.iterator(); iter.hasNext();) {
            RuntimeType runtimeType = (RuntimeType) iter.next();
            if (runtimeType.getId().equals(runtimeTypeId)) {
                return runtimeType;
            }
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> Returns default name which can be used for a new
     * runtime.
     * 
     * @param prefix
     *            name prefix.
     * @return string which will be unique and can be used as a new runtime
     *         name. <!-- end-user-doc -->
     * @generated NOT
     */
    public String getDefaultNewRuntimeName(String prefix) {
        if (isValidNewRuntimeName(prefix)) {
            return prefix.trim();
        }
        for (int i = 1; i < Integer.MAX_VALUE - 1; i++) {
            String name = prefix + i;
            if (isValidNewRuntimeName(name)) {
                return name;
            }
        }
        return "";
    }

    /**
     * <!-- begin-user-doc -->Returns default name which can be used for a new
     * server.
     * 
     * @param prefix
     *            name prefix.
     * @return string which will be unique and can be used as a new server name.
     *         <!-- end-user-doc -->
     * @generated NOT
     */
    public String getDefaultNewServerName(String prefix) {
        if (isValidNewServerName(prefix)) {
            return prefix.trim();
        }
        for (int i = 1; i < Integer.MAX_VALUE - 1; i++) {
            String name = prefix + i;
            if (isValidNewServerName(name)) {
                return name;
            }
        }
        return "";
    }

    /**
     * <!-- begin-user-doc -->Validates if the provided string can be the new
     * server name. <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public boolean isValidNewRuntimeName(String name) {
        if (name == null) {
            return false;
        }
        for (Iterator iter = getRuntimes().iterator(); iter.hasNext();) {
            com.tibco.xpd.deploy.Runtime runtime = (Runtime) iter.next();
            if (name.equals(runtime.getName())) {
                return false;
            }
        }
        return true;
    }

    /**
     * <!-- begin-user-doc --> Validates if the provided string can be the new
     * server name. <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public boolean isValidNewServerName(String name) {
        if (name == null) {
            return false;
        }
        for (Iterator iter = getServers().iterator(); iter.hasNext();) {
            Server server = (Server) iter.next();
            if (name.equals(server.getName())) {
                return false;
            }
        }
        return true;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public ServerGroupType getServerGroupTypeById(String serverGroupTypeId) {
        List serverGroupTypes = getServerGroupTypes();
        for (Iterator iter = serverGroupTypes.iterator(); iter.hasNext();) {
            ServerGroupType serverGroupType = (ServerGroupType) iter.next();
            if (serverGroupType.getId().equals(serverGroupTypeId)) {
                return serverGroupType;
            }
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case DeployPackage.SERVER_CONTAINER__SERVERS:
            return ((InternalEList<?>) getServers())
                    .basicRemove(otherEnd, msgs);
        case DeployPackage.SERVER_CONTAINER__RUNTIMES:
            return ((InternalEList<?>) getRuntimes()).basicRemove(otherEnd,
                    msgs);
        case DeployPackage.SERVER_CONTAINER__SERVER_TYPES:
            return ((InternalEList<?>) getServerTypes()).basicRemove(otherEnd,
                    msgs);
        case DeployPackage.SERVER_CONTAINER__REPOSITORY_TYPES:
            return ((InternalEList<?>) getRepositoryTypes()).basicRemove(
                    otherEnd, msgs);
        case DeployPackage.SERVER_CONTAINER__RUNTIME_TYPES:
            return ((InternalEList<?>) getRuntimeTypes()).basicRemove(otherEnd,
                    msgs);
        case DeployPackage.SERVER_CONTAINER__SERVER_GROUP_TYPES:
            return ((InternalEList<?>) getServerGroupTypes()).basicRemove(
                    otherEnd, msgs);
        case DeployPackage.SERVER_CONTAINER__SERVER_GROUPS:
            return ((InternalEList<?>) getServerGroups()).basicRemove(otherEnd,
                    msgs);
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
        case DeployPackage.SERVER_CONTAINER__SERVERS:
            return getServers();
        case DeployPackage.SERVER_CONTAINER__RUNTIMES:
            return getRuntimes();
        case DeployPackage.SERVER_CONTAINER__SERVER_TYPES:
            return getServerTypes();
        case DeployPackage.SERVER_CONTAINER__REPOSITORY_TYPES:
            return getRepositoryTypes();
        case DeployPackage.SERVER_CONTAINER__RUNTIME_TYPES:
            return getRuntimeTypes();
        case DeployPackage.SERVER_CONTAINER__SERVER_GROUP_TYPES:
            return getServerGroupTypes();
        case DeployPackage.SERVER_CONTAINER__SERVER_GROUPS:
            return getServerGroups();
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
        case DeployPackage.SERVER_CONTAINER__SERVERS:
            getServers().clear();
            getServers().addAll((Collection<? extends Server>) newValue);
            return;
        case DeployPackage.SERVER_CONTAINER__RUNTIMES:
            getRuntimes().clear();
            getRuntimes()
                    .addAll(
                            (Collection<? extends com.tibco.xpd.deploy.Runtime>) newValue);
            return;
        case DeployPackage.SERVER_CONTAINER__SERVER_TYPES:
            getServerTypes().clear();
            getServerTypes()
                    .addAll((Collection<? extends ServerType>) newValue);
            return;
        case DeployPackage.SERVER_CONTAINER__REPOSITORY_TYPES:
            getRepositoryTypes().clear();
            getRepositoryTypes().addAll(
                    (Collection<? extends RepositoryType>) newValue);
            return;
        case DeployPackage.SERVER_CONTAINER__RUNTIME_TYPES:
            getRuntimeTypes().clear();
            getRuntimeTypes().addAll(
                    (Collection<? extends RuntimeType>) newValue);
            return;
        case DeployPackage.SERVER_CONTAINER__SERVER_GROUP_TYPES:
            getServerGroupTypes().clear();
            getServerGroupTypes().addAll(
                    (Collection<? extends ServerGroupType>) newValue);
            return;
        case DeployPackage.SERVER_CONTAINER__SERVER_GROUPS:
            getServerGroups().clear();
            getServerGroups().addAll(
                    (Collection<? extends ServerGroup>) newValue);
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
        case DeployPackage.SERVER_CONTAINER__SERVERS:
            getServers().clear();
            return;
        case DeployPackage.SERVER_CONTAINER__RUNTIMES:
            getRuntimes().clear();
            return;
        case DeployPackage.SERVER_CONTAINER__SERVER_TYPES:
            getServerTypes().clear();
            return;
        case DeployPackage.SERVER_CONTAINER__REPOSITORY_TYPES:
            getRepositoryTypes().clear();
            return;
        case DeployPackage.SERVER_CONTAINER__RUNTIME_TYPES:
            getRuntimeTypes().clear();
            return;
        case DeployPackage.SERVER_CONTAINER__SERVER_GROUP_TYPES:
            getServerGroupTypes().clear();
            return;
        case DeployPackage.SERVER_CONTAINER__SERVER_GROUPS:
            getServerGroups().clear();
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
        case DeployPackage.SERVER_CONTAINER__SERVERS:
            return servers != null && !servers.isEmpty();
        case DeployPackage.SERVER_CONTAINER__RUNTIMES:
            return runtimes != null && !runtimes.isEmpty();
        case DeployPackage.SERVER_CONTAINER__SERVER_TYPES:
            return serverTypes != null && !serverTypes.isEmpty();
        case DeployPackage.SERVER_CONTAINER__REPOSITORY_TYPES:
            return repositoryTypes != null && !repositoryTypes.isEmpty();
        case DeployPackage.SERVER_CONTAINER__RUNTIME_TYPES:
            return runtimeTypes != null && !runtimeTypes.isEmpty();
        case DeployPackage.SERVER_CONTAINER__SERVER_GROUP_TYPES:
            return serverGroupTypes != null && !serverGroupTypes.isEmpty();
        case DeployPackage.SERVER_CONTAINER__SERVER_GROUPS:
            return serverGroups != null && !serverGroups.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // ServerContainerImpl
