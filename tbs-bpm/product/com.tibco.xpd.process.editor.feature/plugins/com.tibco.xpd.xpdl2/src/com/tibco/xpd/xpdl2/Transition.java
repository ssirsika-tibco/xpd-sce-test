/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Transition#getCondition <em>Condition</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Transition#getAssignments <em>Assignments</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Transition#getObject <em>Object</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Transition#getFrom <em>From</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Transition#getQuantity <em>Quantity</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Transition#getTo <em>To</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Transition#getFlowContainer <em>Flow Container</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTransition()
 * @model extendedMetaData="name='Transition_._type' kind='elementOnly' features-order='condition description extendedAttributes assignments object connectorGraphicsInfos'"
 * @generated
 */
public interface Transition extends NamedElement, ExtendedAttributesContainer,
        GraphicalConnector, DescribedElement {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Condition</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Condition</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Condition</em>' containment reference.
     * @see #setCondition(Condition)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTransition_Condition()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Condition' namespace='##targetNamespace'"
     * @generated
     */
    Condition getCondition();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Transition#getCondition <em>Condition</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Condition</em>' containment reference.
     * @see #getCondition()
     * @generated
     */
    void setCondition(Condition value);

    /**
     * Returns the value of the '<em><b>Assignments</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Assignment}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Assignments</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Assignments</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTransition_Assignments()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Assignment' namespace='##targetNamespace' wrap='Assignments'"
     * @generated
     */
    EList<Assignment> getAssignments();

    /**
     * Returns the value of the '<em><b>Object</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Object</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Object</em>' containment reference.
     * @see #setObject(com.tibco.xpd.xpdl2.Object)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTransition_Object()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Object' namespace='##targetNamespace'"
     * @generated
     */
    com.tibco.xpd.xpdl2.Object getObject();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Transition#getObject <em>Object</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Object</em>' containment reference.
     * @see #getObject()
     * @generated
     */
    void setObject(com.tibco.xpd.xpdl2.Object value);

    /**
     * Returns the value of the '<em><b>From</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>From</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>From</em>' attribute.
     * @see #setFrom(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTransition_From()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString" required="true"
     *        extendedMetaData="kind='attribute' name='From'"
     * @generated
     */
    String getFrom();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Transition#getFrom <em>From</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>From</em>' attribute.
     * @see #getFrom()
     * @generated
     */
    void setFrom(String value);

    /**
     * Returns the value of the '<em><b>Quantity</b></em>' attribute.
     * The default value is <code>"1"</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Used only in BPMN. Specifies number of tokens on outgoing transition.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Quantity</em>' attribute.
     * @see #isSetQuantity()
     * @see #unsetQuantity()
     * @see #setQuantity(int)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTransition_Quantity()
     * @model default="1" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='Quantity'"
     * @generated
     */
    int getQuantity();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Transition#getQuantity <em>Quantity</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Quantity</em>' attribute.
     * @see #isSetQuantity()
     * @see #unsetQuantity()
     * @see #getQuantity()
     * @generated
     */
    void setQuantity(int value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Transition#getQuantity <em>Quantity</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetQuantity()
     * @see #getQuantity()
     * @see #setQuantity(int)
     * @generated
     */
    void unsetQuantity();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Transition#getQuantity <em>Quantity</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Quantity</em>' attribute is set.
     * @see #unsetQuantity()
     * @see #getQuantity()
     * @see #setQuantity(int)
     * @generated
     */
    boolean isSetQuantity();

    /**
     * Returns the value of the '<em><b>To</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>To</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>To</em>' attribute.
     * @see #setTo(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTransition_To()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString" required="true"
     *        extendedMetaData="kind='attribute' name='To'"
     * @generated
     */
    String getTo();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Transition#getTo <em>To</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>To</em>' attribute.
     * @see #getTo()
     * @generated
     */
    void setTo(String value);

    /**
     * Returns the value of the '<em><b>Flow Container</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.xpdl2.FlowContainer#getTransitions <em>Transitions</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Flow Container</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Flow Container</em>' container reference.
     * @see #setFlowContainer(FlowContainer)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTransition_FlowContainer()
     * @see com.tibco.xpd.xpdl2.FlowContainer#getTransitions
     * @model opposite="transitions" transient="false"
     * @generated
     */
    FlowContainer getFlowContainer();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Transition#getFlowContainer <em>Flow Container</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Flow Container</em>' container reference.
     * @see #getFlowContainer()
     * @generated
     */
    void setFlowContainer(FlowContainer value);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Parent process, note: when the direct parent of the activity or transition is an activity set, this method will return parent process of it
     * <!-- end-model-doc -->
     * @model kind="operation"
     * @generated
     */
    com.tibco.xpd.xpdl2.Process getProcess();

} // Transition
