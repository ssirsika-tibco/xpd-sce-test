/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
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
 * A representation of the literals of the enumeration '<em><b>Xor Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getXorType()
 * @model extendedMetaData="name='XorType_._type'"
 * @generated
 */
public enum XorType implements Enumerator {
    /**
     * The '<em><b>DATA</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #DATA_VALUE
     * @generated
     * @ordered
     */
    DATA(0, "DATA", "DATA"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>EVENT</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #EVENT_VALUE
     * @generated
     * @ordered
     */
    EVENT(1, "EVENT", "EVENT"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>DATA</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>DATA</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DATA
     * @model
     * @generated
     * @ordered
     */
    public static final int DATA_VALUE = 0;

    /**
     * The '<em><b>EVENT</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>EVENT</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #EVENT
     * @model
     * @generated
     * @ordered
     */
    public static final int EVENT_VALUE = 1;

    /**
     * An array of all the '<em><b>Xor Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final XorType[] VALUES_ARRAY =
            new XorType[] { DATA, EVENT, };

    /**
     * A public read-only list of all the '<em><b>Xor Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<XorType> VALUES =
            Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Xor Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static XorType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            XorType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Xor Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static XorType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            XorType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Xor Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static XorType get(int value) {
        switch (value) {
        case DATA_VALUE:
            return DATA;
        case EVENT_VALUE:
            return EVENT;
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
    private XorType(int value, String name, String literal) {
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

} //XorType
