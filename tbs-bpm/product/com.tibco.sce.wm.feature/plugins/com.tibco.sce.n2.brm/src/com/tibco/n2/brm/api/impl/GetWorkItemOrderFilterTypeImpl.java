/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.GetWorkItemOrderFilterType;
import com.tibco.n2.brm.api.N2BRMPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Get Work Item Order Filter Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetWorkItemOrderFilterTypeImpl#getLimitColumns <em>Limit Columns</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GetWorkItemOrderFilterTypeImpl extends EObjectImpl implements GetWorkItemOrderFilterType {
    /**
     * The default value of the '{@link #getLimitColumns() <em>Limit Columns</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLimitColumns()
     * @generated
     * @ordered
     */
    protected static final short LIMIT_COLUMNS_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getLimitColumns() <em>Limit Columns</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLimitColumns()
     * @generated
     * @ordered
     */
    protected short limitColumns = LIMIT_COLUMNS_EDEFAULT;

    /**
     * This is true if the Limit Columns attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean limitColumnsESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected GetWorkItemOrderFilterTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.GET_WORK_ITEM_ORDER_FILTER_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public short getLimitColumns() {
        return limitColumns;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLimitColumns(short newLimitColumns) {
        short oldLimitColumns = limitColumns;
        limitColumns = newLimitColumns;
        boolean oldLimitColumnsESet = limitColumnsESet;
        limitColumnsESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_WORK_ITEM_ORDER_FILTER_TYPE__LIMIT_COLUMNS, oldLimitColumns, limitColumns, !oldLimitColumnsESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetLimitColumns() {
        short oldLimitColumns = limitColumns;
        boolean oldLimitColumnsESet = limitColumnsESet;
        limitColumns = LIMIT_COLUMNS_EDEFAULT;
        limitColumnsESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.GET_WORK_ITEM_ORDER_FILTER_TYPE__LIMIT_COLUMNS, oldLimitColumns, LIMIT_COLUMNS_EDEFAULT, oldLimitColumnsESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetLimitColumns() {
        return limitColumnsESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case N2BRMPackage.GET_WORK_ITEM_ORDER_FILTER_TYPE__LIMIT_COLUMNS:
                return getLimitColumns();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case N2BRMPackage.GET_WORK_ITEM_ORDER_FILTER_TYPE__LIMIT_COLUMNS:
                setLimitColumns((Short)newValue);
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
            case N2BRMPackage.GET_WORK_ITEM_ORDER_FILTER_TYPE__LIMIT_COLUMNS:
                unsetLimitColumns();
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
            case N2BRMPackage.GET_WORK_ITEM_ORDER_FILTER_TYPE__LIMIT_COLUMNS:
                return isSetLimitColumns();
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
        result.append(" (limitColumns: ");
        if (limitColumnsESet) result.append(limitColumns); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //GetWorkItemOrderFilterTypeImpl
