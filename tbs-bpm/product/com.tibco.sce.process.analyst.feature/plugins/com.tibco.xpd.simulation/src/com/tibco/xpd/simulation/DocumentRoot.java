/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.DocumentRoot#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.DocumentRoot#getActivitySimulationData <em>Activity Simulation Data</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.DocumentRoot#getParticipantSimulationData <em>Participant Simulation Data</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.DocumentRoot#getSplitSimulationData <em>Split Simulation Data</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.DocumentRoot#getStartSimulationData <em>Start Simulation Data</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.DocumentRoot#getTransitionSimulationData <em>Transition Simulation Data</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.DocumentRoot#getWorkflowProcessSimulationData <em>Workflow Process Simulation Data</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.SimulationPackage#getDocumentRoot()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface DocumentRoot extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Mixed</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Mixed</em>' attribute list.
     * @see com.tibco.xpd.simulation.SimulationPackage#getDocumentRoot_Mixed()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='elementWildcard' name=':mixed'"
     * @generated
     */
    FeatureMap getMixed();

    /**
     * Returns the value of the '<em><b>XMLNS Prefix Map</b></em>' map.
     * The key is of type {@link java.lang.String},
     * and the value is of type {@link java.lang.String},
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>XMLNS Prefix Map</em>' map isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>XMLNS Prefix Map</em>' map.
     * @see com.tibco.xpd.simulation.SimulationPackage#getDocumentRoot_XMLNSPrefixMap()
     * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
     *        extendedMetaData="kind='attribute' name='xmlns:prefix'"
     * @generated
     */
    EMap<String, String> getXMLNSPrefixMap();

    /**
     * Returns the value of the '<em><b>XSI Schema Location</b></em>' map.
     * The key is of type {@link java.lang.String},
     * and the value is of type {@link java.lang.String},
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>XSI Schema Location</em>' map isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>XSI Schema Location</em>' map.
     * @see com.tibco.xpd.simulation.SimulationPackage#getDocumentRoot_XSISchemaLocation()
     * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
     *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
     * @generated
     */
    EMap<String, String> getXSISchemaLocation();

    /**
     * Returns the value of the '<em><b>Activity Simulation Data</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Activity Simulation Data</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Activity Simulation Data</em>' containment reference.
     * @see #setActivitySimulationData(ActivitySimulationDataType)
     * @see com.tibco.xpd.simulation.SimulationPackage#getDocumentRoot_ActivitySimulationData()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='ActivitySimulationData' namespace='##targetNamespace'"
     * @generated
     */
    ActivitySimulationDataType getActivitySimulationData();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.DocumentRoot#getActivitySimulationData <em>Activity Simulation Data</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Activity Simulation Data</em>' containment reference.
     * @see #getActivitySimulationData()
     * @generated
     */
    void setActivitySimulationData(ActivitySimulationDataType value);

    /**
     * Returns the value of the '<em><b>Participant Simulation Data</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     * 				Attached as extended attribute of both Package and
     * 				Process level Participants
     * 			
     * <!-- end-model-doc -->
     * @return the value of the '<em>Participant Simulation Data</em>' containment reference.
     * @see #setParticipantSimulationData(ParticipantSimulationDataType)
     * @see com.tibco.xpd.simulation.SimulationPackage#getDocumentRoot_ParticipantSimulationData()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='ParticipantSimulationData' namespace='##targetNamespace'"
     * @generated
     */
    ParticipantSimulationDataType getParticipantSimulationData();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.DocumentRoot#getParticipantSimulationData <em>Participant Simulation Data</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Participant Simulation Data</em>' containment reference.
     * @see #getParticipantSimulationData()
     * @generated
     */
    void setParticipantSimulationData(ParticipantSimulationDataType value);

    /**
     * Returns the value of the '<em><b>Split Simulation Data</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Split Simulation Data</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Split Simulation Data</em>' containment reference.
     * @see #setSplitSimulationData(SplitSimulationDataType)
     * @see com.tibco.xpd.simulation.SimulationPackage#getDocumentRoot_SplitSimulationData()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='SplitSimulationData' namespace='##targetNamespace'"
     * @generated
     */
    SplitSimulationDataType getSplitSimulationData();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.DocumentRoot#getSplitSimulationData <em>Split Simulation Data</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Split Simulation Data</em>' containment reference.
     * @see #getSplitSimulationData()
     * @generated
     */
    void setSplitSimulationData(SplitSimulationDataType value);

    /**
     * Returns the value of the '<em><b>Workflow Process Simulation Data</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     * 				Attached as an extended attribute to WorkflowProcess
     * 			
     * <!-- end-model-doc -->
     * @return the value of the '<em>Workflow Process Simulation Data</em>' containment reference.
     * @see #setWorkflowProcessSimulationData(WorkflowProcessSimulationDataType)
     * @see com.tibco.xpd.simulation.SimulationPackage#getDocumentRoot_WorkflowProcessSimulationData()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='WorkflowProcessSimulationData' namespace='##targetNamespace'"
     * @generated
     */
    WorkflowProcessSimulationDataType getWorkflowProcessSimulationData();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.DocumentRoot#getWorkflowProcessSimulationData <em>Workflow Process Simulation Data</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Workflow Process Simulation Data</em>' containment reference.
     * @see #getWorkflowProcessSimulationData()
     * @generated
     */
    void setWorkflowProcessSimulationData(
            WorkflowProcessSimulationDataType value);

    /**
     * Returns the value of the '<em><b>Start Simulation Data</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Start Simulation Data</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Start Simulation Data</em>' containment reference.
     * @see #setStartSimulationData(StartSimulationDataType)
     * @see com.tibco.xpd.simulation.SimulationPackage#getDocumentRoot_StartSimulationData()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='StartSimulationData' namespace='##targetNamespace'"
     * @generated
     */
    StartSimulationDataType getStartSimulationData();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.DocumentRoot#getStartSimulationData <em>Start Simulation Data</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Start Simulation Data</em>' containment reference.
     * @see #getStartSimulationData()
     * @generated
     */
    void setStartSimulationData(StartSimulationDataType value);

    /**
     * Returns the value of the '<em><b>Transition Simulation Data</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Transition Simulation Data</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Transition Simulation Data</em>' containment reference.
     * @see #setTransitionSimulationData(TransitionSimulationDataType)
     * @see com.tibco.xpd.simulation.SimulationPackage#getDocumentRoot_TransitionSimulationData()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='TransitionSimulationData' namespace='##targetNamespace'"
     * @generated
     */
    TransitionSimulationDataType getTransitionSimulationData();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.DocumentRoot#getTransitionSimulationData <em>Transition Simulation Data</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Transition Simulation Data</em>' containment reference.
     * @see #getTransitionSimulationData()
     * @generated
     */
    void setTransitionSimulationData(TransitionSimulationDataType value);

} // DocumentRoot
