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
 * A representation of the literals of the enumeration '<em><b>Channel Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.n2.common.channeltype.ChanneltypePackage#getChannelType()
 * @model extendedMetaData="name='ChannelType'"
 * @generated
 */
public enum ChannelType implements Enumerator {
    /**
     * The '<em><b>JSP Channel</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #JSP_CHANNEL_VALUE
     * @generated
     * @ordered
     */
    JSP_CHANNEL(0, "JSPChannel", "JSPChannel"),

    /**
     * The '<em><b>GI Channel</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #GI_CHANNEL_VALUE
     * @generated
     * @ordered
     */
    GI_CHANNEL(1, "GIChannel", "GIChannel"),

    /**
     * The '<em><b>Pageflow Channel</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #PAGEFLOW_CHANNEL_VALUE
     * @generated
     * @ordered
     */
    PAGEFLOW_CHANNEL(2, "PageflowChannel", "PageflowChannel"),

    /**
     * The '<em><b>Email Channel</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #EMAIL_CHANNEL_VALUE
     * @generated
     * @ordered
     */
    EMAIL_CHANNEL(3, "EmailChannel", "EmailChannel"),

    /**
     * The '<em><b>Rss Channel</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #RSS_CHANNEL_VALUE
     * @generated
     * @ordered
     */
    RSS_CHANNEL(4, "RssChannel", "RssChannel"), /**
     * The '<em><b>Openspace Channel</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #OPENSPACE_CHANNEL_VALUE
     * @generated
     * @ordered
     */
    OPENSPACE_CHANNEL(5, "openspaceChannel", "openspaceChannel"), /**
     * The '<em><b>Mobile Channel</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #MOBILE_CHANNEL_VALUE
     * @generated
     * @ordered
     */
    MOBILE_CHANNEL(6, "MobileChannel", "MobileChannel");

    /**
     * The '<em><b>JSP Channel</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>JSP Channel</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #JSP_CHANNEL
     * @model name="JSPChannel"
     * @generated
     * @ordered
     */
    public static final int JSP_CHANNEL_VALUE = 0;

    /**
     * The '<em><b>GI Channel</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>GI Channel</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #GI_CHANNEL
     * @model name="GIChannel"
     * @generated
     * @ordered
     */
    public static final int GI_CHANNEL_VALUE = 1;

    /**
     * The '<em><b>Pageflow Channel</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Pageflow Channel</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #PAGEFLOW_CHANNEL
     * @model name="PageflowChannel"
     * @generated
     * @ordered
     */
    public static final int PAGEFLOW_CHANNEL_VALUE = 2;

    /**
     * The '<em><b>Email Channel</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Email Channel</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #EMAIL_CHANNEL
     * @model name="EmailChannel"
     * @generated
     * @ordered
     */
    public static final int EMAIL_CHANNEL_VALUE = 3;

    /**
     * The '<em><b>Rss Channel</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Rss Channel</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #RSS_CHANNEL
     * @model name="RssChannel"
     * @generated
     * @ordered
     */
    public static final int RSS_CHANNEL_VALUE = 4;

    /**
     * The '<em><b>Openspace Channel</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Openspace Channel</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #OPENSPACE_CHANNEL
     * @model name="openspaceChannel"
     * @generated
     * @ordered
     */
    public static final int OPENSPACE_CHANNEL_VALUE = 5;

    /**
     * The '<em><b>Mobile Channel</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Mobile Channel</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #MOBILE_CHANNEL
     * @model name="MobileChannel"
     * @generated
     * @ordered
     */
    public static final int MOBILE_CHANNEL_VALUE = 6;

    /**
     * An array of all the '<em><b>Channel Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final ChannelType[] VALUES_ARRAY =
        new ChannelType[] {
            JSP_CHANNEL,
            GI_CHANNEL,
            PAGEFLOW_CHANNEL,
            EMAIL_CHANNEL,
            RSS_CHANNEL,
            OPENSPACE_CHANNEL,
            MOBILE_CHANNEL,
        };

    /**
     * A public read-only list of all the '<em><b>Channel Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<ChannelType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Channel Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ChannelType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ChannelType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Channel Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ChannelType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ChannelType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Channel Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ChannelType get(int value) {
        switch (value) {
            case JSP_CHANNEL_VALUE: return JSP_CHANNEL;
            case GI_CHANNEL_VALUE: return GI_CHANNEL;
            case PAGEFLOW_CHANNEL_VALUE: return PAGEFLOW_CHANNEL;
            case EMAIL_CHANNEL_VALUE: return EMAIL_CHANNEL;
            case RSS_CHANNEL_VALUE: return RSS_CHANNEL;
            case OPENSPACE_CHANNEL_VALUE: return OPENSPACE_CHANNEL;
            case MOBILE_CHANNEL_VALUE: return MOBILE_CHANNEL;
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
    private ChannelType(int value, String name, String literal) {
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
    
} //ChannelType
