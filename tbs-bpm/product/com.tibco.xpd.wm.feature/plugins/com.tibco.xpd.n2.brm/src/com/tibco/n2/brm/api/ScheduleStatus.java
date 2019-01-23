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
 * A representation of the literals of the enumeration '<em><b>Schedule Status</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * Enumerated value defining whether a work item is within its schedule period (e.g. BEFORE, DURING, AFTER).
 * <!-- end-model-doc -->
 * @see com.tibco.n2.brm.api.N2BRMPackage#getScheduleStatus()
 * @model extendedMetaData="name='ScheduleStatus'"
 * @generated
 */
public enum ScheduleStatus implements Enumerator {
    /**
     * The '<em><b>NOSCHEDULE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #NOSCHEDULE_VALUE
     * @generated
     * @ordered
     */
    NOSCHEDULE(0, "NOSCHEDULE", "NO_SCHEDULE"),

    /**
     * The '<em><b>BEFORE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #BEFORE_VALUE
     * @generated
     * @ordered
     */
    BEFORE(1, "BEFORE", "BEFORE"),

    /**
     * The '<em><b>DURING</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #DURING_VALUE
     * @generated
     * @ordered
     */
    DURING(2, "DURING", "DURING"),

    /**
     * The '<em><b>AFTER</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #AFTER_VALUE
     * @generated
     * @ordered
     */
    AFTER(3, "AFTER", "AFTER");

    /**
     * The '<em><b>NOSCHEDULE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>NOSCHEDULE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #NOSCHEDULE
     * @model literal="NO_SCHEDULE"
     * @generated
     * @ordered
     */
    public static final int NOSCHEDULE_VALUE = 0;

    /**
     * The '<em><b>BEFORE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>BEFORE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #BEFORE
     * @model
     * @generated
     * @ordered
     */
    public static final int BEFORE_VALUE = 1;

    /**
     * The '<em><b>DURING</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>DURING</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DURING
     * @model
     * @generated
     * @ordered
     */
    public static final int DURING_VALUE = 2;

    /**
     * The '<em><b>AFTER</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>AFTER</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #AFTER
     * @model
     * @generated
     * @ordered
     */
    public static final int AFTER_VALUE = 3;

    /**
     * An array of all the '<em><b>Schedule Status</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final ScheduleStatus[] VALUES_ARRAY =
        new ScheduleStatus[] {
            NOSCHEDULE,
            BEFORE,
            DURING,
            AFTER,
        };

    /**
     * A public read-only list of all the '<em><b>Schedule Status</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<ScheduleStatus> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Schedule Status</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ScheduleStatus get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ScheduleStatus result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Schedule Status</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ScheduleStatus getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ScheduleStatus result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Schedule Status</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ScheduleStatus get(int value) {
        switch (value) {
            case NOSCHEDULE_VALUE: return NOSCHEDULE;
            case BEFORE_VALUE: return BEFORE;
            case DURING_VALUE: return DURING;
            case AFTER_VALUE: return AFTER;
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
    private ScheduleStatus(int value, String name, String literal) {
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
    
} //ScheduleStatus
