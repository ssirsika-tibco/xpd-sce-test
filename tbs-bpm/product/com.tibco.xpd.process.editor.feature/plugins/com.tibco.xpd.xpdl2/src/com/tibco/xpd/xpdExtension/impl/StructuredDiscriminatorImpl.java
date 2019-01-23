/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import java.math.BigInteger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdExtension.StructuredDiscriminator;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Structured Discriminator</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.StructuredDiscriminatorImpl#getWaitForIncomingPath <em>Wait For Incoming Path</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.StructuredDiscriminatorImpl#getUpStreamParallelSplit <em>Up Stream Parallel Split</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StructuredDiscriminatorImpl extends EObjectImpl
        implements StructuredDiscriminator {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getWaitForIncomingPath() <em>Wait For Incoming Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWaitForIncomingPath()
     * @generated
     * @ordered
     */
    protected static final BigInteger WAIT_FOR_INCOMING_PATH_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getWaitForIncomingPath() <em>Wait For Incoming Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWaitForIncomingPath()
     * @generated
     * @ordered
     */
    protected BigInteger waitForIncomingPath = WAIT_FOR_INCOMING_PATH_EDEFAULT;

    /**
     * The default value of the '{@link #getUpStreamParallelSplit() <em>Up Stream Parallel Split</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUpStreamParallelSplit()
     * @generated
     * @ordered
     */
    protected static final String UP_STREAM_PARALLEL_SPLIT_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getUpStreamParallelSplit() <em>Up Stream Parallel Split</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUpStreamParallelSplit()
     * @generated
     * @ordered
     */
    protected String upStreamParallelSplit = UP_STREAM_PARALLEL_SPLIT_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected StructuredDiscriminatorImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.STRUCTURED_DISCRIMINATOR;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigInteger getWaitForIncomingPath() {
        return waitForIncomingPath;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWaitForIncomingPath(BigInteger newWaitForIncomingPath) {
        BigInteger oldWaitForIncomingPath = waitForIncomingPath;
        waitForIncomingPath = newWaitForIncomingPath;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.STRUCTURED_DISCRIMINATOR__WAIT_FOR_INCOMING_PATH,
                    oldWaitForIncomingPath, waitForIncomingPath));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getUpStreamParallelSplit() {
        return upStreamParallelSplit;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setUpStreamParallelSplit(String newUpStreamParallelSplit) {
        String oldUpStreamParallelSplit = upStreamParallelSplit;
        upStreamParallelSplit = newUpStreamParallelSplit;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.STRUCTURED_DISCRIMINATOR__UP_STREAM_PARALLEL_SPLIT,
                    oldUpStreamParallelSplit, upStreamParallelSplit));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case XpdExtensionPackage.STRUCTURED_DISCRIMINATOR__WAIT_FOR_INCOMING_PATH:
            return getWaitForIncomingPath();
        case XpdExtensionPackage.STRUCTURED_DISCRIMINATOR__UP_STREAM_PARALLEL_SPLIT:
            return getUpStreamParallelSplit();
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
        case XpdExtensionPackage.STRUCTURED_DISCRIMINATOR__WAIT_FOR_INCOMING_PATH:
            setWaitForIncomingPath((BigInteger) newValue);
            return;
        case XpdExtensionPackage.STRUCTURED_DISCRIMINATOR__UP_STREAM_PARALLEL_SPLIT:
            setUpStreamParallelSplit((String) newValue);
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
        case XpdExtensionPackage.STRUCTURED_DISCRIMINATOR__WAIT_FOR_INCOMING_PATH:
            setWaitForIncomingPath(WAIT_FOR_INCOMING_PATH_EDEFAULT);
            return;
        case XpdExtensionPackage.STRUCTURED_DISCRIMINATOR__UP_STREAM_PARALLEL_SPLIT:
            setUpStreamParallelSplit(UP_STREAM_PARALLEL_SPLIT_EDEFAULT);
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
        case XpdExtensionPackage.STRUCTURED_DISCRIMINATOR__WAIT_FOR_INCOMING_PATH:
            return WAIT_FOR_INCOMING_PATH_EDEFAULT == null
                    ? waitForIncomingPath != null
                    : !WAIT_FOR_INCOMING_PATH_EDEFAULT
                            .equals(waitForIncomingPath);
        case XpdExtensionPackage.STRUCTURED_DISCRIMINATOR__UP_STREAM_PARALLEL_SPLIT:
            return UP_STREAM_PARALLEL_SPLIT_EDEFAULT == null
                    ? upStreamParallelSplit != null
                    : !UP_STREAM_PARALLEL_SPLIT_EDEFAULT
                            .equals(upStreamParallelSplit);
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
        result.append(" (waitForIncomingPath: "); //$NON-NLS-1$
        result.append(waitForIncomingPath);
        result.append(", UpStreamParallelSplit: "); //$NON-NLS-1$
        result.append(upStreamParallelSplit);
        result.append(')');
        return result.toString();
    }

} //StructuredDiscriminatorImpl
