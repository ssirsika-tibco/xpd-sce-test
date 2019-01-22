/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Resource Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * 
 *         Identifies a particular type of Resource.
 *       
 * <!-- end-model-doc -->
 * @see com.tibco.n2.directory.model.de.DePackage#getResourceType()
 * @model extendedMetaData="name='ResourceType'"
 * @generated
 */
public enum ResourceType implements Enumerator {
    /**
     * The '<em><b>Durable</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #DURABLE_VALUE
     * @generated
     * @ordered
     */
    DURABLE(0, "durable", "durable"),

    /**
     * The '<em><b>Consumable</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #CONSUMABLE_VALUE
     * @generated
     * @ordered
     */
    CONSUMABLE(1, "consumable", "consumable"),

    /**
     * The '<em><b>Human</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #HUMAN_VALUE
     * @generated
     * @ordered
     */
    HUMAN(2, "human", "human");

    /**
     * The '<em><b>Durable</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Durable</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DURABLE
     * @model name="durable"
     * @generated
     * @ordered
     */
    public static final int DURABLE_VALUE = 0;

    /**
     * The '<em><b>Consumable</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Consumable</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #CONSUMABLE
     * @model name="consumable"
     * @generated
     * @ordered
     */
    public static final int CONSUMABLE_VALUE = 1;

    /**
     * The '<em><b>Human</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Human</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #HUMAN
     * @model name="human"
     * @generated
     * @ordered
     */
    public static final int HUMAN_VALUE = 2;

    /**
     * An array of all the '<em><b>Resource Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final ResourceType[] VALUES_ARRAY =
        new ResourceType[] {
            DURABLE,
            CONSUMABLE,
            HUMAN,
        };

    /**
     * A public read-only list of all the '<em><b>Resource Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<ResourceType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Resource Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ResourceType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ResourceType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Resource Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ResourceType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ResourceType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Resource Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ResourceType get(int value) {
        switch (value) {
            case DURABLE_VALUE: return DURABLE;
            case CONSUMABLE_VALUE: return CONSUMABLE;
            case HUMAN_VALUE: return HUMAN;
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
    private ResourceType(int value, String name, String literal) {
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
    
} //ResourceType
