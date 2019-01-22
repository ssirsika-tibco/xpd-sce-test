/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.deploy.ConfigParameter;
import com.tibco.xpd.deploy.ConfigParameterInfo;
import com.tibco.xpd.deploy.DeployPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Config Parameter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.impl.ConfigParameterImpl#getKey <em>Key</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ConfigParameterImpl#getValue <em>Value</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ConfigParameterImpl#getConfigParameterInfo <em>Config Parameter Info</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConfigParameterImpl extends EObjectImpl implements ConfigParameter {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * The default value of the '{@link #getKey() <em>Key</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getKey()
     * @generated
     * @ordered
     */
    protected static final String KEY_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getKey() <em>Key</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getKey()
     * @generated
     * @ordered
     */
    protected String key = KEY_EDEFAULT;

    /**
     * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getValue()
     * @generated
     * @ordered
     */
    protected static final String VALUE_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getValue()
     * @generated
     * @ordered
     */
    protected String value = VALUE_EDEFAULT;

    /**
     * This is true if the Value attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean valueESet;

    /**
     * The cached value of the '{@link #getConfigParameterInfo() <em>Config Parameter Info</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getConfigParameterInfo()
     * @generated
     * @ordered
     */
    protected ConfigParameterInfo configParameterInfo;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected ConfigParameterImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DeployPackage.Literals.CONFIG_PARAMETER;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getKey() {
        return key;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setKey(String newKey) {
        String oldKey = key;
        key = newKey;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.CONFIG_PARAMETER__KEY, oldKey, key));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getValue() {
        return value;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setValue(String newValue) {
        String oldValue = value;
        value = newValue;
        boolean oldValueESet = valueESet;
        valueESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.CONFIG_PARAMETER__VALUE, oldValue, value,
                    !oldValueESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetValue() {
        String oldValue = value;
        boolean oldValueESet = valueESet;
        value = VALUE_EDEFAULT;
        valueESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    DeployPackage.CONFIG_PARAMETER__VALUE, oldValue,
                    VALUE_EDEFAULT, oldValueESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetValue() {
        return valueESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ConfigParameterInfo getConfigParameterInfo() {
        if (configParameterInfo != null && configParameterInfo.eIsProxy()) {
            InternalEObject oldConfigParameterInfo = (InternalEObject) configParameterInfo;
            configParameterInfo = (ConfigParameterInfo) eResolveProxy(oldConfigParameterInfo);
            if (configParameterInfo != oldConfigParameterInfo) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(
                            this,
                            Notification.RESOLVE,
                            DeployPackage.CONFIG_PARAMETER__CONFIG_PARAMETER_INFO,
                            oldConfigParameterInfo, configParameterInfo));
            }
        }
        return configParameterInfo;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ConfigParameterInfo basicGetConfigParameterInfo() {
        return configParameterInfo;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setConfigParameterInfo(
            ConfigParameterInfo newConfigParameterInfo) {
        ConfigParameterInfo oldConfigParameterInfo = configParameterInfo;
        configParameterInfo = newConfigParameterInfo;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.CONFIG_PARAMETER__CONFIG_PARAMETER_INFO,
                    oldConfigParameterInfo, configParameterInfo));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case DeployPackage.CONFIG_PARAMETER__KEY:
            return getKey();
        case DeployPackage.CONFIG_PARAMETER__VALUE:
            return getValue();
        case DeployPackage.CONFIG_PARAMETER__CONFIG_PARAMETER_INFO:
            if (resolve)
                return getConfigParameterInfo();
            return basicGetConfigParameterInfo();
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
        case DeployPackage.CONFIG_PARAMETER__KEY:
            setKey((String) newValue);
            return;
        case DeployPackage.CONFIG_PARAMETER__VALUE:
            setValue((String) newValue);
            return;
        case DeployPackage.CONFIG_PARAMETER__CONFIG_PARAMETER_INFO:
            setConfigParameterInfo((ConfigParameterInfo) newValue);
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
        case DeployPackage.CONFIG_PARAMETER__KEY:
            setKey(KEY_EDEFAULT);
            return;
        case DeployPackage.CONFIG_PARAMETER__VALUE:
            unsetValue();
            return;
        case DeployPackage.CONFIG_PARAMETER__CONFIG_PARAMETER_INFO:
            setConfigParameterInfo((ConfigParameterInfo) null);
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
        case DeployPackage.CONFIG_PARAMETER__KEY:
            return KEY_EDEFAULT == null ? key != null : !KEY_EDEFAULT
                    .equals(key);
        case DeployPackage.CONFIG_PARAMETER__VALUE:
            return isSetValue();
        case DeployPackage.CONFIG_PARAMETER__CONFIG_PARAMETER_INFO:
            return configParameterInfo != null;
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
        result.append(" (key: ");
        result.append(key);
        result.append(", value: ");
        if (valueESet)
            result.append(value);
        else
            result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} // ConfigParameterImpl
