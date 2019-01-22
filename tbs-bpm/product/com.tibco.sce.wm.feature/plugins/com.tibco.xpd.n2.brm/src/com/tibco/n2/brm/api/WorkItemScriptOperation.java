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
 * A representation of the literals of the enumeration '<em><b>Work Item Script Operation</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * Enumerated value defining the operation on which a work item script is performed (e.g. OPEN, CLOSE, CANCEL).
 * <!-- end-model-doc -->
 * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemScriptOperation()
 * @model extendedMetaData="name='WorkItemScriptOperation'"
 * @generated
 */
public enum WorkItemScriptOperation implements Enumerator {
    /**
     * The '<em><b>OPEN</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #OPEN_VALUE
     * @generated
     * @ordered
     */
    OPEN(0, "OPEN", "OPEN"),

    /**
     * The '<em><b>CLOSE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #CLOSE_VALUE
     * @generated
     * @ordered
     */
    CLOSE(1, "CLOSE", "CLOSE"),

    /**
     * The '<em><b>COMPLETE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #COMPLETE_VALUE
     * @generated
     * @ordered
     */
    COMPLETE(2, "COMPLETE", "COMPLETE"),

    /**
     * The '<em><b>PEND</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #PEND_VALUE
     * @generated
     * @ordered
     */
    PEND(3, "PEND", "PEND"),

    /**
     * The '<em><b>SCHEDULE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #SCHEDULE_VALUE
     * @generated
     * @ordered
     */
    SCHEDULE(4, "SCHEDULE", "SCHEDULE"),

    /**
     * The '<em><b>RESCHEDULE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #RESCHEDULE_VALUE
     * @generated
     * @ordered
     */
    RESCHEDULE(5, "RESCHEDULE", "RESCHEDULE"), /**
     * The '<em><b>SYSAPPEND</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #SYSAPPEND_VALUE
     * @generated
     * @ordered
     */
    SYSAPPEND(6, "SYSAPPEND", "SYSAPPEND");

    /**
     * The '<em><b>OPEN</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>OPEN</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #OPEN
     * @model
     * @generated
     * @ordered
     */
    public static final int OPEN_VALUE = 0;

    /**
     * The '<em><b>CLOSE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>CLOSE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #CLOSE
     * @model
     * @generated
     * @ordered
     */
    public static final int CLOSE_VALUE = 1;

    /**
     * The '<em><b>COMPLETE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>COMPLETE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #COMPLETE
     * @model
     * @generated
     * @ordered
     */
    public static final int COMPLETE_VALUE = 2;

    /**
     * The '<em><b>PEND</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>PEND</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #PEND
     * @model
     * @generated
     * @ordered
     */
    public static final int PEND_VALUE = 3;

    /**
     * The '<em><b>SCHEDULE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>SCHEDULE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #SCHEDULE
     * @model
     * @generated
     * @ordered
     */
    public static final int SCHEDULE_VALUE = 4;

    /**
     * The '<em><b>RESCHEDULE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>RESCHEDULE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #RESCHEDULE
     * @model
     * @generated
     * @ordered
     */
    public static final int RESCHEDULE_VALUE = 5;

    /**
     * The '<em><b>SYSAPPEND</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>SYSAPPEND</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #SYSAPPEND
     * @model
     * @generated
     * @ordered
     */
    public static final int SYSAPPEND_VALUE = 6;

    /**
     * An array of all the '<em><b>Work Item Script Operation</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final WorkItemScriptOperation[] VALUES_ARRAY =
        new WorkItemScriptOperation[] {
            OPEN,
            CLOSE,
            COMPLETE,
            PEND,
            SCHEDULE,
            RESCHEDULE,
            SYSAPPEND,
        };

    /**
     * A public read-only list of all the '<em><b>Work Item Script Operation</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<WorkItemScriptOperation> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Work Item Script Operation</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static WorkItemScriptOperation get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            WorkItemScriptOperation result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Work Item Script Operation</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static WorkItemScriptOperation getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            WorkItemScriptOperation result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Work Item Script Operation</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static WorkItemScriptOperation get(int value) {
        switch (value) {
            case OPEN_VALUE: return OPEN;
            case CLOSE_VALUE: return CLOSE;
            case COMPLETE_VALUE: return COMPLETE;
            case PEND_VALUE: return PEND;
            case SCHEDULE_VALUE: return SCHEDULE;
            case RESCHEDULE_VALUE: return RESCHEDULE;
            case SYSAPPEND_VALUE: return SYSAPPEND;
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
    private WorkItemScriptOperation(int value, String name, String literal) {
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
    
} //WorkItemScriptOperation
