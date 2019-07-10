/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.DynamicOrganizationMapping;
import com.tibco.xpd.xpdExtension.DynamicOrganizationMappings;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

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
 * An implementation of the model object '<em><b>Dynamic Organization Mappings</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DynamicOrganizationMappingsImpl#getDynamicOrganizationMapping <em>Dynamic Organization Mapping</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DynamicOrganizationMappingsImpl extends EObjectImpl implements DynamicOrganizationMappings {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getDynamicOrganizationMapping() <em>Dynamic Organization Mapping</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDynamicOrganizationMapping()
     * @generated
     * @ordered
     */
    protected EList<DynamicOrganizationMapping> dynamicOrganizationMapping;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DynamicOrganizationMappingsImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.DYNAMIC_ORGANIZATION_MAPPINGS;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<DynamicOrganizationMapping> getDynamicOrganizationMapping() {
        if (dynamicOrganizationMapping == null) {
            dynamicOrganizationMapping =
                    new EObjectContainmentEList<DynamicOrganizationMapping>(DynamicOrganizationMapping.class, this,
                            XpdExtensionPackage.DYNAMIC_ORGANIZATION_MAPPINGS__DYNAMIC_ORGANIZATION_MAPPING);
        }
        return dynamicOrganizationMapping;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case XpdExtensionPackage.DYNAMIC_ORGANIZATION_MAPPINGS__DYNAMIC_ORGANIZATION_MAPPING:
            return ((InternalEList<?>) getDynamicOrganizationMapping()).basicRemove(otherEnd, msgs);
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
        case XpdExtensionPackage.DYNAMIC_ORGANIZATION_MAPPINGS__DYNAMIC_ORGANIZATION_MAPPING:
            return getDynamicOrganizationMapping();
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
        case XpdExtensionPackage.DYNAMIC_ORGANIZATION_MAPPINGS__DYNAMIC_ORGANIZATION_MAPPING:
            getDynamicOrganizationMapping().clear();
            getDynamicOrganizationMapping().addAll((Collection<? extends DynamicOrganizationMapping>) newValue);
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
        case XpdExtensionPackage.DYNAMIC_ORGANIZATION_MAPPINGS__DYNAMIC_ORGANIZATION_MAPPING:
            getDynamicOrganizationMapping().clear();
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
        case XpdExtensionPackage.DYNAMIC_ORGANIZATION_MAPPINGS__DYNAMIC_ORGANIZATION_MAPPING:
            return dynamicOrganizationMapping != null && !dynamicOrganizationMapping.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //DynamicOrganizationMappingsImpl
