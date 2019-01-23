/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Task</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Task#getTaskService <em>Task Service</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Task#getTaskReceive <em>Task Receive</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Task#getTaskManual <em>Task Manual</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Task#getTaskReference <em>Task Reference</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Task#getTaskScript <em>Task Script</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Task#getTaskSend <em>Task Send</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Task#getTaskUser <em>Task User</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Task#getTaskApplication <em>Task Application</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTask()
 * @model extendedMetaData="name='Task_._type' kind='elementOnly' features-order='taskService taskReceive taskManual taskReference taskScript taskSend taskUser taskApplication'"
 * @generated
 */
public interface Task extends Implementation {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Task Service</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Task Service</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Task Service</em>' containment reference.
     * @see #setTaskService(TaskService)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTask_TaskService()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TaskService' namespace='##targetNamespace'"
     * @generated
     */
    TaskService getTaskService();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Task#getTaskService <em>Task Service</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Task Service</em>' containment reference.
     * @see #getTaskService()
     * @generated
     */
    void setTaskService(TaskService value);

    /**
     * Returns the value of the '<em><b>Task Receive</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Task Receive</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Task Receive</em>' containment reference.
     * @see #setTaskReceive(TaskReceive)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTask_TaskReceive()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TaskReceive' namespace='##targetNamespace'"
     * @generated
     */
    TaskReceive getTaskReceive();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Task#getTaskReceive <em>Task Receive</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Task Receive</em>' containment reference.
     * @see #getTaskReceive()
     * @generated
     */
    void setTaskReceive(TaskReceive value);

    /**
     * Returns the value of the '<em><b>Task Manual</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Task Manual</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Task Manual</em>' containment reference.
     * @see #setTaskManual(TaskManual)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTask_TaskManual()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TaskManual' namespace='##targetNamespace'"
     * @generated
     */
    TaskManual getTaskManual();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Task#getTaskManual <em>Task Manual</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Task Manual</em>' containment reference.
     * @see #getTaskManual()
     * @generated
     */
    void setTaskManual(TaskManual value);

    /**
     * Returns the value of the '<em><b>Task Reference</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Task Reference</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Task Reference</em>' containment reference.
     * @see #setTaskReference(TaskReference)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTask_TaskReference()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TaskReference' namespace='##targetNamespace'"
     * @generated
     */
    TaskReference getTaskReference();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Task#getTaskReference <em>Task Reference</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Task Reference</em>' containment reference.
     * @see #getTaskReference()
     * @generated
     */
    void setTaskReference(TaskReference value);

    /**
     * Returns the value of the '<em><b>Task Script</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Task Script</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Task Script</em>' containment reference.
     * @see #setTaskScript(TaskScript)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTask_TaskScript()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TaskScript' namespace='##targetNamespace'"
     * @generated
     */
    TaskScript getTaskScript();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Task#getTaskScript <em>Task Script</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Task Script</em>' containment reference.
     * @see #getTaskScript()
     * @generated
     */
    void setTaskScript(TaskScript value);

    /**
     * Returns the value of the '<em><b>Task Send</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Task Send</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Task Send</em>' containment reference.
     * @see #setTaskSend(TaskSend)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTask_TaskSend()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TaskSend' namespace='##targetNamespace'"
     * @generated
     */
    TaskSend getTaskSend();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Task#getTaskSend <em>Task Send</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Task Send</em>' containment reference.
     * @see #getTaskSend()
     * @generated
     */
    void setTaskSend(TaskSend value);

    /**
     * Returns the value of the '<em><b>Task User</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Task User</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Task User</em>' containment reference.
     * @see #setTaskUser(TaskUser)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTask_TaskUser()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TaskUser' namespace='##targetNamespace'"
     * @generated
     */
    TaskUser getTaskUser();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Task#getTaskUser <em>Task User</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Task User</em>' containment reference.
     * @see #getTaskUser()
     * @generated
     */
    void setTaskUser(TaskUser value);

    /**
     * Returns the value of the '<em><b>Task Application</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Task Application</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Task Application</em>' containment reference.
     * @see #setTaskApplication(TaskApplication)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTask_TaskApplication()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TaskApplication' namespace='##targetNamespace'"
     * @generated
     */
    TaskApplication getTaskApplication();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Task#getTaskApplication <em>Task Application</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Task Application</em>' containment reference.
     * @see #getTaskApplication()
     * @generated
     */
    void setTaskApplication(TaskApplication value);

} // Task
