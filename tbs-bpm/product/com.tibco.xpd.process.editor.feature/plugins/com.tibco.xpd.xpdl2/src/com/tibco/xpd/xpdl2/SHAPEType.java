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
 * A representation of the literals of the enumeration '<em><b>SHAPE Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getSHAPEType()
 * @model extendedMetaData="name='SHAPE_._type'"
 * @generated
 */
public enum SHAPEType implements Enumerator {
    /**
     * The '<em><b>Round Rectangle</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ROUND_RECTANGLE
     * @generated
     * @ordered
     */
    ROUND_RECTANGLE_LITERAL(0, "RoundRectangle", "RoundRectangle"),
    /**
     * The '<em><b>Rectangle</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #RECTANGLE
     * @generated
     * @ordered
     */
    RECTANGLE_LITERAL(1, "Rectangle", "Rectangle"),
    /**
     * The '<em><b>Ellipse</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ELLIPSE
     * @generated
     * @ordered
     */
    ELLIPSE_LITERAL(2, "Ellipse", "Ellipse"),
    /**
     * The '<em><b>Diamond</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #DIAMOND
     * @generated
     * @ordered
     */
    DIAMOND_LITERAL(3, "Diamond", "Diamond"),
    /**
     * The '<em><b>Up Triangle</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #UP_TRIANGLE
     * @generated
     * @ordered
     */
    UP_TRIANGLE_LITERAL(5, "UpTriangle", "UpTriangle"),
    /**
     * The '<em><b>Down Triangle</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #DOWN_TRIANGLE
     * @generated
     * @ordered
     */
    DOWN_TRIANGLE_LITERAL(6, "DownTriangle", "DownTriangle");
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The '<em><b>Round Rectangle</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Round Rectangle</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ROUND_RECTANGLE_LITERAL
     * @model name="RoundRectangle"
     * @generated
     * @ordered
     */
    public static final int ROUND_RECTANGLE = 0;

    /**
     * The '<em><b>Rectangle</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Rectangle</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #RECTANGLE_LITERAL
     * @model name="Rectangle"
     * @generated
     * @ordered
     */
    public static final int RECTANGLE = 1;

    /**
     * The '<em><b>Ellipse</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Ellipse</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ELLIPSE_LITERAL
     * @model name="Ellipse"
     * @generated
     * @ordered
     */
    public static final int ELLIPSE = 2;

    /**
     * The '<em><b>Diamond</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Diamond</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DIAMOND_LITERAL
     * @model name="Diamond"
     * @generated
     * @ordered
     */
    public static final int DIAMOND = 3;

    /**
     * The '<em><b>Up Triangle</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Up Triangle</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #UP_TRIANGLE_LITERAL
     * @model name="UpTriangle"
     * @generated
     * @ordered
     */
    public static final int UP_TRIANGLE = 5;

    /**
     * The '<em><b>Down Triangle</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Down Triangle</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #DOWN_TRIANGLE_LITERAL
     * @model name="DownTriangle"
     * @generated
     * @ordered
     */
    public static final int DOWN_TRIANGLE = 6;

    /**
     * An array of all the '<em><b>SHAPE Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final SHAPEType[] VALUES_ARRAY = new SHAPEType[] {
            ROUND_RECTANGLE_LITERAL, RECTANGLE_LITERAL, ELLIPSE_LITERAL,
            DIAMOND_LITERAL, UP_TRIANGLE_LITERAL, DOWN_TRIANGLE_LITERAL, };

    /**
     * A public read-only list of all the '<em><b>SHAPE Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<SHAPEType> VALUES = Collections
            .unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>SHAPE Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SHAPEType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            SHAPEType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>SHAPE Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SHAPEType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            SHAPEType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>SHAPE Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SHAPEType get(int value) {
        switch (value) {
        case ROUND_RECTANGLE:
            return ROUND_RECTANGLE_LITERAL;
        case RECTANGLE:
            return RECTANGLE_LITERAL;
        case ELLIPSE:
            return ELLIPSE_LITERAL;
        case DIAMOND:
            return DIAMOND_LITERAL;
        case UP_TRIANGLE:
            return UP_TRIANGLE_LITERAL;
        case DOWN_TRIANGLE:
            return DOWN_TRIANGLE_LITERAL;
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
    private SHAPEType(int value, String name, String literal) {
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
