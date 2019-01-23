/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.WorkModelMapping;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Work Model Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkModelMappingImpl#getTypeParamName <em>Type Param Name</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkModelMappingImpl#getModelParamName <em>Model Param Name</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkModelMappingImpl#getDefaultValue <em>Default Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkModelMappingImpl extends EObjectImpl implements WorkModelMapping {
    /**
     * The default value of the '{@link #getTypeParamName() <em>Type Param Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTypeParamName()
     * @generated
     * @ordered
     */
    protected static final String TYPE_PARAM_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTypeParamName() <em>Type Param Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTypeParamName()
     * @generated
     * @ordered
     */
    protected String typeParamName = TYPE_PARAM_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getModelParamName() <em>Model Param Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getModelParamName()
     * @generated
     * @ordered
     */
    protected static final String MODEL_PARAM_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getModelParamName() <em>Model Param Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getModelParamName()
     * @generated
     * @ordered
     */
    protected String modelParamName = MODEL_PARAM_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getDefaultValue() <em>Default Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDefaultValue()
     * @generated
     * @ordered
     */
    protected static final String DEFAULT_VALUE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDefaultValue() <em>Default Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDefaultValue()
     * @generated
     * @ordered
     */
    protected String defaultValue = DEFAULT_VALUE_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorkModelMappingImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.WORK_MODEL_MAPPING;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getTypeParamName() {
        return typeParamName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTypeParamName(String newTypeParamName) {
        String oldTypeParamName = typeParamName;
        typeParamName = newTypeParamName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL_MAPPING__TYPE_PARAM_NAME, oldTypeParamName, typeParamName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getModelParamName() {
        return modelParamName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setModelParamName(String newModelParamName) {
        String oldModelParamName = modelParamName;
        modelParamName = newModelParamName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL_MAPPING__MODEL_PARAM_NAME, oldModelParamName, modelParamName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDefaultValue(String newDefaultValue) {
        String oldDefaultValue = defaultValue;
        defaultValue = newDefaultValue;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL_MAPPING__DEFAULT_VALUE, oldDefaultValue, defaultValue));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case N2BRMPackage.WORK_MODEL_MAPPING__TYPE_PARAM_NAME:
                return getTypeParamName();
            case N2BRMPackage.WORK_MODEL_MAPPING__MODEL_PARAM_NAME:
                return getModelParamName();
            case N2BRMPackage.WORK_MODEL_MAPPING__DEFAULT_VALUE:
                return getDefaultValue();
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
            case N2BRMPackage.WORK_MODEL_MAPPING__TYPE_PARAM_NAME:
                setTypeParamName((String)newValue);
                return;
            case N2BRMPackage.WORK_MODEL_MAPPING__MODEL_PARAM_NAME:
                setModelParamName((String)newValue);
                return;
            case N2BRMPackage.WORK_MODEL_MAPPING__DEFAULT_VALUE:
                setDefaultValue((String)newValue);
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
            case N2BRMPackage.WORK_MODEL_MAPPING__TYPE_PARAM_NAME:
                setTypeParamName(TYPE_PARAM_NAME_EDEFAULT);
                return;
            case N2BRMPackage.WORK_MODEL_MAPPING__MODEL_PARAM_NAME:
                setModelParamName(MODEL_PARAM_NAME_EDEFAULT);
                return;
            case N2BRMPackage.WORK_MODEL_MAPPING__DEFAULT_VALUE:
                setDefaultValue(DEFAULT_VALUE_EDEFAULT);
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
            case N2BRMPackage.WORK_MODEL_MAPPING__TYPE_PARAM_NAME:
                return TYPE_PARAM_NAME_EDEFAULT == null ? typeParamName != null : !TYPE_PARAM_NAME_EDEFAULT.equals(typeParamName);
            case N2BRMPackage.WORK_MODEL_MAPPING__MODEL_PARAM_NAME:
                return MODEL_PARAM_NAME_EDEFAULT == null ? modelParamName != null : !MODEL_PARAM_NAME_EDEFAULT.equals(modelParamName);
            case N2BRMPackage.WORK_MODEL_MAPPING__DEFAULT_VALUE:
                return DEFAULT_VALUE_EDEFAULT == null ? defaultValue != null : !DEFAULT_VALUE_EDEFAULT.equals(defaultValue);
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
        result.append(" (typeParamName: ");
        result.append(typeParamName);
        result.append(", modelParamName: ");
        result.append(modelParamName);
        result.append(", defaultValue: ");
        result.append(defaultValue);
        result.append(')');
        return result.toString();
    }

} //WorkModelMappingImpl
