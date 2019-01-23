/**
 */
package com.tibco.xpd.rsd;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Parameter Style</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.rsd.RsdPackage#getParameterStyle()
 * @model
 * @generated
 */
public enum ParameterStyle implements Enumerator {
    /**
     * The '<em><b>PATH</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #PATH_VALUE
     * @generated
     * @ordered
     */
    PATH(0, "PATH", "PATH"),

    /**
     * The '<em><b>QUERY</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #QUERY_VALUE
     * @generated
     * @ordered
     */
    QUERY(1, "QUERY", "QUERY"),

    /**
     * The '<em><b>HEADER</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #HEADER_VALUE
     * @generated
     * @ordered
     */
    HEADER(2, "HEADER", "HEADER");

    /**
     * The '<em><b>PATH</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>PATH</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #PATH
     * @model
     * @generated
     * @ordered
     */
    public static final int PATH_VALUE = 0;

    /**
     * The '<em><b>QUERY</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>QUERY</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #QUERY
     * @model
     * @generated
     * @ordered
     */
    public static final int QUERY_VALUE = 1;

    /**
     * The '<em><b>HEADER</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>HEADER</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #HEADER
     * @model
     * @generated
     * @ordered
     */
    public static final int HEADER_VALUE = 2;

    /**
     * An array of all the '<em><b>Parameter Style</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final ParameterStyle[] VALUES_ARRAY =
        new ParameterStyle[] {
            PATH,
            QUERY,
            HEADER,
        };

    /**
     * A public read-only list of all the '<em><b>Parameter Style</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<ParameterStyle> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Parameter Style</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ParameterStyle get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ParameterStyle result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Parameter Style</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ParameterStyle getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ParameterStyle result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Parameter Style</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ParameterStyle get(int value) {
        switch (value) {
            case PATH_VALUE: return PATH;
            case QUERY_VALUE: return QUERY;
            case HEADER_VALUE: return HEADER;
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
    private ParameterStyle(int value, String name, String literal) {
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
    
} //ParameterStyle
