/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.WorkModelMapping;
import com.tibco.n2.brm.api.WorkModelType;

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
 * An implementation of the model object '<em><b>Work Model Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkModelTypeImpl#getWorkModelMapping <em>Work Model Mapping</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkModelTypeImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkModelTypeImpl#getWorkTypeID <em>Work Type ID</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkModelTypeImpl extends EObjectImpl implements WorkModelType {
    /**
     * The cached value of the '{@link #getWorkModelMapping() <em>Work Model Mapping</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkModelMapping()
     * @generated
     * @ordered
     */
    protected EList<WorkModelMapping> workModelMapping;

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
     * The default value of the '{@link #getWorkTypeID() <em>Work Type ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkTypeID()
     * @generated
     * @ordered
     */
    protected static final String WORK_TYPE_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getWorkTypeID() <em>Work Type ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkTypeID()
     * @generated
     * @ordered
     */
    protected String workTypeID = WORK_TYPE_ID_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorkModelTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.WORK_MODEL_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<WorkModelMapping> getWorkModelMapping() {
        if (workModelMapping == null) {
            workModelMapping = new EObjectContainmentEList<WorkModelMapping>(WorkModelMapping.class, this, N2BRMPackage.WORK_MODEL_TYPE__WORK_MODEL_MAPPING);
        }
        return workModelMapping;
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
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL_TYPE__VERSION, oldVersion, version));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getWorkTypeID() {
        return workTypeID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkTypeID(String newWorkTypeID) {
        String oldWorkTypeID = workTypeID;
        workTypeID = newWorkTypeID;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL_TYPE__WORK_TYPE_ID, oldWorkTypeID, workTypeID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.WORK_MODEL_TYPE__WORK_MODEL_MAPPING:
                return ((InternalEList<?>)getWorkModelMapping()).basicRemove(otherEnd, msgs);
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
            case N2BRMPackage.WORK_MODEL_TYPE__WORK_MODEL_MAPPING:
                return getWorkModelMapping();
            case N2BRMPackage.WORK_MODEL_TYPE__VERSION:
                return getVersion();
            case N2BRMPackage.WORK_MODEL_TYPE__WORK_TYPE_ID:
                return getWorkTypeID();
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
            case N2BRMPackage.WORK_MODEL_TYPE__WORK_MODEL_MAPPING:
                getWorkModelMapping().clear();
                getWorkModelMapping().addAll((Collection<? extends WorkModelMapping>)newValue);
                return;
            case N2BRMPackage.WORK_MODEL_TYPE__VERSION:
                setVersion((String)newValue);
                return;
            case N2BRMPackage.WORK_MODEL_TYPE__WORK_TYPE_ID:
                setWorkTypeID((String)newValue);
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
            case N2BRMPackage.WORK_MODEL_TYPE__WORK_MODEL_MAPPING:
                getWorkModelMapping().clear();
                return;
            case N2BRMPackage.WORK_MODEL_TYPE__VERSION:
                setVersion(VERSION_EDEFAULT);
                return;
            case N2BRMPackage.WORK_MODEL_TYPE__WORK_TYPE_ID:
                setWorkTypeID(WORK_TYPE_ID_EDEFAULT);
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
            case N2BRMPackage.WORK_MODEL_TYPE__WORK_MODEL_MAPPING:
                return workModelMapping != null && !workModelMapping.isEmpty();
            case N2BRMPackage.WORK_MODEL_TYPE__VERSION:
                return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
            case N2BRMPackage.WORK_MODEL_TYPE__WORK_TYPE_ID:
                return WORK_TYPE_ID_EDEFAULT == null ? workTypeID != null : !WORK_TYPE_ID_EDEFAULT.equals(workTypeID);
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
        result.append(" (version: ");
        result.append(version);
        result.append(", workTypeID: ");
        result.append(workTypeID);
        result.append(')');
        return result.toString();
    }

} //WorkModelTypeImpl
