/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.deploy.impl;

import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerGroup;
import com.tibco.xpd.deploy.ServerGroupType;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Server Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerGroupImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerGroupImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerGroupImpl#getMembers <em>Members</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerGroupImpl#getServerGroupType <em>Server Group Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ServerGroupImpl extends EObjectImpl implements ServerGroup {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected static final String DESCRIPTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected String description = DESCRIPTION_EDEFAULT;

    /**
     * The cached value of the '{@link #getMembers() <em>Members</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMembers()
     * @generated
     * @ordered
     */
    protected EList<Server> members;

    /**
     * The cached value of the '{@link #getServerGroupType() <em>Server Group Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getServerGroupType()
     * @generated
     * @ordered
     */
    protected ServerGroupType serverGroupType;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ServerGroupImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DeployPackage.Literals.SERVER_GROUP;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.SERVER_GROUP__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDescription() {
        return description;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDescription(String newDescription) {
        String oldDescription = description;
        description = newDescription;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.SERVER_GROUP__DESCRIPTION, oldDescription,
                    description));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Server> getMembers() {
        if (members == null) {
            members = new EObjectWithInverseResolvingEList<Server>(
                    Server.class, this, DeployPackage.SERVER_GROUP__MEMBERS,
                    DeployPackage.SERVER__SERVER_GROUP);
        }
        return members;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ServerGroupType getServerGroupType() {
        if (serverGroupType != null && serverGroupType.eIsProxy()) {
            InternalEObject oldServerGroupType = (InternalEObject) serverGroupType;
            serverGroupType = (ServerGroupType) eResolveProxy(oldServerGroupType);
            if (serverGroupType != oldServerGroupType) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            DeployPackage.SERVER_GROUP__SERVER_GROUP_TYPE,
                            oldServerGroupType, serverGroupType));
            }
        }
        return serverGroupType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ServerGroupType basicGetServerGroupType() {
        return serverGroupType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setServerGroupType(ServerGroupType newServerGroupType) {
        ServerGroupType oldServerGroupType = serverGroupType;
        serverGroupType = newServerGroupType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.SERVER_GROUP__SERVER_GROUP_TYPE,
                    oldServerGroupType, serverGroupType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case DeployPackage.SERVER_GROUP__MEMBERS:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getMembers())
                    .basicAdd(otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case DeployPackage.SERVER_GROUP__MEMBERS:
            return ((InternalEList<?>) getMembers())
                    .basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case DeployPackage.SERVER_GROUP__NAME:
            return getName();
        case DeployPackage.SERVER_GROUP__DESCRIPTION:
            return getDescription();
        case DeployPackage.SERVER_GROUP__MEMBERS:
            return getMembers();
        case DeployPackage.SERVER_GROUP__SERVER_GROUP_TYPE:
            if (resolve)
                return getServerGroupType();
            return basicGetServerGroupType();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case DeployPackage.SERVER_GROUP__NAME:
            setName((String) newValue);
            return;
        case DeployPackage.SERVER_GROUP__DESCRIPTION:
            setDescription((String) newValue);
            return;
        case DeployPackage.SERVER_GROUP__MEMBERS:
            getMembers().clear();
            getMembers().addAll((Collection<? extends Server>) newValue);
            return;
        case DeployPackage.SERVER_GROUP__SERVER_GROUP_TYPE:
            setServerGroupType((ServerGroupType) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case DeployPackage.SERVER_GROUP__NAME:
            setName(NAME_EDEFAULT);
            return;
        case DeployPackage.SERVER_GROUP__DESCRIPTION:
            setDescription(DESCRIPTION_EDEFAULT);
            return;
        case DeployPackage.SERVER_GROUP__MEMBERS:
            getMembers().clear();
            return;
        case DeployPackage.SERVER_GROUP__SERVER_GROUP_TYPE:
            setServerGroupType((ServerGroupType) null);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case DeployPackage.SERVER_GROUP__NAME:
            return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
                    .equals(name);
        case DeployPackage.SERVER_GROUP__DESCRIPTION:
            return DESCRIPTION_EDEFAULT == null ? description != null
                    : !DESCRIPTION_EDEFAULT.equals(description);
        case DeployPackage.SERVER_GROUP__MEMBERS:
            return members != null && !members.isEmpty();
        case DeployPackage.SERVER_GROUP__SERVER_GROUP_TYPE:
            return serverGroupType != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
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

} //ServerGroupImpl
