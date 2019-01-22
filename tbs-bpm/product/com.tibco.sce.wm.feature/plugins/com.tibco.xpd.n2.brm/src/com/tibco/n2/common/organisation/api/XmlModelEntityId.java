/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.organisation.api;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Model Entity Id</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Unique identifier for an organization model entity.
 * 
 * Used, and extended, throughout Directory Engine to identify an organization model entity. Extends XmlOrgModelVersion, which provides the organization model version attribute.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.common.organisation.api.XmlModelEntityId#getQualifierSet <em>Qualifier Set</em>}</li>
 *   <li>{@link com.tibco.n2.common.organisation.api.XmlModelEntityId#getEntityType <em>Entity Type</em>}</li>
 *   <li>{@link com.tibco.n2.common.organisation.api.XmlModelEntityId#getGuid <em>Guid</em>}</li>
 *   <li>{@link com.tibco.n2.common.organisation.api.XmlModelEntityId#getQualifier <em>Qualifier</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.common.organisation.api.OrganisationPackage#getXmlModelEntityId()
 * @model extendedMetaData="name='XmlModelEntityId' kind='elementOnly'"
 * @generated
 */
public interface XmlModelEntityId extends XmlOrgModelVersion {
    /**
     * Returns the value of the '<em><b>Qualifier Set</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.common.organisation.api.QualifierSetType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     * An optional collection of qualifying values. This overrides any qualifier attribute set on the parent element, allowing more than one value to be set. Multiple values will only be used for qualifiers of type "enumset".
     * <!-- end-model-doc -->
     * @return the value of the '<em>Qualifier Set</em>' containment reference list.
     * @see com.tibco.n2.common.organisation.api.OrganisationPackage#getXmlModelEntityId_QualifierSet()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='qualifierSet'"
     * @generated
     */
    EList<QualifierSetType> getQualifierSet();

    /**
     * Returns the value of the '<em><b>Entity Type</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.n2.common.organisation.api.OrganisationalEntityType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Enumerated value defining the type of the organization model entity.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Entity Type</em>' attribute.
     * @see com.tibco.n2.common.organisation.api.OrganisationalEntityType
     * @see #isSetEntityType()
     * @see #unsetEntityType()
     * @see #setEntityType(OrganisationalEntityType)
     * @see com.tibco.n2.common.organisation.api.OrganisationPackage#getXmlModelEntityId_EntityType()
     * @model unsettable="true" required="true"
     *        extendedMetaData="kind='attribute' name='entity-type'"
     * @generated
     */
    OrganisationalEntityType getEntityType();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.organisation.api.XmlModelEntityId#getEntityType <em>Entity Type</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.n2.common.organisation.api.XmlModelEntityId#getEntityType <em>Entity Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetEntityType()
     * @see #getEntityType()
     * @see #setEntityType(OrganisationalEntityType)
     * @generated
     */
    void unsetEntityType();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.common.organisation.api.XmlModelEntityId#getEntityType <em>Entity Type</em>}' attribute is set.
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
     * Returns the value of the '<em><b>Guid</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Globally unique ID of the organization model entity.
     * 
     * (Although this value is unique across all entities, if the entity exists within more than one major version, it will share the same GUID.)
     * <!-- end-model-doc -->
     * @return the value of the '<em>Guid</em>' attribute.
     * @see #setGuid(String)
     * @see com.tibco.n2.common.organisation.api.OrganisationPackage#getXmlModelEntityId_Guid()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='guid'"
     * @generated
     */
    String getGuid();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.organisation.api.XmlModelEntityId#getGuid <em>Guid</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Guid</em>' attribute.
     * @see #getGuid()
     * @generated
     */
    void setGuid(String value);

    /**
     * Returns the value of the '<em><b>Qualifier</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Only applicable in certain circumstances, and only to organization model entities whose entity-type is PRIVILEGE or CAPABILITY. These entities can have an association with other entities - for example, a resource may hold many capabilities, or a position may have many privileges. 
     * 
     * These associations can have a qualifying value, according to their nature, to differentiate them. For example, the PRIVILEGE to sign off a purchase order may be qualified with the maximum amount that can be signed off.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Qualifier</em>' attribute.
     * @see #setQualifier(String)
     * @see com.tibco.n2.common.organisation.api.OrganisationPackage#getXmlModelEntityId_Qualifier()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='qualifier'"
     * @generated
     */
    String getQualifier();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.organisation.api.XmlModelEntityId#getQualifier <em>Qualifier</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Qualifier</em>' attribute.
     * @see #getQualifier()
     * @generated
     */
    void setQualifier(String value);

} // XmlModelEntityId
