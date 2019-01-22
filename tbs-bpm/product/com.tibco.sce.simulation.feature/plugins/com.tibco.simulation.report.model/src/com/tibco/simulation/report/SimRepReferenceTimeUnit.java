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
 * A representation of the literals of the enumeration '<em><b>Reference Time Unit</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.simulation.report.SimRepPackage#getSimRepReferenceTimeUnit()
 * @model extendedMetaData="name='ReferenceTimeUnitType'"
 * @generated
 */
public enum SimRepReferenceTimeUnit implements Enumerator {
    /**
     * The '<em><b>SECOND</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #SECOND
     * @generated
     * @ordered
     */
    SECOND_LITERAL(0, "SECOND", "SECOND"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>MINUTE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #MINUTE
     * @generated
     * @ordered
     */
    MINUTE_LITERAL(1, "MINUTE", "MINUTE"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>HOUR</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #HOUR
     * @generated
     * @ordered
     */
    HOUR_LITERAL(2, "HOUR", "HOUR"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * The '<em><b>SECOND</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>SECOND</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #SECOND_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int SECOND = 0;

    /**
     * The '<em><b>MINUTE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>MINUTE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #MINUTE_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int MINUTE = 1;

    /**
     * The '<em><b>HOUR</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>HOUR</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #HOUR_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int HOUR = 2;

    /**
     * An array of all the '<em><b>Reference Time Unit</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final SimRepReferenceTimeUnit[] VALUES_ARRAY =
            new SimRepReferenceTimeUnit[] { SECOND_LITERAL, MINUTE_LITERAL,
                    HOUR_LITERAL, };

    /**
     * A public read-only list of all the '<em><b>Reference Time Unit</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<SimRepReferenceTimeUnit> VALUES = Collections
            .unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Reference Time Unit</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SimRepReferenceTimeUnit get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            SimRepReferenceTimeUnit result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Reference Time Unit</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SimRepReferenceTimeUnit getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            SimRepReferenceTimeUnit result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Reference Time Unit</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SimRepReferenceTimeUnit get(int value) {
        switch (value) {
        case SECOND:
            return SECOND_LITERAL;
        case MINUTE:
            return MINUTE_LITERAL;
        case HOUR:
            return HOUR_LITERAL;
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
    private SimRepReferenceTimeUnit(int value, String name, String literal) {
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

} //SimRepReferenceTimeUnit
