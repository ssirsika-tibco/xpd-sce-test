/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Base Org Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.BaseOrgModel#getVersion <em>Version</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.BaseOrgModel#getStatus <em>Status</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.BaseOrgModel#getAuthor <em>Author</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.BaseOrgModel#getDateCreated <em>Date Created</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getBaseOrgModel()
 * @model abstract="true"
 * @generated
 */
public interface BaseOrgModel extends Namespace {

    /**
     * Returns the value of the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Version</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Version</em>' attribute.
     * @see #setVersion(String)
     * @see com.tibco.xpd.om.core.om.OMPackage#getBaseOrgModel_Version()
     * @model
     * @generated
     */
    String getVersion();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.BaseOrgModel#getVersion <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Version</em>' attribute.
     * @see #getVersion()
     * @generated
     */
    void setVersion(String value);

    /**
     * Returns the value of the '<em><b>Status</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Status</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Status</em>' attribute.
     * @see #setStatus(String)
     * @see com.tibco.xpd.om.core.om.OMPackage#getBaseOrgModel_Status()
     * @model
     * @generated
     */
    String getStatus();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.BaseOrgModel#getStatus <em>Status</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Status</em>' attribute.
     * @see #getStatus()
     * @generated
     */
    void setStatus(String value);

    /**
     * Returns the value of the '<em><b>Author</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Author</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Author</em>' attribute.
     * @see #setAuthor(String)
     * @see com.tibco.xpd.om.core.om.OMPackage#getBaseOrgModel_Author()
     * @model
     * @generated
     */
    String getAuthor();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.BaseOrgModel#getAuthor <em>Author</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Author</em>' attribute.
     * @see #getAuthor()
     * @generated
     */
    void setAuthor(String value);

    /**
     * Returns the value of the '<em><b>Date Created</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Date Created</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Date Created</em>' attribute.
     * @see #setDateCreated(long)
     * @see com.tibco.xpd.om.core.om.OMPackage#getBaseOrgModel_DateCreated()
     * @model
     * @generated
     */
    long getDateCreated();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.BaseOrgModel#getDateCreated <em>Date Created</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Date Created</em>' attribute.
     * @see #getDateCreated()
     * @generated
     */
    void setDateCreated(long value);
} // BaseOrgModel
