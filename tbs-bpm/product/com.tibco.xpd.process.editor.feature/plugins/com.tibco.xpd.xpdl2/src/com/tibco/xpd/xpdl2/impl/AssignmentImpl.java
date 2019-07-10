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

import com.tibco.xpd.xpdl2.AssignTimeType;
import com.tibco.xpd.xpdl2.Assignment;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Assignment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.AssignmentImpl#getOtherElements <em>Other Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.AssignmentImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.AssignmentImpl#getExpression <em>Expression</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.AssignmentImpl#getAssignTime <em>Assign Time</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AssignmentImpl extends EObjectImpl implements Assignment {
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
     * The cached value of the '{@link #getTarget() <em>Target</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTarget()
     * @generated
     * @ordered
     */
    protected Expression target;

    /**
     * The cached value of the '{@link #getExpression() <em>Expression</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExpression()
     * @generated
     * @ordered
     */
    protected Expression expression;

    /**
     * The default value of the '{@link #getAssignTime() <em>Assign Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAssignTime()
     * @generated
     * @ordered
     */
    protected static final AssignTimeType ASSIGN_TIME_EDEFAULT = AssignTimeType.START_LITERAL;

    /**
     * The cached value of the '{@link #getAssignTime() <em>Assign Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAssignTime()
     * @generated
     * @ordered
     */
    protected AssignTimeType assignTime = ASSIGN_TIME_EDEFAULT;

    /**
     * This is true if the Assign Time attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean assignTimeESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AssignmentImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.ASSIGNMENT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherElements() {
        if (otherElements == null) {
            otherElements = new BasicFeatureMap(this, Xpdl2Package.ASSIGNMENT__OTHER_ELEMENTS);
        }
        return otherElements;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Expression getTarget() {
        return target;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTarget(Expression newTarget, NotificationChain msgs) {
        Expression oldTarget = target;
        target = newTarget;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ASSIGNMENT__TARGET, oldTarget, newTarget);
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
    public void setTarget(Expression newTarget) {
        if (newTarget != target) {
            NotificationChain msgs = null;
            if (target != null)
                msgs = ((InternalEObject) target)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ASSIGNMENT__TARGET, null, msgs);
            if (newTarget != null)
                msgs = ((InternalEObject) newTarget)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ASSIGNMENT__TARGET, null, msgs);
            msgs = basicSetTarget(newTarget, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ASSIGNMENT__TARGET, newTarget,
                    newTarget));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Expression getExpression() {
        return expression;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetExpression(Expression newExpression, NotificationChain msgs) {
        Expression oldExpression = expression;
        expression = newExpression;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ASSIGNMENT__EXPRESSION, oldExpression, newExpression);
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
    public void setExpression(Expression newExpression) {
        if (newExpression != expression) {
            NotificationChain msgs = null;
            if (expression != null)
                msgs = ((InternalEObject) expression)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ASSIGNMENT__EXPRESSION, null, msgs);
            if (newExpression != null)
                msgs = ((InternalEObject) newExpression)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.ASSIGNMENT__EXPRESSION, null, msgs);
            msgs = basicSetExpression(newExpression, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ASSIGNMENT__EXPRESSION, newExpression,
                    newExpression));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AssignTimeType getAssignTime() {
        return assignTime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAssignTime(AssignTimeType newAssignTime) {
        AssignTimeType oldAssignTime = assignTime;
        assignTime = newAssignTime == null ? ASSIGN_TIME_EDEFAULT : newAssignTime;
        boolean oldAssignTimeESet = assignTimeESet;
        assignTimeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ASSIGNMENT__ASSIGN_TIME, oldAssignTime,
                    assignTime, !oldAssignTimeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetAssignTime() {
        AssignTimeType oldAssignTime = assignTime;
        boolean oldAssignTimeESet = assignTimeESet;
        assignTime = ASSIGN_TIME_EDEFAULT;
        assignTimeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.ASSIGNMENT__ASSIGN_TIME, oldAssignTime,
                    ASSIGN_TIME_EDEFAULT, oldAssignTimeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetAssignTime() {
        return assignTimeESet;
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
        case Xpdl2Package.ASSIGNMENT__OTHER_ELEMENTS:
            return ((InternalEList<?>) getOtherElements()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.ASSIGNMENT__TARGET:
            return basicSetTarget(null, msgs);
        case Xpdl2Package.ASSIGNMENT__EXPRESSION:
            return basicSetExpression(null, msgs);
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
        case Xpdl2Package.ASSIGNMENT__OTHER_ELEMENTS:
            if (coreType)
                return getOtherElements();
            return ((FeatureMap.Internal) getOtherElements()).getWrapper();
        case Xpdl2Package.ASSIGNMENT__TARGET:
            return getTarget();
        case Xpdl2Package.ASSIGNMENT__EXPRESSION:
            return getExpression();
        case Xpdl2Package.ASSIGNMENT__ASSIGN_TIME:
            return getAssignTime();
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
        case Xpdl2Package.ASSIGNMENT__OTHER_ELEMENTS:
            ((FeatureMap.Internal) getOtherElements()).set(newValue);
            return;
        case Xpdl2Package.ASSIGNMENT__TARGET:
            setTarget((Expression) newValue);
            return;
        case Xpdl2Package.ASSIGNMENT__EXPRESSION:
            setExpression((Expression) newValue);
            return;
        case Xpdl2Package.ASSIGNMENT__ASSIGN_TIME:
            setAssignTime((AssignTimeType) newValue);
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
        case Xpdl2Package.ASSIGNMENT__OTHER_ELEMENTS:
            getOtherElements().clear();
            return;
        case Xpdl2Package.ASSIGNMENT__TARGET:
            setTarget((Expression) null);
            return;
        case Xpdl2Package.ASSIGNMENT__EXPRESSION:
            setExpression((Expression) null);
            return;
        case Xpdl2Package.ASSIGNMENT__ASSIGN_TIME:
            unsetAssignTime();
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
        case Xpdl2Package.ASSIGNMENT__OTHER_ELEMENTS:
            return otherElements != null && !otherElements.isEmpty();
        case Xpdl2Package.ASSIGNMENT__TARGET:
            return target != null;
        case Xpdl2Package.ASSIGNMENT__EXPRESSION:
            return expression != null;
        case Xpdl2Package.ASSIGNMENT__ASSIGN_TIME:
            return isSetAssignTime();
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
        result.append(", assignTime: "); //$NON-NLS-1$
        if (assignTimeESet)
            result.append(assignTime);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //AssignmentImpl
