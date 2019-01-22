/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation;

import java.math.BigInteger;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Participant Simulation Data Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 				Participant in this context is the ROLE not the
 * 				individual. Perhaps later the shift and holiday
 * 				information?
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.ParticipantSimulationDataType#getInstances <em>Instances</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.ParticipantSimulationDataType#getTimeUnitCost <em>Time Unit Cost</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.ParticipantSimulationDataType#getSlaMinimumUtilisation <em>Sla Minimum Utilisation</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.ParticipantSimulationDataType#getSlaMaximumUtilisation <em>Sla Maximum Utilisation</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.SimulationPackage#getParticipantSimulationDataType()
 * @model extendedMetaData="name='ParticipantSimulationDataType' kind='elementOnly'"
 * @generated
 */
public interface ParticipantSimulationDataType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Instances</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     * 						Describes the number of Available Participants.
     * 					
     * <!-- end-model-doc -->
     * @return the value of the '<em>Instances</em>' attribute.
     * @see #setInstances(BigInteger)
     * @see com.tibco.xpd.simulation.SimulationPackage#getParticipantSimulationDataType_Instances()
     * @model unique="false" dataType="com.tibco.xpd.simulation.InstancesType" required="true"
     *        extendedMetaData="kind='element' name='Instances' namespace='##targetNamespace'"
     * @generated
     */
    BigInteger getInstances();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.ParticipantSimulationDataType#getInstances <em>Instances</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Instances</em>' attribute.
     * @see #getInstances()
     * @generated
     */
    void setInstances(BigInteger value);

    /**
     * Returns the value of the '<em><b>Time Unit Cost</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Time Unit Cost</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Time Unit Cost</em>' containment reference.
     * @see #setTimeUnitCost(TimeUnitCostType)
     * @see com.tibco.xpd.simulation.SimulationPackage#getParticipantSimulationDataType_TimeUnitCost()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='TimeUnitCost' namespace='##targetNamespace'"
     * @generated
     */
    TimeUnitCostType getTimeUnitCost();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.ParticipantSimulationDataType#getTimeUnitCost <em>Time Unit Cost</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Time Unit Cost</em>' containment reference.
     * @see #getTimeUnitCost()
     * @generated
     */
    void setTimeUnitCost(TimeUnitCostType value);

    /**
     * Returns the value of the '<em><b>Sla Minimum Utilisation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Sla Minimum Utilisation</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Sla Minimum Utilisation</em>' attribute.
     * @see #setSlaMinimumUtilisation(Double)
     * @see com.tibco.xpd.simulation.SimulationPackage#getParticipantSimulationDataType_SlaMinimumUtilisation()
     * @model dataType="org.eclipse.emf.ecore.xml.type.DoubleObject"
     *        extendedMetaData="kind='attribute' name='SlaMinimumUtilisation'"
     * @generated
     */
    Double getSlaMinimumUtilisation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.ParticipantSimulationDataType#getSlaMinimumUtilisation <em>Sla Minimum Utilisation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Sla Minimum Utilisation</em>' attribute.
     * @see #getSlaMinimumUtilisation()
     * @generated
     */
    void setSlaMinimumUtilisation(Double value);

    /**
     * Returns the value of the '<em><b>Sla Maximum Utilisation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Sla Maximum Utilisation</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Sla Maximum Utilisation</em>' attribute.
     * @see #setSlaMaximumUtilisation(Double)
     * @see com.tibco.xpd.simulation.SimulationPackage#getParticipantSimulationDataType_SlaMaximumUtilisation()
     * @model dataType="org.eclipse.emf.ecore.xml.type.DoubleObject"
     *        extendedMetaData="kind='attribute' name='SlaMaximumUtilisation'"
     * @generated
     */
    Double getSlaMaximumUtilisation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.ParticipantSimulationDataType#getSlaMaximumUtilisation <em>Sla Maximum Utilisation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Sla Maximum Utilisation</em>' attribute.
     * @see #getSlaMaximumUtilisation()
     * @generated
     */
    void setSlaMaximumUtilisation(Double value);

} // ParticipantSimulationDataType
