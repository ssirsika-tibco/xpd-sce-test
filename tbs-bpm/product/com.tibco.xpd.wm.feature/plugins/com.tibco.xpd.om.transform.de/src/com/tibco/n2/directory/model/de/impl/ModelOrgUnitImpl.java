/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de.impl;

import com.tibco.n2.directory.model.de.DePackage;
import com.tibco.n2.directory.model.de.ModelOrgUnit;
import com.tibco.n2.directory.model.de.Position;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model Org Unit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ModelOrgUnitImpl#getModelPosition <em>Model Position</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ModelOrgUnitImpl#getModelOrgUnit <em>Model Org Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ModelOrgUnitImpl extends OrgUnitBaseImpl implements ModelOrgUnit {
    /**
     * The cached value of the '{@link #getModelPosition() <em>Model Position</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getModelPosition()
     * @generated
     * @ordered
     */
    protected EList<Position> modelPosition;

    /**
     * The cached value of the '{@link #getModelOrgUnit() <em>Model Org Unit</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getModelOrgUnit()
     * @generated
     * @ordered
     */
    protected EList<ModelOrgUnit> modelOrgUnit;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ModelOrgUnitImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DePackage.Literals.MODEL_ORG_UNIT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Position> getModelPosition() {
        if (modelPosition == null) {
            modelPosition = new EObjectContainmentEList<Position>(Position.class, this, DePackage.MODEL_ORG_UNIT__MODEL_POSITION);
        }
        return modelPosition;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ModelOrgUnit> getModelOrgUnit() {
        if (modelOrgUnit == null) {
            modelOrgUnit = new EObjectContainmentEList<ModelOrgUnit>(ModelOrgUnit.class, this, DePackage.MODEL_ORG_UNIT__MODEL_ORG_UNIT);
        }
        return modelOrgUnit;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DePackage.MODEL_ORG_UNIT__MODEL_POSITION:
                return ((InternalEList<?>)getModelPosition()).basicRemove(otherEnd, msgs);
            case DePackage.MODEL_ORG_UNIT__MODEL_ORG_UNIT:
                return ((InternalEList<?>)getModelOrgUnit()).basicRemove(otherEnd, msgs);
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
            case DePackage.MODEL_ORG_UNIT__MODEL_POSITION:
                return getModelPosition();
            case DePackage.MODEL_ORG_UNIT__MODEL_ORG_UNIT:
                return getModelOrgUnit();
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
            case DePackage.MODEL_ORG_UNIT__MODEL_POSITION:
                getModelPosition().clear();
                getModelPosition().addAll((Collection<? extends Position>)newValue);
                return;
            case DePackage.MODEL_ORG_UNIT__MODEL_ORG_UNIT:
                getModelOrgUnit().clear();
                getModelOrgUnit().addAll((Collection<? extends ModelOrgUnit>)newValue);
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
            case DePackage.MODEL_ORG_UNIT__MODEL_POSITION:
                getModelPosition().clear();
                return;
            case DePackage.MODEL_ORG_UNIT__MODEL_ORG_UNIT:
                getModelOrgUnit().clear();
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
            case DePackage.MODEL_ORG_UNIT__MODEL_POSITION:
                return modelPosition != null && !modelPosition.isEmpty();
            case DePackage.MODEL_ORG_UNIT__MODEL_ORG_UNIT:
                return modelOrgUnit != null && !modelOrgUnit.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //ModelOrgUnitImpl
