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
 * A representation of the literals of the enumeration '<em><b>Trigger Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerType()
 * @model extendedMetaData="name='Trigger_._type'"
 * @generated
 */
public enum TriggerType implements Enumerator {
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
     * The '<em><b>Timer</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #TIMER
     * @generated
     * @ordered
     */
    TIMER_LITERAL(2, "Timer", "Timer"),
    /**
     * The '<em><b>Error</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ERROR
     * @generated
     * @ordered
     */
    ERROR_LITERAL(3, "Error", "Error"),
    /**
     * The '<em><b>Cancel</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #CANCEL
     * @generated
     * @ordered
     */
    CANCEL_LITERAL(4, "Cancel", "Cancel"),
    /**
     * The '<em><b>Conditional</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #CONDITIONAL
     * @generated
     * @ordered
     */
    CONDITIONAL_LITERAL(5, "Conditional", "Conditional"),
    /**
     * The '<em><b>Link</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #LINK
     * @generated
     * @ordered
     */
    LINK_LITERAL(6, "Link", "Link"),
    /**
     * The '<em><b>Compensation</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #COMPENSATION
     * @generated
     * @ordered
     */
    COMPENSATION_LITERAL(7, "Compensation", "Compensation"),
    /**
     * The '<em><b>Multiple</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #MULTIPLE
     * @generated
     * @ordered
     */
    MULTIPLE_LITERAL(8, "Multiple", "Multiple"),
    /**
     * The '<em><b>Signal</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #SIGNAL
     * @generated
     * @ordered
     */
    SIGNAL_LITERAL(9, "Signal", "Signal"), /**
                                            * The '<em><b>Deprecated Rule</b></em>' literal object.
                                            * <!-- begin-user-doc -->
                                            * <!-- end-user-doc -->
                                            * @see #DEPRECATED_RULE
                                            * @generated
                                            * @ordered
                                            */
    DEPRECATED_RULE_LITERAL(10, "DeprecatedRule", "DeprecatedRule");
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

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
     * The '<em><b>Timer</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Timer</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #TIMER_LITERAL
     * @model name="Timer"
     * @generated
     * @ordered
     */
    public static final int TIMER = 2;

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
    public static final int ERROR = 3;

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
    public static final int CANCEL = 4;

    /**
     * The '<em><b>Conditional</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Conditional</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #CONDITIONAL_LITERAL
     * @model name="Conditional"
     * @generated
     * @ordered
     */
    public static final int CONDITIONAL = 5;

    /**
     * The '<em><b>Link</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Link</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #LINK_LITERAL
     * @model name="Link"
     * @generated
     * @ordered
     */
    public static final int LINK = 6;

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
    public static final int COMPENSATION = 7;

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
    public static final int MULTIPLE = 8;

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
    public static final int SIGNAL = 9;

    /**
     * The '<em><b>Deprecated Rule</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Deprecated Rule</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DEPRECATED_RULE_LITERAL
     * @model name="DeprecatedRule"
     * @generated
     * @ordered
     */
    public static final int DEPRECATED_RULE = 10;

    /**
     * An array of all the '<em><b>Trigger Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final TriggerType[] VALUES_ARRAY = new TriggerType[] {
            NONE_LITERAL, MESSAGE_LITERAL, TIMER_LITERAL, ERROR_LITERAL,
            CANCEL_LITERAL, CONDITIONAL_LITERAL, LINK_LITERAL,
            COMPENSATION_LITERAL, MULTIPLE_LITERAL, SIGNAL_LITERAL,
            DEPRECATED_RULE_LITERAL, };

    /**
     * A public read-only list of all the '<em><b>Trigger Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<TriggerType> VALUES = Collections
            .unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Trigger Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static TriggerType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            TriggerType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Trigger Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static TriggerType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            TriggerType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Trigger Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static TriggerType get(int value) {
        switch (value) {
        case NONE:
            return NONE_LITERAL;
        case MESSAGE:
            return MESSAGE_LITERAL;
        case TIMER:
            return TIMER_LITERAL;
        case ERROR:
            return ERROR_LITERAL;
        case CANCEL:
            return CANCEL_LITERAL;
        case CONDITIONAL:
            return CONDITIONAL_LITERAL;
        case LINK:
            return LINK_LITERAL;
        case COMPENSATION:
            return COMPENSATION_LITERAL;
        case MULTIPLE:
            return MULTIPLE_LITERAL;
        case SIGNAL:
            return SIGNAL_LITERAL;
        case DEPRECATED_RULE:
            return DEPRECATED_RULE_LITERAL;
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
    private TriggerType(int value, String name, String literal) {
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
