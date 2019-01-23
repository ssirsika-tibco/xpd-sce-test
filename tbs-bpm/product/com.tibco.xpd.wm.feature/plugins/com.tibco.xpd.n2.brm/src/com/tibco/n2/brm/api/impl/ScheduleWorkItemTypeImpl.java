/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.Item;
import com.tibco.n2.brm.api.ItemBody;
import com.tibco.n2.brm.api.ItemContext;
import com.tibco.n2.brm.api.ItemSchedule;
import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.ScheduleWorkItemType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Schedule Work Item Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.ScheduleWorkItemTypeImpl#getItem <em>Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ScheduleWorkItemTypeImpl#getItemSchedule <em>Item Schedule</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ScheduleWorkItemTypeImpl#getItemContext <em>Item Context</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ScheduleWorkItemTypeImpl#getItemBody <em>Item Body</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ScheduleWorkItemTypeImpl extends EObjectImpl implements ScheduleWorkItemType {
    /**
     * The cached value of the '{@link #getItem() <em>Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getItem()
     * @generated
     * @ordered
     */
    protected Item item;

    /**
     * The cached value of the '{@link #getItemSchedule() <em>Item Schedule</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getItemSchedule()
     * @generated
     * @ordered
     */
    protected ItemSchedule itemSchedule;

    /**
     * The cached value of the '{@link #getItemContext() <em>Item Context</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getItemContext()
     * @generated
     * @ordered
     */
    protected ItemContext itemContext;

    /**
     * The cached value of the '{@link #getItemBody() <em>Item Body</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getItemBody()
     * @generated
     * @ordered
     */
    protected ItemBody itemBody;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ScheduleWorkItemTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.SCHEDULE_WORK_ITEM_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Item getItem() {
        return item;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetItem(Item newItem, NotificationChain msgs) {
        Item oldItem = item;
        item = newItem;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM, oldItem, newItem);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setItem(Item newItem) {
        if (newItem != item) {
            NotificationChain msgs = null;
            if (item != null)
                msgs = ((InternalEObject)item).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM, null, msgs);
            if (newItem != null)
                msgs = ((InternalEObject)newItem).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM, null, msgs);
            msgs = basicSetItem(newItem, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM, newItem, newItem));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ItemSchedule getItemSchedule() {
        return itemSchedule;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetItemSchedule(ItemSchedule newItemSchedule, NotificationChain msgs) {
        ItemSchedule oldItemSchedule = itemSchedule;
        itemSchedule = newItemSchedule;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM_SCHEDULE, oldItemSchedule, newItemSchedule);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setItemSchedule(ItemSchedule newItemSchedule) {
        if (newItemSchedule != itemSchedule) {
            NotificationChain msgs = null;
            if (itemSchedule != null)
                msgs = ((InternalEObject)itemSchedule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM_SCHEDULE, null, msgs);
            if (newItemSchedule != null)
                msgs = ((InternalEObject)newItemSchedule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM_SCHEDULE, null, msgs);
            msgs = basicSetItemSchedule(newItemSchedule, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM_SCHEDULE, newItemSchedule, newItemSchedule));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ItemContext getItemContext() {
        return itemContext;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetItemContext(ItemContext newItemContext, NotificationChain msgs) {
        ItemContext oldItemContext = itemContext;
        itemContext = newItemContext;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM_CONTEXT, oldItemContext, newItemContext);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setItemContext(ItemContext newItemContext) {
        if (newItemContext != itemContext) {
            NotificationChain msgs = null;
            if (itemContext != null)
                msgs = ((InternalEObject)itemContext).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM_CONTEXT, null, msgs);
            if (newItemContext != null)
                msgs = ((InternalEObject)newItemContext).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM_CONTEXT, null, msgs);
            msgs = basicSetItemContext(newItemContext, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM_CONTEXT, newItemContext, newItemContext));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ItemBody getItemBody() {
        return itemBody;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetItemBody(ItemBody newItemBody, NotificationChain msgs) {
        ItemBody oldItemBody = itemBody;
        itemBody = newItemBody;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM_BODY, oldItemBody, newItemBody);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setItemBody(ItemBody newItemBody) {
        if (newItemBody != itemBody) {
            NotificationChain msgs = null;
            if (itemBody != null)
                msgs = ((InternalEObject)itemBody).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM_BODY, null, msgs);
            if (newItemBody != null)
                msgs = ((InternalEObject)newItemBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM_BODY, null, msgs);
            msgs = basicSetItemBody(newItemBody, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM_BODY, newItemBody, newItemBody));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM:
                return basicSetItem(null, msgs);
            case N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM_SCHEDULE:
                return basicSetItemSchedule(null, msgs);
            case N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM_CONTEXT:
                return basicSetItemContext(null, msgs);
            case N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM_BODY:
                return basicSetItemBody(null, msgs);
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
            case N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM:
                return getItem();
            case N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM_SCHEDULE:
                return getItemSchedule();
            case N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM_CONTEXT:
                return getItemContext();
            case N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM_BODY:
                return getItemBody();
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
            case N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM:
                setItem((Item)newValue);
                return;
            case N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM_SCHEDULE:
                setItemSchedule((ItemSchedule)newValue);
                return;
            case N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM_CONTEXT:
                setItemContext((ItemContext)newValue);
                return;
            case N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM_BODY:
                setItemBody((ItemBody)newValue);
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
            case N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM:
                setItem((Item)null);
                return;
            case N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM_SCHEDULE:
                setItemSchedule((ItemSchedule)null);
                return;
            case N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM_CONTEXT:
                setItemContext((ItemContext)null);
                return;
            case N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM_BODY:
                setItemBody((ItemBody)null);
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
            case N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM:
                return item != null;
            case N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM_SCHEDULE:
                return itemSchedule != null;
            case N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM_CONTEXT:
                return itemContext != null;
            case N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE__ITEM_BODY:
                return itemBody != null;
        }
        return super.eIsSet(featureID);
    }

} //ScheduleWorkItemTypeImpl
