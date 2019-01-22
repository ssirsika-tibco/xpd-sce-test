/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import java.math.BigInteger;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.CostStructure;
import com.tibco.xpd.xpdl2.ResourceCosts;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Cost Structure</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.CostStructureImpl#getFixedCost <em>Fixed Cost</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.CostStructureImpl#getResourceCosts <em>Resource Costs</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CostStructureImpl extends EObjectImpl implements CostStructure {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getFixedCost() <em>Fixed Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFixedCost()
     * @generated
     * @ordered
     */
    protected static final BigInteger FIXED_COST_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFixedCost() <em>Fixed Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFixedCost()
     * @generated
     * @ordered
     */
    protected BigInteger fixedCost = FIXED_COST_EDEFAULT;

    /**
     * The cached value of the '{@link #getResourceCosts() <em>Resource Costs</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResourceCosts()
     * @generated
     * @ordered
     */
    protected EList<ResourceCosts> resourceCosts;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected CostStructureImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.COST_STRUCTURE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigInteger getFixedCost() {
        return fixedCost;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFixedCost(BigInteger newFixedCost) {
        BigInteger oldFixedCost = fixedCost;
        fixedCost = newFixedCost;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.COST_STRUCTURE__FIXED_COST, oldFixedCost,
                    fixedCost));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ResourceCosts> getResourceCosts() {
        if (resourceCosts == null) {
            resourceCosts =
                    new EObjectContainmentEList<ResourceCosts>(
                            ResourceCosts.class, this,
                            Xpdl2Package.COST_STRUCTURE__RESOURCE_COSTS);
        }
        return resourceCosts;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.COST_STRUCTURE__RESOURCE_COSTS:
            return ((InternalEList<?>) getResourceCosts())
                    .basicRemove(otherEnd, msgs);
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
        case Xpdl2Package.COST_STRUCTURE__FIXED_COST:
            return getFixedCost();
        case Xpdl2Package.COST_STRUCTURE__RESOURCE_COSTS:
            return getResourceCosts();
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
        case Xpdl2Package.COST_STRUCTURE__FIXED_COST:
            setFixedCost((BigInteger) newValue);
            return;
        case Xpdl2Package.COST_STRUCTURE__RESOURCE_COSTS:
            getResourceCosts().clear();
            getResourceCosts()
                    .addAll((Collection<? extends ResourceCosts>) newValue);
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
        case Xpdl2Package.COST_STRUCTURE__FIXED_COST:
            setFixedCost(FIXED_COST_EDEFAULT);
            return;
        case Xpdl2Package.COST_STRUCTURE__RESOURCE_COSTS:
            getResourceCosts().clear();
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
        case Xpdl2Package.COST_STRUCTURE__FIXED_COST:
            return FIXED_COST_EDEFAULT == null ? fixedCost != null
                    : !FIXED_COST_EDEFAULT.equals(fixedCost);
        case Xpdl2Package.COST_STRUCTURE__RESOURCE_COSTS:
            return resourceCosts != null && !resourceCosts.isEmpty();
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

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (fixedCost: "); //$NON-NLS-1$
        result.append(fixedCost);
        result.append(')');
        return result.toString();
    }

} //CostStructureImpl
