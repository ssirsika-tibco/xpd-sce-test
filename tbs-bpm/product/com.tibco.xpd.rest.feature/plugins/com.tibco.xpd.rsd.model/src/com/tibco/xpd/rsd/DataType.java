/**
 */
package com.tibco.xpd.rsd;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '
 * <em><b>Data Type</b></em>', and utility methods for working with them. <!--
 * end-user-doc -->
 * @see com.tibco.xpd.rsd.RsdPackage#getDataType()
 * @model
 * @generated
 */
public enum DataType implements Enumerator {
    /**
     * The '<em><b>TEXT</b></em>' literal object. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #TEXT_VALUE
     * @generated NOT
     * @ordered
     */
    TEXT(0, Messages.DataType_Text, "TEXT"), //$NON-NLS-1$

    /**
     * The ' <em><b>INTEGER</b></em>' literal object. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @see #INTEGER_VALUE
     * @generated NOT
     * @ordered
     */
    INTEGER(1, Messages.DataType_Integer, "INTEGER"), //$NON-NLS-1$

    /**
     * The '<em><b>DECIMAL</b></em>' literal object. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @see #DECIMAL_VALUE
     * @generated NOT
     * @ordered
     */
    DECIMAL(2, Messages.DataType_Decimal, "DECIMAL"), //$NON-NLS-1$

    /**
     * The '<em><b>BOOLEAN</b></em>' literal object. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @see #BOOLEAN_VALUE
     * @generated NOT
     * @ordered
     */
    BOOLEAN(3, Messages.DataType_Boolean, "BOOLEAN"), //$NON-NLS-1$

    /**
     * The '<em><b>DATE</b></em>' literal object. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #DATE_VALUE
     * @generated NOT
     * @ordered
     */
    DATE(4, Messages.DataType_Date, "DATE"), //$NON-NLS-1$

    /**
     * The '<em><b>TIME</b></em>' literal object. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #TIME_VALUE
     * @generated NOT
     * @ordered
     */
    TIME(5, Messages.DataType_Time, "TIME"), //$NON-NLS-1$

    /**
     * The '<em><b>DATE TIME</b></em>' literal object. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @see #DATE_TIME_VALUE
     * @generated NOT
     * @ordered
     */
    DATE_TIME(6, Messages.DataType_DateTime, "DATE_TIME"); //$NON-NLS-1$

    /**
     * The '<em><b>TEXT</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>TEXT</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #TEXT
     * @model
     * @generated
     * @ordered
     */
    public static final int TEXT_VALUE = 0;

    /**
     * The '<em><b>INTEGER</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>INTEGER</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #INTEGER
     * @model
     * @generated
     * @ordered
     */
    public static final int INTEGER_VALUE = 1;

    /**
     * The '<em><b>DECIMAL</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>DECIMAL</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DECIMAL
     * @model
     * @generated
     * @ordered
     */
    public static final int DECIMAL_VALUE = 2;

    /**
     * The '<em><b>BOOLEAN</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>BOOLEAN</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #BOOLEAN
     * @model
     * @generated
     * @ordered
     */
    public static final int BOOLEAN_VALUE = 3;

    /**
     * The '<em><b>DATE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>DATE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DATE
     * @model
     * @generated
     * @ordered
     */
    public static final int DATE_VALUE = 4;

    /**
     * The '<em><b>TIME</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>TIME</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #TIME
     * @model
     * @generated
     * @ordered
     */
    public static final int TIME_VALUE = 5;

    /**
     * The '<em><b>DATE TIME</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>DATE TIME</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DATE_TIME
     * @model
     * @generated
     * @ordered
     */
    public static final int DATE_TIME_VALUE = 6;

    /**
     * An array of all the '<em><b>Data Type</b></em>' enumerators. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private static final DataType[] VALUES_ARRAY = new DataType[] {
            TEXT,
            INTEGER,
            DECIMAL,
            BOOLEAN,
            DATE,
            TIME,
            DATE_TIME,
        };

    /**
     * A public read-only list of all the '<em><b>Data Type</b></em>' enumerators.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final List<DataType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Data Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
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
     * <!-- begin-user-doc --> <!-- end-user-doc -->
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
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static DataType get(int value) {
        switch (value) {
            case TEXT_VALUE: return TEXT;
            case INTEGER_VALUE: return INTEGER;
            case DECIMAL_VALUE: return DECIMAL;
            case BOOLEAN_VALUE: return BOOLEAN;
            case DATE_VALUE: return DATE;
            case TIME_VALUE: return TIME;
            case DATE_TIME_VALUE: return DATE_TIME;
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private final int value;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private final String name;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private final String literal;

    /**
     * Only this class can construct instances.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    private DataType(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int getValue() {
      return value;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getName() {
      return name;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getLiteral() {
      return literal;
    }

    /**
     * Returns the literal value of the enumerator, which is its string representation.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        return literal;
    }

} // DataType
