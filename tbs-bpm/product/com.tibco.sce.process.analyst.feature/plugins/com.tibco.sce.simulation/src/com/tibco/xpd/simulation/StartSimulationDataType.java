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
 * A representation of the model object '<em><b>Start Simulation Data Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.StartSimulationDataType#getDuration <em>Duration</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.StartSimulationDataType#getDisplayTimeUnit <em>Display Time Unit</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.StartSimulationDataType#getNumberOfCases <em>Number Of Cases</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.SimulationPackage#getStartSimulationDataType()
 * @model extendedMetaData="name='StartSimulationDataType' kind='elementOnly'"
 * @generated
 */
public interface StartSimulationDataType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Number Of Cases</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Number Of Cases</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Number Of Cases</em>' attribute.
     * @see #isSetNumberOfCases()
     * @see #unsetNumberOfCases()
     * @see #setNumberOfCases(long)
     * @see com.tibco.xpd.simulation.SimulationPackage#getStartSimulationDataType_NumberOfCases()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long"
     *        extendedMetaData="kind='attribute' name='NumberOfCases'"
     * @generated
     */
    long getNumberOfCases();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.StartSimulationDataType#getNumberOfCases <em>Number Of Cases</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Number Of Cases</em>' attribute.
     * @see #isSetNumberOfCases()
     * @see #unsetNumberOfCases()
     * @see #getNumberOfCases()
     * @generated
     */
    void setNumberOfCases(long value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.simulation.StartSimulationDataType#getNumberOfCases <em>Number Of Cases</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetNumberOfCases()
     * @see #getNumberOfCases()
     * @see #setNumberOfCases(long)
     * @generated
     */
    void unsetNumberOfCases();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.simulation.StartSimulationDataType#getNumberOfCases <em>Number Of Cases</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Number Of Cases</em>' attribute is set.
     * @see #unsetNumberOfCases()
     * @see #getNumberOfCases()
     * @see #setNumberOfCases(long)
     * @generated
     */
    boolean isSetNumberOfCases();

    /**
     * Returns the value of the '<em><b>Duration</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Duration</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Duration</em>' containment reference.
     * @see #setDuration(AbstractBasicDistribution)
     * @see com.tibco.xpd.simulation.SimulationPackage#getStartSimulationDataType_Duration()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='Duration' namespace='##targetNamespace' subclass-wrap='Duration'"
     * @generated
     */
    AbstractBasicDistribution getDuration();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.StartSimulationDataType#getDuration <em>Duration</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Duration</em>' containment reference.
     * @see #getDuration()
     * @generated
     */
    void setDuration(AbstractBasicDistribution value);

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
     * @see com.tibco.xpd.simulation.SimulationPackage#getStartSimulationDataType_DisplayTimeUnit()
     * @model default="YEAR" unique="false" unsettable="true" required="true"
     *        extendedMetaData="kind='element' name='DisplayTimeUnit' namespace='##targetNamespace'"
     * @generated
     */
    TimeDisplayUnitType getDisplayTimeUnit();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.StartSimulationDataType#getDisplayTimeUnit <em>Display Time Unit</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.xpd.simulation.StartSimulationDataType#getDisplayTimeUnit <em>Display Time Unit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDisplayTimeUnit()
     * @see #getDisplayTimeUnit()
     * @see #setDisplayTimeUnit(TimeDisplayUnitType)
     * @generated
     */
    void unsetDisplayTimeUnit();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.simulation.StartSimulationDataType#getDisplayTimeUnit <em>Display Time Unit</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Display Time Unit</em>' attribute is set.
     * @see #unsetDisplayTimeUnit()
     * @see #getDisplayTimeUnit()
     * @see #setDisplayTimeUnit(TimeDisplayUnitType)
     * @generated
     */
    boolean isSetDisplayTimeUnit();

} // StartSimulationDataType
