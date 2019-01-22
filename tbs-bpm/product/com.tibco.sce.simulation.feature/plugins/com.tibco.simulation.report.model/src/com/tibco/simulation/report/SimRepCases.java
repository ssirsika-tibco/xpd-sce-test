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
 * A representation of the model object '<em><b>Cases</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.simulation.report.SimRepCases#getCaseStartIntervalDistribution <em>Case Start Interval Distribution</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepCases#getStartedCases <em>Started Cases</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepCases#getFinishedCases <em>Finished Cases</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepCases#getAverageCaseTime <em>Average Case Time</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepCases#getMinCaseTime <em>Min Case Time</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepCases#getMaxCaseTime <em>Max Case Time</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepCases#getCaseCost <em>Case Cost</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.simulation.report.SimRepPackage#getSimRepCases()
 * @model extendedMetaData="name='CasesType' kind='elementOnly'"
 * @generated
 */
public interface SimRepCases extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Case Start Interval Distribution</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Describes how frequently cases are started during the experiment.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Case Start Interval Distribution</em>' containment reference.
     * @see #setCaseStartIntervalDistribution(SimRepDistribution)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepCases_CaseStartIntervalDistribution()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='CaseStartIntervalDistribution' namespace='##targetNamespace'"
     * @generated
     */
    SimRepDistribution getCaseStartIntervalDistribution();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepCases#getCaseStartIntervalDistribution <em>Case Start Interval Distribution</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Case Start Interval Distribution</em>' containment reference.
     * @see #getCaseStartIntervalDistribution()
     * @generated
     */
    void setCaseStartIntervalDistribution(SimRepDistribution value);

    /**
     * Returns the value of the '<em><b>Started Cases</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Current number of started cases.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Started Cases</em>' attribute.
     * @see #isSetStartedCases()
     * @see #unsetStartedCases()
     * @see #setStartedCases(int)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepCases_StartedCases()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
     *        extendedMetaData="kind='element' name='StartedCases' namespace='##targetNamespace'"
     * @generated
     */
    int getStartedCases();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepCases#getStartedCases <em>Started Cases</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Started Cases</em>' attribute.
     * @see #isSetStartedCases()
     * @see #unsetStartedCases()
     * @see #getStartedCases()
     * @generated
     */
    void setStartedCases(int value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepCases#getStartedCases <em>Started Cases</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetStartedCases()
     * @see #getStartedCases()
     * @see #setStartedCases(int)
     * @generated
     */
    void unsetStartedCases();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepCases#getStartedCases <em>Started Cases</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Started Cases</em>' attribute is set.
     * @see #unsetStartedCases()
     * @see #getStartedCases()
     * @see #setStartedCases(int)
     * @generated
     */
    boolean isSetStartedCases();

    /**
     * Returns the value of the '<em><b>Finished Cases</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Current number of finished cases.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Finished Cases</em>' attribute.
     * @see #isSetFinishedCases()
     * @see #unsetFinishedCases()
     * @see #setFinishedCases(int)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepCases_FinishedCases()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
     *        extendedMetaData="kind='element' name='FinishedCases' namespace='##targetNamespace'"
     * @generated
     */
    int getFinishedCases();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepCases#getFinishedCases <em>Finished Cases</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Finished Cases</em>' attribute.
     * @see #isSetFinishedCases()
     * @see #unsetFinishedCases()
     * @see #getFinishedCases()
     * @generated
     */
    void setFinishedCases(int value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepCases#getFinishedCases <em>Finished Cases</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetFinishedCases()
     * @see #getFinishedCases()
     * @see #setFinishedCases(int)
     * @generated
     */
    void unsetFinishedCases();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepCases#getFinishedCases <em>Finished Cases</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Finished Cases</em>' attribute is set.
     * @see #unsetFinishedCases()
     * @see #getFinishedCases()
     * @see #setFinishedCases(int)
     * @generated
     */
    boolean isSetFinishedCases();

    /**
     * Returns the value of the '<em><b>Average Case Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * What was the average time of processing the case.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Average Case Time</em>' attribute.
     * @see #isSetAverageCaseTime()
     * @see #unsetAverageCaseTime()
     * @see #setAverageCaseTime(double)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepCases_AverageCaseTime()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
     *        extendedMetaData="kind='element' name='AverageCaseTime' namespace='##targetNamespace'"
     * @generated
     */
    double getAverageCaseTime();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepCases#getAverageCaseTime <em>Average Case Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Average Case Time</em>' attribute.
     * @see #isSetAverageCaseTime()
     * @see #unsetAverageCaseTime()
     * @see #getAverageCaseTime()
     * @generated
     */
    void setAverageCaseTime(double value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepCases#getAverageCaseTime <em>Average Case Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetAverageCaseTime()
     * @see #getAverageCaseTime()
     * @see #setAverageCaseTime(double)
     * @generated
     */
    void unsetAverageCaseTime();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepCases#getAverageCaseTime <em>Average Case Time</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Average Case Time</em>' attribute is set.
     * @see #unsetAverageCaseTime()
     * @see #getAverageCaseTime()
     * @see #setAverageCaseTime(double)
     * @generated
     */
    boolean isSetAverageCaseTime();

    /**
     * Returns the value of the '<em><b>Min Case Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * What was the shortest time of processing the case.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Min Case Time</em>' attribute.
     * @see #isSetMinCaseTime()
     * @see #unsetMinCaseTime()
     * @see #setMinCaseTime(double)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepCases_MinCaseTime()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
     *        extendedMetaData="kind='element' name='MinCaseTime' namespace='##targetNamespace'"
     * @generated
     */
    double getMinCaseTime();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepCases#getMinCaseTime <em>Min Case Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Min Case Time</em>' attribute.
     * @see #isSetMinCaseTime()
     * @see #unsetMinCaseTime()
     * @see #getMinCaseTime()
     * @generated
     */
    void setMinCaseTime(double value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepCases#getMinCaseTime <em>Min Case Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetMinCaseTime()
     * @see #getMinCaseTime()
     * @see #setMinCaseTime(double)
     * @generated
     */
    void unsetMinCaseTime();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepCases#getMinCaseTime <em>Min Case Time</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Min Case Time</em>' attribute is set.
     * @see #unsetMinCaseTime()
     * @see #getMinCaseTime()
     * @see #setMinCaseTime(double)
     * @generated
     */
    boolean isSetMinCaseTime();

    /**
     * Returns the value of the '<em><b>Max Case Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * What was the shortest time of processing the case.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Max Case Time</em>' attribute.
     * @see #isSetMaxCaseTime()
     * @see #unsetMaxCaseTime()
     * @see #setMaxCaseTime(double)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepCases_MaxCaseTime()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
     *        extendedMetaData="kind='element' name='MaxCaseTime' namespace='##targetNamespace'"
     * @generated
     */
    double getMaxCaseTime();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepCases#getMaxCaseTime <em>Max Case Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Max Case Time</em>' attribute.
     * @see #isSetMaxCaseTime()
     * @see #unsetMaxCaseTime()
     * @see #getMaxCaseTime()
     * @generated
     */
    void setMaxCaseTime(double value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepCases#getMaxCaseTime <em>Max Case Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetMaxCaseTime()
     * @see #getMaxCaseTime()
     * @see #setMaxCaseTime(double)
     * @generated
     */
    void unsetMaxCaseTime();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepCases#getMaxCaseTime <em>Max Case Time</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Max Case Time</em>' attribute is set.
     * @see #unsetMaxCaseTime()
     * @see #getMaxCaseTime()
     * @see #setMaxCaseTime(double)
     * @generated
     */
    boolean isSetMaxCaseTime();

    /**
     * Returns the value of the '<em><b>Case Cost</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Case cost statistic data.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Case Cost</em>' containment reference.
     * @see #setCaseCost(SimRepCost)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepCases_CaseCost()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='CaseCost' namespace='##targetNamespace'"
     * @generated
     */
    SimRepCost getCaseCost();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepCases#getCaseCost <em>Case Cost</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Case Cost</em>' containment reference.
     * @see #getCaseCost()
     * @generated
     */
    void setCaseCost(SimRepCost value);

} // SimRepCases
