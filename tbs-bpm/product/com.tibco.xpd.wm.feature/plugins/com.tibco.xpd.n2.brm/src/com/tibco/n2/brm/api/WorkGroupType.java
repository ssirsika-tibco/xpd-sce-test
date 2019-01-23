/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Work Group Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * Enumerated value defining the the types of work group supported by BRM for implementing group patterns (e.g. CHAINING, SEPARATION_OF_CONCERNS).
 * <!-- end-model-doc -->
 * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkGroupType()
 * @model extendedMetaData="name='WorkGroupType'"
 * @generated
 */
public enum WorkGroupType implements Enumerator {
    /**
     * The '<em><b>CHAINING</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #CHAINING_VALUE
     * @generated
     * @ordered
     */
    CHAINING(0, "CHAINING", "CHAINING"),

    /**
     * The '<em><b>SEPARATIONOFCONCERNS</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #SEPARATIONOFCONCERNS_VALUE
     * @generated
     * @ordered
     */
    SEPARATIONOFCONCERNS(1, "SEPARATIONOFCONCERNS", "SEPARATION_OF_CONCERNS"),

    /**
     * The '<em><b>RETAINMOSTFAMILIAR</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #RETAINMOSTFAMILIAR_VALUE
     * @generated
     * @ordered
     */
    RETAINMOSTFAMILIAR(2, "RETAINMOSTFAMILIAR", "RETAIN_MOST_FAMILIAR"),

    /**
     * The '<em><b>RETAINFAMILIAR</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #RETAINFAMILIAR_VALUE
     * @generated
     * @ordered
     */
    RETAINFAMILIAR(3, "RETAINFAMILIAR", "RETAIN_FAMILIAR"),

    /**
     * The '<em><b>CASEHANDLING</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #CASEHANDLING_VALUE
     * @generated
     * @ordered
     */
    CASEHANDLING(4, "CASEHANDLING", "CASE_HANDLING"),

    /**
     * The '<em><b>NOGROUP</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #NOGROUP_VALUE
     * @generated
     * @ordered
     */
    NOGROUP(5, "NOGROUP", "NO_GROUP");

    /**
     * The '<em><b>CHAINING</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>CHAINING</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #CHAINING
     * @model
     * @generated
     * @ordered
     */
    public static final int CHAINING_VALUE = 0;

    /**
     * The '<em><b>SEPARATIONOFCONCERNS</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>SEPARATIONOFCONCERNS</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #SEPARATIONOFCONCERNS
     * @model literal="SEPARATION_OF_CONCERNS"
     * @generated
     * @ordered
     */
    public static final int SEPARATIONOFCONCERNS_VALUE = 1;

    /**
     * The '<em><b>RETAINMOSTFAMILIAR</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>RETAINMOSTFAMILIAR</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #RETAINMOSTFAMILIAR
     * @model literal="RETAIN_MOST_FAMILIAR"
     * @generated
     * @ordered
     */
    public static final int RETAINMOSTFAMILIAR_VALUE = 2;

    /**
     * The '<em><b>RETAINFAMILIAR</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>RETAINFAMILIAR</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #RETAINFAMILIAR
     * @model literal="RETAIN_FAMILIAR"
     * @generated
     * @ordered
     */
    public static final int RETAINFAMILIAR_VALUE = 3;

    /**
     * The '<em><b>CASEHANDLING</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>CASEHANDLING</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #CASEHANDLING
     * @model literal="CASE_HANDLING"
     * @generated
     * @ordered
     */
    public static final int CASEHANDLING_VALUE = 4;

    /**
     * The '<em><b>NOGROUP</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>NOGROUP</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #NOGROUP
     * @model literal="NO_GROUP"
     * @generated
     * @ordered
     */
    public static final int NOGROUP_VALUE = 5;

    /**
     * An array of all the '<em><b>Work Group Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final WorkGroupType[] VALUES_ARRAY =
        new WorkGroupType[] {
            CHAINING,
            SEPARATIONOFCONCERNS,
            RETAINMOSTFAMILIAR,
            RETAINFAMILIAR,
            CASEHANDLING,
            NOGROUP,
        };

    /**
     * A public read-only list of all the '<em><b>Work Group Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<WorkGroupType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Work Group Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static WorkGroupType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            WorkGroupType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Work Group Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static WorkGroupType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            WorkGroupType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Work Group Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static WorkGroupType get(int value) {
        switch (value) {
            case CHAINING_VALUE: return CHAINING;
            case SEPARATIONOFCONCERNS_VALUE: return SEPARATIONOFCONCERNS;
            case RETAINMOSTFAMILIAR_VALUE: return RETAINMOSTFAMILIAR;
            case RETAINFAMILIAR_VALUE: return RETAINFAMILIAR;
            case CASEHANDLING_VALUE: return CASEHANDLING;
            case NOGROUP_VALUE: return NOGROUP;
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
    private WorkGroupType(int value, String name, String literal) {
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
    
} //WorkGroupType
