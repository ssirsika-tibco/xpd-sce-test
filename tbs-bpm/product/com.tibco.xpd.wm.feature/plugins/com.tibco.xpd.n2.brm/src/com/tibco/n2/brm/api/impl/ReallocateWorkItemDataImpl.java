/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.ManagedObjectID;
import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.ReallocateWorkItemData;
import com.tibco.n2.brm.api.WorkItemBody;

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
 * An implementation of the model object '<em><b>Reallocate Work Item Data</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.ReallocateWorkItemDataImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ReallocateWorkItemDataImpl#getWorkItemID <em>Work Item ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ReallocateWorkItemDataImpl#getResource <em>Resource</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ReallocateWorkItemDataImpl#getWorkItemPayload <em>Work Item Payload</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ReallocateWorkItemDataImpl extends EObjectImpl implements ReallocateWorkItemData {
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
    protected ReallocateWorkItemDataImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.REALLOCATE_WORK_ITEM_DATA;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getGroup() {
        if (group == null) {
            group = new BasicFeatureMap(this, N2BRMPackage.REALLOCATE_WORK_ITEM_DATA__GROUP);
        }
        return group;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ManagedObjectID> getWorkItemID() {
        return getGroup().list(N2BRMPackage.Literals.REALLOCATE_WORK_ITEM_DATA__WORK_ITEM_ID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<String> getResource() {
        return getGroup().list(N2BRMPackage.Literals.REALLOCATE_WORK_ITEM_DATA__RESOURCE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<WorkItemBody> getWorkItemPayload() {
        return getGroup().list(N2BRMPackage.Literals.REALLOCATE_WORK_ITEM_DATA__WORK_ITEM_PAYLOAD);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.REALLOCATE_WORK_ITEM_DATA__GROUP:
                return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
            case N2BRMPackage.REALLOCATE_WORK_ITEM_DATA__WORK_ITEM_ID:
                return ((InternalEList<?>)getWorkItemID()).basicRemove(otherEnd, msgs);
            case N2BRMPackage.REALLOCATE_WORK_ITEM_DATA__WORK_ITEM_PAYLOAD:
                return ((InternalEList<?>)getWorkItemPayload()).basicRemove(otherEnd, msgs);
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
            case N2BRMPackage.REALLOCATE_WORK_ITEM_DATA__GROUP:
                if (coreType) return getGroup();
                return ((FeatureMap.Internal)getGroup()).getWrapper();
            case N2BRMPackage.REALLOCATE_WORK_ITEM_DATA__WORK_ITEM_ID:
                return getWorkItemID();
            case N2BRMPackage.REALLOCATE_WORK_ITEM_DATA__RESOURCE:
                return getResource();
            case N2BRMPackage.REALLOCATE_WORK_ITEM_DATA__WORK_ITEM_PAYLOAD:
                return getWorkItemPayload();
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
            case N2BRMPackage.REALLOCATE_WORK_ITEM_DATA__GROUP:
                ((FeatureMap.Internal)getGroup()).set(newValue);
                return;
            case N2BRMPackage.REALLOCATE_WORK_ITEM_DATA__WORK_ITEM_ID:
                getWorkItemID().clear();
                getWorkItemID().addAll((Collection<? extends ManagedObjectID>)newValue);
                return;
            case N2BRMPackage.REALLOCATE_WORK_ITEM_DATA__RESOURCE:
                getResource().clear();
                getResource().addAll((Collection<? extends String>)newValue);
                return;
            case N2BRMPackage.REALLOCATE_WORK_ITEM_DATA__WORK_ITEM_PAYLOAD:
                getWorkItemPayload().clear();
                getWorkItemPayload().addAll((Collection<? extends WorkItemBody>)newValue);
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
            case N2BRMPackage.REALLOCATE_WORK_ITEM_DATA__GROUP:
                getGroup().clear();
                return;
            case N2BRMPackage.REALLOCATE_WORK_ITEM_DATA__WORK_ITEM_ID:
                getWorkItemID().clear();
                return;
            case N2BRMPackage.REALLOCATE_WORK_ITEM_DATA__RESOURCE:
                getResource().clear();
                return;
            case N2BRMPackage.REALLOCATE_WORK_ITEM_DATA__WORK_ITEM_PAYLOAD:
                getWorkItemPayload().clear();
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
            case N2BRMPackage.REALLOCATE_WORK_ITEM_DATA__GROUP:
                return group != null && !group.isEmpty();
            case N2BRMPackage.REALLOCATE_WORK_ITEM_DATA__WORK_ITEM_ID:
                return !getWorkItemID().isEmpty();
            case N2BRMPackage.REALLOCATE_WORK_ITEM_DATA__RESOURCE:
                return !getResource().isEmpty();
            case N2BRMPackage.REALLOCATE_WORK_ITEM_DATA__WORK_ITEM_PAYLOAD:
                return !getWorkItemPayload().isEmpty();
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

} //ReallocateWorkItemDataImpl
