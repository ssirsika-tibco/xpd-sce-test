/**
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reschedule Timers</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This element is optionally added under xpdExt:SignalData when the
 * given non-cancelling task boundary signal event is configured to 
 * reschedule timer events also attached to the same task.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.RescheduleTimers#getTimerSelectionType <em>Timer Selection Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.RescheduleTimers#getTimerEvents <em>Timer Events</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRescheduleTimers()
 * @model extendedMetaData="name='RescheduleTimers' kind='Element' namespace='##targetNamespace'"
 * @generated
 */
public interface RescheduleTimers extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Timer Selection Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.RescheduleTimerSelectionType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Timer Selection Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timer Selection Type</em>' attribute.
	 * @see com.tibco.xpd.xpdExtension.RescheduleTimerSelectionType
	 * @see #isSetTimerSelectionType()
	 * @see #unsetTimerSelectionType()
	 * @see #setTimerSelectionType(RescheduleTimerSelectionType)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRescheduleTimers_TimerSelectionType()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='attribute' name='TimerSelection'"
	 * @generated
	 */
	RescheduleTimerSelectionType getTimerSelectionType();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.RescheduleTimers#getTimerSelectionType <em>Timer Selection Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Timer Selection Type</em>' attribute.
	 * @see com.tibco.xpd.xpdExtension.RescheduleTimerSelectionType
	 * @see #isSetTimerSelectionType()
	 * @see #unsetTimerSelectionType()
	 * @see #getTimerSelectionType()
	 * @generated
	 */
	void setTimerSelectionType(RescheduleTimerSelectionType value);

	/**
	 * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.RescheduleTimers#getTimerSelectionType <em>Timer Selection Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetTimerSelectionType()
	 * @see #getTimerSelectionType()
	 * @see #setTimerSelectionType(RescheduleTimerSelectionType)
	 * @generated
	 */
	void unsetTimerSelectionType();

	/**
	 * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.RescheduleTimers#getTimerSelectionType <em>Timer Selection Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Timer Selection Type</em>' attribute is set.
	 * @see #unsetTimerSelectionType()
	 * @see #getTimerSelectionType()
	 * @see #setTimerSelectionType(RescheduleTimerSelectionType)
	 * @generated
	 */
	boolean isSetTimerSelectionType();

	/**
	 * Returns the value of the '<em><b>Timer Events</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.xpd.xpdExtension.ActivityRef}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Timer Events</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timer Events</em>' containment reference list.
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRescheduleTimers_TimerEvents()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='ActivityRef' namespace='##targetNamespace' wrap='TimerEvents'"
	 * @generated
	 */
	EList<ActivityRef> getTimerEvents();

} // RescheduleTimers
