/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.OrderFilterCriteria;
import com.tibco.n2.brm.api.ResourcesRequiredType;
import com.tibco.n2.brm.api.WorkListViewCommon;

import com.tibco.n2.common.organisation.api.XmlModelEntityId;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Work List View Common</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkListViewCommonImpl#getEntityID <em>Entity ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkListViewCommonImpl#getResourcesRequired <em>Resources Required</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkListViewCommonImpl#getOrderFilterCriteria <em>Order Filter Criteria</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkListViewCommonImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkListViewCommonImpl#isGetAllocatedItems <em>Get Allocated Items</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkListViewCommonImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkListViewCommonImpl#getOwner <em>Owner</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkListViewCommonImpl#isPublic <em>Public</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkListViewCommonImpl extends EObjectImpl implements WorkListViewCommon {
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
     * The default value of the '{@link #getResourcesRequired() <em>Resources Required</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResourcesRequired()
     * @generated
     * @ordered
     */
    protected static final ResourcesRequiredType RESOURCES_REQUIRED_EDEFAULT = ResourcesRequiredType.ALL;

    /**
     * The cached value of the '{@link #getResourcesRequired() <em>Resources Required</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResourcesRequired()
     * @generated
     * @ordered
     */
    protected ResourcesRequiredType resourcesRequired = RESOURCES_REQUIRED_EDEFAULT;

    /**
     * This is true if the Resources Required attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean resourcesRequiredESet;

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
     * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected static final String DESCRIPTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected String description = DESCRIPTION_EDEFAULT;

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
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getOwner() <em>Owner</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOwner()
     * @generated
     * @ordered
     */
    protected static final String OWNER_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getOwner() <em>Owner</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOwner()
     * @generated
     * @ordered
     */
    protected String owner = OWNER_EDEFAULT;

    /**
     * The default value of the '{@link #isPublic() <em>Public</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isPublic()
     * @generated
     * @ordered
     */
    protected static final boolean PUBLIC_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isPublic() <em>Public</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isPublic()
     * @generated
     * @ordered
     */
    protected boolean public_ = PUBLIC_EDEFAULT;

    /**
     * This is true if the Public attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean publicESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorkListViewCommonImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.WORK_LIST_VIEW_COMMON;
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_LIST_VIEW_COMMON__ENTITY_ID, oldEntityID, newEntityID);
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
                msgs = ((InternalEObject)entityID).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_LIST_VIEW_COMMON__ENTITY_ID, null, msgs);
            if (newEntityID != null)
                msgs = ((InternalEObject)newEntityID).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_LIST_VIEW_COMMON__ENTITY_ID, null, msgs);
            msgs = basicSetEntityID(newEntityID, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_LIST_VIEW_COMMON__ENTITY_ID, newEntityID, newEntityID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResourcesRequiredType getResourcesRequired() {
        return resourcesRequired;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setResourcesRequired(ResourcesRequiredType newResourcesRequired) {
        ResourcesRequiredType oldResourcesRequired = resourcesRequired;
        resourcesRequired = newResourcesRequired == null ? RESOURCES_REQUIRED_EDEFAULT : newResourcesRequired;
        boolean oldResourcesRequiredESet = resourcesRequiredESet;
        resourcesRequiredESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_LIST_VIEW_COMMON__RESOURCES_REQUIRED, oldResourcesRequired, resourcesRequired, !oldResourcesRequiredESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetResourcesRequired() {
        ResourcesRequiredType oldResourcesRequired = resourcesRequired;
        boolean oldResourcesRequiredESet = resourcesRequiredESet;
        resourcesRequired = RESOURCES_REQUIRED_EDEFAULT;
        resourcesRequiredESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.WORK_LIST_VIEW_COMMON__RESOURCES_REQUIRED, oldResourcesRequired, RESOURCES_REQUIRED_EDEFAULT, oldResourcesRequiredESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetResourcesRequired() {
        return resourcesRequiredESet;
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_LIST_VIEW_COMMON__ORDER_FILTER_CRITERIA, oldOrderFilterCriteria, newOrderFilterCriteria);
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
                msgs = ((InternalEObject)orderFilterCriteria).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_LIST_VIEW_COMMON__ORDER_FILTER_CRITERIA, null, msgs);
            if (newOrderFilterCriteria != null)
                msgs = ((InternalEObject)newOrderFilterCriteria).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_LIST_VIEW_COMMON__ORDER_FILTER_CRITERIA, null, msgs);
            msgs = basicSetOrderFilterCriteria(newOrderFilterCriteria, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_LIST_VIEW_COMMON__ORDER_FILTER_CRITERIA, newOrderFilterCriteria, newOrderFilterCriteria));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDescription() {
        return description;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDescription(String newDescription) {
        String oldDescription = description;
        description = newDescription;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_LIST_VIEW_COMMON__DESCRIPTION, oldDescription, description));
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
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_LIST_VIEW_COMMON__GET_ALLOCATED_ITEMS, oldGetAllocatedItems, getAllocatedItems, !oldGetAllocatedItemsESet));
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
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.WORK_LIST_VIEW_COMMON__GET_ALLOCATED_ITEMS, oldGetAllocatedItems, GET_ALLOCATED_ITEMS_EDEFAULT, oldGetAllocatedItemsESet));
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
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_LIST_VIEW_COMMON__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getOwner() {
        return owner;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOwner(String newOwner) {
        String oldOwner = owner;
        owner = newOwner;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_LIST_VIEW_COMMON__OWNER, oldOwner, owner));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isPublic() {
        return public_;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPublic(boolean newPublic) {
        boolean oldPublic = public_;
        public_ = newPublic;
        boolean oldPublicESet = publicESet;
        publicESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_LIST_VIEW_COMMON__PUBLIC, oldPublic, public_, !oldPublicESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetPublic() {
        boolean oldPublic = public_;
        boolean oldPublicESet = publicESet;
        public_ = PUBLIC_EDEFAULT;
        publicESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.WORK_LIST_VIEW_COMMON__PUBLIC, oldPublic, PUBLIC_EDEFAULT, oldPublicESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetPublic() {
        return publicESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__ENTITY_ID:
                return basicSetEntityID(null, msgs);
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__ORDER_FILTER_CRITERIA:
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
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__ENTITY_ID:
                return getEntityID();
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__RESOURCES_REQUIRED:
                return getResourcesRequired();
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__ORDER_FILTER_CRITERIA:
                return getOrderFilterCriteria();
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__DESCRIPTION:
                return getDescription();
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__GET_ALLOCATED_ITEMS:
                return isGetAllocatedItems();
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__NAME:
                return getName();
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__OWNER:
                return getOwner();
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__PUBLIC:
                return isPublic();
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
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__ENTITY_ID:
                setEntityID((XmlModelEntityId)newValue);
                return;
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__RESOURCES_REQUIRED:
                setResourcesRequired((ResourcesRequiredType)newValue);
                return;
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__ORDER_FILTER_CRITERIA:
                setOrderFilterCriteria((OrderFilterCriteria)newValue);
                return;
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__DESCRIPTION:
                setDescription((String)newValue);
                return;
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__GET_ALLOCATED_ITEMS:
                setGetAllocatedItems((Boolean)newValue);
                return;
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__NAME:
                setName((String)newValue);
                return;
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__OWNER:
                setOwner((String)newValue);
                return;
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__PUBLIC:
                setPublic((Boolean)newValue);
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
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__ENTITY_ID:
                setEntityID((XmlModelEntityId)null);
                return;
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__RESOURCES_REQUIRED:
                unsetResourcesRequired();
                return;
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__ORDER_FILTER_CRITERIA:
                setOrderFilterCriteria((OrderFilterCriteria)null);
                return;
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__DESCRIPTION:
                setDescription(DESCRIPTION_EDEFAULT);
                return;
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__GET_ALLOCATED_ITEMS:
                unsetGetAllocatedItems();
                return;
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__NAME:
                setName(NAME_EDEFAULT);
                return;
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__OWNER:
                setOwner(OWNER_EDEFAULT);
                return;
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__PUBLIC:
                unsetPublic();
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
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__ENTITY_ID:
                return entityID != null;
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__RESOURCES_REQUIRED:
                return isSetResourcesRequired();
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__ORDER_FILTER_CRITERIA:
                return orderFilterCriteria != null;
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__DESCRIPTION:
                return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__GET_ALLOCATED_ITEMS:
                return isSetGetAllocatedItems();
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__OWNER:
                return OWNER_EDEFAULT == null ? owner != null : !OWNER_EDEFAULT.equals(owner);
            case N2BRMPackage.WORK_LIST_VIEW_COMMON__PUBLIC:
                return isSetPublic();
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
        result.append(" (resourcesRequired: ");
        if (resourcesRequiredESet) result.append(resourcesRequired); else result.append("<unset>");
        result.append(", description: ");
        result.append(description);
        result.append(", getAllocatedItems: ");
        if (getAllocatedItemsESet) result.append(getAllocatedItems); else result.append("<unset>");
        result.append(", name: ");
        result.append(name);
        result.append(", owner: ");
        result.append(owner);
        result.append(", public: ");
        if (publicESet) result.append(public_); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //WorkListViewCommonImpl
