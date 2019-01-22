/**
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
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
 * A representation of the literals of the enumeration '<em><b>Security Policy</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getSecurityPolicy()
 * @model extendedMetaData="name='SecurityPolicy'"
 * @generated
 */
public enum SecurityPolicy implements Enumerator {
    /**
     * The '<em><b>Username Token</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #USERNAME_TOKEN_VALUE
     * @generated
     * @ordered
     */
    USERNAME_TOKEN(0, "UsernameToken", "UsernameToken"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>X509 Token</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #X509_TOKEN_VALUE
     * @generated
     * @ordered
     */
    X509_TOKEN(1, "X509Token", "X509Token"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>Saml Token</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #SAML_TOKEN_VALUE
     * @generated
     * @ordered
     */
    SAML_TOKEN(2, "SamlToken", "SamlToken"),
    /**
     * The '<em><b>Custom</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #CUSTOM_VALUE
     * @generated
     * @ordered
     */
    CUSTOM(3, "Custom", "Custom"),
    /**
     * The '<em><b>Basic</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #BASIC_VALUE
     * @generated
     * @ordered
     */
    BASIC(4, "Basic", "Basic"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>Username Token</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Username Token</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #USERNAME_TOKEN
     * @model name="UsernameToken"
     * @generated
     * @ordered
     */
    public static final int USERNAME_TOKEN_VALUE = 0;

    /**
     * The '<em><b>X509 Token</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>X509 Token</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #X509_TOKEN
     * @model name="X509Token"
     * @generated
     * @ordered
     */
    public static final int X509_TOKEN_VALUE = 1;

    /**
     * The '<em><b>Saml Token</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Saml Token</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #SAML_TOKEN
     * @model name="SamlToken"
     * @generated
     * @ordered
     */
    public static final int SAML_TOKEN_VALUE = 2;

    /**
     * The '<em><b>Custom</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Custom</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #CUSTOM
     * @model name="Custom"
     * @generated
     * @ordered
     */
    public static final int CUSTOM_VALUE = 3;

    /**
     * The '<em><b>Basic</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Basic</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #BASIC
     * @model name="Basic"
     * @generated
     * @ordered
     */
    public static final int BASIC_VALUE = 4;

    /**
     * An array of all the '<em><b>Security Policy</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final SecurityPolicy[] VALUES_ARRAY = new SecurityPolicy[] {
            USERNAME_TOKEN, X509_TOKEN, SAML_TOKEN, CUSTOM, BASIC, };

    /**
     * A public read-only list of all the '<em><b>Security Policy</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<SecurityPolicy> VALUES =
            Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Security Policy</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param literal the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static SecurityPolicy get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            SecurityPolicy result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Security Policy</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param name the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static SecurityPolicy getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            SecurityPolicy result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Security Policy</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static SecurityPolicy get(int value) {
        switch (value) {
        case USERNAME_TOKEN_VALUE:
            return USERNAME_TOKEN;
        case X509_TOKEN_VALUE:
            return X509_TOKEN;
        case SAML_TOKEN_VALUE:
            return SAML_TOKEN;
        case CUSTOM_VALUE:
            return CUSTOM;
        case BASIC_VALUE:
            return BASIC;
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
    private SecurityPolicy(int value, String name, String literal) {
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

} //SecurityPolicy
