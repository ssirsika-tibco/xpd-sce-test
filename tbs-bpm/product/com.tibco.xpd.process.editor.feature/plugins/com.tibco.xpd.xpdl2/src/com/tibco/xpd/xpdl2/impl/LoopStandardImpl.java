/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import java.math.BigInteger;

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
import com.tibco.xpd.xpdl2.LoopStandard;
import com.tibco.xpd.xpdl2.TestTimeType;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Loop Standard</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.LoopStandardImpl#getOtherElements <em>Other Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.LoopStandardImpl#getLoopCounter <em>Loop Counter</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.LoopStandardImpl#getLoopMaximum <em>Loop Maximum</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.LoopStandardImpl#getTestTime <em>Test Time</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.LoopStandardImpl#getAttributeLoopCondition <em>Attribute Loop Condition</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.LoopStandardImpl#getLoopCondition <em>Loop Condition</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LoopStandardImpl extends EObjectImpl implements LoopStandard {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

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
     * The default value of the '{@link #getLoopCounter() <em>Loop Counter</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLoopCounter()
     * @generated
     * @ordered
     */
    protected static final BigInteger LOOP_COUNTER_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getLoopCounter() <em>Loop Counter</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLoopCounter()
     * @generated
     * @ordered
     */
    protected BigInteger loopCounter = LOOP_COUNTER_EDEFAULT;

    /**
     * The default value of the '{@link #getLoopMaximum() <em>Loop Maximum</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLoopMaximum()
     * @generated
     * @ordered
     */
    protected static final BigInteger LOOP_MAXIMUM_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getLoopMaximum() <em>Loop Maximum</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLoopMaximum()
     * @generated
     * @ordered
     */
    protected BigInteger loopMaximum = LOOP_MAXIMUM_EDEFAULT;

    /**
     * The default value of the '{@link #getTestTime() <em>Test Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTestTime()
     * @generated
     * @ordered
     */
    protected static final TestTimeType TEST_TIME_EDEFAULT =
            TestTimeType.BEFORE_LITERAL;

    /**
     * The cached value of the '{@link #getTestTime() <em>Test Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTestTime()
     * @generated
     * @ordered
     */
    protected TestTimeType testTime = TEST_TIME_EDEFAULT;

    /**
     * This is true if the Test Time attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean testTimeESet;

    /**
     * The default value of the '{@link #getAttributeLoopCondition() <em>Attribute Loop Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttributeLoopCondition()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE_LOOP_CONDITION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttributeLoopCondition() <em>Attribute Loop Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttributeLoopCondition()
     * @generated
     * @ordered
     */
    protected String attributeLoopCondition = ATTRIBUTE_LOOP_CONDITION_EDEFAULT;

    /**
     * The cached value of the '{@link #getLoopCondition() <em>Loop Condition</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLoopCondition()
     * @generated
     * @ordered
     */
    protected Expression loopCondition;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected LoopStandardImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.LOOP_STANDARD;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherElements() {
        if (otherElements == null) {
            otherElements =
                    new BasicFeatureMap(this,
                            Xpdl2Package.LOOP_STANDARD__OTHER_ELEMENTS);
        }
        return otherElements;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Expression getLoopCondition() {
        return loopCondition;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetLoopCondition(Expression newLoopCondition,
            NotificationChain msgs) {
        Expression oldLoopCondition = loopCondition;
        loopCondition = newLoopCondition;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.LOOP_STANDARD__LOOP_CONDITION,
                            oldLoopCondition, newLoopCondition);
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
    public void setLoopCondition(Expression newLoopCondition) {
        if (newLoopCondition != loopCondition) {
            NotificationChain msgs = null;
            if (loopCondition != null)
                msgs =
                        ((InternalEObject) loopCondition)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.LOOP_STANDARD__LOOP_CONDITION,
                                        null,
                                        msgs);
            if (newLoopCondition != null)
                msgs =
                        ((InternalEObject) newLoopCondition)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.LOOP_STANDARD__LOOP_CONDITION,
                                        null,
                                        msgs);
            msgs = basicSetLoopCondition(newLoopCondition, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.LOOP_STANDARD__LOOP_CONDITION,
                    newLoopCondition, newLoopCondition));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigInteger getLoopCounter() {
        return loopCounter;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLoopCounter(BigInteger newLoopCounter) {
        BigInteger oldLoopCounter = loopCounter;
        loopCounter = newLoopCounter;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.LOOP_STANDARD__LOOP_COUNTER, oldLoopCounter,
                    loopCounter));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigInteger getLoopMaximum() {
        return loopMaximum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLoopMaximum(BigInteger newLoopMaximum) {
        BigInteger oldLoopMaximum = loopMaximum;
        loopMaximum = newLoopMaximum;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.LOOP_STANDARD__LOOP_MAXIMUM, oldLoopMaximum,
                    loopMaximum));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TestTimeType getTestTime() {
        return testTime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTestTime(TestTimeType newTestTime) {
        TestTimeType oldTestTime = testTime;
        testTime = newTestTime == null ? TEST_TIME_EDEFAULT : newTestTime;
        boolean oldTestTimeESet = testTimeESet;
        testTimeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.LOOP_STANDARD__TEST_TIME, oldTestTime,
                    testTime, !oldTestTimeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetTestTime() {
        TestTimeType oldTestTime = testTime;
        boolean oldTestTimeESet = testTimeESet;
        testTime = TEST_TIME_EDEFAULT;
        testTimeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.LOOP_STANDARD__TEST_TIME, oldTestTime,
                    TEST_TIME_EDEFAULT, oldTestTimeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetTestTime() {
        return testTimeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttributeLoopCondition() {
        return attributeLoopCondition;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttributeLoopCondition(String newAttributeLoopCondition) {
        String oldAttributeLoopCondition = attributeLoopCondition;
        attributeLoopCondition = newAttributeLoopCondition;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.LOOP_STANDARD__ATTRIBUTE_LOOP_CONDITION,
                    oldAttributeLoopCondition, attributeLoopCondition));
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
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.LOOP_STANDARD__OTHER_ELEMENTS:
            return ((InternalEList<?>) getOtherElements())
                    .basicRemove(otherEnd, msgs);
        case Xpdl2Package.LOOP_STANDARD__LOOP_CONDITION:
            return basicSetLoopCondition(null, msgs);
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
        case Xpdl2Package.LOOP_STANDARD__OTHER_ELEMENTS:
            if (coreType)
                return getOtherElements();
            return ((FeatureMap.Internal) getOtherElements()).getWrapper();
        case Xpdl2Package.LOOP_STANDARD__LOOP_COUNTER:
            return getLoopCounter();
        case Xpdl2Package.LOOP_STANDARD__LOOP_MAXIMUM:
            return getLoopMaximum();
        case Xpdl2Package.LOOP_STANDARD__TEST_TIME:
            return getTestTime();
        case Xpdl2Package.LOOP_STANDARD__ATTRIBUTE_LOOP_CONDITION:
            return getAttributeLoopCondition();
        case Xpdl2Package.LOOP_STANDARD__LOOP_CONDITION:
            return getLoopCondition();
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
        case Xpdl2Package.LOOP_STANDARD__OTHER_ELEMENTS:
            ((FeatureMap.Internal) getOtherElements()).set(newValue);
            return;
        case Xpdl2Package.LOOP_STANDARD__LOOP_COUNTER:
            setLoopCounter((BigInteger) newValue);
            return;
        case Xpdl2Package.LOOP_STANDARD__LOOP_MAXIMUM:
            setLoopMaximum((BigInteger) newValue);
            return;
        case Xpdl2Package.LOOP_STANDARD__TEST_TIME:
            setTestTime((TestTimeType) newValue);
            return;
        case Xpdl2Package.LOOP_STANDARD__ATTRIBUTE_LOOP_CONDITION:
            setAttributeLoopCondition((String) newValue);
            return;
        case Xpdl2Package.LOOP_STANDARD__LOOP_CONDITION:
            setLoopCondition((Expression) newValue);
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
        case Xpdl2Package.LOOP_STANDARD__OTHER_ELEMENTS:
            getOtherElements().clear();
            return;
        case Xpdl2Package.LOOP_STANDARD__LOOP_COUNTER:
            setLoopCounter(LOOP_COUNTER_EDEFAULT);
            return;
        case Xpdl2Package.LOOP_STANDARD__LOOP_MAXIMUM:
            setLoopMaximum(LOOP_MAXIMUM_EDEFAULT);
            return;
        case Xpdl2Package.LOOP_STANDARD__TEST_TIME:
            unsetTestTime();
            return;
        case Xpdl2Package.LOOP_STANDARD__ATTRIBUTE_LOOP_CONDITION:
            setAttributeLoopCondition(ATTRIBUTE_LOOP_CONDITION_EDEFAULT);
            return;
        case Xpdl2Package.LOOP_STANDARD__LOOP_CONDITION:
            setLoopCondition((Expression) null);
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
        case Xpdl2Package.LOOP_STANDARD__OTHER_ELEMENTS:
            return otherElements != null && !otherElements.isEmpty();
        case Xpdl2Package.LOOP_STANDARD__LOOP_COUNTER:
            return LOOP_COUNTER_EDEFAULT == null ? loopCounter != null
                    : !LOOP_COUNTER_EDEFAULT.equals(loopCounter);
        case Xpdl2Package.LOOP_STANDARD__LOOP_MAXIMUM:
            return LOOP_MAXIMUM_EDEFAULT == null ? loopMaximum != null
                    : !LOOP_MAXIMUM_EDEFAULT.equals(loopMaximum);
        case Xpdl2Package.LOOP_STANDARD__TEST_TIME:
            return isSetTestTime();
        case Xpdl2Package.LOOP_STANDARD__ATTRIBUTE_LOOP_CONDITION:
            return ATTRIBUTE_LOOP_CONDITION_EDEFAULT == null ? attributeLoopCondition != null
                    : !ATTRIBUTE_LOOP_CONDITION_EDEFAULT
                            .equals(attributeLoopCondition);
        case Xpdl2Package.LOOP_STANDARD__LOOP_CONDITION:
            return loopCondition != null;
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
        result.append(" (otherElements: "); //$NON-NLS-1$
        result.append(otherElements);
        result.append(", loopCounter: "); //$NON-NLS-1$
        result.append(loopCounter);
        result.append(", loopMaximum: "); //$NON-NLS-1$
        result.append(loopMaximum);
        result.append(", testTime: "); //$NON-NLS-1$
        if (testTimeESet)
            result.append(testTime);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", attributeLoopCondition: "); //$NON-NLS-1$
        result.append(attributeLoopCondition);
        result.append(')');
        return result.toString();
    }

} //LoopStandardImpl
