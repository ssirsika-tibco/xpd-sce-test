/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.WorkItemPriorityType;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Work Item Priority Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemPriorityTypeImpl#getAbsPriority <em>Abs Priority</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemPriorityTypeImpl#getOffsetPriority <em>Offset Priority</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkItemPriorityTypeImpl extends EObjectImpl implements WorkItemPriorityType {
    /**
     * The default value of the '{@link #getAbsPriority() <em>Abs Priority</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAbsPriority()
     * @generated
     * @ordered
     */
    protected static final int ABS_PRIORITY_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getAbsPriority() <em>Abs Priority</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAbsPriority()
     * @generated
     * @ordered
     */
    protected int absPriority = ABS_PRIORITY_EDEFAULT;

    /**
     * This is true if the Abs Priority attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean absPriorityESet;

    /**
     * The default value of the '{@link #getOffsetPriority() <em>Offset Priority</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOffsetPriority()
     * @generated
     * @ordered
     */
    protected static final int OFFSET_PRIORITY_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getOffsetPriority() <em>Offset Priority</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOffsetPriority()
     * @generated
     * @ordered
     */
    protected int offsetPriority = OFFSET_PRIORITY_EDEFAULT;

    /**
     * This is true if the Offset Priority attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean offsetPriorityESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorkItemPriorityTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.WORK_ITEM_PRIORITY_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getAbsPriority() {
        return absPriority;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAbsPriority(int newAbsPriority) {
        int oldAbsPriority = absPriority;
        absPriority = newAbsPriority;
        boolean oldAbsPriorityESet = absPriorityESet;
        absPriorityESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_PRIORITY_TYPE__ABS_PRIORITY, oldAbsPriority, absPriority, !oldAbsPriorityESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetAbsPriority() {
        int oldAbsPriority = absPriority;
        boolean oldAbsPriorityESet = absPriorityESet;
        absPriority = ABS_PRIORITY_EDEFAULT;
        absPriorityESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.WORK_ITEM_PRIORITY_TYPE__ABS_PRIORITY, oldAbsPriority, ABS_PRIORITY_EDEFAULT, oldAbsPriorityESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetAbsPriority() {
        return absPriorityESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getOffsetPriority() {
        return offsetPriority;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOffsetPriority(int newOffsetPriority) {
        int oldOffsetPriority = offsetPriority;
        offsetPriority = newOffsetPriority;
        boolean oldOffsetPriorityESet = offsetPriorityESet;
        offsetPriorityESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_PRIORITY_TYPE__OFFSET_PRIORITY, oldOffsetPriority, offsetPriority, !oldOffsetPriorityESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetOffsetPriority() {
        int oldOffsetPriority = offsetPriority;
        boolean oldOffsetPriorityESet = offsetPriorityESet;
        offsetPriority = OFFSET_PRIORITY_EDEFAULT;
        offsetPriorityESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.WORK_ITEM_PRIORITY_TYPE__OFFSET_PRIORITY, oldOffsetPriority, OFFSET_PRIORITY_EDEFAULT, oldOffsetPriorityESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetOffsetPriority() {
        return offsetPriorityESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case N2BRMPackage.WORK_ITEM_PRIORITY_TYPE__ABS_PRIORITY:
                return getAbsPriority();
            case N2BRMPackage.WORK_ITEM_PRIORITY_TYPE__OFFSET_PRIORITY:
                return getOffsetPriority();
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
            case N2BRMPackage.WORK_ITEM_PRIORITY_TYPE__ABS_PRIORITY:
                setAbsPriority((Integer)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_PRIORITY_TYPE__OFFSET_PRIORITY:
                setOffsetPriority((Integer)newValue);
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
            case N2BRMPackage.WORK_ITEM_PRIORITY_TYPE__ABS_PRIORITY:
                unsetAbsPriority();
                return;
            case N2BRMPackage.WORK_ITEM_PRIORITY_TYPE__OFFSET_PRIORITY:
                unsetOffsetPriority();
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
            case N2BRMPackage.WORK_ITEM_PRIORITY_TYPE__ABS_PRIORITY:
                return isSetAbsPriority();
            case N2BRMPackage.WORK_ITEM_PRIORITY_TYPE__OFFSET_PRIORITY:
                return isSetOffsetPriority();
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
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (absPriority: ");
        if (absPriorityESet) result.append(absPriority); else result.append("<unset>");
        result.append(", offsetPriority: ");
        if (offsetPriorityESet) result.append(offsetPriority); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //WorkItemPriorityTypeImpl
