/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de;

import java.math.BigDecimal;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *         Describes an instance of AttributeType, listing the values assigned to the
 *         attribute instance.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.Attribute#getString <em>String</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Attribute#getInteger <em>Integer</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Attribute#getDecimal <em>Decimal</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Attribute#getDate <em>Date</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Attribute#getTime <em>Time</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Attribute#getDateTime <em>Date Time</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Attribute#isBoolean <em>Boolean</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Attribute#getEnum <em>Enum</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Attribute#getEnumSet <em>Enum Set</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Attribute#getElement <em>Element</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Attribute#getDefinition <em>Definition</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.directory.model.de.DePackage#getAttribute()
 * @model extendedMetaData="name='Attribute' kind='elementOnly'"
 * @generated
 */
public interface Attribute extends EObject {
    /**
     * Returns the value of the '<em><b>String</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>String</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>String</em>' attribute.
     * @see #setString(String)
     * @see com.tibco.n2.directory.model.de.DePackage#getAttribute_String()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='string' namespace='##targetNamespace'"
     * @generated
     */
    String getString();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Attribute#getString <em>String</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>String</em>' attribute.
     * @see #getString()
     * @generated
     */
    void setString(String value);

    /**
     * Returns the value of the '<em><b>Integer</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Integer</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Integer</em>' attribute.
     * @see #isSetInteger()
     * @see #unsetInteger()
     * @see #setInteger(long)
     * @see com.tibco.n2.directory.model.de.DePackage#getAttribute_Integer()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long"
     *        extendedMetaData="kind='element' name='integer' namespace='##targetNamespace'"
     * @generated
     */
    long getInteger();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Attribute#getInteger <em>Integer</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Integer</em>' attribute.
     * @see #isSetInteger()
     * @see #unsetInteger()
     * @see #getInteger()
     * @generated
     */
    void setInteger(long value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.directory.model.de.Attribute#getInteger <em>Integer</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetInteger()
     * @see #getInteger()
     * @see #setInteger(long)
     * @generated
     */
    void unsetInteger();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.directory.model.de.Attribute#getInteger <em>Integer</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Integer</em>' attribute is set.
     * @see #unsetInteger()
     * @see #getInteger()
     * @see #setInteger(long)
     * @generated
     */
    boolean isSetInteger();

    /**
     * Returns the value of the '<em><b>Decimal</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Decimal</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Decimal</em>' attribute.
     * @see #setDecimal(BigDecimal)
     * @see com.tibco.n2.directory.model.de.DePackage#getAttribute_Decimal()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Decimal"
     *        extendedMetaData="kind='element' name='decimal' namespace='##targetNamespace'"
     * @generated
     */
    BigDecimal getDecimal();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Attribute#getDecimal <em>Decimal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Decimal</em>' attribute.
     * @see #getDecimal()
     * @generated
     */
    void setDecimal(BigDecimal value);

    /**
     * Returns the value of the '<em><b>Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Date</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Date</em>' attribute.
     * @see #setDate(XMLGregorianCalendar)
     * @see com.tibco.n2.directory.model.de.DePackage#getAttribute_Date()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Date"
     *        extendedMetaData="kind='element' name='date' namespace='##targetNamespace'"
     * @generated
     */
    XMLGregorianCalendar getDate();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Attribute#getDate <em>Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Date</em>' attribute.
     * @see #getDate()
     * @generated
     */
    void setDate(XMLGregorianCalendar value);

    /**
     * Returns the value of the '<em><b>Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Time</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Time</em>' attribute.
     * @see #setTime(XMLGregorianCalendar)
     * @see com.tibco.n2.directory.model.de.DePackage#getAttribute_Time()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Time"
     *        extendedMetaData="kind='element' name='time' namespace='##targetNamespace'"
     * @generated
     */
    XMLGregorianCalendar getTime();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Attribute#getTime <em>Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Time</em>' attribute.
     * @see #getTime()
     * @generated
     */
    void setTime(XMLGregorianCalendar value);

    /**
     * Returns the value of the '<em><b>Date Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Date Time</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Date Time</em>' attribute.
     * @see #setDateTime(XMLGregorianCalendar)
     * @see com.tibco.n2.directory.model.de.DePackage#getAttribute_DateTime()
     * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
     *        extendedMetaData="kind='element' name='date-time' namespace='##targetNamespace'"
     * @generated
     */
    XMLGregorianCalendar getDateTime();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Attribute#getDateTime <em>Date Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Date Time</em>' attribute.
     * @see #getDateTime()
     * @generated
     */
    void setDateTime(XMLGregorianCalendar value);

    /**
     * Returns the value of the '<em><b>Boolean</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Boolean</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Boolean</em>' attribute.
     * @see #isSetBoolean()
     * @see #unsetBoolean()
     * @see #setBoolean(boolean)
     * @see com.tibco.n2.directory.model.de.DePackage#getAttribute_Boolean()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='element' name='boolean' namespace='##targetNamespace'"
     * @generated
     */
    boolean isBoolean();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Attribute#isBoolean <em>Boolean</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Boolean</em>' attribute.
     * @see #isSetBoolean()
     * @see #unsetBoolean()
     * @see #isBoolean()
     * @generated
     */
    void setBoolean(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.directory.model.de.Attribute#isBoolean <em>Boolean</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetBoolean()
     * @see #isBoolean()
     * @see #setBoolean(boolean)
     * @generated
     */
    void unsetBoolean();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.directory.model.de.Attribute#isBoolean <em>Boolean</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Boolean</em>' attribute is set.
     * @see #unsetBoolean()
     * @see #isBoolean()
     * @see #setBoolean(boolean)
     * @generated
     */
    boolean isSetBoolean();

    /**
     * Returns the value of the '<em><b>Enum</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Enum</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Enum</em>' attribute.
     * @see #setEnum(String)
     * @see com.tibco.n2.directory.model.de.DePackage#getAttribute_Enum()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='enum' namespace='##targetNamespace'"
     * @generated
     */
    String getEnum();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Attribute#getEnum <em>Enum</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Enum</em>' attribute.
     * @see #getEnum()
     * @generated
     */
    void setEnum(String value);

    /**
     * Returns the value of the '<em><b>Enum Set</b></em>' attribute list.
     * The list contents are of type {@link java.lang.String}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Enum Set</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Enum Set</em>' attribute list.
     * @see com.tibco.n2.directory.model.de.DePackage#getAttribute_EnumSet()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='enum-set' namespace='##targetNamespace'"
     * @generated
     */
    EList<String> getEnumSet();

    /**
     * Returns the value of the '<em><b>Element</b></em>' attribute list.
     * The list contents are of type {@link java.lang.String}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Element</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Element</em>' attribute list.
     * @see com.tibco.n2.directory.model.de.DePackage#getAttribute_Element()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<String> getElement();

    /**
     * Returns the value of the '<em><b>Definition</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *           Identifies, by its ID, the AttributeType that defines this Attribute.
     *         
     * <!-- end-model-doc -->
     * @return the value of the '<em>Definition</em>' reference.
     * @see #setDefinition(AttributeType)
     * @see com.tibco.n2.directory.model.de.DePackage#getAttribute_Definition()
     * @model resolveProxies="false" required="true"
     *        extendedMetaData="kind='attribute' name='definition'"
     * @generated
     */
    AttributeType getDefinition();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Attribute#getDefinition <em>Definition</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Definition</em>' reference.
     * @see #getDefinition()
     * @generated
     */
    void setDefinition(AttributeType value);

} // Attribute
