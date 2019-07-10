/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdl2.Cost;
import com.tibco.xpd.xpdl2.InstantiationType;
import com.tibco.xpd.xpdl2.SimulationInformation;
import com.tibco.xpd.xpdl2.TimeEstimation;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Simulation Information</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.SimulationInformationImpl#getCost <em>Cost</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.SimulationInformationImpl#getTimeEstimation <em>Time Estimation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.SimulationInformationImpl#getInstantiation <em>Instantiation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SimulationInformationImpl extends EObjectImpl implements SimulationInformation {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getCost() <em>Cost</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCost()
     * @generated
     * @ordered
     */
    protected Cost cost;

    /**
     * The cached value of the '{@link #getTimeEstimation() <em>Time Estimation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTimeEstimation()
     * @generated
     * @ordered
     */
    protected TimeEstimation timeEstimation;

    /**
     * The default value of the '{@link #getInstantiation() <em>Instantiation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInstantiation()
     * @generated
     * @ordered
     */
    protected static final InstantiationType INSTANTIATION_EDEFAULT = InstantiationType.ONCE_LITERAL;

    /**
     * The cached value of the '{@link #getInstantiation() <em>Instantiation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInstantiation()
     * @generated
     * @ordered
     */
    protected InstantiationType instantiation = INSTANTIATION_EDEFAULT;

    /**
     * This is true if the Instantiation attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean instantiationESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SimulationInformationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.SIMULATION_INFORMATION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Cost getCost() {
        return cost;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCost(Cost newCost, NotificationChain msgs) {
        Cost oldCost = cost;
        cost = newCost;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.SIMULATION_INFORMATION__COST, oldCost, newCost);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCost(Cost newCost) {
        if (newCost != cost) {
            NotificationChain msgs = null;
            if (cost != null)
                msgs = ((InternalEObject) cost).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.SIMULATION_INFORMATION__COST,
                        null,
                        msgs);
            if (newCost != null)
                msgs = ((InternalEObject) newCost).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.SIMULATION_INFORMATION__COST,
                        null,
                        msgs);
            msgs = basicSetCost(newCost, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.SIMULATION_INFORMATION__COST, newCost,
                    newCost));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TimeEstimation getTimeEstimation() {
        return timeEstimation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTimeEstimation(TimeEstimation newTimeEstimation, NotificationChain msgs) {
        TimeEstimation oldTimeEstimation = timeEstimation;
        timeEstimation = newTimeEstimation;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.SIMULATION_INFORMATION__TIME_ESTIMATION, oldTimeEstimation, newTimeEstimation);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTimeEstimation(TimeEstimation newTimeEstimation) {
        if (newTimeEstimation != timeEstimation) {
            NotificationChain msgs = null;
            if (timeEstimation != null)
                msgs = ((InternalEObject) timeEstimation).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.SIMULATION_INFORMATION__TIME_ESTIMATION,
                        null,
                        msgs);
            if (newTimeEstimation != null)
                msgs = ((InternalEObject) newTimeEstimation).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.SIMULATION_INFORMATION__TIME_ESTIMATION,
                        null,
                        msgs);
            msgs = basicSetTimeEstimation(newTimeEstimation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.SIMULATION_INFORMATION__TIME_ESTIMATION,
                    newTimeEstimation, newTimeEstimation));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public InstantiationType getInstantiation() {
        return instantiation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setInstantiation(InstantiationType newInstantiation) {
        InstantiationType oldInstantiation = instantiation;
        instantiation = newInstantiation == null ? INSTANTIATION_EDEFAULT : newInstantiation;
        boolean oldInstantiationESet = instantiationESet;
        instantiationESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.SIMULATION_INFORMATION__INSTANTIATION,
                    oldInstantiation, instantiation, !oldInstantiationESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetInstantiation() {
        InstantiationType oldInstantiation = instantiation;
        boolean oldInstantiationESet = instantiationESet;
        instantiation = INSTANTIATION_EDEFAULT;
        instantiationESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.SIMULATION_INFORMATION__INSTANTIATION,
                    oldInstantiation, INSTANTIATION_EDEFAULT, oldInstantiationESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetInstantiation() {
        return instantiationESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.SIMULATION_INFORMATION__COST:
            return basicSetCost(null, msgs);
        case Xpdl2Package.SIMULATION_INFORMATION__TIME_ESTIMATION:
            return basicSetTimeEstimation(null, msgs);
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
        case Xpdl2Package.SIMULATION_INFORMATION__COST:
            return getCost();
        case Xpdl2Package.SIMULATION_INFORMATION__TIME_ESTIMATION:
            return getTimeEstimation();
        case Xpdl2Package.SIMULATION_INFORMATION__INSTANTIATION:
            return getInstantiation();
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
        case Xpdl2Package.SIMULATION_INFORMATION__COST:
            setCost((Cost) newValue);
            return;
        case Xpdl2Package.SIMULATION_INFORMATION__TIME_ESTIMATION:
            setTimeEstimation((TimeEstimation) newValue);
            return;
        case Xpdl2Package.SIMULATION_INFORMATION__INSTANTIATION:
            setInstantiation((InstantiationType) newValue);
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
        case Xpdl2Package.SIMULATION_INFORMATION__COST:
            setCost((Cost) null);
            return;
        case Xpdl2Package.SIMULATION_INFORMATION__TIME_ESTIMATION:
            setTimeEstimation((TimeEstimation) null);
            return;
        case Xpdl2Package.SIMULATION_INFORMATION__INSTANTIATION:
            unsetInstantiation();
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
        case Xpdl2Package.SIMULATION_INFORMATION__COST:
            return cost != null;
        case Xpdl2Package.SIMULATION_INFORMATION__TIME_ESTIMATION:
            return timeEstimation != null;
        case Xpdl2Package.SIMULATION_INFORMATION__INSTANTIATION:
            return isSetInstantiation();
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
        result.append(" (instantiation: "); //$NON-NLS-1$
        if (instantiationESet)
            result.append(instantiation);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //SimulationInformationImpl
