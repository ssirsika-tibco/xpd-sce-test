/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.DeleteByCaseIdentifierType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Delete By Case Identifier Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DeleteByCaseIdentifierTypeImpl#getFieldPath <em>Field Path</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DeleteByCaseIdentifierTypeImpl#getIdentifierName <em>Identifier Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DeleteByCaseIdentifierTypeImpl extends EObjectImpl implements DeleteByCaseIdentifierType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getFieldPath() <em>Field Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFieldPath()
     * @generated
     * @ordered
     */
    protected static final String FIELD_PATH_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFieldPath() <em>Field Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFieldPath()
     * @generated
     * @ordered
     */
    protected String fieldPath = FIELD_PATH_EDEFAULT;

    /**
     * The default value of the '{@link #getIdentifierName() <em>Identifier Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIdentifierName()
     * @generated
     * @ordered
     */
    protected static final String IDENTIFIER_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIdentifierName() <em>Identifier Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIdentifierName()
     * @generated
     * @ordered
     */
    protected String identifierName = IDENTIFIER_NAME_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DeleteByCaseIdentifierTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.DELETE_BY_CASE_IDENTIFIER_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getFieldPath() {
        return fieldPath;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFieldPath(String newFieldPath) {
        String oldFieldPath = fieldPath;
        fieldPath = newFieldPath;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.DELETE_BY_CASE_IDENTIFIER_TYPE__FIELD_PATH, oldFieldPath, fieldPath));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getIdentifierName() {
        return identifierName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setIdentifierName(String newIdentifierName) {
        String oldIdentifierName = identifierName;
        identifierName = newIdentifierName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.DELETE_BY_CASE_IDENTIFIER_TYPE__IDENTIFIER_NAME, oldIdentifierName,
                    identifierName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case XpdExtensionPackage.DELETE_BY_CASE_IDENTIFIER_TYPE__FIELD_PATH:
            return getFieldPath();
        case XpdExtensionPackage.DELETE_BY_CASE_IDENTIFIER_TYPE__IDENTIFIER_NAME:
            return getIdentifierName();
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
        case XpdExtensionPackage.DELETE_BY_CASE_IDENTIFIER_TYPE__FIELD_PATH:
            setFieldPath((String) newValue);
            return;
        case XpdExtensionPackage.DELETE_BY_CASE_IDENTIFIER_TYPE__IDENTIFIER_NAME:
            setIdentifierName((String) newValue);
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
        case XpdExtensionPackage.DELETE_BY_CASE_IDENTIFIER_TYPE__FIELD_PATH:
            setFieldPath(FIELD_PATH_EDEFAULT);
            return;
        case XpdExtensionPackage.DELETE_BY_CASE_IDENTIFIER_TYPE__IDENTIFIER_NAME:
            setIdentifierName(IDENTIFIER_NAME_EDEFAULT);
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
        case XpdExtensionPackage.DELETE_BY_CASE_IDENTIFIER_TYPE__FIELD_PATH:
            return FIELD_PATH_EDEFAULT == null ? fieldPath != null : !FIELD_PATH_EDEFAULT.equals(fieldPath);
        case XpdExtensionPackage.DELETE_BY_CASE_IDENTIFIER_TYPE__IDENTIFIER_NAME:
            return IDENTIFIER_NAME_EDEFAULT == null ? identifierName != null
                    : !IDENTIFIER_NAME_EDEFAULT.equals(identifierName);
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
        if (eIsProxy())
            return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (fieldPath: "); //$NON-NLS-1$
        result.append(fieldPath);
        result.append(", identifierName: "); //$NON-NLS-1$
        result.append(identifierName);
        result.append(')');
        return result.toString();
    }

} //DeleteByCaseIdentifierTypeImpl
