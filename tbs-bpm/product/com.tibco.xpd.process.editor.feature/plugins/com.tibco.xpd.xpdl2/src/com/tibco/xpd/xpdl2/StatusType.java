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
 * A representation of the literals of the enumeration '<em><b>Status Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getStatusType()
 * @model extendedMetaData="name='Status_._type'"
 * @generated
 */
public enum StatusType implements Enumerator {
    /**
     * The '<em><b>None</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #NONE
     * @generated
     * @ordered
     */
    NONE_LITERAL(0, "None", "None"),
    /**
     * The '<em><b>Ready</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #READY
     * @generated
     * @ordered
     */
    READY_LITERAL(1, "Ready", "Ready"),
    /**
     * The '<em><b>Active</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ACTIVE
     * @generated
     * @ordered
     */
    ACTIVE_LITERAL(2, "Active", "Active"),
    /**
     * The '<em><b>Cancelled</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #CANCELLED
     * @generated
     * @ordered
     */
    CANCELLED_LITERAL(3, "Cancelled", "Cancelled"),
    /**
     * The '<em><b>Aborting</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ABORTING
     * @generated
     * @ordered
     */
    ABORTING_LITERAL(4, "Aborting", "Aborting"),
    /**
     * The '<em><b>Aborted</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ABORTED
     * @generated
     * @ordered
     */
    ABORTED_LITERAL(5, "Aborted", "Aborted"),
    /**
     * The '<em><b>Completing</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #COMPLETING
     * @generated
     * @ordered
     */
    COMPLETING_LITERAL(6, "Completing", "Completing"),
    /**
     * The '<em><b>Completed</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #COMPLETED
     * @generated
     * @ordered
     */
    COMPLETED_LITERAL(7, "Completed", "Completed");
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>None</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>None</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #NONE_LITERAL
     * @model name="None"
     * @generated
     * @ordered
     */
    public static final int NONE = 0;

    /**
     * The '<em><b>Ready</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Ready</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #READY_LITERAL
     * @model name="Ready"
     * @generated
     * @ordered
     */
    public static final int READY = 1;

    /**
     * The '<em><b>Active</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Active</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ACTIVE_LITERAL
     * @model name="Active"
     * @generated
     * @ordered
     */
    public static final int ACTIVE = 2;

    /**
     * The '<em><b>Cancelled</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Cancelled</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #CANCELLED_LITERAL
     * @model name="Cancelled"
     * @generated
     * @ordered
     */
    public static final int CANCELLED = 3;

    /**
     * The '<em><b>Aborting</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Aborting</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ABORTING_LITERAL
     * @model name="Aborting"
     * @generated
     * @ordered
     */
    public static final int ABORTING = 4;

    /**
     * The '<em><b>Aborted</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Aborted</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ABORTED_LITERAL
     * @model name="Aborted"
     * @generated
     * @ordered
     */
    public static final int ABORTED = 5;

    /**
     * The '<em><b>Completing</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Completing</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #COMPLETING_LITERAL
     * @model name="Completing"
     * @generated
     * @ordered
     */
    public static final int COMPLETING = 6;

    /**
     * The '<em><b>Completed</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Completed</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #COMPLETED_LITERAL
     * @model name="Completed"
     * @generated
     * @ordered
     */
    public static final int COMPLETED = 7;

    /**
     * An array of all the '<em><b>Status Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final StatusType[] VALUES_ARRAY = new StatusType[] { NONE_LITERAL, READY_LITERAL, ACTIVE_LITERAL,
            CANCELLED_LITERAL, ABORTING_LITERAL, ABORTED_LITERAL, COMPLETING_LITERAL, COMPLETED_LITERAL, };

    /**
     * A public read-only list of all the '<em><b>Status Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<StatusType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Status Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param literal the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static StatusType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            StatusType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Status Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param name the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static StatusType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            StatusType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Status Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static StatusType get(int value) {
        switch (value) {
        case NONE:
            return NONE_LITERAL;
        case READY:
            return READY_LITERAL;
        case ACTIVE:
            return ACTIVE_LITERAL;
        case CANCELLED:
            return CANCELLED_LITERAL;
        case ABORTING:
            return ABORTING_LITERAL;
        case ABORTED:
            return ABORTED_LITERAL;
        case COMPLETING:
            return COMPLETING_LITERAL;
        case COMPLETED:
            return COMPLETED_LITERAL;
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
    private StatusType(int value, String name, String literal) {
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
