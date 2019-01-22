/**
 * TIBCO Software Inc.
 *
 * $Id$
 */
package com.tibco.simulation.report;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Experiment State</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.simulation.report.SimRepPackage#getSimRepExperimentState()
 * @model extendedMetaData="name='ExperimentStateType'"
 * @generated
 */
public enum SimRepExperimentState implements Enumerator {
    /**
     * The '<em><b>NOT STARTED</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #NOT_STARTED
     * @generated
     * @ordered
     */
    NOT_STARTED_LITERAL(0, "NOT_STARTED", "NOT_STARTED"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>RUNNING</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #RUNNING
     * @generated
     * @ordered
     */
    RUNNING_LITERAL(1, "RUNNING", "RUNNING"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>PAUSED</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #PAUSED
     * @generated
     * @ordered
     */
    PAUSED_LITERAL(2, "PAUSED", "PAUSED"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>FINISHED</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #FINISHED
     * @generated
     * @ordered
     */
    FINISHED_LITERAL(3, "FINISHED", "FINISHED"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>ABORTED</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ABORTED
     * @generated
     * @ordered
     */
    ABORTED_LITERAL(4, "ABORTED", "ABORTED"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * The '<em><b>NOT STARTED</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>NOT STARTED</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #NOT_STARTED_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int NOT_STARTED = 0;

    /**
     * The '<em><b>RUNNING</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>RUNNING</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #RUNNING_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int RUNNING = 1;

    /**
     * The '<em><b>PAUSED</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>PAUSED</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #PAUSED_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int PAUSED = 2;

    /**
     * The '<em><b>FINISHED</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>FINISHED</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #FINISHED_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int FINISHED = 3;

    /**
     * The '<em><b>ABORTED</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>ABORTED</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ABORTED_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int ABORTED = 4;

    /**
     * An array of all the '<em><b>Experiment State</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final SimRepExperimentState[] VALUES_ARRAY =
            new SimRepExperimentState[] { NOT_STARTED_LITERAL, RUNNING_LITERAL,
                    PAUSED_LITERAL, FINISHED_LITERAL, ABORTED_LITERAL, };

    /**
     * A public read-only list of all the '<em><b>Experiment State</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<SimRepExperimentState> VALUES = Collections
            .unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Experiment State</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SimRepExperimentState get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            SimRepExperimentState result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Experiment State</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SimRepExperimentState getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            SimRepExperimentState result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Experiment State</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SimRepExperimentState get(int value) {
        switch (value) {
        case NOT_STARTED:
            return NOT_STARTED_LITERAL;
        case RUNNING:
            return RUNNING_LITERAL;
        case PAUSED:
            return PAUSED_LITERAL;
        case FINISHED:
            return FINISHED_LITERAL;
        case ABORTED:
            return ABORTED_LITERAL;
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
    private SimRepExperimentState(int value, String name, String literal) {
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

} //SimRepExperimentState
