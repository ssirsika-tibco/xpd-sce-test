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
 * A representation of the literals of the enumeration '<em><b>Result Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getResultType()
 * @model extendedMetaData="name='Result_._type'"
 * @generated
 */
public enum ResultType implements Enumerator {
    /**
     * The '<em><b>None</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #NONE
     * @generated
     * @ordered
     */
    NONE_LITERAL(0, "None", "None"),
    /**
     * The '<em><b>Message</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #MESSAGE
     * @generated
     * @ordered
     */
    MESSAGE_LITERAL(1, "Message", "Message"),
    /**
     * The '<em><b>Error</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ERROR
     * @generated
     * @ordered
     */
    ERROR_LITERAL(2, "Error", "Error"),
    /**
     * The '<em><b>Cancel</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #CANCEL
     * @generated
     * @ordered
     */
    CANCEL_LITERAL(3, "Cancel", "Cancel"),
    /**
     * The '<em><b>Compensation</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #COMPENSATION
     * @generated
     * @ordered
     */
    COMPENSATION_LITERAL(4, "Compensation", "Compensation"),
    /**
     * The '<em><b>Signal</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #SIGNAL
     * @generated
     * @ordered
     */
    SIGNAL_LITERAL(5, "Signal", "Signal"),
    /**
     * The '<em><b>Terminate</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #TERMINATE
     * @generated
     * @ordered
     */
    TERMINATE_LITERAL(6, "Terminate", "Terminate"),
    /**
     * The '<em><b>Multiple</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #MULTIPLE
     * @generated
     * @ordered
     */
    MULTIPLE_LITERAL(7, "Multiple", "Multiple"),
    /**
    * The '<em><b>Deprecated Link</b></em>' literal object.
    * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    * @see #DEPRECATED_LINK
    * @generated
    * @ordered
    */
    DEPRECATED_LINK_LITERAL(8, "DeprecatedLink", "DeprecatedLink");
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>None</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>None</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #NONE_LITERAL
     * @model name="None"
     * @generated
     * @ordered
     */
    public static final int NONE = 0;

    /**
     * The '<em><b>Message</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Message</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #MESSAGE_LITERAL
     * @model name="Message"
     * @generated
     * @ordered
     */
    public static final int MESSAGE = 1;

    /**
     * The '<em><b>Error</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Error</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ERROR_LITERAL
     * @model name="Error"
     * @generated
     * @ordered
     */
    public static final int ERROR = 2;

    /**
     * The '<em><b>Cancel</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Cancel</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #CANCEL_LITERAL
     * @model name="Cancel"
     * @generated
     * @ordered
     */
    public static final int CANCEL = 3;

    /**
     * The '<em><b>Compensation</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Compensation</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #COMPENSATION_LITERAL
     * @model name="Compensation"
     * @generated
     * @ordered
     */
    public static final int COMPENSATION = 4;

    /**
     * The '<em><b>Signal</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Signal</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #SIGNAL_LITERAL
     * @model name="Signal"
     * @generated
     * @ordered
     */
    public static final int SIGNAL = 5;

    /**
     * The '<em><b>Terminate</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Terminate</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #TERMINATE_LITERAL
     * @model name="Terminate"
     * @generated
     * @ordered
     */
    public static final int TERMINATE = 6;

    /**
     * The '<em><b>Multiple</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Multiple</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #MULTIPLE_LITERAL
     * @model name="Multiple"
     * @generated
     * @ordered
     */
    public static final int MULTIPLE = 7;

    /**
     * The '<em><b>Deprecated Link</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Deprecated Link</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DEPRECATED_LINK_LITERAL
     * @model name="DeprecatedLink"
     * @generated
     * @ordered
     */
    public static final int DEPRECATED_LINK = 8;

    /**
     * An array of all the '<em><b>Result Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final ResultType[] VALUES_ARRAY =
            new ResultType[] { NONE_LITERAL, MESSAGE_LITERAL, ERROR_LITERAL, CANCEL_LITERAL, COMPENSATION_LITERAL,
                    SIGNAL_LITERAL, TERMINATE_LITERAL, MULTIPLE_LITERAL, DEPRECATED_LINK_LITERAL, };

    /**
     * A public read-only list of all the '<em><b>Result Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<ResultType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Result Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param literal the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static ResultType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ResultType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Result Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param name the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static ResultType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ResultType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Result Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static ResultType get(int value) {
        switch (value) {
        case NONE:
            return NONE_LITERAL;
        case MESSAGE:
            return MESSAGE_LITERAL;
        case ERROR:
            return ERROR_LITERAL;
        case CANCEL:
            return CANCEL_LITERAL;
        case COMPENSATION:
            return COMPENSATION_LITERAL;
        case SIGNAL:
            return SIGNAL_LITERAL;
        case TERMINATE:
            return TERMINATE_LITERAL;
        case MULTIPLE:
            return MULTIPLE_LITERAL;
        case DEPRECATED_LINK:
            return DEPRECATED_LINK_LITERAL;
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
    private ResultType(int value, String name, String literal) {
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
