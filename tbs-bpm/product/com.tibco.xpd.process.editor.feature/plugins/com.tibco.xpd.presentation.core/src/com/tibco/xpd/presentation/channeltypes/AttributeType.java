/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channeltypes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Attribute Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getAttributeType()
 * @model
 * @generated
 */
public enum AttributeType implements Enumerator {
    /**
     * The '<em><b>Text</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #TEXT_VALUE
     * @generated
     * @ordered
     */
    TEXT(0, "Text", "Text"),

    /**
     * The '<em><b>Decimal</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #DECIMAL_VALUE
     * @generated
     * @ordered
     */
    DECIMAL(1, "Decimal", "Decimal"),

    /**
     * The '<em><b>Integer</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #INTEGER_VALUE
     * @generated
     * @ordered
     */
    INTEGER(2, "Integer", "Integer"),

    /**
     * The '<em><b>Boolean</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #BOOLEAN_VALUE
     * @generated
     * @ordered
     */
    BOOLEAN(3, "Boolean", "Boolean"),

    /**
     * The '<em><b>Enum</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ENUM_VALUE
     * @generated
     * @ordered
     */
    ENUM(4, "Enum", "Enum"),

    /**
     * The '<em><b>Enum Set</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ENUM_SET_VALUE
     * @generated
     * @ordered
     */
    ENUM_SET(5, "EnumSet", "EnumSet"), /**
     * The '<em><b>Resource</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #RESOURCE_VALUE
     * @generated
     * @ordered
     */
    RESOURCE(6, "Resource", "Resource");

    /**
     * The '<em><b>Text</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Text</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #TEXT
     * @model name="Text"
     * @generated
     * @ordered
     */
    public static final int TEXT_VALUE = 0;

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
    public static final int DECIMAL_VALUE = 1;

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
    public static final int INTEGER_VALUE = 2;

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
    public static final int BOOLEAN_VALUE = 3;

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
    public static final int ENUM_VALUE = 4;

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
    public static final int ENUM_SET_VALUE = 5;

    /**
     * The '<em><b>Resource</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Resource</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #RESOURCE
     * @model name="Resource"
     * @generated
     * @ordered
     */
    public static final int RESOURCE_VALUE = 6;

    /**
     * An array of all the '<em><b>Attribute Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final AttributeType[] VALUES_ARRAY =
        new AttributeType[] {
            TEXT,
            DECIMAL,
            INTEGER,
            BOOLEAN,
            ENUM,
            ENUM_SET,
            RESOURCE,
        };

    /**
     * A public read-only list of all the '<em><b>Attribute Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<AttributeType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Attribute Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static AttributeType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            AttributeType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Attribute Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static AttributeType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            AttributeType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Attribute Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static AttributeType get(int value) {
        switch (value) {
            case TEXT_VALUE: return TEXT;
            case DECIMAL_VALUE: return DECIMAL;
            case INTEGER_VALUE: return INTEGER;
            case BOOLEAN_VALUE: return BOOLEAN;
            case ENUM_VALUE: return ENUM;
            case ENUM_SET_VALUE: return ENUM_SET;
            case RESOURCE_VALUE: return RESOURCE;
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
    private AttributeType(int value, String name, String literal) {
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
    
} //AttributeType
