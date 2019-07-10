/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.RequiredAccessPrivileges;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import com.tibco.xpd.xpdl2.ExternalReference;

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
 * An implementation of the model object '<em><b>Required Access Privileges</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.RequiredAccessPrivilegesImpl#getPrivilegeReference <em>Privilege Reference</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RequiredAccessPrivilegesImpl extends EObjectImpl implements RequiredAccessPrivileges {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getPrivilegeReference() <em>Privilege Reference</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPrivilegeReference()
     * @generated
     * @ordered
     */
    protected EList<ExternalReference> privilegeReference;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected RequiredAccessPrivilegesImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.REQUIRED_ACCESS_PRIVILEGES;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ExternalReference> getPrivilegeReference() {
        if (privilegeReference == null) {
            privilegeReference = new EObjectContainmentEList<ExternalReference>(ExternalReference.class, this,
                    XpdExtensionPackage.REQUIRED_ACCESS_PRIVILEGES__PRIVILEGE_REFERENCE);
        }
        return privilegeReference;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case XpdExtensionPackage.REQUIRED_ACCESS_PRIVILEGES__PRIVILEGE_REFERENCE:
            return ((InternalEList<?>) getPrivilegeReference()).basicRemove(otherEnd, msgs);
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
        case XpdExtensionPackage.REQUIRED_ACCESS_PRIVILEGES__PRIVILEGE_REFERENCE:
            return getPrivilegeReference();
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
        case XpdExtensionPackage.REQUIRED_ACCESS_PRIVILEGES__PRIVILEGE_REFERENCE:
            getPrivilegeReference().clear();
            getPrivilegeReference().addAll((Collection<? extends ExternalReference>) newValue);
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
        case XpdExtensionPackage.REQUIRED_ACCESS_PRIVILEGES__PRIVILEGE_REFERENCE:
            getPrivilegeReference().clear();
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
        case XpdExtensionPackage.REQUIRED_ACCESS_PRIVILEGES__PRIVILEGE_REFERENCE:
            return privilegeReference != null && !privilegeReference.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //RequiredAccessPrivilegesImpl
