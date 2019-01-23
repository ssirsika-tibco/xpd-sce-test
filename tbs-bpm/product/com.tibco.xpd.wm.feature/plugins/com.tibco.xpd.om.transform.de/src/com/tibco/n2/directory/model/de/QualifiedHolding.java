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
 * A representation of the model object '<em><b>Qualified Holding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *         This decribes the holding of an entity (assigned to a Group, Position or Org-Unit)
 *         and any qualifying value that applies to that holding.
 *         The term "qualifier" simply means that the association may carry a value that
 *         distinguishes two holdings of the same entity. For example, the Privilege
 *         to approve purchase orders may be qualified with the max value to which those
 *         purchase orders are made.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.QualifiedHolding#getString <em>String</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.QualifiedHolding#getInteger <em>Integer</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.QualifiedHolding#getDecimal <em>Decimal</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.QualifiedHolding#getDate <em>Date</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.QualifiedHolding#getTime <em>Time</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.QualifiedHolding#getDateTime <em>Date Time</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.QualifiedHolding#isBoolean <em>Boolean</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.QualifiedHolding#getEnum <em>Enum</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.QualifiedHolding#getEnumSet <em>Enum Set</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.QualifiedHolding#getElement <em>Element</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.directory.model.de.DePackage#getQualifiedHolding()
 * @model abstract="true"
 *        extendedMetaData="name='QualifiedHolding' kind='elementOnly'"
 * @generated
 */
public interface QualifiedHolding extends EObject {
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
     * @see com.tibco.n2.directory.model.de.DePackage#getQualifiedHolding_String()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='string' namespace='##targetNamespace'"
     * @generated
     */
    String getString();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.QualifiedHolding#getString <em>String</em>}' attribute.
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
     * @see com.tibco.n2.directory.model.de.DePackage#getQualifiedHolding_Integer()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long"
     *        extendedMetaData="kind='element' name='integer' namespace='##targetNamespace'"
     * @generated
     */
    long getInteger();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.QualifiedHolding#getInteger <em>Integer</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.n2.directory.model.de.QualifiedHolding#getInteger <em>Integer</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetInteger()
     * @see #getInteger()
     * @see #setInteger(long)
     * @generated
     */
    void unsetInteger();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.directory.model.de.QualifiedHolding#getInteger <em>Integer</em>}' attribute is set.
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
     * @see com.tibco.n2.directory.model.de.DePackage#getQualifiedHolding_Decimal()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Decimal"
     *        extendedMetaData="kind='element' name='decimal' namespace='##targetNamespace'"
     * @generated
     */
    BigDecimal getDecimal();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.QualifiedHolding#getDecimal <em>Decimal</em>}' attribute.
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
     * @see com.tibco.n2.directory.model.de.DePackage#getQualifiedHolding_Date()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Date"
     *        extendedMetaData="kind='element' name='date' namespace='##targetNamespace'"
     * @generated
     */
    XMLGregorianCalendar getDate();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.QualifiedHolding#getDate <em>Date</em>}' attribute.
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
     * @see com.tibco.n2.directory.model.de.DePackage#getQualifiedHolding_Time()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Time"
     *        extendedMetaData="kind='element' name='time' namespace='##targetNamespace'"
     * @generated
     */
    XMLGregorianCalendar getTime();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.QualifiedHolding#getTime <em>Time</em>}' attribute.
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
     * @see com.tibco.n2.directory.model.de.DePackage#getQualifiedHolding_DateTime()
     * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
     *        extendedMetaData="kind='element' name='date-time' namespace='##targetNamespace'"
     * @generated
     */
    XMLGregorianCalendar getDateTime();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.QualifiedHolding#getDateTime <em>Date Time</em>}' attribute.
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
     * @see com.tibco.n2.directory.model.de.DePackage#getQualifiedHolding_Boolean()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='element' name='boolean' namespace='##targetNamespace'"
     * @generated
     */
    boolean isBoolean();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.QualifiedHolding#isBoolean <em>Boolean</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.n2.directory.model.de.QualifiedHolding#isBoolean <em>Boolean</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetBoolean()
     * @see #isBoolean()
     * @see #setBoolean(boolean)
     * @generated
     */
    void unsetBoolean();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.directory.model.de.QualifiedHolding#isBoolean <em>Boolean</em>}' attribute is set.
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
     * @see com.tibco.n2.directory.model.de.DePackage#getQualifiedHolding_Enum()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='enum' namespace='##targetNamespace'"
     * @generated
     */
    String getEnum();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.QualifiedHolding#getEnum <em>Enum</em>}' attribute.
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
     * @see com.tibco.n2.directory.model.de.DePackage#getQualifiedHolding_EnumSet()
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
     * @see com.tibco.n2.directory.model.de.DePackage#getQualifiedHolding_Element()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='element' namespace='##targetNamespace'"
     * @generated
     */
    EList<String> getElement();

} // QualifiedHolding
