/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.impl;

import java.util.Collection;
import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.om.core.om.Allocable;
import com.tibco.xpd.om.core.om.AssociableWithPrivileges;
import com.tibco.xpd.om.core.om.AssociableWithResources;
import com.tibco.xpd.om.core.om.Authorizable;
import com.tibco.xpd.om.core.om.CapabilityAssociation;
import com.tibco.xpd.om.core.om.Capable;
import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.PrivilegeAssociation;
import com.tibco.xpd.om.core.om.ResourceAssociation;
import com.tibco.xpd.om.core.om.SystemAction;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.GroupImpl#getPurpose <em>Purpose</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.GroupImpl#getStartDate <em>Start Date</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.GroupImpl#getEndDate <em>End Date</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.GroupImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.GroupImpl#getCapabilitiesAssociations <em>Capabilities Associations</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.GroupImpl#getAllocationMethod <em>Allocation Method</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.GroupImpl#getPrivilegeAssociations <em>Privilege Associations</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.GroupImpl#getSystemActions <em>System Actions</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.GroupImpl#getResourceAssociation <em>Resource Association</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.GroupImpl#getSubGroups <em>Sub Groups</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GroupImpl extends NamedElementImpl implements Group {
    /**
     * The default value of the '{@link #getPurpose() <em>Purpose</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPurpose()
     * @generated
     * @ordered
     */
    protected static final String PURPOSE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPurpose() <em>Purpose</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPurpose()
     * @generated
     * @ordered
     */
    protected String purpose = PURPOSE_EDEFAULT;

    /**
     * The default value of the '{@link #getStartDate() <em>Start Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartDate()
     * @generated
     * @ordered
     */
    protected static final Date START_DATE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getStartDate() <em>Start Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartDate()
     * @generated
     * @ordered
     */
    protected Date startDate = START_DATE_EDEFAULT;

    /**
     * The default value of the '{@link #getEndDate() <em>End Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEndDate()
     * @generated
     * @ordered
     */
    protected static final Date END_DATE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getEndDate() <em>End Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEndDate()
     * @generated
     * @ordered
     */
    protected Date endDate = END_DATE_EDEFAULT;

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
     * The cached value of the '{@link #getCapabilitiesAssociations() <em>Capabilities Associations</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCapabilitiesAssociations()
     * @generated
     * @ordered
     */
    protected EList<CapabilityAssociation> capabilitiesAssociations;

    /**
     * The default value of the '{@link #getAllocationMethod() <em>Allocation Method</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAllocationMethod()
     * @generated
     * @ordered
     */
    protected static final String ALLOCATION_METHOD_EDEFAULT = "ANY"; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getAllocationMethod() <em>Allocation Method</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAllocationMethod()
     * @generated
     * @ordered
     */
    protected String allocationMethod = ALLOCATION_METHOD_EDEFAULT;

    /**
     * This is true if the Allocation Method attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean allocationMethodESet;

    /**
     * The cached value of the '{@link #getPrivilegeAssociations() <em>Privilege Associations</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPrivilegeAssociations()
     * @generated
     * @ordered
     */
    protected EList<PrivilegeAssociation> privilegeAssociations;

    /**
     * The cached value of the '{@link #getSystemActions() <em>System Actions</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSystemActions()
     * @generated
     * @ordered
     */
    protected EList<SystemAction> systemActions;

    /**
     * The cached value of the '{@link #getResourceAssociation() <em>Resource Association</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResourceAssociation()
     * @generated
     * @ordered
     */
    protected EList<ResourceAssociation> resourceAssociation;

    /**
     * The cached value of the '{@link #getSubGroups() <em>Sub Groups</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSubGroups()
     * @generated
     * @ordered
     */
    protected EList<Group> subGroups;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected GroupImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OMPackage.Literals.GROUP;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPurpose(String newPurpose) {
        String oldPurpose = purpose;
        purpose = newPurpose;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.GROUP__PURPOSE, oldPurpose, purpose));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setStartDate(Date newStartDate) {
        Date oldStartDate = startDate;
        startDate = newStartDate;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.GROUP__START_DATE, oldStartDate, startDate));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEndDate(Date newEndDate) {
        Date oldEndDate = endDate;
        endDate = newEndDate;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.GROUP__END_DATE, oldEndDate, endDate));
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
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.GROUP__DESCRIPTION, oldDescription, description));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<CapabilityAssociation> getCapabilitiesAssociations() {
        if (capabilitiesAssociations == null) {
            capabilitiesAssociations =
                    new EObjectContainmentEList<CapabilityAssociation>(
                            CapabilityAssociation.class, this,
                            OMPackage.GROUP__CAPABILITIES_ASSOCIATIONS);
        }
        return capabilitiesAssociations;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAllocationMethod() {
        return allocationMethod;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAllocationMethod(String newAllocationMethod) {
        String oldAllocationMethod = allocationMethod;
        allocationMethod = newAllocationMethod;
        boolean oldAllocationMethodESet = allocationMethodESet;
        allocationMethodESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.GROUP__ALLOCATION_METHOD, oldAllocationMethod,
                    allocationMethod, !oldAllocationMethodESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetAllocationMethod() {
        String oldAllocationMethod = allocationMethod;
        boolean oldAllocationMethodESet = allocationMethodESet;
        allocationMethod = ALLOCATION_METHOD_EDEFAULT;
        allocationMethodESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    OMPackage.GROUP__ALLOCATION_METHOD, oldAllocationMethod,
                    ALLOCATION_METHOD_EDEFAULT, oldAllocationMethodESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetAllocationMethod() {
        return allocationMethodESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<PrivilegeAssociation> getPrivilegeAssociations() {
        if (privilegeAssociations == null) {
            privilegeAssociations =
                    new EObjectContainmentEList<PrivilegeAssociation>(
                            PrivilegeAssociation.class, this,
                            OMPackage.GROUP__PRIVILEGE_ASSOCIATIONS);
        }
        return privilegeAssociations;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<SystemAction> getSystemActions() {
        if (systemActions == null) {
            systemActions =
                    new EObjectContainmentEList<SystemAction>(
                            SystemAction.class, this,
                            OMPackage.GROUP__SYSTEM_ACTIONS);
        }
        return systemActions;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ResourceAssociation> getResourceAssociation() {
        if (resourceAssociation == null) {
            resourceAssociation =
                    new EObjectContainmentEList<ResourceAssociation>(
                            ResourceAssociation.class, this,
                            OMPackage.GROUP__RESOURCE_ASSOCIATION);
        }
        return resourceAssociation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Group> getSubGroups() {
        if (subGroups == null) {
            subGroups =
                    new EObjectContainmentEList<Group>(Group.class, this,
                            OMPackage.GROUP__SUB_GROUPS);
        }
        return subGroups;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case OMPackage.GROUP__CAPABILITIES_ASSOCIATIONS:
            return ((InternalEList<?>) getCapabilitiesAssociations())
                    .basicRemove(otherEnd, msgs);
        case OMPackage.GROUP__PRIVILEGE_ASSOCIATIONS:
            return ((InternalEList<?>) getPrivilegeAssociations())
                    .basicRemove(otherEnd, msgs);
        case OMPackage.GROUP__SYSTEM_ACTIONS:
            return ((InternalEList<?>) getSystemActions())
                    .basicRemove(otherEnd, msgs);
        case OMPackage.GROUP__RESOURCE_ASSOCIATION:
            return ((InternalEList<?>) getResourceAssociation())
                    .basicRemove(otherEnd, msgs);
        case OMPackage.GROUP__SUB_GROUPS:
            return ((InternalEList<?>) getSubGroups()).basicRemove(otherEnd,
                    msgs);
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
        case OMPackage.GROUP__PURPOSE:
            return getPurpose();
        case OMPackage.GROUP__START_DATE:
            return getStartDate();
        case OMPackage.GROUP__END_DATE:
            return getEndDate();
        case OMPackage.GROUP__DESCRIPTION:
            return getDescription();
        case OMPackage.GROUP__CAPABILITIES_ASSOCIATIONS:
            return getCapabilitiesAssociations();
        case OMPackage.GROUP__ALLOCATION_METHOD:
            return getAllocationMethod();
        case OMPackage.GROUP__PRIVILEGE_ASSOCIATIONS:
            return getPrivilegeAssociations();
        case OMPackage.GROUP__SYSTEM_ACTIONS:
            return getSystemActions();
        case OMPackage.GROUP__RESOURCE_ASSOCIATION:
            return getResourceAssociation();
        case OMPackage.GROUP__SUB_GROUPS:
            return getSubGroups();
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
        case OMPackage.GROUP__PURPOSE:
            setPurpose((String) newValue);
            return;
        case OMPackage.GROUP__START_DATE:
            setStartDate((Date) newValue);
            return;
        case OMPackage.GROUP__END_DATE:
            setEndDate((Date) newValue);
            return;
        case OMPackage.GROUP__DESCRIPTION:
            setDescription((String) newValue);
            return;
        case OMPackage.GROUP__CAPABILITIES_ASSOCIATIONS:
            getCapabilitiesAssociations().clear();
            getCapabilitiesAssociations()
                    .addAll((Collection<? extends CapabilityAssociation>) newValue);
            return;
        case OMPackage.GROUP__ALLOCATION_METHOD:
            setAllocationMethod((String) newValue);
            return;
        case OMPackage.GROUP__PRIVILEGE_ASSOCIATIONS:
            getPrivilegeAssociations().clear();
            getPrivilegeAssociations()
                    .addAll((Collection<? extends PrivilegeAssociation>) newValue);
            return;
        case OMPackage.GROUP__SYSTEM_ACTIONS:
            getSystemActions().clear();
            getSystemActions()
                    .addAll((Collection<? extends SystemAction>) newValue);
            return;
        case OMPackage.GROUP__RESOURCE_ASSOCIATION:
            getResourceAssociation().clear();
            getResourceAssociation()
                    .addAll((Collection<? extends ResourceAssociation>) newValue);
            return;
        case OMPackage.GROUP__SUB_GROUPS:
            getSubGroups().clear();
            getSubGroups().addAll((Collection<? extends Group>) newValue);
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
        case OMPackage.GROUP__PURPOSE:
            setPurpose(PURPOSE_EDEFAULT);
            return;
        case OMPackage.GROUP__START_DATE:
            setStartDate(START_DATE_EDEFAULT);
            return;
        case OMPackage.GROUP__END_DATE:
            setEndDate(END_DATE_EDEFAULT);
            return;
        case OMPackage.GROUP__DESCRIPTION:
            setDescription(DESCRIPTION_EDEFAULT);
            return;
        case OMPackage.GROUP__CAPABILITIES_ASSOCIATIONS:
            getCapabilitiesAssociations().clear();
            return;
        case OMPackage.GROUP__ALLOCATION_METHOD:
            unsetAllocationMethod();
            return;
        case OMPackage.GROUP__PRIVILEGE_ASSOCIATIONS:
            getPrivilegeAssociations().clear();
            return;
        case OMPackage.GROUP__SYSTEM_ACTIONS:
            getSystemActions().clear();
            return;
        case OMPackage.GROUP__RESOURCE_ASSOCIATION:
            getResourceAssociation().clear();
            return;
        case OMPackage.GROUP__SUB_GROUPS:
            getSubGroups().clear();
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
        case OMPackage.GROUP__PURPOSE:
            return PURPOSE_EDEFAULT == null ? purpose != null
                    : !PURPOSE_EDEFAULT.equals(purpose);
        case OMPackage.GROUP__START_DATE:
            return START_DATE_EDEFAULT == null ? startDate != null
                    : !START_DATE_EDEFAULT.equals(startDate);
        case OMPackage.GROUP__END_DATE:
            return END_DATE_EDEFAULT == null ? endDate != null
                    : !END_DATE_EDEFAULT.equals(endDate);
        case OMPackage.GROUP__DESCRIPTION:
            return DESCRIPTION_EDEFAULT == null ? description != null
                    : !DESCRIPTION_EDEFAULT.equals(description);
        case OMPackage.GROUP__CAPABILITIES_ASSOCIATIONS:
            return capabilitiesAssociations != null
                    && !capabilitiesAssociations.isEmpty();
        case OMPackage.GROUP__ALLOCATION_METHOD:
            return isSetAllocationMethod();
        case OMPackage.GROUP__PRIVILEGE_ASSOCIATIONS:
            return privilegeAssociations != null
                    && !privilegeAssociations.isEmpty();
        case OMPackage.GROUP__SYSTEM_ACTIONS:
            return systemActions != null && !systemActions.isEmpty();
        case OMPackage.GROUP__RESOURCE_ASSOCIATION:
            return resourceAssociation != null
                    && !resourceAssociation.isEmpty();
        case OMPackage.GROUP__SUB_GROUPS:
            return subGroups != null && !subGroups.isEmpty();
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == Capable.class) {
            switch (derivedFeatureID) {
            case OMPackage.GROUP__CAPABILITIES_ASSOCIATIONS:
                return OMPackage.CAPABLE__CAPABILITIES_ASSOCIATIONS;
            default:
                return -1;
            }
        }
        if (baseClass == Allocable.class) {
            switch (derivedFeatureID) {
            case OMPackage.GROUP__ALLOCATION_METHOD:
                return OMPackage.ALLOCABLE__ALLOCATION_METHOD;
            default:
                return -1;
            }
        }
        if (baseClass == AssociableWithPrivileges.class) {
            switch (derivedFeatureID) {
            case OMPackage.GROUP__PRIVILEGE_ASSOCIATIONS:
                return OMPackage.ASSOCIABLE_WITH_PRIVILEGES__PRIVILEGE_ASSOCIATIONS;
            default:
                return -1;
            }
        }
        if (baseClass == Authorizable.class) {
            switch (derivedFeatureID) {
            case OMPackage.GROUP__SYSTEM_ACTIONS:
                return OMPackage.AUTHORIZABLE__SYSTEM_ACTIONS;
            default:
                return -1;
            }
        }
        if (baseClass == AssociableWithResources.class) {
            switch (derivedFeatureID) {
            case OMPackage.GROUP__RESOURCE_ASSOCIATION:
                return OMPackage.ASSOCIABLE_WITH_RESOURCES__RESOURCE_ASSOCIATION;
            default:
                return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
        if (baseClass == Capable.class) {
            switch (baseFeatureID) {
            case OMPackage.CAPABLE__CAPABILITIES_ASSOCIATIONS:
                return OMPackage.GROUP__CAPABILITIES_ASSOCIATIONS;
            default:
                return -1;
            }
        }
        if (baseClass == Allocable.class) {
            switch (baseFeatureID) {
            case OMPackage.ALLOCABLE__ALLOCATION_METHOD:
                return OMPackage.GROUP__ALLOCATION_METHOD;
            default:
                return -1;
            }
        }
        if (baseClass == AssociableWithPrivileges.class) {
            switch (baseFeatureID) {
            case OMPackage.ASSOCIABLE_WITH_PRIVILEGES__PRIVILEGE_ASSOCIATIONS:
                return OMPackage.GROUP__PRIVILEGE_ASSOCIATIONS;
            default:
                return -1;
            }
        }
        if (baseClass == Authorizable.class) {
            switch (baseFeatureID) {
            case OMPackage.AUTHORIZABLE__SYSTEM_ACTIONS:
                return OMPackage.GROUP__SYSTEM_ACTIONS;
            default:
                return -1;
            }
        }
        if (baseClass == AssociableWithResources.class) {
            switch (baseFeatureID) {
            case OMPackage.ASSOCIABLE_WITH_RESOURCES__RESOURCE_ASSOCIATION:
                return OMPackage.GROUP__RESOURCE_ASSOCIATION;
            default:
                return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (purpose: "); //$NON-NLS-1$
        result.append(purpose);
        result.append(", startDate: "); //$NON-NLS-1$
        result.append(startDate);
        result.append(", endDate: "); //$NON-NLS-1$
        result.append(endDate);
        result.append(", description: "); //$NON-NLS-1$
        result.append(description);
        result.append(", allocationMethod: "); //$NON-NLS-1$
        if (allocationMethodESet)
            result.append(allocationMethod);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //GroupImpl
