/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.OrgEntityConfigAttributesAvailable;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Org Entity Config Attributes Available</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.OrgEntityConfigAttributesAvailableImpl#getAttributeName <em>Attribute Name</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.OrgEntityConfigAttributesAvailableImpl#isReadOnly <em>Read Only</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OrgEntityConfigAttributesAvailableImpl extends EObjectImpl implements OrgEntityConfigAttributesAvailable {
    /**
     * The default value of the '{@link #getAttributeName() <em>Attribute Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttributeName()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttributeName() <em>Attribute Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttributeName()
     * @generated
     * @ordered
     */
    protected String attributeName = ATTRIBUTE_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #isReadOnly() <em>Read Only</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isReadOnly()
     * @generated
     * @ordered
     */
    protected static final boolean READ_ONLY_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isReadOnly() <em>Read Only</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isReadOnly()
     * @generated
     * @ordered
     */
    protected boolean readOnly = READ_ONLY_EDEFAULT;

    /**
     * This is true if the Read Only attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean readOnlyESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected OrgEntityConfigAttributesAvailableImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttributeName() {
        return attributeName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttributeName(String newAttributeName) {
        String oldAttributeName = attributeName;
        attributeName = newAttributeName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE__ATTRIBUTE_NAME, oldAttributeName, attributeName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isReadOnly() {
        return readOnly;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setReadOnly(boolean newReadOnly) {
        boolean oldReadOnly = readOnly;
        readOnly = newReadOnly;
        boolean oldReadOnlyESet = readOnlyESet;
        readOnlyESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE__READ_ONLY, oldReadOnly, readOnly, !oldReadOnlyESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetReadOnly() {
        boolean oldReadOnly = readOnly;
        boolean oldReadOnlyESet = readOnlyESet;
        readOnly = READ_ONLY_EDEFAULT;
        readOnlyESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE__READ_ONLY, oldReadOnly, READ_ONLY_EDEFAULT, oldReadOnlyESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetReadOnly() {
        return readOnlyESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case N2BRMPackage.ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE__ATTRIBUTE_NAME:
                return getAttributeName();
            case N2BRMPackage.ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE__READ_ONLY:
                return isReadOnly();
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
            case N2BRMPackage.ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE__ATTRIBUTE_NAME:
                setAttributeName((String)newValue);
                return;
            case N2BRMPackage.ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE__READ_ONLY:
                setReadOnly((Boolean)newValue);
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
            case N2BRMPackage.ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE__ATTRIBUTE_NAME:
                setAttributeName(ATTRIBUTE_NAME_EDEFAULT);
                return;
            case N2BRMPackage.ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE__READ_ONLY:
                unsetReadOnly();
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
            case N2BRMPackage.ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE__ATTRIBUTE_NAME:
                return ATTRIBUTE_NAME_EDEFAULT == null ? attributeName != null : !ATTRIBUTE_NAME_EDEFAULT.equals(attributeName);
            case N2BRMPackage.ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE__READ_ONLY:
                return isSetReadOnly();
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
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (attributeName: ");
        result.append(attributeName);
        result.append(", readOnly: ");
        if (readOnlyESet) result.append(readOnly); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //OrgEntityConfigAttributesAvailableImpl
