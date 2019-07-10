/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import java.math.BigDecimal;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.xpd.xpdl2.ResourceCosts;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Resource Costs</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ResourceCostsImpl#getResourceCost <em>Resource Cost</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ResourceCostsImpl#getCostUnitOfTime <em>Cost Unit Of Time</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ResourceCostsImpl extends NamedElementImpl implements ResourceCosts {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getResourceCost() <em>Resource Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResourceCost()
     * @generated
     * @ordered
     */
    protected static final BigDecimal RESOURCE_COST_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getResourceCost() <em>Resource Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResourceCost()
     * @generated
     * @ordered
     */
    protected BigDecimal resourceCost = RESOURCE_COST_EDEFAULT;

    /**
     * The default value of the '{@link #getCostUnitOfTime() <em>Cost Unit Of Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCostUnitOfTime()
     * @generated
     * @ordered
     */
    protected static final String COST_UNIT_OF_TIME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getCostUnitOfTime() <em>Cost Unit Of Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCostUnitOfTime()
     * @generated
     * @ordered
     */
    protected String costUnitOfTime = COST_UNIT_OF_TIME_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ResourceCostsImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.RESOURCE_COSTS;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigDecimal getResourceCost() {
        return resourceCost;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setResourceCost(BigDecimal newResourceCost) {
        BigDecimal oldResourceCost = resourceCost;
        resourceCost = newResourceCost;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.RESOURCE_COSTS__RESOURCE_COST,
                    oldResourceCost, resourceCost));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getCostUnitOfTime() {
        return costUnitOfTime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCostUnitOfTime(String newCostUnitOfTime) {
        String oldCostUnitOfTime = costUnitOfTime;
        costUnitOfTime = newCostUnitOfTime;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.RESOURCE_COSTS__COST_UNIT_OF_TIME,
                    oldCostUnitOfTime, costUnitOfTime));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case Xpdl2Package.RESOURCE_COSTS__RESOURCE_COST:
            return getResourceCost();
        case Xpdl2Package.RESOURCE_COSTS__COST_UNIT_OF_TIME:
            return getCostUnitOfTime();
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
        case Xpdl2Package.RESOURCE_COSTS__RESOURCE_COST:
            setResourceCost((BigDecimal) newValue);
            return;
        case Xpdl2Package.RESOURCE_COSTS__COST_UNIT_OF_TIME:
            setCostUnitOfTime((String) newValue);
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
        case Xpdl2Package.RESOURCE_COSTS__RESOURCE_COST:
            setResourceCost(RESOURCE_COST_EDEFAULT);
            return;
        case Xpdl2Package.RESOURCE_COSTS__COST_UNIT_OF_TIME:
            setCostUnitOfTime(COST_UNIT_OF_TIME_EDEFAULT);
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
        case Xpdl2Package.RESOURCE_COSTS__RESOURCE_COST:
            return RESOURCE_COST_EDEFAULT == null ? resourceCost != null : !RESOURCE_COST_EDEFAULT.equals(resourceCost);
        case Xpdl2Package.RESOURCE_COSTS__COST_UNIT_OF_TIME:
            return COST_UNIT_OF_TIME_EDEFAULT == null ? costUnitOfTime != null
                    : !COST_UNIT_OF_TIME_EDEFAULT.equals(costUnitOfTime);
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
        if (eIsProxy())
            return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (resourceCost: "); //$NON-NLS-1$
        result.append(resourceCost);
        result.append(", costUnitOfTime: "); //$NON-NLS-1$
        result.append(costUnitOfTime);
        result.append(')');
        return result.toString();
    }

} //ResourceCostsImpl
