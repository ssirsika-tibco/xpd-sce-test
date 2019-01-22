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
 * A representation of the literals of the enumeration '<em><b>Column Capability</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * Enumerated value defining the type of operations supported by a column for work list sorting and/or filtering (e.g. NONE, ORDER, FILTER).
 * <!-- end-model-doc -->
 * @see com.tibco.n2.brm.api.N2BRMPackage#getColumnCapability()
 * @model extendedMetaData="name='ColumnCapability'"
 * @generated
 */
public enum ColumnCapability implements Enumerator {
    /**
     * The '<em><b>NONE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #NONE_VALUE
     * @generated
     * @ordered
     */
    NONE(0, "NONE", "NONE"),

    /**
     * The '<em><b>ORDER</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ORDER_VALUE
     * @generated
     * @ordered
     */
    ORDER(1, "ORDER", "ORDER"),

    /**
     * The '<em><b>FILTER</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #FILTER_VALUE
     * @generated
     * @ordered
     */
    FILTER(2, "FILTER", "FILTER"),

    /**
     * The '<em><b>BOTHORDERFILTER</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #BOTHORDERFILTER_VALUE
     * @generated
     * @ordered
     */
    BOTHORDERFILTER(3, "BOTHORDERFILTER", "BOTH_ORDER_FILTER");

    /**
     * The '<em><b>NONE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>NONE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #NONE
     * @model
     * @generated
     * @ordered
     */
    public static final int NONE_VALUE = 0;

    /**
     * The '<em><b>ORDER</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>ORDER</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ORDER
     * @model
     * @generated
     * @ordered
     */
    public static final int ORDER_VALUE = 1;

    /**
     * The '<em><b>FILTER</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>FILTER</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #FILTER
     * @model
     * @generated
     * @ordered
     */
    public static final int FILTER_VALUE = 2;

    /**
     * The '<em><b>BOTHORDERFILTER</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>BOTHORDERFILTER</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #BOTHORDERFILTER
     * @model literal="BOTH_ORDER_FILTER"
     * @generated
     * @ordered
     */
    public static final int BOTHORDERFILTER_VALUE = 3;

    /**
     * An array of all the '<em><b>Column Capability</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final ColumnCapability[] VALUES_ARRAY =
        new ColumnCapability[] {
            NONE,
            ORDER,
            FILTER,
            BOTHORDERFILTER,
        };

    /**
     * A public read-only list of all the '<em><b>Column Capability</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<ColumnCapability> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Column Capability</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ColumnCapability get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ColumnCapability result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Column Capability</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ColumnCapability getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ColumnCapability result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Column Capability</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ColumnCapability get(int value) {
        switch (value) {
            case NONE_VALUE: return NONE;
            case ORDER_VALUE: return ORDER;
            case FILTER_VALUE: return FILTER;
            case BOTHORDERFILTER_VALUE: return BOTHORDERFILTER;
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
    private ColumnCapability(int value, String name, String literal) {
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
    
} //ColumnCapability
