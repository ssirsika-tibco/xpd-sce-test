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
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.DeprecatedResultCompensation;
import com.tibco.xpd.xpdl2.DeprecatedTriggerRule;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.TriggerConditional;
import com.tibco.xpd.xpdl2.TriggerIntermediateMultiple;
import com.tibco.xpd.xpdl2.TriggerResultCancel;
import com.tibco.xpd.xpdl2.TriggerResultCompensation;
import com.tibco.xpd.xpdl2.TriggerResultLink;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.TriggerTimer;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Intermediate Event</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.IntermediateEventImpl#getTriggerResultMessage <em>Trigger Result Message</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.IntermediateEventImpl#getTriggerTimer <em>Trigger Timer</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.IntermediateEventImpl#getResultError <em>Result Error</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.IntermediateEventImpl#getTriggerResultCompensation <em>Trigger Result Compensation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.IntermediateEventImpl#getTriggerConditional <em>Trigger Conditional</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.IntermediateEventImpl#getTriggerResultLink <em>Trigger Result Link</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.IntermediateEventImpl#getTriggerIntermediateMultiple <em>Trigger Intermediate Multiple</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.IntermediateEventImpl#getImplementation <em>Implementation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.IntermediateEventImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.IntermediateEventImpl#getTrigger <em>Trigger</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.IntermediateEventImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.IntermediateEventImpl#getTriggerResultCancel <em>Trigger Result Cancel</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.IntermediateEventImpl#getTriggerResultSignal <em>Trigger Result Signal</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.IntermediateEventImpl#getDeprecatedTriggerRule <em>Deprecated Trigger Rule</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.IntermediateEventImpl#getDeprecatedResultCompensation <em>Deprecated Result Compensation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IntermediateEventImpl extends EventImpl implements IntermediateEvent {
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
     * The cached value of the '{@link #getResultError() <em>Result Error</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getResultError()
     * @generated
     * @ordered
     */
    protected ResultError resultError;

    /**
     * The cached value of the '{@link #getTriggerResultCompensation()
     * <em>Trigger Result Compensation</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getTriggerResultCompensation()
     * @generated
     * @ordered
     */
    protected TriggerResultCompensation triggerResultCompensation;

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
     * The cached value of the '{@link #getTriggerResultLink() <em>Trigger Result Link</em>}' containment reference.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #getTriggerResultLink()
     * @generated
     * @ordered
     */
    protected TriggerResultLink triggerResultLink;

    /**
     * The cached value of the '{@link #getTriggerIntermediateMultiple()
     * <em>Trigger Intermediate Multiple</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getTriggerIntermediateMultiple()
     * @generated
     * @ordered
     */
    protected TriggerIntermediateMultiple triggerIntermediateMultiple;

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
     * The default value of the '{@link #getTarget() <em>Target</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getTarget()
     * @generated
     * @ordered
     */
    protected static final String TARGET_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTarget() <em>Target</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getTarget()
     * @generated
     * @ordered
     */
    protected String target = TARGET_EDEFAULT;

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
     * The cached value of the '{@link #getAnyAttribute() <em>Any Attribute</em>}' attribute list.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getAnyAttribute()
     * @generated
     * @ordered
     */
    protected FeatureMap anyAttribute;

    /**
     * The cached value of the '{@link #getTriggerResultCancel()
     * <em>Trigger Result Cancel</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getTriggerResultCancel()
     * @generated
     * @ordered
     */
    protected TriggerResultCancel triggerResultCancel;

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
     * The cached value of the '{@link #getDeprecatedResultCompensation()
     * <em>Deprecated Result Compensation</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getDeprecatedResultCompensation()
     * @generated
     * @ordered
     */
    protected DeprecatedResultCompensation deprecatedResultCompensation;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected IntermediateEventImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.INTERMEDIATE_EVENT;
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
                    Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_MESSAGE, oldTriggerResultMessage,
                    newTriggerResultMessage);
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
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_MESSAGE,
                        null,
                        msgs);
            if (newTriggerResultMessage != null)
                msgs = ((InternalEObject) newTriggerResultMessage).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_MESSAGE,
                        null,
                        msgs);
            msgs = basicSetTriggerResultMessage(newTriggerResultMessage, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_MESSAGE, newTriggerResultMessage,
                    newTriggerResultMessage));
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
                    Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_TIMER, oldTriggerTimer, newTriggerTimer);
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
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_TIMER,
                        null,
                        msgs);
            if (newTriggerTimer != null)
                msgs = ((InternalEObject) newTriggerTimer).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_TIMER,
                        null,
                        msgs);
            msgs = basicSetTriggerTimer(newTriggerTimer, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_TIMER,
                    newTriggerTimer, newTriggerTimer));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ResultError getResultError() {
        return resultError;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetResultError(ResultError newResultError, NotificationChain msgs) {
        ResultError oldResultError = resultError;
        resultError = newResultError;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.INTERMEDIATE_EVENT__RESULT_ERROR, oldResultError, newResultError);
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
    public void setResultError(ResultError newResultError) {
        if (newResultError != resultError) {
            NotificationChain msgs = null;
            if (resultError != null)
                msgs = ((InternalEObject) resultError).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.INTERMEDIATE_EVENT__RESULT_ERROR,
                        null,
                        msgs);
            if (newResultError != null)
                msgs = ((InternalEObject) newResultError).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.INTERMEDIATE_EVENT__RESULT_ERROR,
                        null,
                        msgs);
            msgs = basicSetResultError(newResultError, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.INTERMEDIATE_EVENT__RESULT_ERROR,
                    newResultError, newResultError));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TriggerResultCompensation getTriggerResultCompensation() {
        return triggerResultCompensation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTriggerResultCompensation(TriggerResultCompensation newTriggerResultCompensation,
            NotificationChain msgs) {
        TriggerResultCompensation oldTriggerResultCompensation = triggerResultCompensation;
        triggerResultCompensation = newTriggerResultCompensation;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_COMPENSATION, oldTriggerResultCompensation,
                    newTriggerResultCompensation);
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
    public void setTriggerResultCompensation(TriggerResultCompensation newTriggerResultCompensation) {
        if (newTriggerResultCompensation != triggerResultCompensation) {
            NotificationChain msgs = null;
            if (triggerResultCompensation != null)
                msgs = ((InternalEObject) triggerResultCompensation).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_COMPENSATION,
                        null,
                        msgs);
            if (newTriggerResultCompensation != null)
                msgs = ((InternalEObject) newTriggerResultCompensation).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_COMPENSATION,
                        null,
                        msgs);
            msgs = basicSetTriggerResultCompensation(newTriggerResultCompensation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_COMPENSATION, newTriggerResultCompensation,
                    newTriggerResultCompensation));
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
                    Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_CONDITIONAL, oldTriggerConditional, newTriggerConditional);
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
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_CONDITIONAL,
                        null,
                        msgs);
            if (newTriggerConditional != null)
                msgs = ((InternalEObject) newTriggerConditional).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_CONDITIONAL,
                        null,
                        msgs);
            msgs = basicSetTriggerConditional(newTriggerConditional, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_CONDITIONAL,
                    newTriggerConditional, newTriggerConditional));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TriggerResultLink getTriggerResultLink() {
        return triggerResultLink;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTriggerResultLink(TriggerResultLink newTriggerResultLink, NotificationChain msgs) {
        TriggerResultLink oldTriggerResultLink = triggerResultLink;
        triggerResultLink = newTriggerResultLink;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_LINK, oldTriggerResultLink, newTriggerResultLink);
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
    public void setTriggerResultLink(TriggerResultLink newTriggerResultLink) {
        if (newTriggerResultLink != triggerResultLink) {
            NotificationChain msgs = null;
            if (triggerResultLink != null)
                msgs = ((InternalEObject) triggerResultLink).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_LINK,
                        null,
                        msgs);
            if (newTriggerResultLink != null)
                msgs = ((InternalEObject) newTriggerResultLink).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_LINK,
                        null,
                        msgs);
            msgs = basicSetTriggerResultLink(newTriggerResultLink, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_LINK,
                    newTriggerResultLink, newTriggerResultLink));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TriggerIntermediateMultiple getTriggerIntermediateMultiple() {
        return triggerIntermediateMultiple;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTriggerIntermediateMultiple(
            TriggerIntermediateMultiple newTriggerIntermediateMultiple, NotificationChain msgs) {
        TriggerIntermediateMultiple oldTriggerIntermediateMultiple = triggerIntermediateMultiple;
        triggerIntermediateMultiple = newTriggerIntermediateMultiple;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_INTERMEDIATE_MULTIPLE, oldTriggerIntermediateMultiple,
                    newTriggerIntermediateMultiple);
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
    public void setTriggerIntermediateMultiple(TriggerIntermediateMultiple newTriggerIntermediateMultiple) {
        if (newTriggerIntermediateMultiple != triggerIntermediateMultiple) {
            NotificationChain msgs = null;
            if (triggerIntermediateMultiple != null)
                msgs = ((InternalEObject) triggerIntermediateMultiple).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_INTERMEDIATE_MULTIPLE,
                        null,
                        msgs);
            if (newTriggerIntermediateMultiple != null)
                msgs = ((InternalEObject) newTriggerIntermediateMultiple).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_INTERMEDIATE_MULTIPLE,
                        null,
                        msgs);
            msgs = basicSetTriggerIntermediateMultiple(newTriggerIntermediateMultiple, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_INTERMEDIATE_MULTIPLE, newTriggerIntermediateMultiple,
                    newTriggerIntermediateMultiple));
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
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.INTERMEDIATE_EVENT__IMPLEMENTATION,
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
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.INTERMEDIATE_EVENT__IMPLEMENTATION,
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
    public String getTarget() {
        return target;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setTarget(String newTarget) {
        String oldTarget = target;
        target = newTarget;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.INTERMEDIATE_EVENT__TARGET, oldTarget,
                    target));
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
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER, oldTrigger,
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
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER,
                    oldTrigger, TRIGGER_EDEFAULT, oldTriggerESet));
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
    public FeatureMap getAnyAttribute() {
        if (anyAttribute == null) {
            anyAttribute = new BasicFeatureMap(this, Xpdl2Package.INTERMEDIATE_EVENT__ANY_ATTRIBUTE);
        }
        return anyAttribute;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public TriggerResultCancel getTriggerResultCancel() {
        return triggerResultCancel;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTriggerResultCancel(TriggerResultCancel newTriggerResultCancel,
            NotificationChain msgs) {
        TriggerResultCancel oldTriggerResultCancel = triggerResultCancel;
        triggerResultCancel = newTriggerResultCancel;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_CANCEL, oldTriggerResultCancel,
                    newTriggerResultCancel);
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
    public void setTriggerResultCancel(TriggerResultCancel newTriggerResultCancel) {
        if (newTriggerResultCancel != triggerResultCancel) {
            NotificationChain msgs = null;
            if (triggerResultCancel != null)
                msgs = ((InternalEObject) triggerResultCancel).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_CANCEL,
                        null,
                        msgs);
            if (newTriggerResultCancel != null)
                msgs = ((InternalEObject) newTriggerResultCancel).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_CANCEL,
                        null,
                        msgs);
            msgs = basicSetTriggerResultCancel(newTriggerResultCancel, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_CANCEL, newTriggerResultCancel,
                    newTriggerResultCancel));
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
                    Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_SIGNAL, oldTriggerResultSignal,
                    newTriggerResultSignal);
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
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_SIGNAL,
                        null,
                        msgs);
            if (newTriggerResultSignal != null)
                msgs = ((InternalEObject) newTriggerResultSignal).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_SIGNAL,
                        null,
                        msgs);
            msgs = basicSetTriggerResultSignal(newTriggerResultSignal, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_SIGNAL, newTriggerResultSignal,
                    newTriggerResultSignal));
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.INTERMEDIATE_EVENT__DEPRECATED_TRIGGER_RULE, oldDeprecatedTriggerRule,
                    newDeprecatedTriggerRule);
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
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.INTERMEDIATE_EVENT__DEPRECATED_TRIGGER_RULE,
                        null,
                        msgs);
            if (newDeprecatedTriggerRule != null)
                msgs = ((InternalEObject) newDeprecatedTriggerRule).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.INTERMEDIATE_EVENT__DEPRECATED_TRIGGER_RULE,
                        null,
                        msgs);
            msgs = basicSetDeprecatedTriggerRule(newDeprecatedTriggerRule, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.INTERMEDIATE_EVENT__DEPRECATED_TRIGGER_RULE, newDeprecatedTriggerRule,
                    newDeprecatedTriggerRule));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public DeprecatedResultCompensation getDeprecatedResultCompensation() {
        return deprecatedResultCompensation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDeprecatedResultCompensation(
            DeprecatedResultCompensation newDeprecatedResultCompensation, NotificationChain msgs) {
        DeprecatedResultCompensation oldDeprecatedResultCompensation = deprecatedResultCompensation;
        deprecatedResultCompensation = newDeprecatedResultCompensation;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.INTERMEDIATE_EVENT__DEPRECATED_RESULT_COMPENSATION, oldDeprecatedResultCompensation,
                    newDeprecatedResultCompensation);
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
    public void setDeprecatedResultCompensation(DeprecatedResultCompensation newDeprecatedResultCompensation) {
        if (newDeprecatedResultCompensation != deprecatedResultCompensation) {
            NotificationChain msgs = null;
            if (deprecatedResultCompensation != null)
                msgs = ((InternalEObject) deprecatedResultCompensation).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.INTERMEDIATE_EVENT__DEPRECATED_RESULT_COMPENSATION,
                        null,
                        msgs);
            if (newDeprecatedResultCompensation != null)
                msgs = ((InternalEObject) newDeprecatedResultCompensation).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.INTERMEDIATE_EVENT__DEPRECATED_RESULT_COMPENSATION,
                        null,
                        msgs);
            msgs = basicSetDeprecatedResultCompensation(newDeprecatedResultCompensation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.INTERMEDIATE_EVENT__DEPRECATED_RESULT_COMPENSATION, newDeprecatedResultCompensation,
                    newDeprecatedResultCompensation));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_MESSAGE:
            return basicSetTriggerResultMessage(null, msgs);
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_TIMER:
            return basicSetTriggerTimer(null, msgs);
        case Xpdl2Package.INTERMEDIATE_EVENT__RESULT_ERROR:
            return basicSetResultError(null, msgs);
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_COMPENSATION:
            return basicSetTriggerResultCompensation(null, msgs);
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_CONDITIONAL:
            return basicSetTriggerConditional(null, msgs);
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_LINK:
            return basicSetTriggerResultLink(null, msgs);
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_INTERMEDIATE_MULTIPLE:
            return basicSetTriggerIntermediateMultiple(null, msgs);
        case Xpdl2Package.INTERMEDIATE_EVENT__ANY_ATTRIBUTE:
            return ((InternalEList<?>) getAnyAttribute()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_CANCEL:
            return basicSetTriggerResultCancel(null, msgs);
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_SIGNAL:
            return basicSetTriggerResultSignal(null, msgs);
        case Xpdl2Package.INTERMEDIATE_EVENT__DEPRECATED_TRIGGER_RULE:
            return basicSetDeprecatedTriggerRule(null, msgs);
        case Xpdl2Package.INTERMEDIATE_EVENT__DEPRECATED_RESULT_COMPENSATION:
            return basicSetDeprecatedResultCompensation(null, msgs);
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
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_MESSAGE:
            return getTriggerResultMessage();
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_TIMER:
            return getTriggerTimer();
        case Xpdl2Package.INTERMEDIATE_EVENT__RESULT_ERROR:
            return getResultError();
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_COMPENSATION:
            return getTriggerResultCompensation();
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_CONDITIONAL:
            return getTriggerConditional();
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_LINK:
            return getTriggerResultLink();
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_INTERMEDIATE_MULTIPLE:
            return getTriggerIntermediateMultiple();
        case Xpdl2Package.INTERMEDIATE_EVENT__IMPLEMENTATION:
            return getImplementation();
        case Xpdl2Package.INTERMEDIATE_EVENT__TARGET:
            return getTarget();
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER:
            return getTrigger();
        case Xpdl2Package.INTERMEDIATE_EVENT__ANY_ATTRIBUTE:
            if (coreType)
                return getAnyAttribute();
            return ((FeatureMap.Internal) getAnyAttribute()).getWrapper();
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_CANCEL:
            return getTriggerResultCancel();
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_SIGNAL:
            return getTriggerResultSignal();
        case Xpdl2Package.INTERMEDIATE_EVENT__DEPRECATED_TRIGGER_RULE:
            return getDeprecatedTriggerRule();
        case Xpdl2Package.INTERMEDIATE_EVENT__DEPRECATED_RESULT_COMPENSATION:
            return getDeprecatedResultCompensation();
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
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_MESSAGE:
            setTriggerResultMessage((TriggerResultMessage) newValue);
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_TIMER:
            setTriggerTimer((TriggerTimer) newValue);
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__RESULT_ERROR:
            setResultError((ResultError) newValue);
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_COMPENSATION:
            setTriggerResultCompensation((TriggerResultCompensation) newValue);
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_CONDITIONAL:
            setTriggerConditional((TriggerConditional) newValue);
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_LINK:
            setTriggerResultLink((TriggerResultLink) newValue);
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_INTERMEDIATE_MULTIPLE:
            setTriggerIntermediateMultiple((TriggerIntermediateMultiple) newValue);
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__IMPLEMENTATION:
            setImplementation((ImplementationType) newValue);
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__TARGET:
            setTarget((String) newValue);
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER:
            setTrigger((TriggerType) newValue);
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__ANY_ATTRIBUTE:
            ((FeatureMap.Internal) getAnyAttribute()).set(newValue);
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_CANCEL:
            setTriggerResultCancel((TriggerResultCancel) newValue);
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_SIGNAL:
            setTriggerResultSignal((TriggerResultSignal) newValue);
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__DEPRECATED_TRIGGER_RULE:
            setDeprecatedTriggerRule((DeprecatedTriggerRule) newValue);
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__DEPRECATED_RESULT_COMPENSATION:
            setDeprecatedResultCompensation((DeprecatedResultCompensation) newValue);
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
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_MESSAGE:
            setTriggerResultMessage((TriggerResultMessage) null);
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_TIMER:
            setTriggerTimer((TriggerTimer) null);
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__RESULT_ERROR:
            setResultError((ResultError) null);
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_COMPENSATION:
            setTriggerResultCompensation((TriggerResultCompensation) null);
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_CONDITIONAL:
            setTriggerConditional((TriggerConditional) null);
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_LINK:
            setTriggerResultLink((TriggerResultLink) null);
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_INTERMEDIATE_MULTIPLE:
            setTriggerIntermediateMultiple((TriggerIntermediateMultiple) null);
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__IMPLEMENTATION:
            unsetImplementation();
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__TARGET:
            setTarget(TARGET_EDEFAULT);
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER:
            unsetTrigger();
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__ANY_ATTRIBUTE:
            getAnyAttribute().clear();
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_CANCEL:
            setTriggerResultCancel((TriggerResultCancel) null);
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_SIGNAL:
            setTriggerResultSignal((TriggerResultSignal) null);
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__DEPRECATED_TRIGGER_RULE:
            setDeprecatedTriggerRule((DeprecatedTriggerRule) null);
            return;
        case Xpdl2Package.INTERMEDIATE_EVENT__DEPRECATED_RESULT_COMPENSATION:
            setDeprecatedResultCompensation((DeprecatedResultCompensation) null);
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
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_MESSAGE:
            return triggerResultMessage != null;
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_TIMER:
            return triggerTimer != null;
        case Xpdl2Package.INTERMEDIATE_EVENT__RESULT_ERROR:
            return resultError != null;
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_COMPENSATION:
            return triggerResultCompensation != null;
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_CONDITIONAL:
            return triggerConditional != null;
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_LINK:
            return triggerResultLink != null;
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_INTERMEDIATE_MULTIPLE:
            return triggerIntermediateMultiple != null;
        case Xpdl2Package.INTERMEDIATE_EVENT__IMPLEMENTATION:
            return isSetImplementation();
        case Xpdl2Package.INTERMEDIATE_EVENT__TARGET:
            return TARGET_EDEFAULT == null ? target != null : !TARGET_EDEFAULT.equals(target);
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER:
            return isSetTrigger();
        case Xpdl2Package.INTERMEDIATE_EVENT__ANY_ATTRIBUTE:
            return anyAttribute != null && !anyAttribute.isEmpty();
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_CANCEL:
            return triggerResultCancel != null;
        case Xpdl2Package.INTERMEDIATE_EVENT__TRIGGER_RESULT_SIGNAL:
            return triggerResultSignal != null;
        case Xpdl2Package.INTERMEDIATE_EVENT__DEPRECATED_TRIGGER_RULE:
            return deprecatedTriggerRule != null;
        case Xpdl2Package.INTERMEDIATE_EVENT__DEPRECATED_RESULT_COMPENSATION:
            return deprecatedResultCompensation != null;
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
        result.append(", target: "); //$NON-NLS-1$
        result.append(target);
        result.append(", trigger: "); //$NON-NLS-1$
        if (triggerESet)
            result.append(trigger);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", anyAttribute: "); //$NON-NLS-1$
        result.append(anyAttribute);
        result.append(')');
        return result.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public EObject getEventTriggerTypeNode() {

        EObject obj = getTriggerIntermediateMultiple();

        if (obj == null) {
            obj = getTriggerResultLink();

            if (obj == null) {
                obj = getTriggerResultMessage();

                if (obj == null) {
                    obj = getTriggerConditional();

                    if (obj == null) {
                        obj = getTriggerTimer();

                        if (obj == null) {
                            obj = getTriggerResultCompensation();

                            if (obj == null) {
                                obj = getResultError();

                                if (obj == null) {
                                    obj = getTriggerResultSignal();

                                    if (obj == null) {
                                        obj = getTriggerResultCancel();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return obj;
    }

} // IntermediateEventImpl
