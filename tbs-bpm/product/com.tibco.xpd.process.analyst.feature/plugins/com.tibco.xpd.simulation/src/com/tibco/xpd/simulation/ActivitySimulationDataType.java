/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Activity Simulation Data Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.ActivitySimulationDataType#getDuration <em>Duration</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.ActivitySimulationDataType#getDisplayTimeUnit <em>Display Time Unit</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.ActivitySimulationDataType#getLoopControl <em>Loop Control</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.ActivitySimulationDataType#getSlaMaximumDelay <em>Sla Maximum Delay</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.SimulationPackage#getActivitySimulationDataType()
 * @model extendedMetaData="name='ActivitySimulationDataType' kind='elementOnly'"
 * @generated
 */
public interface ActivitySimulationDataType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Duration</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Duration</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Duration</em>' containment reference.
     * @see #setDuration(SimulationRealDistributionType)
     * @see com.tibco.xpd.simulation.SimulationPackage#getActivitySimulationDataType_Duration()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='Duration' namespace='##targetNamespace'"
     * @generated
     */
    SimulationRealDistributionType getDuration();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.ActivitySimulationDataType#getDuration <em>Duration</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Duration</em>' containment reference.
     * @see #getDuration()
     * @generated
     */
    void setDuration(SimulationRealDistributionType value);

    /**
     * Returns the value of the '<em><b>Display Time Unit</b></em>' attribute.
     * The default value is <code>"YEAR"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.simulation.TimeDisplayUnitType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Display Time Unit</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Display Time Unit</em>' attribute.
     * @see com.tibco.xpd.simulation.TimeDisplayUnitType
     * @see #isSetDisplayTimeUnit()
     * @see #unsetDisplayTimeUnit()
     * @see #setDisplayTimeUnit(TimeDisplayUnitType)
     * @see com.tibco.xpd.simulation.SimulationPackage#getActivitySimulationDataType_DisplayTimeUnit()
     * @model default="YEAR" unique="false" unsettable="true" required="true"
     *        extendedMetaData="kind='element' name='DisplayTimeUnit' namespace='##targetNamespace'"
     * @generated
     */
    TimeDisplayUnitType getDisplayTimeUnit();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.ActivitySimulationDataType#getDisplayTimeUnit <em>Display Time Unit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Display Time Unit</em>' attribute.
     * @see com.tibco.xpd.simulation.TimeDisplayUnitType
     * @see #isSetDisplayTimeUnit()
     * @see #unsetDisplayTimeUnit()
     * @see #getDisplayTimeUnit()
     * @generated
     */
    void setDisplayTimeUnit(TimeDisplayUnitType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.simulation.ActivitySimulationDataType#getDisplayTimeUnit <em>Display Time Unit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDisplayTimeUnit()
     * @see #getDisplayTimeUnit()
     * @see #setDisplayTimeUnit(TimeDisplayUnitType)
     * @generated
     */
    void unsetDisplayTimeUnit();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.simulation.ActivitySimulationDataType#getDisplayTimeUnit <em>Display Time Unit</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Display Time Unit</em>' attribute is set.
     * @see #unsetDisplayTimeUnit()
     * @see #getDisplayTimeUnit()
     * @see #setDisplayTimeUnit(TimeDisplayUnitType)
     * @generated
     */
    boolean isSetDisplayTimeUnit();

    /**
     * Returns the value of the '<em><b>Loop Control</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Loop Control</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Loop Control</em>' containment reference.
     * @see #setLoopControl(LoopControlType)
     * @see com.tibco.xpd.simulation.SimulationPackage#getActivitySimulationDataType_LoopControl()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='LoopControl' namespace='##targetNamespace'"
     * @generated
     */
    LoopControlType getLoopControl();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.ActivitySimulationDataType#getLoopControl <em>Loop Control</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Loop Control</em>' containment reference.
     * @see #getLoopControl()
     * @generated
     */
    void setLoopControl(LoopControlType value);

    /**
     * Returns the value of the '<em><b>Sla Maximum Delay</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Sla Maximum Delay</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Sla Maximum Delay</em>' attribute.
     * @see #setSlaMaximumDelay(Double)
     * @see com.tibco.xpd.simulation.SimulationPackage#getActivitySimulationDataType_SlaMaximumDelay()
     * @model dataType="org.eclipse.emf.ecore.xml.type.DoubleObject"
     *        extendedMetaData="kind='attribute' name='SlaMaximumDelay'"
     * @generated
     */
    Double getSlaMaximumDelay();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.ActivitySimulationDataType#getSlaMaximumDelay <em>Sla Maximum Delay</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Sla Maximum Delay</em>' attribute.
     * @see #getSlaMaximumDelay()
     * @generated
     */
    void setSlaMaximumDelay(Double value);

} // ActivitySimulationDataType
