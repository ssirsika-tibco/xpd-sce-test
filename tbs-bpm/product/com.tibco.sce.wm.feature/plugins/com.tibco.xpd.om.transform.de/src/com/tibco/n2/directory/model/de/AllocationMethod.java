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
 * A representation of the literals of the enumeration '<em><b>Allocation Method</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * 
 *         Forms part of the SelectionMode attribute group, this enumerated type
 *         identifies the algorithm used to select a resources associated with a
 *         particular organizational entity.
 *       
 * <!-- end-model-doc -->
 * @see com.tibco.n2.directory.model.de.DePackage#getAllocationMethod()
 * @model extendedMetaData="name='AllocationMethod'"
 * @generated
 */
public enum AllocationMethod implements Enumerator {
    /**
     * The '<em><b>ANY</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ANY_VALUE
     * @generated
     * @ordered
     */
    ANY(0, "ANY", "ANY"),

    /**
     * The '<em><b>NEXT</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #NEXT_VALUE
     * @generated
     * @ordered
     */
    NEXT(1, "NEXT", "NEXT"),

    /**
     * The '<em><b>THIS</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #THIS_VALUE
     * @generated
     * @ordered
     */
    THIS(2, "THIS", "THIS"),

    /**
     * The '<em><b>PLUGIN</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #PLUGIN_VALUE
     * @generated
     * @ordered
     */
    PLUGIN(3, "PLUGIN", "PLUGIN");

    /**
     * The '<em><b>ANY</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Work is allocated on a random basis.
     * <!-- end-model-doc -->
     * @see #ANY
     * @model
     * @generated
     * @ordered
     */
    public static final int ANY_VALUE = 0;

    /**
     * The '<em><b>NEXT</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Work is allocated on a round-robin basis.
     * <!-- end-model-doc -->
     * @see #NEXT
     * @model
     * @generated
     * @ordered
     */
    public static final int NEXT_VALUE = 1;

    /**
     * The '<em><b>THIS</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Work is allocated to the specifically identified Resource (user).
     * <!-- end-model-doc -->
     * @see #THIS
     * @model
     * @generated
     * @ordered
     */
    public static final int THIS_VALUE = 2;

    /**
     * The '<em><b>PLUGIN</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Work is allocated according to the algorithm of the named plug-in - not yet supported.
     * <!-- end-model-doc -->
     * @see #PLUGIN
     * @model
     * @generated
     * @ordered
     */
    public static final int PLUGIN_VALUE = 3;

    /**
     * An array of all the '<em><b>Allocation Method</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final AllocationMethod[] VALUES_ARRAY =
        new AllocationMethod[] {
            ANY,
            NEXT,
            THIS,
            PLUGIN,
        };

    /**
     * A public read-only list of all the '<em><b>Allocation Method</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<AllocationMethod> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Allocation Method</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static AllocationMethod get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            AllocationMethod result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Allocation Method</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static AllocationMethod getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            AllocationMethod result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Allocation Method</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static AllocationMethod get(int value) {
        switch (value) {
            case ANY_VALUE: return ANY;
            case NEXT_VALUE: return NEXT;
            case THIS_VALUE: return THIS;
            case PLUGIN_VALUE: return PLUGIN;
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
    private AllocationMethod(int value, String name, String literal) {
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
    
} //AllocationMethod
