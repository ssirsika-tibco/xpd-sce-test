/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.organisation.api;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Execute Query</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Definition of an executable resource query, used to identify a collection of resources that match the criteria specified in the query.
 * 
 * Extension of XmlOrgModelVersion, which provides the organization model version in which the resources to be queried reside.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.common.organisation.api.XmlExecuteQuery#getImplementation <em>Implementation</em>}</li>
 *   <li>{@link com.tibco.n2.common.organisation.api.XmlExecuteQuery#isSingleRandomResult <em>Single Random Result</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.common.organisation.api.OrganisationPackage#getXmlExecuteQuery()
 * @model extendedMetaData="name='XmlExecuteQuery' kind='elementOnly'"
 * @generated
 */
public interface XmlExecuteQuery extends XmlResourceQuery {
    /**
     * Returns the value of the '<em><b>Implementation</b></em>' attribute.
     * The default value is <code>"1"</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Identifies the implementation version, and governs what style of response will be generated. This allows for the XML schema of the response to vary but remain backward compatible.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Implementation</em>' attribute.
     * @see #isSetImplementation()
     * @see #unsetImplementation()
     * @see #setImplementation(int)
     * @see com.tibco.n2.common.organisation.api.OrganisationPackage#getXmlExecuteQuery_Implementation()
     * @model default="1" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='implementation'"
     * @generated
     */
    int getImplementation();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.organisation.api.XmlExecuteQuery#getImplementation <em>Implementation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Implementation</em>' attribute.
     * @see #isSetImplementation()
     * @see #unsetImplementation()
     * @see #getImplementation()
     * @generated
     */
    void setImplementation(int value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.common.organisation.api.XmlExecuteQuery#getImplementation <em>Implementation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetImplementation()
     * @see #getImplementation()
     * @see #setImplementation(int)
     * @generated
     */
    void unsetImplementation();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.common.organisation.api.XmlExecuteQuery#getImplementation <em>Implementation</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Implementation</em>' attribute is set.
     * @see #unsetImplementation()
     * @see #getImplementation()
     * @see #setImplementation(int)
     * @generated
     */
    boolean isSetImplementation();

    /**
     * Returns the value of the '<em><b>Single Random Result</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Boolean value specifying whether the query should return a single, randomly selected user from the result set (TRUE), or all the resources in the result set (FALSE).
     * <!-- end-model-doc -->
     * @return the value of the '<em>Single Random Result</em>' attribute.
     * @see #isSetSingleRandomResult()
     * @see #unsetSingleRandomResult()
     * @see #setSingleRandomResult(boolean)
     * @see com.tibco.n2.common.organisation.api.OrganisationPackage#getXmlExecuteQuery_SingleRandomResult()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='singleRandomResult'"
     * @generated
     */
    boolean isSingleRandomResult();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.organisation.api.XmlExecuteQuery#isSingleRandomResult <em>Single Random Result</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Single Random Result</em>' attribute.
     * @see #isSetSingleRandomResult()
     * @see #unsetSingleRandomResult()
     * @see #isSingleRandomResult()
     * @generated
     */
    void setSingleRandomResult(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.common.organisation.api.XmlExecuteQuery#isSingleRandomResult <em>Single Random Result</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetSingleRandomResult()
     * @see #isSingleRandomResult()
     * @see #setSingleRandomResult(boolean)
     * @generated
     */
    void unsetSingleRandomResult();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.common.organisation.api.XmlExecuteQuery#isSingleRandomResult <em>Single Random Result</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Single Random Result</em>' attribute is set.
     * @see #unsetSingleRandomResult()
     * @see #isSingleRandomResult()
     * @see #setSingleRandomResult(boolean)
     * @generated
     */
    boolean isSetSingleRandomResult();

} // XmlExecuteQuery
