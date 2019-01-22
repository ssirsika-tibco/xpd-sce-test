/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.HiddenPeriod;
import com.tibco.n2.brm.api.ManagedObjectID;
import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.PendWorkItem;

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
 * An implementation of the model object '<em><b>Pend Work Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.PendWorkItemImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.PendWorkItemImpl#getWorkItemID <em>Work Item ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.PendWorkItemImpl#getHiddenPeriod <em>Hidden Period</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PendWorkItemImpl extends EObjectImpl implements PendWorkItem {
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
    protected PendWorkItemImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.PEND_WORK_ITEM;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getGroup() {
        if (group == null) {
            group = new BasicFeatureMap(this, N2BRMPackage.PEND_WORK_ITEM__GROUP);
        }
        return group;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ManagedObjectID> getWorkItemID() {
        return getGroup().list(N2BRMPackage.Literals.PEND_WORK_ITEM__WORK_ITEM_ID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<HiddenPeriod> getHiddenPeriod() {
        return getGroup().list(N2BRMPackage.Literals.PEND_WORK_ITEM__HIDDEN_PERIOD);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.PEND_WORK_ITEM__GROUP:
                return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
            case N2BRMPackage.PEND_WORK_ITEM__WORK_ITEM_ID:
                return ((InternalEList<?>)getWorkItemID()).basicRemove(otherEnd, msgs);
            case N2BRMPackage.PEND_WORK_ITEM__HIDDEN_PERIOD:
                return ((InternalEList<?>)getHiddenPeriod()).basicRemove(otherEnd, msgs);
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
            case N2BRMPackage.PEND_WORK_ITEM__GROUP:
                if (coreType) return getGroup();
                return ((FeatureMap.Internal)getGroup()).getWrapper();
            case N2BRMPackage.PEND_WORK_ITEM__WORK_ITEM_ID:
                return getWorkItemID();
            case N2BRMPackage.PEND_WORK_ITEM__HIDDEN_PERIOD:
                return getHiddenPeriod();
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
            case N2BRMPackage.PEND_WORK_ITEM__GROUP:
                ((FeatureMap.Internal)getGroup()).set(newValue);
                return;
            case N2BRMPackage.PEND_WORK_ITEM__WORK_ITEM_ID:
                getWorkItemID().clear();
                getWorkItemID().addAll((Collection<? extends ManagedObjectID>)newValue);
                return;
            case N2BRMPackage.PEND_WORK_ITEM__HIDDEN_PERIOD:
                getHiddenPeriod().clear();
                getHiddenPeriod().addAll((Collection<? extends HiddenPeriod>)newValue);
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
            case N2BRMPackage.PEND_WORK_ITEM__GROUP:
                getGroup().clear();
                return;
            case N2BRMPackage.PEND_WORK_ITEM__WORK_ITEM_ID:
                getWorkItemID().clear();
                return;
            case N2BRMPackage.PEND_WORK_ITEM__HIDDEN_PERIOD:
                getHiddenPeriod().clear();
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
            case N2BRMPackage.PEND_WORK_ITEM__GROUP:
                return group != null && !group.isEmpty();
            case N2BRMPackage.PEND_WORK_ITEM__WORK_ITEM_ID:
                return !getWorkItemID().isEmpty();
            case N2BRMPackage.PEND_WORK_ITEM__HIDDEN_PERIOD:
                return !getHiddenPeriod().isEmpty();
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
        result.append(" (group: ");
        result.append(group);
        result.append(')');
        return result.toString();
    }

} //PendWorkItemImpl
