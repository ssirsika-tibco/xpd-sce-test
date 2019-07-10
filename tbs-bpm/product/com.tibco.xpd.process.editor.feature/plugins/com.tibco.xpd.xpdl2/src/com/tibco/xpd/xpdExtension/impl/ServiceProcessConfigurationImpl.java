/**
 * Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.ServiceProcessConfiguration;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Service Process Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ServiceProcessConfigurationImpl#isDeployToProcessRuntime <em>Deploy To Process Runtime</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ServiceProcessConfigurationImpl#isDeployToPageflowRuntime <em>Deploy To Pageflow Runtime</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ServiceProcessConfigurationImpl extends EObjectImpl implements ServiceProcessConfiguration {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #isDeployToProcessRuntime() <em>Deploy To Process Runtime</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isDeployToProcessRuntime()
     * @generated
     * @ordered
     */
    protected static final boolean DEPLOY_TO_PROCESS_RUNTIME_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isDeployToProcessRuntime() <em>Deploy To Process Runtime</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isDeployToProcessRuntime()
     * @generated
     * @ordered
     */
    protected boolean deployToProcessRuntime = DEPLOY_TO_PROCESS_RUNTIME_EDEFAULT;

    /**
     * This is true if the Deploy To Process Runtime attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean deployToProcessRuntimeESet;

    /**
     * The default value of the '{@link #isDeployToPageflowRuntime() <em>Deploy To Pageflow Runtime</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isDeployToPageflowRuntime()
     * @generated
     * @ordered
     */
    protected static final boolean DEPLOY_TO_PAGEFLOW_RUNTIME_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isDeployToPageflowRuntime() <em>Deploy To Pageflow Runtime</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isDeployToPageflowRuntime()
     * @generated
     * @ordered
     */
    protected boolean deployToPageflowRuntime = DEPLOY_TO_PAGEFLOW_RUNTIME_EDEFAULT;

    /**
     * This is true if the Deploy To Pageflow Runtime attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean deployToPageflowRuntimeESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ServiceProcessConfigurationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.SERVICE_PROCESS_CONFIGURATION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isDeployToProcessRuntime() {
        return deployToProcessRuntime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDeployToProcessRuntime(boolean newDeployToProcessRuntime) {
        boolean oldDeployToProcessRuntime = deployToProcessRuntime;
        deployToProcessRuntime = newDeployToProcessRuntime;
        boolean oldDeployToProcessRuntimeESet = deployToProcessRuntimeESet;
        deployToProcessRuntimeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.SERVICE_PROCESS_CONFIGURATION__DEPLOY_TO_PROCESS_RUNTIME,
                    oldDeployToProcessRuntime, deployToProcessRuntime, !oldDeployToProcessRuntimeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetDeployToProcessRuntime() {
        boolean oldDeployToProcessRuntime = deployToProcessRuntime;
        boolean oldDeployToProcessRuntimeESet = deployToProcessRuntimeESet;
        deployToProcessRuntime = DEPLOY_TO_PROCESS_RUNTIME_EDEFAULT;
        deployToProcessRuntimeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.SERVICE_PROCESS_CONFIGURATION__DEPLOY_TO_PROCESS_RUNTIME,
                    oldDeployToProcessRuntime, DEPLOY_TO_PROCESS_RUNTIME_EDEFAULT, oldDeployToProcessRuntimeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetDeployToProcessRuntime() {
        return deployToProcessRuntimeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isDeployToPageflowRuntime() {
        return deployToPageflowRuntime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDeployToPageflowRuntime(boolean newDeployToPageflowRuntime) {
        boolean oldDeployToPageflowRuntime = deployToPageflowRuntime;
        deployToPageflowRuntime = newDeployToPageflowRuntime;
        boolean oldDeployToPageflowRuntimeESet = deployToPageflowRuntimeESet;
        deployToPageflowRuntimeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.SERVICE_PROCESS_CONFIGURATION__DEPLOY_TO_PAGEFLOW_RUNTIME,
                    oldDeployToPageflowRuntime, deployToPageflowRuntime, !oldDeployToPageflowRuntimeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetDeployToPageflowRuntime() {
        boolean oldDeployToPageflowRuntime = deployToPageflowRuntime;
        boolean oldDeployToPageflowRuntimeESet = deployToPageflowRuntimeESet;
        deployToPageflowRuntime = DEPLOY_TO_PAGEFLOW_RUNTIME_EDEFAULT;
        deployToPageflowRuntimeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.SERVICE_PROCESS_CONFIGURATION__DEPLOY_TO_PAGEFLOW_RUNTIME,
                    oldDeployToPageflowRuntime, DEPLOY_TO_PAGEFLOW_RUNTIME_EDEFAULT, oldDeployToPageflowRuntimeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetDeployToPageflowRuntime() {
        return deployToPageflowRuntimeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case XpdExtensionPackage.SERVICE_PROCESS_CONFIGURATION__DEPLOY_TO_PROCESS_RUNTIME:
            return isDeployToProcessRuntime();
        case XpdExtensionPackage.SERVICE_PROCESS_CONFIGURATION__DEPLOY_TO_PAGEFLOW_RUNTIME:
            return isDeployToPageflowRuntime();
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
        case XpdExtensionPackage.SERVICE_PROCESS_CONFIGURATION__DEPLOY_TO_PROCESS_RUNTIME:
            setDeployToProcessRuntime((Boolean) newValue);
            return;
        case XpdExtensionPackage.SERVICE_PROCESS_CONFIGURATION__DEPLOY_TO_PAGEFLOW_RUNTIME:
            setDeployToPageflowRuntime((Boolean) newValue);
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
        case XpdExtensionPackage.SERVICE_PROCESS_CONFIGURATION__DEPLOY_TO_PROCESS_RUNTIME:
            unsetDeployToProcessRuntime();
            return;
        case XpdExtensionPackage.SERVICE_PROCESS_CONFIGURATION__DEPLOY_TO_PAGEFLOW_RUNTIME:
            unsetDeployToPageflowRuntime();
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
        case XpdExtensionPackage.SERVICE_PROCESS_CONFIGURATION__DEPLOY_TO_PROCESS_RUNTIME:
            return isSetDeployToProcessRuntime();
        case XpdExtensionPackage.SERVICE_PROCESS_CONFIGURATION__DEPLOY_TO_PAGEFLOW_RUNTIME:
            return isSetDeployToPageflowRuntime();
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

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (deployToProcessRuntime: "); //$NON-NLS-1$
        if (deployToProcessRuntimeESet)
            result.append(deployToProcessRuntime);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", deployToPageflowRuntime: "); //$NON-NLS-1$
        if (deployToPageflowRuntimeESet)
            result.append(deployToPageflowRuntime);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //ServiceProcessConfigurationImpl
