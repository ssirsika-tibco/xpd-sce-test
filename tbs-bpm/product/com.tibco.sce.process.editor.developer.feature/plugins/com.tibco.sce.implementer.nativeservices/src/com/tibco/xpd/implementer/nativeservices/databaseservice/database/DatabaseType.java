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
 * A representation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Database object that defines the server, database(schema) and the stored procedure.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabaseType#getOperation <em>Operation</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabasePackage#getDatabaseType()
 * @model extendedMetaData="name='DatabaseType' kind='elementOnly'"
 * @generated
 */
public interface DatabaseType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Operation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Operation</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Operation</em>' containment reference.
     * @see #setOperation(OperationType)
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabasePackage#getDatabaseType_Operation()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='Operation' namespace='##targetNamespace'"
     * @generated
     */
    OperationType getOperation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabaseType#getOperation <em>Operation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Operation</em>' containment reference.
     * @see #getOperation()
     * @generated
     */
    void setOperation(OperationType value);

} // DatabaseType