/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Config Parameter Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.deploy.DeployPackage#getConfigParameterType()
 * @model
 * @generated
 */
public enum ConfigParameterType implements Enumerator {
    /**
     * The '<em><b>STRING</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #STRING
     * @generated
     * @ordered
     */
    STRING_LITERAL(0, "STRING", "string"),
    /**
     * The '<em><b>BOOLEAN</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #BOOLEAN
     * @generated
     * @ordered
     */
    BOOLEAN_LITERAL(1, "BOOLEAN", "boolean"),
    /**
     * The '<em><b>INTEGER</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #INTEGER
     * @generated
     * @ordered
     */
    INTEGER_LITERAL(2, "INTEGER", "integer"),
    /**
     * The '<em><b>PASSWORD</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #PASSWORD
     * @generated
     * @ordered
     */
    PASSWORD_LITERAL(3, "PASSWORD", "password"), /**
     * The '<em><b>FILE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #FILE
     * @generated
     * @ordered
     */
    FILE_LITERAL(4, "FILE", "file");
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * The '<em><b>STRING</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>STRING</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #STRING_LITERAL
     * @model literal="string"
     * @generated
     * @ordered
     */
    public static final int STRING = 0;

    /**
     * The '<em><b>BOOLEAN</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>BOOLEAN</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #BOOLEAN_LITERAL
     * @model literal="boolean"
     * @generated
     * @ordered
     */
    public static final int BOOLEAN = 1;

    /**
     * The '<em><b>INTEGER</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>INTEGER</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #INTEGER_LITERAL
     * @model literal="integer"
     * @generated
     * @ordered
     */
    public static final int INTEGER = 2;

    /**
     * The '<em><b>PASSWORD</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>PASSWORD</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #PASSWORD_LITERAL
     * @model literal="password"
     * @generated
     * @ordered
     */
    public static final int PASSWORD = 3;

    /**
     * The '<em><b>FILE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>FILE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #FILE_LITERAL
     * @model literal="file"
     * @generated
     * @ordered
     */
    public static final int FILE = 4;

    /**
     * An array of all the '<em><b>Config Parameter Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final ConfigParameterType[] VALUES_ARRAY = new ConfigParameterType[] {
            STRING_LITERAL, BOOLEAN_LITERAL, INTEGER_LITERAL, PASSWORD_LITERAL,
            FILE_LITERAL, };

    /**
     * A public read-only list of all the '<em><b>Config Parameter Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<ConfigParameterType> VALUES = Collections
            .unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Config Parameter Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ConfigParameterType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ConfigParameterType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Config Parameter Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ConfigParameterType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ConfigParameterType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Config Parameter Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ConfigParameterType get(int value) {
        switch (value) {
        case STRING:
            return STRING_LITERAL;
        case BOOLEAN:
            return BOOLEAN_LITERAL;
        case INTEGER:
            return INTEGER_LITERAL;
        case PASSWORD:
            return PASSWORD_LITERAL;
        case FILE:
            return FILE_LITERAL;
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
    private ConfigParameterType(int value, String name, String literal) {
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
