/**
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.ValidationControl;
import com.tibco.xpd.xpdExtension.ValidationIssueOverride;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Validation Control</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ValidationControlImpl#getValidationIssueOverrides <em>Validation Issue Overrides</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ValidationControlImpl extends EObjectImpl implements ValidationControl {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getValidationIssueOverrides() <em>Validation Issue Overrides</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getValidationIssueOverrides()
     * @generated
     * @ordered
     */
    protected EList<ValidationIssueOverride> validationIssueOverrides;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ValidationControlImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.VALIDATION_CONTROL;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ValidationIssueOverride> getValidationIssueOverrides() {
        if (validationIssueOverrides == null) {
            validationIssueOverrides =
                    new EObjectContainmentEList<ValidationIssueOverride>(ValidationIssueOverride.class, this,
                            XpdExtensionPackage.VALIDATION_CONTROL__VALIDATION_ISSUE_OVERRIDES);
        }
        return validationIssueOverrides;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case XpdExtensionPackage.VALIDATION_CONTROL__VALIDATION_ISSUE_OVERRIDES:
            return ((InternalEList<?>) getValidationIssueOverrides()).basicRemove(otherEnd, msgs);
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
        case XpdExtensionPackage.VALIDATION_CONTROL__VALIDATION_ISSUE_OVERRIDES:
            return getValidationIssueOverrides();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case XpdExtensionPackage.VALIDATION_CONTROL__VALIDATION_ISSUE_OVERRIDES:
            getValidationIssueOverrides().clear();
            getValidationIssueOverrides().addAll((Collection<? extends ValidationIssueOverride>) newValue);
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
        case XpdExtensionPackage.VALIDATION_CONTROL__VALIDATION_ISSUE_OVERRIDES:
            getValidationIssueOverrides().clear();
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
        case XpdExtensionPackage.VALIDATION_CONTROL__VALIDATION_ISSUE_OVERRIDES:
            return validationIssueOverrides != null && !validationIssueOverrides.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //ValidationControlImpl
