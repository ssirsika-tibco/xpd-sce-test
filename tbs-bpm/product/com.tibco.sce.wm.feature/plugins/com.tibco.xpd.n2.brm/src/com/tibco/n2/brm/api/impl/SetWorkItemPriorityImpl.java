/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.SetWorkItemPriority;
import com.tibco.n2.brm.api.WorkItemIDandPriorityType;

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
 * An implementation of the model object '<em><b>Set Work Item Priority</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.SetWorkItemPriorityImpl#getWorkItemIDandPriority <em>Work Item IDand Priority</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SetWorkItemPriorityImpl extends EObjectImpl implements SetWorkItemPriority {
    /**
     * The cached value of the '{@link #getWorkItemIDandPriority() <em>Work Item IDand Priority</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkItemIDandPriority()
     * @generated
     * @ordered
     */
    protected EList<WorkItemIDandPriorityType> workItemIDandPriority;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SetWorkItemPriorityImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.SET_WORK_ITEM_PRIORITY;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<WorkItemIDandPriorityType> getWorkItemIDandPriority() {
        if (workItemIDandPriority == null) {
            workItemIDandPriority = new EObjectContainmentEList<WorkItemIDandPriorityType>(WorkItemIDandPriorityType.class, this, N2BRMPackage.SET_WORK_ITEM_PRIORITY__WORK_ITEM_IDAND_PRIORITY);
        }
        return workItemIDandPriority;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.SET_WORK_ITEM_PRIORITY__WORK_ITEM_IDAND_PRIORITY:
                return ((InternalEList<?>)getWorkItemIDandPriority()).basicRemove(otherEnd, msgs);
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
            case N2BRMPackage.SET_WORK_ITEM_PRIORITY__WORK_ITEM_IDAND_PRIORITY:
                return getWorkItemIDandPriority();
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
            case N2BRMPackage.SET_WORK_ITEM_PRIORITY__WORK_ITEM_IDAND_PRIORITY:
                getWorkItemIDandPriority().clear();
                getWorkItemIDandPriority().addAll((Collection<? extends WorkItemIDandPriorityType>)newValue);
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
            case N2BRMPackage.SET_WORK_ITEM_PRIORITY__WORK_ITEM_IDAND_PRIORITY:
                getWorkItemIDandPriority().clear();
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
            case N2BRMPackage.SET_WORK_ITEM_PRIORITY__WORK_ITEM_IDAND_PRIORITY:
                return workItemIDandPriority != null && !workItemIDandPriority.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //SetWorkItemPriorityImpl
