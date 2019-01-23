/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.Item;
import com.tibco.n2.brm.api.N2BRMPackage;

import com.tibco.n2.common.organisation.api.XmlParticipantId;
import com.tibco.n2.common.organisation.api.XmlResourceQuery;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.ItemImpl#getEntities <em>Entities</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ItemImpl#getEntityQuery <em>Entity Query</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ItemImpl#getWorkTypeUID <em>Work Type UID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ItemImpl#getWorkTypeVersion <em>Work Type Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ItemImpl extends BaseItemInfoImpl implements Item {
    /**
     * The cached value of the '{@link #getEntities() <em>Entities</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEntities()
     * @generated
     * @ordered
     */
    protected EList<XmlParticipantId> entities;

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
     * The default value of the '{@link #getWorkTypeUID() <em>Work Type UID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkTypeUID()
     * @generated
     * @ordered
     */
    protected static final String WORK_TYPE_UID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getWorkTypeUID() <em>Work Type UID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkTypeUID()
     * @generated
     * @ordered
     */
    protected String workTypeUID = WORK_TYPE_UID_EDEFAULT;

    /**
     * The default value of the '{@link #getWorkTypeVersion() <em>Work Type Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkTypeVersion()
     * @generated
     * @ordered
     */
    protected static final String WORK_TYPE_VERSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getWorkTypeVersion() <em>Work Type Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkTypeVersion()
     * @generated
     * @ordered
     */
    protected String workTypeVersion = WORK_TYPE_VERSION_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ItemImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.ITEM;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<XmlParticipantId> getEntities() {
        if (entities == null) {
            entities = new EObjectContainmentEList<XmlParticipantId>(XmlParticipantId.class, this, N2BRMPackage.ITEM__ENTITIES);
        }
        return entities;
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.ITEM__ENTITY_QUERY, oldEntityQuery, newEntityQuery);
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
                msgs = ((InternalEObject)entityQuery).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ITEM__ENTITY_QUERY, null, msgs);
            if (newEntityQuery != null)
                msgs = ((InternalEObject)newEntityQuery).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ITEM__ENTITY_QUERY, null, msgs);
            msgs = basicSetEntityQuery(newEntityQuery, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ITEM__ENTITY_QUERY, newEntityQuery, newEntityQuery));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getWorkTypeUID() {
        return workTypeUID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkTypeUID(String newWorkTypeUID) {
        String oldWorkTypeUID = workTypeUID;
        workTypeUID = newWorkTypeUID;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ITEM__WORK_TYPE_UID, oldWorkTypeUID, workTypeUID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getWorkTypeVersion() {
        return workTypeVersion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkTypeVersion(String newWorkTypeVersion) {
        String oldWorkTypeVersion = workTypeVersion;
        workTypeVersion = newWorkTypeVersion;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ITEM__WORK_TYPE_VERSION, oldWorkTypeVersion, workTypeVersion));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.ITEM__ENTITIES:
                return ((InternalEList<?>)getEntities()).basicRemove(otherEnd, msgs);
            case N2BRMPackage.ITEM__ENTITY_QUERY:
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
            case N2BRMPackage.ITEM__ENTITIES:
                return getEntities();
            case N2BRMPackage.ITEM__ENTITY_QUERY:
                return getEntityQuery();
            case N2BRMPackage.ITEM__WORK_TYPE_UID:
                return getWorkTypeUID();
            case N2BRMPackage.ITEM__WORK_TYPE_VERSION:
                return getWorkTypeVersion();
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
            case N2BRMPackage.ITEM__ENTITIES:
                getEntities().clear();
                getEntities().addAll((Collection<? extends XmlParticipantId>)newValue);
                return;
            case N2BRMPackage.ITEM__ENTITY_QUERY:
                setEntityQuery((XmlResourceQuery)newValue);
                return;
            case N2BRMPackage.ITEM__WORK_TYPE_UID:
                setWorkTypeUID((String)newValue);
                return;
            case N2BRMPackage.ITEM__WORK_TYPE_VERSION:
                setWorkTypeVersion((String)newValue);
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
            case N2BRMPackage.ITEM__ENTITIES:
                getEntities().clear();
                return;
            case N2BRMPackage.ITEM__ENTITY_QUERY:
                setEntityQuery((XmlResourceQuery)null);
                return;
            case N2BRMPackage.ITEM__WORK_TYPE_UID:
                setWorkTypeUID(WORK_TYPE_UID_EDEFAULT);
                return;
            case N2BRMPackage.ITEM__WORK_TYPE_VERSION:
                setWorkTypeVersion(WORK_TYPE_VERSION_EDEFAULT);
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
            case N2BRMPackage.ITEM__ENTITIES:
                return entities != null && !entities.isEmpty();
            case N2BRMPackage.ITEM__ENTITY_QUERY:
                return entityQuery != null;
            case N2BRMPackage.ITEM__WORK_TYPE_UID:
                return WORK_TYPE_UID_EDEFAULT == null ? workTypeUID != null : !WORK_TYPE_UID_EDEFAULT.equals(workTypeUID);
            case N2BRMPackage.ITEM__WORK_TYPE_VERSION:
                return WORK_TYPE_VERSION_EDEFAULT == null ? workTypeVersion != null : !WORK_TYPE_VERSION_EDEFAULT.equals(workTypeVersion);
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
        result.append(" (workTypeUID: ");
        result.append(workTypeUID);
        result.append(", workTypeVersion: ");
        result.append(workTypeVersion);
        result.append(')');
        return result.toString();
    }

} //ItemImpl
