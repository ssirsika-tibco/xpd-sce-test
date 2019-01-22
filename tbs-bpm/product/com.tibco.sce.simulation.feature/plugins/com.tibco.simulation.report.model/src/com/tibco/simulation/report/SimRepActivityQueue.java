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
 * A representation of the model object '<em><b>Activity Queue</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.simulation.report.SimRepActivityQueue#getQueueOrder <em>Queue Order</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepActivityQueue#getObservedCases <em>Observed Cases</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepActivityQueue#getCurrentSize <em>Current Size</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepActivityQueue#getMaxSize <em>Max Size</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepActivityQueue#getAverageSize <em>Average Size</em>}</li>
 *   <li>{@link com.tibco.simulation.report.SimRepActivityQueue#getAverageWait <em>Average Wait</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.simulation.report.SimRepPackage#getSimRepActivityQueue()
 * @model extendedMetaData="name='ActivityQueueType' kind='elementOnly'"
 * @generated
 */
public interface SimRepActivityQueue extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Queue Order</b></em>' attribute.
     * The default value is <code>"FIFO"</code>.
     * The literals are from the enumeration {@link com.tibco.simulation.report.SimRepQueueOrder}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Queue Order</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Queue Order</em>' attribute.
     * @see com.tibco.simulation.report.SimRepQueueOrder
     * @see #isSetQueueOrder()
     * @see #unsetQueueOrder()
     * @see #setQueueOrder(SimRepQueueOrder)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepActivityQueue_QueueOrder()
     * @model default="FIFO" unique="false" unsettable="true" required="true"
     *        extendedMetaData="kind='element' name='QueueOrder' namespace='##targetNamespace'"
     * @generated
     */
    SimRepQueueOrder getQueueOrder();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepActivityQueue#getQueueOrder <em>Queue Order</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Queue Order</em>' attribute.
     * @see com.tibco.simulation.report.SimRepQueueOrder
     * @see #isSetQueueOrder()
     * @see #unsetQueueOrder()
     * @see #getQueueOrder()
     * @generated
     */
    void setQueueOrder(SimRepQueueOrder value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepActivityQueue#getQueueOrder <em>Queue Order</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetQueueOrder()
     * @see #getQueueOrder()
     * @see #setQueueOrder(SimRepQueueOrder)
     * @generated
     */
    void unsetQueueOrder();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepActivityQueue#getQueueOrder <em>Queue Order</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Queue Order</em>' attribute is set.
     * @see #unsetQueueOrder()
     * @see #getQueueOrder()
     * @see #setQueueOrder(SimRepQueueOrder)
     * @generated
     */
    boolean isSetQueueOrder();

    /**
     * Returns the value of the '<em><b>Observed Cases</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Observed Cases</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Observed Cases</em>' attribute.
     * @see #isSetObservedCases()
     * @see #unsetObservedCases()
     * @see #setObservedCases(int)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepActivityQueue_ObservedCases()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
     *        extendedMetaData="kind='element' name='ObservedCases' namespace='##targetNamespace'"
     * @generated
     */
    int getObservedCases();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepActivityQueue#getObservedCases <em>Observed Cases</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Observed Cases</em>' attribute.
     * @see #isSetObservedCases()
     * @see #unsetObservedCases()
     * @see #getObservedCases()
     * @generated
     */
    void setObservedCases(int value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepActivityQueue#getObservedCases <em>Observed Cases</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetObservedCases()
     * @see #getObservedCases()
     * @see #setObservedCases(int)
     * @generated
     */
    void unsetObservedCases();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepActivityQueue#getObservedCases <em>Observed Cases</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Observed Cases</em>' attribute is set.
     * @see #unsetObservedCases()
     * @see #getObservedCases()
     * @see #setObservedCases(int)
     * @generated
     */
    boolean isSetObservedCases();

    /**
     * Returns the value of the '<em><b>Current Size</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Current Size</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Current Size</em>' attribute.
     * @see #isSetCurrentSize()
     * @see #unsetCurrentSize()
     * @see #setCurrentSize(int)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepActivityQueue_CurrentSize()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
     *        extendedMetaData="kind='element' name='CurrentSize' namespace='##targetNamespace'"
     * @generated
     */
    int getCurrentSize();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepActivityQueue#getCurrentSize <em>Current Size</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Current Size</em>' attribute.
     * @see #isSetCurrentSize()
     * @see #unsetCurrentSize()
     * @see #getCurrentSize()
     * @generated
     */
    void setCurrentSize(int value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepActivityQueue#getCurrentSize <em>Current Size</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetCurrentSize()
     * @see #getCurrentSize()
     * @see #setCurrentSize(int)
     * @generated
     */
    void unsetCurrentSize();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepActivityQueue#getCurrentSize <em>Current Size</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Current Size</em>' attribute is set.
     * @see #unsetCurrentSize()
     * @see #getCurrentSize()
     * @see #setCurrentSize(int)
     * @generated
     */
    boolean isSetCurrentSize();

    /**
     * Returns the value of the '<em><b>Max Size</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Max Size</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Max Size</em>' attribute.
     * @see #isSetMaxSize()
     * @see #unsetMaxSize()
     * @see #setMaxSize(int)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepActivityQueue_MaxSize()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
     *        extendedMetaData="kind='element' name='MaxSize' namespace='##targetNamespace'"
     * @generated
     */
    int getMaxSize();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepActivityQueue#getMaxSize <em>Max Size</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Max Size</em>' attribute.
     * @see #isSetMaxSize()
     * @see #unsetMaxSize()
     * @see #getMaxSize()
     * @generated
     */
    void setMaxSize(int value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepActivityQueue#getMaxSize <em>Max Size</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetMaxSize()
     * @see #getMaxSize()
     * @see #setMaxSize(int)
     * @generated
     */
    void unsetMaxSize();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepActivityQueue#getMaxSize <em>Max Size</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Max Size</em>' attribute is set.
     * @see #unsetMaxSize()
     * @see #getMaxSize()
     * @see #setMaxSize(int)
     * @generated
     */
    boolean isSetMaxSize();

    /**
     * Returns the value of the '<em><b>Average Size</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Average Size</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Average Size</em>' attribute.
     * @see #isSetAverageSize()
     * @see #unsetAverageSize()
     * @see #setAverageSize(double)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepActivityQueue_AverageSize()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
     *        extendedMetaData="kind='element' name='AverageSize' namespace='##targetNamespace'"
     * @generated
     */
    double getAverageSize();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepActivityQueue#getAverageSize <em>Average Size</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Average Size</em>' attribute.
     * @see #isSetAverageSize()
     * @see #unsetAverageSize()
     * @see #getAverageSize()
     * @generated
     */
    void setAverageSize(double value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepActivityQueue#getAverageSize <em>Average Size</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetAverageSize()
     * @see #getAverageSize()
     * @see #setAverageSize(double)
     * @generated
     */
    void unsetAverageSize();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepActivityQueue#getAverageSize <em>Average Size</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Average Size</em>' attribute is set.
     * @see #unsetAverageSize()
     * @see #getAverageSize()
     * @see #setAverageSize(double)
     * @generated
     */
    boolean isSetAverageSize();

    /**
     * Returns the value of the '<em><b>Average Wait</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Average Wait</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Average Wait</em>' attribute.
     * @see #isSetAverageWait()
     * @see #unsetAverageWait()
     * @see #setAverageWait(double)
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepActivityQueue_AverageWait()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
     *        extendedMetaData="kind='element' name='AverageWait' namespace='##targetNamespace'"
     * @generated
     */
    double getAverageWait();

    /**
     * Sets the value of the '{@link com.tibco.simulation.report.SimRepActivityQueue#getAverageWait <em>Average Wait</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Average Wait</em>' attribute.
     * @see #isSetAverageWait()
     * @see #unsetAverageWait()
     * @see #getAverageWait()
     * @generated
     */
    void setAverageWait(double value);

    /**
     * Unsets the value of the '{@link com.tibco.simulation.report.SimRepActivityQueue#getAverageWait <em>Average Wait</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetAverageWait()
     * @see #getAverageWait()
     * @see #setAverageWait(double)
     * @generated
     */
    void unsetAverageWait();

    /**
     * Returns whether the value of the '{@link com.tibco.simulation.report.SimRepActivityQueue#getAverageWait <em>Average Wait</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Average Wait</em>' attribute is set.
     * @see #unsetAverageWait()
     * @see #getAverageWait()
     * @see #setAverageWait(double)
     * @generated
     */
    boolean isSetAverageWait();

} // SimRepActivityQueue
