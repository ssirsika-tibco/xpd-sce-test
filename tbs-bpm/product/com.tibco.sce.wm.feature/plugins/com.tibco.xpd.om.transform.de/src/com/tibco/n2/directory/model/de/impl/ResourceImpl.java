/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de.impl;

import com.tibco.n2.directory.model.de.Attribute;
import com.tibco.n2.directory.model.de.CapabilityHolding;
import com.tibco.n2.directory.model.de.DePackage;
import com.tibco.n2.directory.model.de.GroupHolding;
import com.tibco.n2.directory.model.de.Location;
import com.tibco.n2.directory.model.de.PositionHolding;
import com.tibco.n2.directory.model.de.Resource;
import com.tibco.n2.directory.model.de.ResourceType;

import java.util.Collection;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Resource</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ResourceImpl#getChoiceGroup <em>Choice Group</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ResourceImpl#getCapabilityHeld <em>Capability Held</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ResourceImpl#getPositionHeld <em>Position Held</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ResourceImpl#getGroupHeld <em>Group Held</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ResourceImpl#getAttributeValue <em>Attribute Value</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ResourceImpl#getEndDate <em>End Date</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ResourceImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ResourceImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ResourceImpl#getLdapAlias <em>Ldap Alias</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ResourceImpl#getLdapDn <em>Ldap Dn</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ResourceImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ResourceImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ResourceImpl#getResourceType <em>Resource Type</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ResourceImpl#getStartDate <em>Start Date</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ResourceImpl extends EObjectImpl implements Resource {
    /**
     * The cached value of the '{@link #getChoiceGroup() <em>Choice Group</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getChoiceGroup()
     * @generated
     * @ordered
     */
    protected FeatureMap choiceGroup;

    /**
     * The default value of the '{@link #getEndDate() <em>End Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEndDate()
     * @generated
     * @ordered
     */
    protected static final XMLGregorianCalendar END_DATE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getEndDate() <em>End Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEndDate()
     * @generated
     * @ordered
     */
    protected XMLGregorianCalendar endDate = END_DATE_EDEFAULT;

    /**
     * The default value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected static final String ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected String id = ID_EDEFAULT;

    /**
     * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLabel()
     * @generated
     * @ordered
     */
    protected static final String LABEL_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLabel()
     * @generated
     * @ordered
     */
    protected String label = LABEL_EDEFAULT;

    /**
     * The default value of the '{@link #getLdapAlias() <em>Ldap Alias</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLdapAlias()
     * @generated
     * @ordered
     */
    protected static final String LDAP_ALIAS_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getLdapAlias() <em>Ldap Alias</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLdapAlias()
     * @generated
     * @ordered
     */
    protected String ldapAlias = LDAP_ALIAS_EDEFAULT;

    /**
     * The default value of the '{@link #getLdapDn() <em>Ldap Dn</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLdapDn()
     * @generated
     * @ordered
     */
    protected static final String LDAP_DN_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getLdapDn() <em>Ldap Dn</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLdapDn()
     * @generated
     * @ordered
     */
    protected String ldapDn = LDAP_DN_EDEFAULT;

    /**
     * The cached value of the '{@link #getLocation() <em>Location</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLocation()
     * @generated
     * @ordered
     */
    protected Location location;

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
     * The default value of the '{@link #getResourceType() <em>Resource Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResourceType()
     * @generated
     * @ordered
     */
    protected static final ResourceType RESOURCE_TYPE_EDEFAULT = ResourceType.HUMAN;

    /**
     * The cached value of the '{@link #getResourceType() <em>Resource Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResourceType()
     * @generated
     * @ordered
     */
    protected ResourceType resourceType = RESOURCE_TYPE_EDEFAULT;

    /**
     * This is true if the Resource Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean resourceTypeESet;

    /**
     * The default value of the '{@link #getStartDate() <em>Start Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartDate()
     * @generated
     * @ordered
     */
    protected static final XMLGregorianCalendar START_DATE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getStartDate() <em>Start Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartDate()
     * @generated
     * @ordered
     */
    protected XMLGregorianCalendar startDate = START_DATE_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ResourceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DePackage.Literals.RESOURCE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getChoiceGroup() {
        if (choiceGroup == null) {
            choiceGroup = new BasicFeatureMap(this, DePackage.RESOURCE__CHOICE_GROUP);
        }
        return choiceGroup;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<CapabilityHolding> getCapabilityHeld() {
        return getChoiceGroup().list(DePackage.Literals.RESOURCE__CAPABILITY_HELD);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<PositionHolding> getPositionHeld() {
        return getChoiceGroup().list(DePackage.Literals.RESOURCE__POSITION_HELD);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<GroupHolding> getGroupHeld() {
        return getChoiceGroup().list(DePackage.Literals.RESOURCE__GROUP_HELD);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Attribute> getAttributeValue() {
        return getChoiceGroup().list(DePackage.Literals.RESOURCE__ATTRIBUTE_VALUE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEndDate(XMLGregorianCalendar newEndDate) {
        XMLGregorianCalendar oldEndDate = endDate;
        endDate = newEndDate;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.RESOURCE__END_DATE, oldEndDate, endDate));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getId() {
        return id;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setId(String newId) {
        String oldId = id;
        id = newId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.RESOURCE__ID, oldId, id));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getLabel() {
        return label;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLabel(String newLabel) {
        String oldLabel = label;
        label = newLabel;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.RESOURCE__LABEL, oldLabel, label));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getLdapAlias() {
        return ldapAlias;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLdapAlias(String newLdapAlias) {
        String oldLdapAlias = ldapAlias;
        ldapAlias = newLdapAlias;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.RESOURCE__LDAP_ALIAS, oldLdapAlias, ldapAlias));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getLdapDn() {
        return ldapDn;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLdapDn(String newLdapDn) {
        String oldLdapDn = ldapDn;
        ldapDn = newLdapDn;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.RESOURCE__LDAP_DN, oldLdapDn, ldapDn));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Location getLocation() {
        return location;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLocation(Location newLocation) {
        Location oldLocation = location;
        location = newLocation;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.RESOURCE__LOCATION, oldLocation, location));
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
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.RESOURCE__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResourceType getResourceType() {
        return resourceType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setResourceType(ResourceType newResourceType) {
        ResourceType oldResourceType = resourceType;
        resourceType = newResourceType == null ? RESOURCE_TYPE_EDEFAULT : newResourceType;
        boolean oldResourceTypeESet = resourceTypeESet;
        resourceTypeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.RESOURCE__RESOURCE_TYPE, oldResourceType, resourceType, !oldResourceTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetResourceType() {
        ResourceType oldResourceType = resourceType;
        boolean oldResourceTypeESet = resourceTypeESet;
        resourceType = RESOURCE_TYPE_EDEFAULT;
        resourceTypeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, DePackage.RESOURCE__RESOURCE_TYPE, oldResourceType, RESOURCE_TYPE_EDEFAULT, oldResourceTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetResourceType() {
        return resourceTypeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setStartDate(XMLGregorianCalendar newStartDate) {
        XMLGregorianCalendar oldStartDate = startDate;
        startDate = newStartDate;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.RESOURCE__START_DATE, oldStartDate, startDate));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DePackage.RESOURCE__CHOICE_GROUP:
                return ((InternalEList<?>)getChoiceGroup()).basicRemove(otherEnd, msgs);
            case DePackage.RESOURCE__CAPABILITY_HELD:
                return ((InternalEList<?>)getCapabilityHeld()).basicRemove(otherEnd, msgs);
            case DePackage.RESOURCE__POSITION_HELD:
                return ((InternalEList<?>)getPositionHeld()).basicRemove(otherEnd, msgs);
            case DePackage.RESOURCE__GROUP_HELD:
                return ((InternalEList<?>)getGroupHeld()).basicRemove(otherEnd, msgs);
            case DePackage.RESOURCE__ATTRIBUTE_VALUE:
                return ((InternalEList<?>)getAttributeValue()).basicRemove(otherEnd, msgs);
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
            case DePackage.RESOURCE__CHOICE_GROUP:
                if (coreType) return getChoiceGroup();
                return ((FeatureMap.Internal)getChoiceGroup()).getWrapper();
            case DePackage.RESOURCE__CAPABILITY_HELD:
                return getCapabilityHeld();
            case DePackage.RESOURCE__POSITION_HELD:
                return getPositionHeld();
            case DePackage.RESOURCE__GROUP_HELD:
                return getGroupHeld();
            case DePackage.RESOURCE__ATTRIBUTE_VALUE:
                return getAttributeValue();
            case DePackage.RESOURCE__END_DATE:
                return getEndDate();
            case DePackage.RESOURCE__ID:
                return getId();
            case DePackage.RESOURCE__LABEL:
                return getLabel();
            case DePackage.RESOURCE__LDAP_ALIAS:
                return getLdapAlias();
            case DePackage.RESOURCE__LDAP_DN:
                return getLdapDn();
            case DePackage.RESOURCE__LOCATION:
                return getLocation();
            case DePackage.RESOURCE__NAME:
                return getName();
            case DePackage.RESOURCE__RESOURCE_TYPE:
                return getResourceType();
            case DePackage.RESOURCE__START_DATE:
                return getStartDate();
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
            case DePackage.RESOURCE__CHOICE_GROUP:
                ((FeatureMap.Internal)getChoiceGroup()).set(newValue);
                return;
            case DePackage.RESOURCE__CAPABILITY_HELD:
                getCapabilityHeld().clear();
                getCapabilityHeld().addAll((Collection<? extends CapabilityHolding>)newValue);
                return;
            case DePackage.RESOURCE__POSITION_HELD:
                getPositionHeld().clear();
                getPositionHeld().addAll((Collection<? extends PositionHolding>)newValue);
                return;
            case DePackage.RESOURCE__GROUP_HELD:
                getGroupHeld().clear();
                getGroupHeld().addAll((Collection<? extends GroupHolding>)newValue);
                return;
            case DePackage.RESOURCE__ATTRIBUTE_VALUE:
                getAttributeValue().clear();
                getAttributeValue().addAll((Collection<? extends Attribute>)newValue);
                return;
            case DePackage.RESOURCE__END_DATE:
                setEndDate((XMLGregorianCalendar)newValue);
                return;
            case DePackage.RESOURCE__ID:
                setId((String)newValue);
                return;
            case DePackage.RESOURCE__LABEL:
                setLabel((String)newValue);
                return;
            case DePackage.RESOURCE__LDAP_ALIAS:
                setLdapAlias((String)newValue);
                return;
            case DePackage.RESOURCE__LDAP_DN:
                setLdapDn((String)newValue);
                return;
            case DePackage.RESOURCE__LOCATION:
                setLocation((Location)newValue);
                return;
            case DePackage.RESOURCE__NAME:
                setName((String)newValue);
                return;
            case DePackage.RESOURCE__RESOURCE_TYPE:
                setResourceType((ResourceType)newValue);
                return;
            case DePackage.RESOURCE__START_DATE:
                setStartDate((XMLGregorianCalendar)newValue);
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
            case DePackage.RESOURCE__CHOICE_GROUP:
                getChoiceGroup().clear();
                return;
            case DePackage.RESOURCE__CAPABILITY_HELD:
                getCapabilityHeld().clear();
                return;
            case DePackage.RESOURCE__POSITION_HELD:
                getPositionHeld().clear();
                return;
            case DePackage.RESOURCE__GROUP_HELD:
                getGroupHeld().clear();
                return;
            case DePackage.RESOURCE__ATTRIBUTE_VALUE:
                getAttributeValue().clear();
                return;
            case DePackage.RESOURCE__END_DATE:
                setEndDate(END_DATE_EDEFAULT);
                return;
            case DePackage.RESOURCE__ID:
                setId(ID_EDEFAULT);
                return;
            case DePackage.RESOURCE__LABEL:
                setLabel(LABEL_EDEFAULT);
                return;
            case DePackage.RESOURCE__LDAP_ALIAS:
                setLdapAlias(LDAP_ALIAS_EDEFAULT);
                return;
            case DePackage.RESOURCE__LDAP_DN:
                setLdapDn(LDAP_DN_EDEFAULT);
                return;
            case DePackage.RESOURCE__LOCATION:
                setLocation((Location)null);
                return;
            case DePackage.RESOURCE__NAME:
                setName(NAME_EDEFAULT);
                return;
            case DePackage.RESOURCE__RESOURCE_TYPE:
                unsetResourceType();
                return;
            case DePackage.RESOURCE__START_DATE:
                setStartDate(START_DATE_EDEFAULT);
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
            case DePackage.RESOURCE__CHOICE_GROUP:
                return choiceGroup != null && !choiceGroup.isEmpty();
            case DePackage.RESOURCE__CAPABILITY_HELD:
                return !getCapabilityHeld().isEmpty();
            case DePackage.RESOURCE__POSITION_HELD:
                return !getPositionHeld().isEmpty();
            case DePackage.RESOURCE__GROUP_HELD:
                return !getGroupHeld().isEmpty();
            case DePackage.RESOURCE__ATTRIBUTE_VALUE:
                return !getAttributeValue().isEmpty();
            case DePackage.RESOURCE__END_DATE:
                return END_DATE_EDEFAULT == null ? endDate != null : !END_DATE_EDEFAULT.equals(endDate);
            case DePackage.RESOURCE__ID:
                return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
            case DePackage.RESOURCE__LABEL:
                return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
            case DePackage.RESOURCE__LDAP_ALIAS:
                return LDAP_ALIAS_EDEFAULT == null ? ldapAlias != null : !LDAP_ALIAS_EDEFAULT.equals(ldapAlias);
            case DePackage.RESOURCE__LDAP_DN:
                return LDAP_DN_EDEFAULT == null ? ldapDn != null : !LDAP_DN_EDEFAULT.equals(ldapDn);
            case DePackage.RESOURCE__LOCATION:
                return location != null;
            case DePackage.RESOURCE__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case DePackage.RESOURCE__RESOURCE_TYPE:
                return isSetResourceType();
            case DePackage.RESOURCE__START_DATE:
                return START_DATE_EDEFAULT == null ? startDate != null : !START_DATE_EDEFAULT.equals(startDate);
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
        result.append(" (choiceGroup: ");
        result.append(choiceGroup);
        result.append(", endDate: ");
        result.append(endDate);
        result.append(", id: ");
        result.append(id);
        result.append(", label: ");
        result.append(label);
        result.append(", ldapAlias: ");
        result.append(ldapAlias);
        result.append(", ldapDn: ");
        result.append(ldapDn);
        result.append(", name: ");
        result.append(name);
        result.append(", resourceType: ");
        if (resourceTypeESet) result.append(resourceType); else result.append("<unset>");
        result.append(", startDate: ");
        result.append(startDate);
        result.append(')');
        return result.toString();
    }

} //ResourceImpl
