/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.ManagedObjectID;
import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.WorkItem;
import com.tibco.n2.brm.api.WorkItemAttributes;
import com.tibco.n2.brm.api.WorkItemBody;
import com.tibco.n2.brm.api.WorkItemHeader;
import com.tibco.n2.brm.api.WorkItemState;

import com.tibco.n2.common.datamodel.WorkTypeSpec;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Work Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemImpl#getHeader <em>Header</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemImpl#getBody <em>Body</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemImpl#getWorkType <em>Work Type</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemImpl#getState <em>State</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemImpl#isVisible <em>Visible</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkItemImpl extends EObjectImpl implements WorkItem {
    /**
     * The cached value of the '{@link #getId() <em>Id</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected ManagedObjectID id;

    /**
     * The cached value of the '{@link #getHeader() <em>Header</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHeader()
     * @generated
     * @ordered
     */
    protected WorkItemHeader header;

    /**
     * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttributes()
     * @generated
     * @ordered
     */
    protected WorkItemAttributes attributes;

    /**
     * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBody()
     * @generated
     * @ordered
     */
    protected WorkItemBody body;

    /**
     * The cached value of the '{@link #getWorkType() <em>Work Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkType()
     * @generated
     * @ordered
     */
    protected WorkTypeSpec workType;

    /**
     * The default value of the '{@link #getState() <em>State</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getState()
     * @generated
     * @ordered
     */
    protected static final WorkItemState STATE_EDEFAULT = WorkItemState.CREATED;

    /**
     * The cached value of the '{@link #getState() <em>State</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getState()
     * @generated
     * @ordered
     */
    protected WorkItemState state = STATE_EDEFAULT;

    /**
     * This is true if the State attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean stateESet;

    /**
     * The default value of the '{@link #isVisible() <em>Visible</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isVisible()
     * @generated
     * @ordered
     */
    protected static final boolean VISIBLE_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isVisible() <em>Visible</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isVisible()
     * @generated
     * @ordered
     */
    protected boolean visible = VISIBLE_EDEFAULT;

    /**
     * This is true if the Visible attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean visibleESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorkItemImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.WORK_ITEM;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ManagedObjectID getId() {
        return id;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetId(ManagedObjectID newId, NotificationChain msgs) {
        ManagedObjectID oldId = id;
        id = newId;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM__ID, oldId, newId);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setId(ManagedObjectID newId) {
        if (newId != id) {
            NotificationChain msgs = null;
            if (id != null)
                msgs = ((InternalEObject)id).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_ITEM__ID, null, msgs);
            if (newId != null)
                msgs = ((InternalEObject)newId).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_ITEM__ID, null, msgs);
            msgs = basicSetId(newId, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM__ID, newId, newId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkItemHeader getHeader() {
        return header;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetHeader(WorkItemHeader newHeader, NotificationChain msgs) {
        WorkItemHeader oldHeader = header;
        header = newHeader;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM__HEADER, oldHeader, newHeader);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setHeader(WorkItemHeader newHeader) {
        if (newHeader != header) {
            NotificationChain msgs = null;
            if (header != null)
                msgs = ((InternalEObject)header).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_ITEM__HEADER, null, msgs);
            if (newHeader != null)
                msgs = ((InternalEObject)newHeader).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_ITEM__HEADER, null, msgs);
            msgs = basicSetHeader(newHeader, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM__HEADER, newHeader, newHeader));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkItemAttributes getAttributes() {
        return attributes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAttributes(WorkItemAttributes newAttributes, NotificationChain msgs) {
        WorkItemAttributes oldAttributes = attributes;
        attributes = newAttributes;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM__ATTRIBUTES, oldAttributes, newAttributes);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttributes(WorkItemAttributes newAttributes) {
        if (newAttributes != attributes) {
            NotificationChain msgs = null;
            if (attributes != null)
                msgs = ((InternalEObject)attributes).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_ITEM__ATTRIBUTES, null, msgs);
            if (newAttributes != null)
                msgs = ((InternalEObject)newAttributes).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_ITEM__ATTRIBUTES, null, msgs);
            msgs = basicSetAttributes(newAttributes, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM__ATTRIBUTES, newAttributes, newAttributes));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkItemBody getBody() {
        return body;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetBody(WorkItemBody newBody, NotificationChain msgs) {
        WorkItemBody oldBody = body;
        body = newBody;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM__BODY, oldBody, newBody);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setBody(WorkItemBody newBody) {
        if (newBody != body) {
            NotificationChain msgs = null;
            if (body != null)
                msgs = ((InternalEObject)body).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_ITEM__BODY, null, msgs);
            if (newBody != null)
                msgs = ((InternalEObject)newBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_ITEM__BODY, null, msgs);
            msgs = basicSetBody(newBody, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM__BODY, newBody, newBody));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkTypeSpec getWorkType() {
        return workType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWorkType(WorkTypeSpec newWorkType, NotificationChain msgs) {
        WorkTypeSpec oldWorkType = workType;
        workType = newWorkType;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM__WORK_TYPE, oldWorkType, newWorkType);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkType(WorkTypeSpec newWorkType) {
        if (newWorkType != workType) {
            NotificationChain msgs = null;
            if (workType != null)
                msgs = ((InternalEObject)workType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_ITEM__WORK_TYPE, null, msgs);
            if (newWorkType != null)
                msgs = ((InternalEObject)newWorkType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_ITEM__WORK_TYPE, null, msgs);
            msgs = basicSetWorkType(newWorkType, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM__WORK_TYPE, newWorkType, newWorkType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkItemState getState() {
        return state;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setState(WorkItemState newState) {
        WorkItemState oldState = state;
        state = newState == null ? STATE_EDEFAULT : newState;
        boolean oldStateESet = stateESet;
        stateESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM__STATE, oldState, state, !oldStateESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetState() {
        WorkItemState oldState = state;
        boolean oldStateESet = stateESet;
        state = STATE_EDEFAULT;
        stateESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.WORK_ITEM__STATE, oldState, STATE_EDEFAULT, oldStateESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetState() {
        return stateESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setVisible(boolean newVisible) {
        boolean oldVisible = visible;
        visible = newVisible;
        boolean oldVisibleESet = visibleESet;
        visibleESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM__VISIBLE, oldVisible, visible, !oldVisibleESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetVisible() {
        boolean oldVisible = visible;
        boolean oldVisibleESet = visibleESet;
        visible = VISIBLE_EDEFAULT;
        visibleESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.WORK_ITEM__VISIBLE, oldVisible, VISIBLE_EDEFAULT, oldVisibleESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetVisible() {
        return visibleESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.WORK_ITEM__ID:
                return basicSetId(null, msgs);
            case N2BRMPackage.WORK_ITEM__HEADER:
                return basicSetHeader(null, msgs);
            case N2BRMPackage.WORK_ITEM__ATTRIBUTES:
                return basicSetAttributes(null, msgs);
            case N2BRMPackage.WORK_ITEM__BODY:
                return basicSetBody(null, msgs);
            case N2BRMPackage.WORK_ITEM__WORK_TYPE:
                return basicSetWorkType(null, msgs);
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
            case N2BRMPackage.WORK_ITEM__ID:
                return getId();
            case N2BRMPackage.WORK_ITEM__HEADER:
                return getHeader();
            case N2BRMPackage.WORK_ITEM__ATTRIBUTES:
                return getAttributes();
            case N2BRMPackage.WORK_ITEM__BODY:
                return getBody();
            case N2BRMPackage.WORK_ITEM__WORK_TYPE:
                return getWorkType();
            case N2BRMPackage.WORK_ITEM__STATE:
                return getState();
            case N2BRMPackage.WORK_ITEM__VISIBLE:
                return isVisible();
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
            case N2BRMPackage.WORK_ITEM__ID:
                setId((ManagedObjectID)newValue);
                return;
            case N2BRMPackage.WORK_ITEM__HEADER:
                setHeader((WorkItemHeader)newValue);
                return;
            case N2BRMPackage.WORK_ITEM__ATTRIBUTES:
                setAttributes((WorkItemAttributes)newValue);
                return;
            case N2BRMPackage.WORK_ITEM__BODY:
                setBody((WorkItemBody)newValue);
                return;
            case N2BRMPackage.WORK_ITEM__WORK_TYPE:
                setWorkType((WorkTypeSpec)newValue);
                return;
            case N2BRMPackage.WORK_ITEM__STATE:
                setState((WorkItemState)newValue);
                return;
            case N2BRMPackage.WORK_ITEM__VISIBLE:
                setVisible((Boolean)newValue);
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
            case N2BRMPackage.WORK_ITEM__ID:
                setId((ManagedObjectID)null);
                return;
            case N2BRMPackage.WORK_ITEM__HEADER:
                setHeader((WorkItemHeader)null);
                return;
            case N2BRMPackage.WORK_ITEM__ATTRIBUTES:
                setAttributes((WorkItemAttributes)null);
                return;
            case N2BRMPackage.WORK_ITEM__BODY:
                setBody((WorkItemBody)null);
                return;
            case N2BRMPackage.WORK_ITEM__WORK_TYPE:
                setWorkType((WorkTypeSpec)null);
                return;
            case N2BRMPackage.WORK_ITEM__STATE:
                unsetState();
                return;
            case N2BRMPackage.WORK_ITEM__VISIBLE:
                unsetVisible();
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
            case N2BRMPackage.WORK_ITEM__ID:
                return id != null;
            case N2BRMPackage.WORK_ITEM__HEADER:
                return header != null;
            case N2BRMPackage.WORK_ITEM__ATTRIBUTES:
                return attributes != null;
            case N2BRMPackage.WORK_ITEM__BODY:
                return body != null;
            case N2BRMPackage.WORK_ITEM__WORK_TYPE:
                return workType != null;
            case N2BRMPackage.WORK_ITEM__STATE:
                return isSetState();
            case N2BRMPackage.WORK_ITEM__VISIBLE:
                return isSetVisible();
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
        result.append(" (state: ");
        if (stateESet) result.append(state); else result.append("<unset>");
        result.append(", visible: ");
        if (visibleESet) result.append(visible); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //WorkItemImpl
