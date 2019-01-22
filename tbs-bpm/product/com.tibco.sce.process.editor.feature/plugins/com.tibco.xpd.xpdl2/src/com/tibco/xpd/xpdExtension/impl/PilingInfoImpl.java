/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.PilingInfo;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import java.math.BigInteger;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Piling Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.PilingInfoImpl#isPilingAllowed <em>Piling Allowed</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.PilingInfoImpl#getMaxPiliableItems <em>Max Piliable Items</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PilingInfoImpl extends EObjectImpl implements PilingInfo {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #isPilingAllowed() <em>Piling Allowed</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isPilingAllowed()
     * @generated
     * @ordered
     */
    protected static final boolean PILING_ALLOWED_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isPilingAllowed() <em>Piling Allowed</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isPilingAllowed()
     * @generated
     * @ordered
     */
    protected boolean pilingAllowed = PILING_ALLOWED_EDEFAULT;

    /**
     * This is true if the Piling Allowed attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean pilingAllowedESet;

    /**
     * The default value of the '{@link #getMaxPiliableItems() <em>Max Piliable Items</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMaxPiliableItems()
     * @generated
     * @ordered
     */
    protected static final String MAX_PILIABLE_ITEMS_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getMaxPiliableItems() <em>Max Piliable Items</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMaxPiliableItems()
     * @generated
     * @ordered
     */
    protected String maxPiliableItems = MAX_PILIABLE_ITEMS_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PilingInfoImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.PILING_INFO;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isPilingAllowed() {
        return pilingAllowed;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPilingAllowed(boolean newPilingAllowed) {
        boolean oldPilingAllowed = pilingAllowed;
        pilingAllowed = newPilingAllowed;
        boolean oldPilingAllowedESet = pilingAllowedESet;
        pilingAllowedESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.PILING_INFO__PILING_ALLOWED,
                    oldPilingAllowed, pilingAllowed, !oldPilingAllowedESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetPilingAllowed() {
        boolean oldPilingAllowed = pilingAllowed;
        boolean oldPilingAllowedESet = pilingAllowedESet;
        pilingAllowed = PILING_ALLOWED_EDEFAULT;
        pilingAllowedESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.PILING_INFO__PILING_ALLOWED,
                    oldPilingAllowed, PILING_ALLOWED_EDEFAULT,
                    oldPilingAllowedESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetPilingAllowed() {
        return pilingAllowedESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getMaxPiliableItems() {
        return maxPiliableItems;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMaxPiliableItems(String newMaxPiliableItems) {
        String oldMaxPiliableItems = maxPiliableItems;
        maxPiliableItems = newMaxPiliableItems;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.PILING_INFO__MAX_PILIABLE_ITEMS,
                    oldMaxPiliableItems, maxPiliableItems));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case XpdExtensionPackage.PILING_INFO__PILING_ALLOWED:
            return isPilingAllowed();
        case XpdExtensionPackage.PILING_INFO__MAX_PILIABLE_ITEMS:
            return getMaxPiliableItems();
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
        case XpdExtensionPackage.PILING_INFO__PILING_ALLOWED:
            setPilingAllowed((Boolean) newValue);
            return;
        case XpdExtensionPackage.PILING_INFO__MAX_PILIABLE_ITEMS:
            setMaxPiliableItems((String) newValue);
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
        case XpdExtensionPackage.PILING_INFO__PILING_ALLOWED:
            unsetPilingAllowed();
            return;
        case XpdExtensionPackage.PILING_INFO__MAX_PILIABLE_ITEMS:
            setMaxPiliableItems(MAX_PILIABLE_ITEMS_EDEFAULT);
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
        case XpdExtensionPackage.PILING_INFO__PILING_ALLOWED:
            return isSetPilingAllowed();
        case XpdExtensionPackage.PILING_INFO__MAX_PILIABLE_ITEMS:
            return MAX_PILIABLE_ITEMS_EDEFAULT == null
                    ? maxPiliableItems != null
                    : !MAX_PILIABLE_ITEMS_EDEFAULT.equals(maxPiliableItems);
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
        result.append(" (pilingAllowed: "); //$NON-NLS-1$
        if (pilingAllowedESet)
            result.append(pilingAllowed);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", maxPiliableItems: "); //$NON-NLS-1$
        result.append(maxPiliableItems);
        result.append(')');
        return result.toString();
    }

} //PilingInfoImpl
