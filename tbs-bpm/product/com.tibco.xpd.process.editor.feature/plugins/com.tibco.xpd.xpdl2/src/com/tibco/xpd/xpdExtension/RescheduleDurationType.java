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
 * A representation of the literals of the enumeration '<em><b>Reschedule Duration Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRescheduleDurationType()
 * @model extendedMetaData="name='DurationRelativeToType_._type'"
 * @generated
 */
public enum RescheduleDurationType implements Enumerator {
    /**
     * The '<em><b>Reschedule Time</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #RESCHEDULE_TIME_VALUE
     * @generated
     * @ordered
     */
    RESCHEDULE_TIME(0, "RescheduleTime", "RescheduleTime"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>Existing Timeout</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #EXISTING_TIMEOUT_VALUE
     * @generated
     * @ordered
     */
    EXISTING_TIMEOUT(1, "ExistingTimeout", "ExistingTimeout"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>Reschedule Time</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Reschedule Time</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #RESCHEDULE_TIME
     * @model name="RescheduleTime"
     * @generated
     * @ordered
     */
    public static final int RESCHEDULE_TIME_VALUE = 0;

    /**
     * The '<em><b>Existing Timeout</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Existing Timeout</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #EXISTING_TIMEOUT
     * @model name="ExistingTimeout"
     * @generated
     * @ordered
     */
    public static final int EXISTING_TIMEOUT_VALUE = 1;

    /**
     * An array of all the '<em><b>Reschedule Duration Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final RescheduleDurationType[] VALUES_ARRAY =
            new RescheduleDurationType[] { RESCHEDULE_TIME, EXISTING_TIMEOUT, };

    /**
     * A public read-only list of all the '<em><b>Reschedule Duration Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<RescheduleDurationType> VALUES =
            Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Reschedule Duration Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param literal the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static RescheduleDurationType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            RescheduleDurationType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Reschedule Duration Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param name the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static RescheduleDurationType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            RescheduleDurationType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Reschedule Duration Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static RescheduleDurationType get(int value) {
        switch (value) {
        case RESCHEDULE_TIME_VALUE:
            return RESCHEDULE_TIME;
        case EXISTING_TIMEOUT_VALUE:
            return EXISTING_TIMEOUT;
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
    private RescheduleDurationType(int value, String name, String literal) {
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

} //RescheduleDurationType
