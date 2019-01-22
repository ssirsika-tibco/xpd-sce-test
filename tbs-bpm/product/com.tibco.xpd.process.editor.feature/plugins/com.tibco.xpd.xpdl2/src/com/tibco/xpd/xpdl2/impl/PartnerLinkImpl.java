/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.xpd.xpdl2.MyRole;
import com.tibco.xpd.xpdl2.PartnerLink;
import com.tibco.xpd.xpdl2.PartnerRole;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Partner Link</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PartnerLinkImpl#getMyRole <em>My Role</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PartnerLinkImpl#getPartnerRole <em>Partner Role</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PartnerLinkImpl#getPartnerLinkTypeId <em>Partner Link Type Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PartnerLinkImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PartnerLinkImpl extends UniqueIdElementImpl implements PartnerLink {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getMyRole() <em>My Role</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMyRole()
     * @generated
     * @ordered
     */
    protected MyRole myRole;

    /**
     * The cached value of the '{@link #getPartnerRole() <em>Partner Role</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPartnerRole()
     * @generated
     * @ordered
     */
    protected PartnerRole partnerRole;

    /**
     * The default value of the '{@link #getPartnerLinkTypeId() <em>Partner Link Type Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPartnerLinkTypeId()
     * @generated
     * @ordered
     */
    protected static final String PARTNER_LINK_TYPE_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPartnerLinkTypeId() <em>Partner Link Type Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPartnerLinkTypeId()
     * @generated
     * @ordered
     */
    protected String partnerLinkTypeId = PARTNER_LINK_TYPE_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PartnerLinkImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.PARTNER_LINK;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MyRole getMyRole() {
        return myRole;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetMyRole(MyRole newMyRole,
            NotificationChain msgs) {
        MyRole oldMyRole = myRole;
        myRole = newMyRole;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.PARTNER_LINK__MY_ROLE, oldMyRole,
                            newMyRole);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMyRole(MyRole newMyRole) {
        if (newMyRole != myRole) {
            NotificationChain msgs = null;
            if (myRole != null)
                msgs =
                        ((InternalEObject) myRole).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.PARTNER_LINK__MY_ROLE,
                                null,
                                msgs);
            if (newMyRole != null)
                msgs =
                        ((InternalEObject) newMyRole).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.PARTNER_LINK__MY_ROLE,
                                null,
                                msgs);
            msgs = basicSetMyRole(newMyRole, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PARTNER_LINK__MY_ROLE, newMyRole, newMyRole));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PartnerRole getPartnerRole() {
        return partnerRole;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPartnerRole(PartnerRole newPartnerRole,
            NotificationChain msgs) {
        PartnerRole oldPartnerRole = partnerRole;
        partnerRole = newPartnerRole;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.PARTNER_LINK__PARTNER_ROLE,
                            oldPartnerRole, newPartnerRole);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPartnerRole(PartnerRole newPartnerRole) {
        if (newPartnerRole != partnerRole) {
            NotificationChain msgs = null;
            if (partnerRole != null)
                msgs =
                        ((InternalEObject) partnerRole)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.PARTNER_LINK__PARTNER_ROLE,
                                        null,
                                        msgs);
            if (newPartnerRole != null)
                msgs =
                        ((InternalEObject) newPartnerRole)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.PARTNER_LINK__PARTNER_ROLE,
                                        null,
                                        msgs);
            msgs = basicSetPartnerRole(newPartnerRole, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PARTNER_LINK__PARTNER_ROLE, newPartnerRole,
                    newPartnerRole));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getPartnerLinkTypeId() {
        return partnerLinkTypeId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPartnerLinkTypeId(String newPartnerLinkTypeId) {
        String oldPartnerLinkTypeId = partnerLinkTypeId;
        partnerLinkTypeId = newPartnerLinkTypeId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PARTNER_LINK__PARTNER_LINK_TYPE_ID,
                    oldPartnerLinkTypeId, partnerLinkTypeId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PARTNER_LINK__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.PARTNER_LINK__MY_ROLE:
            return basicSetMyRole(null, msgs);
        case Xpdl2Package.PARTNER_LINK__PARTNER_ROLE:
            return basicSetPartnerRole(null, msgs);
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
        case Xpdl2Package.PARTNER_LINK__MY_ROLE:
            return getMyRole();
        case Xpdl2Package.PARTNER_LINK__PARTNER_ROLE:
            return getPartnerRole();
        case Xpdl2Package.PARTNER_LINK__PARTNER_LINK_TYPE_ID:
            return getPartnerLinkTypeId();
        case Xpdl2Package.PARTNER_LINK__NAME:
            return getName();
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
        case Xpdl2Package.PARTNER_LINK__MY_ROLE:
            setMyRole((MyRole) newValue);
            return;
        case Xpdl2Package.PARTNER_LINK__PARTNER_ROLE:
            setPartnerRole((PartnerRole) newValue);
            return;
        case Xpdl2Package.PARTNER_LINK__PARTNER_LINK_TYPE_ID:
            setPartnerLinkTypeId((String) newValue);
            return;
        case Xpdl2Package.PARTNER_LINK__NAME:
            setName((String) newValue);
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
        case Xpdl2Package.PARTNER_LINK__MY_ROLE:
            setMyRole((MyRole) null);
            return;
        case Xpdl2Package.PARTNER_LINK__PARTNER_ROLE:
            setPartnerRole((PartnerRole) null);
            return;
        case Xpdl2Package.PARTNER_LINK__PARTNER_LINK_TYPE_ID:
            setPartnerLinkTypeId(PARTNER_LINK_TYPE_ID_EDEFAULT);
            return;
        case Xpdl2Package.PARTNER_LINK__NAME:
            setName(NAME_EDEFAULT);
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
        case Xpdl2Package.PARTNER_LINK__MY_ROLE:
            return myRole != null;
        case Xpdl2Package.PARTNER_LINK__PARTNER_ROLE:
            return partnerRole != null;
        case Xpdl2Package.PARTNER_LINK__PARTNER_LINK_TYPE_ID:
            return PARTNER_LINK_TYPE_ID_EDEFAULT == null ? partnerLinkTypeId != null
                    : !PARTNER_LINK_TYPE_ID_EDEFAULT.equals(partnerLinkTypeId);
        case Xpdl2Package.PARTNER_LINK__NAME:
            return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
                    .equals(name);
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
        result.append(" (partnerLinkTypeId: "); //$NON-NLS-1$
        result.append(partnerLinkTypeId);
        result.append(", name: "); //$NON-NLS-1$
        result.append(name);
        result.append(')');
        return result.toString();
    }

} //PartnerLinkImpl
