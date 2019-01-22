/**
 * Copyright 2005 TIBCO Software Inc. 
 *
 * $Id$
 */
package com.tibco.xpd.simulation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Loop Control Strategy</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.simulation.SimulationPackage#getLoopControlStrategy()
 * @model extendedMetaData="name='LoopControlStrategy'"
 * @generated
 */
public enum LoopControlStrategy implements Enumerator {
    /**
     * The '<em><b>MAX LOOP COUNT</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #MAX_LOOP_COUNT
     * @generated
     * @ordered
     */
    MAX_LOOP_COUNT_LITERAL(0, "MAX_LOOP_COUNT", "MAX_LOOP_COUNT"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>MAX ELAPSE TIME</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #MAX_ELAPSE_TIME
     * @generated
     * @ordered
     */
    MAX_ELAPSE_TIME_LITERAL(1, "MAX_ELAPSE_TIME", "MAX_ELAPSE_TIME"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>NORMAL DISTRIBUTION</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #NORMAL_DISTRIBUTION
     * @generated
     * @ordered
     */
    NORMAL_DISTRIBUTION_LITERAL(2, "NORMAL_DISTRIBUTION", "NORMAL_DISTRIBUTION"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * The '<em><b>MAX LOOP COUNT</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>MAX LOOP COUNT</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #MAX_LOOP_COUNT_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int MAX_LOOP_COUNT = 0;

    /**
     * The '<em><b>MAX ELAPSE TIME</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>MAX ELAPSE TIME</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #MAX_ELAPSE_TIME_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int MAX_ELAPSE_TIME = 1;

    /**
     * The '<em><b>NORMAL DISTRIBUTION</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>NORMAL DISTRIBUTION</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #NORMAL_DISTRIBUTION_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int NORMAL_DISTRIBUTION = 2;

    /**
     * An array of all the '<em><b>Loop Control Strategy</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final LoopControlStrategy[] VALUES_ARRAY =
            new LoopControlStrategy[] { MAX_LOOP_COUNT_LITERAL,
                    MAX_ELAPSE_TIME_LITERAL, NORMAL_DISTRIBUTION_LITERAL, };

    /**
     * A public read-only list of all the '<em><b>Loop Control Strategy</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<LoopControlStrategy> VALUES =
            Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Loop Control Strategy</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static LoopControlStrategy get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            LoopControlStrategy result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Loop Control Strategy</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static LoopControlStrategy getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            LoopControlStrategy result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Loop Control Strategy</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static LoopControlStrategy get(int value) {
        switch (value) {
        case MAX_LOOP_COUNT:
            return MAX_LOOP_COUNT_LITERAL;
        case MAX_ELAPSE_TIME:
            return MAX_ELAPSE_TIME_LITERAL;
        case NORMAL_DISTRIBUTION:
            return NORMAL_DISTRIBUTION_LITERAL;
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
    private LoopControlStrategy(int value, String name, String literal) {
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

} //LoopControlStrategy
