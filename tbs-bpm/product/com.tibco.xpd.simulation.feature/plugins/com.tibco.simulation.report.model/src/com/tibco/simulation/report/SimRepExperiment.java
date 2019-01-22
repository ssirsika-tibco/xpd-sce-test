/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.simulation.report;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Experiment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.simulation.report.SimRepExperiment#getExperimentData <em>Experiment Data</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepExperiment#getCases <em>Cases</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepExperiment#getParticipants <em>Participants</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepExperiment#getActivities <em>Activities</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepExperiment#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepExperiment#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepExperiment#getPackageId <em>Package Id</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepExperiment#getProcessId <em>Process Id</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepExperiment#getProcessName <em>Process Name</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepExperiment#getProcessLabel <em>Process Label</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepExperiment#getPackageName <em>Package Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.simulation.report.SimRepPackage#getSimRepExperiment()
 * @model extendedMetaData="name='ExperimentType' kind='elementOnly'"
 * @generated
 */
public interface SimRepExperiment extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Experiment Data</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Container for the all experiment specific parameters.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Experiment Data</em>' containment reference.
     * @see #setExperimentData(SimRepExperimentData)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepExperiment_ExperimentData()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='ExperimentData' namespace='##targetNamespace'"
     * @generated
     */
    SimRepExperimentData getExperimentData();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepExperiment#getExperimentData <em>Experiment Data</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Experiment Data</em>' containment reference.
     * @see #getExperimentData()
     * @generated
     */
    void setExperimentData(SimRepExperimentData value);

    /**
     * Returns the value of the '<em><b>Cases</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Container for the all cases connected statistics.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Cases</em>' containment reference.
     * @see #setCases(SimRepCases)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepExperiment_Cases()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='Cases' namespace='##targetNamespace'"
     * @generated
     */
    SimRepCases getCases();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepExperiment#getCases <em>Cases</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Cases</em>' containment reference.
     * @see #getCases()
     * @generated
     */
    void setCases(SimRepCases value);

    /**
     * Returns the value of the '<em><b>Participants</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Participants</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Participants</em>' containment reference.
     * @see #setParticipants(SimRepParticipants)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepExperiment_Participants()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='Participants' namespace='##targetNamespace'"
     * @generated
     */
    SimRepParticipants getParticipants();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepExperiment#getParticipants <em>Participants</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Participants</em>' containment reference.
     * @see #getParticipants()
     * @generated
     */
    void setParticipants(SimRepParticipants value);

    /**
     * Returns the value of the '<em><b>Activities</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Activities</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Activities</em>' containment reference.
     * @see #setActivities(SimRepActivities)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepExperiment_Activities()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='Activities' namespace='##targetNamespace'"
     * @generated
     */
    SimRepActivities getActivities();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepExperiment#getActivities <em>Activities</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Activities</em>' containment reference.
     * @see #getActivities()
     * @generated
     */
    void setActivities(SimRepActivities value);

    /**
     * Returns the value of the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Id</em>' attribute.
     * @see #setId(String)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepExperiment_Id()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='Id' namespace='##targetNamespace'"
     * @generated
     */
    String getId();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepExperiment#getId <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Id</em>' attribute.
     * @see #getId()
     * @generated
     */
    void setId(String value);

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepExperiment_Name()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='Name' namespace='##targetNamespace'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepExperiment#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Package Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Package Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Package Id</em>' attribute.
     * @see #setPackageId(String)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepExperiment_PackageId()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='PackageId' namespace='##targetNamespace'"
     * @generated
     */
    String getPackageId();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepExperiment#getPackageId <em>Package Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Package Id</em>' attribute.
     * @see #getPackageId()
     * @generated
     */
    void setPackageId(String value);

    /**
     * Returns the value of the '<em><b>Process Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Process Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Process Id</em>' attribute.
     * @see #setProcessId(String)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepExperiment_ProcessId()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='ProcessId' namespace='##targetNamespace'"
     * @generated
     */
    String getProcessId();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepExperiment#getProcessId <em>Process Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Process Id</em>' attribute.
     * @see #getProcessId()
     * @generated
     */
    void setProcessId(String value);

    /**
     * Returns the value of the '<em><b>Process Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Process Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Process Name</em>' attribute.
     * @see #setProcessName(String)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepExperiment_ProcessName()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='ProcessName' namespace='##targetNamespace'"
     * @generated
     */
    String getProcessName();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepExperiment#getProcessName <em>Process Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Process Name</em>' attribute.
     * @see #getProcessName()
     * @generated
     */
    void setProcessName(String value);

    /**
     * Returns the value of the '<em><b>Process Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Process Label</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Process Label</em>' attribute.
     * @see #setProcessLabel(String)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepExperiment_ProcessLabel()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='ProcessLabel' namespace='##targetNamespace'"
     * @generated
     */
    String getProcessLabel();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepExperiment#getProcessLabel <em>Process Label</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Process Label</em>' attribute.
     * @see #getProcessLabel()
     * @generated
     */
    void setProcessLabel(String value);

    /**
     * Returns the value of the '<em><b>Package Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Package Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Package Name</em>' attribute.
     * @see #setPackageName(String)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepExperiment_PackageName()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='PackageName' namespace='##targetNamespace'"
     * @generated
     */
    String getPackageName();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepExperiment#getPackageName <em>Package Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Package Name</em>' attribute.
     * @see #getPackageName()
     * @generated
     */
    void setPackageName(String value);

} // SimRepExperiment
