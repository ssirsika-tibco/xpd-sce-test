/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.simulation.report.impl;

import com.tibco.simulation.report.SimRepActivityQueue;
import com.tibco.simulation.report.SimRepPackage;
import com.tibco.simulation.report.SimRepQueueOrder;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Activity Queue</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepActivityQueueImpl#getQueueOrder <em>Queue Order</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepActivityQueueImpl#getObservedCases <em>Observed Cases</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepActivityQueueImpl#getCurrentSize <em>Current Size</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepActivityQueueImpl#getMaxSize <em>Max Size</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepActivityQueueImpl#getAverageSize <em>Average Size</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepActivityQueueImpl#getAverageWait <em>Average Wait</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SimRepActivityQueueImpl extends EObjectImpl implements
        SimRepActivityQueue {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getQueueOrder() <em>Queue Order</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getQueueOrder()
     * @generated
     * @ordered
     */
    protected static final SimRepQueueOrder QUEUE_ORDER_EDEFAULT =
            SimRepQueueOrder.FIFO_LITERAL;

    /**
     * The cached value of the '{@link #getQueueOrder() <em>Queue Order</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getQueueOrder()
     * @generated
     * @ordered
     */
    protected SimRepQueueOrder queueOrder = QUEUE_ORDER_EDEFAULT;

    /**
     * This is true if the Queue Order attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean queueOrderESet;

    /**
     * The default value of the '{@link #getObservedCases() <em>Observed Cases</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getObservedCases()
     * @generated
     * @ordered
     */
    protected static final int OBSERVED_CASES_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getObservedCases() <em>Observed Cases</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getObservedCases()
     * @generated
     * @ordered
     */
    protected int observedCases = OBSERVED_CASES_EDEFAULT;

    /**
     * This is true if the Observed Cases attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean observedCasesESet;

    /**
     * The default value of the '{@link #getCurrentSize() <em>Current Size</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCurrentSize()
     * @generated
     * @ordered
     */
    protected static final int CURRENT_SIZE_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getCurrentSize() <em>Current Size</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCurrentSize()
     * @generated
     * @ordered
     */
    protected int currentSize = CURRENT_SIZE_EDEFAULT;

    /**
     * This is true if the Current Size attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean currentSizeESet;

    /**
     * The default value of the '{@link #getMaxSize() <em>Max Size</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMaxSize()
     * @generated
     * @ordered
     */
    protected static final int MAX_SIZE_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getMaxSize() <em>Max Size</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMaxSize()
     * @generated
     * @ordered
     */
    protected int maxSize = MAX_SIZE_EDEFAULT;

    /**
     * This is true if the Max Size attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean maxSizeESet;

    /**
     * The default value of the '{@link #getAverageSize() <em>Average Size</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAverageSize()
     * @generated
     * @ordered
     */
    protected static final double AVERAGE_SIZE_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getAverageSize() <em>Average Size</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAverageSize()
     * @generated
     * @ordered
     */
    protected double averageSize = AVERAGE_SIZE_EDEFAULT;

    /**
     * This is true if the Average Size attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean averageSizeESet;

    /**
     * The default value of the '{@link #getAverageWait() <em>Average Wait</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAverageWait()
     * @generated
     * @ordered
     */
    protected static final double AVERAGE_WAIT_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getAverageWait() <em>Average Wait</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAverageWait()
     * @generated
     * @ordered
     */
    protected double averageWait = AVERAGE_WAIT_EDEFAULT;

    /**
     * This is true if the Average Wait attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean averageWaitESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SimRepActivityQueueImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimRepPackage.Literals.SIM_REP_ACTIVITY_QUEUE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepQueueOrder getQueueOrder() {
        return queueOrder;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setQueueOrder(SimRepQueueOrder newQueueOrder) {
        SimRepQueueOrder oldQueueOrder = queueOrder;
        queueOrder =
                newQueueOrder == null ? QUEUE_ORDER_EDEFAULT : newQueueOrder;
        boolean oldQueueOrderESet = queueOrderESet;
        queueOrderESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_ACTIVITY_QUEUE__QUEUE_ORDER,
                    oldQueueOrder, queueOrder, !oldQueueOrderESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetQueueOrder() {
        SimRepQueueOrder oldQueueOrder = queueOrder;
        boolean oldQueueOrderESet = queueOrderESet;
        queueOrder = QUEUE_ORDER_EDEFAULT;
        queueOrderESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_ACTIVITY_QUEUE__QUEUE_ORDER,
                    oldQueueOrder, QUEUE_ORDER_EDEFAULT, oldQueueOrderESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetQueueOrder() {
        return queueOrderESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getObservedCases() {
        return observedCases;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setObservedCases(int newObservedCases) {
        int oldObservedCases = observedCases;
        observedCases = newObservedCases;
        boolean oldObservedCasesESet = observedCasesESet;
        observedCasesESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_ACTIVITY_QUEUE__OBSERVED_CASES,
                    oldObservedCases, observedCases, !oldObservedCasesESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetObservedCases() {
        int oldObservedCases = observedCases;
        boolean oldObservedCasesESet = observedCasesESet;
        observedCases = OBSERVED_CASES_EDEFAULT;
        observedCasesESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_ACTIVITY_QUEUE__OBSERVED_CASES,
                    oldObservedCases, OBSERVED_CASES_EDEFAULT,
                    oldObservedCasesESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetObservedCases() {
        return observedCasesESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getCurrentSize() {
        return currentSize;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCurrentSize(int newCurrentSize) {
        int oldCurrentSize = currentSize;
        currentSize = newCurrentSize;
        boolean oldCurrentSizeESet = currentSizeESet;
        currentSizeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_ACTIVITY_QUEUE__CURRENT_SIZE,
                    oldCurrentSize, currentSize, !oldCurrentSizeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetCurrentSize() {
        int oldCurrentSize = currentSize;
        boolean oldCurrentSizeESet = currentSizeESet;
        currentSize = CURRENT_SIZE_EDEFAULT;
        currentSizeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_ACTIVITY_QUEUE__CURRENT_SIZE,
                    oldCurrentSize, CURRENT_SIZE_EDEFAULT, oldCurrentSizeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetCurrentSize() {
        return currentSizeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMaxSize(int newMaxSize) {
        int oldMaxSize = maxSize;
        maxSize = newMaxSize;
        boolean oldMaxSizeESet = maxSizeESet;
        maxSizeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_ACTIVITY_QUEUE__MAX_SIZE, oldMaxSize,
                    maxSize, !oldMaxSizeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetMaxSize() {
        int oldMaxSize = maxSize;
        boolean oldMaxSizeESet = maxSizeESet;
        maxSize = MAX_SIZE_EDEFAULT;
        maxSizeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_ACTIVITY_QUEUE__MAX_SIZE, oldMaxSize,
                    MAX_SIZE_EDEFAULT, oldMaxSizeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetMaxSize() {
        return maxSizeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getAverageSize() {
        return averageSize;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAverageSize(double newAverageSize) {
        double oldAverageSize = averageSize;
        averageSize = newAverageSize;
        boolean oldAverageSizeESet = averageSizeESet;
        averageSizeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_ACTIVITY_QUEUE__AVERAGE_SIZE,
                    oldAverageSize, averageSize, !oldAverageSizeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetAverageSize() {
        double oldAverageSize = averageSize;
        boolean oldAverageSizeESet = averageSizeESet;
        averageSize = AVERAGE_SIZE_EDEFAULT;
        averageSizeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_ACTIVITY_QUEUE__AVERAGE_SIZE,
                    oldAverageSize, AVERAGE_SIZE_EDEFAULT, oldAverageSizeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetAverageSize() {
        return averageSizeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getAverageWait() {
        return averageWait;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAverageWait(double newAverageWait) {
        double oldAverageWait = averageWait;
        averageWait = newAverageWait;
        boolean oldAverageWaitESet = averageWaitESet;
        averageWaitESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_ACTIVITY_QUEUE__AVERAGE_WAIT,
                    oldAverageWait, averageWait, !oldAverageWaitESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetAverageWait() {
        double oldAverageWait = averageWait;
        boolean oldAverageWaitESet = averageWaitESet;
        averageWait = AVERAGE_WAIT_EDEFAULT;
        averageWaitESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_ACTIVITY_QUEUE__AVERAGE_WAIT,
                    oldAverageWait, AVERAGE_WAIT_EDEFAULT, oldAverageWaitESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetAverageWait() {
        return averageWaitESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case SimRepPackage.SIM_REP_ACTIVITY_QUEUE__QUEUE_ORDER:
            return getQueueOrder();
        case SimRepPackage.SIM_REP_ACTIVITY_QUEUE__OBSERVED_CASES:
            return getObservedCases();
        case SimRepPackage.SIM_REP_ACTIVITY_QUEUE__CURRENT_SIZE:
            return getCurrentSize();
        case SimRepPackage.SIM_REP_ACTIVITY_QUEUE__MAX_SIZE:
            return getMaxSize();
        case SimRepPackage.SIM_REP_ACTIVITY_QUEUE__AVERAGE_SIZE:
            return getAverageSize();
        case SimRepPackage.SIM_REP_ACTIVITY_QUEUE__AVERAGE_WAIT:
            return getAverageWait();
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
        case SimRepPackage.SIM_REP_ACTIVITY_QUEUE__QUEUE_ORDER:
            setQueueOrder((SimRepQueueOrder) newValue);
            return;
        case SimRepPackage.SIM_REP_ACTIVITY_QUEUE__OBSERVED_CASES:
            setObservedCases((Integer) newValue);
            return;
        case SimRepPackage.SIM_REP_ACTIVITY_QUEUE__CURRENT_SIZE:
            setCurrentSize((Integer) newValue);
            return;
        case SimRepPackage.SIM_REP_ACTIVITY_QUEUE__MAX_SIZE:
            setMaxSize((Integer) newValue);
            return;
        case SimRepPackage.SIM_REP_ACTIVITY_QUEUE__AVERAGE_SIZE:
            setAverageSize((Double) newValue);
            return;
        case SimRepPackage.SIM_REP_ACTIVITY_QUEUE__AVERAGE_WAIT:
            setAverageWait((Double) newValue);
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
        case SimRepPackage.SIM_REP_ACTIVITY_QUEUE__QUEUE_ORDER:
            unsetQueueOrder();
            return;
        case SimRepPackage.SIM_REP_ACTIVITY_QUEUE__OBSERVED_CASES:
            unsetObservedCases();
            return;
        case SimRepPackage.SIM_REP_ACTIVITY_QUEUE__CURRENT_SIZE:
            unsetCurrentSize();
            return;
        case SimRepPackage.SIM_REP_ACTIVITY_QUEUE__MAX_SIZE:
            unsetMaxSize();
            return;
        case SimRepPackage.SIM_REP_ACTIVITY_QUEUE__AVERAGE_SIZE:
            unsetAverageSize();
            return;
        case SimRepPackage.SIM_REP_ACTIVITY_QUEUE__AVERAGE_WAIT:
            unsetAverageWait();
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
        case SimRepPackage.SIM_REP_ACTIVITY_QUEUE__QUEUE_ORDER:
            return isSetQueueOrder();
        case SimRepPackage.SIM_REP_ACTIVITY_QUEUE__OBSERVED_CASES:
            return isSetObservedCases();
        case SimRepPackage.SIM_REP_ACTIVITY_QUEUE__CURRENT_SIZE:
            return isSetCurrentSize();
        case SimRepPackage.SIM_REP_ACTIVITY_QUEUE__MAX_SIZE:
            return isSetMaxSize();
        case SimRepPackage.SIM_REP_ACTIVITY_QUEUE__AVERAGE_SIZE:
            return isSetAverageSize();
        case SimRepPackage.SIM_REP_ACTIVITY_QUEUE__AVERAGE_WAIT:
            return isSetAverageWait();
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
        result.append(" (queueOrder: "); //$NON-NLS-1$
        if (queueOrderESet)
            result.append(queueOrder);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", observedCases: "); //$NON-NLS-1$
        if (observedCasesESet)
            result.append(observedCases);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", currentSize: "); //$NON-NLS-1$
        if (currentSizeESet)
            result.append(currentSize);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", maxSize: "); //$NON-NLS-1$
        if (maxSizeESet)
            result.append(maxSize);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", averageSize: "); //$NON-NLS-1$
        if (averageSizeESet)
            result.append(averageSize);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", averageWait: "); //$NON-NLS-1$
        if (averageWaitESet)
            result.append(averageWait);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //SimRepActivityQueueImpl
