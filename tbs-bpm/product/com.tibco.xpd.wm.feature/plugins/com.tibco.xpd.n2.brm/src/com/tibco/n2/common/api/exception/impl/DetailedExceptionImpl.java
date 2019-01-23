/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.api.exception.impl;

import com.tibco.n2.common.api.exception.DetailedException;
import com.tibco.n2.common.api.exception.ErrorLine;
import com.tibco.n2.common.api.exception.ExceptionPackage;

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
 * An implementation of the model object '<em><b>Detailed Exception</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.common.api.exception.impl.DetailedExceptionImpl#getError <em>Error</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DetailedExceptionImpl extends EObjectImpl implements DetailedException {
    /**
     * The cached value of the '{@link #getError() <em>Error</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getError()
     * @generated
     * @ordered
     */
    protected EList<ErrorLine> error;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DetailedExceptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ExceptionPackage.Literals.DETAILED_EXCEPTION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ErrorLine> getError() {
        if (error == null) {
            error = new EObjectContainmentEList<ErrorLine>(ErrorLine.class, this, ExceptionPackage.DETAILED_EXCEPTION__ERROR);
        }
        return error;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ExceptionPackage.DETAILED_EXCEPTION__ERROR:
                return ((InternalEList<?>)getError()).basicRemove(otherEnd, msgs);
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
            case ExceptionPackage.DETAILED_EXCEPTION__ERROR:
                return getError();
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
            case ExceptionPackage.DETAILED_EXCEPTION__ERROR:
                getError().clear();
                getError().addAll((Collection<? extends ErrorLine>)newValue);
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
            case ExceptionPackage.DETAILED_EXCEPTION__ERROR:
                getError().clear();
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
            case ExceptionPackage.DETAILED_EXCEPTION__ERROR:
                return error != null && !error.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //DetailedExceptionImpl
