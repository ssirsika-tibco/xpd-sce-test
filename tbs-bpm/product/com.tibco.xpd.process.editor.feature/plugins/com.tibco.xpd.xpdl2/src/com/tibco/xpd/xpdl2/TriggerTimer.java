/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Trigger Timer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerTimer#getDeprecatedTimeCycle <em>Deprecated Time Cycle</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerTimer#getDeprecatedTimeDate <em>Deprecated Time Date</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerTimer#getTimeDate <em>Time Date</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerTimer#getTimeCycle <em>Time Cycle</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerTimer()
 * @model extendedMetaData="name='TriggerTimer_._type' kind='elementOnly' features-order='timeDate timeCycle otherElements otherAttributes'"
 * @generated
 */
public interface TriggerTimer extends OtherAttributesContainer, OtherElementsContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Deprecated Time Cycle</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Deprecated Time Cycle</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Deprecated Time Cycle</em>' attribute.
     * @see #setDeprecatedTimeCycle(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerTimer_DeprecatedTimeCycle()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='TimeCycle'"
     * @generated
     */
    String getDeprecatedTimeCycle();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TriggerTimer#getDeprecatedTimeCycle <em>Deprecated Time Cycle</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Deprecated Time Cycle</em>' attribute.
     * @see #getDeprecatedTimeCycle()
     * @generated
     */
    void setDeprecatedTimeCycle(String value);

    /**
     * Returns the value of the '<em><b>Deprecated Time Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * One of TimeDate or TimeCycle must be present
     * <!-- end-model-doc -->
     * @return the value of the '<em>Deprecated Time Date</em>' attribute.
     * @see #setDeprecatedTimeDate(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerTimer_DeprecatedTimeDate()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='TimeDate'"
     * @generated
     */
    String getDeprecatedTimeDate();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TriggerTimer#getDeprecatedTimeDate <em>Deprecated Time Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Deprecated Time Date</em>' attribute.
     * @see #getDeprecatedTimeDate()
     * @generated
     */
    void setDeprecatedTimeDate(String value);

    /**
     * Returns the value of the '<em><b>Time Cycle</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Time Cycle</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Time Cycle</em>' containment reference.
     * @see #setTimeCycle(Expression)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerTimer_TimeCycle()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='TimeCycle' namespace='##targetNamespace'"
     * @generated
     */
    Expression getTimeCycle();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TriggerTimer#getTimeCycle <em>Time Cycle</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Time Cycle</em>' containment reference.
     * @see #getTimeCycle()
     * @generated
     */
    void setTimeCycle(Expression value);

    /**
     * Returns the value of the '<em><b>Time Date</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the value of the '<em>Time Date</em>' containment reference.
     * @see #setTimeDate(Expression)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerTimer_TimeDate()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='TimeDate' namespace='##targetNamespace'"
     * @generated
     */
    Expression getTimeDate();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TriggerTimer#getTimeDate <em>Time Date</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Time Date</em>' containment reference.
     * @see #getTimeDate()
     * @generated
     */
    void setTimeDate(Expression value);

} // TriggerTimer
