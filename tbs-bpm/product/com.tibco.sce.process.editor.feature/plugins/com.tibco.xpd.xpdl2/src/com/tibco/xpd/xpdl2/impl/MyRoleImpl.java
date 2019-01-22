/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdl2.MyRole;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>My Role</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.MyRoleImpl#getRoleName <em>Role Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MyRoleImpl extends EObjectImpl implements MyRole {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getRoleName() <em>Role Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRoleName()
     * @generated
     * @ordered
     */
    protected static final String ROLE_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getRoleName() <em>Role Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRoleName()
     * @generated
     * @ordered
     */
    protected String roleName = ROLE_NAME_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected MyRoleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.MY_ROLE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRoleName(String newRoleName) {
        String oldRoleName = roleName;
        roleName = newRoleName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.MY_ROLE__ROLE_NAME, oldRoleName, roleName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case Xpdl2Package.MY_ROLE__ROLE_NAME:
            return getRoleName();
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
        case Xpdl2Package.MY_ROLE__ROLE_NAME:
            setRoleName((String) newValue);
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
        case Xpdl2Package.MY_ROLE__ROLE_NAME:
            setRoleName(ROLE_NAME_EDEFAULT);
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
        case Xpdl2Package.MY_ROLE__ROLE_NAME:
            return ROLE_NAME_EDEFAULT == null ? roleName != null
                    : !ROLE_NAME_EDEFAULT.equals(roleName);
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
        result.append(" (roleName: "); //$NON-NLS-1$
        result.append(roleName);
        result.append(')');
        return result.toString();
    }

} //MyRoleImpl
