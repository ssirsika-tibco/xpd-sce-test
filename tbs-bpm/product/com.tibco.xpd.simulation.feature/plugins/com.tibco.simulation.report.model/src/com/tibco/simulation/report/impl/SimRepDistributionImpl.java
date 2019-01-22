/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.simulation.report.impl;

import com.tibco.simulation.report.SimRepDistribution;
import com.tibco.simulation.report.SimRepDistributionCategory;
import com.tibco.simulation.report.SimRepPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Distribution</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepDistributionImpl#getLastResetTime <em>Last Reset Time</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepDistributionImpl#getObservedElements <em>Observed Elements</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepDistributionImpl#getDistributionCategory <em>Distribution Category</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepDistributionImpl#getParameter1 <em>Parameter1</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepDistributionImpl#getParameter2 <em>Parameter2</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepDistributionImpl#getSeed <em>Seed</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SimRepDistributionImpl extends EObjectImpl implements
        SimRepDistribution {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getLastResetTime() <em>Last Reset Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLastResetTime()
     * @generated
     * @ordered
     */
    protected static final double LAST_RESET_TIME_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getLastResetTime() <em>Last Reset Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLastResetTime()
     * @generated
     * @ordered
     */
    protected double lastResetTime = LAST_RESET_TIME_EDEFAULT;

    /**
     * This is true if the Last Reset Time attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean lastResetTimeESet;

    /**
     * The default value of the '{@link #getObservedElements() <em>Observed Elements</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getObservedElements()
     * @generated
     * @ordered
     */
    protected static final long OBSERVED_ELEMENTS_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getObservedElements() <em>Observed Elements</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getObservedElements()
     * @generated
     * @ordered
     */
    protected long observedElements = OBSERVED_ELEMENTS_EDEFAULT;

    /**
     * This is true if the Observed Elements attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean observedElementsESet;

    /**
     * The default value of the '{@link #getDistributionCategory() <em>Distribution Category</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDistributionCategory()
     * @generated
     * @ordered
     */
    protected static final SimRepDistributionCategory DISTRIBUTION_CATEGORY_EDEFAULT =
            SimRepDistributionCategory.REAL_CONSTANT_LITERAL;

    /**
     * The cached value of the '{@link #getDistributionCategory() <em>Distribution Category</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDistributionCategory()
     * @generated
     * @ordered
     */
    protected SimRepDistributionCategory distributionCategory =
            DISTRIBUTION_CATEGORY_EDEFAULT;

    /**
     * This is true if the Distribution Category attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean distributionCategoryESet;

    /**
     * The default value of the '{@link #getParameter1() <em>Parameter1</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getParameter1()
     * @generated
     * @ordered
     */
    protected static final double PARAMETER1_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getParameter1() <em>Parameter1</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getParameter1()
     * @generated
     * @ordered
     */
    protected double parameter1 = PARAMETER1_EDEFAULT;

    /**
     * This is true if the Parameter1 attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean parameter1ESet;

    /**
     * The default value of the '{@link #getParameter2() <em>Parameter2</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getParameter2()
     * @generated
     * @ordered
     */
    protected static final double PARAMETER2_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getParameter2() <em>Parameter2</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getParameter2()
     * @generated
     * @ordered
     */
    protected double parameter2 = PARAMETER2_EDEFAULT;

    /**
     * This is true if the Parameter2 attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean parameter2ESet;

    /**
     * The default value of the '{@link #getSeed() <em>Seed</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSeed()
     * @generated
     * @ordered
     */
    protected static final long SEED_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getSeed() <em>Seed</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSeed()
     * @generated
     * @ordered
     */
    protected long seed = SEED_EDEFAULT;

    /**
     * This is true if the Seed attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean seedESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SimRepDistributionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimRepPackage.Literals.SIM_REP_DISTRIBUTION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getLastResetTime() {
        return lastResetTime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLastResetTime(double newLastResetTime) {
        double oldLastResetTime = lastResetTime;
        lastResetTime = newLastResetTime;
        boolean oldLastResetTimeESet = lastResetTimeESet;
        lastResetTimeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_DISTRIBUTION__LAST_RESET_TIME,
                    oldLastResetTime, lastResetTime, !oldLastResetTimeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetLastResetTime() {
        double oldLastResetTime = lastResetTime;
        boolean oldLastResetTimeESet = lastResetTimeESet;
        lastResetTime = LAST_RESET_TIME_EDEFAULT;
        lastResetTimeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_DISTRIBUTION__LAST_RESET_TIME,
                    oldLastResetTime, LAST_RESET_TIME_EDEFAULT,
                    oldLastResetTimeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetLastResetTime() {
        return lastResetTimeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getObservedElements() {
        return observedElements;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setObservedElements(long newObservedElements) {
        long oldObservedElements = observedElements;
        observedElements = newObservedElements;
        boolean oldObservedElementsESet = observedElementsESet;
        observedElementsESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_DISTRIBUTION__OBSERVED_ELEMENTS,
                    oldObservedElements, observedElements,
                    !oldObservedElementsESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetObservedElements() {
        long oldObservedElements = observedElements;
        boolean oldObservedElementsESet = observedElementsESet;
        observedElements = OBSERVED_ELEMENTS_EDEFAULT;
        observedElementsESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_DISTRIBUTION__OBSERVED_ELEMENTS,
                    oldObservedElements, OBSERVED_ELEMENTS_EDEFAULT,
                    oldObservedElementsESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetObservedElements() {
        return observedElementsESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepDistributionCategory getDistributionCategory() {
        return distributionCategory;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDistributionCategory(
            SimRepDistributionCategory newDistributionCategory) {
        SimRepDistributionCategory oldDistributionCategory =
                distributionCategory;
        distributionCategory =
                newDistributionCategory == null ? DISTRIBUTION_CATEGORY_EDEFAULT
                        : newDistributionCategory;
        boolean oldDistributionCategoryESet = distributionCategoryESet;
        distributionCategoryESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_DISTRIBUTION__DISTRIBUTION_CATEGORY,
                    oldDistributionCategory, distributionCategory,
                    !oldDistributionCategoryESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetDistributionCategory() {
        SimRepDistributionCategory oldDistributionCategory =
                distributionCategory;
        boolean oldDistributionCategoryESet = distributionCategoryESet;
        distributionCategory = DISTRIBUTION_CATEGORY_EDEFAULT;
        distributionCategoryESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_DISTRIBUTION__DISTRIBUTION_CATEGORY,
                    oldDistributionCategory, DISTRIBUTION_CATEGORY_EDEFAULT,
                    oldDistributionCategoryESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetDistributionCategory() {
        return distributionCategoryESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getParameter1() {
        return parameter1;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setParameter1(double newParameter1) {
        double oldParameter1 = parameter1;
        parameter1 = newParameter1;
        boolean oldParameter1ESet = parameter1ESet;
        parameter1ESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_DISTRIBUTION__PARAMETER1,
                    oldParameter1, parameter1, !oldParameter1ESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetParameter1() {
        double oldParameter1 = parameter1;
        boolean oldParameter1ESet = parameter1ESet;
        parameter1 = PARAMETER1_EDEFAULT;
        parameter1ESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_DISTRIBUTION__PARAMETER1,
                    oldParameter1, PARAMETER1_EDEFAULT, oldParameter1ESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetParameter1() {
        return parameter1ESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getParameter2() {
        return parameter2;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setParameter2(double newParameter2) {
        double oldParameter2 = parameter2;
        parameter2 = newParameter2;
        boolean oldParameter2ESet = parameter2ESet;
        parameter2ESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_DISTRIBUTION__PARAMETER2,
                    oldParameter2, parameter2, !oldParameter2ESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetParameter2() {
        double oldParameter2 = parameter2;
        boolean oldParameter2ESet = parameter2ESet;
        parameter2 = PARAMETER2_EDEFAULT;
        parameter2ESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_DISTRIBUTION__PARAMETER2,
                    oldParameter2, PARAMETER2_EDEFAULT, oldParameter2ESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetParameter2() {
        return parameter2ESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getSeed() {
        return seed;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSeed(long newSeed) {
        long oldSeed = seed;
        seed = newSeed;
        boolean oldSeedESet = seedESet;
        seedESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_DISTRIBUTION__SEED, oldSeed, seed,
                    !oldSeedESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetSeed() {
        long oldSeed = seed;
        boolean oldSeedESet = seedESet;
        seed = SEED_EDEFAULT;
        seedESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_DISTRIBUTION__SEED, oldSeed,
                    SEED_EDEFAULT, oldSeedESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetSeed() {
        return seedESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case SimRepPackage.SIM_REP_DISTRIBUTION__LAST_RESET_TIME:
            return getLastResetTime();
        case SimRepPackage.SIM_REP_DISTRIBUTION__OBSERVED_ELEMENTS:
            return getObservedElements();
        case SimRepPackage.SIM_REP_DISTRIBUTION__DISTRIBUTION_CATEGORY:
            return getDistributionCategory();
        case SimRepPackage.SIM_REP_DISTRIBUTION__PARAMETER1:
            return getParameter1();
        case SimRepPackage.SIM_REP_DISTRIBUTION__PARAMETER2:
            return getParameter2();
        case SimRepPackage.SIM_REP_DISTRIBUTION__SEED:
            return getSeed();
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
        case SimRepPackage.SIM_REP_DISTRIBUTION__LAST_RESET_TIME:
            setLastResetTime((Double) newValue);
            return;
        case SimRepPackage.SIM_REP_DISTRIBUTION__OBSERVED_ELEMENTS:
            setObservedElements((Long) newValue);
            return;
        case SimRepPackage.SIM_REP_DISTRIBUTION__DISTRIBUTION_CATEGORY:
            setDistributionCategory((SimRepDistributionCategory) newValue);
            return;
        case SimRepPackage.SIM_REP_DISTRIBUTION__PARAMETER1:
            setParameter1((Double) newValue);
            return;
        case SimRepPackage.SIM_REP_DISTRIBUTION__PARAMETER2:
            setParameter2((Double) newValue);
            return;
        case SimRepPackage.SIM_REP_DISTRIBUTION__SEED:
            setSeed((Long) newValue);
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
        case SimRepPackage.SIM_REP_DISTRIBUTION__LAST_RESET_TIME:
            unsetLastResetTime();
            return;
        case SimRepPackage.SIM_REP_DISTRIBUTION__OBSERVED_ELEMENTS:
            unsetObservedElements();
            return;
        case SimRepPackage.SIM_REP_DISTRIBUTION__DISTRIBUTION_CATEGORY:
            unsetDistributionCategory();
            return;
        case SimRepPackage.SIM_REP_DISTRIBUTION__PARAMETER1:
            unsetParameter1();
            return;
        case SimRepPackage.SIM_REP_DISTRIBUTION__PARAMETER2:
            unsetParameter2();
            return;
        case SimRepPackage.SIM_REP_DISTRIBUTION__SEED:
            unsetSeed();
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
        case SimRepPackage.SIM_REP_DISTRIBUTION__LAST_RESET_TIME:
            return isSetLastResetTime();
        case SimRepPackage.SIM_REP_DISTRIBUTION__OBSERVED_ELEMENTS:
            return isSetObservedElements();
        case SimRepPackage.SIM_REP_DISTRIBUTION__DISTRIBUTION_CATEGORY:
            return isSetDistributionCategory();
        case SimRepPackage.SIM_REP_DISTRIBUTION__PARAMETER1:
            return isSetParameter1();
        case SimRepPackage.SIM_REP_DISTRIBUTION__PARAMETER2:
            return isSetParameter2();
        case SimRepPackage.SIM_REP_DISTRIBUTION__SEED:
            return isSetSeed();
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
        result.append(" (lastResetTime: "); //$NON-NLS-1$
        if (lastResetTimeESet)
            result.append(lastResetTime);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", observedElements: "); //$NON-NLS-1$
        if (observedElementsESet)
            result.append(observedElements);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", distributionCategory: "); //$NON-NLS-1$
        if (distributionCategoryESet)
            result.append(distributionCategory);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", parameter1: "); //$NON-NLS-1$
        if (parameter1ESet)
            result.append(parameter1);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", parameter2: "); //$NON-NLS-1$
        if (parameter2ESet)
            result.append(parameter2);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", seed: "); //$NON-NLS-1$
        if (seedESet)
            result.append(seed);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //SimRepDistributionImpl
