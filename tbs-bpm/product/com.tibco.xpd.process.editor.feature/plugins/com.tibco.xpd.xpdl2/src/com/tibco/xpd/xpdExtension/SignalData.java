/**
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.DataMapping;

import com.tibco.xpd.xpdl2.OtherElementsContainer;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Signal Data</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Container for Signal specific data like Correlation mappings, Data mappings and Reschedule configuration, used by  TriggerResultSignal for Intermediate Catch Events.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.SignalData#getCorrelationMappings <em>Correlation Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.SignalData#getDataMappings <em>Data Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.SignalData#getRescheduleTimers <em>Reschedule Timers</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.SignalData#getInputScriptDataMapper <em>Input Script Data Mapper</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.SignalData#getOutputScriptDataMapper <em>Output Script Data Mapper</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getSignalData()
 * @model extendedMetaData="name='SignalData' kind='elementOnly'"
 * @generated
 */
public interface SignalData extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Correlation Mappings</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Correlation Mappings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Correlation Mappings</em>' containment reference.
	 * @see #setCorrelationMappings(CorrelationDataMappings)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getSignalData_CorrelationMappings()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='CorrelationMappings' namespace='##targetNamespace'"
	 * @generated
	 */
	CorrelationDataMappings getCorrelationMappings();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.SignalData#getCorrelationMappings <em>Correlation Mappings</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Correlation Mappings</em>' containment reference.
	 * @see #getCorrelationMappings()
	 * @generated
	 */
	void setCorrelationMappings(CorrelationDataMappings value);

	/**
	 * Returns the value of the '<em><b>Data Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.xpd.xpdl2.DataMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Mappings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Mappings</em>' containment reference list.
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getSignalData_DataMappings()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='DataMapping' namespace='##targetNamespace' wrap='DataMappings'"
	 * @generated
	 */
	EList<DataMapping> getDataMappings();

	/**
	 * Returns the value of the '<em><b>Reschedule Timers</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reschedule Timers</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reschedule Timers</em>' containment reference.
	 * @see #setRescheduleTimers(RescheduleTimers)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getSignalData_RescheduleTimers()
	 * @model containment="true"
	 *        extendedMetaData="name='RescheduleTimers' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	RescheduleTimers getRescheduleTimers();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.SignalData#getRescheduleTimers <em>Reschedule Timers</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reschedule Timers</em>' containment reference.
	 * @see #getRescheduleTimers()
	 * @generated
	 */
	void setRescheduleTimers(RescheduleTimers value);

	/**
	 * Returns the value of the '<em><b>Input Script Data Mapper</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Input Script Data Mapper</em>' containment reference.
	 * @see #setInputScriptDataMapper(ScriptDataMapper)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getSignalData_InputScriptDataMapper()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='InputScriptDataMapper' namespace='##targetNamespace'"
	 * @generated
	 */
	ScriptDataMapper getInputScriptDataMapper();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.SignalData#getInputScriptDataMapper <em>Input Script Data Mapper</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Input Script Data Mapper</em>' containment reference.
	 * @see #getInputScriptDataMapper()
	 * @generated
	 */
	void setInputScriptDataMapper(ScriptDataMapper value);

	/**
	 * Returns the value of the '<em><b>Output Script Data Mapper</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output Script Data Mapper</em>' containment reference.
	 * @see #setOutputScriptDataMapper(ScriptDataMapper)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getSignalData_OutputScriptDataMapper()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='OutputScriptDataMapper' namespace='##targetNamespace'"
	 * @generated
	 */
	ScriptDataMapper getOutputScriptDataMapper();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.SignalData#getOutputScriptDataMapper <em>Output Script Data Mapper</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Output Script Data Mapper</em>' containment reference.
	 * @see #getOutputScriptDataMapper()
	 * @generated
	 */
	void setOutputScriptDataMapper(ScriptDataMapper value);

} // SignalData
