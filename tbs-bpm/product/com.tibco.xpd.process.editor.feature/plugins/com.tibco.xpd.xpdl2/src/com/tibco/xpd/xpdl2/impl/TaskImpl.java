/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskApplication;
import com.tibco.xpd.xpdl2.TaskManual;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TaskReference;
import com.tibco.xpd.xpdl2.TaskScript;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.TaskUser;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Task</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TaskImpl#getTaskService <em>Task Service</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TaskImpl#getTaskReceive <em>Task Receive</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TaskImpl#getTaskManual <em>Task Manual</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TaskImpl#getTaskReference <em>Task Reference</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TaskImpl#getTaskScript <em>Task Script</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TaskImpl#getTaskSend <em>Task Send</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TaskImpl#getTaskUser <em>Task User</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TaskImpl#getTaskApplication <em>Task Application</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TaskImpl extends ImplementationImpl implements Task {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getTaskService() <em>Task Service</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTaskService()
     * @generated
     * @ordered
     */
    protected TaskService taskService;

    /**
     * The cached value of the '{@link #getTaskReceive() <em>Task Receive</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTaskReceive()
     * @generated
     * @ordered
     */
    protected TaskReceive taskReceive;

    /**
     * The cached value of the '{@link #getTaskManual() <em>Task Manual</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTaskManual()
     * @generated
     * @ordered
     */
    protected TaskManual taskManual;

    /**
     * The cached value of the '{@link #getTaskReference() <em>Task Reference</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTaskReference()
     * @generated
     * @ordered
     */
    protected TaskReference taskReference;

    /**
     * The cached value of the '{@link #getTaskScript() <em>Task Script</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTaskScript()
     * @generated
     * @ordered
     */
    protected TaskScript taskScript;

    /**
     * The cached value of the '{@link #getTaskSend() <em>Task Send</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTaskSend()
     * @generated
     * @ordered
     */
    protected TaskSend taskSend;

    /**
     * The cached value of the '{@link #getTaskUser() <em>Task User</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTaskUser()
     * @generated
     * @ordered
     */
    protected TaskUser taskUser;

    /**
     * The cached value of the '{@link #getTaskApplication() <em>Task Application</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTaskApplication()
     * @generated
     * @ordered
     */
    protected TaskApplication taskApplication;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TaskImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.TASK;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TaskService getTaskService() {
        return taskService;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTaskService(TaskService newTaskService,
            NotificationChain msgs) {
        TaskService oldTaskService = taskService;
        taskService = newTaskService;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.TASK__TASK_SERVICE, oldTaskService,
                            newTaskService);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTaskService(TaskService newTaskService) {
        if (newTaskService != taskService) {
            NotificationChain msgs = null;
            if (taskService != null)
                msgs =
                        ((InternalEObject) taskService).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.TASK__TASK_SERVICE,
                                null,
                                msgs);
            if (newTaskService != null)
                msgs =
                        ((InternalEObject) newTaskService).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.TASK__TASK_SERVICE,
                                null,
                                msgs);
            msgs = basicSetTaskService(newTaskService, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TASK__TASK_SERVICE, newTaskService,
                    newTaskService));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TaskReceive getTaskReceive() {
        return taskReceive;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTaskReceive(TaskReceive newTaskReceive,
            NotificationChain msgs) {
        TaskReceive oldTaskReceive = taskReceive;
        taskReceive = newTaskReceive;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.TASK__TASK_RECEIVE, oldTaskReceive,
                            newTaskReceive);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTaskReceive(TaskReceive newTaskReceive) {
        if (newTaskReceive != taskReceive) {
            NotificationChain msgs = null;
            if (taskReceive != null)
                msgs =
                        ((InternalEObject) taskReceive).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.TASK__TASK_RECEIVE,
                                null,
                                msgs);
            if (newTaskReceive != null)
                msgs =
                        ((InternalEObject) newTaskReceive).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.TASK__TASK_RECEIVE,
                                null,
                                msgs);
            msgs = basicSetTaskReceive(newTaskReceive, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TASK__TASK_RECEIVE, newTaskReceive,
                    newTaskReceive));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TaskManual getTaskManual() {
        return taskManual;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTaskManual(TaskManual newTaskManual,
            NotificationChain msgs) {
        TaskManual oldTaskManual = taskManual;
        taskManual = newTaskManual;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.TASK__TASK_MANUAL, oldTaskManual,
                            newTaskManual);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTaskManual(TaskManual newTaskManual) {
        if (newTaskManual != taskManual) {
            NotificationChain msgs = null;
            if (taskManual != null)
                msgs =
                        ((InternalEObject) taskManual).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.TASK__TASK_MANUAL,
                                null,
                                msgs);
            if (newTaskManual != null)
                msgs =
                        ((InternalEObject) newTaskManual).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.TASK__TASK_MANUAL,
                                null,
                                msgs);
            msgs = basicSetTaskManual(newTaskManual, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TASK__TASK_MANUAL, newTaskManual,
                    newTaskManual));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TaskReference getTaskReference() {
        return taskReference;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTaskReference(
            TaskReference newTaskReference, NotificationChain msgs) {
        TaskReference oldTaskReference = taskReference;
        taskReference = newTaskReference;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.TASK__TASK_REFERENCE,
                            oldTaskReference, newTaskReference);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTaskReference(TaskReference newTaskReference) {
        if (newTaskReference != taskReference) {
            NotificationChain msgs = null;
            if (taskReference != null)
                msgs =
                        ((InternalEObject) taskReference).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.TASK__TASK_REFERENCE,
                                null,
                                msgs);
            if (newTaskReference != null)
                msgs =
                        ((InternalEObject) newTaskReference).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.TASK__TASK_REFERENCE,
                                null,
                                msgs);
            msgs = basicSetTaskReference(newTaskReference, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TASK__TASK_REFERENCE, newTaskReference,
                    newTaskReference));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TaskScript getTaskScript() {
        return taskScript;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTaskScript(TaskScript newTaskScript,
            NotificationChain msgs) {
        TaskScript oldTaskScript = taskScript;
        taskScript = newTaskScript;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.TASK__TASK_SCRIPT, oldTaskScript,
                            newTaskScript);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTaskScript(TaskScript newTaskScript) {
        if (newTaskScript != taskScript) {
            NotificationChain msgs = null;
            if (taskScript != null)
                msgs =
                        ((InternalEObject) taskScript).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.TASK__TASK_SCRIPT,
                                null,
                                msgs);
            if (newTaskScript != null)
                msgs =
                        ((InternalEObject) newTaskScript).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.TASK__TASK_SCRIPT,
                                null,
                                msgs);
            msgs = basicSetTaskScript(newTaskScript, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TASK__TASK_SCRIPT, newTaskScript,
                    newTaskScript));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TaskSend getTaskSend() {
        return taskSend;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTaskSend(TaskSend newTaskSend,
            NotificationChain msgs) {
        TaskSend oldTaskSend = taskSend;
        taskSend = newTaskSend;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.TASK__TASK_SEND, oldTaskSend,
                            newTaskSend);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTaskSend(TaskSend newTaskSend) {
        if (newTaskSend != taskSend) {
            NotificationChain msgs = null;
            if (taskSend != null)
                msgs =
                        ((InternalEObject) taskSend).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.TASK__TASK_SEND,
                                null,
                                msgs);
            if (newTaskSend != null)
                msgs =
                        ((InternalEObject) newTaskSend).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.TASK__TASK_SEND,
                                null,
                                msgs);
            msgs = basicSetTaskSend(newTaskSend, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TASK__TASK_SEND, newTaskSend, newTaskSend));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TaskUser getTaskUser() {
        return taskUser;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTaskUser(TaskUser newTaskUser,
            NotificationChain msgs) {
        TaskUser oldTaskUser = taskUser;
        taskUser = newTaskUser;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.TASK__TASK_USER, oldTaskUser,
                            newTaskUser);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTaskUser(TaskUser newTaskUser) {
        if (newTaskUser != taskUser) {
            NotificationChain msgs = null;
            if (taskUser != null)
                msgs =
                        ((InternalEObject) taskUser).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.TASK__TASK_USER,
                                null,
                                msgs);
            if (newTaskUser != null)
                msgs =
                        ((InternalEObject) newTaskUser).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.TASK__TASK_USER,
                                null,
                                msgs);
            msgs = basicSetTaskUser(newTaskUser, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TASK__TASK_USER, newTaskUser, newTaskUser));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TaskApplication getTaskApplication() {
        return taskApplication;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTaskApplication(
            TaskApplication newTaskApplication, NotificationChain msgs) {
        TaskApplication oldTaskApplication = taskApplication;
        taskApplication = newTaskApplication;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.TASK__TASK_APPLICATION,
                            oldTaskApplication, newTaskApplication);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTaskApplication(TaskApplication newTaskApplication) {
        if (newTaskApplication != taskApplication) {
            NotificationChain msgs = null;
            if (taskApplication != null)
                msgs =
                        ((InternalEObject) taskApplication)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.TASK__TASK_APPLICATION,
                                        null,
                                        msgs);
            if (newTaskApplication != null)
                msgs =
                        ((InternalEObject) newTaskApplication)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.TASK__TASK_APPLICATION,
                                        null,
                                        msgs);
            msgs = basicSetTaskApplication(newTaskApplication, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TASK__TASK_APPLICATION, newTaskApplication,
                    newTaskApplication));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.TASK__TASK_SERVICE:
            return basicSetTaskService(null, msgs);
        case Xpdl2Package.TASK__TASK_RECEIVE:
            return basicSetTaskReceive(null, msgs);
        case Xpdl2Package.TASK__TASK_MANUAL:
            return basicSetTaskManual(null, msgs);
        case Xpdl2Package.TASK__TASK_REFERENCE:
            return basicSetTaskReference(null, msgs);
        case Xpdl2Package.TASK__TASK_SCRIPT:
            return basicSetTaskScript(null, msgs);
        case Xpdl2Package.TASK__TASK_SEND:
            return basicSetTaskSend(null, msgs);
        case Xpdl2Package.TASK__TASK_USER:
            return basicSetTaskUser(null, msgs);
        case Xpdl2Package.TASK__TASK_APPLICATION:
            return basicSetTaskApplication(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case Xpdl2Package.TASK__TASK_SERVICE:
            return getTaskService();
        case Xpdl2Package.TASK__TASK_RECEIVE:
            return getTaskReceive();
        case Xpdl2Package.TASK__TASK_MANUAL:
            return getTaskManual();
        case Xpdl2Package.TASK__TASK_REFERENCE:
            return getTaskReference();
        case Xpdl2Package.TASK__TASK_SCRIPT:
            return getTaskScript();
        case Xpdl2Package.TASK__TASK_SEND:
            return getTaskSend();
        case Xpdl2Package.TASK__TASK_USER:
            return getTaskUser();
        case Xpdl2Package.TASK__TASK_APPLICATION:
            return getTaskApplication();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case Xpdl2Package.TASK__TASK_SERVICE:
            setTaskService((TaskService) newValue);
            return;
        case Xpdl2Package.TASK__TASK_RECEIVE:
            setTaskReceive((TaskReceive) newValue);
            return;
        case Xpdl2Package.TASK__TASK_MANUAL:
            setTaskManual((TaskManual) newValue);
            return;
        case Xpdl2Package.TASK__TASK_REFERENCE:
            setTaskReference((TaskReference) newValue);
            return;
        case Xpdl2Package.TASK__TASK_SCRIPT:
            setTaskScript((TaskScript) newValue);
            return;
        case Xpdl2Package.TASK__TASK_SEND:
            setTaskSend((TaskSend) newValue);
            return;
        case Xpdl2Package.TASK__TASK_USER:
            setTaskUser((TaskUser) newValue);
            return;
        case Xpdl2Package.TASK__TASK_APPLICATION:
            setTaskApplication((TaskApplication) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case Xpdl2Package.TASK__TASK_SERVICE:
            setTaskService((TaskService) null);
            return;
        case Xpdl2Package.TASK__TASK_RECEIVE:
            setTaskReceive((TaskReceive) null);
            return;
        case Xpdl2Package.TASK__TASK_MANUAL:
            setTaskManual((TaskManual) null);
            return;
        case Xpdl2Package.TASK__TASK_REFERENCE:
            setTaskReference((TaskReference) null);
            return;
        case Xpdl2Package.TASK__TASK_SCRIPT:
            setTaskScript((TaskScript) null);
            return;
        case Xpdl2Package.TASK__TASK_SEND:
            setTaskSend((TaskSend) null);
            return;
        case Xpdl2Package.TASK__TASK_USER:
            setTaskUser((TaskUser) null);
            return;
        case Xpdl2Package.TASK__TASK_APPLICATION:
            setTaskApplication((TaskApplication) null);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case Xpdl2Package.TASK__TASK_SERVICE:
            return taskService != null;
        case Xpdl2Package.TASK__TASK_RECEIVE:
            return taskReceive != null;
        case Xpdl2Package.TASK__TASK_MANUAL:
            return taskManual != null;
        case Xpdl2Package.TASK__TASK_REFERENCE:
            return taskReference != null;
        case Xpdl2Package.TASK__TASK_SCRIPT:
            return taskScript != null;
        case Xpdl2Package.TASK__TASK_SEND:
            return taskSend != null;
        case Xpdl2Package.TASK__TASK_USER:
            return taskUser != null;
        case Xpdl2Package.TASK__TASK_APPLICATION:
            return taskApplication != null;
        }
        return super.eIsSet(featureID);
    }

} //TaskImpl
