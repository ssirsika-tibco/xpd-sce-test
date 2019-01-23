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
 * A representation of the literals of the enumeration '<em><b>Graph Conformance Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getGraphConformanceType()
 * @model extendedMetaData="name='GraphConformance_._type'"
 * @generated
 */
public enum GraphConformanceType implements Enumerator {
    /**
     * The '<em><b>FULL BLOCKED</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #FULL_BLOCKED
     * @generated
     * @ordered
     */
    FULL_BLOCKED_LITERAL(0, "FULL_BLOCKED", "FULL_BLOCKED"),
    /**
     * The '<em><b>LOOP BLOCKED</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #LOOP_BLOCKED
     * @generated
     * @ordered
     */
    LOOP_BLOCKED_LITERAL(1, "LOOP_BLOCKED", "LOOP_BLOCKED"),
    /**
     * The '<em><b>NON BLOCKED</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #NON_BLOCKED
     * @generated
     * @ordered
     */
    NON_BLOCKED_LITERAL(2, "NON_BLOCKED", "NON_BLOCKED");
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>FULL BLOCKED</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>FULL BLOCKED</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #FULL_BLOCKED_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int FULL_BLOCKED = 0;

    /**
     * The '<em><b>LOOP BLOCKED</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>LOOP BLOCKED</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #LOOP_BLOCKED_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int LOOP_BLOCKED = 1;

    /**
     * The '<em><b>NON BLOCKED</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>NON BLOCKED</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #NON_BLOCKED_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int NON_BLOCKED = 2;

    /**
     * An array of all the '<em><b>Graph Conformance Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final GraphConformanceType[] VALUES_ARRAY =
            new GraphConformanceType[] { FULL_BLOCKED_LITERAL,
                    LOOP_BLOCKED_LITERAL, NON_BLOCKED_LITERAL, };

    /**
     * A public read-only list of all the '<em><b>Graph Conformance Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<GraphConformanceType> VALUES = Collections
            .unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Graph Conformance Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static GraphConformanceType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            GraphConformanceType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Graph Conformance Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static GraphConformanceType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            GraphConformanceType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Graph Conformance Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static GraphConformanceType get(int value) {
        switch (value) {
        case FULL_BLOCKED:
            return FULL_BLOCKED_LITERAL;
        case LOOP_BLOCKED:
            return LOOP_BLOCKED_LITERAL;
        case NON_BLOCKED:
            return NON_BLOCKED_LITERAL;
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
    private GraphConformanceType(int value, String name, String literal) {
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
