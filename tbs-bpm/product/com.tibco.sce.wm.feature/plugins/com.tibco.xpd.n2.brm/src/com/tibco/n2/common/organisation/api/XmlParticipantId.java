/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.organisation.api;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Participant Id</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Identifier for a participant, by either GUID or name. 
 * 
 * Extension of XmlOrgModelVersion, which provides the organization model version in which the participant resides.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.common.organisation.api.XmlParticipantId#getGuid <em>Guid</em>}</li>
 *   <li>{@link com.tibco.n2.common.organisation.api.XmlParticipantId#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.n2.common.organisation.api.XmlParticipantId#getEntityType <em>Entity Type</em>}</li>
 *   <li>{@link com.tibco.n2.common.organisation.api.XmlParticipantId#getQualifier <em>Qualifier</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.common.organisation.api.OrganisationPackage#getXmlParticipantId()
 * @model extendedMetaData="name='XmlParticipantId' kind='elementOnly'"
 * @generated
 */
public interface XmlParticipantId extends XmlOrgModelVersion {
    /**
     * Returns the value of the '<em><b>Guid</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Globally unique identifier for the organization model entity. 
     * 
     * This value is unique across all entities. However, if the entity exists within more than one major organization model version, it will share the same GUID.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Guid</em>' attribute.
     * @see #setGuid(String)
     * @see com.tibco.n2.common.organisation.api.OrganisationPackage#getXmlParticipantId_Guid()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='guid'"
     * @generated
     */
    String getGuid();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.organisation.api.XmlParticipantId#getGuid <em>Guid</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Guid</em>' attribute.
     * @see #getGuid()
     * @generated
     */
    void setGuid(String value);

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Name of the organization model entity.
     * 
     * An entity's name will be unique within its entity type and organization model version.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see com.tibco.n2.common.organisation.api.OrganisationPackage#getXmlParticipantId_Name()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.organisation.api.XmlParticipantId#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Entity Type</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.n2.common.organisation.api.OrganisationalEntityType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Category of this organization model entity (used to improve and validate the search).
     * <!-- end-model-doc -->
     * @return the value of the '<em>Entity Type</em>' attribute.
     * @see com.tibco.n2.common.organisation.api.OrganisationalEntityType
     * @see #isSetEntityType()
     * @see #unsetEntityType()
     * @see #setEntityType(OrganisationalEntityType)
     * @see com.tibco.n2.common.organisation.api.OrganisationPackage#getXmlParticipantId_EntityType()
     * @model unsettable="true"
     *        extendedMetaData="kind='attribute' name='entity-type'"
     * @generated
     */
    OrganisationalEntityType getEntityType();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.organisation.api.XmlParticipantId#getEntityType <em>Entity Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Entity Type</em>' attribute.
     * @see com.tibco.n2.common.organisation.api.OrganisationalEntityType
     * @see #isSetEntityType()
     * @see #unsetEntityType()
     * @see #getEntityType()
     * @generated
     */
    void setEntityType(OrganisationalEntityType value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.common.organisation.api.XmlParticipantId#getEntityType <em>Entity Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetEntityType()
     * @see #getEntityType()
     * @see #setEntityType(OrganisationalEntityType)
     * @generated
     */
    void unsetEntityType();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.common.organisation.api.XmlParticipantId#getEntityType <em>Entity Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Entity Type</em>' attribute is set.
     * @see #unsetEntityType()
     * @see #getEntityType()
     * @see #setEntityType(OrganisationalEntityType)
     * @generated
     */
    boolean isSetEntityType();

    /**
     * Returns the value of the '<em><b>Qualifier</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Qualifier value. This is only applicable in certain circumstances, and only to entities of the entity-type PRIVILEGE or CAPABILITY. 
     * 
     * These entities can have an association with other entities - for example, a resource may hold many capbilities a position may have many privileges. Those associations can have a qualifying value, according to their nature, to differentiate them. 
     * 
     * For example, the privilege to sign off a purchase order may be qualified with the maximum amount for which the purchase order is made.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Qualifier</em>' attribute.
     * @see #setQualifier(String)
     * @see com.tibco.n2.common.organisation.api.OrganisationPackage#getXmlParticipantId_Qualifier()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='qualifier'"
     * @generated
     */
    String getQualifier();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.organisation.api.XmlParticipantId#getQualifier <em>Qualifier</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Qualifier</em>' attribute.
     * @see #getQualifier()
     * @generated
     */
    void setQualifier(String value);

} // XmlParticipantId
