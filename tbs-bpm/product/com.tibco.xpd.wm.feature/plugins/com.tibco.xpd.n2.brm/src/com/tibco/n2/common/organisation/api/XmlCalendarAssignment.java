/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.organisation.api;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Calendar Assignment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Identifies the assignment of the given calendar alias to the identified organization model entity.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.common.organisation.api.XmlCalendarAssignment#getCalendarAlias <em>Calendar Alias</em>}</li>
 *   <li>{@link com.tibco.n2.common.organisation.api.XmlCalendarAssignment#getEntityGuid <em>Entity Guid</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.common.organisation.api.OrganisationPackage#getXmlCalendarAssignment()
 * @model extendedMetaData="name='XmlCalendarAssignment' kind='empty'"
 * @generated
 */
public interface XmlCalendarAssignment extends XmlOrgModelVersion {
    /**
     * Returns the value of the '<em><b>Calendar Alias</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The calendar alias to be assigned to the organization model entity. If not specified, or an empty string, any existing calendar assignment will be removed.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Calendar Alias</em>' attribute.
     * @see #setCalendarAlias(String)
     * @see com.tibco.n2.common.organisation.api.OrganisationPackage#getXmlCalendarAssignment_CalendarAlias()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='calendar-alias'"
     * @generated
     */
    String getCalendarAlias();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.organisation.api.XmlCalendarAssignment#getCalendarAlias <em>Calendar Alias</em>}' attribute.
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
     * The unique identifier of the organization model entity to which the calendar reference is to be assigned.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Entity Guid</em>' attribute.
     * @see #setEntityGuid(String)
     * @see com.tibco.n2.common.organisation.api.OrganisationPackage#getXmlCalendarAssignment_EntityGuid()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='entity-guid'"
     * @generated
     */
    String getEntityGuid();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.organisation.api.XmlCalendarAssignment#getEntityGuid <em>Entity Guid</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Entity Guid</em>' attribute.
     * @see #getEntityGuid()
     * @generated
     */
    void setEntityGuid(String value);

} // XmlCalendarAssignment
