/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.WorkspaceModule;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Workspace Module</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.impl.WorkspaceModuleImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.WorkspaceModuleImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.WorkspaceModuleImpl#getWorkspaceSrcPath <em>Workspace Src Path</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.WorkspaceModuleImpl#getModuleKind <em>Module Kind</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.WorkspaceModuleImpl#isDirty <em>Dirty</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkspaceModuleImpl extends EObjectImpl implements WorkspaceModule {
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
     * The default value of the '{@link #getWorkspaceSrcPath() <em>Workspace Src Path</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getWorkspaceSrcPath()
     * @generated
     * @ordered
     */
    protected static final String WORKSPACE_SRC_PATH_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getWorkspaceSrcPath() <em>Workspace Src Path</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getWorkspaceSrcPath()
     * @generated
     * @ordered
     */
    protected String workspaceSrcPath = WORKSPACE_SRC_PATH_EDEFAULT;

    /**
     * The default value of the '{@link #getModuleKind() <em>Module Kind</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getModuleKind()
     * @generated
     * @ordered
     */
    protected static final String MODULE_KIND_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getModuleKind() <em>Module Kind</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getModuleKind()
     * @generated
     * @ordered
     */
    protected String moduleKind = MODULE_KIND_EDEFAULT;

    /**
     * The default value of the '{@link #isDirty() <em>Dirty</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #isDirty()
     * @generated
     * @ordered
     */
    protected static final boolean DIRTY_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isDirty() <em>Dirty</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #isDirty()
     * @generated
     * @ordered
     */
    protected boolean dirty = DIRTY_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected WorkspaceModuleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DeployPackage.Literals.WORKSPACE_MODULE;
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
                    DeployPackage.WORKSPACE_MODULE__NAME, oldName, name));
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
                    DeployPackage.WORKSPACE_MODULE__DESCRIPTION,
                    oldDescription, description));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getWorkspaceSrcPath() {
        return workspaceSrcPath;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setWorkspaceSrcPath(String newWorkspaceSrcPath) {
        String oldWorkspaceSrcPath = workspaceSrcPath;
        workspaceSrcPath = newWorkspaceSrcPath;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.WORKSPACE_MODULE__WORKSPACE_SRC_PATH,
                    oldWorkspaceSrcPath, workspaceSrcPath));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getModuleKind() {
        return moduleKind;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setModuleKind(String newModuleKind) {
        String oldModuleKind = moduleKind;
        moduleKind = newModuleKind;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.WORKSPACE_MODULE__MODULE_KIND, oldModuleKind,
                    moduleKind));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setDirty(boolean newDirty) {
        boolean oldDirty = dirty;
        dirty = newDirty;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.WORKSPACE_MODULE__DIRTY, oldDirty, dirty));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case DeployPackage.WORKSPACE_MODULE__NAME:
            return getName();
        case DeployPackage.WORKSPACE_MODULE__DESCRIPTION:
            return getDescription();
        case DeployPackage.WORKSPACE_MODULE__WORKSPACE_SRC_PATH:
            return getWorkspaceSrcPath();
        case DeployPackage.WORKSPACE_MODULE__MODULE_KIND:
            return getModuleKind();
        case DeployPackage.WORKSPACE_MODULE__DIRTY:
            return isDirty() ? Boolean.TRUE : Boolean.FALSE;
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
        case DeployPackage.WORKSPACE_MODULE__NAME:
            setName((String) newValue);
            return;
        case DeployPackage.WORKSPACE_MODULE__DESCRIPTION:
            setDescription((String) newValue);
            return;
        case DeployPackage.WORKSPACE_MODULE__WORKSPACE_SRC_PATH:
            setWorkspaceSrcPath((String) newValue);
            return;
        case DeployPackage.WORKSPACE_MODULE__MODULE_KIND:
            setModuleKind((String) newValue);
            return;
        case DeployPackage.WORKSPACE_MODULE__DIRTY:
            setDirty(((Boolean) newValue).booleanValue());
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
        case DeployPackage.WORKSPACE_MODULE__NAME:
            setName(NAME_EDEFAULT);
            return;
        case DeployPackage.WORKSPACE_MODULE__DESCRIPTION:
            setDescription(DESCRIPTION_EDEFAULT);
            return;
        case DeployPackage.WORKSPACE_MODULE__WORKSPACE_SRC_PATH:
            setWorkspaceSrcPath(WORKSPACE_SRC_PATH_EDEFAULT);
            return;
        case DeployPackage.WORKSPACE_MODULE__MODULE_KIND:
            setModuleKind(MODULE_KIND_EDEFAULT);
            return;
        case DeployPackage.WORKSPACE_MODULE__DIRTY:
            setDirty(DIRTY_EDEFAULT);
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
        case DeployPackage.WORKSPACE_MODULE__NAME:
            return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
                    .equals(name);
        case DeployPackage.WORKSPACE_MODULE__DESCRIPTION:
            return DESCRIPTION_EDEFAULT == null ? description != null
                    : !DESCRIPTION_EDEFAULT.equals(description);
        case DeployPackage.WORKSPACE_MODULE__WORKSPACE_SRC_PATH:
            return WORKSPACE_SRC_PATH_EDEFAULT == null ? workspaceSrcPath != null
                    : !WORKSPACE_SRC_PATH_EDEFAULT.equals(workspaceSrcPath);
        case DeployPackage.WORKSPACE_MODULE__MODULE_KIND:
            return MODULE_KIND_EDEFAULT == null ? moduleKind != null
                    : !MODULE_KIND_EDEFAULT.equals(moduleKind);
        case DeployPackage.WORKSPACE_MODULE__DIRTY:
            return dirty != DIRTY_EDEFAULT;
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
        result.append(", workspaceSrcPath: ");
        result.append(workspaceSrcPath);
        result.append(", moduleKind: ");
        result.append(moduleKind);
        result.append(", dirty: ");
        result.append(dirty);
        result.append(')');
        return result.toString();
    }

} // WorkspaceModuleImpl
