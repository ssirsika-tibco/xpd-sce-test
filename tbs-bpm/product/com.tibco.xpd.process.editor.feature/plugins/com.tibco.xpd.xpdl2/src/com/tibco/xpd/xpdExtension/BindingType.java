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
 * A representation of the literals of the enumeration '<em><b>Binding Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getBindingType()
 * @model extendedMetaData="name='BindingType_._type'"
 * @generated
 */
public enum BindingType implements Enumerator {
    //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>Document Literal</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #DOCUMENT_LITERAL_VALUE
     * @generated
     * @ordered
     */
    DOCUMENT_LITERAL(0, "DocumentLiteral", "DocumentLiteral"), /**
     * The '<em><b>RPC Literal</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #RPC_LITERAL_VALUE
     * @generated
     * @ordered
     */
    RPC_LITERAL(1, "RPCLiteral", "RPCLiteral"), /**
     * The '<em><b>RPC Encoded</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #RPC_ENCODED_VALUE
     * @generated
     * @ordered
     */
    RPC_ENCODED(2, "RPCEncoded", "RPCEncoded"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>Document Literal</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Document Literal</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DOCUMENT_LITERAL
     * @model name="DocumentLiteral"
     * @generated
     * @ordered
     */
    public static final int DOCUMENT_LITERAL_VALUE = 0;

    /**
     * The '<em><b>RPC Literal</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>RPC Literal</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #RPC_LITERAL
     * @model name="RPCLiteral"
     * @generated
     * @ordered
     */
    public static final int RPC_LITERAL_VALUE = 1;

    /**
     * The '<em><b>RPC Encoded</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>RPC Encoded</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #RPC_ENCODED
     * @model name="RPCEncoded"
     * @generated
     * @ordered
     */
    public static final int RPC_ENCODED_VALUE = 2;

    /**
     * An array of all the '<em><b>Binding Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final BindingType[] VALUES_ARRAY =
            new BindingType[] { DOCUMENT_LITERAL, RPC_LITERAL, RPC_ENCODED, };

    /**
     * A public read-only list of all the '<em><b>Binding Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<BindingType> VALUES =
            Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Binding Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static BindingType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            BindingType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Binding Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static BindingType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            BindingType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Binding Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static BindingType get(int value) {
        switch (value) {
        case DOCUMENT_LITERAL_VALUE:
            return DOCUMENT_LITERAL;
        case RPC_LITERAL_VALUE:
            return RPC_LITERAL;
        case RPC_ENCODED_VALUE:
            return RPC_ENCODED;
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
    private BindingType(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int getValue() {
        return value;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
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

} //BindingType
