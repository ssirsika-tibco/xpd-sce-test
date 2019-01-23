/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.OrganizationType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Organization Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrganizationTypeImpl#getOrgUnitFeatures <em>Org Unit Features</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OrganizationTypeImpl extends OrgElementTypeImpl implements
        OrganizationType {
    /**
     * The cached value of the '{@link #getOrgUnitFeatures() <em>Org Unit Features</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOrgUnitFeatures()
     * @generated
     * @ordered
     */
    protected EList<OrgUnitFeature> orgUnitFeatures;

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
        return OMPackage.Literals.ORGANIZATION_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<OrgUnitFeature> getOrgUnitFeatures() {
        if (orgUnitFeatures == null) {
            orgUnitFeatures =
                    new EObjectContainmentEList<OrgUnitFeature>(
                            OrgUnitFeature.class, this,
                            OMPackage.ORGANIZATION_TYPE__ORG_UNIT_FEATURES);
        }
        return orgUnitFeatures;
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
        case OMPackage.ORGANIZATION_TYPE__ORG_UNIT_FEATURES:
            return ((InternalEList<?>) getOrgUnitFeatures())
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
        case OMPackage.ORGANIZATION_TYPE__ORG_UNIT_FEATURES:
            return getOrgUnitFeatures();
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
        case OMPackage.ORGANIZATION_TYPE__ORG_UNIT_FEATURES:
            getOrgUnitFeatures().clear();
            getOrgUnitFeatures()
                    .addAll((Collection<? extends OrgUnitFeature>) newValue);
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
        case OMPackage.ORGANIZATION_TYPE__ORG_UNIT_FEATURES:
            getOrgUnitFeatures().clear();
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
        case OMPackage.ORGANIZATION_TYPE__ORG_UNIT_FEATURES:
            return orgUnitFeatures != null && !orgUnitFeatures.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //OrganizationTypeImpl
