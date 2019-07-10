/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdl2.ExclusiveType;
import com.tibco.xpd.xpdl2.GatewayType;
import com.tibco.xpd.xpdl2.Join;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Join</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.JoinImpl#getIncomingCondtion <em>Incoming Condtion</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.JoinImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.JoinImpl#getExclusiveType <em>Exclusive Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class JoinImpl extends EObjectImpl implements Join {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getIncomingCondtion() <em>Incoming Condtion</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIncomingCondtion()
     * @generated
     * @ordered
     */
    protected static final String INCOMING_CONDTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIncomingCondtion() <em>Incoming Condtion</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIncomingCondtion()
     * @generated
     * @ordered
     */
    protected String incomingCondtion = INCOMING_CONDTION_EDEFAULT;

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
    protected JoinImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.JOIN;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getIncomingCondtion() {
        return incomingCondtion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setIncomingCondtion(String newIncomingCondtion) {
        String oldIncomingCondtion = incomingCondtion;
        incomingCondtion = newIncomingCondtion;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.JOIN__INCOMING_CONDTION,
                    oldIncomingCondtion, incomingCondtion));
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
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.JOIN__TYPE, oldType, type,
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
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.JOIN__TYPE, oldType, TYPE_EDEFAULT,
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
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.JOIN__EXCLUSIVE_TYPE, oldExclusiveType,
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
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.JOIN__EXCLUSIVE_TYPE, oldExclusiveType,
                    EXCLUSIVE_TYPE_EDEFAULT, oldExclusiveTypeESet));
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
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case Xpdl2Package.JOIN__INCOMING_CONDTION:
            return getIncomingCondtion();
        case Xpdl2Package.JOIN__TYPE:
            return getType();
        case Xpdl2Package.JOIN__EXCLUSIVE_TYPE:
            return getExclusiveType();
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
        case Xpdl2Package.JOIN__INCOMING_CONDTION:
            setIncomingCondtion((String) newValue);
            return;
        case Xpdl2Package.JOIN__TYPE:
            setType((JoinSplitType) newValue);
            return;
        case Xpdl2Package.JOIN__EXCLUSIVE_TYPE:
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
        case Xpdl2Package.JOIN__INCOMING_CONDTION:
            setIncomingCondtion(INCOMING_CONDTION_EDEFAULT);
            return;
        case Xpdl2Package.JOIN__TYPE:
            unsetType();
            return;
        case Xpdl2Package.JOIN__EXCLUSIVE_TYPE:
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
        case Xpdl2Package.JOIN__INCOMING_CONDTION:
            return INCOMING_CONDTION_EDEFAULT == null ? incomingCondtion != null
                    : !INCOMING_CONDTION_EDEFAULT.equals(incomingCondtion);
        case Xpdl2Package.JOIN__TYPE:
            return isSetType();
        case Xpdl2Package.JOIN__EXCLUSIVE_TYPE:
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
        result.append(" (incomingCondtion: "); //$NON-NLS-1$
        result.append(incomingCondtion);
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

} //JoinImpl
