/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.DataMapping#getActual <em>Actual</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.DataMapping#getDirection <em>Direction</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.DataMapping#getFormal <em>Formal</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.DataMapping#getTestValue <em>Test Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getDataMapping()
 * @model extendedMetaData="name='DataMapping_._type' kind='elementOnly'"
 * @generated
 */
public interface DataMapping extends OtherElementsContainer,
        OtherAttributesContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Actual</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Actual</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Actual</em>' containment reference.
     * @see #setActual(Expression)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getDataMapping_Actual()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='Actual' namespace='##targetNamespace'"
     * @generated
     */
    Expression getActual();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.DataMapping#getActual <em>Actual</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Actual</em>' containment reference.
     * @see #getActual()
     * @generated
     */
    void setActual(Expression value);

    /**
     * Returns the value of the '<em><b>Direction</b></em>' attribute.
     * The default value is <code>"IN"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.DirectionType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Direction</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Direction</em>' attribute.
     * @see com.tibco.xpd.xpdl2.DirectionType
     * @see #isSetDirection()
     * @see #unsetDirection()
     * @see #setDirection(DirectionType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getDataMapping_Direction()
     * @model default="IN" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='Direction'"
     * @generated
     */
    DirectionType getDirection();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.DataMapping#getDirection <em>Direction</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Direction</em>' attribute.
     * @see com.tibco.xpd.xpdl2.DirectionType
     * @see #isSetDirection()
     * @see #unsetDirection()
     * @see #getDirection()
     * @generated
     */
    void setDirection(DirectionType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.DataMapping#getDirection <em>Direction</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDirection()
     * @see #getDirection()
     * @see #setDirection(DirectionType)
     * @generated
     */
    void unsetDirection();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.DataMapping#getDirection <em>Direction</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Direction</em>' attribute is set.
     * @see #unsetDirection()
     * @see #getDirection()
     * @see #setDirection(DirectionType)
     * @generated
     */
    boolean isSetDirection();

    /**
     * Returns the value of the '<em><b>Formal</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Formal</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Formal</em>' attribute.
     * @see #setFormal(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getDataMapping_Formal()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='Formal'"
     * @generated
     */
    String getFormal();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.DataMapping#getFormal <em>Formal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Formal</em>' attribute.
     * @see #getFormal()
     * @generated
     */
    void setFormal(String value);

    /**
     * Returns the value of the '<em><b>Test Value</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Test Value</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Test Value</em>' containment reference.
     * @see #setTestValue(Expression)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getDataMapping_TestValue()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='TestValue' namespace='##targetNamespace'"
     * @generated
     */
    Expression getTestValue();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.DataMapping#getTestValue <em>Test Value</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Test Value</em>' containment reference.
     * @see #getTestValue()
     * @generated
     */
    void setTestValue(Expression value);

} // DataMapping
