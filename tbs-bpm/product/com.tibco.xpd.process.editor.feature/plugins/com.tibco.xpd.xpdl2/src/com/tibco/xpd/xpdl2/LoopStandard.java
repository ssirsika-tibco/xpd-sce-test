/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import java.math.BigInteger;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Loop Standard</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.LoopStandard#getLoopCounter <em>Loop Counter</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.LoopStandard#getLoopMaximum <em>Loop Maximum</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.LoopStandard#getTestTime <em>Test Time</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.LoopStandard#getAttributeLoopCondition <em>Attribute Loop Condition</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.LoopStandard#getLoopCondition <em>Loop Condition</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLoopStandard()
 * @model extendedMetaData="name='LoopStandard_._type' kind='elementOnly' features-order='loopCondition otherElements'"
 * @generated
 */
public interface LoopStandard extends OtherElementsContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Loop Condition</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Loop Condition</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Loop Condition</em>' containment reference.
     * @see #setLoopCondition(Expression)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLoopStandard_LoopCondition()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='LoopCondition' namespace='##targetNamespace'"
     * @generated
     */
    Expression getLoopCondition();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.LoopStandard#getLoopCondition <em>Loop Condition</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Loop Condition</em>' containment reference.
     * @see #getLoopCondition()
     * @generated
     */
    void setLoopCondition(Expression value);

    /**
     * Returns the value of the '<em><b>Loop Counter</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     *  This is updated at run time to count the number of executions of the loop and is available as a property to be used in expressions. Does this belong in the XPDL?
     * <!-- end-model-doc -->
     * @return the value of the '<em>Loop Counter</em>' attribute.
     * @see #setLoopCounter(BigInteger)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLoopStandard_LoopCounter()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Integer"
     *        extendedMetaData="kind='attribute' name='LoopCounter'"
     * @generated
     */
    BigInteger getLoopCounter();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.LoopStandard#getLoopCounter <em>Loop Counter</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Loop Counter</em>' attribute.
     * @see #getLoopCounter()
     * @generated
     */
    void setLoopCounter(BigInteger value);

    /**
     * Returns the value of the '<em><b>Loop Maximum</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Loop Maximum</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Loop Maximum</em>' attribute.
     * @see #setLoopMaximum(BigInteger)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLoopStandard_LoopMaximum()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Integer"
     *        extendedMetaData="kind='attribute' name='LoopMaximum'"
     * @generated
     */
    BigInteger getLoopMaximum();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.LoopStandard#getLoopMaximum <em>Loop Maximum</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Loop Maximum</em>' attribute.
     * @see #getLoopMaximum()
     * @generated
     */
    void setLoopMaximum(BigInteger value);

    /**
     * Returns the value of the '<em><b>Test Time</b></em>' attribute.
     * The default value is <code>"Before"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.TestTimeType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Test Time</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Test Time</em>' attribute.
     * @see com.tibco.xpd.xpdl2.TestTimeType
     * @see #isSetTestTime()
     * @see #unsetTestTime()
     * @see #setTestTime(TestTimeType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLoopStandard_TestTime()
     * @model default="Before" unique="false" unsettable="true" required="true"
     *        extendedMetaData="kind='attribute' name='TestTime'"
     * @generated
     */
    TestTimeType getTestTime();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.LoopStandard#getTestTime <em>Test Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Test Time</em>' attribute.
     * @see com.tibco.xpd.xpdl2.TestTimeType
     * @see #isSetTestTime()
     * @see #unsetTestTime()
     * @see #getTestTime()
     * @generated
     */
    void setTestTime(TestTimeType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.LoopStandard#getTestTime <em>Test Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetTestTime()
     * @see #getTestTime()
     * @see #setTestTime(TestTimeType)
     * @generated
     */
    void unsetTestTime();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.LoopStandard#getTestTime <em>Test Time</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Test Time</em>' attribute is set.
     * @see #unsetTestTime()
     * @see #getTestTime()
     * @see #setTestTime(TestTimeType)
     * @generated
     */
    boolean isSetTestTime();

    /**
     * Returns the value of the '<em><b>Attribute Loop Condition</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Attribute Loop Condition</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Attribute Loop Condition</em>' attribute.
     * @see #setAttributeLoopCondition(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLoopStandard_AttributeLoopCondition()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='LoopCondition'"
     * @generated
     */
    String getAttributeLoopCondition();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.LoopStandard#getAttributeLoopCondition <em>Attribute Loop Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute Loop Condition</em>' attribute.
     * @see #getAttributeLoopCondition()
     * @generated
     */
    void setAttributeLoopCondition(String value);

} // LoopStandard
