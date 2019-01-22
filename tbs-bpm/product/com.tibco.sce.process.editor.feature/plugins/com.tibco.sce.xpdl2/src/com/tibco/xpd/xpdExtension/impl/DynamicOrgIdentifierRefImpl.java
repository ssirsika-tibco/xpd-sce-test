/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.DynamicOrgIdentifierRef;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dynamic Org Identifier Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DynamicOrgIdentifierRefImpl#getIdentifierName <em>Identifier Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DynamicOrgIdentifierRefImpl#getDynamicOrgId <em>Dynamic Org Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DynamicOrgIdentifierRefImpl#getOrgModelPath <em>Org Model Path</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DynamicOrgIdentifierRefImpl extends EObjectImpl
        implements DynamicOrgIdentifierRef {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getIdentifierName() <em>Identifier Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIdentifierName()
     * @generated
     * @ordered
     */
    protected static final String IDENTIFIER_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIdentifierName() <em>Identifier Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIdentifierName()
     * @generated
     * @ordered
     */
    protected String identifierName = IDENTIFIER_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getDynamicOrgId() <em>Dynamic Org Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDynamicOrgId()
     * @generated
     * @ordered
     */
    protected static final String DYNAMIC_ORG_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDynamicOrgId() <em>Dynamic Org Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDynamicOrgId()
     * @generated
     * @ordered
     */
    protected String dynamicOrgId = DYNAMIC_ORG_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getOrgModelPath() <em>Org Model Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOrgModelPath()
     * @generated
     * @ordered
     */
    protected static final String ORG_MODEL_PATH_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getOrgModelPath() <em>Org Model Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOrgModelPath()
     * @generated
     * @ordered
     */
    protected String orgModelPath = ORG_MODEL_PATH_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DynamicOrgIdentifierRefImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.DYNAMIC_ORG_IDENTIFIER_REF;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getIdentifierName() {
        return identifierName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setIdentifierName(String newIdentifierName) {
        String oldIdentifierName = identifierName;
        identifierName = newIdentifierName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.DYNAMIC_ORG_IDENTIFIER_REF__IDENTIFIER_NAME,
                    oldIdentifierName, identifierName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDynamicOrgId() {
        return dynamicOrgId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDynamicOrgId(String newDynamicOrgId) {
        String oldDynamicOrgId = dynamicOrgId;
        dynamicOrgId = newDynamicOrgId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.DYNAMIC_ORG_IDENTIFIER_REF__DYNAMIC_ORG_ID,
                    oldDynamicOrgId, dynamicOrgId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getOrgModelPath() {
        return orgModelPath;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOrgModelPath(String newOrgModelPath) {
        String oldOrgModelPath = orgModelPath;
        orgModelPath = newOrgModelPath;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.DYNAMIC_ORG_IDENTIFIER_REF__ORG_MODEL_PATH,
                    oldOrgModelPath, orgModelPath));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case XpdExtensionPackage.DYNAMIC_ORG_IDENTIFIER_REF__IDENTIFIER_NAME:
            return getIdentifierName();
        case XpdExtensionPackage.DYNAMIC_ORG_IDENTIFIER_REF__DYNAMIC_ORG_ID:
            return getDynamicOrgId();
        case XpdExtensionPackage.DYNAMIC_ORG_IDENTIFIER_REF__ORG_MODEL_PATH:
            return getOrgModelPath();
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
        case XpdExtensionPackage.DYNAMIC_ORG_IDENTIFIER_REF__IDENTIFIER_NAME:
            setIdentifierName((String) newValue);
            return;
        case XpdExtensionPackage.DYNAMIC_ORG_IDENTIFIER_REF__DYNAMIC_ORG_ID:
            setDynamicOrgId((String) newValue);
            return;
        case XpdExtensionPackage.DYNAMIC_ORG_IDENTIFIER_REF__ORG_MODEL_PATH:
            setOrgModelPath((String) newValue);
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
        case XpdExtensionPackage.DYNAMIC_ORG_IDENTIFIER_REF__IDENTIFIER_NAME:
            setIdentifierName(IDENTIFIER_NAME_EDEFAULT);
            return;
        case XpdExtensionPackage.DYNAMIC_ORG_IDENTIFIER_REF__DYNAMIC_ORG_ID:
            setDynamicOrgId(DYNAMIC_ORG_ID_EDEFAULT);
            return;
        case XpdExtensionPackage.DYNAMIC_ORG_IDENTIFIER_REF__ORG_MODEL_PATH:
            setOrgModelPath(ORG_MODEL_PATH_EDEFAULT);
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
        case XpdExtensionPackage.DYNAMIC_ORG_IDENTIFIER_REF__IDENTIFIER_NAME:
            return IDENTIFIER_NAME_EDEFAULT == null ? identifierName != null
                    : !IDENTIFIER_NAME_EDEFAULT.equals(identifierName);
        case XpdExtensionPackage.DYNAMIC_ORG_IDENTIFIER_REF__DYNAMIC_ORG_ID:
            return DYNAMIC_ORG_ID_EDEFAULT == null ? dynamicOrgId != null
                    : !DYNAMIC_ORG_ID_EDEFAULT.equals(dynamicOrgId);
        case XpdExtensionPackage.DYNAMIC_ORG_IDENTIFIER_REF__ORG_MODEL_PATH:
            return ORG_MODEL_PATH_EDEFAULT == null ? orgModelPath != null
                    : !ORG_MODEL_PATH_EDEFAULT.equals(orgModelPath);
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
        result.append(" (identifierName: "); //$NON-NLS-1$
        result.append(identifierName);
        result.append(", dynamicOrgId: "); //$NON-NLS-1$
        result.append(dynamicOrgId);
        result.append(", orgModelPath: "); //$NON-NLS-1$
        result.append(orgModelPath);
        result.append(')');
        return result.toString();
    }

} //DynamicOrgIdentifierRefImpl
