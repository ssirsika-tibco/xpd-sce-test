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
 * A representation of the model object '<em><b>Participant</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.simulation.report.SimRepParticipant#getNoOfInstances <em>No Of Instances</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepParticipant#getIdleInstances <em>Idle Instances</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepParticipant#getAverageIdle <em>Average Idle</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepParticipant#getAverageIdleTime <em>Average Idle Time</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepParticipant#getAverageBusyTime <em>Average Busy Time</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepParticipant#getCostOfWorkForCase <em>Cost Of Work For Case</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepParticipant#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepParticipant#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.simulation.report.SimRepPackage#getSimRepParticipant()
 * @model extendedMetaData="name='ParticipantType' kind='elementOnly'"
 * @generated
 */
public interface SimRepParticipant extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>No Of Instances</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Overall number or participant instances.
     * <!-- end-model-doc -->
     * @return the value of the '<em>No Of Instances</em>' attribute.
     * @see #isSetNoOfInstances()
     * @see #unsetNoOfInstances()
     * @see #setNoOfInstances(int)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepParticipant_NoOfInstances()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
     *        extendedMetaData="kind='element' name='NoOfInstances' namespace='##targetNamespace'"
     * @generated
     */
    int getNoOfInstances();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepParticipant#getNoOfInstances <em>No Of Instances</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>No Of Instances</em>' attribute.
     * @see #isSetNoOfInstances()
     * @see #unsetNoOfInstances()
     * @see #getNoOfInstances()
     * @generated
     */
    void setNoOfInstances(int value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepParticipant#getNoOfInstances <em>No Of Instances</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetNoOfInstances()
     * @see #getNoOfInstances()
     * @see #setNoOfInstances(int)
     * @generated
     */
    void unsetNoOfInstances();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepParticipant#getNoOfInstances <em>No Of Instances</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>No Of Instances</em>' attribute is set.
     * @see #unsetNoOfInstances()
     * @see #getNoOfInstances()
     * @see #setNoOfInstances(int)
     * @generated
     */
    boolean isSetNoOfInstances();

    /**
     * Returns the value of the '<em><b>Idle Instances</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Curent number of idle instances.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Idle Instances</em>' attribute.
     * @see #isSetIdleInstances()
     * @see #unsetIdleInstances()
     * @see #setIdleInstances(int)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepParticipant_IdleInstances()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
     *        extendedMetaData="kind='element' name='IdleInstances' namespace='##targetNamespace'"
     * @generated
     */
    int getIdleInstances();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepParticipant#getIdleInstances <em>Idle Instances</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Idle Instances</em>' attribute.
     * @see #isSetIdleInstances()
     * @see #unsetIdleInstances()
     * @see #getIdleInstances()
     * @generated
     */
    void setIdleInstances(int value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepParticipant#getIdleInstances <em>Idle Instances</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetIdleInstances()
     * @see #getIdleInstances()
     * @see #setIdleInstances(int)
     * @generated
     */
    void unsetIdleInstances();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepParticipant#getIdleInstances <em>Idle Instances</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Idle Instances</em>' attribute is set.
     * @see #unsetIdleInstances()
     * @see #getIdleInstances()
     * @see #setIdleInstances(int)
     * @generated
     */
    boolean isSetIdleInstances();

    /**
     * Returns the value of the '<em><b>Average Idle</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Average number of idle participant instances.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Average Idle</em>' attribute.
     * @see #isSetAverageIdle()
     * @see #unsetAverageIdle()
     * @see #setAverageIdle(double)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepParticipant_AverageIdle()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
     *        extendedMetaData="kind='element' name='AverageIdle' namespace='##targetNamespace'"
     * @generated
     */
    double getAverageIdle();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepParticipant#getAverageIdle <em>Average Idle</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Average Idle</em>' attribute.
     * @see #isSetAverageIdle()
     * @see #unsetAverageIdle()
     * @see #getAverageIdle()
     * @generated
     */
    void setAverageIdle(double value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepParticipant#getAverageIdle <em>Average Idle</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetAverageIdle()
     * @see #getAverageIdle()
     * @see #setAverageIdle(double)
     * @generated
     */
    void unsetAverageIdle();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepParticipant#getAverageIdle <em>Average Idle</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Average Idle</em>' attribute is set.
     * @see #unsetAverageIdle()
     * @see #getAverageIdle()
     * @see #setAverageIdle(double)
     * @generated
     */
    boolean isSetAverageIdle();

    /**
     * Returns the value of the '<em><b>Average Idle Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Average vaiting time of participant.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Average Idle Time</em>' attribute.
     * @see #isSetAverageIdleTime()
     * @see #unsetAverageIdleTime()
     * @see #setAverageIdleTime(double)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepParticipant_AverageIdleTime()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
     *        extendedMetaData="kind='element' name='AverageIdleTime' namespace='##targetNamespace'"
     * @generated
     */
    double getAverageIdleTime();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepParticipant#getAverageIdleTime <em>Average Idle Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Average Idle Time</em>' attribute.
     * @see #isSetAverageIdleTime()
     * @see #unsetAverageIdleTime()
     * @see #getAverageIdleTime()
     * @generated
     */
    void setAverageIdleTime(double value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepParticipant#getAverageIdleTime <em>Average Idle Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetAverageIdleTime()
     * @see #getAverageIdleTime()
     * @see #setAverageIdleTime(double)
     * @generated
     */
    void unsetAverageIdleTime();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepParticipant#getAverageIdleTime <em>Average Idle Time</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Average Idle Time</em>' attribute is set.
     * @see #unsetAverageIdleTime()
     * @see #getAverageIdleTime()
     * @see #setAverageIdleTime(double)
     * @generated
     */
    boolean isSetAverageIdleTime();

    /**
     * Returns the value of the '<em><b>Average Busy Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Average busy time of participant.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Average Busy Time</em>' attribute.
     * @see #isSetAverageBusyTime()
     * @see #unsetAverageBusyTime()
     * @see #setAverageBusyTime(double)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepParticipant_AverageBusyTime()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
     *        extendedMetaData="kind='element' name='AverageBusyTime' namespace='##targetNamespace'"
     * @generated
     */
    double getAverageBusyTime();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepParticipant#getAverageBusyTime <em>Average Busy Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Average Busy Time</em>' attribute.
     * @see #isSetAverageBusyTime()
     * @see #unsetAverageBusyTime()
     * @see #getAverageBusyTime()
     * @generated
     */
    void setAverageBusyTime(double value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepParticipant#getAverageBusyTime <em>Average Busy Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetAverageBusyTime()
     * @see #getAverageBusyTime()
     * @see #setAverageBusyTime(double)
     * @generated
     */
    void unsetAverageBusyTime();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepParticipant#getAverageBusyTime <em>Average Busy Time</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Average Busy Time</em>' attribute is set.
     * @see #unsetAverageBusyTime()
     * @see #getAverageBusyTime()
     * @see #setAverageBusyTime(double)
     * @generated
     */
    boolean isSetAverageBusyTime();

    /**
     * Returns the value of the '<em><b>Cost Of Work For Case</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Cost Of Work For Case</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Cost Of Work For Case</em>' containment reference.
     * @see #setCostOfWorkForCase(SimRepCost)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepParticipant_CostOfWorkForCase()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='CostOfWorkForCase' namespace='##targetNamespace'"
     * @generated
     */
    SimRepCost getCostOfWorkForCase();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepParticipant#getCostOfWorkForCase <em>Cost Of Work For Case</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Cost Of Work For Case</em>' containment reference.
     * @see #getCostOfWorkForCase()
     * @generated
     */
    void setCostOfWorkForCase(SimRepCost value);

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
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepParticipant_Id()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='Id' namespace='##targetNamespace'"
     * @generated
     */
    String getId();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepParticipant#getId <em>Id</em>}' attribute.
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
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepParticipant_Name()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='Name' namespace='##targetNamespace'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepParticipant#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

} // SimRepParticipant
