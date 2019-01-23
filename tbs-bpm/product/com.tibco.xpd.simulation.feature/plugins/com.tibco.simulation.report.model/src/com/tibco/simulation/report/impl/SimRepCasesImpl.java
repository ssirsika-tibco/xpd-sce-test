/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.simulation.report.impl;

import com.tibco.simulation.report.SimRepCases;
import com.tibco.simulation.report.SimRepCost;
import com.tibco.simulation.report.SimRepDistribution;
import com.tibco.simulation.report.SimRepPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Cases</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepCasesImpl#getCaseStartIntervalDistribution <em>Case Start Interval Distribution</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepCasesImpl#getStartedCases <em>Started Cases</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepCasesImpl#getFinishedCases <em>Finished Cases</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepCasesImpl#getAverageCaseTime <em>Average Case Time</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepCasesImpl#getMinCaseTime <em>Min Case Time</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepCasesImpl#getMaxCaseTime <em>Max Case Time</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepCasesImpl#getCaseCost <em>Case Cost</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SimRepCasesImpl extends EObjectImpl implements SimRepCases {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getCaseStartIntervalDistribution() <em>Case Start Interval Distribution</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCaseStartIntervalDistribution()
     * @generated
     * @ordered
     */
    protected SimRepDistribution caseStartIntervalDistribution;

    /**
     * The default value of the '{@link #getStartedCases() <em>Started Cases</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartedCases()
     * @generated
     * @ordered
     */
    protected static final int STARTED_CASES_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getStartedCases() <em>Started Cases</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartedCases()
     * @generated
     * @ordered
     */
    protected int startedCases = STARTED_CASES_EDEFAULT;

    /**
     * This is true if the Started Cases attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean startedCasesESet;

    /**
     * The default value of the '{@link #getFinishedCases() <em>Finished Cases</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFinishedCases()
     * @generated
     * @ordered
     */
    protected static final int FINISHED_CASES_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getFinishedCases() <em>Finished Cases</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFinishedCases()
     * @generated
     * @ordered
     */
    protected int finishedCases = FINISHED_CASES_EDEFAULT;

    /**
     * This is true if the Finished Cases attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean finishedCasesESet;

    /**
     * The default value of the '{@link #getAverageCaseTime() <em>Average Case Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAverageCaseTime()
     * @generated
     * @ordered
     */
    protected static final double AVERAGE_CASE_TIME_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getAverageCaseTime() <em>Average Case Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAverageCaseTime()
     * @generated
     * @ordered
     */
    protected double averageCaseTime = AVERAGE_CASE_TIME_EDEFAULT;

    /**
     * This is true if the Average Case Time attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean averageCaseTimeESet;

    /**
     * The default value of the '{@link #getMinCaseTime() <em>Min Case Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMinCaseTime()
     * @generated
     * @ordered
     */
    protected static final double MIN_CASE_TIME_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getMinCaseTime() <em>Min Case Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMinCaseTime()
     * @generated
     * @ordered
     */
    protected double minCaseTime = MIN_CASE_TIME_EDEFAULT;

    /**
     * This is true if the Min Case Time attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean minCaseTimeESet;

    /**
     * The default value of the '{@link #getMaxCaseTime() <em>Max Case Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMaxCaseTime()
     * @generated
     * @ordered
     */
    protected static final double MAX_CASE_TIME_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getMaxCaseTime() <em>Max Case Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMaxCaseTime()
     * @generated
     * @ordered
     */
    protected double maxCaseTime = MAX_CASE_TIME_EDEFAULT;

    /**
     * This is true if the Max Case Time attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean maxCaseTimeESet;

    /**
     * The cached value of the '{@link #getCaseCost() <em>Case Cost</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCaseCost()
     * @generated
     * @ordered
     */
    protected SimRepCost caseCost;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SimRepCasesImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimRepPackage.Literals.SIM_REP_CASES;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepDistribution getCaseStartIntervalDistribution() {
        return caseStartIntervalDistribution;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCaseStartIntervalDistribution(
            SimRepDistribution newCaseStartIntervalDistribution,
            NotificationChain msgs) {
        SimRepDistribution oldCaseStartIntervalDistribution =
                caseStartIntervalDistribution;
        caseStartIntervalDistribution = newCaseStartIntervalDistribution;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(
                            this,
                            Notification.SET,
                            SimRepPackage.SIM_REP_CASES__CASE_START_INTERVAL_DISTRIBUTION,
                            oldCaseStartIntervalDistribution,
                            newCaseStartIntervalDistribution);
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
    public void setCaseStartIntervalDistribution(
            SimRepDistribution newCaseStartIntervalDistribution) {
        if (newCaseStartIntervalDistribution != caseStartIntervalDistribution) {
            NotificationChain msgs = null;
            if (caseStartIntervalDistribution != null)
                msgs =
                        ((InternalEObject) caseStartIntervalDistribution)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimRepPackage.SIM_REP_CASES__CASE_START_INTERVAL_DISTRIBUTION,
                                        null,
                                        msgs);
            if (newCaseStartIntervalDistribution != null)
                msgs =
                        ((InternalEObject) newCaseStartIntervalDistribution)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimRepPackage.SIM_REP_CASES__CASE_START_INTERVAL_DISTRIBUTION,
                                        null,
                                        msgs);
            msgs =
                    basicSetCaseStartIntervalDistribution(newCaseStartIntervalDistribution,
                            msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimRepPackage.SIM_REP_CASES__CASE_START_INTERVAL_DISTRIBUTION,
                    newCaseStartIntervalDistribution,
                    newCaseStartIntervalDistribution));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getStartedCases() {
        return startedCases;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setStartedCases(int newStartedCases) {
        int oldStartedCases = startedCases;
        startedCases = newStartedCases;
        boolean oldStartedCasesESet = startedCasesESet;
        startedCasesESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_CASES__STARTED_CASES,
                    oldStartedCases, startedCases, !oldStartedCasesESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetStartedCases() {
        int oldStartedCases = startedCases;
        boolean oldStartedCasesESet = startedCasesESet;
        startedCases = STARTED_CASES_EDEFAULT;
        startedCasesESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_CASES__STARTED_CASES,
                    oldStartedCases, STARTED_CASES_EDEFAULT,
                    oldStartedCasesESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetStartedCases() {
        return startedCasesESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getFinishedCases() {
        return finishedCases;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFinishedCases(int newFinishedCases) {
        int oldFinishedCases = finishedCases;
        finishedCases = newFinishedCases;
        boolean oldFinishedCasesESet = finishedCasesESet;
        finishedCasesESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_CASES__FINISHED_CASES,
                    oldFinishedCases, finishedCases, !oldFinishedCasesESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetFinishedCases() {
        int oldFinishedCases = finishedCases;
        boolean oldFinishedCasesESet = finishedCasesESet;
        finishedCases = FINISHED_CASES_EDEFAULT;
        finishedCasesESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_CASES__FINISHED_CASES,
                    oldFinishedCases, FINISHED_CASES_EDEFAULT,
                    oldFinishedCasesESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetFinishedCases() {
        return finishedCasesESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getAverageCaseTime() {
        return averageCaseTime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAverageCaseTime(double newAverageCaseTime) {
        double oldAverageCaseTime = averageCaseTime;
        averageCaseTime = newAverageCaseTime;
        boolean oldAverageCaseTimeESet = averageCaseTimeESet;
        averageCaseTimeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_CASES__AVERAGE_CASE_TIME,
                    oldAverageCaseTime, averageCaseTime,
                    !oldAverageCaseTimeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetAverageCaseTime() {
        double oldAverageCaseTime = averageCaseTime;
        boolean oldAverageCaseTimeESet = averageCaseTimeESet;
        averageCaseTime = AVERAGE_CASE_TIME_EDEFAULT;
        averageCaseTimeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_CASES__AVERAGE_CASE_TIME,
                    oldAverageCaseTime, AVERAGE_CASE_TIME_EDEFAULT,
                    oldAverageCaseTimeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetAverageCaseTime() {
        return averageCaseTimeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getMinCaseTime() {
        return minCaseTime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMinCaseTime(double newMinCaseTime) {
        double oldMinCaseTime = minCaseTime;
        minCaseTime = newMinCaseTime;
        boolean oldMinCaseTimeESet = minCaseTimeESet;
        minCaseTimeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_CASES__MIN_CASE_TIME, oldMinCaseTime,
                    minCaseTime, !oldMinCaseTimeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetMinCaseTime() {
        double oldMinCaseTime = minCaseTime;
        boolean oldMinCaseTimeESet = minCaseTimeESet;
        minCaseTime = MIN_CASE_TIME_EDEFAULT;
        minCaseTimeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_CASES__MIN_CASE_TIME, oldMinCaseTime,
                    MIN_CASE_TIME_EDEFAULT, oldMinCaseTimeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetMinCaseTime() {
        return minCaseTimeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getMaxCaseTime() {
        return maxCaseTime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMaxCaseTime(double newMaxCaseTime) {
        double oldMaxCaseTime = maxCaseTime;
        maxCaseTime = newMaxCaseTime;
        boolean oldMaxCaseTimeESet = maxCaseTimeESet;
        maxCaseTimeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_CASES__MAX_CASE_TIME, oldMaxCaseTime,
                    maxCaseTime, !oldMaxCaseTimeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetMaxCaseTime() {
        double oldMaxCaseTime = maxCaseTime;
        boolean oldMaxCaseTimeESet = maxCaseTimeESet;
        maxCaseTime = MAX_CASE_TIME_EDEFAULT;
        maxCaseTimeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_CASES__MAX_CASE_TIME, oldMaxCaseTime,
                    MAX_CASE_TIME_EDEFAULT, oldMaxCaseTimeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetMaxCaseTime() {
        return maxCaseTimeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepCost getCaseCost() {
        return caseCost;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCaseCost(SimRepCost newCaseCost,
            NotificationChain msgs) {
        SimRepCost oldCaseCost = caseCost;
        caseCost = newCaseCost;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            SimRepPackage.SIM_REP_CASES__CASE_COST,
                            oldCaseCost, newCaseCost);
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
    public void setCaseCost(SimRepCost newCaseCost) {
        if (newCaseCost != caseCost) {
            NotificationChain msgs = null;
            if (caseCost != null)
                msgs =
                        ((InternalEObject) caseCost)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimRepPackage.SIM_REP_CASES__CASE_COST,
                                        null,
                                        msgs);
            if (newCaseCost != null)
                msgs =
                        ((InternalEObject) newCaseCost)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimRepPackage.SIM_REP_CASES__CASE_COST,
                                        null,
                                        msgs);
            msgs = basicSetCaseCost(newCaseCost, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_CASES__CASE_COST, newCaseCost,
                    newCaseCost));
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
        case SimRepPackage.SIM_REP_CASES__CASE_START_INTERVAL_DISTRIBUTION:
            return basicSetCaseStartIntervalDistribution(null, msgs);
        case SimRepPackage.SIM_REP_CASES__CASE_COST:
            return basicSetCaseCost(null, msgs);
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
        case SimRepPackage.SIM_REP_CASES__CASE_START_INTERVAL_DISTRIBUTION:
            return getCaseStartIntervalDistribution();
        case SimRepPackage.SIM_REP_CASES__STARTED_CASES:
            return getStartedCases();
        case SimRepPackage.SIM_REP_CASES__FINISHED_CASES:
            return getFinishedCases();
        case SimRepPackage.SIM_REP_CASES__AVERAGE_CASE_TIME:
            return getAverageCaseTime();
        case SimRepPackage.SIM_REP_CASES__MIN_CASE_TIME:
            return getMinCaseTime();
        case SimRepPackage.SIM_REP_CASES__MAX_CASE_TIME:
            return getMaxCaseTime();
        case SimRepPackage.SIM_REP_CASES__CASE_COST:
            return getCaseCost();
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
        case SimRepPackage.SIM_REP_CASES__CASE_START_INTERVAL_DISTRIBUTION:
            setCaseStartIntervalDistribution((SimRepDistribution) newValue);
            return;
        case SimRepPackage.SIM_REP_CASES__STARTED_CASES:
            setStartedCases((Integer) newValue);
            return;
        case SimRepPackage.SIM_REP_CASES__FINISHED_CASES:
            setFinishedCases((Integer) newValue);
            return;
        case SimRepPackage.SIM_REP_CASES__AVERAGE_CASE_TIME:
            setAverageCaseTime((Double) newValue);
            return;
        case SimRepPackage.SIM_REP_CASES__MIN_CASE_TIME:
            setMinCaseTime((Double) newValue);
            return;
        case SimRepPackage.SIM_REP_CASES__MAX_CASE_TIME:
            setMaxCaseTime((Double) newValue);
            return;
        case SimRepPackage.SIM_REP_CASES__CASE_COST:
            setCaseCost((SimRepCost) newValue);
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
        case SimRepPackage.SIM_REP_CASES__CASE_START_INTERVAL_DISTRIBUTION:
            setCaseStartIntervalDistribution((SimRepDistribution) null);
            return;
        case SimRepPackage.SIM_REP_CASES__STARTED_CASES:
            unsetStartedCases();
            return;
        case SimRepPackage.SIM_REP_CASES__FINISHED_CASES:
            unsetFinishedCases();
            return;
        case SimRepPackage.SIM_REP_CASES__AVERAGE_CASE_TIME:
            unsetAverageCaseTime();
            return;
        case SimRepPackage.SIM_REP_CASES__MIN_CASE_TIME:
            unsetMinCaseTime();
            return;
        case SimRepPackage.SIM_REP_CASES__MAX_CASE_TIME:
            unsetMaxCaseTime();
            return;
        case SimRepPackage.SIM_REP_CASES__CASE_COST:
            setCaseCost((SimRepCost) null);
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
        case SimRepPackage.SIM_REP_CASES__CASE_START_INTERVAL_DISTRIBUTION:
            return caseStartIntervalDistribution != null;
        case SimRepPackage.SIM_REP_CASES__STARTED_CASES:
            return isSetStartedCases();
        case SimRepPackage.SIM_REP_CASES__FINISHED_CASES:
            return isSetFinishedCases();
        case SimRepPackage.SIM_REP_CASES__AVERAGE_CASE_TIME:
            return isSetAverageCaseTime();
        case SimRepPackage.SIM_REP_CASES__MIN_CASE_TIME:
            return isSetMinCaseTime();
        case SimRepPackage.SIM_REP_CASES__MAX_CASE_TIME:
            return isSetMaxCaseTime();
        case SimRepPackage.SIM_REP_CASES__CASE_COST:
            return caseCost != null;
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
        result.append(" (startedCases: "); //$NON-NLS-1$
        if (startedCasesESet)
            result.append(startedCases);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", finishedCases: "); //$NON-NLS-1$
        if (finishedCasesESet)
            result.append(finishedCases);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", averageCaseTime: "); //$NON-NLS-1$
        if (averageCaseTimeESet)
            result.append(averageCaseTime);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", minCaseTime: "); //$NON-NLS-1$
        if (minCaseTimeESet)
            result.append(minCaseTime);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", maxCaseTime: "); //$NON-NLS-1$
        if (maxCaseTimeESet)
            result.append(maxCaseTime);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //SimRepCasesImpl
