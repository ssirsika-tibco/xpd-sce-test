/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.OrderFilterCriteria;
import com.tibco.n2.brm.api.SetResourceOrderFilterCriteriaType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Set Resource Order Filter Criteria Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.SetResourceOrderFilterCriteriaTypeImpl#getOrderFilterCriteria <em>Order Filter Criteria</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.SetResourceOrderFilterCriteriaTypeImpl#getResourceID <em>Resource ID</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SetResourceOrderFilterCriteriaTypeImpl extends EObjectImpl implements SetResourceOrderFilterCriteriaType {
    /**
     * The cached value of the '{@link #getOrderFilterCriteria() <em>Order Filter Criteria</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOrderFilterCriteria()
     * @generated
     * @ordered
     */
    protected OrderFilterCriteria orderFilterCriteria;

    /**
     * The default value of the '{@link #getResourceID() <em>Resource ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResourceID()
     * @generated
     * @ordered
     */
    protected static final String RESOURCE_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getResourceID() <em>Resource ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResourceID()
     * @generated
     * @ordered
     */
    protected String resourceID = RESOURCE_ID_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SetResourceOrderFilterCriteriaTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OrderFilterCriteria getOrderFilterCriteria() {
        return orderFilterCriteria;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetOrderFilterCriteria(OrderFilterCriteria newOrderFilterCriteria, NotificationChain msgs) {
        OrderFilterCriteria oldOrderFilterCriteria = orderFilterCriteria;
        orderFilterCriteria = newOrderFilterCriteria;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__ORDER_FILTER_CRITERIA, oldOrderFilterCriteria, newOrderFilterCriteria);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOrderFilterCriteria(OrderFilterCriteria newOrderFilterCriteria) {
        if (newOrderFilterCriteria != orderFilterCriteria) {
            NotificationChain msgs = null;
            if (orderFilterCriteria != null)
                msgs = ((InternalEObject)orderFilterCriteria).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__ORDER_FILTER_CRITERIA, null, msgs);
            if (newOrderFilterCriteria != null)
                msgs = ((InternalEObject)newOrderFilterCriteria).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__ORDER_FILTER_CRITERIA, null, msgs);
            msgs = basicSetOrderFilterCriteria(newOrderFilterCriteria, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__ORDER_FILTER_CRITERIA, newOrderFilterCriteria, newOrderFilterCriteria));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getResourceID() {
        return resourceID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setResourceID(String newResourceID) {
        String oldResourceID = resourceID;
        resourceID = newResourceID;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__RESOURCE_ID, oldResourceID, resourceID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__ORDER_FILTER_CRITERIA:
                return basicSetOrderFilterCriteria(null, msgs);
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
            case N2BRMPackage.SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__ORDER_FILTER_CRITERIA:
                return getOrderFilterCriteria();
            case N2BRMPackage.SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__RESOURCE_ID:
                return getResourceID();
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
            case N2BRMPackage.SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__ORDER_FILTER_CRITERIA:
                setOrderFilterCriteria((OrderFilterCriteria)newValue);
                return;
            case N2BRMPackage.SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__RESOURCE_ID:
                setResourceID((String)newValue);
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
            case N2BRMPackage.SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__ORDER_FILTER_CRITERIA:
                setOrderFilterCriteria((OrderFilterCriteria)null);
                return;
            case N2BRMPackage.SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__RESOURCE_ID:
                setResourceID(RESOURCE_ID_EDEFAULT);
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
            case N2BRMPackage.SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__ORDER_FILTER_CRITERIA:
                return orderFilterCriteria != null;
            case N2BRMPackage.SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__RESOURCE_ID:
                return RESOURCE_ID_EDEFAULT == null ? resourceID != null : !RESOURCE_ID_EDEFAULT.equals(resourceID);
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
        result.append(" (resourceID: ");
        result.append(resourceID);
        result.append(')');
        return result.toString();
    }

} //SetResourceOrderFilterCriteriaTypeImpl
