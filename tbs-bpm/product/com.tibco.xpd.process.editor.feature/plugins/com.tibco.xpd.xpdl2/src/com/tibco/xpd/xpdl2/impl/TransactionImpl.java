/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdl2.Transaction;
import com.tibco.xpd.xpdl2.TransactionMethodType;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transaction</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TransactionImpl#getTransactionId <em>Transaction Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TransactionImpl#getTransactionMethod <em>Transaction Method</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TransactionImpl#getTransactionProtocol <em>Transaction Protocol</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TransactionImpl extends EObjectImpl implements Transaction {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getTransactionId() <em>Transaction Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTransactionId()
     * @generated
     * @ordered
     */
    protected static final String TRANSACTION_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTransactionId() <em>Transaction Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTransactionId()
     * @generated
     * @ordered
     */
    protected String transactionId = TRANSACTION_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getTransactionMethod() <em>Transaction Method</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTransactionMethod()
     * @generated
     * @ordered
     */
    protected static final TransactionMethodType TRANSACTION_METHOD_EDEFAULT = TransactionMethodType.COMPENSATE_LITERAL;

    /**
     * The cached value of the '{@link #getTransactionMethod() <em>Transaction Method</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTransactionMethod()
     * @generated
     * @ordered
     */
    protected TransactionMethodType transactionMethod = TRANSACTION_METHOD_EDEFAULT;

    /**
     * This is true if the Transaction Method attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean transactionMethodESet;

    /**
     * The default value of the '{@link #getTransactionProtocol() <em>Transaction Protocol</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTransactionProtocol()
     * @generated
     * @ordered
     */
    protected static final String TRANSACTION_PROTOCOL_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTransactionProtocol() <em>Transaction Protocol</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTransactionProtocol()
     * @generated
     * @ordered
     */
    protected String transactionProtocol = TRANSACTION_PROTOCOL_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TransactionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.TRANSACTION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTransactionId(String newTransactionId) {
        String oldTransactionId = transactionId;
        transactionId = newTransactionId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.TRANSACTION__TRANSACTION_ID,
                    oldTransactionId, transactionId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TransactionMethodType getTransactionMethod() {
        return transactionMethod;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTransactionMethod(TransactionMethodType newTransactionMethod) {
        TransactionMethodType oldTransactionMethod = transactionMethod;
        transactionMethod = newTransactionMethod == null ? TRANSACTION_METHOD_EDEFAULT : newTransactionMethod;
        boolean oldTransactionMethodESet = transactionMethodESet;
        transactionMethodESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.TRANSACTION__TRANSACTION_METHOD,
                    oldTransactionMethod, transactionMethod, !oldTransactionMethodESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetTransactionMethod() {
        TransactionMethodType oldTransactionMethod = transactionMethod;
        boolean oldTransactionMethodESet = transactionMethodESet;
        transactionMethod = TRANSACTION_METHOD_EDEFAULT;
        transactionMethodESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.TRANSACTION__TRANSACTION_METHOD,
                    oldTransactionMethod, TRANSACTION_METHOD_EDEFAULT, oldTransactionMethodESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetTransactionMethod() {
        return transactionMethodESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getTransactionProtocol() {
        return transactionProtocol;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTransactionProtocol(String newTransactionProtocol) {
        String oldTransactionProtocol = transactionProtocol;
        transactionProtocol = newTransactionProtocol;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.TRANSACTION__TRANSACTION_PROTOCOL,
                    oldTransactionProtocol, transactionProtocol));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case Xpdl2Package.TRANSACTION__TRANSACTION_ID:
            return getTransactionId();
        case Xpdl2Package.TRANSACTION__TRANSACTION_METHOD:
            return getTransactionMethod();
        case Xpdl2Package.TRANSACTION__TRANSACTION_PROTOCOL:
            return getTransactionProtocol();
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
        case Xpdl2Package.TRANSACTION__TRANSACTION_ID:
            setTransactionId((String) newValue);
            return;
        case Xpdl2Package.TRANSACTION__TRANSACTION_METHOD:
            setTransactionMethod((TransactionMethodType) newValue);
            return;
        case Xpdl2Package.TRANSACTION__TRANSACTION_PROTOCOL:
            setTransactionProtocol((String) newValue);
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
        case Xpdl2Package.TRANSACTION__TRANSACTION_ID:
            setTransactionId(TRANSACTION_ID_EDEFAULT);
            return;
        case Xpdl2Package.TRANSACTION__TRANSACTION_METHOD:
            unsetTransactionMethod();
            return;
        case Xpdl2Package.TRANSACTION__TRANSACTION_PROTOCOL:
            setTransactionProtocol(TRANSACTION_PROTOCOL_EDEFAULT);
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
        case Xpdl2Package.TRANSACTION__TRANSACTION_ID:
            return TRANSACTION_ID_EDEFAULT == null ? transactionId != null
                    : !TRANSACTION_ID_EDEFAULT.equals(transactionId);
        case Xpdl2Package.TRANSACTION__TRANSACTION_METHOD:
            return isSetTransactionMethod();
        case Xpdl2Package.TRANSACTION__TRANSACTION_PROTOCOL:
            return TRANSACTION_PROTOCOL_EDEFAULT == null ? transactionProtocol != null
                    : !TRANSACTION_PROTOCOL_EDEFAULT.equals(transactionProtocol);
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
        result.append(" (transactionId: "); //$NON-NLS-1$
        result.append(transactionId);
        result.append(", transactionMethod: "); //$NON-NLS-1$
        if (transactionMethodESet)
            result.append(transactionMethod);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", transactionProtocol: "); //$NON-NLS-1$
        result.append(transactionProtocol);
        result.append(')');
        return result.toString();
    }

} //TransactionImpl
