/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.ItemBody;
import com.tibco.n2.brm.api.ItemContext;
import com.tibco.n2.brm.api.ItemSchedule;
import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.ScheduleWorkItemWithModelType;

import com.tibco.n2.common.organisation.api.XmlResourceQuery;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Schedule Work Item With Model Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.ScheduleWorkItemWithModelTypeImpl#getItemSchedule <em>Item Schedule</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ScheduleWorkItemWithModelTypeImpl#getItemContext <em>Item Context</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ScheduleWorkItemWithModelTypeImpl#getItemBody <em>Item Body</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ScheduleWorkItemWithModelTypeImpl#getEntityQuery <em>Entity Query</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ScheduleWorkItemWithModelTypeImpl#getGroupID <em>Group ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ScheduleWorkItemWithModelTypeImpl#getWorkModelUID <em>Work Model UID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ScheduleWorkItemWithModelTypeImpl#getWorkModelVersion <em>Work Model Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ScheduleWorkItemWithModelTypeImpl extends EObjectImpl implements ScheduleWorkItemWithModelType {
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
     * The cached value of the '{@link #getEntityQuery() <em>Entity Query</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEntityQuery()
     * @generated
     * @ordered
     */
    protected XmlResourceQuery entityQuery;

    /**
     * The default value of the '{@link #getGroupID() <em>Group ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGroupID()
     * @generated
     * @ordered
     */
    protected static final long GROUP_ID_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getGroupID() <em>Group ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGroupID()
     * @generated
     * @ordered
     */
    protected long groupID = GROUP_ID_EDEFAULT;

    /**
     * This is true if the Group ID attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean groupIDESet;

    /**
     * The default value of the '{@link #getWorkModelUID() <em>Work Model UID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkModelUID()
     * @generated
     * @ordered
     */
    protected static final String WORK_MODEL_UID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getWorkModelUID() <em>Work Model UID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkModelUID()
     * @generated
     * @ordered
     */
    protected String workModelUID = WORK_MODEL_UID_EDEFAULT;

    /**
     * The default value of the '{@link #getWorkModelVersion() <em>Work Model Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkModelVersion()
     * @generated
     * @ordered
     */
    protected static final String WORK_MODEL_VERSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getWorkModelVersion() <em>Work Model Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkModelVersion()
     * @generated
     * @ordered
     */
    protected String workModelVersion = WORK_MODEL_VERSION_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ScheduleWorkItemWithModelTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE;
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_SCHEDULE, oldItemSchedule, newItemSchedule);
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
                msgs = ((InternalEObject)itemSchedule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_SCHEDULE, null, msgs);
            if (newItemSchedule != null)
                msgs = ((InternalEObject)newItemSchedule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_SCHEDULE, null, msgs);
            msgs = basicSetItemSchedule(newItemSchedule, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_SCHEDULE, newItemSchedule, newItemSchedule));
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_CONTEXT, oldItemContext, newItemContext);
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
                msgs = ((InternalEObject)itemContext).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_CONTEXT, null, msgs);
            if (newItemContext != null)
                msgs = ((InternalEObject)newItemContext).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_CONTEXT, null, msgs);
            msgs = basicSetItemContext(newItemContext, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_CONTEXT, newItemContext, newItemContext));
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_BODY, oldItemBody, newItemBody);
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
                msgs = ((InternalEObject)itemBody).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_BODY, null, msgs);
            if (newItemBody != null)
                msgs = ((InternalEObject)newItemBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_BODY, null, msgs);
            msgs = basicSetItemBody(newItemBody, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_BODY, newItemBody, newItemBody));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XmlResourceQuery getEntityQuery() {
        return entityQuery;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetEntityQuery(XmlResourceQuery newEntityQuery, NotificationChain msgs) {
        XmlResourceQuery oldEntityQuery = entityQuery;
        entityQuery = newEntityQuery;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ENTITY_QUERY, oldEntityQuery, newEntityQuery);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEntityQuery(XmlResourceQuery newEntityQuery) {
        if (newEntityQuery != entityQuery) {
            NotificationChain msgs = null;
            if (entityQuery != null)
                msgs = ((InternalEObject)entityQuery).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ENTITY_QUERY, null, msgs);
            if (newEntityQuery != null)
                msgs = ((InternalEObject)newEntityQuery).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ENTITY_QUERY, null, msgs);
            msgs = basicSetEntityQuery(newEntityQuery, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ENTITY_QUERY, newEntityQuery, newEntityQuery));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getGroupID() {
        return groupID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGroupID(long newGroupID) {
        long oldGroupID = groupID;
        groupID = newGroupID;
        boolean oldGroupIDESet = groupIDESet;
        groupIDESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__GROUP_ID, oldGroupID, groupID, !oldGroupIDESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetGroupID() {
        long oldGroupID = groupID;
        boolean oldGroupIDESet = groupIDESet;
        groupID = GROUP_ID_EDEFAULT;
        groupIDESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__GROUP_ID, oldGroupID, GROUP_ID_EDEFAULT, oldGroupIDESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetGroupID() {
        return groupIDESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getWorkModelUID() {
        return workModelUID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkModelUID(String newWorkModelUID) {
        String oldWorkModelUID = workModelUID;
        workModelUID = newWorkModelUID;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__WORK_MODEL_UID, oldWorkModelUID, workModelUID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getWorkModelVersion() {
        return workModelVersion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkModelVersion(String newWorkModelVersion) {
        String oldWorkModelVersion = workModelVersion;
        workModelVersion = newWorkModelVersion;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__WORK_MODEL_VERSION, oldWorkModelVersion, workModelVersion));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_SCHEDULE:
                return basicSetItemSchedule(null, msgs);
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_CONTEXT:
                return basicSetItemContext(null, msgs);
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_BODY:
                return basicSetItemBody(null, msgs);
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ENTITY_QUERY:
                return basicSetEntityQuery(null, msgs);
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
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_SCHEDULE:
                return getItemSchedule();
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_CONTEXT:
                return getItemContext();
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_BODY:
                return getItemBody();
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ENTITY_QUERY:
                return getEntityQuery();
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__GROUP_ID:
                return getGroupID();
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__WORK_MODEL_UID:
                return getWorkModelUID();
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__WORK_MODEL_VERSION:
                return getWorkModelVersion();
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
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_SCHEDULE:
                setItemSchedule((ItemSchedule)newValue);
                return;
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_CONTEXT:
                setItemContext((ItemContext)newValue);
                return;
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_BODY:
                setItemBody((ItemBody)newValue);
                return;
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ENTITY_QUERY:
                setEntityQuery((XmlResourceQuery)newValue);
                return;
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__GROUP_ID:
                setGroupID((Long)newValue);
                return;
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__WORK_MODEL_UID:
                setWorkModelUID((String)newValue);
                return;
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__WORK_MODEL_VERSION:
                setWorkModelVersion((String)newValue);
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
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_SCHEDULE:
                setItemSchedule((ItemSchedule)null);
                return;
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_CONTEXT:
                setItemContext((ItemContext)null);
                return;
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_BODY:
                setItemBody((ItemBody)null);
                return;
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ENTITY_QUERY:
                setEntityQuery((XmlResourceQuery)null);
                return;
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__GROUP_ID:
                unsetGroupID();
                return;
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__WORK_MODEL_UID:
                setWorkModelUID(WORK_MODEL_UID_EDEFAULT);
                return;
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__WORK_MODEL_VERSION:
                setWorkModelVersion(WORK_MODEL_VERSION_EDEFAULT);
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
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_SCHEDULE:
                return itemSchedule != null;
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_CONTEXT:
                return itemContext != null;
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_BODY:
                return itemBody != null;
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ENTITY_QUERY:
                return entityQuery != null;
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__GROUP_ID:
                return isSetGroupID();
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__WORK_MODEL_UID:
                return WORK_MODEL_UID_EDEFAULT == null ? workModelUID != null : !WORK_MODEL_UID_EDEFAULT.equals(workModelUID);
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__WORK_MODEL_VERSION:
                return WORK_MODEL_VERSION_EDEFAULT == null ? workModelVersion != null : !WORK_MODEL_VERSION_EDEFAULT.equals(workModelVersion);
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
        result.append(" (groupID: ");
        if (groupIDESet) result.append(groupID); else result.append("<unset>");
        result.append(", workModelUID: ");
        result.append(workModelUID);
        result.append(", workModelVersion: ");
        result.append(workModelVersion);
        result.append(')');
        return result.toString();
    }

} //ScheduleWorkItemWithModelTypeImpl
