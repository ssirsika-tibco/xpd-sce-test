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
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import com.tibco.xpd.deploy.ConfigParameterInfo;
import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.Loadable;
import com.tibco.xpd.deploy.RepositoryType;
import com.tibco.xpd.deploy.model.extension.RepositoryPublisher;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Repository Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.impl.RepositoryTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.RepositoryTypeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.RepositoryTypeImpl#isValid <em>Valid</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.RepositoryTypeImpl#getLastExtensionId <em>Last Extension Id</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.RepositoryTypeImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.RepositoryTypeImpl#getRepositoryParameterInfos <em>Repository Parameter Infos</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.RepositoryTypeImpl#getRepositoryPublisher <em>Repository Publisher</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RepositoryTypeImpl extends EObjectImpl implements RepositoryType {
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
     * The default value of the '{@link #getId() <em>Id</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getId()
     * @generated
     * @ordered
     */
    protected static final String ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getId() <em>Id</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getId()
     * @generated
     * @ordered
     */
    protected String id = ID_EDEFAULT;

    /**
     * The cached value of the '{@link #getRepositoryParameterInfos() <em>Repository Parameter Infos</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getRepositoryParameterInfos()
     * @generated
     * @ordered
     */
    protected EList<ConfigParameterInfo> repositoryParameterInfos;

    /**
     * The default value of the '{@link #getRepositoryPublisher() <em>Repository Publisher</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getRepositoryPublisher()
     * @generated
     * @ordered
     */
    protected static final RepositoryPublisher REPOSITORY_PUBLISHER_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getRepositoryPublisher() <em>Repository Publisher</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getRepositoryPublisher()
     * @generated
     * @ordered
     */
    protected RepositoryPublisher repositoryPublisher = REPOSITORY_PUBLISHER_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected RepositoryTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DeployPackage.Literals.REPOSITORY_TYPE;
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
                    DeployPackage.REPOSITORY_TYPE__NAME, oldName, name));
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
                    DeployPackage.REPOSITORY_TYPE__DESCRIPTION, oldDescription,
                    description));
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
                    DeployPackage.REPOSITORY_TYPE__VALID, oldValid, valid));
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
                    DeployPackage.REPOSITORY_TYPE__LAST_EXTENSION_ID,
                    oldLastExtensionId, lastExtensionId));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getId() {
        return id;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setId(String newId) {
        String oldId = id;
        id = newId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.REPOSITORY_TYPE__ID, oldId, id));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<ConfigParameterInfo> getRepositoryParameterInfos() {
        if (repositoryParameterInfos == null) {
            repositoryParameterInfos = new EObjectContainmentEList<ConfigParameterInfo>(
                    ConfigParameterInfo.class, this,
                    DeployPackage.REPOSITORY_TYPE__REPOSITORY_PARAMETER_INFOS);
        }
        return repositoryParameterInfos;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public RepositoryPublisher getRepositoryPublisher() {
        return repositoryPublisher;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setRepositoryPublisher(
            RepositoryPublisher newRepositoryPublisher) {
        RepositoryPublisher oldRepositoryPublisher = repositoryPublisher;
        repositoryPublisher = newRepositoryPublisher;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.REPOSITORY_TYPE__REPOSITORY_PUBLISHER,
                    oldRepositoryPublisher, repositoryPublisher));
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
        case DeployPackage.REPOSITORY_TYPE__REPOSITORY_PARAMETER_INFOS:
            return ((InternalEList<?>) getRepositoryParameterInfos())
                    .basicRemove(otherEnd, msgs);
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
        case DeployPackage.REPOSITORY_TYPE__NAME:
            return getName();
        case DeployPackage.REPOSITORY_TYPE__DESCRIPTION:
            return getDescription();
        case DeployPackage.REPOSITORY_TYPE__VALID:
            return isValid() ? Boolean.TRUE : Boolean.FALSE;
        case DeployPackage.REPOSITORY_TYPE__LAST_EXTENSION_ID:
            return getLastExtensionId();
        case DeployPackage.REPOSITORY_TYPE__ID:
            return getId();
        case DeployPackage.REPOSITORY_TYPE__REPOSITORY_PARAMETER_INFOS:
            return getRepositoryParameterInfos();
        case DeployPackage.REPOSITORY_TYPE__REPOSITORY_PUBLISHER:
            return getRepositoryPublisher();
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
        case DeployPackage.REPOSITORY_TYPE__NAME:
            setName((String) newValue);
            return;
        case DeployPackage.REPOSITORY_TYPE__DESCRIPTION:
            setDescription((String) newValue);
            return;
        case DeployPackage.REPOSITORY_TYPE__VALID:
            setValid(((Boolean) newValue).booleanValue());
            return;
        case DeployPackage.REPOSITORY_TYPE__LAST_EXTENSION_ID:
            setLastExtensionId((String) newValue);
            return;
        case DeployPackage.REPOSITORY_TYPE__ID:
            setId((String) newValue);
            return;
        case DeployPackage.REPOSITORY_TYPE__REPOSITORY_PARAMETER_INFOS:
            getRepositoryParameterInfos().clear();
            getRepositoryParameterInfos().addAll(
                    (Collection<? extends ConfigParameterInfo>) newValue);
            return;
        case DeployPackage.REPOSITORY_TYPE__REPOSITORY_PUBLISHER:
            setRepositoryPublisher((RepositoryPublisher) newValue);
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
        case DeployPackage.REPOSITORY_TYPE__NAME:
            setName(NAME_EDEFAULT);
            return;
        case DeployPackage.REPOSITORY_TYPE__DESCRIPTION:
            setDescription(DESCRIPTION_EDEFAULT);
            return;
        case DeployPackage.REPOSITORY_TYPE__VALID:
            setValid(VALID_EDEFAULT);
            return;
        case DeployPackage.REPOSITORY_TYPE__LAST_EXTENSION_ID:
            setLastExtensionId(LAST_EXTENSION_ID_EDEFAULT);
            return;
        case DeployPackage.REPOSITORY_TYPE__ID:
            setId(ID_EDEFAULT);
            return;
        case DeployPackage.REPOSITORY_TYPE__REPOSITORY_PARAMETER_INFOS:
            getRepositoryParameterInfos().clear();
            return;
        case DeployPackage.REPOSITORY_TYPE__REPOSITORY_PUBLISHER:
            setRepositoryPublisher(REPOSITORY_PUBLISHER_EDEFAULT);
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
        case DeployPackage.REPOSITORY_TYPE__NAME:
            return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
                    .equals(name);
        case DeployPackage.REPOSITORY_TYPE__DESCRIPTION:
            return DESCRIPTION_EDEFAULT == null ? description != null
                    : !DESCRIPTION_EDEFAULT.equals(description);
        case DeployPackage.REPOSITORY_TYPE__VALID:
            return valid != VALID_EDEFAULT;
        case DeployPackage.REPOSITORY_TYPE__LAST_EXTENSION_ID:
            return LAST_EXTENSION_ID_EDEFAULT == null ? lastExtensionId != null
                    : !LAST_EXTENSION_ID_EDEFAULT.equals(lastExtensionId);
        case DeployPackage.REPOSITORY_TYPE__ID:
            return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
        case DeployPackage.REPOSITORY_TYPE__REPOSITORY_PARAMETER_INFOS:
            return repositoryParameterInfos != null
                    && !repositoryParameterInfos.isEmpty();
        case DeployPackage.REPOSITORY_TYPE__REPOSITORY_PUBLISHER:
            return REPOSITORY_PUBLISHER_EDEFAULT == null ? repositoryPublisher != null
                    : !REPOSITORY_PUBLISHER_EDEFAULT
                            .equals(repositoryPublisher);
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
            case DeployPackage.REPOSITORY_TYPE__VALID:
                return DeployPackage.LOADABLE__VALID;
            case DeployPackage.REPOSITORY_TYPE__LAST_EXTENSION_ID:
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
                return DeployPackage.REPOSITORY_TYPE__VALID;
            case DeployPackage.LOADABLE__LAST_EXTENSION_ID:
                return DeployPackage.REPOSITORY_TYPE__LAST_EXTENSION_ID;
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
        result.append(", repositoryPublisher: ");
        result.append(repositoryPublisher);
        result.append(')');
        return result.toString();
    }

} // RepositoryTypeImpl
