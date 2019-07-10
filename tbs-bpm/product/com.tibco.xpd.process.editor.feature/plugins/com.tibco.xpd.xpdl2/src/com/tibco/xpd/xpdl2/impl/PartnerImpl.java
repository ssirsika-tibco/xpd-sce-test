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

import com.tibco.xpd.xpdl2.Partner;
import com.tibco.xpd.xpdl2.RoleType;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Partner</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PartnerImpl#getPartnerLinkId <em>Partner Link Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PartnerImpl#getRoleType <em>Role Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PartnerImpl extends EObjectImpl implements Partner {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getPartnerLinkId() <em>Partner Link Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPartnerLinkId()
     * @generated
     * @ordered
     */
    protected static final String PARTNER_LINK_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPartnerLinkId() <em>Partner Link Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPartnerLinkId()
     * @generated
     * @ordered
     */
    protected String partnerLinkId = PARTNER_LINK_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getRoleType() <em>Role Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRoleType()
     * @generated
     * @ordered
     */
    protected static final RoleType ROLE_TYPE_EDEFAULT = RoleType.MY_ROLE_LITERAL;

    /**
     * The cached value of the '{@link #getRoleType() <em>Role Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRoleType()
     * @generated
     * @ordered
     */
    protected RoleType roleType = ROLE_TYPE_EDEFAULT;

    /**
     * This is true if the Role Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean roleTypeESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PartnerImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.PARTNER;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getPartnerLinkId() {
        return partnerLinkId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPartnerLinkId(String newPartnerLinkId) {
        String oldPartnerLinkId = partnerLinkId;
        partnerLinkId = newPartnerLinkId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.PARTNER__PARTNER_LINK_ID,
                    oldPartnerLinkId, partnerLinkId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RoleType getRoleType() {
        return roleType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRoleType(RoleType newRoleType) {
        RoleType oldRoleType = roleType;
        roleType = newRoleType == null ? ROLE_TYPE_EDEFAULT : newRoleType;
        boolean oldRoleTypeESet = roleTypeESet;
        roleTypeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.PARTNER__ROLE_TYPE, oldRoleType,
                    roleType, !oldRoleTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetRoleType() {
        RoleType oldRoleType = roleType;
        boolean oldRoleTypeESet = roleTypeESet;
        roleType = ROLE_TYPE_EDEFAULT;
        roleTypeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.PARTNER__ROLE_TYPE, oldRoleType,
                    ROLE_TYPE_EDEFAULT, oldRoleTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetRoleType() {
        return roleTypeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case Xpdl2Package.PARTNER__PARTNER_LINK_ID:
            return getPartnerLinkId();
        case Xpdl2Package.PARTNER__ROLE_TYPE:
            return getRoleType();
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
        case Xpdl2Package.PARTNER__PARTNER_LINK_ID:
            setPartnerLinkId((String) newValue);
            return;
        case Xpdl2Package.PARTNER__ROLE_TYPE:
            setRoleType((RoleType) newValue);
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
        case Xpdl2Package.PARTNER__PARTNER_LINK_ID:
            setPartnerLinkId(PARTNER_LINK_ID_EDEFAULT);
            return;
        case Xpdl2Package.PARTNER__ROLE_TYPE:
            unsetRoleType();
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
        case Xpdl2Package.PARTNER__PARTNER_LINK_ID:
            return PARTNER_LINK_ID_EDEFAULT == null ? partnerLinkId != null
                    : !PARTNER_LINK_ID_EDEFAULT.equals(partnerLinkId);
        case Xpdl2Package.PARTNER__ROLE_TYPE:
            return isSetRoleType();
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
        result.append(" (partnerLinkId: "); //$NON-NLS-1$
        result.append(partnerLinkId);
        result.append(", roleType: "); //$NON-NLS-1$
        if (roleTypeESet)
            result.append(roleType);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //PartnerImpl
