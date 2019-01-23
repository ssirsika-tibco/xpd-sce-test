/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.Operation;
import com.tibco.xpd.deploy.ServerElementState;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Server Element State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerElementStateImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerElementStateImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.impl.ServerElementStateImpl#getPossibleOperations <em>Possible Operations</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ServerElementStateImpl extends EObjectImpl implements
        ServerElementState {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected static final String DESCRIPTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected String description = DESCRIPTION_EDEFAULT;

    /**
     * The cached value of the '{@link #getPossibleOperations() <em>Possible Operations</em>}' reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getPossibleOperations()
     * @generated
     * @ordered
     */
    protected EList<Operation> possibleOperations;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected ServerElementStateImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DeployPackage.Literals.SERVER_ELEMENT_STATE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.SERVER_ELEMENT_STATE__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getDescription() {
        return description;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setDescription(String newDescription) {
        String oldDescription = description;
        description = newDescription;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DeployPackage.SERVER_ELEMENT_STATE__DESCRIPTION,
                    oldDescription, description));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<Operation> getPossibleOperations() {
        if (possibleOperations == null) {
            possibleOperations = new EObjectResolvingEList<Operation>(
                    Operation.class, this,
                    DeployPackage.SERVER_ELEMENT_STATE__POSSIBLE_OPERATIONS);
        }
        return possibleOperations;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case DeployPackage.SERVER_ELEMENT_STATE__NAME:
            return getName();
        case DeployPackage.SERVER_ELEMENT_STATE__DESCRIPTION:
            return getDescription();
        case DeployPackage.SERVER_ELEMENT_STATE__POSSIBLE_OPERATIONS:
            return getPossibleOperations();
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
        case DeployPackage.SERVER_ELEMENT_STATE__NAME:
            setName((String) newValue);
            return;
        case DeployPackage.SERVER_ELEMENT_STATE__DESCRIPTION:
            setDescription((String) newValue);
            return;
        case DeployPackage.SERVER_ELEMENT_STATE__POSSIBLE_OPERATIONS:
            getPossibleOperations().clear();
            getPossibleOperations().addAll(
                    (Collection<? extends Operation>) newValue);
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
        case DeployPackage.SERVER_ELEMENT_STATE__NAME:
            setName(NAME_EDEFAULT);
            return;
        case DeployPackage.SERVER_ELEMENT_STATE__DESCRIPTION:
            setDescription(DESCRIPTION_EDEFAULT);
            return;
        case DeployPackage.SERVER_ELEMENT_STATE__POSSIBLE_OPERATIONS:
            getPossibleOperations().clear();
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
        case DeployPackage.SERVER_ELEMENT_STATE__NAME:
            return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
                    .equals(name);
        case DeployPackage.SERVER_ELEMENT_STATE__DESCRIPTION:
            return DESCRIPTION_EDEFAULT == null ? description != null
                    : !DESCRIPTION_EDEFAULT.equals(description);
        case DeployPackage.SERVER_ELEMENT_STATE__POSSIBLE_OPERATIONS:
            return possibleOperations != null && !possibleOperations.isEmpty();
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (name: ");
        result.append(name);
        result.append(", description: ");
        result.append(description);
        result.append(')');
        return result.toString();
    }

} // ServerElementStateImpl
