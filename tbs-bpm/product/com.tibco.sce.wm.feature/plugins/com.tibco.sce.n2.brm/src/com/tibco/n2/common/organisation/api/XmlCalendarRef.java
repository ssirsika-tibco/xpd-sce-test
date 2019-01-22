/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.organisation.api;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Calendar Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Identifies the assignment of a calendar to an organizational entity (group, position, organization unit, or organization).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.common.organisation.api.XmlCalendarRef#getCalendarAlias <em>Calendar Alias</em>}</li>
 *   <li>{@link com.tibco.n2.common.organisation.api.XmlCalendarRef#getEntityGuid <em>Entity Guid</em>}</li>
 *   <li>{@link com.tibco.n2.common.organisation.api.XmlCalendarRef#getEntityLabel <em>Entity Label</em>}</li>
 *   <li>{@link com.tibco.n2.common.organisation.api.XmlCalendarRef#getEntityName <em>Entity Name</em>}</li>
 *   <li>{@link com.tibco.n2.common.organisation.api.XmlCalendarRef#getEntityType <em>Entity Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.common.organisation.api.OrganisationPackage#getXmlCalendarRef()
 * @model extendedMetaData="name='XmlCalendarRef' kind='empty'"
 * @generated
 */
public interface XmlCalendarRef extends XmlOrgModelVersion {
    /**
     * Returns the value of the '<em><b>Calendar Alias</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The alias by which the calendar is referenced by the organization model entity.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Calendar Alias</em>' attribute.
     * @see #setCalendarAlias(String)
     * @see com.tibco.n2.common.organisation.api.OrganisationPackage#getXmlCalendarRef_CalendarAlias()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='calendar-alias'"
     * @generated
     */
    String getCalendarAlias();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.organisation.api.XmlCalendarRef#getCalendarAlias <em>Calendar Alias</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Calendar Alias</em>' attribute.
     * @see #getCalendarAlias()
     * @generated
     */
    void setCalendarAlias(String value);

    /**
     * Returns the value of the '<em><b>Entity Guid</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The unique identifier of the organization model entity to which the calendar reference belongs.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Entity Guid</em>' attribute.
     * @see #setEntityGuid(String)
     * @see com.tibco.n2.common.organisation.api.OrganisationPackage#getXmlCalendarRef_EntityGuid()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='entity-guid'"
     * @generated
     */
    String getEntityGuid();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.organisation.api.XmlCalendarRef#getEntityGuid <em>Entity Guid</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Entity Guid</em>' attribute.
     * @see #getEntityGuid()
     * @generated
     */
    void setEntityGuid(String value);

    /**
     * Returns the value of the '<em><b>Entity Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The display name of the organization model entity to which the calendar reference belongs. If this is not present, the entity-name value is used for display.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Entity Label</em>' attribute.
     * @see #setEntityLabel(String)
     * @see com.tibco.n2.common.organisation.api.OrganisationPackage#getXmlCalendarRef_EntityLabel()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='entity-label'"
     * @generated
     */
    String getEntityLabel();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.organisation.api.XmlCalendarRef#getEntityLabel <em>Entity Label</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Entity Label</em>' attribute.
     * @see #getEntityLabel()
     * @generated
     */
    void setEntityLabel(String value);

    /**
     * Returns the value of the '<em><b>Entity Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The name of the organization model entity to which the calendar reference belongs.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Entity Name</em>' attribute.
     * @see #setEntityName(String)
     * @see com.tibco.n2.common.organisation.api.OrganisationPackage#getXmlCalendarRef_EntityName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='entity-name'"
     * @generated
     */
    String getEntityName();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.organisation.api.XmlCalendarRef#getEntityName <em>Entity Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Entity Name</em>' attribute.
     * @see #getEntityName()
     * @generated
     */
    void setEntityName(String value);

    /**
     * Returns the value of the '<em><b>Entity Type</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.n2.common.organisation.api.OrganisationalEntityType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The type identifier of the organization model entity to which the calendar reference belongs.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Entity Type</em>' attribute.
     * @see com.tibco.n2.common.organisation.api.OrganisationalEntityType
     * @see #isSetEntityType()
     * @see #unsetEntityType()
     * @see #setEntityType(OrganisationalEntityType)
     * @see com.tibco.n2.common.organisation.api.OrganisationPackage#getXmlCalendarRef_EntityType()
     * @model unsettable="true" required="true"
     *        extendedMetaData="kind='attribute' name='entity-type'"
     * @generated
     */
    OrganisationalEntityType getEntityType();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.organisation.api.XmlCalendarRef#getEntityType <em>Entity Type</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.n2.common.organisation.api.XmlCalendarRef#getEntityType <em>Entity Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetEntityType()
     * @see #getEntityType()
     * @see #setEntityType(OrganisationalEntityType)
     * @generated
     */
    void unsetEntityType();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.common.organisation.api.XmlCalendarRef#getEntityType <em>Entity Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Entity Type</em>' attribute is set.
     * @see #unsetEntityType()
     * @see #getEntityType()
     * @see #setEntityType(OrganisationalEntityType)
     * @generated
     */
    boolean isSetEntityType();

} // XmlCalendarRef
