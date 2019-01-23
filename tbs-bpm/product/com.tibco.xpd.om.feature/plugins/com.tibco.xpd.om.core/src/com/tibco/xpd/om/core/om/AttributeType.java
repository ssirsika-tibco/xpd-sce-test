/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '
 * <em><b>Attribute Type</b></em>', and utility methods for working with them.
 * <!-- end-user-doc -->
 * 
 * @see com.tibco.xpd.om.core.om.OMPackage#getAttributeType()
 * @model
 * @generated
 */
public enum AttributeType implements Enumerator {
    /**
     * The '<em><b>Text</b></em>' literal object. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #TEXT_VALUE
     * @generated
     * @ordered
     */
    TEXT(0, "Text", Messages.AttributeType_Text), //$NON-NLS-1$ 

    /**
     * The '<em><b>Integer</b></em>' literal object. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @see #INTEGER_VALUE
     * @generated
     * @ordered
     */
    INTEGER(1, "Integer", Messages.AttributeType_Integer), //$NON-NLS-1$ 

    /**
     * The '<em><b>Boolean</b></em>' literal object. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @see #BOOLEAN_VALUE
     * @generated
     * @ordered
     */
    BOOLEAN(2, "Boolean", Messages.AttributeType_Boolean), //$NON-NLS-1$ 

    /**
     * The '<em><b>Decimal</b></em>' literal object. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @see #DECIMAL_VALUE
     * @generated
     * @ordered
     */
    DECIMAL(3, "Decimal", Messages.AttributeType_Decimal), //$NON-NLS-1$  
    /**
     * The '<em><b>Date Time</b></em>' literal object. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @see #DATE_TIME_VALUE
     * @generated
     * @ordered
     */
    DATE_TIME(4, "DateTime", Messages.AttributeType_DateTime), //$NON-NLS-1$ 
    /**
     * The '<em><b>Date</b></em>' literal object. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #DATE_VALUE
     * @generated
     * @ordered
     */
    DATE(5, "Date", Messages.AttributeType_Date), //$NON-NLS-1$ 
    /**
     * The '<em><b>Time</b></em>' literal object. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #TIME_VALUE
     * @generated
     * @ordered
     */
    TIME(6, "Time", Messages.AttributeType_Time), //$NON-NLS-1$ 

    /**
     * The '<em><b>Enum</b></em>' literal object. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #ENUM_VALUE
     * @generated
     * @ordered
     */
    ENUM(7, "Enum", Messages.AttributeType_Enum), //$NON-NLS-1$ 

    /**
     * The '<em><b>Enum Set</b></em>' literal object. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @see #ENUM_SET_VALUE
     * @generated
     * @ordered
     */
    ENUM_SET(8, "EnumSet", Messages.AttributeType_EnumSet), //$NON-NLS-1$ 

    /**
     * The '<em><b>Set</b></em>' literal object. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #SET_VALUE
     * @generated
     * @ordered
     */
    SET(9, "Set", Messages.AttributeType_Set); //$NON-NLS-1$ 

    /**
     * The '<em><b>Text</b></em>' literal value. <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Text</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @see #TEXT
     * @model name="Text"
     * @generated
     * @ordered
     */
    public static final int TEXT_VALUE = 0;

    /**
     * The '<em><b>Integer</b></em>' literal value. <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Integer</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @see #INTEGER
     * @model name="Integer"
     * @generated
     * @ordered
     */
    public static final int INTEGER_VALUE = 1;

    /**
     * The '<em><b>Boolean</b></em>' literal value. <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Boolean</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @see #BOOLEAN
     * @model name="Boolean"
     * @generated
     * @ordered
     */
    public static final int BOOLEAN_VALUE = 2;

    /**
     * The '<em><b>Decimal</b></em>' literal value. <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Decimal</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @see #DECIMAL
     * @model name="Decimal"
     * @generated
     * @ordered
     */
    public static final int DECIMAL_VALUE = 3;

    /**
     * The '<em><b>Date Time</b></em>' literal value. <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Date Time</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @see #DATE_TIME
     * @model name="DateTime"
     * @generated
     * @ordered
     */
    public static final int DATE_TIME_VALUE = 4;

    /**
     * The '<em><b>Date</b></em>' literal value. <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Date</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @see #DATE
     * @model name="Date"
     * @generated
     * @ordered
     */
    public static final int DATE_VALUE = 5;

    /**
     * The '<em><b>Time</b></em>' literal value. <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Time</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @see #TIME
     * @model name="Time"
     * @generated
     * @ordered
     */
    public static final int TIME_VALUE = 6;

    /**
     * The '<em><b>Enum</b></em>' literal value. <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Enum</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @see #ENUM
     * @model name="Enum"
     * @generated
     * @ordered
     */
    public static final int ENUM_VALUE = 7;

    /**
     * The '<em><b>Enum Set</b></em>' literal value. <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Enum Set</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @see #ENUM_SET
     * @model name="EnumSet"
     * @generated
     * @ordered
     */
    public static final int ENUM_SET_VALUE = 8;

    /**
     * The '<em><b>Set</b></em>' literal value. <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Set</b></em>' literal object isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @see #SET
     * @model name="Set"
     * @generated
     * @ordered
     */
    public static final int SET_VALUE = 9;

    /**
     * An array of all the '<em><b>Attribute Type</b></em>' enumerators. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private static final AttributeType[] VALUES_ARRAY = new AttributeType[] {
            TEXT, INTEGER, BOOLEAN, DECIMAL, DATE_TIME, DATE, TIME, ENUM,
            ENUM_SET, SET, };

    /**
     * A public read-only list of all the '<em><b>Attribute Type</b></em>'
     * enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final List<AttributeType> VALUES = Collections
            .unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Attribute Type</b></em>' literal with the specified
     * literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
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
     * Returns the '<em><b>Attribute Type</b></em>' literal with the specified
     * name. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
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
     * Returns the '<em><b>Attribute Type</b></em>' literal with the specified
     * integer value. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static AttributeType get(int value) {
        switch (value) {
        case TEXT_VALUE:
            return TEXT;
        case INTEGER_VALUE:
            return INTEGER;
        case BOOLEAN_VALUE:
            return BOOLEAN;
        case DECIMAL_VALUE:
            return DECIMAL;
        case DATE_TIME_VALUE:
            return DATE_TIME;
        case DATE_VALUE:
            return DATE;
        case TIME_VALUE:
            return TIME;
        case ENUM_VALUE:
            return ENUM;
        case ENUM_SET_VALUE:
            return ENUM_SET;
        case SET_VALUE:
            return SET;
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private final int value;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private final String name;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private final String literal;

    /**
     * Only this class can construct instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    private AttributeType(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public int getValue() {
        return value;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String getLiteral() {
        return literal;
    }

    /**
     * Returns the literal value of the enumerator, which is its string
     * representation. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {
        return literal;
    }

} // AttributeType
