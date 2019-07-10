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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.TriggerTimer;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Trigger Timer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerTimerImpl#getOtherAttributes <em>Other Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerTimerImpl#getOtherElements <em>Other Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerTimerImpl#getDeprecatedTimeCycle <em>Deprecated Time Cycle</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerTimerImpl#getDeprecatedTimeDate <em>Deprecated Time Date</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerTimerImpl#getTimeDate <em>Time Date</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerTimerImpl#getTimeCycle <em>Time Cycle</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TriggerTimerImpl extends EObjectImpl implements TriggerTimer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getOtherAttributes() <em>Other Attributes</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOtherAttributes()
     * @generated
     * @ordered
     */
    protected FeatureMap otherAttributes;

    /**
     * The cached value of the '{@link #getOtherElements() <em>Other Elements</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOtherElements()
     * @generated
     * @ordered
     */
    protected FeatureMap otherElements;

    /**
     * The default value of the '{@link #getDeprecatedTimeCycle() <em>Deprecated Time Cycle</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDeprecatedTimeCycle()
     * @generated
     * @ordered
     */
    protected static final String DEPRECATED_TIME_CYCLE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDeprecatedTimeCycle() <em>Deprecated Time Cycle</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDeprecatedTimeCycle()
     * @generated
     * @ordered
     */
    protected String deprecatedTimeCycle = DEPRECATED_TIME_CYCLE_EDEFAULT;

    /**
     * The default value of the '{@link #getDeprecatedTimeDate() <em>Deprecated Time Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDeprecatedTimeDate()
     * @generated
     * @ordered
     */
    protected static final String DEPRECATED_TIME_DATE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDeprecatedTimeDate() <em>Deprecated Time Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDeprecatedTimeDate()
     * @generated
     * @ordered
     */
    protected String deprecatedTimeDate = DEPRECATED_TIME_DATE_EDEFAULT;

    /**
     * The cached value of the '{@link #getTimeDate() <em>Time Date</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTimeDate()
     * @generated
     * @ordered
     */
    protected Expression timeDate;

    /**
     * The cached value of the '{@link #getTimeCycle() <em>Time Cycle</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTimeCycle()
     * @generated
     * @ordered
     */
    protected Expression timeCycle;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TriggerTimerImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.TRIGGER_TIMER;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherAttributes() {
        if (otherAttributes == null) {
            otherAttributes = new BasicFeatureMap(this, Xpdl2Package.TRIGGER_TIMER__OTHER_ATTRIBUTES);
        }
        return otherAttributes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherElements() {
        if (otherElements == null) {
            otherElements = new BasicFeatureMap(this, Xpdl2Package.TRIGGER_TIMER__OTHER_ELEMENTS);
        }
        return otherElements;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDeprecatedTimeCycle() {
        return deprecatedTimeCycle;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDeprecatedTimeCycle(String newDeprecatedTimeCycle) {
        String oldDeprecatedTimeCycle = deprecatedTimeCycle;
        deprecatedTimeCycle = newDeprecatedTimeCycle;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.TRIGGER_TIMER__DEPRECATED_TIME_CYCLE,
                    oldDeprecatedTimeCycle, deprecatedTimeCycle));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDeprecatedTimeDate() {
        return deprecatedTimeDate;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDeprecatedTimeDate(String newDeprecatedTimeDate) {
        String oldDeprecatedTimeDate = deprecatedTimeDate;
        deprecatedTimeDate = newDeprecatedTimeDate;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.TRIGGER_TIMER__DEPRECATED_TIME_DATE,
                    oldDeprecatedTimeDate, deprecatedTimeDate));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Expression getTimeCycle() {
        return timeCycle;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTimeCycle(Expression newTimeCycle, NotificationChain msgs) {
        Expression oldTimeCycle = timeCycle;
        timeCycle = newTimeCycle;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRIGGER_TIMER__TIME_CYCLE, oldTimeCycle, newTimeCycle);
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
    public void setTimeCycle(Expression newTimeCycle) {
        if (newTimeCycle != timeCycle) {
            NotificationChain msgs = null;
            if (timeCycle != null)
                msgs = ((InternalEObject) timeCycle).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TRIGGER_TIMER__TIME_CYCLE,
                        null,
                        msgs);
            if (newTimeCycle != null)
                msgs = ((InternalEObject) newTimeCycle)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.TRIGGER_TIMER__TIME_CYCLE, null, msgs);
            msgs = basicSetTimeCycle(newTimeCycle, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.TRIGGER_TIMER__TIME_CYCLE, newTimeCycle,
                    newTimeCycle));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EObject getOtherElement(String elementName) {
        // TODO: implement this method
        // Ensure that you remove @generated or mark it @generated NOT
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Expression getTimeDate() {
        return timeDate;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTimeDate(Expression newTimeDate, NotificationChain msgs) {
        Expression oldTimeDate = timeDate;
        timeDate = newTimeDate;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRIGGER_TIMER__TIME_DATE, oldTimeDate, newTimeDate);
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
    public void setTimeDate(Expression newTimeDate) {
        if (newTimeDate != timeDate) {
            NotificationChain msgs = null;
            if (timeDate != null)
                msgs = ((InternalEObject) timeDate).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TRIGGER_TIMER__TIME_DATE,
                        null,
                        msgs);
            if (newTimeDate != null)
                msgs = ((InternalEObject) newTimeDate)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.TRIGGER_TIMER__TIME_DATE, null, msgs);
            msgs = basicSetTimeDate(newTimeDate, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.TRIGGER_TIMER__TIME_DATE, newTimeDate,
                    newTimeDate));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.TRIGGER_TIMER__OTHER_ATTRIBUTES:
            return ((InternalEList<?>) getOtherAttributes()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.TRIGGER_TIMER__OTHER_ELEMENTS:
            return ((InternalEList<?>) getOtherElements()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.TRIGGER_TIMER__TIME_DATE:
            return basicSetTimeDate(null, msgs);
        case Xpdl2Package.TRIGGER_TIMER__TIME_CYCLE:
            return basicSetTimeCycle(null, msgs);
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
        case Xpdl2Package.TRIGGER_TIMER__OTHER_ATTRIBUTES:
            if (coreType)
                return getOtherAttributes();
            return ((FeatureMap.Internal) getOtherAttributes()).getWrapper();
        case Xpdl2Package.TRIGGER_TIMER__OTHER_ELEMENTS:
            if (coreType)
                return getOtherElements();
            return ((FeatureMap.Internal) getOtherElements()).getWrapper();
        case Xpdl2Package.TRIGGER_TIMER__DEPRECATED_TIME_CYCLE:
            return getDeprecatedTimeCycle();
        case Xpdl2Package.TRIGGER_TIMER__DEPRECATED_TIME_DATE:
            return getDeprecatedTimeDate();
        case Xpdl2Package.TRIGGER_TIMER__TIME_DATE:
            return getTimeDate();
        case Xpdl2Package.TRIGGER_TIMER__TIME_CYCLE:
            return getTimeCycle();
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
        case Xpdl2Package.TRIGGER_TIMER__OTHER_ATTRIBUTES:
            ((FeatureMap.Internal) getOtherAttributes()).set(newValue);
            return;
        case Xpdl2Package.TRIGGER_TIMER__OTHER_ELEMENTS:
            ((FeatureMap.Internal) getOtherElements()).set(newValue);
            return;
        case Xpdl2Package.TRIGGER_TIMER__DEPRECATED_TIME_CYCLE:
            setDeprecatedTimeCycle((String) newValue);
            return;
        case Xpdl2Package.TRIGGER_TIMER__DEPRECATED_TIME_DATE:
            setDeprecatedTimeDate((String) newValue);
            return;
        case Xpdl2Package.TRIGGER_TIMER__TIME_DATE:
            setTimeDate((Expression) newValue);
            return;
        case Xpdl2Package.TRIGGER_TIMER__TIME_CYCLE:
            setTimeCycle((Expression) newValue);
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
        case Xpdl2Package.TRIGGER_TIMER__OTHER_ATTRIBUTES:
            getOtherAttributes().clear();
            return;
        case Xpdl2Package.TRIGGER_TIMER__OTHER_ELEMENTS:
            getOtherElements().clear();
            return;
        case Xpdl2Package.TRIGGER_TIMER__DEPRECATED_TIME_CYCLE:
            setDeprecatedTimeCycle(DEPRECATED_TIME_CYCLE_EDEFAULT);
            return;
        case Xpdl2Package.TRIGGER_TIMER__DEPRECATED_TIME_DATE:
            setDeprecatedTimeDate(DEPRECATED_TIME_DATE_EDEFAULT);
            return;
        case Xpdl2Package.TRIGGER_TIMER__TIME_DATE:
            setTimeDate((Expression) null);
            return;
        case Xpdl2Package.TRIGGER_TIMER__TIME_CYCLE:
            setTimeCycle((Expression) null);
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
        case Xpdl2Package.TRIGGER_TIMER__OTHER_ATTRIBUTES:
            return otherAttributes != null && !otherAttributes.isEmpty();
        case Xpdl2Package.TRIGGER_TIMER__OTHER_ELEMENTS:
            return otherElements != null && !otherElements.isEmpty();
        case Xpdl2Package.TRIGGER_TIMER__DEPRECATED_TIME_CYCLE:
            return DEPRECATED_TIME_CYCLE_EDEFAULT == null ? deprecatedTimeCycle != null
                    : !DEPRECATED_TIME_CYCLE_EDEFAULT.equals(deprecatedTimeCycle);
        case Xpdl2Package.TRIGGER_TIMER__DEPRECATED_TIME_DATE:
            return DEPRECATED_TIME_DATE_EDEFAULT == null ? deprecatedTimeDate != null
                    : !DEPRECATED_TIME_DATE_EDEFAULT.equals(deprecatedTimeDate);
        case Xpdl2Package.TRIGGER_TIMER__TIME_DATE:
            return timeDate != null;
        case Xpdl2Package.TRIGGER_TIMER__TIME_CYCLE:
            return timeCycle != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == OtherElementsContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.TRIGGER_TIMER__OTHER_ELEMENTS:
                return Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS;
            default:
                return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
        if (baseClass == OtherElementsContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS:
                return Xpdl2Package.TRIGGER_TIMER__OTHER_ELEMENTS;
            default:
                return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
        result.append(" (otherAttributes: "); //$NON-NLS-1$
        result.append(otherAttributes);
        result.append(", otherElements: "); //$NON-NLS-1$
        result.append(otherElements);
        result.append(", deprecatedTimeCycle: "); //$NON-NLS-1$
        result.append(deprecatedTimeCycle);
        result.append(", deprecatedTimeDate: "); //$NON-NLS-1$
        result.append(deprecatedTimeDate);
        result.append(')');
        return result.toString();
    }

} //TriggerTimerImpl
