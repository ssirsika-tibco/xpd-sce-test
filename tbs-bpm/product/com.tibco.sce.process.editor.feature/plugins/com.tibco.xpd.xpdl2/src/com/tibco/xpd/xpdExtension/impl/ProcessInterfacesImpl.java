/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Process Interfaces</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ProcessInterfacesImpl#getProcessInterface <em>Process Interface</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProcessInterfacesImpl extends EObjectImpl
        implements ProcessInterfaces {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getProcessInterface() <em>Process Interface</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProcessInterface()
     * @generated
     * @ordered
     */
    protected EList<ProcessInterface> processInterface;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ProcessInterfacesImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.PROCESS_INTERFACES;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ProcessInterface> getProcessInterface() {
        if (processInterface == null) {
            processInterface = new EObjectContainmentEList<ProcessInterface>(
                    ProcessInterface.class, this,
                    XpdExtensionPackage.PROCESS_INTERFACES__PROCESS_INTERFACE);
        }
        return processInterface;
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
        case XpdExtensionPackage.PROCESS_INTERFACES__PROCESS_INTERFACE:
            return ((InternalEList<?>) getProcessInterface())
                    .basicRemove(otherEnd, msgs);
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
        case XpdExtensionPackage.PROCESS_INTERFACES__PROCESS_INTERFACE:
            return getProcessInterface();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case XpdExtensionPackage.PROCESS_INTERFACES__PROCESS_INTERFACE:
            getProcessInterface().clear();
            getProcessInterface()
                    .addAll((Collection<? extends ProcessInterface>) newValue);
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
        case XpdExtensionPackage.PROCESS_INTERFACES__PROCESS_INTERFACE:
            getProcessInterface().clear();
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
        case XpdExtensionPackage.PROCESS_INTERFACES__PROCESS_INTERFACE:
            return processInterface != null && !processInterface.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //ProcessInterfacesImpl