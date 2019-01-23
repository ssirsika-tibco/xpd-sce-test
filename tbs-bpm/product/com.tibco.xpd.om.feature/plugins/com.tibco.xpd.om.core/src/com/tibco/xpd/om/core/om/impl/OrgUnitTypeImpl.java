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
import com.tibco.xpd.om.core.om.OrgUnitType;
import com.tibco.xpd.om.core.om.PositionFeature;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Org Unit Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitTypeImpl#getOrgUnitFeatures <em>Org Unit Features</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitTypeImpl#getPositionFeatures <em>Position Features</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OrgUnitTypeImpl extends OrgElementTypeImpl implements OrgUnitType {
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
     * The cached value of the '{@link #getPositionFeatures() <em>Position Features</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPositionFeatures()
     * @generated
     * @ordered
     */
    protected EList<PositionFeature> positionFeatures;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected OrgUnitTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OMPackage.Literals.ORG_UNIT_TYPE;
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
                            OMPackage.ORG_UNIT_TYPE__ORG_UNIT_FEATURES);
        }
        return orgUnitFeatures;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<PositionFeature> getPositionFeatures() {
        if (positionFeatures == null) {
            positionFeatures =
                    new EObjectContainmentEList<PositionFeature>(
                            PositionFeature.class, this,
                            OMPackage.ORG_UNIT_TYPE__POSITION_FEATURES);
        }
        return positionFeatures;
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
        case OMPackage.ORG_UNIT_TYPE__ORG_UNIT_FEATURES:
            return ((InternalEList<?>) getOrgUnitFeatures())
                    .basicRemove(otherEnd, msgs);
        case OMPackage.ORG_UNIT_TYPE__POSITION_FEATURES:
            return ((InternalEList<?>) getPositionFeatures())
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
        case OMPackage.ORG_UNIT_TYPE__ORG_UNIT_FEATURES:
            return getOrgUnitFeatures();
        case OMPackage.ORG_UNIT_TYPE__POSITION_FEATURES:
            return getPositionFeatures();
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
        case OMPackage.ORG_UNIT_TYPE__ORG_UNIT_FEATURES:
            getOrgUnitFeatures().clear();
            getOrgUnitFeatures()
                    .addAll((Collection<? extends OrgUnitFeature>) newValue);
            return;
        case OMPackage.ORG_UNIT_TYPE__POSITION_FEATURES:
            getPositionFeatures().clear();
            getPositionFeatures()
                    .addAll((Collection<? extends PositionFeature>) newValue);
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
        case OMPackage.ORG_UNIT_TYPE__ORG_UNIT_FEATURES:
            getOrgUnitFeatures().clear();
            return;
        case OMPackage.ORG_UNIT_TYPE__POSITION_FEATURES:
            getPositionFeatures().clear();
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
        case OMPackage.ORG_UNIT_TYPE__ORG_UNIT_FEATURES:
            return orgUnitFeatures != null && !orgUnitFeatures.isEmpty();
        case OMPackage.ORG_UNIT_TYPE__POSITION_FEATURES:
            return positionFeatures != null && !positionFeatures.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //OrgUnitTypeImpl
