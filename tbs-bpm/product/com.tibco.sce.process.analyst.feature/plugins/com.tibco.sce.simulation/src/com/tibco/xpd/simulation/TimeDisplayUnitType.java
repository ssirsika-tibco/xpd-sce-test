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
 * A representation of the literals of the enumeration '<em><b>Time Display Unit Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.simulation.SimulationPackage#getTimeDisplayUnitType()
 * @model extendedMetaData="name='TimeDisplayUnitType'"
 * @generated
 */
public enum TimeDisplayUnitType implements Enumerator {
    /**
     * The '<em><b>YEAR</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #YEAR
     * @generated
     * @ordered
     */
    YEAR_LITERAL(0, "YEAR", "YEAR"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>MONTH</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #MONTH
     * @generated
     * @ordered
     */
    MONTH_LITERAL(1, "MONTH", "MONTH"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>DAY</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #DAY
     * @generated
     * @ordered
     */
    DAY_LITERAL(2, "DAY", "DAY"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>HOUR</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #HOUR
     * @generated
     * @ordered
     */
    HOUR_LITERAL(3, "HOUR", "HOUR"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>MINUTE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #MINUTE
     * @generated
     * @ordered
     */
    MINUTE_LITERAL(4, "MINUTE", "MINUTE"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>SECOND</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #SECOND
     * @generated
     * @ordered
     */
    SECOND_LITERAL(5, "SECOND", "SECOND"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * The '<em><b>YEAR</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>YEAR</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #YEAR_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int YEAR = 0;

    /**
     * The '<em><b>MONTH</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>MONTH</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #MONTH_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int MONTH = 1;

    /**
     * The '<em><b>DAY</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>DAY</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DAY_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int DAY = 2;

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
    public static final int HOUR = 3;

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
    public static final int MINUTE = 4;

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
    public static final int SECOND = 5;

    /**
     * An array of all the '<em><b>Time Display Unit Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final TimeDisplayUnitType[] VALUES_ARRAY =
            new TimeDisplayUnitType[] { YEAR_LITERAL, MONTH_LITERAL,
                    DAY_LITERAL, HOUR_LITERAL, MINUTE_LITERAL, SECOND_LITERAL, };

    /**
     * A public read-only list of all the '<em><b>Time Display Unit Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<TimeDisplayUnitType> VALUES =
            Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Time Display Unit Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static TimeDisplayUnitType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            TimeDisplayUnitType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Time Display Unit Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static TimeDisplayUnitType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            TimeDisplayUnitType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Time Display Unit Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static TimeDisplayUnitType get(int value) {
        switch (value) {
        case YEAR:
            return YEAR_LITERAL;
        case MONTH:
            return MONTH_LITERAL;
        case DAY:
            return DAY_LITERAL;
        case HOUR:
            return HOUR_LITERAL;
        case MINUTE:
            return MINUTE_LITERAL;
        case SECOND:
            return SECOND_LITERAL;
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
    private TimeDisplayUnitType(int value, String name, String literal) {
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

} //TimeDisplayUnitType
