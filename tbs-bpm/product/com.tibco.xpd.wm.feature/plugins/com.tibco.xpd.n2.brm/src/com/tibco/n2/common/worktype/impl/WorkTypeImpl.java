/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.worktype.impl;

import com.tibco.n2.common.worktype.WorkType;
import com.tibco.n2.common.worktype.WorktypePackage;

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
 * An implementation of the model object '<em><b>Work Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.common.worktype.impl.WorkTypeImpl#getWorkType <em>Work Type</em>}</li>
 *   <li>{@link com.tibco.n2.common.worktype.impl.WorkTypeImpl#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkTypeImpl extends EObjectImpl implements WorkType {
    /**
     * The cached value of the '{@link #getWorkType() <em>Work Type</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkType()
     * @generated
     * @ordered
     */
    protected EList<com.tibco.n2.common.datamodel.WorkType> workType;

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
    protected WorkTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return WorktypePackage.Literals.WORK_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<com.tibco.n2.common.datamodel.WorkType> getWorkType() {
        if (workType == null) {
            workType = new EObjectContainmentEList<com.tibco.n2.common.datamodel.WorkType>(com.tibco.n2.common.datamodel.WorkType.class, this, WorktypePackage.WORK_TYPE__WORK_TYPE);
        }
        return workType;
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
            eNotify(new ENotificationImpl(this, Notification.SET, WorktypePackage.WORK_TYPE__VERSION, oldVersion, version));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case WorktypePackage.WORK_TYPE__WORK_TYPE:
                return ((InternalEList<?>)getWorkType()).basicRemove(otherEnd, msgs);
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
            case WorktypePackage.WORK_TYPE__WORK_TYPE:
                return getWorkType();
            case WorktypePackage.WORK_TYPE__VERSION:
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
            case WorktypePackage.WORK_TYPE__WORK_TYPE:
                getWorkType().clear();
                getWorkType().addAll((Collection<? extends com.tibco.n2.common.datamodel.WorkType>)newValue);
                return;
            case WorktypePackage.WORK_TYPE__VERSION:
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
            case WorktypePackage.WORK_TYPE__WORK_TYPE:
                getWorkType().clear();
                return;
            case WorktypePackage.WORK_TYPE__VERSION:
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
            case WorktypePackage.WORK_TYPE__WORK_TYPE:
                return workType != null && !workType.isEmpty();
            case WorktypePackage.WORK_TYPE__VERSION:
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
        result.append(" (version: ");
        result.append(version);
        result.append(')');
        return result.toString();
    }

} //WorkTypeImpl
