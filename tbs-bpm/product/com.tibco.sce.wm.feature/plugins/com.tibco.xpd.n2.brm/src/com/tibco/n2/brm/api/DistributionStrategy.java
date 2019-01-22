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
 * A representation of the literals of the enumeration '<em><b>Distribution Strategy</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * Enumerated value defining the the allowable types of distribution strategy (e.g. OFFER, ALLOCATE).
 * <!-- end-model-doc -->
 * @see com.tibco.n2.brm.api.N2BRMPackage#getDistributionStrategy()
 * @model extendedMetaData="name='DistributionStrategy'"
 * @generated
 */
public enum DistributionStrategy implements Enumerator {
    /**
     * The '<em><b>OFFER</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #OFFER_VALUE
     * @generated
     * @ordered
     */
    OFFER(0, "OFFER", "OFFER"),

    /**
     * The '<em><b>ALLOCATE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ALLOCATE_VALUE
     * @generated
     * @ordered
     */
    ALLOCATE(1, "ALLOCATE", "ALLOCATE");

    /**
     * The '<em><b>OFFER</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>OFFER</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #OFFER
     * @model
     * @generated
     * @ordered
     */
    public static final int OFFER_VALUE = 0;

    /**
     * The '<em><b>ALLOCATE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>ALLOCATE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ALLOCATE
     * @model
     * @generated
     * @ordered
     */
    public static final int ALLOCATE_VALUE = 1;

    /**
     * An array of all the '<em><b>Distribution Strategy</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final DistributionStrategy[] VALUES_ARRAY =
        new DistributionStrategy[] {
            OFFER,
            ALLOCATE,
        };

    /**
     * A public read-only list of all the '<em><b>Distribution Strategy</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<DistributionStrategy> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Distribution Strategy</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static DistributionStrategy get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            DistributionStrategy result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Distribution Strategy</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static DistributionStrategy getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            DistributionStrategy result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Distribution Strategy</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static DistributionStrategy get(int value) {
        switch (value) {
            case OFFER_VALUE: return OFFER;
            case ALLOCATE_VALUE: return ALLOCATE;
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
    private DistributionStrategy(int value, String name, String literal) {
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
    
} //DistributionStrategy
