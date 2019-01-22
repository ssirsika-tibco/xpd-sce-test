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
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.om.core.om.Allocable;
import com.tibco.xpd.om.core.om.AssociableWithPrivileges;
import com.tibco.xpd.om.core.om.AttributeValue;
import com.tibco.xpd.om.core.om.Authorizable;
import com.tibco.xpd.om.core.om.Locatable;
import com.tibco.xpd.om.core.om.Location;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.OrgNode;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.core.om.OrgUnitType;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.PrivilegeAssociation;
import com.tibco.xpd.om.core.om.SystemAction;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Org Unit</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitImpl#getPurpose <em>Purpose</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitImpl#getStartDate <em>Start Date</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitImpl#getEndDate <em>End Date</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitImpl#getAttributeValues <em>Attribute Values</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitImpl#getPrivilegeAssociations <em>Privilege Associations</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitImpl#getSystemActions <em>System Actions</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitImpl#getAllocationMethod <em>Allocation Method</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitImpl#getFeature <em>Feature</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitImpl#getPositions <em>Positions</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitImpl#getOrgUnitType <em>Org Unit Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitImpl#getOutgoingRelationships <em>Outgoing Relationships</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitImpl#getIncomingRelationships <em>Incoming Relationships</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OrgUnitImpl extends NamedElementImpl implements OrgUnit {
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
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #getSystemActions()
     * @generated
     * @ordered
     */
    protected EList<SystemAction> systemActions;

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
     * The cached value of the '{@link #getFeature() <em>Feature</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getFeature()
     * @generated
     * @ordered
     */
    protected OrgUnitFeature feature;

    /**
     * The cached value of the '{@link #getPositions() <em>Positions</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getPositions()
     * @generated
     * @ordered
     */
    protected EList<Position> positions;

    /**
     * The cached value of the '{@link #getOutgoingRelationships() <em>Outgoing Relationships</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOutgoingRelationships()
     * @generated
     * @ordered
     */
    protected EList<OrgUnitRelationship> outgoingRelationships;

    /**
     * The cached value of the '{@link #getIncomingRelationships() <em>Incoming Relationships</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIncomingRelationships()
     * @generated
     * @ordered
     */
    protected EList<OrgUnitRelationship> incomingRelationships;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected OrgUnitImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OMPackage.Literals.ORG_UNIT;
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
                    OMPackage.ORG_UNIT__PURPOSE, oldPurpose, purpose));
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
                    OMPackage.ORG_UNIT__START_DATE, oldStartDate, startDate));
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
                    OMPackage.ORG_UNIT__END_DATE, oldEndDate, endDate));
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
                    OMPackage.ORG_UNIT__DESCRIPTION, oldDescription,
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
                            OMPackage.ORG_UNIT__ATTRIBUTE_VALUES);
        }
        return attributeValues;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public OrgElementType getType() {
        return getOrgUnitType();
    }

    /**
     * <!-- begin-user-doc --> Setting type of the OrgUnit is unsupported. Use
     * {@link #setFeature(OrgUnitFeature)} instead. <!-- end-user-doc -->
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
                            OMPackage.ORG_UNIT__LOCATION, oldLocation, location));
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
                    OMPackage.ORG_UNIT__LOCATION, oldLocation, location));
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
                            OMPackage.ORG_UNIT__PRIVILEGE_ASSOCIATIONS);
        }
        return privilegeAssociations;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<SystemAction> getSystemActions() {
        if (systemActions == null) {
            systemActions =
                    new EObjectContainmentEList<SystemAction>(
                            SystemAction.class, this,
                            OMPackage.ORG_UNIT__SYSTEM_ACTIONS);
        }
        return systemActions;
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
                    OMPackage.ORG_UNIT__ALLOCATION_METHOD, oldAllocationMethod,
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
                    OMPackage.ORG_UNIT__ALLOCATION_METHOD, oldAllocationMethod,
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
    public OrgUnitFeature getFeature() {
        if (feature != null && feature.eIsProxy()) {
            InternalEObject oldFeature = (InternalEObject) feature;
            feature = (OrgUnitFeature) eResolveProxy(oldFeature);
            if (feature != oldFeature) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            OMPackage.ORG_UNIT__FEATURE, oldFeature, feature));
            }
        }
        return feature;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public OrgUnitFeature basicGetFeature() {
        return feature;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setFeature(OrgUnitFeature newFeature) {
        OrgUnitFeature oldFeature = feature;
        feature = newFeature;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.ORG_UNIT__FEATURE, oldFeature, feature));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<OrgUnitRelationship> getOutgoingRelationships() {
        if (outgoingRelationships == null) {
            outgoingRelationships =
                    new EObjectWithInverseResolvingEList<OrgUnitRelationship>(
                            OrgUnitRelationship.class, this,
                            OMPackage.ORG_UNIT__OUTGOING_RELATIONSHIPS,
                            OMPackage.ORG_UNIT_RELATIONSHIP__FROM);
        }
        return outgoingRelationships;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<OrgUnitRelationship> getIncomingRelationships() {
        if (incomingRelationships == null) {
            incomingRelationships =
                    new EObjectWithInverseResolvingEList<OrgUnitRelationship>(
                            OrgUnitRelationship.class, this,
                            OMPackage.ORG_UNIT__INCOMING_RELATIONSHIPS,
                            OMPackage.ORG_UNIT_RELATIONSHIP__TO);
        }
        return incomingRelationships;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case OMPackage.ORG_UNIT__OUTGOING_RELATIONSHIPS:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getOutgoingRelationships())
                    .basicAdd(otherEnd, msgs);
        case OMPackage.ORG_UNIT__INCOMING_RELATIONSHIPS:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getIncomingRelationships())
                    .basicAdd(otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<Position> getPositions() {
        if (positions == null) {
            positions =
                    new EObjectContainmentEList<Position>(Position.class, this,
                            OMPackage.ORG_UNIT__POSITIONS);
        }
        return positions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public OrgUnitType getOrgUnitType() {
        OrgUnitFeature feature = getFeature();
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
        case OMPackage.ORG_UNIT__ATTRIBUTE_VALUES:
            return ((InternalEList<?>) getAttributeValues())
                    .basicRemove(otherEnd, msgs);
        case OMPackage.ORG_UNIT__PRIVILEGE_ASSOCIATIONS:
            return ((InternalEList<?>) getPrivilegeAssociations())
                    .basicRemove(otherEnd, msgs);
        case OMPackage.ORG_UNIT__SYSTEM_ACTIONS:
            return ((InternalEList<?>) getSystemActions())
                    .basicRemove(otherEnd, msgs);
        case OMPackage.ORG_UNIT__POSITIONS:
            return ((InternalEList<?>) getPositions()).basicRemove(otherEnd,
                    msgs);
        case OMPackage.ORG_UNIT__OUTGOING_RELATIONSHIPS:
            return ((InternalEList<?>) getOutgoingRelationships())
                    .basicRemove(otherEnd, msgs);
        case OMPackage.ORG_UNIT__INCOMING_RELATIONSHIPS:
            return ((InternalEList<?>) getIncomingRelationships())
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
        case OMPackage.ORG_UNIT__PURPOSE:
            return getPurpose();
        case OMPackage.ORG_UNIT__START_DATE:
            return getStartDate();
        case OMPackage.ORG_UNIT__END_DATE:
            return getEndDate();
        case OMPackage.ORG_UNIT__DESCRIPTION:
            return getDescription();
        case OMPackage.ORG_UNIT__ATTRIBUTE_VALUES:
            return getAttributeValues();
        case OMPackage.ORG_UNIT__TYPE:
            return getType();
        case OMPackage.ORG_UNIT__PRIVILEGE_ASSOCIATIONS:
            return getPrivilegeAssociations();
        case OMPackage.ORG_UNIT__SYSTEM_ACTIONS:
            return getSystemActions();
        case OMPackage.ORG_UNIT__ALLOCATION_METHOD:
            return getAllocationMethod();
        case OMPackage.ORG_UNIT__LOCATION:
            if (resolve)
                return getLocation();
            return basicGetLocation();
        case OMPackage.ORG_UNIT__FEATURE:
            if (resolve)
                return getFeature();
            return basicGetFeature();
        case OMPackage.ORG_UNIT__POSITIONS:
            return getPositions();
        case OMPackage.ORG_UNIT__ORG_UNIT_TYPE:
            return getOrgUnitType();
        case OMPackage.ORG_UNIT__OUTGOING_RELATIONSHIPS:
            return getOutgoingRelationships();
        case OMPackage.ORG_UNIT__INCOMING_RELATIONSHIPS:
            return getIncomingRelationships();
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
        case OMPackage.ORG_UNIT__PURPOSE:
            setPurpose((String) newValue);
            return;
        case OMPackage.ORG_UNIT__START_DATE:
            setStartDate((Date) newValue);
            return;
        case OMPackage.ORG_UNIT__END_DATE:
            setEndDate((Date) newValue);
            return;
        case OMPackage.ORG_UNIT__DESCRIPTION:
            setDescription((String) newValue);
            return;
        case OMPackage.ORG_UNIT__ATTRIBUTE_VALUES:
            getAttributeValues().clear();
            getAttributeValues()
                    .addAll((Collection<? extends AttributeValue>) newValue);
            return;
        case OMPackage.ORG_UNIT__TYPE:
            setType((OrgElementType) newValue);
            return;
        case OMPackage.ORG_UNIT__PRIVILEGE_ASSOCIATIONS:
            getPrivilegeAssociations().clear();
            getPrivilegeAssociations()
                    .addAll((Collection<? extends PrivilegeAssociation>) newValue);
            return;
        case OMPackage.ORG_UNIT__SYSTEM_ACTIONS:
            getSystemActions().clear();
            getSystemActions()
                    .addAll((Collection<? extends SystemAction>) newValue);
            return;
        case OMPackage.ORG_UNIT__ALLOCATION_METHOD:
            setAllocationMethod((String) newValue);
            return;
        case OMPackage.ORG_UNIT__LOCATION:
            setLocation((Location) newValue);
            return;
        case OMPackage.ORG_UNIT__FEATURE:
            setFeature((OrgUnitFeature) newValue);
            return;
        case OMPackage.ORG_UNIT__POSITIONS:
            getPositions().clear();
            getPositions().addAll((Collection<? extends Position>) newValue);
            return;
        case OMPackage.ORG_UNIT__OUTGOING_RELATIONSHIPS:
            getOutgoingRelationships().clear();
            getOutgoingRelationships()
                    .addAll((Collection<? extends OrgUnitRelationship>) newValue);
            return;
        case OMPackage.ORG_UNIT__INCOMING_RELATIONSHIPS:
            getIncomingRelationships().clear();
            getIncomingRelationships()
                    .addAll((Collection<? extends OrgUnitRelationship>) newValue);
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
        case OMPackage.ORG_UNIT__PURPOSE:
            setPurpose(PURPOSE_EDEFAULT);
            return;
        case OMPackage.ORG_UNIT__START_DATE:
            setStartDate(START_DATE_EDEFAULT);
            return;
        case OMPackage.ORG_UNIT__END_DATE:
            setEndDate(END_DATE_EDEFAULT);
            return;
        case OMPackage.ORG_UNIT__DESCRIPTION:
            setDescription(DESCRIPTION_EDEFAULT);
            return;
        case OMPackage.ORG_UNIT__ATTRIBUTE_VALUES:
            getAttributeValues().clear();
            return;
        case OMPackage.ORG_UNIT__TYPE:
            setType((OrgElementType) null);
            return;
        case OMPackage.ORG_UNIT__PRIVILEGE_ASSOCIATIONS:
            getPrivilegeAssociations().clear();
            return;
        case OMPackage.ORG_UNIT__SYSTEM_ACTIONS:
            getSystemActions().clear();
            return;
        case OMPackage.ORG_UNIT__ALLOCATION_METHOD:
            unsetAllocationMethod();
            return;
        case OMPackage.ORG_UNIT__LOCATION:
            setLocation((Location) null);
            return;
        case OMPackage.ORG_UNIT__FEATURE:
            setFeature((OrgUnitFeature) null);
            return;
        case OMPackage.ORG_UNIT__POSITIONS:
            getPositions().clear();
            return;
        case OMPackage.ORG_UNIT__OUTGOING_RELATIONSHIPS:
            getOutgoingRelationships().clear();
            return;
        case OMPackage.ORG_UNIT__INCOMING_RELATIONSHIPS:
            getIncomingRelationships().clear();
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
        case OMPackage.ORG_UNIT__PURPOSE:
            return PURPOSE_EDEFAULT == null ? purpose != null
                    : !PURPOSE_EDEFAULT.equals(purpose);
        case OMPackage.ORG_UNIT__START_DATE:
            return START_DATE_EDEFAULT == null ? startDate != null
                    : !START_DATE_EDEFAULT.equals(startDate);
        case OMPackage.ORG_UNIT__END_DATE:
            return END_DATE_EDEFAULT == null ? endDate != null
                    : !END_DATE_EDEFAULT.equals(endDate);
        case OMPackage.ORG_UNIT__DESCRIPTION:
            return DESCRIPTION_EDEFAULT == null ? description != null
                    : !DESCRIPTION_EDEFAULT.equals(description);
        case OMPackage.ORG_UNIT__ATTRIBUTE_VALUES:
            return attributeValues != null && !attributeValues.isEmpty();
        case OMPackage.ORG_UNIT__TYPE:
            return getType() != null;
        case OMPackage.ORG_UNIT__PRIVILEGE_ASSOCIATIONS:
            return privilegeAssociations != null
                    && !privilegeAssociations.isEmpty();
        case OMPackage.ORG_UNIT__SYSTEM_ACTIONS:
            return systemActions != null && !systemActions.isEmpty();
        case OMPackage.ORG_UNIT__ALLOCATION_METHOD:
            return isSetAllocationMethod();
        case OMPackage.ORG_UNIT__LOCATION:
            return location != null;
        case OMPackage.ORG_UNIT__FEATURE:
            return feature != null;
        case OMPackage.ORG_UNIT__POSITIONS:
            return positions != null && !positions.isEmpty();
        case OMPackage.ORG_UNIT__ORG_UNIT_TYPE:
            return getOrgUnitType() != null;
        case OMPackage.ORG_UNIT__OUTGOING_RELATIONSHIPS:
            return outgoingRelationships != null
                    && !outgoingRelationships.isEmpty();
        case OMPackage.ORG_UNIT__INCOMING_RELATIONSHIPS:
            return incomingRelationships != null
                    && !incomingRelationships.isEmpty();
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
            case OMPackage.ORG_UNIT__PRIVILEGE_ASSOCIATIONS:
                return OMPackage.ASSOCIABLE_WITH_PRIVILEGES__PRIVILEGE_ASSOCIATIONS;
            default:
                return -1;
            }
        }
        if (baseClass == Authorizable.class) {
            switch (derivedFeatureID) {
            case OMPackage.ORG_UNIT__SYSTEM_ACTIONS:
                return OMPackage.AUTHORIZABLE__SYSTEM_ACTIONS;
            default:
                return -1;
            }
        }
        if (baseClass == Allocable.class) {
            switch (derivedFeatureID) {
            case OMPackage.ORG_UNIT__ALLOCATION_METHOD:
                return OMPackage.ALLOCABLE__ALLOCATION_METHOD;
            default:
                return -1;
            }
        }
        if (baseClass == Locatable.class) {
            switch (derivedFeatureID) {
            case OMPackage.ORG_UNIT__LOCATION:
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
                return OMPackage.ORG_UNIT__PRIVILEGE_ASSOCIATIONS;
            default:
                return -1;
            }
        }
        if (baseClass == Authorizable.class) {
            switch (baseFeatureID) {
            case OMPackage.AUTHORIZABLE__SYSTEM_ACTIONS:
                return OMPackage.ORG_UNIT__SYSTEM_ACTIONS;
            default:
                return -1;
            }
        }
        if (baseClass == Allocable.class) {
            switch (baseFeatureID) {
            case OMPackage.ALLOCABLE__ALLOCATION_METHOD:
                return OMPackage.ORG_UNIT__ALLOCATION_METHOD;
            default:
                return -1;
            }
        }
        if (baseClass == Locatable.class) {
            switch (baseFeatureID) {
            case OMPackage.LOCATABLE__LOCATION:
                return OMPackage.ORG_UNIT__LOCATION;
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
        result.append(')');
        return result.toString();
    }

    /**
     * {@inheritDoc}
     * 
     * @generated NOT
     */
    public EList<OrgUnit> getSubUnits() {
        BasicEList<OrgUnit> subUnits = new BasicEList<OrgUnit>();
        for (OrgUnitRelationship rel : getOutgoingRelationships()) {
            if (rel.isIsHierarchical() && rel.getTo() != null
                    && rel.getTo().eContainer() == eContainer()) {
                subUnits.add(rel.getTo());
            }
        }
        return subUnits;
    }

    /**
     * <!-- begin-user-doc --> Returns parent OrgUnit or <code>null</code> if
     * the unit is a top level unit in the organization. <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public OrgUnit getParentOrgUnit() {
        OrgUnitRelationship rel = getIncomingHierachicalRelationship();
        if (rel != null && rel.getFrom() != eContainer()) {
            return rel.getFrom();
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public EList<OrgUnitRelationship> getOutgoingHierachicalRelationships() {
        BasicEList<OrgUnitRelationship> hierarchicalRel =
                new BasicEList<OrgUnitRelationship>();
        for (OrgUnitRelationship rel : getOutgoingRelationships()) {
            if (rel.isIsHierarchical()) {
                hierarchicalRel.add(rel);
            }
        }
        return hierarchicalRel;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public EList<OrgUnitRelationship> getOutgoingNonHierachicalRelationships() {
        BasicEList<OrgUnitRelationship> nonHierarchicalRel =
                new BasicEList<OrgUnitRelationship>();
        for (OrgUnitRelationship rel : getOutgoingRelationships()) {
            if (!rel.isIsHierarchical()) {
                nonHierarchicalRel.add(rel);
            }
        }
        return nonHierarchicalRel;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public OrgUnitRelationship getIncomingHierachicalRelationship() {
        for (OrgUnitRelationship rel : getIncomingRelationships()) {
            if (rel.isIsHierarchical()) {
                return rel;
            }
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public EList<OrgUnitRelationship> getIncomingNonHierachicalRelationships() {
        BasicEList<OrgUnitRelationship> nonHierarchicalRel =
                new BasicEList<OrgUnitRelationship>();
        for (OrgUnitRelationship rel : getIncomingRelationships()) {
            if (!rel.isIsHierarchical()) {
                nonHierarchicalRel.add(rel);
            }
        }
        return nonHierarchicalRel;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public Organization getOrganization() {
        return (Organization) ((eContainer() != null) ? eContainer : null);
    }

} // OrgUnitImpl
