/**
 * Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.RestServiceResourceSecurity;
import com.tibco.xpd.xpdExtension.SecurityPolicy;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import com.tibco.xpd.xpdl2.ExtendedAttribute;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rest Service Resource Security</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.RestServiceResourceSecurityImpl#getExtendedAttributes <em>Extended Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.RestServiceResourceSecurityImpl#getPolicyType <em>Policy Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RestServiceResourceSecurityImpl extends EObjectImpl implements RestServiceResourceSecurity {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getExtendedAttributes() <em>Extended Attributes</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExtendedAttributes()
     * @generated
     * @ordered
     */
    protected EList<ExtendedAttribute> extendedAttributes;

    /**
     * The default value of the '{@link #getPolicyType() <em>Policy Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPolicyType()
     * @generated
     * @ordered
     */
    protected static final SecurityPolicy POLICY_TYPE_EDEFAULT = SecurityPolicy.USERNAME_TOKEN;

    /**
     * The cached value of the '{@link #getPolicyType() <em>Policy Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPolicyType()
     * @generated
     * @ordered
     */
    protected SecurityPolicy policyType = POLICY_TYPE_EDEFAULT;

    /**
     * This is true if the Policy Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean policyTypeESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected RestServiceResourceSecurityImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.REST_SERVICE_RESOURCE_SECURITY;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ExtendedAttribute> getExtendedAttributes() {
        if (extendedAttributes == null) {
            extendedAttributes = new EObjectContainmentEList<ExtendedAttribute>(ExtendedAttribute.class, this,
                    XpdExtensionPackage.REST_SERVICE_RESOURCE_SECURITY__EXTENDED_ATTRIBUTES);
        }
        return extendedAttributes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SecurityPolicy getPolicyType() {
        return policyType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPolicyType(SecurityPolicy newPolicyType) {
        SecurityPolicy oldPolicyType = policyType;
        policyType = newPolicyType == null ? POLICY_TYPE_EDEFAULT : newPolicyType;
        boolean oldPolicyTypeESet = policyTypeESet;
        policyTypeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.REST_SERVICE_RESOURCE_SECURITY__POLICY_TYPE, oldPolicyType, policyType,
                    !oldPolicyTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetPolicyType() {
        SecurityPolicy oldPolicyType = policyType;
        boolean oldPolicyTypeESet = policyTypeESet;
        policyType = POLICY_TYPE_EDEFAULT;
        policyTypeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.REST_SERVICE_RESOURCE_SECURITY__POLICY_TYPE, oldPolicyType,
                    POLICY_TYPE_EDEFAULT, oldPolicyTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetPolicyType() {
        return policyTypeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case XpdExtensionPackage.REST_SERVICE_RESOURCE_SECURITY__EXTENDED_ATTRIBUTES:
            return ((InternalEList<?>) getExtendedAttributes()).basicRemove(otherEnd, msgs);
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
        case XpdExtensionPackage.REST_SERVICE_RESOURCE_SECURITY__EXTENDED_ATTRIBUTES:
            return getExtendedAttributes();
        case XpdExtensionPackage.REST_SERVICE_RESOURCE_SECURITY__POLICY_TYPE:
            return getPolicyType();
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
        case XpdExtensionPackage.REST_SERVICE_RESOURCE_SECURITY__EXTENDED_ATTRIBUTES:
            getExtendedAttributes().clear();
            getExtendedAttributes().addAll((Collection<? extends ExtendedAttribute>) newValue);
            return;
        case XpdExtensionPackage.REST_SERVICE_RESOURCE_SECURITY__POLICY_TYPE:
            setPolicyType((SecurityPolicy) newValue);
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
        case XpdExtensionPackage.REST_SERVICE_RESOURCE_SECURITY__EXTENDED_ATTRIBUTES:
            getExtendedAttributes().clear();
            return;
        case XpdExtensionPackage.REST_SERVICE_RESOURCE_SECURITY__POLICY_TYPE:
            unsetPolicyType();
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
        case XpdExtensionPackage.REST_SERVICE_RESOURCE_SECURITY__EXTENDED_ATTRIBUTES:
            return extendedAttributes != null && !extendedAttributes.isEmpty();
        case XpdExtensionPackage.REST_SERVICE_RESOURCE_SECURITY__POLICY_TYPE:
            return isSetPolicyType();
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
        result.append(" (policyType: "); //$NON-NLS-1$
        if (policyTypeESet)
            result.append(policyType);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //RestServiceResourceSecurityImpl
