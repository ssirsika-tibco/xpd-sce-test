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

import com.tibco.xpd.xpdl2.Deadline;
import com.tibco.xpd.xpdl2.ExceptionName;
import com.tibco.xpd.xpdl2.ExecutionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Deadline</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.DeadlineImpl#getDeadlineDuration <em>Deadline Duration</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.DeadlineImpl#getExceptionName <em>Exception Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.DeadlineImpl#getExecution <em>Execution</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DeadlineImpl extends EObjectImpl implements Deadline {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getDeadlineDuration() <em>Deadline Duration</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDeadlineDuration()
     * @generated
     * @ordered
     */
    protected Expression deadlineDuration;

    /**
     * The cached value of the '{@link #getExceptionName() <em>Exception Name</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExceptionName()
     * @generated
     * @ordered
     */
    protected ExceptionName exceptionName;

    /**
     * The default value of the '{@link #getExecution() <em>Execution</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExecution()
     * @generated
     * @ordered
     */
    protected static final ExecutionType EXECUTION_EDEFAULT =
            ExecutionType.ASYNCHR_LITERAL;

    /**
     * The cached value of the '{@link #getExecution() <em>Execution</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExecution()
     * @generated
     * @ordered
     */
    protected ExecutionType execution = EXECUTION_EDEFAULT;

    /**
     * This is true if the Execution attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean executionESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DeadlineImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.DEADLINE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Expression getDeadlineDuration() {
        return deadlineDuration;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDeadlineDuration(
            Expression newDeadlineDuration, NotificationChain msgs) {
        Expression oldDeadlineDuration = deadlineDuration;
        deadlineDuration = newDeadlineDuration;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.DEADLINE__DEADLINE_DURATION,
                            oldDeadlineDuration, newDeadlineDuration);
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
    public void setDeadlineDuration(Expression newDeadlineDuration) {
        if (newDeadlineDuration != deadlineDuration) {
            NotificationChain msgs = null;
            if (deadlineDuration != null)
                msgs =
                        ((InternalEObject) deadlineDuration)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.DEADLINE__DEADLINE_DURATION,
                                        null,
                                        msgs);
            if (newDeadlineDuration != null)
                msgs =
                        ((InternalEObject) newDeadlineDuration)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.DEADLINE__DEADLINE_DURATION,
                                        null,
                                        msgs);
            msgs = basicSetDeadlineDuration(newDeadlineDuration, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.DEADLINE__DEADLINE_DURATION,
                    newDeadlineDuration, newDeadlineDuration));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExceptionName getExceptionName() {
        return exceptionName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetExceptionName(
            ExceptionName newExceptionName, NotificationChain msgs) {
        ExceptionName oldExceptionName = exceptionName;
        exceptionName = newExceptionName;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.DEADLINE__EXCEPTION_NAME,
                            oldExceptionName, newExceptionName);
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
    public void setExceptionName(ExceptionName newExceptionName) {
        if (newExceptionName != exceptionName) {
            NotificationChain msgs = null;
            if (exceptionName != null)
                msgs =
                        ((InternalEObject) exceptionName)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.DEADLINE__EXCEPTION_NAME,
                                        null,
                                        msgs);
            if (newExceptionName != null)
                msgs =
                        ((InternalEObject) newExceptionName)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.DEADLINE__EXCEPTION_NAME,
                                        null,
                                        msgs);
            msgs = basicSetExceptionName(newExceptionName, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.DEADLINE__EXCEPTION_NAME, newExceptionName,
                    newExceptionName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExecutionType getExecution() {
        return execution;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setExecution(ExecutionType newExecution) {
        ExecutionType oldExecution = execution;
        execution = newExecution == null ? EXECUTION_EDEFAULT : newExecution;
        boolean oldExecutionESet = executionESet;
        executionESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.DEADLINE__EXECUTION, oldExecution, execution,
                    !oldExecutionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetExecution() {
        ExecutionType oldExecution = execution;
        boolean oldExecutionESet = executionESet;
        execution = EXECUTION_EDEFAULT;
        executionESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.DEADLINE__EXECUTION, oldExecution,
                    EXECUTION_EDEFAULT, oldExecutionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetExecution() {
        return executionESet;
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
        case Xpdl2Package.DEADLINE__DEADLINE_DURATION:
            return basicSetDeadlineDuration(null, msgs);
        case Xpdl2Package.DEADLINE__EXCEPTION_NAME:
            return basicSetExceptionName(null, msgs);
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
        case Xpdl2Package.DEADLINE__DEADLINE_DURATION:
            return getDeadlineDuration();
        case Xpdl2Package.DEADLINE__EXCEPTION_NAME:
            return getExceptionName();
        case Xpdl2Package.DEADLINE__EXECUTION:
            return getExecution();
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
        case Xpdl2Package.DEADLINE__DEADLINE_DURATION:
            setDeadlineDuration((Expression) newValue);
            return;
        case Xpdl2Package.DEADLINE__EXCEPTION_NAME:
            setExceptionName((ExceptionName) newValue);
            return;
        case Xpdl2Package.DEADLINE__EXECUTION:
            setExecution((ExecutionType) newValue);
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
        case Xpdl2Package.DEADLINE__DEADLINE_DURATION:
            setDeadlineDuration((Expression) null);
            return;
        case Xpdl2Package.DEADLINE__EXCEPTION_NAME:
            setExceptionName((ExceptionName) null);
            return;
        case Xpdl2Package.DEADLINE__EXECUTION:
            unsetExecution();
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
        case Xpdl2Package.DEADLINE__DEADLINE_DURATION:
            return deadlineDuration != null;
        case Xpdl2Package.DEADLINE__EXCEPTION_NAME:
            return exceptionName != null;
        case Xpdl2Package.DEADLINE__EXECUTION:
            return isSetExecution();
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
        result.append(" (execution: "); //$NON-NLS-1$
        if (executionESet)
            result.append(execution);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //DeadlineImpl
