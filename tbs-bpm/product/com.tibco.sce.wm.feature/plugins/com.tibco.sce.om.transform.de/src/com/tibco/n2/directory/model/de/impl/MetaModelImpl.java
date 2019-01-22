/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de.impl;

import com.tibco.n2.directory.model.de.DePackage;
import com.tibco.n2.directory.model.de.LocationType;
import com.tibco.n2.directory.model.de.MetaModel;
import com.tibco.n2.directory.model.de.OrgUnitType;
import com.tibco.n2.directory.model.de.OrganizationType;
import com.tibco.n2.directory.model.de.PositionType;

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
 * An implementation of the model object '<em><b>Meta Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.MetaModelImpl#getChoiceGroup <em>Choice Group</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.MetaModelImpl#getLocationType <em>Location Type</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.MetaModelImpl#getPositionType <em>Position Type</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.MetaModelImpl#getOrgUnitType <em>Org Unit Type</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.MetaModelImpl#getOrganizationType <em>Organization Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MetaModelImpl extends EObjectImpl implements MetaModel {
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
    protected MetaModelImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DePackage.Literals.META_MODEL;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getChoiceGroup() {
        if (choiceGroup == null) {
            choiceGroup = new BasicFeatureMap(this, DePackage.META_MODEL__CHOICE_GROUP);
        }
        return choiceGroup;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<LocationType> getLocationType() {
        return getChoiceGroup().list(DePackage.Literals.META_MODEL__LOCATION_TYPE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<PositionType> getPositionType() {
        return getChoiceGroup().list(DePackage.Literals.META_MODEL__POSITION_TYPE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<OrgUnitType> getOrgUnitType() {
        return getChoiceGroup().list(DePackage.Literals.META_MODEL__ORG_UNIT_TYPE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<OrganizationType> getOrganizationType() {
        return getChoiceGroup().list(DePackage.Literals.META_MODEL__ORGANIZATION_TYPE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DePackage.META_MODEL__CHOICE_GROUP:
                return ((InternalEList<?>)getChoiceGroup()).basicRemove(otherEnd, msgs);
            case DePackage.META_MODEL__LOCATION_TYPE:
                return ((InternalEList<?>)getLocationType()).basicRemove(otherEnd, msgs);
            case DePackage.META_MODEL__POSITION_TYPE:
                return ((InternalEList<?>)getPositionType()).basicRemove(otherEnd, msgs);
            case DePackage.META_MODEL__ORG_UNIT_TYPE:
                return ((InternalEList<?>)getOrgUnitType()).basicRemove(otherEnd, msgs);
            case DePackage.META_MODEL__ORGANIZATION_TYPE:
                return ((InternalEList<?>)getOrganizationType()).basicRemove(otherEnd, msgs);
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
            case DePackage.META_MODEL__CHOICE_GROUP:
                if (coreType) return getChoiceGroup();
                return ((FeatureMap.Internal)getChoiceGroup()).getWrapper();
            case DePackage.META_MODEL__LOCATION_TYPE:
                return getLocationType();
            case DePackage.META_MODEL__POSITION_TYPE:
                return getPositionType();
            case DePackage.META_MODEL__ORG_UNIT_TYPE:
                return getOrgUnitType();
            case DePackage.META_MODEL__ORGANIZATION_TYPE:
                return getOrganizationType();
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
            case DePackage.META_MODEL__CHOICE_GROUP:
                ((FeatureMap.Internal)getChoiceGroup()).set(newValue);
                return;
            case DePackage.META_MODEL__LOCATION_TYPE:
                getLocationType().clear();
                getLocationType().addAll((Collection<? extends LocationType>)newValue);
                return;
            case DePackage.META_MODEL__POSITION_TYPE:
                getPositionType().clear();
                getPositionType().addAll((Collection<? extends PositionType>)newValue);
                return;
            case DePackage.META_MODEL__ORG_UNIT_TYPE:
                getOrgUnitType().clear();
                getOrgUnitType().addAll((Collection<? extends OrgUnitType>)newValue);
                return;
            case DePackage.META_MODEL__ORGANIZATION_TYPE:
                getOrganizationType().clear();
                getOrganizationType().addAll((Collection<? extends OrganizationType>)newValue);
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
            case DePackage.META_MODEL__CHOICE_GROUP:
                getChoiceGroup().clear();
                return;
            case DePackage.META_MODEL__LOCATION_TYPE:
                getLocationType().clear();
                return;
            case DePackage.META_MODEL__POSITION_TYPE:
                getPositionType().clear();
                return;
            case DePackage.META_MODEL__ORG_UNIT_TYPE:
                getOrgUnitType().clear();
                return;
            case DePackage.META_MODEL__ORGANIZATION_TYPE:
                getOrganizationType().clear();
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
            case DePackage.META_MODEL__CHOICE_GROUP:
                return choiceGroup != null && !choiceGroup.isEmpty();
            case DePackage.META_MODEL__LOCATION_TYPE:
                return !getLocationType().isEmpty();
            case DePackage.META_MODEL__POSITION_TYPE:
                return !getPositionType().isEmpty();
            case DePackage.META_MODEL__ORG_UNIT_TYPE:
                return !getOrgUnitType().isEmpty();
            case DePackage.META_MODEL__ORGANIZATION_TYPE:
                return !getOrganizationType().isEmpty();
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

} //MetaModelImpl
