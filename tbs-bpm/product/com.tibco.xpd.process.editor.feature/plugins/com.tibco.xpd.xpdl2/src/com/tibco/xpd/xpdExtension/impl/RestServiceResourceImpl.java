/**
 * Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.RestServiceResource;
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
 * An implementation of the model object '<em><b>Rest Service Resource</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.RestServiceResourceImpl#getSecurityPolicy <em>Security Policy</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.RestServiceResourceImpl#getResourceName <em>Resource Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.RestServiceResourceImpl#getResourceType <em>Resource Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.RestServiceResourceImpl#getDescription <em>Description</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RestServiceResourceImpl extends EObjectImpl
        implements RestServiceResource {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getSecurityPolicy() <em>Security Policy</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSecurityPolicy()
     * @generated
     * @ordered
     */
    protected EList<RestServiceResourceSecurity> securityPolicy;

    /**
     * The default value of the '{@link #getResourceName() <em>Resource Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResourceName()
     * @generated
     * @ordered
     */
    protected static final String RESOURCE_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getResourceName() <em>Resource Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResourceName()
     * @generated
     * @ordered
     */
    protected String resourceName = RESOURCE_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getResourceType() <em>Resource Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResourceType()
     * @generated
     * @ordered
     */
    protected static final String RESOURCE_TYPE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getResourceType() <em>Resource Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResourceType()
     * @generated
     * @ordered
     */
    protected String resourceType = RESOURCE_TYPE_EDEFAULT;

    /**
     * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected static final String DESCRIPTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected String description = DESCRIPTION_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected RestServiceResourceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.REST_SERVICE_RESOURCE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<RestServiceResourceSecurity> getSecurityPolicy() {
        if (securityPolicy == null) {
            securityPolicy =
                    new EObjectContainmentEList<RestServiceResourceSecurity>(
                            RestServiceResourceSecurity.class, this,
                            XpdExtensionPackage.REST_SERVICE_RESOURCE__SECURITY_POLICY);
        }
        return securityPolicy;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setResourceName(String newResourceName) {
        String oldResourceName = resourceName;
        resourceName = newResourceName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.REST_SERVICE_RESOURCE__RESOURCE_NAME,
                    oldResourceName, resourceName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getResourceType() {
        return resourceType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setResourceType(String newResourceType) {
        String oldResourceType = resourceType;
        resourceType = newResourceType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.REST_SERVICE_RESOURCE__RESOURCE_TYPE,
                    oldResourceType, resourceType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDescription() {
        return description;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDescription(String newDescription) {
        String oldDescription = description;
        description = newDescription;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.REST_SERVICE_RESOURCE__DESCRIPTION,
                    oldDescription, description));
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
        case XpdExtensionPackage.REST_SERVICE_RESOURCE__SECURITY_POLICY:
            return ((InternalEList<?>) getSecurityPolicy())
                    .basicRemove(otherEnd, msgs);
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
        case XpdExtensionPackage.REST_SERVICE_RESOURCE__SECURITY_POLICY:
            return getSecurityPolicy();
        case XpdExtensionPackage.REST_SERVICE_RESOURCE__RESOURCE_NAME:
            return getResourceName();
        case XpdExtensionPackage.REST_SERVICE_RESOURCE__RESOURCE_TYPE:
            return getResourceType();
        case XpdExtensionPackage.REST_SERVICE_RESOURCE__DESCRIPTION:
            return getDescription();
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
        case XpdExtensionPackage.REST_SERVICE_RESOURCE__SECURITY_POLICY:
            getSecurityPolicy().clear();
            getSecurityPolicy().addAll(
                    (Collection<? extends RestServiceResourceSecurity>) newValue);
            return;
        case XpdExtensionPackage.REST_SERVICE_RESOURCE__RESOURCE_NAME:
            setResourceName((String) newValue);
            return;
        case XpdExtensionPackage.REST_SERVICE_RESOURCE__RESOURCE_TYPE:
            setResourceType((String) newValue);
            return;
        case XpdExtensionPackage.REST_SERVICE_RESOURCE__DESCRIPTION:
            setDescription((String) newValue);
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
        case XpdExtensionPackage.REST_SERVICE_RESOURCE__SECURITY_POLICY:
            getSecurityPolicy().clear();
            return;
        case XpdExtensionPackage.REST_SERVICE_RESOURCE__RESOURCE_NAME:
            setResourceName(RESOURCE_NAME_EDEFAULT);
            return;
        case XpdExtensionPackage.REST_SERVICE_RESOURCE__RESOURCE_TYPE:
            setResourceType(RESOURCE_TYPE_EDEFAULT);
            return;
        case XpdExtensionPackage.REST_SERVICE_RESOURCE__DESCRIPTION:
            setDescription(DESCRIPTION_EDEFAULT);
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
        case XpdExtensionPackage.REST_SERVICE_RESOURCE__SECURITY_POLICY:
            return securityPolicy != null && !securityPolicy.isEmpty();
        case XpdExtensionPackage.REST_SERVICE_RESOURCE__RESOURCE_NAME:
            return RESOURCE_NAME_EDEFAULT == null ? resourceName != null
                    : !RESOURCE_NAME_EDEFAULT.equals(resourceName);
        case XpdExtensionPackage.REST_SERVICE_RESOURCE__RESOURCE_TYPE:
            return RESOURCE_TYPE_EDEFAULT == null ? resourceType != null
                    : !RESOURCE_TYPE_EDEFAULT.equals(resourceType);
        case XpdExtensionPackage.REST_SERVICE_RESOURCE__DESCRIPTION:
            return DESCRIPTION_EDEFAULT == null ? description != null
                    : !DESCRIPTION_EDEFAULT.equals(description);
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
        result.append(" (resourceName: "); //$NON-NLS-1$
        result.append(resourceName);
        result.append(", resourceType: "); //$NON-NLS-1$
        result.append(resourceType);
        result.append(", description: "); //$NON-NLS-1$
        result.append(description);
        result.append(')');
        return result.toString();
    }

} //RestServiceResourceImpl
