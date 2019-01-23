/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.workmodel.impl;

import com.tibco.n2.brm.workmodel.WorkModel;
import com.tibco.n2.brm.workmodel.WorkmodelPackage;

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

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Work Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.workmodel.impl.WorkModelImpl#getWorkModel <em>Work Model</em>}</li>
 *   <li>{@link com.tibco.n2.brm.workmodel.impl.WorkModelImpl#getOrgModelVersion <em>Org Model Version</em>}</li>
 *   <li>{@link com.tibco.n2.brm.workmodel.impl.WorkModelImpl#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkModelImpl extends EObjectImpl implements WorkModel {
    /**
     * The cached value of the '{@link #getWorkModel() <em>Work Model</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkModel()
     * @generated
     * @ordered
     */
    protected EList<com.tibco.n2.brm.api.WorkModel> workModel;

    /**
     * The default value of the '{@link #getOrgModelVersion() <em>Org Model Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOrgModelVersion()
     * @generated
     * @ordered
     */
    protected static final int ORG_MODEL_VERSION_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getOrgModelVersion() <em>Org Model Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOrgModelVersion()
     * @generated
     * @ordered
     */
    protected int orgModelVersion = ORG_MODEL_VERSION_EDEFAULT;

    /**
     * This is true if the Org Model Version attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean orgModelVersionESet;

    /**
     * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVersion()
     * @generated
     * @ordered
     */
    protected static final String VERSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVersion()
     * @generated
     * @ordered
     */
    protected String version = VERSION_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorkModelImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return WorkmodelPackage.Literals.WORK_MODEL;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<com.tibco.n2.brm.api.WorkModel> getWorkModel() {
        if (workModel == null) {
            workModel = new EObjectContainmentEList<com.tibco.n2.brm.api.WorkModel>(com.tibco.n2.brm.api.WorkModel.class, this, WorkmodelPackage.WORK_MODEL__WORK_MODEL);
        }
        return workModel;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getOrgModelVersion() {
        return orgModelVersion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOrgModelVersion(int newOrgModelVersion) {
        int oldOrgModelVersion = orgModelVersion;
        orgModelVersion = newOrgModelVersion;
        boolean oldOrgModelVersionESet = orgModelVersionESet;
        orgModelVersionESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WorkmodelPackage.WORK_MODEL__ORG_MODEL_VERSION, oldOrgModelVersion, orgModelVersion, !oldOrgModelVersionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetOrgModelVersion() {
        int oldOrgModelVersion = orgModelVersion;
        boolean oldOrgModelVersionESet = orgModelVersionESet;
        orgModelVersion = ORG_MODEL_VERSION_EDEFAULT;
        orgModelVersionESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, WorkmodelPackage.WORK_MODEL__ORG_MODEL_VERSION, oldOrgModelVersion, ORG_MODEL_VERSION_EDEFAULT, oldOrgModelVersionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetOrgModelVersion() {
        return orgModelVersionESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getVersion() {
        return version;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setVersion(String newVersion) {
        String oldVersion = version;
        version = newVersion;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WorkmodelPackage.WORK_MODEL__VERSION, oldVersion, version));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case WorkmodelPackage.WORK_MODEL__WORK_MODEL:
                return ((InternalEList<?>)getWorkModel()).basicRemove(otherEnd, msgs);
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
            case WorkmodelPackage.WORK_MODEL__WORK_MODEL:
                return getWorkModel();
            case WorkmodelPackage.WORK_MODEL__ORG_MODEL_VERSION:
                return getOrgModelVersion();
            case WorkmodelPackage.WORK_MODEL__VERSION:
                return getVersion();
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
            case WorkmodelPackage.WORK_MODEL__WORK_MODEL:
                getWorkModel().clear();
                getWorkModel().addAll((Collection<? extends com.tibco.n2.brm.api.WorkModel>)newValue);
                return;
            case WorkmodelPackage.WORK_MODEL__ORG_MODEL_VERSION:
                setOrgModelVersion((Integer)newValue);
                return;
            case WorkmodelPackage.WORK_MODEL__VERSION:
                setVersion((String)newValue);
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
            case WorkmodelPackage.WORK_MODEL__WORK_MODEL:
                getWorkModel().clear();
                return;
            case WorkmodelPackage.WORK_MODEL__ORG_MODEL_VERSION:
                unsetOrgModelVersion();
                return;
            case WorkmodelPackage.WORK_MODEL__VERSION:
                setVersion(VERSION_EDEFAULT);
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
            case WorkmodelPackage.WORK_MODEL__WORK_MODEL:
                return workModel != null && !workModel.isEmpty();
            case WorkmodelPackage.WORK_MODEL__ORG_MODEL_VERSION:
                return isSetOrgModelVersion();
            case WorkmodelPackage.WORK_MODEL__VERSION:
                return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
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
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (orgModelVersion: ");
        if (orgModelVersionESet) result.append(orgModelVersion); else result.append("<unset>");
        result.append(", version: ");
        result.append(version);
        result.append(')');
        return result.toString();
    }

} //WorkModelImpl
