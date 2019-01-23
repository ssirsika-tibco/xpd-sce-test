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
 * A representation of the model object '<em><b>Cost</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.simulation.report.SimRepCost#getAverageCost <em>Average Cost</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepCost#getMinCost <em>Min Cost</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepCost#getMaxCost <em>Max Cost</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepCost#getCumulativeAverageCost <em>Cumulative Average Cost</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.simulation.report.SimRepPackage#getSimRepCost()
 * @model extendedMetaData="name='CostType' kind='elementOnly'"
 * @generated
 */
public interface SimRepCost extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Average Cost</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Average cost for one case.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Average Cost</em>' attribute.
     * @see #isSetAverageCost()
     * @see #unsetAverageCost()
     * @see #setAverageCost(double)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepCost_AverageCost()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
     *        extendedMetaData="kind='element' name='AverageCost' namespace='##targetNamespace'"
     * @generated
     */
    double getAverageCost();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepCost#getAverageCost <em>Average Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Average Cost</em>' attribute.
     * @see #isSetAverageCost()
     * @see #unsetAverageCost()
     * @see #getAverageCost()
     * @generated
     */
    void setAverageCost(double value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepCost#getAverageCost <em>Average Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetAverageCost()
     * @see #getAverageCost()
     * @see #setAverageCost(double)
     * @generated
     */
    void unsetAverageCost();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepCost#getAverageCost <em>Average Cost</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Average Cost</em>' attribute is set.
     * @see #unsetAverageCost()
     * @see #getAverageCost()
     * @see #setAverageCost(double)
     * @generated
     */
    boolean isSetAverageCost();

    /**
     * Returns the value of the '<em><b>Min Cost</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Minimal cost for one case.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Min Cost</em>' attribute.
     * @see #isSetMinCost()
     * @see #unsetMinCost()
     * @see #setMinCost(double)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepCost_MinCost()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
     *        extendedMetaData="kind='element' name='MinCost' namespace='##targetNamespace'"
     * @generated
     */
    double getMinCost();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepCost#getMinCost <em>Min Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Min Cost</em>' attribute.
     * @see #isSetMinCost()
     * @see #unsetMinCost()
     * @see #getMinCost()
     * @generated
     */
    void setMinCost(double value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepCost#getMinCost <em>Min Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetMinCost()
     * @see #getMinCost()
     * @see #setMinCost(double)
     * @generated
     */
    void unsetMinCost();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepCost#getMinCost <em>Min Cost</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Min Cost</em>' attribute is set.
     * @see #unsetMinCost()
     * @see #getMinCost()
     * @see #setMinCost(double)
     * @generated
     */
    boolean isSetMinCost();

    /**
     * Returns the value of the '<em><b>Max Cost</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Maximal cost for one case.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Max Cost</em>' attribute.
     * @see #isSetMaxCost()
     * @see #unsetMaxCost()
     * @see #setMaxCost(double)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepCost_MaxCost()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
     *        extendedMetaData="kind='element' name='MaxCost' namespace='##targetNamespace'"
     * @generated
     */
    double getMaxCost();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepCost#getMaxCost <em>Max Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Max Cost</em>' attribute.
     * @see #isSetMaxCost()
     * @see #unsetMaxCost()
     * @see #getMaxCost()
     * @generated
     */
    void setMaxCost(double value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepCost#getMaxCost <em>Max Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetMaxCost()
     * @see #getMaxCost()
     * @see #setMaxCost(double)
     * @generated
     */
    void unsetMaxCost();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepCost#getMaxCost <em>Max Cost</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Max Cost</em>' attribute is set.
     * @see #unsetMaxCost()
     * @see #getMaxCost()
     * @see #setMaxCost(double)
     * @generated
     */
    boolean isSetMaxCost();

    /**
     * Returns the value of the '<em><b>Cumulative Average Cost</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Sum of average cost of all cases 
     * <!-- end-model-doc -->
     * @return the value of the '<em>Cumulative Average Cost</em>' attribute.
     * @see #isSetCumulativeAverageCost()
     * @see #unsetCumulativeAverageCost()
     * @see #setCumulativeAverageCost(double)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepCost_CumulativeAverageCost()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
     *        extendedMetaData="kind='element' name='CumulativeAverageCost' namespace='##targetNamespace'"
     * @generated
     */
    double getCumulativeAverageCost();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepCost#getCumulativeAverageCost <em>Cumulative Average Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Cumulative Average Cost</em>' attribute.
     * @see #isSetCumulativeAverageCost()
     * @see #unsetCumulativeAverageCost()
     * @see #getCumulativeAverageCost()
     * @generated
     */
    void setCumulativeAverageCost(double value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepCost#getCumulativeAverageCost <em>Cumulative Average Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetCumulativeAverageCost()
     * @see #getCumulativeAverageCost()
     * @see #setCumulativeAverageCost(double)
     * @generated
     */
    void unsetCumulativeAverageCost();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepCost#getCumulativeAverageCost <em>Cumulative Average Cost</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Cumulative Average Cost</em>' attribute is set.
     * @see #unsetCumulativeAverageCost()
     * @see #getCumulativeAverageCost()
     * @see #setCumulativeAverageCost(double)
     * @generated
     */
    boolean isSetCumulativeAverageCost();

} // SimRepCost
