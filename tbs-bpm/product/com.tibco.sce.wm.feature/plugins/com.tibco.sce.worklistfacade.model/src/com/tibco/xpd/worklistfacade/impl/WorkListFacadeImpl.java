/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.worklistfacade.impl;

import com.tibco.xpd.worklistfacade.model.WorkItemAttributes;
import com.tibco.xpd.worklistfacade.model.WorkListFacade;
import com.tibco.xpd.worklistfacade.model.WorkListFacadePackage;

import java.math.BigInteger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Work List Facade</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.worklistfacade.impl.WorkListFacadeImpl#getWorkItemAttributes <em>Work Item Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.worklistfacade.impl.WorkListFacadeImpl#getFormatVersion <em>Format Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkListFacadeImpl extends EObjectImpl implements WorkListFacade {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getWorkItemAttributes() <em>Work Item Attributes</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkItemAttributes()
     * @generated
     * @ordered
     */
    protected WorkItemAttributes workItemAttributes;

    /**
     * The default value of the '{@link #getFormatVersion() <em>Format Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFormatVersion()
     * @generated
     * @ordered
     */
    protected static final BigInteger FORMAT_VERSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFormatVersion() <em>Format Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFormatVersion()
     * @generated
     * @ordered
     */
    protected BigInteger formatVersion = FORMAT_VERSION_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorkListFacadeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return WorkListFacadePackage.Literals.WORK_LIST_FACADE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkItemAttributes getWorkItemAttributes() {
        return workItemAttributes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWorkItemAttributes(
            WorkItemAttributes newWorkItemAttributes, NotificationChain msgs) {
        WorkItemAttributes oldWorkItemAttributes = workItemAttributes;
        workItemAttributes = newWorkItemAttributes;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(
                            this,
                            Notification.SET,
                            WorkListFacadePackage.WORK_LIST_FACADE__WORK_ITEM_ATTRIBUTES,
                            oldWorkItemAttributes, newWorkItemAttributes);
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
    public void setWorkItemAttributes(WorkItemAttributes newWorkItemAttributes) {
        if (newWorkItemAttributes != workItemAttributes) {
            NotificationChain msgs = null;
            if (workItemAttributes != null)
                msgs =
                        ((InternalEObject) workItemAttributes)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - WorkListFacadePackage.WORK_LIST_FACADE__WORK_ITEM_ATTRIBUTES,
                                        null,
                                        msgs);
            if (newWorkItemAttributes != null)
                msgs =
                        ((InternalEObject) newWorkItemAttributes)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - WorkListFacadePackage.WORK_LIST_FACADE__WORK_ITEM_ATTRIBUTES,
                                        null,
                                        msgs);
            msgs = basicSetWorkItemAttributes(newWorkItemAttributes, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    WorkListFacadePackage.WORK_LIST_FACADE__WORK_ITEM_ATTRIBUTES,
                    newWorkItemAttributes, newWorkItemAttributes));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigInteger getFormatVersion() {
        return formatVersion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFormatVersion(BigInteger newFormatVersion) {
        BigInteger oldFormatVersion = formatVersion;
        formatVersion = newFormatVersion;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    WorkListFacadePackage.WORK_LIST_FACADE__FORMAT_VERSION,
                    oldFormatVersion, formatVersion));
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
        case WorkListFacadePackage.WORK_LIST_FACADE__WORK_ITEM_ATTRIBUTES:
            return basicSetWorkItemAttributes(null, msgs);
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
        case WorkListFacadePackage.WORK_LIST_FACADE__WORK_ITEM_ATTRIBUTES:
            return getWorkItemAttributes();
        case WorkListFacadePackage.WORK_LIST_FACADE__FORMAT_VERSION:
            return getFormatVersion();
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
        case WorkListFacadePackage.WORK_LIST_FACADE__WORK_ITEM_ATTRIBUTES:
            setWorkItemAttributes((WorkItemAttributes) newValue);
            return;
        case WorkListFacadePackage.WORK_LIST_FACADE__FORMAT_VERSION:
            setFormatVersion((BigInteger) newValue);
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
        case WorkListFacadePackage.WORK_LIST_FACADE__WORK_ITEM_ATTRIBUTES:
            setWorkItemAttributes((WorkItemAttributes) null);
            return;
        case WorkListFacadePackage.WORK_LIST_FACADE__FORMAT_VERSION:
            setFormatVersion(FORMAT_VERSION_EDEFAULT);
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
        case WorkListFacadePackage.WORK_LIST_FACADE__WORK_ITEM_ATTRIBUTES:
            return workItemAttributes != null;
        case WorkListFacadePackage.WORK_LIST_FACADE__FORMAT_VERSION:
            return FORMAT_VERSION_EDEFAULT == null ? formatVersion != null
                    : !FORMAT_VERSION_EDEFAULT.equals(formatVersion);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (formatVersion: "); //$NON-NLS-1$
        result.append(formatVersion);
        result.append(')');
        return result.toString();
    }

} //WorkListFacadeImpl
