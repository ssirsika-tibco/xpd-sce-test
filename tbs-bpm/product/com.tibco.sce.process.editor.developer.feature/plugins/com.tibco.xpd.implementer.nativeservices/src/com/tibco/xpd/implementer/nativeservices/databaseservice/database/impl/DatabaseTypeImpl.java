/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabasePackage;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabaseType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.OperationType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.DatabaseTypeImpl#getOperation <em>Operation</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DatabaseTypeImpl extends EObjectImpl implements DatabaseType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getOperation() <em>Operation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOperation()
     * @generated
     * @ordered
     */
    protected OperationType operation;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DatabaseTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DatabasePackage.Literals.DATABASE_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OperationType getOperation() {
        return operation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetOperation(OperationType newOperation,
            NotificationChain msgs) {
        OperationType oldOperation = operation;
        operation = newOperation;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            DatabasePackage.DATABASE_TYPE__OPERATION,
                            oldOperation, newOperation);
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
    public void setOperation(OperationType newOperation) {
        if (newOperation != operation) {
            NotificationChain msgs = null;
            if (operation != null)
                msgs =
                        ((InternalEObject) operation)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - DatabasePackage.DATABASE_TYPE__OPERATION,
                                        null,
                                        msgs);
            if (newOperation != null)
                msgs =
                        ((InternalEObject) newOperation)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - DatabasePackage.DATABASE_TYPE__OPERATION,
                                        null,
                                        msgs);
            msgs = basicSetOperation(newOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DatabasePackage.DATABASE_TYPE__OPERATION, newOperation,
                    newOperation));
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
        case DatabasePackage.DATABASE_TYPE__OPERATION:
            return basicSetOperation(null, msgs);
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
        case DatabasePackage.DATABASE_TYPE__OPERATION:
            return getOperation();
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
        case DatabasePackage.DATABASE_TYPE__OPERATION:
            setOperation((OperationType) newValue);
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
        case DatabasePackage.DATABASE_TYPE__OPERATION:
            setOperation((OperationType) null);
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
        case DatabasePackage.DATABASE_TYPE__OPERATION:
            return operation != null;
        }
        return super.eIsSet(featureID);
    }

} //DatabaseTypeImpl