/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de.impl;

import com.tibco.n2.directory.model.de.DePackage;
import com.tibco.n2.directory.model.de.Feature;
import com.tibco.n2.directory.model.de.OrganizationType;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Organization Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.OrganizationTypeImpl#getOrgUnitFeature <em>Org Unit Feature</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OrganizationTypeImpl extends EntityTypeImpl implements OrganizationType {
    /**
     * The cached value of the '{@link #getOrgUnitFeature() <em>Org Unit Feature</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOrgUnitFeature()
     * @generated
     * @ordered
     */
    protected EList<Feature> orgUnitFeature;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected OrganizationTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DePackage.Literals.ORGANIZATION_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Feature> getOrgUnitFeature() {
        if (orgUnitFeature == null) {
            orgUnitFeature = new EObjectContainmentEList<Feature>(Feature.class, this, DePackage.ORGANIZATION_TYPE__ORG_UNIT_FEATURE);
        }
        return orgUnitFeature;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DePackage.ORGANIZATION_TYPE__ORG_UNIT_FEATURE:
                return ((InternalEList<?>)getOrgUnitFeature()).basicRemove(otherEnd, msgs);
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
            case DePackage.ORGANIZATION_TYPE__ORG_UNIT_FEATURE:
                return getOrgUnitFeature();
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
            case DePackage.ORGANIZATION_TYPE__ORG_UNIT_FEATURE:
                getOrgUnitFeature().clear();
                getOrgUnitFeature().addAll((Collection<? extends Feature>)newValue);
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
            case DePackage.ORGANIZATION_TYPE__ORG_UNIT_FEATURE:
                getOrgUnitFeature().clear();
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
            case DePackage.ORGANIZATION_TYPE__ORG_UNIT_FEATURE:
                return orgUnitFeature != null && !orgUnitFeature.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //OrganizationTypeImpl
