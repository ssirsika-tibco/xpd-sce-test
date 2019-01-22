/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.datamodel.impl;

import com.tibco.n2.common.datamodel.DatamodelPackage;
import com.tibco.n2.common.datamodel.WorkTypeSpec;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Work Type Spec</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.WorkTypeSpecImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.WorkTypeSpecImpl#getWorkTypeDescription <em>Work Type Description</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.WorkTypeSpecImpl#getWorkTypeID <em>Work Type ID</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkTypeSpecImpl extends EObjectImpl implements WorkTypeSpec {
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
     * The default value of the '{@link #getWorkTypeDescription() <em>Work Type Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkTypeDescription()
     * @generated
     * @ordered
     */
    protected static final String WORK_TYPE_DESCRIPTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getWorkTypeDescription() <em>Work Type Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkTypeDescription()
     * @generated
     * @ordered
     */
    protected String workTypeDescription = WORK_TYPE_DESCRIPTION_EDEFAULT;

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
    protected WorkTypeSpecImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DatamodelPackage.Literals.WORK_TYPE_SPEC;
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
            eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.WORK_TYPE_SPEC__VERSION, oldVersion, version));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getWorkTypeDescription() {
        return workTypeDescription;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkTypeDescription(String newWorkTypeDescription) {
        String oldWorkTypeDescription = workTypeDescription;
        workTypeDescription = newWorkTypeDescription;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.WORK_TYPE_SPEC__WORK_TYPE_DESCRIPTION, oldWorkTypeDescription, workTypeDescription));
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
            eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.WORK_TYPE_SPEC__WORK_TYPE_ID, oldWorkTypeID, workTypeID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DatamodelPackage.WORK_TYPE_SPEC__VERSION:
                return getVersion();
            case DatamodelPackage.WORK_TYPE_SPEC__WORK_TYPE_DESCRIPTION:
                return getWorkTypeDescription();
            case DatamodelPackage.WORK_TYPE_SPEC__WORK_TYPE_ID:
                return getWorkTypeID();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case DatamodelPackage.WORK_TYPE_SPEC__VERSION:
                setVersion((String)newValue);
                return;
            case DatamodelPackage.WORK_TYPE_SPEC__WORK_TYPE_DESCRIPTION:
                setWorkTypeDescription((String)newValue);
                return;
            case DatamodelPackage.WORK_TYPE_SPEC__WORK_TYPE_ID:
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
            case DatamodelPackage.WORK_TYPE_SPEC__VERSION:
                setVersion(VERSION_EDEFAULT);
                return;
            case DatamodelPackage.WORK_TYPE_SPEC__WORK_TYPE_DESCRIPTION:
                setWorkTypeDescription(WORK_TYPE_DESCRIPTION_EDEFAULT);
                return;
            case DatamodelPackage.WORK_TYPE_SPEC__WORK_TYPE_ID:
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
            case DatamodelPackage.WORK_TYPE_SPEC__VERSION:
                return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
            case DatamodelPackage.WORK_TYPE_SPEC__WORK_TYPE_DESCRIPTION:
                return WORK_TYPE_DESCRIPTION_EDEFAULT == null ? workTypeDescription != null : !WORK_TYPE_DESCRIPTION_EDEFAULT.equals(workTypeDescription);
            case DatamodelPackage.WORK_TYPE_SPEC__WORK_TYPE_ID:
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
        result.append(", workTypeDescription: ");
        result.append(workTypeDescription);
        result.append(", workTypeID: ");
        result.append(workTypeID);
        result.append(')');
        return result.toString();
    }

} //WorkTypeSpecImpl
