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

import com.tibco.xpd.xpdl2.Join;
import com.tibco.xpd.xpdl2.Split;
import com.tibco.xpd.xpdl2.TransitionRestriction;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transition Restriction</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TransitionRestrictionImpl#getJoin <em>Join</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TransitionRestrictionImpl#getSplit <em>Split</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TransitionRestrictionImpl extends EObjectImpl implements
        TransitionRestriction {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getJoin() <em>Join</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getJoin()
     * @generated
     * @ordered
     */
    protected Join join;

    /**
     * The cached value of the '{@link #getSplit() <em>Split</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSplit()
     * @generated
     * @ordered
     */
    protected Split split;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TransitionRestrictionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.TRANSITION_RESTRICTION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Join getJoin() {
        return join;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetJoin(Join newJoin, NotificationChain msgs) {
        Join oldJoin = join;
        join = newJoin;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.TRANSITION_RESTRICTION__JOIN, oldJoin,
                            newJoin);
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
    public void setJoin(Join newJoin) {
        if (newJoin != join) {
            NotificationChain msgs = null;
            if (join != null)
                msgs =
                        ((InternalEObject) join)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.TRANSITION_RESTRICTION__JOIN,
                                        null,
                                        msgs);
            if (newJoin != null)
                msgs =
                        ((InternalEObject) newJoin)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.TRANSITION_RESTRICTION__JOIN,
                                        null,
                                        msgs);
            msgs = basicSetJoin(newJoin, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRANSITION_RESTRICTION__JOIN, newJoin, newJoin));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Split getSplit() {
        return split;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSplit(Split newSplit,
            NotificationChain msgs) {
        Split oldSplit = split;
        split = newSplit;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.TRANSITION_RESTRICTION__SPLIT,
                            oldSplit, newSplit);
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
    public void setSplit(Split newSplit) {
        if (newSplit != split) {
            NotificationChain msgs = null;
            if (split != null)
                msgs =
                        ((InternalEObject) split)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.TRANSITION_RESTRICTION__SPLIT,
                                        null,
                                        msgs);
            if (newSplit != null)
                msgs =
                        ((InternalEObject) newSplit)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.TRANSITION_RESTRICTION__SPLIT,
                                        null,
                                        msgs);
            msgs = basicSetSplit(newSplit, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRANSITION_RESTRICTION__SPLIT, newSplit,
                    newSplit));
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
        case Xpdl2Package.TRANSITION_RESTRICTION__JOIN:
            return basicSetJoin(null, msgs);
        case Xpdl2Package.TRANSITION_RESTRICTION__SPLIT:
            return basicSetSplit(null, msgs);
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
        case Xpdl2Package.TRANSITION_RESTRICTION__JOIN:
            return getJoin();
        case Xpdl2Package.TRANSITION_RESTRICTION__SPLIT:
            return getSplit();
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
        case Xpdl2Package.TRANSITION_RESTRICTION__JOIN:
            setJoin((Join) newValue);
            return;
        case Xpdl2Package.TRANSITION_RESTRICTION__SPLIT:
            setSplit((Split) newValue);
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
        case Xpdl2Package.TRANSITION_RESTRICTION__JOIN:
            setJoin((Join) null);
            return;
        case Xpdl2Package.TRANSITION_RESTRICTION__SPLIT:
            setSplit((Split) null);
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
        case Xpdl2Package.TRANSITION_RESTRICTION__JOIN:
            return join != null;
        case Xpdl2Package.TRANSITION_RESTRICTION__SPLIT:
            return split != null;
        }
        return super.eIsSet(featureID);
    }

} //TransitionRestrictionImpl
