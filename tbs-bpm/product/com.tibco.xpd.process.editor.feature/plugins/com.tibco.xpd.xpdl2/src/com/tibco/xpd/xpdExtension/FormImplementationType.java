/**
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
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
 * A representation of the literals of the enumeration '<em><b>Form Implementation Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * Form Implementation Type
 * <!-- end-model-doc -->
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getFormImplementationType()
 * @model
 * @generated
 */
public enum FormImplementationType implements Enumerator {
    /**
     * The '<em><b>User Defined</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #USER_DEFINED_VALUE
     * @generated
     * @ordered
     */
    USER_DEFINED(1, "UserDefined", "UserDefined"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>Form</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #FORM_VALUE
     * @generated
     * @ordered
     */
    FORM(2, "Form", "Form"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>Pageflow</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #PAGEFLOW_VALUE
     * @generated
     * @ordered
     */
    PAGEFLOW(3, "Pageflow", "Pageflow"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>User Defined</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>User Defined</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #USER_DEFINED
     * @model name="UserDefined"
     * @generated
     * @ordered
     */
    public static final int USER_DEFINED_VALUE = 1;

    /**
     * The '<em><b>Form</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Form</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #FORM
     * @model name="Form"
     * @generated
     * @ordered
     */
    public static final int FORM_VALUE = 2;

    /**
     * The '<em><b>Pageflow</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Pageflow</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #PAGEFLOW
     * @model name="Pageflow"
     * @generated
     * @ordered
     */
    public static final int PAGEFLOW_VALUE = 3;

    /**
     * An array of all the '<em><b>Form Implementation Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final FormImplementationType[] VALUES_ARRAY =
            new FormImplementationType[] { USER_DEFINED, FORM, PAGEFLOW, };

    /**
     * A public read-only list of all the '<em><b>Form Implementation Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<FormImplementationType> VALUES =
            Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Form Implementation Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param literal the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static FormImplementationType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            FormImplementationType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Form Implementation Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param name the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static FormImplementationType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            FormImplementationType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Form Implementation Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static FormImplementationType get(int value) {
        switch (value) {
        case USER_DEFINED_VALUE:
            return USER_DEFINED;
        case FORM_VALUE:
            return FORM;
        case PAGEFLOW_VALUE:
            return PAGEFLOW;
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
    private FormImplementationType(int value, String name, String literal) {
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

} //FormImplementationType
