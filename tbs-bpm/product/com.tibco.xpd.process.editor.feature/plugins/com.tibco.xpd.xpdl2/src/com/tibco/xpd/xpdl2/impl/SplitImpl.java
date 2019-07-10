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
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.ExclusiveType;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.GatewayType;
import com.tibco.xpd.xpdl2.Split;
import com.tibco.xpd.xpdl2.TransitionRef;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Split</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.SplitImpl#getTransitionRefs <em>Transition Refs</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.SplitImpl#getOutgoingCondition <em>Outgoing Condition</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.SplitImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.SplitImpl#getExclusiveType <em>Exclusive Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SplitImpl extends EObjectImpl implements Split {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getTransitionRefs() <em>Transition Refs</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTransitionRefs()
     * @generated
     * @ordered
     */
    protected EList<TransitionRef> transitionRefs;

    /**
     * The default value of the '{@link #getOutgoingCondition() <em>Outgoing Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOutgoingCondition()
     * @generated
     * @ordered
     */
    protected static final String OUTGOING_CONDITION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getOutgoingCondition() <em>Outgoing Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOutgoingCondition()
     * @generated
     * @ordered
     */
    protected String outgoingCondition = OUTGOING_CONDITION_EDEFAULT;

    /**
     * The default value of the '{@link #getType() <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getType()
     * @generated
     * @ordered
     */
    protected static final JoinSplitType TYPE_EDEFAULT = JoinSplitType.DEPRECATED_AND_LITERAL;

    /**
     * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getType()
     * @generated
     * @ordered
     */
    protected JoinSplitType type = TYPE_EDEFAULT;

    /**
     * This is true if the Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean typeESet;

    /**
     * The default value of the '{@link #getExclusiveType() <em>Exclusive Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExclusiveType()
     * @generated
     * @ordered
     */
    protected static final ExclusiveType EXCLUSIVE_TYPE_EDEFAULT = ExclusiveType.DATA;

    /**
     * The cached value of the '{@link #getExclusiveType() <em>Exclusive Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExclusiveType()
     * @generated
     * @ordered
     */
    protected ExclusiveType exclusiveType = EXCLUSIVE_TYPE_EDEFAULT;

    /**
     * This is true if the Exclusive Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean exclusiveTypeESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SplitImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.SPLIT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<TransitionRef> getTransitionRefs() {
        if (transitionRefs == null) {
            transitionRefs = new EObjectContainmentEList<TransitionRef>(TransitionRef.class, this,
                    Xpdl2Package.SPLIT__TRANSITION_REFS);
        }
        return transitionRefs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getOutgoingCondition() {
        return outgoingCondition;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOutgoingCondition(String newOutgoingCondition) {
        String oldOutgoingCondition = outgoingCondition;
        outgoingCondition = newOutgoingCondition;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.SPLIT__OUTGOING_CONDITION,
                    oldOutgoingCondition, outgoingCondition));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public JoinSplitType getType() {
        return type;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setType(JoinSplitType newType) {
        JoinSplitType oldType = type;
        type = newType == null ? TYPE_EDEFAULT : newType;
        boolean oldTypeESet = typeESet;
        typeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.SPLIT__TYPE, oldType, type,
                    !oldTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetType() {
        JoinSplitType oldType = type;
        boolean oldTypeESet = typeESet;
        type = TYPE_EDEFAULT;
        typeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.SPLIT__TYPE, oldType, TYPE_EDEFAULT,
                    oldTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetType() {
        return typeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExclusiveType getExclusiveType() {
        return exclusiveType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setExclusiveType(ExclusiveType newExclusiveType) {
        ExclusiveType oldExclusiveType = exclusiveType;
        exclusiveType = newExclusiveType == null ? EXCLUSIVE_TYPE_EDEFAULT : newExclusiveType;
        boolean oldExclusiveTypeESet = exclusiveTypeESet;
        exclusiveTypeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.SPLIT__EXCLUSIVE_TYPE, oldExclusiveType,
                    exclusiveType, !oldExclusiveTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetExclusiveType() {
        ExclusiveType oldExclusiveType = exclusiveType;
        boolean oldExclusiveTypeESet = exclusiveTypeESet;
        exclusiveType = EXCLUSIVE_TYPE_EDEFAULT;
        exclusiveTypeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.SPLIT__EXCLUSIVE_TYPE,
                    oldExclusiveType, EXCLUSIVE_TYPE_EDEFAULT, oldExclusiveTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetExclusiveType() {
        return exclusiveTypeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.SPLIT__TRANSITION_REFS:
            return ((InternalEList<?>) getTransitionRefs()).basicRemove(otherEnd, msgs);
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
        case Xpdl2Package.SPLIT__TRANSITION_REFS:
            return getTransitionRefs();
        case Xpdl2Package.SPLIT__OUTGOING_CONDITION:
            return getOutgoingCondition();
        case Xpdl2Package.SPLIT__TYPE:
            return getType();
        case Xpdl2Package.SPLIT__EXCLUSIVE_TYPE:
            return getExclusiveType();
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
        case Xpdl2Package.SPLIT__TRANSITION_REFS:
            getTransitionRefs().clear();
            getTransitionRefs().addAll((Collection<? extends TransitionRef>) newValue);
            return;
        case Xpdl2Package.SPLIT__OUTGOING_CONDITION:
            setOutgoingCondition((String) newValue);
            return;
        case Xpdl2Package.SPLIT__TYPE:
            setType((JoinSplitType) newValue);
            return;
        case Xpdl2Package.SPLIT__EXCLUSIVE_TYPE:
            setExclusiveType((ExclusiveType) newValue);
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
        case Xpdl2Package.SPLIT__TRANSITION_REFS:
            getTransitionRefs().clear();
            return;
        case Xpdl2Package.SPLIT__OUTGOING_CONDITION:
            setOutgoingCondition(OUTGOING_CONDITION_EDEFAULT);
            return;
        case Xpdl2Package.SPLIT__TYPE:
            unsetType();
            return;
        case Xpdl2Package.SPLIT__EXCLUSIVE_TYPE:
            unsetExclusiveType();
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
        case Xpdl2Package.SPLIT__TRANSITION_REFS:
            return transitionRefs != null && !transitionRefs.isEmpty();
        case Xpdl2Package.SPLIT__OUTGOING_CONDITION:
            return OUTGOING_CONDITION_EDEFAULT == null ? outgoingCondition != null
                    : !OUTGOING_CONDITION_EDEFAULT.equals(outgoingCondition);
        case Xpdl2Package.SPLIT__TYPE:
            return isSetType();
        case Xpdl2Package.SPLIT__EXCLUSIVE_TYPE:
            return isSetExclusiveType();
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
        result.append(" (outgoingCondition: "); //$NON-NLS-1$
        result.append(outgoingCondition);
        result.append(", type: "); //$NON-NLS-1$
        if (typeESet)
            result.append(type);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", exclusiveType: "); //$NON-NLS-1$
        if (exclusiveTypeESet)
            result.append(exclusiveType);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //SplitImpl
