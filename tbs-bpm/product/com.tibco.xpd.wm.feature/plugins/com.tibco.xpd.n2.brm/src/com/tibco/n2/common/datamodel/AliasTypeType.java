/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.datamodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Alias Type Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.n2.common.datamodel.DatamodelPackage#getAliasTypeType()
 * @model extendedMetaData="name='aliasType_._type'"
 * @generated
 */
public enum AliasTypeType implements Enumerator {
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
     * The '<em><b>Decimal Number</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #DECIMAL_NUMBER_VALUE
     * @generated
     * @ordered
     */
    DECIMAL_NUMBER(1, "DecimalNumber", "Decimal Number"),

    /**
     * The '<em><b>Integer Number</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #INTEGER_NUMBER_VALUE
     * @generated
     * @ordered
     */
    INTEGER_NUMBER(2, "IntegerNumber", "Integer Number"),

    /**
     * The '<em><b>Date Time</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #DATE_TIME_VALUE
     * @generated
     * @ordered
     */
    DATE_TIME(3, "DateTime", "Date Time");

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
     * The '<em><b>Decimal Number</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Decimal Number</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DECIMAL_NUMBER
     * @model name="DecimalNumber" literal="Decimal Number"
     * @generated
     * @ordered
     */
    public static final int DECIMAL_NUMBER_VALUE = 1;

    /**
     * The '<em><b>Integer Number</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Integer Number</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #INTEGER_NUMBER
     * @model name="IntegerNumber" literal="Integer Number"
     * @generated
     * @ordered
     */
    public static final int INTEGER_NUMBER_VALUE = 2;

    /**
     * The '<em><b>Date Time</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Date Time</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DATE_TIME
     * @model name="DateTime" literal="Date Time"
     * @generated
     * @ordered
     */
    public static final int DATE_TIME_VALUE = 3;

    /**
     * An array of all the '<em><b>Alias Type Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final AliasTypeType[] VALUES_ARRAY =
        new AliasTypeType[] {
            STRING,
            DECIMAL_NUMBER,
            INTEGER_NUMBER,
            DATE_TIME,
        };

    /**
     * A public read-only list of all the '<em><b>Alias Type Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<AliasTypeType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Alias Type Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static AliasTypeType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            AliasTypeType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Alias Type Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static AliasTypeType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            AliasTypeType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Alias Type Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static AliasTypeType get(int value) {
        switch (value) {
            case STRING_VALUE: return STRING;
            case DECIMAL_NUMBER_VALUE: return DECIMAL_NUMBER;
            case INTEGER_NUMBER_VALUE: return INTEGER_NUMBER;
            case DATE_TIME_VALUE: return DATE_TIME;
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
    private AliasTypeType(int value, String name, String literal) {
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
    
} //AliasTypeType
