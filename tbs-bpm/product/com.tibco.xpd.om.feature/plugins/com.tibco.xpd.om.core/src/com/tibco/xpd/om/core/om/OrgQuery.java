/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Org Query</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgQuery#getGrammar <em>Grammar</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgQuery#getQuery <em>Query</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getOrgQuery()
 * @model
 * @generated
 */
public interface OrgQuery extends Namespace {
    /**
     * Returns the value of the '<em><b>Grammar</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Grammar</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Grammar</em>' attribute.
     * @see #setGrammar(String)
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgQuery_Grammar()
     * @model
     * @generated
     */
    String getGrammar();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.OrgQuery#getGrammar <em>Grammar</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Grammar</em>' attribute.
     * @see #getGrammar()
     * @generated
     */
    void setGrammar(String value);

    /**
     * Returns the value of the '<em><b>Query</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Query</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Query</em>' attribute.
     * @see #setQuery(String)
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgQuery_Query()
     * @model
     * @generated
     */
    String getQuery();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.OrgQuery#getQuery <em>Query</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Query</em>' attribute.
     * @see #getQuery()
     * @generated
     */
    void setQuery(String value);

} // OrgQuery
