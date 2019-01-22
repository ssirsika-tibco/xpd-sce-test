/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Attribute Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.AttributeValue#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.AttributeValue#getValue <em>Value</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.AttributeValue#getEnumSetValues <em>Enum Set Values</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.AttributeValue#getSetValues <em>Set Values</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.AttributeValue#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getAttributeValue()
 * @model
 * @generated
 */
public interface AttributeValue extends EObject {

    /**
     * to boolean values in the model file.
     */
    public static final String TRUE_LITERAL = "true"; //$NON-NLS-1$

    /**
     * to boolean values in the model file.
     */
    public static final String FALSE_LITERAL = "false"; //$NON-NLS-1$

    /**
     * 
     */
    public static final String FORMAT_DATE_ISO_8601 = "yyyy-MM-dd"; //$NON-NLS-1$

    /**
     * 
     */
    public static final String FORMAT_DATE_AND_TIME_ISO_8601 =
            "yyyy-MM-dd'T'HH:mm:ss"; //$NON-NLS-1$

    /**
     * 
     */
    public static final String FORMAT_TIME = "HH:mm:ss"; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Attribute</b></em>' reference. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Attribute</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Attribute</em>' reference.
     * @see #setAttribute(Attribute)
     * @see com.tibco.xpd.om.core.om.OMPackage#getAttributeValue_Attribute()
     * @model required="true"
     * @generated
     */
    Attribute getAttribute();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.AttributeValue#getAttribute <em>Attribute</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute</em>' reference.
     * @see #getAttribute()
     * @generated
     */
    void setAttribute(Attribute value);

    /**
     * Returns the value of the '<em><b>Value</b></em>' attribute. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Value</em>' attribute isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Value</em>' attribute.
     * @see #setValue(String)
     * @see com.tibco.xpd.om.core.om.OMPackage#getAttributeValue_Value()
     * @model
     * @generated
     */
    String getValue();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.AttributeValue#getValue <em>Value</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Value</em>' attribute.
     * @see #getValue()
     * @generated
     */
    void setValue(String value);

    /**
     * Returns the value of the '<em><b>Enum Set Values</b></em>' reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.EnumValue}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Enum Set Values</em>' reference list isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Enum Set Values</em>' reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getAttributeValue_EnumSetValues()
     * @model ordered="false"
     * @generated
     */
    EList<EnumValue> getEnumSetValues();

    /**
     * Returns the value of the '<em><b>Set Values</b></em>' attribute list.
     * The list contents are of type {@link java.lang.String}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Set Values</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Set Values</em>' attribute list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getAttributeValue_SetValues()
     * @model
     * @generated
     */
    EList<String> getSetValues();

    /**
     * Returns the value of the '<em><b>Type</b></em>' attribute. The
     * default value is <code>""</code>. The literals are from the
     * enumeration {@link com.tibco.xpd.om.core.om.AttributeType}. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Type</em>' attribute isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Type</em>' attribute.
     * @see com.tibco.xpd.om.core.om.AttributeType
     * @see com.tibco.xpd.om.core.om.OMPackage#getAttributeValue_Type()
     * @model default="" transient="true" changeable="false" volatile="true"
     * @generated
     */
    AttributeType getType();

} // AttributeValue
