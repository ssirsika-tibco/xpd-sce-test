/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.BaseItemInfo;
import com.tibco.n2.brm.api.DistributionStrategy;
import com.tibco.n2.brm.api.N2BRMPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Base Item Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.BaseItemInfoImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.BaseItemInfoImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.BaseItemInfoImpl#getDistributionStrategy <em>Distribution Strategy</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.BaseItemInfoImpl#getGroupID <em>Group ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.BaseItemInfoImpl#getPriority <em>Priority</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class BaseItemInfoImpl extends EObjectImpl implements BaseItemInfo {
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
     * The default value of the '{@link #getDistributionStrategy() <em>Distribution Strategy</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDistributionStrategy()
     * @generated
     * @ordered
     */
    protected static final DistributionStrategy DISTRIBUTION_STRATEGY_EDEFAULT = DistributionStrategy.OFFER;

    /**
     * The cached value of the '{@link #getDistributionStrategy() <em>Distribution Strategy</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDistributionStrategy()
     * @generated
     * @ordered
     */
    protected DistributionStrategy distributionStrategy = DISTRIBUTION_STRATEGY_EDEFAULT;

    /**
     * This is true if the Distribution Strategy attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean distributionStrategyESet;

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
     * The default value of the '{@link #getPriority() <em>Priority</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPriority()
     * @generated
     * @ordered
     */
    protected static final int PRIORITY_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getPriority() <em>Priority</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPriority()
     * @generated
     * @ordered
     */
    protected int priority = PRIORITY_EDEFAULT;

    /**
     * This is true if the Priority attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean priorityESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected BaseItemInfoImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.BASE_ITEM_INFO;
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
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.BASE_ITEM_INFO__NAME, oldName, name));
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
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.BASE_ITEM_INFO__DESCRIPTION, oldDescription, description));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DistributionStrategy getDistributionStrategy() {
        return distributionStrategy;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDistributionStrategy(DistributionStrategy newDistributionStrategy) {
        DistributionStrategy oldDistributionStrategy = distributionStrategy;
        distributionStrategy = newDistributionStrategy == null ? DISTRIBUTION_STRATEGY_EDEFAULT : newDistributionStrategy;
        boolean oldDistributionStrategyESet = distributionStrategyESet;
        distributionStrategyESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.BASE_ITEM_INFO__DISTRIBUTION_STRATEGY, oldDistributionStrategy, distributionStrategy, !oldDistributionStrategyESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetDistributionStrategy() {
        DistributionStrategy oldDistributionStrategy = distributionStrategy;
        boolean oldDistributionStrategyESet = distributionStrategyESet;
        distributionStrategy = DISTRIBUTION_STRATEGY_EDEFAULT;
        distributionStrategyESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.BASE_ITEM_INFO__DISTRIBUTION_STRATEGY, oldDistributionStrategy, DISTRIBUTION_STRATEGY_EDEFAULT, oldDistributionStrategyESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetDistributionStrategy() {
        return distributionStrategyESet;
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
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.BASE_ITEM_INFO__GROUP_ID, oldGroupID, groupID, !oldGroupIDESet));
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
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.BASE_ITEM_INFO__GROUP_ID, oldGroupID, GROUP_ID_EDEFAULT, oldGroupIDESet));
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
    public int getPriority() {
        return priority;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPriority(int newPriority) {
        int oldPriority = priority;
        priority = newPriority;
        boolean oldPriorityESet = priorityESet;
        priorityESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.BASE_ITEM_INFO__PRIORITY, oldPriority, priority, !oldPriorityESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetPriority() {
        int oldPriority = priority;
        boolean oldPriorityESet = priorityESet;
        priority = PRIORITY_EDEFAULT;
        priorityESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.BASE_ITEM_INFO__PRIORITY, oldPriority, PRIORITY_EDEFAULT, oldPriorityESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetPriority() {
        return priorityESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case N2BRMPackage.BASE_ITEM_INFO__NAME:
                return getName();
            case N2BRMPackage.BASE_ITEM_INFO__DESCRIPTION:
                return getDescription();
            case N2BRMPackage.BASE_ITEM_INFO__DISTRIBUTION_STRATEGY:
                return getDistributionStrategy();
            case N2BRMPackage.BASE_ITEM_INFO__GROUP_ID:
                return getGroupID();
            case N2BRMPackage.BASE_ITEM_INFO__PRIORITY:
                return getPriority();
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
            case N2BRMPackage.BASE_ITEM_INFO__NAME:
                setName((String)newValue);
                return;
            case N2BRMPackage.BASE_ITEM_INFO__DESCRIPTION:
                setDescription((String)newValue);
                return;
            case N2BRMPackage.BASE_ITEM_INFO__DISTRIBUTION_STRATEGY:
                setDistributionStrategy((DistributionStrategy)newValue);
                return;
            case N2BRMPackage.BASE_ITEM_INFO__GROUP_ID:
                setGroupID((Long)newValue);
                return;
            case N2BRMPackage.BASE_ITEM_INFO__PRIORITY:
                setPriority((Integer)newValue);
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
            case N2BRMPackage.BASE_ITEM_INFO__NAME:
                setName(NAME_EDEFAULT);
                return;
            case N2BRMPackage.BASE_ITEM_INFO__DESCRIPTION:
                setDescription(DESCRIPTION_EDEFAULT);
                return;
            case N2BRMPackage.BASE_ITEM_INFO__DISTRIBUTION_STRATEGY:
                unsetDistributionStrategy();
                return;
            case N2BRMPackage.BASE_ITEM_INFO__GROUP_ID:
                unsetGroupID();
                return;
            case N2BRMPackage.BASE_ITEM_INFO__PRIORITY:
                unsetPriority();
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
            case N2BRMPackage.BASE_ITEM_INFO__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case N2BRMPackage.BASE_ITEM_INFO__DESCRIPTION:
                return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
            case N2BRMPackage.BASE_ITEM_INFO__DISTRIBUTION_STRATEGY:
                return isSetDistributionStrategy();
            case N2BRMPackage.BASE_ITEM_INFO__GROUP_ID:
                return isSetGroupID();
            case N2BRMPackage.BASE_ITEM_INFO__PRIORITY:
                return isSetPriority();
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
        result.append(" (name: ");
        result.append(name);
        result.append(", description: ");
        result.append(description);
        result.append(", distributionStrategy: ");
        if (distributionStrategyESet) result.append(distributionStrategy); else result.append("<unset>");
        result.append(", groupID: ");
        if (groupIDESet) result.append(groupID); else result.append("<unset>");
        result.append(", priority: ");
        if (priorityESet) result.append(priority); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //BaseItemInfoImpl
