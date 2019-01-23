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
import com.tibco.xpd.om.core.om.AttributeValue;
import com.tibco.xpd.om.core.om.Authorizable;
import com.tibco.xpd.om.core.om.CapabilityAssociation;
import com.tibco.xpd.om.core.om.Capable;
import com.tibco.xpd.om.core.om.Locatable;
import com.tibco.xpd.om.core.om.Location;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.OrgNode;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.PositionFeature;
import com.tibco.xpd.om.core.om.PositionType;
import com.tibco.xpd.om.core.om.PrivilegeAssociation;
import com.tibco.xpd.om.core.om.ResourceAssociation;
import com.tibco.xpd.om.core.om.SystemAction;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Position</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.PositionImpl#getPurpose <em>Purpose</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.PositionImpl#getStartDate <em>Start Date</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.PositionImpl#getEndDate <em>End Date</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.PositionImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.PositionImpl#getAttributeValues <em>Attribute Values</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.PositionImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.PositionImpl#getPrivilegeAssociations <em>Privilege Associations</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.PositionImpl#getSystemActions <em>System Actions</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.PositionImpl#getCapabilitiesAssociations <em>Capabilities Associations</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.PositionImpl#getAllocationMethod <em>Allocation Method</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.PositionImpl#getResourceAssociation <em>Resource Association</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.PositionImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.PositionImpl#getIdealNumber <em>Ideal Number</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.PositionImpl#getFeature <em>Feature</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.PositionImpl#getPositionType <em>Position Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PositionImpl extends NamedElementImpl implements Position {
    /**
     * The default value of the '{@link #getPurpose() <em>Purpose</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getPurpose()
     * @generated
     * @ordered
     */
    protected static final String PURPOSE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPurpose() <em>Purpose</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getPurpose()
     * @generated
     * @ordered
     */
    protected String purpose = PURPOSE_EDEFAULT;

    /**
     * The default value of the '{@link #getStartDate() <em>Start Date</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getStartDate()
     * @generated
     * @ordered
     */
    protected static final Date START_DATE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getStartDate() <em>Start Date</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getStartDate()
     * @generated
     * @ordered
     */
    protected Date startDate = START_DATE_EDEFAULT;

    /**
     * The default value of the '{@link #getEndDate() <em>End Date</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getEndDate()
     * @generated
     * @ordered
     */
    protected static final Date END_DATE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getEndDate() <em>End Date</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getEndDate()
     * @generated
     * @ordered
     */
    protected Date endDate = END_DATE_EDEFAULT;

    /**
     * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected static final String DESCRIPTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected String description = DESCRIPTION_EDEFAULT;

    /**
     * The cached value of the '{@link #getAttributeValues()
     * <em>Attribute Values</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getAttributeValues()
     * @generated
     * @ordered
     */
    protected EList<AttributeValue> attributeValues;

    /**
     * The cached value of the '{@link #getPrivilegeAssociations()
     * <em>Privilege Associations</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
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
     * The cached value of the '{@link #getCapabilitiesAssociations()
     * <em>Capabilities Associations</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getCapabilitiesAssociations()
     * @generated
     * @ordered
     */
    protected EList<CapabilityAssociation> capabilitiesAssociations;

    /**
     * The default value of the '{@link #getAllocationMethod() <em>Allocation Method</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getAllocationMethod()
     * @generated
     * @ordered
     */
    protected static final String ALLOCATION_METHOD_EDEFAULT = "ANY"; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getAllocationMethod() <em>Allocation Method</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getAllocationMethod()
     * @generated
     * @ordered
     */
    protected String allocationMethod = ALLOCATION_METHOD_EDEFAULT;

    /**
     * This is true if the Allocation Method attribute has been set. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected boolean allocationMethodESet;

    /**
     * The cached value of the '{@link #getResourceAssociation()
     * <em>Resource Association</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getResourceAssociation()
     * @generated
     * @ordered
     */
    protected EList<ResourceAssociation> resourceAssociation;

    /**
     * The cached value of the '{@link #getLocation() <em>Location</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getLocation()
     * @generated
     * @ordered
     */
    protected Location location;

    /**
     * The default value of the '{@link #getIdealNumber() <em>Ideal Number</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getIdealNumber()
     * @generated
     * @ordered
     */
    protected static final int IDEAL_NUMBER_EDEFAULT = 1;

    /**
     * The cached value of the '{@link #getIdealNumber() <em>Ideal Number</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getIdealNumber()
     * @generated
     * @ordered
     */
    protected int idealNumber = IDEAL_NUMBER_EDEFAULT;

    /**
     * The cached value of the '{@link #getFeature() <em>Feature</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getFeature()
     * @generated
     * @ordered
     */
    protected PositionFeature feature;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected PositionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OMPackage.Literals.POSITION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setPurpose(String newPurpose) {
        String oldPurpose = purpose;
        purpose = newPurpose;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.POSITION__PURPOSE, oldPurpose, purpose));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setStartDate(Date newStartDate) {
        Date oldStartDate = startDate;
        startDate = newStartDate;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.POSITION__START_DATE, oldStartDate, startDate));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setEndDate(Date newEndDate) {
        Date oldEndDate = endDate;
        endDate = newEndDate;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.POSITION__END_DATE, oldEndDate, endDate));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getDescription() {
        return description;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setDescription(String newDescription) {
        String oldDescription = description;
        description = newDescription;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.POSITION__DESCRIPTION, oldDescription,
                    description));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<AttributeValue> getAttributeValues() {
        if (attributeValues == null) {
            attributeValues =
                    new EObjectContainmentEList<AttributeValue>(
                            AttributeValue.class, this,
                            OMPackage.POSITION__ATTRIBUTE_VALUES);
        }
        return attributeValues;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public OrgElementType getType() {
        return getPositionType();
    }

    /**
     * <!-- begin-user-doc -->Setting type of the Position directly is
     * unsupported. Use {@link #setFeature(OrgUnitFeature)} instead. <!--
     * end-user-doc -->
     * @generated
     */
    public void setType(OrgElementType newType) {
        // TODO: implement this method to set the 'Type' reference
        // Ensure that you remove @generated or mark it @generated NOT
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Location getLocation() {
        if (location != null && location.eIsProxy()) {
            InternalEObject oldLocation = (InternalEObject) location;
            location = (Location) eResolveProxy(oldLocation);
            if (location != oldLocation) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            OMPackage.POSITION__LOCATION, oldLocation, location));
            }
        }
        return location;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Location basicGetLocation() {
        return location;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setLocation(Location newLocation) {
        Location oldLocation = location;
        location = newLocation;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.POSITION__LOCATION, oldLocation, location));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<PrivilegeAssociation> getPrivilegeAssociations() {
        if (privilegeAssociations == null) {
            privilegeAssociations =
                    new EObjectContainmentEList<PrivilegeAssociation>(
                            PrivilegeAssociation.class, this,
                            OMPackage.POSITION__PRIVILEGE_ASSOCIATIONS);
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
                            OMPackage.POSITION__SYSTEM_ACTIONS);
        }
        return systemActions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<CapabilityAssociation> getCapabilitiesAssociations() {
        if (capabilitiesAssociations == null) {
            capabilitiesAssociations =
                    new EObjectContainmentEList<CapabilityAssociation>(
                            CapabilityAssociation.class, this,
                            OMPackage.POSITION__CAPABILITIES_ASSOCIATIONS);
        }
        return capabilitiesAssociations;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getAllocationMethod() {
        return allocationMethod;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setAllocationMethod(String newAllocationMethod) {
        String oldAllocationMethod = allocationMethod;
        allocationMethod = newAllocationMethod;
        boolean oldAllocationMethodESet = allocationMethodESet;
        allocationMethodESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.POSITION__ALLOCATION_METHOD, oldAllocationMethod,
                    allocationMethod, !oldAllocationMethodESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void unsetAllocationMethod() {
        String oldAllocationMethod = allocationMethod;
        boolean oldAllocationMethodESet = allocationMethodESet;
        allocationMethod = ALLOCATION_METHOD_EDEFAULT;
        allocationMethodESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    OMPackage.POSITION__ALLOCATION_METHOD, oldAllocationMethod,
                    ALLOCATION_METHOD_EDEFAULT, oldAllocationMethodESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetAllocationMethod() {
        return allocationMethodESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<ResourceAssociation> getResourceAssociation() {
        if (resourceAssociation == null) {
            resourceAssociation =
                    new EObjectContainmentEList<ResourceAssociation>(
                            ResourceAssociation.class, this,
                            OMPackage.POSITION__RESOURCE_ASSOCIATION);
        }
        return resourceAssociation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public int getIdealNumber() {
        return idealNumber;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setIdealNumber(int newIdealNumber) {
        int oldIdealNumber = idealNumber;
        idealNumber = newIdealNumber;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.POSITION__IDEAL_NUMBER, oldIdealNumber,
                    idealNumber));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public PositionFeature getFeature() {
        if (feature != null && feature.eIsProxy()) {
            InternalEObject oldFeature = (InternalEObject) feature;
            feature = (PositionFeature) eResolveProxy(oldFeature);
            if (feature != oldFeature) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            OMPackage.POSITION__FEATURE, oldFeature, feature));
            }
        }
        return feature;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public PositionFeature basicGetFeature() {
        return feature;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setFeature(PositionFeature newFeature) {
        PositionFeature oldFeature = feature;
        feature = newFeature;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.POSITION__FEATURE, oldFeature, feature));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public PositionType getPositionType() {
        PositionFeature feature = getFeature();
        if (feature != null) {
            return feature.getFeatureType();
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case OMPackage.POSITION__ATTRIBUTE_VALUES:
            return ((InternalEList<?>) getAttributeValues())
                    .basicRemove(otherEnd, msgs);
        case OMPackage.POSITION__PRIVILEGE_ASSOCIATIONS:
            return ((InternalEList<?>) getPrivilegeAssociations())
                    .basicRemove(otherEnd, msgs);
        case OMPackage.POSITION__SYSTEM_ACTIONS:
            return ((InternalEList<?>) getSystemActions())
                    .basicRemove(otherEnd, msgs);
        case OMPackage.POSITION__CAPABILITIES_ASSOCIATIONS:
            return ((InternalEList<?>) getCapabilitiesAssociations())
                    .basicRemove(otherEnd, msgs);
        case OMPackage.POSITION__RESOURCE_ASSOCIATION:
            return ((InternalEList<?>) getResourceAssociation())
                    .basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case OMPackage.POSITION__PURPOSE:
            return getPurpose();
        case OMPackage.POSITION__START_DATE:
            return getStartDate();
        case OMPackage.POSITION__END_DATE:
            return getEndDate();
        case OMPackage.POSITION__DESCRIPTION:
            return getDescription();
        case OMPackage.POSITION__ATTRIBUTE_VALUES:
            return getAttributeValues();
        case OMPackage.POSITION__TYPE:
            return getType();
        case OMPackage.POSITION__PRIVILEGE_ASSOCIATIONS:
            return getPrivilegeAssociations();
        case OMPackage.POSITION__SYSTEM_ACTIONS:
            return getSystemActions();
        case OMPackage.POSITION__CAPABILITIES_ASSOCIATIONS:
            return getCapabilitiesAssociations();
        case OMPackage.POSITION__ALLOCATION_METHOD:
            return getAllocationMethod();
        case OMPackage.POSITION__RESOURCE_ASSOCIATION:
            return getResourceAssociation();
        case OMPackage.POSITION__LOCATION:
            if (resolve)
                return getLocation();
            return basicGetLocation();
        case OMPackage.POSITION__IDEAL_NUMBER:
            return new Integer(getIdealNumber());
        case OMPackage.POSITION__FEATURE:
            if (resolve)
                return getFeature();
            return basicGetFeature();
        case OMPackage.POSITION__POSITION_TYPE:
            return getPositionType();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case OMPackage.POSITION__PURPOSE:
            setPurpose((String) newValue);
            return;
        case OMPackage.POSITION__START_DATE:
            setStartDate((Date) newValue);
            return;
        case OMPackage.POSITION__END_DATE:
            setEndDate((Date) newValue);
            return;
        case OMPackage.POSITION__DESCRIPTION:
            setDescription((String) newValue);
            return;
        case OMPackage.POSITION__ATTRIBUTE_VALUES:
            getAttributeValues().clear();
            getAttributeValues()
                    .addAll((Collection<? extends AttributeValue>) newValue);
            return;
        case OMPackage.POSITION__TYPE:
            setType((OrgElementType) newValue);
            return;
        case OMPackage.POSITION__PRIVILEGE_ASSOCIATIONS:
            getPrivilegeAssociations().clear();
            getPrivilegeAssociations()
                    .addAll((Collection<? extends PrivilegeAssociation>) newValue);
            return;
        case OMPackage.POSITION__SYSTEM_ACTIONS:
            getSystemActions().clear();
            getSystemActions()
                    .addAll((Collection<? extends SystemAction>) newValue);
            return;
        case OMPackage.POSITION__CAPABILITIES_ASSOCIATIONS:
            getCapabilitiesAssociations().clear();
            getCapabilitiesAssociations()
                    .addAll((Collection<? extends CapabilityAssociation>) newValue);
            return;
        case OMPackage.POSITION__ALLOCATION_METHOD:
            setAllocationMethod((String) newValue);
            return;
        case OMPackage.POSITION__RESOURCE_ASSOCIATION:
            getResourceAssociation().clear();
            getResourceAssociation()
                    .addAll((Collection<? extends ResourceAssociation>) newValue);
            return;
        case OMPackage.POSITION__LOCATION:
            setLocation((Location) newValue);
            return;
        case OMPackage.POSITION__IDEAL_NUMBER:
            setIdealNumber(((Integer) newValue).intValue());
            return;
        case OMPackage.POSITION__FEATURE:
            setFeature((PositionFeature) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case OMPackage.POSITION__PURPOSE:
            setPurpose(PURPOSE_EDEFAULT);
            return;
        case OMPackage.POSITION__START_DATE:
            setStartDate(START_DATE_EDEFAULT);
            return;
        case OMPackage.POSITION__END_DATE:
            setEndDate(END_DATE_EDEFAULT);
            return;
        case OMPackage.POSITION__DESCRIPTION:
            setDescription(DESCRIPTION_EDEFAULT);
            return;
        case OMPackage.POSITION__ATTRIBUTE_VALUES:
            getAttributeValues().clear();
            return;
        case OMPackage.POSITION__TYPE:
            setType((OrgElementType) null);
            return;
        case OMPackage.POSITION__PRIVILEGE_ASSOCIATIONS:
            getPrivilegeAssociations().clear();
            return;
        case OMPackage.POSITION__SYSTEM_ACTIONS:
            getSystemActions().clear();
            return;
        case OMPackage.POSITION__CAPABILITIES_ASSOCIATIONS:
            getCapabilitiesAssociations().clear();
            return;
        case OMPackage.POSITION__ALLOCATION_METHOD:
            unsetAllocationMethod();
            return;
        case OMPackage.POSITION__RESOURCE_ASSOCIATION:
            getResourceAssociation().clear();
            return;
        case OMPackage.POSITION__LOCATION:
            setLocation((Location) null);
            return;
        case OMPackage.POSITION__IDEAL_NUMBER:
            setIdealNumber(IDEAL_NUMBER_EDEFAULT);
            return;
        case OMPackage.POSITION__FEATURE:
            setFeature((PositionFeature) null);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case OMPackage.POSITION__PURPOSE:
            return PURPOSE_EDEFAULT == null ? purpose != null
                    : !PURPOSE_EDEFAULT.equals(purpose);
        case OMPackage.POSITION__START_DATE:
            return START_DATE_EDEFAULT == null ? startDate != null
                    : !START_DATE_EDEFAULT.equals(startDate);
        case OMPackage.POSITION__END_DATE:
            return END_DATE_EDEFAULT == null ? endDate != null
                    : !END_DATE_EDEFAULT.equals(endDate);
        case OMPackage.POSITION__DESCRIPTION:
            return DESCRIPTION_EDEFAULT == null ? description != null
                    : !DESCRIPTION_EDEFAULT.equals(description);
        case OMPackage.POSITION__ATTRIBUTE_VALUES:
            return attributeValues != null && !attributeValues.isEmpty();
        case OMPackage.POSITION__TYPE:
            return getType() != null;
        case OMPackage.POSITION__PRIVILEGE_ASSOCIATIONS:
            return privilegeAssociations != null
                    && !privilegeAssociations.isEmpty();
        case OMPackage.POSITION__SYSTEM_ACTIONS:
            return systemActions != null && !systemActions.isEmpty();
        case OMPackage.POSITION__CAPABILITIES_ASSOCIATIONS:
            return capabilitiesAssociations != null
                    && !capabilitiesAssociations.isEmpty();
        case OMPackage.POSITION__ALLOCATION_METHOD:
            return isSetAllocationMethod();
        case OMPackage.POSITION__RESOURCE_ASSOCIATION:
            return resourceAssociation != null
                    && !resourceAssociation.isEmpty();
        case OMPackage.POSITION__LOCATION:
            return location != null;
        case OMPackage.POSITION__IDEAL_NUMBER:
            return idealNumber != IDEAL_NUMBER_EDEFAULT;
        case OMPackage.POSITION__FEATURE:
            return feature != null;
        case OMPackage.POSITION__POSITION_TYPE:
            return getPositionType() != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == AssociableWithPrivileges.class) {
            switch (derivedFeatureID) {
            case OMPackage.POSITION__PRIVILEGE_ASSOCIATIONS:
                return OMPackage.ASSOCIABLE_WITH_PRIVILEGES__PRIVILEGE_ASSOCIATIONS;
            default:
                return -1;
            }
        }
        if (baseClass == Authorizable.class) {
            switch (derivedFeatureID) {
            case OMPackage.POSITION__SYSTEM_ACTIONS:
                return OMPackage.AUTHORIZABLE__SYSTEM_ACTIONS;
            default:
                return -1;
            }
        }
        if (baseClass == Capable.class) {
            switch (derivedFeatureID) {
            case OMPackage.POSITION__CAPABILITIES_ASSOCIATIONS:
                return OMPackage.CAPABLE__CAPABILITIES_ASSOCIATIONS;
            default:
                return -1;
            }
        }
        if (baseClass == Allocable.class) {
            switch (derivedFeatureID) {
            case OMPackage.POSITION__ALLOCATION_METHOD:
                return OMPackage.ALLOCABLE__ALLOCATION_METHOD;
            default:
                return -1;
            }
        }
        if (baseClass == AssociableWithResources.class) {
            switch (derivedFeatureID) {
            case OMPackage.POSITION__RESOURCE_ASSOCIATION:
                return OMPackage.ASSOCIABLE_WITH_RESOURCES__RESOURCE_ASSOCIATION;
            default:
                return -1;
            }
        }
        if (baseClass == Locatable.class) {
            switch (derivedFeatureID) {
            case OMPackage.POSITION__LOCATION:
                return OMPackage.LOCATABLE__LOCATION;
            default:
                return -1;
            }
        }
        if (baseClass == OrgNode.class) {
            switch (derivedFeatureID) {
            default:
                return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
        if (baseClass == AssociableWithPrivileges.class) {
            switch (baseFeatureID) {
            case OMPackage.ASSOCIABLE_WITH_PRIVILEGES__PRIVILEGE_ASSOCIATIONS:
                return OMPackage.POSITION__PRIVILEGE_ASSOCIATIONS;
            default:
                return -1;
            }
        }
        if (baseClass == Authorizable.class) {
            switch (baseFeatureID) {
            case OMPackage.AUTHORIZABLE__SYSTEM_ACTIONS:
                return OMPackage.POSITION__SYSTEM_ACTIONS;
            default:
                return -1;
            }
        }
        if (baseClass == Capable.class) {
            switch (baseFeatureID) {
            case OMPackage.CAPABLE__CAPABILITIES_ASSOCIATIONS:
                return OMPackage.POSITION__CAPABILITIES_ASSOCIATIONS;
            default:
                return -1;
            }
        }
        if (baseClass == Allocable.class) {
            switch (baseFeatureID) {
            case OMPackage.ALLOCABLE__ALLOCATION_METHOD:
                return OMPackage.POSITION__ALLOCATION_METHOD;
            default:
                return -1;
            }
        }
        if (baseClass == AssociableWithResources.class) {
            switch (baseFeatureID) {
            case OMPackage.ASSOCIABLE_WITH_RESOURCES__RESOURCE_ASSOCIATION:
                return OMPackage.POSITION__RESOURCE_ASSOCIATION;
            default:
                return -1;
            }
        }
        if (baseClass == Locatable.class) {
            switch (baseFeatureID) {
            case OMPackage.LOCATABLE__LOCATION:
                return OMPackage.POSITION__LOCATION;
            default:
                return -1;
            }
        }
        if (baseClass == OrgNode.class) {
            switch (baseFeatureID) {
            default:
                return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
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
        result.append(", idealNumber: "); //$NON-NLS-1$
        result.append(idealNumber);
        result.append(')');
        return result.toString();
    }

} // PositionImpl
