/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.LinkCaseDocOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Link Case Doc Operation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.LinkCaseDocOperationImpl#getTargetCaseRefField <em>Target Case Ref Field</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LinkCaseDocOperationImpl extends EObjectImpl implements LinkCaseDocOperation {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getTargetCaseRefField() <em>Target Case Ref Field</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTargetCaseRefField()
     * @generated
     * @ordered
     */
    protected static final String TARGET_CASE_REF_FIELD_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTargetCaseRefField() <em>Target Case Ref Field</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTargetCaseRefField()
     * @generated
     * @ordered
     */
    protected String targetCaseRefField = TARGET_CASE_REF_FIELD_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected LinkCaseDocOperationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.LINK_CASE_DOC_OPERATION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getTargetCaseRefField() {
        return targetCaseRefField;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTargetCaseRefField(String newTargetCaseRefField) {
        String oldTargetCaseRefField = targetCaseRefField;
        targetCaseRefField = newTargetCaseRefField;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.LINK_CASE_DOC_OPERATION__TARGET_CASE_REF_FIELD, oldTargetCaseRefField,
                    targetCaseRefField));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case XpdExtensionPackage.LINK_CASE_DOC_OPERATION__TARGET_CASE_REF_FIELD:
            return getTargetCaseRefField();
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
        case XpdExtensionPackage.LINK_CASE_DOC_OPERATION__TARGET_CASE_REF_FIELD:
            setTargetCaseRefField((String) newValue);
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
        case XpdExtensionPackage.LINK_CASE_DOC_OPERATION__TARGET_CASE_REF_FIELD:
            setTargetCaseRefField(TARGET_CASE_REF_FIELD_EDEFAULT);
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
        case XpdExtensionPackage.LINK_CASE_DOC_OPERATION__TARGET_CASE_REF_FIELD:
            return TARGET_CASE_REF_FIELD_EDEFAULT == null ? targetCaseRefField != null
                    : !TARGET_CASE_REF_FIELD_EDEFAULT.equals(targetCaseRefField);
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
        result.append(" (targetCaseRefField: "); //$NON-NLS-1$
        result.append(targetCaseRefField);
        result.append(')');
        return result.toString();
    }

} //LinkCaseDocOperationImpl
