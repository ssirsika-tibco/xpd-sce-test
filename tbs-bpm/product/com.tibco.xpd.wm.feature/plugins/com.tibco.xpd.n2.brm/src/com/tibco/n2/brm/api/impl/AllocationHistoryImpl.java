/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.AllocationHistory;
import com.tibco.n2.brm.api.N2BRMPackage;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Allocation History</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.AllocationHistoryImpl#getResourceID <em>Resource ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.AllocationHistoryImpl#getAllocationDate <em>Allocation Date</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.AllocationHistoryImpl#getAllocationID <em>Allocation ID</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class AllocationHistoryImpl extends EObjectImpl implements AllocationHistory {
    /**
     * The default value of the '{@link #getResourceID() <em>Resource ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResourceID()
     * @generated
     * @ordered
     */
    protected static final String RESOURCE_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getResourceID() <em>Resource ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResourceID()
     * @generated
     * @ordered
     */
    protected String resourceID = RESOURCE_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getAllocationDate() <em>Allocation Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAllocationDate()
     * @generated
     * @ordered
     */
    protected static final XMLGregorianCalendar ALLOCATION_DATE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAllocationDate() <em>Allocation Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAllocationDate()
     * @generated
     * @ordered
     */
    protected XMLGregorianCalendar allocationDate = ALLOCATION_DATE_EDEFAULT;

    /**
     * The default value of the '{@link #getAllocationID() <em>Allocation ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAllocationID()
     * @generated
     * @ordered
     */
    protected static final long ALLOCATION_ID_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getAllocationID() <em>Allocation ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAllocationID()
     * @generated
     * @ordered
     */
    protected long allocationID = ALLOCATION_ID_EDEFAULT;

    /**
     * This is true if the Allocation ID attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean allocationIDESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AllocationHistoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.ALLOCATION_HISTORY;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getResourceID() {
        return resourceID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setResourceID(String newResourceID) {
        String oldResourceID = resourceID;
        resourceID = newResourceID;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ALLOCATION_HISTORY__RESOURCE_ID, oldResourceID, resourceID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XMLGregorianCalendar getAllocationDate() {
        return allocationDate;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAllocationDate(XMLGregorianCalendar newAllocationDate) {
        XMLGregorianCalendar oldAllocationDate = allocationDate;
        allocationDate = newAllocationDate;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ALLOCATION_HISTORY__ALLOCATION_DATE, oldAllocationDate, allocationDate));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getAllocationID() {
        return allocationID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAllocationID(long newAllocationID) {
        long oldAllocationID = allocationID;
        allocationID = newAllocationID;
        boolean oldAllocationIDESet = allocationIDESet;
        allocationIDESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ALLOCATION_HISTORY__ALLOCATION_ID, oldAllocationID, allocationID, !oldAllocationIDESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetAllocationID() {
        long oldAllocationID = allocationID;
        boolean oldAllocationIDESet = allocationIDESet;
        allocationID = ALLOCATION_ID_EDEFAULT;
        allocationIDESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.ALLOCATION_HISTORY__ALLOCATION_ID, oldAllocationID, ALLOCATION_ID_EDEFAULT, oldAllocationIDESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetAllocationID() {
        return allocationIDESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case N2BRMPackage.ALLOCATION_HISTORY__RESOURCE_ID:
                return getResourceID();
            case N2BRMPackage.ALLOCATION_HISTORY__ALLOCATION_DATE:
                return getAllocationDate();
            case N2BRMPackage.ALLOCATION_HISTORY__ALLOCATION_ID:
                return getAllocationID();
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
            case N2BRMPackage.ALLOCATION_HISTORY__RESOURCE_ID:
                setResourceID((String)newValue);
                return;
            case N2BRMPackage.ALLOCATION_HISTORY__ALLOCATION_DATE:
                setAllocationDate((XMLGregorianCalendar)newValue);
                return;
            case N2BRMPackage.ALLOCATION_HISTORY__ALLOCATION_ID:
                setAllocationID((Long)newValue);
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
            case N2BRMPackage.ALLOCATION_HISTORY__RESOURCE_ID:
                setResourceID(RESOURCE_ID_EDEFAULT);
                return;
            case N2BRMPackage.ALLOCATION_HISTORY__ALLOCATION_DATE:
                setAllocationDate(ALLOCATION_DATE_EDEFAULT);
                return;
            case N2BRMPackage.ALLOCATION_HISTORY__ALLOCATION_ID:
                unsetAllocationID();
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
            case N2BRMPackage.ALLOCATION_HISTORY__RESOURCE_ID:
                return RESOURCE_ID_EDEFAULT == null ? resourceID != null : !RESOURCE_ID_EDEFAULT.equals(resourceID);
            case N2BRMPackage.ALLOCATION_HISTORY__ALLOCATION_DATE:
                return ALLOCATION_DATE_EDEFAULT == null ? allocationDate != null : !ALLOCATION_DATE_EDEFAULT.equals(allocationDate);
            case N2BRMPackage.ALLOCATION_HISTORY__ALLOCATION_ID:
                return isSetAllocationID();
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
        result.append(" (resourceID: ");
        result.append(resourceID);
        result.append(", allocationDate: ");
        result.append(allocationDate);
        result.append(", allocationID: ");
        if (allocationIDESet) result.append(allocationID); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //AllocationHistoryImpl
