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
 * A representation of the model object '<em><b>Loop Multi Instance</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.LoopMultiInstance#getLoopCounter <em>Loop Counter</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.LoopMultiInstance#getMIFlowCondition <em>MI Flow Condition</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.LoopMultiInstance#getMIOrdering <em>MI Ordering</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.LoopMultiInstance#getMICondition <em>MI Condition</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.LoopMultiInstance#getComplexMIFlowCondition <em>Complex MI Flow Condition</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.LoopMultiInstance#getAttributeComplexMI_FlowCondition <em>Attribute Complex MI Flow Condition</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.LoopMultiInstance#getAttributeMI_Condition <em>Attribute MI Condition</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLoopMultiInstance()
 * @model extendedMetaData="name='LoopMultiInstance_._type' kind='elementOnly' features-order='mICondition complexMIFlowCondition otherElements'"
 * @generated
 */
public interface LoopMultiInstance extends OtherElementsContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Complex MI Flow Condition</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Complex MI Flow Condition</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Complex MI Flow Condition</em>' containment reference.
     * @see #setComplexMIFlowCondition(Expression)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLoopMultiInstance_ComplexMIFlowCondition()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ComplexMI_FlowCondition' namespace='##targetNamespace'"
     * @generated
     */
    Expression getComplexMIFlowCondition();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.LoopMultiInstance#getComplexMIFlowCondition <em>Complex MI Flow Condition</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Complex MI Flow Condition</em>' containment reference.
     * @see #getComplexMIFlowCondition()
     * @generated
     */
    void setComplexMIFlowCondition(Expression value);

    /**
     * Returns the value of the '<em><b>Attribute Complex MI Flow Condition</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Attribute Complex MI Flow Condition</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Attribute Complex MI Flow Condition</em>' attribute.
     * @see #setAttributeComplexMI_FlowCondition(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLoopMultiInstance_AttributeComplexMI_FlowCondition()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='ComplexMI_FlowCondition'"
     * @generated
     */
    String getAttributeComplexMI_FlowCondition();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.LoopMultiInstance#getAttributeComplexMI_FlowCondition <em>Attribute Complex MI Flow Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute Complex MI Flow Condition</em>' attribute.
     * @see #getAttributeComplexMI_FlowCondition()
     * @generated
     */
    void setAttributeComplexMI_FlowCondition(String value);

    /**
     * Returns the value of the '<em><b>Attribute MI Condition</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Attribute MI Condition</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Attribute MI Condition</em>' attribute.
     * @see #setAttributeMI_Condition(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLoopMultiInstance_AttributeMI_Condition()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='MI_Condition'"
     * @generated
     */
    String getAttributeMI_Condition();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.LoopMultiInstance#getAttributeMI_Condition <em>Attribute MI Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute MI Condition</em>' attribute.
     * @see #getAttributeMI_Condition()
     * @generated
     */
    void setAttributeMI_Condition(String value);

    /**
     * Returns the value of the '<em><b>Loop Counter</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     *  This is updated at run time to count the number of executions of the loop and is available as a property to be used in expressions. Does this belong in the XPDL?
     * <!-- end-model-doc -->
     * @return the value of the '<em>Loop Counter</em>' attribute.
     * @see #setLoopCounter(BigInteger)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLoopMultiInstance_LoopCounter()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Integer"
     *        extendedMetaData="kind='attribute' name='LoopCounter'"
     * @generated
     */
    BigInteger getLoopCounter();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.LoopMultiInstance#getLoopCounter <em>Loop Counter</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Loop Counter</em>' attribute.
     * @see #getLoopCounter()
     * @generated
     */
    void setLoopCounter(BigInteger value);

    /**
     * Returns the value of the '<em><b>MI Condition</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>MI Condition</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>MI Condition</em>' containment reference.
     * @see #setMICondition(Expression)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLoopMultiInstance_MICondition()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='MI_Condition' namespace='##targetNamespace'"
     * @generated
     */
    Expression getMICondition();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.LoopMultiInstance#getMICondition <em>MI Condition</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>MI Condition</em>' containment reference.
     * @see #getMICondition()
     * @generated
     */
    void setMICondition(Expression value);

    /**
     * Returns the value of the '<em><b>MI Flow Condition</b></em>' attribute.
     * The default value is <code>"All"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.MIFlowConditionType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>MI Flow Condition</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>MI Flow Condition</em>' attribute.
     * @see com.tibco.xpd.xpdl2.MIFlowConditionType
     * @see #isSetMIFlowCondition()
     * @see #unsetMIFlowCondition()
     * @see #setMIFlowCondition(MIFlowConditionType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLoopMultiInstance_MIFlowCondition()
     * @model default="All" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='MI_FlowCondition'"
     * @generated
     */
    MIFlowConditionType getMIFlowCondition();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.LoopMultiInstance#getMIFlowCondition <em>MI Flow Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>MI Flow Condition</em>' attribute.
     * @see com.tibco.xpd.xpdl2.MIFlowConditionType
     * @see #isSetMIFlowCondition()
     * @see #unsetMIFlowCondition()
     * @see #getMIFlowCondition()
     * @generated
     */
    void setMIFlowCondition(MIFlowConditionType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.LoopMultiInstance#getMIFlowCondition <em>MI Flow Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetMIFlowCondition()
     * @see #getMIFlowCondition()
     * @see #setMIFlowCondition(MIFlowConditionType)
     * @generated
     */
    void unsetMIFlowCondition();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.LoopMultiInstance#getMIFlowCondition <em>MI Flow Condition</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>MI Flow Condition</em>' attribute is set.
     * @see #unsetMIFlowCondition()
     * @see #getMIFlowCondition()
     * @see #setMIFlowCondition(MIFlowConditionType)
     * @generated
     */
    boolean isSetMIFlowCondition();

    /**
     * Returns the value of the '<em><b>MI Ordering</b></em>' attribute.
     * The default value is <code>"Sequential"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.MIOrderingType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>MI Ordering</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>MI Ordering</em>' attribute.
     * @see com.tibco.xpd.xpdl2.MIOrderingType
     * @see #isSetMIOrdering()
     * @see #unsetMIOrdering()
     * @see #setMIOrdering(MIOrderingType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLoopMultiInstance_MIOrdering()
     * @model default="Sequential" unique="false" unsettable="true" required="true"
     *        extendedMetaData="kind='attribute' name='MI_Ordering'"
     * @generated
     */
    MIOrderingType getMIOrdering();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.LoopMultiInstance#getMIOrdering <em>MI Ordering</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>MI Ordering</em>' attribute.
     * @see com.tibco.xpd.xpdl2.MIOrderingType
     * @see #isSetMIOrdering()
     * @see #unsetMIOrdering()
     * @see #getMIOrdering()
     * @generated
     */
    void setMIOrdering(MIOrderingType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.LoopMultiInstance#getMIOrdering <em>MI Ordering</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetMIOrdering()
     * @see #getMIOrdering()
     * @see #setMIOrdering(MIOrderingType)
     * @generated
     */
    void unsetMIOrdering();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.LoopMultiInstance#getMIOrdering <em>MI Ordering</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>MI Ordering</em>' attribute is set.
     * @see #unsetMIOrdering()
     * @see #getMIOrdering()
     * @see #setMIOrdering(MIOrderingType)
     * @generated
     */
    boolean isSetMIOrdering();

} // LoopMultiInstance
