/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import java.math.BigInteger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Activity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getLimit <em>Limit</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getRoute <em>Route</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getImplementation <em>Implementation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getBlockActivity <em>Block Activity</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getEvent <em>Event</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getTransaction <em>Transaction</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getPerformer <em>Performer</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getPerformers <em>Performers</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getPriority <em>Priority</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getDeadline <em>Deadline</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getSimulationInformation <em>Simulation Information</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getIcon <em>Icon</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getTransitionRestrictions <em>Transition Restrictions</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getInputSets <em>Input Sets</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getOutputSets <em>Output Sets</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getIoRules <em>Io Rules</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getLoop <em>Loop</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getAssignments <em>Assignments</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getObject <em>Object</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getFinishMode <em>Finish Mode</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#isIsATransaction <em>Is ATransaction</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#isStartActivity <em>Start Activity</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getStartMode <em>Start Mode</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getStartQuantity <em>Start Quantity</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getStatus <em>Status</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getFlowContainer <em>Flow Container</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getProcess <em>Process</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#isIsForCompensation <em>Is For Compensation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Activity#getCompletionQuantity <em>Completion Quantity</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity()
 * @model extendedMetaData="name='Activity_._type' kind='elementOnly' features-order='description limit route implementation blockActivity event transaction performer performers priority deadline simulationInformation icon documentation transitionRestrictions extendedAttributes dataFields inputSets outputSets iORules loop assignments object nodeGraphicsInfos extensions otherElements otherAttributes'"
 * @generated
 */
public interface Activity extends NamedElement, ExtendedAttributesContainer,
        GraphicalNode, DescribedElement, OtherElementsContainer,
        DataFieldsContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Limit</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Limit</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Limit</em>' containment reference.
     * @see #setLimit(Limit)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_Limit()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Limit' namespace='##targetNamespace'"
     * @generated
     */
    Limit getLimit();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getLimit <em>Limit</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Limit</em>' containment reference.
     * @see #getLimit()
     * @generated
     */
    void setLimit(Limit value);

    /**
     * Returns the value of the '<em><b>Route</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Route</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Route</em>' containment reference.
     * @see #setRoute(Route)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_Route()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Route' namespace='##targetNamespace'"
     * @generated
     */
    Route getRoute();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getRoute <em>Route</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Route</em>' containment reference.
     * @see #getRoute()
     * @generated
     */
    void setRoute(Route value);

    /**
     * Returns the value of the '<em><b>Implementation</b></em>' containment reference.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.xpdl2.Implementation#getActivity <em>Activity</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Implementation</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Implementation</em>' containment reference.
     * @see #setImplementation(Implementation)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_Implementation()
     * @see com.tibco.xpd.xpdl2.Implementation#getActivity
     * @model opposite="activity" containment="true"
     *        extendedMetaData="kind='element' name='Implementation' namespace='##targetNamespace' subclass-wrap='Implementation'"
     * @generated
     */
    Implementation getImplementation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getImplementation <em>Implementation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Implementation</em>' containment reference.
     * @see #getImplementation()
     * @generated
     */
    void setImplementation(Implementation value);

    /**
     * Returns the value of the '<em><b>Block Activity</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Block Activity</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Block Activity</em>' containment reference.
     * @see #setBlockActivity(BlockActivity)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_BlockActivity()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='BlockActivity' namespace='##targetNamespace'"
     * @generated
     */
    BlockActivity getBlockActivity();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getBlockActivity <em>Block Activity</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Block Activity</em>' containment reference.
     * @see #getBlockActivity()
     * @generated
     */
    void setBlockActivity(BlockActivity value);

    /**
     * Returns the value of the '<em><b>Event</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Event</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Event</em>' containment reference.
     * @see #setEvent(Event)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_Event()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Event' namespace='##targetNamespace' subclass-wrap='Event'"
     * @generated
     */
    Event getEvent();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getEvent <em>Event</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Event</em>' containment reference.
     * @see #getEvent()
     * @generated
     */
    void setEvent(Event value);

    /**
     * Returns the value of the '<em><b>Transaction</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Transaction</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Transaction</em>' containment reference.
     * @see #setTransaction(Transaction)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_Transaction()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Transaction' namespace='##targetNamespace'"
     * @generated
     */
    Transaction getTransaction();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getTransaction <em>Transaction</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Transaction</em>' containment reference.
     * @see #getTransaction()
     * @generated
     */
    void setTransaction(Transaction value);

    /**
     * Returns the value of the '<em><b>Performer</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Performer</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Performer</em>' containment reference.
     * @see #setPerformer(Performer)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_Performer()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Performer' namespace='##targetNamespace'"
     * @generated
     */
    Performer getPerformer();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getPerformer <em>Performer</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Performer</em>' containment reference.
     * @see #getPerformer()
     * @generated
     */
    void setPerformer(Performer value);

    /**
     * Returns the value of the '<em><b>Performers</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Performers</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Performers</em>' containment reference.
     * @see #setPerformers(Performers)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_Performers()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Performers' namespace='##targetNamespace'"
     * @generated
     */
    Performers getPerformers();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getPerformers <em>Performers</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Performers</em>' containment reference.
     * @see #getPerformers()
     * @generated
     */
    void setPerformers(Performers value);

    /**
     * Returns the value of the '<em><b>Priority</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Priority</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Priority</em>' containment reference.
     * @see #setPriority(Priority)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_Priority()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Priority' namespace='##targetNamespace'"
     * @generated
     */
    Priority getPriority();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getPriority <em>Priority</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Priority</em>' containment reference.
     * @see #getPriority()
     * @generated
     */
    void setPriority(Priority value);

    /**
     * Returns the value of the '<em><b>Deadline</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Deadline}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Deadline</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Deadline</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_Deadline()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Deadline' namespace='##targetNamespace'"
     * @generated
     */
    EList<Deadline> getDeadline();

    /**
     * Returns the value of the '<em><b>Simulation Information</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Simulation Information</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Simulation Information</em>' containment reference.
     * @see #setSimulationInformation(SimulationInformation)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_SimulationInformation()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='SimulationInformation' namespace='##targetNamespace'"
     * @generated
     */
    SimulationInformation getSimulationInformation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getSimulationInformation <em>Simulation Information</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Simulation Information</em>' containment reference.
     * @see #getSimulationInformation()
     * @generated
     */
    void setSimulationInformation(SimulationInformation value);

    /**
     * Returns the value of the '<em><b>Icon</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Icon</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Icon</em>' containment reference.
     * @see #setIcon(Icon)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_Icon()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Icon' namespace='##targetNamespace'"
     * @generated
     */
    Icon getIcon();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getIcon <em>Icon</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Icon</em>' containment reference.
     * @see #getIcon()
     * @generated
     */
    void setIcon(Icon value);

    /**
     * Returns the value of the '<em><b>Documentation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Documentation</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Documentation</em>' containment reference.
     * @see #setDocumentation(Documentation)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_Documentation()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Documentation' namespace='##targetNamespace'"
     * @generated
     */
    Documentation getDocumentation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getDocumentation <em>Documentation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Documentation</em>' containment reference.
     * @see #getDocumentation()
     * @generated
     */
    void setDocumentation(Documentation value);

    /**
     * Returns the value of the '<em><b>Transition Restrictions</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.TransitionRestriction}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Transition Restrictions</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Transition Restrictions</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_TransitionRestrictions()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TransitionRestriction' namespace='##targetNamespace' wrap='TransitionRestrictions'"
     * @generated
     */
    EList<TransitionRestriction> getTransitionRestrictions();

    /**
     * Returns the value of the '<em><b>Input Sets</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.InputSet}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Input Sets</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Input Sets</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_InputSets()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='InputSet' namespace='##targetNamespace' wrap='InputSets'"
     * @generated
     */
    EList<InputSet> getInputSets();

    /**
     * Returns the value of the '<em><b>Output Sets</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Output Sets</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Output Sets</em>' containment reference.
     * @see #setOutputSets(OutputSet)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_OutputSets()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='OutputSet' namespace='##targetNamespace' wrap='OutputSets'"
     * @generated
     */
    OutputSet getOutputSets();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getOutputSets <em>Output Sets</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Output Sets</em>' containment reference.
     * @see #getOutputSets()
     * @generated
     */
    void setOutputSets(OutputSet value);

    /**
     * Returns the value of the '<em><b>Io Rules</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Io Rules</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Io Rules</em>' containment reference.
     * @see #setIoRules(IORules)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_IoRules()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='IORules' namespace='##targetNamespace'"
     * @generated
     */
    IORules getIoRules();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getIoRules <em>Io Rules</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Io Rules</em>' containment reference.
     * @see #getIoRules()
     * @generated
     */
    void setIoRules(IORules value);

    /**
     * Returns the value of the '<em><b>Loop</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Loop</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Loop</em>' containment reference.
     * @see #setLoop(Loop)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_Loop()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Loop' namespace='##targetNamespace'"
     * @generated
     */
    Loop getLoop();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getLoop <em>Loop</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Loop</em>' containment reference.
     * @see #getLoop()
     * @generated
     */
    void setLoop(Loop value);

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_Assignments()
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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_Object()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Object' namespace='##targetNamespace'"
     * @generated
     */
    com.tibco.xpd.xpdl2.Object getObject();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getObject <em>Object</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Object</em>' containment reference.
     * @see #getObject()
     * @generated
     */
    void setObject(com.tibco.xpd.xpdl2.Object value);

    /**
     * Returns the value of the '<em><b>Extensions</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Extensions</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Extensions</em>' containment reference.
     * @see #setExtensions(EObject)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_Extensions()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Extensions' namespace='##targetNamespace'"
     * @generated
     */
    EObject getExtensions();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getExtensions <em>Extensions</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Extensions</em>' containment reference.
     * @see #getExtensions()
     * @generated
     */
    void setExtensions(EObject value);

    /**
     * Returns the value of the '<em><b>Finish Mode</b></em>' attribute.
     * The default value is <code>"Automatic"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.FinishModeType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Finish Mode</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Finish Mode</em>' attribute.
     * @see com.tibco.xpd.xpdl2.FinishModeType
     * @see #isSetFinishMode()
     * @see #unsetFinishMode()
     * @see #setFinishMode(FinishModeType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_FinishMode()
     * @model default="Automatic" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='FinishMode'"
     * @generated
     */
    FinishModeType getFinishMode();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getFinishMode <em>Finish Mode</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Finish Mode</em>' attribute.
     * @see com.tibco.xpd.xpdl2.FinishModeType
     * @see #isSetFinishMode()
     * @see #unsetFinishMode()
     * @see #getFinishMode()
     * @generated
     */
    void setFinishMode(FinishModeType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getFinishMode <em>Finish Mode</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetFinishMode()
     * @see #getFinishMode()
     * @see #setFinishMode(FinishModeType)
     * @generated
     */
    void unsetFinishMode();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Activity#getFinishMode <em>Finish Mode</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Finish Mode</em>' attribute is set.
     * @see #unsetFinishMode()
     * @see #getFinishMode()
     * @see #setFinishMode(FinishModeType)
     * @generated
     */
    boolean isSetFinishMode();

    /**
     * Returns the value of the '<em><b>Is ATransaction</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Is ATransaction</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Is ATransaction</em>' attribute.
     * @see #isSetIsATransaction()
     * @see #unsetIsATransaction()
     * @see #setIsATransaction(boolean)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_IsATransaction()
     * @model default="false" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='IsATransaction'"
     * @generated
     */
    boolean isIsATransaction();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Activity#isIsATransaction <em>Is ATransaction</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Is ATransaction</em>' attribute.
     * @see #isSetIsATransaction()
     * @see #unsetIsATransaction()
     * @see #isIsATransaction()
     * @generated
     */
    void setIsATransaction(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Activity#isIsATransaction <em>Is ATransaction</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetIsATransaction()
     * @see #isIsATransaction()
     * @see #setIsATransaction(boolean)
     * @generated
     */
    void unsetIsATransaction();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Activity#isIsATransaction <em>Is ATransaction</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Is ATransaction</em>' attribute is set.
     * @see #unsetIsATransaction()
     * @see #isIsATransaction()
     * @see #setIsATransaction(boolean)
     * @generated
     */
    boolean isSetIsATransaction();

    /**
     * Returns the value of the '<em><b>Start Activity</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     *  Designates the first activity to be executed when the process is instantiated. Used when there is no other way to determine this Conflicts with BPMN StartEvent and no process definition should use both.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Start Activity</em>' attribute.
     * @see #isSetStartActivity()
     * @see #unsetStartActivity()
     * @see #setStartActivity(boolean)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_StartActivity()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='StartActivity'"
     * @generated
     */
    boolean isStartActivity();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Activity#isStartActivity <em>Start Activity</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Start Activity</em>' attribute.
     * @see #isSetStartActivity()
     * @see #unsetStartActivity()
     * @see #isStartActivity()
     * @generated
     */
    void setStartActivity(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Activity#isStartActivity <em>Start Activity</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetStartActivity()
     * @see #isStartActivity()
     * @see #setStartActivity(boolean)
     * @generated
     */
    void unsetStartActivity();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Activity#isStartActivity <em>Start Activity</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Start Activity</em>' attribute is set.
     * @see #unsetStartActivity()
     * @see #isStartActivity()
     * @see #setStartActivity(boolean)
     * @generated
     */
    boolean isSetStartActivity();

    /**
     * Returns the value of the '<em><b>Start Mode</b></em>' attribute.
     * The default value is <code>"Automatic"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.StartModeType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Start Mode</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Start Mode</em>' attribute.
     * @see com.tibco.xpd.xpdl2.StartModeType
     * @see #isSetStartMode()
     * @see #unsetStartMode()
     * @see #setStartMode(StartModeType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_StartMode()
     * @model default="Automatic" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='StartMode'"
     * @generated
     */
    StartModeType getStartMode();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getStartMode <em>Start Mode</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Start Mode</em>' attribute.
     * @see com.tibco.xpd.xpdl2.StartModeType
     * @see #isSetStartMode()
     * @see #unsetStartMode()
     * @see #getStartMode()
     * @generated
     */
    void setStartMode(StartModeType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getStartMode <em>Start Mode</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetStartMode()
     * @see #getStartMode()
     * @see #setStartMode(StartModeType)
     * @generated
     */
    void unsetStartMode();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Activity#getStartMode <em>Start Mode</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Start Mode</em>' attribute is set.
     * @see #unsetStartMode()
     * @see #getStartMode()
     * @see #setStartMode(StartModeType)
     * @generated
     */
    boolean isSetStartMode();

    /**
     * Returns the value of the '<em><b>Start Quantity</b></em>' attribute.
     * The default value is <code>"1"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Start Quantity</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Start Quantity</em>' attribute.
     * @see #isSetStartQuantity()
     * @see #unsetStartQuantity()
     * @see #setStartQuantity(BigInteger)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_StartQuantity()
     * @model default="1" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Integer"
     *        extendedMetaData="kind='attribute' name='StartQuantity'"
     * @generated
     */
    BigInteger getStartQuantity();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getStartQuantity <em>Start Quantity</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Start Quantity</em>' attribute.
     * @see #isSetStartQuantity()
     * @see #unsetStartQuantity()
     * @see #getStartQuantity()
     * @generated
     */
    void setStartQuantity(BigInteger value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getStartQuantity <em>Start Quantity</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetStartQuantity()
     * @see #getStartQuantity()
     * @see #setStartQuantity(BigInteger)
     * @generated
     */
    void unsetStartQuantity();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Activity#getStartQuantity <em>Start Quantity</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Start Quantity</em>' attribute is set.
     * @see #unsetStartQuantity()
     * @see #getStartQuantity()
     * @see #setStartQuantity(BigInteger)
     * @generated
     */
    boolean isSetStartQuantity();

    /**
     * Returns the value of the '<em><b>Status</b></em>' attribute.
     * The default value is <code>"None"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.StatusType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     *  BPMN: Status values are assigned during execution. Status can be treated as a property and used in expressions local to an Activity. It is unclear that status belongs in the XPDL document.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Status</em>' attribute.
     * @see com.tibco.xpd.xpdl2.StatusType
     * @see #isSetStatus()
     * @see #unsetStatus()
     * @see #setStatus(StatusType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_Status()
     * @model default="None" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='Status'"
     * @generated
     */
    StatusType getStatus();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getStatus <em>Status</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Status</em>' attribute.
     * @see com.tibco.xpd.xpdl2.StatusType
     * @see #isSetStatus()
     * @see #unsetStatus()
     * @see #getStatus()
     * @generated
     */
    void setStatus(StatusType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getStatus <em>Status</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetStatus()
     * @see #getStatus()
     * @see #setStatus(StatusType)
     * @generated
     */
    void unsetStatus();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Activity#getStatus <em>Status</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Status</em>' attribute is set.
     * @see #unsetStatus()
     * @see #getStatus()
     * @see #setStatus(StatusType)
     * @generated
     */
    boolean isSetStatus();

    /**
     * Returns the value of the '<em><b>Flow Container</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.xpdl2.FlowContainer#getActivities <em>Activities</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Flow Container</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Flow Container</em>' container reference.
     * @see #setFlowContainer(FlowContainer)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_FlowContainer()
     * @see com.tibco.xpd.xpdl2.FlowContainer#getActivities
     * @model opposite="activities" transient="false"
     * @generated
     */
    FlowContainer getFlowContainer();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getFlowContainer <em>Flow Container</em>}' container reference.
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
     * @model kind="operation"
     * @generated
     */
    EList<Transition> getOutgoingTransitions();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model kind="operation"
     * @generated
     */
    EList<Transition> getIncomingTransitions();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model kind="operation"
     * @generated
     */
    EList<Performer> getPerformerList();

    /**
     * Returns the value of the '<em><b>Process</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the value of the '<em>Process</em>' reference.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_Process()
     * @model transient="true" changeable="false" derived="true"
     * @generated
     */
    com.tibco.xpd.xpdl2.Process getProcess();

    /**
     * Returns the value of the '<em><b>Is For Compensation</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Is For Compensation</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Is For Compensation</em>' attribute.
     * @see #isSetIsForCompensation()
     * @see #unsetIsForCompensation()
     * @see #setIsForCompensation(boolean)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_IsForCompensation()
     * @model default="false" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='IsForCompensation'"
     * @generated
     */
    boolean isIsForCompensation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Activity#isIsForCompensation <em>Is For Compensation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Is For Compensation</em>' attribute.
     * @see #isSetIsForCompensation()
     * @see #unsetIsForCompensation()
     * @see #isIsForCompensation()
     * @generated
     */
    void setIsForCompensation(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Activity#isIsForCompensation <em>Is For Compensation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetIsForCompensation()
     * @see #isIsForCompensation()
     * @see #setIsForCompensation(boolean)
     * @generated
     */
    void unsetIsForCompensation();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Activity#isIsForCompensation <em>Is For Compensation</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Is For Compensation</em>' attribute is set.
     * @see #unsetIsForCompensation()
     * @see #isIsForCompensation()
     * @see #setIsForCompensation(boolean)
     * @generated
     */
    boolean isSetIsForCompensation();

    /**
     * Returns the value of the '<em><b>Completion Quantity</b></em>' attribute.
     * The default value is <code>"1"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Completion Quantity</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Completion Quantity</em>' attribute.
     * @see #isSetCompletionQuantity()
     * @see #unsetCompletionQuantity()
     * @see #setCompletionQuantity(BigInteger)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getActivity_CompletionQuantity()
     * @model default="1" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Integer"
     *        extendedMetaData="kind='attribute' name='CompletionQuantity'"
     * @generated
     */
    BigInteger getCompletionQuantity();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getCompletionQuantity <em>Completion Quantity</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Completion Quantity</em>' attribute.
     * @see #isSetCompletionQuantity()
     * @see #unsetCompletionQuantity()
     * @see #getCompletionQuantity()
     * @generated
     */
    void setCompletionQuantity(BigInteger value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Activity#getCompletionQuantity <em>Completion Quantity</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetCompletionQuantity()
     * @see #getCompletionQuantity()
     * @see #setCompletionQuantity(BigInteger)
     * @generated
     */
    void unsetCompletionQuantity();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Activity#getCompletionQuantity <em>Completion Quantity</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Completion Quantity</em>' attribute is set.
     * @see #unsetCompletionQuantity()
     * @see #getCompletionQuantity()
     * @see #setCompletionQuantity(BigInteger)
     * @generated
     */
    boolean isSetCompletionQuantity();

} // Activity
