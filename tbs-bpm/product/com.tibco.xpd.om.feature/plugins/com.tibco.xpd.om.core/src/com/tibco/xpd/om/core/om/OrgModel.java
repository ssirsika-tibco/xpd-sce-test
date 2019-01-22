/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Org Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgModel#getGroups <em>Groups</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgModel#getCapabilityCategories <em>Capability Categories</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgModel#getCapabilities <em>Capabilities</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgModel#getOrganizations <em>Organizations</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgModel#getLocations <em>Locations</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgModel#getPrivileges <em>Privileges</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgModel#getPrivilegeCategories <em>Privilege Categories</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgModel#getMetamodels <em>Metamodels</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgModel#getResources <em>Resources</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgModel#getEmbeddedMetamodel <em>Embedded Metamodel</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgModel#getQueries <em>Queries</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgModel#getHumanResourceType <em>Human Resource Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgModel#getConsumableResourceType <em>Consumable Resource Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgModel#getDurableResourceType <em>Durable Resource Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgModel#getDynamicOrgReferences <em>Dynamic Org References</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getOrgModel()
 * @model
 * @generated
 */
public interface OrgModel extends BaseOrgModel, Authorizable {
    /**
     * Returns the value of the '<em><b>Groups</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.Group}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Groups</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Groups</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgModel_Groups()
     * @model containment="true"
     * @generated
     */
    EList<Group> getGroups();

    /**
     * Returns the value of the '<em><b>Capability Categories</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.CapabilityCategory}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Capability Categories</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Capability Categories</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgModel_CapabilityCategories()
     * @model containment="true"
     * @generated
     */
    EList<CapabilityCategory> getCapabilityCategories();

    /**
     * Returns the value of the '<em><b>Capabilities</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.Capability}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Capabilities</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Capabilities</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgModel_Capabilities()
     * @model containment="true"
     * @generated
     */
    EList<Capability> getCapabilities();

    /**
     * Returns the value of the '<em><b>Organizations</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.Organization}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Organizations</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Organizations</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgModel_Organizations()
     * @model containment="true"
     * @generated
     */
    EList<Organization> getOrganizations();

    /**
     * Returns the value of the '<em><b>Locations</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.Location}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Locations</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Locations</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgModel_Locations()
     * @model containment="true"
     * @generated
     */
    EList<Location> getLocations();

    /**
     * Returns the value of the '<em><b>Privileges</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.Privilege}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Privileges</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Privileges</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgModel_Privileges()
     * @model containment="true"
     * @generated
     */
    EList<Privilege> getPrivileges();

    /**
     * Returns the value of the '<em><b>Privilege Categories</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.PrivilegeCategory}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Privilege Categories</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Privilege Categories</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgModel_PrivilegeCategories()
     * @model containment="true"
     * @generated
     */
    EList<PrivilegeCategory> getPrivilegeCategories();

    /**
     * Returns the value of the '<em><b>Metamodels</b></em>' reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.OrgMetaModel}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Metamodels</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Metamodels</em>' reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgModel_Metamodels()
     * @model
     * @generated
     */
    EList<OrgMetaModel> getMetamodels();

    /**
     * Returns the value of the '<em><b>Resources</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.Resource}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resources</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Resources</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgModel_Resources()
     * @model containment="true"
     * @generated
     */
    EList<Resource> getResources();

    /**
     * Returns the value of the '<em><b>Embedded Metamodel</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Embedded Metamodel</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Embedded Metamodel</em>' containment reference.
     * @see #setEmbeddedMetamodel(OrgMetaModel)
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgModel_EmbeddedMetamodel()
     * @model containment="true" required="true"
     * @generated
     */
    OrgMetaModel getEmbeddedMetamodel();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.OrgModel#getEmbeddedMetamodel <em>Embedded Metamodel</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Embedded Metamodel</em>' containment reference.
     * @see #getEmbeddedMetamodel()
     * @generated
     */
    void setEmbeddedMetamodel(OrgMetaModel value);

    /**
     * Returns the value of the '<em><b>Human Resource Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Human Resource Type</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Human Resource Type</em>' reference.
     * @see #setHumanResourceType(ResourceType)
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgModel_HumanResourceType()
     * @model
     * @generated
     */
    ResourceType getHumanResourceType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.OrgModel#getHumanResourceType <em>Human Resource Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Human Resource Type</em>' reference.
     * @see #getHumanResourceType()
     * @generated
     */
    void setHumanResourceType(ResourceType value);

    /**
     * Returns the value of the '<em><b>Consumable Resource Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Consumable Resource Type</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Consumable Resource Type</em>' reference.
     * @see #setConsumableResourceType(ResourceType)
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgModel_ConsumableResourceType()
     * @model
     * @generated
     */
    ResourceType getConsumableResourceType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.OrgModel#getConsumableResourceType <em>Consumable Resource Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Consumable Resource Type</em>' reference.
     * @see #getConsumableResourceType()
     * @generated
     */
    void setConsumableResourceType(ResourceType value);

    /**
     * Returns the value of the '<em><b>Durable Resource Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Durable Resource Type</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Durable Resource Type</em>' reference.
     * @see #setDurableResourceType(ResourceType)
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgModel_DurableResourceType()
     * @model
     * @generated
     */
    ResourceType getDurableResourceType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.OrgModel#getDurableResourceType <em>Durable Resource Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Durable Resource Type</em>' reference.
     * @see #getDurableResourceType()
     * @generated
     */
    void setDurableResourceType(ResourceType value);

    /**
     * Returns the value of the '<em><b>Dynamic Org References</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.DynamicOrgReference}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Dynamic Org References</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Dynamic Org References</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgModel_DynamicOrgReferences()
     * @model containment="true"
     * @generated
     */
    EList<DynamicOrgReference> getDynamicOrgReferences();

    /**
     * Returns the value of the '<em><b>Queries</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.OrgQuery}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Queries</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Queries</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgModel_Queries()
     * @model containment="true"
     * @generated
     */
    EList<OrgQuery> getQueries();

} // OrgModel
