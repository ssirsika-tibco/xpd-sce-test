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
import com.tibco.xpd.xpdl2.LoopMultiInstance;
import com.tibco.xpd.xpdl2.MIFlowConditionType;
import com.tibco.xpd.xpdl2.MIOrderingType;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Loop Multi Instance</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.LoopMultiInstanceImpl#getOtherElements <em>Other Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.LoopMultiInstanceImpl#getLoopCounter <em>Loop Counter</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.LoopMultiInstanceImpl#getMIFlowCondition <em>MI Flow Condition</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.LoopMultiInstanceImpl#getMIOrdering <em>MI Ordering</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.LoopMultiInstanceImpl#getMICondition <em>MI Condition</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.LoopMultiInstanceImpl#getComplexMIFlowCondition <em>Complex MI Flow Condition</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.LoopMultiInstanceImpl#getAttributeComplexMI_FlowCondition <em>Attribute Complex MI Flow Condition</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.LoopMultiInstanceImpl#getAttributeMI_Condition <em>Attribute MI Condition</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LoopMultiInstanceImpl extends EObjectImpl implements LoopMultiInstance {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

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
     * The default value of the '{@link #getMIFlowCondition() <em>MI Flow Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMIFlowCondition()
     * @generated
     * @ordered
     */
    protected static final MIFlowConditionType MI_FLOW_CONDITION_EDEFAULT = MIFlowConditionType.ALL_LITERAL;

    /**
     * The cached value of the '{@link #getMIFlowCondition() <em>MI Flow Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMIFlowCondition()
     * @generated
     * @ordered
     */
    protected MIFlowConditionType mIFlowCondition = MI_FLOW_CONDITION_EDEFAULT;

    /**
     * This is true if the MI Flow Condition attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean mIFlowConditionESet;

    /**
     * The default value of the '{@link #getMIOrdering() <em>MI Ordering</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMIOrdering()
     * @generated
     * @ordered
     */
    protected static final MIOrderingType MI_ORDERING_EDEFAULT = MIOrderingType.SEQUENTIAL_LITERAL;

    /**
     * The cached value of the '{@link #getMIOrdering() <em>MI Ordering</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMIOrdering()
     * @generated
     * @ordered
     */
    protected MIOrderingType mIOrdering = MI_ORDERING_EDEFAULT;

    /**
     * This is true if the MI Ordering attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean mIOrderingESet;

    /**
     * The cached value of the '{@link #getMICondition() <em>MI Condition</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMICondition()
     * @generated
     * @ordered
     */
    protected Expression mICondition;

    /**
     * The cached value of the '{@link #getComplexMIFlowCondition() <em>Complex MI Flow Condition</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getComplexMIFlowCondition()
     * @generated
     * @ordered
     */
    protected Expression complexMIFlowCondition;

    /**
     * The default value of the '{@link #getAttributeComplexMI_FlowCondition() <em>Attribute Complex MI Flow Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttributeComplexMI_FlowCondition()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE_COMPLEX_MI_FLOW_CONDITION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttributeComplexMI_FlowCondition() <em>Attribute Complex MI Flow Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttributeComplexMI_FlowCondition()
     * @generated
     * @ordered
     */
    protected String attributeComplexMI_FlowCondition = ATTRIBUTE_COMPLEX_MI_FLOW_CONDITION_EDEFAULT;

    /**
     * The default value of the '{@link #getAttributeMI_Condition() <em>Attribute MI Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttributeMI_Condition()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE_MI_CONDITION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttributeMI_Condition() <em>Attribute MI Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttributeMI_Condition()
     * @generated
     * @ordered
     */
    protected String attributeMI_Condition = ATTRIBUTE_MI_CONDITION_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected LoopMultiInstanceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.LOOP_MULTI_INSTANCE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherElements() {
        if (otherElements == null) {
            otherElements = new BasicFeatureMap(this, Xpdl2Package.LOOP_MULTI_INSTANCE__OTHER_ELEMENTS);
        }
        return otherElements;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Expression getComplexMIFlowCondition() {
        return complexMIFlowCondition;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetComplexMIFlowCondition(Expression newComplexMIFlowCondition,
            NotificationChain msgs) {
        Expression oldComplexMIFlowCondition = complexMIFlowCondition;
        complexMIFlowCondition = newComplexMIFlowCondition;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.LOOP_MULTI_INSTANCE__COMPLEX_MI_FLOW_CONDITION, oldComplexMIFlowCondition,
                    newComplexMIFlowCondition);
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
    public void setComplexMIFlowCondition(Expression newComplexMIFlowCondition) {
        if (newComplexMIFlowCondition != complexMIFlowCondition) {
            NotificationChain msgs = null;
            if (complexMIFlowCondition != null)
                msgs = ((InternalEObject) complexMIFlowCondition).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.LOOP_MULTI_INSTANCE__COMPLEX_MI_FLOW_CONDITION,
                        null,
                        msgs);
            if (newComplexMIFlowCondition != null)
                msgs = ((InternalEObject) newComplexMIFlowCondition).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.LOOP_MULTI_INSTANCE__COMPLEX_MI_FLOW_CONDITION,
                        null,
                        msgs);
            msgs = basicSetComplexMIFlowCondition(newComplexMIFlowCondition, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.LOOP_MULTI_INSTANCE__COMPLEX_MI_FLOW_CONDITION, newComplexMIFlowCondition,
                    newComplexMIFlowCondition));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttributeComplexMI_FlowCondition() {
        return attributeComplexMI_FlowCondition;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttributeComplexMI_FlowCondition(String newAttributeComplexMI_FlowCondition) {
        String oldAttributeComplexMI_FlowCondition = attributeComplexMI_FlowCondition;
        attributeComplexMI_FlowCondition = newAttributeComplexMI_FlowCondition;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.LOOP_MULTI_INSTANCE__ATTRIBUTE_COMPLEX_MI_FLOW_CONDITION,
                    oldAttributeComplexMI_FlowCondition, attributeComplexMI_FlowCondition));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttributeMI_Condition() {
        return attributeMI_Condition;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttributeMI_Condition(String newAttributeMI_Condition) {
        String oldAttributeMI_Condition = attributeMI_Condition;
        attributeMI_Condition = newAttributeMI_Condition;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.LOOP_MULTI_INSTANCE__ATTRIBUTE_MI_CONDITION, oldAttributeMI_Condition,
                    attributeMI_Condition));
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
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.LOOP_MULTI_INSTANCE__LOOP_COUNTER,
                    oldLoopCounter, loopCounter));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Expression getMICondition() {
        return mICondition;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetMICondition(Expression newMICondition, NotificationChain msgs) {
        Expression oldMICondition = mICondition;
        mICondition = newMICondition;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.LOOP_MULTI_INSTANCE__MI_CONDITION, oldMICondition, newMICondition);
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
    public void setMICondition(Expression newMICondition) {
        if (newMICondition != mICondition) {
            NotificationChain msgs = null;
            if (mICondition != null)
                msgs = ((InternalEObject) mICondition).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.LOOP_MULTI_INSTANCE__MI_CONDITION,
                        null,
                        msgs);
            if (newMICondition != null)
                msgs = ((InternalEObject) newMICondition).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.LOOP_MULTI_INSTANCE__MI_CONDITION,
                        null,
                        msgs);
            msgs = basicSetMICondition(newMICondition, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.LOOP_MULTI_INSTANCE__MI_CONDITION,
                    newMICondition, newMICondition));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MIFlowConditionType getMIFlowCondition() {
        return mIFlowCondition;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMIFlowCondition(MIFlowConditionType newMIFlowCondition) {
        MIFlowConditionType oldMIFlowCondition = mIFlowCondition;
        mIFlowCondition = newMIFlowCondition == null ? MI_FLOW_CONDITION_EDEFAULT : newMIFlowCondition;
        boolean oldMIFlowConditionESet = mIFlowConditionESet;
        mIFlowConditionESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.LOOP_MULTI_INSTANCE__MI_FLOW_CONDITION,
                    oldMIFlowCondition, mIFlowCondition, !oldMIFlowConditionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetMIFlowCondition() {
        MIFlowConditionType oldMIFlowCondition = mIFlowCondition;
        boolean oldMIFlowConditionESet = mIFlowConditionESet;
        mIFlowCondition = MI_FLOW_CONDITION_EDEFAULT;
        mIFlowConditionESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.LOOP_MULTI_INSTANCE__MI_FLOW_CONDITION,
                    oldMIFlowCondition, MI_FLOW_CONDITION_EDEFAULT, oldMIFlowConditionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetMIFlowCondition() {
        return mIFlowConditionESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MIOrderingType getMIOrdering() {
        return mIOrdering;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMIOrdering(MIOrderingType newMIOrdering) {
        MIOrderingType oldMIOrdering = mIOrdering;
        mIOrdering = newMIOrdering == null ? MI_ORDERING_EDEFAULT : newMIOrdering;
        boolean oldMIOrderingESet = mIOrderingESet;
        mIOrderingESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.LOOP_MULTI_INSTANCE__MI_ORDERING,
                    oldMIOrdering, mIOrdering, !oldMIOrderingESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetMIOrdering() {
        MIOrderingType oldMIOrdering = mIOrdering;
        boolean oldMIOrderingESet = mIOrderingESet;
        mIOrdering = MI_ORDERING_EDEFAULT;
        mIOrderingESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.LOOP_MULTI_INSTANCE__MI_ORDERING,
                    oldMIOrdering, MI_ORDERING_EDEFAULT, oldMIOrderingESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetMIOrdering() {
        return mIOrderingESet;
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
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.LOOP_MULTI_INSTANCE__OTHER_ELEMENTS:
            return ((InternalEList<?>) getOtherElements()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.LOOP_MULTI_INSTANCE__MI_CONDITION:
            return basicSetMICondition(null, msgs);
        case Xpdl2Package.LOOP_MULTI_INSTANCE__COMPLEX_MI_FLOW_CONDITION:
            return basicSetComplexMIFlowCondition(null, msgs);
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
        case Xpdl2Package.LOOP_MULTI_INSTANCE__OTHER_ELEMENTS:
            if (coreType)
                return getOtherElements();
            return ((FeatureMap.Internal) getOtherElements()).getWrapper();
        case Xpdl2Package.LOOP_MULTI_INSTANCE__LOOP_COUNTER:
            return getLoopCounter();
        case Xpdl2Package.LOOP_MULTI_INSTANCE__MI_FLOW_CONDITION:
            return getMIFlowCondition();
        case Xpdl2Package.LOOP_MULTI_INSTANCE__MI_ORDERING:
            return getMIOrdering();
        case Xpdl2Package.LOOP_MULTI_INSTANCE__MI_CONDITION:
            return getMICondition();
        case Xpdl2Package.LOOP_MULTI_INSTANCE__COMPLEX_MI_FLOW_CONDITION:
            return getComplexMIFlowCondition();
        case Xpdl2Package.LOOP_MULTI_INSTANCE__ATTRIBUTE_COMPLEX_MI_FLOW_CONDITION:
            return getAttributeComplexMI_FlowCondition();
        case Xpdl2Package.LOOP_MULTI_INSTANCE__ATTRIBUTE_MI_CONDITION:
            return getAttributeMI_Condition();
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
        case Xpdl2Package.LOOP_MULTI_INSTANCE__OTHER_ELEMENTS:
            ((FeatureMap.Internal) getOtherElements()).set(newValue);
            return;
        case Xpdl2Package.LOOP_MULTI_INSTANCE__LOOP_COUNTER:
            setLoopCounter((BigInteger) newValue);
            return;
        case Xpdl2Package.LOOP_MULTI_INSTANCE__MI_FLOW_CONDITION:
            setMIFlowCondition((MIFlowConditionType) newValue);
            return;
        case Xpdl2Package.LOOP_MULTI_INSTANCE__MI_ORDERING:
            setMIOrdering((MIOrderingType) newValue);
            return;
        case Xpdl2Package.LOOP_MULTI_INSTANCE__MI_CONDITION:
            setMICondition((Expression) newValue);
            return;
        case Xpdl2Package.LOOP_MULTI_INSTANCE__COMPLEX_MI_FLOW_CONDITION:
            setComplexMIFlowCondition((Expression) newValue);
            return;
        case Xpdl2Package.LOOP_MULTI_INSTANCE__ATTRIBUTE_COMPLEX_MI_FLOW_CONDITION:
            setAttributeComplexMI_FlowCondition((String) newValue);
            return;
        case Xpdl2Package.LOOP_MULTI_INSTANCE__ATTRIBUTE_MI_CONDITION:
            setAttributeMI_Condition((String) newValue);
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
        case Xpdl2Package.LOOP_MULTI_INSTANCE__OTHER_ELEMENTS:
            getOtherElements().clear();
            return;
        case Xpdl2Package.LOOP_MULTI_INSTANCE__LOOP_COUNTER:
            setLoopCounter(LOOP_COUNTER_EDEFAULT);
            return;
        case Xpdl2Package.LOOP_MULTI_INSTANCE__MI_FLOW_CONDITION:
            unsetMIFlowCondition();
            return;
        case Xpdl2Package.LOOP_MULTI_INSTANCE__MI_ORDERING:
            unsetMIOrdering();
            return;
        case Xpdl2Package.LOOP_MULTI_INSTANCE__MI_CONDITION:
            setMICondition((Expression) null);
            return;
        case Xpdl2Package.LOOP_MULTI_INSTANCE__COMPLEX_MI_FLOW_CONDITION:
            setComplexMIFlowCondition((Expression) null);
            return;
        case Xpdl2Package.LOOP_MULTI_INSTANCE__ATTRIBUTE_COMPLEX_MI_FLOW_CONDITION:
            setAttributeComplexMI_FlowCondition(ATTRIBUTE_COMPLEX_MI_FLOW_CONDITION_EDEFAULT);
            return;
        case Xpdl2Package.LOOP_MULTI_INSTANCE__ATTRIBUTE_MI_CONDITION:
            setAttributeMI_Condition(ATTRIBUTE_MI_CONDITION_EDEFAULT);
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
        case Xpdl2Package.LOOP_MULTI_INSTANCE__OTHER_ELEMENTS:
            return otherElements != null && !otherElements.isEmpty();
        case Xpdl2Package.LOOP_MULTI_INSTANCE__LOOP_COUNTER:
            return LOOP_COUNTER_EDEFAULT == null ? loopCounter != null : !LOOP_COUNTER_EDEFAULT.equals(loopCounter);
        case Xpdl2Package.LOOP_MULTI_INSTANCE__MI_FLOW_CONDITION:
            return isSetMIFlowCondition();
        case Xpdl2Package.LOOP_MULTI_INSTANCE__MI_ORDERING:
            return isSetMIOrdering();
        case Xpdl2Package.LOOP_MULTI_INSTANCE__MI_CONDITION:
            return mICondition != null;
        case Xpdl2Package.LOOP_MULTI_INSTANCE__COMPLEX_MI_FLOW_CONDITION:
            return complexMIFlowCondition != null;
        case Xpdl2Package.LOOP_MULTI_INSTANCE__ATTRIBUTE_COMPLEX_MI_FLOW_CONDITION:
            return ATTRIBUTE_COMPLEX_MI_FLOW_CONDITION_EDEFAULT == null ? attributeComplexMI_FlowCondition != null
                    : !ATTRIBUTE_COMPLEX_MI_FLOW_CONDITION_EDEFAULT.equals(attributeComplexMI_FlowCondition);
        case Xpdl2Package.LOOP_MULTI_INSTANCE__ATTRIBUTE_MI_CONDITION:
            return ATTRIBUTE_MI_CONDITION_EDEFAULT == null ? attributeMI_Condition != null
                    : !ATTRIBUTE_MI_CONDITION_EDEFAULT.equals(attributeMI_Condition);
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
        result.append(" (otherElements: "); //$NON-NLS-1$
        result.append(otherElements);
        result.append(", loopCounter: "); //$NON-NLS-1$
        result.append(loopCounter);
        result.append(", mIFlowCondition: "); //$NON-NLS-1$
        if (mIFlowConditionESet)
            result.append(mIFlowCondition);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", mIOrdering: "); //$NON-NLS-1$
        if (mIOrderingESet)
            result.append(mIOrdering);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", attributeComplexMI_FlowCondition: "); //$NON-NLS-1$
        result.append(attributeComplexMI_FlowCondition);
        result.append(", attributeMI_Condition: "); //$NON-NLS-1$
        result.append(attributeMI_Condition);
        result.append(')');
        return result.toString();
    }

} //LoopMultiInstanceImpl
