/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.DynamicOrgIdentifierRef;
import com.tibco.xpd.xpdExtension.DynamicOrganizationMapping;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dynamic Organization Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DynamicOrganizationMappingImpl#getSourcePath <em>Source Path</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DynamicOrganizationMappingImpl#getDynamicOrgIdentifierRef <em>Dynamic Org Identifier Ref</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DynamicOrganizationMappingImpl extends EObjectImpl implements DynamicOrganizationMapping {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getSourcePath() <em>Source Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSourcePath()
     * @generated
     * @ordered
     */
    protected static final String SOURCE_PATH_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getSourcePath() <em>Source Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSourcePath()
     * @generated
     * @ordered
     */
    protected String sourcePath = SOURCE_PATH_EDEFAULT;

    /**
     * The cached value of the '{@link #getDynamicOrgIdentifierRef() <em>Dynamic Org Identifier Ref</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDynamicOrgIdentifierRef()
     * @generated
     * @ordered
     */
    protected DynamicOrgIdentifierRef dynamicOrgIdentifierRef;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DynamicOrganizationMappingImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.DYNAMIC_ORGANIZATION_MAPPING;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getSourcePath() {
        return sourcePath;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSourcePath(String newSourcePath) {
        String oldSourcePath = sourcePath;
        sourcePath = newSourcePath;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.DYNAMIC_ORGANIZATION_MAPPING__SOURCE_PATH, oldSourcePath, sourcePath));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DynamicOrgIdentifierRef getDynamicOrgIdentifierRef() {
        return dynamicOrgIdentifierRef;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDynamicOrgIdentifierRef(DynamicOrgIdentifierRef newDynamicOrgIdentifierRef,
            NotificationChain msgs) {
        DynamicOrgIdentifierRef oldDynamicOrgIdentifierRef = dynamicOrgIdentifierRef;
        dynamicOrgIdentifierRef = newDynamicOrgIdentifierRef;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.DYNAMIC_ORGANIZATION_MAPPING__DYNAMIC_ORG_IDENTIFIER_REF,
                    oldDynamicOrgIdentifierRef, newDynamicOrgIdentifierRef);
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
    public void setDynamicOrgIdentifierRef(DynamicOrgIdentifierRef newDynamicOrgIdentifierRef) {
        if (newDynamicOrgIdentifierRef != dynamicOrgIdentifierRef) {
            NotificationChain msgs = null;
            if (dynamicOrgIdentifierRef != null)
                msgs = ((InternalEObject) dynamicOrgIdentifierRef).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.DYNAMIC_ORGANIZATION_MAPPING__DYNAMIC_ORG_IDENTIFIER_REF,
                        null,
                        msgs);
            if (newDynamicOrgIdentifierRef != null)
                msgs = ((InternalEObject) newDynamicOrgIdentifierRef).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.DYNAMIC_ORGANIZATION_MAPPING__DYNAMIC_ORG_IDENTIFIER_REF,
                        null,
                        msgs);
            msgs = basicSetDynamicOrgIdentifierRef(newDynamicOrgIdentifierRef, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.DYNAMIC_ORGANIZATION_MAPPING__DYNAMIC_ORG_IDENTIFIER_REF,
                    newDynamicOrgIdentifierRef, newDynamicOrgIdentifierRef));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case XpdExtensionPackage.DYNAMIC_ORGANIZATION_MAPPING__DYNAMIC_ORG_IDENTIFIER_REF:
            return basicSetDynamicOrgIdentifierRef(null, msgs);
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
        case XpdExtensionPackage.DYNAMIC_ORGANIZATION_MAPPING__SOURCE_PATH:
            return getSourcePath();
        case XpdExtensionPackage.DYNAMIC_ORGANIZATION_MAPPING__DYNAMIC_ORG_IDENTIFIER_REF:
            return getDynamicOrgIdentifierRef();
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
        case XpdExtensionPackage.DYNAMIC_ORGANIZATION_MAPPING__SOURCE_PATH:
            setSourcePath((String) newValue);
            return;
        case XpdExtensionPackage.DYNAMIC_ORGANIZATION_MAPPING__DYNAMIC_ORG_IDENTIFIER_REF:
            setDynamicOrgIdentifierRef((DynamicOrgIdentifierRef) newValue);
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
        case XpdExtensionPackage.DYNAMIC_ORGANIZATION_MAPPING__SOURCE_PATH:
            setSourcePath(SOURCE_PATH_EDEFAULT);
            return;
        case XpdExtensionPackage.DYNAMIC_ORGANIZATION_MAPPING__DYNAMIC_ORG_IDENTIFIER_REF:
            setDynamicOrgIdentifierRef((DynamicOrgIdentifierRef) null);
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
        case XpdExtensionPackage.DYNAMIC_ORGANIZATION_MAPPING__SOURCE_PATH:
            return SOURCE_PATH_EDEFAULT == null ? sourcePath != null : !SOURCE_PATH_EDEFAULT.equals(sourcePath);
        case XpdExtensionPackage.DYNAMIC_ORGANIZATION_MAPPING__DYNAMIC_ORG_IDENTIFIER_REF:
            return dynamicOrgIdentifierRef != null;
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
        result.append(" (sourcePath: "); //$NON-NLS-1$
        result.append(sourcePath);
        result.append(')');
        return result.toString();
    }

} //DynamicOrganizationMappingImpl
