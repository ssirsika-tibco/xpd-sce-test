/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.impl;

import com.tibco.xpd.simulation.AbstractBasicDistribution;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.StartSimulationDataType;
import com.tibco.xpd.simulation.TimeDisplayUnitType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Start Simulation Data Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.impl.StartSimulationDataTypeImpl#getDuration <em>Duration</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.StartSimulationDataTypeImpl#getDisplayTimeUnit <em>Display Time Unit</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.StartSimulationDataTypeImpl#getNumberOfCases <em>Number Of Cases</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StartSimulationDataTypeImpl extends EObjectImpl implements
        StartSimulationDataType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getDuration() <em>Duration</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDuration()
     * @generated
     * @ordered
     */
    protected AbstractBasicDistribution duration;

    /**
     * The default value of the '{@link #getDisplayTimeUnit() <em>Display Time Unit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDisplayTimeUnit()
     * @generated
     * @ordered
     */
    protected static final TimeDisplayUnitType DISPLAY_TIME_UNIT_EDEFAULT =
            TimeDisplayUnitType.YEAR_LITERAL;

    /**
     * The cached value of the '{@link #getDisplayTimeUnit() <em>Display Time Unit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDisplayTimeUnit()
     * @generated
     * @ordered
     */
    protected TimeDisplayUnitType displayTimeUnit = DISPLAY_TIME_UNIT_EDEFAULT;

    /**
     * This is true if the Display Time Unit attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean displayTimeUnitESet;

    /**
     * The default value of the '{@link #getNumberOfCases() <em>Number Of Cases</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNumberOfCases()
     * @generated
     * @ordered
     */
    protected static final long NUMBER_OF_CASES_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getNumberOfCases() <em>Number Of Cases</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNumberOfCases()
     * @generated
     * @ordered
     */
    protected long numberOfCases = NUMBER_OF_CASES_EDEFAULT;

    /**
     * This is true if the Number Of Cases attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean numberOfCasesESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected StartSimulationDataTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimulationPackage.Literals.START_SIMULATION_DATA_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AbstractBasicDistribution getDuration() {
        return duration;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDuration(
            AbstractBasicDistribution newDuration, NotificationChain msgs) {
        AbstractBasicDistribution oldDuration = duration;
        duration = newDuration;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(
                            this,
                            Notification.SET,
                            SimulationPackage.START_SIMULATION_DATA_TYPE__DURATION,
                            oldDuration, newDuration);
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
    public void setDuration(AbstractBasicDistribution newDuration) {
        if (newDuration != duration) {
            NotificationChain msgs = null;
            if (duration != null)
                msgs =
                        ((InternalEObject) duration)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.START_SIMULATION_DATA_TYPE__DURATION,
                                        null,
                                        msgs);
            if (newDuration != null)
                msgs =
                        ((InternalEObject) newDuration)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.START_SIMULATION_DATA_TYPE__DURATION,
                                        null,
                                        msgs);
            msgs = basicSetDuration(newDuration, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimulationPackage.START_SIMULATION_DATA_TYPE__DURATION,
                    newDuration, newDuration));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TimeDisplayUnitType getDisplayTimeUnit() {
        return displayTimeUnit;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDisplayTimeUnit(TimeDisplayUnitType newDisplayTimeUnit) {
        TimeDisplayUnitType oldDisplayTimeUnit = displayTimeUnit;
        displayTimeUnit =
                newDisplayTimeUnit == null ? DISPLAY_TIME_UNIT_EDEFAULT
                        : newDisplayTimeUnit;
        boolean oldDisplayTimeUnitESet = displayTimeUnitESet;
        displayTimeUnitESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimulationPackage.START_SIMULATION_DATA_TYPE__DISPLAY_TIME_UNIT,
                    oldDisplayTimeUnit, displayTimeUnit,
                    !oldDisplayTimeUnitESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetDisplayTimeUnit() {
        TimeDisplayUnitType oldDisplayTimeUnit = displayTimeUnit;
        boolean oldDisplayTimeUnitESet = displayTimeUnitESet;
        displayTimeUnit = DISPLAY_TIME_UNIT_EDEFAULT;
        displayTimeUnitESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.UNSET,
                    SimulationPackage.START_SIMULATION_DATA_TYPE__DISPLAY_TIME_UNIT,
                    oldDisplayTimeUnit, DISPLAY_TIME_UNIT_EDEFAULT,
                    oldDisplayTimeUnitESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetDisplayTimeUnit() {
        return displayTimeUnitESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getNumberOfCases() {
        return numberOfCases;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNumberOfCases(long newNumberOfCases) {
        long oldNumberOfCases = numberOfCases;
        numberOfCases = newNumberOfCases;
        boolean oldNumberOfCasesESet = numberOfCasesESet;
        numberOfCasesESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimulationPackage.START_SIMULATION_DATA_TYPE__NUMBER_OF_CASES,
                    oldNumberOfCases, numberOfCases, !oldNumberOfCasesESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetNumberOfCases() {
        long oldNumberOfCases = numberOfCases;
        boolean oldNumberOfCasesESet = numberOfCasesESet;
        numberOfCases = NUMBER_OF_CASES_EDEFAULT;
        numberOfCasesESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.UNSET,
                    SimulationPackage.START_SIMULATION_DATA_TYPE__NUMBER_OF_CASES,
                    oldNumberOfCases, NUMBER_OF_CASES_EDEFAULT,
                    oldNumberOfCasesESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetNumberOfCases() {
        return numberOfCasesESet;
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
        case SimulationPackage.START_SIMULATION_DATA_TYPE__DURATION:
            return basicSetDuration(null, msgs);
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
        case SimulationPackage.START_SIMULATION_DATA_TYPE__DURATION:
            return getDuration();
        case SimulationPackage.START_SIMULATION_DATA_TYPE__DISPLAY_TIME_UNIT:
            return getDisplayTimeUnit();
        case SimulationPackage.START_SIMULATION_DATA_TYPE__NUMBER_OF_CASES:
            return new Long(getNumberOfCases());
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
        case SimulationPackage.START_SIMULATION_DATA_TYPE__DURATION:
            setDuration((AbstractBasicDistribution) newValue);
            return;
        case SimulationPackage.START_SIMULATION_DATA_TYPE__DISPLAY_TIME_UNIT:
            setDisplayTimeUnit((TimeDisplayUnitType) newValue);
            return;
        case SimulationPackage.START_SIMULATION_DATA_TYPE__NUMBER_OF_CASES:
            setNumberOfCases(((Long) newValue).longValue());
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
        case SimulationPackage.START_SIMULATION_DATA_TYPE__DURATION:
            setDuration((AbstractBasicDistribution) null);
            return;
        case SimulationPackage.START_SIMULATION_DATA_TYPE__DISPLAY_TIME_UNIT:
            unsetDisplayTimeUnit();
            return;
        case SimulationPackage.START_SIMULATION_DATA_TYPE__NUMBER_OF_CASES:
            unsetNumberOfCases();
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
        case SimulationPackage.START_SIMULATION_DATA_TYPE__DURATION:
            return duration != null;
        case SimulationPackage.START_SIMULATION_DATA_TYPE__DISPLAY_TIME_UNIT:
            return isSetDisplayTimeUnit();
        case SimulationPackage.START_SIMULATION_DATA_TYPE__NUMBER_OF_CASES:
            return isSetNumberOfCases();
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
        result.append(" (displayTimeUnit: "); //$NON-NLS-1$
        if (displayTimeUnitESet)
            result.append(displayTimeUnit);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", numberOfCases: "); //$NON-NLS-1$
        if (numberOfCasesESet)
            result.append(numberOfCases);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //StartSimulationDataTypeImpl