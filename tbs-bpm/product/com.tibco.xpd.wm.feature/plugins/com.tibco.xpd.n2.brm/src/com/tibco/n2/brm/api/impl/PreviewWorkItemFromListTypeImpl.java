/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.ObjectID;
import com.tibco.n2.brm.api.PreviewWorkItemFromListType;

import com.tibco.n2.common.organisation.api.XmlModelEntityId;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Preview Work Item From List Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.PreviewWorkItemFromListTypeImpl#getEntityID <em>Entity ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.PreviewWorkItemFromListTypeImpl#getWorkItemID <em>Work Item ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.PreviewWorkItemFromListTypeImpl#getRequiredField <em>Required Field</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.PreviewWorkItemFromListTypeImpl#isRequireWorkType <em>Require Work Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PreviewWorkItemFromListTypeImpl extends EObjectImpl implements PreviewWorkItemFromListType {
    /**
     * The cached value of the '{@link #getEntityID() <em>Entity ID</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEntityID()
     * @generated
     * @ordered
     */
    protected XmlModelEntityId entityID;

    /**
     * The cached value of the '{@link #getWorkItemID() <em>Work Item ID</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkItemID()
     * @generated
     * @ordered
     */
    protected EList<ObjectID> workItemID;

    /**
     * The cached value of the '{@link #getRequiredField() <em>Required Field</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRequiredField()
     * @generated
     * @ordered
     */
    protected EList<String> requiredField;

    /**
     * The default value of the '{@link #isRequireWorkType() <em>Require Work Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isRequireWorkType()
     * @generated
     * @ordered
     */
    protected static final boolean REQUIRE_WORK_TYPE_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isRequireWorkType() <em>Require Work Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isRequireWorkType()
     * @generated
     * @ordered
     */
    protected boolean requireWorkType = REQUIRE_WORK_TYPE_EDEFAULT;

    /**
     * This is true if the Require Work Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean requireWorkTypeESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PreviewWorkItemFromListTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.PREVIEW_WORK_ITEM_FROM_LIST_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XmlModelEntityId getEntityID() {
        return entityID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetEntityID(XmlModelEntityId newEntityID, NotificationChain msgs) {
        XmlModelEntityId oldEntityID = entityID;
        entityID = newEntityID;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE__ENTITY_ID, oldEntityID, newEntityID);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEntityID(XmlModelEntityId newEntityID) {
        if (newEntityID != entityID) {
            NotificationChain msgs = null;
            if (entityID != null)
                msgs = ((InternalEObject)entityID).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE__ENTITY_ID, null, msgs);
            if (newEntityID != null)
                msgs = ((InternalEObject)newEntityID).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE__ENTITY_ID, null, msgs);
            msgs = basicSetEntityID(newEntityID, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE__ENTITY_ID, newEntityID, newEntityID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ObjectID> getWorkItemID() {
        if (workItemID == null) {
            workItemID = new EObjectContainmentEList<ObjectID>(ObjectID.class, this, N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE__WORK_ITEM_ID);
        }
        return workItemID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<String> getRequiredField() {
        if (requiredField == null) {
            requiredField = new EDataTypeEList<String>(String.class, this, N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE__REQUIRED_FIELD);
        }
        return requiredField;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isRequireWorkType() {
        return requireWorkType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRequireWorkType(boolean newRequireWorkType) {
        boolean oldRequireWorkType = requireWorkType;
        requireWorkType = newRequireWorkType;
        boolean oldRequireWorkTypeESet = requireWorkTypeESet;
        requireWorkTypeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE__REQUIRE_WORK_TYPE, oldRequireWorkType, requireWorkType, !oldRequireWorkTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetRequireWorkType() {
        boolean oldRequireWorkType = requireWorkType;
        boolean oldRequireWorkTypeESet = requireWorkTypeESet;
        requireWorkType = REQUIRE_WORK_TYPE_EDEFAULT;
        requireWorkTypeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE__REQUIRE_WORK_TYPE, oldRequireWorkType, REQUIRE_WORK_TYPE_EDEFAULT, oldRequireWorkTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetRequireWorkType() {
        return requireWorkTypeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE__ENTITY_ID:
                return basicSetEntityID(null, msgs);
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE__WORK_ITEM_ID:
                return ((InternalEList<?>)getWorkItemID()).basicRemove(otherEnd, msgs);
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
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE__ENTITY_ID:
                return getEntityID();
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE__WORK_ITEM_ID:
                return getWorkItemID();
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE__REQUIRED_FIELD:
                return getRequiredField();
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE__REQUIRE_WORK_TYPE:
                return isRequireWorkType();
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
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE__ENTITY_ID:
                setEntityID((XmlModelEntityId)newValue);
                return;
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE__WORK_ITEM_ID:
                getWorkItemID().clear();
                getWorkItemID().addAll((Collection<? extends ObjectID>)newValue);
                return;
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE__REQUIRED_FIELD:
                getRequiredField().clear();
                getRequiredField().addAll((Collection<? extends String>)newValue);
                return;
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE__REQUIRE_WORK_TYPE:
                setRequireWorkType((Boolean)newValue);
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
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE__ENTITY_ID:
                setEntityID((XmlModelEntityId)null);
                return;
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE__WORK_ITEM_ID:
                getWorkItemID().clear();
                return;
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE__REQUIRED_FIELD:
                getRequiredField().clear();
                return;
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE__REQUIRE_WORK_TYPE:
                unsetRequireWorkType();
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
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE__ENTITY_ID:
                return entityID != null;
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE__WORK_ITEM_ID:
                return workItemID != null && !workItemID.isEmpty();
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE__REQUIRED_FIELD:
                return requiredField != null && !requiredField.isEmpty();
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE__REQUIRE_WORK_TYPE:
                return isSetRequireWorkType();
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
        result.append(" (requiredField: ");
        result.append(requiredField);
        result.append(", requireWorkType: ");
        if (requireWorkTypeESet) result.append(requireWorkType); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //PreviewWorkItemFromListTypeImpl
