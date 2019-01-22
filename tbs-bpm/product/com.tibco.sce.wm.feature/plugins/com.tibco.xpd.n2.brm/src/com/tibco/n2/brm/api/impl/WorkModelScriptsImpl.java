/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.WorkModelScript;
import com.tibco.n2.brm.api.WorkModelScripts;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Work Model Scripts</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkModelScriptsImpl#getWorkModelScript <em>Work Model Script</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkModelScriptsImpl extends EObjectImpl implements WorkModelScripts {
    /**
     * The cached value of the '{@link #getWorkModelScript() <em>Work Model Script</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkModelScript()
     * @generated
     * @ordered
     */
    protected EList<WorkModelScript> workModelScript;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorkModelScriptsImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.WORK_MODEL_SCRIPTS;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<WorkModelScript> getWorkModelScript() {
        if (workModelScript == null) {
            workModelScript = new EObjectContainmentEList<WorkModelScript>(WorkModelScript.class, this, N2BRMPackage.WORK_MODEL_SCRIPTS__WORK_MODEL_SCRIPT);
        }
        return workModelScript;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.WORK_MODEL_SCRIPTS__WORK_MODEL_SCRIPT:
                return ((InternalEList<?>)getWorkModelScript()).basicRemove(otherEnd, msgs);
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
            case N2BRMPackage.WORK_MODEL_SCRIPTS__WORK_MODEL_SCRIPT:
                return getWorkModelScript();
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
            case N2BRMPackage.WORK_MODEL_SCRIPTS__WORK_MODEL_SCRIPT:
                getWorkModelScript().clear();
                getWorkModelScript().addAll((Collection<? extends WorkModelScript>)newValue);
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
            case N2BRMPackage.WORK_MODEL_SCRIPTS__WORK_MODEL_SCRIPT:
                getWorkModelScript().clear();
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
            case N2BRMPackage.WORK_MODEL_SCRIPTS__WORK_MODEL_SCRIPT:
                return workModelScript != null && !workModelScript.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //WorkModelScriptsImpl
