/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.GetWorkListItemsForViewType;
import com.tibco.n2.brm.api.N2BRMPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Get Work List Items For View Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetWorkListItemsForViewTypeImpl#isGetAllocatedItems <em>Get Allocated Items</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetWorkListItemsForViewTypeImpl#isGetTotalCount <em>Get Total Count</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetWorkListItemsForViewTypeImpl#getNumberOfItems <em>Number Of Items</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetWorkListItemsForViewTypeImpl#getStartPosition <em>Start Position</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetWorkListItemsForViewTypeImpl#getWorkListViewID <em>Work List View ID</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GetWorkListItemsForViewTypeImpl extends EObjectImpl implements GetWorkListItemsForViewType {
    /**
     * The default value of the '{@link #isGetAllocatedItems() <em>Get Allocated Items</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isGetAllocatedItems()
     * @generated
     * @ordered
     */
    protected static final boolean GET_ALLOCATED_ITEMS_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isGetAllocatedItems() <em>Get Allocated Items</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isGetAllocatedItems()
     * @generated
     * @ordered
     */
    protected boolean getAllocatedItems = GET_ALLOCATED_ITEMS_EDEFAULT;

    /**
     * This is true if the Get Allocated Items attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean getAllocatedItemsESet;

    /**
     * The default value of the '{@link #isGetTotalCount() <em>Get Total Count</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isGetTotalCount()
     * @generated
     * @ordered
     */
    protected static final boolean GET_TOTAL_COUNT_EDEFAULT = true;

    /**
     * The cached value of the '{@link #isGetTotalCount() <em>Get Total Count</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isGetTotalCount()
     * @generated
     * @ordered
     */
    protected boolean getTotalCount = GET_TOTAL_COUNT_EDEFAULT;

    /**
     * This is true if the Get Total Count attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean getTotalCountESet;

    /**
     * The default value of the '{@link #getNumberOfItems() <em>Number Of Items</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNumberOfItems()
     * @generated
     * @ordered
     */
    protected static final long NUMBER_OF_ITEMS_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getNumberOfItems() <em>Number Of Items</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNumberOfItems()
     * @generated
     * @ordered
     */
    protected long numberOfItems = NUMBER_OF_ITEMS_EDEFAULT;

    /**
     * This is true if the Number Of Items attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean numberOfItemsESet;

    /**
     * The default value of the '{@link #getStartPosition() <em>Start Position</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartPosition()
     * @generated
     * @ordered
     */
    protected static final long START_POSITION_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getStartPosition() <em>Start Position</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartPosition()
     * @generated
     * @ordered
     */
    protected long startPosition = START_POSITION_EDEFAULT;

    /**
     * This is true if the Start Position attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean startPositionESet;

    /**
     * The default value of the '{@link #getWorkListViewID() <em>Work List View ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkListViewID()
     * @generated
     * @ordered
     */
    protected static final long WORK_LIST_VIEW_ID_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getWorkListViewID() <em>Work List View ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkListViewID()
     * @generated
     * @ordered
     */
    protected long workListViewID = WORK_LIST_VIEW_ID_EDEFAULT;

    /**
     * This is true if the Work List View ID attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean workListViewIDESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected GetWorkListItemsForViewTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isGetAllocatedItems() {
        return getAllocatedItems;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetAllocatedItems(boolean newGetAllocatedItems) {
        boolean oldGetAllocatedItems = getAllocatedItems;
        getAllocatedItems = newGetAllocatedItems;
        boolean oldGetAllocatedItemsESet = getAllocatedItemsESet;
        getAllocatedItemsESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__GET_ALLOCATED_ITEMS, oldGetAllocatedItems, getAllocatedItems, !oldGetAllocatedItemsESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetGetAllocatedItems() {
        boolean oldGetAllocatedItems = getAllocatedItems;
        boolean oldGetAllocatedItemsESet = getAllocatedItemsESet;
        getAllocatedItems = GET_ALLOCATED_ITEMS_EDEFAULT;
        getAllocatedItemsESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__GET_ALLOCATED_ITEMS, oldGetAllocatedItems, GET_ALLOCATED_ITEMS_EDEFAULT, oldGetAllocatedItemsESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetGetAllocatedItems() {
        return getAllocatedItemsESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isGetTotalCount() {
        return getTotalCount;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetTotalCount(boolean newGetTotalCount) {
        boolean oldGetTotalCount = getTotalCount;
        getTotalCount = newGetTotalCount;
        boolean oldGetTotalCountESet = getTotalCountESet;
        getTotalCountESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__GET_TOTAL_COUNT, oldGetTotalCount, getTotalCount, !oldGetTotalCountESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetGetTotalCount() {
        boolean oldGetTotalCount = getTotalCount;
        boolean oldGetTotalCountESet = getTotalCountESet;
        getTotalCount = GET_TOTAL_COUNT_EDEFAULT;
        getTotalCountESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__GET_TOTAL_COUNT, oldGetTotalCount, GET_TOTAL_COUNT_EDEFAULT, oldGetTotalCountESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetGetTotalCount() {
        return getTotalCountESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getNumberOfItems() {
        return numberOfItems;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNumberOfItems(long newNumberOfItems) {
        long oldNumberOfItems = numberOfItems;
        numberOfItems = newNumberOfItems;
        boolean oldNumberOfItemsESet = numberOfItemsESet;
        numberOfItemsESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__NUMBER_OF_ITEMS, oldNumberOfItems, numberOfItems, !oldNumberOfItemsESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetNumberOfItems() {
        long oldNumberOfItems = numberOfItems;
        boolean oldNumberOfItemsESet = numberOfItemsESet;
        numberOfItems = NUMBER_OF_ITEMS_EDEFAULT;
        numberOfItemsESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__NUMBER_OF_ITEMS, oldNumberOfItems, NUMBER_OF_ITEMS_EDEFAULT, oldNumberOfItemsESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetNumberOfItems() {
        return numberOfItemsESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getStartPosition() {
        return startPosition;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setStartPosition(long newStartPosition) {
        long oldStartPosition = startPosition;
        startPosition = newStartPosition;
        boolean oldStartPositionESet = startPositionESet;
        startPositionESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__START_POSITION, oldStartPosition, startPosition, !oldStartPositionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetStartPosition() {
        long oldStartPosition = startPosition;
        boolean oldStartPositionESet = startPositionESet;
        startPosition = START_POSITION_EDEFAULT;
        startPositionESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__START_POSITION, oldStartPosition, START_POSITION_EDEFAULT, oldStartPositionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetStartPosition() {
        return startPositionESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getWorkListViewID() {
        return workListViewID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkListViewID(long newWorkListViewID) {
        long oldWorkListViewID = workListViewID;
        workListViewID = newWorkListViewID;
        boolean oldWorkListViewIDESet = workListViewIDESet;
        workListViewIDESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__WORK_LIST_VIEW_ID, oldWorkListViewID, workListViewID, !oldWorkListViewIDESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetWorkListViewID() {
        long oldWorkListViewID = workListViewID;
        boolean oldWorkListViewIDESet = workListViewIDESet;
        workListViewID = WORK_LIST_VIEW_ID_EDEFAULT;
        workListViewIDESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__WORK_LIST_VIEW_ID, oldWorkListViewID, WORK_LIST_VIEW_ID_EDEFAULT, oldWorkListViewIDESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetWorkListViewID() {
        return workListViewIDESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__GET_ALLOCATED_ITEMS:
                return isGetAllocatedItems();
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__GET_TOTAL_COUNT:
                return isGetTotalCount();
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__NUMBER_OF_ITEMS:
                return getNumberOfItems();
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__START_POSITION:
                return getStartPosition();
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__WORK_LIST_VIEW_ID:
                return getWorkListViewID();
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
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__GET_ALLOCATED_ITEMS:
                setGetAllocatedItems((Boolean)newValue);
                return;
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__GET_TOTAL_COUNT:
                setGetTotalCount((Boolean)newValue);
                return;
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__NUMBER_OF_ITEMS:
                setNumberOfItems((Long)newValue);
                return;
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__START_POSITION:
                setStartPosition((Long)newValue);
                return;
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__WORK_LIST_VIEW_ID:
                setWorkListViewID((Long)newValue);
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
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__GET_ALLOCATED_ITEMS:
                unsetGetAllocatedItems();
                return;
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__GET_TOTAL_COUNT:
                unsetGetTotalCount();
                return;
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__NUMBER_OF_ITEMS:
                unsetNumberOfItems();
                return;
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__START_POSITION:
                unsetStartPosition();
                return;
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__WORK_LIST_VIEW_ID:
                unsetWorkListViewID();
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
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__GET_ALLOCATED_ITEMS:
                return isSetGetAllocatedItems();
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__GET_TOTAL_COUNT:
                return isSetGetTotalCount();
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__NUMBER_OF_ITEMS:
                return isSetNumberOfItems();
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__START_POSITION:
                return isSetStartPosition();
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__WORK_LIST_VIEW_ID:
                return isSetWorkListViewID();
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
        result.append(" (getAllocatedItems: ");
        if (getAllocatedItemsESet) result.append(getAllocatedItems); else result.append("<unset>");
        result.append(", getTotalCount: ");
        if (getTotalCountESet) result.append(getTotalCount); else result.append("<unset>");
        result.append(", numberOfItems: ");
        if (numberOfItemsESet) result.append(numberOfItems); else result.append("<unset>");
        result.append(", startPosition: ");
        if (startPositionESet) result.append(startPosition); else result.append("<unset>");
        result.append(", workListViewID: ");
        if (workListViewIDESet) result.append(workListViewID); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //GetWorkListItemsForViewTypeImpl
