/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Max Elapse Time Strategy Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.MaxElapseTimeStrategyType#getDisplayTimeUnit <em>Display Time Unit</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.MaxElapseTimeStrategyType#getMaxElapseTime <em>Max Elapse Time</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.SimulationPackage#getMaxElapseTimeStrategyType()
 * @model extendedMetaData="name='MaxElapseTimeStrategyType' kind='elementOnly'"
 * @generated
 */
public interface MaxElapseTimeStrategyType extends LoopControlTransitionType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

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
     * @see com.tibco.xpd.simulation.SimulationPackage#getMaxElapseTimeStrategyType_DisplayTimeUnit()
     * @model default="YEAR" unique="false" unsettable="true" required="true"
     *        extendedMetaData="kind='element' name='DisplayTimeUnit' namespace='##targetNamespace'"
     * @generated
     */
    TimeDisplayUnitType getDisplayTimeUnit();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.MaxElapseTimeStrategyType#getDisplayTimeUnit <em>Display Time Unit</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.xpd.simulation.MaxElapseTimeStrategyType#getDisplayTimeUnit <em>Display Time Unit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDisplayTimeUnit()
     * @see #getDisplayTimeUnit()
     * @see #setDisplayTimeUnit(TimeDisplayUnitType)
     * @generated
     */
    void unsetDisplayTimeUnit();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.simulation.MaxElapseTimeStrategyType#getDisplayTimeUnit <em>Display Time Unit</em>}' attribute is set.
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
     * Returns the value of the '<em><b>Max Elapse Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Max Elapse Time</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Max Elapse Time</em>' attribute.
     * @see #isSetMaxElapseTime()
     * @see #unsetMaxElapseTime()
     * @see #setMaxElapseTime(double)
     * @see com.tibco.xpd.simulation.SimulationPackage#getMaxElapseTimeStrategyType_MaxElapseTime()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
     *        extendedMetaData="kind='element' name='MaxElapseTime' namespace='##targetNamespace'"
     * @generated
     */
    double getMaxElapseTime();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.MaxElapseTimeStrategyType#getMaxElapseTime <em>Max Elapse Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Max Elapse Time</em>' attribute.
     * @see #isSetMaxElapseTime()
     * @see #unsetMaxElapseTime()
     * @see #getMaxElapseTime()
     * @generated
     */
    void setMaxElapseTime(double value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.simulation.MaxElapseTimeStrategyType#getMaxElapseTime <em>Max Elapse Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetMaxElapseTime()
     * @see #getMaxElapseTime()
     * @see #setMaxElapseTime(double)
     * @generated
     */
    void unsetMaxElapseTime();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.simulation.MaxElapseTimeStrategyType#getMaxElapseTime <em>Max Elapse Time</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Max Elapse Time</em>' attribute is set.
     * @see #unsetMaxElapseTime()
     * @see #getMaxElapseTime()
     * @see #setMaxElapseTime(double)
     * @generated
     */
    boolean isSetMaxElapseTime();

} // MaxElapseTimeStrategyType