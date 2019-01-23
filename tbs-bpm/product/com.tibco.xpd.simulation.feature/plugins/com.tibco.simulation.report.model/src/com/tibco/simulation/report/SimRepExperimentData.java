/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.simulation.report;

import javax.xml.datatype.XMLGregorianCalendar;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Experiment Data</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.simulation.report.SimRepExperimentData#getExperimentState <em>Experiment State</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepExperimentData#getSimulationTime <em>Simulation Time</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepExperimentData#getReferenceTimeUnit <em>Reference Time Unit</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepExperimentData#getReferenceStartTime <em>Reference Start Time</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepExperimentData#getRealTime <em>Real Time</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepExperimentData#getReferenceCostUnit <em>Reference Cost Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.simulation.report.SimRepPackage#getSimRepExperimentData()
 * @model extendedMetaData="name='ExperimentDataType' kind='elementOnly'"
 * @generated
 */
public interface SimRepExperimentData extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Experiment State</b></em>' attribute.
     * The default value is <code>"NOT_STARTED"</code>.
     * The literals are from the enumeration {@link com.tibco.simulation.report.SimRepExperimentState}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Experiment State</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Experiment State</em>' attribute.
     * @see com.tibco.simulation.report.SimRepExperimentState
     * @see #isSetExperimentState()
     * @see #unsetExperimentState()
     * @see #setExperimentState(SimRepExperimentState)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepExperimentData_ExperimentState()
     * @model default="NOT_STARTED" unique="false" unsettable="true" required="true"
     *        extendedMetaData="kind='element' name='ExperimentState' namespace='##targetNamespace'"
     * @generated
     */
    SimRepExperimentState getExperimentState();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepExperimentData#getExperimentState <em>Experiment State</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Experiment State</em>' attribute.
     * @see com.tibco.simulation.report.SimRepExperimentState
     * @see #isSetExperimentState()
     * @see #unsetExperimentState()
     * @see #getExperimentState()
     * @generated
     */
    void setExperimentState(SimRepExperimentState value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepExperimentData#getExperimentState <em>Experiment State</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetExperimentState()
     * @see #getExperimentState()
     * @see #setExperimentState(SimRepExperimentState)
     * @generated
     */
    void unsetExperimentState();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepExperimentData#getExperimentState <em>Experiment State</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Experiment State</em>' attribute is set.
     * @see #unsetExperimentState()
     * @see #getExperimentState()
     * @see #setExperimentState(SimRepExperimentState)
     * @generated
     */
    boolean isSetExperimentState();

    /**
     * Returns the value of the '<em><b>Simulation Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Unit independent double value abstract simulation time. Every other time dependent element is based on this time.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Simulation Time</em>' attribute.
     * @see #isSetSimulationTime()
     * @see #unsetSimulationTime()
     * @see #setSimulationTime(double)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepExperimentData_SimulationTime()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
     *        extendedMetaData="kind='element' name='SimulationTime' namespace='##targetNamespace'"
     * @generated
     */
    double getSimulationTime();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepExperimentData#getSimulationTime <em>Simulation Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Simulation Time</em>' attribute.
     * @see #isSetSimulationTime()
     * @see #unsetSimulationTime()
     * @see #getSimulationTime()
     * @generated
     */
    void setSimulationTime(double value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepExperimentData#getSimulationTime <em>Simulation Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetSimulationTime()
     * @see #getSimulationTime()
     * @see #setSimulationTime(double)
     * @generated
     */
    void unsetSimulationTime();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepExperimentData#getSimulationTime <em>Simulation Time</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Simulation Time</em>' attribute is set.
     * @see #unsetSimulationTime()
     * @see #getSimulationTime()
     * @see #setSimulationTime(double)
     * @generated
     */
    boolean isSetSimulationTime();

    /**
     * Returns the value of the '<em><b>Reference Time Unit</b></em>' attribute.
     * The default value is <code>"SECOND"</code>.
     * The literals are from the enumeration {@link com.tibco.simulation.report.SimRepReferenceTimeUnit}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * This is time unit which adds meaning to simulation time. It says what is one unit of simulation time.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Reference Time Unit</em>' attribute.
     * @see com.tibco.simulation.report.SimRepReferenceTimeUnit
     * @see #isSetReferenceTimeUnit()
     * @see #unsetReferenceTimeUnit()
     * @see #setReferenceTimeUnit(SimRepReferenceTimeUnit)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepExperimentData_ReferenceTimeUnit()
     * @model default="SECOND" unique="false" unsettable="true" required="true"
     *        extendedMetaData="kind='element' name='ReferenceTimeUnit' namespace='##targetNamespace'"
     * @generated
     */
    SimRepReferenceTimeUnit getReferenceTimeUnit();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepExperimentData#getReferenceTimeUnit <em>Reference Time Unit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Reference Time Unit</em>' attribute.
     * @see com.tibco.simulation.report.SimRepReferenceTimeUnit
     * @see #isSetReferenceTimeUnit()
     * @see #unsetReferenceTimeUnit()
     * @see #getReferenceTimeUnit()
     * @generated
     */
    void setReferenceTimeUnit(SimRepReferenceTimeUnit value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepExperimentData#getReferenceTimeUnit <em>Reference Time Unit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetReferenceTimeUnit()
     * @see #getReferenceTimeUnit()
     * @see #setReferenceTimeUnit(SimRepReferenceTimeUnit)
     * @generated
     */
    void unsetReferenceTimeUnit();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepExperimentData#getReferenceTimeUnit <em>Reference Time Unit</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Reference Time Unit</em>' attribute is set.
     * @see #unsetReferenceTimeUnit()
     * @see #getReferenceTimeUnit()
     * @see #setReferenceTimeUnit(SimRepReferenceTimeUnit)
     * @generated
     */
    boolean isSetReferenceTimeUnit();

    /**
     * Returns the value of the '<em><b>Reference Start Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Point in real time from which simulation real time should start.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Reference Start Time</em>' attribute.
     * @see #setReferenceStartTime(String)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepExperimentData_ReferenceStartTime()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='ReferenceStartTime' namespace='##targetNamespace'"
     * @generated
     */
    String getReferenceStartTime();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepExperimentData#getReferenceStartTime <em>Reference Start Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Reference Start Time</em>' attribute.
     * @see #getReferenceStartTime()
     * @generated
     */
    void setReferenceStartTime(String value);

    /**
     * Returns the value of the '<em><b>Real Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Shows the real time simulation time based on SimulationTime, ReferenceTimeUnit and ReferenceStartTime
     * <!-- end-model-doc -->
     * @return the value of the '<em>Real Time</em>' attribute.
     * @see #setRealTime(String)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepExperimentData_RealTime()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='RealTime' namespace='##targetNamespace'"
     * @generated
     */
    String getRealTime();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepExperimentData#getRealTime <em>Real Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Real Time</em>' attribute.
     * @see #getRealTime()
     * @generated
     */
    void setRealTime(String value);

    /**
     * Returns the value of the '<em><b>Reference Cost Unit</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Unit of cost for whole experiment (for example $ or GBP). It ads the meaning to all cost related statistic values.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Reference Cost Unit</em>' attribute.
     * @see #setReferenceCostUnit(String)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepExperimentData_ReferenceCostUnit()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='ReferenceCostUnit' namespace='##targetNamespace'"
     * @generated
     */
    String getReferenceCostUnit();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepExperimentData#getReferenceCostUnit <em>Reference Cost Unit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Reference Cost Unit</em>' attribute.
     * @see #getReferenceCostUnit()
     * @generated
     */
    void setReferenceCostUnit(String value);

} // SimRepExperimentData
