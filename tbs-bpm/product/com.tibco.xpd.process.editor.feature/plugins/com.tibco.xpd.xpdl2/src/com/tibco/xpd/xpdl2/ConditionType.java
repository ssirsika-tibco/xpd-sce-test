/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '
 * <em><b>Condition Type</b></em>', and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getConditionType()
 * @model extendedMetaData="name='Type_._type'"
 * @generated
 */
public enum ConditionType implements Enumerator {
    /**
     * The '<em><b>CONDITION</b></em>' literal object. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @see #CONDITION
     * @generated NOT
     * @ordered
     */
    CONDITION_LITERAL(0, "CONDITION", "CONDITION"),
    /**
     * The '<em><b>OTHERWISE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #OTHERWISE
     * @generated
     * @ordered
     */
    OTHERWISE_LITERAL(1, "OTHERWISE", "OTHERWISE"),
    /**
     * The '<em><b>EXCEPTION</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #EXCEPTION
     * @generated
     * @ordered
     */
    EXCEPTION_LITERAL(2, "EXCEPTION", "EXCEPTION"),
    /**
     * The '<em><b>DEFAULTEXCEPTION</b></em>' literal object. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #DEFAULTEXCEPTION
     * @generated
     * @ordered
     */
    DEFAULTEXCEPTION_LITERAL(3, "DEFAULTEXCEPTION", "DEFAULTEXCEPTION");
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>CONDITION</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>CONDITION</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #CONDITION_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int CONDITION = 0;

    /**
     * The '<em><b>OTHERWISE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>OTHERWISE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #OTHERWISE_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int OTHERWISE = 1;

    /**
     * The '<em><b>EXCEPTION</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>EXCEPTION</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #EXCEPTION_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int EXCEPTION = 2;

    /**
     * The '<em><b>DEFAULTEXCEPTION</b></em>' literal value.
     * <!-- begin-user-doc
     * -->
     * <p>
     * If the meaning of '<em><b>DEFAULTEXCEPTION</b></em>' literal object isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DEFAULTEXCEPTION_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int DEFAULTEXCEPTION = 3;

    /**
     * An array of all the '<em><b>Condition Type</b></em>' enumerators. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private static final ConditionType[] VALUES_ARRAY =
            new ConditionType[] { CONDITION_LITERAL, OTHERWISE_LITERAL, EXCEPTION_LITERAL, DEFAULTEXCEPTION_LITERAL, };

    /**
     * A public read-only list of all the '<em><b>Condition Type</b></em>' enumerators.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final List<ConditionType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Condition Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param literal the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static ConditionType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ConditionType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Condition Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param name the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static ConditionType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ConditionType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Condition Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static ConditionType get(int value) {
        switch (value) {
        case CONDITION:
            return CONDITION_LITERAL;
        case OTHERWISE:
            return OTHERWISE_LITERAL;
        case EXCEPTION:
            return EXCEPTION_LITERAL;
        case DEFAULTEXCEPTION:
            return DEFAULTEXCEPTION_LITERAL;
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private final int value;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private final String name;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private final String literal;

    /**
     * Only this class can construct instances.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    private ConditionType(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public int getValue() {
        return value;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getLiteral() {
        return literal;
    }

    /**
     * Returns the literal value of the enumerator, which is its string representation.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        return literal;
    }
}
