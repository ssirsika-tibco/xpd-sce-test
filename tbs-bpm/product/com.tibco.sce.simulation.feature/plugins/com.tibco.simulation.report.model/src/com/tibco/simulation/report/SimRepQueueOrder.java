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
 * A representation of the literals of the enumeration '<em><b>Queue Order</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.simulation.report.SimRepPackage#getSimRepQueueOrder()
 * @model extendedMetaData="name='QueueOrderType'"
 * @generated
 */
public enum SimRepQueueOrder implements Enumerator {
    /**
     * The '<em><b>FIFO</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #FIFO
     * @generated
     * @ordered
     */
    FIFO_LITERAL(0, "FIFO", "FIFO"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>LIFO</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #LIFO
     * @generated
     * @ordered
     */
    LIFO_LITERAL(1, "LIFO", "LIFO"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * The '<em><b>FIFO</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>FIFO</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #FIFO_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int FIFO = 0;

    /**
     * The '<em><b>LIFO</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>LIFO</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #LIFO_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int LIFO = 1;

    /**
     * An array of all the '<em><b>Queue Order</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final SimRepQueueOrder[] VALUES_ARRAY =
            new SimRepQueueOrder[] { FIFO_LITERAL, LIFO_LITERAL, };

    /**
     * A public read-only list of all the '<em><b>Queue Order</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<SimRepQueueOrder> VALUES = Collections
            .unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Queue Order</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SimRepQueueOrder get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            SimRepQueueOrder result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Queue Order</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SimRepQueueOrder getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            SimRepQueueOrder result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Queue Order</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SimRepQueueOrder get(int value) {
        switch (value) {
        case FIFO:
            return FIFO_LITERAL;
        case LIFO:
            return LIFO_LITERAL;
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
    private SimRepQueueOrder(int value, String name, String literal) {
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

} //SimRepQueueOrder
