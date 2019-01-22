/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.organisation.api;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Resource Query</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Base type for the specification of a resource query.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.common.organisation.api.XmlResourceQuery#getQuery <em>Query</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.common.organisation.api.OrganisationPackage#getXmlResourceQuery()
 * @model extendedMetaData="name='XmlResourceQuery' kind='elementOnly'"
 * @generated
 */
public interface XmlResourceQuery extends XmlOrgModelVersion {
    /**
     * Returns the value of the '<em><b>Query</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Resource query to be executed. The query must conform to the Resource Query Language (RQL) specification. See the product documentation for detailed information about RQL syntax.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Query</em>' attribute.
     * @see #setQuery(String)
     * @see com.tibco.n2.common.organisation.api.OrganisationPackage#getXmlResourceQuery_Query()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='query'"
     * @generated
     */
    String getQuery();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.organisation.api.XmlResourceQuery#getQuery <em>Query</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Query</em>' attribute.
     * @see #getQuery()
     * @generated
     */
    void setQuery(String value);

} // XmlResourceQuery
