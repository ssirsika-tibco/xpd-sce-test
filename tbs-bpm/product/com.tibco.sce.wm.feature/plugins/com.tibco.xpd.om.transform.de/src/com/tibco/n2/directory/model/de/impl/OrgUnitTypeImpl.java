/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de.impl;

import com.tibco.n2.directory.model.de.DePackage;
import com.tibco.n2.directory.model.de.Feature;
import com.tibco.n2.directory.model.de.OrgUnitType;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Org Unit Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.OrgUnitTypeImpl#getChoiceGroup <em>Choice Group</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.OrgUnitTypeImpl#getPositionFeature <em>Position Feature</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.OrgUnitTypeImpl#getOrgUnitFeature <em>Org Unit Feature</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OrgUnitTypeImpl extends EntityTypeImpl implements OrgUnitType {
    /**
     * The cached value of the '{@link #getChoiceGroup() <em>Choice Group</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getChoiceGroup()
     * @generated
     * @ordered
     */
    protected FeatureMap choiceGroup;

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
        return DePackage.Literals.ORG_UNIT_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getChoiceGroup() {
        if (choiceGroup == null) {
            choiceGroup = new BasicFeatureMap(this, DePackage.ORG_UNIT_TYPE__CHOICE_GROUP);
        }
        return choiceGroup;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Feature> getPositionFeature() {
        return getChoiceGroup().list(DePackage.Literals.ORG_UNIT_TYPE__POSITION_FEATURE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Feature> getOrgUnitFeature() {
        return getChoiceGroup().list(DePackage.Literals.ORG_UNIT_TYPE__ORG_UNIT_FEATURE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DePackage.ORG_UNIT_TYPE__CHOICE_GROUP:
                return ((InternalEList<?>)getChoiceGroup()).basicRemove(otherEnd, msgs);
            case DePackage.ORG_UNIT_TYPE__POSITION_FEATURE:
                return ((InternalEList<?>)getPositionFeature()).basicRemove(otherEnd, msgs);
            case DePackage.ORG_UNIT_TYPE__ORG_UNIT_FEATURE:
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
            case DePackage.ORG_UNIT_TYPE__CHOICE_GROUP:
                if (coreType) return getChoiceGroup();
                return ((FeatureMap.Internal)getChoiceGroup()).getWrapper();
            case DePackage.ORG_UNIT_TYPE__POSITION_FEATURE:
                return getPositionFeature();
            case DePackage.ORG_UNIT_TYPE__ORG_UNIT_FEATURE:
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
            case DePackage.ORG_UNIT_TYPE__CHOICE_GROUP:
                ((FeatureMap.Internal)getChoiceGroup()).set(newValue);
                return;
            case DePackage.ORG_UNIT_TYPE__POSITION_FEATURE:
                getPositionFeature().clear();
                getPositionFeature().addAll((Collection<? extends Feature>)newValue);
                return;
            case DePackage.ORG_UNIT_TYPE__ORG_UNIT_FEATURE:
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
            case DePackage.ORG_UNIT_TYPE__CHOICE_GROUP:
                getChoiceGroup().clear();
                return;
            case DePackage.ORG_UNIT_TYPE__POSITION_FEATURE:
                getPositionFeature().clear();
                return;
            case DePackage.ORG_UNIT_TYPE__ORG_UNIT_FEATURE:
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
            case DePackage.ORG_UNIT_TYPE__CHOICE_GROUP:
                return choiceGroup != null && !choiceGroup.isEmpty();
            case DePackage.ORG_UNIT_TYPE__POSITION_FEATURE:
                return !getPositionFeature().isEmpty();
            case DePackage.ORG_UNIT_TYPE__ORG_UNIT_FEATURE:
                return !getOrgUnitFeature().isEmpty();
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
        result.append(" (choiceGroup: ");
        result.append(choiceGroup);
        result.append(')');
        return result.toString();
    }

} //OrgUnitTypeImpl
