/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.GetViewsForResourceType;
import com.tibco.n2.brm.api.N2BRMPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Get Views For Resource Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetViewsForResourceTypeImpl#getApiVersion <em>Api Version</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetViewsForResourceTypeImpl#getNumberOfItems <em>Number Of Items</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.GetViewsForResourceTypeImpl#getStartPosition <em>Start Position</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GetViewsForResourceTypeImpl extends EObjectImpl implements GetViewsForResourceType {
    /**
     * The default value of the '{@link #getApiVersion() <em>Api Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getApiVersion()
     * @generated
     * @ordered
     */
    protected static final int API_VERSION_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getApiVersion() <em>Api Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getApiVersion()
     * @generated
     * @ordered
     */
    protected int apiVersion = API_VERSION_EDEFAULT;

    /**
     * This is true if the Api Version attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean apiVersionESet;

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
    protected GetViewsForResourceTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.GET_VIEWS_FOR_RESOURCE_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getApiVersion() {
        return apiVersion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setApiVersion(int newApiVersion) {
        int oldApiVersion = apiVersion;
        apiVersion = newApiVersion;
        boolean oldApiVersionESet = apiVersionESet;
        apiVersionESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_VIEWS_FOR_RESOURCE_TYPE__API_VERSION, oldApiVersion, apiVersion, !oldApiVersionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetApiVersion() {
        int oldApiVersion = apiVersion;
        boolean oldApiVersionESet = apiVersionESet;
        apiVersion = API_VERSION_EDEFAULT;
        apiVersionESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.GET_VIEWS_FOR_RESOURCE_TYPE__API_VERSION, oldApiVersion, API_VERSION_EDEFAULT, oldApiVersionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetApiVersion() {
        return apiVersionESet;
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
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_VIEWS_FOR_RESOURCE_TYPE__NUMBER_OF_ITEMS, oldNumberOfItems, numberOfItems, !oldNumberOfItemsESet));
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
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.GET_VIEWS_FOR_RESOURCE_TYPE__NUMBER_OF_ITEMS, oldNumberOfItems, NUMBER_OF_ITEMS_EDEFAULT, oldNumberOfItemsESet));
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
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.GET_VIEWS_FOR_RESOURCE_TYPE__START_POSITION, oldStartPosition, startPosition, !oldStartPositionESet));
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
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.GET_VIEWS_FOR_RESOURCE_TYPE__START_POSITION, oldStartPosition, START_POSITION_EDEFAULT, oldStartPositionESet));
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
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case N2BRMPackage.GET_VIEWS_FOR_RESOURCE_TYPE__API_VERSION:
                return getApiVersion();
            case N2BRMPackage.GET_VIEWS_FOR_RESOURCE_TYPE__NUMBER_OF_ITEMS:
                return getNumberOfItems();
            case N2BRMPackage.GET_VIEWS_FOR_RESOURCE_TYPE__START_POSITION:
                return getStartPosition();
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
            case N2BRMPackage.GET_VIEWS_FOR_RESOURCE_TYPE__API_VERSION:
                setApiVersion((Integer)newValue);
                return;
            case N2BRMPackage.GET_VIEWS_FOR_RESOURCE_TYPE__NUMBER_OF_ITEMS:
                setNumberOfItems((Long)newValue);
                return;
            case N2BRMPackage.GET_VIEWS_FOR_RESOURCE_TYPE__START_POSITION:
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
            case N2BRMPackage.GET_VIEWS_FOR_RESOURCE_TYPE__API_VERSION:
                unsetApiVersion();
                return;
            case N2BRMPackage.GET_VIEWS_FOR_RESOURCE_TYPE__NUMBER_OF_ITEMS:
                unsetNumberOfItems();
                return;
            case N2BRMPackage.GET_VIEWS_FOR_RESOURCE_TYPE__START_POSITION:
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
            case N2BRMPackage.GET_VIEWS_FOR_RESOURCE_TYPE__API_VERSION:
                return isSetApiVersion();
            case N2BRMPackage.GET_VIEWS_FOR_RESOURCE_TYPE__NUMBER_OF_ITEMS:
                return isSetNumberOfItems();
            case N2BRMPackage.GET_VIEWS_FOR_RESOURCE_TYPE__START_POSITION:
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
        result.append(" (apiVersion: ");
        if (apiVersionESet) result.append(apiVersion); else result.append("<unset>");
        result.append(", numberOfItems: ");
        if (numberOfItemsESet) result.append(numberOfItems); else result.append("<unset>");
        result.append(", startPosition: ");
        if (startPositionESet) result.append(startPosition); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //GetViewsForResourceTypeImpl
