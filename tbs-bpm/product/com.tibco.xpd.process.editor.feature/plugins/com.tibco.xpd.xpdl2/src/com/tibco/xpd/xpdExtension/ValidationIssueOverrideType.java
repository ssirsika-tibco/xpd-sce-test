/**
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Validation Issue Override Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * Enumeration of various validation rule overrides.
 * <!-- end-model-doc -->
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getValidationIssueOverrideType()
 * @model extendedMetaData="name='ValidationIssueOverrideType_._type'"
 * @generated
 */
public enum ValidationIssueOverrideType implements Enumerator {
    /**
     * The '<em><b>Suppress Until Next Flow Change</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #SUPPRESS_UNTIL_NEXT_FLOW_CHANGE_VALUE
     * @generated
     * @ordered
     */
    SUPPRESS_UNTIL_NEXT_FLOW_CHANGE(1, "SuppressUntilNextFlowChange", //$NON-NLS-1$
            "SuppressUntilNextFlowChange"), //$NON-NLS-1$

    /**
     * The '<em><b>Suppress Until Manual Reactivation</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #SUPPRESS_UNTIL_MANUAL_REACTIVATION_VALUE
     * @generated
     * @ordered
     */
    SUPPRESS_UNTIL_MANUAL_REACTIVATION(2, "SuppressUntilManualReactivation", //$NON-NLS-1$
            "SuppressUntilManualReactivation"); //$NON-NLS-1$

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>Suppress Until Next Flow Change</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Suppress Until Next Flow Change</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #SUPPRESS_UNTIL_NEXT_FLOW_CHANGE
     * @model name="SuppressUntilNextFlowChange"
     * @generated
     * @ordered
     */
    public static final int SUPPRESS_UNTIL_NEXT_FLOW_CHANGE_VALUE = 1;

    /**
     * The '<em><b>Suppress Until Manual Reactivation</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Suppress Until Manual Reactivation</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #SUPPRESS_UNTIL_MANUAL_REACTIVATION
     * @model name="SuppressUntilManualReactivation"
     * @generated
     * @ordered
     */
    public static final int SUPPRESS_UNTIL_MANUAL_REACTIVATION_VALUE = 2;

    /**
     * An array of all the '<em><b>Validation Issue Override Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final ValidationIssueOverrideType[] VALUES_ARRAY =
            new ValidationIssueOverrideType[] { SUPPRESS_UNTIL_NEXT_FLOW_CHANGE, SUPPRESS_UNTIL_MANUAL_REACTIVATION, };

    /**
     * A public read-only list of all the '<em><b>Validation Issue Override Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<ValidationIssueOverrideType> VALUES =
            Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Validation Issue Override Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param literal the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static ValidationIssueOverrideType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ValidationIssueOverrideType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Validation Issue Override Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param name the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static ValidationIssueOverrideType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ValidationIssueOverrideType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Validation Issue Override Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static ValidationIssueOverrideType get(int value) {
        switch (value) {
        case SUPPRESS_UNTIL_NEXT_FLOW_CHANGE_VALUE:
            return SUPPRESS_UNTIL_NEXT_FLOW_CHANGE;
        case SUPPRESS_UNTIL_MANUAL_REACTIVATION_VALUE:
            return SUPPRESS_UNTIL_MANUAL_REACTIVATION;
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
    private ValidationIssueOverrideType(int value, String name, String literal) {
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

} //ValidationIssueOverrideType
