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
 * A representation of the literals of the enumeration '<em><b>Shared Resource Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getSharedResourceType()
 * @model extendedMetaData="name='SharedResourceType_._type'"
 * @generated
 */
public enum SharedResourceType implements Enumerator {
    /**
     * The '<em><b>Database</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #DATABASE_VALUE
     * @generated
     * @ordered
     */
    DATABASE(1, "Database", "Database"), /**
                                          * The '<em><b>Web Service</b></em>' literal object.
                                          * <!-- begin-user-doc -->
                                          * <!-- end-user-doc -->
                                          * @see #WEB_SERVICE_VALUE
                                          * @generated
                                          * @ordered
                                          */
    WEB_SERVICE(2, "WebService", "WebService"), /**
                                                 * The '<em><b>Email</b></em>' literal object.
                                                 * <!-- begin-user-doc -->
                                                 * <!-- end-user-doc -->
                                                 * @see #EMAIL_VALUE
                                                 * @generated
                                                 * @ordered
                                                 */
    EMAIL(3, "Email", "Email"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>Database</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Database</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DATABASE
     * @model name="Database"
     * @generated
     * @ordered
     */
    public static final int DATABASE_VALUE = 1;

    /**
     * The '<em><b>Web Service</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Web Service</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #WEB_SERVICE
     * @model name="WebService"
     * @generated
     * @ordered
     */
    public static final int WEB_SERVICE_VALUE = 2;

    /**
     * The '<em><b>Email</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Email</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #EMAIL
     * @model name="Email"
     * @generated
     * @ordered
     */
    public static final int EMAIL_VALUE = 3;

    /**
     * An array of all the '<em><b>Shared Resource Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final SharedResourceType[] VALUES_ARRAY =
            new SharedResourceType[] { DATABASE, WEB_SERVICE, EMAIL, };

    /**
     * A public read-only list of all the '<em><b>Shared Resource Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<SharedResourceType> VALUES =
            Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Shared Resource Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SharedResourceType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            SharedResourceType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Shared Resource Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SharedResourceType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            SharedResourceType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Shared Resource Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SharedResourceType get(int value) {
        switch (value) {
        case DATABASE_VALUE:
            return DATABASE;
        case WEB_SERVICE_VALUE:
            return WEB_SERVICE;
        case EMAIL_VALUE:
            return EMAIL;
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
    private SharedResourceType(int value, String name, String literal) {
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

} //SharedResourceType
