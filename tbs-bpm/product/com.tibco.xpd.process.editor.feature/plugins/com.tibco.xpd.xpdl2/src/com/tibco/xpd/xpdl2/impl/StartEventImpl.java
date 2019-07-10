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

import com.tibco.xpd.xpdl2.DeprecatedTriggerRule;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.TriggerConditional;
import com.tibco.xpd.xpdl2.TriggerMultiple;
import com.tibco.xpd.xpdl2.TriggerResultLink;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.TriggerTimer;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Start Event</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.StartEventImpl#getTriggerResultMessage <em>Trigger Result Message</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.StartEventImpl#getTriggerTimer <em>Trigger Timer</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.StartEventImpl#getTriggerConditional <em>Trigger Conditional</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.StartEventImpl#getTriggerResultSignal <em>Trigger Result Signal</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.StartEventImpl#getTriggerMultiple <em>Trigger Multiple</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.StartEventImpl#getImplementation <em>Implementation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.StartEventImpl#getTrigger <em>Trigger</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.StartEventImpl#getDeprecatedTriggerRule <em>Deprecated Trigger Rule</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.StartEventImpl#getDeprecatedTriggerResultLink <em>Deprecated Trigger Result Link</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StartEventImpl extends EventImpl implements StartEvent {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getTriggerResultMessage()
     * <em>Trigger Result Message</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
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
     * The cached value of the '{@link #getTriggerConditional() <em>Trigger Conditional</em>}' containment reference.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #getTriggerConditional()
     * @generated
     * @ordered
     */
    protected TriggerConditional triggerConditional;

    /**
     * The cached value of the '{@link #getTriggerResultSignal()
     * <em>Trigger Result Signal</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getTriggerResultSignal()
     * @generated
     * @ordered
     */
    protected TriggerResultSignal triggerResultSignal;

    /**
     * The cached value of the '{@link #getTriggerMultiple() <em>Trigger Multiple</em>}' containment reference.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #getTriggerMultiple()
     * @generated
     * @ordered
     */
    protected TriggerMultiple triggerMultiple;

    /**
     * The default value of the '{@link #getImplementation() <em>Implementation</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getImplementation()
     * @generated
     * @ordered
     */
    protected static final ImplementationType IMPLEMENTATION_EDEFAULT = ImplementationType.WEB_SERVICE_LITERAL;

    /**
     * The cached value of the '{@link #getImplementation() <em>Implementation</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getImplementation()
     * @generated
     * @ordered
     */
    protected ImplementationType implementation = IMPLEMENTATION_EDEFAULT;

    /**
     * This is true if the Implementation attribute has been set. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected boolean implementationESet;

    /**
     * The default value of the '{@link #getTrigger() <em>Trigger</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getTrigger()
     * @generated
     * @ordered
     */
    protected static final TriggerType TRIGGER_EDEFAULT = TriggerType.NONE_LITERAL;

    /**
     * The cached value of the '{@link #getTrigger() <em>Trigger</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getTrigger()
     * @generated
     * @ordered
     */
    protected TriggerType trigger = TRIGGER_EDEFAULT;

    /**
     * This is true if the Trigger attribute has been set.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean triggerESet;

    /**
     * The cached value of the '{@link #getDeprecatedTriggerRule()
     * <em>Deprecated Trigger Rule</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getDeprecatedTriggerRule()
     * @generated
     * @ordered
     */
    protected DeprecatedTriggerRule deprecatedTriggerRule;

    /**
     * The cached value of the '{@link #getDeprecatedTriggerResultLink()
     * <em>Deprecated Trigger Result Link</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getDeprecatedTriggerResultLink()
     * @generated
     * @ordered
     */
    protected TriggerResultLink deprecatedTriggerResultLink;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected StartEventImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.START_EVENT;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TriggerResultMessage getTriggerResultMessage() {
        return triggerResultMessage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTriggerResultMessage(TriggerResultMessage newTriggerResultMessage,
            NotificationChain msgs) {
        TriggerResultMessage oldTriggerResultMessage = triggerResultMessage;
        triggerResultMessage = newTriggerResultMessage;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.START_EVENT__TRIGGER_RESULT_MESSAGE, oldTriggerResultMessage, newTriggerResultMessage);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setTriggerResultMessage(TriggerResultMessage newTriggerResultMessage) {
        if (newTriggerResultMessage != triggerResultMessage) {
            NotificationChain msgs = null;
            if (triggerResultMessage != null)
                msgs = ((InternalEObject) triggerResultMessage).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.START_EVENT__TRIGGER_RESULT_MESSAGE,
                        null,
                        msgs);
            if (newTriggerResultMessage != null)
                msgs = ((InternalEObject) newTriggerResultMessage).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.START_EVENT__TRIGGER_RESULT_MESSAGE,
                        null,
                        msgs);
            msgs = basicSetTriggerResultMessage(newTriggerResultMessage, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.START_EVENT__TRIGGER_RESULT_MESSAGE,
                    newTriggerResultMessage, newTriggerResultMessage));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TriggerTimer getTriggerTimer() {
        return triggerTimer;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTriggerTimer(TriggerTimer newTriggerTimer, NotificationChain msgs) {
        TriggerTimer oldTriggerTimer = triggerTimer;
        triggerTimer = newTriggerTimer;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.START_EVENT__TRIGGER_TIMER, oldTriggerTimer, newTriggerTimer);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setTriggerTimer(TriggerTimer newTriggerTimer) {
        if (newTriggerTimer != triggerTimer) {
            NotificationChain msgs = null;
            if (triggerTimer != null)
                msgs = ((InternalEObject) triggerTimer).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.START_EVENT__TRIGGER_TIMER,
                        null,
                        msgs);
            if (newTriggerTimer != null)
                msgs = ((InternalEObject) newTriggerTimer).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.START_EVENT__TRIGGER_TIMER,
                        null,
                        msgs);
            msgs = basicSetTriggerTimer(newTriggerTimer, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.START_EVENT__TRIGGER_TIMER,
                    newTriggerTimer, newTriggerTimer));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TriggerConditional getTriggerConditional() {
        return triggerConditional;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTriggerConditional(TriggerConditional newTriggerConditional,
            NotificationChain msgs) {
        TriggerConditional oldTriggerConditional = triggerConditional;
        triggerConditional = newTriggerConditional;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.START_EVENT__TRIGGER_CONDITIONAL, oldTriggerConditional, newTriggerConditional);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setTriggerConditional(TriggerConditional newTriggerConditional) {
        if (newTriggerConditional != triggerConditional) {
            NotificationChain msgs = null;
            if (triggerConditional != null)
                msgs = ((InternalEObject) triggerConditional).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.START_EVENT__TRIGGER_CONDITIONAL,
                        null,
                        msgs);
            if (newTriggerConditional != null)
                msgs = ((InternalEObject) newTriggerConditional).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.START_EVENT__TRIGGER_CONDITIONAL,
                        null,
                        msgs);
            msgs = basicSetTriggerConditional(newTriggerConditional, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.START_EVENT__TRIGGER_CONDITIONAL,
                    newTriggerConditional, newTriggerConditional));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TriggerResultSignal getTriggerResultSignal() {
        return triggerResultSignal;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTriggerResultSignal(TriggerResultSignal newTriggerResultSignal,
            NotificationChain msgs) {
        TriggerResultSignal oldTriggerResultSignal = triggerResultSignal;
        triggerResultSignal = newTriggerResultSignal;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.START_EVENT__TRIGGER_RESULT_SIGNAL, oldTriggerResultSignal, newTriggerResultSignal);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setTriggerResultSignal(TriggerResultSignal newTriggerResultSignal) {
        if (newTriggerResultSignal != triggerResultSignal) {
            NotificationChain msgs = null;
            if (triggerResultSignal != null)
                msgs = ((InternalEObject) triggerResultSignal).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.START_EVENT__TRIGGER_RESULT_SIGNAL,
                        null,
                        msgs);
            if (newTriggerResultSignal != null)
                msgs = ((InternalEObject) newTriggerResultSignal).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.START_EVENT__TRIGGER_RESULT_SIGNAL,
                        null,
                        msgs);
            msgs = basicSetTriggerResultSignal(newTriggerResultSignal, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.START_EVENT__TRIGGER_RESULT_SIGNAL,
                    newTriggerResultSignal, newTriggerResultSignal));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TriggerMultiple getTriggerMultiple() {
        return triggerMultiple;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTriggerMultiple(TriggerMultiple newTriggerMultiple, NotificationChain msgs) {
        TriggerMultiple oldTriggerMultiple = triggerMultiple;
        triggerMultiple = newTriggerMultiple;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.START_EVENT__TRIGGER_MULTIPLE, oldTriggerMultiple, newTriggerMultiple);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setTriggerMultiple(TriggerMultiple newTriggerMultiple) {
        if (newTriggerMultiple != triggerMultiple) {
            NotificationChain msgs = null;
            if (triggerMultiple != null)
                msgs = ((InternalEObject) triggerMultiple).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.START_EVENT__TRIGGER_MULTIPLE,
                        null,
                        msgs);
            if (newTriggerMultiple != null)
                msgs = ((InternalEObject) newTriggerMultiple).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.START_EVENT__TRIGGER_MULTIPLE,
                        null,
                        msgs);
            msgs = basicSetTriggerMultiple(newTriggerMultiple, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.START_EVENT__TRIGGER_MULTIPLE,
                    newTriggerMultiple, newTriggerMultiple));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ImplementationType getImplementation() {
        return implementation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setImplementation(ImplementationType newImplementation) {
        ImplementationType oldImplementation = implementation;
        implementation = newImplementation == null ? IMPLEMENTATION_EDEFAULT : newImplementation;
        boolean oldImplementationESet = implementationESet;
        implementationESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.START_EVENT__IMPLEMENTATION,
                    oldImplementation, implementation, !oldImplementationESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void unsetImplementation() {
        ImplementationType oldImplementation = implementation;
        boolean oldImplementationESet = implementationESet;
        implementation = IMPLEMENTATION_EDEFAULT;
        implementationESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.START_EVENT__IMPLEMENTATION,
                    oldImplementation, IMPLEMENTATION_EDEFAULT, oldImplementationESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetImplementation() {
        return implementationESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TriggerType getTrigger() {
        return trigger;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setTrigger(TriggerType newTrigger) {
        TriggerType oldTrigger = trigger;
        trigger = newTrigger == null ? TRIGGER_EDEFAULT : newTrigger;
        boolean oldTriggerESet = triggerESet;
        triggerESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.START_EVENT__TRIGGER, oldTrigger,
                    trigger, !oldTriggerESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void unsetTrigger() {
        TriggerType oldTrigger = trigger;
        boolean oldTriggerESet = triggerESet;
        trigger = TRIGGER_EDEFAULT;
        triggerESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.START_EVENT__TRIGGER, oldTrigger,
                    TRIGGER_EDEFAULT, oldTriggerESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetTrigger() {
        return triggerESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public DeprecatedTriggerRule getDeprecatedTriggerRule() {
        return deprecatedTriggerRule;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDeprecatedTriggerRule(DeprecatedTriggerRule newDeprecatedTriggerRule,
            NotificationChain msgs) {
        DeprecatedTriggerRule oldDeprecatedTriggerRule = deprecatedTriggerRule;
        deprecatedTriggerRule = newDeprecatedTriggerRule;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET, Xpdl2Package.START_EVENT__DEPRECATED_TRIGGER_RULE,
                            oldDeprecatedTriggerRule, newDeprecatedTriggerRule);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setDeprecatedTriggerRule(DeprecatedTriggerRule newDeprecatedTriggerRule) {
        if (newDeprecatedTriggerRule != deprecatedTriggerRule) {
            NotificationChain msgs = null;
            if (deprecatedTriggerRule != null)
                msgs = ((InternalEObject) deprecatedTriggerRule).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.START_EVENT__DEPRECATED_TRIGGER_RULE,
                        null,
                        msgs);
            if (newDeprecatedTriggerRule != null)
                msgs = ((InternalEObject) newDeprecatedTriggerRule).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.START_EVENT__DEPRECATED_TRIGGER_RULE,
                        null,
                        msgs);
            msgs = basicSetDeprecatedTriggerRule(newDeprecatedTriggerRule, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.START_EVENT__DEPRECATED_TRIGGER_RULE,
                    newDeprecatedTriggerRule, newDeprecatedTriggerRule));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TriggerResultLink getDeprecatedTriggerResultLink() {
        return deprecatedTriggerResultLink;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDeprecatedTriggerResultLink(TriggerResultLink newDeprecatedTriggerResultLink,
            NotificationChain msgs) {
        TriggerResultLink oldDeprecatedTriggerResultLink = deprecatedTriggerResultLink;
        deprecatedTriggerResultLink = newDeprecatedTriggerResultLink;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.START_EVENT__DEPRECATED_TRIGGER_RESULT_LINK, oldDeprecatedTriggerResultLink,
                    newDeprecatedTriggerResultLink);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setDeprecatedTriggerResultLink(TriggerResultLink newDeprecatedTriggerResultLink) {
        if (newDeprecatedTriggerResultLink != deprecatedTriggerResultLink) {
            NotificationChain msgs = null;
            if (deprecatedTriggerResultLink != null)
                msgs = ((InternalEObject) deprecatedTriggerResultLink).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.START_EVENT__DEPRECATED_TRIGGER_RESULT_LINK,
                        null,
                        msgs);
            if (newDeprecatedTriggerResultLink != null)
                msgs = ((InternalEObject) newDeprecatedTriggerResultLink).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.START_EVENT__DEPRECATED_TRIGGER_RESULT_LINK,
                        null,
                        msgs);
            msgs = basicSetDeprecatedTriggerResultLink(newDeprecatedTriggerResultLink, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.START_EVENT__DEPRECATED_TRIGGER_RESULT_LINK, newDeprecatedTriggerResultLink,
                    newDeprecatedTriggerResultLink));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.START_EVENT__TRIGGER_RESULT_MESSAGE:
            return basicSetTriggerResultMessage(null, msgs);
        case Xpdl2Package.START_EVENT__TRIGGER_TIMER:
            return basicSetTriggerTimer(null, msgs);
        case Xpdl2Package.START_EVENT__TRIGGER_CONDITIONAL:
            return basicSetTriggerConditional(null, msgs);
        case Xpdl2Package.START_EVENT__TRIGGER_RESULT_SIGNAL:
            return basicSetTriggerResultSignal(null, msgs);
        case Xpdl2Package.START_EVENT__TRIGGER_MULTIPLE:
            return basicSetTriggerMultiple(null, msgs);
        case Xpdl2Package.START_EVENT__DEPRECATED_TRIGGER_RULE:
            return basicSetDeprecatedTriggerRule(null, msgs);
        case Xpdl2Package.START_EVENT__DEPRECATED_TRIGGER_RESULT_LINK:
            return basicSetDeprecatedTriggerResultLink(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case Xpdl2Package.START_EVENT__TRIGGER_RESULT_MESSAGE:
            return getTriggerResultMessage();
        case Xpdl2Package.START_EVENT__TRIGGER_TIMER:
            return getTriggerTimer();
        case Xpdl2Package.START_EVENT__TRIGGER_CONDITIONAL:
            return getTriggerConditional();
        case Xpdl2Package.START_EVENT__TRIGGER_RESULT_SIGNAL:
            return getTriggerResultSignal();
        case Xpdl2Package.START_EVENT__TRIGGER_MULTIPLE:
            return getTriggerMultiple();
        case Xpdl2Package.START_EVENT__IMPLEMENTATION:
            return getImplementation();
        case Xpdl2Package.START_EVENT__TRIGGER:
            return getTrigger();
        case Xpdl2Package.START_EVENT__DEPRECATED_TRIGGER_RULE:
            return getDeprecatedTriggerRule();
        case Xpdl2Package.START_EVENT__DEPRECATED_TRIGGER_RESULT_LINK:
            return getDeprecatedTriggerResultLink();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case Xpdl2Package.START_EVENT__TRIGGER_RESULT_MESSAGE:
            setTriggerResultMessage((TriggerResultMessage) newValue);
            return;
        case Xpdl2Package.START_EVENT__TRIGGER_TIMER:
            setTriggerTimer((TriggerTimer) newValue);
            return;
        case Xpdl2Package.START_EVENT__TRIGGER_CONDITIONAL:
            setTriggerConditional((TriggerConditional) newValue);
            return;
        case Xpdl2Package.START_EVENT__TRIGGER_RESULT_SIGNAL:
            setTriggerResultSignal((TriggerResultSignal) newValue);
            return;
        case Xpdl2Package.START_EVENT__TRIGGER_MULTIPLE:
            setTriggerMultiple((TriggerMultiple) newValue);
            return;
        case Xpdl2Package.START_EVENT__IMPLEMENTATION:
            setImplementation((ImplementationType) newValue);
            return;
        case Xpdl2Package.START_EVENT__TRIGGER:
            setTrigger((TriggerType) newValue);
            return;
        case Xpdl2Package.START_EVENT__DEPRECATED_TRIGGER_RULE:
            setDeprecatedTriggerRule((DeprecatedTriggerRule) newValue);
            return;
        case Xpdl2Package.START_EVENT__DEPRECATED_TRIGGER_RESULT_LINK:
            setDeprecatedTriggerResultLink((TriggerResultLink) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case Xpdl2Package.START_EVENT__TRIGGER_RESULT_MESSAGE:
            setTriggerResultMessage((TriggerResultMessage) null);
            return;
        case Xpdl2Package.START_EVENT__TRIGGER_TIMER:
            setTriggerTimer((TriggerTimer) null);
            return;
        case Xpdl2Package.START_EVENT__TRIGGER_CONDITIONAL:
            setTriggerConditional((TriggerConditional) null);
            return;
        case Xpdl2Package.START_EVENT__TRIGGER_RESULT_SIGNAL:
            setTriggerResultSignal((TriggerResultSignal) null);
            return;
        case Xpdl2Package.START_EVENT__TRIGGER_MULTIPLE:
            setTriggerMultiple((TriggerMultiple) null);
            return;
        case Xpdl2Package.START_EVENT__IMPLEMENTATION:
            unsetImplementation();
            return;
        case Xpdl2Package.START_EVENT__TRIGGER:
            unsetTrigger();
            return;
        case Xpdl2Package.START_EVENT__DEPRECATED_TRIGGER_RULE:
            setDeprecatedTriggerRule((DeprecatedTriggerRule) null);
            return;
        case Xpdl2Package.START_EVENT__DEPRECATED_TRIGGER_RESULT_LINK:
            setDeprecatedTriggerResultLink((TriggerResultLink) null);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case Xpdl2Package.START_EVENT__TRIGGER_RESULT_MESSAGE:
            return triggerResultMessage != null;
        case Xpdl2Package.START_EVENT__TRIGGER_TIMER:
            return triggerTimer != null;
        case Xpdl2Package.START_EVENT__TRIGGER_CONDITIONAL:
            return triggerConditional != null;
        case Xpdl2Package.START_EVENT__TRIGGER_RESULT_SIGNAL:
            return triggerResultSignal != null;
        case Xpdl2Package.START_EVENT__TRIGGER_MULTIPLE:
            return triggerMultiple != null;
        case Xpdl2Package.START_EVENT__IMPLEMENTATION:
            return isSetImplementation();
        case Xpdl2Package.START_EVENT__TRIGGER:
            return isSetTrigger();
        case Xpdl2Package.START_EVENT__DEPRECATED_TRIGGER_RULE:
            return deprecatedTriggerRule != null;
        case Xpdl2Package.START_EVENT__DEPRECATED_TRIGGER_RESULT_LINK:
            return deprecatedTriggerResultLink != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
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
        result.append(", trigger: "); //$NON-NLS-1$
        if (triggerESet)
            result.append(trigger);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public EObject getEventTriggerTypeNode() {
        EObject obj = getTriggerMultiple();

        if (obj == null) {
            obj = getTriggerResultMessage();

            if (obj == null) {
                obj = getTriggerConditional();

                if (obj == null) {
                    obj = getTriggerTimer();

                    if (obj == null) {
                        obj = getTriggerResultSignal();
                    }
                }
            }
        }

        return obj;
    }

} // StartEventImpl
