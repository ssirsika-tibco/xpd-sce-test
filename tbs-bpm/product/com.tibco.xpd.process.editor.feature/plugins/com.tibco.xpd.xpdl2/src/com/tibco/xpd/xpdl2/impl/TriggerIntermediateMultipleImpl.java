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

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;
import com.tibco.xpd.xpdl2.DeprecatedResultCompensation;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.TriggerConditional;
import com.tibco.xpd.xpdl2.TriggerIntermediateMultiple;
import com.tibco.xpd.xpdl2.TriggerResultCompensation;
import com.tibco.xpd.xpdl2.TriggerResultLink;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerTimer;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Trigger Intermediate Multiple</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerIntermediateMultipleImpl#getOtherAttributes <em>Other Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerIntermediateMultipleImpl#getTriggerResultMessage <em>Trigger Result Message</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerIntermediateMultipleImpl#getTriggerTimer <em>Trigger Timer</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerIntermediateMultipleImpl#getResultError <em>Result Error</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerIntermediateMultipleImpl#getTriggerResultCompensation <em>Trigger Result Compensation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerIntermediateMultipleImpl#getDeprecatedResultCompensation <em>Deprecated Result Compensation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerIntermediateMultipleImpl#getTriggerConditional <em>Trigger Conditional</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerIntermediateMultipleImpl#getTriggerResultLink <em>Trigger Result Link</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TriggerIntermediateMultipleImpl extends EObjectImpl implements TriggerIntermediateMultiple {
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
     * The cached value of the '{@link #getTriggerResultMessage() <em>Trigger Result Message</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTriggerResultMessage()
     * @generated
     * @ordered
     */
    protected TriggerResultMessage triggerResultMessage;

    /**
     * The cached value of the '{@link #getTriggerTimer() <em>Trigger Timer</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTriggerTimer()
     * @generated
     * @ordered
     */
    protected TriggerTimer triggerTimer;

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
     * The cached value of the '{@link #getDeprecatedResultCompensation() <em>Deprecated Result Compensation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDeprecatedResultCompensation()
     * @generated
     * @ordered
     */
    protected DeprecatedResultCompensation deprecatedResultCompensation;

    /**
     * The cached value of the '{@link #getTriggerConditional() <em>Trigger Conditional</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTriggerConditional()
     * @generated
     * @ordered
     */
    protected TriggerConditional triggerConditional;

    /**
     * The cached value of the '{@link #getTriggerResultLink() <em>Trigger Result Link</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTriggerResultLink()
     * @generated
     * @ordered
     */
    protected TriggerResultLink triggerResultLink;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TriggerIntermediateMultipleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.TRIGGER_INTERMEDIATE_MULTIPLE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherAttributes() {
        if (otherAttributes == null) {
            otherAttributes = new BasicFeatureMap(this, Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__OTHER_ATTRIBUTES);
        }
        return otherAttributes;
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
                    Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_MESSAGE, oldTriggerResultMessage,
                    newTriggerResultMessage);
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
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_MESSAGE,
                        null,
                        msgs);
            if (newTriggerResultMessage != null)
                msgs = ((InternalEObject) newTriggerResultMessage).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_MESSAGE,
                        null,
                        msgs);
            msgs = basicSetTriggerResultMessage(newTriggerResultMessage, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_MESSAGE, newTriggerResultMessage,
                    newTriggerResultMessage));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TriggerTimer getTriggerTimer() {
        return triggerTimer;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTriggerTimer(TriggerTimer newTriggerTimer, NotificationChain msgs) {
        TriggerTimer oldTriggerTimer = triggerTimer;
        triggerTimer = newTriggerTimer;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_TIMER, oldTriggerTimer, newTriggerTimer);
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
    public void setTriggerTimer(TriggerTimer newTriggerTimer) {
        if (newTriggerTimer != triggerTimer) {
            NotificationChain msgs = null;
            if (triggerTimer != null)
                msgs = ((InternalEObject) triggerTimer).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_TIMER,
                        null,
                        msgs);
            if (newTriggerTimer != null)
                msgs = ((InternalEObject) newTriggerTimer).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_TIMER,
                        null,
                        msgs);
            msgs = basicSetTriggerTimer(newTriggerTimer, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_TIMER, newTriggerTimer, newTriggerTimer));
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
                    Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__RESULT_ERROR, oldResultError, newResultError);
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
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__RESULT_ERROR,
                        null,
                        msgs);
            if (newResultError != null)
                msgs = ((InternalEObject) newResultError).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__RESULT_ERROR,
                        null,
                        msgs);
            msgs = basicSetResultError(newResultError, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__RESULT_ERROR, newResultError, newResultError));
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_COMPENSATION,
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
                        EOPPOSITE_FEATURE_BASE
                                - Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_COMPENSATION,
                        null,
                        msgs);
            if (newTriggerResultCompensation != null)
                msgs = ((InternalEObject) newTriggerResultCompensation).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_COMPENSATION,
                        null,
                        msgs);
            msgs = basicSetTriggerResultCompensation(newTriggerResultCompensation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_COMPENSATION,
                    newTriggerResultCompensation, newTriggerResultCompensation));
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
                    Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__DEPRECATED_RESULT_COMPENSATION,
                    oldDeprecatedResultCompensation, newDeprecatedResultCompensation);
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
                        EOPPOSITE_FEATURE_BASE
                                - Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__DEPRECATED_RESULT_COMPENSATION,
                        null,
                        msgs);
            if (newDeprecatedResultCompensation != null)
                msgs = ((InternalEObject) newDeprecatedResultCompensation).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__DEPRECATED_RESULT_COMPENSATION,
                        null,
                        msgs);
            msgs = basicSetDeprecatedResultCompensation(newDeprecatedResultCompensation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__DEPRECATED_RESULT_COMPENSATION,
                    newDeprecatedResultCompensation, newDeprecatedResultCompensation));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TriggerConditional getTriggerConditional() {
        return triggerConditional;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTriggerConditional(TriggerConditional newTriggerConditional,
            NotificationChain msgs) {
        TriggerConditional oldTriggerConditional = triggerConditional;
        triggerConditional = newTriggerConditional;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_CONDITIONAL, oldTriggerConditional,
                    newTriggerConditional);
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
    public void setTriggerConditional(TriggerConditional newTriggerConditional) {
        if (newTriggerConditional != triggerConditional) {
            NotificationChain msgs = null;
            if (triggerConditional != null)
                msgs = ((InternalEObject) triggerConditional).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_CONDITIONAL,
                        null,
                        msgs);
            if (newTriggerConditional != null)
                msgs = ((InternalEObject) newTriggerConditional).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_CONDITIONAL,
                        null,
                        msgs);
            msgs = basicSetTriggerConditional(newTriggerConditional, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_CONDITIONAL, newTriggerConditional,
                    newTriggerConditional));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TriggerResultLink getTriggerResultLink() {
        return triggerResultLink;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTriggerResultLink(TriggerResultLink newTriggerResultLink, NotificationChain msgs) {
        TriggerResultLink oldTriggerResultLink = triggerResultLink;
        triggerResultLink = newTriggerResultLink;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_LINK, oldTriggerResultLink,
                    newTriggerResultLink);
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
    public void setTriggerResultLink(TriggerResultLink newTriggerResultLink) {
        if (newTriggerResultLink != triggerResultLink) {
            NotificationChain msgs = null;
            if (triggerResultLink != null)
                msgs = ((InternalEObject) triggerResultLink).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_LINK,
                        null,
                        msgs);
            if (newTriggerResultLink != null)
                msgs = ((InternalEObject) newTriggerResultLink).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_LINK,
                        null,
                        msgs);
            msgs = basicSetTriggerResultLink(newTriggerResultLink, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_LINK, newTriggerResultLink,
                    newTriggerResultLink));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__OTHER_ATTRIBUTES:
            return ((InternalEList<?>) getOtherAttributes()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_MESSAGE:
            return basicSetTriggerResultMessage(null, msgs);
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_TIMER:
            return basicSetTriggerTimer(null, msgs);
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__RESULT_ERROR:
            return basicSetResultError(null, msgs);
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_COMPENSATION:
            return basicSetTriggerResultCompensation(null, msgs);
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__DEPRECATED_RESULT_COMPENSATION:
            return basicSetDeprecatedResultCompensation(null, msgs);
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_CONDITIONAL:
            return basicSetTriggerConditional(null, msgs);
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_LINK:
            return basicSetTriggerResultLink(null, msgs);
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
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__OTHER_ATTRIBUTES:
            if (coreType)
                return getOtherAttributes();
            return ((FeatureMap.Internal) getOtherAttributes()).getWrapper();
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_MESSAGE:
            return getTriggerResultMessage();
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_TIMER:
            return getTriggerTimer();
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__RESULT_ERROR:
            return getResultError();
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_COMPENSATION:
            return getTriggerResultCompensation();
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__DEPRECATED_RESULT_COMPENSATION:
            return getDeprecatedResultCompensation();
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_CONDITIONAL:
            return getTriggerConditional();
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_LINK:
            return getTriggerResultLink();
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
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__OTHER_ATTRIBUTES:
            ((FeatureMap.Internal) getOtherAttributes()).set(newValue);
            return;
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_MESSAGE:
            setTriggerResultMessage((TriggerResultMessage) newValue);
            return;
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_TIMER:
            setTriggerTimer((TriggerTimer) newValue);
            return;
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__RESULT_ERROR:
            setResultError((ResultError) newValue);
            return;
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_COMPENSATION:
            setTriggerResultCompensation((TriggerResultCompensation) newValue);
            return;
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__DEPRECATED_RESULT_COMPENSATION:
            setDeprecatedResultCompensation((DeprecatedResultCompensation) newValue);
            return;
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_CONDITIONAL:
            setTriggerConditional((TriggerConditional) newValue);
            return;
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_LINK:
            setTriggerResultLink((TriggerResultLink) newValue);
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
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__OTHER_ATTRIBUTES:
            getOtherAttributes().clear();
            return;
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_MESSAGE:
            setTriggerResultMessage((TriggerResultMessage) null);
            return;
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_TIMER:
            setTriggerTimer((TriggerTimer) null);
            return;
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__RESULT_ERROR:
            setResultError((ResultError) null);
            return;
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_COMPENSATION:
            setTriggerResultCompensation((TriggerResultCompensation) null);
            return;
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__DEPRECATED_RESULT_COMPENSATION:
            setDeprecatedResultCompensation((DeprecatedResultCompensation) null);
            return;
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_CONDITIONAL:
            setTriggerConditional((TriggerConditional) null);
            return;
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_LINK:
            setTriggerResultLink((TriggerResultLink) null);
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
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__OTHER_ATTRIBUTES:
            return otherAttributes != null && !otherAttributes.isEmpty();
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_MESSAGE:
            return triggerResultMessage != null;
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_TIMER:
            return triggerTimer != null;
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__RESULT_ERROR:
            return resultError != null;
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_COMPENSATION:
            return triggerResultCompensation != null;
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__DEPRECATED_RESULT_COMPENSATION:
            return deprecatedResultCompensation != null;
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_CONDITIONAL:
            return triggerConditional != null;
        case Xpdl2Package.TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_LINK:
            return triggerResultLink != null;
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
        result.append(" (otherAttributes: "); //$NON-NLS-1$
        result.append(otherAttributes);
        result.append(')');
        return result.toString();
    }

} //TriggerIntermediateMultipleImpl
