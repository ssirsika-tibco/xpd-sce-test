/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.xpdl2.Expression;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>User Task Scripts</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Container for the Schedule, Reschedule, Open, Close, Submit scripts for a Work Item of a User Task.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.UserTaskScripts#getOpenScript <em>Open Script</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.UserTaskScripts#getCloseScript <em>Close Script</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.UserTaskScripts#getSubmitScript <em>Submit Script</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.UserTaskScripts#getScheduleScript <em>Schedule Script</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.UserTaskScripts#getRescheduleScript <em>Reschedule Script</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getUserTaskScripts()
 * @model extendedMetaData="name='UserTaskScripts' kind='elementOnly'"
 * @generated
 */
public interface UserTaskScripts extends EObject {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Open Script</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Open Script</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Open Script</em>' containment reference.
     * @see #setOpenScript(Expression)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getUserTaskScripts_OpenScript()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='OpenScript' namespace='##targetNamespace'"
     * @generated
     */
    Expression getOpenScript();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.UserTaskScripts#getOpenScript <em>Open Script</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Open Script</em>' containment reference.
     * @see #getOpenScript()
     * @generated
     */
    void setOpenScript(Expression value);

    /**
     * Returns the value of the '<em><b>Close Script</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Close Script</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Close Script</em>' containment reference.
     * @see #setCloseScript(Expression)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getUserTaskScripts_CloseScript()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='CloseScript' namespace='##targetNamespace'"
     * @generated
     */
    Expression getCloseScript();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.UserTaskScripts#getCloseScript <em>Close Script</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Close Script</em>' containment reference.
     * @see #getCloseScript()
     * @generated
     */
    void setCloseScript(Expression value);

    /**
     * Returns the value of the '<em><b>Submit Script</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Submit Script</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Submit Script</em>' containment reference.
     * @see #setSubmitScript(Expression)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getUserTaskScripts_SubmitScript()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='SubmitScript' namespace='##targetNamespace'"
     * @generated
     */
    Expression getSubmitScript();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.UserTaskScripts#getSubmitScript <em>Submit Script</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Submit Script</em>' containment reference.
     * @see #getSubmitScript()
     * @generated
     */
    void setSubmitScript(Expression value);

    /**
     * Returns the value of the '<em><b>Schedule Script</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Schedule Script</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Schedule Script</em>' containment reference.
     * @see #setScheduleScript(Expression)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getUserTaskScripts_ScheduleScript()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ScheduleScript' namespace='##targetNamespace'"
     * @generated
     */
    Expression getScheduleScript();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.UserTaskScripts#getScheduleScript <em>Schedule Script</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Schedule Script</em>' containment reference.
     * @see #getScheduleScript()
     * @generated
     */
    void setScheduleScript(Expression value);

    /**
     * Returns the value of the '<em><b>Reschedule Script</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Reschedule Script</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Reschedule Script</em>' containment reference.
     * @see #setRescheduleScript(Expression)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getUserTaskScripts_RescheduleScript()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='RescheduleScript' namespace='##targetNamespace'"
     * @generated
     */
    Expression getRescheduleScript();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.UserTaskScripts#getRescheduleScript <em>Reschedule Script</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Reschedule Script</em>' containment reference.
     * @see #getRescheduleScript()
     * @generated
     */
    void setRescheduleScript(Expression value);

} // UserTaskScripts