/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Audit Event Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAuditEventType()
 * @model extendedMetaData="name='Type_._type'"
 * @generated
 */
public enum AuditEventType implements Enumerator {
    /**
     * The '<em><b>Initiated</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #INITIATED
     * @generated
     * @ordered
     */
    INITIATED_LITERAL(0, "Initiated", "Initiated"),
    /**
     * The '<em><b>Completed</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #COMPLETED
     * @generated
     * @ordered
     */
    COMPLETED_LITERAL(1, "Completed", "Completed"),
    /**
     * The '<em><b>Deadline Expired</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #DEADLINE_EXPIRED
     * @generated
     * @ordered
     */
    DEADLINE_EXPIRED_LITERAL(2, "DeadlineExpired", "DeadlineExpired"),
    /**
     * The '<em><b>Cancelled</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #CANCELLED
     * @generated
     * @ordered
     */
    CANCELLED_LITERAL(3, "Cancelled", "Cancelled");
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>Initiated</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Initiated</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #INITIATED_LITERAL
     * @model name="Initiated"
     * @generated
     * @ordered
     */
    public static final int INITIATED = 0;

    /**
     * The '<em><b>Completed</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Completed</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #COMPLETED_LITERAL
     * @model name="Completed"
     * @generated
     * @ordered
     */
    public static final int COMPLETED = 1;

    /**
     * The '<em><b>Deadline Expired</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Deadline Expired</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DEADLINE_EXPIRED_LITERAL
     * @model name="DeadlineExpired"
     * @generated
     * @ordered
     */
    public static final int DEADLINE_EXPIRED = 2;

    /**
     * The '<em><b>Cancelled</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Cancelled</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #CANCELLED_LITERAL
     * @model name="Cancelled"
     * @generated
     * @ordered
     */
    public static final int CANCELLED = 3;

    /**
     * An array of all the '<em><b>Audit Event Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final AuditEventType[] VALUES_ARRAY =
            new AuditEventType[] { INITIATED_LITERAL, COMPLETED_LITERAL, DEADLINE_EXPIRED_LITERAL, CANCELLED_LITERAL, };

    /**
     * A public read-only list of all the '<em><b>Audit Event Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<AuditEventType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Audit Event Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param literal the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static AuditEventType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            AuditEventType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Audit Event Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param name the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static AuditEventType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            AuditEventType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Audit Event Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static AuditEventType get(int value) {
        switch (value) {
        case INITIATED:
            return INITIATED_LITERAL;
        case COMPLETED:
            return COMPLETED_LITERAL;
        case DEADLINE_EXPIRED:
            return DEADLINE_EXPIRED_LITERAL;
        case CANCELLED:
            return CANCELLED_LITERAL;
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
    private AuditEventType(int value, String name, String literal) {
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
