/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.impl;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.om.core.om.Allocable;
import com.tibco.xpd.om.core.om.AttributeValue;
import com.tibco.xpd.om.core.om.DynamicOrgIdentifier;
import com.tibco.xpd.om.core.om.Locatable;
import com.tibco.xpd.om.core.om.Location;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.OrgNode;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.OrganizationType;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Organization</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrganizationImpl#getPurpose <em>Purpose</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrganizationImpl#getStartDate <em>Start Date</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrganizationImpl#getEndDate <em>End Date</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrganizationImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrganizationImpl#getAttributeValues <em>Attribute Values</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrganizationImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrganizationImpl#getAllocationMethod <em>Allocation Method</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrganizationImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrganizationImpl#getUnits <em>Units</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrganizationImpl#getOrganizationType <em>Organization Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrganizationImpl#getOrgUnitRelationships <em>Org Unit Relationships</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrganizationImpl#isDynamic <em>Dynamic</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrganizationImpl#getDynamicOrgIdentifiers <em>Dynamic Org Identifiers</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OrganizationImpl extends NamedElementImpl implements Organization {
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
     * The cached value of the '{@link #getLocation() <em>Location</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getLocation()
     * @generated
     * @ordered
     */
    protected Location location;

    /**
     * The cached value of the '{@link #getUnits() <em>Units</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getUnits()
     * @generated
     * @ordered
     */
    protected EList<OrgUnit> units;

    /**
     * The cached value of the '{@link #getOrganizationType() <em>Organization Type</em>}' reference.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getOrganizationType()
     * @generated
     * @ordered
     */
    protected OrganizationType organizationType;

    /**
     * The cached value of the '{@link #getOrgUnitRelationships()
     * <em>Org Unit Relationships</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getOrgUnitRelationships()
     * @generated
     * @ordered
     */
    protected EList<OrgUnitRelationship> orgUnitRelationships;

    /**
     * The default value of the '{@link #isDynamic() <em>Dynamic</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isDynamic()
     * @generated
     * @ordered
     */
    protected static final boolean DYNAMIC_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isDynamic() <em>Dynamic</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isDynamic()
     * @generated
     * @ordered
     */
    protected boolean dynamic = DYNAMIC_EDEFAULT;

    /**
     * The cached value of the '{@link #getDynamicOrgIdentifiers() <em>Dynamic Org Identifiers</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDynamicOrgIdentifiers()
     * @generated
     * @ordered
     */
    protected EList<DynamicOrgIdentifier> dynamicOrgIdentifiers;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected OrganizationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OMPackage.Literals.ORGANIZATION;
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
                    OMPackage.ORGANIZATION__PURPOSE, oldPurpose, purpose));
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
                    OMPackage.ORGANIZATION__START_DATE, oldStartDate, startDate));
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
                    OMPackage.ORGANIZATION__END_DATE, oldEndDate, endDate));
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
                    OMPackage.ORGANIZATION__DESCRIPTION, oldDescription,
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
                            OMPackage.ORGANIZATION__ATTRIBUTE_VALUES);
        }
        return attributeValues;
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
                            OMPackage.ORGANIZATION__LOCATION, oldLocation,
                            location));
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
                    OMPackage.ORGANIZATION__LOCATION, oldLocation, location));
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
                    OMPackage.ORGANIZATION__ALLOCATION_METHOD,
                    oldAllocationMethod, allocationMethod,
                    !oldAllocationMethodESet));
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
                    OMPackage.ORGANIZATION__ALLOCATION_METHOD,
                    oldAllocationMethod, ALLOCATION_METHOD_EDEFAULT,
                    oldAllocationMethodESet));
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
    public EList<OrgUnit> getUnits() {
        if (units == null) {
            units =
                    new EObjectContainmentEList<OrgUnit>(OrgUnit.class, this,
                            OMPackage.ORGANIZATION__UNITS);
        }
        return units;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public OrganizationType getOrganizationType() {
        if (organizationType != null && organizationType.eIsProxy()) {
            InternalEObject oldOrganizationType =
                    (InternalEObject) organizationType;
            organizationType =
                    (OrganizationType) eResolveProxy(oldOrganizationType);
            if (organizationType != oldOrganizationType) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            OMPackage.ORGANIZATION__ORGANIZATION_TYPE,
                            oldOrganizationType, organizationType));
            }
        }
        return organizationType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public OrganizationType basicGetOrganizationType() {
        return organizationType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setOrganizationType(OrganizationType newOrganizationType) {
        OrganizationType oldOrganizationType = organizationType;
        organizationType = newOrganizationType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.ORGANIZATION__ORGANIZATION_TYPE,
                    oldOrganizationType, organizationType));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public OrgElementType getType() {
        return getOrganizationType();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public void setType(OrgElementType newType) {
        setOrganizationType((OrganizationType) newType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<OrgUnitRelationship> getOrgUnitRelationships() {
        if (orgUnitRelationships == null) {
            orgUnitRelationships =
                    new EObjectContainmentEList<OrgUnitRelationship>(
                            OrgUnitRelationship.class, this,
                            OMPackage.ORGANIZATION__ORG_UNIT_RELATIONSHIPS);
        }
        return orgUnitRelationships;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isDynamic() {
        return dynamic;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDynamic(boolean newDynamic) {
        boolean oldDynamic = dynamic;
        dynamic = newDynamic;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.ORGANIZATION__DYNAMIC, oldDynamic, dynamic));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<DynamicOrgIdentifier> getDynamicOrgIdentifiers() {
        if (dynamicOrgIdentifiers == null) {
            dynamicOrgIdentifiers =
                    new EObjectContainmentEList<DynamicOrgIdentifier>(
                            DynamicOrgIdentifier.class, this,
                            OMPackage.ORGANIZATION__DYNAMIC_ORG_IDENTIFIERS);
        }
        return dynamicOrgIdentifiers;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public EList<OrgUnit> getSubUnits() {
        BasicEList<OrgUnit> subUnits = new BasicEList<OrgUnit>(getUnits());
        for (Iterator<OrgUnit> iterator = subUnits.iterator(); iterator
                .hasNext();) {
            OrgUnit orgUnit = iterator.next();
            OrgUnitRelationship rel =
                    orgUnit.getIncomingHierachicalRelationship();
            if (rel != null && rel.getFrom() != null
                    && rel.getFrom().eContainer() == orgUnit.eContainer()) {
                iterator.remove();
            }

        }
        return subUnits;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case OMPackage.ORGANIZATION__ATTRIBUTE_VALUES:
            return ((InternalEList<?>) getAttributeValues())
                    .basicRemove(otherEnd, msgs);
        case OMPackage.ORGANIZATION__UNITS:
            return ((InternalEList<?>) getUnits()).basicRemove(otherEnd, msgs);
        case OMPackage.ORGANIZATION__ORG_UNIT_RELATIONSHIPS:
            return ((InternalEList<?>) getOrgUnitRelationships())
                    .basicRemove(otherEnd, msgs);
        case OMPackage.ORGANIZATION__DYNAMIC_ORG_IDENTIFIERS:
            return ((InternalEList<?>) getDynamicOrgIdentifiers())
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
        case OMPackage.ORGANIZATION__PURPOSE:
            return getPurpose();
        case OMPackage.ORGANIZATION__START_DATE:
            return getStartDate();
        case OMPackage.ORGANIZATION__END_DATE:
            return getEndDate();
        case OMPackage.ORGANIZATION__DESCRIPTION:
            return getDescription();
        case OMPackage.ORGANIZATION__ATTRIBUTE_VALUES:
            return getAttributeValues();
        case OMPackage.ORGANIZATION__TYPE:
            return getType();
        case OMPackage.ORGANIZATION__ALLOCATION_METHOD:
            return getAllocationMethod();
        case OMPackage.ORGANIZATION__LOCATION:
            if (resolve)
                return getLocation();
            return basicGetLocation();
        case OMPackage.ORGANIZATION__UNITS:
            return getUnits();
        case OMPackage.ORGANIZATION__ORGANIZATION_TYPE:
            if (resolve)
                return getOrganizationType();
            return basicGetOrganizationType();
        case OMPackage.ORGANIZATION__ORG_UNIT_RELATIONSHIPS:
            return getOrgUnitRelationships();
        case OMPackage.ORGANIZATION__DYNAMIC:
            return isDynamic();
        case OMPackage.ORGANIZATION__DYNAMIC_ORG_IDENTIFIERS:
            return getDynamicOrgIdentifiers();
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
        case OMPackage.ORGANIZATION__PURPOSE:
            setPurpose((String) newValue);
            return;
        case OMPackage.ORGANIZATION__START_DATE:
            setStartDate((Date) newValue);
            return;
        case OMPackage.ORGANIZATION__END_DATE:
            setEndDate((Date) newValue);
            return;
        case OMPackage.ORGANIZATION__DESCRIPTION:
            setDescription((String) newValue);
            return;
        case OMPackage.ORGANIZATION__ATTRIBUTE_VALUES:
            getAttributeValues().clear();
            getAttributeValues()
                    .addAll((Collection<? extends AttributeValue>) newValue);
            return;
        case OMPackage.ORGANIZATION__TYPE:
            setType((OrgElementType) newValue);
            return;
        case OMPackage.ORGANIZATION__ALLOCATION_METHOD:
            setAllocationMethod((String) newValue);
            return;
        case OMPackage.ORGANIZATION__LOCATION:
            setLocation((Location) newValue);
            return;
        case OMPackage.ORGANIZATION__UNITS:
            getUnits().clear();
            getUnits().addAll((Collection<? extends OrgUnit>) newValue);
            return;
        case OMPackage.ORGANIZATION__ORGANIZATION_TYPE:
            setOrganizationType((OrganizationType) newValue);
            return;
        case OMPackage.ORGANIZATION__ORG_UNIT_RELATIONSHIPS:
            getOrgUnitRelationships().clear();
            getOrgUnitRelationships()
                    .addAll((Collection<? extends OrgUnitRelationship>) newValue);
            return;
        case OMPackage.ORGANIZATION__DYNAMIC:
            setDynamic((Boolean) newValue);
            return;
        case OMPackage.ORGANIZATION__DYNAMIC_ORG_IDENTIFIERS:
            getDynamicOrgIdentifiers().clear();
            getDynamicOrgIdentifiers()
                    .addAll((Collection<? extends DynamicOrgIdentifier>) newValue);
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
        case OMPackage.ORGANIZATION__PURPOSE:
            setPurpose(PURPOSE_EDEFAULT);
            return;
        case OMPackage.ORGANIZATION__START_DATE:
            setStartDate(START_DATE_EDEFAULT);
            return;
        case OMPackage.ORGANIZATION__END_DATE:
            setEndDate(END_DATE_EDEFAULT);
            return;
        case OMPackage.ORGANIZATION__DESCRIPTION:
            setDescription(DESCRIPTION_EDEFAULT);
            return;
        case OMPackage.ORGANIZATION__ATTRIBUTE_VALUES:
            getAttributeValues().clear();
            return;
        case OMPackage.ORGANIZATION__TYPE:
            setType((OrgElementType) null);
            return;
        case OMPackage.ORGANIZATION__ALLOCATION_METHOD:
            unsetAllocationMethod();
            return;
        case OMPackage.ORGANIZATION__LOCATION:
            setLocation((Location) null);
            return;
        case OMPackage.ORGANIZATION__UNITS:
            getUnits().clear();
            return;
        case OMPackage.ORGANIZATION__ORGANIZATION_TYPE:
            setOrganizationType((OrganizationType) null);
            return;
        case OMPackage.ORGANIZATION__ORG_UNIT_RELATIONSHIPS:
            getOrgUnitRelationships().clear();
            return;
        case OMPackage.ORGANIZATION__DYNAMIC:
            setDynamic(DYNAMIC_EDEFAULT);
            return;
        case OMPackage.ORGANIZATION__DYNAMIC_ORG_IDENTIFIERS:
            getDynamicOrgIdentifiers().clear();
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
        case OMPackage.ORGANIZATION__PURPOSE:
            return PURPOSE_EDEFAULT == null ? purpose != null
                    : !PURPOSE_EDEFAULT.equals(purpose);
        case OMPackage.ORGANIZATION__START_DATE:
            return START_DATE_EDEFAULT == null ? startDate != null
                    : !START_DATE_EDEFAULT.equals(startDate);
        case OMPackage.ORGANIZATION__END_DATE:
            return END_DATE_EDEFAULT == null ? endDate != null
                    : !END_DATE_EDEFAULT.equals(endDate);
        case OMPackage.ORGANIZATION__DESCRIPTION:
            return DESCRIPTION_EDEFAULT == null ? description != null
                    : !DESCRIPTION_EDEFAULT.equals(description);
        case OMPackage.ORGANIZATION__ATTRIBUTE_VALUES:
            return attributeValues != null && !attributeValues.isEmpty();
        case OMPackage.ORGANIZATION__TYPE:
            return getType() != null;
        case OMPackage.ORGANIZATION__ALLOCATION_METHOD:
            return isSetAllocationMethod();
        case OMPackage.ORGANIZATION__LOCATION:
            return location != null;
        case OMPackage.ORGANIZATION__UNITS:
            return units != null && !units.isEmpty();
        case OMPackage.ORGANIZATION__ORGANIZATION_TYPE:
            return organizationType != null;
        case OMPackage.ORGANIZATION__ORG_UNIT_RELATIONSHIPS:
            return orgUnitRelationships != null
                    && !orgUnitRelationships.isEmpty();
        case OMPackage.ORGANIZATION__DYNAMIC:
            return dynamic != DYNAMIC_EDEFAULT;
        case OMPackage.ORGANIZATION__DYNAMIC_ORG_IDENTIFIERS:
            return dynamicOrgIdentifiers != null
                    && !dynamicOrgIdentifiers.isEmpty();
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == Allocable.class) {
            switch (derivedFeatureID) {
            case OMPackage.ORGANIZATION__ALLOCATION_METHOD:
                return OMPackage.ALLOCABLE__ALLOCATION_METHOD;
            default:
                return -1;
            }
        }
        if (baseClass == Locatable.class) {
            switch (derivedFeatureID) {
            case OMPackage.ORGANIZATION__LOCATION:
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
        if (baseClass == Allocable.class) {
            switch (baseFeatureID) {
            case OMPackage.ALLOCABLE__ALLOCATION_METHOD:
                return OMPackage.ORGANIZATION__ALLOCATION_METHOD;
            default:
                return -1;
            }
        }
        if (baseClass == Locatable.class) {
            switch (baseFeatureID) {
            case OMPackage.LOCATABLE__LOCATION:
                return OMPackage.ORGANIZATION__LOCATION;
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
        result.append(", dynamic: "); //$NON-NLS-1$
        result.append(dynamic);
        result.append(')');
        return result.toString();
    }

} // OrganizationImpl
