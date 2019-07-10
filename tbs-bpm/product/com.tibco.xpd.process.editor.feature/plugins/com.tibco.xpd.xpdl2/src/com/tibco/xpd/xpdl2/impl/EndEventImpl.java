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

import com.tibco.xpd.xpdl2.DeprecatedResultCompensation;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.ResultMultiple;
import com.tibco.xpd.xpdl2.ResultType;
import com.tibco.xpd.xpdl2.TriggerResultCompensation;
import com.tibco.xpd.xpdl2.TriggerResultLink;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>End Event</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.EndEventImpl#getTriggerResultMessage <em>Trigger Result Message</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.EndEventImpl#getResultError <em>Result Error</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.EndEventImpl#getTriggerResultCompensation <em>Trigger Result Compensation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.EndEventImpl#getTriggerResultSignal <em>Trigger Result Signal</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.EndEventImpl#getResultMultiple <em>Result Multiple</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.EndEventImpl#getImplementation <em>Implementation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.EndEventImpl#getResult <em>Result</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.EndEventImpl#getDeprecatedTriggerResultLink <em>Deprecated Trigger Result Link</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.EndEventImpl#getDeprecatedResultCompensation <em>Deprecated Result Compensation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EndEventImpl extends EventImpl implements EndEvent {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getTriggerResultMessage() <em>Trigger Result Message</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTriggerResultMessage()
     * @generated
     * @ordered
     */
    protected TriggerResultMessage triggerResultMessage;

    /**
     * The cached value of the '{@link #getResultError() <em>Result Error</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResultError()
     * @generated
     * @ordered
     */
    protected ResultError resultError;

    /**
     * The cached value of the '{@link #getTriggerResultCompensation() <em>Trigger Result Compensation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTriggerResultCompensation()
     * @generated
     * @ordered
     */
    protected TriggerResultCompensation triggerResultCompensation;

    /**
     * The cached value of the '{@link #getTriggerResultSignal() <em>Trigger Result Signal</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTriggerResultSignal()
     * @generated
     * @ordered
     */
    protected TriggerResultSignal triggerResultSignal;

    /**
     * The cached value of the '{@link #getResultMultiple() <em>Result Multiple</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResultMultiple()
     * @generated
     * @ordered
     */
    protected ResultMultiple resultMultiple;

    /**
     * The default value of the '{@link #getImplementation() <em>Implementation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getImplementation()
     * @generated
     * @ordered
     */
    protected static final ImplementationType IMPLEMENTATION_EDEFAULT = ImplementationType.WEB_SERVICE_LITERAL;

    /**
     * The cached value of the '{@link #getImplementation() <em>Implementation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getImplementation()
     * @generated
     * @ordered
     */
    protected ImplementationType implementation = IMPLEMENTATION_EDEFAULT;

    /**
     * This is true if the Implementation attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean implementationESet;

    /**
     * The default value of the '{@link #getResult() <em>Result</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResult()
     * @generated
     * @ordered
     */
    protected static final ResultType RESULT_EDEFAULT = ResultType.NONE_LITERAL;

    /**
     * The cached value of the '{@link #getResult() <em>Result</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResult()
     * @generated
     * @ordered
     */
    protected ResultType result = RESULT_EDEFAULT;

    /**
     * This is true if the Result attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean resultESet;

    /**
     * The cached value of the '{@link #getDeprecatedTriggerResultLink() <em>Deprecated Trigger Result Link</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDeprecatedTriggerResultLink()
     * @generated
     * @ordered
     */
    protected TriggerResultLink deprecatedTriggerResultLink;

    /**
     * The cached value of the '{@link #getDeprecatedResultCompensation() <em>Deprecated Result Compensation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDeprecatedResultCompensation()
     * @generated
     * @ordered
     */
    protected DeprecatedResultCompensation deprecatedResultCompensation;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected EndEventImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.END_EVENT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TriggerResultMessage getTriggerResultMessage() {
        return triggerResultMessage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTriggerResultMessage(TriggerResultMessage newTriggerResultMessage,
            NotificationChain msgs) {
        TriggerResultMessage oldTriggerResultMessage = triggerResultMessage;
        triggerResultMessage = newTriggerResultMessage;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.END_EVENT__TRIGGER_RESULT_MESSAGE, oldTriggerResultMessage, newTriggerResultMessage);
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
    public void setTriggerResultMessage(TriggerResultMessage newTriggerResultMessage) {
        if (newTriggerResultMessage != triggerResultMessage) {
            NotificationChain msgs = null;
            if (triggerResultMessage != null)
                msgs = ((InternalEObject) triggerResultMessage).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.END_EVENT__TRIGGER_RESULT_MESSAGE,
                        null,
                        msgs);
            if (newTriggerResultMessage != null)
                msgs = ((InternalEObject) newTriggerResultMessage).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.END_EVENT__TRIGGER_RESULT_MESSAGE,
                        null,
                        msgs);
            msgs = basicSetTriggerResultMessage(newTriggerResultMessage, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.END_EVENT__TRIGGER_RESULT_MESSAGE,
                    newTriggerResultMessage, newTriggerResultMessage));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResultError getResultError() {
        return resultError;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetResultError(ResultError newResultError, NotificationChain msgs) {
        ResultError oldResultError = resultError;
        resultError = newResultError;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.END_EVENT__RESULT_ERROR, oldResultError, newResultError);
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
    public void setResultError(ResultError newResultError) {
        if (newResultError != resultError) {
            NotificationChain msgs = null;
            if (resultError != null)
                msgs = ((InternalEObject) resultError).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.END_EVENT__RESULT_ERROR,
                        null,
                        msgs);
            if (newResultError != null)
                msgs = ((InternalEObject) newResultError)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.END_EVENT__RESULT_ERROR, null, msgs);
            msgs = basicSetResultError(newResultError, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.END_EVENT__RESULT_ERROR, newResultError,
                    newResultError));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TriggerResultCompensation getTriggerResultCompensation() {
        return triggerResultCompensation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTriggerResultCompensation(TriggerResultCompensation newTriggerResultCompensation,
            NotificationChain msgs) {
        TriggerResultCompensation oldTriggerResultCompensation = triggerResultCompensation;
        triggerResultCompensation = newTriggerResultCompensation;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET, Xpdl2Package.END_EVENT__TRIGGER_RESULT_COMPENSATION,
                            oldTriggerResultCompensation, newTriggerResultCompensation);
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
    public void setTriggerResultCompensation(TriggerResultCompensation newTriggerResultCompensation) {
        if (newTriggerResultCompensation != triggerResultCompensation) {
            NotificationChain msgs = null;
            if (triggerResultCompensation != null)
                msgs = ((InternalEObject) triggerResultCompensation).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.END_EVENT__TRIGGER_RESULT_COMPENSATION,
                        null,
                        msgs);
            if (newTriggerResultCompensation != null)
                msgs = ((InternalEObject) newTriggerResultCompensation).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.END_EVENT__TRIGGER_RESULT_COMPENSATION,
                        null,
                        msgs);
            msgs = basicSetTriggerResultCompensation(newTriggerResultCompensation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.END_EVENT__TRIGGER_RESULT_COMPENSATION,
                    newTriggerResultCompensation, newTriggerResultCompensation));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TriggerResultSignal getTriggerResultSignal() {
        return triggerResultSignal;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTriggerResultSignal(TriggerResultSignal newTriggerResultSignal,
            NotificationChain msgs) {
        TriggerResultSignal oldTriggerResultSignal = triggerResultSignal;
        triggerResultSignal = newTriggerResultSignal;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.END_EVENT__TRIGGER_RESULT_SIGNAL, oldTriggerResultSignal, newTriggerResultSignal);
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
    public void setTriggerResultSignal(TriggerResultSignal newTriggerResultSignal) {
        if (newTriggerResultSignal != triggerResultSignal) {
            NotificationChain msgs = null;
            if (triggerResultSignal != null)
                msgs = ((InternalEObject) triggerResultSignal).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.END_EVENT__TRIGGER_RESULT_SIGNAL,
                        null,
                        msgs);
            if (newTriggerResultSignal != null)
                msgs = ((InternalEObject) newTriggerResultSignal).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.END_EVENT__TRIGGER_RESULT_SIGNAL,
                        null,
                        msgs);
            msgs = basicSetTriggerResultSignal(newTriggerResultSignal, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.END_EVENT__TRIGGER_RESULT_SIGNAL,
                    newTriggerResultSignal, newTriggerResultSignal));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeprecatedResultCompensation getDeprecatedResultCompensation() {
        return deprecatedResultCompensation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDeprecatedResultCompensation(
            DeprecatedResultCompensation newDeprecatedResultCompensation, NotificationChain msgs) {
        DeprecatedResultCompensation oldDeprecatedResultCompensation = deprecatedResultCompensation;
        deprecatedResultCompensation = newDeprecatedResultCompensation;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.END_EVENT__DEPRECATED_RESULT_COMPENSATION, oldDeprecatedResultCompensation,
                    newDeprecatedResultCompensation);
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
    public void setDeprecatedResultCompensation(DeprecatedResultCompensation newDeprecatedResultCompensation) {
        if (newDeprecatedResultCompensation != deprecatedResultCompensation) {
            NotificationChain msgs = null;
            if (deprecatedResultCompensation != null)
                msgs = ((InternalEObject) deprecatedResultCompensation).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.END_EVENT__DEPRECATED_RESULT_COMPENSATION,
                        null,
                        msgs);
            if (newDeprecatedResultCompensation != null)
                msgs = ((InternalEObject) newDeprecatedResultCompensation).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.END_EVENT__DEPRECATED_RESULT_COMPENSATION,
                        null,
                        msgs);
            msgs = basicSetDeprecatedResultCompensation(newDeprecatedResultCompensation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.END_EVENT__DEPRECATED_RESULT_COMPENSATION, newDeprecatedResultCompensation,
                    newDeprecatedResultCompensation));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResultMultiple getResultMultiple() {
        return resultMultiple;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetResultMultiple(ResultMultiple newResultMultiple, NotificationChain msgs) {
        ResultMultiple oldResultMultiple = resultMultiple;
        resultMultiple = newResultMultiple;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.END_EVENT__RESULT_MULTIPLE, oldResultMultiple, newResultMultiple);
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
    public void setResultMultiple(ResultMultiple newResultMultiple) {
        if (newResultMultiple != resultMultiple) {
            NotificationChain msgs = null;
            if (resultMultiple != null)
                msgs = ((InternalEObject) resultMultiple).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.END_EVENT__RESULT_MULTIPLE,
                        null,
                        msgs);
            if (newResultMultiple != null)
                msgs = ((InternalEObject) newResultMultiple).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.END_EVENT__RESULT_MULTIPLE,
                        null,
                        msgs);
            msgs = basicSetResultMultiple(newResultMultiple, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.END_EVENT__RESULT_MULTIPLE,
                    newResultMultiple, newResultMultiple));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ImplementationType getImplementation() {
        return implementation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setImplementation(ImplementationType newImplementation) {
        ImplementationType oldImplementation = implementation;
        implementation = newImplementation == null ? IMPLEMENTATION_EDEFAULT : newImplementation;
        boolean oldImplementationESet = implementationESet;
        implementationESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.END_EVENT__IMPLEMENTATION,
                    oldImplementation, implementation, !oldImplementationESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetImplementation() {
        ImplementationType oldImplementation = implementation;
        boolean oldImplementationESet = implementationESet;
        implementation = IMPLEMENTATION_EDEFAULT;
        implementationESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.END_EVENT__IMPLEMENTATION,
                    oldImplementation, IMPLEMENTATION_EDEFAULT, oldImplementationESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetImplementation() {
        return implementationESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResultType getResult() {
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setResult(ResultType newResult) {
        ResultType oldResult = result;
        result = newResult == null ? RESULT_EDEFAULT : newResult;
        boolean oldResultESet = resultESet;
        resultESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.END_EVENT__RESULT, oldResult, result,
                    !oldResultESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetResult() {
        ResultType oldResult = result;
        boolean oldResultESet = resultESet;
        result = RESULT_EDEFAULT;
        resultESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.END_EVENT__RESULT, oldResult,
                    RESULT_EDEFAULT, oldResultESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetResult() {
        return resultESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TriggerResultLink getDeprecatedTriggerResultLink() {
        return deprecatedTriggerResultLink;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDeprecatedTriggerResultLink(TriggerResultLink newDeprecatedTriggerResultLink,
            NotificationChain msgs) {
        TriggerResultLink oldDeprecatedTriggerResultLink = deprecatedTriggerResultLink;
        deprecatedTriggerResultLink = newDeprecatedTriggerResultLink;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.END_EVENT__DEPRECATED_TRIGGER_RESULT_LINK, oldDeprecatedTriggerResultLink,
                    newDeprecatedTriggerResultLink);
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
    public void setDeprecatedTriggerResultLink(TriggerResultLink newDeprecatedTriggerResultLink) {
        if (newDeprecatedTriggerResultLink != deprecatedTriggerResultLink) {
            NotificationChain msgs = null;
            if (deprecatedTriggerResultLink != null)
                msgs = ((InternalEObject) deprecatedTriggerResultLink).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.END_EVENT__DEPRECATED_TRIGGER_RESULT_LINK,
                        null,
                        msgs);
            if (newDeprecatedTriggerResultLink != null)
                msgs = ((InternalEObject) newDeprecatedTriggerResultLink).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.END_EVENT__DEPRECATED_TRIGGER_RESULT_LINK,
                        null,
                        msgs);
            msgs = basicSetDeprecatedTriggerResultLink(newDeprecatedTriggerResultLink, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.END_EVENT__DEPRECATED_TRIGGER_RESULT_LINK, newDeprecatedTriggerResultLink,
                    newDeprecatedTriggerResultLink));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.END_EVENT__TRIGGER_RESULT_MESSAGE:
            return basicSetTriggerResultMessage(null, msgs);
        case Xpdl2Package.END_EVENT__RESULT_ERROR:
            return basicSetResultError(null, msgs);
        case Xpdl2Package.END_EVENT__TRIGGER_RESULT_COMPENSATION:
            return basicSetTriggerResultCompensation(null, msgs);
        case Xpdl2Package.END_EVENT__TRIGGER_RESULT_SIGNAL:
            return basicSetTriggerResultSignal(null, msgs);
        case Xpdl2Package.END_EVENT__RESULT_MULTIPLE:
            return basicSetResultMultiple(null, msgs);
        case Xpdl2Package.END_EVENT__DEPRECATED_TRIGGER_RESULT_LINK:
            return basicSetDeprecatedTriggerResultLink(null, msgs);
        case Xpdl2Package.END_EVENT__DEPRECATED_RESULT_COMPENSATION:
            return basicSetDeprecatedResultCompensation(null, msgs);
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
        case Xpdl2Package.END_EVENT__TRIGGER_RESULT_MESSAGE:
            return getTriggerResultMessage();
        case Xpdl2Package.END_EVENT__RESULT_ERROR:
            return getResultError();
        case Xpdl2Package.END_EVENT__TRIGGER_RESULT_COMPENSATION:
            return getTriggerResultCompensation();
        case Xpdl2Package.END_EVENT__TRIGGER_RESULT_SIGNAL:
            return getTriggerResultSignal();
        case Xpdl2Package.END_EVENT__RESULT_MULTIPLE:
            return getResultMultiple();
        case Xpdl2Package.END_EVENT__IMPLEMENTATION:
            return getImplementation();
        case Xpdl2Package.END_EVENT__RESULT:
            return getResult();
        case Xpdl2Package.END_EVENT__DEPRECATED_TRIGGER_RESULT_LINK:
            return getDeprecatedTriggerResultLink();
        case Xpdl2Package.END_EVENT__DEPRECATED_RESULT_COMPENSATION:
            return getDeprecatedResultCompensation();
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
        case Xpdl2Package.END_EVENT__TRIGGER_RESULT_MESSAGE:
            setTriggerResultMessage((TriggerResultMessage) newValue);
            return;
        case Xpdl2Package.END_EVENT__RESULT_ERROR:
            setResultError((ResultError) newValue);
            return;
        case Xpdl2Package.END_EVENT__TRIGGER_RESULT_COMPENSATION:
            setTriggerResultCompensation((TriggerResultCompensation) newValue);
            return;
        case Xpdl2Package.END_EVENT__TRIGGER_RESULT_SIGNAL:
            setTriggerResultSignal((TriggerResultSignal) newValue);
            return;
        case Xpdl2Package.END_EVENT__RESULT_MULTIPLE:
            setResultMultiple((ResultMultiple) newValue);
            return;
        case Xpdl2Package.END_EVENT__IMPLEMENTATION:
            setImplementation((ImplementationType) newValue);
            return;
        case Xpdl2Package.END_EVENT__RESULT:
            setResult((ResultType) newValue);
            return;
        case Xpdl2Package.END_EVENT__DEPRECATED_TRIGGER_RESULT_LINK:
            setDeprecatedTriggerResultLink((TriggerResultLink) newValue);
            return;
        case Xpdl2Package.END_EVENT__DEPRECATED_RESULT_COMPENSATION:
            setDeprecatedResultCompensation((DeprecatedResultCompensation) newValue);
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
        case Xpdl2Package.END_EVENT__TRIGGER_RESULT_MESSAGE:
            setTriggerResultMessage((TriggerResultMessage) null);
            return;
        case Xpdl2Package.END_EVENT__RESULT_ERROR:
            setResultError((ResultError) null);
            return;
        case Xpdl2Package.END_EVENT__TRIGGER_RESULT_COMPENSATION:
            setTriggerResultCompensation((TriggerResultCompensation) null);
            return;
        case Xpdl2Package.END_EVENT__TRIGGER_RESULT_SIGNAL:
            setTriggerResultSignal((TriggerResultSignal) null);
            return;
        case Xpdl2Package.END_EVENT__RESULT_MULTIPLE:
            setResultMultiple((ResultMultiple) null);
            return;
        case Xpdl2Package.END_EVENT__IMPLEMENTATION:
            unsetImplementation();
            return;
        case Xpdl2Package.END_EVENT__RESULT:
            unsetResult();
            return;
        case Xpdl2Package.END_EVENT__DEPRECATED_TRIGGER_RESULT_LINK:
            setDeprecatedTriggerResultLink((TriggerResultLink) null);
            return;
        case Xpdl2Package.END_EVENT__DEPRECATED_RESULT_COMPENSATION:
            setDeprecatedResultCompensation((DeprecatedResultCompensation) null);
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
        case Xpdl2Package.END_EVENT__TRIGGER_RESULT_MESSAGE:
            return triggerResultMessage != null;
        case Xpdl2Package.END_EVENT__RESULT_ERROR:
            return resultError != null;
        case Xpdl2Package.END_EVENT__TRIGGER_RESULT_COMPENSATION:
            return triggerResultCompensation != null;
        case Xpdl2Package.END_EVENT__TRIGGER_RESULT_SIGNAL:
            return triggerResultSignal != null;
        case Xpdl2Package.END_EVENT__RESULT_MULTIPLE:
            return resultMultiple != null;
        case Xpdl2Package.END_EVENT__IMPLEMENTATION:
            return isSetImplementation();
        case Xpdl2Package.END_EVENT__RESULT:
            return isSetResult();
        case Xpdl2Package.END_EVENT__DEPRECATED_TRIGGER_RESULT_LINK:
            return deprecatedTriggerResultLink != null;
        case Xpdl2Package.END_EVENT__DEPRECATED_RESULT_COMPENSATION:
            return deprecatedResultCompensation != null;
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
        result.append(" (implementation: "); //$NON-NLS-1$
        if (implementationESet)
            result.append(implementation);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", result: "); //$NON-NLS-1$
        if (resultESet)
            result.append(result);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public EObject getEventTriggerTypeNode() {
        EObject obj = getTriggerResultSignal();

        if (obj == null) {
            obj = getTriggerResultMessage();

            if (obj == null) {
                obj = getTriggerResultCompensation();

                if (obj == null) {
                    obj = getResultError();

                    if (obj == null) {
                        obj = getResultMultiple();

                        if (obj == null) {
                            obj = getTriggerResultSignal();
                        }
                    }
                }
            }
        }

        return obj;
    }

} //EndEventImpl
