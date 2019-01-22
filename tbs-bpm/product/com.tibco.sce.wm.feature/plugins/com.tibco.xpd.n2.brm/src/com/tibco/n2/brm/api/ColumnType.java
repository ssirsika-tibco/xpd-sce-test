/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Column Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * Enumerated value defining the type of a work list (e.g. COL_NUMERIC, COL_DATETIME).
 * <!-- end-model-doc -->
 * @see com.tibco.n2.brm.api.N2BRMPackage#getColumnType()
 * @model extendedMetaData="name='ColumnType'"
 * @generated
 */
public enum ColumnType implements Enumerator {
    /**
     * The '<em><b>COLUNSPECIFIED</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #COLUNSPECIFIED_VALUE
     * @generated
     * @ordered
     */
    COLUNSPECIFIED(0, "COLUNSPECIFIED", "COL_UNSPECIFIED"),

    /**
     * The '<em><b>COLNUMERIC</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #COLNUMERIC_VALUE
     * @generated
     * @ordered
     */
    COLNUMERIC(1, "COLNUMERIC", "COL_NUMERIC"),

    /**
     * The '<em><b>COLSTRING</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #COLSTRING_VALUE
     * @generated
     * @ordered
     */
    COLSTRING(2, "COLSTRING", "COL_STRING"),

    /**
     * The '<em><b>COLDATETIME</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #COLDATETIME_VALUE
     * @generated
     * @ordered
     */
    COLDATETIME(3, "COLDATETIME", "COL_DATETIME"),

    /**
     * The '<em><b>COLBOOLEAN</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #COLBOOLEAN_VALUE
     * @generated
     * @ordered
     */
    COLBOOLEAN(4, "COLBOOLEAN", "COL_BOOLEAN"),

    /**
     * The '<em><b>COLSTATE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #COLSTATE_VALUE
     * @generated
     * @ordered
     */
    COLSTATE(5, "COLSTATE", "COL_STATE"),

    /**
     * The '<em><b>COLDISTSTRATEGY</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #COLDISTSTRATEGY_VALUE
     * @generated
     * @ordered
     */
    COLDISTSTRATEGY(6, "COLDISTSTRATEGY", "COL_DISTSTRATEGY");

    /**
     * The '<em><b>COLUNSPECIFIED</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>COLUNSPECIFIED</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #COLUNSPECIFIED
     * @model literal="COL_UNSPECIFIED"
     * @generated
     * @ordered
     */
    public static final int COLUNSPECIFIED_VALUE = 0;

    /**
     * The '<em><b>COLNUMERIC</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>COLNUMERIC</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #COLNUMERIC
     * @model literal="COL_NUMERIC"
     * @generated
     * @ordered
     */
    public static final int COLNUMERIC_VALUE = 1;

    /**
     * The '<em><b>COLSTRING</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>COLSTRING</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #COLSTRING
     * @model literal="COL_STRING"
     * @generated
     * @ordered
     */
    public static final int COLSTRING_VALUE = 2;

    /**
     * The '<em><b>COLDATETIME</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>COLDATETIME</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #COLDATETIME
     * @model literal="COL_DATETIME"
     * @generated
     * @ordered
     */
    public static final int COLDATETIME_VALUE = 3;

    /**
     * The '<em><b>COLBOOLEAN</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>COLBOOLEAN</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #COLBOOLEAN
     * @model literal="COL_BOOLEAN"
     * @generated
     * @ordered
     */
    public static final int COLBOOLEAN_VALUE = 4;

    /**
     * The '<em><b>COLSTATE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>COLSTATE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #COLSTATE
     * @model literal="COL_STATE"
     * @generated
     * @ordered
     */
    public static final int COLSTATE_VALUE = 5;

    /**
     * The '<em><b>COLDISTSTRATEGY</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>COLDISTSTRATEGY</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #COLDISTSTRATEGY
     * @model literal="COL_DISTSTRATEGY"
     * @generated
     * @ordered
     */
    public static final int COLDISTSTRATEGY_VALUE = 6;

    /**
     * An array of all the '<em><b>Column Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final ColumnType[] VALUES_ARRAY =
        new ColumnType[] {
            COLUNSPECIFIED,
            COLNUMERIC,
            COLSTRING,
            COLDATETIME,
            COLBOOLEAN,
            COLSTATE,
            COLDISTSTRATEGY,
        };

    /**
     * A public read-only list of all the '<em><b>Column Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<ColumnType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Column Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ColumnType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ColumnType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Column Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ColumnType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ColumnType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Column Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ColumnType get(int value) {
        switch (value) {
            case COLUNSPECIFIED_VALUE: return COLUNSPECIFIED;
            case COLNUMERIC_VALUE: return COLNUMERIC;
            case COLSTRING_VALUE: return COLSTRING;
            case COLDATETIME_VALUE: return COLDATETIME;
            case COLBOOLEAN_VALUE: return COLBOOLEAN;
            case COLSTATE_VALUE: return COLSTATE;
            case COLDISTSTRATEGY_VALUE: return COLDISTSTRATEGY;
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
    private ColumnType(int value, String name, String literal) {
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
    
} //ColumnType
