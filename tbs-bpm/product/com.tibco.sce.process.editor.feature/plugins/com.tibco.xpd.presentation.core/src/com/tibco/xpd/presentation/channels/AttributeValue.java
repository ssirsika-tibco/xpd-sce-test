/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channels;

import com.tibco.xpd.presentation.channeltypes.Attribute;
import com.tibco.xpd.presentation.channeltypes.AttributeType;
import com.tibco.xpd.presentation.channeltypes.EnumLiteral;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.presentation.channels.AttributeValue#getValue <em>Value</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channels.AttributeValue#getEnumValues <em>Enum Values</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channels.AttributeValue#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channels.AttributeValue#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.presentation.channels.ChannelsPackage#getAttributeValue()
 * @model
 * @generated
 */
public interface AttributeValue extends EObject {
    /**
     * Returns the value of the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Value</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Value</em>' attribute.
     * @see #setValue(String)
     * @see com.tibco.xpd.presentation.channels.ChannelsPackage#getAttributeValue_Value()
     * @model
     * @generated
     */
    String getValue();

    /**
     * Sets the value of the '{@link com.tibco.xpd.presentation.channels.AttributeValue#getValue <em>Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Value</em>' attribute.
     * @see #getValue()
     * @generated
     */
    void setValue(String value);

    /**
     * Returns the value of the '<em><b>Enum Values</b></em>' reference list.
     * The list contents are of type {@link com.tibco.xpd.presentation.channeltypes.EnumLiteral}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Enum Values</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Enum Values</em>' reference list.
     * @see com.tibco.xpd.presentation.channels.ChannelsPackage#getAttributeValue_EnumValues()
     * @model
     * @generated
     */
    EList<EnumLiteral> getEnumValues();

    /**
     * Returns the value of the '<em><b>Attribute</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Attribute</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Attribute</em>' reference.
     * @see #setAttribute(Attribute)
     * @see com.tibco.xpd.presentation.channels.ChannelsPackage#getAttributeValue_Attribute()
     * @model required="true"
     * @generated
     */
    Attribute getAttribute();

    /**
     * Sets the value of the '{@link com.tibco.xpd.presentation.channels.AttributeValue#getAttribute <em>Attribute</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute</em>' reference.
     * @see #getAttribute()
     * @generated
     */
    void setAttribute(Attribute value);

    /**
     * Returns the value of the '<em><b>Type</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.presentation.channeltypes.AttributeType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Type</em>' attribute.
     * @see com.tibco.xpd.presentation.channeltypes.AttributeType
     * @see com.tibco.xpd.presentation.channels.ChannelsPackage#getAttributeValue_Type()
     * @model transient="true" changeable="false" volatile="true" derived="true"
     * @generated
     */
    AttributeType getType();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model
     * @generated
     */
    String getResolvedValue(boolean useDefault);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model kind="operation"
     * @generated
     */
    String getAttributeName();

} // AttributeValue
