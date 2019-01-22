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
 * A representation of the literals of the enumeration '<em><b>Data Mapper Array Inflation Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDataMapperArrayInflationType()
 * @model extendedMetaData="name='DataMapperArrayInflationType_._type'"
 * @generated
 */
public enum DataMapperArrayInflationType implements Enumerator {
    //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>Append List</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #APPEND_LIST_VALUE
     * @generated
     * @ordered
     */
    APPEND_LIST(1, "AppendList", "AppendList"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>Merge List</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #MERGE_LIST_VALUE
     * @generated
     * @ordered
     */
    MERGE_LIST(2, "MergeList", "MergeList"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>Append List</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Append List</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #APPEND_LIST
     * @model name="AppendList"
     * @generated
     * @ordered
     */
    public static final int APPEND_LIST_VALUE = 1;

    /**
     * The '<em><b>Merge List</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Merge List</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #MERGE_LIST
     * @model name="MergeList"
     * @generated
     * @ordered
     */
    public static final int MERGE_LIST_VALUE = 2;

    /**
     * An array of all the '<em><b>Data Mapper Array Inflation Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final DataMapperArrayInflationType[] VALUES_ARRAY =
            new DataMapperArrayInflationType[] { APPEND_LIST, MERGE_LIST, };

    /**
     * A public read-only list of all the '<em><b>Data Mapper Array Inflation Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<DataMapperArrayInflationType> VALUES =
            Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Data Mapper Array Inflation Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param literal the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static DataMapperArrayInflationType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            DataMapperArrayInflationType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Data Mapper Array Inflation Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param name the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static DataMapperArrayInflationType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            DataMapperArrayInflationType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Data Mapper Array Inflation Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static DataMapperArrayInflationType get(int value) {
        switch (value) {
        case APPEND_LIST_VALUE:
            return APPEND_LIST;
        case MERGE_LIST_VALUE:
            return MERGE_LIST;
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
    private DataMapperArrayInflationType(int value, String name,
            String literal) {
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

} //DataMapperArrayInflationType
