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

import com.tibco.xpd.internal.Messages;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Publication Status Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPublicationStatusType()
 * @model extendedMetaData="name='PublicationStatus_._type'"
 * @generated
 */
public enum PublicationStatusType implements Enumerator {
    /**
     * The '<em><b>UNDER REVISION</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #UNDER_REVISION
     * @generated
     * @ordered
     */
    UNDER_REVISION_LITERAL(0, "UNDER_REVISION", "UNDER_REVISION"),
    /**
     * The '<em><b>RELEASED</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #RELEASED
     * @generated
     * @ordered
     */
    RELEASED_LITERAL(1, "RELEASED", "RELEASED"),
    /**
     * The '<em><b>UNDER TEST</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #UNDER_TEST
     * @generated
     * @ordered
     */
    UNDER_TEST_LITERAL(2, "UNDER_TEST", "UNDER_TEST");
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>UNDER REVISION</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>UNDER REVISION</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #UNDER_REVISION_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int UNDER_REVISION = 0;

    /**
     * The '<em><b>RELEASED</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>RELEASED</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #RELEASED_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int RELEASED = 1;

    /**
     * The '<em><b>UNDER TEST</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>UNDER TEST</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #UNDER_TEST_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int UNDER_TEST = 2;

    /**
     * An array of all the '<em><b>Publication Status Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final PublicationStatusType[] VALUES_ARRAY =
            new PublicationStatusType[] { UNDER_REVISION_LITERAL,
                    RELEASED_LITERAL, UNDER_TEST_LITERAL, };

    /**
     * A public read-only list of all the '<em><b>Publication Status Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<PublicationStatusType> VALUES = Collections
            .unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Publication Status Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static PublicationStatusType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            PublicationStatusType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Publication Status Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static PublicationStatusType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            PublicationStatusType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Publication Status Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static PublicationStatusType get(int value) {
        switch (value) {
        case UNDER_REVISION:
            return UNDER_REVISION_LITERAL;
        case RELEASED:
            return RELEASED_LITERAL;
        case UNDER_TEST:
            return UNDER_TEST_LITERAL;
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
    private PublicationStatusType(int value, String name, String literal) {
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

    /**
     * @param statusType
     * @return
     */
    public static String getUIText(int statusType) {
        switch (statusType) {
        case UNDER_REVISION:
            return Messages.PublicationStatusType_Under_Revision_UI_Text;
        case RELEASED:
            return Messages.PublicationStatusType_Released_UI_Text;
        case UNDER_TEST:
            return Messages.PublicationStatusType_Under_Test_UI_Text;
        default:
            return ""; //$NON-NLS-1$
        }
    }
}
