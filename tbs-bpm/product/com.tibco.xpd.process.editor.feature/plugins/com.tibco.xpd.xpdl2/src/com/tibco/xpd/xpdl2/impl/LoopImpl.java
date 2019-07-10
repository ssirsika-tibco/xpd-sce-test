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

import com.tibco.xpd.xpdl2.Loop;
import com.tibco.xpd.xpdl2.LoopMultiInstance;
import com.tibco.xpd.xpdl2.LoopStandard;
import com.tibco.xpd.xpdl2.LoopType;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Loop</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.LoopImpl#getLoopStandard <em>Loop Standard</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.LoopImpl#getLoopMultiInstance <em>Loop Multi Instance</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.LoopImpl#getLoopType <em>Loop Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.LoopImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LoopImpl extends EObjectImpl implements Loop {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getLoopStandard() <em>Loop Standard</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLoopStandard()
     * @generated
     * @ordered
     */
    protected LoopStandard loopStandard;

    /**
     * The cached value of the '{@link #getLoopMultiInstance() <em>Loop Multi Instance</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLoopMultiInstance()
     * @generated
     * @ordered
     */
    protected LoopMultiInstance loopMultiInstance;

    /**
     * The default value of the '{@link #getLoopType() <em>Loop Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLoopType()
     * @generated
     * @ordered
     */
    protected static final LoopType LOOP_TYPE_EDEFAULT = LoopType.STANDARD_LITERAL;

    /**
     * The cached value of the '{@link #getLoopType() <em>Loop Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLoopType()
     * @generated
     * @ordered
     */
    protected LoopType loopType = LOOP_TYPE_EDEFAULT;

    /**
     * This is true if the Loop Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean loopTypeESet;

    /**
     * The cached value of the '{@link #getAnyAttribute() <em>Any Attribute</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAnyAttribute()
     * @generated
     * @ordered
     */
    protected FeatureMap anyAttribute;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected LoopImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.LOOP;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public LoopStandard getLoopStandard() {
        return loopStandard;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetLoopStandard(LoopStandard newLoopStandard, NotificationChain msgs) {
        LoopStandard oldLoopStandard = loopStandard;
        loopStandard = newLoopStandard;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.LOOP__LOOP_STANDARD, oldLoopStandard, newLoopStandard);
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
    public void setLoopStandard(LoopStandard newLoopStandard) {
        if (newLoopStandard != loopStandard) {
            NotificationChain msgs = null;
            if (loopStandard != null)
                msgs = ((InternalEObject) loopStandard)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.LOOP__LOOP_STANDARD, null, msgs);
            if (newLoopStandard != null)
                msgs = ((InternalEObject) newLoopStandard)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.LOOP__LOOP_STANDARD, null, msgs);
            msgs = basicSetLoopStandard(newLoopStandard, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.LOOP__LOOP_STANDARD, newLoopStandard,
                    newLoopStandard));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public LoopMultiInstance getLoopMultiInstance() {
        return loopMultiInstance;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetLoopMultiInstance(LoopMultiInstance newLoopMultiInstance, NotificationChain msgs) {
        LoopMultiInstance oldLoopMultiInstance = loopMultiInstance;
        loopMultiInstance = newLoopMultiInstance;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.LOOP__LOOP_MULTI_INSTANCE, oldLoopMultiInstance, newLoopMultiInstance);
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
    public void setLoopMultiInstance(LoopMultiInstance newLoopMultiInstance) {
        if (newLoopMultiInstance != loopMultiInstance) {
            NotificationChain msgs = null;
            if (loopMultiInstance != null)
                msgs = ((InternalEObject) loopMultiInstance).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.LOOP__LOOP_MULTI_INSTANCE,
                        null,
                        msgs);
            if (newLoopMultiInstance != null)
                msgs = ((InternalEObject) newLoopMultiInstance)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.LOOP__LOOP_MULTI_INSTANCE, null, msgs);
            msgs = basicSetLoopMultiInstance(newLoopMultiInstance, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.LOOP__LOOP_MULTI_INSTANCE,
                    newLoopMultiInstance, newLoopMultiInstance));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public LoopType getLoopType() {
        return loopType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLoopType(LoopType newLoopType) {
        LoopType oldLoopType = loopType;
        loopType = newLoopType == null ? LOOP_TYPE_EDEFAULT : newLoopType;
        boolean oldLoopTypeESet = loopTypeESet;
        loopTypeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.LOOP__LOOP_TYPE, oldLoopType, loopType,
                    !oldLoopTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetLoopType() {
        LoopType oldLoopType = loopType;
        boolean oldLoopTypeESet = loopTypeESet;
        loopType = LOOP_TYPE_EDEFAULT;
        loopTypeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.LOOP__LOOP_TYPE, oldLoopType,
                    LOOP_TYPE_EDEFAULT, oldLoopTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetLoopType() {
        return loopTypeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getAnyAttribute() {
        if (anyAttribute == null) {
            anyAttribute = new BasicFeatureMap(this, Xpdl2Package.LOOP__ANY_ATTRIBUTE);
        }
        return anyAttribute;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.LOOP__LOOP_STANDARD:
            return basicSetLoopStandard(null, msgs);
        case Xpdl2Package.LOOP__LOOP_MULTI_INSTANCE:
            return basicSetLoopMultiInstance(null, msgs);
        case Xpdl2Package.LOOP__ANY_ATTRIBUTE:
            return ((InternalEList<?>) getAnyAttribute()).basicRemove(otherEnd, msgs);
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
        case Xpdl2Package.LOOP__LOOP_STANDARD:
            return getLoopStandard();
        case Xpdl2Package.LOOP__LOOP_MULTI_INSTANCE:
            return getLoopMultiInstance();
        case Xpdl2Package.LOOP__LOOP_TYPE:
            return getLoopType();
        case Xpdl2Package.LOOP__ANY_ATTRIBUTE:
            if (coreType)
                return getAnyAttribute();
            return ((FeatureMap.Internal) getAnyAttribute()).getWrapper();
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
        case Xpdl2Package.LOOP__LOOP_STANDARD:
            setLoopStandard((LoopStandard) newValue);
            return;
        case Xpdl2Package.LOOP__LOOP_MULTI_INSTANCE:
            setLoopMultiInstance((LoopMultiInstance) newValue);
            return;
        case Xpdl2Package.LOOP__LOOP_TYPE:
            setLoopType((LoopType) newValue);
            return;
        case Xpdl2Package.LOOP__ANY_ATTRIBUTE:
            ((FeatureMap.Internal) getAnyAttribute()).set(newValue);
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
        case Xpdl2Package.LOOP__LOOP_STANDARD:
            setLoopStandard((LoopStandard) null);
            return;
        case Xpdl2Package.LOOP__LOOP_MULTI_INSTANCE:
            setLoopMultiInstance((LoopMultiInstance) null);
            return;
        case Xpdl2Package.LOOP__LOOP_TYPE:
            unsetLoopType();
            return;
        case Xpdl2Package.LOOP__ANY_ATTRIBUTE:
            getAnyAttribute().clear();
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
        case Xpdl2Package.LOOP__LOOP_STANDARD:
            return loopStandard != null;
        case Xpdl2Package.LOOP__LOOP_MULTI_INSTANCE:
            return loopMultiInstance != null;
        case Xpdl2Package.LOOP__LOOP_TYPE:
            return isSetLoopType();
        case Xpdl2Package.LOOP__ANY_ATTRIBUTE:
            return anyAttribute != null && !anyAttribute.isEmpty();
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
        result.append(" (loopType: "); //$NON-NLS-1$
        if (loopTypeESet)
            result.append(loopType);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", anyAttribute: "); //$NON-NLS-1$
        result.append(anyAttribute);
        result.append(')');
        return result.toString();
    }

} //LoopImpl
