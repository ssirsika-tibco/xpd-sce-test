/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.CompositeIdentifierType;
import com.tibco.xpd.xpdExtension.DeleteByCompositeIdentifiersType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Delete By Composite Identifiers Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DeleteByCompositeIdentifiersTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DeleteByCompositeIdentifiersTypeImpl#getCompositeIdentifier <em>Composite Identifier</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DeleteByCompositeIdentifiersTypeImpl extends EObjectImpl implements DeleteByCompositeIdentifiersType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getGroup() <em>Group</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGroup()
     * @generated
     * @ordered
     */
    protected FeatureMap group;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DeleteByCompositeIdentifiersTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.DELETE_BY_COMPOSITE_IDENTIFIERS_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getGroup() {
        if (group == null) {
            group = new BasicFeatureMap(this, XpdExtensionPackage.DELETE_BY_COMPOSITE_IDENTIFIERS_TYPE__GROUP);
        }
        return group;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<CompositeIdentifierType> getCompositeIdentifier() {
        return getGroup().list(XpdExtensionPackage.Literals.DELETE_BY_COMPOSITE_IDENTIFIERS_TYPE__COMPOSITE_IDENTIFIER);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case XpdExtensionPackage.DELETE_BY_COMPOSITE_IDENTIFIERS_TYPE__GROUP:
            return ((InternalEList<?>) getGroup()).basicRemove(otherEnd, msgs);
        case XpdExtensionPackage.DELETE_BY_COMPOSITE_IDENTIFIERS_TYPE__COMPOSITE_IDENTIFIER:
            return ((InternalEList<?>) getCompositeIdentifier()).basicRemove(otherEnd, msgs);
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
        case XpdExtensionPackage.DELETE_BY_COMPOSITE_IDENTIFIERS_TYPE__GROUP:
            if (coreType)
                return getGroup();
            return ((FeatureMap.Internal) getGroup()).getWrapper();
        case XpdExtensionPackage.DELETE_BY_COMPOSITE_IDENTIFIERS_TYPE__COMPOSITE_IDENTIFIER:
            return getCompositeIdentifier();
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
        case XpdExtensionPackage.DELETE_BY_COMPOSITE_IDENTIFIERS_TYPE__GROUP:
            ((FeatureMap.Internal) getGroup()).set(newValue);
            return;
        case XpdExtensionPackage.DELETE_BY_COMPOSITE_IDENTIFIERS_TYPE__COMPOSITE_IDENTIFIER:
            getCompositeIdentifier().clear();
            getCompositeIdentifier().addAll((Collection<? extends CompositeIdentifierType>) newValue);
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
        case XpdExtensionPackage.DELETE_BY_COMPOSITE_IDENTIFIERS_TYPE__GROUP:
            getGroup().clear();
            return;
        case XpdExtensionPackage.DELETE_BY_COMPOSITE_IDENTIFIERS_TYPE__COMPOSITE_IDENTIFIER:
            getCompositeIdentifier().clear();
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
        case XpdExtensionPackage.DELETE_BY_COMPOSITE_IDENTIFIERS_TYPE__GROUP:
            return group != null && !group.isEmpty();
        case XpdExtensionPackage.DELETE_BY_COMPOSITE_IDENTIFIERS_TYPE__COMPOSITE_IDENTIFIER:
            return !getCompositeIdentifier().isEmpty();
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
        result.append(" (group: "); //$NON-NLS-1$
        result.append(group);
        result.append(')');
        return result.toString();
    }

} //DeleteByCompositeIdentifiersTypeImpl
