/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.AdHocOrderingType;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Activity Set</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivitySetImpl#getActivities <em>Activities</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivitySetImpl#getTransitions <em>Transitions</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivitySetImpl#isAdHoc <em>Ad Hoc</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivitySetImpl#getAdHocCompletionCondition <em>Ad Hoc Completion Condition</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivitySetImpl#getAdHocOrdering <em>Ad Hoc Ordering</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivitySetImpl#getDefaultStartActivityId <em>Default Start Activity Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivitySetImpl#getObject <em>Object</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ActivitySetImpl#getProcess <em>Process</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ActivitySetImpl extends NamedElementImpl implements ActivitySet {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getActivities() <em>Activities</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getActivities()
     * @generated
     * @ordered
     */
    protected EList<Activity> activities;

    /**
     * The cached value of the '{@link #getTransitions() <em>Transitions</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getTransitions()
     * @generated
     * @ordered
     */
    protected EList<Transition> transitions;

    /**
     * The default value of the '{@link #isAdHoc() <em>Ad Hoc</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #isAdHoc()
     * @generated
     * @ordered
     */
    protected static final boolean AD_HOC_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isAdHoc() <em>Ad Hoc</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #isAdHoc()
     * @generated
     * @ordered
     */
    protected boolean adHoc = AD_HOC_EDEFAULT;

    /**
     * This is true if the Ad Hoc attribute has been set.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean adHocESet;

    /**
     * The default value of the '{@link #getAdHocCompletionCondition() <em>Ad Hoc Completion Condition</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getAdHocCompletionCondition()
     * @generated
     * @ordered
     */
    protected static final String AD_HOC_COMPLETION_CONDITION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAdHocCompletionCondition() <em>Ad Hoc Completion Condition</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getAdHocCompletionCondition()
     * @generated
     * @ordered
     */
    protected String adHocCompletionCondition =
            AD_HOC_COMPLETION_CONDITION_EDEFAULT;

    /**
     * The default value of the '{@link #getAdHocOrdering() <em>Ad Hoc Ordering</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getAdHocOrdering()
     * @generated
     * @ordered
     */
    protected static final AdHocOrderingType AD_HOC_ORDERING_EDEFAULT =
            AdHocOrderingType.PARALLEL_LITERAL;

    /**
     * The cached value of the '{@link #getAdHocOrdering() <em>Ad Hoc Ordering</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getAdHocOrdering()
     * @generated
     * @ordered
     */
    protected AdHocOrderingType adHocOrdering = AD_HOC_ORDERING_EDEFAULT;

    /**
     * This is true if the Ad Hoc Ordering attribute has been set. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected boolean adHocOrderingESet;

    /**
     * The default value of the '{@link #getDefaultStartActivityId() <em>Default Start Activity Id</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getDefaultStartActivityId()
     * @generated
     * @ordered
     */
    protected static final String DEFAULT_START_ACTIVITY_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDefaultStartActivityId() <em>Default Start Activity Id</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getDefaultStartActivityId()
     * @generated
     * @ordered
     */
    protected String defaultStartActivityId =
            DEFAULT_START_ACTIVITY_ID_EDEFAULT;

    /**
     * The cached value of the '{@link #getObject() <em>Object</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getObject()
     * @generated
     * @ordered
     */
    protected com.tibco.xpd.xpdl2.Object object;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected ActivitySetImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.ACTIVITY_SET;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<Activity> getActivities() {
        if (activities == null) {
            activities =
                    new EObjectContainmentWithInverseEList<Activity>(
                            Activity.class, this,
                            Xpdl2Package.ACTIVITY_SET__ACTIVITIES,
                            Xpdl2Package.ACTIVITY__FLOW_CONTAINER);
        }
        return activities;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<Transition> getTransitions() {
        if (transitions == null) {
            transitions =
                    new EObjectContainmentWithInverseEList<Transition>(
                            Transition.class, this,
                            Xpdl2Package.ACTIVITY_SET__TRANSITIONS,
                            Xpdl2Package.TRANSITION__FLOW_CONTAINER);
        }
        return transitions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public boolean isAdHoc() {
        return adHoc;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setAdHoc(boolean newAdHoc) {
        boolean oldAdHoc = adHoc;
        adHoc = newAdHoc;
        boolean oldAdHocESet = adHocESet;
        adHocESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ACTIVITY_SET__AD_HOC, oldAdHoc, adHoc,
                    !oldAdHocESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void unsetAdHoc() {
        boolean oldAdHoc = adHoc;
        boolean oldAdHocESet = adHocESet;
        adHoc = AD_HOC_EDEFAULT;
        adHocESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.ACTIVITY_SET__AD_HOC, oldAdHoc,
                    AD_HOC_EDEFAULT, oldAdHocESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetAdHoc() {
        return adHocESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getAdHocCompletionCondition() {
        return adHocCompletionCondition;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setAdHocCompletionCondition(String newAdHocCompletionCondition) {
        String oldAdHocCompletionCondition = adHocCompletionCondition;
        adHocCompletionCondition = newAdHocCompletionCondition;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ACTIVITY_SET__AD_HOC_COMPLETION_CONDITION,
                    oldAdHocCompletionCondition, adHocCompletionCondition));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public AdHocOrderingType getAdHocOrdering() {
        return adHocOrdering;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setAdHocOrdering(AdHocOrderingType newAdHocOrdering) {
        AdHocOrderingType oldAdHocOrdering = adHocOrdering;
        adHocOrdering =
                newAdHocOrdering == null ? AD_HOC_ORDERING_EDEFAULT
                        : newAdHocOrdering;
        boolean oldAdHocOrderingESet = adHocOrderingESet;
        adHocOrderingESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ACTIVITY_SET__AD_HOC_ORDERING,
                    oldAdHocOrdering, adHocOrdering, !oldAdHocOrderingESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void unsetAdHocOrdering() {
        AdHocOrderingType oldAdHocOrdering = adHocOrdering;
        boolean oldAdHocOrderingESet = adHocOrderingESet;
        adHocOrdering = AD_HOC_ORDERING_EDEFAULT;
        adHocOrderingESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.ACTIVITY_SET__AD_HOC_ORDERING,
                    oldAdHocOrdering, AD_HOC_ORDERING_EDEFAULT,
                    oldAdHocOrderingESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetAdHocOrdering() {
        return adHocOrderingESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getDefaultStartActivityId() {
        return defaultStartActivityId;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setDefaultStartActivityId(String newDefaultStartActivityId) {
        String oldDefaultStartActivityId = defaultStartActivityId;
        defaultStartActivityId = newDefaultStartActivityId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ACTIVITY_SET__DEFAULT_START_ACTIVITY_ID,
                    oldDefaultStartActivityId, defaultStartActivityId));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public com.tibco.xpd.xpdl2.Object getObject() {
        return object;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetObject(
            com.tibco.xpd.xpdl2.Object newObject, NotificationChain msgs) {
        com.tibco.xpd.xpdl2.Object oldObject = object;
        object = newObject;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.ACTIVITY_SET__OBJECT, oldObject,
                            newObject);
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
    public void setObject(com.tibco.xpd.xpdl2.Object newObject) {
        if (newObject != object) {
            NotificationChain msgs = null;
            if (object != null)
                msgs =
                        ((InternalEObject) object).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.ACTIVITY_SET__OBJECT,
                                null,
                                msgs);
            if (newObject != null)
                msgs =
                        ((InternalEObject) newObject).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.ACTIVITY_SET__OBJECT,
                                null,
                                msgs);
            msgs = basicSetObject(newObject, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ACTIVITY_SET__OBJECT, newObject, newObject));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public com.tibco.xpd.xpdl2.Process getProcess() {
        if (eContainerFeatureID() != Xpdl2Package.ACTIVITY_SET__PROCESS)
            return null;
        return (com.tibco.xpd.xpdl2.Process) eInternalContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetProcess(
            com.tibco.xpd.xpdl2.Process newProcess, NotificationChain msgs) {
        msgs =
                eBasicSetContainer((InternalEObject) newProcess,
                        Xpdl2Package.ACTIVITY_SET__PROCESS,
                        msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setProcess(com.tibco.xpd.xpdl2.Process newProcess) {
        if (newProcess != eInternalContainer()
                || (eContainerFeatureID() != Xpdl2Package.ACTIVITY_SET__PROCESS && newProcess != null)) {
            if (EcoreUtil.isAncestor(this, newProcess))
                throw new IllegalArgumentException(
                        "Recursive containment not allowed for " + toString()); //$NON-NLS-1$
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newProcess != null)
                msgs =
                        ((InternalEObject) newProcess).eInverseAdd(this,
                                Xpdl2Package.PROCESS__ACTIVITY_SETS,
                                com.tibco.xpd.xpdl2.Process.class,
                                msgs);
            msgs = basicSetProcess(newProcess, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ACTIVITY_SET__PROCESS, newProcess, newProcess));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public Activity getActivity(String id) {
        return (Activity) EMFSearchUtil.findInList(getActivities(),
                Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(),
                id);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public Transition getTransition(String id) {
        return (Transition) EMFSearchUtil.findInList(getTransitions(),
                Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(),
                id);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public EList findStartActivities() {
        return Xpdl2InternalUtil.findStartActivity(this);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.ACTIVITY_SET__ACTIVITIES:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getActivities())
                    .basicAdd(otherEnd, msgs);
        case Xpdl2Package.ACTIVITY_SET__TRANSITIONS:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getTransitions())
                    .basicAdd(otherEnd, msgs);
        case Xpdl2Package.ACTIVITY_SET__PROCESS:
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            return basicSetProcess((com.tibco.xpd.xpdl2.Process) otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
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
        case Xpdl2Package.ACTIVITY_SET__ACTIVITIES:
            return ((InternalEList<?>) getActivities()).basicRemove(otherEnd,
                    msgs);
        case Xpdl2Package.ACTIVITY_SET__TRANSITIONS:
            return ((InternalEList<?>) getTransitions()).basicRemove(otherEnd,
                    msgs);
        case Xpdl2Package.ACTIVITY_SET__OBJECT:
            return basicSetObject(null, msgs);
        case Xpdl2Package.ACTIVITY_SET__PROCESS:
            return basicSetProcess(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eBasicRemoveFromContainerFeature(
            NotificationChain msgs) {
        switch (eContainerFeatureID()) {
        case Xpdl2Package.ACTIVITY_SET__PROCESS:
            return eInternalContainer().eInverseRemove(this,
                    Xpdl2Package.PROCESS__ACTIVITY_SETS,
                    com.tibco.xpd.xpdl2.Process.class,
                    msgs);
        }
        return super.eBasicRemoveFromContainerFeature(msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case Xpdl2Package.ACTIVITY_SET__ACTIVITIES:
            return getActivities();
        case Xpdl2Package.ACTIVITY_SET__TRANSITIONS:
            return getTransitions();
        case Xpdl2Package.ACTIVITY_SET__AD_HOC:
            return isAdHoc();
        case Xpdl2Package.ACTIVITY_SET__AD_HOC_COMPLETION_CONDITION:
            return getAdHocCompletionCondition();
        case Xpdl2Package.ACTIVITY_SET__AD_HOC_ORDERING:
            return getAdHocOrdering();
        case Xpdl2Package.ACTIVITY_SET__DEFAULT_START_ACTIVITY_ID:
            return getDefaultStartActivityId();
        case Xpdl2Package.ACTIVITY_SET__OBJECT:
            return getObject();
        case Xpdl2Package.ACTIVITY_SET__PROCESS:
            return getProcess();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case Xpdl2Package.ACTIVITY_SET__ACTIVITIES:
            getActivities().clear();
            getActivities().addAll((Collection<? extends Activity>) newValue);
            return;
        case Xpdl2Package.ACTIVITY_SET__TRANSITIONS:
            getTransitions().clear();
            getTransitions()
                    .addAll((Collection<? extends Transition>) newValue);
            return;
        case Xpdl2Package.ACTIVITY_SET__AD_HOC:
            setAdHoc((Boolean) newValue);
            return;
        case Xpdl2Package.ACTIVITY_SET__AD_HOC_COMPLETION_CONDITION:
            setAdHocCompletionCondition((String) newValue);
            return;
        case Xpdl2Package.ACTIVITY_SET__AD_HOC_ORDERING:
            setAdHocOrdering((AdHocOrderingType) newValue);
            return;
        case Xpdl2Package.ACTIVITY_SET__DEFAULT_START_ACTIVITY_ID:
            setDefaultStartActivityId((String) newValue);
            return;
        case Xpdl2Package.ACTIVITY_SET__OBJECT:
            setObject((com.tibco.xpd.xpdl2.Object) newValue);
            return;
        case Xpdl2Package.ACTIVITY_SET__PROCESS:
            setProcess((com.tibco.xpd.xpdl2.Process) newValue);
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
        case Xpdl2Package.ACTIVITY_SET__ACTIVITIES:
            getActivities().clear();
            return;
        case Xpdl2Package.ACTIVITY_SET__TRANSITIONS:
            getTransitions().clear();
            return;
        case Xpdl2Package.ACTIVITY_SET__AD_HOC:
            unsetAdHoc();
            return;
        case Xpdl2Package.ACTIVITY_SET__AD_HOC_COMPLETION_CONDITION:
            setAdHocCompletionCondition(AD_HOC_COMPLETION_CONDITION_EDEFAULT);
            return;
        case Xpdl2Package.ACTIVITY_SET__AD_HOC_ORDERING:
            unsetAdHocOrdering();
            return;
        case Xpdl2Package.ACTIVITY_SET__DEFAULT_START_ACTIVITY_ID:
            setDefaultStartActivityId(DEFAULT_START_ACTIVITY_ID_EDEFAULT);
            return;
        case Xpdl2Package.ACTIVITY_SET__OBJECT:
            setObject((com.tibco.xpd.xpdl2.Object) null);
            return;
        case Xpdl2Package.ACTIVITY_SET__PROCESS:
            setProcess((com.tibco.xpd.xpdl2.Process) null);
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
        case Xpdl2Package.ACTIVITY_SET__ACTIVITIES:
            return activities != null && !activities.isEmpty();
        case Xpdl2Package.ACTIVITY_SET__TRANSITIONS:
            return transitions != null && !transitions.isEmpty();
        case Xpdl2Package.ACTIVITY_SET__AD_HOC:
            return isSetAdHoc();
        case Xpdl2Package.ACTIVITY_SET__AD_HOC_COMPLETION_CONDITION:
            return AD_HOC_COMPLETION_CONDITION_EDEFAULT == null ? adHocCompletionCondition != null
                    : !AD_HOC_COMPLETION_CONDITION_EDEFAULT
                            .equals(adHocCompletionCondition);
        case Xpdl2Package.ACTIVITY_SET__AD_HOC_ORDERING:
            return isSetAdHocOrdering();
        case Xpdl2Package.ACTIVITY_SET__DEFAULT_START_ACTIVITY_ID:
            return DEFAULT_START_ACTIVITY_ID_EDEFAULT == null ? defaultStartActivityId != null
                    : !DEFAULT_START_ACTIVITY_ID_EDEFAULT
                            .equals(defaultStartActivityId);
        case Xpdl2Package.ACTIVITY_SET__OBJECT:
            return object != null;
        case Xpdl2Package.ACTIVITY_SET__PROCESS:
            return getProcess() != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == FlowContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.ACTIVITY_SET__ACTIVITIES:
                return Xpdl2Package.FLOW_CONTAINER__ACTIVITIES;
            case Xpdl2Package.ACTIVITY_SET__TRANSITIONS:
                return Xpdl2Package.FLOW_CONTAINER__TRANSITIONS;
            case Xpdl2Package.ACTIVITY_SET__AD_HOC:
                return Xpdl2Package.FLOW_CONTAINER__AD_HOC;
            case Xpdl2Package.ACTIVITY_SET__AD_HOC_COMPLETION_CONDITION:
                return Xpdl2Package.FLOW_CONTAINER__AD_HOC_COMPLETION_CONDITION;
            case Xpdl2Package.ACTIVITY_SET__AD_HOC_ORDERING:
                return Xpdl2Package.FLOW_CONTAINER__AD_HOC_ORDERING;
            case Xpdl2Package.ACTIVITY_SET__DEFAULT_START_ACTIVITY_ID:
                return Xpdl2Package.FLOW_CONTAINER__DEFAULT_START_ACTIVITY_ID;
            default:
                return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
        if (baseClass == FlowContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.FLOW_CONTAINER__ACTIVITIES:
                return Xpdl2Package.ACTIVITY_SET__ACTIVITIES;
            case Xpdl2Package.FLOW_CONTAINER__TRANSITIONS:
                return Xpdl2Package.ACTIVITY_SET__TRANSITIONS;
            case Xpdl2Package.FLOW_CONTAINER__AD_HOC:
                return Xpdl2Package.ACTIVITY_SET__AD_HOC;
            case Xpdl2Package.FLOW_CONTAINER__AD_HOC_COMPLETION_CONDITION:
                return Xpdl2Package.ACTIVITY_SET__AD_HOC_COMPLETION_CONDITION;
            case Xpdl2Package.FLOW_CONTAINER__AD_HOC_ORDERING:
                return Xpdl2Package.ACTIVITY_SET__AD_HOC_ORDERING;
            case Xpdl2Package.FLOW_CONTAINER__DEFAULT_START_ACTIVITY_ID:
                return Xpdl2Package.ACTIVITY_SET__DEFAULT_START_ACTIVITY_ID;
            default:
                return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (adHoc: "); //$NON-NLS-1$
        if (adHocESet)
            result.append(adHoc);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", adHocCompletionCondition: "); //$NON-NLS-1$
        result.append(adHocCompletionCondition);
        result.append(", adHocOrdering: "); //$NON-NLS-1$
        if (adHocOrderingESet)
            result.append(adHocOrdering);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", defaultStartActivityId: "); //$NON-NLS-1$
        result.append(defaultStartActivityId);
        result.append(')');
        return result.toString();
    }

} // ActivitySetImpl
