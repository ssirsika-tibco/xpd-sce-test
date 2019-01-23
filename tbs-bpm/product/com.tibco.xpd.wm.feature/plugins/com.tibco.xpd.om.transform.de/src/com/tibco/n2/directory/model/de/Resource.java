/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *         Defines a resource (human, durable or consumable) and the associations it may have with
 *         groups, positions and capabilities.
 *         This is only used for the 'system' org-model (version 0), to define the tibco-admin user.
 *         It should not be used for any other purpose.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.Resource#getChoiceGroup <em>Choice Group</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Resource#getCapabilityHeld <em>Capability Held</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Resource#getPositionHeld <em>Position Held</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Resource#getGroupHeld <em>Group Held</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Resource#getAttributeValue <em>Attribute Value</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Resource#getEndDate <em>End Date</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Resource#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Resource#getLabel <em>Label</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Resource#getLdapAlias <em>Ldap Alias</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Resource#getLdapDn <em>Ldap Dn</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Resource#getLocation <em>Location</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Resource#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Resource#getResourceType <em>Resource Type</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Resource#getStartDate <em>Start Date</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.directory.model.de.DePackage#getResource()
 * @model extendedMetaData="name='Resource' kind='elementOnly'"
 * @generated
 */
public interface Resource extends EObject {
    /**
     * Returns the value of the '<em><b>Choice Group</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Choice Group</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Choice Group</em>' attribute list.
     * @see com.tibco.n2.directory.model.de.DePackage#getResource_ChoiceGroup()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='group' name='choiceGroup:0'"
     * @generated
     */
    FeatureMap getChoiceGroup();

    /**
     * Returns the value of the '<em><b>Capability Held</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.CapabilityHolding}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Capability Held</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Capability Held</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getResource_CapabilityHeld()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='capability-held' namespace='##targetNamespace' group='#choiceGroup:0'"
     * @generated
     */
    EList<CapabilityHolding> getCapabilityHeld();

    /**
     * Returns the value of the '<em><b>Position Held</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.PositionHolding}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Position Held</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Position Held</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getResource_PositionHeld()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='position-held' namespace='##targetNamespace' group='#choiceGroup:0'"
     * @generated
     */
    EList<PositionHolding> getPositionHeld();

    /**
     * Returns the value of the '<em><b>Group Held</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.GroupHolding}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Group Held</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Group Held</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getResource_GroupHeld()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='group-held' namespace='##targetNamespace' group='#choiceGroup:0'"
     * @generated
     */
    EList<GroupHolding> getGroupHeld();

    /**
     * Returns the value of the '<em><b>Attribute Value</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.Attribute}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Attribute Value</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Attribute Value</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getResource_AttributeValue()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='attribute-value' namespace='##targetNamespace' group='#choiceGroup:0'"
     * @generated
     */
    EList<Attribute> getAttributeValue();

    /**
     * Returns the value of the '<em><b>End Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>End Date</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>End Date</em>' attribute.
     * @see #setEndDate(XMLGregorianCalendar)
     * @see com.tibco.n2.directory.model.de.DePackage#getResource_EndDate()
     * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
     *        extendedMetaData="kind='attribute' name='end-date'"
     * @generated
     */
    XMLGregorianCalendar getEndDate();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Resource#getEndDate <em>End Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>End Date</em>' attribute.
     * @see #getEndDate()
     * @generated
     */
    void setEndDate(XMLGregorianCalendar value);

    /**
     * Returns the value of the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Id</em>' attribute.
     * @see #setId(String)
     * @see com.tibco.n2.directory.model.de.DePackage#getResource_Id()
     * @model id="true" dataType="org.eclipse.emf.ecore.xml.type.ID" required="true"
     *        extendedMetaData="kind='attribute' name='id'"
     * @generated
     */
    String getId();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Resource#getId <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Id</em>' attribute.
     * @see #getId()
     * @generated
     */
    void setId(String value);

    /**
     * Returns the value of the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Label</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Label</em>' attribute.
     * @see #setLabel(String)
     * @see com.tibco.n2.directory.model.de.DePackage#getResource_Label()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='label'"
     * @generated
     */
    String getLabel();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Resource#getLabel <em>Label</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Label</em>' attribute.
     * @see #getLabel()
     * @generated
     */
    void setLabel(String value);

    /**
     * Returns the value of the '<em><b>Ldap Alias</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Ldap Alias</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Ldap Alias</em>' attribute.
     * @see #setLdapAlias(String)
     * @see com.tibco.n2.directory.model.de.DePackage#getResource_LdapAlias()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='ldap-alias'"
     * @generated
     */
    String getLdapAlias();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Resource#getLdapAlias <em>Ldap Alias</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Ldap Alias</em>' attribute.
     * @see #getLdapAlias()
     * @generated
     */
    void setLdapAlias(String value);

    /**
     * Returns the value of the '<em><b>Ldap Dn</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Ldap Dn</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Ldap Dn</em>' attribute.
     * @see #setLdapDn(String)
     * @see com.tibco.n2.directory.model.de.DePackage#getResource_LdapDn()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='ldap-dn'"
     * @generated
     */
    String getLdapDn();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Resource#getLdapDn <em>Ldap Dn</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Ldap Dn</em>' attribute.
     * @see #getLdapDn()
     * @generated
     */
    void setLdapDn(String value);

    /**
     * Returns the value of the '<em><b>Location</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Location</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Location</em>' reference.
     * @see #setLocation(Location)
     * @see com.tibco.n2.directory.model.de.DePackage#getResource_Location()
     * @model resolveProxies="false"
     *        extendedMetaData="kind='attribute' name='location'"
     * @generated
     */
    Location getLocation();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Resource#getLocation <em>Location</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Location</em>' reference.
     * @see #getLocation()
     * @generated
     */
    void setLocation(Location value);

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see com.tibco.n2.directory.model.de.DePackage#getResource_Name()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Resource#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Resource Type</b></em>' attribute.
     * The default value is <code>"human"</code>.
     * The literals are from the enumeration {@link com.tibco.n2.directory.model.de.ResourceType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resource Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Resource Type</em>' attribute.
     * @see com.tibco.n2.directory.model.de.ResourceType
     * @see #isSetResourceType()
     * @see #unsetResourceType()
     * @see #setResourceType(ResourceType)
     * @see com.tibco.n2.directory.model.de.DePackage#getResource_ResourceType()
     * @model default="human" unsettable="true"
     *        extendedMetaData="kind='attribute' name='resource-type'"
     * @generated
     */
    ResourceType getResourceType();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Resource#getResourceType <em>Resource Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Resource Type</em>' attribute.
     * @see com.tibco.n2.directory.model.de.ResourceType
     * @see #isSetResourceType()
     * @see #unsetResourceType()
     * @see #getResourceType()
     * @generated
     */
    void setResourceType(ResourceType value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.directory.model.de.Resource#getResourceType <em>Resource Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetResourceType()
     * @see #getResourceType()
     * @see #setResourceType(ResourceType)
     * @generated
     */
    void unsetResourceType();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.directory.model.de.Resource#getResourceType <em>Resource Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Resource Type</em>' attribute is set.
     * @see #unsetResourceType()
     * @see #getResourceType()
     * @see #setResourceType(ResourceType)
     * @generated
     */
    boolean isSetResourceType();

    /**
     * Returns the value of the '<em><b>Start Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Start Date</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Start Date</em>' attribute.
     * @see #setStartDate(XMLGregorianCalendar)
     * @see com.tibco.n2.directory.model.de.DePackage#getResource_StartDate()
     * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
     *        extendedMetaData="kind='attribute' name='start-date'"
     * @generated
     */
    XMLGregorianCalendar getStartDate();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Resource#getStartDate <em>Start Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Start Date</em>' attribute.
     * @see #getStartDate()
     * @generated
     */
    void setStartDate(XMLGregorianCalendar value);

} // Resource
