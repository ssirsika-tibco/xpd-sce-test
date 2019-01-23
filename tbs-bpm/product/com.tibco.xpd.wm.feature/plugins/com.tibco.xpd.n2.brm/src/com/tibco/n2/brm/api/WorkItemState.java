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
 * A representation of the literals of the enumeration '<em><b>Work Item State</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * Enumerated value defining the current lifecycle state of a work item (e.g. CREATED, OFFERED, ALLOCATED).
 * <!-- end-model-doc -->
 * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemState()
 * @model extendedMetaData="name='WorkItemState'"
 * @generated
 */
public enum WorkItemState implements Enumerator {
    /**
     * The '<em><b>CREATED</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #CREATED_VALUE
     * @generated
     * @ordered
     */
    CREATED(0, "CREATED", "CREATED"),

    /**
     * The '<em><b>OFFERED</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #OFFERED_VALUE
     * @generated
     * @ordered
     */
    OFFERED(1, "OFFERED", "OFFERED"),

    /**
     * The '<em><b>ALLOCATED</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ALLOCATED_VALUE
     * @generated
     * @ordered
     */
    ALLOCATED(2, "ALLOCATED", "ALLOCATED"),

    /**
     * The '<em><b>OPENED</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #OPENED_VALUE
     * @generated
     * @ordered
     */
    OPENED(3, "OPENED", "OPENED"),

    /**
     * The '<em><b>PENDED</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #PENDED_VALUE
     * @generated
     * @ordered
     */
    PENDED(4, "PENDED", "PENDED"),

    /**
     * The '<em><b>PENDHIDDEN</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #PENDHIDDEN_VALUE
     * @generated
     * @ordered
     */
    PENDHIDDEN(5, "PENDHIDDEN", "PENDHIDDEN"),

    /**
     * The '<em><b>SUSPENDED</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #SUSPENDED_VALUE
     * @generated
     * @ordered
     */
    SUSPENDED(6, "SUSPENDED", "SUSPENDED"),

    /**
     * The '<em><b>COMPLETED</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #COMPLETED_VALUE
     * @generated
     * @ordered
     */
    COMPLETED(7, "COMPLETED", "COMPLETED"),

    /**
     * The '<em><b>CANCELLED</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #CANCELLED_VALUE
     * @generated
     * @ordered
     */
    CANCELLED(8, "CANCELLED", "CANCELLED");

    /**
     * The '<em><b>CREATED</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>CREATED</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #CREATED
     * @model
     * @generated
     * @ordered
     */
    public static final int CREATED_VALUE = 0;

    /**
     * The '<em><b>OFFERED</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>OFFERED</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #OFFERED
     * @model
     * @generated
     * @ordered
     */
    public static final int OFFERED_VALUE = 1;

    /**
     * The '<em><b>ALLOCATED</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>ALLOCATED</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ALLOCATED
     * @model
     * @generated
     * @ordered
     */
    public static final int ALLOCATED_VALUE = 2;

    /**
     * The '<em><b>OPENED</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>OPENED</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #OPENED
     * @model
     * @generated
     * @ordered
     */
    public static final int OPENED_VALUE = 3;

    /**
     * The '<em><b>PENDED</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>PENDED</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #PENDED
     * @model
     * @generated
     * @ordered
     */
    public static final int PENDED_VALUE = 4;

    /**
     * The '<em><b>PENDHIDDEN</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>PENDHIDDEN</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #PENDHIDDEN
     * @model
     * @generated
     * @ordered
     */
    public static final int PENDHIDDEN_VALUE = 5;

    /**
     * The '<em><b>SUSPENDED</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>SUSPENDED</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #SUSPENDED
     * @model
     * @generated
     * @ordered
     */
    public static final int SUSPENDED_VALUE = 6;

    /**
     * The '<em><b>COMPLETED</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>COMPLETED</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #COMPLETED
     * @model
     * @generated
     * @ordered
     */
    public static final int COMPLETED_VALUE = 7;

    /**
     * The '<em><b>CANCELLED</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>CANCELLED</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #CANCELLED
     * @model
     * @generated
     * @ordered
     */
    public static final int CANCELLED_VALUE = 8;

    /**
     * An array of all the '<em><b>Work Item State</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final WorkItemState[] VALUES_ARRAY =
        new WorkItemState[] {
            CREATED,
            OFFERED,
            ALLOCATED,
            OPENED,
            PENDED,
            PENDHIDDEN,
            SUSPENDED,
            COMPLETED,
            CANCELLED,
        };

    /**
     * A public read-only list of all the '<em><b>Work Item State</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<WorkItemState> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Work Item State</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static WorkItemState get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            WorkItemState result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Work Item State</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static WorkItemState getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            WorkItemState result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Work Item State</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static WorkItemState get(int value) {
        switch (value) {
            case CREATED_VALUE: return CREATED;
            case OFFERED_VALUE: return OFFERED;
            case ALLOCATED_VALUE: return ALLOCATED;
            case OPENED_VALUE: return OPENED;
            case PENDED_VALUE: return PENDED;
            case PENDHIDDEN_VALUE: return PENDHIDDEN;
            case SUSPENDED_VALUE: return SUSPENDED;
            case COMPLETED_VALUE: return COMPLETED;
            case CANCELLED_VALUE: return CANCELLED;
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
    private WorkItemState(int value, String name, String literal) {
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
    
} //WorkItemState
