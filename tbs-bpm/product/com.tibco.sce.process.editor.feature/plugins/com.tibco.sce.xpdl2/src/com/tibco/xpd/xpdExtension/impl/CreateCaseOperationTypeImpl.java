/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.CreateCaseOperationType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Create Case Operation Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CreateCaseOperationTypeImpl#getFromFieldPath <em>From Field Path</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CreateCaseOperationTypeImpl#getToCaseRefField <em>To Case Ref Field</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CreateCaseOperationTypeImpl extends EObjectImpl
        implements CreateCaseOperationType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getFromFieldPath() <em>From Field Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFromFieldPath()
     * @generated
     * @ordered
     */
    protected static final String FROM_FIELD_PATH_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFromFieldPath() <em>From Field Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFromFieldPath()
     * @generated
     * @ordered
     */
    protected String fromFieldPath = FROM_FIELD_PATH_EDEFAULT;

    /**
     * The default value of the '{@link #getToCaseRefField() <em>To Case Ref Field</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getToCaseRefField()
     * @generated
     * @ordered
     */
    protected static final String TO_CASE_REF_FIELD_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getToCaseRefField() <em>To Case Ref Field</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getToCaseRefField()
     * @generated
     * @ordered
     */
    protected String toCaseRefField = TO_CASE_REF_FIELD_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected CreateCaseOperationTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.CREATE_CASE_OPERATION_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getFromFieldPath() {
        return fromFieldPath;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFromFieldPath(String newFromFieldPath) {
        String oldFromFieldPath = fromFieldPath;
        fromFieldPath = newFromFieldPath;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CREATE_CASE_OPERATION_TYPE__FROM_FIELD_PATH,
                    oldFromFieldPath, fromFieldPath));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getToCaseRefField() {
        return toCaseRefField;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setToCaseRefField(String newToCaseRefField) {
        String oldToCaseRefField = toCaseRefField;
        toCaseRefField = newToCaseRefField;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CREATE_CASE_OPERATION_TYPE__TO_CASE_REF_FIELD,
                    oldToCaseRefField, toCaseRefField));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case XpdExtensionPackage.CREATE_CASE_OPERATION_TYPE__FROM_FIELD_PATH:
            return getFromFieldPath();
        case XpdExtensionPackage.CREATE_CASE_OPERATION_TYPE__TO_CASE_REF_FIELD:
            return getToCaseRefField();
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
        case XpdExtensionPackage.CREATE_CASE_OPERATION_TYPE__FROM_FIELD_PATH:
            setFromFieldPath((String) newValue);
            return;
        case XpdExtensionPackage.CREATE_CASE_OPERATION_TYPE__TO_CASE_REF_FIELD:
            setToCaseRefField((String) newValue);
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
        case XpdExtensionPackage.CREATE_CASE_OPERATION_TYPE__FROM_FIELD_PATH:
            setFromFieldPath(FROM_FIELD_PATH_EDEFAULT);
            return;
        case XpdExtensionPackage.CREATE_CASE_OPERATION_TYPE__TO_CASE_REF_FIELD:
            setToCaseRefField(TO_CASE_REF_FIELD_EDEFAULT);
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
        case XpdExtensionPackage.CREATE_CASE_OPERATION_TYPE__FROM_FIELD_PATH:
            return FROM_FIELD_PATH_EDEFAULT == null ? fromFieldPath != null
                    : !FROM_FIELD_PATH_EDEFAULT.equals(fromFieldPath);
        case XpdExtensionPackage.CREATE_CASE_OPERATION_TYPE__TO_CASE_REF_FIELD:
            return TO_CASE_REF_FIELD_EDEFAULT == null ? toCaseRefField != null
                    : !TO_CASE_REF_FIELD_EDEFAULT.equals(toCaseRefField);
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

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (fromFieldPath: "); //$NON-NLS-1$
        result.append(fromFieldPath);
        result.append(", toCaseRefField: "); //$NON-NLS-1$
        result.append(toCaseRefField);
        result.append(')');
        return result.toString();
    }

} //CreateCaseOperationTypeImpl
