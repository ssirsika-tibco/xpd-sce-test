/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.ManagedObjectID;
import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.PushNotificationType;

import com.tibco.n2.common.datamodel.WorkTypeSpec;

import com.tibco.n2.common.organisation.api.XmlModelEntityId;

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

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Push Notification Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.PushNotificationTypeImpl#getWorkItemID <em>Work Item ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.PushNotificationTypeImpl#getWorkTypeID <em>Work Type ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.PushNotificationTypeImpl#getResourceIDs <em>Resource IDs</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PushNotificationTypeImpl extends EObjectImpl implements PushNotificationType {
    /**
     * The cached value of the '{@link #getWorkItemID() <em>Work Item ID</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkItemID()
     * @generated
     * @ordered
     */
    protected ManagedObjectID workItemID;

    /**
     * The cached value of the '{@link #getWorkTypeID() <em>Work Type ID</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkTypeID()
     * @generated
     * @ordered
     */
    protected WorkTypeSpec workTypeID;

    /**
     * The cached value of the '{@link #getResourceIDs() <em>Resource IDs</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResourceIDs()
     * @generated
     * @ordered
     */
    protected EList<XmlModelEntityId> resourceIDs;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PushNotificationTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.PUSH_NOTIFICATION_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ManagedObjectID getWorkItemID() {
        return workItemID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWorkItemID(ManagedObjectID newWorkItemID, NotificationChain msgs) {
        ManagedObjectID oldWorkItemID = workItemID;
        workItemID = newWorkItemID;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.PUSH_NOTIFICATION_TYPE__WORK_ITEM_ID, oldWorkItemID, newWorkItemID);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkItemID(ManagedObjectID newWorkItemID) {
        if (newWorkItemID != workItemID) {
            NotificationChain msgs = null;
            if (workItemID != null)
                msgs = ((InternalEObject)workItemID).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.PUSH_NOTIFICATION_TYPE__WORK_ITEM_ID, null, msgs);
            if (newWorkItemID != null)
                msgs = ((InternalEObject)newWorkItemID).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.PUSH_NOTIFICATION_TYPE__WORK_ITEM_ID, null, msgs);
            msgs = basicSetWorkItemID(newWorkItemID, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.PUSH_NOTIFICATION_TYPE__WORK_ITEM_ID, newWorkItemID, newWorkItemID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkTypeSpec getWorkTypeID() {
        return workTypeID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWorkTypeID(WorkTypeSpec newWorkTypeID, NotificationChain msgs) {
        WorkTypeSpec oldWorkTypeID = workTypeID;
        workTypeID = newWorkTypeID;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.PUSH_NOTIFICATION_TYPE__WORK_TYPE_ID, oldWorkTypeID, newWorkTypeID);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkTypeID(WorkTypeSpec newWorkTypeID) {
        if (newWorkTypeID != workTypeID) {
            NotificationChain msgs = null;
            if (workTypeID != null)
                msgs = ((InternalEObject)workTypeID).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.PUSH_NOTIFICATION_TYPE__WORK_TYPE_ID, null, msgs);
            if (newWorkTypeID != null)
                msgs = ((InternalEObject)newWorkTypeID).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.PUSH_NOTIFICATION_TYPE__WORK_TYPE_ID, null, msgs);
            msgs = basicSetWorkTypeID(newWorkTypeID, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.PUSH_NOTIFICATION_TYPE__WORK_TYPE_ID, newWorkTypeID, newWorkTypeID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<XmlModelEntityId> getResourceIDs() {
        if (resourceIDs == null) {
            resourceIDs = new EObjectContainmentEList<XmlModelEntityId>(XmlModelEntityId.class, this, N2BRMPackage.PUSH_NOTIFICATION_TYPE__RESOURCE_IDS);
        }
        return resourceIDs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.PUSH_NOTIFICATION_TYPE__WORK_ITEM_ID:
                return basicSetWorkItemID(null, msgs);
            case N2BRMPackage.PUSH_NOTIFICATION_TYPE__WORK_TYPE_ID:
                return basicSetWorkTypeID(null, msgs);
            case N2BRMPackage.PUSH_NOTIFICATION_TYPE__RESOURCE_IDS:
                return ((InternalEList<?>)getResourceIDs()).basicRemove(otherEnd, msgs);
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
            case N2BRMPackage.PUSH_NOTIFICATION_TYPE__WORK_ITEM_ID:
                return getWorkItemID();
            case N2BRMPackage.PUSH_NOTIFICATION_TYPE__WORK_TYPE_ID:
                return getWorkTypeID();
            case N2BRMPackage.PUSH_NOTIFICATION_TYPE__RESOURCE_IDS:
                return getResourceIDs();
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
            case N2BRMPackage.PUSH_NOTIFICATION_TYPE__WORK_ITEM_ID:
                setWorkItemID((ManagedObjectID)newValue);
                return;
            case N2BRMPackage.PUSH_NOTIFICATION_TYPE__WORK_TYPE_ID:
                setWorkTypeID((WorkTypeSpec)newValue);
                return;
            case N2BRMPackage.PUSH_NOTIFICATION_TYPE__RESOURCE_IDS:
                getResourceIDs().clear();
                getResourceIDs().addAll((Collection<? extends XmlModelEntityId>)newValue);
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
            case N2BRMPackage.PUSH_NOTIFICATION_TYPE__WORK_ITEM_ID:
                setWorkItemID((ManagedObjectID)null);
                return;
            case N2BRMPackage.PUSH_NOTIFICATION_TYPE__WORK_TYPE_ID:
                setWorkTypeID((WorkTypeSpec)null);
                return;
            case N2BRMPackage.PUSH_NOTIFICATION_TYPE__RESOURCE_IDS:
                getResourceIDs().clear();
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
            case N2BRMPackage.PUSH_NOTIFICATION_TYPE__WORK_ITEM_ID:
                return workItemID != null;
            case N2BRMPackage.PUSH_NOTIFICATION_TYPE__WORK_TYPE_ID:
                return workTypeID != null;
            case N2BRMPackage.PUSH_NOTIFICATION_TYPE__RESOURCE_IDS:
                return resourceIDs != null && !resourceIDs.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //PushNotificationTypeImpl
