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
 * A representation of the literals of the enumeration '<em><b>Xpd Model Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * XpdModelType attribute is added to xpdl element 'WorkflowProcess' and specifies the Model type(pageflow , decision flow, task library..)
 * <!-- end-model-doc -->
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getXpdModelType()
 * @model extendedMetaData="name='XpdModelType_._type'"
 * @generated
 */
public enum XpdModelType implements Enumerator {
    //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>Page Flow</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #PAGE_FLOW_VALUE
     * @generated
     * @ordered
     */
    PAGE_FLOW(0, "PageFlow", "PageFlow"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>Task Library</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #TASK_LIBRARY_VALUE
     * @generated
     * @ordered
     */
    TASK_LIBRARY(1, "TaskLibrary", "TaskLibrary"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>Decision Flow</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #DECISION_FLOW_VALUE
     * @generated
     * @ordered
     */
    DECISION_FLOW(2, "DecisionFlow", "DecisionFlow"),
    /**
     * The '<em><b>Service Process</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #SERVICE_PROCESS_VALUE
     * @generated
     * @ordered
     */
    SERVICE_PROCESS(3, "ServiceProcess", "ServiceProcess"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>Page Flow</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Page Flow</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #PAGE_FLOW
     * @model name="PageFlow"
     * @generated
     * @ordered
     */
    public static final int PAGE_FLOW_VALUE = 0;

    /**
     * The '<em><b>Task Library</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Task Library</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #TASK_LIBRARY
     * @model name="TaskLibrary"
     * @generated
     * @ordered
     */
    public static final int TASK_LIBRARY_VALUE = 1;

    /**
     * The '<em><b>Decision Flow</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Decision Flow</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DECISION_FLOW
     * @model name="DecisionFlow"
     * @generated
     * @ordered
     */
    public static final int DECISION_FLOW_VALUE = 2;

    /**
     * The '<em><b>Service Process</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Service Process</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #SERVICE_PROCESS
     * @model name="ServiceProcess"
     * @generated
     * @ordered
     */
    public static final int SERVICE_PROCESS_VALUE = 3;

    /**
     * An array of all the '<em><b>Xpd Model Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final XpdModelType[] VALUES_ARRAY =
            new XpdModelType[] { PAGE_FLOW, TASK_LIBRARY, DECISION_FLOW, SERVICE_PROCESS, };

    /**
     * A public read-only list of all the '<em><b>Xpd Model Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<XpdModelType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Xpd Model Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param literal the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static XpdModelType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            XpdModelType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Xpd Model Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param name the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static XpdModelType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            XpdModelType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Xpd Model Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static XpdModelType get(int value) {
        switch (value) {
        case PAGE_FLOW_VALUE:
            return PAGE_FLOW;
        case TASK_LIBRARY_VALUE:
            return TASK_LIBRARY;
        case DECISION_FLOW_VALUE:
            return DECISION_FLOW;
        case SERVICE_PROCESS_VALUE:
            return SERVICE_PROCESS;
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
    private XpdModelType(int value, String name, String literal) {
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

} //XpdModelType
