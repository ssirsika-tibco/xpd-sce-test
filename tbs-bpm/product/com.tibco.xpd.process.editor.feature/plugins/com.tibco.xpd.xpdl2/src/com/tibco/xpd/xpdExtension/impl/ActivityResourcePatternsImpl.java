/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.AllocationStrategy;
import com.tibco.xpd.xpdExtension.PilingInfo;
import com.tibco.xpd.xpdExtension.WorkItemPriority;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Activity Resource Patterns</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ActivityResourcePatternsImpl#getAllocationStrategy <em>Allocation Strategy</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ActivityResourcePatternsImpl#getPiling <em>Piling</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ActivityResourcePatternsImpl#getWorkItemPriority <em>Work Item Priority</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ActivityResourcePatternsImpl extends EObjectImpl
        implements ActivityResourcePatterns {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getAllocationStrategy() <em>Allocation Strategy</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAllocationStrategy()
     * @generated
     * @ordered
     */
    protected AllocationStrategy allocationStrategy;

    /**
     * The cached value of the '{@link #getPiling() <em>Piling</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPiling()
     * @generated
     * @ordered
     */
    protected PilingInfo piling;

    /**
     * The cached value of the '{@link #getWorkItemPriority() <em>Work Item Priority</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkItemPriority()
     * @generated
     * @ordered
     */
    protected WorkItemPriority workItemPriority;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ActivityResourcePatternsImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.ACTIVITY_RESOURCE_PATTERNS;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AllocationStrategy getAllocationStrategy() {
        return allocationStrategy;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAllocationStrategy(
            AllocationStrategy newAllocationStrategy, NotificationChain msgs) {
        AllocationStrategy oldAllocationStrategy = allocationStrategy;
        allocationStrategy = newAllocationStrategy;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this,
                    Notification.SET,
                    XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS__ALLOCATION_STRATEGY,
                    oldAllocationStrategy, newAllocationStrategy);
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
    public void setAllocationStrategy(
            AllocationStrategy newAllocationStrategy) {
        if (newAllocationStrategy != allocationStrategy) {
            NotificationChain msgs = null;
            if (allocationStrategy != null)
                msgs = ((InternalEObject) allocationStrategy).eInverseRemove(
                        this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS__ALLOCATION_STRATEGY,
                        null,
                        msgs);
            if (newAllocationStrategy != null)
                msgs = ((InternalEObject) newAllocationStrategy).eInverseAdd(
                        this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS__ALLOCATION_STRATEGY,
                        null,
                        msgs);
            msgs = basicSetAllocationStrategy(newAllocationStrategy, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS__ALLOCATION_STRATEGY,
                    newAllocationStrategy, newAllocationStrategy));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PilingInfo getPiling() {
        return piling;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPiling(PilingInfo newPiling,
            NotificationChain msgs) {
        PilingInfo oldPiling = piling;
        piling = newPiling;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this,
                    Notification.SET,
                    XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS__PILING,
                    oldPiling, newPiling);
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
    public void setPiling(PilingInfo newPiling) {
        if (newPiling != piling) {
            NotificationChain msgs = null;
            if (piling != null)
                msgs = ((InternalEObject) piling).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS__PILING,
                        null,
                        msgs);
            if (newPiling != null)
                msgs = ((InternalEObject) newPiling).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS__PILING,
                        null,
                        msgs);
            msgs = basicSetPiling(newPiling, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS__PILING,
                    newPiling, newPiling));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkItemPriority getWorkItemPriority() {
        return workItemPriority;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWorkItemPriority(
            WorkItemPriority newWorkItemPriority, NotificationChain msgs) {
        WorkItemPriority oldWorkItemPriority = workItemPriority;
        workItemPriority = newWorkItemPriority;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this,
                    Notification.SET,
                    XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS__WORK_ITEM_PRIORITY,
                    oldWorkItemPriority, newWorkItemPriority);
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
    public void setWorkItemPriority(WorkItemPriority newWorkItemPriority) {
        if (newWorkItemPriority != workItemPriority) {
            NotificationChain msgs = null;
            if (workItemPriority != null)
                msgs = ((InternalEObject) workItemPriority).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS__WORK_ITEM_PRIORITY,
                        null,
                        msgs);
            if (newWorkItemPriority != null)
                msgs = ((InternalEObject) newWorkItemPriority).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS__WORK_ITEM_PRIORITY,
                        null,
                        msgs);
            msgs = basicSetWorkItemPriority(newWorkItemPriority, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS__WORK_ITEM_PRIORITY,
                    newWorkItemPriority, newWorkItemPriority));
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
        case XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS__ALLOCATION_STRATEGY:
            return basicSetAllocationStrategy(null, msgs);
        case XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS__PILING:
            return basicSetPiling(null, msgs);
        case XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS__WORK_ITEM_PRIORITY:
            return basicSetWorkItemPriority(null, msgs);
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
        case XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS__ALLOCATION_STRATEGY:
            return getAllocationStrategy();
        case XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS__PILING:
            return getPiling();
        case XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS__WORK_ITEM_PRIORITY:
            return getWorkItemPriority();
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
        case XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS__ALLOCATION_STRATEGY:
            setAllocationStrategy((AllocationStrategy) newValue);
            return;
        case XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS__PILING:
            setPiling((PilingInfo) newValue);
            return;
        case XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS__WORK_ITEM_PRIORITY:
            setWorkItemPriority((WorkItemPriority) newValue);
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
        case XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS__ALLOCATION_STRATEGY:
            setAllocationStrategy((AllocationStrategy) null);
            return;
        case XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS__PILING:
            setPiling((PilingInfo) null);
            return;
        case XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS__WORK_ITEM_PRIORITY:
            setWorkItemPriority((WorkItemPriority) null);
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
        case XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS__ALLOCATION_STRATEGY:
            return allocationStrategy != null;
        case XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS__PILING:
            return piling != null;
        case XpdExtensionPackage.ACTIVITY_RESOURCE_PATTERNS__WORK_ITEM_PRIORITY:
            return workItemPriority != null;
        }
        return super.eIsSet(featureID);
    }

} //ActivityResourcePatternsImpl
