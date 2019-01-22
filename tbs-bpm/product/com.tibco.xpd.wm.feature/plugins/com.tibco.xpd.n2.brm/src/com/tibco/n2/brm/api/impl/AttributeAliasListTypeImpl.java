/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.AttributeAliasListType;
import com.tibco.n2.brm.api.N2BRMPackage;

import com.tibco.n2.common.datamodel.AliasType;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute Alias List Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.AttributeAliasListTypeImpl#getAttributeAlias <em>Attribute Alias</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AttributeAliasListTypeImpl extends EObjectImpl implements AttributeAliasListType {
    /**
     * The cached value of the '{@link #getAttributeAlias() <em>Attribute Alias</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttributeAlias()
     * @generated
     * @ordered
     */
    protected EList<AliasType> attributeAlias;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AttributeAliasListTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.ATTRIBUTE_ALIAS_LIST_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<AliasType> getAttributeAlias() {
        if (attributeAlias == null) {
            attributeAlias = new EObjectContainmentEList<AliasType>(AliasType.class, this, N2BRMPackage.ATTRIBUTE_ALIAS_LIST_TYPE__ATTRIBUTE_ALIAS);
        }
        return attributeAlias;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.ATTRIBUTE_ALIAS_LIST_TYPE__ATTRIBUTE_ALIAS:
                return ((InternalEList<?>)getAttributeAlias()).basicRemove(otherEnd, msgs);
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
            case N2BRMPackage.ATTRIBUTE_ALIAS_LIST_TYPE__ATTRIBUTE_ALIAS:
                return getAttributeAlias();
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
            case N2BRMPackage.ATTRIBUTE_ALIAS_LIST_TYPE__ATTRIBUTE_ALIAS:
                getAttributeAlias().clear();
                getAttributeAlias().addAll((Collection<? extends AliasType>)newValue);
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
            case N2BRMPackage.ATTRIBUTE_ALIAS_LIST_TYPE__ATTRIBUTE_ALIAS:
                getAttributeAlias().clear();
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
            case N2BRMPackage.ATTRIBUTE_ALIAS_LIST_TYPE__ATTRIBUTE_ALIAS:
                return attributeAlias != null && !attributeAlias.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //AttributeAliasListTypeImpl
