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
 * A representation of the model object '<em><b>Time Unit Cost Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.TimeUnitCostType#getCost <em>Cost</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.TimeUnitCostType#getTimeDisplayUnit <em>Time Display Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.SimulationPackage#getTimeUnitCostType()
 * @model extendedMetaData="name='TimeUnitCostType' kind='elementOnly'"
 * @generated
 */
public interface TimeUnitCostType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Cost</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     * 						Unit cost are in the same currency for whole
     * 						package.
     * 					
     * <!-- end-model-doc -->
     * @return the value of the '<em>Cost</em>' attribute.
     * @see #isSetCost()
     * @see #unsetCost()
     * @see #setCost(double)
     * @see com.tibco.xpd.simulation.SimulationPackage#getTimeUnitCostType_Cost()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
     *        extendedMetaData="kind='element' name='Cost' namespace='##targetNamespace'"
     * @generated
     */
    double getCost();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.TimeUnitCostType#getCost <em>Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Cost</em>' attribute.
     * @see #isSetCost()
     * @see #unsetCost()
     * @see #getCost()
     * @generated
     */
    void setCost(double value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.simulation.TimeUnitCostType#getCost <em>Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetCost()
     * @see #getCost()
     * @see #setCost(double)
     * @generated
     */
    void unsetCost();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.simulation.TimeUnitCostType#getCost <em>Cost</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Cost</em>' attribute is set.
     * @see #unsetCost()
     * @see #getCost()
     * @see #setCost(double)
     * @generated
     */
    boolean isSetCost();

    /**
     * Returns the value of the '<em><b>Time Display Unit</b></em>' attribute.
     * The default value is <code>"YEAR"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.simulation.TimeDisplayUnitType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Time Display Unit</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Time Display Unit</em>' attribute.
     * @see com.tibco.xpd.simulation.TimeDisplayUnitType
     * @see #isSetTimeDisplayUnit()
     * @see #unsetTimeDisplayUnit()
     * @see #setTimeDisplayUnit(TimeDisplayUnitType)
     * @see com.tibco.xpd.simulation.SimulationPackage#getTimeUnitCostType_TimeDisplayUnit()
     * @model default="YEAR" unique="false" unsettable="true" required="true"
     *        extendedMetaData="kind='element' name='TimeDisplayUnit' namespace='##targetNamespace'"
     * @generated
     */
    TimeDisplayUnitType getTimeDisplayUnit();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.TimeUnitCostType#getTimeDisplayUnit <em>Time Display Unit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Time Display Unit</em>' attribute.
     * @see com.tibco.xpd.simulation.TimeDisplayUnitType
     * @see #isSetTimeDisplayUnit()
     * @see #unsetTimeDisplayUnit()
     * @see #getTimeDisplayUnit()
     * @generated
     */
    void setTimeDisplayUnit(TimeDisplayUnitType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.simulation.TimeUnitCostType#getTimeDisplayUnit <em>Time Display Unit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetTimeDisplayUnit()
     * @see #getTimeDisplayUnit()
     * @see #setTimeDisplayUnit(TimeDisplayUnitType)
     * @generated
     */
    void unsetTimeDisplayUnit();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.simulation.TimeUnitCostType#getTimeDisplayUnit <em>Time Display Unit</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Time Display Unit</em>' attribute is set.
     * @see #unsetTimeDisplayUnit()
     * @see #getTimeDisplayUnit()
     * @see #setTimeDisplayUnit(TimeDisplayUnitType)
     * @generated
     */
    boolean isSetTimeDisplayUnit();

} // TimeUnitCostType
