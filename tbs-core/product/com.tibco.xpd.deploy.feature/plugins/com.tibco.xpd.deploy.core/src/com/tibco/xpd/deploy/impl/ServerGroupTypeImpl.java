/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.deploy.impl;

import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.Loadable;
import com.tibco.xpd.deploy.ServerGroupType;
import com.tibco.xpd.deploy.ServerType;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Server Group Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerGroupTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerGroupTypeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerGroupTypeImpl#isValid <em>Valid</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerGroupTypeImpl#getLastExtensionId <em>Last Extension Id</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerGroupTypeImpl#getServerTypes <em>Server Types</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerGroupTypeImpl#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ServerGroupTypeImpl extends EObjectImpl implements ServerGroupType {
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
     * The cached value of the '{@link #getServerTypes() <em>Server Types</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getServerTypes()
     * @generated
     * @ordered
     */
    protected EList<ServerType> serverTypes;

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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ServerGroupTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DeployPackage.Literals.SERVER_GROUP_TYPE;
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
                    DeployPackage.SERVER_GROUP_TYPE__NAME, oldName, name));
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
                    DeployPackage.SERVER_GROUP_TYPE__DESCRIPTION,
                    oldDescription, description));
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
                    DeployPackage.SERVER_GROUP_TYPE__VALID, oldValid, valid));
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
                    DeployPackage.SERVER_GROUP_TYPE__LAST_EXTENSION_ID,
                    oldLastExtensionId, lastExtensionId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ServerType> getServerTypes() {
        if (serverTypes == null) {
            serverTypes = new EObjectResolvingEList<ServerType>(
                    ServerType.class, this,
                    DeployPackage.SERVER_GROUP_TYPE__SERVER_TYPES);
        }
        return serverTypes;
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
                    DeployPackage.SERVER_GROUP_TYPE__ID, oldId, id));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case DeployPackage.SERVER_GROUP_TYPE__NAME:
            return getName();
        case DeployPackage.SERVER_GROUP_TYPE__DESCRIPTION:
            return getDescription();
        case DeployPackage.SERVER_GROUP_TYPE__VALID:
            return isValid() ? Boolean.TRUE : Boolean.FALSE;
        case DeployPackage.SERVER_GROUP_TYPE__LAST_EXTENSION_ID:
            return getLastExtensionId();
        case DeployPackage.SERVER_GROUP_TYPE__SERVER_TYPES:
            return getServerTypes();
        case DeployPackage.SERVER_GROUP_TYPE__ID:
            return getId();
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
        case DeployPackage.SERVER_GROUP_TYPE__NAME:
            setName((String) newValue);
            return;
        case DeployPackage.SERVER_GROUP_TYPE__DESCRIPTION:
            setDescription((String) newValue);
            return;
        case DeployPackage.SERVER_GROUP_TYPE__VALID:
            setValid(((Boolean) newValue).booleanValue());
            return;
        case DeployPackage.SERVER_GROUP_TYPE__LAST_EXTENSION_ID:
            setLastExtensionId((String) newValue);
            return;
        case DeployPackage.SERVER_GROUP_TYPE__SERVER_TYPES:
            getServerTypes().clear();
            getServerTypes()
                    .addAll((Collection<? extends ServerType>) newValue);
            return;
        case DeployPackage.SERVER_GROUP_TYPE__ID:
            setId((String) newValue);
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
        case DeployPackage.SERVER_GROUP_TYPE__NAME:
            setName(NAME_EDEFAULT);
            return;
        case DeployPackage.SERVER_GROUP_TYPE__DESCRIPTION:
            setDescription(DESCRIPTION_EDEFAULT);
            return;
        case DeployPackage.SERVER_GROUP_TYPE__VALID:
            setValid(VALID_EDEFAULT);
            return;
        case DeployPackage.SERVER_GROUP_TYPE__LAST_EXTENSION_ID:
            setLastExtensionId(LAST_EXTENSION_ID_EDEFAULT);
            return;
        case DeployPackage.SERVER_GROUP_TYPE__SERVER_TYPES:
            getServerTypes().clear();
            return;
        case DeployPackage.SERVER_GROUP_TYPE__ID:
            setId(ID_EDEFAULT);
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
        case DeployPackage.SERVER_GROUP_TYPE__NAME:
            return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
                    .equals(name);
        case DeployPackage.SERVER_GROUP_TYPE__DESCRIPTION:
            return DESCRIPTION_EDEFAULT == null ? description != null
                    : !DESCRIPTION_EDEFAULT.equals(description);
        case DeployPackage.SERVER_GROUP_TYPE__VALID:
            return valid != VALID_EDEFAULT;
        case DeployPackage.SERVER_GROUP_TYPE__LAST_EXTENSION_ID:
            return LAST_EXTENSION_ID_EDEFAULT == null ? lastExtensionId != null
                    : !LAST_EXTENSION_ID_EDEFAULT.equals(lastExtensionId);
        case DeployPackage.SERVER_GROUP_TYPE__SERVER_TYPES:
            return serverTypes != null && !serverTypes.isEmpty();
        case DeployPackage.SERVER_GROUP_TYPE__ID:
            return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
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
            case DeployPackage.SERVER_GROUP_TYPE__VALID:
                return DeployPackage.LOADABLE__VALID;
            case DeployPackage.SERVER_GROUP_TYPE__LAST_EXTENSION_ID:
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
                return DeployPackage.SERVER_GROUP_TYPE__VALID;
            case DeployPackage.LOADABLE__LAST_EXTENSION_ID:
                return DeployPackage.SERVER_GROUP_TYPE__LAST_EXTENSION_ID;
            default:
                return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
        result.append(", valid: ");
        result.append(valid);
        result.append(", lastExtensionId: ");
        result.append(lastExtensionId);
        result.append(", id: ");
        result.append(id);
        result.append(')');
        return result.toString();
    }

} //ServerGroupTypeImpl
