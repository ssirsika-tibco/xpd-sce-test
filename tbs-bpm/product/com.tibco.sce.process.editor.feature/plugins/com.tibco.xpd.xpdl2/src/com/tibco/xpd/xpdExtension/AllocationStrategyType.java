/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Allocation Strategy Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAllocationStrategyType()
 * @model extendedMetaData="name='AllocationStrategyType_._type'"
 * @generated
 */
public enum AllocationStrategyType implements Enumerator {
    /**
     * The '<em><b>SYSTEM DETERMINED</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #SYSTEM_DETERMINED_VALUE
     * @generated
     * @ordered
     */
    SYSTEM_DETERMINED(0, "SYSTEM_DETERMINED", "SYSTEM_DETERMINED"),
    /**
     * The '<em><b>RANDOM</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #RANDOM_VALUE
     * @generated
     * @ordered
     */
    RANDOM(1, "RANDOM", "RANDOM"),
    /**
     * The '<em><b>ROUND ROBIN</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ROUND_ROBIN_VALUE
     * @generated
     * @ordered
     */
    ROUND_ROBIN(2, "ROUND_ROBIN", "ROUND_ROBIN");
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>SYSTEM DETERMINED</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>SYSTEM DETERMINED</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #SYSTEM_DETERMINED
     * @model
     * @generated
     * @ordered
     */
    public static final int SYSTEM_DETERMINED_VALUE = 0;

    /**
     * The '<em><b>RANDOM</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>RANDOM</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #RANDOM
     * @model
     * @generated
     * @ordered
     */
    public static final int RANDOM_VALUE = 1;

    /**
     * The '<em><b>ROUND ROBIN</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>ROUND ROBIN</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ROUND_ROBIN
     * @model
     * @generated
     * @ordered
     */
    public static final int ROUND_ROBIN_VALUE = 2;

    /**
     * An array of all the '<em><b>Allocation Strategy Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final AllocationStrategyType[] VALUES_ARRAY =
            new AllocationStrategyType[] { SYSTEM_DETERMINED, RANDOM,
                    ROUND_ROBIN, };

    /**
     * A public read-only list of all the '<em><b>Allocation Strategy Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<AllocationStrategyType> VALUES =
            Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Allocation Strategy Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param literal the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static AllocationStrategyType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            AllocationStrategyType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Allocation Strategy Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param name the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static AllocationStrategyType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            AllocationStrategyType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Allocation Strategy Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static AllocationStrategyType get(int value) {
        switch (value) {
        case SYSTEM_DETERMINED_VALUE:
            return SYSTEM_DETERMINED;
        case RANDOM_VALUE:
            return RANDOM;
        case ROUND_ROBIN_VALUE:
            return ROUND_ROBIN;
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
    private AllocationStrategyType(int value, String name, String literal) {
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
