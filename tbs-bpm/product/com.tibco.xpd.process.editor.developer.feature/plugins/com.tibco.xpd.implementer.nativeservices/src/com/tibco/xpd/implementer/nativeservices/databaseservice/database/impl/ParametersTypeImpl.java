/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl;

import com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabasePackage;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.ParameterType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.ParametersType;

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
 * An implementation of the model object '<em><b>Parameters Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.ParametersTypeImpl#getParameter <em>Parameter</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ParametersTypeImpl extends EObjectImpl implements ParametersType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getParameter() <em>Parameter</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getParameter()
     * @generated
     * @ordered
     */
    protected EList<ParameterType> parameter;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ParametersTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DatabasePackage.Literals.PARAMETERS_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ParameterType> getParameter() {
        if (parameter == null) {
            parameter =
                    new EObjectContainmentEList<ParameterType>(
                            ParameterType.class, this,
                            DatabasePackage.PARAMETERS_TYPE__PARAMETER);
        }
        return parameter;
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
        case DatabasePackage.PARAMETERS_TYPE__PARAMETER:
            return ((InternalEList<?>) getParameter()).basicRemove(otherEnd,
                    msgs);
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
        case DatabasePackage.PARAMETERS_TYPE__PARAMETER:
            return getParameter();
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
        case DatabasePackage.PARAMETERS_TYPE__PARAMETER:
            getParameter().clear();
            getParameter()
                    .addAll((Collection<? extends ParameterType>) newValue);
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
        case DatabasePackage.PARAMETERS_TYPE__PARAMETER:
            getParameter().clear();
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
        case DatabasePackage.PARAMETERS_TYPE__PARAMETER:
            return parameter != null && !parameter.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //ParametersTypeImpl