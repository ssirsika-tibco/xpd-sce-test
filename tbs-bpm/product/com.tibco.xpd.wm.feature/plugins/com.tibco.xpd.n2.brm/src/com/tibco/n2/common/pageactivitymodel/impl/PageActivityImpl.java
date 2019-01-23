/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.pageactivitymodel.impl;

import com.tibco.n2.common.datamodel.DataModel;

import com.tibco.n2.common.pageactivitymodel.PageActivity;
import com.tibco.n2.common.pageactivitymodel.PageactivitymodelPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Page Activity</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.common.pageactivitymodel.impl.PageActivityImpl#getActivityModelID <em>Activity Model ID</em>}</li>
 *   <li>{@link com.tibco.n2.common.pageactivitymodel.impl.PageActivityImpl#getActivityDescription <em>Activity Description</em>}</li>
 *   <li>{@link com.tibco.n2.common.pageactivitymodel.impl.PageActivityImpl#getModuleName <em>Module Name</em>}</li>
 *   <li>{@link com.tibco.n2.common.pageactivitymodel.impl.PageActivityImpl#getModuleVersion <em>Module Version</em>}</li>
 *   <li>{@link com.tibco.n2.common.pageactivitymodel.impl.PageActivityImpl#getProcessName <em>Process Name</em>}</li>
 *   <li>{@link com.tibco.n2.common.pageactivitymodel.impl.PageActivityImpl#getDataModel <em>Data Model</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PageActivityImpl extends EObjectImpl implements PageActivity {
    /**
     * The default value of the '{@link #getActivityModelID() <em>Activity Model ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getActivityModelID()
     * @generated
     * @ordered
     */
    protected static final String ACTIVITY_MODEL_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getActivityModelID() <em>Activity Model ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getActivityModelID()
     * @generated
     * @ordered
     */
    protected String activityModelID = ACTIVITY_MODEL_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getActivityDescription() <em>Activity Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getActivityDescription()
     * @generated
     * @ordered
     */
    protected static final String ACTIVITY_DESCRIPTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getActivityDescription() <em>Activity Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getActivityDescription()
     * @generated
     * @ordered
     */
    protected String activityDescription = ACTIVITY_DESCRIPTION_EDEFAULT;

    /**
     * The default value of the '{@link #getModuleName() <em>Module Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getModuleName()
     * @generated
     * @ordered
     */
    protected static final String MODULE_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getModuleName() <em>Module Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getModuleName()
     * @generated
     * @ordered
     */
    protected String moduleName = MODULE_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getModuleVersion() <em>Module Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getModuleVersion()
     * @generated
     * @ordered
     */
    protected static final String MODULE_VERSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getModuleVersion() <em>Module Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getModuleVersion()
     * @generated
     * @ordered
     */
    protected String moduleVersion = MODULE_VERSION_EDEFAULT;

    /**
     * The default value of the '{@link #getProcessName() <em>Process Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProcessName()
     * @generated
     * @ordered
     */
    protected static final String PROCESS_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getProcessName() <em>Process Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProcessName()
     * @generated
     * @ordered
     */
    protected String processName = PROCESS_NAME_EDEFAULT;

    /**
     * The cached value of the '{@link #getDataModel() <em>Data Model</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDataModel()
     * @generated
     * @ordered
     */
    protected DataModel dataModel;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PageActivityImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PageactivitymodelPackage.Literals.PAGE_ACTIVITY;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getActivityModelID() {
        return activityModelID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setActivityModelID(String newActivityModelID) {
        String oldActivityModelID = activityModelID;
        activityModelID = newActivityModelID;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PageactivitymodelPackage.PAGE_ACTIVITY__ACTIVITY_MODEL_ID, oldActivityModelID, activityModelID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getActivityDescription() {
        return activityDescription;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setActivityDescription(String newActivityDescription) {
        String oldActivityDescription = activityDescription;
        activityDescription = newActivityDescription;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PageactivitymodelPackage.PAGE_ACTIVITY__ACTIVITY_DESCRIPTION, oldActivityDescription, activityDescription));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setModuleName(String newModuleName) {
        String oldModuleName = moduleName;
        moduleName = newModuleName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PageactivitymodelPackage.PAGE_ACTIVITY__MODULE_NAME, oldModuleName, moduleName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getModuleVersion() {
        return moduleVersion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setModuleVersion(String newModuleVersion) {
        String oldModuleVersion = moduleVersion;
        moduleVersion = newModuleVersion;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PageactivitymodelPackage.PAGE_ACTIVITY__MODULE_VERSION, oldModuleVersion, moduleVersion));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getProcessName() {
        return processName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setProcessName(String newProcessName) {
        String oldProcessName = processName;
        processName = newProcessName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PageactivitymodelPackage.PAGE_ACTIVITY__PROCESS_NAME, oldProcessName, processName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DataModel getDataModel() {
        return dataModel;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDataModel(DataModel newDataModel, NotificationChain msgs) {
        DataModel oldDataModel = dataModel;
        dataModel = newDataModel;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PageactivitymodelPackage.PAGE_ACTIVITY__DATA_MODEL, oldDataModel, newDataModel);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDataModel(DataModel newDataModel) {
        if (newDataModel != dataModel) {
            NotificationChain msgs = null;
            if (dataModel != null)
                msgs = ((InternalEObject)dataModel).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PageactivitymodelPackage.PAGE_ACTIVITY__DATA_MODEL, null, msgs);
            if (newDataModel != null)
                msgs = ((InternalEObject)newDataModel).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PageactivitymodelPackage.PAGE_ACTIVITY__DATA_MODEL, null, msgs);
            msgs = basicSetDataModel(newDataModel, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PageactivitymodelPackage.PAGE_ACTIVITY__DATA_MODEL, newDataModel, newDataModel));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case PageactivitymodelPackage.PAGE_ACTIVITY__DATA_MODEL:
                return basicSetDataModel(null, msgs);
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
            case PageactivitymodelPackage.PAGE_ACTIVITY__ACTIVITY_MODEL_ID:
                return getActivityModelID();
            case PageactivitymodelPackage.PAGE_ACTIVITY__ACTIVITY_DESCRIPTION:
                return getActivityDescription();
            case PageactivitymodelPackage.PAGE_ACTIVITY__MODULE_NAME:
                return getModuleName();
            case PageactivitymodelPackage.PAGE_ACTIVITY__MODULE_VERSION:
                return getModuleVersion();
            case PageactivitymodelPackage.PAGE_ACTIVITY__PROCESS_NAME:
                return getProcessName();
            case PageactivitymodelPackage.PAGE_ACTIVITY__DATA_MODEL:
                return getDataModel();
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
            case PageactivitymodelPackage.PAGE_ACTIVITY__ACTIVITY_MODEL_ID:
                setActivityModelID((String)newValue);
                return;
            case PageactivitymodelPackage.PAGE_ACTIVITY__ACTIVITY_DESCRIPTION:
                setActivityDescription((String)newValue);
                return;
            case PageactivitymodelPackage.PAGE_ACTIVITY__MODULE_NAME:
                setModuleName((String)newValue);
                return;
            case PageactivitymodelPackage.PAGE_ACTIVITY__MODULE_VERSION:
                setModuleVersion((String)newValue);
                return;
            case PageactivitymodelPackage.PAGE_ACTIVITY__PROCESS_NAME:
                setProcessName((String)newValue);
                return;
            case PageactivitymodelPackage.PAGE_ACTIVITY__DATA_MODEL:
                setDataModel((DataModel)newValue);
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
            case PageactivitymodelPackage.PAGE_ACTIVITY__ACTIVITY_MODEL_ID:
                setActivityModelID(ACTIVITY_MODEL_ID_EDEFAULT);
                return;
            case PageactivitymodelPackage.PAGE_ACTIVITY__ACTIVITY_DESCRIPTION:
                setActivityDescription(ACTIVITY_DESCRIPTION_EDEFAULT);
                return;
            case PageactivitymodelPackage.PAGE_ACTIVITY__MODULE_NAME:
                setModuleName(MODULE_NAME_EDEFAULT);
                return;
            case PageactivitymodelPackage.PAGE_ACTIVITY__MODULE_VERSION:
                setModuleVersion(MODULE_VERSION_EDEFAULT);
                return;
            case PageactivitymodelPackage.PAGE_ACTIVITY__PROCESS_NAME:
                setProcessName(PROCESS_NAME_EDEFAULT);
                return;
            case PageactivitymodelPackage.PAGE_ACTIVITY__DATA_MODEL:
                setDataModel((DataModel)null);
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
            case PageactivitymodelPackage.PAGE_ACTIVITY__ACTIVITY_MODEL_ID:
                return ACTIVITY_MODEL_ID_EDEFAULT == null ? activityModelID != null : !ACTIVITY_MODEL_ID_EDEFAULT.equals(activityModelID);
            case PageactivitymodelPackage.PAGE_ACTIVITY__ACTIVITY_DESCRIPTION:
                return ACTIVITY_DESCRIPTION_EDEFAULT == null ? activityDescription != null : !ACTIVITY_DESCRIPTION_EDEFAULT.equals(activityDescription);
            case PageactivitymodelPackage.PAGE_ACTIVITY__MODULE_NAME:
                return MODULE_NAME_EDEFAULT == null ? moduleName != null : !MODULE_NAME_EDEFAULT.equals(moduleName);
            case PageactivitymodelPackage.PAGE_ACTIVITY__MODULE_VERSION:
                return MODULE_VERSION_EDEFAULT == null ? moduleVersion != null : !MODULE_VERSION_EDEFAULT.equals(moduleVersion);
            case PageactivitymodelPackage.PAGE_ACTIVITY__PROCESS_NAME:
                return PROCESS_NAME_EDEFAULT == null ? processName != null : !PROCESS_NAME_EDEFAULT.equals(processName);
            case PageactivitymodelPackage.PAGE_ACTIVITY__DATA_MODEL:
                return dataModel != null;
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
        result.append(" (activityModelID: ");
        result.append(activityModelID);
        result.append(", activityDescription: ");
        result.append(activityDescription);
        result.append(", moduleName: ");
        result.append(moduleName);
        result.append(", moduleVersion: ");
        result.append(moduleVersion);
        result.append(", processName: ");
        result.append(processName);
        result.append(')');
        return result.toString();
    }

} //PageActivityImpl
