/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdExtension.UserTaskScripts;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Expression;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>User Task Scripts</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.UserTaskScriptsImpl#getOpenScript <em>Open Script</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.UserTaskScriptsImpl#getCloseScript <em>Close Script</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.UserTaskScriptsImpl#getSubmitScript <em>Submit Script</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.UserTaskScriptsImpl#getScheduleScript <em>Schedule Script</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.UserTaskScriptsImpl#getRescheduleScript <em>Reschedule Script</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UserTaskScriptsImpl extends EObjectImpl implements UserTaskScripts {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getOpenScript() <em>Open Script</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOpenScript()
     * @generated
     * @ordered
     */
    protected Expression openScript;

    /**
     * The cached value of the '{@link #getCloseScript() <em>Close Script</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCloseScript()
     * @generated
     * @ordered
     */
    protected Expression closeScript;

    /**
     * The cached value of the '{@link #getSubmitScript() <em>Submit Script</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSubmitScript()
     * @generated
     * @ordered
     */
    protected Expression submitScript;

    /**
     * The cached value of the '{@link #getScheduleScript() <em>Schedule Script</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getScheduleScript()
     * @generated
     * @ordered
     */
    protected Expression scheduleScript;

    /**
     * The cached value of the '{@link #getRescheduleScript() <em>Reschedule Script</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRescheduleScript()
     * @generated
     * @ordered
     */
    protected Expression rescheduleScript;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected UserTaskScriptsImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.USER_TASK_SCRIPTS;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Expression getOpenScript() {
        return openScript;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetOpenScript(Expression newOpenScript, NotificationChain msgs) {
        Expression oldOpenScript = openScript;
        openScript = newOpenScript;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.USER_TASK_SCRIPTS__OPEN_SCRIPT, oldOpenScript, newOpenScript);
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
    public void setOpenScript(Expression newOpenScript) {
        if (newOpenScript != openScript) {
            NotificationChain msgs = null;
            if (openScript != null)
                msgs = ((InternalEObject) openScript).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.USER_TASK_SCRIPTS__OPEN_SCRIPT,
                        null,
                        msgs);
            if (newOpenScript != null)
                msgs = ((InternalEObject) newOpenScript).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.USER_TASK_SCRIPTS__OPEN_SCRIPT,
                        null,
                        msgs);
            msgs = basicSetOpenScript(newOpenScript, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.USER_TASK_SCRIPTS__OPEN_SCRIPT,
                    newOpenScript, newOpenScript));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Expression getCloseScript() {
        return closeScript;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCloseScript(Expression newCloseScript, NotificationChain msgs) {
        Expression oldCloseScript = closeScript;
        closeScript = newCloseScript;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.USER_TASK_SCRIPTS__CLOSE_SCRIPT, oldCloseScript, newCloseScript);
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
    public void setCloseScript(Expression newCloseScript) {
        if (newCloseScript != closeScript) {
            NotificationChain msgs = null;
            if (closeScript != null)
                msgs = ((InternalEObject) closeScript).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.USER_TASK_SCRIPTS__CLOSE_SCRIPT,
                        null,
                        msgs);
            if (newCloseScript != null)
                msgs = ((InternalEObject) newCloseScript).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.USER_TASK_SCRIPTS__CLOSE_SCRIPT,
                        null,
                        msgs);
            msgs = basicSetCloseScript(newCloseScript, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.USER_TASK_SCRIPTS__CLOSE_SCRIPT,
                    newCloseScript, newCloseScript));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Expression getSubmitScript() {
        return submitScript;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSubmitScript(Expression newSubmitScript, NotificationChain msgs) {
        Expression oldSubmitScript = submitScript;
        submitScript = newSubmitScript;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.USER_TASK_SCRIPTS__SUBMIT_SCRIPT, oldSubmitScript, newSubmitScript);
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
    public void setSubmitScript(Expression newSubmitScript) {
        if (newSubmitScript != submitScript) {
            NotificationChain msgs = null;
            if (submitScript != null)
                msgs = ((InternalEObject) submitScript).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.USER_TASK_SCRIPTS__SUBMIT_SCRIPT,
                        null,
                        msgs);
            if (newSubmitScript != null)
                msgs = ((InternalEObject) newSubmitScript).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.USER_TASK_SCRIPTS__SUBMIT_SCRIPT,
                        null,
                        msgs);
            msgs = basicSetSubmitScript(newSubmitScript, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.USER_TASK_SCRIPTS__SUBMIT_SCRIPT,
                    newSubmitScript, newSubmitScript));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Expression getScheduleScript() {
        return scheduleScript;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetScheduleScript(Expression newScheduleScript, NotificationChain msgs) {
        Expression oldScheduleScript = scheduleScript;
        scheduleScript = newScheduleScript;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.USER_TASK_SCRIPTS__SCHEDULE_SCRIPT, oldScheduleScript, newScheduleScript);
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
    public void setScheduleScript(Expression newScheduleScript) {
        if (newScheduleScript != scheduleScript) {
            NotificationChain msgs = null;
            if (scheduleScript != null)
                msgs = ((InternalEObject) scheduleScript).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.USER_TASK_SCRIPTS__SCHEDULE_SCRIPT,
                        null,
                        msgs);
            if (newScheduleScript != null)
                msgs = ((InternalEObject) newScheduleScript).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.USER_TASK_SCRIPTS__SCHEDULE_SCRIPT,
                        null,
                        msgs);
            msgs = basicSetScheduleScript(newScheduleScript, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.USER_TASK_SCRIPTS__SCHEDULE_SCRIPT, newScheduleScript, newScheduleScript));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Expression getRescheduleScript() {
        return rescheduleScript;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetRescheduleScript(Expression newRescheduleScript, NotificationChain msgs) {
        Expression oldRescheduleScript = rescheduleScript;
        rescheduleScript = newRescheduleScript;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.USER_TASK_SCRIPTS__RESCHEDULE_SCRIPT, oldRescheduleScript, newRescheduleScript);
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
    public void setRescheduleScript(Expression newRescheduleScript) {
        if (newRescheduleScript != rescheduleScript) {
            NotificationChain msgs = null;
            if (rescheduleScript != null)
                msgs = ((InternalEObject) rescheduleScript).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.USER_TASK_SCRIPTS__RESCHEDULE_SCRIPT,
                        null,
                        msgs);
            if (newRescheduleScript != null)
                msgs = ((InternalEObject) newRescheduleScript).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.USER_TASK_SCRIPTS__RESCHEDULE_SCRIPT,
                        null,
                        msgs);
            msgs = basicSetRescheduleScript(newRescheduleScript, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.USER_TASK_SCRIPTS__RESCHEDULE_SCRIPT, newRescheduleScript,
                    newRescheduleScript));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case XpdExtensionPackage.USER_TASK_SCRIPTS__OPEN_SCRIPT:
            return basicSetOpenScript(null, msgs);
        case XpdExtensionPackage.USER_TASK_SCRIPTS__CLOSE_SCRIPT:
            return basicSetCloseScript(null, msgs);
        case XpdExtensionPackage.USER_TASK_SCRIPTS__SUBMIT_SCRIPT:
            return basicSetSubmitScript(null, msgs);
        case XpdExtensionPackage.USER_TASK_SCRIPTS__SCHEDULE_SCRIPT:
            return basicSetScheduleScript(null, msgs);
        case XpdExtensionPackage.USER_TASK_SCRIPTS__RESCHEDULE_SCRIPT:
            return basicSetRescheduleScript(null, msgs);
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
        case XpdExtensionPackage.USER_TASK_SCRIPTS__OPEN_SCRIPT:
            return getOpenScript();
        case XpdExtensionPackage.USER_TASK_SCRIPTS__CLOSE_SCRIPT:
            return getCloseScript();
        case XpdExtensionPackage.USER_TASK_SCRIPTS__SUBMIT_SCRIPT:
            return getSubmitScript();
        case XpdExtensionPackage.USER_TASK_SCRIPTS__SCHEDULE_SCRIPT:
            return getScheduleScript();
        case XpdExtensionPackage.USER_TASK_SCRIPTS__RESCHEDULE_SCRIPT:
            return getRescheduleScript();
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
        case XpdExtensionPackage.USER_TASK_SCRIPTS__OPEN_SCRIPT:
            setOpenScript((Expression) newValue);
            return;
        case XpdExtensionPackage.USER_TASK_SCRIPTS__CLOSE_SCRIPT:
            setCloseScript((Expression) newValue);
            return;
        case XpdExtensionPackage.USER_TASK_SCRIPTS__SUBMIT_SCRIPT:
            setSubmitScript((Expression) newValue);
            return;
        case XpdExtensionPackage.USER_TASK_SCRIPTS__SCHEDULE_SCRIPT:
            setScheduleScript((Expression) newValue);
            return;
        case XpdExtensionPackage.USER_TASK_SCRIPTS__RESCHEDULE_SCRIPT:
            setRescheduleScript((Expression) newValue);
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
        case XpdExtensionPackage.USER_TASK_SCRIPTS__OPEN_SCRIPT:
            setOpenScript((Expression) null);
            return;
        case XpdExtensionPackage.USER_TASK_SCRIPTS__CLOSE_SCRIPT:
            setCloseScript((Expression) null);
            return;
        case XpdExtensionPackage.USER_TASK_SCRIPTS__SUBMIT_SCRIPT:
            setSubmitScript((Expression) null);
            return;
        case XpdExtensionPackage.USER_TASK_SCRIPTS__SCHEDULE_SCRIPT:
            setScheduleScript((Expression) null);
            return;
        case XpdExtensionPackage.USER_TASK_SCRIPTS__RESCHEDULE_SCRIPT:
            setRescheduleScript((Expression) null);
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
        case XpdExtensionPackage.USER_TASK_SCRIPTS__OPEN_SCRIPT:
            return openScript != null;
        case XpdExtensionPackage.USER_TASK_SCRIPTS__CLOSE_SCRIPT:
            return closeScript != null;
        case XpdExtensionPackage.USER_TASK_SCRIPTS__SUBMIT_SCRIPT:
            return submitScript != null;
        case XpdExtensionPackage.USER_TASK_SCRIPTS__SCHEDULE_SCRIPT:
            return scheduleScript != null;
        case XpdExtensionPackage.USER_TASK_SCRIPTS__RESCHEDULE_SCRIPT:
            return rescheduleScript != null;
        }
        return super.eIsSet(featureID);
    }

} //UserTaskScriptsImpl