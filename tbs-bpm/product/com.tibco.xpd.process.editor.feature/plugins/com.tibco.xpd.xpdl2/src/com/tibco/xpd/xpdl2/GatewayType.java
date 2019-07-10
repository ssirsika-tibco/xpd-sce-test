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
 * A representation of the literals of the enumeration '<em><b>Gateway Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getGatewayType()
 * @model extendedMetaData="name='GatewayType_._3_._type'"
 * @generated
 */
public enum GatewayType implements Enumerator {
    /**
     * The '<em><b>AND</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #AND
     * @generated
     * @ordered
     */
    AND_LITERAL(0, "AND", "AND"),
    /**
     * The '<em><b>XOR</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #XOR
     * @generated
     * @ordered
     */
    XOR_LITERAL(1, "XOR", "XOR"),
    /**
     * The '<em><b>OR</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #OR
     * @generated
     * @ordered
     */
    OR_LITERAL(2, "OR", "OR"),
    /**
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
    PARALLEL_LITERAL(6, "Parallel", "Parallel"),
    /**
    * The '<em><b>DEPRECATEDXOREVENT</b></em>' literal object.
    * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    * @see #DEPRECATEDXOREVENT
    * @generated
    * @ordered
    */
    DEPRECATEDXOREVENT_LITERAL(7, "DEPRECATEDXOREVENT", "DEPRECATEDXOREVENT");
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>AND</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>AND</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #AND_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int AND = 0;

    /**
     * The '<em><b>XOR</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>XOR</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #XOR_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int XOR = 1;

    /**
     * The '<em><b>OR</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>OR</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #OR_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int OR = 2;

    /**
     * The '<em><b>Complex</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>COMPLEX</b></em>' literal object isn't clear,
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
     * The '<em><b>DEPRECATEDXOREVENT</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>DEPRECATEDXOREVENT</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DEPRECATEDXOREVENT_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int DEPRECATEDXOREVENT = 7;

    /**
     * An array of all the '<em><b>Gateway Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final GatewayType[] VALUES_ARRAY = new GatewayType[] { AND_LITERAL, XOR_LITERAL, OR_LITERAL,
            COMPLEX_LITERAL, INCLUSIVE_LITERAL, EXCLUSIVE_LITERAL, PARALLEL_LITERAL, DEPRECATEDXOREVENT_LITERAL, };

    /**
     * A public read-only list of all the '<em><b>Gateway Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<GatewayType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Gateway Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param literal the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static GatewayType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            GatewayType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Gateway Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param name the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static GatewayType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            GatewayType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Gateway Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static GatewayType get(int value) {
        switch (value) {
        case AND:
            return AND_LITERAL;
        case XOR:
            return XOR_LITERAL;
        case OR:
            return OR_LITERAL;
        case COMPLEX:
            return COMPLEX_LITERAL;
        case INCLUSIVE:
            return INCLUSIVE_LITERAL;
        case EXCLUSIVE:
            return EXCLUSIVE_LITERAL;
        case PARALLEL:
            return PARALLEL_LITERAL;
        case DEPRECATEDXOREVENT:
            return DEPRECATEDXOREVENT_LITERAL;
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
    private GatewayType(int value, String name, String literal) {
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
