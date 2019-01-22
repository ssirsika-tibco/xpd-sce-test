/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.FindByFileNameOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Find By File Name Operation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.FindByFileNameOperationImpl#getFileNameField <em>File Name Field</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FindByFileNameOperationImpl extends EObjectImpl
        implements FindByFileNameOperation {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getFileNameField() <em>File Name Field</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFileNameField()
     * @generated
     * @ordered
     */
    protected static final String FILE_NAME_FIELD_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFileNameField() <em>File Name Field</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFileNameField()
     * @generated
     * @ordered
     */
    protected String fileNameField = FILE_NAME_FIELD_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected FindByFileNameOperationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.FIND_BY_FILE_NAME_OPERATION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getFileNameField() {
        return fileNameField;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFileNameField(String newFileNameField) {
        String oldFileNameField = fileNameField;
        fileNameField = newFileNameField;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.FIND_BY_FILE_NAME_OPERATION__FILE_NAME_FIELD,
                    oldFileNameField, fileNameField));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case XpdExtensionPackage.FIND_BY_FILE_NAME_OPERATION__FILE_NAME_FIELD:
            return getFileNameField();
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
        case XpdExtensionPackage.FIND_BY_FILE_NAME_OPERATION__FILE_NAME_FIELD:
            setFileNameField((String) newValue);
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
        case XpdExtensionPackage.FIND_BY_FILE_NAME_OPERATION__FILE_NAME_FIELD:
            setFileNameField(FILE_NAME_FIELD_EDEFAULT);
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
        case XpdExtensionPackage.FIND_BY_FILE_NAME_OPERATION__FILE_NAME_FIELD:
            return FILE_NAME_FIELD_EDEFAULT == null ? fileNameField != null
                    : !FILE_NAME_FIELD_EDEFAULT.equals(fileNameField);
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
        result.append(" (fileNameField: "); //$NON-NLS-1$
        result.append(fileNameField);
        result.append(')');
        return result.toString();
    }

} //FindByFileNameOperationImpl
