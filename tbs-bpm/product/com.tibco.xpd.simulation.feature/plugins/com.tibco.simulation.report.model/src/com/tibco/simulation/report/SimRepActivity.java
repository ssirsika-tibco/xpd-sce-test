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
 * A representation of the model object '<em><b>Activity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.simulation.report.SimRepActivity#getDurationDistribution <em>Duration Distribution</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepActivity#getActivityQueue <em>Activity Queue</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepActivity#getActivityCost <em>Activity Cost</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepActivity#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepActivity#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepActivity#getAverageDuration <em>Average Duration</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.simulation.report.SimRepPackage#getSimRepActivity()
 * @model extendedMetaData="name='ActivityType' kind='elementOnly'"
 * @generated
 */
public interface SimRepActivity extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Duration Distribution</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Shows what distribution was used to determine times of activiry duration.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Duration Distribution</em>' containment reference.
     * @see #setDurationDistribution(SimRepDistribution)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepActivity_DurationDistribution()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='DurationDistribution' namespace='##targetNamespace'"
     * @generated
     */
    SimRepDistribution getDurationDistribution();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepActivity#getDurationDistribution <em>Duration Distribution</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Duration Distribution</em>' containment reference.
     * @see #getDurationDistribution()
     * @generated
     */
    void setDurationDistribution(SimRepDistribution value);

    /**
     * Returns the value of the '<em><b>Activity Queue</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Represent queue which is used by activity to holds cases to be processed by activity.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Activity Queue</em>' containment reference.
     * @see #setActivityQueue(SimRepActivityQueue)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepActivity_ActivityQueue()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='ActivityQueue' namespace='##targetNamespace'"
     * @generated
     */
    SimRepActivityQueue getActivityQueue();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepActivity#getActivityQueue <em>Activity Queue</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Activity Queue</em>' containment reference.
     * @see #getActivityQueue()
     * @generated
     */
    void setActivityQueue(SimRepActivityQueue value);

    /**
     * Returns the value of the '<em><b>Activity Cost</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Activity Cost</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Activity Cost</em>' containment reference.
     * @see #setActivityCost(SimRepCost)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepActivity_ActivityCost()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='ActivityCost' namespace='##targetNamespace'"
     * @generated
     */
    SimRepCost getActivityCost();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepActivity#getActivityCost <em>Activity Cost</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Activity Cost</em>' containment reference.
     * @see #getActivityCost()
     * @generated
     */
    void setActivityCost(SimRepCost value);

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
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepActivity_Id()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='Id' namespace='##targetNamespace'"
     * @generated
     */
    String getId();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepActivity#getId <em>Id</em>}' attribute.
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
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepActivity_Name()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='Name' namespace='##targetNamespace'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepActivity#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Average Duration</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Average Duration</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Average Duration</em>' attribute.
     * @see #setAverageDuration(double)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepActivity_AverageDuration()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
     *        extendedMetaData="kind='attribute' name='AverageDuration' namespace='##targetNamespace'"
     * @generated
     */
    double getAverageDuration();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepActivity#getAverageDuration <em>Average Duration</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Average Duration</em>' attribute.
     * @see #getAverageDuration()
     * @generated
     */
    void setAverageDuration(double value);

} // SimRepActivity
