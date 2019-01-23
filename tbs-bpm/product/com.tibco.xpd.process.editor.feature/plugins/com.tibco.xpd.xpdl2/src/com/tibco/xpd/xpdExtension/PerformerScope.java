/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
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
 * A representation of the literals of the enumeration '<em><b>Performer Scope</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getPerformerScope()
 * @model extendedMetaData="name='PerformerScope_._type'"
 * @generated
 */
public enum PerformerScope implements Enumerator {
    /**
     * The '<em><b>Set In Library</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #SET_IN_LIBRARY_VALUE
     * @generated
     * @ordered
     */
    SET_IN_LIBRARY(0, "SetInLibrary", "SetInLibrary"), /**
     * The '<em><b>Set In Process</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #SET_IN_PROCESS_VALUE
     * @generated
     * @ordered
     */
    SET_IN_PROCESS(1, "SetInProcess", "SetInProcess"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>Set In Library</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Set In Library</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #SET_IN_LIBRARY
     * @model name="SetInLibrary"
     * @generated
     * @ordered
     */
    public static final int SET_IN_LIBRARY_VALUE = 0;

    /**
     * The '<em><b>Set In Process</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Set In Process</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #SET_IN_PROCESS
     * @model name="SetInProcess"
     * @generated
     * @ordered
     */
    public static final int SET_IN_PROCESS_VALUE = 1;

    /**
     * An array of all the '<em><b>Performer Scope</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final PerformerScope[] VALUES_ARRAY =
            new PerformerScope[] { SET_IN_LIBRARY, SET_IN_PROCESS, };

    /**
     * A public read-only list of all the '<em><b>Performer Scope</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<PerformerScope> VALUES =
            Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Performer Scope</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static PerformerScope get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            PerformerScope result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Performer Scope</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static PerformerScope getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            PerformerScope result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Performer Scope</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static PerformerScope get(int value) {
        switch (value) {
        case SET_IN_LIBRARY_VALUE:
            return SET_IN_LIBRARY;
        case SET_IN_PROCESS_VALUE:
            return SET_IN_PROCESS;
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
    private PerformerScope(int value, String name, String literal) {
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

} //PerformerScope
