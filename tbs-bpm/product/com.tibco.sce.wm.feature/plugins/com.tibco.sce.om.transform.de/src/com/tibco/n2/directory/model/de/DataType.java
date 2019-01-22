/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Data Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * 
 *         Defines the range of permitted attribute data types.
 *       
 * <!-- end-model-doc -->
 * @see com.tibco.n2.directory.model.de.DePackage#getDataType()
 * @model extendedMetaData="name='DataType'"
 * @generated
 */
public enum DataType implements Enumerator {
    /**
     * The '<em><b>String</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #STRING_VALUE
     * @generated
     * @ordered
     */
    STRING(0, "String", "String"),

    /**
     * The '<em><b>Integer</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #INTEGER_VALUE
     * @generated
     * @ordered
     */
    INTEGER(1, "Integer", "Integer"),

    /**
     * The '<em><b>Decimal</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #DECIMAL_VALUE
     * @generated
     * @ordered
     */
    DECIMAL(2, "Decimal", "Decimal"),

    /**
     * The '<em><b>Date</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #DATE_VALUE
     * @generated
     * @ordered
     */
    DATE(3, "Date", "Date"),

    /**
     * The '<em><b>Time</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #TIME_VALUE
     * @generated
     * @ordered
     */
    TIME(4, "Time", "Time"),

    /**
     * The '<em><b>Date Time</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #DATE_TIME_VALUE
     * @generated
     * @ordered
     */
    DATE_TIME(5, "DateTime", "DateTime"),

    /**
     * The '<em><b>Boolean</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #BOOLEAN_VALUE
     * @generated
     * @ordered
     */
    BOOLEAN(6, "Boolean", "Boolean"),

    /**
     * The '<em><b>Enum</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ENUM_VALUE
     * @generated
     * @ordered
     */
    ENUM(7, "Enum", "Enum"),

    /**
     * The '<em><b>Enum Set</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ENUM_SET_VALUE
     * @generated
     * @ordered
     */
    ENUM_SET(8, "EnumSet", "EnumSet"), /**
     * The '<em><b>Set</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #SET_VALUE
     * @generated
     * @ordered
     */
    SET(9, "Set", "Set");

    /**
     * The '<em><b>String</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>String</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #STRING
     * @model name="String"
     * @generated
     * @ordered
     */
    public static final int STRING_VALUE = 0;

    /**
     * The '<em><b>Integer</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Integer</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #INTEGER
     * @model name="Integer"
     * @generated
     * @ordered
     */
    public static final int INTEGER_VALUE = 1;

    /**
     * The '<em><b>Decimal</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Decimal</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DECIMAL
     * @model name="Decimal"
     * @generated
     * @ordered
     */
    public static final int DECIMAL_VALUE = 2;

    /**
     * The '<em><b>Date</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Date</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DATE
     * @model name="Date"
     * @generated
     * @ordered
     */
    public static final int DATE_VALUE = 3;

    /**
     * The '<em><b>Time</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Time</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #TIME
     * @model name="Time"
     * @generated
     * @ordered
     */
    public static final int TIME_VALUE = 4;

    /**
     * The '<em><b>Date Time</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Date Time</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DATE_TIME
     * @model name="DateTime"
     * @generated
     * @ordered
     */
    public static final int DATE_TIME_VALUE = 5;

    /**
     * The '<em><b>Boolean</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Boolean</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #BOOLEAN
     * @model name="Boolean"
     * @generated
     * @ordered
     */
    public static final int BOOLEAN_VALUE = 6;

    /**
     * The '<em><b>Enum</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Enum</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ENUM
     * @model name="Enum"
     * @generated
     * @ordered
     */
    public static final int ENUM_VALUE = 7;

    /**
     * The '<em><b>Enum Set</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Enum Set</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ENUM_SET
     * @model name="EnumSet"
     * @generated
     * @ordered
     */
    public static final int ENUM_SET_VALUE = 8;

    /**
     * The '<em><b>Set</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Set</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #SET
     * @model name="Set"
     * @generated
     * @ordered
     */
    public static final int SET_VALUE = 9;

    /**
     * An array of all the '<em><b>Data Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final DataType[] VALUES_ARRAY =
        new DataType[] {
            STRING,
            INTEGER,
            DECIMAL,
            DATE,
            TIME,
            DATE_TIME,
            BOOLEAN,
            ENUM,
            ENUM_SET,
            SET,
        };

    /**
     * A public read-only list of all the '<em><b>Data Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<DataType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Data Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static DataType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            DataType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Data Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static DataType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            DataType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Data Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static DataType get(int value) {
        switch (value) {
            case STRING_VALUE: return STRING;
            case INTEGER_VALUE: return INTEGER;
            case DECIMAL_VALUE: return DECIMAL;
            case DATE_VALUE: return DATE;
            case TIME_VALUE: return TIME;
            case DATE_TIME_VALUE: return DATE_TIME;
            case BOOLEAN_VALUE: return BOOLEAN;
            case ENUM_VALUE: return ENUM;
            case ENUM_SET_VALUE: return ENUM_SET;
            case SET_VALUE: return SET;
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
    private DataType(int value, String name, String literal) {
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
    
} //DataType
