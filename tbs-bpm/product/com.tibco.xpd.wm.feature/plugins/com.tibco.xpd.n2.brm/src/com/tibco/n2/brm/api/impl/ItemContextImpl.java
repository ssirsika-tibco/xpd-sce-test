/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.ItemContext;
import com.tibco.n2.brm.api.N2BRMPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Item Context</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.ItemContextImpl#getActivityID <em>Activity ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ItemContextImpl#getActivityName <em>Activity Name</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ItemContextImpl#getAppInstance <em>App Instance</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ItemContextImpl#getAppName <em>App Name</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ItemContextImpl#getAppID <em>App ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ItemContextImpl#getAppInstanceDescription <em>App Instance Description</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ItemContextImpl extends EObjectImpl implements ItemContext {
    /**
     * The default value of the '{@link #getActivityID() <em>Activity ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getActivityID()
     * @generated
     * @ordered
     */
    protected static final String ACTIVITY_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getActivityID() <em>Activity ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getActivityID()
     * @generated
     * @ordered
     */
    protected String activityID = ACTIVITY_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getActivityName() <em>Activity Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getActivityName()
     * @generated
     * @ordered
     */
    protected static final String ACTIVITY_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getActivityName() <em>Activity Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getActivityName()
     * @generated
     * @ordered
     */
    protected String activityName = ACTIVITY_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getAppInstance() <em>App Instance</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAppInstance()
     * @generated
     * @ordered
     */
    protected static final String APP_INSTANCE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAppInstance() <em>App Instance</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAppInstance()
     * @generated
     * @ordered
     */
    protected String appInstance = APP_INSTANCE_EDEFAULT;

    /**
     * The default value of the '{@link #getAppName() <em>App Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAppName()
     * @generated
     * @ordered
     */
    protected static final String APP_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAppName() <em>App Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAppName()
     * @generated
     * @ordered
     */
    protected String appName = APP_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getAppID() <em>App ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAppID()
     * @generated
     * @ordered
     */
    protected static final String APP_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAppID() <em>App ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAppID()
     * @generated
     * @ordered
     */
    protected String appID = APP_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getAppInstanceDescription() <em>App Instance Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAppInstanceDescription()
     * @generated
     * @ordered
     */
    protected static final String APP_INSTANCE_DESCRIPTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAppInstanceDescription() <em>App Instance Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAppInstanceDescription()
     * @generated
     * @ordered
     */
    protected String appInstanceDescription = APP_INSTANCE_DESCRIPTION_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ItemContextImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.ITEM_CONTEXT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getActivityID() {
        return activityID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setActivityID(String newActivityID) {
        String oldActivityID = activityID;
        activityID = newActivityID;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ITEM_CONTEXT__ACTIVITY_ID, oldActivityID, activityID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getActivityName() {
        return activityName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setActivityName(String newActivityName) {
        String oldActivityName = activityName;
        activityName = newActivityName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ITEM_CONTEXT__ACTIVITY_NAME, oldActivityName, activityName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAppInstance() {
        return appInstance;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAppInstance(String newAppInstance) {
        String oldAppInstance = appInstance;
        appInstance = newAppInstance;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ITEM_CONTEXT__APP_INSTANCE, oldAppInstance, appInstance));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAppName() {
        return appName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAppName(String newAppName) {
        String oldAppName = appName;
        appName = newAppName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ITEM_CONTEXT__APP_NAME, oldAppName, appName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAppID() {
        return appID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAppID(String newAppID) {
        String oldAppID = appID;
        appID = newAppID;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ITEM_CONTEXT__APP_ID, oldAppID, appID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAppInstanceDescription() {
        return appInstanceDescription;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAppInstanceDescription(String newAppInstanceDescription) {
        String oldAppInstanceDescription = appInstanceDescription;
        appInstanceDescription = newAppInstanceDescription;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ITEM_CONTEXT__APP_INSTANCE_DESCRIPTION, oldAppInstanceDescription, appInstanceDescription));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case N2BRMPackage.ITEM_CONTEXT__ACTIVITY_ID:
                return getActivityID();
            case N2BRMPackage.ITEM_CONTEXT__ACTIVITY_NAME:
                return getActivityName();
            case N2BRMPackage.ITEM_CONTEXT__APP_INSTANCE:
                return getAppInstance();
            case N2BRMPackage.ITEM_CONTEXT__APP_NAME:
                return getAppName();
            case N2BRMPackage.ITEM_CONTEXT__APP_ID:
                return getAppID();
            case N2BRMPackage.ITEM_CONTEXT__APP_INSTANCE_DESCRIPTION:
                return getAppInstanceDescription();
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
            case N2BRMPackage.ITEM_CONTEXT__ACTIVITY_ID:
                setActivityID((String)newValue);
                return;
            case N2BRMPackage.ITEM_CONTEXT__ACTIVITY_NAME:
                setActivityName((String)newValue);
                return;
            case N2BRMPackage.ITEM_CONTEXT__APP_INSTANCE:
                setAppInstance((String)newValue);
                return;
            case N2BRMPackage.ITEM_CONTEXT__APP_NAME:
                setAppName((String)newValue);
                return;
            case N2BRMPackage.ITEM_CONTEXT__APP_ID:
                setAppID((String)newValue);
                return;
            case N2BRMPackage.ITEM_CONTEXT__APP_INSTANCE_DESCRIPTION:
                setAppInstanceDescription((String)newValue);
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
            case N2BRMPackage.ITEM_CONTEXT__ACTIVITY_ID:
                setActivityID(ACTIVITY_ID_EDEFAULT);
                return;
            case N2BRMPackage.ITEM_CONTEXT__ACTIVITY_NAME:
                setActivityName(ACTIVITY_NAME_EDEFAULT);
                return;
            case N2BRMPackage.ITEM_CONTEXT__APP_INSTANCE:
                setAppInstance(APP_INSTANCE_EDEFAULT);
                return;
            case N2BRMPackage.ITEM_CONTEXT__APP_NAME:
                setAppName(APP_NAME_EDEFAULT);
                return;
            case N2BRMPackage.ITEM_CONTEXT__APP_ID:
                setAppID(APP_ID_EDEFAULT);
                return;
            case N2BRMPackage.ITEM_CONTEXT__APP_INSTANCE_DESCRIPTION:
                setAppInstanceDescription(APP_INSTANCE_DESCRIPTION_EDEFAULT);
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
            case N2BRMPackage.ITEM_CONTEXT__ACTIVITY_ID:
                return ACTIVITY_ID_EDEFAULT == null ? activityID != null : !ACTIVITY_ID_EDEFAULT.equals(activityID);
            case N2BRMPackage.ITEM_CONTEXT__ACTIVITY_NAME:
                return ACTIVITY_NAME_EDEFAULT == null ? activityName != null : !ACTIVITY_NAME_EDEFAULT.equals(activityName);
            case N2BRMPackage.ITEM_CONTEXT__APP_INSTANCE:
                return APP_INSTANCE_EDEFAULT == null ? appInstance != null : !APP_INSTANCE_EDEFAULT.equals(appInstance);
            case N2BRMPackage.ITEM_CONTEXT__APP_NAME:
                return APP_NAME_EDEFAULT == null ? appName != null : !APP_NAME_EDEFAULT.equals(appName);
            case N2BRMPackage.ITEM_CONTEXT__APP_ID:
                return APP_ID_EDEFAULT == null ? appID != null : !APP_ID_EDEFAULT.equals(appID);
            case N2BRMPackage.ITEM_CONTEXT__APP_INSTANCE_DESCRIPTION:
                return APP_INSTANCE_DESCRIPTION_EDEFAULT == null ? appInstanceDescription != null : !APP_INSTANCE_DESCRIPTION_EDEFAULT.equals(appInstanceDescription);
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
        result.append(" (activityID: ");
        result.append(activityID);
        result.append(", activityName: ");
        result.append(activityName);
        result.append(", appInstance: ");
        result.append(appInstance);
        result.append(", appName: ");
        result.append(appName);
        result.append(", appID: ");
        result.append(appID);
        result.append(", appInstanceDescription: ");
        result.append(appInstanceDescription);
        result.append(')');
        return result.toString();
    }

} //ItemContextImpl
