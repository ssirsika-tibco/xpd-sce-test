/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataType;
import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.OrderFilterCriteria;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Get Work List Items For Global Data Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetWorkListItemsForGlobalDataTypeImpl#getGlobalDataRef <em>Global Data Ref</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetWorkListItemsForGlobalDataTypeImpl#getOrderFilterCriteria <em>Order Filter Criteria</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetWorkListItemsForGlobalDataTypeImpl#getNumberOfItems <em>Number Of Items</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetWorkListItemsForGlobalDataTypeImpl#getStartPosition <em>Start Position</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GetWorkListItemsForGlobalDataTypeImpl extends EObjectImpl implements GetWorkListItemsForGlobalDataType {
    /**
     * The cached value of the '{@link #getGlobalDataRef() <em>Global Data Ref</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGlobalDataRef()
     * @generated
     * @ordered
     */
    protected EList<String> globalDataRef;

    /**
     * The cached value of the '{@link #getOrderFilterCriteria() <em>Order Filter Criteria</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOrderFilterCriteria()
     * @generated
     * @ordered
     */
    protected OrderFilterCriteria orderFilterCriteria;

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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected GetWorkListItemsForGlobalDataTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<String> getGlobalDataRef() {
        if (globalDataRef == null) {
            globalDataRef = new EDataTypeEList<String>(String.class, this, N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__GLOBAL_DATA_REF);
        }
        return globalDataRef;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OrderFilterCriteria getOrderFilterCriteria() {
        return orderFilterCriteria;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetOrderFilterCriteria(OrderFilterCriteria newOrderFilterCriteria, NotificationChain msgs) {
        OrderFilterCriteria oldOrderFilterCriteria = orderFilterCriteria;
        orderFilterCriteria = newOrderFilterCriteria;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__ORDER_FILTER_CRITERIA, oldOrderFilterCriteria, newOrderFilterCriteria);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOrderFilterCriteria(OrderFilterCriteria newOrderFilterCriteria) {
        if (newOrderFilterCriteria != orderFilterCriteria) {
            NotificationChain msgs = null;
            if (orderFilterCriteria != null)
                msgs = ((InternalEObject)orderFilterCriteria).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__ORDER_FILTER_CRITERIA, null, msgs);
            if (newOrderFilterCriteria != null)
                msgs = ((InternalEObject)newOrderFilterCriteria).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__ORDER_FILTER_CRITERIA, null, msgs);
            msgs = basicSetOrderFilterCriteria(newOrderFilterCriteria, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__ORDER_FILTER_CRITERIA, newOrderFilterCriteria, newOrderFilterCriteria));
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
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__NUMBER_OF_ITEMS, oldNumberOfItems, numberOfItems, !oldNumberOfItemsESet));
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
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__NUMBER_OF_ITEMS, oldNumberOfItems, NUMBER_OF_ITEMS_EDEFAULT, oldNumberOfItemsESet));
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
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__START_POSITION, oldStartPosition, startPosition, !oldStartPositionESet));
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
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__START_POSITION, oldStartPosition, START_POSITION_EDEFAULT, oldStartPositionESet));
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
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__ORDER_FILTER_CRITERIA:
                return basicSetOrderFilterCriteria(null, msgs);
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
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__GLOBAL_DATA_REF:
                return getGlobalDataRef();
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__ORDER_FILTER_CRITERIA:
                return getOrderFilterCriteria();
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__NUMBER_OF_ITEMS:
                return getNumberOfItems();
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__START_POSITION:
                return getStartPosition();
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
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__GLOBAL_DATA_REF:
                getGlobalDataRef().clear();
                getGlobalDataRef().addAll((Collection<? extends String>)newValue);
                return;
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__ORDER_FILTER_CRITERIA:
                setOrderFilterCriteria((OrderFilterCriteria)newValue);
                return;
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__NUMBER_OF_ITEMS:
                setNumberOfItems((Long)newValue);
                return;
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__START_POSITION:
                setStartPosition((Long)newValue);
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
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__GLOBAL_DATA_REF:
                getGlobalDataRef().clear();
                return;
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__ORDER_FILTER_CRITERIA:
                setOrderFilterCriteria((OrderFilterCriteria)null);
                return;
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__NUMBER_OF_ITEMS:
                unsetNumberOfItems();
                return;
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__START_POSITION:
                unsetStartPosition();
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
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__GLOBAL_DATA_REF:
                return globalDataRef != null && !globalDataRef.isEmpty();
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__ORDER_FILTER_CRITERIA:
                return orderFilterCriteria != null;
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__NUMBER_OF_ITEMS:
                return isSetNumberOfItems();
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__START_POSITION:
                return isSetStartPosition();
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
        result.append(" (globalDataRef: ");
        result.append(globalDataRef);
        result.append(", numberOfItems: ");
        if (numberOfItemsESet) result.append(numberOfItems); else result.append("<unset>");
        result.append(", startPosition: ");
        if (startPositionESet) result.append(startPosition); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //GetWorkListItemsForGlobalDataTypeImpl
