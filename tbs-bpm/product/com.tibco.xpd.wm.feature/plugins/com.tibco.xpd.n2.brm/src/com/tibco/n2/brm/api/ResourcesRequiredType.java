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
 * A representation of the literals of the enumeration '<em><b>Resources Required Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * Enumerated value defining if specific resources are being requested. Currently the only supported option is ALL.
 * <!-- end-model-doc -->
 * @see com.tibco.n2.brm.api.N2BRMPackage#getResourcesRequiredType()
 * @model extendedMetaData="name='ResourcesRequiredType'"
 * @generated
 */
public enum ResourcesRequiredType implements Enumerator {
    /**
     * The '<em><b>ALL</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ALL_VALUE
     * @generated
     * @ordered
     */
    ALL(0, "ALL", "ALL"),

    /**
     * The '<em><b>MINE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #MINE_VALUE
     * @generated
     * @ordered
     */
    MINE(1, "MINE", "MINE");

    /**
     * The '<em><b>ALL</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>ALL</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ALL
     * @model
     * @generated
     * @ordered
     */
    public static final int ALL_VALUE = 0;

    /**
     * The '<em><b>MINE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>MINE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #MINE
     * @model
     * @generated
     * @ordered
     */
    public static final int MINE_VALUE = 1;

    /**
     * An array of all the '<em><b>Resources Required Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final ResourcesRequiredType[] VALUES_ARRAY =
        new ResourcesRequiredType[] {
            ALL,
            MINE,
        };

    /**
     * A public read-only list of all the '<em><b>Resources Required Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<ResourcesRequiredType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Resources Required Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ResourcesRequiredType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ResourcesRequiredType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Resources Required Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ResourcesRequiredType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ResourcesRequiredType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Resources Required Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ResourcesRequiredType get(int value) {
        switch (value) {
            case ALL_VALUE: return ALL;
            case MINE_VALUE: return MINE;
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
    private ResourcesRequiredType(int value, String name, String literal) {
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
    
} //ResourcesRequiredType
