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
 * A representation of the literals of the enumeration '<em><b>Distribution Category</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.simulation.report.SimRepPackage#getSimRepDistributionCategory()
 * @model extendedMetaData="name='DistributionCategoryType'"
 * @generated
 */
public enum SimRepDistributionCategory implements Enumerator {
    /**
     * The '<em><b>REAL CONSTANT</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #REAL_CONSTANT
     * @generated
     * @ordered
     */
    REAL_CONSTANT_LITERAL(0, "REAL_CONSTANT", "REAL_CONSTANT"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>REAL UNIFORM</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #REAL_UNIFORM
     * @generated
     * @ordered
     */
    REAL_UNIFORM_LITERAL(1, "REAL_UNIFORM", "REAL_UNIFORM"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>REAL NORMAL</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #REAL_NORMAL
     * @generated
     * @ordered
     */
    REAL_NORMAL_LITERAL(2, "REAL_NORMAL", "REAL_NORMAL"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>REAL EXPONENTIAL</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #REAL_EXPONENTIAL
     * @generated
     * @ordered
     */
    REAL_EXPONENTIAL_LITERAL(3, "REAL_EXPONENTIAL", "REAL_EXPONENTIAL"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>PARAMETER BASED</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #PARAMETER_BASED
     * @generated
     * @ordered
     */
    PARAMETER_BASED_LITERAL(4, "PARAMETER_BASED", "PARAMETER_BASED"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>EMPIRICAL</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #EMPIRICAL
     * @generated
     * @ordered
     */
    EMPIRICAL_LITERAL(5, "EMPIRICAL", "EMPIRICAL"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * The '<em><b>REAL CONSTANT</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>REAL CONSTANT</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #REAL_CONSTANT_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int REAL_CONSTANT = 0;

    /**
     * The '<em><b>REAL UNIFORM</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>REAL UNIFORM</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #REAL_UNIFORM_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int REAL_UNIFORM = 1;

    /**
     * The '<em><b>REAL NORMAL</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>REAL NORMAL</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #REAL_NORMAL_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int REAL_NORMAL = 2;

    /**
     * The '<em><b>REAL EXPONENTIAL</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>REAL EXPONENTIAL</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #REAL_EXPONENTIAL_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int REAL_EXPONENTIAL = 3;

    /**
     * The '<em><b>PARAMETER BASED</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>PARAMETER BASED</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #PARAMETER_BASED_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int PARAMETER_BASED = 4;

    /**
     * The '<em><b>EMPIRICAL</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>EMPIRICAL</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #EMPIRICAL_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int EMPIRICAL = 5;

    /**
     * An array of all the '<em><b>Distribution Category</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final SimRepDistributionCategory[] VALUES_ARRAY =
            new SimRepDistributionCategory[] { REAL_CONSTANT_LITERAL,
                    REAL_UNIFORM_LITERAL, REAL_NORMAL_LITERAL,
                    REAL_EXPONENTIAL_LITERAL, PARAMETER_BASED_LITERAL,
                    EMPIRICAL_LITERAL, };

    /**
     * A public read-only list of all the '<em><b>Distribution Category</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<SimRepDistributionCategory> VALUES = Collections
            .unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Distribution Category</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SimRepDistributionCategory get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            SimRepDistributionCategory result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Distribution Category</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SimRepDistributionCategory getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            SimRepDistributionCategory result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Distribution Category</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SimRepDistributionCategory get(int value) {
        switch (value) {
        case REAL_CONSTANT:
            return REAL_CONSTANT_LITERAL;
        case REAL_UNIFORM:
            return REAL_UNIFORM_LITERAL;
        case REAL_NORMAL:
            return REAL_NORMAL_LITERAL;
        case REAL_EXPONENTIAL:
            return REAL_EXPONENTIAL_LITERAL;
        case PARAMETER_BASED:
            return PARAMETER_BASED_LITERAL;
        case EMPIRICAL:
            return EMPIRICAL_LITERAL;
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
    private SimRepDistributionCategory(int value, String name, String literal) {
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

} //SimRepDistributionCategory
