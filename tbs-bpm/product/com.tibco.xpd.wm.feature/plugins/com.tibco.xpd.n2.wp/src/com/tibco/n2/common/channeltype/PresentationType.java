/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.channeltype;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Presentation Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.n2.common.channeltype.ChanneltypePackage#getPresentationType()
 * @model extendedMetaData="name='PresentationType'"
 * @generated
 */
public enum PresentationType implements Enumerator {
    /**
     * The '<em><b>JSP</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #JSP_VALUE
     * @generated
     * @ordered
     */
    JSP(0, "JSP", "JSP"),

    /**
     * The '<em><b>GI</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #GI_VALUE
     * @generated
     * @ordered
     */
    GI(1, "GI", "GI"),

    /**
     * The '<em><b>Pageflow</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #PAGEFLOW_VALUE
     * @generated
     * @ordered
     */
    PAGEFLOW(2, "Pageflow", "Pageflow"),

    /**
     * The '<em><b>Email</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #EMAIL_VALUE
     * @generated
     * @ordered
     */
    EMAIL(3, "Email", "Email"),

    /**
     * The '<em><b>Rss</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #RSS_VALUE
     * @generated
     * @ordered
     */
    RSS(4, "Rss", "Rss"),

    /**
     * The '<em><b>GWT</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #GWT_VALUE
     * @generated
     * @ordered
     */
    GWT(5, "GWT", "GWT"), /**
     * The '<em><b>IPhone</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #IPHONE_VALUE
     * @generated
     * @ordered
     */
    IPHONE(6, "iPhone", "iPhone");

    /**
     * The '<em><b>JSP</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>JSP</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #JSP
     * @model
     * @generated
     * @ordered
     */
    public static final int JSP_VALUE = 0;

    /**
     * The '<em><b>GI</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>GI</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #GI
     * @model
     * @generated
     * @ordered
     */
    public static final int GI_VALUE = 1;

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
    public static final int PAGEFLOW_VALUE = 2;

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
     * The '<em><b>Rss</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Rss</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #RSS
     * @model name="Rss"
     * @generated
     * @ordered
     */
    public static final int RSS_VALUE = 4;

    /**
     * The '<em><b>GWT</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>GWT</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #GWT
     * @model
     * @generated
     * @ordered
     */
    public static final int GWT_VALUE = 5;

    /**
     * The '<em><b>IPhone</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>IPhone</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #IPHONE
     * @model name="iPhone"
     * @generated
     * @ordered
     */
    public static final int IPHONE_VALUE = 6;

    /**
     * An array of all the '<em><b>Presentation Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final PresentationType[] VALUES_ARRAY =
        new PresentationType[] {
            JSP,
            GI,
            PAGEFLOW,
            EMAIL,
            RSS,
            GWT,
            IPHONE,
        };

    /**
     * A public read-only list of all the '<em><b>Presentation Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<PresentationType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Presentation Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static PresentationType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            PresentationType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Presentation Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static PresentationType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            PresentationType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Presentation Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static PresentationType get(int value) {
        switch (value) {
            case JSP_VALUE: return JSP;
            case GI_VALUE: return GI;
            case PAGEFLOW_VALUE: return PAGEFLOW;
            case EMAIL_VALUE: return EMAIL;
            case RSS_VALUE: return RSS;
            case GWT_VALUE: return GWT;
            case IPHONE_VALUE: return IPHONE;
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
    private PresentationType(int value, String name, String literal) {
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
    
} //PresentationType
