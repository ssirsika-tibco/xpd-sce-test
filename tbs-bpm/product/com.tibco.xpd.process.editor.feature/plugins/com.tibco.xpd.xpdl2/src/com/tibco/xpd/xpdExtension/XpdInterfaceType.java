/**
 * Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Xpd Interface Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * XpdInterfaceType attribute is added to xpdl element 'ProcessInterface' and specifies the Interface type(ProcessInterface , ServiceProcessInterface)
 * <!-- end-model-doc -->
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getXpdInterfaceType()
 * @model extendedMetaData="name='XpdInterfaceType_._type'"
 * @generated
 */
public enum XpdInterfaceType implements Enumerator {
    /**
     * The '<em><b>Process Interface</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #PROCESS_INTERFACE_VALUE
     * @generated
     * @ordered
     */
    PROCESS_INTERFACE(0, "ProcessInterface", "ProcessInterface"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>Service Process Interface</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #SERVICE_PROCESS_INTERFACE_VALUE
     * @generated
     * @ordered
     */
    SERVICE_PROCESS_INTERFACE(1, "ServiceProcessInterface", //$NON-NLS-1$
            "ServiceProcessInterface"); //$NON-NLS-1$

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>Process Interface</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Process Interface</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #PROCESS_INTERFACE
     * @model name="ProcessInterface"
     * @generated
     * @ordered
     */
    public static final int PROCESS_INTERFACE_VALUE = 0;

    /**
     * The '<em><b>Service Process Interface</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Service Process Interface</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #SERVICE_PROCESS_INTERFACE
     * @model name="ServiceProcessInterface"
     * @generated
     * @ordered
     */
    public static final int SERVICE_PROCESS_INTERFACE_VALUE = 1;

    /**
     * An array of all the '<em><b>Xpd Interface Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final XpdInterfaceType[] VALUES_ARRAY =
            new XpdInterfaceType[] { PROCESS_INTERFACE, SERVICE_PROCESS_INTERFACE, };

    /**
     * A public read-only list of all the '<em><b>Xpd Interface Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<XpdInterfaceType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Xpd Interface Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param literal the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static XpdInterfaceType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            XpdInterfaceType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Xpd Interface Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param name the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static XpdInterfaceType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            XpdInterfaceType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Xpd Interface Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static XpdInterfaceType get(int value) {
        switch (value) {
        case PROCESS_INTERFACE_VALUE:
            return PROCESS_INTERFACE;
        case SERVICE_PROCESS_INTERFACE_VALUE:
            return SERVICE_PROCESS_INTERFACE;
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
    private XpdInterfaceType(int value, String name, String literal) {
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

} //XpdInterfaceType
