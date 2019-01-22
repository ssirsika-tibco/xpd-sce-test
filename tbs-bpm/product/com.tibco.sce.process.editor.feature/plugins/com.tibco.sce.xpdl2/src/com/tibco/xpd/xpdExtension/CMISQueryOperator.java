/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>CMIS Query Operator</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCMISQueryOperator()
 * @model
 * @generated
 */
public enum CMISQueryOperator implements Enumerator {
    /**
     * The '<em><b>EQUAL</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #EQUAL_VALUE
     * @generated
     * @ordered
     */
    EQUAL(0, "EQUAL", "EQUAL"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>NOT EQUAL</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #NOT_EQUAL_VALUE
     * @generated
     * @ordered
     */
    NOT_EQUAL(1, "NOT_EQUAL", "NOT_EQUAL"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>GREATER THAN</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #GREATER_THAN_VALUE
     * @generated
     * @ordered
     */
    GREATER_THAN(2, "GREATER_THAN", "GREATER_THAN"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>GREATER THAN EQUAL</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #GREATER_THAN_EQUAL_VALUE
     * @generated
     * @ordered
     */
    GREATER_THAN_EQUAL(3, "GREATER_THAN_EQUAL", "GREATER_THAN_EQUAL"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>LESS THAN</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #LESS_THAN_VALUE
     * @generated
     * @ordered
     */
    LESS_THAN(4, "LESS_THAN", "LESS_THAN"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>LESS THAN EQUAL</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #LESS_THAN_EQUAL_VALUE
     * @generated
     * @ordered
     */
    LESS_THAN_EQUAL(5, "LESS_THAN_EQUAL", "LESS_THAN_EQUAL"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>IN</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #IN_VALUE
     * @generated
     * @ordered
     */
    IN(6, "IN", "IN"),
    /**
     * The '<em><b>NOT IN</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #NOT_IN_VALUE
     * @generated
     * @ordered
     */
    NOT_IN(7, "NOT_IN", "NOT_IN"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>CONTAINS</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #CONTAINS_VALUE
     * @generated
     * @ordered
     */
    CONTAINS(8, "CONTAINS", "CONTAINS"),
    /**
     * The '<em><b>NOT CONTAINS</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #NOT_CONTAINS_VALUE
     * @generated
     * @ordered
     */
    NOT_CONTAINS(9, "NOT_CONTAINS", "NOT_CONTAINS"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>LIKE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #LIKE_VALUE
     * @generated
     * @ordered
     */
    LIKE(10, "LIKE", "LIKE"),
    /**
     * The '<em><b>NOT LIKE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #NOT_LIKE_VALUE
     * @generated
     * @ordered
     */
    NOT_LIKE(11, "NOT_LIKE", "NOT_LIKE"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>NULL</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #NULL_VALUE
     * @generated
     * @ordered
     */
    NULL(12, "NULL", "NULL"),
    /**
     * The '<em><b>NOT NULL</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #NOT_NULL_VALUE
     * @generated
     * @ordered
     */
    NOT_NULL(13, "NOT_NULL", "NOT_NULL"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>EQUAL</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>EQUAL</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * = Operator for Conditional Expression
     * <!-- end-model-doc -->
     * @see #EQUAL
     * @model
     * @generated
     * @ordered
     */
    public static final int EQUAL_VALUE = 0;

    /**
     * The '<em><b>NOT EQUAL</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>NOT EQUAL</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * <> Operator for Conditional Expression
     * <!-- end-model-doc -->
     * @see #NOT_EQUAL
     * @model
     * @generated
     * @ordered
     */
    public static final int NOT_EQUAL_VALUE = 1;

    /**
     * The '<em><b>GREATER THAN</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>GREATER THAN</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * > Operator for Conditional Expression
     * <!-- end-model-doc -->
     * @see #GREATER_THAN
     * @model
     * @generated
     * @ordered
     */
    public static final int GREATER_THAN_VALUE = 2;

    /**
     * The '<em><b>GREATER THAN EQUAL</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>GREATER THAN EQUAL</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * >= Operator for Conditional Expression
     * <!-- end-model-doc -->
     * @see #GREATER_THAN_EQUAL
     * @model
     * @generated
     * @ordered
     */
    public static final int GREATER_THAN_EQUAL_VALUE = 3;

    /**
     * The '<em><b>LESS THAN</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>LESS THAN</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * < Operator for Conditional Expression
     * <!-- end-model-doc -->
     * @see #LESS_THAN
     * @model
     * @generated
     * @ordered
     */
    public static final int LESS_THAN_VALUE = 4;

    /**
     * The '<em><b>LESS THAN EQUAL</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>LESS THAN EQUAL</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * <= Operator for Conditional Expression
     * <!-- end-model-doc -->
     * @see #LESS_THAN_EQUAL
     * @model
     * @generated
     * @ordered
     */
    public static final int LESS_THAN_EQUAL_VALUE = 5;

    /**
     * The '<em><b>IN</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>IN</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * IN Operator for Conditional Expression
     * <!-- end-model-doc -->
     * @see #IN
     * @model
     * @generated
     * @ordered
     */
    public static final int IN_VALUE = 6;

    /**
     * The '<em><b>NOT IN</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * NOT IN Operator for Conditional Expression
     * <!-- end-model-doc -->
     * @see #NOT_IN
     * @model
     * @generated
     * @ordered
     */
    public static final int NOT_IN_VALUE = 7;

    /**
     * The '<em><b>CONTAINS</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>CONTAINS</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * CONTAINS Operator for Conditional Expression
     * <!-- end-model-doc -->
     * @see #CONTAINS
     * @model
     * @generated
     * @ordered
     */
    public static final int CONTAINS_VALUE = 8;

    /**
     * The '<em><b>NOT CONTAINS</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * NOT CONTAINS Operator for Conditional Expression
     * <!-- end-model-doc -->
     * @see #NOT_CONTAINS
     * @model
     * @generated
     * @ordered
     */
    public static final int NOT_CONTAINS_VALUE = 9;

    /**
     * The '<em><b>LIKE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>LIKE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * LIKE Operator for Conditional Expression
     * <!-- end-model-doc -->
     * @see #LIKE
     * @model
     * @generated
     * @ordered
     */
    public static final int LIKE_VALUE = 10;

    /**
     * The '<em><b>NOT LIKE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * NOT LIKE Operator for Conditional Expression
     * <!-- end-model-doc -->
     * @see #NOT_LIKE
     * @model
     * @generated
     * @ordered
     */
    public static final int NOT_LIKE_VALUE = 11;

    /**
     * The '<em><b>NULL</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>NULL</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * NULL Operator for Conditional Expression
     * <!-- end-model-doc -->
     * @see #NULL
     * @model
     * @generated
     * @ordered
     */
    public static final int NULL_VALUE = 12;

    /**
     * The '<em><b>NOT NULL</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * NOT NULL Operator for Conditional Expression
     * <!-- end-model-doc -->
     * @see #NOT_NULL
     * @model
     * @generated
     * @ordered
     */
    public static final int NOT_NULL_VALUE = 13;

    /**
     * An array of all the '<em><b>CMIS Query Operator</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final CMISQueryOperator[] VALUES_ARRAY =
            new CMISQueryOperator[] { EQUAL, NOT_EQUAL, GREATER_THAN,
                    GREATER_THAN_EQUAL, LESS_THAN, LESS_THAN_EQUAL, IN, NOT_IN,
                    CONTAINS, NOT_CONTAINS, LIKE, NOT_LIKE, NULL, NOT_NULL, };

    /**
     * A public read-only list of all the '<em><b>CMIS Query Operator</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<CMISQueryOperator> VALUES =
            Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>CMIS Query Operator</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param literal the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static CMISQueryOperator get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            CMISQueryOperator result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>CMIS Query Operator</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param name the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static CMISQueryOperator getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            CMISQueryOperator result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>CMIS Query Operator</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static CMISQueryOperator get(int value) {
        switch (value) {
        case EQUAL_VALUE:
            return EQUAL;
        case NOT_EQUAL_VALUE:
            return NOT_EQUAL;
        case GREATER_THAN_VALUE:
            return GREATER_THAN;
        case GREATER_THAN_EQUAL_VALUE:
            return GREATER_THAN_EQUAL;
        case LESS_THAN_VALUE:
            return LESS_THAN;
        case LESS_THAN_EQUAL_VALUE:
            return LESS_THAN_EQUAL;
        case IN_VALUE:
            return IN;
        case NOT_IN_VALUE:
            return NOT_IN;
        case CONTAINS_VALUE:
            return CONTAINS;
        case NOT_CONTAINS_VALUE:
            return NOT_CONTAINS;
        case LIKE_VALUE:
            return LIKE;
        case NOT_LIKE_VALUE:
            return NOT_LIKE;
        case NULL_VALUE:
            return NULL;
        case NOT_NULL_VALUE:
            return NOT_NULL;
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
    private CMISQueryOperator(int value, String name, String literal) {
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

} //CMISQueryOperator
