/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy.impl;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.deploy.ConfigParameter;
import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.ModuleContainer;
import com.tibco.xpd.deploy.ServerElement;
import com.tibco.xpd.deploy.ServerElementState;
import com.tibco.xpd.deploy.ServerElementType;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Module Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.impl.ModuleContainerImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ModuleContainerImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ModuleContainerImpl#getServerElementType <em>Server Element Type</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ModuleContainerImpl#getServerElementState <em>Server Element State</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ModuleContainerImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ModuleContainerImpl#getChildren <em>Children</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ModuleContainerImpl extends EObjectImpl implements ModuleContainer {
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
     * The cached value of the '{@link #getServerElementType() <em>Server Element Type</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getServerElementType()
     * @generated
     * @ordered
     */
    protected ServerElementType serverElementType;

    /**
     * The cached value of the '{@link #getServerElementState() <em>Server Element State</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getServerElementState()
     * @generated
     * @ordered
     */
    protected ServerElementState serverElementState;

    /**
     * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getParameters()
     * @generated
     * @ordered
     */
    protected EList<ConfigParameter> parameters;

    /**
     * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getChildren()
     * @generated
     * @ordered
     */
    protected EList<ServerElement> children;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected ModuleContainerImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DeployPackage.Literals.MODULE_CONTAINER;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ServerElementType getServerElementType() {
        if (serverElementType != null && serverElementType.eIsProxy()) {
            InternalEObject oldServerElementType = (InternalEObject) serverElementType;
            serverElementType = (ServerElementType) eResolveProxy(oldServerElementType);
            if (serverElementType != oldServerElementType) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(
                            this,
                            Notification.RESOLVE,
                            DeployPackage.MODULE_CONTAINER__SERVER_ELEMENT_TYPE,
                            oldServerElementType, serverElementType));
            }
        }
        return serverElementType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ServerElementType basicGetServerElementType() {
        return serverElementType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setServerElementType(ServerElementType newServerElementType) {
        ServerElementType oldServerElementType = serverElementType;
        serverElementType = newServerElementType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.MODULE_CONTAINER__SERVER_ELEMENT_TYPE,
                    oldServerElementType, serverElementType));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ServerElementState getServerElementState() {
        if (serverElementState != null && serverElementState.eIsProxy()) {
            InternalEObject oldServerElementState = (InternalEObject) serverElementState;
            serverElementState = (ServerElementState) eResolveProxy(oldServerElementState);
            if (serverElementState != oldServerElementState) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(
                            this,
                            Notification.RESOLVE,
                            DeployPackage.MODULE_CONTAINER__SERVER_ELEMENT_STATE,
                            oldServerElementState, serverElementState));
            }
        }
        return serverElementState;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ServerElementState basicGetServerElementState() {
        return serverElementState;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setServerElementState(ServerElementState newServerElementState) {
        ServerElementState oldServerElementState = serverElementState;
        serverElementState = newServerElementState;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.MODULE_CONTAINER__SERVER_ELEMENT_STATE,
                    oldServerElementState, serverElementState));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<ConfigParameter> getParameters() {
        if (parameters == null) {
            parameters = new EObjectContainmentEList<ConfigParameter>(
                    ConfigParameter.class, this,
                    DeployPackage.MODULE_CONTAINER__PARAMETERS);
        }
        return parameters;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<ServerElement> getChildren() {
        if (children == null) {
            children = new EObjectContainmentEList<ServerElement>(
                    ServerElement.class, this,
                    DeployPackage.MODULE_CONTAINER__CHILDREN);
        }
        return children;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @SuppressWarnings("unchecked")
    public ConfigParameter getConfigParameter(String key) {
        if (key != null) {
            List<ConfigParameter> params = (List<ConfigParameter>) getParameters();
            for (ConfigParameter parameter : params) {
                if (key.equals(parameter.getKey())) {
                    return parameter;
                }
            }
        }
        return null;
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
                    DeployPackage.MODULE_CONTAINER__NAME, oldName, name));
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
                    DeployPackage.MODULE_CONTAINER__DESCRIPTION,
                    oldDescription, description));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case DeployPackage.MODULE_CONTAINER__PARAMETERS:
            return ((InternalEList<?>) getParameters()).basicRemove(otherEnd,
                    msgs);
        case DeployPackage.MODULE_CONTAINER__CHILDREN:
            return ((InternalEList<?>) getChildren()).basicRemove(otherEnd,
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
        case DeployPackage.MODULE_CONTAINER__NAME:
            return getName();
        case DeployPackage.MODULE_CONTAINER__DESCRIPTION:
            return getDescription();
        case DeployPackage.MODULE_CONTAINER__SERVER_ELEMENT_TYPE:
            if (resolve)
                return getServerElementType();
            return basicGetServerElementType();
        case DeployPackage.MODULE_CONTAINER__SERVER_ELEMENT_STATE:
            if (resolve)
                return getServerElementState();
            return basicGetServerElementState();
        case DeployPackage.MODULE_CONTAINER__PARAMETERS:
            return getParameters();
        case DeployPackage.MODULE_CONTAINER__CHILDREN:
            return getChildren();
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
        case DeployPackage.MODULE_CONTAINER__NAME:
            setName((String) newValue);
            return;
        case DeployPackage.MODULE_CONTAINER__DESCRIPTION:
            setDescription((String) newValue);
            return;
        case DeployPackage.MODULE_CONTAINER__SERVER_ELEMENT_TYPE:
            setServerElementType((ServerElementType) newValue);
            return;
        case DeployPackage.MODULE_CONTAINER__SERVER_ELEMENT_STATE:
            setServerElementState((ServerElementState) newValue);
            return;
        case DeployPackage.MODULE_CONTAINER__PARAMETERS:
            getParameters().clear();
            getParameters().addAll(
                    (Collection<? extends ConfigParameter>) newValue);
            return;
        case DeployPackage.MODULE_CONTAINER__CHILDREN:
            getChildren().clear();
            getChildren()
                    .addAll((Collection<? extends ServerElement>) newValue);
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
        case DeployPackage.MODULE_CONTAINER__NAME:
            setName(NAME_EDEFAULT);
            return;
        case DeployPackage.MODULE_CONTAINER__DESCRIPTION:
            setDescription(DESCRIPTION_EDEFAULT);
            return;
        case DeployPackage.MODULE_CONTAINER__SERVER_ELEMENT_TYPE:
            setServerElementType((ServerElementType) null);
            return;
        case DeployPackage.MODULE_CONTAINER__SERVER_ELEMENT_STATE:
            setServerElementState((ServerElementState) null);
            return;
        case DeployPackage.MODULE_CONTAINER__PARAMETERS:
            getParameters().clear();
            return;
        case DeployPackage.MODULE_CONTAINER__CHILDREN:
            getChildren().clear();
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
        case DeployPackage.MODULE_CONTAINER__NAME:
            return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
                    .equals(name);
        case DeployPackage.MODULE_CONTAINER__DESCRIPTION:
            return DESCRIPTION_EDEFAULT == null ? description != null
                    : !DESCRIPTION_EDEFAULT.equals(description);
        case DeployPackage.MODULE_CONTAINER__SERVER_ELEMENT_TYPE:
            return serverElementType != null;
        case DeployPackage.MODULE_CONTAINER__SERVER_ELEMENT_STATE:
            return serverElementState != null;
        case DeployPackage.MODULE_CONTAINER__PARAMETERS:
            return parameters != null && !parameters.isEmpty();
        case DeployPackage.MODULE_CONTAINER__CHILDREN:
            return children != null && !children.isEmpty();
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

} // ModuleContainerImpl
