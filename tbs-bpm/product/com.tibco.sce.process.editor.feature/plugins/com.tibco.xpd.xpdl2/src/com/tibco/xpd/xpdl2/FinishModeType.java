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
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Finish Mode Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getFinishModeType()
 * @model extendedMetaData="name='FinishMode_._type'"
 * @generated
 */
public enum FinishModeType implements Enumerator {
    /**
     * The '<em><b>Automatic</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #AUTOMATIC
     * @generated
     * @ordered
     */
    AUTOMATIC_LITERAL(0, "Automatic", "Automatic"),
    /**
     * The '<em><b>Manual</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #MANUAL
     * @generated
     * @ordered
     */
    MANUAL_LITERAL(1, "Manual", "Manual");
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>Automatic</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Automatic</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #AUTOMATIC_LITERAL
     * @model name="Automatic"
     * @generated
     * @ordered
     */
    public static final int AUTOMATIC = 0;

    /**
     * The '<em><b>Manual</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Manual</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #MANUAL_LITERAL
     * @model name="Manual"
     * @generated
     * @ordered
     */
    public static final int MANUAL = 1;

    /**
     * An array of all the '<em><b>Finish Mode Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final FinishModeType[] VALUES_ARRAY = new FinishModeType[] {
            AUTOMATIC_LITERAL, MANUAL_LITERAL, };

    /**
     * A public read-only list of all the '<em><b>Finish Mode Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<FinishModeType> VALUES = Collections
            .unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Finish Mode Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static FinishModeType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            FinishModeType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Finish Mode Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static FinishModeType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            FinishModeType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Finish Mode Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static FinishModeType get(int value) {
        switch (value) {
        case AUTOMATIC:
            return AUTOMATIC_LITERAL;
        case MANUAL:
            return MANUAL_LITERAL;
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
    private FinishModeType(int value, String name, String literal) {
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
}
