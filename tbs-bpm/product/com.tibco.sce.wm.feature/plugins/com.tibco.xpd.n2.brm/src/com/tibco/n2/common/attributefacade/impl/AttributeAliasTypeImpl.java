/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.attributefacade.impl;

import com.tibco.n2.common.attributefacade.AttributeAliasType;
import com.tibco.n2.common.attributefacade.AttributefacadePackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute Alias Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.common.attributefacade.impl.AttributeAliasTypeImpl#getAttributeAlias <em>Attribute Alias</em>}</li>
 *   <li>{@link com.tibco.n2.common.attributefacade.impl.AttributeAliasTypeImpl#getAttributeName <em>Attribute Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AttributeAliasTypeImpl extends EObjectImpl implements AttributeAliasType {
    /**
     * The default value of the '{@link #getAttributeAlias() <em>Attribute Alias</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttributeAlias()
     * @generated
     * @ordered
     */
    protected static final Object ATTRIBUTE_ALIAS_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttributeAlias() <em>Attribute Alias</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttributeAlias()
     * @generated
     * @ordered
     */
    protected Object attributeAlias = ATTRIBUTE_ALIAS_EDEFAULT;

    /**
     * The default value of the '{@link #getAttributeName() <em>Attribute Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttributeName()
     * @generated
     * @ordered
     */
    protected static final Object ATTRIBUTE_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttributeName() <em>Attribute Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttributeName()
     * @generated
     * @ordered
     */
    protected Object attributeName = ATTRIBUTE_NAME_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AttributeAliasTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return AttributefacadePackage.Literals.ATTRIBUTE_ALIAS_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Object getAttributeAlias() {
        return attributeAlias;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttributeAlias(Object newAttributeAlias) {
        Object oldAttributeAlias = attributeAlias;
        attributeAlias = newAttributeAlias;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, AttributefacadePackage.ATTRIBUTE_ALIAS_TYPE__ATTRIBUTE_ALIAS, oldAttributeAlias, attributeAlias));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Object getAttributeName() {
        return attributeName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttributeName(Object newAttributeName) {
        Object oldAttributeName = attributeName;
        attributeName = newAttributeName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, AttributefacadePackage.ATTRIBUTE_ALIAS_TYPE__ATTRIBUTE_NAME, oldAttributeName, attributeName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case AttributefacadePackage.ATTRIBUTE_ALIAS_TYPE__ATTRIBUTE_ALIAS:
                return getAttributeAlias();
            case AttributefacadePackage.ATTRIBUTE_ALIAS_TYPE__ATTRIBUTE_NAME:
                return getAttributeName();
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
            case AttributefacadePackage.ATTRIBUTE_ALIAS_TYPE__ATTRIBUTE_ALIAS:
                setAttributeAlias(newValue);
                return;
            case AttributefacadePackage.ATTRIBUTE_ALIAS_TYPE__ATTRIBUTE_NAME:
                setAttributeName(newValue);
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
            case AttributefacadePackage.ATTRIBUTE_ALIAS_TYPE__ATTRIBUTE_ALIAS:
                setAttributeAlias(ATTRIBUTE_ALIAS_EDEFAULT);
                return;
            case AttributefacadePackage.ATTRIBUTE_ALIAS_TYPE__ATTRIBUTE_NAME:
                setAttributeName(ATTRIBUTE_NAME_EDEFAULT);
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
            case AttributefacadePackage.ATTRIBUTE_ALIAS_TYPE__ATTRIBUTE_ALIAS:
                return ATTRIBUTE_ALIAS_EDEFAULT == null ? attributeAlias != null : !ATTRIBUTE_ALIAS_EDEFAULT.equals(attributeAlias);
            case AttributefacadePackage.ATTRIBUTE_ALIAS_TYPE__ATTRIBUTE_NAME:
                return ATTRIBUTE_NAME_EDEFAULT == null ? attributeName != null : !ATTRIBUTE_NAME_EDEFAULT.equals(attributeName);
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
        result.append(" (attributeAlias: ");
        result.append(attributeAlias);
        result.append(", attributeName: ");
        result.append(attributeName);
        result.append(')');
        return result.toString();
    }

} //AttributeAliasTypeImpl
