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
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '
 * <em><b>Participant Type</b></em>', and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getParticipantType()
 * @model extendedMetaData="name='Type_._1_._type'"
 * @generated
 */
public enum ParticipantType implements Enumerator {
    /**
     * The '<em><b>RESOURCE SET</b></em>' literal object.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #RESOURCE_SET
     * @generated
     * @ordered
     */
    RESOURCE_SET_LITERAL(0, "RESOURCE_SET", "RESOURCE_SET"),
    /**
     * The '<em><b>RESOURCE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #RESOURCE
     * @generated
     * @ordered
     */
    RESOURCE_LITERAL(1, "RESOURCE", "RESOURCE"),
    /**
     * The '<em><b>ROLE</b></em>' literal object.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #ROLE
     * @generated
     * @ordered
     */
    ROLE_LITERAL(2, "ROLE", "ROLE"),
    /**
     * The '<em><b>ORGANIZATIONAL UNIT</b></em>' literal object. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #ORGANIZATIONAL_UNIT
     * @generated
     * @ordered
     */
    ORGANIZATIONAL_UNIT_LITERAL(3, "ORGANIZATIONAL_UNIT", "ORGANIZATIONAL_UNIT"),
    /**
     * The '<em><b>HUMAN</b></em>' literal object.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #HUMAN
     * @generated
     * @ordered
     */
    HUMAN_LITERAL(4, "HUMAN", "HUMAN"),
    /**
     * The '<em><b>SYSTEM</b></em>' literal object.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #SYSTEM
     * @generated
     * @ordered
     */
    SYSTEM_LITERAL(5, "SYSTEM", "SYSTEM");
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>RESOURCE SET</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>RESOURCE SET</b></em>' literal object isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #RESOURCE_SET_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int RESOURCE_SET = 0;

    /**
     * The '<em><b>RESOURCE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>RESOURCE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #RESOURCE_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int RESOURCE = 1;

    /**
     * The '<em><b>ROLE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>ROLE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ROLE_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int ROLE = 2;

    /**
     * The '<em><b>ORGANIZATIONAL UNIT</b></em>' literal value. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>ORGANIZATIONAL UNIT</b></em>' literal object
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @see #ORGANIZATIONAL_UNIT_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int ORGANIZATIONAL_UNIT = 3;

    /**
     * The '<em><b>HUMAN</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>HUMAN</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #HUMAN_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int HUMAN = 4;

    /**
     * The '<em><b>SYSTEM</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>SYSTEM</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #SYSTEM_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int SYSTEM = 5;

    /**
     * An array of all the '<em><b>Participant Type</b></em>' enumerators. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private static final ParticipantType[] VALUES_ARRAY =
            new ParticipantType[] { RESOURCE_SET_LITERAL, RESOURCE_LITERAL,
                    ROLE_LITERAL, ORGANIZATIONAL_UNIT_LITERAL, HUMAN_LITERAL,
                    SYSTEM_LITERAL, };

    /**
     * A public read-only list of all the '<em><b>Participant Type</b></em>' enumerators.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final List<ParticipantType> VALUES = Collections
            .unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Participant Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static ParticipantType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ParticipantType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Participant Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static ParticipantType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ParticipantType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Participant Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static ParticipantType get(int value) {
        switch (value) {
        case RESOURCE_SET:
            return RESOURCE_SET_LITERAL;
        case RESOURCE:
            return RESOURCE_LITERAL;
        case ROLE:
            return ROLE_LITERAL;
        case ORGANIZATIONAL_UNIT:
            return ORGANIZATIONAL_UNIT_LITERAL;
        case HUMAN:
            return HUMAN_LITERAL;
        case SYSTEM:
            return SYSTEM_LITERAL;
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
    private ParticipantType(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public int getValue() {
        return value;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
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
}
