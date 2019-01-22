/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.ColumnDefinition;
import com.tibco.n2.brm.api.GetWorkItemOrderFilterResponseType;
import com.tibco.n2.brm.api.N2BRMPackage;

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
 * An implementation of the model object '<em><b>Get Work Item Order Filter Response Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetWorkItemOrderFilterResponseTypeImpl#getColumnData <em>Column Data</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GetWorkItemOrderFilterResponseTypeImpl extends EObjectImpl implements GetWorkItemOrderFilterResponseType {
    /**
     * The cached value of the '{@link #getColumnData() <em>Column Data</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getColumnData()
     * @generated
     * @ordered
     */
    protected EList<ColumnDefinition> columnData;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected GetWorkItemOrderFilterResponseTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.GET_WORK_ITEM_ORDER_FILTER_RESPONSE_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ColumnDefinition> getColumnData() {
        if (columnData == null) {
            columnData = new EObjectContainmentEList<ColumnDefinition>(ColumnDefinition.class, this, N2BRMPackage.GET_WORK_ITEM_ORDER_FILTER_RESPONSE_TYPE__COLUMN_DATA);
        }
        return columnData;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.GET_WORK_ITEM_ORDER_FILTER_RESPONSE_TYPE__COLUMN_DATA:
                return ((InternalEList<?>)getColumnData()).basicRemove(otherEnd, msgs);
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
            case N2BRMPackage.GET_WORK_ITEM_ORDER_FILTER_RESPONSE_TYPE__COLUMN_DATA:
                return getColumnData();
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
            case N2BRMPackage.GET_WORK_ITEM_ORDER_FILTER_RESPONSE_TYPE__COLUMN_DATA:
                getColumnData().clear();
                getColumnData().addAll((Collection<? extends ColumnDefinition>)newValue);
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
            case N2BRMPackage.GET_WORK_ITEM_ORDER_FILTER_RESPONSE_TYPE__COLUMN_DATA:
                getColumnData().clear();
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
            case N2BRMPackage.GET_WORK_ITEM_ORDER_FILTER_RESPONSE_TYPE__COLUMN_DATA:
                return columnData != null && !columnData.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //GetWorkItemOrderFilterResponseTypeImpl
