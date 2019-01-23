/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.GetEditableWorkListViewsResponseType;
import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.WorkListViewPageItem;

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
 * An implementation of the model object '<em><b>Get Editable Work List Views Response Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetEditableWorkListViewsResponseTypeImpl#getWorkListViews <em>Work List Views</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GetEditableWorkListViewsResponseTypeImpl extends EObjectImpl implements GetEditableWorkListViewsResponseType {
    /**
     * The cached value of the '{@link #getWorkListViews() <em>Work List Views</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkListViews()
     * @generated
     * @ordered
     */
    protected EList<WorkListViewPageItem> workListViews;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected GetEditableWorkListViewsResponseTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<WorkListViewPageItem> getWorkListViews() {
        if (workListViews == null) {
            workListViews = new EObjectContainmentEList<WorkListViewPageItem>(WorkListViewPageItem.class, this, N2BRMPackage.GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE_TYPE__WORK_LIST_VIEWS);
        }
        return workListViews;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE_TYPE__WORK_LIST_VIEWS:
                return ((InternalEList<?>)getWorkListViews()).basicRemove(otherEnd, msgs);
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
            case N2BRMPackage.GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE_TYPE__WORK_LIST_VIEWS:
                return getWorkListViews();
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
            case N2BRMPackage.GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE_TYPE__WORK_LIST_VIEWS:
                getWorkListViews().clear();
                getWorkListViews().addAll((Collection<? extends WorkListViewPageItem>)newValue);
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
            case N2BRMPackage.GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE_TYPE__WORK_LIST_VIEWS:
                getWorkListViews().clear();
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
            case N2BRMPackage.GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE_TYPE__WORK_LIST_VIEWS:
                return workListViews != null && !workListViews.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //GetEditableWorkListViewsResponseTypeImpl
