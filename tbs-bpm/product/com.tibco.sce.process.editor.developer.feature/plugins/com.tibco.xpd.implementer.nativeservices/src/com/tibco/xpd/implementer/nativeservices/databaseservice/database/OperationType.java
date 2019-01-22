/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.databaseservice.database;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Operation Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Database operation type - can be stored procedure, Select or Update.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.OperationType#getSql <em>Sql</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.OperationType#getParameters <em>Parameters</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.OperationType#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabasePackage#getOperationType()
 * @model extendedMetaData="name='OperationType' kind='elementOnly'"
 * @generated
 */
public interface OperationType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Sql</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Sql</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Sql</em>' attribute.
     * @see #setSql(String)
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabasePackage#getOperationType_Sql()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='Sql' namespace='##targetNamespace'"
     * @generated
     */
    String getSql();

    /**
     * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.OperationType#getSql <em>Sql</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Sql</em>' attribute.
     * @see #getSql()
     * @generated
     */
    void setSql(String value);

    /**
     * Returns the value of the '<em><b>Parameters</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parameters</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Parameters</em>' containment reference.
     * @see #setParameters(ParametersType)
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabasePackage#getOperationType_Parameters()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Parameters' namespace='##targetNamespace'"
     * @generated
     */
    ParametersType getParameters();

    /**
     * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.OperationType#getParameters <em>Parameters</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Parameters</em>' containment reference.
     * @see #getParameters()
     * @generated
     */
    void setParameters(ParametersType value);

    /**
     * Returns the value of the '<em><b>Type</b></em>' attribute.
     * The default value is <code>"StoredProc"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.SqlType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Type</em>' attribute.
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.SqlType
     * @see #isSetType()
     * @see #unsetType()
     * @see #setType(SqlType)
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabasePackage#getOperationType_Type()
     * @model default="StoredProc" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='Type' namespace='##targetNamespace'"
     * @generated
     */
    SqlType getType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.OperationType#getType <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Type</em>' attribute.
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.SqlType
     * @see #isSetType()
     * @see #unsetType()
     * @see #getType()
     * @generated
     */
    void setType(SqlType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.OperationType#getType <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetType()
     * @see #getType()
     * @see #setType(SqlType)
     * @generated
     */
    void unsetType();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.OperationType#getType <em>Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Type</em>' attribute is set.
     * @see #unsetType()
     * @see #getType()
     * @see #setType(SqlType)
     * @generated
     */
    boolean isSetType();

} // OperationType