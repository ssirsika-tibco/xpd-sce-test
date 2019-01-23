/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Join Split Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getJoinSplitType()
 * @model extendedMetaData="name='GatewayType_._type'"
 * @generated
 */
public enum JoinSplitType implements Enumerator {
    /**
     * The '<em><b>Deprecated AND</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #DEPRECATED_AND
     * @generated
     * @ordered
     */
    DEPRECATED_AND_LITERAL(0, "deprecatedAND", "deprecatedAND"), /**
                                                                  * The '<em><b>Deprecated XOR</b></em>' literal object.
                                                                  * <!-- begin-user-doc -->
                                                                  * <!-- end-user-doc -->
                                                                  * @see #DEPRECATED_XOR
                                                                  * @generated
                                                                  * @ordered
                                                                  */
    DEPRECATED_XOR_LITERAL(1, "deprecatedXOR", "deprecatedXOR"), /**
                                                                  * The '<em><b>Deprecated OR</b></em>' literal object.
                                                                  * <!-- begin-user-doc -->
                                                                  * <!-- end-user-doc -->
                                                                  * @see #DEPRECATED_OR
                                                                  * @generated
                                                                  * @ordered
                                                                  */
    DEPRECATED_OR_LITERAL(2, "deprecatedOR", "deprecatedOR"), /**
                                                               * The '<em><b>Complex</b></em>' literal object.
                                                               * <!-- begin-user-doc -->
                                                               * <!-- end-user-doc -->
                                                               * @see #COMPLEX
                                                               * @generated
                                                               * @ordered
                                                               */
    COMPLEX_LITERAL(3, "Complex", "Complex"),
    /**
     * The '<em><b>Inclusive</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #INCLUSIVE
     * @generated
     * @ordered
     */
    INCLUSIVE_LITERAL(4, "Inclusive", "Inclusive"),
    /**
     * The '<em><b>Exclusive</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #EXCLUSIVE
     * @generated
     * @ordered
     */
    EXCLUSIVE_LITERAL(5, "Exclusive", "Exclusive"),
    /**
     * The '<em><b>Parallel</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #PARALLEL
     * @generated
     * @ordered
     */
    PARALLEL_LITERAL(6, "Parallel", "Parallel");
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>Deprecated AND</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Deprecated AND</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DEPRECATED_AND_LITERAL
     * @model name="deprecatedAND"
     * @generated
     * @ordered
     */
    public static final int DEPRECATED_AND = 0;

    /**
     * The '<em><b>Deprecated XOR</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Deprecated XOR</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DEPRECATED_XOR_LITERAL
     * @model name="deprecatedXOR"
     * @generated
     * @ordered
     */
    public static final int DEPRECATED_XOR = 1;

    /**
     * The '<em><b>Deprecated OR</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Deprecated OR</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DEPRECATED_OR_LITERAL
     * @model name="deprecatedOR"
     * @generated
     * @ordered
     */
    public static final int DEPRECATED_OR = 2;

    /**
     * The '<em><b>Complex</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Complex</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #COMPLEX_LITERAL
     * @model name="Complex"
     * @generated
     * @ordered
     */
    public static final int COMPLEX = 3;

    /**
     * The '<em><b>Inclusive</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>INCLUSIVE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #INCLUSIVE_LITERAL
     * @model name="Inclusive"
     * @generated
     * @ordered
     */
    public static final int INCLUSIVE = 4;

    /**
     * The '<em><b>Exclusive</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>EXCLUSIVE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #EXCLUSIVE_LITERAL
     * @model name="Exclusive"
     * @generated
     * @ordered
     */
    public static final int EXCLUSIVE = 5;

    /**
     * The '<em><b>Parallel</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>PARALLEL</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #PARALLEL_LITERAL
     * @model name="Parallel"
     * @generated
     * @ordered
     */
    public static final int PARALLEL = 6;

    /**
     * An array of all the '<em><b>Join Split Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final JoinSplitType[] VALUES_ARRAY = new JoinSplitType[] {
            DEPRECATED_AND_LITERAL, DEPRECATED_XOR_LITERAL,
            DEPRECATED_OR_LITERAL, COMPLEX_LITERAL, INCLUSIVE_LITERAL,
            EXCLUSIVE_LITERAL, PARALLEL_LITERAL, };

    /**
     * A public read-only list of all the '<em><b>Join Split Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<JoinSplitType> VALUES = Collections
            .unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Join Split Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static JoinSplitType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            JoinSplitType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Join Split Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static JoinSplitType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            JoinSplitType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Join Split Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static JoinSplitType get(int value) {
        switch (value) {
        case DEPRECATED_AND:
            return DEPRECATED_AND_LITERAL;
        case DEPRECATED_XOR:
            return DEPRECATED_XOR_LITERAL;
        case DEPRECATED_OR:
            return DEPRECATED_OR_LITERAL;
        case COMPLEX:
            return COMPLEX_LITERAL;
        case INCLUSIVE:
            return INCLUSIVE_LITERAL;
        case EXCLUSIVE:
            return EXCLUSIVE_LITERAL;
        case PARALLEL:
            return PARALLEL_LITERAL;
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
    private JoinSplitType(int value, String name, String literal) {
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
