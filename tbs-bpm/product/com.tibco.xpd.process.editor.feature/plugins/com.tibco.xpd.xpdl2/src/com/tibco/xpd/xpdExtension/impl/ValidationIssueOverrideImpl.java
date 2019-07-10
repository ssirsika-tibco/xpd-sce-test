/**
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.ValidationIssueOverride;
import com.tibco.xpd.xpdExtension.ValidationIssueOverrideType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Validation Issue Override</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ValidationIssueOverrideImpl#getValidationIssueId <em>Validation Issue Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ValidationIssueOverrideImpl#getOverrideType <em>Override Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ValidationIssueOverrideImpl extends EObjectImpl implements ValidationIssueOverride {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getValidationIssueId() <em>Validation Issue Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getValidationIssueId()
     * @generated
     * @ordered
     */
    protected static final String VALIDATION_ISSUE_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getValidationIssueId() <em>Validation Issue Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getValidationIssueId()
     * @generated
     * @ordered
     */
    protected String validationIssueId = VALIDATION_ISSUE_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getOverrideType() <em>Override Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOverrideType()
     * @generated
     * @ordered
     */
    protected static final ValidationIssueOverrideType OVERRIDE_TYPE_EDEFAULT =
            ValidationIssueOverrideType.SUPPRESS_UNTIL_NEXT_FLOW_CHANGE;

    /**
     * The cached value of the '{@link #getOverrideType() <em>Override Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOverrideType()
     * @generated
     * @ordered
     */
    protected ValidationIssueOverrideType overrideType = OVERRIDE_TYPE_EDEFAULT;

    /**
     * This is true if the Override Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean overrideTypeESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ValidationIssueOverrideImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.VALIDATION_ISSUE_OVERRIDE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getValidationIssueId() {
        return validationIssueId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setValidationIssueId(String newValidationIssueId) {
        String oldValidationIssueId = validationIssueId;
        validationIssueId = newValidationIssueId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.VALIDATION_ISSUE_OVERRIDE__VALIDATION_ISSUE_ID, oldValidationIssueId,
                    validationIssueId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ValidationIssueOverrideType getOverrideType() {
        return overrideType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOverrideType(ValidationIssueOverrideType newOverrideType) {
        ValidationIssueOverrideType oldOverrideType = overrideType;
        overrideType = newOverrideType == null ? OVERRIDE_TYPE_EDEFAULT : newOverrideType;
        boolean oldOverrideTypeESet = overrideTypeESet;
        overrideTypeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.VALIDATION_ISSUE_OVERRIDE__OVERRIDE_TYPE, oldOverrideType, overrideType,
                    !oldOverrideTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetOverrideType() {
        ValidationIssueOverrideType oldOverrideType = overrideType;
        boolean oldOverrideTypeESet = overrideTypeESet;
        overrideType = OVERRIDE_TYPE_EDEFAULT;
        overrideTypeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.VALIDATION_ISSUE_OVERRIDE__OVERRIDE_TYPE, oldOverrideType,
                    OVERRIDE_TYPE_EDEFAULT, oldOverrideTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetOverrideType() {
        return overrideTypeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case XpdExtensionPackage.VALIDATION_ISSUE_OVERRIDE__VALIDATION_ISSUE_ID:
            return getValidationIssueId();
        case XpdExtensionPackage.VALIDATION_ISSUE_OVERRIDE__OVERRIDE_TYPE:
            return getOverrideType();
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
        case XpdExtensionPackage.VALIDATION_ISSUE_OVERRIDE__VALIDATION_ISSUE_ID:
            setValidationIssueId((String) newValue);
            return;
        case XpdExtensionPackage.VALIDATION_ISSUE_OVERRIDE__OVERRIDE_TYPE:
            setOverrideType((ValidationIssueOverrideType) newValue);
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
        case XpdExtensionPackage.VALIDATION_ISSUE_OVERRIDE__VALIDATION_ISSUE_ID:
            setValidationIssueId(VALIDATION_ISSUE_ID_EDEFAULT);
            return;
        case XpdExtensionPackage.VALIDATION_ISSUE_OVERRIDE__OVERRIDE_TYPE:
            unsetOverrideType();
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
        case XpdExtensionPackage.VALIDATION_ISSUE_OVERRIDE__VALIDATION_ISSUE_ID:
            return VALIDATION_ISSUE_ID_EDEFAULT == null ? validationIssueId != null
                    : !VALIDATION_ISSUE_ID_EDEFAULT.equals(validationIssueId);
        case XpdExtensionPackage.VALIDATION_ISSUE_OVERRIDE__OVERRIDE_TYPE:
            return isSetOverrideType();
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
        result.append(" (validationIssueId: "); //$NON-NLS-1$
        result.append(validationIssueId);
        result.append(", overrideType: "); //$NON-NLS-1$
        if (overrideTypeESet)
            result.append(overrideType);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //ValidationIssueOverrideImpl
