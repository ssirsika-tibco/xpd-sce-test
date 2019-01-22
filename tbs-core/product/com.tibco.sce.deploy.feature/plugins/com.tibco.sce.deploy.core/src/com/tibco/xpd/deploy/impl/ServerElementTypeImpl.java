/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.Operation;
import com.tibco.xpd.deploy.ServerElementState;
import com.tibco.xpd.deploy.ServerElementType;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Server Element Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerElementTypeImpl#getOperations <em>Operations</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerElementTypeImpl#getStates <em>States</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ServerElementTypeImpl extends EObjectImpl implements
        ServerElementType {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * The cached value of the '{@link #getOperations() <em>Operations</em>}' reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getOperations()
     * @generated
     * @ordered
     */
    protected EList<Operation> operations;

    /**
     * The cached value of the '{@link #getStates() <em>States</em>}' reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getStates()
     * @generated
     * @ordered
     */
    protected EList<ServerElementState> states;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected ServerElementTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DeployPackage.Literals.SERVER_ELEMENT_TYPE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<Operation> getOperations() {
        if (operations == null) {
            operations = new EObjectResolvingEList<Operation>(Operation.class,
                    this, DeployPackage.SERVER_ELEMENT_TYPE__OPERATIONS);
        }
        return operations;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<ServerElementState> getStates() {
        if (states == null) {
            states = new EObjectResolvingEList<ServerElementState>(
                    ServerElementState.class, this,
                    DeployPackage.SERVER_ELEMENT_TYPE__STATES);
        }
        return states;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case DeployPackage.SERVER_ELEMENT_TYPE__OPERATIONS:
            return getOperations();
        case DeployPackage.SERVER_ELEMENT_TYPE__STATES:
            return getStates();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case DeployPackage.SERVER_ELEMENT_TYPE__OPERATIONS:
            getOperations().clear();
            getOperations().addAll((Collection<? extends Operation>) newValue);
            return;
        case DeployPackage.SERVER_ELEMENT_TYPE__STATES:
            getStates().clear();
            getStates().addAll(
                    (Collection<? extends ServerElementState>) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case DeployPackage.SERVER_ELEMENT_TYPE__OPERATIONS:
            getOperations().clear();
            return;
        case DeployPackage.SERVER_ELEMENT_TYPE__STATES:
            getStates().clear();
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case DeployPackage.SERVER_ELEMENT_TYPE__OPERATIONS:
            return operations != null && !operations.isEmpty();
        case DeployPackage.SERVER_ELEMENT_TYPE__STATES:
            return states != null && !states.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // ServerElementTypeImpl
