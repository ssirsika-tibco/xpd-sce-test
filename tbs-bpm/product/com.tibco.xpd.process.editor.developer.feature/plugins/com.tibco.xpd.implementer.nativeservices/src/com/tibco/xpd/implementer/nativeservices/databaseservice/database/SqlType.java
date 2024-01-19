/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.databaseservice.database;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;
import com.tibco.xpd.implementer.nativeservices.internal.Messages;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Sql Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * Stored procedure, select or update.
 * <!-- end-model-doc -->
 * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabasePackage#getSqlType()
 * @model extendedMetaData="name='SqlType'"
 * @generated
 */
public enum SqlType implements Enumerator {
    /**
     * The '<em><b>Stored Proc</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #STORED_PROC
     * @generated NOT - Changed literal value to "Stored Procedure"
     * @ordered
     */
    STORED_PROC_LITERAL(0, Messages.SqlType_StoredProcedureType, "StoredProc"), //$NON-NLS-1$
    /**
     * The '<em><b>Select</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #SELECT
     * @generated NOT
     * @ordered
     */
    SELECT_LITERAL(1, Messages.SqlType_SelectType, "Select"), //$NON-NLS-1$
    /**
     * The '<em><b>Update</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #UPDATE
     * @generated NOT
     * @ordered
     */
    UPDATE_LITERAL(2, Messages.SqlType_UpdateType, "Update"); //$NON-NLS-1$
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>Stored Proc</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Stored Proc</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #STORED_PROC_LITERAL
     * @model name="StoredProc"
     * @generated
     * @ordered
     */
    public static final int STORED_PROC = 0;

    /**
     * The '<em><b>Select</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>SELECT</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #SELECT_LITERAL
     * @model name="Select"
     * @generated
     * @ordered
     */
    public static final int SELECT = 1;

    /**
     * The '<em><b>Update</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>UPDATE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #UPDATE_LITERAL
     * @model name="Update"
     * @generated
     * @ordered
     */
    public static final int UPDATE = 2;

    /**
     * An array of all the '<em><b>Sql Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final SqlType[] VALUES_ARRAY =
            new SqlType[] { STORED_PROC_LITERAL, SELECT_LITERAL,
                    UPDATE_LITERAL, };

    /**
     * A public read-only list of all the '<em><b>Sql Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<SqlType> VALUES =
            Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Sql Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SqlType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            SqlType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Sql Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SqlType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            SqlType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Sql Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SqlType get(int value) {
        switch (value) {
        case STORED_PROC:
            return STORED_PROC_LITERAL;
        case SELECT:
            return SELECT_LITERAL;
        case UPDATE:
            return UPDATE_LITERAL;
        }
        return null;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private final int value;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private final String name;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private final String literal;

    /**
     * Only this class can construct instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private SqlType(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getValue() {
        return value;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getLiteral() {
        return literal;
    }

    /**
     * Returns the literal value of the enumerator, which is its string representation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        return literal;
    }
}
