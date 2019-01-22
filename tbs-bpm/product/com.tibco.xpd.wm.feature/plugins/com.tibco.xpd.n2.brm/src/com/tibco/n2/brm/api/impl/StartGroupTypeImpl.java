/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.StartGroupType;
import com.tibco.n2.brm.api.WorkGroupType;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Start Group Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.StartGroupTypeImpl#getGroupType <em>Group Type</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.StartGroupTypeImpl#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StartGroupTypeImpl extends EObjectImpl implements StartGroupType {
    /**
     * The default value of the '{@link #getGroupType() <em>Group Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGroupType()
     * @generated
     * @ordered
     */
    protected static final WorkGroupType GROUP_TYPE_EDEFAULT = WorkGroupType.CHAINING;

    /**
     * The cached value of the '{@link #getGroupType() <em>Group Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGroupType()
     * @generated
     * @ordered
     */
    protected WorkGroupType groupType = GROUP_TYPE_EDEFAULT;

    /**
     * This is true if the Group Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean groupTypeESet;

    /**
     * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected static final String DESCRIPTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected String description = DESCRIPTION_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected StartGroupTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.START_GROUP_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkGroupType getGroupType() {
        return groupType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGroupType(WorkGroupType newGroupType) {
        WorkGroupType oldGroupType = groupType;
        groupType = newGroupType == null ? GROUP_TYPE_EDEFAULT : newGroupType;
        boolean oldGroupTypeESet = groupTypeESet;
        groupTypeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.START_GROUP_TYPE__GROUP_TYPE, oldGroupType, groupType, !oldGroupTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetGroupType() {
        WorkGroupType oldGroupType = groupType;
        boolean oldGroupTypeESet = groupTypeESet;
        groupType = GROUP_TYPE_EDEFAULT;
        groupTypeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.START_GROUP_TYPE__GROUP_TYPE, oldGroupType, GROUP_TYPE_EDEFAULT, oldGroupTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetGroupType() {
        return groupTypeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDescription() {
        return description;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDescription(String newDescription) {
        String oldDescription = description;
        description = newDescription;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.START_GROUP_TYPE__DESCRIPTION, oldDescription, description));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case N2BRMPackage.START_GROUP_TYPE__GROUP_TYPE:
                return getGroupType();
            case N2BRMPackage.START_GROUP_TYPE__DESCRIPTION:
                return getDescription();
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
            case N2BRMPackage.START_GROUP_TYPE__GROUP_TYPE:
                setGroupType((WorkGroupType)newValue);
                return;
            case N2BRMPackage.START_GROUP_TYPE__DESCRIPTION:
                setDescription((String)newValue);
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
            case N2BRMPackage.START_GROUP_TYPE__GROUP_TYPE:
                unsetGroupType();
                return;
            case N2BRMPackage.START_GROUP_TYPE__DESCRIPTION:
                setDescription(DESCRIPTION_EDEFAULT);
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
            case N2BRMPackage.START_GROUP_TYPE__GROUP_TYPE:
                return isSetGroupType();
            case N2BRMPackage.START_GROUP_TYPE__DESCRIPTION:
                return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
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
        result.append(" (groupType: ");
        if (groupTypeESet) result.append(groupType); else result.append("<unset>");
        result.append(", description: ");
        result.append(description);
        result.append(')');
        return result.toString();
    }

} //StartGroupTypeImpl
