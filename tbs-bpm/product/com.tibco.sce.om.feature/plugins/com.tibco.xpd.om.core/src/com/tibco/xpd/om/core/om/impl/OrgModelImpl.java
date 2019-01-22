/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.om.core.om.AssociableWithPrivileges;
import com.tibco.xpd.om.core.om.Authorizable;
import com.tibco.xpd.om.core.om.Capability;
import com.tibco.xpd.om.core.om.CapabilityCategory;
import com.tibco.xpd.om.core.om.DynamicOrgReference;
import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.Location;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgMetaModel;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgQuery;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.Privilege;
import com.tibco.xpd.om.core.om.PrivilegeAssociation;
import com.tibco.xpd.om.core.om.PrivilegeCategory;
import com.tibco.xpd.om.core.om.Resource;
import com.tibco.xpd.om.core.om.ResourceType;
import com.tibco.xpd.om.core.om.SystemAction;
import com.tibco.xpd.om.core.om.SystemActionIdentifier;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Org Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgModelImpl#getPrivilegeAssociations <em>Privilege Associations</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgModelImpl#getSystemActions <em>System Actions</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgModelImpl#getGroups <em>Groups</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgModelImpl#getCapabilityCategories <em>Capability Categories</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgModelImpl#getCapabilities <em>Capabilities</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgModelImpl#getOrganizations <em>Organizations</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgModelImpl#getLocations <em>Locations</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgModelImpl#getPrivileges <em>Privileges</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgModelImpl#getPrivilegeCategories <em>Privilege Categories</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgModelImpl#getMetamodels <em>Metamodels</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgModelImpl#getResources <em>Resources</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgModelImpl#getEmbeddedMetamodel <em>Embedded Metamodel</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgModelImpl#getQueries <em>Queries</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgModelImpl#getHumanResourceType <em>Human Resource Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgModelImpl#getConsumableResourceType <em>Consumable Resource Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgModelImpl#getDurableResourceType <em>Durable Resource Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgModelImpl#getDynamicOrgReferences <em>Dynamic Org References</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OrgModelImpl extends BaseOrgModelImpl implements OrgModel {
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
     * The cached value of the '{@link #getGroups() <em>Groups</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGroups()
     * @generated
     * @ordered
     */
    protected EList<Group> groups;

    /**
     * The cached value of the '{@link #getCapabilityCategories() <em>Capability Categories</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCapabilityCategories()
     * @generated
     * @ordered
     */
    protected EList<CapabilityCategory> capabilityCategories;

    /**
     * The cached value of the '{@link #getCapabilities() <em>Capabilities</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCapabilities()
     * @generated
     * @ordered
     */
    protected EList<Capability> capabilities;

    /**
     * The cached value of the '{@link #getOrganizations() <em>Organizations</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOrganizations()
     * @generated
     * @ordered
     */
    protected EList<Organization> organizations;

    /**
     * The cached value of the '{@link #getLocations() <em>Locations</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLocations()
     * @generated
     * @ordered
     */
    protected EList<Location> locations;

    /**
     * The cached value of the '{@link #getPrivileges() <em>Privileges</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPrivileges()
     * @generated
     * @ordered
     */
    protected EList<Privilege> privileges;

    /**
     * The cached value of the '{@link #getPrivilegeCategories() <em>Privilege Categories</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPrivilegeCategories()
     * @generated
     * @ordered
     */
    protected EList<PrivilegeCategory> privilegeCategories;

    /**
     * The cached value of the '{@link #getMetamodels() <em>Metamodels</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMetamodels()
     * @generated
     * @ordered
     */
    protected EList<OrgMetaModel> metamodels;

    /**
     * The cached value of the '{@link #getResources() <em>Resources</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResources()
     * @generated
     * @ordered
     */
    protected EList<Resource> resources;

    /**
     * The cached value of the '{@link #getEmbeddedMetamodel() <em>Embedded Metamodel</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEmbeddedMetamodel()
     * @generated
     * @ordered
     */
    protected OrgMetaModel embeddedMetamodel;

    /**
     * The cached value of the '{@link #getQueries() <em>Queries</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getQueries()
     * @generated
     * @ordered
     */
    protected EList<OrgQuery> queries;

    /**
     * The cached value of the '{@link #getHumanResourceType() <em>Human Resource Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHumanResourceType()
     * @generated
     * @ordered
     */
    protected ResourceType humanResourceType;

    /**
     * The cached value of the '{@link #getConsumableResourceType() <em>Consumable Resource Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getConsumableResourceType()
     * @generated
     * @ordered
     */
    protected ResourceType consumableResourceType;

    /**
     * The cached value of the '{@link #getDurableResourceType() <em>Durable Resource Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDurableResourceType()
     * @generated
     * @ordered
     */
    protected ResourceType durableResourceType;

    /**
     * The cached value of the '{@link #getDynamicOrgReferences() <em>Dynamic Org References</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDynamicOrgReferences()
     * @generated
     * @ordered
     */
    protected EList<DynamicOrgReference> dynamicOrgReferences;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected OrgModelImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OMPackage.Literals.ORG_MODEL;
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
                            OMPackage.ORG_MODEL__PRIVILEGE_ASSOCIATIONS);
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
                            OMPackage.ORG_MODEL__SYSTEM_ACTIONS);
        }
        return systemActions;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Group> getGroups() {
        if (groups == null) {
            groups =
                    new EObjectContainmentEList<Group>(Group.class, this,
                            OMPackage.ORG_MODEL__GROUPS);
        }
        return groups;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<CapabilityCategory> getCapabilityCategories() {
        if (capabilityCategories == null) {
            capabilityCategories =
                    new EObjectContainmentEList<CapabilityCategory>(
                            CapabilityCategory.class, this,
                            OMPackage.ORG_MODEL__CAPABILITY_CATEGORIES);
        }
        return capabilityCategories;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Capability> getCapabilities() {
        if (capabilities == null) {
            capabilities =
                    new EObjectContainmentEList<Capability>(Capability.class,
                            this, OMPackage.ORG_MODEL__CAPABILITIES);
        }
        return capabilities;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Organization> getOrganizations() {
        if (organizations == null) {
            organizations =
                    new EObjectContainmentEList<Organization>(
                            Organization.class, this,
                            OMPackage.ORG_MODEL__ORGANIZATIONS);
        }
        return organizations;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Location> getLocations() {
        if (locations == null) {
            locations =
                    new EObjectContainmentEList<Location>(Location.class, this,
                            OMPackage.ORG_MODEL__LOCATIONS);
        }
        return locations;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Privilege> getPrivileges() {
        if (privileges == null) {
            privileges =
                    new EObjectContainmentEList<Privilege>(Privilege.class,
                            this, OMPackage.ORG_MODEL__PRIVILEGES);
        }
        return privileges;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<PrivilegeCategory> getPrivilegeCategories() {
        if (privilegeCategories == null) {
            privilegeCategories =
                    new EObjectContainmentEList<PrivilegeCategory>(
                            PrivilegeCategory.class, this,
                            OMPackage.ORG_MODEL__PRIVILEGE_CATEGORIES);
        }
        return privilegeCategories;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<OrgMetaModel> getMetamodels() {
        if (metamodels == null) {
            metamodels =
                    new EObjectResolvingEList<OrgMetaModel>(OrgMetaModel.class,
                            this, OMPackage.ORG_MODEL__METAMODELS);
        }
        return metamodels;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Resource> getResources() {
        if (resources == null) {
            resources =
                    new EObjectContainmentEList<Resource>(Resource.class, this,
                            OMPackage.ORG_MODEL__RESOURCES);
        }
        return resources;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OrgMetaModel getEmbeddedMetamodel() {
        return embeddedMetamodel;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetEmbeddedMetamodel(
            OrgMetaModel newEmbeddedMetamodel, NotificationChain msgs) {
        OrgMetaModel oldEmbeddedMetamodel = embeddedMetamodel;
        embeddedMetamodel = newEmbeddedMetamodel;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            OMPackage.ORG_MODEL__EMBEDDED_METAMODEL,
                            oldEmbeddedMetamodel, newEmbeddedMetamodel);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEmbeddedMetamodel(OrgMetaModel newEmbeddedMetamodel) {
        if (newEmbeddedMetamodel != embeddedMetamodel) {
            NotificationChain msgs = null;
            if (embeddedMetamodel != null)
                msgs =
                        ((InternalEObject) embeddedMetamodel)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - OMPackage.ORG_MODEL__EMBEDDED_METAMODEL,
                                        null,
                                        msgs);
            if (newEmbeddedMetamodel != null)
                msgs =
                        ((InternalEObject) newEmbeddedMetamodel)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - OMPackage.ORG_MODEL__EMBEDDED_METAMODEL,
                                        null,
                                        msgs);
            msgs = basicSetEmbeddedMetamodel(newEmbeddedMetamodel, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.ORG_MODEL__EMBEDDED_METAMODEL,
                    newEmbeddedMetamodel, newEmbeddedMetamodel));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResourceType getHumanResourceType() {
        if (humanResourceType != null && humanResourceType.eIsProxy()) {
            InternalEObject oldHumanResourceType =
                    (InternalEObject) humanResourceType;
            humanResourceType =
                    (ResourceType) eResolveProxy(oldHumanResourceType);
            if (humanResourceType != oldHumanResourceType) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            OMPackage.ORG_MODEL__HUMAN_RESOURCE_TYPE,
                            oldHumanResourceType, humanResourceType));
            }
        }
        return humanResourceType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResourceType basicGetHumanResourceType() {
        return humanResourceType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setHumanResourceType(ResourceType newHumanResourceType) {
        ResourceType oldHumanResourceType = humanResourceType;
        humanResourceType = newHumanResourceType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.ORG_MODEL__HUMAN_RESOURCE_TYPE,
                    oldHumanResourceType, humanResourceType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResourceType getConsumableResourceType() {
        if (consumableResourceType != null && consumableResourceType.eIsProxy()) {
            InternalEObject oldConsumableResourceType =
                    (InternalEObject) consumableResourceType;
            consumableResourceType =
                    (ResourceType) eResolveProxy(oldConsumableResourceType);
            if (consumableResourceType != oldConsumableResourceType) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            OMPackage.ORG_MODEL__CONSUMABLE_RESOURCE_TYPE,
                            oldConsumableResourceType, consumableResourceType));
            }
        }
        return consumableResourceType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResourceType basicGetConsumableResourceType() {
        return consumableResourceType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setConsumableResourceType(ResourceType newConsumableResourceType) {
        ResourceType oldConsumableResourceType = consumableResourceType;
        consumableResourceType = newConsumableResourceType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.ORG_MODEL__CONSUMABLE_RESOURCE_TYPE,
                    oldConsumableResourceType, consumableResourceType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResourceType getDurableResourceType() {
        if (durableResourceType != null && durableResourceType.eIsProxy()) {
            InternalEObject oldDurableResourceType =
                    (InternalEObject) durableResourceType;
            durableResourceType =
                    (ResourceType) eResolveProxy(oldDurableResourceType);
            if (durableResourceType != oldDurableResourceType) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            OMPackage.ORG_MODEL__DURABLE_RESOURCE_TYPE,
                            oldDurableResourceType, durableResourceType));
            }
        }
        return durableResourceType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResourceType basicGetDurableResourceType() {
        return durableResourceType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDurableResourceType(ResourceType newDurableResourceType) {
        ResourceType oldDurableResourceType = durableResourceType;
        durableResourceType = newDurableResourceType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.ORG_MODEL__DURABLE_RESOURCE_TYPE,
                    oldDurableResourceType, durableResourceType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<DynamicOrgReference> getDynamicOrgReferences() {
        if (dynamicOrgReferences == null) {
            dynamicOrgReferences =
                    new EObjectContainmentEList<DynamicOrgReference>(
                            DynamicOrgReference.class, this,
                            OMPackage.ORG_MODEL__DYNAMIC_ORG_REFERENCES);
        }
        return dynamicOrgReferences;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<OrgQuery> getQueries() {
        if (queries == null) {
            queries =
                    new EObjectContainmentEList<OrgQuery>(OrgQuery.class, this,
                            OMPackage.ORG_MODEL__QUERIES);
        }
        return queries;
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
        case OMPackage.ORG_MODEL__PRIVILEGE_ASSOCIATIONS:
            return ((InternalEList<?>) getPrivilegeAssociations())
                    .basicRemove(otherEnd, msgs);
        case OMPackage.ORG_MODEL__SYSTEM_ACTIONS:
            return ((InternalEList<?>) getSystemActions())
                    .basicRemove(otherEnd, msgs);
        case OMPackage.ORG_MODEL__GROUPS:
            return ((InternalEList<?>) getGroups()).basicRemove(otherEnd, msgs);
        case OMPackage.ORG_MODEL__CAPABILITY_CATEGORIES:
            return ((InternalEList<?>) getCapabilityCategories())
                    .basicRemove(otherEnd, msgs);
        case OMPackage.ORG_MODEL__CAPABILITIES:
            return ((InternalEList<?>) getCapabilities()).basicRemove(otherEnd,
                    msgs);
        case OMPackage.ORG_MODEL__ORGANIZATIONS:
            return ((InternalEList<?>) getOrganizations())
                    .basicRemove(otherEnd, msgs);
        case OMPackage.ORG_MODEL__LOCATIONS:
            return ((InternalEList<?>) getLocations()).basicRemove(otherEnd,
                    msgs);
        case OMPackage.ORG_MODEL__PRIVILEGES:
            return ((InternalEList<?>) getPrivileges()).basicRemove(otherEnd,
                    msgs);
        case OMPackage.ORG_MODEL__PRIVILEGE_CATEGORIES:
            return ((InternalEList<?>) getPrivilegeCategories())
                    .basicRemove(otherEnd, msgs);
        case OMPackage.ORG_MODEL__RESOURCES:
            return ((InternalEList<?>) getResources()).basicRemove(otherEnd,
                    msgs);
        case OMPackage.ORG_MODEL__EMBEDDED_METAMODEL:
            return basicSetEmbeddedMetamodel(null, msgs);
        case OMPackage.ORG_MODEL__QUERIES:
            return ((InternalEList<?>) getQueries())
                    .basicRemove(otherEnd, msgs);
        case OMPackage.ORG_MODEL__DYNAMIC_ORG_REFERENCES:
            return ((InternalEList<?>) getDynamicOrgReferences())
                    .basicRemove(otherEnd, msgs);
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
        case OMPackage.ORG_MODEL__PRIVILEGE_ASSOCIATIONS:
            return getPrivilegeAssociations();
        case OMPackage.ORG_MODEL__SYSTEM_ACTIONS:
            return getSystemActions();
        case OMPackage.ORG_MODEL__GROUPS:
            return getGroups();
        case OMPackage.ORG_MODEL__CAPABILITY_CATEGORIES:
            return getCapabilityCategories();
        case OMPackage.ORG_MODEL__CAPABILITIES:
            return getCapabilities();
        case OMPackage.ORG_MODEL__ORGANIZATIONS:
            return getOrganizations();
        case OMPackage.ORG_MODEL__LOCATIONS:
            return getLocations();
        case OMPackage.ORG_MODEL__PRIVILEGES:
            return getPrivileges();
        case OMPackage.ORG_MODEL__PRIVILEGE_CATEGORIES:
            return getPrivilegeCategories();
        case OMPackage.ORG_MODEL__METAMODELS:
            return getMetamodels();
        case OMPackage.ORG_MODEL__RESOURCES:
            return getResources();
        case OMPackage.ORG_MODEL__EMBEDDED_METAMODEL:
            return getEmbeddedMetamodel();
        case OMPackage.ORG_MODEL__QUERIES:
            return getQueries();
        case OMPackage.ORG_MODEL__HUMAN_RESOURCE_TYPE:
            if (resolve)
                return getHumanResourceType();
            return basicGetHumanResourceType();
        case OMPackage.ORG_MODEL__CONSUMABLE_RESOURCE_TYPE:
            if (resolve)
                return getConsumableResourceType();
            return basicGetConsumableResourceType();
        case OMPackage.ORG_MODEL__DURABLE_RESOURCE_TYPE:
            if (resolve)
                return getDurableResourceType();
            return basicGetDurableResourceType();
        case OMPackage.ORG_MODEL__DYNAMIC_ORG_REFERENCES:
            return getDynamicOrgReferences();
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
        case OMPackage.ORG_MODEL__PRIVILEGE_ASSOCIATIONS:
            getPrivilegeAssociations().clear();
            getPrivilegeAssociations()
                    .addAll((Collection<? extends PrivilegeAssociation>) newValue);
            return;
        case OMPackage.ORG_MODEL__SYSTEM_ACTIONS:
            getSystemActions().clear();
            getSystemActions()
                    .addAll((Collection<? extends SystemAction>) newValue);
            return;
        case OMPackage.ORG_MODEL__GROUPS:
            getGroups().clear();
            getGroups().addAll((Collection<? extends Group>) newValue);
            return;
        case OMPackage.ORG_MODEL__CAPABILITY_CATEGORIES:
            getCapabilityCategories().clear();
            getCapabilityCategories()
                    .addAll((Collection<? extends CapabilityCategory>) newValue);
            return;
        case OMPackage.ORG_MODEL__CAPABILITIES:
            getCapabilities().clear();
            getCapabilities()
                    .addAll((Collection<? extends Capability>) newValue);
            return;
        case OMPackage.ORG_MODEL__ORGANIZATIONS:
            getOrganizations().clear();
            getOrganizations()
                    .addAll((Collection<? extends Organization>) newValue);
            return;
        case OMPackage.ORG_MODEL__LOCATIONS:
            getLocations().clear();
            getLocations().addAll((Collection<? extends Location>) newValue);
            return;
        case OMPackage.ORG_MODEL__PRIVILEGES:
            getPrivileges().clear();
            getPrivileges().addAll((Collection<? extends Privilege>) newValue);
            return;
        case OMPackage.ORG_MODEL__PRIVILEGE_CATEGORIES:
            getPrivilegeCategories().clear();
            getPrivilegeCategories()
                    .addAll((Collection<? extends PrivilegeCategory>) newValue);
            return;
        case OMPackage.ORG_MODEL__METAMODELS:
            getMetamodels().clear();
            getMetamodels()
                    .addAll((Collection<? extends OrgMetaModel>) newValue);
            return;
        case OMPackage.ORG_MODEL__RESOURCES:
            getResources().clear();
            getResources().addAll((Collection<? extends Resource>) newValue);
            return;
        case OMPackage.ORG_MODEL__EMBEDDED_METAMODEL:
            setEmbeddedMetamodel((OrgMetaModel) newValue);
            return;
        case OMPackage.ORG_MODEL__QUERIES:
            getQueries().clear();
            getQueries().addAll((Collection<? extends OrgQuery>) newValue);
            return;
        case OMPackage.ORG_MODEL__HUMAN_RESOURCE_TYPE:
            setHumanResourceType((ResourceType) newValue);
            return;
        case OMPackage.ORG_MODEL__CONSUMABLE_RESOURCE_TYPE:
            setConsumableResourceType((ResourceType) newValue);
            return;
        case OMPackage.ORG_MODEL__DURABLE_RESOURCE_TYPE:
            setDurableResourceType((ResourceType) newValue);
            return;
        case OMPackage.ORG_MODEL__DYNAMIC_ORG_REFERENCES:
            getDynamicOrgReferences().clear();
            getDynamicOrgReferences()
                    .addAll((Collection<? extends DynamicOrgReference>) newValue);
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
        case OMPackage.ORG_MODEL__PRIVILEGE_ASSOCIATIONS:
            getPrivilegeAssociations().clear();
            return;
        case OMPackage.ORG_MODEL__SYSTEM_ACTIONS:
            getSystemActions().clear();
            return;
        case OMPackage.ORG_MODEL__GROUPS:
            getGroups().clear();
            return;
        case OMPackage.ORG_MODEL__CAPABILITY_CATEGORIES:
            getCapabilityCategories().clear();
            return;
        case OMPackage.ORG_MODEL__CAPABILITIES:
            getCapabilities().clear();
            return;
        case OMPackage.ORG_MODEL__ORGANIZATIONS:
            getOrganizations().clear();
            return;
        case OMPackage.ORG_MODEL__LOCATIONS:
            getLocations().clear();
            return;
        case OMPackage.ORG_MODEL__PRIVILEGES:
            getPrivileges().clear();
            return;
        case OMPackage.ORG_MODEL__PRIVILEGE_CATEGORIES:
            getPrivilegeCategories().clear();
            return;
        case OMPackage.ORG_MODEL__METAMODELS:
            getMetamodels().clear();
            return;
        case OMPackage.ORG_MODEL__RESOURCES:
            getResources().clear();
            return;
        case OMPackage.ORG_MODEL__EMBEDDED_METAMODEL:
            setEmbeddedMetamodel((OrgMetaModel) null);
            return;
        case OMPackage.ORG_MODEL__QUERIES:
            getQueries().clear();
            return;
        case OMPackage.ORG_MODEL__HUMAN_RESOURCE_TYPE:
            setHumanResourceType((ResourceType) null);
            return;
        case OMPackage.ORG_MODEL__CONSUMABLE_RESOURCE_TYPE:
            setConsumableResourceType((ResourceType) null);
            return;
        case OMPackage.ORG_MODEL__DURABLE_RESOURCE_TYPE:
            setDurableResourceType((ResourceType) null);
            return;
        case OMPackage.ORG_MODEL__DYNAMIC_ORG_REFERENCES:
            getDynamicOrgReferences().clear();
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
        case OMPackage.ORG_MODEL__PRIVILEGE_ASSOCIATIONS:
            return privilegeAssociations != null
                    && !privilegeAssociations.isEmpty();
        case OMPackage.ORG_MODEL__SYSTEM_ACTIONS:
            return systemActions != null && !systemActions.isEmpty();
        case OMPackage.ORG_MODEL__GROUPS:
            return groups != null && !groups.isEmpty();
        case OMPackage.ORG_MODEL__CAPABILITY_CATEGORIES:
            return capabilityCategories != null
                    && !capabilityCategories.isEmpty();
        case OMPackage.ORG_MODEL__CAPABILITIES:
            return capabilities != null && !capabilities.isEmpty();
        case OMPackage.ORG_MODEL__ORGANIZATIONS:
            return organizations != null && !organizations.isEmpty();
        case OMPackage.ORG_MODEL__LOCATIONS:
            return locations != null && !locations.isEmpty();
        case OMPackage.ORG_MODEL__PRIVILEGES:
            return privileges != null && !privileges.isEmpty();
        case OMPackage.ORG_MODEL__PRIVILEGE_CATEGORIES:
            return privilegeCategories != null
                    && !privilegeCategories.isEmpty();
        case OMPackage.ORG_MODEL__METAMODELS:
            return metamodels != null && !metamodels.isEmpty();
        case OMPackage.ORG_MODEL__RESOURCES:
            return resources != null && !resources.isEmpty();
        case OMPackage.ORG_MODEL__EMBEDDED_METAMODEL:
            return embeddedMetamodel != null;
        case OMPackage.ORG_MODEL__QUERIES:
            return queries != null && !queries.isEmpty();
        case OMPackage.ORG_MODEL__HUMAN_RESOURCE_TYPE:
            return humanResourceType != null;
        case OMPackage.ORG_MODEL__CONSUMABLE_RESOURCE_TYPE:
            return consumableResourceType != null;
        case OMPackage.ORG_MODEL__DURABLE_RESOURCE_TYPE:
            return durableResourceType != null;
        case OMPackage.ORG_MODEL__DYNAMIC_ORG_REFERENCES:
            return dynamicOrgReferences != null
                    && !dynamicOrgReferences.isEmpty();
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
        if (baseClass == AssociableWithPrivileges.class) {
            switch (derivedFeatureID) {
            case OMPackage.ORG_MODEL__PRIVILEGE_ASSOCIATIONS:
                return OMPackage.ASSOCIABLE_WITH_PRIVILEGES__PRIVILEGE_ASSOCIATIONS;
            default:
                return -1;
            }
        }
        if (baseClass == Authorizable.class) {
            switch (derivedFeatureID) {
            case OMPackage.ORG_MODEL__SYSTEM_ACTIONS:
                return OMPackage.AUTHORIZABLE__SYSTEM_ACTIONS;
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
        if (baseClass == AssociableWithPrivileges.class) {
            switch (baseFeatureID) {
            case OMPackage.ASSOCIABLE_WITH_PRIVILEGES__PRIVILEGE_ASSOCIATIONS:
                return OMPackage.ORG_MODEL__PRIVILEGE_ASSOCIATIONS;
            default:
                return -1;
            }
        }
        if (baseClass == Authorizable.class) {
            switch (baseFeatureID) {
            case OMPackage.AUTHORIZABLE__SYSTEM_ACTIONS:
                return OMPackage.ORG_MODEL__SYSTEM_ACTIONS;
            default:
                return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

} //OrgModelImpl
