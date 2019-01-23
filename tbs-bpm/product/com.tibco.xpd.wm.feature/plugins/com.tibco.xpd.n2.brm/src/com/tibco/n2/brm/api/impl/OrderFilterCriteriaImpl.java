/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.OrderFilterCriteria;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Order Filter Criteria</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.OrderFilterCriteriaImpl#getOrder <em>Order</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.OrderFilterCriteriaImpl#getFilter <em>Filter</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OrderFilterCriteriaImpl extends EObjectImpl implements OrderFilterCriteria {
    /**
     * The default value of the '{@link #getOrder() <em>Order</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOrder()
     * @generated
     * @ordered
     */
    protected static final String ORDER_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getOrder() <em>Order</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOrder()
     * @generated
     * @ordered
     */
    protected String order = ORDER_EDEFAULT;

    /**
     * The default value of the '{@link #getFilter() <em>Filter</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFilter()
     * @generated
     * @ordered
     */
    protected static final String FILTER_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFilter() <em>Filter</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFilter()
     * @generated
     * @ordered
     */
    protected String filter = FILTER_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected OrderFilterCriteriaImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.ORDER_FILTER_CRITERIA;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getOrder() {
        return order;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOrder(String newOrder) {
        String oldOrder = order;
        order = newOrder;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ORDER_FILTER_CRITERIA__ORDER, oldOrder, order));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getFilter() {
        return filter;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFilter(String newFilter) {
        String oldFilter = filter;
        filter = newFilter;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ORDER_FILTER_CRITERIA__FILTER, oldFilter, filter));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case N2BRMPackage.ORDER_FILTER_CRITERIA__ORDER:
                return getOrder();
            case N2BRMPackage.ORDER_FILTER_CRITERIA__FILTER:
                return getFilter();
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
            case N2BRMPackage.ORDER_FILTER_CRITERIA__ORDER:
                setOrder((String)newValue);
                return;
            case N2BRMPackage.ORDER_FILTER_CRITERIA__FILTER:
                setFilter((String)newValue);
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
            case N2BRMPackage.ORDER_FILTER_CRITERIA__ORDER:
                setOrder(ORDER_EDEFAULT);
                return;
            case N2BRMPackage.ORDER_FILTER_CRITERIA__FILTER:
                setFilter(FILTER_EDEFAULT);
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
            case N2BRMPackage.ORDER_FILTER_CRITERIA__ORDER:
                return ORDER_EDEFAULT == null ? order != null : !ORDER_EDEFAULT.equals(order);
            case N2BRMPackage.ORDER_FILTER_CRITERIA__FILTER:
                return FILTER_EDEFAULT == null ? filter != null : !FILTER_EDEFAULT.equals(filter);
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
        result.append(" (order: ");
        result.append(order);
        result.append(", filter: ");
        result.append(filter);
        result.append(')');
        return result.toString();
    }

} //OrderFilterCriteriaImpl
