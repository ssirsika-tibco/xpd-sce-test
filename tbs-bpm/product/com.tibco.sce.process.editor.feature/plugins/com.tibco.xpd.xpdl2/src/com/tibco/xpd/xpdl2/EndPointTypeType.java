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
 * A representation of the literals of the enumeration '<em><b>End Point Type Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getEndPointTypeType()
 * @model extendedMetaData="name='EndPointType_._type'"
 * @generated
 */
public enum EndPointTypeType implements Enumerator {
    /**
     * The '<em><b>WSDL</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #WSDL
     * @generated
     * @ordered
     */
    WSDL_LITERAL(0, "WSDL", "WSDL"),
    /**
     * The '<em><b>Service</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #SERVICE
     * @generated
     * @ordered
     */
    SERVICE_LITERAL(1, "Service", "Service");
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>WSDL</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>WSDL</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #WSDL_LITERAL
     * @model
     * @generated
     * @ordered
     */
    public static final int WSDL = 0;

    /**
     * The '<em><b>Service</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Service</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #SERVICE_LITERAL
     * @model name="Service"
     * @generated
     * @ordered
     */
    public static final int SERVICE = 1;

    /**
     * An array of all the '<em><b>End Point Type Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final EndPointTypeType[] VALUES_ARRAY =
            new EndPointTypeType[] { WSDL_LITERAL, SERVICE_LITERAL, };

    /**
     * A public read-only list of all the '<em><b>End Point Type Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<EndPointTypeType> VALUES = Collections
            .unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>End Point Type Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static EndPointTypeType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            EndPointTypeType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>End Point Type Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static EndPointTypeType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            EndPointTypeType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>End Point Type Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static EndPointTypeType get(int value) {
        switch (value) {
        case WSDL:
            return WSDL_LITERAL;
        case SERVICE:
            return SERVICE_LITERAL;
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
    private EndPointTypeType(int value, String name, String literal) {
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
