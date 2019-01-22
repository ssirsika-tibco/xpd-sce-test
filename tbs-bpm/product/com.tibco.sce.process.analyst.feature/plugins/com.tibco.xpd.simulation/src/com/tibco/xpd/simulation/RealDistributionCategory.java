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
 * A representation of the literals of the enumeration '<em><b>Real Distribution Category</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.simulation.SimulationPackage#getRealDistributionCategory()
 * @model extendedMetaData="name='RealDistributionCategory'"
 * @generated
 */
public enum RealDistributionCategory implements Enumerator {
    /**
     * The '<em><b>CONSTANT</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #CONSTANT
     * @generated
     * @ordered
     */
    CONSTANT_LITERAL(0, "CONSTANT", "CONSTANT"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>UNIFORM</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #UNIFORM
     * @generated
     * @ordered
     */
    UNIFORM_LITERAL(1, "UNIFORM", "UNIFORM"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>NORMAL</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #NORMAL
     * @generated
     * @ordered
     */
    NORMAL_LITERAL(2, "NORMAL", "NORMAL"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>EXPONENTIAL</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #EXPONENTIAL
     * @generated
     * @ordered
     */
    EXPONENTIAL_LITERAL(3, "EXPONENTIAL", "EXPONENTIAL"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>EMPIRICAL</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #EMPIRICAL
     * @generated
     * @ordered
     */
    EMPIRICAL_LITERAL(4, "EMPIRICAL", "EMPIRICAL"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>PARAMETER BASED</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #PARAMETER_BASED
     * @generated
     * @ordered
     */
    PARAMETER_BASED_LITERAL(5, "PARAMETER_BASED", "PARAMETER_BASED"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * The '<em><b>CONSTANT</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>CONSTANT</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #CONSTANT_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int CONSTANT = 0;

    /**
     * The '<em><b>UNIFORM</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>UNIFORM</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #UNIFORM_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int UNIFORM = 1;

    /**
     * The '<em><b>NORMAL</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>NORMAL</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #NORMAL_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int NORMAL = 2;

    /**
     * The '<em><b>EXPONENTIAL</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>EXPONENTIAL</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #EXPONENTIAL_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int EXPONENTIAL = 3;

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
    public static final int EMPIRICAL = 4;

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
    public static final int PARAMETER_BASED = 5;

    /**
     * An array of all the '<em><b>Real Distribution Category</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final RealDistributionCategory[] VALUES_ARRAY =
            new RealDistributionCategory[] { CONSTANT_LITERAL, UNIFORM_LITERAL,
                    NORMAL_LITERAL, EXPONENTIAL_LITERAL, EMPIRICAL_LITERAL,
                    PARAMETER_BASED_LITERAL, };

    /**
     * A public read-only list of all the '<em><b>Real Distribution Category</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<RealDistributionCategory> VALUES =
            Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Real Distribution Category</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static RealDistributionCategory get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            RealDistributionCategory result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Real Distribution Category</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static RealDistributionCategory getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            RealDistributionCategory result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Real Distribution Category</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static RealDistributionCategory get(int value) {
        switch (value) {
        case CONSTANT:
            return CONSTANT_LITERAL;
        case UNIFORM:
            return UNIFORM_LITERAL;
        case NORMAL:
            return NORMAL_LITERAL;
        case EXPONENTIAL:
            return EXPONENTIAL_LITERAL;
        case EMPIRICAL:
            return EMPIRICAL_LITERAL;
        case PARAMETER_BASED:
            return PARAMETER_BASED_LITERAL;
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
    private RealDistributionCategory(int value, String name, String literal) {
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

} //RealDistributionCategory
